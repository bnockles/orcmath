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
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;

public class GraphingGivenDomain extends DynamicType{

	public static int ONLY_QUADRATICS= Graph.QUADRATIC;
	public static int ONLY_LINEAR= Graph.LINEAR;
	public static int ONLY_RADICAL= Graph.RADICAL;
	public static int ONLY_ABSOLUTE_VALUE= Graph.ABSOLUTE_VALUE;
	CoordinateImage image;
	int functionType;
	
	
	public GraphingGivenDomain(){
		instructions = "";
		numberOfColumns=2;
		verticalSpacing=10;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
	}
	
	public GraphingGivenDomain(int difficulty, int functionSelection){
		instructions = "";
		numberOfColumns=2;
		verticalSpacing=10;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		getInstance(difficulty, functionSelection);
	}
	
	public static Graph getRandomGraph(int functionType, double initialY, double domainLow, boolean inclusiveLeft, double domainHigh, boolean inclusiveRight){
		int a = 0;
		int b=0;
		int c=0;
		double denominator=1;
		double lowBound=domainLow;
		double upBound=domainHigh;
		String functionText="";
		if(functionType==Graph.CONSTANT){
			a = (int) initialY;
			lowBound=domainLow;
			upBound=domainHigh;
		}
		else if(functionType==Graph.LINEAR){
			b=Ops.randomInt(-5, 5);			
			a = Ops.randomNotZero(-5, 5);
			denominator=a*domainLow/(initialY-(int)b);
			lowBound=domainLow;
			upBound=domainHigh;
			double lowy=Math.abs(a*lowBound/denominator+b);
			double highy=Math.abs(a*upBound/denominator+b);
//			while(lowy>20 || highy>20){
//				a = Ops.randomNotZero(-5, 5);
//				denominator=Ops.randomNotZero(1, 5);
//				b=Ops.randomInt(-5, 5);
//				lowy=Math.abs(a*lowBound/denominator+b);
//				highy=Math.abs(a*upBound/denominator+b);
//			}
		}
		else if(functionType==Graph.QUADRATIC){
			
			int root1 = Ops.randomInt(-8, 8);
			int root2 = Ops.randomInt(-8, 8);
			if(root1==0){
				root2 = Ops.randomNotZero(-8, 8);
			}

			a = Ops.randomNotZero(-1, 1);
			b = -(root1+root2);
			c = root1*root2-(int)initialY;

			Term[] quadratic={new Term(a, "x^2"),new Term(b, "x"),new Term(c)};
			
			double lowy=Math.abs(a*Math.pow(lowBound,2)+b*lowBound+c);
			double highy=Math.abs(a*Math.pow(upBound,2)+b*upBound+c);
			while(lowy>25 || highy>25){
				root1 = Ops.randomInt(-8, 8);
				root2 = Ops.randomInt(-8, 8);
				if(root1==0){
					root2 = Ops.randomNotZero(-8, 8);
				}

				a = Ops.randomNotZero(-1, 1);
				b = -(root1+root2);
				c = root1*root2-(int)initialY;

				lowy=Math.abs(a*Math.pow(lowBound,2)+b*lowBound+c);
				highy=Math.abs(a*Math.pow(upBound,2)+b*upBound+c);
				
				quadratic[0]=new Term(a, "x^2");
				quadratic[1]=new Term(b, "x");
				quadratic[2]=new Term(c);
			}
			
		}else if(functionType==Graph.RADICAL){
			a=Ops.randomNotZero(-2, 2);
			if(a>0){
				b=(int) (lowBound*(double)(-a));
				c=(int)initialY;
			}else{
				b=(int) (upBound*(double)(-a));
				c=(int)(initialY+Math.sqrt(a*lowBound+b));
			}
		}else if(functionType==Graph.ABSOLUTE_VALUE){
			double difference = upBound-lowBound;
			double vertex = Ops.randomInt((int)lowBound+1, (int)(lowBound+difference));
			a=Ops.randomNotZero(-4, 4);
			if(a>0){
				b=(int) (vertex*(double)(-a));
			}else{
				b=(int) (vertex*(double)(-a));
			}
			c=(int) (initialY-Math.abs(a*domainLow+b));
		}
		
		
		Graph graph=new Graph();
		if(functionType==Graph.CONSTANT){
			double[] coefs = {(double)a};
			graph.setFunction(Graph.CONSTANT,coefs,inclusiveLeft,lowBound, inclusiveRight,upBound);
		}
		else if(functionType==Graph.LINEAR){
			double[] coefs = {(double)a/denominator,(double)b};
			graph.setFunction(Graph.LINEAR,coefs,inclusiveLeft,lowBound, inclusiveRight,upBound);
		}
		else if(functionType==ONLY_QUADRATICS){
			double[] coefs = {(double)a,(double)b,(double)c};
			graph.setFunction(Graph.QUADRATIC,coefs,inclusiveLeft,lowBound, inclusiveRight,upBound);
		}else if(functionType==ONLY_RADICAL){
			double[] coefs = {(double)a,(double)b,(double)c};
			graph.setFunction(Graph.RADICAL,coefs,inclusiveLeft,lowBound, inclusiveRight,upBound);
		}else if(functionType==Graph.ABSOLUTE_VALUE){
			double[] coefs = {(double)a,(double)b,(double)c};
			graph.setFunction(Graph.ABSOLUTE_VALUE,coefs,inclusiveLeft,lowBound, inclusiveRight,upBound);
		}
		return graph;
	}
	
