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
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;


public class MultiplyDivideRationals extends Type{

	
	private String[] variationsOnInstructions= {"Evaluate. Leave the denominator in factored form.", "Simplify the following expression."};
	
	public MultiplyDivideRationals(){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=80;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.33;
	}
	
	public MultiplyDivideRationals(int difficulty){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=80;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.33;
		getInstance(difficulty);
		//question and answer is determined in the getInstance constructor
	}
	
	public void getInstance(int diff){
		//chooses the variable
		String variable = "" + Variable.randVar();
		
		//creates an array of quantities that will be common in both the numerator and denominator of the product.
		int numberOfCommonQuantities = 2;
		//if (diff == 2) numberOfCommonQuantities = 2;
		//else
			if (diff>2 && Math.random()>.5) numberOfCommonQuantities = 3;
		boolean quantity2IsUsed = false;
		boolean quantity3IsUsed = false;
		if (numberOfCommonQuantities>=2) quantity2IsUsed = true;
		if (numberOfCommonQuantities==3) quantity3IsUsed = true;
		
		Expression[] commonQuantities = new Expression[numberOfCommonQuantities];
		for (int index = 0; index < numberOfCommonQuantities; index ++){
			Expression e = randExpression(variable, diff);
			commonQuantities[index]=e;
		}
		
		//determines where the common quantities show up;
		int numberOfQuantitiesInNum1 = 0;
		int numberOfQuantitiesInDen1 = 0;
		int numberOfQuantitiesInNum2 = 0;
		int numberOfQuantitiesInDen2 = 0;
		
		boolean quantity1ShowsUpInNumerator1 = false;
		boolean quantity1ShowsUpInNumerator2 = false;
		boolean quantity1ShowsUpInDenominator1 = false;
		boolean quantity1ShowsUpInDenominator2 = false;
		boolean quantity2ShowsUpInNumerator1 = false;
		boolean quantity2ShowsUpInNumerator2 = false;
		boolean quantity2ShowsUpInDenominator1 = false;
		boolean quantity2ShowsUpInDenominator2 = false;
		boolean quantity3ShowsUpInNumerator1 = false;
		boolean quantity3ShowsUpInNumerator2 = false;
		boolean quantity3ShowsUpInDenominator1 = false;
		boolean quantity3ShowsUpInDenominator2 = false;
		
		//determines where the quantities appear
		//determines where 1st quantity appears
		double quantity1firstDestination = Math.random();
		if (quantity1firstDestination<.5) {
			quantity1ShowsUpInNumerator1 = true;
			numberOfQuantitiesInNum1++;
			double quantity1secondDestination = Math.random();
			if (quantity1secondDestination<.5) {
				quantity1ShowsUpInDenominator1 = true; 
				numberOfQuantitiesInDen1++;
			}
			else {
				quantity1ShowsUpInDenominator2 = true;
				numberOfQuantitiesInDen2++;
			}
		}
		else{
			quantity1ShowsUpInNumerator2 = true;
			numberOfQuantitiesInNum2++;
			double quantity1secondDestination = Math.random();
			if (quantity1secondDestination<.5) {
				quantity1ShowsUpInDenominator1 = true;
				numberOfQuantitiesInDen1++;
			}
			else {
				quantity1ShowsUpInDenominator2 = true;
				numberOfQuantitiesInDen2++;
			}
		}
		//determines where 2nd quantity appears
		if (quantity2IsUsed){
		double quantity2firstDestination = Math.random();
		if (quantity2firstDestination<.5) {
			quantity2ShowsUpInNumerator1 = true;
			numberOfQuantitiesInNum1++;
			double quantity2secondDestination = Math.random();
			if (quantity2secondDestination<.5){
				quantity2ShowsUpInDenominator1 = true; 
				numberOfQuantitiesInDen1++;
			}
			else {
				quantity2ShowsUpInDenominator2 = true;
				numberOfQuantitiesInDen2++;
			}
		}
		else{
			quantity2ShowsUpInNumerator2 = true;
			numberOfQuantitiesInNum2++;
			double quantity2secondDestination = Math.random();
			if (quantity2secondDestination<.5){
				quantity2ShowsUpInDenominator1 = true; 
				numberOfQuantitiesInDen1++;
			}
			else {
				quantity2ShowsUpInDenominator2 = true;
				numberOfQuantitiesInDen2++;
			}
		}
		}
		//determines where 3rd quantity appears
		if (quantity3IsUsed){
			if (numberOfQuantitiesInNum1<=1){
				quantity3ShowsUpInNumerator1=true;
				numberOfQuantitiesInNum1++;
				if (numberOfQuantitiesInDen2<=1){
					quantity3ShowsUpInDenominator2 = true;
					numberOfQuantitiesInDen2++;
				}
				else if (numberOfQuantitiesInDen1<=1){
					quantity3ShowsUpInDenominator1 = true;
					numberOfQuantitiesInDen1++;
				}
			}
			else if (numberOfQuantitiesInNum2<=1){
				quantity3ShowsUpInNumerator2=true;
				numberOfQuantitiesInNum2++;
				if (numberOfQuantitiesInDen1<=1){
					quantity3ShowsUpInDenominator1 = true;
					numberOfQuantitiesInDen1++;
				}
				else if (numberOfQuantitiesInDen2<=1){
					quantity3ShowsUpInDenominator2 = true;
					numberOfQuantitiesInDen2++;
				}
			}
		}
		
		
		boolean extraFactor = false;
		boolean extraQuantityShowsUpInNumerator1 = false;
		boolean extraQuantityShowsUpInNumerator2 = false;
		boolean extraQuantityShowsUpInDenominator1 = false;
		boolean extraQuantityShowsUpInDenominator2 = false;
		Expression extraQuantity = randExpression(variable, diff);
		if (Math.random()<.6) extraFactor = true;
		if (extraFactor){
			if (numberOfQuantitiesInDen2<=1){
				extraQuantityShowsUpInDenominator2 = true;
				numberOfQuantitiesInDen2++;
			}
			else if (numberOfQuantitiesInDen1<=1){
				extraQuantityShowsUpInDenominator1 = true;
				numberOfQuantitiesInDen1++;
			}
			else if (numberOfQuantitiesInNum1<=1){
				extraQuantityShowsUpInNumerator1 = true;
				numberOfQuantitiesInNum1++;
			}
			else if (numberOfQuantitiesInNum2<=1){
				extraQuantityShowsUpInNumerator2 = true;
				numberOfQuantitiesInNum2++;
			}
		}
		
		
		

		Term num1 = createATerm(diff, variable);
		if (quantity1ShowsUpInNumerator1) num1.appendExpressionFactor(commonQuantities[0]);
		if (quantity2ShowsUpInNumerator1) num1.appendExpressionFactor(commonQuantities[1]);
		if (quantity3ShowsUpInNumerator1) num1.appendExpressionFactor(commonQuantities[2]);
		if (extraQuantityShowsUpInNumerator1) num1.appendExpressionFactor(extraQuantity);
		Term[] numerator1 = Ops.expand(Term.getCopy(num1));
		//System.out.println("The 1st numerator (factored) = " + num1 + ", and expanded = " + Format.termArrayToString(numerator1));
		
		
		Term den1 = createATerm(diff, variable);
		if (quantity1ShowsUpInDenominator1) den1.appendExpressionFactor(commonQuantities[0]);
		if (quantity2ShowsUpInDenominator1) den1.appendExpressionFactor(commonQuantities[1]);
		if (quantity3ShowsUpInDenominator1) den1.appendExpressionFactor(commonQuantities[2]);
		if (extraQuantityShowsUpInDenominator1) den1.appendExpressionFactor(extraQuantity);
		Term[] denominator1 = Ops.expand(Term.getCopy(den1));
		//System.out.println("The 1st denominator (factored) = " + den1 + ", and expanded = " + Format.termArrayToString(denominator1));
		
		Term num2 = createATerm(diff, variable);
		if (quantity1ShowsUpInNumerator2) num2.appendExpressionFactor(commonQuantities[0]);
		if (quantity2ShowsUpInNumerator2) num2.appendExpressionFactor(commonQuantities[1]);
		if (quantity3ShowsUpInNumerator2) num2.appendExpressionFactor(commonQuantities[2]);
		if (extraQuantityShowsUpInNumerator2) num2.appendExpressionFactor(extraQuantity);
		Term[] numerator2 = Ops.expand(Term.getCopy(num2));
		//System.out.println("The 2nd numerator (factored) = " + num2 + ", and expanded = " + Format.termArrayToString(numerator2));
		
		Term den2 = createATerm(diff, variable);
		if (quantity1ShowsUpInDenominator2) den2.appendExpressionFactor(commonQuantities[0]);
		if (quantity2ShowsUpInDenominator2) den2.appendExpressionFactor(commonQuantities[1]);
		if (quantity3ShowsUpInDenominator2) den2.appendExpressionFactor(commonQuantities[2]);
		if (extraQuantityShowsUpInDenominator2) den2.appendExpressionFactor(extraQuantity);
		Term[] denominator2 = Ops.expand(Term.getCopy(den2));
		//System.out.println("The 2nd denominator (factored) = " + den2 + ", and expanded = " + Format.termArrayToString(denominator2));
		
		boolean multiply = true;
		if (Math.random()>.5) multiply=false;
		
		if (multiply){
			question = "\\frac{"+Format.termArrayToString(numerator1)+"}{" + Format.termArrayToString(denominator1) + "}\\cdot\\frac{"+Format.termArrayToString(numerator2)+"}{" + Format.termArrayToString(denominator2) + "}";
			
			System.out.println("\nnum1 = " + num1);
			System.out.println("den1 = " + den1);
			Fraction one = new Fraction(num1,den1);
			System.out.println("Fraction one = " + one);
			Term t1 = new Term(one);
			System.out.println("Term one = " + t1);
			
			System.out.println("num2 = " + num2);
			System.out.println("den2 = " + den2);
			Fraction two = new Fraction(num2,den2);
			System.out.println("Fraction two = " + two);
			Term t2 = new Term(two);
			System.out.println("Term two = " + t2);
			answer = "" + Ops.multiplyTerms(t1, t2);
			System.out.println("The answer is " + answer +"\n");
		}
		else{
			question = "\\frac{"+Format.termArrayToString(numerator1)+"}{" + Format.termArrayToString(denominator1) + "}\\div\\frac{"+ Format.termArrayToString(denominator2) +"}{" + Format.termArrayToString(numerator2) + "}";
			Fraction one = new Fraction(num1,den1);
			Term t1 = new Term(one);
			Fraction two = new Fraction(num2,den2);
			Term t2 = new Term(two);
			answer = "" + Ops.multiplyTerms(t1, t2);
			System.out.println("The answer is " + answer +"\n");
		}

		
		
	}
	
	public Expression randExpression(String var, int diff){
		int coefficient = 1;
		if (diff == 3 || diff == 4) {
			coefficient = Ops.randomInt(2, diff+1);
		}
		Term variableTerm = new Term(coefficient, var);
		int constant = Ops.randomInt(1, diff+3);
		if (Math.random()>.5) constant = constant * (-1);
		Term constantTerm = new Term (constant);
		Term[] t = {variableTerm, constantTerm};
		Expression e = new Expression(t);
		return e;
	}
	
	public Term createATerm(int difficulty, String variable){
		Term t;
		if (difficulty== 1 || difficulty ==2) t = new Term(Ops.randomInt(1, 6));
		else{
			int coefficient = Ops.randomInt(1, 2*difficulty);
			int exp;
			if (Math.random()<.3){
			exp = Ops.randomInt(1, difficulty-1);
			}
			else exp = 1;
			String vars;
			if (exp ==1) vars = variable;
			else vars = variable + "^" + exp;
			t = new Term(coefficient, vars);
		}
		return t;
	}
}
