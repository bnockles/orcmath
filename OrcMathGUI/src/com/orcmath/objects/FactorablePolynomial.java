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

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

public class FactorablePolynomial {

	Term[] polynomial;
	ArrayList<Expression> factoredForm;
	String variable;
	
	//this method is dependent on all of the terms being based into it either being constant or rational (of constants)
	//IOW, a "root" can't be "2x".
	public FactorablePolynomial(Term[] roots, String variable){
		this.variable=variable;
		factoredForm = new ArrayList<Expression>();
		for (int termIndex = 0; termIndex<roots.length; termIndex++){
			if (!roots[termIndex].getType().equals("constant") && !roots[termIndex].getType().equals("rational")){
				System.out.println("Inappropriate term type fed into FactorablePolynomial method! Revise code calling this method.");
			}
			else{
				//by default, variable coefficient is 1...
				int variableCoefficient = 1;
				//...but it may vary if the root is a rational.For example, if a root is 3/4, then the coefficient is 4 so that we have
				//(4x-3) as the binomial
				if (roots[termIndex].getType().equals("rational")){
					System.out.println("THE ROOT IS RATIONAL: "+roots[termIndex].toString());
					variableCoefficient = roots[termIndex].getDenominator().getCoefficient();
				}
				Term variableTerm = new Term (variableCoefficient, variable);
				boolean oppSign = !roots[termIndex].getSign();
				Term[] binomial;
				if (roots[termIndex].getCoefficient()!=0){
					Term rootTerm = Term.asNumerator(roots[termIndex]);
					rootTerm.setSign(oppSign);
					binomial = new Term[2];
					binomial[0] = variableTerm;
					binomial[1] = rootTerm;
				}
				else {
					binomial = new Term[1];
					binomial[0]=variableTerm;
				}
//				System.out.println("This binomial has a root of " + roots[termIndex] + ": " + Format.termArrayToString(binomial));
				Expression factor = new Expression(binomial);
				factoredForm.add(factor);
			}
		}
//		System.out.println("This is from the constructor of the FactorablePolynomial class.\n" +
//				"a polynomial is being expanded from factored form: "+ Format.expressionArrayToString(getFactoredForm()));
		determineTermArray();
	}
	
	public void determineTermArray(){
		polynomial =  Ops.expand(factoredForm);
	}

	public Term[] getPolynomial() {
		return polynomial;
	}

	public void setPolynomial(Term[] polynomial) {
		this.polynomial = polynomial;
	}

	public ArrayList<Expression> getFactoredForm() {
		return factoredForm;
	}

	public void setFactoredForm(ArrayList<Expression> factoredForm) {
		this.factoredForm = factoredForm;
	}

	public String polynomialToString() {
		
		return Format.termArrayToString(Ops.combineLikeTerms(polynomial));
	}

	public String factoredToString() {
		ArrayList<Expression> uniqueExpressionStrings = new ArrayList<Expression>();
		
		for(Expression e:factoredForm){
			if (uniqueExpressionStrings.contains(e)){
				int exponent=uniqueExpressionStrings.get(uniqueExpressionStrings.indexOf(e)).getExponent()+1;
				uniqueExpressionStrings.get(uniqueExpressionStrings.indexOf(e)).setExponent(exponent);
			}else{
				uniqueExpressionStrings.add(e);
			}
		}
		return Format.expressionArrayToString(uniqueExpressionStrings);
	}
	
	public Term getATerm(){
		Term aTerm = new Term(0);
		for (int index = 0; index<polynomial.length; index++){
//			System.out.println("Checking the polynomial for an A term. Currently checking: "+polynomial[index]+"\nthe variables are: "+polynomial[index].getVariables());
			if (polynomial[index].getVariables().equals(variable+"^{2}")){
				aTerm=Term.getCopy(polynomial[index]);
			}
		}
		return aTerm;
	}
	
	public Term getBTerm(){
		Term bTerm = new Term(0);
		for (int index = 0; index<polynomial.length; index++){
			if (polynomial[index].getVariables().equals(variable)){
				bTerm=Term.getCopy(polynomial[index]);
			}
		}
		return bTerm;
	}
	
	public Term getCTerm(){
		Term cTerm = new Term(0);
		for (int index = 0; index<polynomial.length; index++){
			if (polynomial[index].getVariables().equals("")){
				cTerm=Term.getCopy(polynomial[index]);
			}
		}
		return cTerm;
	}
	
}