	public static Graph getRandomGraph(int functionType, double domainLow, boolean inclusiveLeft, double domainHigh, boolean inclusiveRight){
		int a = 0;
		int b=0;
		int c=0;
		double denominator=1;
		double lowBound=domainLow;
		double upBound=domainHigh;
		String functionText="";
		if(functionType==Graph.CONSTANT){
			a = Ops.randomNotZero(-7, 7);
			lowBound=domainLow;
			upBound=domainHigh;
		}
		else if(functionType==Graph.LINEAR){
			a = Ops.randomNotZero(-5, 5);
			denominator=Ops.randomNotZero(1, 5);
			b=Ops.randomInt(-5, 5);
			lowBound=domainLow;
			upBound=domainHigh;
			double lowy=Math.abs(a*lowBound/denominator+b);
			double highy=Math.abs(a*upBound/denominator+b);
			while(lowy>20 || highy>20){
				a = Ops.randomNotZero(-5, 5);
				denominator=Ops.randomNotZero(1, 5);
				b=Ops.randomInt(-5, 5);
				lowy=Math.abs(a*lowBound/denominator+b);
				highy=Math.abs(a*upBound/denominator+b);
			}
		}
		else if(functionType==Graph.QUADRATIC){
			
			int root1 = Ops.randomInt(-8, 8);
			int root2 = Ops.randomInt(-8, 8);
			if(root1==0){
				root2 = Ops.randomNotZero(-8, 8);
			}

			a = Ops.randomNotZero(-1, 1);
			b = -(root1+root2);
			c = root1*root2;

			Term[] quadratic={new Term(a, "x^2"),new Term(b, "x"),new Term(c)};
			
			double lowy=Math.abs(a*Math.pow(lowBound,2)+b*lowBound+c);
			double highy=Math.abs(a*Math.pow(upBound,2)+b*upBound+c);
			while(lowy>25 || highy>25){
				root1 = Ops.randomInt(-8, 8);
				root2 = Ops.randomInt(-8, 8);
				if(root1==0){
					root2 = Ops.randomNotZero(-8, 8);
				}

				a = Ops.randomNotZero(-1, 1);
				b = -(root1+root2);
				c = root1*root2;

				lowy=Math.abs(a*Math.pow(lowBound,2)+b*lowBound+c);
				highy=Math.abs(a*Math.pow(upBound,2)+b*upBound+c);
				
				quadratic[0]=new Term(a, "x^2");
				quadratic[1]=new Term(b, "x");
				quadratic[2]=new Term(c);
			}
			
		}else if(functionType==Graph.RADICAL){
			a=Ops.randomNotZero(-2, 2);
			c=Ops.randomInt(-6, 6);
			if(a>0){
				b=(int) (lowBound*(double)(-a));
			}else{
				b=(int) (upBound*(double)(-a));
			}
		}else if(functionType==Graph.ABSOLUTE_VALUE){
			double difference = upBound-lowBound;
			double vertex = Ops.randomInt((int)lowBound+1, (int)(lowBound+difference));
			a=Ops.randomNotZero(-4, 4);
			c=Ops.randomInt(-6, 6);
			if(a>0){
				b=(int) (vertex*(double)(-a));
			}else{
				b=(int) (vertex*(double)(-a));
			}
		}
		
		
		Graph graph=new Graph();
		if(functionType==Graph.CONSTANT){
			double[] coefs = {(double)a};
			graph.setFunction(Graph.CONSTANT,coefs,inclusiveLeft,lowBound, inclusiveRight,upBound);
		}
		else if(functionType==Graph.LINEAR){
			double[] coefs = {(double)a/denominator,(double)b};
			graph.setFunction(Graph.LINEAR,coefs,inclusiveLeft,lowBound, inclusiveRight,upBound);
		}
		else if(functionType==ONLY_QUADRATICS){
			double[] coefs = {(double)a,(double)b,(double)c};
			graph.setFunction(Graph.QUADRATIC,coefs,inclusiveLeft,lowBound, inclusiveRight,upBound);
		}else if(functionType==ONLY_RADICAL){
			double[] coefs = {(double)a,(double)b,(double)c};
			graph.setFunction(Graph.RADICAL,coefs,inclusiveLeft,lowBound, inclusiveRight,upBound);
		}else if(functionType==Graph.ABSOLUTE_VALUE){
			double[] coefs = {(double)a,(double)b,(double)c};
			graph.setFunction(Graph.ABSOLUTE_VALUE,coefs,inclusiveLeft,lowBound, inclusiveRight,upBound);
		}
		return graph;
	}
	
