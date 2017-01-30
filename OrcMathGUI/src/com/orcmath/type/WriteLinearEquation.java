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

import com.orcmath.objects.Fraction;
import com.orcmath.objects.LinearFunction;
import com.orcmath.objects.Ops;

public class WriteLinearEquation extends Type{
	
	double questionType;
	boolean perpendicularLineEquation; //false for parallelLines
	
	public WriteLinearEquation(){
		questionType=Math.random();
		if (questionType>.5){
			perpendicularLineEquation=true;
			keyTheorem="Two lines are perpendicular if the slopes of the equations defining the lines are reciprocals of each other.";
		}else{
			perpendicularLineEquation=false;
			keyTheorem="Two lines are parallel if the slopes of the equations defining the lines are equivalent.";
		}
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public WriteLinearEquation(int difficulty){
		questionType=Math.random();
		if (questionType>.5){
			perpendicularLineEquation=true;
			keyTheorem="Two lines are perpendicular if the slopes of the equations defining the lines are reciprocals of each other.";
		}else{
			perpendicularLineEquation=false;
			keyTheorem="Two lines are parallel if the slopes of the equations defining the lines are equivalent.";
		}
		questionType=Math.random();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		this.difficulty=difficulty;
		getInstance();
	}
	
	protected void getInstance(){
		String theQuestion = "\\begin{array}{l}";
		int answerSlopeNumerator = Ops.randomInt(1, 2*difficulty);
		if (Math.random()>.5) answerSlopeNumerator=answerSlopeNumerator*(-1);
		int answerSlopeDenominator = Ops.randomInt(1, difficulty+2);
		Fraction answerSlope = new Fraction(answerSlopeNumerator, answerSlopeDenominator);
		Fraction answerYintercept;
		if (difficulty<4) answerYintercept=new Fraction(Ops.randomInt(difficulty*(-2)+3,difficulty*(2)+3),1);
		else answerYintercept=new Fraction(Ops.randomInt(difficulty*(-2)+3,difficulty*(2)+3),Ops.randomInt(1, 3));
		LinearFunction answerFunction = new LinearFunction(answerSlope,answerYintercept);
		
		LinearFunction compareTo;
		//perpendicular lines
		if (perpendicularLineEquation){
			int compareYintercept = Ops.randomInt(-6, 6);
			while (compareYintercept==answerYintercept.getNumer()) compareYintercept = Ops.randomInt(-6, 6);
			compareTo = new LinearFunction(answerFunction.getPerpendicularSlope(), compareYintercept);
		theQuestion += "\\text{Write an equation for a line that passes through the point }" + answerFunction.getAPoint((-3)*difficulty, 3*difficulty);
		theQuestion += "\\text{ and is perpendicular}";
		theQuestion += "\\\\\\text{to the line, }";
		if(Math.random()>.5) theQuestion += compareTo.toString() + "\\text{.}";
		else theQuestion += compareTo.getSlopeInterceptForm() + "\\text{.}";
		theQuestion += "\\end{array}";
		question = theQuestion;
		answer = answerFunction.getSlopeInterceptForm();
		}
		//parallel lines
		else{
				int compareYintercept = Ops.randomInt(-6, 6);
				while (compareYintercept==answerYintercept.getNumer()) compareYintercept = Ops.randomInt(-6, 6);
				compareTo = new LinearFunction(answerFunction.getSlope(), compareYintercept);
			theQuestion += "\\text{Write an equation for a line that passes through the point }" + answerFunction.getAPoint((-3)*difficulty, 3*difficulty);
			theQuestion += "\\text{ and is parallel}";
			theQuestion += "\\\\\\text{to the line, }";
			if(Math.random()>.5) theQuestion += compareTo.toString() + "\\text{.}";
			else theQuestion += compareTo.getSlopeInterceptForm() + "\\text{.}";
			theQuestion += "\\end{array}";
			question = theQuestion;
			answer = answerFunction.getSlopeInterceptForm();
		}
	}
}
