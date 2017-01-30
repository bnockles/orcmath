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

public class LocusSolution extends Type{
	
	private String graphic1Address = "PDFs/Graphics/TypeSpecific/Locus/twotrees.png";
	
	static int lastSelection;
	static int selectionBeforeLast1;
	static int selectionBeforeLast2;
	static int selectionBeforeLast3;
	static int selectionBeforeLast4;
	static int lastSolutionToTom;
	
	public LocusSolution(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=20;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.30;
	}
	
	public LocusSolution(int difficulty){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=20;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.30;
		getInstance(difficulty);
		//question and answer is determined in the getInstance constructor
	}
	
	public void getInstance(int difficulty){
		int itemNumber = Ops.randomInt(1, 6);
		while (itemNumber == lastSelection || 
				itemNumber == selectionBeforeLast1 ||
				itemNumber == selectionBeforeLast2 ||
				itemNumber == selectionBeforeLast3 //||
				//itemNumber == selectionBeforeLast4 
				 ) itemNumber = Ops.randomInt(1, 5);
		
		if (itemNumber == 1) get1Instance();
		if (itemNumber == 2) get2Instance();
		if (itemNumber == 3) get3Instance();
		if (itemNumber == 4) get4Instance();
		if (itemNumber == 5) get5Instance();
		if (itemNumber == 6) get6Instance();

		selectionBeforeLast4 = selectionBeforeLast3;
		selectionBeforeLast3 = selectionBeforeLast2;
		selectionBeforeLast2 = selectionBeforeLast1;
		selectionBeforeLast1 = lastSelection;
		lastSelection = itemNumber;
	}
	
	public void get1Instance(){
		String theQuestion = "\\begin{array}{l}";

		int distanceBetweenTowns = Ops.randomInt(10, 30);
		int distanceFromTown;
		
		int numberOfSolutions;
		
		double selector = Math.random();
		if (selector<=.6){
			numberOfSolutions=2;
			distanceFromTown = distanceBetweenTowns/2 + Ops.randomInt(1, distanceBetweenTowns/2);
		}
		else if (selector>.6 && selector<=.8){
			numberOfSolutions=1;
			distanceBetweenTowns = 2*((int)(distanceBetweenTowns/2));
			distanceFromTown = (int)(distanceBetweenTowns/2);	
		}
		else {
			numberOfSolutions=0;
			distanceFromTown=Ops.randomInt(5, (int)(distanceBetweenTowns/2-1));
		}
		
		char[] points = Variable.randCapVars(2);
		
		double textVersion = Math.random();
		if (textVersion<.5){
			System.out.println("Selected 1");
			theQuestion += "\\text{Towns A and B are " + distanceBetweenTowns + " miles apart. How many points are equidistant}";
			theQuestion += "\\\\\\text{from both towns and " + distanceFromTown + " miles from Town A?}";
		}
		else{
			System.out.println("Selected 2");
			theQuestion += "\\text{The distance between points }" + points[0] + "\\text{ and }" + points[1] + "\\text{ is }" + distanceBetweenTowns + "\\text{ units. How many points }";
			theQuestion += "\\\\\\text{are equidistant from }"  + points[0] + "\\text{ and }" + points[1]+ "\\text{ and also }" + distanceFromTown + "\\text{ units from}";
			theQuestion += "\\\\\\text{point }" + points[Ops.randomInt(0, 1)] + "\\text{?}";
		}
		theQuestion += "\\\\\\text{    (1) 1}";
		theQuestion +="\\\\\\text{    (2) 2}";
		theQuestion +="\\\\\\text{    (3) 0}";
		theQuestion +="\\\\\\text{    (4) 4}";
		theQuestion += "\\end{array}";
		
		question = theQuestion;
		answer = "\\text{The number of points is " + numberOfSolutions + ".}";
	}
	
	public void get2Instance(){
		String theQuestion = "\\begin{array}{l}";

		int distanceFromIntersection = 10*Ops.randomInt(5, 30);
		
		int numberOfSolutions = 4;
		
		double selector = Math.random();
		
		if (selector<.5){
			System.out.println("Selected 3");
			theQuestion += "\\text{What is the total number of points equidistant from two intersecting, straight }";
			theQuestion += "\\\\\\text{roads and also " + distanceFromIntersection + " feet from the traffic light at the center of the intersection?}";
		}
		else {
			System.out.println("Selected 4");
			theQuestion += "\\text{What is the total number of points equidistant from two intersecting}";
			theQuestion += "\\\\\\text{lines and also " + distanceFromIntersection + " units from the intersection of both lines?}";	
		}
		
		theQuestion += "\\\\\\text{    (1) 1}";
		theQuestion +="\\\\\\text{    (2) 2}";
		theQuestion +="\\\\\\text{    (3) 3}";
		theQuestion +="\\\\\\text{    (4) 4}";
		theQuestion += "\\end{array}";
		
		question = theQuestion;
		answer = "\\text{The number of points is " + numberOfSolutions + ".}";
	}
	
