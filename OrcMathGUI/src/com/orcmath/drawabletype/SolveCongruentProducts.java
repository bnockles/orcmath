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

import java.util.ArrayList;
import java.util.Arrays;

import com.orcmath.drawable.CoordinateImage;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.LinearExpression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.QuadraticEquation;
import com.orcmath.objects.Term;

public abstract class SolveCongruentProducts extends DynamicType{

	
	boolean part2IsAddedToPart1;
	String product1Part1;
	String product1Part2;
	String product2Part1;
	String product2Part2;
	boolean product1IsSquare;
	boolean product2IsSquare;
	boolean linear;
	int actualMeasurep1p1;
	int actualMeasurep1p2;
	int actualMeasurep2p1;
	int actualMeasurep2p2;
	Expression expressionProduct1Part1;
	Expression expressionProduct1Part2;
	Expression expressionProduct2Part1;
	Expression expressionProduct2Part2;
	int xValue;
	char variable;
	
	CoordinateImage image;
	
	protected void getInstance(){
		//step two, determine the type of question and the objective
		image = new CoordinateImage(600, 300, -20, 20, -10, 10);
		drawDynamicImage(image);
		//from the image, take point names that are used in the question text
		
		product1Part1=getProduct1Part1();
		product1Part2=getProduct1Part2();
		product2Part1=getProduct2Part1();
		product2Part2=getProduct2Part2();

		
		String questionText =initiateString();

		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();

		answerWork.newLine("\\left("+product1Part1+"\\right)"+"\\left("+product1Part2+"\\right)", 
				"\\left("+product2Part1+"\\right)"+"\\left("+product2Part2+"\\right)",
				keyTheorem,//theorem
				"textbf");
		ArrayList<Expression> leftSide= new ArrayList<Expression>();
		ArrayList<Expression> rightSide= new ArrayList<Expression>();
		//there is usually two types of questions, questions where part 2 is added to part 1, and questions where i
		if(part2IsAddedToPart1){
			leftSide.add(expressionProduct1Part1);
			rightSide.add(expressionProduct2Part1);
		}
		leftSide.add(expressionProduct1Part2);
		rightSide.add(expressionProduct2Part2);
		answerWork.newLine("\\left("+expressionProduct1Part1+"\\right)"+"\\left("+Format.combineExpressionsOperation(leftSide)+"\\right)", 
				"\\left("+expressionProduct2Part1+"\\right)"+"\\left("+Format.combineExpressionsOperation(rightSide)+"\\right)",
				"Substitution");
		Term[] left=expressionProduct1Part2.getTermsOfExpression();
		Term[] right=expressionProduct2Part2.getTermsOfExpression();
		if(part2IsAddedToPart1){
		left = Ops.combineExpressions(expressionProduct1Part1, expressionProduct1Part2);
		right = Ops.combineExpressions(expressionProduct2Part1, expressionProduct2Part2);
			
		answerWork.newLine("\\left( "+expressionProduct1Part1+"\\right)\\left( "+Format.termArrayToString(left)+"\\right)", 
				"\\left( "+expressionProduct2Part1+"\\right)\\left( "+Format.termArrayToString(right)+"\\right)",
				"Combine like terms");
		}

		left = Ops.distribute(expressionProduct1Part1.getTermsOfExpression(), left);
		right = Ops.distribute(expressionProduct2Part1.getTermsOfExpression(), right);
		answerWork.newLine(Format.termArrayToString(left), 
				Format.termArrayToString(right),
				"Distribute");
		
			
		if(left.length==4 || right.length==4){
			left = Ops.combineLikeTerms(left);
			right = Ops.combineLikeTerms(right);//combine like terms, if the result after factoring is quadratic (four terms)
			answerWork.newLine(Format.termArrayToString(left), Format.termArrayToString(right),"Combine like terms");
		}
		
		
		
		
		if(Ops.getDegreeOfTermArray(left)<2 && Ops.getDegreeOfTermArray(right)<2) answerWork.addLinearEquationSteps(new Expression(left), new Expression(right), ""+variable, xValue);
		else answerWork.addQuadraticEquationSteps(new Expression(left), new Expression(right), ""+variable, xValue);
		
		//variations
		
			questionText+="# If #"+product1Part1+"="+expressionProduct1Part1+",# ";
			if(!product1IsSquare){
				questionText+="#"+product1Part2+"="+expressionProduct1Part2+",# ";
			}
			if(product2IsSquare){
				questionText+="and #"+product2Part1+"="+expressionProduct2Part1+",#";
			}else{
				questionText+="#"+product2Part1+"="+expressionProduct2Part1+",# and #"+product2Part2+"="+expressionProduct2Part2+",#";
			}
	
			questionText+=" what is the value of #"+variable+"?";

		
		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	
	protected String initiateString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProduct1Part1() {
		return product1Part1;
	}

	public String getProduct1Part2() {
		return product1Part2;
	}

	public String getProduct2Part1() {
		return product2Part1;
	}

	public String getProduct2Part2() {
		return product2Part2;
	}

	protected abstract void drawDynamicImage(CoordinateImage image2);

	protected void initializeExpressions(){
		System.out.println("Creating an expression with xValue ="+xValue+
				" and actual values ="+actualMeasurep1p1+", "+actualMeasurep1p2+
				", "+actualMeasurep2p1+", "+actualMeasurep2p2+". ");
		if(difficulty==1){
			linear =true;
			boolean variableSet = false;
			expressionProduct1Part1=new Expression(new Term(actualMeasurep1p1));
			if(!product1IsSquare) {
				expressionProduct1Part2=new Expression(new Term(""+variable));
				variableSet=true;
			}
			else expressionProduct1Part2=new Expression(new Term(0));
			expressionProduct2Part1=new Expression(new Term(actualMeasurep2p1));
			if(!variableSet){
				expressionProduct2Part2=new Expression(new Term(""+variable));
			}else expressionProduct2Part2=new Expression(new Term(actualMeasurep2p2));
		}else if(difficulty==2){
			linear =true;
			expressionProduct1Part1=new Expression(new Term(actualMeasurep1p1));
			if(actualMeasurep1p2!=0){
				expressionProduct1Part2=new LinearExpression(xValue, variable, actualMeasurep1p2, true).getExpression();
			}else{
				expressionProduct1Part2=new Expression(new Term(0));
			}
			expressionProduct2Part1=new Expression(new Term(actualMeasurep2p1));
			if(actualMeasurep2p2!=0){
				expressionProduct2Part2=new LinearExpression(xValue, variable, actualMeasurep2p2, true).getExpression();
			}else{
				expressionProduct2Part2=new Expression(new Term(0));
			}
		}else if (difficulty==3){
			linear =true;
			if(Math.random()>.5){
				expressionProduct1Part1=new Expression(new Term(actualMeasurep1p1));
				expressionProduct1Part2=new LinearExpression(xValue, variable, actualMeasurep1p2, true).getExpression();
				expressionProduct2Part1=new Expression(new Term(actualMeasurep2p1));
				expressionProduct2Part2=new LinearExpression(xValue, variable, actualMeasurep2p2, true).getExpression();	
			}else{
				expressionProduct1Part1=new LinearExpression(xValue, variable, actualMeasurep1p1, true).getExpression();
				expressionProduct1Part2=new Expression(new Term(actualMeasurep1p2));
				expressionProduct2Part1=new Expression(new Term(actualMeasurep2p1));
				expressionProduct2Part2=new Expression(new Term(actualMeasurep2p2));
			}
		}else{
			linear=false;
			if(Math.random()>.5){
			expressionProduct1Part1=new LinearExpression(xValue, variable, actualMeasurep1p1, true).getExpression();
			expressionProduct1Part2=new LinearExpression(xValue, variable, actualMeasurep1p2, true).getExpression();
			expressionProduct2Part1=new Expression(new Term(actualMeasurep2p1));
			expressionProduct2Part2=new Expression(new Term(actualMeasurep2p2));
			}else{
				expressionProduct1Part1=new LinearExpression(xValue, variable, actualMeasurep1p1, true).getExpression();
				expressionProduct1Part2=new LinearExpression(xValue, variable, actualMeasurep1p2, false).getExpression();
				expressionProduct2Part1=new LinearExpression(xValue, variable, actualMeasurep2p1, true).getExpression();
				expressionProduct2Part2=new Expression(new Term(actualMeasurep2p2));
			}
		}

	}
	


	
}
