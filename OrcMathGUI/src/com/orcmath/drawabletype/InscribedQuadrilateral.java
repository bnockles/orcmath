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

import com.orcmath.drawable.Circle;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Variable;

public class InscribedQuadrilateral extends SolveSupplementary{

	char[] labels;
	Circle circle;
	CoordinatePoint p1;
	CoordinatePoint p2;
	CoordinatePoint p3;
	CoordinatePoint p4;
	
	public static int MINIMUM_ARC=40;
	
	public InscribedQuadrilateral(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=20;	
		whetherInstructionsAreNeverIncluded=true;
	}
	
	public InscribedQuadrilateral(int difficulty){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=20;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;
		getInstance();
		//question and answer is determined in the getInstance constructor
	}

	private void initiateKeyTheorems() {
		keyTheorem="The opposite angles of a quadrilateral inscribed in a circle are supplementary.";
		falseTheorem1="The opposite angles of a quadrilateral inscribed in a circle are perpendicular.";
		falseTheorem2="The opposite angles of a quadrilateral inscribed in a circle are complementary.";
		falseTheorem3="The adjacent angles of a quadrilateral inscribed in a circle are supplementary";	
	}
	protected String getAngle1Measure() {
		String name = "m"+getAngle1Name();
		return name;
	}

	protected String getAngle1Name() {
		String name = "\\angle "+labels[3]+labels[0]+labels[1];
		return name;
	}

	protected String getAngle2Measure() {
		String name = "m"+getAngle2Name();
		return name;
	}

	protected String getAngle2Name() {
		String name = "\\angle "+labels[3]+labels[2]+labels[1];
		return name;
	}

	protected String initiateString() {
		String text = "In the diagram below, #"+labels[0]+",# #"+labels[1]+",# #"+labels[2]+
				",# and #"+labels[3]+"# are points on the circle. ";
		return text;
	}

	protected void drawDynamicImage(CoordinateImage image2) {
		labels = Variable.randCapVars(4);
		circle=new Circle(new CoordinatePoint(9, 0));
		int split1 = Ops.randomInt(0, 90-MINIMUM_ARC);
		int split2 = Ops.randomInt(split1+MINIMUM_ARC,split1+90);
		int split3 = Ops.randomInt(split2+MINIMUM_ARC,split2+90);
		int split4 = Ops.randomInt(split3+MINIMUM_ARC,split3+90);
		while((split4-split2)%2==1)split4 = Ops.randomInt(split3+MINIMUM_ARC,split3+90);
		p1 = circle.getPointOnCircle(split1, true);
		p2 = circle.getPointOnCircle(split2, true);
		p3 = circle.getPointOnCircle(split3, true);
		p4 = circle.getPointOnCircle(split4, true);
		
		image.drawCircle(circle);
		image.drawPoint(p1);
		image.drawPoint(p2);
		image.drawPoint(p3);
		image.drawPoint(p4);
		image.drawSegment(p1, p2);
		image.drawSegment(p2, p3);
		image.drawSegment(p1, p2);
		image.drawSegment(p3, p4);
		image.drawSegment(p4, p1);
		image.drawCircleLabel(""+labels[0], circle, split1, true);
		image.drawCircleLabel(""+labels[1], circle, split2, true);
		image.drawCircleLabel(""+labels[2], circle, split3, true);
		image.drawCircleLabel(""+labels[3], circle, split4, true);
		
		//IMPORTANT! Always end with these lines to initialize critical variable!
		angle1Actual=(split4-split2)/2;
		angle2Actual=180-angle1Actual;
		xValue=Ops.randomNotZero(1, 12);
		variable = Variable.randVar();
		initializeExpressions();
		dynamicImage=image.getImage();
	}
}
