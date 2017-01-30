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
package com.orcmath.objects;

public class LinearFunction {
	Fraction yIntercept;
	Fraction slope;
	OrderedPair aPoint;
	
	public LinearFunction(Fraction m, Fraction b){
		slope = m;
		yIntercept = b;		
	}
	
	public LinearFunction(Fraction m, int b){
		slope = m;
		yIntercept = new Fraction(b,1);		
	}

	public LinearFunction(int m, int b){
		slope = new Fraction(m,1);
		yIntercept = new Fraction(b,1);		
	}
	
	public LinearFunction(Fraction m, OrderedPair point){
		slope = m;
		Fraction xAsFraction = new Fraction(point.getxCoordinate(),new Term(1));
		Fraction product = Ops.multiplyFractions(slope, xAsFraction);
		Term fromX = new Term(product);
		boolean xSign = !fromX.getSign();
		fromX.setSign(xSign);
		Term desiredY = Term.getCopy(point.getyCoordinate());
		Term difference = Ops.addRationals(desiredY, fromX);
		yIntercept = new Fraction(Term.asNumerator(difference),difference.getDenominator());		
	}
	
	public String toString(){
		//a linear equation is y = (a/b)x+(c/d)
		//standard form is then (-ad)x+(bd)y=bc
		Term negativeA = Term.getCopy(slope.getNumerator());
		boolean aSign =!negativeA.getSign();
		negativeA.setSign(aSign);
		Term d = Term.getCopy(yIntercept.getDenominator());
		Term b = Term.getCopy(slope.getDenominator());
		Term c = Term.getCopy(yIntercept.getNumerator());
		
		Term negadtiveAD=Ops.multiplyTerms(negativeA, d);
		Term bd=Ops.multiplyTerms(b, d);
		Term bc = Ops.multiplyTerms(b, c);
		
		String standardForm = "";
		if (negadtiveAD.toString().equals("1"))standardForm += "" + "x";
		else if (negadtiveAD.toString().equals("-1"))standardForm += "-" + "x";
		else standardForm += negadtiveAD + "x";
		if (bd.getSign()) {
			standardForm += " + ";
			if (bd.toString().equals("1")) standardForm += "y = " + bc;
			else standardForm += bd + "y = " + bc;
		}
		else {
			String absbd = bd.toString().replaceAll("-","");
			if (bd.toString().equals("1")) standardForm += " - y = " + bc;
			else standardForm += " - " + absbd + "y = " + bc;
		}
		return standardForm;
	}
	
	public String getSlopeInterceptForm(){
		Term yInterceptTerm = new Term(yIntercept);
		String slopeInterceptForm = "y = ";
		if(slope.toString().equals("1")) slopeInterceptForm +="x";
		else if(slope.toString().equals("-1")) slopeInterceptForm +="-x";
		else slopeInterceptForm +=slope +"x";
		if (yInterceptTerm.toString().equals("0"));
		else if (yInterceptTerm.getSign()) slopeInterceptForm += " + " + yInterceptTerm;
		else {
			String absyInterceptTerm = yInterceptTerm.toString().replaceAll("-","");
			slopeInterceptForm += " - " + absyInterceptTerm;
		}
		return slopeInterceptForm;
	}
	
	public String getPointSlopeForm(){
		OrderedPair point = getAPoint(-8, 8);
		String s = "y";
		if (point.getyCoordinate().isPositive) s += " - " + point.getyCoordinate() + " = " + slope;
		else{
			String absyCoordinate = point.getyCoordinate().toString().replaceAll("-","");
			s += " + " + absyCoordinate + " = " + slope;
		}
		if (point.getxCoordinate().toString().equals("0")){
			s+= "x";
		}
		else s+= "\\left(x";
		if (point.getxCoordinate().isPositive) s += " - " + point.getxCoordinate() + "\\right)";
		else{
			String absxCoordinate = point.getxCoordinate().toString().replaceAll("-","");
			s += " + " + absxCoordinate + "\\right)";
		}
		return s;
	}

	public Fraction getyIntercept() {
		return yIntercept;
	}

	public void setyIntercept(Fraction yIntercept) {
		this.yIntercept = yIntercept;
	}

	public Fraction getSlope() {
		return slope;
	}

	public void setSlope(Fraction slope) {
		this.slope = slope;
	}
	
	public Fraction getPerpendicularSlope(){
		Fraction perpSlope = slope.getReciprocal();
		Fraction negative1 = new Fraction (-1,1);
		return Ops.multiplyFractions(negative1, perpSlope);
	}
	
	public OrderedPair getAPoint(int minXValue, int maxXValue){
		int x = Ops.randomInt(minXValue, maxXValue);
		Fraction xFraction = new Fraction(x,1);
		Fraction product = Ops.multiplyFractions(slope, xFraction);
		Term a = new Term(product);
		Term b = new Term(yIntercept);
		//creates x and y term coordinates
		Term xCoordinate = new Term(xFraction);
		Term yCoordinate = Ops.addRationals(a, b);
		return new OrderedPair(xCoordinate, yCoordinate);
	}
	
	
}
