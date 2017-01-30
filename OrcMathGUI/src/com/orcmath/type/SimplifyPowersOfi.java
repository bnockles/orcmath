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

import java.util.ArrayList;

import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Procedure;
import com.orcmath.objects.Term;


public class SimplifyPowersOfi extends Type{

	private String[] variationsOnInstructions= {"Simplify by writing the following expression as a complex number \nin the form a + bi"};
	
	public SimplifyPowersOfi(){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=150;
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor = .40;
	}

	public SimplifyPowersOfi(int difficulty){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=150;
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor = .40;
		getInstance(difficulty);

	}
	
	private void getInstance(int difficulty) {
		Term[] powersOfi = new Term[difficulty+2];
		for (int numberOfTerms = 0; numberOfTerms<powersOfi.length; numberOfTerms++ ){
			Term term = new Term(Ops.randomInt(1, difficulty*8));
			if (Math.random()>5) term.setSign(false);
			term.makeImaginary(Ops.randomInt(1, difficulty*25));
			powersOfi[numberOfTerms]=term;
		}
		
		question = Format.termArrayToString(powersOfi);
		
		for (int numberOfTerms = 0; numberOfTerms<powersOfi.length; numberOfTerms++ ){
			powersOfi[numberOfTerms] = Procedure.simplifyImaginary(powersOfi[numberOfTerms]);
		}
		System.out.println(Format.termArrayToString(powersOfi));
		Term[] answerArray = Ops.combineLikeTerms(powersOfi);
		
		answer  = Format.termArrayToString(answerArray);
		//answer = "\\text{No method has been written yet to simplify powers of i}";
	}
	
}
