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

public class SimplestRadicalForm {

private int root;
private Term termBeforeSimplification;
public String representationBeforeSimplification;
private Term factor;
private Term radicand;

private boolean negative = false;
private boolean containsDenominator;
//private String representation;

int remainingRadicand;
ArrayList<Variable> remainingVariables;


public SimplestRadicalForm(int root,int radicand){
	containsDenominator=false;
	this.root=root;
	termBeforeSimplification = new Term(radicand);
	if (root==2){
	representationBeforeSimplification="\\sqrt{"+radicand+"}";
	}
	else{
	representationBeforeSimplification="\\sqrt["+root+"]{"+radicand+"}";	
	}
	
	int factorCoefficient = determineRootOfConstant(radicand);
	factor = new Term (factorCoefficient);
	
	this.radicand = new Term(remainingRadicand);
	
	//representation=determineRepresentation();
}

public SimplestRadicalForm(int root,Term term){
	this.root=root;
	if(term.getCoefficient()<0){
		negative=true;
	}
	term.setSign(true);
	containsDenominator=false;
	//System.out.println("This term was input to the constructor of a SimplestRadicalForm: " + term);
	//this part determines everything BEFORE simplification
	termBeforeSimplification=term;
	if (root==2){
	representationBeforeSimplification="\\sqrt{"+term+"}";
	}
	else{
	representationBeforeSimplification="\\sqrt["+root+"]{"+term+"}";	
	}
	
	//this part determines everything AFTER simplification
	int factorCoefficient = determineRootOfConstant(term.getCoefficient());
	//1. The factor:
	//String factorVariables = Term.stringFromArrayList(determineRootOfVariables(term.getVariableFactorization()));
	//System.out.println("factoredVariable= " + factorVariables);
	factor = new Term(factorCoefficient,determineRootOfVariables(term.getVariableFactorization()));

	//2. The radicand:
	//String radicandVariables = determineRootOfVariables(remainingVariables);
	radicand = new Term(remainingRadicand,remainingVariables);
	
	//3. Takes care of denominator, if present
	if(term.getType().equals("rational")){
		containsDenominator=true;
		//System.out.println("Problem #"+count);
		int denFactorCoefficient = determineRootOfConstant(term.getDenominator().getCoefficient());
		//System.out.println("denFactorCoef: "+denFactorCoefficient);
		Term denFactor=new Term(denFactorCoefficient,determineRootOfVariables(term.getDenominator().getVariableFactorization()));
		//System.out.println("denFactor: "+denFactor);
		Term denRadicand = new Term(remainingRadicand,remainingVariables);
		//System.out.println("denRadicand: "+denRadicand);
		radicand=(Ops.multiplyTerms(radicand,denRadicand));
		factor=new Term(new Fraction(factor,Ops.multiplyTerms(denFactor,denRadicand)));
		//System.out.println("(SimplestRadicalForm) Factor is "+factor+", it has denominator"+factor.getDenominator());
		count++;
	}
}

public static int count =1;

public String toString() {
	String coefficient="";
	if(factor.getCoefficient()!=1){
		if(factor.getCoefficient()==0){
			return "0";
		}
		coefficient+=""+factor.getCoefficient();
	}
	String representation ="";
	String fractionStart = "";
	String fractionMiddle = "";
	String fractionEnd = "";
	String rootNumber="";
	String radicalStart="";
	String radicalMiddle="";
	String radicalEnd="";
	String imaginary="";
	if(negative){
		imaginary="i";
	}
	if(!radicand.toString().equals("1")){
		radicalStart="\\sqrt";
		radicalMiddle="{";
		radicalEnd="}";
		if(root!=2){
			rootNumber="["+root+"]";
		}
	}
	String denominatorString="";
	if(containsDenominator&&factor.getDenominator().getCoefficient()!=1){
		fractionStart = "\\frac{";
		fractionMiddle = "}{";
		fractionEnd = "}";	
		denominatorString=factor.getDenominator().toString();
	}
	String radicandString="";
	if(!radicand.toString().equals("1")){
		radicandString=radicand.toString();
	}

	if(denominatorString.equals("")&&radicandString.equals("")&&!negative){
		coefficient="1";
	}
	representation = fractionStart+coefficient+imaginary+factor.getVariables()+radicalStart+rootNumber+radicalMiddle+radicandString+radicalEnd+fractionMiddle+denominatorString.toString()+fractionEnd;
	
	return representation;
}


private int determineRootOfConstant(int radicand){
	int greatestSquareFactor = 1;
	for (int number = 1; (Math.pow(number, root))<=radicand; number ++){
		if (Ops.isInteger(radicand/(Math.pow(number, root)))) {
			greatestSquareFactor=number;
		}
		
	}
	remainingRadicand = (int)(radicand/(Math.pow(greatestSquareFactor, root)));
	return greatestSquareFactor;
}



public int getRoot() {
	return root;
}

public Term getFactor() {
	return factor;
}

public Term getRadicand() {
	return radicand;
}

private ArrayList<Variable> determineRootOfVariables(ArrayList<Variable> list) {
	ArrayList<Variable> arrayList = Variable.getCopy(list);
	//System.out.println("determineRootOfVariables is called with input " + Term.stringFromArrayList(arrayList));
	ArrayList<Variable> rootOfVariables=new ArrayList<Variable>();//declare a list in which simplified roots will be added
	for(int index=0; index<arrayList.size(); index++){//consider one variable at a time
		char var = arrayList.get(index).getBase();//declares the facored base
		int rootExponent=0;//declares the factored exponent
		while (arrayList.get(index).getExponent()>=root){
			rootExponent = rootExponent+1;
			arrayList.get(index).setExponent(arrayList.get(index).getExponent()-root);
		}
		if (rootExponent>0) rootOfVariables.add(new Variable(var, rootExponent));
		if (arrayList.get(index).getExponent()==0) arrayList.remove(index);	
	}
	
	remainingVariables=arrayList;
	return rootOfVariables;
	//	ArrayList<Variable> rootOfVariables=new ArrayList<Variable>();
//	
//	for(int index=0; index<arrayList.size(); index++){
//	int repeat = 1;
//	while (index+1<arrayList.size() && arrayList.get(index).equals(arrayList.get(index+1))){
//		repeat++;
//	}
//	while (repeat>=root){
//		rootOfVariables.add(arrayList.get(index));
//		for (int deletion=0; deletion<root; deletion++ ){
//			System.out.println(arrayList.get(index)+" removed " + deletion + " time.");
//			arrayList.remove(index);
//		}
//		arrayList.trimToSize();
//		repeat=repeat-root;
//	}
//	}
//
//	remainingVariables=arrayList;
//	return rootOfVariables.toString();
	
}

//private String arrayListToCharArray(ArrayList<Character> list){
//	char[] c = new char[list.size()];
//	for (int index=0; index<list.size(); index ++){
//		c[index] = list.get(index); 
//	}
//	return c.toString();
//	
//}

//public String getRepresentation() {
//	return representation;
//}



public String getRepresentationBeforeSimplification() {
	return representationBeforeSimplification;
}

public Term getTermBeforeSimplification() {
	return termBeforeSimplification;
}

//private boolean variablesSimplifyCompletely(ArrayList<Character> listOfVariables) {
//	
//	boolean simplifies = false;
//	
//	int count = 1;
//	for (int index=0;index<listOfVariables.size(); index++){	
//		while (listOfVariables.get(index).equals(listOfVariables.get(index+1))){
//			count++;
//			index++;
//		}
//		if (Ops.isInteger(count/2)){
//			simplifies=true;
//		}
//		else{
//			simplifies=false;
//		}
//	}
//	return simplifies;
//}

	
}



