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
import java.io.IOException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.net.MalformedURLException;
import java.awt.Desktop;

import javax.imageio.ImageIO;



public class MathPage {

	//fields
	protected static String subject = "GEO";
	protected static String unit = "7";
	protected static String lesson = "0";
	protected static String worksheetName = "Congruence Within Circles";
//	protected static String FILE = "Lessons/" + subject +"/" + subject + " " +unit+"/"+unit+"."+lesson+"/resources/"+worksheetName+".pdf";
	//use this for a test worksheet
	protected static String FILE = "PDFs/Worksheet.pdf";

	protected static String[] problemTypes = {
//		"Geometry: Identify Theorem",

//		"Transformations: Translation",
//	  	"Transformations: Reflection",
//	  	"Transformations: Rotation",
//	  	"Transformations: Dilation",
//	  	"Transformations: Compositions",
		
		//		"Circles: Secant Segments",
		"Circles: Central Angles",
//		"Circles: Parallel Chords",
//		"Circles: Inscribed Angles",
//		"Circles: Intersecting Chords",
//		"Circles: Inscribed Quadrilaterals",
//		"Circles: Tangent Lines",
//		"Circles: Inscribed Angles with a Common Arc",
		
//		"Graphing: Removeable Discontinuity",
		//		"Polynomials: Factoring polynomials",
		//"Polynomials: Factoring polynomials (with rational coefficients)",
		//"Polynomials: Factoring higher degree polynomials",
		//"Functions: Identify functions"
		//"Functions: Use inverse variation to solve for unknown values"
		//"Polynomials: Solve any quadratic",
		//"Rational Expressions: Multiply and divide rational expressions",
		//"Rational Expressions: Add and subtract rational expressions",
		//"Rational Expressions: Simplify complex rational expressions",
		//"Rational Expressions: Simplify polynomials with fractional coefficients",
		//"Rational Expressions: Distribute polynomials with fractional coefficients",
		
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
		

		}; //until the applet is made, change this field to change the type of worksheet
	protected String teacherName = "Mr. Nockles";
	protected static boolean includeID= true;
	protected boolean eachQuestionHasItsOwnInstructions = false;
	protected static String headingText = "GE 7 Quiz";
	protected static String mainInstructions = "Answer each question completely. YOU MUST INCLUDE A JUSTIFICATION FOR YOUR REASONING";
	public static int latexWidth = 700; 
	protected int numberOfProblems = 10;
	protected static boolean problemsIncreaseInDifficulty = true;
	protected static boolean problemTypesAreSorted = true;
	protected int numberOfPages;

	protected static int difficulty =2;
	protected static int numberOfColumns=1;

//	protected static boolean overwriteVerticalSpacing = false;
//	protected static float verticalSpacing =45;
	//protected static double scaleFactor=.40;//TODO must write a way to determine scaleFactor universally

