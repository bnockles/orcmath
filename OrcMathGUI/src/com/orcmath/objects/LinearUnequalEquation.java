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

//creates two sides of a linear equation where one side is not equal to the other
//FOR EXAMPLE, one side is twice the other in value (usages in geometry)
//actual value1
//actual value2
//variable character
//value of x
//whether or not there is a variable on both sides
public class LinearUnequalEquation extends LinearEquation{

	private int actualValue2;
	
	public LinearUnequalEquation(int actualValue1, int actualValue2, char variable, int solution, boolean variableOnBothSides){	
		this.actualValue=actualValue1;
		x=variable;
		this.solution=solution;
		this.variableOnBothSides=variableOnBothSides;
		leftSide = randomExpression(actualValue1);
		if (!variableOnBothSides){
			Term term = new Term(actualValue2);
			Term[] termArray = {term};
			rightSide=new Expression(termArray);
		}
		else{
			rightSide = randomDistinctExpression(actualValue2, leftSide);
		}

	}
	
	public int getActualValue2(){
		return actualValue2;
	}
	
}
