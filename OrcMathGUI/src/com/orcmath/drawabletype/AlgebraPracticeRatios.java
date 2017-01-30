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

import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class AlgebraPracticeRatios extends MultipleChoiceQuestion{
	
	int xValue;
	int numberOfNumbers;
	int total;
	int[] coefficients;
	
	public AlgebraPracticeRatios(){
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	public AlgebraPracticeRatios(int difficulty){
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		getInstance(difficulty);
	}
	
	private void setUp(){
		xValue = Ops.randomInt(2, 10);
		numberOfNumbers=Ops.randomInt(2, 6);
		coefficients=new int[numberOfNumbers];
		total=0;
		for(int i=0; i<numberOfNumbers; i++){
			coefficients[i]=Ops.randomInt(1, 6);
			//there can be duplicate coefficients, but not in a row, or even the same as two before
			while((i>0 && coefficients[i-1]==coefficients[i]) ||(i>1 && coefficients[i-2]==coefficients[i])){
				coefficients[i]=Ops.randomInt(1, 6);
			}
			total+=coefficients[i]*xValue;
		}

		String questionText = "The ratio between "+numberOfNumbers+" numbers is "+coefficients[0];
		for(int i=1; i<numberOfNumbers; i++){
			questionText+=":"+coefficients[i];
		}
		questionText +=". The sum of the numbers is "+total+". What are the numbers?";
		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
	}
	
	
	private void getInstance(int difficulty){
		setUp();
		
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		answerWork.setCheckWork(true);
		answerWork.addSolveRatioSteps(coefficients, total);
		answerWork.finish();
		answer=answerWork.getLatexTabular();
	}
}
