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

public class Fraction
{
boolean ofConstants;
int numer;
int denom;
int gcf;
ArrayList<Variable> variableNumerator;
ArrayList<Variable> variableDenominator;
ArrayList<Expression> expressionNumerator;
ArrayList<Expression> expressionDenominator;
Term numerator;
Term denominator;
boolean isPositive;

    public Fraction(int num, int den)
    {
    	//factorizations don't work for negative numbers because the methods only count UP. 
    	//MUST convert to positive for all operations, then back to negative
    	isPositive = true;
    	if (num<0 && den > 0){
        	num = num *(-1);
        	isPositive = false;
        }
    	else if (num > 0 && den <0){
        	den = den *(-1);
        	isPositive = false;
        }
    	else if (num< 0 && den < 0){
    		num = num *(-1);
    		den = den *(-1);
    		isPositive = true;
    	}
    	
    	ofConstants = true;
        //System.out.println("\nA fraction of integers is being created.");
        int check=num;
        gcf=1;
        boolean checking=true;
        while (checking){
            if(Ops.isInteger((double)den/check) && Ops.isInteger((double)num/check)){
            	gcf=check;
                checking=false;
                numer = num/check;
                denom = den/check;
            }
            else
            {
                check=check-1;
            }
        }
        if (!isPositive) numer = numer*(-1);
        numerator=new Term(numer);
        denominator=new Term(denom);
        
    }
    
    private void initFromIntValues(int num, int den){
    	//factorizations don't work for negative numbers because the methods only count UP. 
    	//MUST convert to positive for all operations, then back to negative
    	isPositive = true;
    	if (num<0 && den > 0){
        	num = num *(-1);
        	isPositive = false;
        }
    	else if (num > 0 && den <0){
        	den = den *(-1);
        	isPositive = false;
        }
    	else if (num< 0 && den < 0){
    		num = num *(-1);
    		den = den *(-1);
    		isPositive = true;
    	}
    	
    	ofConstants = true;
        //System.out.println("\nA fraction of integers is being created.");
        int check=num;
        gcf=1;
        boolean checking=true;
        while (checking){
            if(Ops.isInteger((double)den/check) && Ops.isInteger((double)num/check)){
            	gcf=check;
                checking=false;
                numer = num/check;
                denom = den/check;
            }
            else
            {
                check=check-1;
            }
        }
        if (!isPositive) numer = numer*(-1);
        numerator=new Term(numer);
        denominator=new Term(denom);
    }

