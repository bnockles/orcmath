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
import com.orcmath.objects.Variable;
import com.orcmath.type.Type;

public class GeometricRelationship extends Type{
	
	//this part of code forces the generator to choose a standard that is unique from the last six.
	static int lastSelection;
	static int selectionBeforeLast1;
	static int selectionBeforeLast2;
	static int selectionBeforeLast3;
	static int selectionBeforeLast4;
	static int selectionBeforeLast5;

	
	public GeometricRelationship(){
		instructions = "" ;
		numberOfColumns=2;
		verticalSpacing=10;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.33;
		getInstance();
	}
	
	public GeometricRelationship(int difficulty){
		instructions = "" ;
		numberOfColumns=2;
		verticalSpacing=10;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.33;
		getInstance();
		//question and answer is determined in the getInstance constructor
	}
	
	protected void getInstance(){
		
		int selection = Ops.randomInt(5, 5);
		while (selection == lastSelection || 
				 selection == selectionBeforeLast1 ||
				 selection == selectionBeforeLast2 ||
				 selection == selectionBeforeLast3 ||
				 selection == selectionBeforeLast4 ||
				 selection == selectionBeforeLast5) selection = Ops.randomInt(0, 8);
		 
		
		//G.G.1
		if (selection == 0) getGG1Instance();
		//G.G.2
		if (selection == 1) getGG2Instance();
		//G.G.3
		if (selection == 2) getGG3Instance();
		//G.G.4
		if (selection == 3) getGG4Instance();
		//G.G.5
		if (selection == 4) getGG5Instance();
		//G.G.6
		if (selection == 5) getGG6Instance();
		//G.G.7
		if (selection == 6) getGG7Instance();	
		//G.G.8
		if (selection == 7) getGG8Instance();	
		//G.G.9
		if (selection == 8) getGG9Instance();


		selectionBeforeLast4 = selectionBeforeLast3;
		selectionBeforeLast3 = selectionBeforeLast2;
		selectionBeforeLast2 = selectionBeforeLast1;
		selectionBeforeLast1 = lastSelection;
		lastSelection = selection;
	}
	


	private void getGG1Instance(){
		String theQuestion = "\\begin{array}{l}";
		MultipleChoice mc;
		char[] lineVariables = Variable.randVars(3);
		char lineVariable=lineVariables[0];
		char lineVariable2=lineVariables[1];
		char perpVariable = lineVariables[2];
		char pointVariable = Variable.randCapVar();
		char planeVariable = Variable.randCapVar();
		//so that the variables are never the same
		while (perpVariable==lineVariable) perpVariable = Variable.randVar();
		
		int questionVariation = Ops.randomInt(0, 1);
		if (questionVariation == 0){
		theQuestion += "\\text{Lines }" + lineVariable + "_1\\text{ and }" + lineVariable + "_2\\text{ intersect at point }" + pointVariable + "\\text{. " +
				"Line }" + perpVariable + "\\text{ is perpendicular to lines }" + lineVariable + "_1\\text{ and }" + lineVariable + "_2";
		theQuestion += "\\\\\\text{at point }" + pointVariable + "\\text{. Which statement is always true?}";
		
		mc = new MultipleChoice(
				"Lines }"+ lineVariable + "_1\\text{ and }" + lineVariable + "_2 \\text{ are perpendicular.}",
				"Line }" + perpVariable + "\\text{ is parallel to the plane determined by lines }" + lineVariable + "_1\\text{ and }" + lineVariable + "_2.",
				"Line }" + perpVariable + "\\text{ is perpendicular to the plane determined by lines }" + lineVariable + "_1\\text{ and }" + lineVariable + "_2.",
				"Line }" + perpVariable + "\\text{ is coplanar with lines }" + lineVariable + "_1\\text{ and }" + lineVariable + "_2.",
				"Line }" + perpVariable + "\\text{ is perpendicular to the plane determined by lines }" + lineVariable + "_1\\text{ and }" + lineVariable + "_2.");
		
		}
		else{
			theQuestion += "\\text{In plane }\\mathcal{" + planeVariable + "}\\text{, lines }" + lineVariable + "\\text{ and }"+ lineVariable2 + "\\text{ intersect at point }" + pointVariable + 
					"\\text{. If line }" + perpVariable + "\\text{ is perpendicular to}";
			theQuestion += "\\\\\\text{line }" + lineVariable + "\\text{ and }" + lineVariable2 + "\\text{ at point }" + pointVariable + "\\text{, Then line }" + perpVariable + "\\text{ is}";	
		
		mc = new MultipleChoice(
				"contained in plane }\\mathcal{" +planeVariable +"}",
				"perpendicular to plane }\\mathcal{" +planeVariable +"}",
				"parallel to plane }\\mathcal{" +planeVariable +"}",
				"skew to plane }\\mathcal{" +planeVariable +"}",
				"perpendicular to plane }\\mathcal{" +planeVariable +"}");
		}
		theQuestion += "\\\\\\text{    (1) " + mc.getChoiceA();
		theQuestion +="\\\\\\text{    (2) " + mc.getChoiceB();
		theQuestion +="\\\\\\text{    (3) " + mc.getChoiceC();
		theQuestion +="\\\\\\text{    (4) " + mc.getChoiceD();
		theQuestion += "\\end{array}";
		question = theQuestion;
		answer = "\\text{" + mc.getAnswer();
	}
	
