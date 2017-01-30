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

import com.orcmath.objects.*;

public class SimplifyExpression extends Type {

	
	private String[] variationsOnInstructions= {"Simplify the following expression.", "Simplify.", "Combine like terms."};
	
	public SimplifyExpression() {
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=110;
		scaleFactor = .40;
		whetherInstructionsAreNeverIncluded=false;
	}
	
	public SimplifyExpression(int difficulty) {
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];
		numberOfColumns=2;
		verticalSpacing=110;
		scaleFactor = .40;
		whetherInstructionsAreNeverIncluded=false;
		Term[] expression = getInstance(difficulty);
		question = Format.termArrayToString(expression);
		answer = Format.termArrayToString(Ops.combineLikeTerms(expression));	
	}
	
	private Term[] getInstance(int difficulty){
		difficulty= 2*difficulty+1;//a maximum of 9 terms in a problem 
		
		Term[] terms=new Term[difficulty];
		int collection = Ops.randomInt(1, 4); //picks from a random collection
		//System.out.println("Collection is: "+collection);
		for (int index=0; index<difficulty; index ++){
			//System.out.println("Index is: " + index);
			int lowSelect = 2*(int)(difficulty/4);//if difficulty is expert, no beginner level variables will be used.
			int highSelect=	(int)((Math.pow(2, (difficulty/2)))-1);
			int randomPick = Ops.randomInt(lowSelect,highSelect);
			Term t = new Term(getACoefficient(difficulty/2),//selects a coefficient
					getAVariable(collection,randomPick));//selects a variable
			//System.out.println("This variable was selected: " + randomPick);
			terms[index]=t;
			//System.out.println("Created term: " + t.toString()+ " at index: " + index);
		}
		return terms;
	}
	
	public String getAVariable(int collection, int selection){//collection is 1-4, selection is 0-1, 0-2, 0-4, 0-8
		String s = "";
		if (collection==1){
			if (selection==0){s= "xy";}
			if (selection==1){s= "x";}//-----beginner level
			if (selection==2){s= "x^2";}//-----practicing level
			if (selection==3){s= "yx";}
			if (selection==4){s= "y^2";}//-----intermediate level
			if (selection==5){s= "x^2y";}
			if (selection==6){s= "yx^2";}
			if (selection==7){s= "x^2y^2";}
			if (selection==8){s= "y^2x^2";}//-----expert level(beginner level is omitted)
		}
		if (collection==2){
			if (selection==0){s= "a";}
			if (selection==1){s= "b";}//-----beginner level
			if (selection==2){s= "ab";}//-----practicing level
			if (selection==3){s= "ac";}
			if (selection==4){s= "bc";}//-----intermediate level
			if (selection==5){s= "abc";}
			if (selection==6){s= "ca";}
			if (selection==7){s= "bac";}
			if (selection==8){s= "ba";}//-----expert level(beginner level is omitted)
		}
		if (collection==3){
			if (selection==0){s= "x^2";}
			if (selection==1){s= "y^2";}//-----beginner level
			if (selection==2){s= "xv";}//-----practicing level
			if (selection==3){s= "vx";}
			if (selection==4){s= "x^4y^2z";}//-----intermediate level
			if (selection==5){s= "x^2y^2";}
			if (selection==6){s= "y^4x^2z";}
			if (selection==7){s= "zx^4y^2";}
			if (selection==8){s= "x^2y^4z";}//-----expert level(beginner level is omitted)
		}
		if (collection==4){
			if (selection==0){s= "";}
			if (selection==1){s= "m";}//-----beginner level
			if (selection==2){s= "n";}//-----practicing level
			if (selection==3){s= "mn";}
			if (selection==4){s= "m^2n^2";}//-----intermediate level
			if (selection==5){s= "nm";}
			if (selection==6){s= "n^2m^2";}
			if (selection==7){s= "m^2n^3m";}
			if (selection==8){s= "m^3n^3";}//-----expert level(beginner level is omitted)
		}

		return s;
			
	}
	
	public int getACoefficient(int range){//range is a number between 1 and 4
		double r=Math.random();
		int sign;
		if (r>=.5){
			sign=1;
		}
		else{
			sign=-1;
		}
		int coefficient = sign*Ops.randomInt(1,(int)Math.pow(4, range));
		return coefficient;
	}
	
	
	}
