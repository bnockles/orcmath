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
import com.orcmath.objects.SimplestRadicalForm;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;


public class AddRadical extends Type{

	private String[] variationsOnInstructions= {"Simplify the following expression.", "Write in simplest radical form.","Write the following expression as a single term in simplest radical form."};
	
	public AddRadical(){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=100;
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor = .35;
	}
	
	public AddRadical(int difficulty){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=100;
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor = .35;
		getInstance(difficulty);
	}
	
	private void getInstance(int difficulty) {
		int rootMax = difficulty;
		if (rootMax==1) rootMax=2;
		int root = Ops.randomInt(2, rootMax);//this makes the root anywhere between 2 and 4, depending on the difficulty. The easiest problems are always root 2
		
		//this part creates the coefficient that can be simplified. Variables are created in next Term declaration
		int coefficientMin = 2;
		int coefficientMax = 6+difficulty-root*2;
		int numberOfExponents=difficulty;
		int minExp = 1;
		int maxExp = 4+difficulty;
		Term simplifiableTerm1 = randomTerm(coefficientMin, coefficientMax, numberOfExponents, minExp, maxExp);
		Term simplifiableTerm2 = Term.getCopy(simplifiableTerm1);
		simplifiableTerm2.setCoefficient(Ops.randomInt(coefficientMin, coefficientMax));
		//System.out.println("\nThe simplifiable term started as " + simplifiableTerm1);
		
		//takes pieces out of term 1 and keeps them as pieces that will go in front
		char[] useTheseVariables = new char[numberOfExponents];
		ArrayList<Variable> term1variables = new ArrayList<Variable>();
		ArrayList<Variable> radicandVariables = simplifiableTerm1.getVariableFactorization();
		boolean nothingRemovedYet = true;
		for (int index = 0; index< radicandVariables.size(); index ++){
				//all this first command does is add the variables to a list for use when making the non-simplifiable part
				useTheseVariables[index] = radicandVariables.get(index).getBase();
				
				//takes terms from inside the radical and moves them to the outside
				int existingExp = radicandVariables.get(index).getExponent();
				int removed = Ops.randomInt(0, existingExp-1);
				int remaining = existingExp - removed;
				char base = radicandVariables.get(index).getBase();
				
				radicandVariables.get(index).setExponent(remaining);
				
				if (removed>0) {
					term1variables.add(new Variable(base,removed));
					nothingRemovedYet = false;
				}
				//System.out.print(" but became " + simplifiableTerm1.getVariableFactorization().get(index).getExponent());	
		}
		//this removes any variables that have an exponent of 0
		for (int index = 0; index < radicandVariables.size(); index++){
			if (radicandVariables.get(index).getExponent()==0) {
				radicandVariables.remove(index);
				index--;
			}
		}
		//sets new radicand
		simplifiableTerm1.setVariableFactorization(radicandVariables);
		//System.out.println("\nThe 1st simplifiable term is now " + simplifiableTerm1);
		//System.out.println("\nThis was taken out" + term1variables);
		
		ArrayList<Variable> term2variables = new ArrayList<Variable>();
		ArrayList<Variable> radicandVariables2 = simplifiableTerm2.getVariableFactorization();
		for (int index = 0; index< radicandVariables2.size(); index ++){
			if (Math.random()>.5 || nothingRemovedYet) {
				int existingExp = radicandVariables2.get(index).getExponent();
				int removed = Ops.randomInt(1, existingExp);
				int remaining = existingExp - removed;
				char base = radicandVariables2.get(index).getBase();
				
				radicandVariables2.get(index).setExponent(remaining);
				if (removed!=0) term2variables.add(new Variable(base,removed));
				nothingRemovedYet = false;
			}
		}
		//this removes any variables that have an exponent of 0
		for (int index = 0; index < radicandVariables2.size(); index++){
			if (radicandVariables2.get(index).getExponent()==0) {
				radicandVariables2.remove(index);
				index--;
			}
		}
		simplifiableTerm2.setVariableFactorization(radicandVariables2);
		
		//System.out.println("The 2nd simplifiable term = " + simplifiableTerm2);
		
		int coefficient1 = Ops.randomInt(2, difficulty*3);
		if (Math.random()>.5) coefficient1=coefficient1*-1;
		Term outsideTerm1 = new Term(coefficient1, term1variables);

		int coefficient2 = Ops.randomInt(2, difficulty*3);
		//if (Math.random()>.5) coefficient2=coefficient2*-1;
		Term outsideTerm2 = new Term(coefficient2, term2variables);
		
		//System.out.println("The simplifiable term = " + simplifiableTerm);
		
		//this part creates the term that will (probably) remain under the radical(randomization may create a term that can be simplified)
		coefficientMax = 7;
		int exponentMin = 1;
		int exponentMax = difficulty;
		Term nonSimplifiableTerm = randomTerm(coefficientMin, coefficientMax, Ops.randomInt(0, numberOfExponents), exponentMin, exponentMax, useTheseVariables);
		//System.out.println("The \"nonsimplifiable\" term = " + nonSimplifiableTerm);
		//this assures that the term WILL remain under the radical
		SimplestRadicalForm termFromWhichWeTakeTheRadicand = new SimplestRadicalForm(root, nonSimplifiableTerm);
		Term cantBeSimplified = termFromWhichWeTakeTheRadicand.getRadicand();
		
		SimplestRadicalForm termToBeSimplified1 = new SimplestRadicalForm(root, Ops.multiplyTerms(Ops.pow(simplifiableTerm1, root),cantBeSimplified));
		SimplestRadicalForm termToBeSimplified2 = new SimplestRadicalForm(root, Ops.multiplyTerms(Ops.pow(simplifiableTerm2, root),cantBeSimplified));
		
		boolean addOrSubtract = true;
		if (Math.random()>.5) {
			addOrSubtract=false;
		}
		
		Term outside;
		if (addOrSubtract) {
			question = outsideTerm1 + termToBeSimplified1.getRepresentationBeforeSimplification() + "\\;+\\;" + outsideTerm2 + termToBeSimplified2.getRepresentationBeforeSimplification();
			outside = Ops.addLikeTerms(Ops.multiplyTerms(outsideTerm1, termToBeSimplified1.getFactor()), Ops.multiplyTerms(outsideTerm2, termToBeSimplified2.getFactor()));
		}
		else {
			question = outsideTerm1 + termToBeSimplified1.getRepresentationBeforeSimplification() + "\\;-\\;" +outsideTerm2//.toString().replaceAll("-", "") 
					+ termToBeSimplified2.getRepresentationBeforeSimplification();
			outsideTerm2.setSign(false);
			outside = Ops.addLikeTerms(Ops.multiplyTerms(outsideTerm1, termToBeSimplified1.getFactor()), Ops.multiplyTerms(outsideTerm2, termToBeSimplified2.getFactor()));
		}
		
		
		
		
		answer = "" + outside + "\\sqrt["+termToBeSimplified1.getRoot()+"]{" + termToBeSimplified1.getRadicand() + "}";
	}
	
}