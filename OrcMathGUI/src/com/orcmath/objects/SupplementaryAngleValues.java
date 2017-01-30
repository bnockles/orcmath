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

public class SupplementaryAngleValues {

	int valueOfSmallerAngle;
	int valueOfLargerAngle;
	int xValue;
	char x;
	
	Expression smallerAngleExpression;
	Expression largerAngleExpression;
	
	boolean isQuadratic;
	
	int[] easyToDivideIntegers = {2,3,4,5,6,7,8,9,10,12,15,20,25};
	
	public SupplementaryAngleValues(int valueOfSmallerAngle, boolean isQuadratic){
		this.isQuadratic=isQuadratic;
		x = Variable.randVar();
		this.valueOfSmallerAngle=valueOfSmallerAngle;
		valueOfLargerAngle = 180-valueOfSmallerAngle;
		if(!isQuadratic){
			determineLinearExpressions();
		}
		else determineQuadraticExpressions();	
	}

	private void determineQuadraticExpressions() {
		xValue = 7;
		while (xValue==7|| xValue==9 || xValue ==8){
			xValue=Ops.randomInt(1, 10);
		}
		Term t1=new Term(xValue);
		Term t2 = new Term(Ops.randomInt((-3)*xValue, 3*xValue));
		Term[] roots = {t1,t2};
		FactorablePolynomial whenSimplified=new FactorablePolynomial(roots, ""+x);
		int a = whenSimplified.getATerm().getCoefficient();
		int b = whenSimplified.getBTerm().getCoefficient();
		int c = whenSimplified.getCTerm().getCoefficient()+180;
		if (Math.random()>.5){
			smallerAngleExpression = createAQuadraticExpression(a,b,c, valueOfSmallerAngle);
			largerAngleExpression = createCorrespondingLinearExpression(b,c,smallerAngleExpression);
		}
		else{
			largerAngleExpression = createAQuadraticExpression(a,b,c, valueOfLargerAngle);
			smallerAngleExpression = createCorrespondingLinearExpression(b,c,largerAngleExpression);
		}		
	}

	private Expression createCorrespondingLinearExpression(int b, int c,Expression angleExpression) {
		int slope = b-angleExpression.getTermsOfExpression()[1].getCoefficient();
		int intercept = c-angleExpression.getTermsOfExpression()[2].getCoefficient();
		return createLinearExpression(slope, intercept);
	}

	private Expression createAQuadraticExpression(int a, int b, int c, int valueOfSmallerAngle) {
		Term aTerm = new Term(a,x+"^2");
		int bCoefficient = Ops.randomInt(-b, b);
		Term bTerm = new Term(bCoefficient,""+x);
		Term cTerm = new Term(valueOfSmallerAngle-(a*(int)Math.pow(xValue, 2))-bCoefficient*xValue);
		Term[] expression = {aTerm, bTerm, cTerm};
		return new Expression(expression);
	}

	private void determineLinearExpressions() {
		xValue = Ops.randomInt(2, 2*(int)(Math.sqrt(valueOfSmallerAngle)));
		int coefficient1;
		int constant1;
		int coefficient2;
		int constant2;
		int sumOfCoefficients = easyToDivideIntegers[Ops.randomInt(0, easyToDivideIntegers.length-1)];
		coefficient1 =0;
		while (coefficient1==0){
			coefficient1= Ops.randomInt(-(int)(valueOfSmallerAngle/3), (int)(valueOfSmallerAngle/3));
		}
		int product1 = coefficient1*xValue;
		constant1 = valueOfSmallerAngle-product1;
		
		coefficient2 = sumOfCoefficients-coefficient1;
		int product2 = coefficient2*xValue;
		constant2 = valueOfLargerAngle-product2;
		//creates expressions
		smallerAngleExpression = createLinearExpression(coefficient1, constant1);
		largerAngleExpression = createLinearExpression(coefficient2, constant2);
	}

	public Expression createLinearExpression(int coefficient, int constant){
		Term variable = new Term(coefficient,""+x);
		Term constantTerm = new Term(constant);
		if(coefficient>0){
			Term[] expression = {variable, constantTerm};
			return new Expression(expression);
		}
		else{
			Term[] expression = {constantTerm,variable};
			return new Expression(expression);
		}
	}
	
	public String smallerAngleExpression(){
		return smallerAngleExpression.toString();
	}

	public String largerAngleExpression(){
		return largerAngleExpression.toString();
	}
	
	public int getValueOfSmallerAngle() {
		return valueOfSmallerAngle;
	}



	public int getValueOfLargerAngle() {
		return valueOfLargerAngle;
	}



	public int getxValue() {
		return xValue;
	}


	public String getVariable(){
		return ""+x;
	}
	
	
}

