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

public class InscribedAngleSameArc extends SolvingCongruency{
	
	//fields accessed by methods
	
	char[] labels;
	CoordinateImage image;
	Circle circle;
	CoordinatePoint p1;
	CoordinatePoint p2;
	CoordinatePoint p3;
	CoordinatePoint p4;
	int randomAngle1;
	int randomAngle2;
	int randomAngle3;
	int randomAngle4;
	
	String questionType;
	
	//static fields
	public static int MINIMUM_ARC=40;
	public static ArrayList<String> variations = new ArrayList<String>(Arrays.asList("Solve for Interior Angle"));
	
	public InscribedAngleSameArc(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
	}
	
	public InscribedAngleSameArc(int difficulty){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;
		getInstance();
	}
	
	private void initiateKeyTheorems(){
		keyTheorem="If inscribed angles of a circle intercept the same arc, then the angles are congruent";
		falseTheorem1="If inscribed angles of a circle intercept the same arc, then the angles are perpendicular";
		falseTheorem2="If inscribed angles of a circle intercept the same arc, then the angles are formed by points on the circle";
		falseTheorem3="If an arc is intercepted by inscribed angles, then the arc is a semi-circle";
	}
	
	protected String getCongruentPart1Measure() {
		return "m\\angle "+labels[2]+labels[0]+labels[3];
	}

	protected String getCongruentPart2Measure() {
		return "m\\angle "+labels[2]+labels[1]+labels[3];
	}

	protected String getCongruentPart1Name() {
		return "\\angle "+labels[2]+labels[0]+labels[3];
	}

	protected String getCongruentPart2Name() {
		return "\\angle "+labels[2]+labels[1]+labels[3];
	}

	protected String initiateString() {
		return "In the diagram below, #"+labels[0]+"#, #"+labels[1]+"#, #"+labels[2]+"#, and #"+labels[3]+
				"# are points on the circle.#";
	}

	protected int getActualMeasure() {
		// TODO Auto-generated method stub
		int x = Math.abs(randomAngle4-randomAngle3)/2;
		return x;
	}
	
	protected void drawDynamicImage(CoordinateImage image){
		//names of points
		labels = Variable.randCapVars(4);
		circle = new Circle(new CoordinatePoint(Ops.randomInt(7, 9), 0));
		int split1 = Ops.randomInt(MINIMUM_ARC, 207);//200
		int split2 = Ops.randomInt(split1+MINIMUM_ARC, 258);//250
		int split3 = Ops.randomInt(split2+MINIMUM_ARC, 309);
		randomAngle1 = (int)(Math.random()*(split1-MINIMUM_ARC)+MINIMUM_ARC);//200
		randomAngle2 = (int)(Math.random()*(split2-split1-MINIMUM_ARC))+split1+MINIMUM_ARC;
		randomAngle3 = (int)(Math.random()*(split3-split2-MINIMUM_ARC))+split2+MINIMUM_ARC;
		randomAngle4 = (int)(Math.random()*(360-split3-MINIMUM_ARC))+split3+MINIMUM_ARC;
		if((randomAngle4 - randomAngle1)%2!=0){
			randomAngle4+=1;
		}

		p1 = circle.getPointOnCircle(randomAngle1, true);
		p2 = circle.getPointOnCircle(randomAngle2, true);
		p3 = circle.getPointOnCircle(randomAngle3, true);
		p4 = circle.getPointOnCircle(randomAngle4, true);

		image.drawCircle(circle);
		image.drawPoint(p1);
		image.drawPoint(p2);
		image.drawPoint(p3);
		image.drawPoint(p4);

		image.drawSegment(p1, p3);
		image.drawSegment(p2, p4);	
		image.drawSegment(p1, p4);	
		image.drawSegment(p2, p3);	
		
		image.drawCircleLabel(""+labels[0], circle, randomAngle1, true);
		image.drawCircleLabel(""+labels[1], circle, randomAngle2, true);
		image.drawCircleLabel(""+labels[2], circle, randomAngle3, true);
		image.drawCircleLabel(""+labels[3], circle, randomAngle4, true);
		
		//IMPORTANT! Always end with these lines to initialize critical variable!
		actualMeasure=getActualMeasure();
		xValue=Ops.randomNotZero(1, 12);
		variable = Variable.randVar();
		initializeExpressions();
		dynamicImage=image.getImage();
	}
	
}