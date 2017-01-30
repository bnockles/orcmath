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

import java.awt.font.NumericShaper;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;


public class SimplifyComplexFraction extends Type{

	private String[] variationsOnInstructions= {"Simplify and reduce to a single fraction."};
	private boolean lastEntryWasInteger;
	private Term lastEnteredTerm;
	private Term beforeLastEnteredTerm;
	
	public SimplifyComplexFraction(){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=180;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		lastEntryWasInteger = false;
		lastEnteredTerm = new Term(0);
		beforeLastEnteredTerm = new Term(0);
	}
	
	public SimplifyComplexFraction(int difficulty){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=180;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		lastEntryWasInteger = false;
		lastEnteredTerm = new Term(0);
		beforeLastEnteredTerm = new Term(0);
		getInstance(difficulty);
		//question and answer is determined in the getInstance constructor
	}
	
	public void getInstance(int diff){
		//chooses the variable
		char[] variables = Variable.randVars(2); 
		String variable = "" + variables[0];
		String variable2 = "" + variables[1];
	
		Term one = new Term(1);
			int k = Ops.randomInt(2, 5);
		Term constant = new Term(k);
		Term multipleOfConstant = new Term(Ops.randomInt(2, 3)*k);
		Term negativeOne = new Term(-1);
		Term x = new Term(variable);
		Term xSquared = new Term(variable+"^2");
		Term y = new Term(variable2);
		Term ySquared = new Term(variable2+"^2");
		Term negativeYSquared = new Term(-1, variable2+"^2");
		Term negativeX = new Term(-1,variable);
		Term negativeY = new Term(-1,variable2);
		
		//x+y
		Term i1 = new Term(1);
		Term[] p1 = {x,y};
		Expression e1 = new Expression(p1);
		i1.appendExpressionFactor(e1);
		
		
		//x-y
		Term i2 = new Term(1);
		Term[] p2 = {x,negativeY};
		Expression e2 = new Expression(p2);
		i2.appendExpressionFactor(e2);
		
		//x-1
		Term i3 = new Term(1);
		Term[] p3 = {x,negativeOne};
		Expression e3 = new Expression(p3);
		i3.appendExpressionFactor(e3);

		//x+1
		Term i4 = new Term(1);
		Term[] p4 = {x,one};
		Expression e4 = new Expression(p4);
		i4.appendExpressionFactor(e4);
		
		//x^2-1
		Term i5 = new Term(1);
		Term[] p5 = {xSquared,negativeOne};
		Expression e5 = new Expression(p5);
		i5.appendExpressionFactor(e5);
		
		//x^2-y^2
		Term i6 = new Term(1);
		Term[] p6 = {xSquared,negativeYSquared};
		Expression e6 = new Expression(p6);
		i6.appendExpressionFactor(e6);
		
		Term xPlusY = i1;
		Term xMinusY = i2;
		Term xMinus1 = i3;
		Term xPlus1 = i4;
		Term xSquaredMinus1 = i5;
		Term xSquaredMinusYSquared = i6;
		Term[] selectionOfTerms = {
				one, //0
				constant,//1
				multipleOfConstant,//2
				negativeOne,//3
				x, //4
				xSquared,//5
				y,//6
				ySquared,//7
				negativeYSquared,//8
				negativeX,//9
				negativeY,//10
				xPlusY,//11
				xMinusY,//12
				xMinus1,//13
				xPlus1,//14
				xSquaredMinus1,//15
				xSquaredMinusYSquared};//16
		
		int[] numeratorSelections = new int[4];
		int[] denominatorSelections = new int[4];
		
		int[] possibleNumerators1 = {0,3,4,5,9};
		int[] possibleNumerators2 = {0,3,4,5,9};
		int[] possibleNumerators3 = {0,3,4,5,9};
		int[] possibleNumerators4 = {0,3,4,5,9,12,13,14};
		
		int[] possibleDenominators1 = {0,1,2,4};
		int[] possibleDenominators2 = {0,1,2,4};
		int[] possibleDenominators3 = {0,4,4,13,14,15};
		int[] possibleDenominators4 = {0,11,12,16};
		
		int randNum1=0;
		int randNum2=0;
		int randNum3=0;
		int randNum4=0;
		int randDen1=0;
		int randDen2=0;
		int randDen3=0;
		int randDen4=0;
	
		int[] pickNumeratorFrom;
		int[] pickDenominatorFrom;
		
		if (diff ==1) {
			pickNumeratorFrom=possibleNumerators1;
			pickDenominatorFrom=possibleDenominators1;
			randNum1 = Ops.randomInt(0, possibleNumerators1.length-1);
			randNum2 = Ops.randomInt(0, possibleNumerators1.length-1);
			while (randNum1==randNum2){
				randNum2 = Ops.randomInt(0, possibleNumerators1.length-1);
			}
			randNum3 = Ops.randomInt(0, possibleNumerators1.length-1);
			randNum4 = Ops.randomInt(0, possibleNumerators1.length-1);
			//assures there is always 1 variable
			if (randNum1!=4 && randNum2!=4 && randNum3!=4 && randNum4!=4) randNum2 = 4; 
			randDen1 = Ops.randomInt(0, possibleDenominators1.length-1);
			randDen2 = Ops.randomInt(0, possibleDenominators1.length-1);
			while (randDen1==randDen2 || (randDen1 == 0 && randDen2 == 3) || (randDen1 == 3 && randDen2 == 0)){
				randDen2 = Ops.randomInt(0, possibleDenominators1.length-1);
			}
			randDen3 = Ops.randomInt(0, possibleDenominators1.length-1);
			randDen4 = Ops.randomInt(0, possibleDenominators1.length-1);
			while (randDen3==randDen4 || (randDen1 == 0 && randDen2 == 3) || (randDen1 == 3 && randDen2 == 0)){
				randDen4 = Ops.randomInt(0, possibleDenominators1.length-1);
			}
		}
		
		else if (diff ==2) {
			pickNumeratorFrom=possibleNumerators2;
			pickDenominatorFrom=possibleDenominators2;
			randNum1 = Ops.randomInt(0, possibleNumerators2.length-1);
			randNum2 = Ops.randomInt(0, possibleNumerators2.length-1);
			while (randNum1==randNum2){
				randNum2 = Ops.randomInt(0, possibleNumerators2.length-1);
			}
			randNum3 = Ops.randomInt(0, possibleNumerators2.length-1);
			randNum4 = Ops.randomInt(0, possibleNumerators2.length-1);
			//assures there is always 1 variable
			if (randNum1!=4 && randNum2!=4 && randNum3!=4 && randNum4!=4) randNum2 = 4; 
			randDen1 = Ops.randomInt(0, possibleDenominators2.length-1);
			randDen2 = Ops.randomInt(0, possibleDenominators2.length-1);
			while (randDen1==randDen2 || (randDen1 == 0 && randDen2 == 3) || (randDen1 == 3 && randDen2 == 0)){
				randDen2 = Ops.randomInt(0, possibleDenominators2.length-1);
			}
			randDen3 = Ops.randomInt(0, possibleDenominators2.length-1);
			randDen4 = Ops.randomInt(0, possibleDenominators2.length-1);
			while (randDen3==randDen4 || (randDen1 == 0 && randDen2 == 3) || (randDen1 == 3 && randDen2 == 0)){
				randDen4 = Ops.randomInt(0, possibleDenominators2.length-1);
			}
		}
		
		else if (diff ==3) {
			pickNumeratorFrom=possibleNumerators3;
			pickDenominatorFrom=possibleDenominators3;
			randNum1 = Ops.randomInt(0, possibleNumerators3.length-1);
			randNum2 = Ops.randomInt(0, possibleNumerators3.length-1);
			while (randNum1==randNum2){
				randNum2 = Ops.randomInt(0, possibleNumerators3.length-1);
			}
			randNum3 = Ops.randomInt(0, possibleNumerators3.length-1);
			randNum4 = Ops.randomInt(0, possibleNumerators3.length-1);
			randDen1 = Ops.randomInt(0, possibleDenominators3.length-1);
			randDen2 = Ops.randomInt(0, possibleDenominators3.length-1);
			while (randDen1==randDen2 || (randDen1 == 0 && randDen2 == 3) || (randDen1 == 3 && randDen2 == 0)){
				randDen2 = Ops.randomInt(0, possibleDenominators3.length-1);
			}
			randDen3 = Ops.randomInt(0, possibleDenominators3.length-1);
			randDen4 = Ops.randomInt(0, possibleDenominators3.length-1);
			while (randDen3==randDen4 || (randDen1 == 0 && randDen2 == 3) || (randDen1 == 3 && randDen2 == 0)){
				randDen4 = Ops.randomInt(0, possibleDenominators3.length-1);
			}
		}
			
		else {
			pickNumeratorFrom=possibleNumerators4;
			pickDenominatorFrom=possibleDenominators4;
			randNum1 = Ops.randomInt(0, possibleNumerators4.length-1);
			randNum2 = Ops.randomInt(0, possibleNumerators4.length-1);
			while (randNum1==randNum2){
				randNum2 = Ops.randomInt(0, possibleNumerators4.length-1);
			}
			randNum3 = Ops.randomInt(0, possibleNumerators4.length-1);
			randNum4 = Ops.randomInt(0, possibleNumerators4.length-1);
			randDen1 = Ops.randomInt(0, possibleDenominators4.length-1);
			randDen2 = Ops.randomInt(0, possibleDenominators4.length-1);
			while (randDen1==randDen2 || (randDen1 == 0 && randDen2 == 3) || (randDen1 == 3 && randDen2 == 0)){
				randDen2 = Ops.randomInt(0, possibleDenominators4.length-1);
			}
			randDen3 = Ops.randomInt(0, possibleDenominators4.length-1);
			randDen4 = Ops.randomInt(0, possibleDenominators4.length-1);
			while (randDen3==randDen4 || (randDen1 == 0 && randDen2 == 3) || (randDen1 == 3 && randDen2 == 0)){
				randDen4 = Ops.randomInt(0, possibleDenominators4.length-1);
			}
		}
			
		
		Term numOne = selectionOfTerms[pickNumeratorFrom[randNum1]];
		System.out.println("Term " +numOne+", at selection " +randNum1);
		Term denOne = selectionOfTerms[pickDenominatorFrom[randDen1]];
		System.out.println("Term " +denOne+", at selection " +randDen1);
		Fraction frac1 = new Fraction(numOne,denOne);
		Term t1 = new Term(frac1);
		
		Term numTwo = selectionOfTerms[pickNumeratorFrom[randNum2]];
		System.out.println("Term " +numTwo+", at selection " +randNum2);
		Term denTwo = selectionOfTerms[pickDenominatorFrom[randDen2]];
		System.out.println("Term " +denTwo+", at selection " +randDen2);
		Fraction frac2 = new Fraction(numTwo,denTwo);
		Term t2 = new Term(frac2);
		
		Term numThree = selectionOfTerms[pickNumeratorFrom[randNum3]];
		System.out.println("Term " +numThree+", at selection " +randNum3);
		Term denThree = selectionOfTerms[pickDenominatorFrom[randDen3]];
		System.out.println("Term " +denThree+", at selection " +randDen3);
		Fraction frac3 = new Fraction(numThree,denThree);
		Term t3 = new Term(frac3);
		
		Term numFour = selectionOfTerms[pickNumeratorFrom[randNum4]];
		System.out.println("Term " +numFour+", at selection " +randNum4);
		Term denFour = selectionOfTerms[pickDenominatorFrom[randDen4]];
		System.out.println("Term " +denFour+", at selection " +randDen4);
		Fraction frac4 = new Fraction(numFour,denFour);
		Term t4 = new Term(frac4);
		
//		System.out.println("These terms were randomly selected: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//		System.out.println("1st numerator = " + numOne);
//		System.out.println("2nd numerator = " + numTwo);
//		System.out.println("1st denominator = " + denOne);
//		System.out.println("2nd denominator = " + denTwo);
		
		Term[] numerator = {t1,t2};
		String top = Format.removeParentheses(Format.termArrayToString(numerator));
		Term simplifiedNumerator = Ops.addRationals(Term.getCopy(t1),Term.getCopy(t2));
		simplifiedNumerator.simplifyExpressionFactors();
		

		Term[] denominator = {t3, t4};
		String bottom = Format.removeParentheses(Format.termArrayToString(denominator));
		Term simplifiedDenominator = Ops.addRationals(Term.getCopy(t3), Term.getCopy(t4));
		simplifiedDenominator.simplifyExpressionFactors();


//		Expression num = new Expression(numerator);
//		Expression den = new Expression(denominator);
//		Term n = new Term(1);
//		n.appendExpressionFactor(num);
//		Term d = new Term(1);
//		d.appendExpressionFactor(den);
//		
//		Fraction complex = new Fraction(n,d);
//		System.out.println("\n Complex fraction =" + Format.removeParentheses("" +complex));
		question = "\\frac{" + top + "}{" + bottom + "}";
		Fraction dividend = Ops.divideTerms(simplifiedNumerator, simplifiedDenominator);
		System.out.println("Simplified = " + dividend);
		answer = "" + dividend;
	}
	
	
//	public void getInstance(int diff){
//		//chooses the variable
//		char[] variables = Variable.randVars(2); 
//		String variable = "" + variables[0];
//		String variable2 = "" + variables[1];
//	
//		Term one = new Term(1);
//		int k = Ops.randomInt(2, 5);
//		Term constant = new Term(k);
//		Term multipleOfConstant = new Term(Ops.randomInt(2, 3)*k);
//		Term negativeOne = new Term(-1);
//		Term x = new Term(variable);
//		Term xSquared = new Term(variable+"^2");
//		Term y = new Term(variable2);
//		Term ySquared = new Term(variable2+"^2");
//		Term negativeYSquared = new Term(-1, variable2+"^2");
//		Term negativeX = new Term(-1,variable);
//		Term negativeY = new Term(-1,variable);
//		Term OneOverX = new Term(new Fraction(one,x));
//		Term negativeOneOverX = new Term(new Fraction(negativeOne,x));
//		Term OneOverY = new Term(new Fraction(one,y));
//		Term negativeOneOverY = new Term(new Fraction(negativeOne,y));
//		Term[] selectionOfTerms = {one, constant,multipleOfConstant,negativeOne,x, xSquared,y,ySquared,negativeYSquared,negativeX,negativeY,OneOverX,negativeOneOverX,negativeY,negativeOneOverY};
//		
//		double denominatorType = Math.random();//if denominators are used in the fraction, this selects the type of denominator.
//		//if the value is less than .5, only one variable is used
//		if (diff<3) denominatorType = .4;
//		
//		Term numOne = createATerm(diff, selectionOfTerms, denominatorType);
//		Term numTwo = createATerm(diff, selectionOfTerms, denominatorType);
//		Term denOne = createATerm(diff, selectionOfTerms, denominatorType);
//		Term denTwo = createATerm(diff, selectionOfTerms, denominatorType);
//		
////		numOne.simplifyExpressionFactors();
////		numTwo.simplifyExpressionFactors();
////		denOne.simplifyExpressionFactors();
////		denTwo.simplifyExpressionFactors();
//
//		System.out.println("These terms were randomly selected: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//		System.out.println("1st numerator = " + numOne);
//		System.out.println("2nd numerator = " + numTwo);
//		System.out.println("1st denominator = " + denOne);
//		System.out.println("2nd denominator = " + denTwo);
//		
//		Term[] numerator = {numOne,numTwo};
//		String top = Format.removeParentheses(Format.termArrayToString(numerator));
//		Term simplifiedNumerator = Ops.addRationals(Term.getCopy(numOne),Term.getCopy(numTwo));
//		simplifiedNumerator.simplifyExpressionFactors();
//		//System.out.println("Numerator, combined = " + simplifiedNumerator);
//		//System.out.println("The two numerators form this string = " + Format.termArrayToString(numerator));
//
//		Term[] denominator = {denOne, denTwo};
//		String bottom = Format.removeParentheses(Format.termArrayToString(denominator));
//		Term simplifiedDenominator = Ops.addRationals(Term.getCopy(denOne), Term.getCopy(denTwo));
//		//System.out.println("Denominator, combined = " + simplifiedDenominator);
//		simplifiedDenominator.simplifyExpressionFactors();
//		//System.out.println("Denominator, simplified = " + simplifiedDenominator);
//
////		Expression num = new Expression(numerator);
////		Expression den = new Expression(denominator);
////		Term n = new Term(1);
////		n.appendExpressionFactor(num);
////		Term d = new Term(1);
////		d.appendExpressionFactor(den);
////		
////		Fraction complex = new Fraction(n,d);
////		System.out.println("\n Complex fraction =" + Format.removeParentheses("" +complex));
//		question = "\\frac{" + top + "}{" + bottom + "}";
//		Fraction dividend = Ops.divideTerms(simplifiedNumerator, simplifiedDenominator);
//		System.out.println("Simplified = " + dividend);
//		answer = "" + dividend;
//	}
	
