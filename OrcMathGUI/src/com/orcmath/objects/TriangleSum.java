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

public class TriangleSum {

	int valueOfAngleA;
	int valueOfAngleB;
	int valueOfAngleC;//this is the angle that should always be identified as the right angle
	int xValue;
	char x;
	Expression angleAExpression;
	Expression angleBExpression;
	Expression angleCExpression;
	
	
	
	public TriangleSum(int firstAngle, int secondAngleOr90DegreeAngle, boolean rightAngleIsGiven, boolean quadratic, boolean aTermIsGreaterThan1){
		valueOfAngleA=firstAngle;
		valueOfAngleC=secondAngleOr90DegreeAngle;
		valueOfAngleB = 180-valueOfAngleA-valueOfAngleC;
		x=Variable.randVar();
		
		if (quadratic){
			xValue = Ops.randomInt(1, 12);
			determineQuadraticExpressions(rightAngleIsGiven, aTermIsGreaterThan1);
		}
		else{
			xValue = Ops.randomInt(1, 12);
			determineLinearExpression(rightAngleIsGiven);
		}
		
	}



	private void determineQuadraticExpressions(boolean rightAngleIsGiven,boolean aTermIsGreaterThan1) {
		Term t1=new Term(xValue);
		Term t2 = new Term(Ops.randomInt(-2*xValue, 2*xValue));
		Term[] roots = {t1,t2};
		FactorablePolynomial whenSimplified=new FactorablePolynomial(roots, ""+x);
		
		//the expressions defining the measure of each angle must simplify to this factorablePolynomial, THEN 180 is subtracted.
		int squaresAddToThisNumber = whenSimplified.getATerm().getCoefficient();
		int linearsAddToThisNumber = whenSimplified.getBTerm().getCoefficient();
		int constantsAddToThisNumber = whenSimplified.getCTerm().getCoefficient()+180;//180 is added to both sides of the equation
		
		System.out.println(Format.termArrayToString(whenSimplified.getPolynomial())+"<---factorable");
		
		//not all of the expressions are quadratic, no more than two
		boolean aIsQuadratic=false;
		boolean bIsQuadratic=false;
		boolean cIsQuadratic=false;
		
		double whichIsQuadratic1 = Math.random();
		double whichIsQuadratic2 = Math.random();
		if(whichIsQuadratic1<.33 || (whichIsQuadratic2<.33&&aTermIsGreaterThan1)){
			aIsQuadratic=true;
		}
		else if(whichIsQuadratic1<.67|| (whichIsQuadratic2<.67&&aTermIsGreaterThan1)){
			bIsQuadratic=true;
		}
		else{
			if(rightAngleIsGiven){
				if(Math.random()<.5){
					aIsQuadratic=true;
					if(aTermIsGreaterThan1)bIsQuadratic=true;
				}
				else{
					bIsQuadratic=true;
					if(aTermIsGreaterThan1)aIsQuadratic=true;
				}
			}
			else{
				cIsQuadratic=true;
			}
		}
		
		//begin with angle C, since it may be a constant
		if(rightAngleIsGiven){
			Term[] c ={new Term(90)};
			angleCExpression=new Expression(c);
			constantsAddToThisNumber=constantsAddToThisNumber-90;
		}
		else{
			Term[] cTerms=createTermArray(valueOfAngleC,cIsQuadratic);
			if(cIsQuadratic){
				squaresAddToThisNumber = squaresAddToThisNumber - cTerms[0].getCoefficient();
				linearsAddToThisNumber = linearsAddToThisNumber - cTerms[1].getCoefficient();
				constantsAddToThisNumber = constantsAddToThisNumber - cTerms[2].getCoefficient();
			}
			else{
				linearsAddToThisNumber = linearsAddToThisNumber - cTerms[0].getCoefficient();
				constantsAddToThisNumber = constantsAddToThisNumber - cTerms[1].getCoefficient();
			}
			angleCExpression=new Expression(cTerms);
		}
		
		Term[] aTerms = createTermArray(valueOfAngleA,aIsQuadratic);
		if(aIsQuadratic){
			squaresAddToThisNumber = squaresAddToThisNumber - aTerms[0].getCoefficient();
			linearsAddToThisNumber = linearsAddToThisNumber - aTerms[1].getCoefficient();
			constantsAddToThisNumber = constantsAddToThisNumber - aTerms[2].getCoefficient();
		}
		else{
			linearsAddToThisNumber = linearsAddToThisNumber - aTerms[0].getCoefficient();
			constantsAddToThisNumber = constantsAddToThisNumber - aTerms[1].getCoefficient();
		}
		Term[] bTerms = createTermArray(valueOfAngleB,bIsQuadratic);
		if(bIsQuadratic){
			squaresAddToThisNumber = squaresAddToThisNumber - bTerms[0].getCoefficient();
			linearsAddToThisNumber = linearsAddToThisNumber - bTerms[1].getCoefficient();
			constantsAddToThisNumber = constantsAddToThisNumber - bTerms[2].getCoefficient();
		}
		else{
			linearsAddToThisNumber = linearsAddToThisNumber - bTerms[0].getCoefficient();
			constantsAddToThisNumber = constantsAddToThisNumber - bTerms[1].getCoefficient();
		}
		angleAExpression=new Expression(aTerms);
		angleBExpression=new Expression(bTerms);
		System.out.println(xValue+"<---xValue");
		System.out.println(angleCExpression+"<---given");
		System.out.println(angleAExpression+"<---A, which totals "+valueOfAngleA);
		System.out.println(angleBExpression+"<---B, which totals "+valueOfAngleB);
		
	}



