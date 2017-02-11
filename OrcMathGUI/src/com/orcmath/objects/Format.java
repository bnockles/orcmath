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


public class Format {
	static int decimalplaces;
	
	
	public static String doubleToString(double d){
		 int integer=(int)(d+.5);
	     int[] fractionAttempt = Ops.isRational(d);
		 if(Ops.isInteger(d)){
			return ""+(int)Ops.roundDouble(d, 0);
		 }else if(Math.abs(d-integer)<.00001){
				return ""+(int)Ops.roundDouble(d, 0);
		 }else if(Ops.isInteger(Math.pow(d,2))){
			return "\\sqrt("+(int)(Math.pow(d, 2))+")";
		}else if(fractionAttempt[0]==1){
			if(fractionAttempt[1]==0)return ""+0;
			else return "\\frac{"+fractionAttempt[1]+"}{"+fractionAttempt[2]+"}";
		}else return ""+d;
		
	}
	
	public static String termArrayToString(Term[] t){
		if(t.length==0){
			return "0";
		}
		//initialize
		String representation = "";

		//the first term
		if (t[0].isPositive) representation += t[0].toString();
		else {
			String s = t[0].toString().replaceAll("[-]", "");//remove the negative sign
			representation += "-" + s;
		}

		//every term after the first
		for (int index=1; index<t.length;index++){
			if(t[index].getCoefficient()==0){}
			else if (t[index].isPositive){
				representation = representation + "\\;+\\; " + t[index].toString();
			}
			else if(!t[index].isPositive){
				String s= t[index].toString().replaceAll("[-]", "");//takes the String and removes minus sign 
				representation = representation + "\\; -\\;" + s;
			}
		}
		return representation;
	}

	//this returns a string of all the terms in both expressions, in their natural order,
	//without terms combined (i.e. if you are showing steps and you don't want to mix up or combine terms yet)
	public static String combineExpressionsOperation(ArrayList<Expression> e){
//		System.out.println("Attempting to string these expressions together"+expressionArrayToString(e));
		if (e==null) return "";
		else if (e.isEmpty()) return "";
		else{
			String returnThis = e.get(0).toString();
			if(e.size()>1){
				for (int index=1; index<e.size();index++){
					if(e.get(index).getTermsOfExpression().length==1 && e.get(index).getTermsOfExpression()[0].getCoefficient()==0){
						//won't do anything with a trivial expressions
					}else{
						if (!e.get(index).getTermsOfExpression()[0].isPositive){
							returnThis += "\\;-\\;"+ e.get(index).toString().replaceFirst("-", "") ;
						}
						else {
							returnThis += "\\;+\\;"+ e.get(index).toString();
						}
					}
				}
			}
			return returnThis;
		}
	}
	
	//returns string of binomials attached to a term. i.e. 3x(2x-1)(4x+6)(3x^2-8x+2)
	public static String expressionArrayToString(ArrayList<Expression> e){
		if (e==null) return "";
		else if (e.isEmpty()) return "";
		else if (e.get(0)==null) return "";
		else{
			String representation="";
			if (e.get(0).getExponent()==1) representation ="\\left(" + e.get(0).toString() + "\\right)";
			else representation ="\\left(" + e.get(0).toString() + "\\right)^"+e.get(0).getExponent();
			for (int index=1; index<e.size();index++){
//				System.out.println("index is "+index+" and expression length is "+e.size());
//				System.out.println("(Format class) The expression yielding the error is: " +e.get(index).toString());
				if (e.get(index).getExponent()==1)
					representation = representation + "\\left("+ e.get(index).toString() + "\\right)";
				else representation = representation + "\\left(" + e.get(index).toString() + "\\right)^"+e.get(index).getExponent();
			}
			return representation;
		}
	}
	
	public static String removeParentheses(String input){
		String result = input;
		result=result.replaceAll("\\\\left", "").replaceAll("\\\\right", "").replaceAll("\\(", "").replaceAll("\\)","");
		return result;
	}
	
