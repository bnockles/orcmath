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
package com.orcmath.drawabletype;

import java.util.ArrayList;
import java.util.Arrays;

import com.orcmath.drawable.CoordinateImage;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Variable;

/*
 * This is a template! Do not call for an instance of this class!
 */
public class Template extends DynamicType{

	//fields accessed by methods
		int difficulty;
		String questionType;
		char[] labels;
		CoordinateImage image;
/*		include any and all variables involved in the graphic
 * 
 */
		
		
		//static fields
		//IMPORTANT: Don't forget to write key theorem in the constructor!
		public static int questionWidth = 600;
		public static ArrayList<String> variations = new ArrayList<String>(Arrays.asList(""));//include for problems that can be written different ways
		static int lastSelection;
		static int selectionBeforeLast1;
		static int selectionBeforeLast2;
		
		public Template(){
			keyTheorem="";
			instructions = "";
			numberOfColumns=1;
			verticalSpacing=45;	
			whetherInstructionsAreNeverIncluded=true;
			scaleFactor=.35;
		}
		
		public Template(int difficulty){
			keyTheorem="";
			instructions = "";
			numberOfColumns=1;
			verticalSpacing=45;	
			whetherInstructionsAreNeverIncluded=true;
			scaleFactor=.35;
			this.difficulty=difficulty;
			getInstance();
		}
		
		protected void getInstance(){

			image = new CoordinateImage(300, 300, -10, 10, -10, 10);
			drawDynamicImage(image);

			questionType=variations.get(Ops.randomInt(0, variations.size()-1));
			if(questionType.equals(variations.get(0))){
				writeQuestionType1();		
			}
			/*
			 * for more question types, add else statements
			 */
			
		}
		
		
		
		public void writeQuestionType1(){	
			//prepare the measurement labels (example below) 
//			int arc1measure=Math.abs(randomAngle2-randomAngle1);
//			int arc2measure=Math.abs(randomAngle4-randomAngle3);
			
			//declare expresions (example below)
//			Expression arc1expression;
//			Expression arc2expression;

		
			//initiate question string
			String text="";//question text goes here
			
			//prepare the image at each difficulty level
			if(difficulty==1){
				/*
				 * do this to the graphic 
				 */
				text+="";//and add this to the instructions
			}else if(difficulty==2){
				/*
				 * do this to the graphic
				 */
				text+="";//and add this to the instructions
			}else if(difficulty==3){
				/*
				 * do this to the graphic
				 */
				text+="";//and add this to the instructions
			}else{
				/*
				 * do this to the graphic
				 */
				text+="";//and add this to the instructions
			}
			

		
			
			WorkTable answerWork = new WorkTable();
			answerWork.addHorizontalLine();
			/*
			 * IMPORTANT!
			 * This line is the first line of the answer key and must include the key theorem
			 */
			answerWork.newLine("",//left side of equation 
					"", //right side of equation 
					keyTheorem,//theorem
					"textbf");//text style (bold by default)
			//variations
			if(difficulty==1){
				/*
				 * do this to the answer
				 */
			}else if(difficulty==2){
				/*
				 * do this to the answer
				 */
			}else if(difficulty==3){
				/*
				 * do this to the answer
				 */
			}else{
				/*
				 * do this to the answer
				 */
			}
			
			question = Problem.getLatexLines(text, "#", questionWidth, "text");
			answerWork.finish();//cannot omit this or there will be an error!
			answer=answerWork.getLatexTabular();
			
		}

		public void drawDynamicImage(CoordinateImage image){
			labels = Variable.randCapVars(5);
			/*
			 * instructions for drawing graphic
			 */
			dynamicImage=image.getImage();
		}
}
