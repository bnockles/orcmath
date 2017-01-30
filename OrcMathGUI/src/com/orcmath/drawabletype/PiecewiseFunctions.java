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
import java.util.Collections;
import java.util.Comparator;

import com.lessonflow.lesson.Lesson;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.Graph;
import com.orcmath.drawable.RemoveableDiscontinuity;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;

public class PiecewiseFunctions extends DynamicType{
	CoordinateImage image;
	
	//variables involved in type of worksheet
	boolean identifyContinuity;
	boolean identifyRemoveableContinuity;
	boolean allowTrigFunctions;//allows for sine, cosine and tangent functions in radian mode
	boolean allowCalculusFunctions;//allows for functions that would not be seen in normal scope of Algebra 2

	
	//variables used in determining question text 
	int functionType;
	int numberOfGraphs;
	boolean continuous;
	boolean alreadyContainsDomainRestrictedFunction;//will not edit domain of a radical function, etc.
	int indexOfDomainRestrictedFunction;
	boolean lastFunctionTypeConstant;
	
	public PiecewiseFunctions(){
		instructions = "";
		numberOfColumns=2;
		verticalSpacing=10;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
	}
	
	public PiecewiseFunctions(int difficulty, boolean identifyRemoveableContinuity, boolean includeGrid){
		instructions = "";
		numberOfColumns=2;
		verticalSpacing=10;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		lastFunctionTypeConstant=false;
		this.identifyRemoveableContinuity=identifyRemoveableContinuity;
		getRemoveableDiscontinuityInstance(difficulty, includeGrid);
	}
	
	public PiecewiseFunctions(int difficulty, boolean identifyContinuity, boolean allowTrigFunctions, boolean allowCalculusFunctions, boolean includeGrid){
		instructions = "";
		numberOfColumns=2;
		verticalSpacing=10;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		lastFunctionTypeConstant=false;
		this.identifyContinuity=identifyContinuity;
		this.allowCalculusFunctions=allowCalculusFunctions;
		getInstance(difficulty,includeGrid);
	}
	
	
	
