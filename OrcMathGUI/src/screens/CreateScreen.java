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
package screens;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.orcmath.local.Problem;

import components.LaTeXReference;
import components.MenuButton;
import components.TopicAccordion;
import components.UpdateNotification;
import data.CustomProblemData;
import data.Worksheet;
import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.Graphic;
import guiTeacher.components.Link;
import guiTeacher.components.ProgressBar;
import guiTeacher.components.ScrollableDragablePane;
import guiTeacher.components.SearchBox;
import guiTeacher.components.SimpleTable;
import guiTeacher.components.SimpleTable.MatchingLengthException;
import guiTeacher.components.SimpleTableRow;
import guiTeacher.components.TableHeader;
import guiTeacher.components.TextBox;
import guiTeacher.components.TextField;
import guiTeacher.interfaces.TextComponent;
import guiTeacher.interfaces.Visible;
import main.OrcMath;

public class CreateScreen extends OrcMathScreen {

	//fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2521723056380224274L;
	//	protected static String worksheetName = "This was created by OrcMath";
	//	protected static String FILE = "Lessons/" + subject +"/" + subject + " " +unit+"/"+unit+"."+lesson+"/resources/"+worksheetName+".pdf";
	//TODO: Images are saving images to a PDF folder that is static. Make this dynamic (ClassworkProblems.class)
	protected static boolean includeID= false;
	protected static int customNumberOfPages = 1;//there is always an error when this is greater than 1, I think it has to do with MathPage disagreeing with numberOfPages Field


	int[] answerImageMargins;
	double[] answerScaleFactors;
	private static int identifier;//TODO once applet is made, this is no longer static


	private int customProblemIndex;

	private static final int BUTTON_WIDTH = 90;
	private static final int BUTTON_HEIGHT = 40;
	private File saveDirectory;
	private LaTeXReference reference;
	private TextField fileName;
	private TextField heading;
	private TextBox instructionsField;
	public Button generate;
	private TopicAccordion questionsByTopic;
	private SimpleTable outputTable;
	private ScrollableDragablePane tableScroll;
	private ProgressBar progressBar;
	private boolean refrenceShowing;

	private Graphic orcWorker;
	private SearchBox search;


	public static final int MARGIN = 20;
	private static final int _ACCORDION_WIDTH= 410;
	private static final int _FIELD_WIDTH= 440;
	private static final int _INDEX_OF_QUANTITY = 0;
	private static final int _INDEX_OF_QUESTION_TYPE = 1;
	private static final int _INDEX_OF_DIFFICULTY = 2;
	private static final int fieldMargin = MARGIN+_ACCORDION_WIDTH+MARGIN;

	public CreateScreen(int width, int height) {
		super(width, height);
	}

	@Override
	public void initAllObjects(List<Visible> viewObjects) {
		refrenceShowing = false;
//		customProblems = new ArrayList<CustomProblemData>();
		customProblemIndex = 0;
		search = new SearchBox(MARGIN, MARGIN+30, _ACCORDION_WIDTH, 30);
		viewObjects.add(search);
		questionsByTopic = new TopicAccordion(this, MARGIN,MARGIN+65,_ACCORDION_WIDTH, search);
		int textFieldHeight = 30;
		int vertSpace = 5+TextField.DESCRIPTION_SPACE;
		reference = new LaTeXReference(this, fieldMargin, MARGIN+25+vertSpace, _FIELD_WIDTH, 500);
		//		reference.setVisible(false);
		viewObjects.add(reference);
		fileName = new TextField(fieldMargin, MARGIN+25+vertSpace, _FIELD_WIDTH, textFieldHeight, "Worksheet","File Name");
		heading = new TextField(fieldMargin, fileName.getY()+fileName.getHeight()+vertSpace, _FIELD_WIDTH, textFieldHeight, "Practice","Header");
		instructionsField = new TextBox(fieldMargin,heading.getY()+heading.getHeight()+vertSpace,_FIELD_WIDTH,200,"Show your work.","Main Instructions");

		//output table
		addTable(viewObjects);


		MenuButton mb = new MenuButton(MARGIN, getHeight()-MenuButton.HEIGHT-MARGIN-UpdateNotification.NOTIFICATION_HEIGHT);
		addGenerateButton(viewObjects);


		int space = 40;
		int progressBarHeight = 50;
		orcWorker = new Graphic(generate.getX()-60, getHeight()-MARGIN-UpdateNotification.NOTIFICATION_HEIGHT-generate.getHeight(), .35, "resources/doing homework.jpg");
		orcWorker.setVisible(false);
		int progressBarWidth = orcWorker.getX()-mb.getX()-mb.getWidth()-space*2;
		progressBar = new ProgressBar(MARGIN + mb.getWidth()+space, getHeight() - UpdateNotification.NOTIFICATION_HEIGHT-progressBarHeight, progressBarWidth, progressBarHeight);
		progressBar.setBarColor(new Color(51,204,204));


		viewObjects.add(questionsByTopic);
		viewObjects.add(fileName);
		viewObjects.add(heading);
		viewObjects.add(instructionsField);
		viewObjects.add(mb);
		viewObjects.add(orcWorker);
		viewObjects.add(progressBar);



	}

