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

public class SeeAndDo extends MathPage{

	//fields
	protected static String subject = "A2";
	protected static String unit = "Review";
	protected static String lesson = "4";
	protected static String worksheetName = "5.4 Factoring by Grouping";
	protected static String FILE = "Lessons/" + subject +"/" + subject + " " +unit+"/"+unit+"."+lesson+"/resources/"+worksheetName+".pdf";
	protected static boolean includeID= false;
	protected static int numberOfPages = 1;
	
	
	protected static String headingText = "Review: Factoring by grouping";
	protected static String mainInstructions = "Factor each expression using factoring by grouping. A few examples are laid out for you below. " +
			"Refer to the steps in the exmples to review, then try it on your own.";
	protected static boolean eachQuestionHasItsOwnInstructions = true;//this has to remain true in order for the question to have a title (theorem)
	protected static int numberOfColumns=1;
	protected static int numberOfProblems = 10;
	protected static int numberOfExamples = 4;
	protected static boolean problemTypesAreSorted = true;
	protected static int difficulty =2;
	protected static boolean problemsIncreaseInDifficulty = false;
	protected static boolean customDifficulty = false;
	protected static int[] customDifficulties = {1,2,2,};


	protected static boolean overwriteVerticalSpacing = false;
	protected static float verticalSpacing =175;

	protected static String[] problemTypes = {

		//		"Algebra: Linear Equations"
//		"Algebra: Linear Equations (Multiple Choice)",
//		"Algebra: Quadratic Equations",
//		"Algebra: Ratio Sums"
//		"Algebra: Pythagorean Theorem Equations"
//		"Rational Equations: Solve Proportions (Involving Linear Equations)"//includes key
//	  	"Geometry: Identify Theorem and Solve",//NOTE: As of right now, only circle theorems are used in this set
//		"Geometry: Identify Theorem Only",

//		"Transformations: Reflection",
//	  	"Transformations: Rotation",
//	  	"Transformations: Dilation",
//	  	"Transformations: Translation",
//	  	"Transformations: Compositions",
//		
//		"Formulas: Surface Area of a Sphere",
//		"Formulas: Volume of a Cone",
//		"Formulas: Volume of a Pyramid",
//		"Formulas: Volume of a Sphere",
//		"Formulas: Surface Area of a Cylinder",
//		"Formulas: Surface Area of a Cone",
//		"Formulas: Volume of a Cylinder",
		
//		"Circles: Secant Segments",							//check
//		"Circles: Chord Lengths",
//		
//		"Circles: Parallel Chords",								//check
//		"Circles: Tangent Lines",							//check
//		"Circles: Inscribed Angles with a Common Arc",				//check
//		
//		"Circles: Distance Between Center and Chord",				//check
//		"Circles: Pythagorean Theorem with Tangent Lines",			//check
//
//		"Circles: Central Angles",							//check
//		"Circles: Intersecting Chords",
//		"Circles: Inscribed Angles",							//check
//		"Circles: Secant Angles",
//		"Circles: Inscribed Quadrilaterals",				//check
//		"Circles: Angles Formed by a Chord and a Tangent",		//check
		
//		"Graphing: Quadratics given domain",
//		"Graphing: Radicals given domain",
//		"Graphing: Absolute value given domain"
//		"Graphing: Piecewise functions",
//		"Graphing: Determine continuity of piecewise function, including removeable discontinuity"
//		"Graphing: Determine continuity of piecewise function",
//		"Graphing: Determine continuity of piecewise function (algebraically)",
//		"Graphing: Removeable Discontinuity",
		
//		"Graphing: Identify Max and Min"
		
//

		
//		"Polynomials: Factoring polynomials",
//		"Polynomials: Factoring GCF",
//		"Polynomials: Solve any quadratic",
//		"Systems of linear and quadratic equations",
//		"Systems of linear and quadratic equations, (x,y) solution",
//		"Systems of linear and quadratic equations, (x,y) solution, check work",
		
//		"Polynomials: Factoring polynomials",
//		"Polynomials: Factoring by grouping",
//		"Polynomials: Factoring by grouping with GCF",
		"Polynomials: Factoring trinomial by grouping",
		"Polynomials: Factoring trinomial by grouping",
		"Polynomials: Factoring trinomial by grouping and GCF",
//		"Polynomials: Factoring higher degree polynomials",//does not contain explained answer key

//		"Polynomials: Factoring polynomials (with rational coefficients)",//INFERIOR, SHOULD BE DELETED, BUT ERASE ASSOCIATED METHODS IN PROBLEM CLASS
//		"Functions: Identify functions"
//		"Functions: Use inverse variation to solve for unknown values"
//		"Polynomials: Solve any quadratic",
//		"Systems of linear and quadratic equations"
//		"Rational Expressions: Multiply and divide rational expressions",
//		"Rational Expressions: Identifying the LCD"
//		"Rational Expressions: Add and subtract rational expressions",//key is not entirely complete
//		"Rational Expressions: Simplify complex rational expressions",
//		"Rational Expressions: Simplify polynomials with fractional coefficients",
//		"Rational Expressions: Distribute polynomials with fractional coefficients",
//		"Rational Equations: Solve Proportions (Involving Linear Equations)"//includes key
		
//		"Logarithms: Solving Equations (Level 1)"//includes key
		
//		"Coordinate Geometry: Calculate distance",
//		"Coordinate Geometry: Calculate midpoint",
//		"Coordinate Geometry: Determine the relationship between two lines",
//		"Geometry Basics: Use algebra to calculate the measure of an angle",
//		"Quadrilaterals: Use algebra to calculate angle measures in a quadrilateral",
//		"Quadrilaterals: Algebraically solve using Pythagorean Theorem applications",              //no key!
//		"Quadrilaterals: Algebraically solve using Pythagorean Theorem applications Using Quadratic Equations"
		//		"Quadrilaterals: Use algebra to calculate measures of congruent parts in a quadrilateral",
//		
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
// 		"Rational Expressions: Simplify polynomials"
		

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
		  SeeAndDo sAd = new SeeAndDo();
		  sAd.createPdf(FILE);
		  sAd.openFile(FILE);
	  }
	  
