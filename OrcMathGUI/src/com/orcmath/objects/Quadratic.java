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


public class Quadratic {
	private double a;
	private double b;
	private double c;
	private double discriminant;
	private double root1;
	private double root2;
	private boolean imaginary;
	private boolean decimalForm;
	private double xvertex;
	private double yvertex;
	private String standardForm;

	public Quadratic() {//generates the default parabola, y=x^2
		a = 1;
		b=0;
		c=0;
		root1=0;
		root2=0;
		imaginary=false;
		decimalForm=false;
		xvertex=0;
		yvertex=0;
		standardForm = a + "x^2 + " + b + "x + " + c;
	}
	
	public Quadratic(int a, int b, int c) {
		this.a = a;
		this.b=0;
		this.c=0;
		double d;
		discriminant=b*b-4*a*c;
			if (discriminant<0)
			{
				imaginary=true;
				d = Math.sqrt(-discriminant);			
			}
			else
			{
				imaginary=false;
				d = Math.sqrt(discriminant);
			}
		root1=(-b+d)/(2*a);
		root2=(-b-d)/(2*a);	
		xvertex=-b/(2*a);
		yvertex=a*Math.pow(xvertex,2)+b*xvertex+c;
		standardForm=getStandardForm();

	}
		
	public String getRoots(){//returns a string, stating what the roots are
		if (decimalForm){
			if (imaginary){
				return  "x = " + root1 + "i, x = " + root2 + "i";
			}
			else{
				return "x = " + root1 + ", x = " + root2;
			}
		}
		else{
			
			if (imaginary){
				return  "x = " + root1 + "i, x = " + root2 + "i";
			}
			else{
				return "x = " + root1 + ", x = " + root2;
			}
		}
	}
	
	public void setDecimalForm(boolean b){
		decimalForm = b;
	}
	
	public void setEquation(double a, double b, double c) {//calculates all information about a quadratic
		this.a = a;
		this.b=b;
		this.c=c;
		double d;
		discriminant=b*b-4*a*c;
			if (discriminant<0)
			{
				imaginary=true;
				d = Math.sqrt(-discriminant);			
			}
			else
			{
				imaginary=false;
				d = Math.sqrt(discriminant);
			}
		root1=(-b+d)/(2*a);
		root2=(-b-d)/(2*a);	
		xvertex=-b/(2*a);
		yvertex=a*Math.pow(xvertex,2)+b*xvertex+c;
		standardForm=getStandardForm();

	}
	
	public void roundValues(double r){//when used, all doubles will be rounded according to the number of decimal places
		root1 = Format.roundedDouble(root1,r);
		root2 = Format.roundedDouble(root2,r);
		xvertex = Format.roundedDouble(xvertex,r);
		yvertex = Format.roundedDouble(yvertex,r);

	}
	
	public String getStandardForm(){//method with generate a string that shows values of a, b and c appropriately, depending on whether they're values are trivial or not
		String as= (Format.getCoefficient(a));
		String bs= (Format.getCoefficient(b));
		String cs= (Format.getCoefficient(c));
		if (a == 1){//case when a = 1
			if (b!=0){//case when a=1, b�0
				if (c!=0){//case when a=1, b�0, c�0
					standardForm= "x^2 + " + bs + "x + " + cs;
					return standardForm;
				}
				else{//case when a=1, b�0, c=0
					standardForm= "x^2 + " + bs + "x";
					return standardForm;
				}
				
			}
			else {//case when a=1, b=0
				if (c!=0){//case when a=1, b=0, c�0
					standardForm= "x^2 + " + cs;
					return standardForm;
				}
				else{//case when a=1, b=0, c=0
					standardForm= "x^2";
					return standardForm;
				}
			}
		}
		else{//case when a�1
			if (b!=0){//case when a�1, b�0
				if (c!=0){//case when a=1, b�0, c�0
					standardForm= as+ "x^2 + " + bs + "x + " + cs;
					return standardForm;
				}
				else{//case when a�1, b�0, c=0
					standardForm= as+ "x^2 + " + bs + "x";
					return standardForm;
				}
				
			}
			else {//case when a�1, b=0
				if (c!=0){//case when a=1, b=0, c�0
					standardForm= as + "x^2 + " + cs;
					return standardForm;
				}
				else{//case when a�1, b=0, c=0
					standardForm= as + "x^2";
					return standardForm;
				}
			}
		}
	}
	


	

}
