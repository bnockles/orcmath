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

public class ChordDistanceFromCenter extends DynamicType{
	
	char[] labels;	
	Circle circle;
	CoordinatePoint chordIntersect;
	CoordinatePoint centerToCircleThroughChordPoint;
	CoordinatePoint chordEndpoint;
	CoordinatePoint chordEndpoint2;
	int xValue;
	char variable;
	int actualDistanceToChord;
	Expression radiusExpression;
	Expression halfChordExpression;
	Expression chordExpression;
	Expression centerToChord;

	
	public ChordDistanceFromCenter(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.5;
	}
	
	
	public ChordDistanceFromCenter(int diff) {
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
			keyTheorem="A diameter or radius is perpendicular to a chord if and only if it bisects the chord.";
			falseTheorem1="A diameter or radius is perpendicular to a chord if and only if it shares an endpoint with the chord.";
			falseTheorem2="The length of a chord is always equal to the distance from the center of the circle to the chord.";
			falseTheorem3="The length of a chord is always equal to the length of the radius intersecting the chord."; 
		}
	
	public String getCenterName(){
		return ""+labels[0];
	}
	
	public String getRadiusThroughChordName(){
		return "\\overline{"+labels[0]+labels[3]+"}";
	}
	
	public String getChordIntersectionName(){
		return ""+labels[4];
	}
	
	public String getAName(){
		return "\\overline{"+labels[0]+labels[4]+"}";
	}
	
	public String getAMeasure(){
		return ""+labels[0]+labels[4];
	}

	public String getBName(){
		return "\\overline{"+labels[1]+labels[4]+"}";
	}
	
	public String getOtherHalfChordName(){
		return "\\overline{"+labels[4]+labels[2]+"}";
	}
	
	public String getOtherHalfChordMeasure(){
		return ""+labels[4]+labels[2];
	}
	
	public String getBMeasure(){
		return ""+labels[1]+labels[4];
	}
	
	public String getCName(){
		return "\\overline{"+labels[0]+labels[1]+"}";
	}
	
	public String getCMeasure(){
		return ""+labels[0]+labels[1];
	}
	
	public String getChordName(){
		return "\\overline{"+getChordMeasure()+"}";
	}
	
	public String getChordMeasure(){
		return ""+labels[1]+labels[2];
	}

	
	protected void getInstance(){

//		CoordinateImage image = new CoordinateImage(700, 350, -12, 12, -6, 6);
		CoordinateImage image = new CoordinateImage(700, 350, -6, 6,CoordinateImage.Y_COORDINATE);
//		image.addGrid(1, 1);
//		image.addAxes(1, 1, true);
		drawDynamicImage(image);

		WorkTable answerWork = new WorkTable();
		answerWork.setPositiveSolutionsOnly(true);
		answerWork.addHorizontalLine();
		//initiate question string
		String questionText = "In the diagram below, #"+getChordName()+"# is a chord of circle #"+
					getCenterName()+"# and #";
		
		
		double theoremVersion = Math.random();
		if(theoremVersion<.5){	
			questionText+=getRadiusThroughChordName()+"\\perp"+getChordName()+
					".# If #"+getChordMeasure()+"="+chordExpression+"# and #"+getCMeasure()+
					"="+radiusExpression+",# find #"+getAMeasure()+".";
			answerWork.setSymbol("\\cong");
			answerWork.newLine(getBName(), 
					getOtherHalfChordName(),
					keyTheorem,//theorem
					"textbf");
		}else{
			questionText+=getRadiusThroughChordName()+"# bisects #"+getChordName()+
					"# at point, #"+getChordIntersectionName()+".# If #"+getChordMeasure()+"="+chordExpression+"# and #"+getCMeasure()+
					"="+radiusExpression+",# find #"+getAMeasure()+".";	
			answerWork.setSymbol("\\perp");
			answerWork.newLine(getRadiusThroughChordName(), 
					getChordName(),
					keyTheorem,//theorem
					"textbf");
		}
			answerWork.setSymbol("=");
			answerWork.newLine(getBMeasure()+"+"+getOtherHalfChordMeasure(), 
					chordExpression.toString(),
					"Segment Addition Postulate");
			answerWork.newLine("2\\left("+getBMeasure()+"\\right)", 
					chordExpression.toString(),
					"Since #"+getChordName()+"# is bisected at point, #"+getChordIntersectionName()+",# we can substitute #"+getBMeasure()+
					"# with #"+getOtherHalfChordMeasure());

			answerWork.newLine("\\frac{2}{2}\\left("+getBMeasure()+"\\right)",  
					"\\frac{"+chordExpression+"}{2}",
					"Divide both sides by 2");
			answerWork.setSymbol("=");
			answerWork.newLine(getBMeasure(), 
					halfChordExpression.toString(),
					"Simplify.");
			answerWork.newLine(getAMeasure()+"^2+"+getBMeasure()+"^2", 
				getCMeasure()+"^2",
				"Since #"+getRadiusThroughChordName()+"\\perp"+getChordName()+",# the Pythagorean Theorem can be applied.");
			answerWork.newLine("\\left("+centerToChord+"\\right)^2+\\left("+halfChordExpression+"\\right)^2", 
					"\\left("+radiusExpression+"\\right)^2",
					"Substitution.");
			answerWork.addPythagoreanTheoremEquationSteps(centerToChord,halfChordExpression,radiusExpression, ""+variable, actualDistanceToChord);
		
		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}

