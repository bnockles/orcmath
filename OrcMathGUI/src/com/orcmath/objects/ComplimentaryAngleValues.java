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


//creates two expressions that add to equal 90�:
//actual value of smaller angle

//randomly generates:
//variable character
//value of x

public class ComplimentaryAngleValues {

	int valueOfSmallerAngle;
	int valueOfLargerAngle;
	int xValue;
	int coefficient1;
	int constant1;
	int coefficient2;
	int constant2;
	char x;
	
	
	
	public ComplimentaryAngleValues(int valueOfSmallerAngle){
		this.valueOfSmallerAngle=valueOfSmallerAngle;
		xValue = Ops.randomInt(2, 2*(int)(Math.sqrt(valueOfSmallerAngle)));
		coefficient1 = Ops.randomInt(2, (int)(valueOfSmallerAngle/3));
		int product1 = coefficient1*xValue;
		constant1 = valueOfSmallerAngle-product1;
		
		valueOfLargerAngle = 90-valueOfSmallerAngle;
		coefficient2 = Ops.randomInt(2, (int)(valueOfLargerAngle/3));
		//use if you don't want the coefficients to be the same
		//while (coefficient2==coefficient1) coefficient2 = Ops.randomInt(2, (int)(Math.sqrt(valueOfLargerAngle)));
		int product2 = coefficient2*xValue;
		constant2 = valueOfLargerAngle-product2;	
		
		x = Variable.randVar();
	}

	public String smallerAngleExpression(){
		String s = "" + coefficient1 + x;
		if (constant1>0) s += " + " + constant1;
		else if (constant1==0);
		else {
			int absb = constant1 * (-1);
			s += " - " + absb;
		}
		return s;
	}

	public String largerAngleExpression(){
		String s = "" + coefficient2 + x;
		if (constant2>0) s += " + " + constant2;
		else if (constant2==0);
		else {
			int absb = constant2 * (-1);
			s += " - " + absb;
		}
		return s;
	}
	
	public int getValueOfSmallerAngle() {
		return valueOfSmallerAngle;
	}



	public char getX() {
		return x;
	}

	public int getValueOfLargerAngle() {
		return valueOfLargerAngle;
	}



	public int getxValue() {
		return xValue;
	}



	public int getCoefficient1() {
		return coefficient1;
	}



	public int getConstant1() {
		return constant1;
	}



	public int getCoefficient2() {
		return coefficient2;
	}



	public int getConstant2() {
		return constant2;
	}

	
	
}
