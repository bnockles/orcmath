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
import java.util.ArrayList;
import java.util.Arrays;

import com.orcmath.drawable.Circle;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Variable;

public class SecantLengths extends SolveCongruentProducts{
	
	//fields accessed by methods
	
	char[] labels;
	CoordinateImage image;
	Circle circle;
	CoordinatePoint a1;
	CoordinatePoint a2;
	CoordinatePoint b1;
	CoordinatePoint b2;
	CoordinatePoint exterior;
	
	//static fields
	private static int MINIMUM_ANGLE=15;
	private static int MAXIMUM_ANGLE=70;
//	public static ArrayList<String> variations = new ArrayList<String>(Arrays.asList("Lengths of secant segments."));
	private boolean segment1IsSecant;
	private boolean segment2IsSecant;
	
	
	public SecantLengths(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
	}
	
	public SecantLengths(int difficulty){
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
		if(!part2IsAddedToPart1){
			return ""+labels[4]+labels[1];
		}else if (segment1IsSecant){
			return ""+labels[0]+labels[1];
		}else{
			return ""+labels[4]+labels[0];
		}		
	}

	public String getProduct2Part1() {
		return ""+labels[4]+labels[2];
	}

	public String getProduct2Part2() {
		if(!part2IsAddedToPart1){
			return ""+labels[4]+labels[3];
		}else if (segment2IsSecant){
			return ""+labels[2]+labels[3];
		}else{
			return ""+labels[4]+labels[2];
		}	
	}


	protected String initiateString() {
		String text = "In the diagram below, #";
		if(segment1IsSecant){
			text+="\\overline{"+labels[4]+labels[1]+"}# is secant to the circle at points #"+labels[0]+"# and #"+labels[1]+"# and #";
		}else{
			text+="\\overline{"+labels[4]+labels[0]+"}# is tangent to the circle at point #"+labels[0]+"# and #";
		}
		if(segment2IsSecant){
			text+="\\overline{"+labels[4]+labels[3]+"}# is secant to the circle at points #"+labels[2]+"# and #"+labels[3]+". ";
		}else{
			text+="\\overline{"+labels[4]+labels[2]+"}# is tangent to the circle at point #"+labels[2]+".";
		}
		return text;
		
	}
	
