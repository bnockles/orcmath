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

//this class is essentially identical to Linear Equation, but less customizable (easier to construct)
//input:
//value of one side
public class BalancedEquation {
	
	int valueOfOneSide;
	int xValue;
	int coefficient1;
	int constant1;
	int coefficient2;
	int constant2;
	char x;
	
		
		public BalancedEquation(int valueOfOneSide){
			this.valueOfOneSide=valueOfOneSide;
			xValue = Ops.randomInt(2, 2*(int)(Math.sqrt(valueOfOneSide)));
			coefficient1 = Ops.randomInt(2, (int)(valueOfOneSide/3));
			int product1 = coefficient1*xValue;
			constant1 = valueOfOneSide-product1;
			
			coefficient2 = Ops.randomInt(2, (int)(valueOfOneSide/3));
			//use if you don't want the coefficients to be the same
			while (coefficient2==coefficient1) coefficient2 = Ops.randomInt(2, (int)(valueOfOneSide/3));
			int product2 = coefficient2*xValue;
			constant2 = valueOfOneSide-product2;	
			
			x = Variable.randVar();
		}
		
		public String getExpression1(){
			String s = "" + coefficient1 + x;
			if (constant1>0) s += " + " + constant1;
			else if (constant1==0);
			else {
				int absb = constant1 * (-1);
				s += " - " + absb;
			}
			return s;
		}

		public String getExpression2(){
			String s = "" + coefficient2 + x;
			if (constant2>0) s += " + " + constant2;
			else if (constant2==0);
			else {
				int absb = constant2 * (-1);
				s += " - " + absb;
			}
			return s;
		}

		public int getValueOfOneSide() {
			return valueOfOneSide;
		}

		public int getxValue() {
			return xValue;
		}

		public int getCoefficient1() {
			return coefficient1;
		}

		public int getConstant1() {
			return constant1;
		}

		public int getCoefficient2() {
			return coefficient2;
		}

		public int getConstant2() {
			return constant2;
		}

		public char getX() {
			return x;
		}
		
		

}
