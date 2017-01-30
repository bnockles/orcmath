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
package com.orcmath.drawable;

import java.util.ArrayList;

import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;

public class Graph{
	
	
	double graphResolution = 10; //number of points plotted within width of one unit
	double windowLeft;
	double windowRight;
	double windowDown;
	double windowUp;
	
	ArrayList<CoordinatePoint> solutionSet;
	double rangeLow;
	double rangeHigh;
	CoordinatePoint leftEndpoint;
	CoordinatePoint rightEndpoint;
	double domainLow;
	double domainHigh;
	boolean restrictedLow=false;
	boolean restrictedHigh=false;
	boolean lowInclusive;
	boolean highInclusive;
	
	
	int functionType;
	boolean radianMode=false;
	
	
	//when you add a new function type, updat evaluateAt, findRelativeMinMax and getFunctionText()
	public static int CONSTANT=0;//f(x) = a
	public static int LINEAR=1;//f(x) = ax + b
	public static int QUADRATIC=2;//ax^2+bx+c
	public static int CUBIC=3;//ax^3+bx^2+cx+d
	public static int QUARTIC=4;//ax^4+bx^3+cx^2+dx+e
	public static int RADICAL = 10;//sqrt(ax+b)+c
	public static int ABSOLUTE_VALUE = 11;
	public static int BINOMIAL = 12;//(ax+b)^c DID NOT SET METHOD FOR FINDING RELATIVE MINS
	//abs(ax+b)+c//NOTE: Not defined for absolute value functions with coefficient in front!
	//when this is updated, don't forget to change maxMin method
	public static int SINE = 20;//sin(ax+b)+c
	public static int COSINE = 21;//cos(ax+b)+c
	public static int TANGENT = 22;//tan(ax+b)+c
	public static int COSECANT = 23;//csc(ax+b)+c
	public static int SECANT = 24;//sec(ax+b)+c
	public static int COTANGENT = 25;//cot(ax+b)+c
	public static int REMOVEABLE_DISCONTINUITY = 30;//[(ax+b)(cx+d)]/[(cx+d)]
	double a;
	double b;
	double c;
	double d;
	double e;
	
	public Graph(){
		windowLeft=-10.0;
		windowRight=10.0;
		windowDown=-10.0;
		windowUp=10.0;
		
	}

	protected double setValue(double[] doubles, int index){
		try{
			return doubles[index];
		}catch(IndexOutOfBoundsException e){
			return 0.0;
		}
	}
	
	public static RemoveableDiscontinuity getRandomSingleRemoveableDiscontinuity(boolean aEquals1){
		RemoveableDiscontinuity g = new RemoveableDiscontinuity();
		double a=1;
		double c=1;
		if(!aEquals1){
			a=Ops.randomNotZero(-3, 3);
			c=Ops.randomNotZero(-4, 4);
		}
		double b=Ops.randomInt(-8, 8);
		double d=Ops.randomInt(-9, 9);
		double[] coefs = {a,b,c,d};
		g.setFunction(REMOVEABLE_DISCONTINUITY, coefs);
		return g;
	}
	
	public void setFunction(int type, 
			double[] coefficents,//see key in declaration for guide on how coefficients are used in each function
			boolean lowInclusive, double domainLow, boolean highInclusive, double domainHigh){
		
		setLowBound(lowInclusive, domainLow, false);
		setHighBound(highInclusive, domainHigh, false);
		setFunction(type, coefficents);	
	}
	
	public void setFunction(int type, 
			double[] coefficents//see key in declaration for guide on how coefficients are used in each function
			){
		
		a=setValue(coefficents, 0);
		b=setValue(coefficents, 1);
		c=setValue(coefficents, 2);
		d=setValue(coefficents, 3);
		e=setValue(coefficents, 4);
		
		//for the sake of making a graph, start with these values
		//change later to +/- infinity
		if(!restrictedLow){
			domainLow=windowLeft;
		}else{
			if(domainLow<windowLeft)windowLeft=domainLow-1;
		}
		if(!restrictedHigh)domainHigh=windowRight;
		else {
			if(domainHigh>windowRight) windowRight=domainHigh+1;
		}
		
		functionType=type;
		solutionSet = new ArrayList<CoordinatePoint>();
		double increase=1.0/graphResolution;
		for(double x=domainLow; x<=domainHigh;x+=increase){
			double y=evaluateAt(x);
			if((!restrictedLow || !restrictedHigh)&&(y>windowUp || y<windowDown)){
				//the point is not added if it falls outside of the window
			}else{
				solutionSet.add(new CoordinatePoint(x, y));
				if(y<windowDown)windowDown=y-2;
				if(y>windowUp)windowUp=y+2;
			}
		}
		if(restrictedLow){
			leftEndpoint=new CoordinatePoint(domainLow, evaluateAt(domainLow));
		}
		if(restrictedHigh){
			rightEndpoint=new CoordinatePoint(domainHigh, evaluateAt(domainHigh));
		}
	}
	
