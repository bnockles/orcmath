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

import java.awt.geom.AffineTransform;

import com.orcmath.drawable.Circle;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Variable;

public class ChordLengths extends SolveCongruentProducts{
	
	//fields accessed by methods
	
	char[] labels;
	CoordinateImage image;
	Circle circle;
	CoordinatePoint a1;
	CoordinatePoint a2;
	CoordinatePoint b1;
	CoordinatePoint b2;
	CoordinatePoint intersection;

	
	
	public ChordLengths(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
	}
	
	public ChordLengths(int difficulty){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;
		getInstance();
	}
	
	private void initiateKeyTheorems(){
			keyTheorem="For a given point and a circle, the product of the lengths of the two segments" +
					" from the point to the circle is constant along any line through the point and the circle.";
			falseTheorem1="For a given point and a circle, the length of the chord interior of the circle is always" +
					" half the length of the segment exterior of the circle.";
			falseTheorem2="The lengths of two secants to a circle are always proportional";
			falseTheorem3="For a given point and a circle, the sum of the lengths of the two segments" +
					" from the point to the circle is constant along any line through the point and the circle.";
	}
	
	public String getProduct1Part1() {
			return ""+labels[4]+labels[0];

	}

	public String getProduct1Part2() {
			return ""+labels[4]+labels[1];	
	}

	public String getProduct2Part1() {
		return ""+labels[4]+labels[2];
	}

	public String getProduct2Part2() {
			return ""+labels[4]+labels[3];
	}


	protected String initiateString() {
		String text = "In the diagram below, #"+labels[0]+",# #"+labels[1]+",# #"+labels[2]+",# #"+labels[3]+"# are points " +
				"on the circle and #\\overline{"+labels[0]+labels[1]+"}# and #\\overline{"+labels[2]+labels[3]+"}# intersect at point, #"+labels[4]+".";
		
		return text;
		
	}
	
