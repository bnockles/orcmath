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


public class Term implements Comparable<Term>{
	String type;
	int coefficient;
	public boolean isPositive=true;
	public boolean imaginary=false;
	public boolean containsARadical=false;
	public boolean containsExpressionArray=false;
	int iExponent;
	String variables;
	//this part is crazy. an expression that is PART of a term, such as 2x(x-1) - as a numerator
	ArrayList<Expression> expressionFactors = new ArrayList<Expression>();;
	Term denominator;
	ArrayList<SimplestRadicalForm> radicalComponent;
	ArrayList<ConstantFactor> primeFactorization;
	ArrayList<Variable> variableFactorization;
	int degree;

	public static String VARIABLE_TYPE="variable";
	public static String CONSTANT_TYPE="constant";

	public Term(int givenCoefficient, String givenVariables)
	{
		//System.out.println("\n Creating a new term");
		type = "variable";
		coefficient = givenCoefficient;
		if (coefficient<0){
			isPositive=false;
		}
		variables = Format.addBracketsAroundExponents(givenVariables);
		//        expressionFactors = new ArrayList<Expression>();
		//        System.out.println("The coefficient is: " + coefficient + ". Now we will work on interpreting the variable string, "+givenVariables+"\nwith brackets, this is read as: "+variables);

		//radicalComponent = new SimplestRadicalForm(1,1); 
		primeFactorization = determinePrimeFactorization(coefficient);
		variableFactorization = variableStringToArrayList(variables);
		degree=determineDegree();
		//        System.out.println("Term: This is the variable string: " + variables + ". The degree is: "+degree);

	}

	public Term(String givenVariables)
	{
		//System.out.println("\n Creating a new term");
		type = "variable";
		coefficient = 1;
		variables = Format.addBracketsAroundExponents(givenVariables);
		//        expressionFactors = new ArrayList<Expression>();
		//System.out.println("This is the variable string: " + variables + ". Now we will factorize.");
		primeFactorization = determinePrimeFactorization(1);
		variableFactorization = variableStringToArrayList(variables);
		degree=determineDegree();
	}

	public Term(int givenConstant)
	{
		//System.out.println("Creating a new term");
		type = "constant";
		coefficient = givenConstant;
		if (coefficient<0){
			isPositive=false;
		}
		variables = "";
		//    	expressionFactors = new ArrayList<Expression>();
		//radicalComponent = new SimplestRadicalForm(1,1); it cannot have a radical component because every radical component needs a term component
		primeFactorization = determinePrimeFactorization(givenConstant);
		variableFactorization = variableStringToArrayList(variables);
	}

	public Term(ArrayList<ConstantFactor> cf, ArrayList<Variable> vf)
	{
		type = "variable";
		coefficient = productFromArrayList(cf);
		variables = stringFromArrayList(vf);
		//        expressionFactors = new ArrayList<Expression>();
		//radicalComponent = new SimplestRadicalForm(1,1);
		primeFactorization = cf; 
		variableFactorization = vf;

	}

	public Term(SimplestRadicalForm radical)
	{
		type = radical.getFactor().getType();
		coefficient = radical.getFactor().getCoefficient();
		if (coefficient<0){
			isPositive=false;
		}
		variables = radical.getFactor().getVariables();
		//        expressionFactors = new ArrayList<Expression>();
		if (!Ops.areEqualTerms(radical.getRadicand(), new Term(1))){
			radicalComponent=new ArrayList<SimplestRadicalForm>();
			this.radicalComponent.add(new SimplestRadicalForm(radical.getRoot(), radical.getRadicand()));
			System.out.println("In the term constructor, this radical was added:"+radical.getRadicand());
			containsARadical=true;
		}
		primeFactorization = determinePrimeFactorization(coefficient);
		variableFactorization = variableStringToArrayList(variables);
	}

	public void addRadicalComponent(ArrayList<SimplestRadicalForm> component){
		//radical is only added if it is not trivial
		for(int index=0; index<component.size(); index++){
			if(!component.get(index).toString().equals("1")){
				if(!containsARadical){
					radicalComponent=new ArrayList<SimplestRadicalForm>();
				}
				radicalComponent.add(component.get(index));
				containsARadical=true;
			}
		}
	}

	public void addRadicalComponent(SimplestRadicalForm component){
		//radical is only added if it is not trivial
		if(!component.toString().equals("1")){
			if(!containsARadical){
				radicalComponent=new ArrayList<SimplestRadicalForm>();
			}
			radicalComponent.add(component);
			containsARadical=true;
		}
	}


