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
package com.orcmath.objects;

public class Angle {
	double measure;
	String name;
	Segment side1;
	Segment side2;
	Point vertex;
	
	
	
	public Angle(Point point1, Point vertex, Point point2, double measure ){
		name = point1.toString()+vertex.toString()+point2.toString();
		side1 = new Segment(point1, vertex);
		this.vertex=vertex;
		side1 = new Segment(point2, vertex);
		this.measure=measure;
	}

	public Angle(Segment s1, Segment s2, double angleMeasure){
		side1=s1;
		side2=s2;
		try {
			vertex = side1.determineCommonPoint(side2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String side1NameWithoutVertex = side1.toString().replaceAll(vertex.toString(), "");
		String side2NameWithoutVertex = side2.toString().replaceAll(vertex.toString(), "");
		name = side1NameWithoutVertex+vertex.toString()+side2NameWithoutVertex;
		measure = angleMeasure;
	}
	
	public double getMeasure(){
		return measure;
	}
	
	public String toString(){
		return name;
	}
}
