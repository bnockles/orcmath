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

import java.util.ArrayList;

import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.Graph;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;

public class IdentifyRelativeMaxMin extends DynamicType{
	public static int ONLY_QUADRATICS= Graph.QUADRATIC;
	public static int ONLY_LINEAR= Graph.LINEAR;
	public static int ONLY_RADICAL= Graph.RADICAL;
	public static int ONLY_ABSOLUTE_VALUE= Graph.ABSOLUTE_VALUE;
	CoordinateImage image;
	int functionType;
	private static int lastChosen;
	
	
	public IdentifyRelativeMaxMin(){
		instructions = "";
		numberOfColumns=2;
		verticalSpacing=10;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
	}
	
	public IdentifyRelativeMaxMin(int difficulty){
		instructions = "Identify any relative maximums or minimums of the following function.";
		numberOfColumns=2;
		verticalSpacing=10;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		getInstance(difficulty);
	}
	
	
	
	
	public void getInstance(int diff){
		//determines which type of function to create
		
		double random=Math.random();
		if(random<.40 && lastChosen!=Graph.QUADRATIC){
			functionType=Graph.QUADRATIC;
			lastChosen=Graph.QUADRATIC;
		}
		else if(random<.80  && lastChosen!=Graph.CUBIC) {
			functionType=Graph.CUBIC;
			lastChosen=Graph.CUBIC;
		}else {
			functionType=Graph.ABSOLUTE_VALUE;
			lastChosen=Graph.ABSOLUTE_VALUE;
		}

		
		//functionTypeMethod
		
		
		


		Graph graph = GraphingGivenDomain.getRandomGraph(functionType, false);
		
		
		//declare graph
		
		
		String latexTabular="\\begin{tabular}{rcl}";
		latexTabular+="f(x) & = & "+graph.getFunctionText()+"\\\\";
		latexTabular+="\\\\\\end{tabular}";
		question = latexTabular;


		
		WorkTable answerWork = new WorkTable();
		answerWork.addGraph(graph, answerImages);
		String answerText = "There is a ";
		ArrayList<CoordinatePoint> points= graph.getRelativeMaxMins();
		for(int index=0; index<points.size(); index++){
			CoordinatePoint p=points.get(index);
			answerText+=p.getDescription()+" at "+p;
			if(index<points.size()-2){
				answerText+=", ";
			}else if(index<points.size()-1){
				answerText+=", and ";
			}
		}
		answerText+=".";
		answerWork.newTextLine(answerText);
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
}