	public void setOrcWorkerVisible(boolean b){
		orcWorker.setVisible(b);
	}

	public void setDirectory(File directory){
		saveDirectory = directory;
	}

	public String getSaveDirectory() {
		return saveDirectory.getAbsolutePath();
	}

	private void addGenerateButton(List<Visible> viewObjects) {
		generate = new Button(getWidth()-MARGIN-BUTTON_WIDTH, getHeight()-MARGIN-BUTTON_HEIGHT-UpdateNotification.NOTIFICATION_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT, "Generate", OrcMath.ACCENT_COLOR, new Action() {

			@Override
			public void act() {
				//				String directory = getSaveDirectory();
				//				boolean success = (new File(directory)).mkdirs();
				//				if (success) {
				//					System.out.println("Directories: " 
				//							+ directory + " created");
				//				}
				generate.setEnabled(false);
				identifier=(int)(Math.random()*1000);//TODO once applet is made, this is no longer randomly assigned
				//		  Problem problem = new Problem(problemType,difficulty);
				//		  numberOfColumns = problem.getNumberOfColumns();
				//		  instructions = problem.getInstructions();
				//		  verticalSpacing = problem.getVerticalSpacing();	    


				String[] types = outputTable.getAllColumnValuesAtIndex(_INDEX_OF_QUESTION_TYPE);
				if(types.length >=0){
					//						new BuildCounter(types);

					int[] difficultyValues = outputTable.getAllColumnIntValuesAtIndex(_INDEX_OF_DIFFICULTY);
					String[] quantitiesTexts = outputTable.getAllColumnValuesAtIndex(_INDEX_OF_QUANTITY);
					int[] quantities = new int[quantitiesTexts.length]; 
					int sum =0;
					for(int i = 0; i < quantitiesTexts.length; i++){
						quantities[i]=Integer.parseInt(quantitiesTexts[i]);
						sum+=quantities[i];
					}
					String[] problems = new String[sum];
					int[] difficulties = new int[sum];
					int problemsIndex = 0;
					for(int i = 0; i< quantities.length; i++){
						for(int j = 0; j < quantities[i]; j++){
							problems[problemsIndex]=types[i];
							difficulties[problemsIndex]=difficultyValues[i];
							problemsIndex++;
						}
					}

					Settings s = OrcMath.settings;
					Worksheet cps = new Worksheet();

					cps.setCustomProblems(customProblemsInTable());
					cps.setNumberOfPages(s.getPages());
					cps.setNumberOfProblems(sum);
					cps.setNumberOfColumns(s.getColumns());
					cps.setEachQuestionHasItsOwnInstructions(s.isEachQuestionHasItsOwnDirections());
					cps.setProblemTypes(problems);
					cps.setDifficultyMode(s.getDifficultySettining());
					cps.setDifficulty(s.getDifficulty());
					cps.setCustomDifficulties(difficulties);
					cps.setHeadingText(heading.getText());
					cps.setTeacherName(s.getCustomTeacherName());
					cps.setIncludeHeader(s.includeHeader());
					cps.setIncludeInstructions(s.includeMainInstructions());
					cps.setProblemTypesAreSorted(!s.isShuffled());
					cps.setInstructions(instructionsField.getText());
					cps.setFolder(saveDirectory.getAbsolutePath());
					cps.setWorksheetName(fileName.getText());
					cps.setOverrideVerticalSpacing(OrcMath.settings.getOverrideVerticalSpacing());
					cps.setVerticalSpacingStatic(OrcMath.settings.getVSpacing());
					//						cps.createPdf();
					progressBar.setTask(cps);
					progressBar.startTask(new Action() {

						@Override
						public void act() {
							generate.setEnabled(true);
						}
					});
				}else{
					System.out.println("You cannot create a worksheet with no problems");
				}



			}

		});
		viewObjects.add(generate);

	}

