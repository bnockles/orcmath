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


public class Polynomial
{
    Term[] termArray;
    int degree;
    
    public Polynomial(int[] coefficients, char variable)
    {
        degree = coefficients.length-1;
     	//System.out.println("Starting a polynomial of degree: " + degree);
        termArray=new Term[coefficients.length];        
        for (int index=0;index<(coefficients.length); index++)
        {
            if (index<(degree))
            {
                int exponent = degree-index;
            	String variableString = "" + variable;
            	if (exponent !=1) variableString += "^" + exponent;
            	Term t=new Term(coefficients[index],variableString);
                termArray[index]=t;
            }
            else if (index==degree)
            {
                Term t=new Term(coefficients[index]);
                termArray[index]=t;
            }
        }
        
    }
    
    public Polynomial(Fraction[] coefficients, char variable)
    {
        degree = coefficients.length-1;
     	//System.out.println("Starting a polynomial of degree: " + degree);
        termArray=new Term[coefficients.length];        
        for (int index=0;index<(coefficients.length); index++)
        {
            if (index<(degree))
            {
                int exponent = degree-index;
            	String variableString = "" + variable;
            	if (exponent !=1) variableString += "^" + exponent;
            	Term t=new Term(coefficients[index],variableString);
                termArray[index]=t;
            }
            else if (index==degree)
            {
                Term t=new Term(coefficients[index]);
                termArray[index]=t;
            }
        }
        
    }
    
    public void increaseDegree(char variableToIcrease, int byHowMuch){
    	String s = "" + variableToIcrease;
    	if (byHowMuch!=1) s += "^" + byHowMuch;
    	for (int index = 0; index<termArray.length; index++){	
    		termArray[index] = Ops.multiplyTerms(new Term(1,s), termArray[index]);
    	}
    }
    
    
    public Term[] getTermArray() {
		return termArray;
	}

	public void setTermArray(Term[] termArray) {
		this.termArray = termArray;
	}

	public int getDegree() {
		return degree;
	}

	

	public String toString(){
    	return Format.termArrayToString(termArray);
    	}

}