	public static Graph getRandomGraph(int functionType, boolean randomRestrictionOnDomain){
		int a = 0;
		int b=0;
		int c=0;
		int d=0;
		double denominator=1;
		double lowBound=0;
		double upBound=1;
		String functionText="";
		if(functionType==Graph.CONSTANT){
			a = Ops.randomNotZero(-7, 7);
			lowBound=Ops.randomInt(-10, 5);
			upBound=lowBound+Ops.randomNotZero(1, 5);
		}
		else if(functionType==Graph.LINEAR){
			a = Ops.randomNotZero(-5, 5);
			denominator=Ops.randomNotZero(1, 5);
			b=Ops.randomInt(-5, 5);
			lowBound=Ops.randomInt((int)((-10-b)*denominator/a), (int)((5-b)*denominator/a));
			upBound=lowBound+Ops.randomNotZero(1, (int)((10-b)*denominator/a));
		}
		else if(functionType==Graph.QUADRATIC){
			
			int root1 = Ops.randomInt(-8, 8);
			int root2 = Ops.randomInt(-8, 8);
			if(root1==0){
				root2 = Ops.randomNotZero(-8, 8);
			}

			a = Ops.randomNotZero(-1, 1);
			b = -(root1+root2);
			c = root1*root2;

			Term[] quadratic;
			if (b!=0&&c!=0) {
				quadratic= new Term[3];
				quadratic[0] = new Term(a, "x^2");		
				quadratic[1] = new Term(b, "x");
				quadratic[2] = new Term(c);
			}
			else {
				quadratic= new Term[2];
				quadratic[0] = new Term(a, "x^2");
				if(b==0){
					quadratic[1] = new Term(c);
				}else{
					quadratic[1] = new Term(b,"x");
				}
			}
			if(Math.random()<.5){
				lowBound=root1-Ops.randomInt(2, 4);
				upBound=root1+Ops.randomInt(1, 4);
			}else{
				lowBound=root2-Ops.randomInt(2, 4);
				upBound=root2+Ops.randomInt(1, 4);
			}
		}else if(functionType==Graph.CUBIC){
			
			int root1 = Ops.randomInt(-4, 4);
			int root2 = Ops.randomInt(-4, 4);
			int root3 = Ops.randomInt(-4, 4);
			if(root1==0){
				root2 = Ops.randomNotZero(-4, 4);
			}

			a = Ops.randomNotZero(-1, 1);
			b = -(root1+root2+root3);
			c = root1*root2+root1*root3+root2*root3;
			d = root1*root2*root3;

			Term[] cubic= new Term[4];
			cubic[0] = new Term(a, "x^3");		
			cubic[1] = new Term(b, "x^2");
			cubic[2] = new Term(c,"x");
			cubic[3] = new Term(d);

			if(Math.random()<.5){
				lowBound=root1-Ops.randomInt(2, 4);
				upBound=root1+Ops.randomInt(1, 4);
			}else{
				lowBound=root2-Ops.randomInt(2, 4);
				upBound=root2+Ops.randomInt(1, 4);
			}
		}else if(functionType==Graph.RADICAL){
			a=Ops.randomNotZero(-2, 2);
			b=Ops.randomInt(-8, 8);
			c=Ops.randomInt(-6, 6);
			
			if(a>0){
				lowBound=(double)(-b)/(double)(a);
				upBound=(int)lowBound+Ops.randomInt(6, 12);
			}else{
				upBound=(double)(-b)/(double)(a);
				lowBound=(int)upBound-Ops.randomInt(6, 12);
			}
		}else if(functionType==Graph.ABSOLUTE_VALUE){
			a=Ops.randomNotZero(-4, 4);
			b=Ops.randomNotZero(-5, 5);
			c=Ops.randomInt(-6, 6);
			double vertex = -b/a;
			lowBound=vertex-Ops.randomInt(1, 5);
			upBound=vertex+Ops.randomInt(1, 5);
		}
		
		boolean inclusiveLeft=false;
		if(Math.random()<.5){
			inclusiveLeft=true;
		}
		boolean inclusiveRight=false;
		if(Math.random()<.5){
			inclusiveRight=true;
		}
		
		Graph graph=new Graph();
		double[] coefs = null;
		if(functionType==Graph.CONSTANT){
			coefs = new double[1];
			coefs[0]=(double)a;	
		}
		else if(functionType==Graph.LINEAR){
			coefs = new double[2];
			coefs[0]=(double)a/denominator;	
			coefs[1]=(double)b;
		}
		else if(functionType==ONLY_QUADRATICS || functionType==ONLY_RADICAL || functionType==Graph.ABSOLUTE_VALUE){
			coefs = new double[3];
			coefs[0]=(double)a;	
			coefs[1]=(double)b;
			coefs[2]=(double)c;
		}else if(functionType==Graph.CUBIC){
			coefs = new double[4];
			coefs[0]=(double)a;	
			coefs[1]=(double)b;
			coefs[2]=(double)c;
			coefs[3]=(double)d;
		}
		if(randomRestrictionOnDomain){
			graph.setFunction(functionType,coefs,inclusiveLeft,lowBound, inclusiveRight,upBound);
		}else{
			graph.setFunction(functionType,coefs);
		}
		return graph;
	}
	
	
	