	protected ArrayList<CustomProblemData> customProblemsInTable() {
		ArrayList<CustomProblemData> includedProblems = new ArrayList<CustomProblemData>();
		for(SimpleTableRow data: outputTable.getRows()){
			try{
				TextComponent t = data.getValues()[_INDEX_OF_QUESTION_TYPE];
				if(t instanceof CustomProblemData){
					CustomProblemData cpd = (CustomProblemData)t;
					includedProblems.add(cpd);	
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return includedProblems;
	}

	private void addTable(List<Visible> viewObjects) {
		String[] columns = {"Quantity","Question Type","Difficulty"};
		boolean[] editable = {true,false,true};
		int[] widths = {60,0,70};
		int widthSum =0;
		for(int i: widths){
			widthSum+=i;
		}
		widths[1] = _FIELD_WIDTH-widthSum-SimpleTable.EDIT_COLUMN;
		outputTable = new SimpleTable(this, 0, 0, _FIELD_WIDTH, 100, new TableHeader(columns, widths,editable, 30));
		outputTable.setInputType(0,TextField.INPUT_TYPE_NUMERIC);
		outputTable.setInputType(2,TextField.INPUT_TYPE_NUMERIC);
		ArrayList<Visible> scrollContents = new ArrayList<Visible>();
		scrollContents.add(outputTable);

		tableScroll = new ScrollableDragablePane(this, scrollContents, fieldMargin, instructionsField.getY()+instructionsField.getHeight()+25, _FIELD_WIDTH, 200);
		outputTable.setContainer(tableScroll);
		viewObjects.add(tableScroll);
	}

	public void addQuestion(String[] data){
		outputTable.addRow(data);
		tableScroll.setUpContentImage();
		tableScroll.update();
	}

	//called when a custom problem is created
	public void addQuestion(String problemLaTeX, String solutionLaTeX) {
		String solution = (solutionLaTeX.equals(LaTeXEditor.PLACEHOLDER_TEXT))?"":solutionLaTeX;
//		customProblems.add(new CustomProblemData(problemLaTeX, solution));

		String[] data = new String[3];
		data[_INDEX_OF_DIFFICULTY] = "N/A";
		data[_INDEX_OF_QUANTITY] = "1";
		data[_INDEX_OF_QUESTION_TYPE] = Problem.CUSTOM_TAG;
		outputTable.addRow(data);
		boolean[] edit = {false,false,false};
		try{
			outputTable.setRowEdit(edit);
			outputTable.update();
		}catch(MatchingLengthException e){
			e.printStackTrace();
		}
		outputTable.setColumnContent(_INDEX_OF_QUESTION_TYPE, new CustomProblemData(problemLaTeX, solution,++customProblemIndex, new Action() {

			@Override
			public void act() {
				questionsByTopic.viewCustomProblem(problemLaTeX, solutionLaTeX);
			}
		} ));
		tableScroll.setUpContentImage();
		tableScroll.update();
	}

	/**
	 * called by LaTeX editor when latex is being typed
	 */
	public void showGuide(boolean on) {
		if(refrenceShowing != on){
			fileName.setVisible(!on);
			heading.setVisible(!on);
			instructionsField.setVisible(!on);
			tableScroll.setVisible(!on);
			reference.setVisible(on);
			refrenceShowing = on;
			update();
		}
	}

	public LaTeXReference getReference() {
		return reference;
	}





}
