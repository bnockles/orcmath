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

public class CongruentValues {

	int solution;
	int actualValue;
	int type;
	char x;//this is the variable that is used in the equation
	Equation equation;
	
	
	public static int LINEAR = 0;
	public static int QUADRATIC = 1;
	
	public CongruentValues(int actualValue, int type, boolean variableOnBothSides){
		this.actualValue=actualValue;
		solution = Ops.randomInt(1, 10);
		this.type=type;
		x=Variable.randVar();
		if (type==LINEAR){
			equation = new LinearEquation(actualValue, x, solution, variableOnBothSides);
		}
		else if(type ==QUADRATIC){
			equation = new QuadraticEquation(actualValue, x, solution, variableOnBothSides);
		}
	}
	
	public Equation getEquation(){
		return equation;
	}
	
	public String getVariable(){
		return ""+x;
	}
}