	protected static Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	protected static Font titleBold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	protected static Font paragraphFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	protected static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	protected static Font tiny = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);
	protected static String latexFontCode = "text";
	protected static boolean customDifficulty = false;
	protected static int[] customDifficulties={};
	protected Document document;
	protected PdfWriter writer;
	
	public static void setLatexFontCode(String s){
		latexFontCode=s;
	}
	
	float scaleFactorPercent;//the exact same scalefactor setting is used again for making instructions
	//returns high resolution LateX image
	private Image prepareLatexImage(String filename, double scaleFactor){
		Image image=null;
		try {
			image = Image.getInstance(filename);
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scaleFactorPercent=(float)scaleFactor*100;
		if(image.getHeight()*scaleFactor>630){
			scaleFactorPercent=(float)63000/(float)image.getHeight();
			scaleFactor=scaleFactorPercent/100;
		}
		image.scalePercent(scaleFactorPercent);
		return image;
	}
	
	private Image prepareInstructions(PdfPCell cell, String instruction, int cellWidthLimit, double scaleFactor){
		Image instructionImage = null;
		File file = new File("PDFs/Graphics/instruction.png");
		try {
			ImageIO.write(Problem.toImage(Problem.getLatexLines(instruction, "#", cellWidthLimit*2, latexFontCode)), "png", file.getAbsoluteFile());
			instructionImage = Image.getInstance("PDFs/Graphics/instruction.png");	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		instructionImage.scalePercent(scaleFactorPercent);
		int scaledInstructionsImageHeight = (int)(instructionImage.getWidth()*scaleFactor);
		
//		cell.setPaddingLeft(((scaledInstructionsImageWidth)/-2));
		cell.addElement(new Chunk(instructionImage,0,0));

//		cell.setPadding(0);
//		cell.setPaddingTop(table.getDefaultCell().getPaddingTop());
		
		return instructionImage;
	}
	
	protected void addNumberedTableOfImages(PdfPTable table, String[] filenames, int[] topMargins, double[] scaleFactors, boolean instructionsForEachQuestion, boolean[] neverInstructions, String[] instructions, float[] verticalSpace, boolean overwriteVerticalSpacing, float verticalSpacing){
		//all specifications for the table should be declared before this method is called
		eachQuestionHasItsOwnInstructions = instructionsForEachQuestion;
		
		//numberOfRemainingColumnsInRow is declared outside the for loop. Embedded it constantly counts down to zero and resets.
		int numberOfRemainingColumnsInRow=2*numberOfColumns;
		//TODO 550 is currently used as an approximation of the tableWidth. Figure this out another way
		int cellWidthLimit = (530-(15*numberOfColumns))/(numberOfColumns);
		for (int index=0; index<filenames.length;index++){
			int itemNumber=index+1;
			Image image = prepareLatexImage(filenames[index],scaleFactors[index]);
			//sets all of cell preferences to the defaults
			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			if (!overwriteVerticalSpacing){
				verticalSpacing = verticalSpace[index];
			}
			cell.setPaddingBottom(verticalSpacing);

			//makes image of instructions and places question according to where it must go.
			int scaledInstructionsImageWidth=0;
			if (instructionsForEachQuestion && !neverInstructions[index]) {
				Image instructionsImage = prepareInstructions(cell, instructions[index], cellWidthLimit, scaleFactors[index]);
				int scaledInstructionsImageHeight = (int)(instructionsImage.getHeight()*scaleFactors[index]);
				scaledInstructionsImageWidth = (int)(instructionsImage.getWidth()*scaleFactors[index]);
				cell.addElement(new Chunk(image,0,-scaledInstructionsImageHeight));		
			}else{
				cell.addElement(new Chunk(image,0,0));				
			}
			
			
			//this is carried out when a row is finished. resets the number of columns remaining
			if(numberOfRemainingColumnsInRow==0) numberOfRemainingColumnsInRow=2*numberOfColumns;

			int scaledImageWidth=(int)image.getScaledWidth();
			if(scaledInstructionsImageWidth>scaledImageWidth)scaledImageWidth=scaledInstructionsImageWidth;
			//I have found there is a margin at the top of each graphic that is inversely proportional to what seems to be approximately te square of the 
			//scale factor. The next two lines are an attempt to remedy it.
			float littleRemainingSpace=(float) (1/Math.pow((scaleFactors[index]),1.7));
			float topPadding = (float)((topMargins[index]-littleRemainingSpace)*scaleFactors[index]);
//			System.out.println("\n\nQuestion "+(index+1)+"\ninfo about padding:\ninstructions height = "+
//			scaledInstructionsImageHeight+"\nimage height = "+
//					scaledImageHeight+"\nvertical space = "+(int)verticalSpace[index]+
//					"\ntop margin = "+topMargins[index]+"\ntop padding = "+topPadding);
			cell.setPaddingTop(topPadding);
					
			if (scaledImageWidth>=cellWidthLimit){
				if (numberOfRemainingColumnsInRow>=4){
					//if the current cell is extra long, add cell that expands over many columns
					PdfPCell numberCell=addNumbering(itemNumber, table);
					table.addCell(numberCell);//-1 column
					cell.setColspan(3);
					table.addCell(cell);
					numberOfRemainingColumnsInRow=numberOfRemainingColumnsInRow-4;
					
				}
				else{
					//if there is no room, add cells until a new row is made
					for(int countBlanks=0;countBlanks<numberOfRemainingColumnsInRow;countBlanks++){
						table.addCell("");
					}
					PdfPCell numberCell=addNumbering(itemNumber, table);
					table.addCell(numberCell);//-1 column
					cell.setColspan(3);
					table.addCell(cell);
					numberOfRemainingColumnsInRow=numberOfRemainingColumnsInRow-4;
				}
			}
			else{//when the image is not too wide for the column
				PdfPCell numberCell=addNumbering(itemNumber, table);
				table.addCell(numberCell);//-1 column
				table.addCell(cell);//cell without instructions is added only if instruction cell isn't
				numberOfRemainingColumnsInRow=numberOfRemainingColumnsInRow-2;
			}
		}
		table.completeRow();
	}

    public Image cropImage(Image image, float cutFromLeft, float cutFromTop, float width, float height) throws DocumentException {
    	PdfContentByte cb = writer.getDirectContent();
        PdfTemplate t = cb.createTemplate(width, height);
        float origWidth = image.getScaledWidth();
        float origHeight = image.getScaledHeight();
        t.addImage(image, origWidth, 0, 0, origHeight, -cutFromLeft, -cutFromTop);
        return Image.getInstance(t);
    }
    
	
//	protected void addAnswerSheet(Document document, BufferedImage[] answers,int[] answerImageMargins, double[] answerScaleFactors, int identifier, int numberOfPages) throws DocumentException, IOException{
	protected void addAnswerSheet(Document document, int[] answerImageMargins, double[] answerScaleFactors, int identifier) throws DocumentException, IOException{
	//		AnswerSheet answersPage = new AnswerSheet(identifier, answers,answerImageMargins,answerScaleFactors, numberOfColumns,answers.length, numberOfPages);
		
		int numberOfProbs=answerImageMargins.length;
		
		AnswerSheet answersPage = new AnswerSheet(identifier, answerImageMargins,answerScaleFactors, numberOfColumns,numberOfProbs, numberOfPages);
		for (int page = 1; page <= numberOfPages; page++){
			  String[] filenames=new String[numberOfProbs];
			  for(int index=0; index<numberOfProbs; index++){
				  filenames[index]=AnswerSheet.answerAddress+((page-1)*numberOfProbs+(index+1)+".png");
			  }
			  PdfPTable table = answersPage.getAnswersTable(page, filenames);
			  document.add(table);
		  }
	  }
	
	protected static void addTableHeader(PdfPTable table, String text, Font font){
		PdfPCell header = new PdfPCell(new Phrase(text,font));
	    header.setPadding(5);
	    header.setColspan(2*numberOfColumns);
	    header.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(header);
	}
	
	protected static PdfPCell addNumbering(int number, PdfPTable table){
		PdfPCell cell;	
		cell=new PdfPCell(new Phrase(number + ")",paragraphFont));
		cell.setBorder(table.getDefaultCell().getBorder());
		cell.setPaddingTop(table.getDefaultCell().getPaddingTop());
		//System.out.println("The numbering padding is: " + cell.getPaddingTop());
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		return cell;
	}	
	
	
	
	  public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
	public void setEachQuestionHasItsOwnInstructions(boolean b){
		eachQuestionHasItsOwnInstructions = b;
	}

	public void setNumberOfPages(int n){
		numberOfPages = n;
	}
	
	public void setNumberOfColumns(int n){
		numberOfColumns = n;
	}
	
	public void openFile(String file) {
			try{
				File pdfFile = new File(file);
				if (pdfFile.exists()){
					if (Desktop.isDesktopSupported()){
						Desktop.getDesktop().open(pdfFile);
					}
					else{
						System.out.println("Awt Desktop is not supported!");
					}
				}else {
					System.out.println("File does not exist!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}
