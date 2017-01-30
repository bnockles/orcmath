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

import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.Graph;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.QuadraticEquation;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;
import com.orcmath.type.Type;

public class QuadLinSystem extends DynamicType{

	int degree;
	private String[] variationsOnInstructions= {"Which values of x are in the solution set of the following system of equations?","What is the total number of points of intersection in the graphs of the following equations?","Solve the following system of equations algebraically.", "Identify the ordered pairs that satisfy the following system of equations."};
	private String[] variationsOnOrderedPairInstructions= {"What is the solution of the following system of equations?","Solve the following system of equations algebraically.", "Identify the ordered pairs that satisfy the following system of equations."};
	boolean graphically;
	
	public QuadLinSystem(){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=200;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		degree = 2;
	}
	
	public QuadLinSystem(int difficulty, boolean writtenAsSeparateFunctions, boolean orderedPairSolution, boolean checkwork){
		if(writtenAsSeparateFunctions){
			instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
			if(orderedPairSolution)instructions = variationsOnOrderedPairInstructions[Ops.randomInt(0, (variationsOnOrderedPairInstructions.length)-1)];
		}
		else instructions = "Solve for x";
		numberOfColumns=2;
		verticalSpacing=200;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		degree = 2;
		graphically=false;
		getInstance(difficulty, writtenAsSeparateFunctions, orderedPairSolution, checkwork);
	}
	