	protected double evaluateAt(double x){
		double y=0.0;
		if(functionType==CONSTANT){
			y=a;
		}
		else if(functionType==LINEAR){
			y=a*x+b;
		}
		else if(functionType==QUADRATIC){
			y= a*Math.pow(x, 2)+b*x+c;
		}
		else if(functionType==CUBIC){
			y=a*Math.pow(x, 3)+b*Math.pow(x, 2)+c*x + d;
		}
		else if(functionType==QUARTIC){
			y=a*Math.pow(x, 4)+b*Math.pow(x, 3)+c*Math.pow(x, 2)+d*x + e;
		}
		else if(functionType==RADICAL){
			y=Math.sqrt(a*x+b)+c;
		}else if(functionType==ABSOLUTE_VALUE){
			y=Math.abs(a*x+b)+c;
		}else if(functionType==BINOMIAL){
			y=Math.pow(a*x+b,c);
		}else if(functionType==REMOVEABLE_DISCONTINUITY){
			y=(a*x+b);//NOTE: Since the continuity is removeable it appears in the graph
			//classes calling this method will have to recognize the existance of the removeable
			//discontinuity
		}
		return y;
	}
	
	private void recalculate(){
		solutionSet = new ArrayList<CoordinatePoint>();
		double increase=1.0/graphResolution;
		for(double x=domainLow; x<=domainHigh;x+=increase){
			double y=evaluateAt(x);
			if((!restrictedLow || !restrictedHigh)&&(y>windowUp || y<windowDown)){
				//the point is not added if it falls outside of the window
			}else{
				solutionSet.add(new CoordinatePoint(x, y));
				if(y<windowDown)windowDown=y-2;
				if(y>windowUp)windowUp=y+2;
			}
		}
		if(restrictedLow){
			leftEndpoint=new CoordinatePoint(domainLow, evaluateAt(domainLow));
		}
		if(restrictedHigh){
			rightEndpoint=new CoordinatePoint(domainHigh, evaluateAt(domainHigh));
		}
	}
	
	public void setLowBound(boolean inclusive, double bound, boolean recalculate){
		restrictedLow=true;
		this.lowInclusive=inclusive;
		this.domainLow=bound;
		if(recalculate)recalculate();
	}
	
	public void setHighBound(boolean inclusive, double bound, boolean recalculate){
		restrictedHigh=true;
		this.highInclusive=inclusive;
		this.domainHigh=bound;
		if(recalculate)recalculate();
	}
	
	public ArrayList<CoordinatePoint> getSolutionSet() {
		return solutionSet;
	}

	public double getWindowLeft() {
		return windowLeft;
	}

	public void setWindowLeft(double windowLeft) {
		this.windowLeft = windowLeft;
	}

	public double getWindowRight() {
		return windowRight;
	}

	public void setWindowRight(double windowRight) {
		this.windowRight = windowRight;
	}

	public double getWindowDown() {
		return windowDown;
	}

	public void setWindowDown(double windowDown) {
		this.windowDown = windowDown;
		recalculate();
	}

	public double getWindowUp() {
		return windowUp;
	}

	public void setWindowUp(double windowUp) {
		this.windowUp = windowUp;
		recalculate();
	}

	public Object getDomainLow(){
		if(restrictedLow){
			return domainLow;
		}else{
			return null;
		}
	}


	public Object getDomainHigh() {
		if(restrictedLow){
			return domainHigh;
		}else{
			return null;
		}
	}

	public boolean isRestrictedLow() {
		return restrictedLow;
	}

	public ArrayList<CoordinatePoint> getRelativeMaxMins(){
		ArrayList<CoordinatePoint> maxMins=new ArrayList<CoordinatePoint>();
		if(functionType==QUADRATIC){
			double x=-b/(2*a);
			double y =evaluateAt(x);
			CoordinatePoint rm = new CoordinatePoint(x, y);
			if(a<0){
				rm.setDescription("relative maximum");
			}else if(a>0)rm.setDescription("relative minimum");
			maxMins.add(rm);
		}
		if(functionType==ABSOLUTE_VALUE){
			double x=-b/a;
			double y =evaluateAt(x);
			CoordinatePoint rm = new CoordinatePoint(x, y);
			rm.setDescription("relative minimum");
			maxMins.add(rm);
		}
		return maxMins;
	}
	
