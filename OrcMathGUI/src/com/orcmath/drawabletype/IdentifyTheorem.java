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
import com.orcmath.local.MultipleChoice;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.local.Worksheet;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Variable;
import com.orcmath.type.Type;
import com.orcmath.type.WriteLinearEquation;

public class IdentifyTheorem extends DynamicType{

	//fields accessed by methods
		String questionType;
		char[] labels;
		CoordinateImage image;
		Type selectedType;
		ArrayList<Type> mcTypes;
		MultipleChoice mc;
		boolean multipleChoice = true;
		boolean includeAlgebraicWork;
/*		include any and all variables involved in the graphic
 * 
 */
		
		
		//static fields
		public static String keyTheorem="";//always include
		public static int questionWidth = Worksheet.latexWidth;
		public static String[] theoremFamily={
			"Cirles",
//			"Coordinate Geometry"
		};
		
		public static ArrayList<Type> circleTheorems= new ArrayList<Type>(Arrays.asList(
				new IntersectingChords(),
				new InscribedAngleArc(),
				new ParallelChords(),
				new InscribedAngleSameArc(),
				new TangentLines(),
				new CentralAngles(),
				new SecantLengths(),
				new ChordLengths(),
				new ChordDistanceFromCenter(),
				new TangentPythagorean(),
				new ChordTangentAngle(),
				new InscribedQuadrilateral(),
				new SecantAngles()));
		public static ArrayList<Type> coordianteTheorems= new ArrayList<Type>(Arrays.asList(
				new Midpoint(),
				new DistanceFormula(),
				new WriteLinearEquation(),
				new WriteLinearEquation()));
		static String lastSelection;
		static String selectionBeforeLast1;
		static String selectionBeforeLast2;
		static String selectionBeforeLast3;
		static String selectionBeforeLast4;
		static String selectionBeforeLast5;
		static String selectionBeforeLast6;
		static String selectionBeforeLast7;
		static String selectionBeforeLast8;
		static String selectionBeforeLast9;
		static String selectionBeforeLast10;
		static String selectionBeforeLast11;
		
		public IdentifyTheorem(){
			instructions = "";
			numberOfColumns=1;
			verticalSpacing=0;	
			whetherInstructionsAreNeverIncluded=true;
//			scaleFactor=.35;
		}
		
		public IdentifyTheorem(int difficulty, boolean includeAlgebraicWork){
			instructions = "";
			numberOfColumns=1;
			verticalSpacing=0;	
			whetherInstructionsAreNeverIncluded=true;
//			scaleFactor=.35;
			this.difficulty=difficulty;
			this.includeAlgebraicWork=includeAlgebraicWork;
			getInstance();
		}
		
		protected void getInstance(){	
			
			pickQuestion();
			
			String choice = selectedType.getKeyTheorem();
			while (choice.equals(lastSelection)|| 
					choice.equals(selectionBeforeLast1) ||
					 choice.equals(selectionBeforeLast2) ||
					 choice.equals(selectionBeforeLast3)||
					 choice.equals(selectionBeforeLast4)||
					 choice.equals(selectionBeforeLast5)||
					 choice.equals(selectionBeforeLast6)||
					 choice.equals(selectionBeforeLast7)||
					 choice.equals(selectionBeforeLast8)||
					 choice.equals(selectionBeforeLast9)||
					 choice.equals(selectionBeforeLast10)
					 ) {
				System.out.println("Stuck in IdentifyTheorem class");
				pickQuestion();
				choice= selectedType.getKeyTheorem();
			}
			
			selectionBeforeLast10 = selectionBeforeLast9;
			selectionBeforeLast9 = selectionBeforeLast8;
			selectionBeforeLast8 = selectionBeforeLast7;
			selectionBeforeLast7 = selectionBeforeLast6;
			selectionBeforeLast6 = selectionBeforeLast5;
			selectionBeforeLast5 = selectionBeforeLast4;
			selectionBeforeLast4 = selectionBeforeLast3;
			selectionBeforeLast3 = selectionBeforeLast2;
			selectionBeforeLast2 = selectionBeforeLast1;
			selectionBeforeLast1 = lastSelection;
			lastSelection = choice;
			
			
			selectedType.setData(difficulty);
			question=selectedType.getQuestion();
			System.out.println("Class of question type is "+selectedType.getClass());
			if(DynamicType.class.isAssignableFrom(selectedType.getClass())){
				image = new CoordinateImage(300, 300, -10, 10, -10, 10);
				dynamicImage=((DynamicType)selectedType).getDynamicImage();
			}
			writeQuestion();
			
		}
		