	  public void createPdf(String filename) throws DocumentException, IOException{
	      //step 1
		  Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);//can delete this specifications if I want
		  //step 2
	      PdfWriter.getInstance(document, new FileOutputStream(FILE));
	      //step 3

	      document.open();
	      
	      numberOfProblems=numberOfExamples+numberOfProblems;
	      System.out.println("Number of problems is "+numberOfProblems);
	      
	      answerFileNames=new String[(numberOfProblems)*numberOfPages];
	      answers=new BufferedImage[(numberOfProblems)*numberOfPages];
	      answerImageMargins=new int[(numberOfProblems)*numberOfPages];
	      answerScaleFactors=new double[(numberOfProblems)*numberOfPages];
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
		  
		  for (int index=0; index<numberOfProblems/(numberOfExamples+1);index++){
			  System.out.println("See And Do Index = "+index);
			  for(int exampleIndex=0; exampleIndex<numberOfProblems; exampleIndex++){
				  try{
				  questionAddresses[(numberOfExamples+1)*index+exampleIndex]=questionFileNameStem+(index+1)+"."+exampleIndex+".png";
				  int answerNumber = (pageNumber-1)*numberOfProblems+exampleIndex+1;
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
				  File file = new File(questionAddresses[(numberOfExamples+1)*index+exampleIndex]);
				  if(exampleIndex==0){//the image for the first question is taken from the answer to show an example
					  ImageIO.write(p.getAnswerImage(), "png", file.getAbsoluteFile());
					  imageMargins[(numberOfExamples+1)*index+exampleIndex]=p.getAnswerNeedsThisMuchExtraSpaceOnTop();//when this was zero, it worked for one line problms
					  verticalSpace[(numberOfExamples+1)*index+exampleIndex] = 5;
					  answerScaleFactors[(pageNumber-1)*numberOfProblems+exampleIndex]=0;//if the work was shown on the page, it does not appear on answer sheet
				  }
				  else {
					  ImageIO.write(p.getQuestionImage(), "png", file.getAbsoluteFile());
					  imageMargins[(numberOfExamples+1)*index+exampleIndex]=p.getQuestionNeedsThisMuchExtraSpaceOnTop();
					  verticalSpace[(numberOfExamples+1)*index+exampleIndex] = p.getVerticalSpacing();
					  answerScaleFactors[(pageNumber-1)*numberOfProblems+exampleIndex]=p.getScaleFactor();
				  }
				  answerImageMargins[(pageNumber-1)*numberOfProblems+exampleIndex]=p.getAnswerNeedsThisMuchExtraSpaceOnTop();
				  
				  p.makeAnswerImage(""+answerNumber);
				  answerFileNames[answerNumber-1]=AnswerSheet.answerAddress+(answerNumber)+".png";	
				  scaleFactors[(numberOfExamples+1)*index+exampleIndex]=p.getScaleFactor();
				  neverIncludeInstructions[(numberOfExamples+1)*index+exampleIndex] = true;
				  instructionsForEachQuestion[(numberOfExamples+1)*index+exampleIndex]=p.getKeyTheorem();
			  }catch(ArrayIndexOutOfBoundsException e){
				  
			  }
			  }
		  }
		  for(String s:answerFileNames){
			  System.out.println("SeeAndDo answer file = "+s);
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
	  
	  private static void addEmptyLine(Paragraph paragraph, int number) {
	    for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
	  }
	}