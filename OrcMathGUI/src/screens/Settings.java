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
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import components.BackToCreateButton;
import components.HelpButton;
import data.DirectorySelector;
import guiTeacher.components.Action;
import guiTeacher.components.Checkbox;
import guiTeacher.components.Component;
import guiTeacher.components.Link;
import guiTeacher.components.RadioButtons;
import guiTeacher.components.TextArea;
import guiTeacher.components.TextField;
import guiTeacher.components.TextLabel;
import guiTeacher.interfaces.TextComponent;
import guiTeacher.interfaces.Visible;
import main.OrcMath;


public class Settings extends OrcMathScreen {

	public static final int  DIFFICULT_MODE_CUSTOM = 0;
	public static final int  DIFFICULT_MODE_UNIFORM = 1;
	public static final int  DIFFICULT_MODE_INCREMENTAL = 2;

	private static final int _MARGIN1 = 20;
	private static final int _MARGIN2 = 340;
	private static final int _Y_MARGIN = 30;
	private static final int _FORM_LINE_WIDTH = 55;


	private Link directory;
	private TextLabel directoryName;
	private TextField teacherName;
	private Checkbox checkbox;
	private TextField spacing;
	private TextField columns;
	private TextField pages;
	private Checkbox includeDirections;
	private Checkbox includeHeader;
	private Checkbox includeMainInstructions;
	private ArrayList<TextField> textFields;
	private TextArea explanation;
	private RadioButtons difficultyMode;
	private TextField staticDifficulty;
	private Checkbox shuffle;


