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
import com.orcmath.objects.Fraction;
import com.orcmath.objects.LinearFunction;
import com.orcmath.objects.Ops;
import com.orcmath.type.Type;

public class DetermineRelationshipOfLines extends Type{
	
	public DetermineRelationshipOfLines(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=80;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public DetermineRelationshipOfLines(int difficulty){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=80;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		getInstance(difficulty);
		//question and answer is determined in the getInstance constructor
	}
	
	private void getInstance(int difficulty){
		String theQuestion = "\\begin{array}{l}";
		Fraction slope1;
		Fraction slope2;
		Fraction intercept1;
		Fraction intercept2;
		if (difficulty==1 || difficulty==2){
			int slope1numerator = Ops.randomInt(-3, 3);
			while (slope1numerator == 0) slope1numerator = Ops.randomInt(-3, 3);
			slope1 = new Fraction (slope1numerator, Ops.randomInt(1, 3));	
		}
		else{
			int slope1numerator = Ops.randomInt(difficulty*(-2),difficulty*2);
			while (slope1numerator == 0) slope1numerator = Ops.randomInt(difficulty*(-2),difficulty*2);
			slope1 = new Fraction(slope1numerator,Ops.randomInt(1, difficulty)*2);
		}
		int yIntercept1 = Ops.randomInt(difficulty*(-3), difficulty*3);
		intercept1 = new Fraction(yIntercept1, 1);
		LinearFunction line1 = new LinearFunction(slope1, intercept1);
		
		
		LinearFunction line2;
		MultipleChoice mc;
		boolean sameLine = false;
		int yIntercept2 = Ops.randomInt(difficulty*(-3), difficulty*3);
		while (yIntercept2==yIntercept1) yIntercept2 = Ops.randomInt(difficulty*(-3), difficulty*3);
		intercept2 = new Fraction(yIntercept2, 1);
		double line2Type = Math.random();
		//35% chance of being parallel
		if (line2Type<=.4){
			line2 = new LinearFunction(slope1, intercept2);
			mc = new MultipleChoice(
			"parallel.}",
			"perpendicular.}",
			"the same line.}",
			"neither perpendicular nor parallel.}",
			"parallel.}");
		}
		//40% chance of being perpendicular
		else if (line2Type>.4 && line2Type<=.8){
			slope2 = line1.getPerpendicularSlope();
			line2 = new LinearFunction(slope2, intercept2);
			mc = new MultipleChoice(
			"parallel.}",
			"perpendicular.}",
			"the same line.}",
			"neither perpendicular nor parallel.}",
			"perpendicular.}");
		}
		//20% chance of being neither perp or par
		else if (line2Type>.8 && line2Type<=1){
			int slope1Numer = slope1.getNumer();
			int slope1Denom = slope1.getDenom();
			int slope2Numer = Ops.randomInt(-3, 3);
			int slope2Denom = Ops.randomInt(-3, 3);
			while (slope2Numer==slope1Numer || slope2Numer==0) slope2Numer = Ops.randomInt(-3, 3);
			while (slope2Denom==slope1Denom || slope2Denom==0) slope2Denom = Ops.randomInt(-3, 3);
			slope2 = new Fraction(slope2Numer, slope2Denom);
			line2 = new LinearFunction(slope2, intercept2);
			mc = new MultipleChoice(
			"parallel.}",
			"perpendicular.}",
			"the same line.}",
			"neither perpendicular nor parallel.}",
			"neither perpendicular nor parallel.}");
		}
		//0% chance of being the same line
		//TODO figure out how to make the same line look different (require simplification)
		else{
			sameLine = true;//this will be used to "disguise" the line
			line2 = new LinearFunction(slope1, yIntercept1);
			mc = new MultipleChoice(
			"parallel.}",
			"perpendicular.}",
			"the same line.}",
			"neither perpendicular nor parallel.}",
			"the same line.}");	
		}
		
		theQuestion += "\\text{Consider the two lines given by the equations }";
		theQuestion +="\\\\\\text{                    }" + line1;
		if (sameLine){
			System.out.println("Program not written for same line questions");
		}
		else theQuestion +="\\\\\\text{                    }" + line2.getSlopeInterceptForm();
		theQuestion +="\\\\\\text{The two lines are}";
		theQuestion += "\\\\\\text{    (1) " + mc.getChoiceA();
		theQuestion +="\\\\\\text{    (2) " + mc.getChoiceB();
		theQuestion +="\\\\\\text{    (3) " + mc.getChoiceC();
		theQuestion +="\\\\\\text{    (4) " + mc.getChoiceD();
		theQuestion += "\\end{array}";
		question = theQuestion;
		answer = "\\text{" + mc.getAnswer();

	}
}
