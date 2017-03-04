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
package com.orcmath.objects;

import java.util.ArrayList;

public class Expression {
	Term[] terms;
	//TODO I have not written a way to represent exponents of an expression
	//this will be done in the Format class
	//I also need to make a constructor for an expression with an exponent
	int exponent;
	String representationAfterSimplification;
	public Term[] simplifiedVersion;
	String representationBeforeSimplification;

	public Expression(Term[] t){
		terms = t;
		exponent = 1;
		representationBeforeSimplification=determineRepresentation(terms);
		simplifiedVersion=Ops.combineLikeTerms(t);
		representationAfterSimplification = determineRepresentation(simplifiedVersion);
	}
	
	public Expression(Term[] t, int exponent){
		terms = t;
		this.exponent = exponent;
		representationBeforeSimplification=determineRepresentation(terms);
		simplifiedVersion=Ops.combineLikeTerms(t);
		representationAfterSimplification = determineRepresentation(simplifiedVersion);
		//System.out.println("Managed to combine like terms!");
	}
	
	public Expression(Term t){
		Term[] singleTerm = {t};
		terms=singleTerm;
		this.exponent = 1;
		representationBeforeSimplification=determineRepresentation(terms);
		simplifiedVersion=singleTerm;
		representationAfterSimplification = determineRepresentation(simplifiedVersion);		
	}
	
	public boolean containsAVariable(){
		boolean containsVar=false;
		for(Term t:terms){
			if (t.getType()==Term.VARIABLE_TYPE){
				containsVar=true;
				break;
			}
		}
		return containsVar;
	}
	
	private static String determineRepresentation(Term[] t){
		if (t.length==0) return "";
		String representation = "";
		
		char[] test = t[0].toString().toCharArray();
		//if the term is negative but the first character is not a negative sign, add a negative sign to the front
		if (!t[0].isPositive && test[0]!= '-'){
			representation+="-" + t[0].toString();
		}
		//if a negative sign is not needed, simply add the term EXCEPT...
		else{
			//only add the term to the string if its value is not zero EXCEPT if there is only ONE term
			if(t[0].getCoefficient()!=0 || t.length==1){
				representation=t[0].toString();	
			}
		}
		//after the first term has been decided, these rules apply for all following terms
		for (int index=1; index<t.length;index++){
			//0 terms don't get added
			if(t[index].getCoefficient()==0){
				//nothing happens
			}
			//positive terms get a '+' sign in front
			else if (t[index].isPositive){
				representation += "\\; +\\; " + t[index].toString();
			}
			//negative terms get a '-' sign in front
			else{
				String s= t[index].toString().replaceAll("[-]", "");//takes the String and removes minus sign 
				representation += "\\; -\\; " + s;
			}
		}

		return representation;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof Expression){
			return (this.hashCode()==obj.hashCode());
		}
		else{
		return false;
		}
	}
	
    public int hashCode(){
        int hash = 1;
        hash = 31 * hash + (null == representationAfterSimplification ? 0 : representationAfterSimplification.hashCode());
        return hash;
    }
    
	
    
    
	//this method is accessed when canceling opposite factors in the numerator and denominator of a fraction. 
	//all it does is return a boolean. The actual method in the fraction class changes the sign of the fraction
	//after the common expressions cancel
	public boolean isOppositeOf(Object obj){
		if(obj instanceof Expression){
			Term[] check2 = Ops.combineLikeTerms(((Expression)obj).simplifiedVersion).clone();
			for (int index = 0; index<check2.length; index++){
				boolean change = !check2[index].getSign();
				check2[index].setSign(change);
			}
			Expression changedSign = new Expression(check2);
			return (this.hashCode()==changedSign.hashCode());
		}
		else{
		return false;
		}
	}
	
	public Term[] getTermsOfExpression(){
		return terms;
	}

	public String toString() {
		return Format.termArrayToString(getTermsOfExpression());
	}

	public String getRepresentationBeforeSimplification() {
		return representationBeforeSimplification;
	}

	public void setRepresentationBeforeSimplification(
			String representationBeforeSimplification) {
		this.representationBeforeSimplification = representationBeforeSimplification;
	}


	public int getExponent() {
		return exponent;
	}

	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	public static ArrayList<Expression> quantifyExponents(ArrayList<Expression> arrayOfPolynomials) {
		ArrayList<Expression> lengthened = new ArrayList<Expression>();
		for (int atIndex = 0; atIndex< arrayOfPolynomials.size(); atIndex++){
			if (arrayOfPolynomials.get(atIndex).getExponent()<0){
				System.out.println("quantifyExponents, in the Expression class, not defined for negative exponents");
				return null;
			}
			else{
				int addIndex = 0;
				while (addIndex<arrayOfPolynomials.get(atIndex).getExponent()){
					lengthened.add(arrayOfPolynomials.get(atIndex));
					addIndex++;
				}	
			}
		}
		return lengthened;
		
	}

	public static Expression getCopy(Expression expression) {	
		Term[] terms = new Term[expression.getTermsOfExpression().length];
		for (int index = 0; index<expression.getTermsOfExpression().length; index++){
			terms[index] = Term.getCopy(expression.getTermsOfExpression()[index]);
		}
		Expression e = new Expression(terms, expression.getExponent());
		return e;
	}

	public static Expression linearExpression(int value, int variableValue, String var, int difficulty) {
	
		int coef = Ops.randomNotZero(-10, 10);
		int cons = value - coef*variableValue;
		int attemptCount = 0;
		while(Math.abs(cons)>100 && attemptCount < 10){
			coef = (int) (Math.random()*Ops.randomNotZero(-10, 10));
			cons = value - coef*variableValue;
			attemptCount ++;
		}
		if(attemptCount >= 10){
			System.out.println(" - - - - - The linearExpression method in the Expression class was not able to create an expression for the value "+value);
		}
		Term x = new Term(coef,var);
		Term c = new Term(cons);
		Term[] terms = {x,c};
		if(coef<0 && cons>0){
			terms[0] = c;
			terms[1] = x;
		}
		return new Expression(terms);
	}

	public static Expression constantExpression(int i) {
		Term x = new Term(i);
		Term[] terms = {x};
		return new Expression(terms);
	}
	
	public static Expression constantExpression(double i) {
		Term x = new Term(new Fraction(i));
		Term[] terms = {x};
		return new Expression(terms);
	}

	
}
