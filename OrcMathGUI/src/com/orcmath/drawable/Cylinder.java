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

public class Cylinder {

	double radius;
	double height;
	double scaleFactor;
	double drawnRadius = radius*scaleFactor;
	double drawnHeight = height*scaleFactor;
	double perspective = .35;
	CoordinatePoint center;
	
	public Cylinder(double r, double h){
		radius=r;
		height=h;
		scaleFactor = 8/radius;
		if(10/height<8/radius)scaleFactor=10/height;
		drawnRadius = radius*scaleFactor;
		drawnHeight = height*scaleFactor;
	}
	
	public void draw(CoordinateImage image, CoordinatePoint givenCenter){
		this.center=givenCenter;
		//top
		image.drawOval(center.getxCoordinate(), center.getyCoordinate()+drawnHeight/2, 2*drawnRadius, perspective*drawnRadius);
		//sides
		image.drawSegment(new CoordinatePoint(center.getxCoordinate()-drawnRadius, center.getyCoordinate()+drawnHeight/2-perspective*drawnRadius), 
				new CoordinatePoint(center.getxCoordinate()-drawnRadius, center.getyCoordinate()-drawnHeight/2-perspective*drawnRadius));
		image.drawSegment(new CoordinatePoint(center.getxCoordinate()+drawnRadius, center.getyCoordinate()+drawnHeight/2-perspective*drawnRadius), 
				new CoordinatePoint(center.getxCoordinate()+drawnRadius, center.getyCoordinate()-drawnHeight/2-perspective*drawnRadius));
		//base
		image.drawArc(center.getxCoordinate(), center.getyCoordinate()-drawnHeight/2, 2*drawnRadius, perspective*drawnRadius,180,180);
		image.drawDottedArc(center.getxCoordinate(), center.getyCoordinate()-drawnHeight/2, 2*drawnRadius, perspective*drawnRadius,0,180);	
	}
	
	public void labelRadius(CoordinateImage image, String label){
		image.drawSegment(new CoordinatePoint(center.getxCoordinate(), center.getyCoordinate()+drawnHeight/2-perspective*drawnRadius), 
				new CoordinatePoint(center.getxCoordinate()+drawnRadius, center.getyCoordinate()+drawnHeight/2-perspective*drawnRadius));
		image.drawLatex(label, center.getxCoordinate()+drawnRadius/2.25, center.getyCoordinate()+drawnHeight/2-perspective*drawnRadius);
	}
	
	public void labelHeight(CoordinateImage image, String label){
		image.drawLatex(label, center.getxCoordinate()+drawnRadius+.5, center.getyCoordinate()-.5);
	}
	
	public void labelDiameter(CoordinateImage image, String label){
		CoordinatePoint left =new CoordinatePoint(center.getxCoordinate()-drawnRadius, center.getyCoordinate()+drawnHeight/2-perspective*drawnRadius);
		CoordinatePoint right =new CoordinatePoint(center.getxCoordinate()+drawnRadius, center.getyCoordinate()+drawnHeight/2-perspective*drawnRadius);
		image.drawDottedSegment(left, right);
		image.drawSegmentLatex(label, left, right, false, false);			
	}
}
