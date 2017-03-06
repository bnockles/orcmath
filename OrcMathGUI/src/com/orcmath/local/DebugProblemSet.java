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
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.SimplestRadicalForm;
import com.orcmath.objects.Term;

public class DebugProblemSet extends MathPage{

	//fields
	protected static String subject = "GEO";
	protected static String unit = "4";
	protected static String lesson = "2";
	protected String folder = "Lessons/" + subject +"/" + subject + " " +unit+"/"+unit+"."+lesson+"/resources/";
	protected String FILE = folder+worksheetName+".pdf";
	protected static boolean includeID= false;



	protected String headingText = "More Rotation Practice";

	protected String mainInstructions = "";
	protected static boolean eachQuestionHasItsOwnInstructions = true;
	protected static int numberOfColumns=1;
	protected int customNumberOfProblems = 2;
	protected static boolean problemTypesAreSorted = true;
	protected static int difficulty =4;
	protected static boolean problemsIncreaseInDifficulty = false;
	protected static boolean customDifficulty = false;
	protected static int[] customDifficulties = {3,3,4,3,4};


	protected boolean overwriteVerticalSpacing = false;
	protected float verticalSpacing =140;//usually 140

	protected  String[] problemTypes;
	
	protected static final String[] allTypes = {
			"Trig: Law of Cosines (Sides)",
			"Trig: Law of Cosines (Angles)"
//			"Trig: Law of Sines",
//			"Trig: Right Triangle Angles",
//			"Trig: Right Triangle Sides"
//			"Similarity: Triangle Angle Bisector Theorem"
//			"Similarity: Side Splitter Theorem with Quadratics"
//			"Similarity: Side Splitter Theorem"
//							"Algebra: Linear Equations",
//					"Algebra: Linear Equations (Multiple Choice)",
//					"Algebra: Quadratic Equations",
//					"Algebra: Ratio Sums",
//					"Algebra: Pythagorean Theorem Equations",
//					"Rational Equations: Solve Proportions (Involving Linear Equations)",//includes key
//////	NOT WORKING			  	"Geometry: Identify Theorem and Solve",//NOTE: As of right now, only circle theorems are used in this set
////		NOT WORKING FOR ALL QUESTION TYPES			"Geometry: Identify Theorem Only",
//
//					"Transformations: Reflection",
//			"Transformations: Rotation",
//				  	"Transformations: Dilation",
//				  	"Transformations: Translation",
//				  	"Transformations: Compositions",
////			//		
//					"Formulas: Surface Area of a Sphere",
//					"Formulas: Volume of a Cone",
//					"Formulas: Volume of a Pyramid",
////		ENDLESS LOOP			"Formulas: Volume of a Sphere",
//					"Formulas: Surface Area of a Cylinder",
//					"Formulas: Surface Area of a Cone",
//					"Formulas: Volume of a Cylinder",
////
//					"Circles: Secant Segments",							//check
////		NOT WORKING			"Circles: Chord Lengths",
//			//		
//					"Circles: Parallel Chords",								//check
//					"Circles: Tangent Lines",							//check
//					"Circles: Inscribed Angles with a Common Arc",				//check
//			//		
//					"Circles: Distance Between Center and Chord",				//check
//					"Circles: Pythagorean Theorem with Tangent Lines",			//check
//			//
//					"Circles: Central Angles",							//check
//					"Circles: Inscribed Quadrilaterals",				//check
////					"Circles: Intersecting Chords",
//					"Circles: Inscribed Angles",//check
//					"Circles: Secant Angles",
//					"Circles: Angles Formed by a Chord and a Tangent",		//check
//
//					"Graphing: Quadratics given domain",
//					"Graphing: Radicals given domain",
//					"Graphing: Absolute value given domain",
////		NOT WORKING			"Graphing: Piecewise functions",
////					"Graphing: Determine continuity of piecewise function, including removeable discontinuity",
//					"Graphing: Determine continuity of piecewise function",
//					"Graphing: Determine continuity of piecewise function (algebraically)",
//					"Graphing: Removeable Discontinuity",
//
//					"Graphing: Identify Max and Min",
//
//			//
//					"Systems of linear and quadratic equations",
//					"Systems of linear and quadratic equations, (x,y) solution",
//					"Systems of linear and quadratic equations, (x,y) solution, check work",
//
//
////
//					"Polynomials: Factoring polynomials",
//					"Polynomials: Factoring by grouping",
//					"Polynomials: Factoring GCF",
//					"Polynomials: Factoring by grouping with GCF",
//					"Polynomials: Factoring trinomial by grouping",
//					"Polynomials: Factoring trinomial by grouping",
//					"Polynomials: Factoring trinomial by grouping and GCF",
//					"Polynomials: Factoring higher degree polynomials",//does not contain explained answer key
//					"Polynomials: Solve any quadratic",
//
//					"Polynomials: Factoring polynomials (with rational coefficients)",//INFERIOR, SHOULD BE DELETED, BUT ERASE ASSOCIATED METHODS IN PROBLEM CLASS
//					"Functions: Identify functions",
//					"Functions: Use inverse variation to solve for unknown values",
//					"Systems of linear and quadratic equations",
//					"Systems of linear and quadratic equations by graphing",
//					"Rational Expressions: Multiply and divide rational expressions",
//					"Rational Expressions: Identifying the LCD",
//					"Rational Expressions: Add and subtract rational expressions",//key is not entirely complete
//					"Rational Expressions: Simplify complex rational expressions",
//					"Rational Expressions: Simplify polynomials with fractional coefficients",
//					"Rational Expressions: Distribute polynomials with fractional coefficients",
//					"Rational Equations: Solve Proportions (Involving Linear Equations)",//includes key
//
//					"Logarithms: Solving Equations (Level 1)",//includes key
//
//					"Coordinate Geometry: Calculate distance",
//					"Coordinate Geometry: Calculate midpoint",
//					"Coordinate Geometry: Determine the relationship between two lines",
//					"Geometry Basics: Use algebra to calculate the measure of an angle",
//					"Quadrilaterals: Use algebra to calculate angle measures in a quadrilateral",
//					"Quadrilaterals: Algebraically solve using Pythagorean Theorem applications",              //no key!
//					"Quadrilaterals: Algebraically solve using Pythagorean Theorem applications Using Quadratic Equations",
//					"Quadrilaterals: Use algebra to calculate measures of congruent parts in a quadrilateral",
//			//		
//					"Coordinate Geometry: Write the equation of a line that is perp. or par. to a given line",
//					"Geometric relationships",
//					"Geometric Constructions: Justifications",
//					"Geometric Constructions: Justifications",
//					"Geometric Constructions",
//					"Locus: Sketch locus in a coordinate plane",
//					"Locus: Identify the number of points",
//
//					"Radical Expressions: Simplify radicals",
//					"Radical Expressions: Add radicals",
//					"Radical Expressions: Rationalize denominators",
//			//		
//					"Complex Numbers: Rationalize Imaginary Denominators",
//					"Complex Numbers: Multiply Complex Numbers",
//			"Complex Numbers: Simplify powers of i",
//		NOT WORKING	 		"Rational Expressions: Simplify polynomials"


	};