	public void get3Instance(){
		String theQuestion = "\\begin{array}{l}";

		int distanceBetweenLines = Ops.randomInt(3, 12);
		int distanceFromPoint;
		
		int numberOfSolutions;
		
		double selector = Math.random();
		if (selector<=.5){
			numberOfSolutions=2;
			distanceFromPoint = distanceBetweenLines/2 + Ops.randomInt(1, distanceBetweenLines/2);
		}
		else if (selector>.5 && selector<=.8){
			numberOfSolutions=1;
			distanceBetweenLines = 2*((int)(distanceBetweenLines/2));
			distanceFromPoint = (int)(distanceBetweenLines/2);	
		}
		else {
			numberOfSolutions=0;
			distanceFromPoint=Ops.randomInt(5, (int)(distanceBetweenLines/2-1));
		}
		
		char[] lines = Variable.randVars(2);
		char point = Variable.randCapVar();
		
		double textVersion = Math.random();
		if (textVersion<.5){
			System.out.println("Selected 5");
			theQuestion += "\\text{The distance between parallel lines }" + lines[0] + "\\text{ and }" + lines[1] + "\\text{ is  " + distanceBetweenLines + " units. Point }" + point;  
			theQuestion += "\\\\\\text{ is on line }" + lines[Ops.randomInt(0, 1)]+"\\text{. How many points are equidistant from lines }" + lines[0] + "\\text{ and }" + lines[1];
			theQuestion += "\\\\\\text{and " + distanceFromPoint + " units from point }" + point + "\\text{?}";
		}
		else{
			System.out.println("Selected 6");
			theQuestion += "\\text{Two pipes run parallel underground. The pipes are " + distanceBetweenLines+" feet apart. How}";
			theQuestion += "\\\\\\text{many points are equidistant from both pipes but also " + distanceFromPoint + " feet from a }";
			theQuestion += "\\\\\\text{sprinkler head that is found on one of the pipes?}";
		}
		theQuestion += "\\\\\\text{    (1) 1}";
		theQuestion +="\\\\\\text{    (2) 2}";
		theQuestion +="\\\\\\text{    (3) 0}";
		theQuestion +="\\\\\\text{    (4) 4}";
		theQuestion += "\\end{array}";
		
		question = theQuestion;
		answer = "\\text{The number of points is " + numberOfSolutions + ".}";
	}
	
	public void get4Instance(){
		String theQuestion = "\\begin{array}{l}";

		int distanceFromLine = Ops.randomInt(5, 20);//how far away the tree is from the pipe
		int distanceFromPoint;//given distance from tree
		int distanceBetweenPointAndLine;//given distance from line
		
		
		int numberOfSolutions;
		
		double selector = Math.random();
		if (selector<=.5 && lastSolutionToTom!=2){
			numberOfSolutions=2;
			lastSolutionToTom=2;
			distanceFromPoint = Ops.randomInt(2, distanceFromLine-2);
			distanceBetweenPointAndLine = Ops.randomInt(distanceFromLine-distanceFromPoint+1, distanceFromLine+distanceFromPoint-1);
		}
		else if ((selector>.5 && selector<=.8) && lastSolutionToTom!=1){
			numberOfSolutions=1;
			lastSolutionToTom=1;
			distanceFromPoint = Ops.randomInt(2, distanceFromLine-2);
			if (Math.random()>.5){
				distanceBetweenPointAndLine = distanceFromLine-distanceFromPoint;
			}
			else{
				distanceBetweenPointAndLine = distanceFromLine+distanceFromPoint;
			}
		}
		else {
			numberOfSolutions=0;
			lastSolutionToTom=0;
			distanceFromPoint = Ops.randomInt(2, distanceFromLine-2);
			distanceBetweenPointAndLine = Ops.randomInt(1, distanceFromLine-distanceFromPoint);
		}
		
		char line = Variable.randVar();
		char point = Variable.randCapVar();
		
		double textVersion = Math.random();
		if (textVersion<.5){
			if (numberOfSolutions==0 && lastSolutionToTom!=2){
				numberOfSolutions=2;
				lastSolutionToTom=2;
				distanceFromPoint = Ops.randomInt(2, distanceFromLine-2);
				distanceBetweenPointAndLine = Ops.randomInt(distanceFromLine-distanceFromPoint+1, distanceFromLine+distanceFromPoint-1);
			}
			else if (numberOfSolutions==0){
				numberOfSolutions=1;
				lastSolutionToTom=1;
				distanceFromPoint = Ops.randomInt(2, distanceFromLine-2);
				if (Math.random()>.5){
					distanceBetweenPointAndLine = distanceFromLine-distanceFromPoint;
				}
				else{
					distanceBetweenPointAndLine = distanceFromLine+distanceFromPoint;
				}
			}
			System.out.println("Selected 7");
			theQuestion += "\\text{Tom wants to install a lampost in his yard. Before digging a hole }";
			theQuestion += "\\\\\\text{for the post, he considers the locations of other objects. A sprinkler}";
			theQuestion += "\\\\\\text{pipe runs underground in a straight line. Also, a tree is growing " + distanceFromLine+"\\text{}";
			theQuestion += "\\\\\\text{feet from the pipe. How many locations are " + distanceFromPoint + " feet from the tree and " + distanceBetweenPointAndLine+"\\text{}";
			theQuestion += "\\\\\\text{feet from the sprinkler?}";
		}
		else{
			theQuestion += "\\text{Point }" + point +"\\text{ is " + distanceFromLine + " units from line }" + line + "\\text{. How many points are " + distanceFromPoint+"\\text{}";
			theQuestion += "\\\\\\text{units from point }" + point + " and " + distanceBetweenPointAndLine + " units from line }" + line + "\\text{?}";
		}
		theQuestion += "\\\\\\text{    (1) 1}";
		theQuestion +="\\\\\\text{    (2) 2}";
		theQuestion +="\\\\\\text{    (3) 0}";
		theQuestion +="\\\\\\text{    (4) 4}";
		theQuestion += "\\end{array}";
		
		question = theQuestion;
		answer = "\\text{The number of points is " + numberOfSolutions + ".}";
	}
	