	private Term[] createTermArray(int actualValue, boolean isQuadratic) {	
		int remainder = actualValue;
		if (isQuadratic){
			int aCoefficient = Ops.randomInt(-4, 4);
			Term aTerm = new Term(aCoefficient,x+"^2");
			remainder= remainder-(int)(aCoefficient *Math.pow(xValue, 2));
			int bCoefficient = Ops.randomInt(-12, 12);
			Term bTerm = new Term(bCoefficient,""+x);
			remainder= remainder-(int)(bCoefficient *xValue);
			Term cTerm = new Term(remainder);
			Term[] terms = {aTerm,bTerm, cTerm};
			return terms;
		}
		else{
			int bCoefficient = Ops.randomInt(-12, 12);
			int product =bCoefficient*xValue;
			remainder=remainder-product;
			Term bTerm = new Term(bCoefficient,""+x);
			Term cTerm = new Term(remainder);
			Term[] terms = {bTerm, cTerm};
			return terms;
		}
		
	}



	private void determineLinearExpression(boolean rightAngleIsGiven) {
		int coefficient1 = Ops.randomInt(1, (int)(valueOfAngleA/3));
		while(coefficient1*xValue>120){
			coefficient1 = Ops.randomInt(1, (int)(valueOfAngleA/3));
		}
		int product1 = coefficient1*xValue;
		int constant1 = valueOfAngleA-product1;
		Term tA1=new Term(coefficient1,""+x);
		Term tA2=new Term(constant1);
		Term[] a={tA1,tA2};
		
		int coefficient2 = Ops.randomInt(1, (int)(valueOfAngleB/3));
		while(coefficient2*xValue>120){
			coefficient2 = Ops.randomInt(1, (int)(valueOfAngleA/3));
		}
		int product2 = coefficient2*xValue;
		int constant2 = valueOfAngleB-product2;
		Term tB1=new Term(coefficient2,""+x);
		Term tB2=new Term(constant2);
		Term[] b={tB1,tB2};
		
		Term[] c;
		if(rightAngleIsGiven){
			c=new Term[1];
			c[0]=new Term(90);
		}
		else{
			c=new Term[2];
			int coefficient3 = Ops.randomInt(-2*(int)(valueOfAngleC/3), (int)(valueOfAngleC/3));
			int product3 = coefficient3*xValue;
			int constant3 = valueOfAngleC-product3;
			Term tC1=new Term(coefficient3,""+x);
			Term tC2=new Term(constant3);
			c[0]=tC1;
			c[1]=tC2;
		}
		
		angleAExpression=new Expression(a);
		angleBExpression=new Expression(b);
		angleCExpression=new Expression(c);
	}



	public int getValueOfAngleA() {
		return valueOfAngleA;
	}



	public int getValueOfAngleB() {
		return valueOfAngleB;
	}



	public int getValueOfAngleC() {
		return valueOfAngleC;
	}



	public int getSolution() {
		return xValue;
	}



	public char getVariable() {
		return x;
	}



	public Expression getAngleAExpression() {
		return angleAExpression;
	}



	public Expression getAngleBExpression() {
		return angleBExpression;
	}



	public Expression getAngleCExpression() {
		return angleCExpression;
	}


	
}
