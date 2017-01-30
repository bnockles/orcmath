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


/**
 * @author bnockles
 * This class generates the expressions of an equation that correctly exhibits the relationship determined by the Pythagoran theorem 
 * currently ALL QUADRATIC VARIATIONS are written such that a=1
 * It is recommended that in order to generate a quadratic variation where a>1, another constructor be written
 * where a boolean "aGreaterThan1" is declared as true
 */
public class PythagoreanEquation {

	int solution;
	char x;
	Term sideAActual;
	Term sideBActual;
	Term sideCActual;
	boolean sideAIsConstant;
	boolean sideBIsConstant;
	boolean sideCIsConstant;
	Expression sideA;
	Expression sideB;
	Expression sideC;
	boolean isQuadratic;
	boolean isPythagoreanTriple;
	
	int[] tripleA = {3,5,6,9,7,8,10};
	int[] tripleB = {4,12,8,12,24,15,24};
	int[] tripleC = {5,13,10,15,25,17,26};
	
	
	public PythagoreanEquation(boolean triple, boolean isQuadratic){
		sideAIsConstant=false;
		sideBIsConstant=false;
		sideCIsConstant=false;
		x=Variable.randVar();
		isPythagoreanTriple=triple;
		this.isQuadratic=isQuadratic;
		if (triple){//both of these question types are relatively easy
			//selects the actual values
			int selectionOfValues = Ops.randomInt(0, tripleA.length-1);
			double version = Math.random();//determines whether a should be the shorter side or b should
			solution = Ops.randomInt(1, tripleC[selectionOfValues]);//the solution is some x that is less than the largest side of the triangle
			Term variabTerm= new Term(""+x);
			int aValue;
			int bValue;
			int cValue;
			if(version<.5){
				aValue = tripleA[selectionOfValues];
				bValue = tripleB[selectionOfValues];
			}
			else{
				aValue = tripleB[selectionOfValues];
				bValue = tripleA[selectionOfValues];
			}
			cValue = tripleC[selectionOfValues];
			sideAActual=new Term(aValue);
			sideBActual=new Term(bValue);
			sideCActual=new Term(cValue);
			if(isQuadratic){
				int aConstant = aValue - solution;
				int bConstant = bValue - solution;
				int cConstant = cValue - solution;
				Term[] aTerms ={variabTerm,new Term(aConstant)};
				Term[] bTerms ={variabTerm,new Term(bConstant)};
				Term[] cTerms ={variabTerm,new Term(cConstant)};
				sideA=new Expression(aTerms);
				sideB=new Expression(bTerms);
				sideC=new Expression(cTerms);
			}
			else{
				double determinesWhichTermStaysTheSame = Math.random();//either term a or b stays as a constant
				if(determinesWhichTermStaysTheSame<.5){//here term 'A' stays the same
					sideAIsConstant=true;
					Term[] aTerms={sideAActual};
					int bConstant = bValue - solution;
					Term[] bTerms ={variabTerm,new Term(bConstant)};
					sideA=new Expression(aTerms);
					sideB=new Expression(bTerms);
				}
				else{//here term 'B' stays the same
					sideBIsConstant=true;
					Term[] bTerms={sideBActual};
					int aConstant = aValue - solution;
					Term[] aTerms ={variabTerm,new Term(aConstant)};
					sideA=new Expression(aTerms);
					sideB=new Expression(bTerms);
				}
				int cConstant = cValue - solution;
				Term[] cTerms ={variabTerm,new Term(cConstant)};
				sideC=new Expression(cTerms);
			}
		}
		else{//these questions range from medium to hard
			if(isQuadratic){//in both of these variations, the hypotenuse is a radical term
				sideCIsConstant=true;
				int aValue = Ops.randomInt(2, 10);
				int bValue = Ops.randomInt(2, 10);
				sideAActual=new Term(aValue);
				sideBActual=new Term(bValue);
				int scalar =1;
				makeAnEquationFromTwoKnownSides(aValue,bValue,scalar);
			}
			else{//in this variation, one side is radical and the others are algebraic
				double version = Math.random();//determines whether a should be the shorter side or b should
				if(version<.5){//side 'A' is the radical
					sideAIsConstant=true;
					int bValue = Ops.randomInt(2, 10);
					int cValue = Ops.randomInt(3, 15);
					sideBActual=new Term(bValue);
					sideCActual=new Term(cValue);
					while(cValue<=bValue || bValue==7 || cValue==7 || bValue==11 || cValue==11){
						cValue = Ops.randomInt(3, 15);
					}
					int scalar =1;
					boolean aIsRadical=true;
					makeAnEquationFromHypotenuseAndSide(aIsRadical, cValue, bValue, scalar);
				}
				else{//side 'B' is the radical
					sideBIsConstant=true;
					int aValue = Ops.randomInt(2, 10);
					int cValue = Ops.randomInt(3, 15);
					sideAActual=new Term(aValue);
					sideCActual=new Term(cValue);
					while(cValue<=aValue || aValue==7 || cValue==7 || aValue==11 || cValue==11){
						cValue = Ops.randomInt(3, 15);
					}
					int scalar =1;
					boolean aIsRadical=false;
					makeAnEquationFromHypotenuseAndSide(aIsRadical, cValue, aValue, scalar);
				}
			}
		}
	}


