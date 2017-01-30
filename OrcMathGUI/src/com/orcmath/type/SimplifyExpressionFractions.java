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
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class SimplifyExpressionFractions extends Type {
	
	private String[] variationsOnInstructions= {"Simplify the following expression.", "Simplify.", "Combine like terms."};

	public SimplifyExpressionFractions() {
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=110;
		scaleFactor = .32;
		whetherInstructionsAreNeverIncluded=false;
	}
	
	public SimplifyExpressionFractions(int difficulty) {
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=110;
		scaleFactor = .32;
		whetherInstructionsAreNeverIncluded=false;
		getInstance(difficulty);
		//in this constructor, question and answer are determined in the methods

	}
	
	
	private void getInstance(int difficulty){
		int selection = Ops.randomInt(1, 1);
		if (selection == 1) addSubtractPolynomials(difficulty);
		
	}
	
	private void addSubtractPolynomials(int diff){
		char variable = Variable.randVar();
		int numberOfTerms = 2;
		if (diff == 4 || diff==3) numberOfTerms=3;
		Fraction[] polynomial1coefficients = new Fraction[numberOfTerms];
		Fraction[] polynomial2coefficients = new Fraction[numberOfTerms];
		for (int count = 0; count < numberOfTerms; count ++){
			int numerator1 = Ops.randomInt(1, 2*diff+2);
			double positiveOrNegative1 = Math.random();
			if (positiveOrNegative1>.5) numerator1 = numerator1*(-1);
			int denominator1 = Ops.randomInt(1, 2*diff+2);
			int numerator2 = Ops.randomInt(1, 2*diff+2);
			double positiveOrNegative2 = Math.random();
			if (positiveOrNegative2>.5) numerator2 = numerator2*(-1);
			int denominator2 = Ops.randomInt(1, 2*diff+2);
			polynomial1coefficients[count] = new Fraction (numerator1, denominator1);
			polynomial2coefficients[count] = new Fraction (numerator2, denominator2);
		}
		Polynomial firstPolynomial= new Polynomial(polynomial1coefficients, variable);
		Polynomial secondPolynomial= new Polynomial(polynomial2coefficients, variable);
		double addOrSubtract = Math.random();
		String operation = "";
		if (addOrSubtract>.5) operation += " + ";
		else operation += " - ";
		question = "\\left(" + Format.termArrayToString(firstPolynomial.getTermArray()) + "\\right)" + operation + "\\left(" + Format.termArrayToString(secondPolynomial.getTermArray()) + "\\right)";
		
		//finds the simplified answer:

		if (operation.equals(" - ")){
			for (int index = 0; index < secondPolynomial.getTermArray().length; index ++){
				if (secondPolynomial.getTermArray()[index].getSign())
				secondPolynomial.getTermArray()[index].setSign(false);
				else secondPolynomial.getTermArray()[index].setSign(true);
			}
		}
		Term[] entireThing = new Term[numberOfTerms*2];
		for (int index = 0; index < firstPolynomial.getTermArray().length; index ++){
			entireThing[index]=firstPolynomial.getTermArray()[index];
		}
		for (int index = 0; index < secondPolynomial.getTermArray().length; index ++){
			entireThing[numberOfTerms+index]=secondPolynomial.getTermArray()[index];
		}
		System.out.println(Format.termArrayToString(entireThing));
		answer = Format.termArrayToString(Ops.combineLikeTerms(entireThing));

	}
	
}
