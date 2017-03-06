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


import java.text.DecimalFormat;
import java.util.*;




public class Ops{

	public static boolean isInteger(double d){
		int integer=(int) Math.floor((d+.5));
		//        System.out.println("integer = "+integer+" and d ="+d);
		if (Math.abs(d-integer)<.000000001)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/*the following method is exceptionally designed for THIS program's purposes
	 * Because this is high school math, we imagine that denominators will rarely be greater
	 * than 30. In fact, all rational numbers that can only be expressed with denominators larger
	 * than 30 will always be treated as decimals 
	 * 
	 * Note that the object[] is a boolean and two integers
	 * this prevent the method from having to be called twice (once to find out if it is rational
	 * and again to find out what the numerator and denominator are
	 */
	public static int[] isRational(double d){
		int[] data = new int[3];
		int isRational=0;
		int numerator=0;
		int denominator=0;
		for(int denominatorTest =2; denominatorTest<30; denominatorTest++){
			if(Ops.isInteger(d*denominatorTest)){
				isRational=1;
				numerator = (int)Math.floor(d*denominatorTest+.5);
				denominator=denominatorTest;
				break;
			}
		}
		data[0]=isRational;
		data[1]=numerator;
		data[2]=denominator;
		//		System.out.println("The isRational method took "+(finish-startTime)+" ms. It found " + numerator + "/"+denominator);
		return data;
	}

	public static boolean isSquare(int integer){
		double sqrt = Math.sqrt(integer);
		if (Ops.isInteger(sqrt)) return true;
		else return false;
	}

	public static int randomInt(int minimumInclusive, int maximumInclusive){
		int range = maximumInclusive-minimumInclusive;
		int randomAdd=(int)(Math.random()*range+.5);
		int randomInt=(minimumInclusive+randomAdd);
		return randomInt;
	}

	public static double randomDouble(double minimumInclusive, double maximumInclusive){
		double range = maximumInclusive-minimumInclusive;
		double randomAdd=(Math.random()*range);
		double randomInt=(minimumInclusive+randomAdd);
		return randomInt;
	}

	public static int randomNotZero(int minimumInclusive, int maximumInclusive){
		int range = maximumInclusive-minimumInclusive;
		int randomAdd=(int)(Math.random()*range+.5);
		int randomInt=(minimumInclusive+randomAdd);
		while (randomInt==0){
			randomInt=randomInt(minimumInclusive,maximumInclusive);
		}
		return randomInt;
	}

	public static double roundDouble(double input, int decimalPlaces){
		int multiple = (int)(input*Math.pow(10, decimalPlaces)+.5);
		return multiple/Math.pow(10, decimalPlaces);

	}

	
	public static boolean areInts(double[] values) {
		for(double d: values){
			if((int)d != d)return false;
		}
		return true;
	}
	
	public static Expression[] simplifyRatio(Expression num1, Expression den1) {

		Term t1 = Ops.findGCF(num1);
		Term t2 = Ops.findGCF(den1);
		Term[] terms = {t1,t2};
		Term factor1 = Ops.findGCF(terms);
		Expression num1Simp = new Expression(Ops.divideExpressionByTerm(num1.getTermsOfExpression(), factor1));
		Expression den1Simp = new Expression(Ops.divideExpressionByTerm(den1.getTermsOfExpression(), factor1));
		Expression[] newFrac = {num1Simp,den1Simp};
		return newFrac;
	}
	
	public static int multiplyAllFactors(ArrayList<ConstantFactor> rootOfConstants) {
		int finalProduct = 1;
		for (int index=0; index < rootOfConstants.size(); index ++){
			finalProduct = finalProduct*(int)Math.pow(rootOfConstants.get(index).getBase(), rootOfConstants.get(index).getExponent());
		}
		return finalProduct;
	}


	//this isn't a very effective method. Try replacing it with a boolean that undergoes several steps, rahter than 'if' statements, while it is true
	public static boolean areEqualTerms(Term t1, Term t2){
		if (t1.getExpressionFactors()==null && t2.getExpressionFactors()!=null) return false;
		else if (t1.getExpressionFactors()!=null && t2.getExpressionFactors()==null) return false;
		else if (t1.getExpressionFactors().isEmpty() && t2.getExpressionFactors().isEmpty()
				&& t1.getCoefficient()==t2.getCoefficient() 
				&& t1.getVariableFactorization().equals(t2.getVariableFactorization())){
			if(t1.getType().equals("rational")||t2.getType().equals("rational")){
				if(areEqualTerms(t1.getDenominator(), t2.getDenominator())){
					return true;
				}else{
					return false;
				}
			}else{
				return true;

			}
		}
		//terms with expressions
		else if(t1.getCoefficient()==t2.getCoefficient() 
				&& t1.getVariableFactorization().equals(t2.getVariableFactorization()) 
				&& haveEqualExpressionFactors(t1, t2)){
			return true;
		}
		else{
			return false;
		}
	}

	public static boolean haveEqualExpressionFactors(Term t1, Term t2){
		ArrayList<Expression> t1expressions = new ArrayList<Expression>();
		t1expressions.addAll(Expression.quantifyExponents(t1.getExpressionFactors()));
		ArrayList<Expression> t2expressions = new ArrayList<Expression>();
		t2expressions.addAll(Expression.quantifyExponents(t2.getExpressionFactors()));
		if (t1expressions.size()==0 && t2expressions.size()==0) return true;
		else if (t1expressions.size()==t2expressions.size()){
			System.out.println("The haveEqualExpressionFactors method is being called and this part has been known not to function properly.");
			boolean theSame=false;
			for (int compareIndex = 0; t1.getExpressionFactors().size()>0 && compareIndex<t1.getExpressionFactors().size(); compareIndex++){
				boolean foundMatch = false;
				if (t1.getExpressionFactors().size()==0) break;
				for (int compareTo = 0; compareTo<t2.getExpressionFactors().size(); compareTo++){
					if (t2.getExpressionFactors().size()!=0 
							&& t1expressions.size()!=0
							&& t1expressions.get(compareIndex).
							equals(t2expressions.get(compareTo))){
						foundMatch = true;
						t2expressions.remove(compareTo);
					}
					if (foundMatch) {
						t1expressions.remove(compareIndex);
						compareIndex--;
						theSame=true;
					}
					else {
						theSame=false;
						break;
					}
				}
			}

			return theSame;
		}
		else return false;
	}

	public static boolean areLikeTerms(Term t1, Term t2)
	{

		if(t1.toString().replaceAll("-", "").matches("\\d+") && t2.toString().replaceAll("-", "").matches("\\d+") ){
			t1.setType(Term.CONSTANT_TYPE);
			t2.setType(Term.CONSTANT_TYPE);
			return true;
		}

		//    	if (t1.getVariableFactorization()!= null && 
		//    			t2.getVariableFactorization()== null ) return false;
		//    	else if (t2.getVariableFactorization()!=null && t1.getVariableFactorization()== null ) return false;
		//    	else if (t2.getVariableFactorization()==null && t1.getVariableFactorization()==null){
		//    		if(!t2.containsARadical && !t1.containsARadical)return true;
		//    		else{
		//    			System.out.println("!!!Ops: This \"areLikeTerms\" method does not reconginze radical terms");
		//    			return false;
		//    		}
		//    	}
		else{
			ArrayList<Variable> list1 = Term.combineDuplicateVariables(t1.getVariableFactorization());
			ArrayList<Variable> list2 = Term.combineDuplicateVariables(t2.getVariableFactorization());
			boolean bothRealOrBothImaginary = false;
			if (t1.imaginary==t2.imaginary) bothRealOrBothImaginary = true;
			return (t1.getType().equals(t2.getType()) && list1.equals(list2) && bothRealOrBothImaginary//&& haveEqualExpressionFactors(t1, t2)
					);
		}

	}

	public static Term addRationals(Term t1, Term t2){
		Term sum;

		//System.out.println("\n The add rationals method has been called.");
		//System.out.println("This is the first rational: " + t1 + " and it's denominator = " + t1.getDenominator());
		//System.out.println("This is the second rational: " + t2 + " and it's denominator = " + t2.getDenominator());

		if (t1.getDenominator()==null) {
			t1.setDenominator(new Term(1));
			//System.out.println("The first term's denominator was null.");
		}
		if (t2.getDenominator()==null){
			t2.setDenominator(new Term(1));
			//System.out.println("The second term's denominator was null.");
		}
		//the result numerator multiplies by top and bottom of t2, while the result denominator multiplies by top and bottom of t1
		Fraction findMultiplierFirst = new Fraction(t1.getDenominator(),t2.getDenominator());
		//System.out.println("Divided the first term's denominator by the second term's to get a lcm. This was returned: " + findMultiplier);
		Term findMultiplier = findMultiplierFirst.getNumerator();
		Term findMultiplierDenominator = findMultiplierFirst.getDenominator();
		//findMultiplier.simplifyExpressionFactors();
		//if (findMultiplierDenominator.getExpressionFactors()!=null)
		//findMultiplierDenominator.simplifyExpressionFactors();
		Term tSumPart1 = Ops.multiplyTerms(findMultiplier, Term.asNumerator(t2));
		Term tSumPart2 = Ops.multiplyTerms(findMultiplierDenominator, Term.asNumerator(t1));

		//expands when expressionFactors are present
		Term[] one = Ops.expand(tSumPart1);
		//System.out.println("First expanded sum part: " + Format.termArrayToString(one));
		Term[] two = Ops.expand(tSumPart2);
		//System.out.println("Second expanded sum part: " + Format.termArrayToString(two));

		Term[] unsimplifiedNumeratorSum = new Term[one.length + two.length];
		for (int index = 0; index<one.length; index++){
			unsimplifiedNumeratorSum[index] = one[index];
			//System.out.println("Just added to numerator: " + one[index]);
		}
		for (int index = 0; index<two.length; index++){
			unsimplifiedNumeratorSum[one.length+index] = two[index];
			//System.out.println("Just added to numerator: " + two[index]);
		}


		Term[] numerator = combineLikeTerms(unsimplifiedNumeratorSum);
		if (numerator.length==0) return new Term(0);
		//System.out.println("The numerator, before making a fraction: " + Format.termArrayToString(numerator));
		Term denominator = multiplyTerms(findMultiplier, t2.getDenominator());
		//System.out.println("We are about to simplify the denominator's expressions.");
		//denominator.simplifyExpressionFactors();
		Fraction[] fractionPartsOfSum = new Fraction[numerator.length];
		for (int index=0; index < numerator.length; index ++){
			Fraction fraction = new Fraction(numerator[index], denominator, false);
			fractionPartsOfSum[index] = fraction;
			//System.out.println("This fraction now has a common denominator: " + fraction);
		}
		if (fractionPartsOfSum.length == 1) {
			//System.out.println("Two fractions were added and this is the result: " + fractionPartsOfSum[0]);
			return new Term(fractionPartsOfSum[0]);
		}
		//else if (tSumPart1.getCoefficient()+tSumPart2.getCoefficient()==0) return new Term(0);
		else{
			System.out.println("This is line 169 of Ops. This part of the code has not yet been tested.");
			sum = new Term(fractionPartsOfSum);
			return sum;   
		}
	}


	public static Term addLikeTerms(Term t1, Term t2)//TODO Add a throws Exception for when the terms are not like
	{
		Term sum;
		if (t1.getType().equals("rational") || t2.getType().equals("rational")) sum=addRationals(t1, t2);
		else{
			int sumCoefficient = t1.getCoefficient() + t2.getCoefficient();
			if (t1.getType().equals(Term.CONSTANT_TYPE) && t2.getType().equals(Term.CONSTANT_TYPE)){
				sum = new Term(sumCoefficient);
			}else{
				ArrayList<Variable> list = Term.combineDuplicateVariables(t1.getVariableFactorization());
				String sumVariables = Term.stringFromArrayList(list);
				sum = new Term(sumCoefficient, sumVariables);
				if (t1.imaginary) sum.makeImaginary(t1.getiExponent());
			}
		}
		return sum; 
	}

	public static Term[] combineExpressions(Expression e1, Expression e2){
		Term[] allTerms = new Term[e1.getTermsOfExpression().length+e2.getTermsOfExpression().length];
		for(int index=0; index<allTerms.length; index++){
			if(index<e1.getTermsOfExpression().length){
				allTerms[index]=e1.getTermsOfExpression()[index];
			}else{
				allTerms[index]=e2.getTermsOfExpression()[index-e1.getTermsOfExpression().length];
			}
		}
		//    	System.out.println("\n Combining like terms\nThere are "+allTerms.length+" terms to combine.\nThese are the terms\n"+e1.toString()+" and "+e2.toString()+"\ncombined that makes ");
		for(Term t:allTerms){
			System.out.println(t+"");
		}
		Term[] returnThis = combineLikeTerms(allTerms);
		//    	System.out.println("Result from combining = "+new Expression(returnThis));
		return returnThis;
	}

	public static Term[] subtractExpressions(Expression fromThis, Expression subtractThis){
		Term[] allTerms = new Term[fromThis.getTermsOfExpression().length+subtractThis.getTermsOfExpression().length];
		for(int index=0; index<allTerms.length; index++){
			if(index<fromThis.getTermsOfExpression().length){
				allTerms[index]=fromThis.getTermsOfExpression()[index];
			}else{
				allTerms[index]=multiplyTerms(new Term(-1), subtractThis.getTermsOfExpression()[index-fromThis.getTermsOfExpression().length]);
			}
		}
		//    	System.out.println("\n Combining like terms\nThere are "+allTerms.length+" terms to combine.\nThese are the terms\n"+e1.toString()+" and "+e2.toString()+"\ncombined that makes ");
		for(Term t:allTerms){
			System.out.println(t+"");
		}
		Term[] returnThis = combineLikeTerms(allTerms);
		//    	System.out.println("Result from combining = "+new Expression(returnThis));
		return returnThis;
	}

	public static Term[] addTermToExpression(Term t, Expression e){
		Term[] allTerms = new Term[1+e.getTermsOfExpression().length];
		for(int index=0; index<allTerms.length; index++){
			if(index<e.getTermsOfExpression().length){
				allTerms[index]=e.getTermsOfExpression()[index];
			}else{
				allTerms[index]=t;
			}
		}
		Term[] returnThis = combineLikeTerms(allTerms);
		return returnThis;
	}

	public static Term[] combineLikeTerms(Term[] terms){
//		System.out.println("Ops.class Combing terms "+Arrays.toString(terms));
		ArrayList<Term> combinedTerms = new ArrayList<Term>();
		ArrayList<Term> uncombinedTerms = new ArrayList<Term>();
		//makes an arrayList copy of all the terms in the input array
		//this list is called "uncombined terms"
		for (Term t:terms){
			Term copy = Term.getCopy(t);
			uncombinedTerms.add(copy);
		}
//		System.out.println("Ops.class Uncombed terms "+uncombinedTerms);
		//    	System.out.println("\n Combining like terms\nThere are "+uncombinedTerms.size()+" terms to combine.\nThese are the terms\n"+uncombinedTerms);
		//beginning with the first term, selects it and compares it to all the others
		for (int index=0;index<uncombinedTerms.size();index++){
			Term termSum = uncombinedTerms.get(index);
			for (int compareIndex=index+1;compareIndex<uncombinedTerms.size();compareIndex++){

				if (Ops.areLikeTerms(uncombinedTerms.get(index), uncombinedTerms.get(compareIndex))){// && !terms[index].isTrivial() && !terms[compareIndex].isTrivial()){
					termSum = Ops.addLikeTerms(termSum, uncombinedTerms.get(compareIndex));
					uncombinedTerms.remove(compareIndex);
					compareIndex--;
				}
				//if there is no like term found, the next term is selected and this process is repeated
			}
			//the resulting combined terms are only added to the returned result if the sum is not zero
			if (termSum.getCoefficient()!=0)combinedTerms.add(termSum);
		}

		Term[] simplified = new Term[combinedTerms.size()];
		//System.out.println("\n Finished combining like terms!");
		for (int count=0;count<combinedTerms.size();count++){
			simplified[count]=combinedTerms.get(count);
		}
		Arrays.sort(simplified);
//		System.out.println("Ops.class Result of combing terms "+Arrays.toString(simplified));
		return simplified;
	}

	public static Fraction multiplyFractions(Fraction a, Fraction b){
		Term numeratorProduct = multiplyTerms(a.getNumerator(), b.getNumerator());
		Term denominatorProduct = multiplyTerms(a.getDenominator(), b.getDenominator());
		Fraction product = new Fraction(numeratorProduct,denominatorProduct);
		return product;
	}

	public static Term multiplyContantTerms(Term t1,Term t2){
		return new Term(t1.getCoefficient()*t2.getCoefficient());
	}

	public static Term multiplyVariableTerms(Term t1,Term t2){
		int productCoefficient = t1.getCoefficient()*t2.getCoefficient();
		//makes a list of all the variables
		ArrayList<Variable> productVariableList = new ArrayList<Variable>();
		//adds the variables from the 1st term
		for (int adds=0; adds<t1.getVariableFactorization().size(); adds++){
			Variable listVariable = new Variable(t1.getVariableFactorization().get(adds).getBase(), t1.getVariableFactorization().get(adds).getExponent());
			productVariableList.add(listVariable);
		}
		//adds the variables from the 2nd term
		for (int index = 0; index < t2.getVariableFactorization().size(); index++){//adds additional bases from t2
			productVariableList.add(t2.getVariableFactorization().get(index));
		}
		Collections.sort(productVariableList);//sorts the list so that identical bases are side-by-side
		//System.out.println("This is the variable result: " + Term.combineDuplicateVariables(productVariableList));//the result is already sorted

		String productVariables = Term.stringFromArrayList(Term.combineDuplicateVariables(productVariableList));
		//System.out.println("But this is the resulting string: " + productVariables);
		Term product = new Term(productCoefficient, productVariables);

		return product;
	}

	public static Term multiplyTerms(Term t1, Term t2){//TODO figure out how to identify like bases.
		Term product; 	
		//    	System.out.println("There may be an error in multiply terms. Follow carefully! -----------------------------------------");
		//    	System.out.println("These are the terms being multiplied: " + t1 + " and " + t2);
		if (t1.getType().equals(Term.CONSTANT_TYPE) && t2.getType().equals(Term.CONSTANT_TYPE)){
			//radical portion of a radical
			product = multiplyContantTerms(t1,t2);
			if (t1.containsRadical() || t2.containsRadical()){
				System.out.println("(Ops class) Recognized two radical terms:" + t1.getRadicalComponent()+" and" +t2.getRadicalComponent());
				ArrayList<SimplestRadicalForm> radicalProduct=new ArrayList<SimplestRadicalForm>();
				if(t1.getRadicalComponent().toString().equals("1")){
					radicalProduct=t2.getRadicalComponent();
				}
				else if(t2.getRadicalComponent().toString().equals("1")){
					radicalProduct=t1.getRadicalComponent();
				}
				//if both terms have radicalComponents
				//only radicals with common roots can be multiplied
				else{

					for (int comparator=0; comparator<t1.getRadicalComponent().size(); comparator++){
						boolean foundAMatch1 = false;
						for (int compareTo=0; compareTo<t2.getRadicalComponent().size(); compareTo++){
							//if two radicals are found with the same root
							if (t1.getRadicalComponent().get(comparator).getRoot()==t2.getRadicalComponent().get(compareTo).getRoot()){
								foundAMatch1=true;
								//the crazy constructor below basically creates a simplestRadicalForm of the mathcing root and the product of the 
								//radicands from the radicals that have the same root
								SimplestRadicalForm productOfMatch = new SimplestRadicalForm(t1.getRadicalComponent().get(comparator).getRoot(),multiplyTerms(t1.getRadicalComponent().get(comparator).getRadicand(), t2.getRadicalComponent().get(compareTo).getRadicand()));
								//    	    						System.out.println("Product of match="+productOfMatch);
								radicalProduct.add(productOfMatch);
							}
						}
						//any radical that never found a match gets added auomatically on its own
						if(foundAMatch1==false){
							radicalProduct.add(t1.getRadicalComponent().get(comparator));
						}    				
					}
					//finally, we go through the second term's radicals and make sure that every one was already added.
					//if there is a radical that was never added (as a product), it gets added on its own
					for (int comparator=0; comparator<t2.getRadicalComponent().size(); comparator++){
						boolean foundAMatch2 = false;
						for (int compareTo=0; compareTo<t1.getRadicalComponent().size(); compareTo++){	
							if (t2.getRadicalComponent().get(comparator).getRoot()==t1.getRadicalComponent().get(compareTo).getRoot()){
								foundAMatch2 = true;
							}
						}
						//check for non-matches. Mathches were already added, so only the non-matches still need to be added
						if(!foundAMatch2){
							radicalProduct.add(t2.getRadicalComponent().get(comparator));
						}
					}
				}
				//the factor of each radical is multiplied by the existing product
				Term productOfFactors=new Term(1);
				ArrayList<SimplestRadicalForm> justUnsimplifiableRadicands= new ArrayList<SimplestRadicalForm>();
				for (int factorIndex=0; factorIndex<radicalProduct.size(); factorIndex++){
					productOfFactors=multiplyTerms(productOfFactors, Term.getCopy(radicalProduct.get(factorIndex).getFactor()));
					//adds the unsimplifiable radicands
					justUnsimplifiableRadicands.add(new SimplestRadicalForm(radicalProduct.get(factorIndex).getRoot(), Term.getCopy(radicalProduct.get(factorIndex).getRadicand())));
				}
//				System.out.println("Product of all factors = "+productOfFactors);
				product=Term.getCopy(multiplyTerms(productOfFactors, product));
//				System.out.println("Product="+product+" unsimplifiable part = "+justUnsimplifiableRadicands);
				if(!justUnsimplifiableRadicands.toString().equals("[1]")){
//					System.out.println("multiplyTerms method isn't working with radicals that don't simplify!");
					product.addRadicalComponent(justUnsimplifiableRadicands);
				}
			}
			if(product.getRadicalComponent().toString().equals("1")){
				product.containsARadical=false;
			}
			if(product.containsARadical)System.out.println("product "+product+" is not a radical term");
			return product;
		}


		//    	if (t1.getType().equals("rational") && t2.getType().equals("rational"))

		//method when either term is rational (involves canceling coefficients when they simplify)
		if (t1.getType().equals("rational") || t2.getType().equals("rational")){
			//    		if (t1.getDenominator()==null) t1.setDenominator(new Term(1));
			//    		if (t2.getDenominator()==null) t2.setDenominator(new Term(1));
			//multiplies the numerators
			Term numFactor1 = Term.asNumerator(t1);
			Term numFactor2 = Term.asNumerator(t2);
			Term numerator = Ops.multiplyTerms(numFactor1, numFactor2);
			//    		System.out.println("Ops:\n*******************\n "+numFactor1.toString()+" by "+numFactor2.toString()+" is "+numerator.toString());
			//multiplies the denominators
			Term denominator = Ops.multiplyTerms(t1.getDenominator(), t2.getDenominator());

			//    		System.out.println("denominator\n "+t1.getDenominator().toString()+" by "+t2.getDenominator().toString()+" is "+denominator.toString());
			//makes a new fraction from the numerator and denominator above
			
			Fraction productf = new Fraction(numerator, denominator);
			if(productf.getDenominator().getType().equals(Term.CONSTANT_TYPE) && productf.getDenom() == 1){
				product = productf.getNumerator();
			}else{
				product = new Term(productf);
				
			}
			return product;
		}
		//method when neither term is rational
		else{

			int productCoefficient = t1.getCoefficient()*t2.getCoefficient();


			//makes a list of all the variables
			ArrayList<Variable> productVariableList = new ArrayList<Variable>();
			//adds the variables from the 1st term
			for (int adds=0; adds<t1.getVariableFactorization().size(); adds++){
				Variable listVariable = new Variable(t1.getVariableFactorization().get(adds).getBase(), t1.getVariableFactorization().get(adds).getExponent());
				//    			System.out.println("(Ops)variable = "+t1.getVariableFactorization().get(adds).getBase()+"^"+t1.getVariableFactorization().get(adds).getExponent());
				productVariableList.add(listVariable);
			}
			//adds the variables from the 2nd term
			for (int index = 0; index < t2.getVariableFactorization().size(); index++){//adds additional bases from t2
				//        	productVariableList.add(t2.getVariableFactorization().get(index));
				Variable listVariable = new Variable(t2.getVariableFactorization().get(index).getBase(), t2.getVariableFactorization().get(index).getExponent());
				productVariableList.add(listVariable);
				//        	System.out.println("(Ops)variable = "+t2.getVariableFactorization().get(index).getBase()+"^"+t2.getVariableFactorization().get(index).getExponent() +"   number of variables so far: "+productVariableList.size());
			}
			Collections.sort(productVariableList);//sorts the list so that identical bases are side-by-side
			//        System.out.println("Ops: This is the variable result: " + Term.combineDuplicateVariables(productVariableList));//the result is already sorted

			String productVariables = Term.stringFromArrayList(Term.combineDuplicateVariables(productVariableList));
			//        System.out.println("Ops: But this is the resulting string: " + productVariables);
			product = new Term(productCoefficient, productVariables);
		}

		//makes a list of all the expressions
		ArrayList<Expression> productExpressions = new ArrayList<Expression>();
		if(t1.hasExpressionFactors()){
			//adds the expressions from the 1st term
			for (int adds=0; adds<t1.getExpressionFactors().size(); adds++){
				Expression listExpression = new Expression(t1.getExpressionFactors().get(adds).getTermsOfExpression(), t1.getExpressionFactors().get(adds).getExponent());
				productExpressions.add(listExpression);
			}
		}
		//adds the expressions from the 2nd term
		if(t2.hasExpressionFactors()){
			for (int index = 0; index < t2.getExpressionFactors().size(); index++){//adds additional bases from t2
				productExpressions.add(t2.getExpressionFactors().get(index));
			}
		}
		//expressions factors are added, if they exist
		if(t1.hasExpressionFactors()||t2.hasExpressionFactors()){
			product.appendExpressionFactors(Term.combineDuplicateExpressions(productExpressions));
		}

		//all this code is for setting type. i.e. two imaginaries make a negative, etc...
		if ((t1.getType().equals("variable") || t1.getType().equals("constant")) && t2.getType().equals("imaginary"))  
		{
			product.makeImaginary(1);   
		}
		if ((t2.getType().equals("variable") || t2.getType().equals("constant")) && t1.getType().equals("imaginary"))  
		{
			product.makeImaginary(1);   
		}
		if ((t1.getType().equals("imaginary") && t2.getType().equals("imaginary"))) 
		{
			product.setCoefficient(product.getCoefficient()*(-1));
			product.setSign(false);
		}  

		//this last part of the code deals with any radical components of the two terms
		if (t1.containsRadical() || t2.containsRadical()){
			System.out.println("(Ops class) Recognized two radical terms:" + t1.getRadicalComponent()+" and" +t2.getRadicalComponent());
			ArrayList<SimplestRadicalForm> radicalProduct=new ArrayList<SimplestRadicalForm>();
			if(t1.getRadicalComponent().toString().equals("1")){
				radicalProduct=t2.getRadicalComponent();
			}
			else if(t2.getRadicalComponent().toString().equals("1")){
				radicalProduct=t1.getRadicalComponent();
			}
			//if both terms have radicalComponents
			//only radicals with common roots can be multiplied
			else{

				for (int comparator=0; comparator<t1.getRadicalComponent().size(); comparator++){
					boolean foundAMatch1 = false;
					for (int compareTo=0; compareTo<t2.getRadicalComponent().size(); compareTo++){
						//if two radicals are found with the same root
						if (t1.getRadicalComponent().get(comparator).getRoot()==t2.getRadicalComponent().get(compareTo).getRoot()){
							foundAMatch1=true;
							//the crazy constructor below basically creates a simplestRadicalForm of the mathcing root and the product of the 
							//radicands from the radicals that have the same root
							SimplestRadicalForm productOfMatch = new SimplestRadicalForm(t1.getRadicalComponent().get(comparator).getRoot(),multiplyTerms(t1.getRadicalComponent().get(comparator).getRadicand(), t2.getRadicalComponent().get(compareTo).getRadicand()));
							System.out.println("Product of match="+productOfMatch);
							radicalProduct.add(productOfMatch);
						}
					}
					//any radical that never found a match gets added auomatically on its own
					if(foundAMatch1==false){
						radicalProduct.add(t1.getRadicalComponent().get(comparator));
					}    				
				}
				//finally, we go through the second term's radicals and make sure that every one was already added.
				//if there is a radical that was never added (as a product), it gets added on its own
				for (int comparator=0; comparator<t2.getRadicalComponent().size(); comparator++){
					boolean foundAMatch2 = false;
					for (int compareTo=0; compareTo<t1.getRadicalComponent().size(); compareTo++){	
						if (t2.getRadicalComponent().get(comparator).getRoot()==t1.getRadicalComponent().get(compareTo).getRoot()){
							foundAMatch2 = true;
						}
					}
					//check for non-matches. Mathches were already added, so only the non-matches still need to be added
					if(!foundAMatch2){
						radicalProduct.add(t2.getRadicalComponent().get(comparator));
					}
				}
			}
			//the factor of each radical is multiplied by the existing product
			Term productOfFactors=new Term(1);
			ArrayList<SimplestRadicalForm> justUnsimplifiableRadicands= new ArrayList<SimplestRadicalForm>();
			for (int factorIndex=0; factorIndex<radicalProduct.size(); factorIndex++){
				productOfFactors=multiplyTerms(productOfFactors, Term.getCopy(radicalProduct.get(factorIndex).getFactor()));
				//adds the unsimplifiable radicands
				justUnsimplifiableRadicands.add(new SimplestRadicalForm(radicalProduct.get(factorIndex).getRoot(), Term.getCopy(radicalProduct.get(factorIndex).getRadicand())));
			}
			System.out.println("Product of all factors = "+productOfFactors);
			product=Term.getCopy(multiplyTerms(productOfFactors, product));
			System.out.println("Product="+product+" unsimplifiable part = "+justUnsimplifiableRadicands);
			if(!justUnsimplifiableRadicands.toString().equals("[1]")){
				System.out.println("multiplyTerms method isn't working with radicals that don't simplify!");
				product.addRadicalComponent(justUnsimplifiableRadicands);
			}
		}
		return product;
	}

	public static Fraction divideTerms(Term numerator, Term denominator){
		Fraction dividend = new Fraction (numerator, denominator);
		return dividend;    	
	}

	public static Term[] divideExpressionByTerm (Term[] numerator, Term denominator){
		Term [] result = new Term[numerator.length];
		for (int index = 0; index<numerator.length; index++){
			Term termDividend = new Term(divideTerms(numerator[index], denominator));
			result[index] = termDividend;
		}
		return result;
	}


	public static Term root(Term term, int root){
		Term result = new Term(new SimplestRadicalForm(root, term));   	
		return result;
	}

	public static Term pow(Term term, int power){
		System.out.println("This is the term we are raising to exponent, "+power+": "+term);
		Term result = Term.getCopy(term);
		System.out.println("Made a copy and got this: "+result);
		for (int index = 1; index<power; index ++) {
			result = multiplyTerms(result, term);
			System.out.println("Result of squaring = "+result);
		}

		return result;
	}

	public static Term[] distribute (Term t, Term[] e)
	{
		Term[] productTerms=new Term[e.length];
		for (int index=0; index<productTerms.length; index++)
		{
			Term product = multiplyTerms(t,e[index]);
			productTerms[index]=product;
		}
		return productTerms;
	}


	public static Term[] distribute (Term[] e1,Term[] e2){

		//System.out.println("We are distributing these expressions:" + Format.termArrayToString(e1) + " and " + Format.termArrayToString(e2));
		ArrayList<Term> productTerms=new ArrayList<Term>();
		for (int index1=0; index1<e1.length; index1++)
		{
			for (int index2=0; index2<e2.length; index2++)
			{
				if(e1[index1].containsARadical)System.out.println(e1[index1]+" contains a radical.");
				if(e2[index2].containsARadical)System.out.println(e2[index2]+" contains a radical.");
				productTerms.add(		
						multiplyTerms(e1[index1],e2[index2])
						);
			}
		}
		Term[] termArray = Term.ArrayListToTermArray(productTerms);  
		return termArray;
	}

	public static Term[] expand(ArrayList<Expression> arrayOfPolynomials){
		//first, any expressions with exponents get added to the list multiple times.
		//IOW, (x+3)^2(x-2) becomes (x+3)(x+3)(x-2)
		arrayOfPolynomials = Expression.quantifyExponents(arrayOfPolynomials);
		//the first Expression matches the first in the ArrayList
		Term[] currentProduct = arrayOfPolynomials.get(0).getTermsOfExpression();
		for (int index = 1; index < arrayOfPolynomials.size(); index ++){
			//the polynomial expands as more and more expressions are multiplied
			currentProduct = combineLikeTerms(Ops.distribute(currentProduct, arrayOfPolynomials.get(index).getTermsOfExpression()));

		}
		return currentProduct;
	}

	public static Term[] expand(Term t){
		Term[] currentProduct;
		if (t.getExpressionFactors()==null){
			currentProduct=new Term[1];
			currentProduct[0] = t;
			return currentProduct;
		}
		else{
			ArrayList<Expression> arrayOfPolynomials = t.getExpressionFactors();
			arrayOfPolynomials = Expression.quantifyExponents(arrayOfPolynomials);
			currentProduct=new Term[1];
			currentProduct[0]=new Term(1);
			if(arrayOfPolynomials.size()!=0){
				currentProduct = arrayOfPolynomials.get(0).getTermsOfExpression();
				for (int index = 1; index < arrayOfPolynomials.size(); index ++){
					currentProduct = Ops.distribute(currentProduct, arrayOfPolynomials.get(index).getTermsOfExpression());
				}
			}
			Term justTerm = Term.getCopy(t);
			//removes all expression factors so that they don't get multiplied twice
			while (justTerm.getExpressionFactors().size()>0) justTerm.getExpressionFactors().remove(0);
			currentProduct = Ops.distribute(justTerm, currentProduct);
			return combineLikeTerms(currentProduct);
		}
	}

	//TODO this method is a long way from complete or functional
	public static Term factor(Term[] terms){
		Term unFactored = new Term(1);
		ArrayList<Expression> identity = new ArrayList<Expression>();
		identity.add(new Expression(terms));
		//		unFactored.appendExpressionFactors(identity);
		//    	if (terms.length<4){
		//    		if (terms.length==2){   			
		//    			if (terms[0].isSquare() && !terms[1].getSign() && terms[1].isSquare()){
		//    				return factorDifferenceOfSquares(terms[0], terms[1]);
		//    			}
		//    			else {
		//    				System.out.println("This is not a difference of squares");
		//    				return unFactored;
		//    			}
		//    		}
		//    		else{
		//        		System.out.println("\n NOTE: Either the quantity" + Format.termArrayToString(terms) + " cannot be factored or" +
		//        				"\n there is no program written yet to factor it.\n");
		//        		return unFactored;
		//    		}
		//    	}
		//    	else {
		//    		System.out.println("\n NOTE: Either the quantity" + Format.termArrayToString(terms) + " cannot be factored or" +
		//    				"\n there is no program written yet to factor it.\n");
		return unFactored;
		//    	}
	}

	public static Term findGCF(Term[] terms){
		Expression testThis = new Expression(terms);
		return findGCF(testThis);
	}

	public static Term findGCF(Expression e){
		if (e.getTermsOfExpression().length==0) return new Term(0);
		else if (e.getTermsOfExpression().length==1) return e.getTermsOfExpression()[0];
		else{
			//sets up data we need to make the term that we will return
			int coef = 1;
			int[] coefficients = new int[e.getTermsOfExpression().length];
			int largestCoef=e.getTermsOfExpression()[0].getCoefficient();
			boolean allCoefEqual=true;
			for(int index=0; index<coefficients.length; index++){
				coefficients[index]=e.getTermsOfExpression()[index].getCoefficient();
				if(Math.abs(coefficients[index])!=0 && Math.abs(coefficients[index])!=largestCoef){
					allCoefEqual=false;
				}
				if(Math.abs(coefficients[index])>largestCoef)largestCoef=Math.abs(coefficients[index]);
			}

			if(allCoefEqual){
				coef=largestCoef;
			}else{	
				for(int index=2; index<=Math.abs(largestCoef/2)+1; index++){
					boolean isAFactor=true;	
					for(int i:coefficients){
						if(i%index==0){

						}else{
							isAFactor=false;
							break;
						}
					}
					if(isAFactor){
						coef=index;
					}
				}
			}

			ArrayList<Variable> var = new ArrayList<Variable>();

			Term testSource = e.getTermsOfExpression()[0];
			//    		ArrayList<ConstantFactor> testFactors = testSource.getPrimeFactorization();
			ArrayList<Variable> testVariables = testSource.getVariableFactorization();
			//tests each constant factor, starting with highest exponent and working down
			//    		for (int factorIndex = 0; factorIndex<testFactors.size(); factorIndex++){
			//    			int exp = testFactors.get(factorIndex).getExponent();
			//    			while (exp>0){
			//    				int currentFactor = (int)Math.pow(testFactors.get(factorIndex).getBase(), exp);
			//    				boolean isCommon = false;
			//    				for (int checkIndex = 1; checkIndex<e.getTermsOfExpression().length; checkIndex++){
			//    					if (Ops.isInteger((double)e.getTermsOfExpression()[checkIndex].getCoefficient()/currentFactor)){
			//    						isCommon = true;
			//    					}
			//    					else {
			//    						isCommon = false;
			//    						break;
			//    					}
			//    				}
			//    				if (isCommon){
			//    					coef = coef * currentFactor;
			//    					//we don't need to check this factor anymore, so setting exp=0 will exit the while loop
			//    					exp = 0;
			//    				}
			//    				else {
			//    					//since this factor is not common, try a smaller exponent
			//    					exp --;
			//    				}
			//    			}
			//
			//    		}

			//tests each variable factor, starting with highest exponent and working down
			for (int variableIndex = 0; variableIndex<testVariables.size(); variableIndex++){
				//selects the exponent we will try for
				int exp = testVariables.get(variableIndex).getExponent();
				while (exp>0){
					char currentVariable = testVariables.get(variableIndex).getBase();
					boolean isCommon = false;
					for (int checkIndex = 1; checkIndex<e.getTermsOfExpression().length; checkIndex++){
						//now we have to search within the term for a base
						ArrayList<Variable> currentVariableCollection = e.getTermsOfExpression()[checkIndex].getVariableFactorization();
						if(currentVariableCollection.size()==0){
							isCommon=false;
							break;
						}
						for (int baseIndex = 0; baseIndex<currentVariableCollection.size(); baseIndex++){
							if (currentVariable==currentVariableCollection.get(baseIndex).getBase() && currentVariableCollection.get(baseIndex).getExponent()>=exp)
								isCommon = true;
							else {
								isCommon = false;
								break;
							}
						}
						if (!isCommon) break;
					}
					if (isCommon){
						var.add(new Variable(currentVariable, exp));
						//we don't need to check this factor anymore, exit loop
						break;
					}
					else {
						//since this factor is not common, try a smaller exponent
						exp --;
					}
				}

			}

//			System.out.print("- - - -- - - - - -- -- - - -- - - /nLooking for GCD of these terms:\n   ");
//			for(Term t: e.getTermsOfExpression()){
//				System.out.print(""+t.getCoefficient()+" ");
//			}
//			System.out.println("/nAnswer = "+coef);

			return new Term(coef,var);
		}
	}

	public static ArrayList<Expression> factorDifferenceOfSquares(Term t1, Term t2){
		System.out.println("Input : " + t1 + ", " + t2);
		System.out.println("The variableFactorization of t1: " + Term.stringFromArrayList(t1.getVariableFactorization()));
		ArrayList<Expression> factored = new ArrayList<Expression>();
		SimplestRadicalForm srf1 = new SimplestRadicalForm(2, t1);
		SimplestRadicalForm srf2 = new SimplestRadicalForm(2, t2);
		Term[] differenceofSquares1 = {srf1.getFactor(),srf2.getFactor()};
		System.out.println("First expression = " + Format.termArrayToString(differenceofSquares1));
		Term opposite = Term.getCopy(srf2.getFactor());
		boolean sign = !opposite.getSign();
		opposite.setSign(sign);
		Term[] differenceofSquares2 = {srf1.getFactor(),opposite};
		System.out.println("second expression = " + Format.termArrayToString(differenceofSquares2));
		Expression e1 = new Expression(differenceofSquares1);
		Expression e2 = new Expression(differenceofSquares2);
		factored.add(e1);
		factored.add(e2);
		return factored;
	}

	public static int getDegreeOfTermArray(Term[] terms){
		int degree = 0;
		for(int index=0; index<terms.length; index++){
			int check = terms[index].getDegree();
			if (check>degree){
				degree = check;
			}
		}
		return degree;
	}


	public static int randomRelativePrime(int intprime, int min, int max){
		int relativelyPrime= Ops.randomNotZero(min, max);
		Fraction check = new Fraction (relativelyPrime,intprime);
		while (check.getGcf()!=1){
			relativelyPrime = Ops.randomInt(min, max);
			check = new Fraction (relativelyPrime,intprime);
		}
		return relativelyPrime;
	}

	/**
	 * 
	 * @param sideA
	 * @param sideB
	 * @param sideC
	 * @return measure of angle opposite side a
	 */
	public static double lawOfCosines(double a, double b, double c) {

		return Math.acos((Math.pow(a, 2)-Math.pow(b, 2)-Math.pow(c, 2))/(-2*b*c));
	}

	public static int plugIn(String variable, int xValue, Expression expression) {
		Term[] terms = expression.getTermsOfExpression();//Note:  
		/**
		 * a NullPointerException on the above line
		 * signifies that no "leftExpression" and "rightExpression" have been set
		 * in the class calling for work to be shown, there is a boolean that calls for the solution to be plugged into the 
		 * original equation. Either change the boolean to "false" or before showing the work, set the 
		 * leftExpression and rightExpression by calling the respective methods
		 */
		
		int solution=0;
		

		
		for(int index = 0; index< terms.length; index++){
			int coefficient = terms[index].getCoefficient();
			int degree = terms[index].getDegree();
			int valueRaised = (int)Math.pow(xValue, degree);
			int value = coefficient*valueRaised;
			solution+=value;
		}

		return solution;
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @param b
	 * @return a double with .5, .33 or .66
	 */
	public static double randomSimpleDouble(double e, double f, boolean includeThirds) {
		if(includeThirds){
				double div = randomInt(2, 3);
				double d = randomInt((int)(e*div), (int)(f*div));
				return d/div;				
		}else{
			double d = randomInt((int)(e*2), (int)(f*2));
			return d/2.0;
		}
	}

	public static Term getTermOfDegree(Term[] quadratic, int i) {
		for(Term t: quadratic){
			if(t.degree == i)return t;
		}
		return null;
	}
}

