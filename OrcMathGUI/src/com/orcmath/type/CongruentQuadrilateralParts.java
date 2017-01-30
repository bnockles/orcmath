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
package com.orcmath.type;

import com.orcmath.objects.BalancedEquation;
import com.orcmath.objects.ComplimentaryAngleValues;
import com.orcmath.objects.CongruentValues;
import com.orcmath.objects.Ops;
import com.orcmath.objects.QuadrilateralGraphic;
import com.orcmath.objects.SupplementaryAngleValues;

public class CongruentQuadrilateralParts extends Type{

	public QuadrilateralGraphic quad;
	
	static int lastSelection;
	static int selectionBeforeLast1;
	static int selectionBeforeLast2;
		
	public CongruentQuadrilateralParts(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=80;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public CongruentQuadrilateralParts(int difficulty){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=80;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		getInstance(difficulty);
		//question and answer is determined in the getInstance constructor
	}
	
	private void getInstance(int difficulty){

		//step one: select a quadrilateral type (every type can be used for any question type)
		int type=QuadrilateralGraphic.PARALLELOGRAM;
		boolean diagonalsDrawn=true;
		if (difficulty==1) diagonalsDrawn=false;
		while (type == lastSelection || 
				type == selectionBeforeLast1 ||
				type == selectionBeforeLast2
				){
			double chooser=Math.random();
			if(chooser<.2){
				type=QuadrilateralGraphic.KITE;
			}
			else if (chooser<.4){
				type=QuadrilateralGraphic.TRAPEZOID;
			}
			else if(chooser<.6){
				type=QuadrilateralGraphic.PARALLELOGRAM;
			}
			else if(chooser<.8){
				if(difficulty!=1)
					type=QuadrilateralGraphic.RECTANGLE;
				else type=QuadrilateralGraphic.PARALLELOGRAM;
			}
			else {
				type=QuadrilateralGraphic.RHOMBUS;
			}
		}
		selectionBeforeLast2=selectionBeforeLast1;
		selectionBeforeLast1=lastSelection;
		lastSelection=type;
		
		quad=new QuadrilateralGraphic(type, diagonalsDrawn);
		
		//step two, determine the type of question and the objective
		int questionType=Ops.randomInt(1, 2);
		int objective=Ops.randomInt(0, 1);
		

		
		//step 3, write the question
		if (questionType==1){
			setQuestionType1(difficulty,objective);//congruent sides	
		}
		else if (questionType==2){
			setQuestionType2(difficulty,objective);//congruent angles	
		}
		else{
			question="";
			answer = "";
		}
	}
	


	public void setQuestionType1(int difficulty, int objective){
		CongruentValues congruentValues;
		boolean level3VariableOnly = false;
		if (difficulty == 1) {
			congruentValues = new CongruentValues(Ops.randomInt(4, 20),Ops.randomInt(0, 0),false);
		}
		else if(difficulty == 2){
			congruentValues = new CongruentValues(Ops.randomInt(4, 20),Ops.randomInt(0, 0),true);
		}
		else if (difficulty==3){
			if(Math.random()<.5){//a linear equation, solve for an actual value
				congruentValues = new CongruentValues(Ops.randomInt(20, 70),CongruentValues.LINEAR,true);
			}
			else{//a quadratic equation, solve for variable only
				level3VariableOnly=true;
				congruentValues = new CongruentValues(Ops.randomInt(20, 70),CongruentValues.QUADRATIC,false);
			}
		}
		else{
			congruentValues = new CongruentValues(Ops.randomInt(20, 70),Ops.randomInt(0, 1),true);
		}
		
		//initiate question string
		String theQuestion = "\\begin{array}{l}";

		theQuestion += "\\text{In the diagram below of "+ quad.getTypeName()+" }"+quad.getName()+
				"\\text{, }"+quad.getCongruentSide1()+"="+congruentValues.getEquation().getLeftSide();
		theQuestion += "\\\\\\text{and }"+quad.getCongruentSide2()+"="+congruentValues.getEquation().getRightSide()+"\\text{.}";
		//for level one questions, the variable does not need to be plugged in
		if(difficulty==1 || level3VariableOnly){
			theQuestion += "\\text{ What is the value of }"+congruentValues.getVariable()+"\\text{?}";
		}
		else{
			theQuestion += "\\text{What is the value of }"+getSideObjective(quad,objective)+"\\text{?}";
		}
		theQuestion += "\\\\\\text{                                      }\\includegraphics[interpolation=bicubic]{"+quad.getImageAddress()+"}";
		theQuestion += "\\end{array}\n";

		question = theQuestion;
		answer = "\\text{The value of the variable is }" + congruentValues.getEquation().getSolution() + "\\text{ and }"+getSideObjective(quad, objective)+"="+congruentValues.getEquation().getActualValue();
	}
	
	private void setQuestionType2(int difficulty, int objective) {
		CongruentValues congruentValues;
		boolean level3VariableOnly=false;
		if (difficulty == 1) {
			congruentValues = new CongruentValues(Ops.randomInt(20, 70),Ops.randomInt(0, 0),false);
		}
		else if(difficulty == 2){
			congruentValues = new CongruentValues(Ops.randomInt(20, 70),Ops.randomInt(0, 0),true);
		}
		else if (difficulty==3){
			if(Math.random()<.5){//a linear equation, solve for an actual value
				congruentValues = new CongruentValues(Ops.randomInt(20, 70),CongruentValues.LINEAR,true);
			}
			else{//a quadratic equation, solve for variable only
				level3VariableOnly=true;
				congruentValues = new CongruentValues(Ops.randomInt(20, 70),CongruentValues.QUADRATIC,false);
			}
		}
		else{
			congruentValues = new CongruentValues(Ops.randomInt(20, 70),Ops.randomInt(0, 1),true);
		}
		
		String theQuestion = "\\begin{array}{l}";

		theQuestion += "\\text{In the diagram below of "+ quad.getTypeName()+" }"+quad.getName()+
				"\\text{, }m\\angle "+quad.getCongruentAngle1()+"="+congruentValues.getEquation().getLeftSide();
		theQuestion += "\\\\\\text{and }m\\angle "+quad.getCongruentAngle2()+"="+congruentValues.getEquation().getRightSide()+"\\text{.}";
		//for level one questions, the variable does not need to be plugged in
		if(difficulty==1 || level3VariableOnly){
			theQuestion += "\\text{ What is the value of }"+congruentValues.getVariable()+"\\text{?}";
		}
		else{
			theQuestion += "\\text{ What is the value of }m\\angle "+getAngleObjective(quad,objective)+"\\text{?}";
		}
		theQuestion += "\\\\\\text{                                      }\\includegraphics[interpolation=bicubic]{"+quad.getImageAddress()+"}";
		theQuestion += "\\end{array}\n";

		question = theQuestion;
		answer = "\\text{The value of the variable is }" + congruentValues.getEquation().getSolution() + "\\text{ and }"+getAngleObjective(quad, objective)+"="+congruentValues.getEquation().getActualValue();
		
	}

	public String getSideObjective(QuadrilateralGraphic quad, int objective){
		if(objective==1){
			return quad.getCongruentSide1();
		}
		else{
			return quad.getCongruentSide2();
		}
		
	}
	
	public String getAngleObjective(QuadrilateralGraphic quad, int objective){
		if(objective==1){
			return quad.getCongruentAngle1();
		}
		else{
			return quad.getCongruentAngle2();
		}
		
	}
	

}
