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

import com.orcmath.drawable.CoordinateImage;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.LinearExpression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.QuadraticEquation;
import com.orcmath.objects.Term;

public class SolveSupplementary extends DynamicType{

	boolean part2IsAddedToPart1;
	String angle1Name;
	String angle2Name;
	String angle1Measure;
	String angle2Measure;
	int angle1Actual;
	int angle2Actual;
	Expression expressionAngle1;
	Expression expressionAngle2;
	double variation;
	int xValue;
	char variable;
	boolean linear;	
	CoordinateImage image;
	
	protected void getInstance(){
		//step two, determine the type of question and the objective
		image = new CoordinateImage(600, 300, -10, 10,CoordinateImage.Y_COORDINATE);
		drawDynamicImage(image);
		//from the image, take point names that are used in the question text

		 angle1Name=getAngle1Name();
		 angle2Name=getAngle2Name();
		 angle1Measure=getAngle1Measure();
		 angle2Measure=getAngle2Measure();
		
		String questionText =initiateString();
		questionText+="If #"+angle1Measure+"="+expressionAngle1+",# and #"+
				angle2Measure+"="+expressionAngle2+",# what is ";
		int solution;
		if(variation<.5){
			solution = angle1Actual;
			if(difficulty<3)
				questionText+="the value of "+variable+"?";
			else {
				questionText+="#"+angle1Measure+"?";
			}	
		}else{
			solution=angle2Actual;
			if(difficulty<3)
				questionText+="the value of "+variable+"?";
			else {
				questionText+="#"+angle2Measure+"?";
			}	
		}
		
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();

		answerWork.newLine(angle1Measure+"+"+angle2Measure, 
				"180",
				keyTheorem,//theorem
				"textbf");
		answerWork.newLine("\\left("+expressionAngle1+"\\right)+\\left("+expressionAngle2+"\\right)", 
				"180",
				"Substitution.");
		if(difficulty==4){
		for(int index=0; index<expressionAngle1.getTermsOfExpression().length;index++){
			System.out.println("The term is "+expressionAngle1.getTermsOfExpression()[index]+" and its degree is "+expressionAngle1.getTermsOfExpression()[index].getDegree());
		}
		for(int index=0; index<expressionAngle2.getTermsOfExpression().length;index++){
			System.out.println("The term is "+expressionAngle2.getTermsOfExpression()[index]+" and its degree is "+expressionAngle2.getTermsOfExpression()[index].getDegree());
		}
		}
		answerWork.newLine(Format.termArrayToString(Ops.combineLikeTerms(Ops.combineExpressions(expressionAngle1, expressionAngle2))), 
				"180",
				"Combine like terms.");
		
		if(difficulty<4)
			answerWork.addLinearEquationSteps(new Expression(Ops.combineExpressions(expressionAngle1, expressionAngle2)), new Expression(new Term(180)), ""+variable, solution);
		else answerWork.addQuadraticEquationSteps(new Expression(Ops.combineExpressions(expressionAngle1, expressionAngle2)), 
				new Expression(new Term(180)), 
				""+variable, 
				solution);
		if(difficulty>2){
			answerWork.newTextLine("Plug in "+xValue+" for #"+variable+".");
			if(variation<.5){
				answerWork.plugIn(""+variable,xValue, expressionAngle1,angle1Measure);
			}else{
				answerWork.plugIn(""+variable,xValue, expressionAngle2,angle2Measure);
			}
		}
		
		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	
	protected String getAngle2Measure() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getAngle1Measure() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getAngle2Name() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getAngle1Name() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String initiateString() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void drawDynamicImage(CoordinateImage image2) {
		// This needs to be overwritten in the subclass	
	}
	
	protected void initializeExpressions(){
		variation = Math.random();
		if(difficulty==1){
			if(variation<.5){
				expressionAngle1=new LinearExpression(xValue, variable, angle1Actual, true, false).getExpression();
				expressionAngle2=new LinearExpression(xValue, variable, angle2Actual, false).getExpression();
			}else{
				expressionAngle2=new LinearExpression(xValue, variable, angle2Actual, true, false).getExpression();
				expressionAngle1=new LinearExpression(xValue, variable, angle1Actual, false).getExpression();
			}
		}else if(difficulty==2){
			if(variation<.5){
				expressionAngle1=new LinearExpression(xValue, variable, angle1Actual, true).getExpression();
				expressionAngle2=new LinearExpression(xValue, variable, angle2Actual, true).getExpression();
			}else{
				expressionAngle2=new LinearExpression(xValue, variable, angle2Actual, true).getExpression();
				expressionAngle1=new LinearExpression(xValue, variable, angle1Actual, true).getExpression();
			}
		}else if(difficulty==3){
			if(variation<.5){
				expressionAngle1=new LinearExpression(xValue, variable, angle1Actual, true).getExpression();
				expressionAngle2=new LinearExpression(xValue, variable, angle2Actual, true).getExpression();
			}else{
				expressionAngle2=new LinearExpression(xValue, variable, angle2Actual, true).getExpression();
				expressionAngle1=new LinearExpression(xValue, variable, angle1Actual, true).getExpression();
			}
		}else{
			if(variation<.5){
				expressionAngle1=new QuadraticEquation(angle1Actual, variable, xValue, true,false).getRightSideExpression();
				expressionAngle2=new LinearExpression(xValue, variable, angle2Actual, true).getExpression();
			}else{
				expressionAngle2=new QuadraticEquation(angle1Actual, variable, xValue, true,false).getRightSideExpression();
				expressionAngle1=new LinearExpression(xValue, variable, angle1Actual, true).getExpression();
			}

		}
	}
	
}
