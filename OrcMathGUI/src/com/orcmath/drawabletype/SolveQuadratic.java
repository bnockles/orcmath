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

import com.orcmath.local.WorkTable;
import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Quadratic;
import com.orcmath.objects.Term;
import com.orcmath.type.Type;

public class SolveQuadratic extends Type{

	int degree;
	private String[] variationsOnInstructions= {"Solve for x", "Identify the roots of the following equation."};
	
	public SolveQuadratic(){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=170;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		degree = 2;
	}
	
	public SolveQuadratic(int difficulty){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=170;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		degree = 2;
		getInstance(difficulty);
	}
	


	public void getInstance(int diff){
		//roots is a term array because only terms can be integers or rationals or radicals, for that matter

		//String variable ="" + Variable.randVar();
		int root1 = Ops.randomInt(-8, 8);
		int root2 = Ops.randomInt(-8, 8);
		int a = 1;
		int b = -(root1+root2);
		int c = root1*root2;
		if(diff==2){
			int scalar = Ops.randomInt(1, 5);
			a=scalar*a;
			b=scalar*b;
			c=scalar*c;
		}
		if(diff==3 ||diff==4){
			a = Ops.randomInt(1, diff*2);
			b = Ops.randomInt(-diff*2, diff*2);
			c = Ops.randomInt(-diff*2, diff*2);
			if(c == 0) c = Ops.randomInt(-diff*2, diff*2);
		}
		
		Term[] quadratic;
		if (b!=0) {
			quadratic= new Term[3];
			quadratic[0] = new Term(a, "x^2");
			quadratic[1] = new Term(b, "x");
			quadratic[2] = new Term(c);
		}
		else {
			quadratic= new Term[2];
			quadratic[0] = new Term(a, "x^2");
			quadratic[1] = new Term(c);
		}
		
		question = Format.termArrayToString(quadratic) + "= 0";
		
		WorkTable answerWork = new WorkTable();
		answerWork.addSolvingForQuadraticRootsSteps(quadratic, false);//NOTE: "false" solve for x and not y
		answerWork.addHorizontalLine();
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	
}