	private void drawDynamicImage(CoordinateImage image) {
		labels = Variable.randCapVars(5);
		int[] lengths = new PythagoreanTriple(3).getSelectedExample();	
		//this while loop makes a new selection whenever the ratio is such that the triangle would be
		//to thin
		while((double)lengths[0]/(double)lengths[1]<.3){
			lengths = new PythagoreanTriple(3).getSelectedExample();
		}
		
		centerToCircleThroughChordPoint = new CoordinatePoint(lengths[2], 0);
		circle = new Circle(centerToCircleThroughChordPoint);//the circle's radius is the hypotenuse of the right triangle
		//sometimes the distance from the center to the chord is the longer side of the right triangle, other times it is the shorter side
		int version = Ops.randomInt(0, 1);
		actualDistanceToChord = lengths[version];
		int distanceToTop = lengths[(version+1)%2];		
		chordIntersect=new CoordinatePoint(actualDistanceToChord, 0);
		chordEndpoint = new CoordinatePoint(actualDistanceToChord, distanceToTop);
		chordEndpoint2 = new CoordinatePoint(actualDistanceToChord, -distanceToTop);
		
		double scale = (double)6/circle.getRadius();
		double theta = Ops.randomDouble(0, 2*Math.PI);
		
		//dilate the points to fit in the graphic
		centerToCircleThroughChordPoint.dilate(scale, scale);
		chordIntersect.dilate(scale, scale);
		chordEndpoint.dilate(scale, scale);
		chordEndpoint2.dilate(scale, scale);
		//rotate the points to change up the monotony of the image
		centerToCircleThroughChordPoint.rotate(theta,false);
		chordIntersect.rotate(theta,false);
		chordEndpoint.rotate(theta,false);
		chordEndpoint2.rotate(theta,false);
		
		image.drawCircle(circle);
		image.drawPoint(circle.getCenter());
		image.drawPoint(chordIntersect);
		image.drawPoint(centerToCircleThroughChordPoint);
		image.drawPoint(chordEndpoint);
		image.drawPoint(chordEndpoint2);
		image.drawCircleLabel(labels[0]+"", new Circle(chordEndpoint,2),circle.getCenter());
		image.drawCircleLabel(labels[1]+"", circle, chordEndpoint);
		image.drawCircleLabel(labels[2]+"", circle, chordEndpoint2);
		image.drawCircleLabel(labels[3]+"", circle, centerToCircleThroughChordPoint);
		double centerIntersectionDistance = actualDistanceToChord;
		double intersectionEdgeDistance = lengths[2]-actualDistanceToChord;
		if(intersectionEdgeDistance>centerIntersectionDistance)	
			image.drawCircleLabel(labels[4]+"", circle, chordIntersect);
		else
			image.drawCircleLabel(labels[4]+"", new Circle(centerToCircleThroughChordPoint,2), chordIntersect);
		image.drawSegment(chordEndpoint, chordEndpoint2);
		image.drawSegment(circle.getCenter(), chordEndpoint);
		
		image.drawSegment(circle.getCenter(), centerToCircleThroughChordPoint);
	
		
		
		if(difficulty==1){
			xValue=actualDistanceToChord;
			variable=Variable.randVar();
			radiusExpression = new LinearExpression(xValue, variable, lengths[2], false).getExpression();
			halfChordExpression = new LinearExpression(xValue, variable, distanceToTop, false).getExpression();
			chordExpression = new LinearExpression(xValue, variable, 2*distanceToTop, false).getExpression();
			centerToChord = new LinearExpression(xValue, variable, xValue, true, false).getExpression();//this creates the expression, simply: "x"
		}else if(difficulty==2){//quadratic equations that cancel
			xValue=actualDistanceToChord;
			variable=Variable.randVar();
			radiusExpression = new LinearExpression(xValue, variable,  lengths[2], true, false).getExpression();
			halfChordExpression = new LinearExpression(xValue, variable, distanceToTop, false).getExpression();
			chordExpression = new LinearExpression(xValue, variable, 2*distanceToTop, false).getExpression();
			centerToChord = new LinearExpression(xValue, variable, xValue, true, false).getExpression();//this creates the expression, simply: "x"
		}else if(difficulty==3){//more difficult quadratic equations that cancel
			xValue=Ops.randomInt(1, 10);
			variable=Variable.randVar();
			radiusExpression = new LinearExpression(xValue, variable,  lengths[2], true, false).getExpression();
			halfChordExpression = new LinearExpression(xValue, variable, distanceToTop, false).getExpression();
			chordExpression = new LinearExpression(xValue, variable, 2*distanceToTop, false).getExpression();
			centerToChord = new LinearExpression(xValue, variable, actualDistanceToChord, true, false).getExpression();
		}else{//quadratic equations that do not cancel
			xValue=actualDistanceToChord;
			variable=Variable.randVar();
			radiusExpression = new LinearExpression(xValue, variable,  lengths[2], true, false).getExpression();
			halfChordExpression = new LinearExpression(xValue, variable, distanceToTop, true, false).getExpression();
			chordExpression = new Expression(Ops.distribute(new Term(2), halfChordExpression.getTermsOfExpression()));
			centerToChord = new LinearExpression(xValue, variable, xValue, true, false).getExpression();
		}
		image.drawAngleSideLatex(radiusExpression.toString(), centerToChord.toString(), chordEndpoint, circle.getCenter(), chordIntersect, true);
		
		dynamicImage=image.getImage();
	}
	


	
}