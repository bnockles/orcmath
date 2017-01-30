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
import com.orcmath.objects.Variable;

public class SolvingCongruency extends DynamicType{

	String congPart1;
	String congPart2;
	String measureCongPart1;
	String measureCongPart2;
	boolean linear;
	int actualMeasure;
	Expression expressionCongPart1;
	Expression expressionCongPart2;
	int xValue;
	char variable;
	
	CoordinateImage image;
	
	protected void getInstance(){
		//step two, determine the type of question and the objective
		image = new CoordinateImage(600, 300, -20, 20, -10, 10);
		drawDynamicImage(image);
		//from the image, take point names that are used in the question text
		
		congPart1=getCongruentPart1Name();
		congPart2=getCongruentPart2Name();
		measureCongPart1=getCongruentPart1Measure();
		measureCongPart2=getCongruentPart2Measure();
		
		String questionText =initiateString();

		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();

		answerWork.setSymbol("\\cong");
		answerWork.newLine(congPart1, 
				congPart2,
				keyTheorem,//theorem
				"textbf");
		answerWork.setSymbol("=");
		answerWork.newLine(measureCongPart1, 
				measureCongPart2,
				"Definition of congruent");

		//variations
		if(difficulty==1){
			questionText+="# If #"+measureCongPart1+"="+actualMeasure+",# what is the measure of #"+congPart2+"?";
			answerWork.newLine(""+actualMeasure, 
					measureCongPart2,
					"Substitution.");
		}else{
			if(difficulty==2 || difficulty==3){
				questionText+="# If #"+measureCongPart1+"="+expressionCongPart1+
						",# and #"+measureCongPart2+"="+expressionCongPart2+
						"# what is the value of #"+variable+"?";
			}else{//difficulty 4
				if(linear){
					questionText+="# If #"+measureCongPart1+"="+expressionCongPart1+
						",# and #"+measureCongPart2+"="+expressionCongPart2+
						"# what is the measure of #"+congPart2+"?";	
				}else{
					questionText+="# If #"+measureCongPart1+"="+expressionCongPart1+
							",# and #"+measureCongPart2+"="+expressionCongPart2+
							"# what is the value of #"+variable+"?";
				}
			}
			answerWork.newLine(expressionCongPart1+"", 
					expressionCongPart2+"",
					"Substitution.");
			if(linear){
				answerWork.addLinearEquationSteps(expressionCongPart1,expressionCongPart2, variable+"",xValue);
			}else{
//				answerWork.addQuadraticEquationSteps(expressionCongPart1,expressionCongPart2, variable+"",xValue);
			}
			if(difficulty==4){
				if(linear){
				int m = 1;
				int b = 0;
				if(expressionCongPart2.getTermsOfExpression()[0].getType().equals(Term.VARIABLE_TYPE)){
					m=expressionCongPart2.getTermsOfExpression()[0].getCoefficient();
					b=expressionCongPart2.getTermsOfExpression()[1].getCoefficient();
				}else if(expressionCongPart2.getTermsOfExpression().length==1){
					b=expressionCongPart2.getTermsOfExpression()[0].getCoefficient();
				}else{
					m=expressionCongPart2.getTermsOfExpression()[1].getCoefficient();
					b=expressionCongPart2.getTermsOfExpression()[0].getCoefficient();
				}
				if(m!=1){
					answerWork.newLine(measureCongPart2,
						m+"\\left("+xValue+"\\right)\\;"+answerWork.getLeadingOperation(b), 
						"Plug in the value of "+variable);
				}else if(expressionCongPart2.getTermsOfExpression().length==1){
					
				}else{
					answerWork.newLine(measureCongPart2,
							xValue+"\\;"+answerWork.getLeadingOperation(b), 
							"Plug in the value of "+variable);	
				}
				answerWork.newLine(measureCongPart2,""+actualMeasure, 
						"Final answer.");
				}else{//for quadratic equations
					answerWork.addQuadraticEquationSteps(expressionCongPart1, expressionCongPart2, ""+variable, actualMeasure);
					answerWork.plugIn(""+variable, xValue, expressionCongPart1);	
					answerWork.plugIn(""+variable, xValue, expressionCongPart2);	
				}
			}
		}
		
		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	
	protected void initializeExpressions(){
		linear =true;
		if(difficulty==1){
			expressionCongPart1=new LinearExpression(xValue, variable, actualMeasure, false).getExpression();
			expressionCongPart2=new LinearExpression(xValue, variable, actualMeasure, false).getExpression();
		}else if(difficulty==2){
			expressionCongPart1=new LinearExpression(xValue, variable, actualMeasure, false).getExpression();
			expressionCongPart2=new LinearExpression(xValue, variable, actualMeasure, true).getExpression();	
		}else if (difficulty==3){
			expressionCongPart1=new LinearExpression(xValue, variable, actualMeasure, true).getExpression();
			expressionCongPart2=new LinearExpression(xValue, variable, actualMeasure, true).getExpression();	
		}else{
			linear=false;
			expressionCongPart1=new QuadraticEquation(actualMeasure,variable,xValue,true,false).getLeftSideExpression();
			expressionCongPart2=new QuadraticEquation(actualMeasure, variable, xValue, true,false).getRightSideExpression();		
		}

	}
	
	protected String getCongruentPart2Measure() {
		// TODO Auto-generated method stub
		return null;
	}



	protected String getCongruentPart1Measure() {
		// TODO Auto-generated method stub
		return null;
	}



	protected String getCongruentPart1Name() {
		// TODO Auto-generated method stub
		return null;
	}



	protected String getCongruentPart2Name() {
		// TODO Auto-generated method stub
		return null;
	}



	protected String initiateString() {
		return null;
	}



	protected int getActualMeasure() {
		// TODO Auto-generated method stub
		return 0;
	}



	protected void drawDynamicImage(CoordinateImage image2) {
		//overwrite this method in each instance
	}

	
}