	private void makeAnEquationFromHypotenuseAndSide(boolean unknownSideIsSideA, int cValue, int sideValue, int scalar) {
		solution = Ops.randomInt(1, cValue);//the solution is some x that is less than the largest side of the triangle
		Term variabTerm= new Term(""+x);
		if(unknownSideIsSideA){
			sideAActual = new Term(new SimplestRadicalForm(2, cValue*cValue-sideValue*sideValue));
			Term[] aTerm = {sideAActual};
			sideA = new Expression(aTerm);
			
			int bConstant = sideValue - solution;
			Term[] bTerms ={variabTerm,new Term(bConstant)};
			sideB=new Expression(bTerms);
		}
		else{
			sideBActual=new Term(new SimplestRadicalForm(2, cValue*cValue-sideValue*sideValue));
			Term[] bTerm = {sideBActual};
			sideB = new Expression(bTerm);
			int aConstant = sideValue - solution;
			Term[] aTerms ={variabTerm,new Term(aConstant)};
			sideA=new Expression(aTerms);
		}		
		int cConstant = cValue - solution;
		Term[] cTerms ={variabTerm,new Term(cConstant)};
		sideC = new Expression(cTerms);	
	}


	private void makeAnEquationFromTwoKnownSides(int aValue, int bValue, int scalar) {	
		if (bValue>aValue){
			solution = Ops.randomInt(1, bValue);//the solution is some x that is less than the largest side of the triangle
		}
		else{
			solution = Ops.randomInt(1, aValue);//the solution is some x that is less than the largest side of the triangle	
		}
		int aConstant = aValue - solution;
		int bConstant = bValue - solution;
		Term variabTerm= new Term(""+x);
		Term[] aTerms ={variabTerm,new Term(aConstant)};
		Term[] bTerms ={variabTerm,new Term(bConstant)};
		sideA=new Expression(aTerms);
		sideB=new Expression(bTerms);
		//the hypotenuse is always represented as a constant
		Term[] termC = {new Term(new SimplestRadicalForm(2,aValue*aValue+bValue*bValue))};
		sideC = new Expression(termC);
	}


	
	public boolean isSideAConstant() {
		return sideAIsConstant;
	}


	public boolean isSideBConstant() {
		return sideBIsConstant;
	}


	public boolean isSideCConstant() {
		return sideCIsConstant;
	}


	public int getSolution() {
		return solution;
	}


	public char getX() {
		return x;
	}


	public Term getSideAActual() {
		return sideAActual;
	}


	public Term getSideBActual() {
		return sideBActual;
	}


	public Term getSideCActual() {
		return sideCActual;
	}


	public Expression getSideA() {
		return sideA;
	}


	public Expression getSideB() {
		return sideB;
	}


	public Expression getSideC() {
		return sideC;
	}


	public boolean isQuadratic() {
		return isQuadratic;
	}


	public boolean isPythagoreanTriple() {
		return isPythagoreanTriple;
	}


	public int[] getTripleA() {
		return tripleA;
	}


	public int[] getTripleB() {
		return tripleB;
	}


	public int[] getTripleC() {
		return tripleC;
	}
	
	
	
}