	protected void drawDynamicImage(CoordinateImage image){
		part2IsAddedToPart1 = false;
		labels = Variable.randCapVars(5);
		CoordinatePoint center = null;
		Circle circle=null;
		CoordinatePoint intersectionReferenceCircleCenter = null;
		boolean goodImage = false;//a good image is an image in which the points are far enough apart that the labels can be read
			while (!goodImage){
				actualMeasurep1p1=Ops.randomInt(3, 8+(int)(difficulty*1.5));
				actualMeasurep2p1=Ops.randomInt(3, 8+(int)(difficulty*1.5));	
				while(actualMeasurep1p1==actualMeasurep2p1){
//					actualMeasurep2p1=Ops.randomInt(3, 8+(int)(difficulty*1.5));
					actualMeasurep2p1=Ops.randomInt(3, 8);	
				}
				int k = actualMeasurep1p1;
				if(actualMeasurep2p1>k) k = actualMeasurep2p1;
				int increment = k;
				int constant =1;
				while(constant==1){
					if(k%actualMeasurep1p1==0 &&
							k%actualMeasurep2p1==0 &&
							k/actualMeasurep1p1>=actualMeasurep1p1 &&
							k/actualMeasurep2p1>=actualMeasurep2p1)constant=k;
					else k+=increment;
				}

				actualMeasurep1p2=k/actualMeasurep1p1;
				actualMeasurep2p2=k/actualMeasurep2p1;
				
				intersection=new CoordinatePoint(0, 0);
				a1=new CoordinatePoint(-actualMeasurep1p1, 0);
				a2=new CoordinatePoint(actualMeasurep1p2, 0);
				
				int inscribedRectangleHeight = Ops.randomInt(0, actualMeasurep1p1+actualMeasurep1p2);
				center = new CoordinatePoint(((double)actualMeasurep1p2-(double)actualMeasurep1p1)/2, inscribedRectangleHeight/2);
				circle = new Circle(center, DrawableOps.getDistance(center, a2));
				double[] lineToCenter = DrawableOps.getLinearEquation(intersection, center);
				CoordinatePoint[] minMax = circle.getLineCircleSolution(lineToCenter[0], lineToCenter[1]);
				if(minMax!=null){
					System.out.println("Stuck in a loop:" + actualMeasurep1p1+", "+actualMeasurep1p2);
					double min =DrawableOps.getDistance(minMax[0], intersection);
					double max =DrawableOps.getDistance(minMax[1], intersection);
					intersectionReferenceCircleCenter=minMax[0];
					
					if(actualMeasurep2p1<max && actualMeasurep2p1>min)goodImage=true;
				}
			}
			
			//this next sequence carefully looks at possible points and chooses the point that is farthest from a1 and a2 (for visibility)
			Circle constructPoints = new Circle (intersection,actualMeasurep2p1);
			double[] lineEq=circle.getRadicalLineCoefficients(constructPoints);
			CoordinatePoint[] bs = circle.getLineCircleSolution(lineEq[0], lineEq[1]);
			b1=bs[0];
			double minB1Distance=DrawableOps.getDistance(b1, a1);
			if(DrawableOps.getDistance(b1, a2)<minB1Distance)minB1Distance=DrawableOps.getDistance(b1, a2);
			double minB2Distance=DrawableOps.getDistance(bs[1], a1);
			if(DrawableOps.getDistance(bs[1], a2)<minB2Distance)minB2Distance=DrawableOps.getDistance(bs[1], a2);
			if(minB2Distance>minB1Distance)b1=bs[1];
			double[]secondLine = DrawableOps.getLinearEquation(b1, intersection);
			CoordinatePoint[] b2s=circle.getLineCircleSolution(secondLine[0], secondLine[1]);
			b2=b2s[0];
			if(b2.getxCoordinate()==b1.getxCoordinate())b2=b2s[1];

			
		AffineTransform orig = image.getGraphics().getTransform();
		double scale = 6/circle.getRadius();

		a1.dilate(scale, scale);
		a2.dilate(scale, scale);
		b1.dilate(scale, scale);
		b2.dilate(scale, scale);
		intersection.dilate(scale, scale);
		circle.dilate(scale, scale);
		image.getGraphics().translate(-image.getLocation(center.getxCoordinate(), CoordinateImage.X_COORDINATE), -image.getLocation(center.getyCoordinate(), CoordinateImage.Y_COORDINATE));
		
		image.drawCircle(circle);
		image.drawPoint(a1);
		image.drawPoint(a2);
		image.drawPoint(b1);
		image.drawPoint(b2);
		image.drawPoint(intersection);
		
		image.drawSegment(a1, a2);
		image.drawSegment(b1, b2);	
		
		
		
		image.drawCircleLabel(""+labels[4], new Circle(intersectionReferenceCircleCenter,DrawableOps.getDistance(intersectionReferenceCircleCenter, intersection)), intersection);
		image.drawCircleLabel(""+labels[0], circle, a1);
		image.drawCircleLabel(""+labels[1], circle, a2);
		image.drawCircleLabel(""+labels[2], circle, b1);
		image.drawCircleLabel(""+labels[3], circle, b2);
		image.getGraphics().setTransform(orig);
		
//		if((Math.abs(circle.getStandardPositionAngle(a1, true)-circle.getStandardPositionAngle(a2, true))<15) ||
//				(Math.abs(circle.getStandardPositionAngle(b1, true)-circle.getStandardPositionAngle(b2, true))<15)){
//			getInstance();
//		}
		//IMPORTANT! Always end with these lines to initialize critical variable!
		xValue=Ops.randomNotZero(1, 12);
		variable = Variable.randVar();
//		System.out.println("Checkpoint in Secant Lengths:\n"+actualMeasurep1p1+"\n"
//				+actualMeasurep1p2+"\n"
//				+actualMeasurep2p1+"\n"
//				+actualMeasurep2p2);
		initializeExpressions();
//		System.out.println("Checkpoint in Secant Lengths");
		dynamicImage=image.getImage();
		}
//	}
	
}