	public Term(Fraction f)
	{
		type = "rational";
		coefficient = f.getNumerator().getCoefficient();
		//System.out.println("the numerator coefficient is: " + coefficient);
		if (coefficient<0){
			isPositive=false;
		}

		if (f.getNumerator().getVariableFactorization()!= null) variables = stringFromArrayList(f.getNumerator().getVariableFactorization());
		else {
			variables = "";
			if(f.getDenom() == 1){
				type = Term.CONSTANT_TYPE;
			}
		}
		//        expressionFactors = new ArrayList<Expression>();
		if(f.getExpressionNumerator()!=null){
			if (!f.getExpressionNumerator().isEmpty())
				appendExpressionFactors(f.getExpressionNumerator());
		}
		//System.out.println("Fraction was just made, with the following expressionFactors: " + Format.expressionArrayToString(expressionFactors));
		//System.out.println("But its input expressionFactors were " + Format.expressionArrayToString(f.getExpressionNumerator()));
		//System.out.println("The variables are: " + variables);
		this.denominator = f.getDenominator();
		//System.out.println("the denominator is: " + this.denominator);
		//radicalComponent = new SimplestRadicalForm(1,1);
		primeFactorization = determinePrimeFactorization(coefficient);
		//        System.out.println("Creating a rational term with numerator variables "+f.getVariableNumerator());
		//        System.out.println("Creating a rational term with denominator "+f.getDenominator());
		if(f.getVariableNumerator() != null){
			variableFactorization = f.getVariableNumerator();
		}else{
			variableFactorization = new ArrayList<Variable>();
		}

		//standard constructor
		//		//System.out.println("\n Creating a new term");
		//		type = "variable";
		//		coefficient = givenCoefficient;
		//		if (coefficient<0){
		//			isPositive=false;
		//		}
		//		variables = Format.addBracketsAroundExponents(givenVariables);
		//		//        expressionFactors = new ArrayList<Expression>();
		//		//        System.out.println("The coefficient is: " + coefficient + ". Now we will work on interpreting the variable string, "+givenVariables+"\nwith brackets, this is read as: "+variables);
		//
		//		//radicalComponent = new SimplestRadicalForm(1,1); 
		//		primeFactorization = determinePrimeFactorization(coefficient);
		//		variableFactorization = variableStringToArrayList(variables);
		//		degree=determineDegree();

		//		System.out.println("Term.java Creating a fraction using constructor A : "+f.getNumer()+"/"+f.getDenom()+" result ="+toString());
	}

	public Term(Fraction f, String variables)
	{
		type = "rational";
		coefficient = f.getNumer();
		if (coefficient<0){
			isPositive=false;
		}
		this.denominator=new Term (f.getDenom());

		variableFactorization = variableStringToArrayList(variables);
		if (f.getVariableNumerator()!= null){
			for (int index = 0; index<f.getVariableNumerator().size(); index ++)
				variableFactorization.add(f.getVariableNumerator().get(index)); 
			this.variables = stringFromArrayList(variableFactorization);
		}
		else this.variables = variables;
		//        expressionFactors = new ArrayList<Expression>();
		//radicalComponent = new SimplestRadicalForm(1,1);
		primeFactorization = determinePrimeFactorization(f.getNumer());
		System.out.println("Term.java Creating a fraction using constructor B : "+f.getNumer()+"/"+f.getDenom()+" result ="+toString());
	}

	public Term (Fraction[] f){
		//System.out.println("The constructor Term (FractionArray) has been called");
		if (f.length==1){
			type = "rational";
			coefficient = f[0].getNumer();
			if (coefficient<0){
				isPositive=false;
			}
			if (f[0].getVariableNumerator()!= null) variables = stringFromArrayList(f[0].getVariableNumerator());
			else variables = "";
			//            expressionFactors = new ArrayList<Expression>();
			this.denominator = f[0].getDenominator();
			//this.denominator.simplifyExpressionFactors();
			//System.out.println("the denominator is: " + this.denominator);
			//radicalComponent = new SimplestRadicalForm(1,1);
			primeFactorization = determinePrimeFactorization(coefficient);
			variableFactorization = f[0].getVariableNumerator();
		}
		else{
			type = "rational";
			Term[] numerator = new Term[f.length];
			for (int index = 0; index<f.length; index++){
				numerator[index] = f[index].getNumerator();
			}
			coefficient = 1;
			variables = "";
			//    	expressionFactors = new ArrayList<Expression>();
			//radicalComponent = new SimplestRadicalForm(1,1); it cannot hav a radical component because every radical component needs a term component
			primeFactorization = determinePrimeFactorization(1);
			variableFactorization = variableStringToArrayList(variables);
			//expressionFactors = new Expression[1];
			System.out.println(numerator.length);
			Expression e = new Expression(numerator);
			expressionFactors.add(e);
			this.denominator = f[0].getDenominator();
		}
	}

	public void appendExpressionFactor(Expression e){
		if(!containsExpressionArray){
			expressionFactors=new ArrayList<Expression>();
		}
		containsExpressionArray=true;   	
		expressionFactors.add(e);
		//simplifyExpressionFactors();
	}   

	public void appendExpressionFactors(ArrayList<Expression> e){
		if(!containsExpressionArray){
			expressionFactors=new ArrayList<Expression>();
		}
		containsExpressionArray=true;  
		for (int index = 0; index<e.size(); index ++) expressionFactors.add(e.get(index));
		//simplifyExpressionFactors();
	}

	public Term(int givenCoefficient, ArrayList<Variable> givenVariableArrayList) {
		type = "variable";
		coefficient = givenCoefficient;
		if (coefficient<0){
			isPositive=false;
		}
		variables = stringFromArrayList(givenVariableArrayList);
		//        expressionFactors = new ArrayList<Expression>();
		primeFactorization = determinePrimeFactorization(coefficient);
		variableFactorization = givenVariableArrayList;
		degree=determineDegree();
	}

	public void makeImaginary(int iExponent)
	{
		imaginary = true;
		this.iExponent = iExponent;
		//the 'i' is appended in the toString method.
	}

	public int compareTo(Term compareTerm){
		int compareDegree = ((Term) compareTerm).getDegree();
		//Descending order (switch for ascending order)
		return compareDegree-this.degree;
	}

