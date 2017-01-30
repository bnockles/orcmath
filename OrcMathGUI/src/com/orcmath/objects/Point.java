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

import java.util.ArrayList;

public class Point {
	String label;
	ArrayList<Segment> segmentContainers;
	ArrayList<Plane> planeContainers;
	
	public Point(String s){
		label=s;
	}
	
	public void addSegmentContainer(Segment containsThisPoint){
		segmentContainers.add(containsThisPoint);
	}

	public void addPlaneContainer(Plane containsThisPoint){
		planeContainers.add(containsThisPoint);
	}
	
	public void setLabel(String label){
		this.label =label; 
	}
	
	public String toString(){
		return label;
	}
	
	public ArrayList<Segment> getContainingSegments(){
		return segmentContainers;
	}
	
	public ArrayList<Plane> getContainingPlanes(){
		return planeContainers;
	}
}
