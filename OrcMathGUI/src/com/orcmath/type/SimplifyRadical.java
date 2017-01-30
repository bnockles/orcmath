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


import com.orcmath.objects.Ops;
import com.orcmath.objects.SimplestRadicalForm;
import com.orcmath.objects.Term;

public class SimplifyRadical extends Type {

	private String[] variationsOnInstructions= {"Write in simplest radical form.", "Simplify.", "Simplify the following radical expression."};
	
	public SimplifyRadical(){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=100;
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor = .35;
	}

	public SimplifyRadical(int difficulty){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=100;
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor = .35;
		SimplestRadicalForm radical=getInstance(difficulty);
		question = radical.getRepresentationBeforeSimplification();
		answer = radical.toString();
	}

	private SimplestRadicalForm getInstance(int difficulty) {
		int rootMax = difficulty;
		if (rootMax==1) rootMax=2;
		int root = Ops.randomInt(2, rootMax);//this makes the root anywhere between 2 and 4, depending on the difficulty. The easiest problems are always root 2
		
		//this part creates the coefficient that can be simplified. Variables are created in next Term declaration
		int coefficientMin = 2;
		int coefficientMax = 6+difficulty;
		Term simplifiableTerm = new Term(Ops.randomInt(coefficientMin, coefficientMax));
		//System.out.println("The simplifiable term = " + simplifiableTerm);
		
		//this part creates the term that will (probably) remain under the radical(randomization may create a term that can be simplified)
		coefficientMax = 7;
		int numberOfExponents=difficulty;
		int exponentMin = 1;
		int exponentMax = 4*difficulty;
		Term nonSimplifiableTerm = randomTerm(coefficientMin, coefficientMax, numberOfExponents, exponentMin, exponentMax);
		//System.out.println("The \"nonsimplifiable\" term = " + nonSimplifiableTerm);
		
		SimplestRadicalForm termToBeSimplified = new SimplestRadicalForm(root, Ops.multiplyTerms(Ops.pow(simplifiableTerm, root),nonSimplifiableTerm));
		
		return termToBeSimplified;
	}
	
}
