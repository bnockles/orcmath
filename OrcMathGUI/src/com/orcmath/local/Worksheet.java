/*******************************************************************************
 * Copyright (c) 2012-2017 Benjamin Nockles
 *
 * This file is part of OrcMath.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License 
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.orcmath.local;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.orcmath.objects.*;
import com.orcmath.type.*;



public class Worksheet extends MathPage{

  private static String instructions;
  //private AnswerSheet answersPage; 
  private BufferedImage[] answers;
  int[] answerImageMargins;
  double[] answerScaleFactors;
  private static int identifier;//TODO once applet is made, this is no longer static
  private static boolean overwriteVerticalSpacing=false;
  private static float verticalSpacing =45;

  


//no constructor (therefore, all fields must be static :)

//methods  
  public static void main(String[] args) throws DocumentException, IOException {
	    //make the directory for the worksheet
	  	    String directory = "Lessons/" + subject +"/" + subject + " " +unit+"/"+unit+"."+lesson+"/resources/";
	  	    boolean success = (new File(directory)).mkdirs();
	  	    if (success) {
	  	    	System.out.println("Directories: " 
	  	    			+ directory + " created");
	  	    }
	  
	  
	  identifier=Ops.randomInt(10, 99)*100;//TODO once applet is made, this is no longer randomly assigned
//	  Problem problem = new Problem(problemType,difficulty);
//	  numberOfColumns = problem.getNumberOfColumns();
//	  instructions = problem.getInstructions();
	  instructions = mainInstructions;
//	  verticalSpacing = problem.getVerticalSpacing();	    
	  Worksheet w =  new Worksheet();
			  w.createPdf(FILE);
	  w.openFile(FILE);
  }


//this method is called by the worksheet creator applet
public static void create(String[] topicsArray, int difficultyLevel, boolean increasingLevel, int numberOfProblems, int numberOfWorksheets) throws DocumentException, IOException {
	  identifier=Ops.randomInt(10, 99)*100;//TODO once applet is made, this is no longer randomly assigned
	  Problem problem = new Problem(topicsArray[0],difficultyLevel);//TODO this will not work for arrays until this is fixed
	  numberOfColumns = problem.getNumberOfColumns();
	  instructions = problem.getInstructions();
	  verticalSpacing = problem.getVerticalSpacing();
	    
	  new Worksheet().createPdf(FILE);
	  
  }
  
  
  public void createPdf(String filename) throws DocumentException, IOException{
      //step 1
	  Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);//can delete this specifications if I want
	  //step 2
      PdfWriter.getInstance(document, new FileOutputStream(FILE));
      //step 3

      document.open();
      
      answers=new BufferedImage[numberOfProblems*numberOfPages];
      answerImageMargins=new int[numberOfProblems*numberOfPages];
      answerScaleFactors=new double[numberOfProblems*numberOfPages];
      int page=1;
      
     for (int index = 0; index<numberOfPages; index++){
      addHeader(document, page);
      addInstructions(document);
      addContent(document, page);
      document.newPage();
      page++;
     }
      
      addAnswerSheet(document, answerImageMargins, answerScaleFactors, identifier);
      document.close();
  }


//  // iText allows to add metadata to the PDF which can be viewed in your Adobe
//  // Reader
//  // under File -> Properties
//  private static void addMetaData(Document document) {
//    document.addTitle("My first PDF");
//    document.addSubject("Using iText");
//    document.addKeywords("Java, PDF, iText");
//    document.addAuthor("Benjamin Nockles");
//    document.addCreator("Benjamin Nockles");
//  }


  
  private  void addHeader(Document document, int pageNumber) throws DocumentException {
    Paragraph preface = new Paragraph();
    // Lets write a big header
    preface.add(new Paragraph("NAME:", headerFont));
    preface.add(new Paragraph("DATE:", headerFont));
    preface.add(new Paragraph(teacherName, headerFont));
    if (includeID) preface.add(new Paragraph(""+(identifier+pageNumber), smallBold));

    addEmptyLine(preface, 1);//1 blank line

    document.add(preface);
  }

  
  private static void addInstructions(Document document) throws DocumentException{
	  Paragraph text = new Paragraph();
	  text.setAlignment(Element.ALIGN_CENTER);
	  text.add(new Paragraph(headingText, titleBold));
	  text.setAlignment(Element.ALIGN_LEFT);
	  text.add(new Paragraph(instructions,paragraphFont));
	  addEmptyLine(text, 1);
	  document.add(text);
  }
  

  private void addContent(Document document, int pageNumber) throws DocumentException, IOException{
	  PdfPTable table = new PdfPTable(2*numberOfColumns);
	  table.setWidthPercentage(100);
	  table.getDefaultCell().setBorder(0);
	  //table.getDefaultCell().setPaddingBottom(verticalSpacing);
	  

	  //this section must create all of the questions and the BufferedImage[] of answers
	  //answers=new BufferedImage[numberOfProblems];
	  String[] questionAddresses=new String[numberOfProblems];
	  int[] imageMargins=new int[numberOfProblems];
	  double[] scaleFactors=new double[numberOfProblems];
	  boolean[] neverIncludeInstructions = new boolean[numberOfProblems];
	  float[] verticalSpace = new float[numberOfProblems];
	  //answerImageMargins=new int[numberOfProblems*numberOfPages];
	  String questionFileNameStem = "PDFs/Graphics/question";
	  int difficultyStep=1;
	  double d=((double)difficulty)/((double)numberOfProblems);
	  double stepInterval=d;

	  //Prepares a String array of instructions for each question:
	  String[] instructionsForEachQuestion = new String[numberOfProblems];
	  
	  //very important! This piece is what determines the order of the problem types
	  String[] problemOrder = new String[numberOfProblems];
	  //populates the array with types
	  for (int index = 0; index<problemOrder.length; index++) problemOrder[index] = problemTypes[index%(problemTypes.length)];
	  if (!problemTypesAreSorted) Collections.shuffle(Arrays.asList(problemOrder));
	  
	  for (int index=0; index<numberOfProblems;index++){
		  questionAddresses[index]=questionFileNameStem+(index+1)+".png";
		  int answerNumber = (pageNumber-1)*numberOfProblems+index;

		  Problem p;
		  if (problemsIncreaseInDifficulty){
			  p = new Problem(problemOrder[index],difficultyStep);
			  File file = new File(questionAddresses[index]);
			  ImageIO.write(p.getQuestionImage(), "png", file.getAbsoluteFile());
			  difficultyStep=1;
			  difficultyStep=(int)(difficultyStep+stepInterval);
			  stepInterval=stepInterval+d;
		  }
		  else{
			  p = new Problem(problemOrder[index],difficulty);
			  File file = new File(questionAddresses[index]);
			  ImageIO.write(p.getQuestionImage(), "png", file.getAbsoluteFile());
		  }
		  imageMargins[index]=p.getQuestionNeedsThisMuchExtraSpaceOnTop();//when this was zero, it worked for one line problms
		  System.out.println("-------This much space on top: "+imageMargins[index]);
		  scaleFactors[index]=p.getScaleFactor();
		  System.out.println("-------Scale factor: "+scaleFactors[index]);
		  neverIncludeInstructions[index] = p.getWhetherInstructionsAreNeverIncluded();
		  verticalSpace[index] = p.getVerticalSpacing();
		  answerImageMargins[(pageNumber-1)*numberOfProblems+index]=p.getAnswerNeedsThisMuchExtraSpaceOnTop();
		  answerScaleFactors[(pageNumber-1)*numberOfProblems+index]=p.getScaleFactor();
		  p.makeAnswerImage(""+answerNumber);
		  answerFileNames[answerNumber-1]=AnswerSheet.answerAddress+(answerNumber)+".png";
		  
		  instructionsForEachQuestion[index]=p.getInstructions();
	  }
	  addNumberedTableOfImages(table, questionAddresses, imageMargins, scaleFactors, eachQuestionHasItsOwnInstructions, neverIncludeInstructions, instructionsForEachQuestion, verticalSpace, overwriteVerticalSpacing, verticalSpacing);
	  float[] columnWidths;
	  if (numberOfColumns == 1) columnWidths = new float[] {1f, 20f};
	  else columnWidths = new float[] {1f, 10f,1f, 10f};//TODO write a method to determine these numbers
	  table.setWidths(columnWidths);
	  document.add(table);
  }
  



  
  private static void addEmptyLine(Paragraph paragraph, int number) {
    for (int i = 0; i < number; i++) {
      paragraph.add(new Paragraph(" "));
    }
  }
} 
