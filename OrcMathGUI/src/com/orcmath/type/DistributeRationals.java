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

import com.orcmath.objects.Format;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Polynomial;
import com.orcmath.objects.Variable;

public class DistributeRationals extends Type {
	
	private String[] variationsOnInstructions= {"Expand.", "Distribute."};

	public DistributeRationals() {
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=110;
		scaleFactor = .32;
		whetherInstructionsAreNeverIncluded=false;
	}
	
	public DistributeRationals(int difficulty) {
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=110;
		scaleFactor = .32;
		whetherInstructionsAreNeverIncluded=false;
		getInstance(difficulty);
	}
	
	
	private void getInstance(int difficulty){
		//picks a letter that will be variables in the expression
		char variable = Variable.randVar();	
		
		//sets up first polynomial
		int numberOfTermsInFirstPolynomial = 1;
		if (difficulty == 2 || difficulty==3) numberOfTermsInFirstPolynomial=2;
		else if (difficulty == 4) numberOfTermsInFirstPolynomial=3;
		
			//I may want the degree to be equal to the number of terms. i.e. x^2 + x
		boolean increaseDegree = false; 
		if (Math.random()>.5) increaseDegree=true;
		Fraction[] polynomial1coefficients = new Fraction[numberOfTermsInFirstPolynomial];
		for (int count = 0; count < numberOfTermsInFirstPolynomial; count ++){
			int numerator;
			if (difficulty==1) numerator = Ops.randomInt(2, 4);
			else numerator = Ops.randomInt(1, 2*difficulty+2);
			double positiveOrNegative = Math.random();
			//if (positiveOrNegative>.5) 
				numerator = numerator*(-1);
			System.out.println("This numerator selected:" + numerator);	
			int denominator;
			if (difficulty==1) {
				denominator = Ops.randomInt(2, 5);
				while (numerator == denominator || numerator == (denominator * (-1))) denominator = Ops.randomInt(2, 5);
			}
			else denominator = Ops.randomInt(1, 2*difficulty+2);
			while (denominator == 7) denominator = Ops.randomInt(1, 2*difficulty+2);
			polynomial1coefficients[count] = new Fraction (numerator, denominator);
		}
		Polynomial firstPolynomial= new Polynomial(polynomial1coefficients, variable);
		if (increaseDegree) firstPolynomial.increaseDegree(variable, 1);
		
		//sets up second polynomial
		int numberOfTermsInSecondPolynomial = 2;
		if (difficulty == 3 || difficulty==4) numberOfTermsInSecondPolynomial=3;
		Fraction[] polynomial2coefficients = new Fraction[numberOfTermsInSecondPolynomial];
		for (int count = 0; count < numberOfTermsInSecondPolynomial; count ++){
			int numerator = Ops.randomInt(1, 2*difficulty+2);
			double positiveOrNegative = Math.random();
			if (positiveOrNegative>.5) numerator = numerator*(-1);
			int denominator = Ops.randomInt(1, 2*difficulty+2);
			while (denominator == 7) denominator = Ops.randomInt(1, 2*difficulty+2);
			polynomial2coefficients[count] = new Fraction (numerator, denominator);
		}
		Polynomial secondPolynomial= new Polynomial(polynomial2coefficients, variable);
		

		question = "\\left(" + Format.termArrayToString(firstPolynomial.getTermArray()) + "\\right)\\left(" + Format.termArrayToString(secondPolynomial.getTermArray()) + "\\right)";
		
		//finds the simplified answer:
		answer = Format.termArrayToString(Ops.combineLikeTerms(Ops.distribute(firstPolynomial.getTermArray(), secondPolynomial.getTermArray())));

	}
	
}
