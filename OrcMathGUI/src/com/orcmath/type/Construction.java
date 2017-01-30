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

public class Construction extends Type{
	
	static int lastSelection;
	static int selectionBeforeLast1;
	static int selectionBeforeLast2;
	static int selectionBeforeLast3;
	static int selectionBeforeLast4;
	
	public Construction(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.4;
	}
	
	public Construction(int difficulty){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.4;
		getInstance();
		//question and answer is determined in the getInstance constructor
	}
	
	protected void getInstance(){
		
		int graphicNumber = Ops.randomInt(1, 9);
		while (graphicNumber == lastSelection || 
				graphicNumber == selectionBeforeLast1 ||
				 graphicNumber == selectionBeforeLast2 ||
				 graphicNumber == selectionBeforeLast3 ||
				 graphicNumber == selectionBeforeLast4
				 ) graphicNumber = Ops.randomInt(1, 9);
		
		String theQuestion = "\\begin{array}{l}";
		theQuestion += "\\includegraphics[width=40cm,interpolation=bicubic]{"+"PDFs/Graphics/TypeSpecific/Construction/construction"+graphicNumber+".png}";
		theQuestion += "\\end{array}";

		question = theQuestion;
		answer = "";
		
		selectionBeforeLast4 = selectionBeforeLast3;
		selectionBeforeLast3 = selectionBeforeLast2;
		selectionBeforeLast2 = selectionBeforeLast1;
		selectionBeforeLast1 = lastSelection;
		lastSelection = graphicNumber;
		
	}
	
}
