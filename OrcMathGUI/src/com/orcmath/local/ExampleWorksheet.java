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
import com.orcmath.objects.Ops;
import com.orcmath.type.Type;

public class ExampleWorksheet extends MathPage{

	//fields
	protected static String subject = "A2";
	protected static String unit = "5";
	protected static String lesson = "4";
	protected static String worksheetName = "5.4 Factoring by Grouping Review";
	protected static String FILE = "Lessons/" + subject +"/" + subject + " " +unit+"/"+unit+"."+lesson+"/resources/"+worksheetName+".pdf";
	protected static boolean includeID= false;
	protected static int numberOfPages = 1;
	
	
	protected static String headingText = "Review: Factoring by grouping";
	protected static String mainInstructions = "Although the quadratic formula can be used to solve for the roots of any quadratic," +
			" there are times when factoring by grouping is easier to use. In the problems you will be working on involving removeable " +
			"discontinuity, you will use factoring by grouping to simplify rational expressions. A few examples are laid out for you below. " +
			"Refer to the examples, then try it on your own.";
	protected static String[] questions ={
//		"Read example 1. Compare this to a problem that would require you to only solve for volume. What extra step is involved?",
//		"Read example 2. What is the objective? What extra formulas are involved and how are they used?",
//		"Read example 3. (a) What are you trying to solve for? (b) Are there any extra steps involved? (c)" +
//		" Describe HOW the Pythagorean Theorem is used AND WHY it must be used."
	};
	protected static boolean eachQuestionHasItsOwnInstructions = true;//this has to remain true in order for the question to have a title (theorem)
	protected static int numberOfColumns=1;
	protected static int numberOfProblems = 3;
	protected static boolean problemTypesAreSorted = true;
	protected static int difficulty =3;
	protected static boolean problemsIncreaseInDifficulty = false;
	protected static boolean customDifficulty = false;
	protected static int[] customDifficulties = {1,2,2,2};


	protected static boolean overwriteVerticalSpacing = true;
	protected static float verticalSpacing =5;
	protected static float verticalSpaceBetweenQuestions=100;

	protected static String[] problemTypes = {
//	  	"Geometry: Identify Theorem",	//NOTE: As of right now, only circle theorems are used in this set
//		"Transformations: Translation",
//	  	"Transformations: Reflection",
//	  	"Transformations: Rotation",
//	  	"Transformations: Rotation",
//	  	"Transformations: Dilation",
//	  	"Transformations: Dilation",
//	  	"Transformations: Compositions",
		
//		"Formulas: Volume of a Cylinder",
//		"Formulas: Volume of a Cone",
//		"Formulas: Volume of a Sphere",
//		"Formulas: Volume of a Pyramid",
//		"Formulas: Surface Area of a Cylinder",
//		"Formulas: Surface Area of a Cone",
//		"Formulas: Surface Area of a Sphere",
//		
//		"Circles: Secant Segments",
//		"Circles: Chord Lengths",
//		
//		"Circles: Central Angles",
//		"Circles: Parallel Chords",	
//		"Circles: Tangent Lines",
//		"Circles: Inscribed Angles with a Common Arc",
//		
//		"Circles: Distance Between Center and Chord",
//		"Circles: Pythagorean Theorem with Tangent Lines",
//
//		"Circles: Angles Formed by a Chord and a Tangent",
//		"Circles: Inscribed Angles",
//		"Circles: Intersecting Chords",
//		"Circles: Inscribed Quadrilaterals",
//		"Circles: Secant Angles",
	  	
		
//		"Polynomials: Factoring polynomials",
//		"Polynomials: Factoring by grouping",
//		"Polynomials: Factoring by grouping with GCF",
		"Polynomials: Factoring trinomial by grouping",
		"Polynomials: Factoring trinomial by grouping",
		"Polynomials: Factoring trinomial by grouping and GCF",
//		"Polynomials: Factoring higher degree polynomials",//does not contain explained answer key
		
		//"Functions: Identify functions"
		//"Functions: Use inverse variation to solve for unknown values"
		//"Polynomials: Solve any quadratic",
		//"Rational Expressions: Multiply and divide rational expressions",
		//"Rational Expressions: Add and subtract rational expressions",
		//"Rational Expressions: Simplify complex rational expressions",
		//"Rational Expressions: Simplify polynomials with fractional coefficients",
		//"Rational Expressions: Distribute polynomials with fractional coefficients",
//		"Graphing: Removeable Discontinuity",
//		"Graphing: Determine continuity of piecewise function (algebraically)",
		
//		"Coordinate Geometry: Calculate distance",
//		"Coordinate Geometry: Calculate midpoint",
//		"Coordinate Geometry: Determine the relationship between two lines",
//		"Geometry Basics: Use algebra to calculate the measure of an angle",
//		"Quadrilaterals: Use algebra to calculate angle measures in a quadrilateral",
//		"Quadrilaterals: Algebraically solve using Pythagorean Theorem applications",
//		"Quadrilaterals: Use algebra to calculate measures of congruent parts in a quadrilateral",
		
//		"Coordinate Geometry: Write the equation of a line that is perp. or par. to a given line",
//		"Geometric relationships",
//		"Geometric Constructions: Justifications",
//		"Geometric Constructions: Justifications",
//		"Geometric Constructions",
//		"Locus: Sketch locus in a coordinate plane",
//		"Locus: Identify the number of points",
		
//		"Radical Expressions: Simplify radicals",
//		"Radical Expressions: Add radicals",
//		"Radical Expressions: Rationalize denominators"
//		
//		"Complex Numbers: Rationalize Imaginary Denominators",
//		"Complex Numbers: Multiply Complex Numbers",
//		"Complex Numbers: Simplify powers of i",
		//"Rational Expressions: Simplify polynomials"
		

		};