	public int determineDegree(){
		if (variableFactorization!=null){
			if (!variableFactorization.isEmpty()){
				Collections.sort(variableFactorization);
				return variableFactorization.get(0).getExponent();
			}
			else{
				return 0;
			}
		}
		else return 0;
	}


	public static Term[] ArrayListToTermArray(ArrayList<Term> terms)
	{
		Term [] termArray = new Term[terms.size()];
		for (int index=0; index < terms.size(); index ++)
		{
			termArray[index] = terms.get(index);
		}
		return termArray;
	}

	public static char[] stripExponents(ArrayList<Variable> variableExpression){
		char[] variablesOnly = new char[variableExpression.size()];
		for (int index = 0; index< variableExpression.size(); index++){
			variablesOnly[index] = variableExpression.get(index).getBase();
		}
		return variablesOnly;
	}

	public static ArrayList<Variable> combineDuplicateVariables(ArrayList<Variable> variableStem){//this method was designed to combine stems that contain the same variable listed twice
		//System.out.println("The combineDuplicateVariables method has been called.");

		//if (!variableStem.isEmpty()){//I had to add this line to avoid a nullPointerException
		Collections.sort(variableStem);
		//This part adds exponents of like bases.
		for (int checkDuplicate=1; checkDuplicate<variableStem.size(); checkDuplicate++){
			if (checkDuplicate-1>=0 && variableStem.get(checkDuplicate).getBase()==variableStem.get(checkDuplicate-1).getBase()){
				//    				System.out.println("(Term) We found a match! "+variableStem.get(checkDuplicate)+" and "+variableStem.get(checkDuplicate-1));
				int exp = variableStem.get(checkDuplicate).getExponent()+variableStem.get(checkDuplicate-1).getExponent();
				variableStem.get(checkDuplicate-1).setExponent(exp);
				variableStem.remove(checkDuplicate);
				//This part removes exponents that have a base of 0
				if(variableStem.get(checkDuplicate-1).getExponent()==0){
					variableStem.remove(checkDuplicate-1);
					checkDuplicate=checkDuplicate-1;
				}
				variableStem.trimToSize();
				checkDuplicate=checkDuplicate-1;//the list has been shorten so a new item has taken the place of what was in the index.
			}
		}

		//}
		return variableStem; 	
	}

	public void simplifyExpressionFactors(){
		//    	System.out.println("The simplifyExpreessionFactors method is called. The length of the expressionFactors arrayList is " + expressionFactors.size());
		for (int index = 0; index < expressionFactors.size(); index ++){
			Expression currentExpression = expressionFactors.get(index);
			if (currentExpression.getTermsOfExpression().length == 1){
				//System.out.println("\n\none expression is alone:" + currentExpression.getTermsOfExpression()[0]);
				Term raised = Ops.pow(currentExpression.getTermsOfExpression()[0], currentExpression.getExponent());
				//System.out.println("This was the coefficient of the term: " + this.coefficient);
				coefficient = coefficient* raised.getCoefficient();
				//System.out.println("This is the new coefficient of the term: " + this.coefficient);
				//System.out.println("This was the variableFactorization of the term: " + stringFromArrayList(variableFactorization));
				variableFactorization.addAll(raised.getVariableFactorization());
				variableFactorization = combineDuplicateVariables(variableFactorization);
				//System.out.println("This is the new variableFactorization of the term: " + stringFromArrayList(variableFactorization));
				expressionFactors.remove(index);
				index--;
			}
			//when an expression is longer that 1 term long, factor out the gcf
			else {
				//System.out.println("\n\nFinding a gcf. These are the terms " + Format.termArrayToString(currentExpression.getTermsOfExpression()));
				Term gcf = Ops.findGCF(currentExpression);
				//System.out.println("This is the gcf we found: " + gcf);
				this.coefficient = this.coefficient* gcf.getCoefficient();
				variableFactorization.addAll(gcf.getVariableFactorization());
				expressionFactors.remove(currentExpression);

				Expression replacement = new Expression(Ops.divideExpressionByTerm(currentExpression.getTermsOfExpression(), gcf));
				expressionFactors.add(replacement);

				//currentExpression = new Expression(Ops.divideExpressionByTerm(currentExpression.getTermsOfExpression(), gcf));
				//System.out.println("This was left after dividing by the gcf: " + replacement);
			}
			//after the gcf is removed, we can try to factor by difference of squares

			if (currentExpression.getTermsOfExpression().length==2 
					&& Term.getCopy(currentExpression.getTermsOfExpression()[0]).isSquare() 
					&& !currentExpression.getTermsOfExpression()[1].getSign() 
					&& Term.getCopy(currentExpression.getTermsOfExpression()[1]).isSquare()){
				//    			System.out.println("difference of squares called");
				System.out.println(Format.expressionArrayToString(Ops.factorDifferenceOfSquares(currentExpression.getTermsOfExpression()[0], currentExpression.getTermsOfExpression()[1])));
				expressionFactors.addAll(Ops.factorDifferenceOfSquares(currentExpression.getTermsOfExpression()[0], currentExpression.getTermsOfExpression()[1]));
				expressionFactors.remove(index);
				index--;
			}
		}
		if (coefficient<0) isPositive=false;
		primeFactorization = determinePrimeFactorization(coefficient);
		variables = stringFromArrayList(variableFactorization);
	}