    public Fraction (Term num, Term den){
    	ofConstants = false;
        //System.out.println("\nA fraction of terms is being created.");
        
        int top= num.getCoefficient();
        int bottom= den.getCoefficient();
        Fraction coFraction = new Fraction(top, bottom);

        
        //TODO The method would be shorter if I simply wrote: numeratorList=num.getVariableFactorization
        //but I believe the changes would affect the original
        ArrayList<Variable> numeratorList = new ArrayList<Variable>();
        if (num.getVariableFactorization()!=null){
        for (int index=0; index<num.getVariableFactorization().size(); index++){
        	int exponent = num.getVariableFactorization().get(index).getExponent();
        	Variable listVariable = new Variable(num.getVariableFactorization().get(index).getBase(), exponent);
        	numeratorList.add(listVariable);
//        	System.out.println("This variable was added to the numerator: " + listVariable.toString());
        }
        }
        
        ArrayList<Variable> denominatorList = new ArrayList<Variable>();
        if (den.getVariableFactorization()!=null){
        for (int index=0; index<den.getVariableFactorization().size(); index++){
        	int exponent = (-1) * den.getVariableFactorization().get(index).getExponent();
        	Variable listVariable = new Variable(den.getVariableFactorization().get(index).getBase(), exponent);
        	denominatorList.add(listVariable);
//        	System.out.println("This variable was added to the denominator: " + listVariable.toString());
        }
        }
        
        
        ArrayList<ConstantFactor> defaultCoefficient = new ArrayList<ConstantFactor>();
        defaultCoefficient.add(new ConstantFactor(1));
        
        //this adds all expressions from the input num to a new ArrayList that will be added to the numerator
        ArrayList<Expression> numeratorExpressions = new ArrayList<Expression>();
        if(num.getExpressionFactors()!=null){
        	for (int index=0; index < num.getExpressionFactors().size(); index ++){
        		int exponent = num.getExpressionFactors().get(index).getExponent();
        		Expression e = Expression.getCopy(num.getExpressionFactors().get(index));
        		//Expression e = new Expression(num.getExpressionFactors().get(index).getTermsOfExpression(), exponent);
        		numeratorExpressions.add(e);
        	}
        }
        //this adds all expressions from the input den, gives them negative exponents and adds them to a new ArrayList that will be added to the numerator
        ArrayList<Expression> denominatorExpressions = new ArrayList<Expression>();
        if(den.getExpressionFactors()!=null){
        for (int index=0; index < den.getExpressionFactors().size(); index ++){
        	Expression e = new Expression(den.getExpressionFactors().get(index).getTermsOfExpression());
        	int exponent = den.getExpressionFactors().get(index).getExponent();
        	exponent = exponent * (-1);
        	e.setExponent(exponent);
        	denominatorExpressions.add(e);
        }
        }
        
        Term t1 = new Term(defaultCoefficient, numeratorList);
        Term t2 = new Term(defaultCoefficient, denominatorList);
        //now add the expressions to the numerator and denominator
        t1.appendExpressionFactors(numeratorExpressions);
        t2.appendExpressionFactors(denominatorExpressions);
//        System.out.println("(Fraction class) t1 = " + t1);
//        System.out.println("(Fraction class) t2 = " + t2);
        
        //since all of the variables in t2 have negative exponents, the following method 
        //cancels exponents       
        Term t3 = Ops.multiplyTerms(t1, t2);
//        System.out.println("(Fraction class) product = " + t3);
        
        
        //This section takes the result from above (a string of variables and exponents) and adds those with negative exponents to the denominator.
        variableNumerator=new ArrayList<Variable>();
        variableDenominator = new ArrayList<Variable>();
        for (int index = 0; index<t3.getVariableFactorization().size(); index++){
        	//System.out.println("This variable being checked has an exponent of : " + t3.getVariableFactorization().get(index));
        	if (t3.getVariableFactorization().get(index).getExponent()<0){
        		Variable var = new Variable(t3.getVariableFactorization().get(index).getBase(), (-1)*t3.getVariableFactorization().get(index).getExponent());
        		variableDenominator.add(var);
        	}
        	else if (t3.getVariableFactorization().get(index).getExponent()>0){
        		Variable var = new Variable(t3.getVariableFactorization().get(index).getBase(), t3.getVariableFactorization().get(index).getExponent());
        		variableNumerator.add(var);
        	}
        }
        
        //This section does the same with the expression factors
        expressionNumerator=new ArrayList<Expression>();
        expressionDenominator = new ArrayList<Expression>();
        for (int index = 0; index<t3.getExpressionFactors().size(); index++){
        	//System.out.println("This variable being checked has an exponent of : " + t3.getVariableFactorization().get(index));
        	if (t3.getExpressionFactors().get(index).getExponent()<0){
        		Expression a = Expression.getCopy(t3.getExpressionFactors().get(index));
        		a.setExponent(a.getExponent()*(-1));
        		expressionDenominator.add(a);	
        	}
        	else if (t3.getExpressionFactors().get(index).getExponent()>0){
        		Expression b = Expression.getCopy(t3.getExpressionFactors().get(index));
        		expressionNumerator.add(b);
        	}
        }
        
        
        if(num.getType().equals(Term.CONSTANT_TYPE)){
        	numerator = new Term (coFraction.getNumer());
        	ofConstants=true;
        }else{
        	numerator = new Term (coFraction.getNumer(), Term.stringFromArrayList(variableNumerator));
        	numerator.appendExpressionFactors(expressionNumerator);
        }
        if(den.getType().equals(Term.CONSTANT_TYPE)){
        	denominator=new Term (coFraction.getDenom());
//        	System.out.println("A constant fraction was made");
        }else{
        	denominator = new Term (coFraction.getDenom(), Term.stringFromArrayList(variableDenominator));
        	denominator.appendExpressionFactors(expressionDenominator);
        	ofConstants=false;
        }
        
        
//        System.out.println("Fraction ******************\n" +
//        		"Fraction created with input "+num.toString()+" and "+den.toString()+"\nResult has numerator = "+numerator.toString()+"("+numerator.getCoefficient()+") and denominator = "+denominator.toString());
      
        numer = coFraction.getNumer();
        denom = coFraction.getDenom();

        if (Term.stringFromArrayList(variableNumerator).isEmpty() && Term.stringFromArrayList(variableDenominator).isEmpty() && expressionNumerator.isEmpty() && expressionDenominator.isEmpty()){
        	ofConstants=true;
        }
    }
    

    
//the following constructor is for adding fractions. Since a fraction with a sum of terms in the numerator
//is actually a term array with like denominators, the individual terms should not be simplified, as in the 
    //normal constructor.
    public Fraction (Term unsimplifiedNumerator, Term unsimplifiedDenominator, boolean simplified){
    	if (simplified == true) new Fraction(unsimplifiedNumerator, unsimplifiedDenominator);
    	else{
    		numerator = unsimplifiedNumerator;
    		denominator = unsimplifiedDenominator;
    	}
    }
    
    
    /**
     * approximates a double using a fraction
     * @param d
     */
    public Fraction(double d) {
		double rounded = (int)(d*10000)/10000.0;
		int denominator = 1;
		while((rounded*denominator)%1 > 0.001 && (rounded*denominator)%1 < 0.999){
			denominator++;
		}
		System.out.println(d +" AS A FRACTION IS "+(int)(rounded*denominator+.5)+"/"+ denominator);
		initFromIntValues((int)(rounded*denominator+.5), denominator);
	}

