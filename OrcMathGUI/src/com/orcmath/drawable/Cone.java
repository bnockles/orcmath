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

public class Cone {

	double radius;
	double height;
	double scaleFactor;
	double drawnRadius = radius*scaleFactor;
	double drawnHeight = height*scaleFactor;
	double perspective = .35;
	CoordinatePoint center;
	CoordinatePoint vertex;
	double baseValue; //location of base (above or below x-axis)
	
	public Cone(double r, double h){
		radius=r;
		height=h;
		scaleFactor = 8/radius;
		if(10/height<8/radius)scaleFactor=10/height;
		drawnRadius = radius*scaleFactor;
		drawnHeight = height*scaleFactor;
	}
	
	public void draw(CoordinateImage image, CoordinatePoint givenCenter, boolean rightsideup){
		this.center=givenCenter;
		if(Math.random()<.5 || rightsideup){//upside down
			baseValue=center.getyCoordinate()-drawnHeight/2;
		}else{
			baseValue=center.getyCoordinate()+drawnHeight/2;
		}	
		//top
		image.drawOval(center.getxCoordinate(), baseValue, 2*drawnRadius, perspective*drawnRadius);
		vertex= new CoordinatePoint(center.getxCoordinate(), -baseValue);
		image.drawSegment(new CoordinatePoint(center.getxCoordinate()-drawnRadius, baseValue-perspective*drawnRadius), 
				vertex);
		image.drawSegment(new CoordinatePoint(center.getxCoordinate()+drawnRadius, baseValue-perspective*drawnRadius), 
				vertex);
	}
	
	public void labelRadius(CoordinateImage image, String label){
		image.drawDottedSegment(new CoordinatePoint(center.getxCoordinate(), baseValue-perspective*drawnRadius), 
				new CoordinatePoint(center.getxCoordinate()+drawnRadius, baseValue-perspective*drawnRadius));
		image.drawLatex(label, center.getxCoordinate()+drawnRadius/2.25, baseValue-perspective*drawnRadius);
	}
	
	public void labelDiameter(CoordinateImage image, String label){
		CoordinatePoint left =new CoordinatePoint(center.getxCoordinate()-drawnRadius, baseValue-perspective*drawnRadius);
		CoordinatePoint right =new CoordinatePoint(center.getxCoordinate()+drawnRadius, baseValue-perspective*drawnRadius);
		image.drawDottedSegment(left, 
				right);
		if(baseValue<0){
			image.drawSegmentLatex(label, left, right, true, false);	
		}else{
			image.drawSegmentLatex(label, left, right, false, false);			
		}
	}
	
	public void labelHeight(CoordinateImage image, String label){
		image.drawDottedSegment(new CoordinatePoint(center.getxCoordinate(), baseValue-perspective*drawnRadius), 
				vertex);
		image.drawLatex(label, center.getxCoordinate()-1, center.getyCoordinate()-.5);
	}
	
	public void labelSlant(CoordinateImage image, String label){
		image.drawDottedSegment(new CoordinatePoint(center.getxCoordinate()+drawnRadius+.5, baseValue-perspective*drawnRadius), 
				new CoordinatePoint(vertex.getxCoordinate()+.5, vertex.getyCoordinate()));
		image.drawLatex(label, (center.getxCoordinate()+drawnRadius+vertex.getxCoordinate()+1)/2+.5, center.getyCoordinate()-.5);
	}
	
}