	public static ArrayList<Expression> combineDuplicateExpressions(ArrayList<Expression> expressionArray){//this method was designed to combine stems that contain the same variable listed twice

		//This part adds exponents of like expressions.
		for (int currentCheckIndex = 0; currentCheckIndex<expressionArray.size(); currentCheckIndex++){
			for (int checkDuplicate=currentCheckIndex+1; checkDuplicate<expressionArray.size(); checkDuplicate++){
				if (expressionArray.get(currentCheckIndex).equals(expressionArray.get(checkDuplicate))){
					//System.out.println("We found a match! ");
					int exp = expressionArray.get(currentCheckIndex).getExponent()+expressionArray.get(checkDuplicate).getExponent();
					expressionArray.get(currentCheckIndex).setExponent(exp);
					//System.out.println("The new exponent is " + expressionArray.get(currentCheckIndex).getExponent());
					//System.out.println("We are removing " + expressionArray.get(checkDuplicate));
					expressionArray.remove(checkDuplicate);
					//This part removes exponents that have a base of 0
					if(expressionArray.get(currentCheckIndex).getExponent()==0){
						//System.out.println("We are removing " + expressionArray.get(currentCheckIndex));
						expressionArray.remove(currentCheckIndex);
						if (currentCheckIndex>0)
							currentCheckIndex=currentCheckIndex-1;
					}
					expressionArray.trimToSize();
					if (checkDuplicate>0)
						checkDuplicate=checkDuplicate-1;//the list has been shorten so a new item has taken the place of what was in the index.
				}
				else if (expressionArray.get(currentCheckIndex).isOppositeOf(expressionArray.get(checkDuplicate))){
					//System.out.println("We found an opposite match! ");
					int exp = expressionArray.get(currentCheckIndex).getExponent()+expressionArray.get(checkDuplicate).getExponent();
					expressionArray.get(currentCheckIndex).setExponent(exp);
					expressionArray.remove(checkDuplicate);
					Term[] changeSign = {new Term(-1)};
					Expression toChangeSign = new Expression(changeSign);
					expressionArray.add(0,toChangeSign);
					//we just added a term at the beginning so everything has moved forward, however checkDuplicate did not move forward because it was removed
					currentCheckIndex++;
					if(expressionArray.get(currentCheckIndex).getExponent()==0){
						expressionArray.remove(currentCheckIndex);
						currentCheckIndex=currentCheckIndex-1;
					}
					expressionArray.trimToSize();
				}
			}
		}

		//}
		return expressionArray; 	
	}

	public static ArrayList<ConstantFactor> determinePrimeFactorization(int coefficient)
	{
		//System.out.println("\t We are determining the prime factorization of " + coefficient);
		ArrayList<ConstantFactor> primeFactorization = new ArrayList<ConstantFactor>();
		int remainder = coefficient;
		for (int tryFactor=2; tryFactor<=remainder; tryFactor++)
		{
			//System.out.println("Checking to see if " + remainder + " is divisible by " + tryFactor);
			if (Ops.isInteger((double)remainder/(double)tryFactor))
			{
				//System.out.println(remainder + " is divisible by " + tryFactor);
				remainder=remainder/tryFactor;
				ConstantFactor factor=new ConstantFactor(tryFactor);
				//System.out.println("The facor is " + factor.getBase() + " and the exponent is " + factor.getExponent());
				while (Ops.isInteger((double)remainder/(double)tryFactor))
				{
					factor.setExponent(factor.getExponent()+1);
					//System.out.print(", " + factor.getExponent());
					remainder=remainder/tryFactor;
				}
				//System.out.println("\t Whew! finished testing this factor: " + factor.getBase() + " with exponent: " + factor.getExponent());
				primeFactorization.add(factor);
			}
		}
		return primeFactorization; 
	}

	public static ArrayList<Variable> variableStringToArrayList(String stringOfVariables)
	{
		ArrayList<Variable> variableFactorization = new ArrayList<Variable>();
		String variables=stringOfVariables.replaceAll("[\\{\\}\\^*]", "");
		//        System.out.println("\n Now we will convert the variable string, "+stringOfVariables+",to an array list.");
		//        System.out.println("This is the variable string without carets: " + variables );
		if (variables.length()==1)
		{
			//System.out.println("There is only one variable of degree 1.");
			variableFactorization.add(new Variable(variables.charAt(0), 1));
		}
		else
		{
			boolean isPositive=true; //by default, exponents are positive unless a negative sign is encountered
			for (int index=1; index<variables.length(); index++)
			{

				//            	System.out.println("Term: This is the character being fed through the method:" + variables.charAt(index));
				if (Character.isDigit(variables.charAt(index)))//this section is for when an exponent is encountered.
				{
					//            		System.out.println("Term: The character is a digit: " + variables.charAt(index));
					int exponent = Character.getNumericValue(variables.charAt(index));
					if (!isPositive){
						exponent=(-1)*exponent;
					}
					int previousAlphaChar = 1;
					if (index+1<variables.length() && Character.isDigit(variables.charAt(index+1)))//this is when the exponent is >9
					{
						exponent = exponent*10 + Character.getNumericValue(variables.charAt(index+1));
						index++;
						previousAlphaChar ++;
					}
					Variable variable;
					if (isPositive){
						variable = new Variable(variables.charAt(index-previousAlphaChar),exponent);
					}
					else{
						variable = new Variable(variables.charAt(index-previousAlphaChar-1),exponent);
					}
					//            	    System.out.println("Term: The added variable: " + variable.toString());
					variableFactorization.add(variable);
					isPositive=true;//return to default
				}
				else//when the character is not an exponent
				{
					//System.out.println("The character is a letter: " + variables.charAt(index));
					if (variables.charAt(index)=='-'){
						System.out.println("Term: Recognize negative exponent");
						isPositive=false;
					}
					else{
						if (!Character.isDigit(variables.charAt(index-1)))//if the previous character is not a digit 
						{
							Variable variable = new Variable(variables.charAt(index-1),1);
							variableFactorization.add(variable);
							//System.out.println("The added variable: " + variable.toString());
							if(index+1==variables.length()){
								Variable variable2 = new Variable(variables.charAt(index),1);
								variableFactorization.add(variable2);  
								//System.out.println("The added variable, which is the last variable: " + variable.toString());
							}
						}
						else if(index+1==variables.length())
						{
							Variable variable = new Variable(variables.charAt(index),1);
							variableFactorization.add(variable);  
							//System.out.println("The added variable, which is the last variable: " + variable.toString());
						}
					}
				}
			}
		}
		return variableFactorization;
	}