//import java.util.ArrayList;
//
//public class SimplestRadicalForm {
//
//private int root;
//private Term termBeforeSimplification;
//private String representationBeforeSimplification;
//private Term factor;
//private Term radicand;
//private String representation;
//
//ArrayList<Character> remainingVariables;
//ArrayList<Integer> remainingConstants;
//
//
//
//
//public SimplestRadicalForm(int root,int radicand){
//	this.root=root;
//	termBeforeSimplification = new Term(radicand);
//	if (root==2){
//	representationBeforeSimplification="//sqrt{"+radicand+"}";
//	}
//	else{
//	representationBeforeSimplification="//sqrt["+root+"]{"+radicand+"}";	
//	}
//	
//	int factorCoefficient = determineRootOfConstant(termBeforeSimplification.getConstantFactors());
//	factor = new Term (factorCoefficient);
//	
//	int radicandCoefficient = determineRootOfConstant(remainingConstants);
//	this.radicand = new Term(radicandCoefficient);
//	
//	representation=determineRepresentation();
//}
//
//public SimplestRadicalForm(int root,Term term){
//	this.root=root;
//	
//	//this part determines everything BEFORE simplification
//	termBeforeSimplification=term;
//	if (root==2){
//	representationBeforeSimplification="//sqrt{"+term+"}";
//	}
//	else{
//	representationBeforeSimplification="//sqrt["+root+"]{"+term+"}";	
//	}
//	
//	System.out.println("The string before simplification: " + representationBeforeSimplification);
//	//this part determines everything AFTER simplification
//	//1. The factor:
//	int factorCoefficient = determineRootOfConstant(term.getConstantFactors());
//	String factorVariables = determineRootOfVariables(term.getVariableFactors());
//	factor = new Term(factorCoefficient,factorVariables);
//	
//	//2. The radicand:
//	int radicandCoefficient = determineRootOfConstant(remainingConstants);
//	String radicandVariables = determineRootOfVariables(remainingVariables);
//	radicand = new Term(radicandCoefficient,radicandVariables);
//	
//	//this final part does the representation:
//	representation=determineRepresentation();
//}
//
//
//private String determineRepresentation() {
//	if (root==2){
//		if (Ops.areEqualTerms(factor, new Term(1))){
//			return "//sqrt{"+radicand.toString()+"}";
//		}
//		else{
//			return factor.toString()+"//sqrt{"+radicand.toString()+"}";
//		}		
//	}
//	if (Ops.areEqualTerms(factor, new Term(1))){
//		return "//sqrt["+root+"]{"+radicand.toString()+"}";
//	}
//	else{
//		return factor.toString()+"//sqrt["+root+"]{"+radicand.toString()+"}";
//	}
//}
//
//
//private int determineRootOfConstant(ArrayList<Integer> listOfConstants){
//	ArrayList<Integer> rootOfConstants=new ArrayList<Integer>();
//	
//	for(int index=0; index<listOfConstants.size(); index++){
//	int repeat = 1;
//	if (index<listOfConstants.size()-1){
//	while (listOfConstants.get(index).equals(listOfConstants.get(index+1))){
//		repeat++;
//	}
//	}
//	while (repeat>=root){
//		rootOfConstants.add(listOfConstants.get(index));
//		for (int deletion=1; deletion<root; deletion++ ){
//			System.out.println(listOfConstants.get(index)+" removed " + deletion + " time.");
//			listOfConstants.remove(index);
//		}
//		listOfConstants.trimToSize();
//		repeat=repeat-root;
//	}
//	}
//
//	remainingConstants=listOfConstants;
//	return Ops.multiplyAllFactors(rootOfConstants);
//}
//
//
//private String determineRootOfVariables(ArrayList<Character> listOfVariables) {
//	ArrayList<Character> rootOfVariables=new ArrayList<Character>();
//	
//	for(int index=0; index<listOfVariables.size(); index++){
//	int repeat = 1;
//	while (listOfVariables.get(index).equals(listOfVariables.get(index+1))){
//		repeat++;
//	}
//	while (repeat>=root){
//		rootOfVariables.add(listOfVariables.get(index));
//		for (int deletion=0; deletion<root; deletion++ ){
//			System.out.println(listOfVariables.get(index)+" removed " + deletion + " time.");
//			listOfVariables.remove(index);
//		}
//		listOfVariables.trimToSize();
//		repeat=repeat-root;
//	}
//	}
//
//	remainingVariables=listOfVariables;
//	return arrayListToCharArray(rootOfVariables).toString();
//	
//}
//
//private String arrayListToCharArray(ArrayList<Character> list){
//	char[] c = new char[list.size()];
//	for (int index=0; index<list.size(); index ++){
//		c[index] = list.get(index); 
//	}
//	return c.toString();
//	
//}
//
//public String getRepresentation() {
//	return representation;
//}
//
//
//
//public String getRepresentationBeforeSimplification() {
//	return representationBeforeSimplification;
//}
//
//public Term getTermBeforeSimplification() {
//	return termBeforeSimplification;
//}
//
////private boolean variablesSimplifyCompletely(ArrayList<Character> listOfVariables) {
////	
////	boolean simplifies = false;
////	
////	int count = 1;
////	for (int index=0;index<listOfVariables.size(); index++){	
////		while (listOfVariables.get(index).equals(listOfVariables.get(index+1))){
////			count++;
////			index++;
////		}
////		if (Ops.isInteger(count/2)){
////			simplifies=true;
////		}
////		else{
////			simplifies=false;
////		}
////	}
////	return simplifies;
////}
//
//	
//}
