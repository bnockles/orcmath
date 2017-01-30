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

import java.awt.Color;

import com.orcmath.drawable.Circle;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.CoordinateSegment;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.LinearExpression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.QuadraticEquation;
import com.orcmath.objects.QuadrilateralGraphic;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class TangentLines extends DynamicType{
	
	char[] labels;	
	Circle circle;
	CoordinatePoint exteriorPoint;
	int xValue;
	char variable;
	int actualLength;
	Expression segment1Expression;
	Expression segment2Expression;

	
	public TangentLines(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.5;
	}
	
	public TangentLines(int difficulty){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.5;
		this.difficulty=difficulty;
		getInstance();
		//question and answer is determined in the getInstance constructor
	}
	
	private void initiateKeyTheorems(){
		keyTheorem="If two segments are tangent to a circle from the same external point then the segments are congruent.";
		falseTheorem1="If two segments are tangent to a circle from the same external point then the segments are perpendicular.";
		falseTheorem2="If two tangent segments intersect a circle, then they are congruent to the circle's radius";
		falseTheorem3="A segment tangent to a circle is perpendicular to the radius through the point of tangency."; 
	}
	
	protected void getInstance(){

		CoordinateImage image = new CoordinateImage(700, 350, -12, 12, -6, 6);
//		image.addGrid(1, 1);
//		image.addAxes(1, 1, true);
		drawDynamicImage(image);


		//initiate question string
		String questionText = "In the diagram below, #\\overline{"+labels[0]+labels[2]+"}# and #" +
				"\\overline{"+labels[1]+labels[2]+"}# are tangent to the circle at points #"
				+labels[0]+"# and #"+labels[1]+".#";


		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();

		answerWork.setSymbol("\\cong");
		answerWork.newLine("\\overline{"+labels[0]+labels[2]+"}", 
				"\\overline{"+labels[1]+labels[2]+"}",
				keyTheorem,//theorem
				"textbf");
		answerWork.setSymbol("=");
		answerWork.newLine(""+labels[0]+labels[2]+"", 
				""+labels[1]+labels[2]+"",
				"Definition of congruent");

		//variations
		if(difficulty==1){
			questionText+=" If #"+labels[0]+labels[2]+"="+segment1Expression+",# what is the length of #\\overline{"+labels[1]+labels[2]+"}?";
			answerWork.newLine(segment1Expression+"", 
					""+labels[1]+labels[2]+"",
					"Substitution.");
		}else{
			if(difficulty==2 || difficulty==3){
				questionText+=" If #"+labels[0]+labels[2]+"="+segment1Expression+
						",# and #"+labels[1]+labels[2]+"="+segment2Expression+
						"# what is the value of #"+variable+"?";
			}else{//difficulty 4
				questionText+=" If #"+labels[0]+labels[2]+"="+segment1Expression+
						",# and #"+labels[1]+labels[2]+"="+segment2Expression+
						"# what are all possible lengths of #"+labels[1]+labels[2]+"?";			
			}
			answerWork.newLine(segment1Expression+"", 
					segment2Expression+"",
					"Substitution.");
			if(difficulty<4){
				answerWork.addLinearEquationSteps(segment1Expression,segment2Expression, variable+"",xValue);
			}
			else{
				answerWork.addQuadraticEquationSteps(segment1Expression, segment2Expression, ""+variable, actualLength);
				answerWork.plugIn(""+variable, xValue, segment1Expression);	
				answerWork.plugIn(""+variable, xValue, segment2Expression);	
//				int m = 1;
//				int b = 0;
//				if(segment2Expression.getTermsOfExpression()[0].getType().equals(Term.VARIABLE_TYPE)){
//					m=segment2Expression.getTermsOfExpression()[0].getCoefficient();
//					b=segment2Expression.getTermsOfExpression()[1].getCoefficient();
//				}else if(segment2Expression.getTermsOfExpression().length==1){
//					b=segment2Expression.getTermsOfExpression()[0].getCoefficient();
//				}else{
//					m=segment2Expression.getTermsOfExpression()[1].getCoefficient();
//					b=segment2Expression.getTermsOfExpression()[0].getCoefficient();
//				}
//				if(m!=1){
//					answerWork.newLine(""+labels[1]+labels[2],
//						m+"\\left("+xValue+"\\right)\\;"+answerWork.getLeadingOperation(b), 
//						"Plug in the value of "+variable);
//				}else if(segment2Expression.getTermsOfExpression().length==1){
//					
//				}else{
//					answerWork.newLine(""+labels[1]+labels[2],
//							xValue+"\\;"+answerWork.getLeadingOperation(b), 
//							"Plug in the value of "+variable);	
//				}
				answerWork.newLine(""+labels[1]+labels[2],actualLength+"", 
						"Final answer.");
			}
		}
		
		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}

	private void drawDynamicImage(CoordinateImage image) {
		labels = Variable.randCapVars(3);
		circle = new Circle(new CoordinatePoint(Ops.randomInt(2, 5), 0));
		exteriorPoint = new CoordinatePoint(Ops.randomInt(-12, 12), Ops.randomInt(-6, 6));
		while(DrawableOps.getDistance(exteriorPoint, circle.getCenter())<circle.getRadius()+1){
			exteriorPoint = new CoordinatePoint(Ops.randomInt(-12, 12), Ops.randomInt(-6, 6));
		}
		CoordinatePoint outsideCircle = new CoordinatePoint((exteriorPoint.getxCoordinate()+circle.getCenter().getxCoordinate())/2, 
				(exteriorPoint.getyCoordinate()+circle.getCenter().getyCoordinate())/2);
		double distance = DrawableOps.getDistance(outsideCircle, circle.getCenter());
		Circle reference = new Circle(outsideCircle,distance);
		double[] coefficients= circle.getRadicalLineCoefficients(reference);
		CoordinatePoint[] soultions = circle.getLineCircleSolution(coefficients[0], coefficients[1]);
		CoordinatePoint p1 = soultions[0];
		CoordinatePoint p2 = soultions[1];
		
		image.drawCircle(circle);
		image.drawPoint(exteriorPoint);
		image.drawPoint(p1);
		image.drawPoint(p2);
		image.drawCircleLabel(labels[0]+"", circle, p1);
		image.drawCircleLabel(labels[1]+"", circle, p2);
		image.drawCircleLabel(labels[2]+"", circle, exteriorPoint);
		image.drawSegment(exteriorPoint, p1);
		image.drawSegment(exteriorPoint, p2);
	
		xValue=Ops.randomInt(1, 12);
		variable=Variable.randVar();
		actualLength = Ops.randomInt(4, 30);
		if(difficulty==4){
			actualLength = Ops.randomInt(4, 15);//without this restriction, the quadratic expressions can be insane
		}
		
		boolean segment1ContainsVariable=false;
		boolean segment2ContainsVariable=false;
		if(difficulty==2||difficulty==3){
			if(Math.random()<.5){
				segment1ContainsVariable=true;
				segment2ContainsVariable=false;
			}else{
				segment1ContainsVariable=false;
				segment2ContainsVariable=true;
			}
		}if(difficulty==4){
			segment1ContainsVariable=true;
			segment2ContainsVariable=true;
		}
		if(difficulty<4){
			segment1Expression = new LinearExpression(xValue, variable, actualLength, segment1ContainsVariable,false).getExpression();
			segment2Expression = new LinearExpression(xValue, variable, actualLength, segment2ContainsVariable,false).getExpression();		
		}else{
			segment1Expression=new QuadraticEquation(actualLength,variable,xValue,true).getLeftSideExpression();
			segment2Expression=new QuadraticEquation(actualLength, variable, xValue, true).getRightSideExpression();
		}
		if(segment1ContainsVariable&&segment2ContainsVariable){
			while((segment2Expression.getTermsOfExpression()[0].getCoefficient()==segment1Expression.getTermsOfExpression()[0].getCoefficient())){
				segment2Expression = new LinearExpression(xValue, variable, actualLength, segment2ContainsVariable).getExpression();
			}
		}
		if(difficulty==1){
			image.drawAngleSideLatex(segment1Expression.toString(),"", p1,exteriorPoint,p2, true);		
		}else if(difficulty==2){
			image.drawAngleSideLatex(segment1Expression.toString(),segment2Expression.toString(), p1,exteriorPoint,p2, true);		
		}
		
		dynamicImage=image.getImage();
	}
	


	
}