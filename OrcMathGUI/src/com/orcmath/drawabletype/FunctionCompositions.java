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
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.Graph;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;

public class FunctionCompositions extends DynamicType{

	public FunctionCompositions(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}

	public FunctionCompositions(int difficulty){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		this.difficulty=difficulty;
		getInstance();
		//question and answer is determined in the getInstance constructor
	}
	
	protected void getInstance(){

		Graph f=new Graph();
		int ftype;
		double[] fcoefficents;
		f.setFunction(ftype, fcoefficents);

		Graph g=new Graph();
		int gtype;
		double[] gcoefficents;
		g.setFunction(gtype, gcoefficents);
		
		String objective;//this string should say f of g or g of f
		//initiate question string
		String questionText = "Given the function #f(x)="+f.getFunctionText()+ "# and #g(x)="+g.getFunctionText()+"#, determine #"+objective+"#";
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		answerWork.newTextLine("We will try to write an equation in the form, #y=mx+b.# To do this, we first need to " +
				"calculate the value of the slope of the given line.");

		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	
}
