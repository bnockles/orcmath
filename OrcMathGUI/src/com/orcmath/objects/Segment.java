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

public class Segment {
		String name;
		double measure;
		Point endpoint1;
		Point endpoint2;
		ArrayList<Plane> planeContainers;
		
		public Segment(Point point1, Point point2, double length){
			endpoint1=point1;
			endpoint2=point2;
			name = endpoint1.toString()+endpoint2.toString();
			measure = length;
			endpoint1.addSegmentContainer(this);
			endpoint2.addSegmentContainer(this);
		}
		
		public Segment(Point point1, Point point2){
			endpoint1=point1;
			endpoint2=point2;
			name = endpoint1.toString()+endpoint2.toString();
			endpoint1.addSegmentContainer(this);
			endpoint2.addSegmentContainer(this);
		}

		
		@SuppressWarnings("null")
		public Point determineCommonPoint(Segment other) throws Exception{
			if (other.getEndpoint1().getContainingSegments().contains(this)){
				return other.getEndpoint1();
			}
			else if(other.getEndpoint2().getContainingSegments().contains(this)){
				return other.getEndpoint2();
			}
			else{
				Exception NullPointerException = null;
				throw NullPointerException;
			}
		}
		
		public double getMeasure() {
			return measure;
		}

		public void setMeasure(double measure) {
			this.measure = measure;
		}

		public Point getEndpoint1() {
			return endpoint1;
		}

		public Point getEndpoint2() {
			return endpoint2;
		}
		
		public String toString(){
			return name;
		}
		
	}