	public Term createATerm(int difficulty, Term[] selectionOTerms, double denominatorType){
		Term t;
//		0 Term one = new Term(1);
//		1 Term constant = new Term(k);
//		2 Term multipleOfConstant = new Term(Ops.randomInt(2, 3)*k);
//		3 Term negativeOne = new Term(-1);
//		4 Term x = new Term(variable);
//		5 Term xSquared = new Term(variable+"^2");
//		6 Term y = new Term(variable2);
//		7 Term ySquared = new Term(variable2+"^2");
//		8 Term negativeYSquared = new Term(-1, variable2+"^2");
//		9 Term negativeX = new Term(-1,variable);
//		10 Term negativeY = new Term(-1,variable);
//		11 Term OneOverX = new Term(new Fraction(one,x));
//		12 Term negativeOneOverX = new Term(new Fraction(negativeOne,x));
//		13 Term OneOverY = new Term(new Fraction(one,y));
//		14 Term negativeOneOverY = new Term(new Fraction(negativeOne,y));
		
		double constantOrFraction = Math.random();
		double numeratorType = Math.random();//a random to determine if numerator will be constant or expression
		//double denominatorType = Math.random();//a random to determine what type of denominator to use.
		boolean denominatorsAreExpressions;
		
		//determines what type of denominators we will see
		if (denominatorType>.5 || difficulty<3) denominatorsAreExpressions = false;
		else denominatorsAreExpressions = true;
		
		//creates a constant
		if (constantOrFraction>.7 && !lastEntryWasInteger){
			t = singleConstantTerm(difficulty, selectionOTerms);
			beforeLastEnteredTerm = lastEnteredTerm;
			lastEnteredTerm = t;
			lastEntryWasInteger=true;
		}
	
		//creates a fraction
		else {
			Term top;
			if (numeratorType>.5) top = singleConstantTerm(difficulty, selectionOTerms);
			else {
				top = expressionTerm(difficulty, selectionOTerms, denominatorType);
			}
			Term bottom;
			if (!denominatorsAreExpressions){
				bottom = singleConstantTerm(difficulty, selectionOTerms);
			}
			else bottom = expressionTerm(difficulty, selectionOTerms, denominatorType);
			Fraction f = new Fraction(top,bottom);
			t = new Term (f);
//			while (Ops.areEqualTerms(t, lastEnteredTerm) || Ops.areEqualTerms(t, beforeLastEnteredTerm)){
//				f = new Fraction(top, expressionTerm(difficulty, selectionOTerms, denominatorType));
//				t = new Term(f);
//			}
			beforeLastEnteredTerm = lastEnteredTerm;
			lastEnteredTerm = t;
			lastEntryWasInteger=false;
		}
		return t;
	}
	
	
private Term expressionTerm(int difficulty, Term[] selectionOfTerms, double selection) {
		System.out.println("expressionTerm was called. Selection = " + selection);
		Term t = new Term (1);//the term	
		Expression e;//the expression that will be appended to the term
		Term[] terms = new Term[2]; //the term[] that is the expression;
		
//		0 Term one = new Term(1);
//		1 Term constant = new Term(k);
//		2 Term multipleOfConstant = new Term(Ops.randomInt(2, 3)*k);
//		3 Term negativeOne = new Term(-1);
//		4 Term x = new Term(variable);
//		5 Term xSquared = new Term(variable+"^2");
//		6 Term y = new Term(variable2);
//		7 Term ySquared = new Term(variable2+"^2");
//		8 Term negativeYSquared = new Term(-1, variable2+"^2");
//		9 Term negativeX = new Term(-1,variable);
//		10 Term negativeY = new Term(-1,variable);
//		11 Term OneOverX = new Term(new Fraction(one,x));
//		12 Term negativeOneOverX = new Term(new Fraction(negativeOne,x));
//		13 Term OneOverY = new Term(new Fraction(one,y));
//		14 Term negativeOneOverY = new Term(new Fraction(negativeOne,y));
		
		//when selection is less than .5, no extra variable is introduced
		if (selection<.5) {
			double t1Chooser = Math.random();
			double t2Chooser = Math.random();
			if (t1Chooser>.7)terms[0] = selectionOfTerms[0];//1
			else if (t1Chooser>.3)terms[0] = selectionOfTerms[4];//x
			else terms[0] = selectionOfTerms[5];//x^2
			
			//this part is only terms that go with 1
			if(t1Chooser>.7){
				if (t2Chooser>.5)terms[1] = selectionOfTerms[4];//x
				else terms[1] = selectionOfTerms[9];//-x
			}
			//terms that go with x or x^2
			else{
				if (t2Chooser>.6)terms[1] = selectionOfTerms[0];//1
				else terms[1] = selectionOfTerms[3];//-1
			}
		}
		//this is where new variables are introduced
		if (selection>.5){
			double t1Chooser = Math.random();
			double t2Chooser = Math.random();
			if (t1Chooser>.3)terms[0] = selectionOfTerms[4];//x
			else terms[0] = selectionOfTerms[5];//x^2

			
			//this part is only terms that go with x
			if(t1Chooser>.3){
				if (t2Chooser>.8)terms[1] = selectionOfTerms[6];//y
				else terms[1] = selectionOfTerms[10];//-y
			}
			//terms that go with x or x^2
			else terms[1] = selectionOfTerms[8];//-y^2
		}
		e = new Expression(terms);
		t.appendExpressionFactor(e);
		return t;
	}

private Term singleConstantTerm(int difficulty, Term[] selectionOfTerms){
	System.out.println("singleConstantTerm was called.");
		Term t;
		double selection = Math.random();
	
		if (selection<.1) t = selectionOfTerms[0];
		else if (selection<.2) t = selectionOfTerms[1];
		else if (selection<.3) t = selectionOfTerms[2];
		else if (selection<.4) t = selectionOfTerms[3];
		//the following terms are variables
		else if (selection<.6) t = selectionOfTerms[4];
		else if (selection<.8) t = selectionOfTerms[5];
		else t = selectionOfTerms[9];
//		while (Ops.areEqualTerms(t, lastEnteredTerm)){
//			t = singleConstantTerm(difficulty, selectionOfTerms);
//		}
		beforeLastEnteredTerm = lastEnteredTerm;
		lastEnteredTerm = t;
		lastEntryWasInteger=true;
	
		return t;
}
	

//currently this method is not used, but maybe I will use it at another time
	public Expression randExpression(String var, int diff){
		int coefficient = 1;
		if (diff == 3 || diff == 4) {
			coefficient = Ops.randomInt(2, diff+1);
		}
		Term variableTerm = new Term(coefficient, var);
		int constant = Ops.randomInt(0, diff);
		if (Math.random()>.5) constant = constant * (-1);
		Term constantTerm = new Term (constant);
		Term[] t = {variableTerm, constantTerm};
		Expression e = new Expression(t);
		return e;
	}
	
}
