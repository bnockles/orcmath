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
import com.orcmath.objects.Ops;
import com.orcmath.objects.Variable;

public class CentralAngles extends SolvingCongruency{
	
	//fields accessed by methods
	
	char[] labels;
	CoordinateImage image;
	Circle circle;
	CoordinatePoint p1;
	CoordinatePoint p2;
	CoordinatePoint pp1;
	CoordinatePoint pp2;
	int randomAngle1;
	int randomAngle2;

	int questionType;//in this class, we can identify congruent chords, arcs or angles
	static int findChordValue=0;
	static int findArcValue=1;
	static int findAngleValue=2;
	
	//static fields
	public static int MINIMUM_ARC=30;
	public static ArrayList<String> variations = new ArrayList<String>(Arrays.asList("Congruent Central Angles"));
	
	public CentralAngles(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
	}
	
	public CentralAngles(int difficulty){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;
		getInstance();
	}
	
	private void initiateKeyTheorems(){
		questionType=Ops.randomInt(0, 2);
		if(questionType==findChordValue){
			keyTheorem="Congruent central angles have congruent chords";
			falseTheorem1="Congruent chords have congruent arcs";
			falseTheorem2="Congruent arcs have congruent central angles";
			falseTheorem3="Congruent arcs are formed by congruent radii";
		}else if(questionType==findArcValue){
			keyTheorem="Congruent chords have congruent arcs";
			falseTheorem1="Congruent central angles have congruent chords";
			falseTheorem2="Congruent arcs have congruent central angles";
			falseTheorem3="Congruent arcs are formed by congruent radii";
		}else{//angle value
			keyTheorem="Congruent arcs have congruent central angles";
			falseTheorem1="Congruent chords have congruent arcs";
			falseTheorem2="Congruent central angles have congruent chords";
			falseTheorem3="Congruent arcs are formed by congruent radii";
		}
	}
	
	protected String getCongruentPart1Measure() {
		if(questionType==findChordValue){
			return ""+labels[0]+labels[2];
		}else if(questionType==findArcValue){
			return "m\\widehat{"+labels[0]+labels[2]+"}";
		}else{//angle value
			return "m\\angle "+labels[0]+labels[4]+labels[2];
		}
	}

	protected String getCongruentPart2Measure() {
		if(questionType==findChordValue){
			return ""+labels[1]+labels[3];
		}else if(questionType==findArcValue){
			return "m\\widehat{"+labels[1]+labels[3]+"}";
		}else{//angle value
			return "m\\angle "+labels[1]+labels[4]+labels[3];
		}
	}

	protected String getCongruentPart1Name() {
		if(questionType==findChordValue){
			return "\\overline{"+labels[0]+labels[2]+"}";
		}else if(questionType==findArcValue){
			return "\\widehat{"+labels[0]+labels[2]+"}";
		}else{//angle value
			return "\\angle "+labels[0]+labels[4]+labels[2];
		}
	}

	protected String getCongruentPart2Name() {
		if(questionType==findChordValue){
			return "\\overline{"+labels[1]+labels[3]+"}";
		}else if(questionType==findArcValue){
			return "\\widehat{"+labels[1]+labels[3]+"}";
		}else{//angle value
			return "\\angle "+labels[1]+labels[4]+labels[3];
		}
	}

	protected String initiateString() {
		String text = "In the diagram below, #"+labels[0]+"#, #"+labels[2]+"#, #"+labels[1]+"#, and #"+labels[3]+
				"# are points on circle#"+labels[4]+".";
		if(questionType==findChordValue){
			text+= "#Also, #\\angle "+labels[0]+labels[4]+labels[2]+"\\cong\\angle "+labels[1]+labels[4]+labels[3]+".";
		}else if(questionType==findArcValue){
			text+= "#Also, #\\angle "+labels[0]+labels[4]+labels[2]+"\\cong\\angle "+labels[1]+labels[4]+labels[3]+".";
		}else{//angle value
			text+= "#Also, #\\widehat{"+labels[0]+labels[2]+"}\\cong\\widehat{"+labels[1]+labels[3]+"}.";
		}
		return text;
		
	}
	
	protected void drawDynamicImage(CoordinateImage image){
		//names of points
		labels = Variable.randCapVars(5);
		circle = new Circle(new CoordinatePoint(Ops.randomInt(7, 9), 0));
		actualMeasure=Ops.randomInt(MINIMUM_ARC, 180-MINIMUM_ARC);
		randomAngle1 = 0;
		randomAngle2 = Ops.randomInt(actualMeasure+MINIMUM_ARC, 360-MINIMUM_ARC-actualMeasure);

		p1 = circle.getPointOnCircle(randomAngle1, true);
		p2 = circle.getPointOnCircle(randomAngle2, true);
		pp1 = circle.getPointOnCircle(randomAngle1+actualMeasure, true);
		pp2 = circle.getPointOnCircle(randomAngle2+actualMeasure, true);

		int rotateAngle = Ops.randomInt(0, 180);
		p1.rotate(rotateAngle, true);
		p2.rotate(rotateAngle, true);
		pp1.rotate(rotateAngle, true);
		pp2.rotate(rotateAngle, true);
		
		image.drawCircle(circle);
		image.drawPoint(circle.getCenter());
		image.drawPoint(p1);
		image.drawPoint(p2);
		image.drawPoint(pp1);
		image.drawPoint(pp2);
		
		if(questionType==findChordValue || (difficulty>2 && Math.random()<.4)){
			image.drawSegment(p1, pp1);
			image.drawSegment(p2, pp2);	
		}
		image.drawSegment(p1, circle.getCenter());
		image.drawSegment(p2, circle.getCenter());	
		image.drawSegment(pp1, circle.getCenter());
		image.drawSegment(pp2, circle.getCenter());	
		
		image.drawCircleLabel(""+labels[0], circle, randomAngle1+rotateAngle, true);
		image.drawCircleLabel(""+labels[1], circle, randomAngle2+rotateAngle, true);
		image.drawCircleLabel(""+labels[2], circle, randomAngle1+actualMeasure+rotateAngle, true);
		image.drawCircleLabel(""+labels[3], circle, randomAngle2+actualMeasure+rotateAngle, true);
		image.drawAngleVertexLabel(""+labels[4], p1, circle.getCenter(), pp1);
		
		//IMPORTANT! Always end with these lines to initialize critical variable!
		xValue=Ops.randomNotZero(1, 12);
		variable = Variable.randVar();
		initializeExpressions();
		dynamicImage=image.getImage();
	}
	
}