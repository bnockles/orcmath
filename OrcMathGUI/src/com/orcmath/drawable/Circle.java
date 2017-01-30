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

import java.util.ArrayList;

import com.orcmath.drawabletype.DrawableOps;

public class Circle {

	CoordinatePoint center;
	CoordinatePoint pointOnCircle;
	ArrayList<CoordinatePoint> pointsOnCircle;
	
	
	public Circle(CoordinatePoint point) {//circle around the origin through given point
		center = new CoordinatePoint(0.0, 0.0);
		pointOnCircle = point;
		pointsOnCircle=new ArrayList<CoordinatePoint>();
		pointsOnCircle.add(pointOnCircle);
	}
	
	public Circle(CoordinatePoint center, double radius) {//circle around the origin through given point
		this.center = center;		
		pointOnCircle = new CoordinatePoint(center.getxCoordinate(), 
				center.getyCoordinate()+
				radius);
		pointsOnCircle=new ArrayList<CoordinatePoint>();
		pointsOnCircle.add(pointOnCircle);
	}
	
	public void dilate(double xScale, double yScale){
		center.setxCoordinate(center.getxCoordinate()*xScale);
		center.setyCoordinate(center.getyCoordinate()*yScale);
		for(int index=0; index<pointsOnCircle.size(); index++){
			double oldX = pointsOnCircle.get(index).getxCoordinate();
			double oldY = pointsOnCircle.get(index).getyCoordinate();
			pointsOnCircle.get(index).setxCoordinate(oldX*xScale);
			pointsOnCircle.get(index).setyCoordinate(oldY*yScale);
		}
		
	}
	
	public CoordinatePoint getCenter(){
		return center;
	}
	
	public void setCenter(CoordinatePoint newCenter){
		double xDifference = newCenter.getxCoordinate()-center.getxCoordinate();
		double yDifference = newCenter.getyCoordinate()-center.getyCoordinate();
		center = newCenter;
		for(int index=0; index<pointsOnCircle.size(); index++){
			double oldX = pointsOnCircle.get(index).getxCoordinate();
			double oldY = pointsOnCircle.get(index).getyCoordinate();
			pointsOnCircle.get(index).setxCoordinate(oldX+xDifference);
			pointsOnCircle.get(index).setyCoordinate(oldY+yDifference);
		}
	}
	
	public double getRadius(){
		return Math.sqrt(Math.pow(pointOnCircle.getxCoordinate()-center.getxCoordinate(), 2)+Math.pow(pointOnCircle.getyCoordinate()-center.getyCoordinate(), 2));
	}
	
	//returns the angle, in standard position, whose ray goes through the given point (IOW, the point does not have to be on the circle)
	public double getStandardPositionAngle(CoordinatePoint p, boolean inDegrees){
		double distanceFromCenter = DrawableOps.getDistance(p, getCenter());
		double sine=(p.getyCoordinate()-getCenter().getyCoordinate())/distanceFromCenter;
		double cosine=(p.getxCoordinate()-getCenter().getxCoordinate())/distanceFromCenter;
		double angleInRadians;
		if(sine>=0 && cosine>=0){
			angleInRadians=Math.asin(sine);
		}else if(sine>=0 && cosine<0){
			angleInRadians=Math.PI-Math.asin(sine);
		}else if(sine<0 && cosine<0){
			angleInRadians=Math.PI-Math.asin(sine);
		}else{
			angleInRadians=2*Math.PI+Math.asin(sine);
		}
		if(inDegrees){
			double angleInDegrees = angleInRadians*180/Math.PI;
			return angleInDegrees;
		}else{
			return angleInRadians;
		}
	}
	