	public QuadLinSystem(int difficulty){
		instructions = variationsOnOrderedPairInstructions[Ops.randomInt(0, (variationsOnOrderedPairInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=15;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		degree = 2;
		graphically=true;
		getInstance(difficulty, true, true, false);
	}
	

	public void getInstance(int diff, boolean writtenAsSeparateFunctions, boolean orderedPairSolution, boolean checkwork){
		char variable =Variable.randVar();
		int actualMeasure=Ops.randomInt(-50, 50);
		int xValue=Ops.randomNotZero(-10, 10);
		QuadraticEquation qe=new QuadraticEquation(actualMeasure,variable,xValue,true,true);
		Expression expressionCongPart1=qe.getLeftSideExpression();
		Expression expressionCongPart2=qe.getRightSideExpression();
		
		String text = "#"+expressionCongPart1+"="+expressionCongPart2+"#";
		question = Problem.getLatexLines(text, "#", QUESTION_WIDTH, "text");
		
		WorkTable answerWork = new WorkTable();
		answerWork.addQuadraticEquationSteps(expressionCongPart1, expressionCongPart2, ""+variable, actualMeasure);
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
//		
		
		//NOTE: ONCE, WHILE IN A PINCH, I REPLACED ALL THE OLD CODE WITH NEW CODE
		//APPARENTLY THERE ARE SOME NEAT OPTIONS IN THE OLD CODE, INCLUDING SOLVING GRAPHICALLY AND 
		//CHOSING WHETHER OR NOT TO INCLUDE AN ANSWER CHECK
		
		//		int root1 = Ops.randomInt(-8, 8);
//		int root2 = Ops.randomInt(-8, 8);
//		int slopeDenominator = 1;//the slope denominator: when x is plugged into the linear equation, interger results are desired
//		if(diff==3){
//			slopeDenominator=Math.abs(Ops.randomNotZero(-1, 3));
//		}else if(diff>3){
//			slopeDenominator=Ops.randomInt(2, 5);
//		}
//		//makes sure roots are divisible by slopeDenominator
//		if(root1%slopeDenominator!=0){
//			root1=root1*slopeDenominator;
//		}if(root2%slopeDenominator!=0){
//			root2=root2*slopeDenominator;
//		}
//		
//		int a = 1;
//		int b = -(root1+root2);
//		int c = root1*root2;
//		int scalar=1;
//		if(diff>=2){
//			scalar = Ops.randomInt(1, 4);
//			a=scalar*a;
//			b=scalar*b;
//			c=scalar*c;
//		}
//		if((diff==3 ||diff==4)&&!graphically){
//			a = Ops.randomInt(1, diff*2);
//			b = Ops.randomInt(-diff*2, diff*2);
//			c = Ops.randomInt(-diff*2, diff*2);
//			if(c == 0) c = Ops.randomInt(-diff*2, diff*2);
//		}
//		
//		//set up equation for line
//		int m =Ops.randomInt(-3, 3);
//		int yint = Ops.randomInt(-5, 5);
//		if(diff==2){
//			m = Ops.randomNotZero(-5, 5);
//			yint = Ops.randomNotZero(-10, 10);
//		}else if(diff==3 ||diff==4){
//			m = Ops.randomInt(-diff*2, diff*2);
//			yint = Ops.randomInt(-diff*2, diff*2);
//		}
//
//		int sfA=-m;
//		int sfB=slopeDenominator;
//		int sfC=slopeDenominator*yint;
//		if(sfA<0){
//			sfA=-sfA;
//			sfB=-sfB;
//			sfC=-sfC;
//		}
//		
//		//make a quadratic before set to zero
//		Term[] quadratic;
//		if (slopeDenominator*b+m!=0) {
//			quadratic= new Term[3];
//			quadratic[0] = new Term(a, "x^2");
//			if(slopeDenominator==1){				
//				quadratic[1] = new Term(b+m, "x");
//			}else{
//				quadratic[1] = new Term(new Fraction(slopeDenominator*b+m, slopeDenominator), "x");
//			}
//			quadratic[2] = new Term(c+yint);
//		}
//		else {
//			quadratic= new Term[2];
//			quadratic[0] = new Term(a, "x^2");
//			quadratic[1] = new Term(c+yint);
//		}
//		Term[] linear;
//		if(m!=0&&yint!=0){
//			linear=new Term[2];
//			if(slopeDenominator==1){
//				linear[0]=new Term(m,"x");
//				linear[1]=new Term(yint);
//			}else{
//				linear[0]=new Term(new Fraction(m, slopeDenominator),"x");
//				linear[1]=new Term(yint);
//			}
//		}else{
//			linear=new Term[1];
//			if(m==0){
//				linear[0]=new Term(yint);
//			}else {
//				if(slopeDenominator==1){
//					linear[0]=new Term(m,"x");
//				}else{
//					linear[0]=new Term(new Fraction(m, slopeDenominator),"x");
//				}
//			}
//		}
//		
//		//make a quadratic before set to zero
//		Term[] solvingQuadratic;
//		Term[] solvingQuadraticFactoredGCD;
//		if (slopeDenominator*b+m!=0) {
//			solvingQuadratic= new Term[3];
//			solvingQuadratic[0] = new Term(a, "x^2");
//			solvingQuadratic[1] = new Term(b, "x");
//			solvingQuadratic[2] = new Term(c);
//			solvingQuadraticFactoredGCD= new Term[3];
//			solvingQuadraticFactoredGCD[0] = new Term(a/scalar, "x^2");
//			solvingQuadraticFactoredGCD[1] = new Term(b/scalar, "x");
//			solvingQuadraticFactoredGCD[2] = new Term(c/scalar);
//		}
//		else {
//			solvingQuadratic= new Term[2];
//			solvingQuadratic[0] = new Term(a, "x^2");
//			solvingQuadratic[1] = new Term(c);
//			solvingQuadraticFactoredGCD= new Term[2];
//			solvingQuadraticFactoredGCD[0] = new Term(a/scalar, "x^2");
//			solvingQuadraticFactoredGCD[1] = new Term(c/scalar);
//		}
//		
//		WorkTable answerWork = new WorkTable();
//		
//		answerWork.setCheckWork(checkwork);
//		if(writtenAsSeparateFunctions){
//			String latexTabular="\\begin{tabular}{rcl}";
//			latexTabular+="y & = & "+Format.termArrayToString(quadratic)+"\\\\";
//			if(diff==1){//quadratic is in standard form, linear in slope-intercept form
//				latexTabular+="y & = & "+Format.termArrayToString(linear)+"\\\\";
//			}else{//linea is in standard form
//				Term[] standardForm = {new Term(sfA,"x"),new Term(sfB,"y")};
//				latexTabular+=Format.termArrayToString(standardForm)+"& = & "+sfC+"\\\\";
//				if(!graphically){
//					answerWork.newLine(standardForm[1].toString(), Ops.multiplyTerms(new Term(-1), standardForm[0]).toString()+Format.getStringWithSign(sfC), "Subtract "+standardForm[0].toString()+" from both sides.");
//					if(standardForm[1].getCoefficient()!=1){
//						answerWork.newLine("y", Format.termArrayToString(linear), "Divide both sides by "+standardForm[1].getCoefficient());
//					}
//				}
//			}
//			latexTabular+="\\\\\\end{tabular}";
//			question=latexTabular;
//			if(graphically){
//				Graph graph = new Graph();
//				double[] coefs = {a,b+m,c+yint};
//				graph.setFunction(Graph.QUADRATIC, coefs);
//				Graph lingraph = new Graph();
//				double[] lincoefs = {m,yint};
//				lingraph.setFunction(Graph.LINEAR, lincoefs);
//				
//				//calculates solution
//				int x_1=(int) (-(b)-Math.sqrt(Math.pow(b, 2)-4*a*(c)))/(2*a);
//				int x_2=(int) (-(b)+Math.sqrt(Math.pow(b, 2)-4*a*(c)))/(2*a);
//				int y_1=m*x_1+yint;
//				int y_2=m*x_2+yint;
//				
//				int windowLeft = -10;
//				if(x_1<windowLeft)windowLeft=(int)((x_1-5)/5)*5;
//				int windowRight = 10;
//				if(x_2>windowRight)windowRight=(int)((x_2+5)/5)*5;
//				int windowDown = -10;
//				if(y_1<windowDown)windowDown=(int)((y_1-5)/5)*5;
//				if(y_2<windowDown)windowDown=(int)((y_2-5)/5)*5;
//				int windowUp = 10;
//				if(y_2>windowUp)windowUp=(int)((y_2+5)/5)*5;
//				if(y_1>windowUp)windowUp=(int)((y_1+5)/5)*5;
//				
//				graph.setLowBound(true, windowLeft, true);
//				graph.setHighBound(true, windowRight, true);
//				lingraph.setLowBound(true, windowLeft, true);
//				lingraph.setHighBound(true, windowRight, true);
//
//				CoordinateImage image= new CoordinateImage(550, 550, windowLeft, windowRight, windowDown,windowUp);
//				//			image = new CoordinateImage(550, 550, -max, max, -max, max);
//				int xScale=(int)(windowRight/10);
//				if(xScale<(int)(Math.abs(windowLeft/10)))xScale=Math.abs((int)(windowLeft/10));
//				int yScale=(int)(windowUp/10);
//				if(yScale<(int)(Math.abs(windowDown/10)))yScale=Math.abs((int)(windowDown/10)+1);
//				image.addAxes(xScale, yScale, true);
//				image.addGrid(xScale, yScale);
//				dynamicImage=image.getImage();
//				ArrayList<Graph> graphs = new ArrayList<Graph>();
//				graphs.add(graph);
//				graphs.add(lingraph);
//
//				answerWork.setExplanationWidth(1000);
//				answerWork.addGraphs(graphs, windowLeft, windowRight, windowDown,windowUp, answerImages);
//				answerWork.newLine("("+x_1+","+y_1+")","("+x_2+","+y_2+")" ,"Solutions.");
//				answerWork.addHorizontalLine();
//			}else{
//				answerWork.newLine(Format.termArrayToString(quadratic),Format.termArrayToString(linear) ,"Set equations equal to each other.");
//			}
//			
//		}else{
//			question = Format.termArrayToString(quadratic)+"="+Format.termArrayToString(linear);		
//		}
//		
//		if(!graphically){
//			answerWork.newLine(Format.termArrayToString(solvingQuadratic), "0","Get zero on one side.");
//			if(scalar!=1){
//				answerWork.newLine(Format.termArrayToString(solvingQuadraticFactoredGCD), "0","Divide both sides by "+scalar);	
//			}
//			//		System.out.println("QuadLinSystem: factored quadratic has this many terms:"+solvingQuadraticFactoredGCD.length);
//			answerWork.setLeftExpression(new Expression(linear));
//			answerWork.setRightExpression(new Expression(linear));
//			answerWork.addSolvingForQuadraticRootsSteps(solvingQuadraticFactoredGCD, orderedPairSolution);
//			answerWork.addHorizontalLine();
//		}
//		answerWork.finish();//cannot omit this or there will be an error!
//		answer=answerWork.getLatexTabular();
	}
	
}
