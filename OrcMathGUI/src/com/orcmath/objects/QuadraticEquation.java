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

//creates two sides of a quadratic equation from input:
//actual value
//variable character
//value of x
//whether or not there is a variable on both sides
public class QuadraticEquation extends Equation{

	FactorablePolynomial whenSimplified;
	
	public QuadraticEquation(int actualValue, char variable, int solution, boolean variableOnBothSides) {
		this.actualValue=actualValue;
		x= variable;
		this. solution=solution;
		this.variableOnBothSides=variableOnBothSides;
		Term t1=new Term(solution);
		Term t2 = new Term(Ops.randomInt((-3)*solution, 3*solution));
		Term[] roots = {t1,t2};
		whenSimplified=new FactorablePolynomial(roots, ""+x);
		if(!variableOnBothSides){
			determineSimpleLeftAndRightSide();
		}
		else{
			determineLeftAndRightSide(true);
		}
	}
	
	public QuadraticEquation(int actualValue, char variable, int solution, boolean variableOnBothSides, boolean nonTrivialCoefficient) {
		this.actualValue=actualValue;
		x= variable;
		this. solution=solution;
		this.variableOnBothSides=variableOnBothSides;
		Term t1=new Term(solution);
		Term t2 = new Term(Ops.randomInt((-3)*solution, 3*solution));
		Term[] roots = {t1,t2};
		whenSimplified=new FactorablePolynomial(roots, ""+x);
		if(!variableOnBothSides){
			determineSimpleLeftAndRightSide();
		}
		else{
			determineLeftAndRightSide(nonTrivialCoefficient);
		}
	}
	
	
	private void determineSimpleLeftAndRightSide() {
		Term rightTerm = new Term(actualValue);
		Term[] rightTerms = {rightTerm};
		rightSide=new Expression(rightTerms);	
		Term leftCTerm = Ops.addLikeTerms(rightTerm, whenSimplified.getCTerm());
		Term[] leftTerms = {whenSimplified.getATerm(),whenSimplified.getBTerm(), leftCTerm};
		leftSide=new Expression(leftTerms);
		
	}


	public void determineLeftAndRightSide(boolean nonTrivialCoefficient){
		//choose an A term that doesn't produce a ridiculously large product
		int rightACoefficient=1;
		if(nonTrivialCoefficient){
			rightACoefficient = Ops.randomInt(-4, 4);
		}
		
		//if the solution is more than 6, then a is not more than 2.
		//This helps avoid constants that are too large
		while (rightACoefficient==0 || rightACoefficient==-1 || (solution>6 && Math.abs(rightACoefficient)>2)){
			rightACoefficient = Ops.randomInt(-4, 4);
			System.out.println("In loop 1 (class:Quadratic Equation)");
		}
		int aResult = solution*solution*rightACoefficient;		
		Term rightATerm = new Term(rightACoefficient,x+"^2");
			
		int rightBCoefficient = Ops.randomInt(-actualValue, actualValue);
		int bResult = aResult + solution*rightBCoefficient;	
		//if the result after b is far (more than 12*solution) units above the actual value, b is adjusted
		int lowestBCoef=rightBCoefficient;
		int count=0;
		while (Math.abs(actualValue-bResult)>12*solution && count<10){
			rightBCoefficient = Ops.randomInt(-actualValue, actualValue);
			bResult = aResult + solution*rightBCoefficient;	
			if(rightBCoefficient<lowestBCoef)lowestBCoef=rightBCoefficient;
			count++;
			System.out.println("a="+rightACoefficient+", b=" + rightBCoefficient +". In loop 2 (class:Quadratic Equation)");
		}
		if(count==10){
			rightBCoefficient = lowestBCoef;
			bResult = aResult + solution*rightBCoefficient;	
		}
		Term rightBTerm = new Term(rightBCoefficient,""+x);
		
		//cTerm is made of the left overs
		Term rightCTerm = new Term(actualValue-bResult);
		Term[] rightTerms = {rightATerm,rightBTerm, rightCTerm};
		rightSide=new Expression(rightTerms);
//		System.out.println(Format.termArrayToString(whenSimplified.getPolynomial())+"<---factorable");
//		System.out.println(Format.termArrayToString(rightTerms)+"<---added to both sides");
		Term leftATerm = Ops.addLikeTerms(rightATerm, whenSimplified.getATerm());
		Term leftBTerm = Ops.addLikeTerms(rightBTerm, whenSimplified.getBTerm());
		Term leftCTerm = Ops.addLikeTerms(rightCTerm, whenSimplified.getCTerm());
		Term[] leftTerms = {leftATerm,leftBTerm, leftCTerm};
//		System.out.println(Format.termArrayToString(leftTerms)+"<---result");
		leftSide=new Expression(leftTerms);
	}
	
}
