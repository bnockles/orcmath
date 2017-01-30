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

public class AlgebraicExpression {

	int actualValue;
	int solution;
	char variable;
	boolean isQuadratic;
	
	Expression expression;
	
	public AlgebraicExpression(int value, int xValue, char variable, boolean isQuadratic){
		actualValue=value;
		solution=xValue;
		this.variable=variable;
		this.isQuadratic=isQuadratic;
		
		if (!isQuadratic){//for linear expressions
			int coefficient = Ops.randomInt(-15, 15);
			while (coefficient==0){
				coefficient = Ops.randomInt(-15, 15);
			}
			Term variableTerm = new Term(coefficient,""+variable);
			int product = coefficient*solution;
			Term constantTerm = new Term(actualValue-product);
			Term[] terms = {variableTerm,constantTerm};
			expression=new Expression(terms);			
		}
		else{//constructing quadratic expressions
			int otherRoot = Ops.randomInt(-2*Math.abs(solution), 2*Math.abs(solution));
			Term[] roots ={new Term(xValue), new Term(otherRoot)};
			FactorablePolynomial polynomial= new FactorablePolynomial(roots, ""+variable);
			Term cTerm = Ops.addLikeTerms(polynomial.getCTerm(),new Term(actualValue));
			Term[] terms = {polynomial.getATerm(), polynomial.getBTerm(), cTerm};
			expression = new Expression(terms);
		}
	}

	public char getVariable() {
		return variable;
	}

	public void setVariable(char variable) {
		this.variable = variable;
	}

	public int getActualValue() {
		return actualValue;
	}

	public int getSolution() {
		return solution;
	}

	public boolean isQuadratic() {
		return isQuadratic;
	}

	public Expression getExpression() {
		return expression;
	}
}