	private void getGG2Instance(){
		String theQuestion = "\\begin{array}{l}";
		char lineVariable=Variable.randVar();
		char pointVariable = Variable.randCapVar();
		theQuestion += "\\text{Point }" + pointVariable + "\\text{ is on line }" + lineVariable + "\\text{. What's the total number of planes that are perpendicular}";
		theQuestion += "\\\\\\text{to line }" + lineVariable + "\\text{ and pass through point }" + pointVariable+ "\\text{?}";
		//no multiple choice is constructed because choices must correspond to numerals
		theQuestion += "\\\\\\text{    (1) 1}";
		theQuestion +="\\\\\\text{    (2) 2}" ;
		theQuestion +="\\\\\\text{    (3) 0}";
		theQuestion +="\\\\\\text{    (4) infinite}";
		theQuestion += "\\end{array}";
		question =theQuestion;
		answer = "\\text{1}";
		
	}
	
	private void getGG3Instance(){
		char pointVariable = Variable.randCapVar();
		String theQuestion = "\\begin{array}{l}";
		int questionVariation = Ops.randomInt(0, 1);
		if (questionVariation==0){
			theQuestion += "\\text{Through a given point, }" + pointVariable + "\\text{, on a plane, how many lines can be drawn}";
			theQuestion += "\\\\\\text{that are perpendicular to that plane?}";
		}
		else{
			char planeVariable = Variable.randPlaneVar();
			theQuestion += "\\text{Point }" + pointVariable + "\\text{ is not contained in plane }\\mathcal{" + planeVariable + "}\\text{ How many lines can be drawn}";
			theQuestion += "\\\\\\text{through point }" + pointVariable + "\\text{ that are perpendicular to plane }\\mathcal{" + planeVariable + "}\\text{?}";
		}
		//no multiple choice is constructed because choices must correspond to numerals
		theQuestion += "\\\\\\text{    (1) 1}";
		theQuestion +="\\\\\\text{    (2) 2}" ;
		theQuestion +="\\\\\\text{    (3) infinite}";
		theQuestion +="\\\\\\text{    (4) none}";
		theQuestion += "\\end{array}";
		question =theQuestion;
		answer = "\\text{1}";
	}

