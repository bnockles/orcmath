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

import com.orcmath.local.MultipleChoice;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.PythagoreanEquation;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class AlgebraPracticeLinear extends MultipleChoiceQuestion{

	char variable;
	
	public AlgebraPracticeLinear(){
		variable=Variable.randVar();
		instructions = "Solve for "+variable+".";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public AlgebraPracticeLinear(int difficulty, boolean multiplechoice){
		addMultipleChoice=multiplechoice;
		variable=Variable.randVar();
		instructions = "Solve for "+variable+".";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		getInstance(difficulty);
	}
	
	private void getInstance(int difficulty){
		
		int xValue = Ops.randomInt(-12, 12);
		int leftCoef = 1;
		int rightCoef = 1;
		int leftB= 1;
		int rightB = 51;
		while (Math.abs(rightB)>50){
			leftCoef = Ops.randomNotZero(-10, 10);
			//insert this code for positive results
//			if((xValue<0 && leftCoef>0) || (xValue>0 && leftCoef<0)){
//				leftCoef=-leftCoef;
//			}
//
			rightCoef = Ops.randomNotZero(-10, 10);
			while(rightCoef==leftCoef || rightCoef ==-leftCoef){
				rightCoef = Ops.randomNotZero(-10, 10);
			}
			leftB= Ops.randomInt(-50, 50);
			//insert this code for positive results
//			while(leftCoef*xValue+leftB<0){
//				leftB= Ops.randomInt(-50, 50);
//			}
//	
			rightB = leftCoef*xValue+leftB-rightCoef*xValue;
		}
		Term[] left = {new Term(leftCoef,""+variable),new Term(leftB)};
		Term[] right = {new Term(rightCoef,""+variable),new Term(rightB)};
		Expression leftExp = new Expression(left);
		Expression rightExp = new Expression(right);
		question = leftExp+"="+rightExp;

		if(addMultipleChoice){
			int distractor1 = (rightB+leftB)/(leftCoef-rightCoef);
			int distractor2 = (rightB+leftB)/(leftCoef+rightCoef);
			int distractor3 = (rightB-leftB)/(leftCoef+rightCoef);
			while(distractor1==xValue || distractor1==distractor2 || distractor1==distractor3){
				distractor1=Ops.randomInt(xValue-6, xValue-2);
			}
			while(distractor2==xValue || distractor2==distractor3){
				distractor2=Ops.randomInt(xValue-2, xValue+2);
			}
			while(distractor3==xValue){
				distractor3=Ops.randomInt(xValue+2, xValue+6);
			}
			multipleChoice=new MultipleChoice(
					xValue+"",
					distractor1+"",
					distractor2+"",
					distractor3+"",
					xValue+"");


			lineUnderImage=multipleChoice.getMultipleChoiceTable("In the equation above, what is the value of "+variable+"?", true, QUESTION_WIDTH);
		}
		
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		answerWork.setCheckWork(true);
		answerWork.addLinearEquationSteps(leftExp, rightExp, ""+variable, xValue);
		answerWork.finish();
		answer=answerWork.getLatexTabular();
	}
}