	public Settings(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initAllObjects(List<Visible> viewObjects) {

		BackToCreateButton btcb = new BackToCreateButton(getWidth()-_MARGIN1-BackToCreateButton.WIDTH, getHeight()-BackToCreateButton.HEIGHT-_MARGIN1);
		HelpButton hb = new HelpButton(getWidth()-2*_MARGIN1-2*BackToCreateButton.WIDTH, getHeight()-BackToCreateButton.HEIGHT-_MARGIN1);
		int fieldHeight = 30;


		directory = new Link( _MARGIN1, _Y_MARGIN, 120, 40, "Edit Save Location:",new Action() {

			@Override
			public void act() {
				new DirectorySelector();
			}
		});
		directoryName = new TextLabel(_MARGIN1+directory.getWidth()+5, _Y_MARGIN, 350, fieldHeight, "Directory ");
		viewObjects.add(directoryName);

		int leftWidth = _MARGIN2-_MARGIN1-10;
		teacherName = new TextField(_MARGIN1, _Y_MARGIN+_FORM_LINE_WIDTH*1,leftWidth, fieldHeight, OrcMath.sd.getTeacherName(),"Teacher Name");
		checkbox = new Checkbox("Override vertical spacing", _MARGIN1, _Y_MARGIN+_FORM_LINE_WIDTH*2, leftWidth, OrcMath.sd.isOverrideVerticalSpacing(), null);
		spacing = new TextField(_MARGIN1, _Y_MARGIN+_FORM_LINE_WIDTH*3, leftWidth, fieldHeight, OrcMath.sd.getVSpacing()+"","Vertical Space Between Questions");
		spacing.setInputType(TextField.INPUT_TYPE_NUMERIC);
		columns = new TextField(_MARGIN1, _Y_MARGIN+_FORM_LINE_WIDTH*4, leftWidth/2-2, fieldHeight, 1+"","Number of Columns");
		columns.setInputType(49,51,1);
		pages = new TextField(_MARGIN1+leftWidth/2+4, _Y_MARGIN+_FORM_LINE_WIDTH*4, leftWidth/2-2, fieldHeight, 1+"","Number of Pages");
		pages.setInputType(49,57,1);
		includeDirections = new Checkbox("Each question has its own directions", _MARGIN1, _Y_MARGIN+_FORM_LINE_WIDTH*5, leftWidth, false, null);
		includeHeader = new Checkbox("Include Header", _MARGIN1, _Y_MARGIN+_FORM_LINE_WIDTH*6, leftWidth, OrcMath.sd.isIncludeHeader(), null);
		includeMainInstructions= new Checkbox("Include Main Instructions", _MARGIN1, _Y_MARGIN+_FORM_LINE_WIDTH*7, leftWidth, OrcMath.sd.isIncludeMainInstructions(), null);
		shuffle= new Checkbox("Shuffle Order", _MARGIN1, _Y_MARGIN+_FORM_LINE_WIDTH*8, leftWidth, OrcMath.sd.isShuffleOrder(), null);
		String[] diff = {"Custom","Uniform","Incremental"};
		difficultyMode = new RadioButtons(_MARGIN1, _Y_MARGIN+_FORM_LINE_WIDTH*9, _MARGIN2, 48,"Difficulty Setting", diff, 0, RadioButtons.STYLE_HORIZONTAL);
		staticDifficulty = new TextField(_MARGIN1+110, _Y_MARGIN+_FORM_LINE_WIDTH*9-7, leftWidth-200, fieldHeight, 1+"","");
		staticDifficulty.setInputType(49,52,1);

		explanation = new TextArea(_MARGIN2, _Y_MARGIN+30, getWidth()-_MARGIN2-_MARGIN1, getHeight()-_Y_MARGIN*2-btcb.getHeight(), "");
		explanation.setCustomTextColor(new Color(0,102,102));

		addHoverText(checkbox,"If you think there isn't enough blank space in between questions for students to show their work, check this box to override the vertical space and make it larger (or even smaller)");
		addHoverText(spacing,"Vertical space describes the space in between questions, however, this value is meaningless if the check box above is not checked. A reasonable value is between 20 and 140.");
		addHoverText(columns, "To save space in the page, you can have the questions drawn across 1-3 columns, however, questions with graphics in them, such as geometry questions or graphing questions, won't fit in PDF with more than one column. ");
		addHoverText(includeDirections, "If you are creating a page with multiple types of questions (such as a test) you should check this box. It generates instructions for each question. If you are creating a page of exercises using all the same types, leave this unchecked, since having instructions for each question would be redundant.");
		addHoverText(includeHeader,"At the top of every worksheet is a header. You can edit the header on the Generate page, but here you can decide whether or not you wish to display it.");
		addHoverText(includeMainInstructions, "At the top of every worksheet, under the header, are \"main instructions\". These instructions describe the entire assignment, such as \"For full credit, show your work completely\". You can edit the instructions on the Generate page, but here you can decide whether or not you wish to display it.");
		addHoverText(teacherName, "The teacher name appears automatically at the top of every page.");
		addHoverText(pages, "The number of \"pages\" means the number of unique worksheets. This is handy, since it allows you to make multiple versions, but be aware that more pages consumes more memory, which can cause the Java Virtual Machine to crash. ");
		addHoverText(shuffle, "This will randomize the order of the type of question on each page.");
		addHoverText(difficultyMode, "The generated questions vary in difficulty. \"Difficulty\" is a value between '1' and '4', with '4' being the hardest. On the Generate page, you can view examples of each question with respect to its difficulty, (though not all questions have this variance.) There are three difficulty settings. The first setting is \"Custom\". The Custom setting sets the difficulty of each question according to what you specify on the Generate page. The second setting is \"Uniform\" which will ignore what you have on the Generate page and set all of the difficulties to whatever you specify here. The third setting is \"Incremental\". The Incremental setting increases the difficulty on a linear scale, beginning at '1' and ending at whatever you specify.");
		addHoverText(staticDifficulty,"This value must be between 1 and 4 (you won't be able to type anything else). The value you enter has a different meaning depending on the difficulty mode of your worksheet.");

		viewObjects.add(spacing);
		viewObjects.add(teacherName);
		viewObjects.add(directory);
		viewObjects.add(checkbox);
		viewObjects.add(columns);
		viewObjects.add(pages);
		viewObjects.add(includeDirections);
		viewObjects.add(btcb);
		viewObjects.add(hb);
		viewObjects.add(explanation);
		viewObjects.add(includeHeader);
		viewObjects.add(includeMainInstructions);
		viewObjects.add(shuffle);
		viewObjects.add(difficultyMode);
		viewObjects.add(staticDifficulty);

		textFields = new ArrayList<TextField>();
		for(Visible v: viewObjects){
			if(v instanceof TextField){
				textFields.add((TextField)v);
			}
		}
	}


	private void addHoverText(Component c, String string) {
		c.setHoverAction(new Action() {

			@Override
			public void act() {
				explanation.setText(string);
			}
		});
	}

	public int getColumns(){
		return Integer.parseInt(columns.getText());
	}

	public int getPages(){
		return Integer.parseInt(pages.getText());
	}

	public boolean isEachQuestionHasItsOwnDirections(){
		return includeDirections.isChecked();
	}


	public String getCustomTeacherName() {
		return teacherName.getText();
	}

	public boolean getOverrideVerticalSpacing() {
		return checkbox.isChecked();
	}

	public int getVSpacing() {
		return Integer.parseInt(spacing.getText());
	}


	public boolean includeHeader(){
		return includeHeader.isChecked();
	}

	public boolean includeMainInstructions(){
		return includeMainInstructions.isChecked();
	}

	public boolean isShuffled(){
		return shuffle.isChecked();
	}

	public int getDifficultySettining(){
		return difficultyMode.getIndexOfChecked();
	}

	public int getDifficulty(){
		return Integer.parseInt(staticDifficulty.getText());
	}



	public boolean containsEmptyFields() {
		boolean emptiesExist = false;
		for(TextComponent tc: textFields){
			if(tc.getText().equals("")){
				emptiesExist=true;
				break;
			}
		}
		return emptiesExist;
	}

	public void setDirectoryText(String directoryName) {
		this.directoryName.setText(directoryName);
	}

}
