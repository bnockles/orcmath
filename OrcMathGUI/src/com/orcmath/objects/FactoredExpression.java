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

import java.util.*;

public class FactoredExpression
{
    Term[] expressionBeforeFactoring;
    ArrayList<ConstantFactor> gcfConstantFactors;
    ArrayList<Variable> gcfVariableFactors;
    Term gcf;
    Term[] factoredExpression;
    
    public FactoredExpression (Term[] terms)
    {
        expressionBeforeFactoring = terms;
        //factorCoefficients();
        //factorVariables();

        factorGcf(expressionBeforeFactoring);//initiates ArrayLists
//        System.out.println("Here is the gcf of coefficients: " + gcfConstantFactors);
//        System.out.println("Here is the gcf of variables: " + gcfVariableFactors);
        gcf = new Term(gcfConstantFactors,gcfVariableFactors);
        factoredExpression = Ops.divideExpressionByTerm(expressionBeforeFactoring, gcf);    
    }
      
    public void factorGcf(Term[] terms)
    {
        gcfConstantFactors = new ArrayList<ConstantFactor>();
        gcfVariableFactors = new ArrayList<Variable>();

        
    ArrayList<ConstantFactor> factorsOf1stTerm= new ArrayList<ConstantFactor>();
//all of the exponents begin as 1's
	for(int set=0; set< terms[0].getPrimeFactorization().size(); set ++){
		ConstantFactor c = new ConstantFactor(terms[0].getPrimeFactorization().get(set).getBase(), 1);
		factorsOf1stTerm.add(c);
	}
    
	System.out.println("Here is the term prime factorization: " + terms[0].getPrimeFactorization());
	System.out.println("Just the bases: " + factorsOf1stTerm);
    
    //select each constant factor and test to see if it is common
        for(int index=0; index<factorsOf1stTerm.size(); index ++)
            {
                
                //suppose the exponent is the same as the one in the first term
        		int possibleExponent = terms[0].getPrimeFactorization().get(index).getExponent();
        		System.out.println("Possible exponent is currently: " + possibleExponent);
                boolean isCommonFactor = false;
                //start at the 2nd term (index 1) and check to see if this base is present
                for (int compare =1; compare<terms.length; compare++){
                //we need to make another list with exponents of 1 - we only compare bases:
                ArrayList<ConstantFactor> basesOfAnotherTerm= new ArrayList<ConstantFactor>();
            		for(int set=0; set< terms[compare].getPrimeFactorization().size(); set ++){
            		ConstantFactor c = new ConstantFactor(terms[compare].getPrimeFactorization().get(set).getBase(), 1);
            		basesOfAnotherTerm.add(c);
            		}
                //now see if these two have something in common
                if (basesOfAnotherTerm.contains(factorsOf1stTerm.get(index)))
                {
                    isCommonFactor = true;
                    System.out.println("Possible exponent is currently: " + possibleExponent);
                    int indexOfMatch = basesOfAnotherTerm.indexOf(factorsOf1stTerm.get(index));
                    //here we reduce the possible exponent if the one in the other term is smaller
                    if (possibleExponent > terms[compare].getPrimeFactorization().get(indexOfMatch).getExponent()){
                            possibleExponent = terms[compare].getPrimeFactorization().get(indexOfMatch).getExponent();
                        }
                }
                else
                {
                    isCommonFactor = false;
                    //break because we do not need to check other terms
                    break;
                }
                }
                if (isCommonFactor) gcfConstantFactors.add(new ConstantFactor(factorsOf1stTerm.get(index).getBase(), possibleExponent));
                
            }
        
        //now the same is done with the variables
        ArrayList<Variable> variablesOf1stTerm= new ArrayList<Variable>();
        		for(int set=0; set< terms[0].getVariableFactorization().size(); set ++){
        			Variable v = new Variable(terms[0].getVariableFactorization().get(set).getBase(), 1);
        			variablesOf1stTerm.add(v);
        		}
        
        for(int index=0; index<variablesOf1stTerm.size(); index ++){
                int possibleExponent=terms[0].getVariableFactorization().get(index).getExponent();
                boolean isCommonFactor = false;
                for (int compare =1; compare<terms.length; compare++) {
                    //we need to make another list with exponents of 1 - we only compare bases:
                    ArrayList<Variable> variableBasesOfAnotherTerm= new ArrayList<Variable>();
                    		for(int set=0; set< terms[compare].getVariableFactorization().size(); set ++){
                    			Variable v = new Variable(terms[compare].getVariableFactorization().get(set).getBase(), 1);
                    			variableBasesOfAnotherTerm.add(v);
                    		}

                  //now see if these two have something in common
                if (variableBasesOfAnotherTerm.contains(variablesOf1stTerm.get(index)))
                {
                    isCommonFactor = true;
                    int indexOfMatch = variableBasesOfAnotherTerm.indexOf(variablesOf1stTerm.get(index));
                  //here we reduce the possible exponent if the one in the other term is smaller
                    if (possibleExponent > terms[compare].getVariableFactorization().get(indexOfMatch).getExponent())
                            possibleExponent = terms[compare].getVariableFactorization().get(indexOfMatch).getExponent();
                    //compare++;
                }
                else
                {
                    isCommonFactor = false;
                    break;
                }
                }
                if (isCommonFactor)
                {
                    gcfVariableFactors.add(new Variable(variablesOf1stTerm.get(index).getBase(), possibleExponent));
                }    
            }

        }

    
    
	public Term[] getExpressionBeforeFactoring() {
		return expressionBeforeFactoring;
	}

	public Term getGcf() {
		return gcf;
	}

	public Term[] getFactoredExpression() {
		return factoredExpression;
	}

	public String toString(){
	return "\\("+gcf + "\\)\\("+ Term.termArrayToString(factoredExpression) +"\\)";
	}
}