	//______________________________________________________________________________
	private static String instructions;
	//private AnswerSheet answersPage; 

	int[] answerImageMargins;
	double[] answerScaleFactors;
	private static int identifier;//TODO once applet is made, this is no longer static




	//no constructor (therefore, all fields must be static :)

	//methods  
	public static void main(String[] args) throws DocumentException, IOException {
		//make the directory for the worksheet

		//		Term term1 = new Term(3,"x^-1");
		//		System.out.println("negative exponent: "+term1+"\n");
		//		  
		//		Term term = new Term(new Fraction(new Term(3,"x^9"),new Term(4,"x^4")));  
		////		System.out.println("simplified term: "+term+"\n");
		//		SimplestRadicalForm srf = new SimplestRadicalForm(2, term);
		//		System.out.println(srf.representationBeforeSimplification+ ", with radicand, "+term+" becomes "+srf);
		//		  
		//		
		//		Term term2 = new Term(new Fraction(new Term(6,"x^2"),new Term(2,"x")));  
		//		SimplestRadicalForm srf2 = new SimplestRadicalForm(2, term2);
		//		System.out.println(srf2.representationBeforeSimplification+ ", with radicand, "+term2+" becomes "+srf2);

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
		//		  verticalSpacing = problem.getVerticalSpacing();	    
		DebugProblemSet cps = new DebugProblemSet();
		instructions = cps.getInstructions();
		cps.setProblemTypes(allTypes);
		cps.createPdf();
		cps.openFile(cps.getFile());
	}


	
	private String getInstructions() {
		return mainInstructions;
	}

