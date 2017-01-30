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

public class Interval {
	double[] restrictions;
	double min;
	double max;
	boolean minInclusive;
	boolean maxInclusive;
	String setOfNumbers;


	public Interval(String typeOfNumbers){
		if (typeOfNumbers.equals("real") ||
				typeOfNumbers.equals("integer") ||
				typeOfNumbers.equals("rational") ||
				typeOfNumbers.equals("complex"))
		{
			setOfNumbers=typeOfNumbers;
		}
		else{System.out.println("An invalid number type was entered");}
	}

	public void addMinMax(double min, boolean inclusiveMin, double max, boolean inclusiveMax){
		this.min=min;
		this.max=max;
		minInclusive= inclusiveMin;
		maxInclusive= inclusiveMax;
	}

	public void setRestrictions(double[] restrictions){
		this.restrictions=restrictions;
	}

	public String getIntervalNotation(){
		String s="";
		if(minInclusive) s+= "["+min+","+max;
		else s+= "("+min+","+max;
		if(maxInclusive) s+="]";
		else s+= ")";
		return s;
	}

}