	//Know and apply that two lines perpendicular to the same plane are coplanar
	private void getGG4Instance() {
		String theQuestion = "\\begin{array}{l}";
		theQuestion += "\\text{If two different lines are pependicular to the same plane, they are}";
		int answerVariation = Ops.randomInt(0, 1);
		MultipleChoice mc;
		if (answerVariation==0){
			mc = new MultipleChoice(
				"parallel}",
				"perpendicular}",
				"congruent}",
				"collinear}",
				"parallel}");
		}
		else{
			mc = new MultipleChoice(
					"collinear}",
					"coplanar}",
					"congruent}",
					"consecutive}",
					"coplanar}");		
		}
		theQuestion += "\\\\\\text{    (1) " + mc.getChoiceA();
		theQuestion +="\\\\\\text{    (2) " + mc.getChoiceB();
		theQuestion +="\\\\\\text{    (3) " + mc.getChoiceC();
		theQuestion +="\\\\\\text{    (4) " + mc.getChoiceD();
		theQuestion += "\\end{array}";
		question = theQuestion;
		answer = "\\text{" + mc.getAnswer();
	}
	
	private void getGG5Instance(){
		char[] planeVariables = Variable.randPlaneVars(2);
		char lineVariable = Variable.randVar();
		String theQuestion = "\\begin{array}{l}";
		
		theQuestion += "\\text{If line }" + lineVariable + "\\text{ is contained in plane }\\mathcal{"+ planeVariables[0]+ "}\\text{ and }"+ lineVariable + "\\text{ is perpendicular to plane }\\mathcal{" + planeVariables[1] + "}\\text{, which}";
		theQuestion += "\\\\\\text{statement is true?}";
		MultipleChoice mc = new MultipleChoice(
				"Line }" + lineVariable + "\\text{ is parallel to plane }" + planeVariables[1],
				"Line }" + lineVariable + "\\text{ is perpendicular to plane }" + planeVariables[0],
				"Plane }" + planeVariables[0] + "\\text{ is parallel to plane }" + planeVariables[1],
				"Plane }" + planeVariables[0] + "\\text{ is perpendicular to plane }" + planeVariables[1],
				"Plane }" + planeVariables[0] + "\\text{ is perpendicular to plane }" + planeVariables[1]);

		theQuestion += "\\\\\\text{    (1) " + mc.getChoiceA();
		theQuestion +="\\\\\\text{    (2) " + mc.getChoiceB();
		theQuestion +="\\\\\\text{    (3) " + mc.getChoiceC();
		theQuestion +="\\\\\\text{    (4) " + mc.getChoiceD();
		theQuestion += "\\end{array}";
		question = theQuestion;
		answer = "\\text{" + mc.getAnswer();
	}
	
	private void getGG6Instance(){
		char[] lineVariables = Variable.randVars(2);
		char planeVariable = Variable.randPlaneVar();
		char pointVariable = Variable.randCapVar();
		String theQuestion = "\\begin{array}{l}";
		theQuestion += "\\text{The line }" + lineVariables[0] + "\\text{ is perpendicular to plane }\\mathcal{" + planeVariable + "}\\text{ at point }" + pointVariable + "\\text{.}";
		theQuestion += "\\\\\\text{If line }" + lineVariables[1] + "\\text{ is perpendicular to }" + lineVariables[0] + "\\text{ at }" + pointVariable + "\\text{, which must be true?}";

		MultipleChoice mc = new MultipleChoice(
				"Line }" + lineVariables[1] + "\\text{ is in plane }\\mathcal{" + planeVariable + "}",
				"Line }" + lineVariables[1] + "\\text{ is parallel to plane }\\mathcal{" + planeVariable + "}",
				"Line }" + lineVariables[1] + "\\text{ is perpendicular to plane }\\mathcal{" + planeVariable + "}",
				"Line }" + lineVariables[0] + "\\text{ is in plane }\\mathcal{" + planeVariable + "}",
				"Line }" + lineVariables[1] + "\\text{ is in plane }\\mathcal{" + planeVariable + "}");

		theQuestion += "\\\\\\text{    (1) " + mc.getChoiceA();
		theQuestion +="\\\\\\text{    (2) " + mc.getChoiceB();
		theQuestion +="\\\\\\text{    (3) " + mc.getChoiceC();
		theQuestion +="\\\\\\text{    (4) " + mc.getChoiceD();
		theQuestion += "\\end{array}";
		question = theQuestion;
		answer = "\\text{" + mc.getAnswer();
	}
	