	public void openFile(){
		super.openFile(getFile());
	}
	
	public DebugProblemSet(){
		problemTypes = new String[0];
		numberOfProblems=0;
		numberOfPages=1;
	}

	public void setProblemTypes(String[] types){
		problemTypes = types;
		numberOfProblems = types.length;
	}

	public void setNumberOfPages(int n){
		numberOfPages = n;
	}
	
	public  void setWorksheetName(String text) {
		FILE =getFolder() +"/"+ text+".pdf";
	}

	private String getFile() {
		return FILE;
	}

	public String getFolder(){
		return folder;
	}
	
	public void setFolder(String folder){
		this.folder = folder;
	}
	
	public void createPdf() throws DocumentException, IOException{
		//step 1
		document = new Document(PageSize.LETTER, 50, 50, 50, 50);//can delete this specifications if I want
		//step 2
		System.out.println("ClassworkProblemSet.java saving to folder: "+getFolder());
		if ((new File(getFolder())).mkdirs()){
			System.out.println("Created folder "+getFolder());
		}
		writer =PdfWriter.getInstance(document, new FileOutputStream(getFile()));
		//step 3

		document.open();

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


	private void addInstructions(Document document) throws DocumentException{
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
		String questionFileNameStem = "PDFs/Graphics/question/";
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
//			questionAddresses[index]=questionFileNameStem+((pageNumber-1)*numberOfProblems+index+1)+".png";
			String fileSafe = problemOrder[index].replace(":", "_");
			questionAddresses[index]=questionFileNameStem+fileSafe+".png";
			int answerNumber = (pageNumber-1)*numberOfProblems+index+1;

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
			//TODO: This is saving images to a PDF folder that is static. Make this dynamic
//			System.out.println("Creating problem "+problemOrder[index]);
			ImageIO.write(p.getQuestionImage(), "png", file.getAbsoluteFile());
			System.out.println("Saved "+file.getAbsolutePath());
			imageMargins[index]=p.getQuestionNeedsThisMuchExtraSpaceOnTop();//when this was zero, it worked for one line problms
			scaleFactors[index]=p.getScaleFactor();
			neverIncludeInstructions[index] = p.getWhetherInstructionsAreNeverIncluded();
			verticalSpace[index] = p.getVerticalSpacing();
			answerImageMargins[(pageNumber-1)*numberOfProblems+index]=p.getAnswerNeedsThisMuchExtraSpaceOnTop();
			answerScaleFactors[(pageNumber-1)*numberOfProblems+index]=p.getScaleFactor();
			p.makeAnswerImage("PDFs/Graphics/answer"+answerNumber+".png");
				  
			instructionsForEachQuestion[index]=p.getInstructions();
		}
		addNumberedTableOfImages(table, questionAddresses, imageMargins, scaleFactors, eachQuestionHasItsOwnInstructions, neverIncludeInstructions, instructionsForEachQuestion, verticalSpace,overwriteVerticalSpacing,verticalSpacing);
		float[] columnWidths;
		if (numberOfColumns == 1) columnWidths = new float[] {1f, 20f};
		else columnWidths = new float[] {1f, 10f,1f, 10f};//TODO write a method to determine these numbers
		table.setWidths(columnWidths);
		document.add(table);
	}


	public boolean isOverwriteVerticalSpacing(){
		return overwriteVerticalSpacing;
	}
	
	public void setOverwriteVerticalSpacing(boolean b){
		overwriteVerticalSpacing = b;
	}
	
	


	public float getVerticalSpacing() {
		return verticalSpacing;
	}



	public void setVerticalSpacing(float verticalSpacing) {
		this.verticalSpacing = verticalSpacing;
	}



	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public void setHeading(String text) {
		headingText = text;
	}
	public void setInstructions(String text) {
		mainInstructions = text;
	}
}