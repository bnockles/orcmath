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

import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Ops;
import com.orcmath.objects.OrderedPair;
import com.orcmath.objects.Procedure;
import com.orcmath.type.Type;

public class DistanceFormula extends DynamicType{
	
	public DistanceFormula(){
		keyTheorem="Distance Formula: The distance between two points in a coordinate plane is the square root of the sum of the squares of the distances between the x- and y-values of the endpoints";
		instructions = "";
		numberOfColumns=2;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.5;
	}
	
	public DistanceFormula(int difficulty){
		keyTheorem="Distance Formula: The distance between two points in a coordinate plane is the square root of the sum of the squares of the distances between the x- and y-values of the endpoints";
		instructions = "";
		numberOfColumns=2;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.5;
		this.difficulty=difficulty;
		getInstance();
		//question and answer is determined in the getInstance constructor
	}

	protected void getInstance(){
		String theQuestion = "";
		int differenceInX;
		if (difficulty==1) differenceInX= Ops.randomInt(2, (int)(4));
		else differenceInX= Ops.randomInt(2, difficulty*2);
		int differenceInY; 
		if (difficulty==1) differenceInY = differenceInX * Ops.randomInt(2, 3);
		else{
		differenceInY = differenceInX * Ops.randomInt(2, difficulty);
		}
		
		//sometimes the distance can't be simplified (30% chance)
		double special= Math.random();
		if (special<.6 && special>=.5){
			differenceInY=Ops.randomInt(3, 3*difficulty);
		}
		if (special<.5 && special>=.45){
			differenceInY=7;
			differenceInX=24;
		}
		if (special<.45 && special>=.4){
			differenceInY=24;
			differenceInX=7;
		}
		if (special<.4 && special>=.35){
			differenceInY=12;
			differenceInX=5;
		}
		if (special<.35 && special>=.3){
			differenceInY=5;
			differenceInX=12;
		}
		if (special<.3 && special>=.25){
			differenceInY=8;
			differenceInX=15;
		}
		if (special<.25 && special>=.2){
			differenceInY=15;
			differenceInX=8;
		}
		if (special<.2&& special>=.1){
			int factor = Ops.randomInt(1, difficulty);
			differenceInY=4*factor;
			differenceInX=3*factor;
		}
		if (special<.2&& special>=.1){
			int factor = Ops.randomInt(1, difficulty);
			differenceInY=3*factor;
			differenceInX=4*factor;
		}
		
		
		OrderedPair point1 = new OrderedPair(Ops.randomInt((-1)*(int)(difficulty*2.5), (int)(difficulty*2)), Ops.randomInt((-1)*(int)(difficulty*2.5), (int)(difficulty*2)));
		OrderedPair point2 = new OrderedPair(point1.getXIntValue()+differenceInX, point1.getYIntValue()+differenceInY);
		theQuestion += "What is the distance between #";
		if (Math.random()<.5) theQuestion += point1 + "# and #" + point2;
		else theQuestion += point2 + "# and #" + point1;
		theQuestion += "#? Write your answer in simplest radical form.";
		question =  Problem.getLatexLines(theQuestion, "#", QUESTION_WIDTH, "text");
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		answerWork.addDistanceFormulaStepsRadical(point1.getXIntValue(), point1.getYIntValue(),point2.getXIntValue(), point2.getYIntValue());
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	
}