	public int toInt(Fraction fraction){
        //if (ofConstants && denom==1)
        //{
            return numer;
        //}
        //else
        //{
        //throw Error;
        //} TODO figure out how to throw an exception when the denominator is not 1
    }
    
    public Fraction getReciprocal(){
    	Fraction reciprocal = new Fraction(this.getDenominator(),this.getNumerator());
    	return reciprocal;
    }
    
    public String toString()
    {
    	if (ofConstants == true){
        	if (numer==1 && denom==1){
        		return "1";
        	}
        	else if (numer==0){
        		return "0";
        	}
        	else if (denom==1){
        		return "" + numer;
        	}
        	else{
        		return "\\frac{" + numer + "}{" + denom + "}";
        	}
        }
        
        //when the term contains variables
        else{
        	
        	if (!denominator.toString().equals("1")) return "\\frac{" + numerator + "}{" + denominator + "}"; 
        	else return "" + numerator;
        }
    }

	public int getNumer() {
		return numer;
	}

	public void setNumer(int numer) {
		this.numer = numer;
	}

	public int getDenom() {
		return denom;
	}

	public void setDenom(int denom) {
		this.denom = denom;
	}

	public int getGcf() {
		return gcf;
	}
	
	public Term getNumerator() {
		return numerator;
	}

	public void setNumerator(Term numerator) {
		this.numerator = numerator;
	}

	public Term getDenominator() {
		return denominator;
	}

	public void setDenominator(Term denominator) {
		this.denominator = denominator;
	}

	public ArrayList<Variable> getVariableNumerator() {
		return variableNumerator;
	}

	public ArrayList<Variable> getVariableDenominator() {
		return variableDenominator;
	}

	public ArrayList<Expression> getExpressionNumerator() {
		return expressionNumerator;
	}

	public void setExpressionNumerator(ArrayList<Expression> expressionNumerator) {
		this.expressionNumerator = expressionNumerator;
	}

	public ArrayList<Expression> getExpressionDenominator() {
		return expressionDenominator;
	}

	public void setExpressionDenominator(ArrayList<Expression> expressionDenominator) {
		this.expressionDenominator = expressionDenominator;
	}
	
	
	
	
    
    
}