	public static int productFromArrayList(ArrayList<ConstantFactor> list){
		int product = 1;
		for (int index=0; index< list.size(); index++)
		{
			product = product * (int)(Math.pow(list.get(index).getBase(),list.get(index).getExponent()));
		}
		return product;
	}

	public static String stringFromArrayList(ArrayList<Variable> list)
	{
		String s = "";
		if (list!=null){
			for (int index=0; index< list.size(); index++)
			{
				s = s + list.get(index).getBase();
				if (list.get(index).getExponent()!=1)
				{
					s = s + "^{" + list.get(index).getExponent() + "}";
				}
			}  
		}
		return s;
	}

	public boolean getSign() {
		return isPositive;
	}

	public void setSign(boolean trueForPositiveFalseForNegative) {
		isPositive = trueForPositiveFalseForNegative;
		if (isPositive && coefficient<0){
			coefficient=coefficient*(-1);
		}
		if (!isPositive && coefficient>0){
			coefficient=coefficient*(-1);
		}
	}

	public boolean containsRadical(){
		return containsARadical;
	}

	public String getType()
	{
		if(toString().matches("-?[0-9]+"))return CONSTANT_TYPE;
		else return type;
	}

	public boolean containsVariable(){
		return toString().matches("[0-9]+?[a-zA-Z]+");
	}

	public Term getDenominator() {
		if(denominator==null || denominator.toString().equals("")){
			denominator=new Term(1);
			return denominator;
		}else{
			return denominator;
		}
	}

	public void setDenominator(Term den) {
		denominator = den;
	}

	public int getCoefficient()
	{
		return coefficient;
	}

	public String getVariables()
	{
		return variables;
	}

	public ArrayList<SimplestRadicalForm> getRadicalComponent(){	
		if(containsARadical){
			return radicalComponent;
		}
		else{
			radicalComponent = new ArrayList<SimplestRadicalForm>();
			radicalComponent.add(new SimplestRadicalForm(2, 1));
			return radicalComponent;
		}
	}

	public void setExpressionFactors(ArrayList<Expression> expressionFactors) {
		this.expressionFactors = expressionFactors;
	}

	public ArrayList<Expression> getExpressionFactors() {
		return expressionFactors;
	}

	public boolean hasExpressionFactors(){
		return containsExpressionArray;
	}

	public void setType(String s)
	{
		type = s;
	}

	public ArrayList<ConstantFactor> getPrimeFactorization()
	{
		return primeFactorization;
	}

	public ArrayList<Variable> getVariableFactorization()
	{
		return variableFactorization;
	}

	public static Term asNumerator(Term t){
		Term numerator = new Term(t.getCoefficient(),t.getVariables());
		numerator.setType("variable");
		if(t.hasExpressionFactors()){
			ArrayList<Expression> e = new ArrayList<Expression>();
			for (int expressionIndex = 0; expressionIndex<t.getExpressionFactors().size(); expressionIndex++){
				e.add(Expression.getCopy(t.getExpressionFactors().get(expressionIndex)));
			}
			numerator.setExpressionFactors(e);
		}
		if (!t.getType().equals("rational") && 
				!t.getType().equals("constant") && 
				!t.getType().equals("variable") &&
				!t.getType().equals("rational"))
			System.out.println("The asNumerator(Term) method is being called but the method is not yet defined for the type of term being copied.");
		return numerator;
	}

	public static String termArrayToString(Term[] expression)
	{
		String string=expression[0].toString();

		for (int index=1; index<expression.length; index++)
		{
			if (expression[index].getSign())//returns true for positive
			{
				string = string + " + " + expression[index];
			}
			else
			{
				int temp = expression[index].getCoefficient() * (-1);
				string=string + " - " + temp + expression[index].getVariables();
			}
		}
		return string;
	}

	private String iExponentString(){
		if (iExponent==1) return "";
		else return "^{" + iExponent + "}";  			
	}

	public int getiExponent() {
		return iExponent;
	}

	public void setiExponent(int iExponent) {
		this.iExponent = iExponent;
	}

