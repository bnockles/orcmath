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

//creates two sides of a linear equation from input:
//actual value
//variable character
//value of x
//whether or not there is a variable on both sides
public class LinearEquation extends Equation{


	public LinearEquation(){
	}
	
	public LinearEquation(int actualValue, char variable, int solution, boolean variableOnBothSides){
		this.actualValue=actualValue;
		x=variable;
		this.solution=solution;
		this.variableOnBothSides=variableOnBothSides;
		leftSide = randomExpression(actualValue);
		if (!variableOnBothSides){
			Term term = new Term(actualValue);
			Term[] termArray = {term};
			rightSide=new Expression(termArray);
		}
		else{
			rightSide = randomDistinctExpression(actualValue, leftSide);
		}

	}

	public Expression randomExpression(int actualValue){
		int coefficient = Ops.randomInt((int)(actualValue/(-2)), (int)(actualValue/2));
		while (coefficient==0 || coefficient*solution>120 || coefficient*solution<-120){
			coefficient = Ops.randomInt((int)(actualValue/(-2)), (int)(actualValue/2));
		}
		int product = coefficient*solution;
		int constant = actualValue-product;
		Term variable = new Term(coefficient,""+x);
		Term constantTerm = new Term(constant);
		if(coefficient>0){
			Term[] expression = {variable, constantTerm};
			return new Expression(expression);
		}
		else{
			Term[] expression = {constantTerm,variable};
			return new Expression(expression);
		}
	}

	//returns an expression that is NOT identical to the input
	public Expression randomDistinctExpression(int value, Expression e){
		Expression f = randomExpression(value);
		while (f.equals(e)){
			f=randomExpression(value);
		}
		return f;
	}
	

	
}
