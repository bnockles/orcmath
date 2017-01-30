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
package com.orcmath.drawabletype;

import com.orcmath.drawable.CoordinateImage;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.LinearExpression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class SolveProportionLinear extends MultipleChoiceQuestion{
	

	char variable;
	
	public SolveProportionLinear(){
		variable=Variable.randVar();
		instructions = "Solve for "+variable;
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public SolveProportionLinear(int difficulty){
		variable=Variable.randVar();
		instructions = "Solve for "+variable;
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		this.difficulty=difficulty;
		getInstance();
		//question and answer is determined in the getInstance constructor
	}
	


	
	
	protected void getInstance(){	
		int numValue2;
		int denValue2;
		int numValueSimplified;
		int denValueSimplified;
		
		//Step 1: Determine 1st and 2nd ratios
		int numValue1=20;
		int denValue1=20;
		int lcd=1;
		//this while loop avoids fractions like 19/20 that contain large, unsimplifiable digits
		while((numValue1>12 || denValue1>12) && lcd==1){
			numValue1 = Ops.randomInt(1, 20);
			denValue1 = Ops.randomInt(2, 20);
			while(denValue1==numValue1){
				denValue1 = Ops.randomInt(2, 20);
			}
			lcd=numValue1;
			boolean isLCD=false;
			while(!isLCD){
				if(denValue1%lcd==0 && numValue1%lcd==0){
					isLCD=true;
				}else{
					lcd--;			
				}
			}
		}
		
		
		
		numValueSimplified=numValue1/lcd;
		denValueSimplified=denValue1/lcd;

		int multiple=Ops.randomInt(1, 5);
		numValue2=multiple*numValueSimplified;
		denValue2=multiple*denValueSimplified;
		
		//Step 2: Determine which parts of the ratio contain a variable
		boolean num1containsVar=false;
		boolean num2containsVar=false;
		boolean den1containsVar=false;
		boolean den2containsVar=false;
		
		if(Math.random()>.5)num1containsVar=true;
		if(!num1containsVar)den2containsVar=true;
		if(Math.random()>.5)num2containsVar=true;
		if(!num2containsVar)den1containsVar=true;
		
		//Step 3 determine x value and expressions
		Expression num1Expression;
		Expression num2Expression;
		Expression den1Expression;
		Expression den2Expression;
		
		int coefMin = -5;
		int coefMax = 5;
		int bValueMax = 20;
		int xValue=Ops.randomInt(-12, 12);
		if(num1containsVar)num1Expression=Problem.createLinearExpression(coefMax, bValueMax, xValue, numValue1, ""+variable);
		else num1Expression=new Expression(new Term(numValue1));
		if(num2containsVar)num2Expression=Problem.createLinearExpression(coefMax, bValueMax, xValue, numValue2, ""+variable);
		else num2Expression=new Expression(new Term(numValue2));
		if(den1containsVar)den1Expression=Problem.createLinearExpression(coefMax, bValueMax, xValue, denValue1, ""+variable);
		else den1Expression=new Expression(new Term(denValue1));
		if(den2containsVar)den2Expression=Problem.createLinearExpression(coefMax, bValueMax, xValue, denValue2, ""+variable);
		else den2Expression=new Expression(new Term(denValue2));
		
		question = "\\frac{"+num1Expression+"}{"+den1Expression+"}=\\frac{"+num2Expression+"}{"+den2Expression+"}";
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		answerWork.setCheckWork(true);
		answerWork.addSolveProportionLinearSteps(num1Expression,den1Expression,num2Expression,den2Expression, ""+variable, xValue);
		
		answerWork.finish();
		answer=answerWork.getLatexTabular();
		
	}
	
}
