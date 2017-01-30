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

public class Triangle {
	String name;
	Segment a;
	Segment b;
	Segment c;
	Angle A;
	Angle B;
	Angle C;
	
	public static int SSS=0;
	public static int SAS=1;
	
	public Triangle (String name,double a, double b, double c, int valueType){
		this.name = name;
		if (valueType==SSS){
			determineTriangleFromSSSDate(a,b,c);
		}
		else if (valueType==SAS){
			determineTriangleFromSASDate(a,b,c);
		}
	}
	
	private void determineTriangleFromSASDate(double a2, double b2, double c2) {
		char[] characters=name.toCharArray();
		Point aPoint=new Point(characters[0]+"");
		Point bPoint=new Point(characters[1]+"");
		Point cPoint=new Point(characters[2]+"");		
		this.a = new Segment(bPoint, cPoint, a2);
		this.c = new Segment(bPoint, aPoint, c2);
		this.B = new Angle(this.a, this.c, b2);
		this.b = new Segment(aPoint,cPoint, determineLength(a2, c2, b2));
				
	}

	private void determineTriangleFromSSSDate(double a2, double b2, double c2) {
		char[] characters=name.toCharArray();
		Point aPoint=new Point(characters[0]+"");
		Point bPoint=new Point(characters[1]+"");
		Point cPoint=new Point(characters[2]+"");		
		this.a = new Segment(bPoint, cPoint, a2);
		this.b = new Segment(aPoint, cPoint, b2);
		this.c = new Segment(bPoint, aPoint, c2);
		A = new Angle(this.b,this.c,determineAngle(this.b.getMeasure(),this.c.getMeasure(),this.a.getMeasure()));
		B = new Angle(this.a,this.c,determineAngle(this.a.getMeasure(),this.c.getMeasure(),this.b.getMeasure()));
		C = new Angle(this.a,this.b,determineAngle(this.a.getMeasure(),this.b.getMeasure(),this.c.getMeasure()));
	}


	
	public Triangle(Segment a, Segment b, Segment c){

		Angle a1 = new Angle(a,b,determineAngle(this.a.getMeasure(), this.b.getMeasure(), this.c.getMeasure()));

		Angle a2 = new Angle(a,c,determineAngle(this.a.getMeasure(), this.c.getMeasure(), this.b.getMeasure()));

		Angle a3 = new Angle(c,b,determineAngle(this.c.getMeasure(), this.b.getMeasure(), this.a.getMeasure()));
		
		if (a1.getMeasure()==90){
			this.a=a;
			this.b=b;
			this.c= c;
			C=a1;
			B= a2;
			A= a3;;		
		}
		else if (a2.getMeasure()==90){
			this.a=a;
			this.b=c;
			this.c= b;
			C=a2;
			B= a1;
			A= a3;	
		}
		else {
			this.c=a;
			this.b=b;
			this.a=c;
			C=a3;
			B=a2;
			A=a1;
		}

		
	}
	
	public double determineLength(double a, double b, double c){
		double C= Math.pow((Math.pow(a, 2)+Math.pow(b, 2)-(2*a*b*Math.cos(c))),.5);
		return C;
	}
	
	public double determineAngle(double a, double b, double c){
		double C= Math.acos(Math.pow(c, 2)-Math.pow(a, 2)-Math.pow(b, 2))/(2*a*b)*180/Math.PI;
		return C;
	}

	public Segment getSideA() {
		return a;
	}

	public Segment getSideB() {
		return b;
	}

	public Segment getSideC() {
		return c;
	}

	public Angle getA() {
		return A;
	}

	public Angle getB() {
		return B;
	}

	public Angle getC() {
		return C;
	}
	
	
}
