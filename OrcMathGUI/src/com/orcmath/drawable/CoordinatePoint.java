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
package com.orcmath.drawable;

import com.orcmath.drawabletype.DrawableOps;
import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Point;

public class CoordinatePoint {
	private double xCoordinate;
	private double yCoordinate;
	private String label;
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CoordinatePoint(double x, double y, String label){
		xCoordinate=x;
		yCoordinate=y;
		this.label = label;
	}
	
	public CoordinatePoint(double x, double y){
		xCoordinate=x;
		yCoordinate=y;
		this.label = "";
	}
	
	public void translate(double xChange, double yChange){
		xCoordinate=xCoordinate+xChange;
		yCoordinate=yCoordinate+yChange;
	}
	
	public void dilate(double xScale, double yScale){
		xCoordinate=xCoordinate*xScale;
		yCoordinate=yCoordinate*yScale;
	}

	public void rotate(double angle, boolean inDegrees){
		if(inDegrees){
			angle = angle*Math.PI/180;
		}
		double xStore = xCoordinate;
		xCoordinate=xCoordinate*Math.cos(angle)-yCoordinate*Math.sin(angle);
		yCoordinate=xStore*Math.sin(angle)+yCoordinate*Math.cos(angle);
	}	
	
	public void rotate(double angle, boolean inDegrees, CoordinatePoint center){
		if(inDegrees){
			angle = angle*Math.PI/180;
		}
		double xTranslation = center.getxCoordinate();
		double yTranslation = center.getyCoordinate();
		xCoordinate=xCoordinate-xTranslation;
		yCoordinate=yCoordinate-yTranslation;
		double xStore = xCoordinate;
		xCoordinate=xCoordinate*Math.cos(angle)-yCoordinate*Math.sin(angle);
		yCoordinate=xStore*Math.sin(angle)+yCoordinate*Math.cos(angle);
		xCoordinate=xCoordinate+xTranslation;
		yCoordinate=yCoordinate+yTranslation;
	}	
	
	public void reflectOverX(){
		yCoordinate=yCoordinate*(-1);
	}
	
	public void reflectOverY(){
		xCoordinate=xCoordinate*(-1);
	}
	
	public void reflectOverXEqualsY(){
		double storeX = xCoordinate;
		xCoordinate=yCoordinate;
		yCoordinate=storeX;
	}
	
	public void reflect(double m, double b){
		if (m==DrawableOps.undefinedIdentifier){
			double distanceToB=xCoordinate-b;
			xCoordinate=xCoordinate-2*distanceToB;
		}else if(m==0){
			double yDistanceToB=yCoordinate-b;
			yCoordinate=yCoordinate-2*yDistanceToB;
		}else{
			double perpSlope =-1/m;
			double perpIntrcpt=yCoordinate-perpSlope*xCoordinate;
			double[] equation1 = {m,b};
			double[] equation2 = {perpSlope,perpIntrcpt};
			CoordinatePoint p = DrawableOps.getIntersectionOfLines(equation1, equation2);
			double xDistanceToP=xCoordinate-p.getxCoordinate();
			double yDistanceToP=yCoordinate-p.getyCoordinate();
			xCoordinate=xCoordinate-2*xDistanceToP;
			yCoordinate=yCoordinate-2*yDistanceToP;
		}
	}
	
	public double getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public double getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label=label;
	}
	
	public String toString(){
		return "("+Format.doubleToString(xCoordinate)+","+Format.doubleToString(yCoordinate)+")";
	}
	
	public String roundToIntegers(){
		return "("+(int)(Ops.roundDouble(xCoordinate, 0))+","+(int)(Ops.roundDouble(yCoordinate, 0))+")";
	}
	
}
