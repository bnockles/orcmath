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

public class Procedure {

	public static SimplestRadicalForm calculateDistance(OrderedPair point1, OrderedPair point2){
		SimplestRadicalForm distance;
		Term xDifference;
		Term yDifference;
		Term sumOfSquares;
		if (Ops.areLikeTerms(point1.getxCoordinate(), point2.getxCoordinate())){
			boolean toSubtract = !point2.getxCoordinate().getSign(); 
			point2.getxCoordinate().setSign(toSubtract);
			xDifference = Ops.addLikeTerms(point1.getxCoordinate(), point2.getxCoordinate());
			//puts the original term back to how it was;
			point2.getxCoordinate().setSign(!toSubtract);
		}
		//when they are not like terms (some crazy sh**, hopefully this part of the code is never called)
		else {
			System.out.println("Get ready for some crazy sh**, you are trying to calculate the distnace between two ordered pairs that don't have like terms for coordinates.");
			System.out.println("WAAAAAAIT!!!!! At this time, there is not code for that sort of nonsense. Null is returned.");
			return null;		
		}
		if (Ops.areLikeTerms(point1.getyCoordinate(), point2.getyCoordinate())){
			boolean toSubtract = !point2.getyCoordinate().getSign(); 
			point2.getyCoordinate().setSign(toSubtract);
			yDifference = Ops.addLikeTerms(point1.getyCoordinate(), point2.getyCoordinate());
			//puts the original term back to how it was;
			point2.getyCoordinate().setSign(!toSubtract);
		}
		//when they are not like terms (some crazy sh**, hopefully this part of the code is never called)
		else {
			System.out.println("Get ready for some crazy sh**, you are trying to calculate the distnace between two ordered pairs that don't have like terms for coordinates.");
			System.out.println("WAAAAAAIT!!!!! At this time, there is not code for that sort of nonsense. Null is returned.");
			return null;		
		}
		Term xDifferenceSquared = Ops.pow(xDifference, 2);
		Term yDifferenceSquared = Ops.pow(yDifference, 2);
		if (Ops.areLikeTerms(xDifferenceSquared, yDifferenceSquared)){
			sumOfSquares = Ops.addLikeTerms(xDifferenceSquared, yDifferenceSquared);
		}
		//when they are not like terms (some crazy sh**, hopefully this part of the code is never called)
		else {
			System.out.println("Get ready for some crazy sh**, you are trying to calculate the distnace between two ordered pairs that don't have like terms for coordinates.");
			System.out.println("WAAAAAAIT!!!!! At this time, there is not code for that sort of nonsense. Null is returned.");
			return null;		
		}
		distance = new SimplestRadicalForm(2, sumOfSquares);
		return distance;	
	}
	
	public static Term simplifyImaginary(Term imaginary){
		int remainder = imaginary.getiExponent()%4;
		Term one = new Term (imaginary.getCoefficient());
		Term negativeOne = new Term ((-1)*imaginary.getCoefficient());
		Term i = new Term (imaginary.getCoefficient());
		i.makeImaginary(1);
		Term negativei = new Term((-1)*imaginary.getCoefficient());
		negativei.makeImaginary(1);
		if (remainder==0) return one;
		if (remainder==1) return i;
		if (remainder==2) return negativeOne;
		else return negativei;
	}
	
	public static OrderedPair calculateMidpoint(OrderedPair point1, OrderedPair point2){
	OrderedPair midpoint;
	if (point1.consistsOfIntegerCoordinates() && point2.consistsOfIntegerCoordinates()){
		Fraction xCoordiante = new Fraction (point1.getXIntValue()+point2.getXIntValue(),2);
		Fraction yCoordiante = new Fraction (point1.getYIntValue()+point2.getYIntValue(),2);
		Term xTerm = new Term(xCoordiante);
		Term yTerm = new Term(yCoordiante);
		midpoint=new OrderedPair(xTerm,yTerm);
	}
	else{
		System.out.println("WAAAAAAIT!!!!! At this time, there is not code for finding the midpoint of points that don't consist of integer coordinates. Null is returned.");
		return null;
	}
	return midpoint;
	}
}