	//Know and apply that if a line is perpendicular to a plane, then every plane containing the line is perpendicular to the given plane
	private void getGG7Instance(){//TODO Finish this section
		String theQuestion = "\\begin{array}{l}";
		char lineVariable = Variable.randVar();
		char planeVariable = Variable.randPlaneVar();
		char pointVariable = Variable.randCapVar();
		theQuestion += "\\text{Line }" + lineVariable + "\\text{ is perpendicular to plane }\\mathcal{" + planeVariable + "}\\text{ at point }" + pointVariable + "\\text{.}" ;
		theQuestion += "\\\\\\text{Which statement is true?}";

		MultipleChoice mc = new MultipleChoice(
				"Any point in plane }\\mathcal{" + planeVariable + "}\\text{ also will be on line }" + lineVariable + "\\text{.}" ,
				"Only one line in plane }\\mathcal{" + planeVariable + "}\\text{ will intersect line }" + lineVariable + "\\text{.}" ,
				"All planes that intersect plane }\\mathcal{" + planeVariable + "}\\text{ will pass through }" + pointVariable + "\\text{.}" ,
				"Any plane containing line }" + lineVariable + "\\text{ is perpendiculat to plane }\\mathcal{" + planeVariable + "}\\text{.}",
				"Any plane containing line }" + lineVariable + "\\text{ is perpendiculat to plane }\\mathcal{" + planeVariable + "}\\text{.}");

		theQuestion += "\\\\\\text{    (1) " + mc.getChoiceA();
		theQuestion +="\\\\\\text{    (2) " + mc.getChoiceB();
		theQuestion +="\\\\\\text{    (3) " + mc.getChoiceC();
		theQuestion +="\\\\\\text{    (4) " + mc.getChoiceD();
		theQuestion += "\\end{array}";
		question = theQuestion;
		answer = "\\text{" + mc.getAnswer();
	}
	
	//Know and apply that if a plane intersects two parallel planes, then the intersection is two parallel lines
	private void getGG8Instance(){
		String theQuestion = "\\begin{array}{l}";
		MultipleChoice mc;
		int questionVariation = Ops.randomInt(0, 1);
		if (questionVariation == 0){
		theQuestion += "\\text{In three-dimensional space, two planes are parallel and a third plane intersects both}";
		theQuestion += "\\\\\\text{of the parallel planes.  The intersection of the planes is a}";
		mc = new MultipleChoice(
				"plane}",
				"point}",
				"pair of parallel lines}",
				"pair of intersecting lines}",
				"pair of parallel lines}");
		}
		else{
			char[] planeVars = Variable.randPlaneVars(3);	
			char[] lineVars = Variable.randVars(2);
			theQuestion += "\\text{Plane }\\mathcal{" + planeVars[0] + "}\\text{ is parallel to plane }" +
					"\\mathcal{" + planeVars[1] + "}\\text{. Plane }\\mathcal{" + planeVars[2] + "}\\text{ intersects plane }\\mathcal{"
					+ planeVars[0] + "}\\text{ in line }" + lineVars[0] + "\\text{ and intersects}";
			theQuestion += "\\\\\\text{plane }\\mathcal{" + planeVars[1] + "}\\text{ in line }" + lineVars[1] + 
					"\\text{. Lines }" + lineVars[0] + "\\text{ and }" + lineVars[1] + "\\text{ are}";
			mc = new MultipleChoice(
					"intersecting}",
					"parallel}",
					"perpendicular}",
					"skew}",
					"parallel}");
		}
			theQuestion += "\\\\\\text{    (1) " + mc.getChoiceA();
			theQuestion +="\\\\\\text{    (2) " + mc.getChoiceB();
			theQuestion +="\\\\\\text{    (3) " + mc.getChoiceC();
			theQuestion +="\\\\\\text{    (4) " + mc.getChoiceD();
			theQuestion += "\\end{array}";
			question = theQuestion;
			answer = "\\text{" + mc.getAnswer();
		
	}
	
