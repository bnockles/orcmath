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
package com.orcmath.type;

import com.orcmath.objects.FactorablePolynomial;
import com.orcmath.objects.Format;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class Factor extends Type{

	private int degree;
	String[] variationsOnInstructions= {"Factor."};
	
	//this constructor is used for formating from static files
	public Factor(){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=50;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
	}
	
	public Factor(int difficulty, boolean trueForIntegersOnly){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=50;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		degree = 2;
		getInstance(difficulty, false, trueForIntegersOnly);
		//question and answer is determined in the getInstance constructor
	}
	
	public Factor(int difficulty, boolean trueForIntegersOnly, boolean trueForGCFOnly, int maximumDegree){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=80;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		if(trueForGCFOnly){
			degree = Ops.randomInt(1, maximumDegree+3);
		}else{
			degree = Ops.randomInt(3, maximumDegree+3);
		}
		getInstance(difficulty, trueForGCFOnly, trueForIntegersOnly);
		//question and answer is determined in the getInstance constructor
	}
	

	public void getInstance(int diff, boolean trueForGCFOnly, boolean integerRoots){
		//roots is a term array because only terms can be integers or rationals or radicals, for that matter
		Term[] roots = new Term[degree];
		for (int rootIndex = 0; rootIndex < roots.length; rootIndex++){
			Term aRoot;
			Term zeroRoot= new Term(0);
			//problems only have 15% chance of having a root zero
			if(trueForGCFOnly){
				if(rootIndex<degree-1){
					aRoot = new Term(0);	
				}else{
					aRoot = new Term(Ops.randomNotZero(-9, 9));
				}
			}
			else if (rootIndex==0 && Math.random()<.15) aRoot = zeroRoot;
			else {
				//first and second difficulty: the first root is an integer
				//or if integerRoots is true
				if ((diff == 1 && rootIndex==0) || integerRoots){
					int theRootValue = Ops.randomNotZero(diff*-3-1, diff*3+1);
					//15% of the time the polynomial is a difference of squares
					if (rootIndex ==1 && Math.random()<.15) theRootValue = roots[0].getCoefficient() * (-1);
					aRoot = new Term (theRootValue);
				}
				//at the 3rd and 4th difficulty, there is a chance of having a rational root
				else{
					int rootNumerator;
					if (rootIndex==0) rootNumerator = Ops.randomInt(1, diff*2+1);
					else rootNumerator = Ops.randomInt(2, diff*2+1);
					//50% of the time the root is negative
					if (Math.random()>.5) rootNumerator = rootNumerator * (-1);
					//20% of the time the polynomial is a difference of squares
					if (rootIndex ==1 && Math.random()<.2) rootNumerator = roots[0].getCoefficient() * (-1);
					int rootDenominator = Ops.randomInt(2, diff*2+1);
					Fraction theRootValue = new Fraction (rootNumerator, rootDenominator);
					aRoot = new Term (theRootValue);
				}
			}
			roots[rootIndex]=aRoot;
		}
		String variable ="" + Variable.randVar();
		FactorablePolynomial factorThis = new FactorablePolynomial(roots, variable);
		int scalar = Ops.randomNotZero(-12, 12);
		if(trueForGCFOnly){
			question = Format.termArrayToString(Ops.distribute(new Term(scalar), factorThis.getPolynomial()));
			answer = scalar+factorThis.factoredToString();	
		}else{
			question = factorThis.polynomialToString();
			answer = factorThis.factoredToString();	
		}
	}
}