	public static String addBracketsAroundExponents(String variableInput){
		String variableOutput = "";
		for (int index=0; index<variableInput.length(); index++){
			if (Character.isLetter(variableInput.charAt(index)) || variableInput.charAt(index)=='^')//if the character in the input string is not a digit, simply add it to the output string.
				variableOutput = variableOutput + variableInput.charAt(index);
			if (Character.isDigit(variableInput.charAt(index))){//if it is a digit...
//				System.out.println("(Format)digit found = "+variableInput.charAt(index)+", previous character is "+variableInput.charAt(index-1));
				if(variableInput.charAt(index)=='1'){//see if it is one
					try{
						if(Character.isDigit(variableInput.charAt(index+1))||variableInput.charAt(index-1)=='-'){//if it is a one and the next character is a digit, or the previous character is a '-' that's cool
							if(Character.isDigit(variableInput.charAt(index+1))){//add the entire integer digit
								variableOutput = variableOutput + '{' + variableInput.charAt(index);//...add a bracket and the digit...
								while (index+1<variableInput.length() && Character.isDigit(variableInput.charAt(index+1))){ //...then if the next character is a digit...
									variableOutput = variableOutput + variableInput.charAt(index+1);//...add that digit too.
									index++;
								}
							}else{//add a negative exponent
								variableOutput = variableOutput + "{-" + variableInput.charAt(index);//...add a bracket and the digit...
//								System.out.println("(Format)NEGATIVE EXPONENT!");
								while (index+1<variableInput.length() && Character.isDigit(variableInput.charAt(index+1))){ //...then if the next character is a digit...
									variableOutput = variableOutput + variableInput.charAt(index+1);//...add that digit too.
									index++;
								}
							}
							variableOutput = variableOutput + "}";//close the exponent
							
						}else{//next character is not a 1
//							System.out.println("(Format)NOT a negative exponent!");
							if(variableOutput.length()==1);
							else variableOutput=variableOutput.substring(0, variableOutput.length()-1);//delete the last caret, which is a "^"
						}
					}catch(Exception e){
						if(variableInput.charAt(index-1)!='-'&&variableOutput.charAt(variableOutput.length()-1)=='^'){//if the last letter is a caret, delete it
							variableOutput=variableOutput.substring(0, variableOutput.length()-1);//delete the last caret, which is a "^"
						}
						else if(variableInput.charAt(index-1)=='-'){
							variableOutput = variableOutput + "{-" + variableInput.charAt(index)+"}";//...add a bracket and the digit...
//							System.out.println("(Format)NEGATIVE EXPONENT!");
						}
					}
				}else{//it is not '1'
					variableOutput = variableOutput + '{' + variableInput.charAt(index);//...add a bracket and the digit...
					while (index+1<variableInput.length() && Character.isDigit(variableInput.charAt(index+1))){ //...then if the next character is a digit...
						variableOutput = variableOutput + variableInput.charAt(index+1);//...add that digit too.
						index++;
					}
					variableOutput = variableOutput + "}"; //then add the closing bracket.	
				}
			}
		}
//		if(variableInput.contains("1"))System.out.println("output = "+variableOutput);
		return variableOutput;
	}
	
	public static double roundedDouble(double n, double r){//when used, all doubles will be rounded according to the number of decimal places
		if (r!=0){
		decimalplaces=(int)Math.pow(10, r);
		int ns=(int)Math.round((n*decimalplaces));//scales it
		n = ((double)ns)/decimalplaces;
		return n;
		}
		else{
			n=(int)n;
			return n;
			}
	}
	
	public static boolean identifyTrivialDecimals(double n){//tells whether or not the decimal stem is .0
		int j = (int)n;
		if (n-j==0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static String getCoefficient(double n){//returns a string that will not include a trivial decimal stem
		if ((identifyTrivialDecimals(n))){
			int m=(int)n;
			String s = "" + m;
			return s;
		}
		else{
			String s = "" + n;
			return s;
		}
	}
	
	public static String getStringWithSign(int input){
		if(input>0) return "\\;+\\;"+input;
		else if(input==0) return "";
		else return "\\;-\\;"+Math.abs(input);
	}
	
	public static String getStringWithSign(double input){
		if(input>0) return "\\;+\\;"+doubleToString(input);
		else if(input==0) return "";
		else return "\\;-\\;"+doubleToString(Math.abs(input));
	}
	
	//returns 1st, 
	public static String getOrdinalNumber(int number){
		if(number%10==1){
			return number+"st";
		}else if(number%10==2){
			return number+"nd";
		}else if(number%10==3){
			return number+"rd";
		}else{
			return number+"th";	
		}
	}
}