	protected void drawDynamicImage(CoordinateImage image){
		if(difficulty==1){
			part2IsAddedToPart1 = false;
		}else{
			part2IsAddedToPart1 = true;
		}
		labels = Variable.randCapVars(5);
		CoordinatePoint center = null;
		Circle circle=null;
		CoordinatePoint[] possibleB2s=null;
			while (possibleB2s==null){
				actualMeasurep1p1=Ops.randomInt(3, 10+(int)(difficulty*1.5));
				int difference = Ops.randomNotZero(-2, 2);
				actualMeasurep2p1=actualMeasurep1p1+difference;
				while(actualMeasurep1p1==actualMeasurep2p1){
					actualMeasurep2p1=Ops.randomInt(3, 8+(int)(difficulty*1.5));	
				}
				//k, a possible constant, starts as the larger of the two part1 distances
				int k = actualMeasurep1p1;
				if(actualMeasurep2p1>k) k = actualMeasurep2p1;
				int increment = k;
				int constant =1;//this will become the constant that part1 * (part1+part2) yield
				while(constant==1){
					if(k%actualMeasurep1p1==0 &&
							k%actualMeasurep2p1==0 &&
							(k/actualMeasurep1p1)>=actualMeasurep1p1 &&
							(k/actualMeasurep2p1)>=actualMeasurep2p1)constant=k;
					else k+=increment;
				}

				
				exterior=new CoordinatePoint(0, 0);
				a1=new CoordinatePoint(actualMeasurep1p1, 0);
				if(part2IsAddedToPart1){
					actualMeasurep1p2=k/actualMeasurep1p1-actualMeasurep1p1;
					actualMeasurep2p2=k/actualMeasurep2p1-actualMeasurep2p1;
					a2=new CoordinatePoint(actualMeasurep1p1+actualMeasurep1p2, 0);
				}else{
					actualMeasurep1p2=k/actualMeasurep1p1;
					actualMeasurep2p2=k/actualMeasurep2p1;
					a2=new CoordinatePoint(actualMeasurep1p2, 0);
				}
				
				CoordinatePoint temp = new CoordinatePoint(actualMeasurep2p1, 0);
				Circle helperCircle = new Circle(temp);//this is a circle around the exterior point

				//this is the construction of THE circle
				double xCoordinateCircle;
				double minRadius;
				double maxRadius;
				if(part2IsAddedToPart1){
					xCoordinateCircle = (double)actualMeasurep1p1+(double)actualMeasurep1p2/2;//the xValue of the center of THE circle
					minRadius = actualMeasurep1p2/2;//the radius of THE circle must reach a1 and a2
					maxRadius = (actualMeasurep1p2-actualMeasurep1p1)/1.5;//the radius of THE circle must not be larger than the center of a square constructed on segment a1a2.
					
				}else{
					xCoordinateCircle = ((double)actualMeasurep1p1+(double)actualMeasurep1p2)/2;//the xValue of the center of THE circle
					minRadius = (actualMeasurep1p2-actualMeasurep1p1)/2;//the radius of THE circle must reach a1 and a2
					maxRadius = (actualMeasurep1p2)/1.5;//the radius of THE circle must not be larger than the center of a square constructed on segment a1a2.
				}
				
				//the above maximum helps the picture look graphically more satisfying and full
				double yCoordinatePoint=Ops.randomDouble(minRadius,maxRadius);
				center = new CoordinatePoint(xCoordinateCircle,yCoordinatePoint);
				double radius = DrawableOps.getDistance(a1, center);
				if(radius>(DrawableOps.getDistance(exterior, center)-actualMeasurep2p1)){
					circle = new Circle(center,radius); 

					/*
					 * at this point, we have an appropriate circle AND a circle around the exterior point that has a radius of the 
					 * needed p2p1 length. (helperCircle) We need to select an intersection that will serve as point b1
					 */
					double[] intermediateRay= circle.getRadicalLineCoefficients(helperCircle);
					CoordinatePoint[] possibleBs = circle.getLineCircleSolution(intermediateRay[0], intermediateRay[1]);
					b1 = possibleBs[0];

					/*
					 * At this point, we have THE circle, a1, a2 and b1. We need b2.
					 */

					double[] intermediateRay2= DrawableOps.getLinearEquation(exterior, b1);
					possibleB2s = circle.getLineCircleSolution(intermediateRay2[0], intermediateRay2[1]);
					b2 = possibleB2s[1];
					if(b2.getxCoordinate()==b1.getxCoordinate() && b2.getyCoordinate()==b1.getyCoordinate())b2=possibleB2s[0];
				}else{

				}
			}

//			b2 = possibleB2s[0];

//		double[] angleRay = DrawableOps.getLinearEquation(exterior, b1);
//		CoordinatePoint[] possibleB2s = circle.getLineCircleSolution(angleRay[0], angleRay[1]);
//		b2 = possibleB2s[0];
		segment1IsSecant=true;
		segment2IsSecant=true;
		if(actualMeasurep1p2==0){
			segment1IsSecant=false;
			product1IsSquare=true;
		}
		if(actualMeasurep2p2==0){
			segment2IsSecant=false;
			product2IsSquare=true;
		}
		AffineTransform orig = image.getGraphics().getTransform();
		double scale = 6/circle.getRadius();

		a1.dilate(scale, scale);
		a2.dilate(scale, scale);
		b1.dilate(scale, scale);
		b2.dilate(scale, scale);
		exterior.dilate(scale, scale);
		circle.dilate(scale, scale);
		image.getGraphics().translate(-image.getLocation(center.getxCoordinate(), CoordinateImage.X_COORDINATE), -image.getLocation(center.getyCoordinate(), CoordinateImage.Y_COORDINATE));
		
		
		image.drawCircle(circle);
		image.drawPoint(a1);
		image.drawPoint(a2);
		image.drawPoint(b1);
		image.drawPoint(b2);
		image.drawPoint(exterior);
		image.drawPoint(circle.getCenter());
		
		image.drawSegment(exterior, a2);
		image.drawSegment(exterior, b2);	
		
		
		
		image.drawCircleLabel(""+labels[4], circle, exterior);
		image.drawCircleLabel(""+labels[0], circle, a1);
		if(segment1IsSecant)image.drawCircleLabel(""+labels[1], circle, a2);
		image.drawCircleLabel(""+labels[2], circle, b1);
		if(segment2IsSecant)image.drawCircleLabel(""+labels[3], circle, b2);
		image.getGraphics().setTransform(orig);
		
//		if((Math.abs(circle.getStandardPositionAngle(a1, true)-circle.getStandardPositionAngle(a2, true))<15) ||
//				(Math.abs(circle.getStandardPositionAngle(b1, true)-circle.getStandardPositionAngle(b2, true))<15)){
//			getInstance();
//		}
		//IMPORTANT! Always end with these lines to initialize critical variable!
		xValue=Ops.randomNotZero(1, 12);
		variable = Variable.randVar();
		initializeExpressions();
		dynamicImage=image.getImage();
		}
//	}
	
}