	//______________________________________________________________________________
	  private static String instructions;
	  //private AnswerSheet answersPage; 
	  private BufferedImage[] answers;
	  int[] answerImageMargins;
	  double[] answerScaleFactors;
	  private static int identifier;//TODO once applet is made, this is no longer static

	  


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
//		  Problem problem = new Problem(problemType,difficulty);
//		  numberOfColumns = problem.getNumberOfColumns();
//		  instructions = problem.getInstructions();
		  instructions = mainInstructions;
//		  verticalSpacing = problem.getVerticalSpacing();	    
		  ExampleWorksheet ew = new ExampleWorksheet();
		  ew.createPdf(FILE);
		  ew.openFile(FILE);
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
	      addQuestions(document,page);
	      document.newPage();
	      page++;
	     }
	    
	      document.close();
	  }
	  


	private void addHeader(Document document, int pageNumber) throws DocumentException {
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
		  
	

		  String[] questionAddresses=new String[numberOfProblems];
		  int[] imageMargins=new int[numberOfProblems];
		  double[] scaleFactors=new double[numberOfProblems];
		  boolean[] neverIncludeInstructions = new boolean[numberOfProblems];
		  float[] verticalSpace = new float[numberOfProblems];
		  String questionFileNameStem = "PDFs/Graphics/question";
		  int difficultyStep=1;
		  double d=((double)difficulty)/((double)numberOfProblems);
		  double stepInterval=d;
		  
		  
	
		  String[] instructionsForEachQuestion = new String[numberOfProblems];
		  
		  //very important! This piece is what determines the order of the problem types
		  String[] problemOrder = new String[numberOfProblems];
		  //populates the array with types
		  for (int index = 0; index<problemOrder.length; index++) problemOrder[index] = problemTypes[index%(problemTypes.length)];
		  if (!problemTypesAreSorted) Collections.shuffle(Arrays.asList(problemOrder));
		  
		  for (int index=0; index<numberOfProblems;index++){
			  questionAddresses[index]=questionFileNameStem+(index+1)+".png";
	
			  Problem p;
	
			  if(customDifficulty){
				  p = new Problem(problemOrder[index],customDifficulties[index%customDifficulties.length]);
			  }else{
				  if (problemsIncreaseInDifficulty){
					  p = new Problem(problemOrder[index],difficultyStep);
					  difficultyStep=1;
					  difficultyStep=(int)(difficultyStep+stepInterval);
					  stepInterval=stepInterval+d;
				  }
				  else{
					  p = new Problem(problemOrder[index],difficulty);	  
				  }
			  }
			  File file = new File(questionAddresses[index]);
			  ImageIO.write(p.getAnswerImage(), "png", file.getAbsoluteFile());
			  imageMargins[index]=p.getAnswerNeedsThisMuchExtraSpaceOnTop();//when this was zero, it worked for one line problms
			  scaleFactors[index]=p.getScaleFactor();
			  neverIncludeInstructions[index] = true;
			  verticalSpace[index] = p.getVerticalSpacing();
			  instructionsForEachQuestion[index]=p.getKeyTheorem();
		  }
		  setLatexFontCode("textbf");
		  addNumberedTableOfImages(table, questionAddresses, imageMargins, scaleFactors, eachQuestionHasItsOwnInstructions, neverIncludeInstructions, instructionsForEachQuestion, verticalSpace,overwriteVerticalSpacing,verticalSpacing);
		  setLatexFontCode("text");
		  
		  float[] columnWidths;
		  if (numberOfColumns == 1) columnWidths = new float[] {1f, 20f};
		  else columnWidths = new float[] {1f, 10f,1f, 10f};//TODO write a method to determine these numbers
		  table.setWidths(columnWidths);
		  document.add(table);
	  }

	private void addQuestions(Document document, int pageNumber) throws DocumentException, IOException{
		  PdfPTable table = new PdfPTable(2);
		  table.setWidthPercentage(100);
		  table.getDefaultCell().setBorder(0);
		  //table.getDefaultCell().setPaddingBottom(verticalSpacing);
		  

		  //this section must create all of the questions and the BufferedImage[] of answers
		  //answers=new BufferedImage[numberOfProblems];
		  String[] questionAddresses=new String[questions.length];
		  int[] imageMargins=new int[questions.length];
		  double[] scaleFactors=new double[questions.length];
		  boolean[] neverIncludeInstructions = new boolean[questions.length];
		  float[] verticalSpace = new float[questions.length];

		  String promptFileNameStem = "PDFs/Graphics/prompt";

		  //Prepares a String array of instructions for each question:
		  String[] instructionsForEachQuestion = questions;
		  
		  
		  for (int index=0; index<questions.length; index++){
			  questionAddresses[index]=promptFileNameStem+(index+1)+".png";


			  File file = new File(questionAddresses[index]);	
			  BufferedImage bi=Problem.toImage(Problem.getLatexLines(questions[index], "#", Type.QUESTION_WIDTH, latexFontCode));
			  ImageIO.write(bi, "png", file.getAbsoluteFile());
			  
			  imageMargins[index]=bi.getHeight()-40;//when this was zero, it worked for one line problms
			  scaleFactors[index]=Type.scaleFactor+.1;
			  neverIncludeInstructions[index] = true;
			  verticalSpace[index] = verticalSpaceBetweenQuestions;
			  instructionsForEachQuestion[index]="";
		  }
		  addNumberedTableOfImages(table, questionAddresses, imageMargins, scaleFactors, eachQuestionHasItsOwnInstructions, neverIncludeInstructions, instructionsForEachQuestion, verticalSpace,false,verticalSpacing);
		  
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