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
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;


public class MultiplyComplexNumbers extends Type{

private String[] variationsOnInstructions= {"Simplify the expression to a + bi form:"};
	
	
	public MultiplyComplexNumbers(){
	instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
	numberOfColumns=2;
	verticalSpacing=120;
	whetherInstructionsAreNeverIncluded=false;
	scaleFactor = .40;
}

	public MultiplyComplexNumbers(int difficulty){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=120;
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor = .40;
		getInstance(difficulty);

	}
	
	private void getInstance(int difficulty) {
		int realNumCoef;
		int imagNumCoef;
		int realDenCoef;
		int imagDenCoef;
		
		//makes distinct coefficients
		realNumCoef = Ops.randomInt(1, 5*difficulty);
		realDenCoef = Ops.randomInt(1, 5*difficulty);
		while (realNumCoef==realDenCoef) realDenCoef = Ops.randomInt(1, 5*difficulty);
		//possibility of the coefs. being negative
		if (Math.random()>.5) realNumCoef = realNumCoef*(-1);
		if (Math.random()>.5) realDenCoef = realDenCoef*(-1);
		
		//makes distinct coefficients
		imagNumCoef = Ops.randomInt(1, 5*difficulty);
		imagDenCoef = Ops.randomInt(1, 5*difficulty);
		while (imagNumCoef==imagDenCoef) imagDenCoef = Ops.randomInt(1, 5*difficulty);
		//possibility of the coefs. being negative
		if (Math.random()>.5) imagNumCoef = imagNumCoef*(-1);
		if (Math.random()>.5) imagDenCoef = imagDenCoef*(-1);
		
		Term numReal = new Term(realNumCoef);
		Term denReal = new Term(realDenCoef);
		Term numImag = new Term(imagNumCoef);
		numImag.makeImaginary(1);
		Term denImag = new Term(imagDenCoef);
		denImag.makeImaginary(1);
		
		Term[] numeratorTerms = {numReal, numImag};
		Term[] denominatorTerms = {denReal, denImag};
		
		
		question = "\\left(" + Format.termArrayToString(numeratorTerms) + "\\right)\\left(" + Format.termArrayToString(denominatorTerms) + "\\right)";
		answer = "No method has been written yet to simplify powers of i";
	}
	
}
