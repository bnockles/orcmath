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
import com.orcmath.type.Type;

public class SketchXYLocus extends Type{

	private String graphic1Address = "PDFs/Graphics/TypeSpecific/Locus/RegentsGrid.jpg";
	
	static int lastSelection;
	static int selectionBeforeLast1;
	static int selectionBeforeLast2;
	static int selectionBeforeLast3;
	
	public SketchXYLocus(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=20;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.25;
	}
	
	public SketchXYLocus(int difficulty){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=20;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.25;
		getInstance(difficulty);
		//question and answer is determined in the getInstance constructor
	}
	
	private void getInstance(int difficulty){
		
		int graphicNumber = Ops.randomInt(1, 3);
		while (graphicNumber == lastSelection || 
				graphicNumber == selectionBeforeLast1 ||
				 graphicNumber == selectionBeforeLast2 ||
				 graphicNumber == selectionBeforeLast3
				 ) graphicNumber = Ops.randomInt(1, 3);
		
		if (graphicNumber == 1) get1Instance();
		if (graphicNumber == 2) get2Instance();
		if (graphicNumber == 3) get3Instance();
//		
//		selectionBeforeLast3 = selectionBeforeLast2;
//		selectionBeforeLast2 = selectionBeforeLast1;
		selectionBeforeLast1 = lastSelection;
		lastSelection = graphicNumber;
		
	}
	
	private void get1Instance(){
		String theQuestion = "\\begin{array}{l}";

		theQuestion += "\\text{On the set of axes below, graph the locus of points equidistant from the x-axis and y-axis.}";
		theQuestion += "\\\\\\text{On the same set of axes, graph the locus of points "+Ops.randomInt(2, 6) +" units from the line x =" +Ops.randomInt(-4, 4)+". Label}";
		theQuestion += "\\\\\\text{with an X all points that satisfy both conditions.}";
		theQuestion += "\\\\\\text{                                      }\\includegraphics[width=20cm,interpolation=bicubic]{"+graphic1Address+"}";
		theQuestion += "\\end{array}";

		question = theQuestion;
		answer = "";
	}
	
	private void get2Instance(){
		String theQuestion = "\\begin{array}{l}";
		
		String line = "y";
		boolean horizontal = true;
		
		if (Math.random()>.5) {
			line = "x";
			horizontal=false;
		}
		
		int lineValue = Ops.randomInt(-5, 5);
		int distanceFromLine = Ops.randomInt(1, 6);
		int distanceFromPoint = 5;
		
		int pointXValue = 0;
		int pointYValue = 0;
		
		
		if (horizontal){
			if (Math.random()>.5){
			pointYValue = lineValue + distanceFromLine + Ops.randomInt(3,4);
			}
			else {
			pointYValue = lineValue - distanceFromLine - Ops.randomInt(3,4);
			}
			pointXValue = Ops.randomInt(-6, 6);
		}
		else {
			if (Math.random()>.5){
			pointXValue = lineValue + distanceFromLine + Ops.randomInt(3,4);
			}
			else {
			pointXValue = lineValue - distanceFromLine - Ops.randomInt(3,4);
			}
			pointYValue = Ops.randomInt(-6, 6);
		}
	

		theQuestion += "\\text{On the set of axes below, graph the locus of points that are }"+ distanceFromLine + "\\text{ units from the line }"+ line +" = " + lineValue;
		theQuestion += "\\\\\\text{and the locus of points that are }"+ distanceFromPoint +"\\text{ units from the point }"+ "\\left("+ pointXValue + ",\\;"+ pointYValue + "\\right).\\text{ Label with an X all points}"; 
		theQuestion += "\\\\\\text{that satisfy both conditions.}";
		theQuestion += "\\\\\\text{                                      }\\includegraphics[width=20cm,interpolation=bicubic]{"+graphic1Address+"}";
		theQuestion += "\\end{array}";

		question = theQuestion;
		answer = "";
	}
	private void get3Instance(){
		String theQuestion = "\\begin{array}{l}";
		
		String line = "y";
		boolean horizontal = true;
		
		if (Math.random()>.5) {
			line = "x";
			horizontal=false;
		}
		
		int lineValue = Ops.randomInt(-5, 5);
		int aboveAndBelowLine = Ops.randomInt(2, 5);
		

		int distanceFromPoint = 5;
		int distanceFromLine = Ops.randomInt(3, 4);
		

		int pointXValue = 0;
		int pointYValue = 0;
		
		
		if (horizontal){	
			if (Math.random()>.5){
			pointYValue = lineValue + distanceFromLine;
			}
			else{
				pointYValue = lineValue - distanceFromLine;	
			}
			pointXValue = Ops.randomInt(-6, 6);
		}
		else {
			if (Math.random()>.5){
				pointXValue = lineValue + distanceFromLine;
			}
			else{
				pointXValue = lineValue - distanceFromLine;
			}
			pointYValue = Ops.randomInt(-6, 6);
		}
	

		theQuestion += "\\text{On the set of axes below, graph the locus of points that are equidistant from the lines }"+ line + " = " + (lineValue+aboveAndBelowLine) + "\\text{ and }";
		theQuestion += "\\\\" + line + " = " + (lineValue-aboveAndBelowLine) + "\\text{ and the locus of points that are }"+ distanceFromPoint +"\\text{ units from the point }"+ "\\left("+ pointXValue + ",\\;"+ pointYValue + "\\right).\\text{ Label with an X all points}"; 
		theQuestion += "\\\\\\text{that satisfy both conditions.}";
		theQuestion += "\\\\\\text{                                      }\\includegraphics[width=20cm,interpolation=bicubic]{"+graphic1Address+"}";
		theQuestion += "\\end{array}";

		question = theQuestion;	
		answer = "";
	}
}
