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
import java.util.Arrays;

public class Variable implements Comparable<Variable>
{
    char variable;
    int exp;
    
    public Variable(char x, int y)
    {
        variable=x;
        exp=y;
    }
    
	public int compareTo(Variable compareVariable){
		int compareDegree = ((Variable) compareVariable).getBase();
		//Descending order (switch for ascending order)
		return this.variable - compareDegree;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof Variable){
			return (this.variable==((Variable)obj).variable && this.exp==((Variable)obj).exp);
		}
		else{
		return false;
		}
	}
    
    public String toString()
    {
        return "" + variable + "^" + exp; 
    }

	public char getBase() {
		return variable;
	}

	public int getExponent() {
		return exp;
	}

	public void setExponent(int exp) {
		this.exp = exp;
	}
	
    public static char randVar(){
    	int rand = Ops.randomInt(0,19);
    	String possibleVariables = "abcdejkmnpqrstuvwxyz";
    	char randomVar = possibleVariables.charAt(rand);
    	return randomVar;
    }
    
    
    public static char randCapVar(){
    	int rand = Ops.randomInt(0,19);
    	String possibleVariables = "ABCDEJKMNPQRSTUVWXYZ";
    	char randomVar = possibleVariables.charAt(rand);
    	return randomVar;
    }
    
    public static char[] randCapVars(int numberOfUniqueChars){
    	char [] chars = new char[numberOfUniqueChars];
    	String variablesString = "ABCDEJKMNPQRSTUVWXYZ";
    	ArrayList<Character> possibleVariables=new ArrayList<Character>();
    	for (int index = 0; index<variablesString.length(); index++){
    		possibleVariables.add(variablesString.charAt(index));
    	}
    	for (int index = 0; index<numberOfUniqueChars; index++){
    		int rand = Ops.randomInt(0,possibleVariables.size()-1);
    		char randomChar = possibleVariables.get(rand);
    		possibleVariables.remove(rand);
    		chars[index] = randomChar;
    	}
    	return chars;
    }
    
    public static char randPlaneVar(){
    	int rand = Ops.randomInt(0,9);
    	String possibleVariables = "ABCDMNPQRS";
    	char randomVar = possibleVariables.charAt(rand);
    	return randomVar;
    }
    
    public static char[] randPlaneVars(int numberOfUniqueChars){
    	char [] chars = new char[numberOfUniqueChars];
    	String variablesString = "ABCDMNPQRS";
    	ArrayList<Character> possibleVariables=new ArrayList<Character>();
    	for (int index = 0; index<variablesString.length(); index++){
    		possibleVariables.add(variablesString.charAt(index));
    	}
    	for (int index = 0; index<numberOfUniqueChars; index++){
    		int rand = Ops.randomInt(0,possibleVariables.size()-1);
    		char randomChar = possibleVariables.get(rand);
    		possibleVariables.remove(rand);
    		chars[index] = randomChar;
    	}
    	return chars;
    }
    
    public static char[] randVars(int numberOfUniqueChars){
    	char [] chars = new char[numberOfUniqueChars];
    	String variablesString = "abcdejkmnpqrstuvwxyz";
    	ArrayList<Character> possibleVariables=new ArrayList<Character>();
    	for (int index = 0; index<variablesString.length(); index++){
    		possibleVariables.add(variablesString.charAt(index));
    	}
    	for (int index = 0; index<numberOfUniqueChars; index++){
    		int rand = Ops.randomInt(0,possibleVariables.size()-1);
    		char randomChar = possibleVariables.get(rand);
    		possibleVariables.remove(rand);
    		chars[index] = randomChar;
    	}
    	return chars;
    }

	public static ArrayList<Variable> getCopy(ArrayList<Variable> list) {
		ArrayList<Variable> copy = new ArrayList<Variable>();
		for (int index = 0; index < list.size(); index++){
			Variable v = new Variable(list.get(index).getBase(), list.get(index).getExponent());
			copy.add(v);
		}
		return copy;
	}
}