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

import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;
import com.orcmath.objects.SimplestRadicalForm;
import com.orcmath.objects.Term;

public class RationalizeDenominator extends Type{

	private String[] variationsOnInstructions= {"Simplify the following expression", "Write in simplest radical form"};
	
	public RationalizeDenominator(){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=100;
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor = .35;

	}
	
	public RationalizeDenominator(int difficulty){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=100;
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor = .35;
		getInstance(difficulty);

	}
	
	private void getInstance(int difficulty) {
		question = "\\frac{" + Format.termArrayToString(getNumerator(difficulty)) + "}{" + Format.termArrayToString(getRadicalExpression(difficulty)) + "}";
		answer = "\\text{No method has been written yet to generate an answer for this question type}";
		
	}
	
	private Term[] getNumerator(int difficulty){
		Term[] numeratorExpression;
		if (difficulty <= 2){
			numeratorExpression = new Term[1];
			int constant = Ops.randomInt(1, 10);
			if (Math.random()>.6){
				constant = constant *(-1);
			}
			Term term = new Term(constant); 
			numeratorExpression[0] = term;
			return numeratorExpression;
		}
		else if (difficulty==3){
			return getRadicalExpression(difficulty-1);
		}
		else {
			return getRadicalExpression(difficulty-1);
		}
	}
	
	private Term[] getRadicalExpression(int difficulty){
		Term[] radicalExpression;
		if (difficulty==1){
			int radicand = Ops.randomInt(2, 10);
			while (radicand==4 || radicand == 9 || radicand==8) radicand = Ops.randomInt(2, 10);
			SimplestRadicalForm radical = new SimplestRadicalForm(2, radicand);
			Term term = new Term(radical);
			radicalExpression = new Term[1];
			radicalExpression[0] = term;
			return radicalExpression;
		}
		else if (difficulty==2 || difficulty==3){
			radicalExpression = new Term[2];
	
			int constant = Ops.randomInt(1, 10);
			if (Math.random()>.6){
				constant = constant *(-1);
			}
			radicalExpression[0] = new Term (constant);
			
			int radicand = Ops.randomInt(2, 10);
			while (radicand==4 || radicand == 9 || radicand==8) radicand = Ops.randomInt(2, 10);
			SimplestRadicalForm radical = new SimplestRadicalForm(2, radicand);
			Term radicalPart = new Term(radical);
			int coefficient = Ops.randomInt(1, (-1+difficulty)*(-1+difficulty));
			if (Math.random()>.5){
				coefficient = coefficient*(-1);
			}
			Term coefficeintPart=new Term(coefficient);
			Term term = Ops.multiplyTerms(coefficeintPart, radicalPart);
			radicalExpression[1] = term;
			return radicalExpression;
		}
		else{
			radicalExpression = new Term[2];
			
			int radicand1 = Ops.randomInt(2, 11);
			while (radicand1==4 || radicand1 == 9 || radicand1==8) radicand1 = Ops.randomInt(2, 11);
			SimplestRadicalForm radical1 = new SimplestRadicalForm(2, radicand1);
			Term radicalPart = new Term(radical1);
			int coefficient1 = Ops.randomInt(2,7);
			if (Math.random()>.5){
				coefficient1 = coefficient1*(-1);
			}
			Term coefficeintPart=new Term(coefficient1);
			Term term1 = Ops.multiplyTerms(coefficeintPart, radicalPart);
			radicalExpression[0]=term1;
			
			int radicand2 = Ops.randomInt(2, 11);
			while (radicand2==4 || radicand2 == 9 || radicand2==8 || radicand2==radicand1) radicand2 = Ops.randomInt(2, 10);
			SimplestRadicalForm radical2 = new SimplestRadicalForm(2, radicand2);
			Term radicalPart1=new Term(radical2);
			int coefficient2 = Ops.randomInt(2,7);
			if (Math.random()>.5){
				coefficient2 = coefficient2*(-1);
			}
			Term coefficeintPart1=new Term(coefficient2);
			Term term2 = Ops.multiplyTerms(coefficeintPart1, radicalPart1);
			radicalExpression[1]=term2;
			
			return radicalExpression;
		}
	}
}