	public void getInstance(int diff, int functionSelection){
		//determines which type of function to create
		
		if(functionSelection==ONLY_QUADRATICS){
			functionType=ONLY_QUADRATICS;
		}else if(functionSelection==ONLY_RADICAL){
			functionType=ONLY_RADICAL;
			}
		else if(functionSelection==ONLY_ABSOLUTE_VALUE){
			functionType=Graph.ABSOLUTE_VALUE;
			}
		
		
		//functionTypeMethod
		
		
		WorkTable answerWork = new WorkTable();
		


		Graph graph = getRandomGraph(functionType, true);
		
		
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
		
		
		String interval=lowAsString+leftSign+" x"+rightSign+" "+highAsString;
		String latexTabular="\\begin{tabular}{rcl}";
		latexTabular+="f(x) & = & "+graph.getFunctionText()+",\\hspace{5pt}"+interval+"\\\\";

		latexTabular+="\\\\\\end{tabular}";
		int max = 10;
		image= new CoordinateImage(650, 650, graph.getWindowLeft(), graph.getWindowRight(), graph.getWindowDown(),graph.getWindowUp());
//		image = new CoordinateImage(550, 550, -max, max, -max, max);
		int xScale=(int)(graph.getWindowRight()/10);
		if(xScale<(int)(Math.abs(graph.getWindowLeft()/10)))xScale=Math.abs((int)(graph.getWindowLeft()/10));
		int yScale=(int)(graph.getWindowUp()/10);
		if(yScale<(int)(Math.abs(graph.getWindowDown()/10)))yScale=Math.abs((int)(graph.getWindowDown()/10+1));
		image.addAxes(xScale, yScale, true);
		image.addGrid(xScale, yScale);
		dynamicImage=image.getImage();
		
		question = latexTabular;


		
		answerWork.addGraph(graph, answerImages);
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	
	
}
