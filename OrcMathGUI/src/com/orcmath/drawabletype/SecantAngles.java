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

import com.orcmath.drawable.Circle;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.CoordinateSegment;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.LinearExpression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class SecantAngles extends DynamicType{
	

	
	//fields accessed by methods
	
	char[] labels;
	CoordinateImage image;
	Circle circle;
	CoordinatePoint p1;
	CoordinatePoint p2;
	CoordinatePoint p3;
	CoordinatePoint p4;
	CoordinatePoint p5;
	double largeArcLabelLocation;
	double smallArcLabelLocation;
	Expression largeArcExpression;
	Expression smallArcExpression;
	Expression angleExpression;
	int largeArcMeasure;
	int smallArcMeasure;
	int actualMeasure;
	String findThis;
	double theUnknown;
	private int xValue;
	private char variable;
	

	public SecantAngles(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
	}
	
	public SecantAngles(int difficulty){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;
		getInstance();
	}
	
	private void initiateKeyTheorems(){
		keyTheorem="The measure of an angle formed by two lines that intersect outside a circle is half the difference of the measures of the intercepted arcs.";
		falseTheorem1="The measure of an angle formed by two intersecting chords is equal to half the sum of the intercepted arcs.";
		falseTheorem2="The measure of an angle formed by two lines that intersect outside a circle is half the sum of the measures of the intercepted arcs.";
		falseTheorem3="The measure of an angle formed by two lines that intersect outside a circle is half the measures of the intercepted arc.";	
	}
	
	protected void getInstance(){
		image = new CoordinateImage(750, 300, -10, 10, CoordinateImage.Y_COORDINATE);
		drawDynamicImage(image);


		//initiate question string
		String input="In the diagram below, #"+labels[0]+",# #"+labels[1]+",# #"+labels[2]+",# and #"+labels[3]+"# " +
				"are points on the circle. If #m\\widehat{ "+labels[1]+labels[2]+"}=" +largeArcExpression+",# #m\\widehat{ "+labels[0]+labels[3]+"}=" +smallArcExpression +
				",# and #m\\angle "+labels[0]+labels[4]+labels[3]+"="+angleExpression+"#, find #"+findThis+".";
		question = Problem.getLatexLines(input, "#", QUESTION_WIDTH, "text");
		
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		answerWork.newLine("m\\angle "+labels[0]+labels[4]+labels[3], "\\frac{m\\widehat{ "+labels[1]+labels[2]+"}-m\\widehat{"+labels[0]+labels[3]+"}}{2}", 
				keyTheorem,"textbf");
		answerWork.newLine(angleExpression.toString(), "\\frac{"+largeArcExpression+"-"+smallArcExpression+"}{2}", 
				"Substitute.");

		smallArcExpression=new Expression(Ops.distribute(new Term(-1), smallArcExpression.getTermsOfExpression()));
		answerWork.newLine(angleExpression.toString(), "\\frac{"+Format.termArrayToString(Ops.combineExpressions(largeArcExpression,smallArcExpression))+"}{2}", 
					"Subtract.");
		if(difficulty==1){
			answerWork.newLine("m\\angle "+labels[0]+labels[4]+labels[3], actualMeasure+"", 
					"Final answer");
		}else{
			answerWork.newLine("2\\left("+angleExpression.toString()+"\\right)", Format.termArrayToString(Ops.combineExpressions(largeArcExpression,smallArcExpression)), 
					"Multiply both sides by 2.");		
			Term[] combinedRight = Ops.combineExpressions(largeArcExpression,smallArcExpression);

			Expression leftResult = new Expression(Ops.distribute(new Term(2), angleExpression.getTermsOfExpression()));
			Expression rightResult = new Expression(combinedRight);
			answerWork.newLine(leftResult.toString(), rightResult.toString(), 
					"Distribute");
			answerWork.addLinearEquationSteps(leftResult,rightResult, variable+"",xValue);
			if(difficulty==4){
				if(theUnknown<.33) answerWork.plugIn(variable+"", xValue, angleExpression, "m\\angle "+labels[0]+labels[4]+labels[3]);
				else if(theUnknown<.67)answerWork.plugIn(variable+"", xValue, largeArcExpression, "m\\widehat{"+labels[1]+labels[2]+"}");
				else answerWork.plugIn(variable+"", xValue, smallArcExpression, "m\\widehat{"+labels[0]+labels[3]+"}");
			}
		}
		
		answerWork.finish();
		answer=answerWork.getLatexTabular();
	}
	

	public void drawDynamicImage(CoordinateImage image){
		//names of points
		labels = Variable.randCapVars(5);
		circle = new Circle(new CoordinatePoint(7, 0));
		int split1 = Ops.randomInt(5, 45);
		int split2 = 180-Ops.randomInt(split1+10,80);
		int split4 = Ops.randomInt(315,355);
		int referenceAngle = 360-split4;
		int split3 = 180+Ops.randomInt(referenceAngle+5,80);
		p1 = circle.getPointOnCircle(split1, true);
		p2 = circle.getPointOnCircle(split2, true);
		p3 = circle.getPointOnCircle(split3, true);
		p4 = circle.getPointOnCircle(split4, true);
		double[] eq1 = DrawableOps.getLinearEquation(p1, p2);
		double[] eq2 = DrawableOps.getLinearEquation(p3, p4);
		p5 = DrawableOps.getIntersectionOfLines(eq1, eq2);
		
		double theta = Ops.randomDouble(0, 2*Math.PI);
		p1.rotate(theta, false);
		p2.rotate(theta, false);
		p3.rotate(theta, false);
		p4.rotate(theta, false);
		p5.rotate(theta, false);
		largeArcLabelLocation=((split2+split3)/2+theta*180/Math.PI)%360;
		smallArcLabelLocation=(((split1+split4)%360)/2+theta*180/Math.PI)%360;
		if(Math.abs(p5.getxCoordinate())>image.getxMax() || 
				Math.abs(p5.getyCoordinate())>image.getyMax()){
			double scale = Math.abs(image.getxMax()/p5.getxCoordinate());
			if(Math.abs(image.getyMax()/p5.getyCoordinate())<scale) scale = Math.abs(image.getyMax()/p5.getyCoordinate());
			circle.dilate(scale, scale);
			p1.dilate(scale, scale);
			p2.dilate(scale, scale);
			p3.dilate(scale, scale);
			p4.dilate(scale, scale);
			p5.dilate(scale, scale);
		}

		image.drawCircle(circle);
		image.drawPoint(p1);
		image.drawPoint(p2);
		image.drawPoint(p3);
		image.drawPoint(p4);
		image.drawPoint(p5);
		image.drawSegment(p2, p5);
		image.drawSegment(p3, p5);	
		image.drawCircleLabel(""+labels[0], new Circle(p3,1), p1);
		image.drawCircleLabel(""+labels[1], circle, p2);
		image.drawCircleLabel(""+labels[2], circle, p3);
		image.drawCircleLabel(""+labels[3], new Circle(p2,1), p4);
		image.drawCircleLabel(""+labels[4], circle, p5);
		
		largeArcMeasure=split3-split2;
		smallArcMeasure=split1+360-split4;
		if((largeArcMeasure-smallArcMeasure)%2==1)smallArcMeasure++;
		actualMeasure=(largeArcMeasure-smallArcMeasure)/2;
		xValue=Ops.randomNotZero(1, 12);
		variable = Variable.randVar();
		initializeExpressions();
		dynamicImage=image.getImage();
	}

	private void initializeExpressions() {
		theUnknown =Math.random();
		if(difficulty==2 || difficulty==3){
			findThis="#the value of #"+variable;
		}else if(theUnknown<.33 || difficulty==1){//the exterior angle is unknown
			findThis="m\\angle "+labels[0]+labels[4]+labels[3];
		}else if(theUnknown<.67){//the larger arc is unknown
			findThis="m\\widehat{"+labels[1]+labels[2]+"}";
		}else{//the smaller arc is unknwon
			findThis="m\\widehat{"+labels[0]+labels[3]+"}";
		}
		if(difficulty==1){
			largeArcExpression = new LinearExpression(xValue, variable, largeArcMeasure, false).getExpression();
			smallArcExpression = new LinearExpression(xValue, variable, smallArcMeasure, false).getExpression();
			Term[] xTerm = {new Term(1,"x")};
			angleExpression = new Expression(xTerm);
		}else if(difficulty==2){
			largeArcExpression = new LinearExpression(xValue, variable, largeArcMeasure, true).getExpression();
			smallArcExpression = new LinearExpression(xValue, variable, smallArcMeasure, true).getExpression();
			angleExpression = new LinearExpression(xValue, variable, actualMeasure, false).getExpression();
		}else if(difficulty==3){
			largeArcExpression = new LinearExpression(xValue, variable, largeArcMeasure, true).getExpression();
			smallArcExpression = new LinearExpression(xValue, variable, smallArcMeasure, true).getExpression();
			angleExpression = new LinearExpression(xValue, variable, actualMeasure, true).getExpression();
		}else{
			largeArcExpression = new LinearExpression(xValue, variable, largeArcMeasure, true).getExpression();
			smallArcExpression = new LinearExpression(xValue, variable, smallArcMeasure, true).getExpression();
			angleExpression = new LinearExpression(xValue, variable, actualMeasure, true).getExpression();
		}
		
	}
	
}