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
import java.awt.geom.AffineTransform;

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
import com.orcmath.objects.PythagoreanTriple;
import com.orcmath.objects.QuadrilateralGraphic;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class TangentPythagorean extends DynamicType{
	
	char[] labels;	
	Circle circle;
	CoordinatePoint pointOfTangency;
	CoordinatePoint exteriorPoint;
	CoordinatePoint intersection;
	int xValue;
	char variable;
	int actualLength;
	Expression radiusExpression;
	Expression tangentExpression;
	Expression lineExpression;
	Expression segmentExpression;

	
	public TangentPythagorean(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.5;
	}
	
	public TangentPythagorean(int difficulty){
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
		keyTheorem="A line is tangent to a circle iff the line is perpendicular at the endpoint of a radius.";
		falseTheorem1="If two segments are tangent to a circle from the same external point then the segments are perpendicular.";
		falseTheorem2="If two tangent segments intersect a circle, then they are congruent to the circle's radius";
		falseTheorem3="If two segments are tangent to a circle from the same external point then the segments are congruent."; 
	}
	
	public String getPointOfTangencyName(){
		return ""+labels[0];
	}
	
	public String getAName(){
		return "\\overline{"+labels[1]+labels[0]+"}";
	}
	
	public String getBName(){
		return "\\overline{"+labels[2]+labels[0]+"}";
	}
	
	public String getCName(){
		return "\\overline{"+labels[1]+labels[2]+"}";
	}
	
	public String getSecondRadiusName(){
		return "\\overline{"+labels[1]+labels[3]+"}";
	}
	
	public String getSegmentName(){
		return "\\overline{"+labels[2]+labels[3]+"}";
	}
	
	public String getAMeasure(){
		return ""+labels[1]+labels[0];
	}
	
	public String getBMeasure(){
		return ""+labels[2]+labels[0];
	}
	
	public String getCMeasure(){
		return ""+labels[1]+labels[2];
	}
	
	public String getSecondRadiusMeasure(){
		return ""+labels[1]+labels[3];
	}
	
	public String getSegmentMeasure(){
		return ""+labels[2]+labels[3];
	}
	
	protected void getInstance(){

		CoordinateImage image = new CoordinateImage(700, 350, -6, 6, CoordinateImage.Y_COORDINATE);
		drawDynamicImage(image);


		//initiate question string
		String questionText;
		if(difficulty==1){
			questionText = "In the diagram below, #"+getBName()+"# is tangent to the circle at point #"+
					getPointOfTangencyName()+"#. If #"+getAMeasure()+"="+radiusExpression+"#, #"+
					getBMeasure()+"="+tangentExpression+"# and #"+getCMeasure()+
					"="+lineExpression+",# what is the value of #"+variable+"?";
		}else if(difficulty==2){
			questionText = "In the diagram below, #"+getBName()+"# is tangent to the circle at point #"+
					getPointOfTangencyName()+"#. If #"+getBMeasure()+"="+tangentExpression+"# and #"+
					getSegmentMeasure()+"="+segmentExpression+",# what is the length of the radius of circle #"+labels[1]+"?";
		}else if(difficulty==3){
			questionText = "In the diagram below, #"+getBName()+"# is tangent to the circle at point #"+
					getPointOfTangencyName()+"#. If #"+getAMeasure()+"="+radiusExpression+"#, #"+
					getBMeasure()+"="+tangentExpression+"# and #"+
					getSegmentMeasure()+"="+segmentExpression+",# what is the length of the radius of circle #"+labels[1]+"?";
		}else {
			questionText = "In the diagram below, #"+getBName()+"# is tangent to the circle at point #"+
					getPointOfTangencyName()+"#. If #"+getAMeasure()+"="+radiusExpression+"#, #"+
					getBMeasure()+"="+tangentExpression+"# and #"+
					getSegmentMeasure()+"="+segmentExpression+",# what is the length of the radius of circle #"+labels[1]+"?";
		}


		WorkTable answerWork = new WorkTable();
		answerWork.setPositiveSolutionsOnly(true);
		answerWork.addHorizontalLine();

		answerWork.setSymbol("\\perp");
		answerWork.newLine(getAName(), 
				getBName(),
				keyTheorem,//theorem
				"textbf");
		answerWork.setSymbol("=");
		answerWork.newLine(getAMeasure()+"^2+"+getBMeasure()+"^2", 
				getCMeasure()+"^2",
				"Since #"+getAName()+"\\perp"+getBName()+"# the Pythagorean Theorem can be applied");
		if(difficulty>1){
			answerWork.addHorizontalLine();
			answerWork.newTextLine("The length of #"+getCName()+"# is not provided in the question, but we know #"+getSecondRadiusMeasure()+"+"+getSegmentMeasure()+
					"="+getCMeasure()+"# so we can say #"+radiusExpression+"+"+segmentExpression+"="+getCName());
			answerWork.addHorizontalLine();
		}
		answerWork.newLine("\\left("+radiusExpression+"\\right)^2+\\left("+tangentExpression+"\\right)^2", 
				"\\left("+lineExpression+"\\right)^2",
				"Substitution.");
		answerWork.addPythagoreanTheoremEquationSteps(radiusExpression,tangentExpression,lineExpression, ""+variable, actualLength);
		
		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}

	private void drawDynamicImage(CoordinateImage image) {
		labels = Variable.randCapVars(4);
		int[] lengths = new PythagoreanTriple(2).getSelectedExample();	
		for(int index = 0; index<lengths.length; index++){
			System.out.println(""+lengths[index]);
		}
		circle = new Circle(new CoordinatePoint(lengths[0], 0));//the circle's diameter is the shortest length of the right triangle
		pointOfTangency=new CoordinatePoint(0, -lengths[0]);
		exteriorPoint = new CoordinatePoint(lengths[1], -lengths[0]);
		double[] line=DrawableOps.getLinearEquation(circle.getCenter(), exteriorPoint);
		CoordinatePoint[] intersections= circle.getLineCircleSolution(line[0], line[1]);
		intersection =intersections[0];
		if(intersections[1].getxCoordinate()>intersections[0].getxCoordinate())intersection =intersections[1];
		
		double scale = (double)10/(double)lengths[1];
		double possibleScale = (double)5/circle.getRadius();
		if(possibleScale<scale)scale=possibleScale;

		circle.dilate(scale, scale);
		pointOfTangency.dilate(scale, scale);
		exteriorPoint.dilate(scale, scale);
		intersection.dilate(scale, scale);
		//50% change of reflection
		double reflect = Math.random();
		double rotate;
		if(reflect<.25){
			pointOfTangency.reflectOverX();
			exteriorPoint.reflectOverX();
			intersection.reflectOverX();
			rotate = Ops.randomDouble(-Math.PI/4, 0);
		}else if(reflect<.5){
			pointOfTangency.reflectOverY();
			exteriorPoint.reflectOverY();
			intersection.reflectOverY();
			rotate = Ops.randomDouble(-Math.PI/4, 0);
		}else if(reflect<.75){
			pointOfTangency.reflectOverY();
			pointOfTangency.reflectOverX();
			intersection.reflectOverY();
			intersection.reflectOverX();
			exteriorPoint.reflectOverY();
			exteriorPoint.reflectOverX();
			rotate = Ops.randomDouble(0, Math.PI/4);
		}else{
			rotate = Ops.randomDouble(0, Math.PI/4);
		}
		
		pointOfTangency.rotate(rotate, false);
		exteriorPoint.rotate(rotate, false);
		intersection.rotate(rotate, false);
		
		if(Math.abs(exteriorPoint.getyCoordinate())>6){
			pointOfTangency.rotate(-rotate, false);
			exteriorPoint.rotate(-rotate, false);
			intersection.rotate(-rotate, false);
		}
		
		image.drawCircle(circle);
		image.drawPoint(circle.getCenter());
		image.drawPoint(pointOfTangency);
		image.drawPoint(exteriorPoint);
		image.drawPoint(intersection);
		image.drawCircleLabel(labels[0]+"", circle, pointOfTangency);
		image.drawCircleLabel(labels[1]+"", new Circle(exteriorPoint,2),circle.getCenter());
		image.drawCircleLabel(labels[2]+"", circle, exteriorPoint);
		image.drawCircleLabel(labels[3]+"", new Circle(pointOfTangency,2), intersection);
		
		image.drawSegment(exteriorPoint, pointOfTangency);
		image.drawSegment(pointOfTangency, circle.getCenter());
		image.drawSegment(exteriorPoint, circle.getCenter());
	
		
		
		if(difficulty==1){
			xValue=lengths[0];
			variable=Variable.randVar();
			radiusExpression = new LinearExpression(xValue, variable, xValue, true,false).getExpression();
			tangentExpression = new LinearExpression(xValue, variable, lengths[1], false).getExpression();
			lineExpression = new LinearExpression(xValue, variable, lengths[2], false).getExpression();	
		}else if(difficulty==2){//quadratic equations that cancel
			xValue=lengths[0];
			variable=Variable.randVar();
			radiusExpression = new LinearExpression(xValue, variable, xValue, true,false).getExpression();
			tangentExpression = new LinearExpression(xValue, variable, lengths[1], false).getExpression();
			segmentExpression = new LinearExpression(xValue, variable, lengths[2]-lengths[0], false).getExpression();
			lineExpression=new Expression(Ops.combineExpressions(radiusExpression, segmentExpression));
			image.drawSegmentLatex(tangentExpression.toString(), pointOfTangency, exteriorPoint, true, true);
			image.drawSegmentLatex(segmentExpression.toString(), intersection, exteriorPoint, true, true);
		}else if(difficulty==3){//
			xValue=lengths[0];
			variable=Variable.randVar();
			radiusExpression = new LinearExpression(xValue, variable, xValue, true,false).getExpression();
			tangentExpression = new LinearExpression(xValue, variable, lengths[1], true,false).getExpression();
			segmentExpression = new LinearExpression(xValue, variable, lengths[2]-lengths[0], false).getExpression();
			lineExpression=new Expression(Ops.combineExpressions(radiusExpression, segmentExpression));
			image.drawSegmentLatex(tangentExpression.toString(), pointOfTangency, exteriorPoint, true, true);
			image.drawSegmentLatex(segmentExpression.toString(), intersection, exteriorPoint, true, true);
		}else{
			xValue=lengths[0];
			variable=Variable.randVar();
			radiusExpression = new LinearExpression(xValue, variable, xValue, true,false).getExpression();
			tangentExpression = new LinearExpression(xValue, variable, lengths[1], true,false).getExpression();
			segmentExpression = new LinearExpression(xValue, variable, lengths[2]-lengths[0], false).getExpression();
			lineExpression=new Expression(Ops.combineExpressions(radiusExpression, segmentExpression));	
		}
		
		dynamicImage=image.getImage();
	}
	


	
}