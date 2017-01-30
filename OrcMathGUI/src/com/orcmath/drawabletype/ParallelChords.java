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

public class ParallelChords extends SolvingCongruency{
	
	//fields accessed by methods
	
	char[] labels;
	CoordinateImage image;
	Circle circle;
	CoordinatePoint p1;
	CoordinatePoint p2;
	CoordinatePoint pp1;
	CoordinatePoint pp2;
	
	String questionType;
	
	//static fields
	public static int MINIMUM_ARC=40;
	public static ArrayList<String> variations = new ArrayList<String>(Arrays.asList("Solve for Interior Angle"));
	
	public ParallelChords(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
	}
	
	public ParallelChords(int difficulty){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;
		getInstance();
	}
	
	private void initiateKeyTheorems(){
		keyTheorem="Parallel chords intercept congruent arcs.";
		falseTheorem1="Parallel chords split their intercepted arcs in half.";
		falseTheorem2="Parallel chords split their intercepted arcs at a 90 degree angle.";
		falseTheorem3="Parallel chords create corresponding arcs";
	}
	
	protected String getCongruentPart1Measure() {
		return "m\\widehat{ "+labels[0]+labels[3]+"}";
	}

	protected String getCongruentPart2Measure() {
		return "m\\widehat{ "+labels[1]+labels[2]+"}";
	}

	protected String getCongruentPart1Name() {
		return "\\widehat{ "+labels[0]+labels[3]+"}";
	}

	protected String getCongruentPart2Name() {
		return "\\widehat{ "+labels[1]+labels[2]+"}";
	}

	protected String initiateString() {
		return "In the diagram below, #"+labels[0]+"#, #"+labels[1]+"#, #"+labels[3]+"#, and #"+labels[2]+
				"# are points on the circle and #\\overline{"+labels[0]+labels[1]+"}\\parallel\\overline{"+
				labels[2]+labels[3]+"}.";
	}
	
	protected void drawDynamicImage(CoordinateImage image){
		//names of points
		labels = Variable.randCapVars(4);
		int angularWidth = Ops.randomInt(15, 100);
		circle = new Circle(new CoordinatePoint(Ops.randomInt(7, 9), 0));
		int split1 = Ops.randomInt(5,70);
		int split2 = 180-split1;
		int split3 = 180 +Ops.randomInt(10,70);
		int split4 = 540-split3;
		
		p1 = circle.getPointOnCircle(split1, true);
		p2 = circle.getPointOnCircle(split2, true);
		pp1 = circle.getPointOnCircle(split3, true);
		pp2 = circle.getPointOnCircle(split4, true);

		image.drawCircle(circle);
		double angle = Ops.randomDouble(0, Math.PI);
		p1.rotate(angle, false);
		p2.rotate(angle, false);
		pp1.rotate(angle, false);
		pp2.rotate(angle, false);
		split1 = (int)(split1+angle*180/Math.PI);
		split2 = (int)(split2+angle*180/Math.PI);
		split3 = (int)(split3+angle*180/Math.PI);
		split4 = (int)(split4+angle*180/Math.PI);
		
		
		image.drawPoint(p1);
		image.drawPoint(p2);
		image.drawPoint(pp1);
		image.drawPoint(pp2);

		image.drawSegment(p1, p2);
		image.drawSegment(pp1, pp2);	

		
		image.drawCircleLabel(""+labels[0], circle, split1, true);
		image.drawCircleLabel(""+labels[1], circle, split2, true);
		image.drawCircleLabel(""+labels[2], circle, split3, true);
		image.drawCircleLabel(""+labels[3], circle, split4, true);
		
		//IMPORTANT! Always end with these lines to initialize critical variable!
		actualMeasure=angularWidth;
		xValue=Ops.randomNotZero(1, 12);
		variable = Variable.randVar();
		initializeExpressions();
		
		if(difficulty==1){
			image.drawCircleLatex(expressionCongPart2.toString(), circle, (split1+split4)/2+180, true);
		}else if(difficulty==2){
			image.drawCircleLatex(expressionCongPart1.toString(), circle, (split1+split4)/2+180, true);
			image.drawCircleLatex(expressionCongPart2.toString(), circle, (split2+split3)/2, true);
		}

		
		dynamicImage=image.getImage();
	}
	
}