	public void getRemoveableDiscontinuityInstance(int diff, boolean includeGrid){

		
		RemoveableDiscontinuity graph = Graph.getRandomSingleRemoveableDiscontinuity(false);
	
		if(graph.getLimitAtPointOfDiscontinuity()>graph.getWindowUp())graph.setWindowUp(graph.getLimitAtPointOfDiscontinuity()+2);
		if(graph.getLimitAtPointOfDiscontinuity()<graph.getWindowDown())graph.setWindowDown(graph.getLimitAtPointOfDiscontinuity()-2);
		
		

		
		
		//declare graph
		
		String leftSign="<";
		if(graph.isLowInclusive()){
			leftSign="\\leq";
		}
		String rightSign="<";
		if(graph.isHighInclusive()){
			rightSign="\\leq";
		}
		
		
		String lowAsString="";
		try{
			lowAsString=Format.doubleToString((Double) graph.getDomainLow());
		}catch(Exception e){
			lowAsString="-\\infty";
		}
		String highAsString="";
		try{
			highAsString=Format.doubleToString((Double) graph.getDomainHigh());
		}catch(Exception e){
			highAsString="\\infty";
		}
		
		String piecewiseText="\\left\\{\\begin{array}{cl}";
		String interval1="x\\neq"+Format.doubleToString(graph.getDiscontinuityX());
//		String interval1=lowAsString+leftSign+" x< "+Format.doubleToString(graph.getDiscontinuityX());
		String interval2="x="+Format.doubleToString(graph.getDiscontinuityX());
//		String interval3=Format.doubleToString(graph.getDiscontinuityX())+"< x"+rightSign+" "+highAsString;
		piecewiseText+=graph.getFunctionText()+"& : "+interval1+"\\\\";
		piecewiseText+="k & : "+interval2+"\\\\";
//		piecewiseText+=graph.getFunctionText()+"& : "+interval3;
		piecewiseText+="\\end{array}\\right.";
		
		String latexTabular="\\begin{tabular}{rcl}";
		String line=Problem.getLatexLines("Find the value of #k# such that the function #f(x)# described below is continuous.", "#", QUESTION_WIDTH, "text");
		latexTabular+="\\multicolumn{3}{l}{"+line+"}\\\\";;
		latexTabular+="f(x) & = & "+piecewiseText+"\\\\";
		latexTabular+="\\\\\\end{tabular}";

		double windowLeft=graph.getWindowLeft();
		double windowRight=graph.getWindowRight();
		double windowDown=graph.getWindowDown();
		double windowUp=graph.getWindowUp();
		if(!includeGrid){
			image= new CoordinateImage(550, 550, windowLeft, windowRight, windowDown,windowUp);
			//		image = new CoordinateImage(550, 550, -max, max, -max, max);
			int xScale=(int)(windowRight/10);
			if(xScale<(int)(Math.abs(windowLeft/10)))xScale=Math.abs((int)(windowLeft/10));
			int yScale=(int)(windowUp/10);
			if(yScale<(int)(Math.abs(windowDown/10)))yScale=Math.abs((int)(windowDown/10));
			image.addAxes(xScale, yScale, true);
			image.addGrid(xScale, yScale);
			dynamicImage=image.getImage();
		}
		
		question =latexTabular;


		WorkTable answerWork = new WorkTable();
		answerWork.addGraph(graph, answerImages);
		answerWork.getCoordinatePlane().drawOpenPoint(new CoordinatePoint(graph.getDiscontinuityX(), graph.getLimitAtPointOfDiscontinuity()));
		String answerText="At"+Format.doubleToString(graph.getDiscontinuityX())+", the value of k must be "+Format.doubleToString(graph.getLimitAtPointOfDiscontinuity());
		answerWork.newTextLine(answerText);
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	
	
	public void getInstance(int diff,boolean includeGrid){
		if(identifyContinuity || diff==1||diff==2){
			numberOfGraphs=2;
		}
		else if(diff==3||diff==4){
			numberOfGraphs=3;
		}
		int[] splits =new  int[numberOfGraphs+1];
		splits[0]=-10;
		splits[numberOfGraphs]=10;
		if(numberOfGraphs==2)splits[1]=Ops.randomNotZero(-5, 5);
		else{
			splits[1]=Ops.randomNotZero(-8, 0);
			splits[2]=Ops.randomNotZero(1, 8);
		}
		
		ArrayList<Graph> graphs = new ArrayList<Graph>();
		for(int graphIndex=0; graphIndex<numberOfGraphs;graphIndex++){
			double random =Math.random(); 
			
			if(random<.2 && !lastFunctionTypeConstant){
				functionType=Graph.CONSTANT;
				lastFunctionTypeConstant=true;
			}
			else{
				lastFunctionTypeConstant=false;
				if(random<.45)functionType=Graph.LINEAR;
				else if(random<.60)functionType=Graph.QUADRATIC;
				else if(random<.75)functionType=Graph.ABSOLUTE_VALUE;
				else if(!alreadyContainsDomainRestrictedFunction){
					functionType=Graph.RADICAL;	
					indexOfDomainRestrictedFunction=graphIndex;
					alreadyContainsDomainRestrictedFunction=true;
				}
				else{
					functionType=Graph.LINEAR;	
				}
			}
			if(graphIndex>0 && identifyContinuity){
				double lastEndpoint = graphs.get(graphIndex-1).getRightEndpoint().getyCoordinate();
				if(Math.random()<.75 && lastEndpoint-(int)lastEndpoint==0){	
					Graph graph = GraphingGivenDomain.getRandomGraph(functionType,lastEndpoint, splits[graphIndex],true,splits[graphIndex+1],false);
					continuous=true;
					graphs.add(graph);
				}else{
					Graph graph = GraphingGivenDomain.getRandomGraph(functionType,(int)(lastEndpoint)+Ops.randomInt(-3, 3), splits[graphIndex],true,splits[graphIndex+1],false);
					continuous=false;
					graphs.add(graph);
				}
			}else{
				Graph graph = GraphingGivenDomain.getRandomGraph(functionType,splits[graphIndex],true,splits[graphIndex+1],false);
				graphs.add(graph);
			}
		}

	
	double windowLeft=-10;
	double windowRight=10;
	double windowDown=-10;
	double windowUp=10;
	String piecewiseText="\\left\\{\\begin{array}{lr}";
	for(Graph g:graphs){
		if (g.getWindowLeft()<windowLeft)windowLeft=g.getWindowLeft();
		if (g.getWindowRight()>windowRight)windowRight=g.getWindowRight();
		if (g.getWindowDown()<windowDown)windowDown=g.getWindowDown();
		if (g.getWindowUp()>windowUp)windowUp=g.getWindowUp();
		String leftSign="<";
		if(g.isLowInclusive()){
			leftSign="\\leq";
		}
		String rightSign="<";
		if(g.isHighInclusive()){
			rightSign="\\leq";
		}
		String lowAsString="";
		try{
			lowAsString=Format.doubleToString((Double) g.getDomainLow());
		}catch(Exception e){
			lowAsString="-\\infty";
		}
		String highAsString="";
		try{
			highAsString=Format.doubleToString((Double) g.getDomainHigh());
		}catch(Exception e){
			highAsString="\\infty";
		}
		
		
		String interval=lowAsString+leftSign+" x"+rightSign+" "+highAsString;
		piecewiseText+=g.getFunctionText()+"& : "+interval;
		if(graphs.indexOf(g)!=graphs.size()-1)piecewiseText+="\\\\";
	}
	piecewiseText+="\\end{array}\\right.";
	
	
		
		
		String latexTabular="\\begin{tabular}{rcl}";
		if(identifyContinuity){
			String questionText="";
			if(includeGrid){
				questionText+="Graph the function, then i";
			}else{
				questionText+="I";
			}
			questionText+="dentify whether or not the function #f(x)# is continuous at #x="+splits[1]+"#";
			String line=Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
			latexTabular+="\\multicolumn{3}{l}{"+line+"}\\\\";;
		}
		latexTabular+="f(x) & = & "+piecewiseText+"\\\\";

		latexTabular+="\\\\\\end{tabular}";
		int max = 10;
		if(includeGrid){
			image= new CoordinateImage(650, 650, windowLeft, windowRight, windowDown,windowUp);
			//		image = new CoordinateImage(550, 550, -max, max, -max, max);
			int xScale=(int)(windowRight/10);
			if(xScale<(int)(Math.abs(windowLeft/10)))xScale=Math.abs((int)(windowLeft/10));
			int yScale=(int)(windowUp/10);
			if(yScale<(int)(Math.abs(windowDown/10)))yScale=Math.abs((int)(windowDown/10));
			image.addAxes(xScale, yScale, true);
			image.addGrid(xScale, yScale);
			dynamicImage=image.getImage();
		}
		
		question =latexTabular;


		WorkTable answerWork = new WorkTable();
		answerWork.addGraphs(graphs, windowLeft,windowRight,windowDown,windowUp,answerImages);
		if(identifyContinuity){
			String answerText="The function is ";
			if(continuous)answerText+="continuous.";
			else answerText+="not continuous.";
			answerWork.newTextLine(answerText);
		}
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
}