	//Know and apply that if two planes are perpendicular to the same line, they are parallel
	private void getGG9Instance(){
		String theQuestion = "\\begin{array}{l}";
		MultipleChoice mc;
		char lineVariable = Variable.randVar();
		char[] planeVariables = Variable.randPlaneVars(2);
		int questionVariation = Ops.randomInt(0, 2);
		if (questionVariation == 0){
			theQuestion += "\\text{Line }" + lineVariable + "\\text{ is drawn so that it is perpendicular to two distinct planes, }\\mathcal{" + planeVariables[0]+ "}";
			theQuestion += "\\\\\\text{ and }\\mathcal{" + planeVariables[1] + "}\\text{. What maust be true about planes }\\mathcal{" + planeVariables[0]+ 
					"}\\text{ and }\\mathcal{" + planeVariables[1] + "}\\text{?}";
			mc = new MultipleChoice(
					"Planes }\\mathcal{" + planeVariables[0]+ "}\\text{ and }\\mathcal{" + planeVariables[1] + "}\\text{ are skew.}",
					"Planes }\\mathcal{" + planeVariables[0]+ "}\\text{ and }\\mathcal{" + planeVariables[1] + "}\\text{ are parallel.}",
					"Planes }\\mathcal{" + planeVariables[0]+ "}\\text{ and }\\mathcal{" + planeVariables[1] + "}\\text{ are perpendicular.}",
					"Plane }\\mathcal{" + planeVariables[0]+ "}\\text{ intersects plane }\\mathcal{" + planeVariables[1] + "}\\text{ but is not perpendicular.}",
					"Planes }\\mathcal{" + planeVariables[0]+ "}\\text{ and }\\mathcal{" + planeVariables[1] + "}\\text{ are parallel.}");
		}		
		else if (questionVariation == 1){
			theQuestion += "\\text{Plane }\\mathcal{" + planeVariables[0]+ "}\\text{ is perpendicular to line }" + lineVariable + "\\text{ and plane }\\mathcal{" + planeVariables[1] + "}";
			theQuestion += "\\\\\\text{ is perpendicular to line }" + lineVariable + "\\text{. Which statement is correct?}";
			mc = new MultipleChoice(
					"Plane }\\mathcal{" + planeVariables[0]+ "}\\text{ is perpendicular to plane }\\mathcal{" + planeVariables[1] + "}\\text{.}",
					"Plane }\\mathcal{" + planeVariables[0]+ "}\\text{ is parallel to plane }\\mathcal{" + planeVariables[1] + "}\\text{.}",
					"Plane }\\mathcal{" + planeVariables[0]+ "}\\text{ intersects plane }\\mathcal{" + planeVariables[1] + "}\\text{.}",
					"Plane }\\mathcal{" + planeVariables[0]+ "}\\text{ bisects plane }\\mathcal{" + planeVariables[1] + "}\\text{.}",
					"Plane }\\mathcal{" + planeVariables[0]+ "}\\text{ is parallel to plane }\\mathcal{" + planeVariables[1] + "}\\text{.}");
		}
		else {
			theQuestion += "\\text{A support beam between the floor and ceiling of a house forms a 90}^\\circ\\text{ angle with the}";
			theQuestion += "\\\\\\text{floor. The builder wants to make sure that the floor and ceiling are parallel. Which}";
			theQuestion += "\\\\\\text{angle should the support beam form with the ceiling?}";

			mc = new MultipleChoice(
					"45}^\\circ",
					"60}^\\circ",
					"90}^\\circ",
					"180}^\\circ",
					"90}^\\circ");
		}
		theQuestion += "\\\\\\text{    (1) " + mc.getChoiceA();
		theQuestion +="\\\\\\text{    (2) " + mc.getChoiceB();
		theQuestion +="\\\\\\text{    (3) " + mc.getChoiceC();
		theQuestion +="\\\\\\text{    (4) " + mc.getChoiceD();
		theQuestion += "\\end{array}";
		question = theQuestion;
		answer = "\\text{" + mc.getAnswer();

	}
	
}