		private void pickQuestion(){
			String fromFamily = theoremFamily[Ops.randomInt(0, theoremFamily.length-1)];
			ArrayList<Type> questionTypes;
			if(fromFamily.equals(theoremFamily[0])){
				questionTypes=circleTheorems;
			}else{
				questionTypes=circleTheorems;
			}
			
			System.out.println("The selected family of questions contains "+questionTypes.size()+" types");
			if(difficulty>2 || !multipleChoice){
				selectedType=questionTypes.get(Ops.randomInt(0, questionTypes.size()-1));
			}else{
				mcTypes=new ArrayList<Type>();
				if (difficulty==1){		
				Type nextAddition;
				for(int index=0; index<4; index++){
					nextAddition =questionTypes.get(Ops.randomInt(0, questionTypes.size()-1));
					while(mcTypes.contains(nextAddition)){
						nextAddition =questionTypes.get(Ops.randomInt(0, questionTypes.size()-1));
					}
					mcTypes.add(nextAddition);
				}
				selectedType=mcTypes.get(Ops.randomInt(0, mcTypes.size()-1));
				mc = new MultipleChoice(
						mcTypes.get(0).getKeyTheorem(),
						mcTypes.get(1).getKeyTheorem(),
						mcTypes.get(2).getKeyTheorem(),
						mcTypes.get(3).getKeyTheorem(),
						selectedType.getKeyTheorem());
				}else{
					selectedType=questionTypes.get(Ops.randomInt(0, questionTypes.size()-1));
					mc = new MultipleChoice(
							selectedType.getKeyTheorem(),
							selectedType.getFalseTheorem(1),
							selectedType.getFalseTheorem(2),
							selectedType.getFalseTheorem(3),
							selectedType.getKeyTheorem());
				}
			}
		}
		
		public void writeQuestion(){	

//			String text="";//question text goes here
			
			//prepare the image at each difficulty level
			if(difficulty<3 && multipleChoice){
//				text+=Problem.getLatexLines("Which statement or theorem is most suitable for solving this problem?", "#", questionWidth, "text");//and add this to the instructions
//				text+="}\\\\\\text{     (1) "+mc.getChoiceA();
//				text+="}\\\\\\text{     (2) "+mc.getChoiceB();
//				text+="}\\\\\\text{     (3) "+mc.getChoiceC();
//				text+="}\\\\\\text{     (4) "+mc.getChoiceD();
				lineUnderImage=mc.getMultipleChoiceTable("Which statement or theorem is most suitable for solving this problem?",false, questionWidth);
//				lineUnderImage = text;
			}else{

				lineUnderImage=Problem.getLatexLines("Write a theorem or justification that would be used to solve this problem.", "#%&", questionWidth, "text");//and add this to the instructions
			}
			
			String answerLines = Problem.getLatexLines("---------------#\\\\#"+mc.getCorrectSelction()+" "+selectedType.getKeyTheorem(), "#", questionWidth, "text");
			if(includeAlgebraicWork){
			answerLines+="\\\\"+selectedType.getAnswer();
			}
			answer=answerLines;
			
			
		}

		public void drawDynamicImage(CoordinateImage image){
			labels = Variable.randCapVars(5);
			/*
			 * instructions for drawing graphic
			 */
			dynamicImage=image.getImage();
		}
}
