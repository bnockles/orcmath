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

import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.CoordinateSegment;

public class DrawableOps {

	public static double undefinedIdentifier = .1011011101111;
	
	public static double getDistance(CoordinatePoint a, CoordinatePoint b){
		double xDiff = a.getxCoordinate()-b.getxCoordinate();
		double yDiff = a.getyCoordinate()-b.getyCoordinate();
		return Math.sqrt(Math.pow(xDiff, 2)+Math.pow(yDiff,2));
	}

	public static double getPixelDistance(CoordinateImage image, CoordinatePoint a, CoordinatePoint b){
		double xDiff = a.getxCoordinate()-b.getxCoordinate();
		int xDiffPixels = image.getLocation(xDiff, CoordinateImage.X_COORDINATE);
		double yDiff = a.getyCoordinate()-b.getyCoordinate();
		int yDiffPixels = image.getLocation(yDiff, CoordinateImage.Y_COORDINATE);
		return Math.sqrt(Math.pow(xDiffPixels, 2)+Math.pow(yDiffPixels,2));
	}
	
	//returns the coefficients of a linear equation as an array. i.e. {m,b}
	//if the line is undefined, it returns the undefined slope and b is the x value;
	public static double[] getLinearEquation(CoordinatePoint p1, CoordinatePoint p2){
		if((p1.getxCoordinate()-p2.getxCoordinate())!=0){
			double slope = (p1.getyCoordinate()-p2.getyCoordinate())/(p1.getxCoordinate()-p2.getxCoordinate());
			double intercept = p1.getyCoordinate()-slope*p1.getxCoordinate();
			double[] coefficients = {slope,intercept};
			return coefficients;
		}else{
			double[] coefficients = {undefinedIdentifier,p1.getxCoordinate()};
			return coefficients;
		}
	}
	
	public static double[] getPerpendicularBisectorEquation(CoordinatePoint p1, CoordinatePoint p2){
		if((p1.getxCoordinate()-p2.getxCoordinate())!=0){
			double origSlope = (p1.getyCoordinate()-p2.getyCoordinate())/(p1.getxCoordinate()-p2.getxCoordinate());
			double perpSlope = -1/origSlope;
			double intercept = (p1.getyCoordinate()+p2.getyCoordinate())/2-perpSlope*(p1.getxCoordinate()+p1.getxCoordinate())/2;
			double[] coefficients = {perpSlope,intercept};
			return coefficients;
		}else{
			System.out.println("The perpendicular bisector method yielded a line with an undefined slope");
			double[] coefficients = {undefinedIdentifier,p1.getxCoordinate()};
			return coefficients;
		}
	}
	
	public static CoordinatePoint getIntersectionOfLines(double[] equation1, double[] equation2){
		CoordinatePoint intersection =null;
		if(equation1[0]==undefinedIdentifier&&equation1[1]==undefinedIdentifier);
		else if(equation1[0]==undefinedIdentifier) intersection=new CoordinatePoint(equation1[1],equation2[0]*equation1[1]+equation2[1]);
		else if(equation2[0]==undefinedIdentifier) intersection=new CoordinatePoint(equation2[1],equation1[0]*equation2[1]+equation1[1]);
		else{
			double x=(equation2[1]-equation1[1])/(equation1[0]-equation2[0]);
			double y=equation1[0]*x+equation1[1];
			intersection= new CoordinatePoint(x, y);
		}
		return intersection;
	}
	
	public static CoordinatePoint getIntersectionOfSegments(CoordinateSegment s1, CoordinateSegment s2) {
		double[] equation1 = s1.getLinearEquationCoefficients();
		double[] equation2 = s2.getLinearEquationCoefficients();
		//mx+b=nx+c
		if ((equation1[0]-equation2[0])!=0){
			return getIntersectionOfLines(equation1, equation2);
		}else{
			return null;
		}
	}

	public static CoordinatePoint rotate(CoordinatePoint p, double angle) {
		double x = p.getxCoordinate();
		double y = p.getyCoordinate();
		return new CoordinatePoint(x*Math.cos(angle)-y*Math.sin(angle), Math.sin(angle)*x+y*Math.cos(angle));
	}

}
