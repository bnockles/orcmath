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

import com.orcmath.local.MultipleChoice;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Relation;
import com.orcmath.type.Type;

public class IdentifyFunction extends Type{

	public IdentifyFunction(){
		numberOfColumns=2;
		verticalSpacing=80;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public IdentifyFunction(int difficulty){
		numberOfColumns=2;
		verticalSpacing=80;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		getInstance(difficulty);
	}
	
	public void getInstance(int difficulty){
		String theQuestion = "\\begin{array}{l}";
		theQuestion +="\\text{Which of the following is a function?}";
		Relation[] relations = Relation.getThreeRelations();
		Relation function = new Relation('a');
		function.defineFunction(true);
		MultipleChoice mc = new MultipleChoice(
				relations[0].toString(),
				relations[1].toString(),
				relations[2].toString(),
				function.toString(),
				function.toString());
		theQuestion += "\\\\\\text{    (1) }" + mc.getChoiceA();
		theQuestion +="\\\\\\text{    (2) }" + mc.getChoiceB();
		theQuestion +="\\\\\\text{    (3) }" + mc.getChoiceC();
		theQuestion +="\\\\\\text{    (4) }" + mc.getChoiceD();
		theQuestion += "\\end{array}";
		
		question = theQuestion;
		answer = mc.getAnswer();
	}
	
}
