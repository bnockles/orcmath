/*******************************************************************************
 * Copyright (c) 2017 Benjamin Nockles
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
package data;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.scilab.forge.jlatexmath.ParseException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.orcmath.local.Problem;

import guiTeacher.interfaces.Task;
import main.OrcMath;
import screens.Settings;

public class Worksheet implements Task{

	//	private 
	//fields

	private String worksheetName = "Congruence Within Circles";

	//set before PDF generation begins
	private String[] problemTypes;
	private String folder;
	private String teacherName;
	private boolean includeID;
	private boolean eachQuestionHasItsOwnInstructions;
	private String headingText ;
	private boolean includeHeading;
	private String mainInstructions;
	private boolean includeInstructions;
	private boolean overrideVerticalSpacing;
	private float verticalSpacingStatic;
	private int numberOfProblems;
	private int numberOfPages;
	private int numberOfColumns;
	private int difficulty;
	private int[] customDifficulties;
	private int difficultyMode;
	private ArrayList<String> headerLines;
	private boolean includeTeacherNameInHeader;
	private int identifier;//used to match sheets with answer keys
	private  boolean keepQuestionOrder;
	private ArrayList<CustomProblemData> customProblems;
	private int customIndex;
	
	//task related
	private boolean finished;

	//setup during PDF construction
	private String latexFontCode = "text";
	private QuestionData[] answers;//cumulative of all answers across multiple sheets
	private int count;//counts number of problems generated 
	private String currentTask;
	private float scaleFactorPercent;//the exact same scalefactor setting is used again for making instructions


	protected  boolean problemsIncreaseInDifficulty = true;

	//values required for making PDFs
	protected Document document;
	protected PdfWriter writer;

	//constants
	public static final String SUBFOLDER_WORKSHEETS = "worksheets";
	public static final String SUBFOLDER_IMAGES = "images";
	public static final int LATEX_WIDTH = 700; 
	protected static final Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	protected static final Font titleBold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	protected static final Font paragraphFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	protected static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	protected static final Font tiny = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.NORMAL);


	//		protected static boolean overwriteVerticalSpacing = false;
	//		protected static float verticalSpacing =45;
	//protected static double scaleFactor=.40;//TODO must write a way to determine scaleFactor universally


	public Worksheet(){
		currentTask = "Creating PDF...";
		worksheetName = "Woksheet";
		teacherName="Mr. Nockles";
		folder="PDFs/Files";
		includeID= false;
		eachQuestionHasItsOwnInstructions = false;
		headingText = "Practice";
		includeInstructions = false;
		overrideVerticalSpacing = false;
		verticalSpacingStatic=90;
		mainInstructions="Show all your work.";
		numberOfProblems = 10;
		numberOfPages=1;
		numberOfColumns=1;
		difficulty =2;
		difficultyMode = Settings.DIFFICULT_MODE_CUSTOM;
		customDifficulties = new int[numberOfProblems];
		for(int i = 0; i< numberOfProblems; i++){
			customDifficulties[i]=difficulty;
		}
		latexFontCode = "text";
		count = 0;
		headerLines = new ArrayList<String>();
		headerLines.add("NAME");
		headerLines.add("PERIOD");
		includeTeacherNameInHeader = true;
		keepQuestionOrder = true;

	}

	public void createPdf() throws DocumentException, IOException{
		//step 1
		document = new Document(PageSize.LETTER, 50, 50, 50, 50);//can delete this specifications if I want
		//step 2
		System.out.println("Worksheet.java saving to folder: "+getFolder());
		if ((new File(getWorksheetFolder())).mkdirs()){
			System.out.println("Created folder "+getWorksheetFolder());
		}
		writer =PdfWriter.getInstance(document, new FileOutputStream(getFilename()));
		//step 3

		document.open();

		answers = new QuestionData[numberOfProblems*numberOfPages];
		for(int i = 0; i < answers.length; i++){
			answers[i]= new QuestionData();//every answer starts with no data. Data is added as the questions get created
		}

		int page=1;

		for (int index = 0; index<numberOfPages; index++){
			addHeader(document, page);
			addInstructions(document);
			currentTask = "Creating questions and answers for page "+page;
			addContent(document, page);
			document.newPage();
			page++;
		}

		addAnswerSheet(document, answers, identifier);
		document.close();
	}



	//returns high resolution LateX image
	private Image prepareLatexImage(String filename, double scaleFactor){
		Image image=null;
		try {
			image = Image.getInstance(filename);
			scaleFactorPercent=(float)scaleFactor*100;
			System.out.println("1. scaling "+scaleFactor+", scaleFactorPercent = "+scaleFactorPercent);
			if(image.getHeight()*scaleFactor>650){
				scaleFactorPercent=(float)65000/(float)image.getHeight();
				//				scaleFactor=scaleFactorPercent/100;

			}
//			System.out.println("2. image height = "+image.getScaledHeight()+", scaling "+scaleFactor+", scaleFactorPercent = "+scaleFactorPercent);
			image.scalePercent(scaleFactorPercent);
//			System.out.println("3. image height = "+image.getScaledHeight()+", scaling "+scaleFactor+", scaleFactorPercent = "+scaleFactorPercent);
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e) {

			e.printStackTrace();
		}
		return image;
	}

	private Image prepareAndAddInstructions(PdfPCell cell, String instruction, int cellWidthLimit, double scaleFactor, int heightOfProblem){
		Image instructionImage = null;
		File file = new File(getFolder()+"/"+SUBFOLDER_IMAGES+"/instruction.png");
		try {
			ImageIO.write(Problem.toImage(Problem.getLatexLines(instruction, "#", cellWidthLimit*2, latexFontCode)), "png", file.getAbsoluteFile());
			instructionImage = Image.getInstance(file.getAbsolutePath());	
		} catch (Exception e) {
			e.printStackTrace();
			if(OrcMath.createScreen != null){
				OrcMath.createScreen.presentNotification("An error ocurred while attempting to render the instructions on your worksheet.");
			}
		}
		instructionImage.scalePercent(scaleFactorPercent);
		//			int scaledInstructionsImageHeight = (int)(instructionImage.getWidth()*scaleFactor);

		//			cell.setPaddingLeft(((scaledInstructionsImageWidth)/-2));
		cell.addElement(new Chunk(instructionImage,0,heightOfProblem-instructionImage.getScaledHeight()));//-100 used to be 0

		//			cell.setPadding(0);
		//			cell.setPaddingTop(table.getDefaultCell().getPaddingTop());

		return instructionImage;
	}

	public Image cropImage(Image image, float cutFromLeft, float cutFromTop, float width, float height) throws DocumentException {
		PdfContentByte cb = writer.getDirectContent();
		PdfTemplate t = cb.createTemplate(width, height);
		float origWidth = image.getScaledWidth();
		float origHeight = image.getScaledHeight();
		t.addImage(image, origWidth, 0, 0, origHeight, -cutFromLeft, -cutFromTop);
		return Image.getInstance(t);
	}


	//		protected void addAnswerSheet(Document document, BufferedImage[] answers,int[] answerImageMargins, double[] answerScaleFactors, int identifier, int numberOfPages) throws DocumentException, IOException{
	protected void addAnswerSheet(Document document, QuestionData[] answers, int identifier) throws DocumentException, IOException{



		AnswerSheet answersPage = new AnswerSheet(identifier, answers, numberOfProblems, numberOfPages);
		for (int page = 1; page <= numberOfPages; page++){
			PdfPTable table = answersPage.getAnswersTable(page, numberOfProblems*(page-1),numberOfProblems*page);
			document.add(table);
		}
	}


	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}


	private  void addHeader(Document document, int pageNumber) throws DocumentException {
		Paragraph preface = new Paragraph();
		// Lets write a big header
		for(String s: headerLines){
			preface.add(new Paragraph(s,headerFont));
		}
		if(includeTeacherNameInHeader)preface.add(new Paragraph(teacherName, headerFont));
		if (includeID) preface.add(new Paragraph(""+(identifier+pageNumber), smallBold));

		addEmptyLine(preface, 1);//1 blank line

		document.add(preface);
	}


	private void addInstructions(Document document) throws DocumentException{
		Paragraph text = new Paragraph();
		if(includeHeading){
			text.setAlignment(Element.ALIGN_CENTER);
			text.add(new Paragraph(headingText, titleBold));

		}
		if(includeInstructions){
			text.setAlignment(Element.ALIGN_LEFT);
			text.add(new Paragraph(getInstructions(),paragraphFont));
			addEmptyLine(text, 1);
		}
		document.add(text);
	}

	//This method adds content ON EACH page, use "numberOfColumns", not "numberOfColumns*numberOfPages"
	private void addContent(Document document, int pageNumber) throws DocumentException, IOException{
		//there are always twice he number of columns because an extra column is where the number goes.
		PdfPTable table = new PdfPTable(2*numberOfColumns);
		table.setWidthPercentage(100);
		table.getDefaultCell().setBorder(0);
		//table.getDefaultCell().setPaddingBottom(verticalSpacing);

		//very important! This piece is what determines the order of the problem types
		String[] problemOrder = new String[numberOfProblems];
		//populates the array with types
		for (int index = 0; index<problemOrder.length; index++) {
			problemOrder[index] = problemTypes[index%(problemTypes.length)];
		}
		//shuffle if needed
		if (!keepQuestionOrder){
			for(int i=0; i < problemOrder.length; i++){
				int swap = (int) (Math.random()*problemOrder.length);
				String temp = problemOrder[i];
				int tempD = customDifficulties[i];
				customDifficulties[i] = customDifficulties[swap];
				problemOrder[i] = problemOrder[swap];
				customDifficulties[swap] = tempD;
				problemOrder[swap]= temp;
			}
		}

		QuestionData[] questions = createProblems(problemOrder, pageNumber);

		addNumberedTableOfImages(table,questions,numberOfColumns);
		float[] columnWidths;
		if (numberOfColumns == 1) columnWidths = new float[] {1f, 20f};
		else if(numberOfColumns ==2){
			columnWidths = new float[] {1f, 10f,1f, 10f};//TODO write a method to determine these numbers

		}else{
			columnWidths = new float[] {1f, 10f,1f, 10f,1f,10f};
		}
		table.setWidths(columnWidths);
		document.add(table);
	}

	private QuestionData[] createProblems(String[] problemOrder, int pageNumber){
		//values needed for automatically increasing question difficulty
		int difficultyStep=1;
		double d=((double)difficulty)/((double)numberOfProblems);
		double stepInterval=d;
		//this section must create all of the questions and the BufferedImage[] of answers
		QuestionData[] questions = new QuestionData[numberOfProblems];
		String questionFileNameStem = getFolder()+"/"+SUBFOLDER_IMAGES+"/question";;
		for (int index=0; index<numberOfProblems;index++){
			//index of question out of total
			int j = (pageNumber-1)*numberOfProblems+index;
			QuestionData q = new QuestionData(questionFileNameStem+(j+1)+".png");

			try{
				Problem p;
				System.out.println("Creating a problem: "+(problemOrder[index]));
				//custom problem
				if (problemOrder[index].contains(Problem.CUSTOM_TAG)){
					CustomProblemData data = customProblems.get(++customIndex%customProblems.size());
					p = new Problem(data.getProblemLaTeX(),data.getSolutionLaTeX());
				}
				//generated problem
				else{
					if(difficultyMode == Settings.DIFFICULT_MODE_CUSTOM){
						p = new Problem(problemOrder[index],customDifficulties[index%customDifficulties.length]);
					}else{
						if (difficultyMode == Settings.DIFFICULT_MODE_INCREMENTAL){
							p = new Problem(problemOrder[index],difficultyStep);
							difficultyStep=1;
							difficultyStep=(int)(difficultyStep+stepInterval);
							stepInterval=stepInterval+d;
						}
						else{
							//static difficulty

							p = new Problem(problemOrder[index],difficulty);	  


						}

					}
				}
				File file = new File(q.getAddress());
				//TODO: This is saving images to a PDF folder that is static. Make this dynamic
				//				System.out.println("Creating problem "+problemOrder[index]);
				try {
					if ((new File(getImagesFolder())).mkdirs()){
						System.out.println("Created folder "+getImagesFolder());
					}
					ImageIO.write(p.getQuestionImage(), "png", file.getAbsoluteFile());
				} catch (Exception e) {
					e.printStackTrace();
					if(OrcMath.createScreen != null){
						OrcMath.createScreen.presentNotification("Incountered an error while attempting to save the file "+file.getAbsolutePath());
					}
				}
				q.setImageMargin(p.getQuestionNeedsThisMuchExtraSpaceOnTop());//when this was zero, it worked for one line problms
				q.setScaleFactors(p.getScaleFactor());
				q.setNeverIncludeInstructions(p.getWhetherInstructionsAreNeverIncluded());
				q.setVerticalSpace(p.getVerticalSpacing());
				questions[index]=q;
				count++;

				//setup answers
				answers[j].setQuestionAddresses(getFolder()+"/"+SUBFOLDER_IMAGES+"/answer"+(j+1)+".png");
				answers[j].setImageMargin(p.getAnswerNeedsThisMuchExtraSpaceOnTop());
				answers[j].setScaleFactors(p.getScaleFactor());
				p.makeAnswerImage(answers[j].getQuestionAddresses());
				count++;
				q.setInstructions(p.getInstructions());
			}catch(ParseException pe){
				showProblemSpecificError(problemOrder, index);
			}catch(NullPointerException e){
				showProblemSpecificError(problemOrder, index);
			}
		}
		return questions;
	}


	protected void showProblemSpecificError(String[] problemOrder, int index){
		if(OrcMath.createScreen != null){
			int maxChar = 20;
			String description = problemOrder[index];
			if(maxChar < description.length()){
				description = description.substring(0, maxChar)+"...";
			}
			OrcMath.createScreen.presentNotification("There was a bug in the \""+description+"\" module, (diff="+difficulty+"). Try again or use a different module. Question omitted.");
		}
	}

	//this method is also used by Answer sheet
	protected void addNumberedTableOfImages(PdfPTable table, QuestionData[] data, int columns){


		//numberOfRemainingColumnsInRow is declared outside the for loop. Embedded, it constantly counts down to zero and resets.
		int numberOfRemainingColumnsInRow=2*columns;
		//TODO 550 is currently used as an approximation of the tableWidth. Figure this out another way
		int cellWidthLimit = (530-(15*columns))/(columns);
		int itemNumber = 0;
		for (int index=0; index<data.length; index++){
			QuestionData q = data[index];
			if(q != null && q.getQuestionAddresses() != null){
				itemNumber++;
				System.out.println("Worksheet.java "+q.getQuestionAddresses()+", index = "+index);
				Image image = prepareLatexImage(q.getAddress(),q.getScaleFactors());
				//sets all of cell preferences to the defaults
				PdfPCell cell = new PdfPCell();
				cell.setBorder(0);
				float verticalSpacing = q.getVerticalSpace();
				if (overrideVerticalSpacing){
					verticalSpacing = this.verticalSpacingStatic;
				}
				cell.setPaddingBottom(verticalSpacing);

				//makes image of instructions and places question according to where it must go.
				int scaledInstructionsImageWidth=0;


				if (eachQuestionHasItsOwnInstructions && !q.neverIncludeInstructions()) {
					Image instructionsImage = prepareAndAddInstructions(cell, q.getInstructions(), cellWidthLimit, q.getScaleFactors(),(int)(image.getScaledHeight()));
					int scaledInstructionsImageHeight = (int)(instructionsImage.getHeight()*q.getScaleFactors());
					scaledInstructionsImageWidth = (int)(instructionsImage.getWidth()*q.getScaleFactors());
					//add the image of the question under the instructions
					cell.addElement(new Chunk(image,0,-scaledInstructionsImageHeight));		
				}else{

					cell.addElement(new Chunk(image,0,0));				
				}


				//this is carried out when a row is finished. resets the number of columns remaining
				if(numberOfRemainingColumnsInRow==0) numberOfRemainingColumnsInRow=2*columns;

				int scaledImageWidth=(int)image.getScaledWidth();
				if(scaledInstructionsImageWidth>scaledImageWidth)scaledImageWidth=scaledInstructionsImageWidth;
				/**
				 * I have found there is a margin at the top of each graphic that is inversely proportional to what seems to be approximately the square of the 
				 * scale factor. The next two lines are an attempt to remedy it.
				 */
				//				float littleRemainingSpace=(float) (1/Math.pow((q.getScaleFactors()),1.7));
				float topPadding = (float)(image.getScaledHeight()-15);
				//				float topPadding = (float)((q.getImageMargin()-littleRemainingSpace)*q.getScaleFactors());
				//				System.out.println("topPadding = "+topPadding);
				cell.setPaddingTop(topPadding);


				/**
				 * This last part is used to determine if there is space for the cell to fit into the current row. If
				 * the image is too wide, it is added to the next row
				 */
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
			}else{
				System.out.println("A question came up null");
			}
		}
		table.completeRow();
	}

	public void openFile(){
		openFile(getFilename());
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

	public String getInstructions() {
		return mainInstructions;
	}

	public void setInstructions(String s){
		mainInstructions = s;
	}

	public String getFolder(){
		return folder;
	}

	public void setFolder(String folder){
		this.folder = folder;
	}

	private String getImagesFolder(){
		return getFolder()+"/"+SUBFOLDER_IMAGES+"/";
	}

	private String getWorksheetFolder(){
		return getFolder()+"/"+SUBFOLDER_WORKSHEETS+"/";
	}

	private String getFilename() {
		return getWorksheetFolder()+worksheetName+".pdf";
	}

	public void setWorksheetName(String worksheetName) {
		this.worksheetName = worksheetName;
	}

	public void setProblemTypes(String[] problemTypes) {
		this.problemTypes = problemTypes;
	}

	public void setIncludeID(boolean includeID) {
		this.includeID = includeID;
	}

	public void setHeadingText(String headingText) {
		this.headingText = headingText;
	}

	public void setMainInstructions(String mainInstructions) {
		this.mainInstructions = mainInstructions;
	}

	public void setIncludeInstructions(boolean includeInstructions) {
		this.includeInstructions = includeInstructions;
	}



	public void setNumberOfProblems(int numberOfProblems) {
		this.numberOfProblems = numberOfProblems;
		resetDificulties();
	}

	private void resetDificulties() {
		customDifficulties = new int[numberOfProblems];
		for(int i = 0; i < numberOfProblems; i++){
			customDifficulties[i] = difficulty;
		}
	}

	public int getNumberOfProblems(){
		return numberOfProblems;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		resetDificulties();
	}

	public void setCustomDifficulties(int[] customDifficulties) {
		this.customDifficulties = customDifficulties;
	}

	public  void setDifficultyMode(int mode) {
		this.difficultyMode = mode;
	}

	public void setIncludeTeacherNameInHeader(boolean includeTeacherNameInHeader) {
		this.includeTeacherNameInHeader = includeTeacherNameInHeader;
	}

	public void setScaleFactorPercent(float scaleFactorPercent) {
		this.scaleFactorPercent = scaleFactorPercent;
	}

	public  void setProblemsIncreaseInDifficulty(boolean problemsIncreaseInDifficulty) {
		this.problemsIncreaseInDifficulty = problemsIncreaseInDifficulty;
	}

	public  void setProblemTypesAreSorted(boolean problemTypesAreSorted) {
		this.keepQuestionOrder = problemTypesAreSorted;
	}

	public void setIncludeHeader(boolean b){
		includeHeading = b;
	}

	public void setCustomProblems(ArrayList<CustomProblemData> data){
		this.customProblems = data;
	}

	public void setIdentifier(int i){
		identifier = i;
	}

	public boolean isOverrideVerticalSpacing() {
		return overrideVerticalSpacing;
	}

	public void setOverrideVerticalSpacing(boolean overrideVerticalSpacing) {
		this.overrideVerticalSpacing = overrideVerticalSpacing;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setVerticalSpacingStatic(int vSpacing) {
		this.verticalSpacingStatic =vSpacing; 
	}

	@Override
	public double getProgress() {
		return count;
	}

	@Override
	public int getTotal() {
		//every question and every answer is one task
		return 2*numberOfPages*numberOfProblems;
	}


	public boolean isFinished(){
		return finished;
	}
	@Override
	public void start() {

		Thread creator = new Thread(new Runnable() {

			@Override
			public void run() {
				finished = false;
				try {
					if(OrcMath.createScreen != null){
						OrcMath.createScreen.setOrcWorkerVisible(true);
					}
					createPdf();
					openFile();
				}catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
					if(OrcMath.createScreen != null){
						String error;
						if(fnfe.getMessage().contains("The process cannot access the file because it is being used by another process")){
							error = "A file with the same name is already open. Close the open file or change the File Name.";
						}else{
							error = "The Save Location is not valid. Change it in the Settings Screen.";
						}
						OrcMath.createScreen.presentNotification(error);
					}
				} 
				catch(DocumentException de){
					String error;
					if(de.getMessage().contains("Infinite table loop")){
						error = "One or more rows of the table is too large to fit on a single page. Reduce the vertical spacing.";
					}else{
						error = "An unknown error occurred when trying to create a document.";
					}
					OrcMath.createScreen.presentNotification(error);
					de.printStackTrace();
				}
				catch (Exception e) {
					e.printStackTrace();
					if(OrcMath.createScreen != null){
						OrcMath.createScreen.presentNotification("An error occurred while generating the file.");
					}
				}

				finally{
					count = (int) getTotal();
					if(OrcMath.createScreen != null){
						OrcMath.createScreen.setOrcWorkerVisible(false);
					}
					OrcMath.createScreen.generate.setEnabled(true);
					finished = true;
				}
			}
		});
		creator.start();

	}

	@Override
	public String getDescriptionOfCurrentTask() {
		return currentTask;
	}

	public void reset(){
		count = 0;
	}




}
