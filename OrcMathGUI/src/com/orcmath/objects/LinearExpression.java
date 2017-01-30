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
//returns an expression in terms of a given x that yields the actual value, which can be a decimal
public class LinearExpression {

	private int solution;
	private char x;
	private int actualValue;
	Expression expression;
	
	public LinearExpression(int xValue, char variable, int actualValue, boolean containsAVariable){
		if(containsAVariable)
		solution=xValue;
		x=variable;
		this.actualValue=actualValue;
		if(containsAVariable)expression = randomExpression(true);
		else{
			Term[] oneTerm = {new Term(actualValue)};
			expression = new Expression(oneTerm);
		}
	}
	
	public LinearExpression(int xValue, char variable, int actualValue, boolean containsAVariable, boolean nonTrivialCoefficient){
		if(containsAVariable)
		solution=xValue;
		x=variable;
		this.actualValue=actualValue;
		if(containsAVariable)expression = randomExpression(nonTrivialCoefficient);
		else{
			Term[] oneTerm = {new Term(actualValue)};
			expression = new Expression(oneTerm);
		}
	}
	
	public Expression randomExpression(boolean nonTrivialCoefficient){
		int coefficient;
		if(nonTrivialCoefficient){
			if(actualValue>40){
				coefficient= Ops.randomInt(-12, 12);
			}else{
				coefficient= Ops.randomInt(-5, 5);
			}
		}else{
			coefficient=1;
		}
		while ((coefficient==0 || Math.abs(coefficient*solution)>120) && Math.abs(solution)<120){
//			System.out.println("Stuck in the randomExpression while loop in LinearExpression class. Coefficient is "+coefficient);
			coefficient = Ops.randomInt(-10, 10);
		}
		int product = coefficient*solution;		
		int constant = actualValue-product;
		Term variable = new Term(coefficient,""+x);
		Term constantTerm = new Term(constant);
		if(coefficient>0){	
			Term[] expression; 
			if(constantTerm.getCoefficient()!=0){
				expression = new Term[2];
				expression[0] = variable;
				expression[1] = constantTerm;
			}else{
				expression = new Term[1];
				expression[0] = variable;
			}
			return new Expression(expression);
		}
		else{
			Term[] expression; 
			if(constantTerm.getCoefficient()!=0){
				expression = new Term[2];
				expression[0] = constantTerm;
				expression[1] = variable;
			}else{
				expression = new Term[1];
				expression[0] = variable;
			}
			return new Expression(expression);
		}
	}

	public int getSolution() {
		return solution;
	}

	public void setSolution(int solution) {
		this.solution = solution;
	}

	public int getActualValue() {
		return actualValue;
	}

	public void setActualValue(int actualValue) {
		this.actualValue = actualValue;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	
}