	public String toString()
	{
		//    	if(coefficient==0)return ""+0;
		String s;
		//what to do when the coefficient is trivial (1 or -1)
		if ((coefficient==1 || coefficient==-1)){	
			//when it is not rational AND the coefficient is trivial
			if (!type.equals("rational") || getDenominator().toString().equals("1")){
				//when there is no variable AND when it is not rational AND the coefficient is trivial
				if (variables.isEmpty()){
					//when it is positive AND when there is no variable AND when it is not rational AND the coefficient is trivial
					if (isPositive){
						if (imaginary) s= "\\imath" + iExponentString() + Format.expressionArrayToString(expressionFactors) ;
						else if (!hasExpressionFactors()){
							if (containsARadical){
								s= "";
							}
							else{
								s= "1" ;
							}
						}
						else s= Format.expressionArrayToString(expressionFactors);
					}
					//when it is negative AND when there is no variable AND when it is not rational AND the coefficient is trivial
					else{
						if (imaginary) s= "-\\imath" + Format.expressionArrayToString(expressionFactors) ;
						else if (!hasExpressionFactors()) s= "-1";
						else s= "-" + Format.expressionArrayToString(expressionFactors);
					}
				}
				//when there is at least one variable AND when it is not rational AND the coefficient is trivial
				else {
					if (isPositive){
						if (imaginary) s= variables + "\\imath"+ Format.expressionArrayToString(expressionFactors) ;
						else s= variables + Format.expressionArrayToString(expressionFactors);
					}
					//when it is negative AND when there is at least one variable AND when it is not rational AND the coefficient is trivial
					else{
						if (imaginary) s= "-" + variables + "\\imath" + iExponentString() + Format.expressionArrayToString(expressionFactors) ;
						else s= "-" + variables + Format.expressionArrayToString(expressionFactors);
					}
				}
			}
			//when it is rational AND the coefficient is trivial
			else{
				//when there is no variable AND when it is rational AND the coefficient is trivial

				if (variables.isEmpty()){
					//when it is positive AND when there is no variable AND when it is rational AND the coefficient is trivial
					if (isPositive){
						if (imaginary) s= "\\frac{\\imath" + Format.expressionArrayToString(expressionFactors) +"}{" + denominator + "}";
						else if (!hasExpressionFactors())s= "\\frac{1}{" + denominator + "}";
						else s= "\\frac{" + Format.expressionArrayToString(expressionFactors)+"}{"+denominator + "}";
					}
					//when it is negative AND when there is no variable AND when it is rational AND the coefficient is trivial
					else{
						if (imaginary) s= "-\\frac{\\imath" + Format.expressionArrayToString(expressionFactors)+ "}{" + denominator + "}";
						else if (!hasExpressionFactors()) s= "-\\frac{1}{" + denominator + "}";
						else s= "-\\frac{" + Format.expressionArrayToString(expressionFactors) + "}{" + denominator + "}";
					}
				}
				//when there is at least one variable AND when it is rational AND the coefficient is trivial
				else {
					if (isPositive){
						if (imaginary) s= "\\frac{" + variables + "\\imath" + iExponentString() + Format.expressionArrayToString(expressionFactors) +"}{" + denominator + "}";
						else s= "\\frac{" + variables + Format.expressionArrayToString(expressionFactors) + "}{"+denominator+"}" ;
					}
					//when it is negative AND when there is at least one variable AND when it is rational AND the coefficient is trivial
					else{
						if (imaginary) s= "-\\frac{" + variables + "\\imath" + iExponentString() + Format.expressionArrayToString(expressionFactors) +"}{" + denominator + "}";
						else s= "-\\frac{" + variables + Format.expressionArrayToString(expressionFactors) +"}{" + denominator + "}";
					}
				}
			}
		}
		//what to do when the coefficient is not trivial (1 or -1)
		else{	
			if (coefficient==0) s= "0";
			//when it is not rational AND the coefficient is not trivial
			if (!type.equals("rational") || getDenominator().toString().equals("1")){
				if (imaginary) s= coefficient + "\\imath" + iExponentString() + variables + Format.expressionArrayToString(expressionFactors);
				else s= coefficient + variables + Format.expressionArrayToString(expressionFactors);
			}
			//when it is rational AND the coefficient is trivial
			else{
				if (imaginary) s= "\\frac{" + coefficient + "\\imath" + iExponentString() + variables + Format.expressionArrayToString(expressionFactors) + "}{" + denominator + "}";
				else s= "\\frac{" + coefficient + variables + Format.expressionArrayToString(expressionFactors) + "}{" + denominator + "}";
			}

		}
		if (containsARadical){
			boolean radicalsAreTrivial=true;
			for(int index=0; index<radicalComponent.size(); index++){
				if(!radicalComponent.get(index).toString().equals("1")){
					radicalsAreTrivial=false;
					s+=radicalComponent.get(index).toString();
				}
			}
			if(radicalsAreTrivial){
				s="1";
			}
		}
		return s;
	}	

	public int getDegree() {
		return determineDegree();
	}

	public void setVariableFactorization(ArrayList<Variable> variableFactorization) {
		this.variableFactorization = variableFactorization;
		variables = stringFromArrayList(variableFactorization);
	}

	public String getSimplifiedSquareRootString(){
		SimplestRadicalForm form = new SimplestRadicalForm(2, this);


		return form.toString();
	}

	public void setCoefficient(int i) {
		coefficient=i;		
	}

	public void clear() {
		coefficient=0;
		degree=0;
		isPositive=true;
		variables="";
		primeFactorization.clear();
		variableFactorization.clear();

	}

