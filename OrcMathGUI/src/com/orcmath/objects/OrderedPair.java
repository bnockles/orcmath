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

public class OrderedPair {
	Term xCoordinate;
	Term yCoordinate;
	int xIntValue;
	int yIntValue;
	boolean consistsOfIntegers;
	
	public OrderedPair(Term x, Term y){
		xCoordinate = x;
		yCoordinate = y;
		if (x.type.equals("constant") && y.type.equals("constant")){
			xIntValue = xCoordinate.getCoefficient();
			yIntValue = yCoordinate.getCoefficient();
			consistsOfIntegers=true;
		}
		else if (x.type.equals("rational") && y.type.equals("rational") 
				&& x.getDenominator().type.equals("constant")
				&& y.getDenominator().type.equals("constant")
				&& x.getDenominator().getCoefficient()==1
				&& y.getDenominator().getCoefficient()==1){
			xIntValue = xCoordinate.getCoefficient();
			yIntValue = yCoordinate.getCoefficient();
			consistsOfIntegers=true;
		}
	}
	
	public boolean consistsOfIntegerCoordinates(){
		return consistsOfIntegers;
	}
	
	public OrderedPair(int x, int y){
		Term xterm = new Term(x);
		Term yterm = new Term(y);
		xCoordinate = xterm;
		yCoordinate = yterm;
		xIntValue = x;
		yIntValue = y;
		consistsOfIntegers=true;
	}
	
	public String toString(){
		return "\\left(" +xCoordinate+","+yCoordinate+ "\\right)";
	}
	
	public Term getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(Term xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public Term getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(Term yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public int getXIntValue() {
		return xIntValue;
	}

	public int getYIntValue() {
		return yIntValue;
	}
	

	
}
