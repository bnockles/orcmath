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

public class Sphere {
	
	double radius;
	double perspective = 1;
	CoordinatePoint center;
	
	public Sphere(double r){
		radius=r;
	}
	
	public void draw(CoordinateImage image, CoordinatePoint givenCenter){
		this.center=givenCenter;
		//top
		image.drawCircle(new Circle(center,5));
		image.drawDottedArc(center.getxCoordinate(), center.getyCoordinate()+2*perspective, 10, 2,0,180);
		image.drawArc(center.getxCoordinate(), center.getyCoordinate()+2*perspective, 10, 2,180,180);
	}
	
	public void labelRadius(CoordinateImage image, String label){
		CoordinatePoint edge = new CoordinatePoint(center.getxCoordinate()+5, center.getyCoordinate());
		image.drawDottedSegment(center, edge);
		image.drawSegmentLatex(label, center, edge, false, false);
	}
	
	public void labelDiameter(CoordinateImage image, String label){
		CoordinatePoint edge1 = new CoordinatePoint(center.getxCoordinate()-5, center.getyCoordinate());
		CoordinatePoint edge2 = new CoordinatePoint(center.getxCoordinate()+5, center.getyCoordinate());
		image.drawDottedSegment(edge1, edge2);
		image.drawSegmentLatex(label, edge1, edge2, false, false);
	}
}
