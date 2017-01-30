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
package com.orcmath.type;

import com.orcmath.objects.Expression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.PythagoreanEquation;
import com.orcmath.objects.QuadrilateralGraphic;
import com.orcmath.objects.SimplestRadicalForm;
import com.orcmath.objects.SupplementaryAngleValues;
import com.orcmath.objects.Term;
import com.orcmath.objects.TriangleSum;
import com.orcmath.objects.Variable;

public class RightTriangleSides extends Type{

	PythagoreanEquation equation;
	private QuadrilateralGraphic quad;
	
	static int lastSelection;
	static int selectionBeforeLast1;
	

	public RightTriangleSides(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public RightTriangleSides(int difficulty, boolean alwaysQuadratic){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		getInstance(difficulty, alwaysQuadratic);
	}
	
	private void getInstance(int difficulty, boolean alwaysQuadratic){
		String theQuestion = "\\begin{array}{l}";
		
		int objective=Ops.randomInt(0, 1);//determine which side you are solving for
		int questionType=Ops.randomInt(1, 1);


			if (questionType==1){
				
				int type=QuadrilateralGraphic.RHOMBUS;
				while (type == lastSelection){
					double chooser=Math.random();
					if(chooser<.33){
						type=QuadrilateralGraphic.KITE;
					}
					else if(chooser<.67) {
						type=QuadrilateralGraphic.RECTANGLE;
					}
					else {
						type=QuadrilateralGraphic.RHOMBUS;
					}
				}
				quad=new QuadrilateralGraphic(type, true);		
				
				selectionBeforeLast1=lastSelection;
				lastSelection=type;
				
				if ((difficulty == 1||difficulty == 2) && !alwaysQuadratic) {
					equation = new PythagoreanEquation(true,false);
				}
				else if (difficulty<=3){
					if(Math.random()<.5 || alwaysQuadratic){//50% of the time, it is a pythagorean triple that is quadratic
						equation = new PythagoreanEquation(true,true);
					}
					else {//otherwise it is a linear that is NOT a triple
						equation = new PythagoreanEquation(false,false);
					}
				}
				else {
					
					if(Math.random()<.3){//30% of the time, it is a pythagorean triple that is quadratic
						equation = new PythagoreanEquation(true,true);
					}
					else if(Math.random()<.6 && !alwaysQuadratic){//30% of the time it is a linear that is NOT a triple
						equation = new PythagoreanEquation(false,false);
					}
					else{//40% of the time it is not a triple and is quadratic
						equation = new PythagoreanEquation(false,true);
					}
				}
				
				theQuestion += "\\text{In the diagram below of "+quad.getTypeName()+" }"+quad.getName()+
						"\\text{, }"+quad.getSideA()+"="+equation.getSideA()+"\\text{, }"+quad.getSideB()+"="+equation.getSideB();
				theQuestion += "\\\\\\text{and }"+quad.getHypotenuse()+"="+equation.getSideC()+"\\text{.}";
				//for level one questions, the variable does not need to be plugged in
				if(difficulty==1){
					theQuestion += "\\text{ What is the value of }"+equation.getX()+"\\text{?}";
				}
				else{
					theQuestion += "\\text{ What is the value of }"+getObjective(quad, equation,objective)+"\\text{?}";
				}
				//copy this line to change the size:
				//				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=10cm,interpolation=bicubic]{"+quad.getImageAddress()+"}";

				theQuestion += "\\\\\\text{                                      }\\includegraphics[interpolation=bicubic]{"+quad.getImageAddress()+"}";
				theQuestion += "\\end{array}\n";

				question = theQuestion;
				answer = "\\text{The value of the variable is }" + equation.getSolution() + "\\text{ and }"+getObjective(quad, equation,objective)+"="+getActualValue(equation,objective);
			}
			else{
				question="";
				answer = "";
			}
	}
	
	public String getObjective(QuadrilateralGraphic quad, PythagoreanEquation eq, int objectiveNumber){
		if(objectiveNumber==0){
			if(!eq.isSideAConstant()){
				return quad.getSideA();
			}
			else{
				return quad.getSideB();
			}
		}
		else{
			if(!eq.isSideCConstant()){
				return quad.getHypotenuse();
			}
			else{
				return quad.getSideA();
			}
		}
	}
	
	public Term getActualValue(PythagoreanEquation eq, int objectiveNumber){
		if(objectiveNumber==0){
			if(!eq.isSideAConstant()){
				return eq.getSideAActual();
			}
			else{
				return eq.getSideBActual();
			}
		}
		else{
			if(!eq.isSideCConstant()){
				return eq.getSideCActual();
			}
			else{
				return eq.getSideAActual();
			}
		}
	}
}
