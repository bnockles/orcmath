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

public class Relation{
	public char label;
	public boolean oneToOne;
	public int type;
	public Interval domain;
	public Interval range;
	public String function;

	public Relation(char x){
		label = x;
	}

	public void defineFunction(boolean isOneToOne){
		oneToOne=isOneToOne;
		if (oneToOne){
			function = setOneToOneFunction();
		}
		else{
			function = setNonOneToOneFunction();
		}
	}
	
	public String setOneToOneFunction(){
		type = Ops.randomInt(0, 5);
		if (type == 0) {
			Fraction coef = new Fraction(Ops.randomInt(1, 5), Ops.randomInt(2, 5));
			return "y="+coef.toString()+"x";
		}
		else if (type == 1) return "y="+Ops.randomInt(2, 5)+"^x";
		else if (type == 2) {
			int a = Ops.randomInt(1, 4);
			int b = Ops.randomInt(-5, 5);
			int c = Ops.randomInt(-5, 5);
			int d = Ops.randomInt(-5, 5);
			int[] coefs= {a,b,c,d};
			Polynomial cubic = new Polynomial(coefs, 'x');
			return "y="+cubic.toString();
		}
		else if (type == 3){
			int m = Ops.randomInt(-5, 5);
			int b = Ops.randomInt(-6, 6);
			int[] coefs= {m,b};
			Polynomial linear = new Polynomial(coefs, 'x');
			return "y="+linear.toString();
		}
		else if (type ==4){
			return "y=\\frac{" + Ops.randomInt(1, 5) +"}{x + "+Ops.randomInt(1, 6)+"}";
		}
		else return "y = " + Ops.randomInt(-5, 5);
	}

	public String setNonOneToOneFunction(){
		type = Ops.randomInt(6, 8);
		if (type == 5) return "y=\\sin x";
		else if (type == 6) return "y=\\cos x";
		else if (type == 7) {
			int a = Ops.randomInt(1, 4);
			int b = Ops.randomInt(-5, 5);
			int c = Ops.randomInt(-5, 5);
			int[] coefs= {a,b,c};
			Polynomial quadratic = new Polynomial(coefs, 'x');
			return "y="+quadratic.toString();
		}
		else return "y=|x|";		
	}
	
	public void setRelationNotFunction(int type){
		if (type == -1) function = "x=\\sin y";
		else if (type == -2) function ="x=\\cos y";
		else if (type == -3) {
			int a = Ops.randomInt(1, 4);
			int b = Ops.randomInt(-5, 5);
			int c = Ops.randomInt(-5, 5);
			int[] coefs= {a,b,c};
			Polynomial quadratic = new Polynomial(coefs, 'y');
			function = "x="+quadratic.toString();
		}
		else if (type == -4) {
			function = "x^2 + y^2="+(int)Math.pow((double)Ops.randomInt(2, 5),2);
		}
		else function = "x=|y|";		
	}
	
	public static Relation[] getThreeRelations(){
		Relation[] relations = new Relation[3];
		int a = -1*Ops.randomInt(1, 5);
		int b = -1*Ops.randomInt(1, 5);
		while (a==b) {b = -1*Ops.randomInt(1, 5);}
		int c = -1*Ops.randomInt(1, 5);
		while (a==c || b==c) {c = -1*Ops.randomInt(1, 5);}
		System.out.println(a +" , "+ b +" , "+ c);
		relations[0] = new Relation('f');
		relations[1] = new Relation('g');
		relations[2] = new Relation('h');
		relations[0].setRelationNotFunction(a);
		relations[1].setRelationNotFunction(b);
		relations[2].setRelationNotFunction(c);
		return relations;
	}
	
	public String getName(){
		return label+"(x)";
	}

	public int getType(){
		return type;
	}
	
	public String toString(){
		return function;
	}

}