	public double[] getRadicalLineCoefficients(Circle circle2){
		double[] cofficients = new double[2];
		double r=getRadius();
		double s=circle2.getRadius();
		double a = getCenter().getxCoordinate();
		double b= getCenter().getyCoordinate();
		double c = circle2.getCenter().getxCoordinate();
		double d= circle2.getCenter().getyCoordinate();
		double m;
		double k;
		if((d-b)!=0){
			m = (a-c)/(d-b);
			k=(Math.pow(r, 2)-Math.pow(s, 2)+Math.pow(c, 2)-Math.pow(a, 2)+Math.pow(d, 2)-Math.pow(b, 2))/(2*(d-b));
		}else{
			m = DrawableOps.undefinedIdentifier;
			//r=radius, s=circle2 radius, 
			double u = Math.abs(a-c);
			if(a-c>0){
				k = (Math.pow(s, 2)-Math.pow(r, 2)-Math.pow(u, 2))/(2*u)+getCenter().getxCoordinate();
			}else{
				k = (-1)*(Math.pow(s, 2)-Math.pow(r, 2)-Math.pow(u, 2))/(2*u)+getCenter().getxCoordinate();
			}
		}
		cofficients[0]=m;
		cofficients[1]=k;
		return cofficients;
	}
	
	//will always return two solutions or no solutions (IOW, will return trivial solutions, in order from least to greatest)
	public CoordinatePoint[] getLineCircleSolution(double m, double b){
		System.out.println("Attempting to solve system:\n"+getRadius()+"^2=(x-"+getCenter().getxCoordinate()+")^2+(y-"+getCenter().getyCoordinate()+")^2\ny="+m+"x+"+b);
		if(m==DrawableOps.undefinedIdentifier){
			double rSquared = Math.pow(getRadius(),2);
			double xQuantitySquared = Math.pow(b-getCenter().getxCoordinate(),2);
			double sqrt=Math.sqrt(rSquared-xQuantitySquared);
			CoordinatePoint [] solution ={				
					new CoordinatePoint(b, getCenter().getyCoordinate()+sqrt),
					new CoordinatePoint(b, getCenter().getyCoordinate()-sqrt)
			};
			return solution;
		}else{
			double h = getCenter().getxCoordinate(); 
			double k = getCenter().getyCoordinate();
			double r = getRadius();
			double x=Math.pow(m, 2)+1;
			double y=2*m*(b-k)-2*h;
			double z=Math.pow(h, 2)+Math.pow((b-k),2)-Math.pow(r, 2);
			//cases for quadratic formula
			double determinant = Math.pow(y, 2)-4*x*z;
			if(determinant==0){
				double x1 =-y/(2*x);
				CoordinatePoint[] solution ={
						new CoordinatePoint(x1, m*x1+b),
						new CoordinatePoint(x1, m*x1+b)
				};
				return solution;
			}else if(determinant>0){
				double x1=(-y+Math.sqrt(determinant))/(2*x);
				double x2=(-y-Math.sqrt(determinant))/(2*x);
				if(x1<x2){//assures that the smaller x value is returned first
				CoordinatePoint [] solution ={
						new CoordinatePoint(x1, m*x1+b),
						new CoordinatePoint(x2, m*x2+b)
				};
				return solution;
				}else{
					CoordinatePoint [] solution ={
							new CoordinatePoint(x2, m*x2+b),
							new CoordinatePoint(x1, m*x1+b)
					};
					return solution;
				}
			}else{//imaginary solution (no intersection)
				return null;
			}	
		}
	}
	
	public CoordinatePoint getPointOnCircle(double angle, boolean inDegrees){
		if(inDegrees){
			angle = (double)angle*Math.PI/180;
		}
		double xValue = getRadius()*Math.cos(angle);
		double yValue = getRadius()*Math.sin(angle);
		CoordinatePoint point= new CoordinatePoint(xValue+getCenter().getxCoordinate(), yValue+getCenter().getyCoordinate());
		return point;
	}
	
	public CoordinatePoint getRandomPointOnCircle(double angleMin, double angleMax, boolean inDegrees){
		double randomAngle = Math.random()*(angleMax-angleMin)+angleMin;
		return getPointOnCircle(randomAngle, inDegrees);
	}
}