	public boolean isTrivial(){
		if (coefficient==0 && variables.equals("")){
			return true;
		}
		else{
			return false;
		}
	}

	public boolean isSquare(){
		boolean changed = false;
		if (!this.getSign()) {
			this.setSign(true);
			changed = true;
		}
		SimplestRadicalForm squareRoot = new SimplestRadicalForm(2, Term.getCopy(this));
		System.out.println("We are checking to see if the term, " + this + " is a perfect square.");
		System.out.println("The radical form, " + squareRoot);
		System.out.println("The radicand " + squareRoot.getRadicand());
		if (Ops.areEqualTerms(squareRoot.getRadicand(), new Term(1))){
			if (changed) this.setSign(false);
			return true;
		}
		else return false;
	}

	public static ArrayList<Variable> getVariableFactorizationCopy(Term term){
		ArrayList<Variable> copy = new ArrayList<Variable>();
		try{
			for (int index = 0; index<term.getVariableFactorization().size(); index ++){
				char base = term.getVariableFactorization().get(index).getBase();
				int exp = term.getVariableFactorization().get(index).getExponent();
				copy.add(new Variable(base,exp));
			}
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		return copy;

	}

	public static Term getCopy(Term term) {
		Term copy ;
		//		System.out.println("Term.class Making a copy of "+term+", which is of type "+term.getType());
		//for terms that are not constant terms
		if (!term.getType().equals(Term.CONSTANT_TYPE)){
			copy = new Term(term.getCoefficient(), getVariableFactorizationCopy(term));
			if (term.imaginary){
				copy.makeImaginary(term.getiExponent());
			}
		}
		//for constant terms only
		else {
			copy = new Term(term.getCoefficient());
			if (term.imaginary){
				copy.makeImaginary(term.getiExponent());
			}
		}
		if (term.denominator!= null && !term.getDenominator().toString().equals("1"))
			copy.setDenominator(Term.getCopy(term.getDenominator()));
		else copy.setDenominator(new Term(1));
		if (term.hasExpressionFactors()){
			ArrayList<Expression> e = new ArrayList<Expression>();
			for (int expressionIndex = 0; expressionIndex<term.getExpressionFactors().size(); expressionIndex++){
				e.add(Expression.getCopy(term.getExpressionFactors().get(expressionIndex)));
			}
			copy.setExpressionFactors(e);
		}
		copy.setType(term.getType().toString());
		if (!copy.getType().equals("rational") && 
				!copy.getType().equals("constant") && 
				!copy.getType().equals("variable") &&
				!copy.getType().equals("rational"))
			//			System.out.println("Term.class The getCopy(Term) method is being called but the method is not yet defined for the type of term being copied.");

			//copies the radical portion
			if(term.containsARadical){		
				ArrayList<SimplestRadicalForm> component = new ArrayList<SimplestRadicalForm>();
				for (int index=0; index<term.getRadicalComponent().size(); index++){
					System.out.println("Copying the radical component:"+term.getRadicalComponent().get(index));
					component.add(term.getRadicalComponent().get(index));
				}
				copy.addRadicalComponent(component);
				//			System.out.println("Term.class Copied:" +copy);
			}
		return copy;
	}

}
//import java.util.*;
//
//public class Term implements Comparable<Term>{//can delete "implements Comparable<Term>" if need be
//ArrayList<Integer> constantFactors;
//ArrayList<Character> variableFactors;
//String representation;
//int coefficient;
//int degree;
//boolean isPositive;
//String variables;
//
//	public Term(int x){
//		//System.out.println("");
//		//System.out.println("A new term is being made:");
//		coefficient = x;
//		if (x>=0){
//			isPositive=true;
//		}
//		else{
//			isPositive=false;
//		}
//		constantFactors=factorization(x);
//		variables="";
//		variableFactors=variableFactorization("");
//		degree=determineDegree();
//		representation="" + x;
//	}
//
//
//	public Term(int integer,String string){
//		//System.out.println("");
//		//System.out.println("A new term is being made:");
//		coefficient = integer;
//		if (integer>=0){
//			isPositive=true;
//		}
//		else{
//			isPositive=false;
//		}
//		variables = string;
//		constantFactors = factorization(integer);
//		variableFactors = variableFactorization(string);
//		degree=determineDegree();
//		representation = "" + coefficient + variables;
//
//	}
//	
//	
//	public Term(String string){
//		//System.out.println("");
//		//System.out.println("A new term is being made:");
//		char[] c =string.toCharArray();
//		if (c[0]=='-'){
//			isPositive=false;
//			string = string.replaceAll("-","");
//			//System.out.println("The term, " + string + " was marked as negative and '-' was removed.");
//			coefficient = -1;
//		}
//		else{
//			isPositive=true;
//			coefficient = 1;
//		}
//		variables = string;
//		constantFactors = new ArrayList<Integer>();
//		constantFactors.add(1);
//		variableFactors = variableFactorization(string);
//		degree=determineDegree();
//		representation = "" + variables;
//		//System.out.println("This is how the expression is represented:" + representation);
//		//System.out.println("degree =" + degree);
//	}
//	
//	public int compareTo(Term compareTerm){
//		int compareDegree = ((Term) compareTerm).getDegree();
//		//Descending order (switch for ascending order)
//		return compareDegree-this.degree;
//	}
//
//	public static String setVariableRepresentation(char[] vars){//used in the Ops class to determine variable representation after multiplication
//		//System.out.println("\n The beginning of the setVariableRepresentation method. The length of the String[] is " + vars.length);
//		String variableRepresentation = "";
//		if (vars.length>0){//if the array is more than 0
//		Arrays.sort(vars);//sort it alphabetically
//		int countingVariable=1;
//			for (int currentVariable=0;currentVariable<vars.length; currentVariable++){
//			while (currentVariable<vars.length-1 && vars[currentVariable]==vars[currentVariable+1]){
//				//System.out.println("We are counting the # of occurences of " + vars[currentVariable]);
//				currentVariable++;
//				countingVariable++;
//				//System.out.println("So far: " + (countingVariable));
//			}
//			//System.out.println("The number of consecutive " + vars[currentVariable] + "'s stopped at: " + (countingVariable));
//			if (countingVariable != 1){
//			variableRepresentation=variableRepresentation+ vars[currentVariable] +"^"+(countingVariable);
//			countingVariable=1;
//			}
//			else{
//			variableRepresentation=variableRepresentation+ vars[currentVariable];	
//			}
//			}
//				
//			}
//		//System.out.println(variableRepresentation);
//		return variableRepresentation;
//	}
//		
//	
//	public static ArrayList<Integer> factorization(int integer){
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		//System.out.println("The coefficient is " + integer);
//		//System.out.println("Let's factor it:");
//		for (int factorTest=2;factorTest<=integer;factorTest++){
//			if (Ops.isInteger(((double)integer)/((double)factorTest))){
//				Integer i = new Integer(factorTest);
//				list.add(i);
//				integer=integer/factorTest;
//				//System.out.println("We divided by " + factorTest + " to get " + integer);
//				factorTest=factorTest-1;
//			}
//		}
//		//System.out.println(list.toString());
//		return list;	
//	}
//
//	public static ArrayList<Character> variableFactorization(String string){
//		ArrayList<Character> list = new ArrayList<Character>();
//		string = string.replaceAll("[\\^-]", "");//takes the String and removes caret signs 
//		//System.out.println("The input string is: " + string);
//		char[] characterList;
//		characterList = string.toCharArray();
//		int index =1;
//		for (index=1;index<characterList.length;index++){
//			if (Character.isDigit(characterList[index])){
//				int exp = Character.getNumericValue(characterList[index]);
//				//System.out.println("The " + characterList[index-1]+ " exponent is " + exp);
//				Character c = new Character(characterList[index-1]);			
//				for (int j=0;j<exp;j++){
//					list.add(c);
//					//System.out.println("We have added the variable "+ c +", " + (j+1) + " time(s).");
//					//System.out.println(list);
//				}
//			}
//			else{
//				if (!Character.isDigit(characterList[index-1])){
//				//System.out.println(characterList[index-1]+ " does not have an exponent.");
//				Character c = new Character(characterList[index-1]);
//				list.add(c);
//				//System.out.println(characterList[index-1]+ " added to the list 1 time.");
//				//System.out.println("The list of variables is" + list);
//				}
//				//System.out.println(characterList[index-1]+ " is an exponent!");
//			}
//		}
//		if (index==characterList.length && !Character.isDigit(characterList[index-1])){
//			//System.out.println(characterList[index-1]+ " does not have an exponent.");
//			Character c = new Character(characterList[index-1]);
//			list.add(c);
//			//System.out.println(characterList[index-1]+ " added to the list 1 time.");
//			//System.out.println("The list of variables is" + list);
//		}
//		list.trimToSize();
//		Collections.sort(list);
//		//System.out.println("The list of variables is" + list);
//		return list;
//	}
//	
//	public static int[] integerToIntArray(ArrayList<Integer> integers){
//	    int[] array = new int[integers.size()];
//	    for (int i=0; i < array.length; i++)
//	    	{
//	        array[i] = integers.get(i).intValue();
//	    	}
//	    return array;
//	}
//
//	public int determineDegree(){
//		if (!variableFactors.isEmpty()){
//		Collections.sort(variableFactors);
//		int consecutiveFactors=1;
//		boolean stillCounting=true;
//		while (stillCounting && consecutiveFactors<variableFactors.size()){
//			if(variableFactors.get(consecutiveFactors-1).equals(variableFactors.get(consecutiveFactors))){
//				consecutiveFactors++;
//				//System.out.println("So far, the number of consecutive factors: " + (consecutiveFactors));		
//			}
//			else{
//				stillCounting=false;
//			}
//		}
//		return consecutiveFactors;
//		}
//		else{
//			return 0;
//		}
//	}
//	
//
//	//all following methods are getters and setters
//	//
//	//
//	//
//	
//
//	public int getCoefficient() {
//		return coefficient;
//	}
//
//	public void setCoefficient(int coefficient) {
//		this.coefficient = coefficient;
//	}
//
//	public String getVariables() {
//		return variables;
//	}
//
//	public void setVariables(String variables) {
//		this.variables = variables;
//	}
//
//	public int getDegree() {
//		return degree;
//	}
//
//	public void setDegree(int degree) {
//		this.degree = degree;
//	}
//	
//	
//	public ArrayList<Integer> getConstantFactors() {
//		return constantFactors;
//	}
//
//	public ArrayList<Character> getVariableFactors() {
//		return variableFactors;
//	}
//
//	public String toString() {
//		return representation;
//	}
//

//

//
//}
