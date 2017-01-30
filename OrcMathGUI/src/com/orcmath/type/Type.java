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
package com.orcmath.type;


import java.util.ArrayList;

import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class Type {
	

	//these fields are about the display of questions
	protected String instructions;
	protected String question;
	protected String answer;
	protected int difficulty;
	
	public String keyTheorem="";
	public String falseTheorem1="";
	public String falseTheorem2="";
	public String falseTheorem3="";
	public static int numberOfColumns = 1;
	public static int verticalSpacing = 50;
	public static boolean whetherInstructionsAreNeverIncluded = true;
	public static double scaleFactor = .24;//this no longer has an affect. Look in Problem class, "getScaleFactor()"
	public static final int QUESTION_WIDTH=1100;
//	Object graphic;
	
	//All difficulty in on a scale of 1 to 4. The individual constructors of each problem type can modify accordingly

	
	public void setData(int newDifficulty){
		difficulty=newDifficulty;
		getInstance();
	}
	
	protected void getInstance(){}
	
	public String getInstructions() {
		return instructions;
	}
	public String getKeyTheorem() {
		return keyTheorem;
	}
	
	public String getFalseTheorem(int selection){
		
		if(selection==1){
			if(falseTheorem1.equals("")){
				return "No multiple choice selection for this question type";
			}else{
				return falseTheorem1;
			}
		}else if(selection==2){
			if(falseTheorem2.equals("")){
				return "No multiple choice selection for this question type";
			}else{
				return falseTheorem2;
			}
		}else if(selection==3){
			if(falseTheorem3.equals("")){
				return "No multiple choice selection for this question type";
			}else{
				return falseTheorem3;
			}
		}else{
			return "Invalid call for false theorem. (Called for selection: \""+selection+"\", but only 3 selections are available.)";
		}
	}
	
	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}
	
//	public Object getGraphic() {
//		return graphic;
//	}
	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public int getVerticalSpacing() {
		return verticalSpacing;
	}

	public double getScaleFactor(){
		return scaleFactor;
	}
	
	public boolean getWhetherInstructionsAreNeverIncluded(){
		return whetherInstructionsAreNeverIncluded;
	}

	public static Term randomTerm(int coefficientMin, int coefficientMax, int numberOfExponents, int exponentMin, int exponentMax){
		//We are now creating the term that will be simplified from the radical
		//it has a coefficient:
		int coefficient = Ops.randomInt(coefficientMin, coefficientMax);//a number between 2 and 10
		//it has a string of variables
		ArrayList<Variable> variables = new ArrayList<Variable>();
		for (int index = 0; index<numberOfExponents; index ++){
			char var = Variable.randVar();// calls the method that selects a random variable, omitting I and O
			int exp = Ops.randomInt(exponentMin, exponentMax);
			variables.add(new Variable (var, exp));
		}
		Term term = new Term(coefficient, variables);//in this case, variables is an ArrayList
		return term;
		
	}
	
	public  static Term randomTerm(int coefficientMin, int coefficientMax, int numberOfExponents, int exponentMin, int exponentMax, char[] fromThisSelectionOfVars){
		//We are now creating the term that will be simplified from the radical
		//it has a coefficient:
		int coefficient = Ops.randomInt(coefficientMin, coefficientMax);//a number between 2 and 10
		//it has a string of variables
		ArrayList<Variable> variables = new ArrayList<Variable>();
		for (int index = 0; index<numberOfExponents; index ++){
			char var = fromThisSelectionOfVars[Ops.randomInt(0, fromThisSelectionOfVars.length-1)];// calls the method that selects a random variable, omitting I and O
			int exp = Ops.randomInt(exponentMin, exponentMax);
			variables.add(new Variable (var, exp));
		}
		//variables = Term.combineDuplicateVariables(variables);
		Term term = new Term(coefficient, Term.combineDuplicateVariables(variables));//in this case, variables is an ArrayList
		return term;
		
	}
}
