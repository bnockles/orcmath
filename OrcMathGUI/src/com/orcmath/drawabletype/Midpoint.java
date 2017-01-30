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
import com.orcmath.objects.Ops;
import com.orcmath.objects.OrderedPair;
import com.orcmath.objects.Procedure;
import com.orcmath.objects.Variable;
import com.orcmath.type.Type;

public class Midpoint extends Type{

	public Midpoint(){
		keyTheorem="Midpoint formula: The coordinate of the midpoint between two points in a coordinate plane is determine by the average of the x- and y- coordinate values";
		instructions = "";
		numberOfColumns=2;
		verticalSpacing=120;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public Midpoint(int difficulty){
		keyTheorem="Midpoint formula: The coordinate of the midpoint between two points in a coordinate plane is determine by the average of the x- and y- coordinate values";
		instructions = "";
		numberOfColumns=2;
		verticalSpacing=120;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		this.difficulty=difficulty;
		getInstance();
		//question and answer is determined in the getInstance constructor
	}

	
	protected void getInstance(){
		String theQuestion = "";
		int xCoordinate1 = Ops.randomInt((-1)*(int)(difficulty*5),(int)(difficulty*5));
		int yCoordinate1 = Ops.randomInt((-1)*(int)(difficulty*5),(int)(difficulty*5));
		int xCoordinate2;
		int yCoordinate2;
		if (difficulty==1 || difficulty==2){
			xCoordinate2 = xCoordinate1 + 2*Ops.randomInt(1, 8);
			yCoordinate2 = yCoordinate1 + 2*Ops.randomInt(1, 8);
		}
		else{
		xCoordinate2 = Ops.randomInt((-1)*(int)(difficulty*5),(int)(difficulty*5));
		yCoordinate2 = Ops.randomInt((-1)*(int)(difficulty*5),(int)(difficulty*5));
		}
		while (xCoordinate2 == xCoordinate1) xCoordinate2 = Ops.randomInt((-1)*(int)(difficulty*2.5),(int)(difficulty*2));
		while (yCoordinate2 == yCoordinate1) yCoordinate2 = Ops.randomInt((-1)*(int)(difficulty*2.5),(int)(difficulty*2));
			
		
		OrderedPair point1 = new OrderedPair(xCoordinate1,yCoordinate1);
		OrderedPair point2 = new OrderedPair(xCoordinate2,yCoordinate2);
		
		double selection = Math.random();
		boolean answerIsAnEndpoint=false;
		char[] vars = Variable.randCapVars(3);
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		if (selection<.33) {
			theQuestion = "What is the midpoint between #" + point2 + "# and #" + point1 + "#?";
			answerWork.addMidpointSteps("M", point1.getXIntValue(), point1.getYIntValue(), point2.getXIntValue(), point2.getYIntValue(), !answerIsAnEndpoint);

		}
		else if (selection<.66){
			OrderedPair m = Procedure.calculateMidpoint(point1, point2);
			theQuestion = "The line segment #\\overline{"+vars[0] + vars[1] + "}# has endpoints #" + vars[0] + point1 + "# and #"+ vars[1] + "(x,y)#. If " +
					"the midpoint,"+vars[2]+", of #\\overline{"+vars[0] + vars[1] + "}# is #" + m + "#, what are the exact coordinates of point #" + vars[1] +"#?";
			answerIsAnEndpoint=true;
			answerWork.addMidpointSteps(""+vars[2], point1.getXIntValue(), point1.getYIntValue(), (point1.getXIntValue()+point2.getXIntValue())/2, (point1.getYIntValue()+point2.getYIntValue())/2, !answerIsAnEndpoint);

		}
		else {
			theQuestion = "Given #\\overline{"+vars[0] + vars[1] + "}# with endpoints #" +
					vars[0] + point1 + "# and #"+ vars[1] + point2 + "#, what is the midpoint, #"+ vars[2]+
					"#, of #\\overline{"+vars[0] + vars[1] + "}#?";
			answerWork.addMidpointSteps(""+vars[2], point1.getXIntValue(), point1.getYIntValue(), point2.getXIntValue(), point2.getYIntValue(), !answerIsAnEndpoint);
		}
		question =  Problem.getLatexLines(theQuestion, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	
}