	public void setRestrictedLow(boolean restrictedLow) {
		this.restrictedLow = restrictedLow;
	}

	public boolean isRestrictedHigh() {
		return restrictedHigh;
	}

	public void setRestrictedHigh(boolean restrictedHigh) {
		this.restrictedHigh = restrictedHigh;
	}

	public boolean isLowInclusive() {
		return lowInclusive;
	}

	public void setLowInclusive(boolean lowInclusive) {
		this.lowInclusive = lowInclusive;
	}

	public boolean isHighInclusive() {
		return highInclusive;
	}

	public void setHighInclusive(boolean highInclusive) {
		this.highInclusive = highInclusive;
	}

	public CoordinatePoint getLeftEndpoint() {
		return leftEndpoint;
	}

	public void setLeftEndpoint(CoordinatePoint leftEndpoint) {
		this.leftEndpoint = leftEndpoint;
	}

	public CoordinatePoint getRightEndpoint() {
		return rightEndpoint;
	}

	public void setRightEndpoint(CoordinatePoint rightEndpoint) {
		this.rightEndpoint = rightEndpoint;
	}

	public String getFunctionText() {
		String functionText="";
		if(functionType==CONSTANT){
			functionText=Format.doubleToString(a);	
		}else if(functionType==LINEAR){
			if(a!=0){
				if(a==1)functionText="x";
				else if(a==-1)functionText="-x";
				else functionText=Format.doubleToString(a)+"x";
			}
			functionText+=Format.getStringWithSign(b);
		}else if(functionType==QUADRATIC){
			if(a==1)functionText="x^2";
			else if(a==-1)functionText="-x^2";
			else functionText=Format.doubleToString(a)+"x^2";
			if(b!=0){
				if(b==1)functionText+="+x";
				else if(b==-1)functionText+="-x";
				else functionText+=Format.getStringWithSign(b)+"x";
			}
			functionText+=Format.getStringWithSign(c);

		}else if(functionType==CUBIC){
			if(a==1)functionText="x^3";
			else if(a==-1)functionText="-x^3";
			else functionText=Format.doubleToString(a)+"x^3";
			if(b!=0){
				if(b==1)functionText+="+x^2";
				else if(b==-1)functionText+="-x^2";
				else functionText+=Format.getStringWithSign(b)+"x^2";
			}
			if(c!=0){
				if(c==1)functionText+="+x";
				else if(c==-1)functionText+="-x";
				else functionText+=Format.getStringWithSign(c)+"x";
			}
			functionText+=Format.getStringWithSign(c);

		}
		else if(functionType==RADICAL){
			functionText="\\sqrt{";
			if(a!=0){
				if(a==1)functionText+="x";
				else if(a==-1)functionText+="-x";
				else functionText+=Format.doubleToString(a)+"x";
			}
			functionText+=Format.getStringWithSign(b)+"}"+Format.getStringWithSign(c);	
		}else if(functionType==ABSOLUTE_VALUE){
			functionText="|";
			if(a!=0){
				if(a==1)functionText+="x";
				else if(a==-1)functionText+="-x";
				else functionText+=Format.doubleToString(a)+"x";
			}
			functionText+=Format.getStringWithSign(b)+"|"+Format.getStringWithSign(c);	
		}else if(functionType==BINOMIAL){
			functionText="\\(";
			if(a!=0){
				if(a==1)functionText+="x";
				else if(a==-1)functionText+="-x";
				else functionText+=Format.doubleToString(a)+"x";
			}
			functionText+=Format.getStringWithSign(b)+"\\)^{"+c+"}";	
		}else if(functionType==REMOVEABLE_DISCONTINUITY){
			double aCoef=a*c;
			double bCoef=a*d+b*c;
			double cCoef=b*d;
			functionText="\\frac{";
			if(aCoef==1)functionText+="x^2";
			else if(aCoef==-1)functionText+="-x^2";
			else functionText+=Format.doubleToString(aCoef)+"x^2";
			if(bCoef!=0){
				if(bCoef==1)functionText+="+x";
				else if(bCoef==-1)functionText+="-x";
				else functionText+=Format.getStringWithSign(bCoef)+"x";
			}
			functionText+=Format.getStringWithSign(cCoef);
			functionText+="}{";
			if(c!=0){
				if(c==1)functionText+="x";
				else if(c==-1)functionText+="-x";
				else functionText+=Format.doubleToString(c)+"x";
			}
			functionText+=Format.getStringWithSign(d)+"}";
		}else{
			functionText="\\text{Error: Function Text not defined in Graph class.}";	
		}
		return functionText;
	}
	
	
	
}
