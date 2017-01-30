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

import com.orcmath.drawable.Circle;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.LinearExpression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class EquationParallelPerp extends DynamicType{

	char[] labels;
	CoordinateImage image;
	CoordinatePoint point;
	CoordinatePoint linePoint1;
	CoordinatePoint linePoint2;
	CoordinatePoint intersection;
	int slopeNumerator;
	int slopeDenominator;
	int yIntercept;
	int givenSlopeNumerator;
	int givenSlopeDenominator;
	int givenYIntercept;

	public static int undefinedIdentifier = 101101110;//used to recognize undefined (vertical) slopes

	public EquationParallelPerp(boolean parallel){
		initiateKeyTheorems(parallel);
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}

	public EquationParallelPerp(boolean parallel, boolean includeImage, int difficulty){
		initiateKeyTheorems(parallel);
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		this.difficulty=difficulty;
		getInstance(parallel, includeImage);
		//question and answer is determined in the getInstance constructor
	}

	private void initiateKeyTheorems(boolean parallel){
		if(parallel){
			keyTheorem="Two lines are parallel if they have the same slope but different y-intercepts";
			falseTheorem1="Two lines are parallel if they have the same slope and y-intercepts.";
			falseTheorem2="Two lines are parallel if their slopes are reciprocals of each other.";
			falseTheorem3="Two lines are parallel if their slopes are negative reciprocals.";
		}else{
			keyTheorem="Two lines are perpendicular if their slopes are negative reciprocals.";
			falseTheorem1="Two lines are perpendicular if they have the same slope and y-intercepts.";
			falseTheorem2="Two lines are perpendicular if they have the same slope but different y-intercepts";
			falseTheorem3="Two lines are perpendicular if their slopes are reciprocals.";
		}
	}

	protected void getInstance(boolean parallel, boolean includeImage){
		//initiates the slope and y-intercept of the answer;
		slopeNumerator=Ops.randomNotZero(-4, 4);
		slopeDenominator=Ops.randomInt(1, 4);
		yIntercept=Ops.randomInt(-8, 8);
		String description;//used in the question text
		if(parallel){
			givenSlopeNumerator=slopeNumerator;
			givenSlopeDenominator=slopeDenominator;
			givenYIntercept=Ops.randomInt(-8, 8);
			while(givenYIntercept==yIntercept){
				givenYIntercept=Ops.randomInt(-8, 8);
			}
			description="parallel ";
		}else{
			givenSlopeNumerator=-slopeDenominator;
			givenSlopeDenominator=slopeNumerator;
			if(givenSlopeDenominator==0){
				givenSlopeNumerator=undefinedIdentifier;
				givenSlopeDenominator=1;
			}
			givenYIntercept=Ops.randomInt(-8, 8);
			description="perpendicular ";
		}
		//selects a point with integer coordinates on the line that can be seen in the coordinate plane
		int xValue = slopeDenominator*Ops.randomInt(-6, 6);
		int yValue = slopeNumerator*xValue/slopeDenominator+yIntercept;
		while (xValue>10 || xValue<-10 || yValue>10 || yValue<-10){
			xValue = slopeDenominator*Ops.randomInt(-6, 6);
			yValue = slopeNumerator*xValue/slopeDenominator+yIntercept;
		}
		intersection = new CoordinatePoint(xValue, yValue);
		//creates another point that our desired line goes through
		if(parallel){
			point=intersection;
		}else{
			int xValue2 = slopeDenominator*Ops.randomInt(-6, 6);
			int yValue2 = slopeNumerator*xValue/slopeDenominator+yIntercept;
			while (xValue2==xValue || xValue2>10 || xValue2<-10 || yValue2>10 || yValue2<-10){
				xValue2 = slopeDenominator*Ops.randomInt(-6, 6);
				yValue2 = slopeNumerator*xValue/slopeDenominator+yIntercept;
			}
			point = new CoordinatePoint(xValue2, yValue2);
		}
		//calls the image constructor, if it is needed
		if(includeImage){
			image = new CoordinateImage(300, 300, -10, 10, -10, 10);
			drawDynamicImage(image);
		}
		//initiates the string describing the given line, depending on difficulty
		String givenLine="";
		String givenSlopeTerm = new Term(new Fraction(givenSlopeNumerator, givenSlopeDenominator)).toString();
		String givenInterceptTerm = WorkTable.getLeadingOperation(givenYIntercept);
		String slopeTerm;
		String interceptTerm= WorkTable.getLeadingOperation(yIntercept);
		if(parallel){
			slopeTerm=givenSlopeTerm;
		}else{
			slopeTerm = new Term(new Fraction(slopeNumerator, slopeDenominator)).toString();	
		}

		int a = 0;
		int b = 0;
		int c = 0;
		if(difficulty==1 || difficulty==2){
			givenLine="y="+givenSlopeTerm+"x"+givenInterceptTerm;
		}else{
			int k = Ops.randomInt(1, 3);
			a = -givenSlopeNumerator*k;
			b = givenSlopeDenominator*k;
			c = givenYIntercept*givenSlopeDenominator*k;
			if(a!=1 || a!=-1 || a!=0){
				givenLine=a+"x";
			}else if (a==1){
				givenLine="x";
			}else if (a==-1){
				givenLine="-x";
			}
			if(b!=1 && b!=-1 && a!=0){
				if(b>0)givenLine+="+"+b+"y="+c;
				else givenLine+=b+"y="+c;
			}else{
				if(b==1)givenLine+="y="+c;
				else if(b==-1)givenLine+="-y="+c;
				else givenLine+="="+c;
			}
		}

		//initiate question string
		String questionText = "Write an equation of the line that passes through the point, ("+(int)point.getxCoordinate()+","+(int)point.getyCoordinate()+")"+
				" and is "+description+"to the line whose equation is #"+givenLine+"#.";
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		answerWork.newTextLine("We will try to write an equation in the form, #y=mx+b.# To do this, we first need to " +
				"calculate the value of the slope of the given line.");
		Fraction givenSlope = new Fraction(givenSlopeNumerator, givenSlopeDenominator);
		if(difficulty>2){//it is necessary to isolate y
			answerWork.newLine(a+"x"+WorkTable.getLeadingOperation(b)+"y", ""+c, "Original equation");
			answerWork.setSymbol("\\hspace{1}");
			answerWork.newLine(WorkTable.getLeadingOperation(new Term(-a,"x"))+"\\hspace{"+WorkTable.getWidth(WorkTable.getLeadingOperation(b)+"y")+"}", new Term(-a,"x")+"", "Additive inverse.");
			answerWork.setSymbol("=");
			answerWork.newLine(b+"y", new Term(-a,"x")+WorkTable.getLeadingOperation(c), "Simplify.");
			answerWork.newLine("\\frac{"+b+"y}{"+b+"}", "\\frac{"+-a+"x"+WorkTable.getLeadingOperation(c)+"}{"+b+"}", "Divide both sides by "+b);
			answerWork.newLine("y", slopeTerm+"x"+givenInterceptTerm, "Simplify.");
		}else{
			answerWork.newLine("y", givenSlope.toString()+"x"+givenInterceptTerm, "#y =mx+b# form");
		}
		//identify the slope
		
		answerWork.newLine("\\text{original slope}", givenSlope.toString(), "Taken from above equation.");
		Fraction slope = new Fraction(slopeNumerator, slopeDenominator);
		if(parallel){
			answerWork.newLine("\\text{parallel slope}", slope.toString(), "We are asked to find a parallel line, therefore the two lines must have equal slopes");
		}else{
			answerWork.newLine("\\text{perpendicular slope}", slope.toString(), "We are asked to find a perpendicular line, therefore the slopes of the two lines must be negative reciprocals");
		}
		answerWork.addHorizontalLine();
		answerWork.newTextLine("Now that we know the slope is "+slope.toString()+", we need to find #b#. Since " +
				"the line must go through the point, ("+(int)point.getxCoordinate()+","+(int)point.getyCoordinate()+"), " +
				"we will plug in these values for #x# and #y# to solve for #b#.");
		answerWork.newLine("y", slopeTerm+"x+b", "We know the value of #m#, but we still need to find #b#.");
		answerWork.newLine(""+(int)point.getyCoordinate(), slopeTerm+"\\left("+(int)point.getxCoordinate()+"\\right)+b", "Substitute.");
		int pluginValue=(slopeNumerator*(int)point.getxCoordinate()/slopeDenominator);
		answerWork.newLine(""+(int)point.getyCoordinate(), pluginValue+"+b", "Simplify.");
		answerWork.setSymbol("\\hspace{1}");
		answerWork.newLine(""+WorkTable.getLeadingOperation(-pluginValue), ""+WorkTable.getLeadingOperation(-pluginValue), "Additive inverse.");
		answerWork.setSymbol("=");
		int bValue = (int)point.getyCoordinate()-pluginValue;
		answerWork.newLine(bValue+"", "b", "Simplify.");
		answerWork.addHorizontalLine();
		answerWork.newTextLine("Therefore, we have found the value of #m# = "+slopeTerm+" and the value of #b#="+bValue+". This gives us the final answer:");
		answerWork.newLine("y", slopeTerm+"x"+WorkTable.getLeadingOperation(bValue), "Final Answer");
		answerWork.newLine((int)point.getyCoordinate()+"", slopeTerm+"\\left("+(int)point.getxCoordinate()+"\\right)"+WorkTable.getLeadingOperation(bValue), "You can check your answer by plugging in the coordinates of the original point.");
	
		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	public void drawDynamicImage(CoordinateImage image){
		labels = Variable.randCapVars(3);

		dynamicImage=image.getImage();
	}

}
