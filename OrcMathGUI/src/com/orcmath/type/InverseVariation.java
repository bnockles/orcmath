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

import com.orcmath.objects.Ops;
import com.orcmath.objects.Variable;

public class InverseVariation extends Type{
	
	public InverseVariation(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=20;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public InverseVariation(int difficulty){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=20;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		getInstance(difficulty);
		//question and answer is determined in the getInstance constructor
	}

	
	public void getInstance(int difficulty){
		String theQuestion = "\\begin{array}{l}";
		
		char[] variables = Variable.randVars(2);
		int a;
		int billsWeight = 10*Ops.randomInt(12, 19);
		double distanceFromFulcrum = .5*Ops.randomInt(7, 14);
		int dansWeight = 10*Ops.randomInt(12, 19);
		while (dansWeight==billsWeight){
			dansWeight = 10*Ops.randomInt(12, 19);
		}
		int b;
		int x;
		int x2;
		double y;
		double y2;
		int k;
		
		if (difficulty==1 || difficulty==2){
			
			a = Ops.randomInt(2, 6);
			b = Ops.randomInt(2, 8);
			x = a * b;
			y = Ops.randomInt(2, 15);
			if (Math.random()>.5) x2 = b;
			else x2 = (int)(a*y);
			k = (int)(a*x*y);
		}
		else{
			k = Ops.randomInt(50, 400);
			x = Ops.randomInt(4, 30);
			x2 = Ops.randomInt(4, 30);
			while (x == x2){
				x2 = Ops.randomInt(4, 30);
			}
			y = k/x;
		}
		
		String theAnswer;
		double textVersion = Math.random();
		if(textVersion>.5){
			theQuestion += "\\text{If }" + variables[0] +  "\\text{ varies inversely as }" + variables[1] +  "\\text{, and }" + variables[0] + " = " + x + "\\text{ when }" + variables[1] + " = " + (int)y + "\\text{, what is the value of }" + variables[1] + "\\text{ when }" + variables[0] + " = " + x2 + "\\text{?}";
			theQuestion += "\\end{array}";
			theAnswer = "\\text{The value of }" + variables[1] + "\\text{ when }" + variables[0] + " = " + x2 + "\\text{ is }" + (double)k/(double)x2;
		}
		else{
			theQuestion += "\\text{To balance a seesaw, the distance, in feet, a person is from the fulcrum is inversely  }";
			theQuestion += "\\\\\\text{proportional to the person�s weight, in pounds. Bill, who weighs " + billsWeight + " pounds, is}";
			theQuestion += "\\\\\\text{sitting " + distanceFromFulcrum + " feet away from the fulcrum. If Dan weighs " + dansWeight + " pounds, how far from the}";
			theQuestion += "\\\\\\text{fulcrum should he sit to balance the seesaw, to the nearest tenth of a foot?}";
			theQuestion += "\\end{array}";
			theAnswer = "\\text{Dan should sit " + (billsWeight*distanceFromFulcrum)/dansWeight + " feet away from the fulcrum.}";
		}

		question = theQuestion;
		answer =  theAnswer;
	}
}
