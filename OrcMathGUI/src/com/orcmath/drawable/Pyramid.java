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

public class Pyramid {

	double length;
	double width;
	double height;
	double scaleFactor;
	double drawnLength;
	double drawnWidth;
	double drawnHeight;
	double perspective = .35;
	double offset = 2;
	CoordinatePoint center;
	CoordinatePoint vertex;
	CoordinatePoint baseLeftFront;
	CoordinatePoint baseRightFront;
	CoordinatePoint baseRightRear;
	
	public Pyramid(double l,double w, double h){
		length=l;
		width=w;
		height=h;
		scaleFactor = 16/width;
		if(16/height<scaleFactor)scaleFactor=16/height;
		if(perspective*16/length<scaleFactor)scaleFactor=perspective*16/length;
		drawnWidth = width*scaleFactor;
		drawnLength=perspective*scaleFactor*length;
		drawnHeight = height*scaleFactor;
	}
	
	public void draw(CoordinateImage image, CoordinatePoint givenCenter){
		this.center=givenCenter;
		baseLeftFront = new CoordinatePoint(center.getxCoordinate()-drawnWidth-offset/2, center.getyCoordinate()-drawnLength/2-drawnHeight/2);
		baseRightFront = new CoordinatePoint(center.getxCoordinate()+drawnWidth-offset/2, center.getyCoordinate()-drawnLength/2-drawnHeight/2);
		CoordinatePoint baseLeftRear= new CoordinatePoint(center.getxCoordinate()-drawnWidth+offset/2, center.getyCoordinate()+drawnLength/2-drawnHeight/2);
		baseRightRear= new CoordinatePoint(center.getxCoordinate()+drawnWidth+offset/2, center.getyCoordinate()+drawnLength/2-drawnHeight/2);
		vertex= new CoordinatePoint(center.getxCoordinate(), center.getyCoordinate()+drawnHeight/2);
		image.drawSegment(baseLeftFront,baseRightFront);
		image.drawDottedSegment(baseLeftFront,baseLeftRear);
		image.drawSegment(baseLeftFront,vertex);
		image.drawSegment(baseRightFront,baseRightRear);
		image.drawSegment(baseRightFront,vertex);
		image.drawDottedSegment(baseLeftRear,baseRightRear);
		image.drawDottedSegment(baseLeftRear,vertex);
		image.drawSegment(baseRightRear,vertex);
	}
	
	public void labelHeight(CoordinateImage image, String label){
		CoordinatePoint baseCenter = new CoordinatePoint(center.getxCoordinate(), center.getyCoordinate()-drawnHeight/2);
		image.drawDottedSegment(baseCenter,vertex);
		image.drawSegmentLatex(label, baseCenter, vertex, false, true);
	}
	
	public void labelLength(CoordinateImage image, String label){
		image.drawSegmentLatex(label, baseRightFront, baseRightRear, true, false);
	}
	
	public void labelWidth(CoordinateImage image, String label){
		image.drawSegmentLatex(label, baseRightFront, baseLeftFront, true, false);
	}
}