	public void get5Instance(){
		String theQuestion = "\\begin{array}{l}";


		int numberOfSolutions;

		numberOfSolutions = 1;
		
		double textVersion = Math.random();
		if (textVersion<.5){
		theQuestion += "\\text{How many points are equidistant from two parallel lines and also equidistant from}";
		theQuestion += "\\\\\\text{two points on one of the lines?}";		
		}
		else{
			char[] lines = Variable.randVars(2);
			char[] points = Variable.randCapVars(2);
			
			theQuestion += "\\text{Lines }" + lines[0] + "\\text{ and }" + lines[1] + "\\text{ are parallel. Points }" + points[0] + "\\text{ and }" + points[1] + "\\text{ are on line }"; 
			theQuestion += "\\\\" + lines[Ops.randomInt(0, 1)] + "\\text{. How many points are equidistant from }"+ lines[0] + "\\text{ and }" + lines[1];
			theQuestion += "\\\\\\text{ and also equidistant from points }"+ points[0] + "\\text{ and }" + points[1] + "\\text{?}";
		}

		theQuestion += "\\\\\\text{    (1) 1}";
		theQuestion +="\\\\\\text{    (2) 2}";
		theQuestion +="\\\\\\text{    (3) an infinite number}";
		theQuestion +="\\\\\\text{    (4) 4}";
		theQuestion += "\\end{array}";
		
		question = theQuestion;
		answer = "\\text{The number of points is " + numberOfSolutions + ".}";
	}
	
	public void get6Instance(){
		String theQuestion = "\\begin{array}{l}";

		int distanceBetweenTrees = Ops.randomInt(5, 20);//distance between the trees	
		int distanceFromFence = Ops.randomInt(1, distanceBetweenTrees);
		int numberOfSolutions = 2;

		char[] points = Variable.randCapVars(2);
		
		double textVersion = Math.random();
		if (textVersion<.5){
			
			theQuestion += "\\text{Alyssa has a treasure map, represented in the accompanying diagram,}";
			theQuestion += "\\\\\\text{that shows two trees " + distanceBetweenTrees + " feet apart and a straight fence connecting them. }";
			theQuestion += "\\\\\\text{The map states that the treasure is buried " + distanceFromFence + " from the fence and}"; 
			theQuestion += "\\\\\\text{equidistant from the two trees.}";
			theQuestion += "\\\\\\text{                                      }\\includegraphics[width=20cm,interpolation=bicubic]{"+graphic1Address+"}";
			theQuestion += "\\\\\\text{How many possible location could the treasure be found?}";
		}
		else{
			theQuestion += "\\text{Points }" + points[0] +"\\text{ and }" + points[1] + "\\text{ are connected by }\\overline{" + points[0]+ points[1] + "}\\text{. The length of }\\overline{" + points[0]+ points[1] + "}\\text{ is }" + distanceBetweenTrees + "\\text{ units. }";
			theQuestion += "\\\\\\text{How many points are }" + distanceFromFence + "{ units from }\\overline{" + points[0]+ points[1] + "}\\text{ and equidistant from points }" + points[0] +"\\text{ and }" + points[1] +"\\text{?}";
		}
		theQuestion += "\\\\\\text{    (1) 1}";
		theQuestion +="\\\\\\text{    (2) 2}";
		theQuestion +="\\\\\\text{    (3) 0}";
		theQuestion +="\\\\\\text{    (4) 4}";
		theQuestion += "\\end{array}";
		
		question = theQuestion;
		answer = "\\text{The number of points is " + numberOfSolutions + ".}";
	
	}

}
