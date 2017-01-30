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
package com.orcmath.drawabletype;

import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;
import com.orcmath.type.Type;

public class AddRationalExpressionsFactoring extends Type{

private String[] variationsOnInstructions= {"Evaluate the following and leave your answer in factored form", "Simplify."};
private String[] variationsOnLCDInstructions= {"Identify the LCD"};	

	int a;
	int b;
	int c;
	int d;
	int e;
	int f;
	int g;
	int h;
	int j;
	int k;
	boolean requireAdding;//false if all that is necessary is to identify the LCD
	boolean singleTermDenominator;
	int commonFactor;
	int highestSharedPower;
	int remainingPower;
	boolean subtract;
	
	/**
	 * This form:
	 *    (ax+b)     +    (gx+h)  
	 * (cx+d)(ex+f)    (jx+k)(ex+f)
	 *  
	 *             OR
	 *            (ax+b)        +     ()
	 *    cx^highestSharedPower   jx^highestSharedPower+remainingPower
	 */
	
	public AddRationalExpressionsFactoring(){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=180;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
	}
	
	public AddRationalExpressionsFactoring(boolean trueForAddingFalseForLCD, int difficulty){
		requireAdding=trueForAddingFalseForLCD;
		if(requireAdding)instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		else instructions = variationsOnLCDInstructions[Ops.randomInt(0, (variationsOnLCDInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=180;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		getInstance(difficulty);
	}

	
	
	private void set1Variables(){
			if(Math.random()<.6){
				singleTermDenominator=true;
			}else{
				singleTermDenominator=false;
			}
			if (singleTermDenominator){
				
				commonFactor=Ops.randomNotZero(2, 8);
				c=Ops.randomNotZero(1, 6);
				d=0;
				e=0;
				f=1;
				j=Ops.randomRelativePrime(c, 1, 6);
				k=0;
				
				a=Ops.randomRelativePrime(c*commonFactor, -7, 7);
				b=Ops.randomRelativePrime(c*commonFactor, -7, 7);
				g=Ops.randomRelativePrime(j*commonFactor, -7, 7);
				h=Ops.randomRelativePrime(j*commonFactor, -7, 7);
				
				highestSharedPower=Ops.randomInt(1, 6);
				remainingPower=Ops.randomInt(1, 3);//how much greater the ramaining power is from the other
			}else{
				a=1;
				c=1;
				e=1;
				g=1;
				j=1;
				double determine = Math.random();
				if(determine<.33){
					d=0;
					f=Ops.randomNotZero(-6, 6);
					k=Ops.randomNotZero(-6, 6);
				}else if(determine<.67){
					f=0;
					d=Ops.randomNotZero(-6, 6);
					k=Ops.randomNotZero(-6, 6);
				}else{
					k=0;
					f=Ops.randomNotZero(-6, 6);
					d=Ops.randomNotZero(-6, 6);
				}
				b=Ops.randomNotZero(-6, 6);
				h=Ops.randomNotZero(-6, 6);
			}
	}
	
	private void set2Variables(){
		a=1;
		c=1;
		e=1;
		g=Ops.randomNotZero(-1, 1);
		j=1;
		d=Ops.randomNotZero(-6, 6);
		f=Ops.randomNotZero(-6, 6);
		k=Ops.randomNotZero(-6, 6);
		b=Ops.randomNotZero(-6, 6);
		h=Ops.randomNotZero(-6, 6);
	}
	
	private void set3Variables(){
		if(Math.random()<.5){
			c=Ops.randomInt(1, 4);
			d=Ops.randomRelativePrime(c, -6, 6);
			j=Ops.randomInt(1, 4);
			k=Ops.randomRelativePrime(j, -6, 6);
			e=1;
			f=Ops.randomInt(-6, 6);
		}else{
			c=1;
			d=Ops.randomInt(-6, 6);
			j=1;
			k=Ops.randomInt(-6, 6);
			e=Ops.randomInt(1, 4);
			f=Ops.randomRelativePrime(e, -6, 6);
		}
		a=1;
		g=Ops.randomNotZero(-1, 1);
		b=Ops.randomInt(-6, 6);
		h=Ops.randomInt(-6, 6);
	}
	

	
	private void set4Variables(){
		int primefactor1 = Ops.randomInt(2, 6);
		int primefactor2 = Ops.randomInt(2, 6);
		int primefactor3 = Ops.randomInt(2, 6);
		
		a=Ops.randomInt(-4, 4);
		b=Ops.randomRelativePrime(a, -6, 6);
		g=Ops.randomInt(-4, 4);
		h=Ops.randomRelativePrime(g, -6, 6);
		c=Ops.randomInt(-4, 4);
		d=Ops.randomRelativePrime(c, -6, 6);
		e=Ops.randomInt(-4, 4);
		f=Ops.randomRelativePrime(e, -6, 6);
		j=Ops.randomInt(-4, 4);
		k=Ops.randomRelativePrime(e, -6, 6);
		
	}
	
	public void getInstance(int diff){
		
		//chooses the variable
		String variable = "" + Variable.randVar();
		if(diff==1){
			set1Variables();
		}else if(diff==2){
			set2Variables();
		}else if(diff==3){
			set3Variables();
		}else{
			set4Variables();//this method is incomplete
		}
		if(g<0){
			subtract=true;
		}
		else subtract=false;
		
		
		Term num1a=null;
		Term num1b=null;
		Term num2a=null;
		Term num2b=null;
		Term denA1a=null;
		Term denA1b=null;
		Term denA2a=null;
		Term denA2b=null;
		Term denB1a=null;
		Term denB1b=null;
		Term[] num1=null;
		Term[] num2=null;
		Term[] denA1=null;
		Term[] denA2=null;
		Term[] denB1=null;
		Term[] den1=null;
		Term[] den2=null;
		Term cTermFactored=null;
		Term jTermFactored=null;
		Term gcdTermFactored=null;//only used in single term denominator problems
		Term combinedFactors=null;
		if(singleTermDenominator){//monomial denominator
			num1a=new Term(a,variable);
			num1b=new Term(b);	
			num2a=new Term(g,variable);
			num2b=new Term(h);
			denB1=new Term[1];//the uncommon factor from the right
			denA1=new Term[1];//the uncommon factor from the left
			denA2=new Term[1];//the common factor from in both denominators
			gcdTermFactored=new Term(commonFactor,variable+"^"+(highestSharedPower));
			denA2[0]=gcdTermFactored;

			if(Math.random()<.5){//alternates whether the term on the left(c) has a higher power of the exp or the right(j)
				denA1a=new Term(c*commonFactor,variable+"^"+(highestSharedPower+remainingPower));
				denB1a=new Term(j*commonFactor,variable+"^"+highestSharedPower);
				cTermFactored=new Term(c,variable+"^"+remainingPower);
				jTermFactored=new Term(j);
				denB1[0]=jTermFactored;
				denA1[0]=cTermFactored;
			}else{
				denA1a=new Term(c*commonFactor,variable+"^"+highestSharedPower);
				denB1a=new Term(j*commonFactor,variable+"^"+(highestSharedPower+remainingPower));
				cTermFactored=new Term(c);
				jTermFactored=new Term(j,variable+"^"+remainingPower);
				denB1[0]=jTermFactored;
				denA1[0]=cTermFactored;
			}
			combinedFactors=Ops.multiplyTerms(gcdTermFactored, Ops.multiplyTerms(cTermFactored, jTermFactored));
			den1=new Term[1];
			den1[0]=denA1a;
			den2=new Term[1];
			den2[0]=denB1a;
			num1=new Term[1];
			num2=new Term[1];
			if(Math.random()<.5){//varies whether the numerator is constant or variable
				num1[0] =num1a;
			}else{
				num1[0] =num1b;
			}
			if(Math.random()<.5){
				num2[0] =num2a;
			}else{
				num2[0] =num2b;
			}
			
		}else{//binomial factors in denominator
			num1a=new Term(a,variable);
			num1b=new Term(b);
			num2a=new Term(g,variable);
			num2b=new Term(h);
			denA1a=new Term(c,variable);
			denA1b=new Term(d);
			denA2a=new Term(e,variable);
			denA2b=new Term(f);
			denA1 =new Term[2];
			denB1a=new Term(j,variable);
			denB1b=new Term(k);
			denA1[0]=denA1a;
			denA1[1]=denA1b;
			denA2=new Term[2];
			denA2[0]=denA2a;
			denA2[1]=denA2b;
			denB1=new Term[2];
			denB1[0] =denB1a;
			denB1[1] =denB1b;
			den1 = Ops.distribute(denA1, denA2);//denominators, fully distributed
			den2 = Ops.distribute(denB1, denA2);
			num1=new Term[2];
			num2=new Term[2];
			num1[0] =num1a;
			num1[1]=num1b;
			num2[0] =num2a;
			num2[1] =num2b;
		}//for now, numerator is always two terms, which will require distribution
		
		
		
		
		String operation="\\;+\\;";
		if(subtract) operation="\\;-\\;";
		if(!requireAdding)operation="\\;,\\;";
		
		//these strings change based on properties of a. if a!=1, then it is necessary for the string to represent factoring by grouping.
		String den1ChangingString=Format.termArrayToString(Ops.combineLikeTerms(den1));
		String den2ChangingString=Format.termArrayToString(Ops.combineLikeTerms(den2));
		
		String numerator2="";//numerator changes based on whether or not we are adding or subtracting
		if(subtract){
			numerator2=Format.termArrayToString(Ops.distribute(new Term(-1), num2));
		}else{
			numerator2=Format.termArrayToString(num2);
		}
		
		question = "\\frac{"+Format.termArrayToString(num1)+"}{"+den1ChangingString+"}"+
		operation+"\\frac{"+numerator2+"}{"+den2ChangingString+"}";
		
		WorkTable theAnswer=new WorkTable();
		
		if(subtract){
			operation="\\;+\\;";
			theAnswer.newLine("\\;","\\frac{"+Format.termArrayToString(num1)+"}{"+den1ChangingString+"}"+
					operation+"\\frac{"+Format.termArrayToString(num2)+"}{"+den2ChangingString+"}","Flip signs");
		}
		
		if(c*e!=1 || j*e!=1){
			if(c*e!=1) den1ChangingString=Format.termArrayToString(den1);
			if(j*e!=1) den2ChangingString=Format.termArrayToString(den2);
			String questionExpanded ="\\frac{"+Format.termArrayToString(num1)+"}{" +
					den1ChangingString+"}"+
					operation+
					"\\frac{"+Format.termArrayToString(num2)+"}{" +
					den2ChangingString+"}";
			if(diff==3||diff==4){
				theAnswer.newLine("\\;", questionExpanded, "Expand the center term of both denominators in preparation to factor by grouping.");
			}
		}
		//factored form after grouping
		if(!singleTermDenominator){
		theAnswer.newLine("\\;", "\\frac{"+Format.termArrayToString(num1)+"}{\\left(" +
				Format.termArrayToString(denA1)+"\\right)\\left(" +
				Format.termArrayToString(denA2)+"\\right)"+"}"+
		operation+
		"\\frac{"+Format.termArrayToString(num2)+"}{\\left(" +
				Format.termArrayToString(denB1)+"\\right)\\left(" +
				Format.termArrayToString(denA2)+"\\right)"+"}", "Factor the denominator of both fractions.");
		//identify common denominator
		theAnswer.newTextLine("In this case, the Least Common Denominator (LCD) is#\\left("+
				Format.termArrayToString(denB1)+"\\right)\\left("+
				Format.termArrayToString(denA1)+"\\right)\\left("+
				Format.termArrayToString(denA2)+"\\right)#");
		}else{//factor out the GCD
			
			//identify the GCF
			theAnswer.newTextLine("The Greatest Common Factor (GCF) of #"+Format.termArrayToString(den1)+"# and #"+Format.termArrayToString(den2)+"# is#\\left("+
					gcdTermFactored+"\\right)#");

			theAnswer.newLine("\\;", "\\frac{"+Format.termArrayToString(num1)+"}{\\left(" +
					gcdTermFactored+"\\right)\\left(" +
					cTermFactored+"\\right)"+"}"+
			operation+
			"\\frac{"+Format.termArrayToString(num2)+"}{\\left(" +
					gcdTermFactored+"\\right)\\left(" +
					jTermFactored+"\\right)"+"}", "Factor out the GCD of both denominators.");	
			//identify common denominator
			
			theAnswer.newTextLine("In this case, the Least Common Denominator (LCD) is#\\left("+
					gcdTermFactored+"\\right)\\left("+
					cTermFactored+"\\right)\\left("+
					jTermFactored+"\\right)# which is equal to #" +
					combinedFactors+"#");
		}
		
		
	


		if(requireAdding){
			//multiply both fractions to get LCD
			
			theAnswer.newLine("\\;", "\\frac{\\left(" +
					Format.termArrayToString(denB1)+"\\right)}{\\left(" +
					Format.termArrayToString(denB1)+"\\right)}\\frac{\\left("+Format.termArrayToString(num1)+"\\right)}{\\left(" +
					Format.termArrayToString(denA1)+"\\right)\\left(" +
					Format.termArrayToString(denA2)+"\\right)"+"}"+
					operation+
					"\\frac{\\left(" +
					Format.termArrayToString(denA1)+"\\right)}{\\left(" +
					Format.termArrayToString(denA1)+"\\right)}\\frac{\\left("+Format.termArrayToString(num2)+"\\right)}{\\left(" +
					Format.termArrayToString(denB1)+"\\right)\\left(" +
					Format.termArrayToString(denA2)+"\\right)"+"}", "Multiply to get the LCD of both fractions.");

			theAnswer.newLine("\\;", "\\frac{"+Format.termArrayToString(Ops.distribute(num1, denB1))+"}{\\left(" +
					Format.termArrayToString(denB1)+"\\right)\\left(" +
					Format.termArrayToString(denA1)+"\\right)\\left(" +
					Format.termArrayToString(denA2)+"\\right)"+"}"+
					operation+
					"\\frac{"+Format.termArrayToString(Ops.distribute(num2, denA1))+"}{\\left(" +
					Format.termArrayToString(denB1)+"\\right)\\left(" +
					Format.termArrayToString(denA2)+"\\right)\\left(" +
					Format.termArrayToString(denA1)+"\\right)}", "Distribute in numerators.");

			if(!singleTermDenominator){
				theAnswer.newLine("\\;", "\\frac{"+Format.termArrayToString(Ops.combineExpressions(new Expression(Ops.distribute(num1, denB1)), new Expression(Ops.distribute(num2, denA1))))+"}{\\left(" +
						Format.termArrayToString(denB1)+"\\right)\\left(" +
						Format.termArrayToString(denA2)+"\\right)\\left(" +
						Format.termArrayToString(denA1)+"\\right)}", "Combine fractions with common denominator and simplify.");
			}else{
				theAnswer.newLine("\\;", "\\frac{"+Format.termArrayToString(Ops.combineExpressions(new Expression(Ops.distribute(num1, denB1)), new Expression(Ops.distribute(num2, denA1))))+"}{" +
						combinedFactors+"}", "Combine fractions with common denominator and simplify. (The denominator is the same as the LCD.)");
			}
		}
		theAnswer.finish();//cannot omit this or there will be an error!
		answer=theAnswer.getLatexTabular();
		
	}
	
	public Term createATerm(int difficulty, String variable){
		Term t;
		if (difficulty== 1 || difficulty ==2) t = new Term(1);
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
	
	public Expression randExpression(String var, int diff){
		int coefficient = 1;
		if (diff == 3 || diff == 4) {
			coefficient = Ops.randomInt(2, diff+1);
		}
		Term variableTerm = new Term(coefficient, var);
		int constant = Ops.randomInt(1, diff*2);
		if (Math.random()>.5) constant = constant * (-1);
		Term constantTerm = new Term (constant);
		Term[] t = {variableTerm, constantTerm};
		Expression e = new Expression(t);
		return e;
	}
	
}
