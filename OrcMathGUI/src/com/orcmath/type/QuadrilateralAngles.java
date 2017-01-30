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

import com.orcmath.objects.CongruentValues;
import com.orcmath.objects.Ops;
import com.orcmath.objects.QuadrilateralGraphic;
import com.orcmath.objects.SupplementaryAngleValues;
import com.orcmath.objects.TriangleSum;

public class QuadrilateralAngles extends Type {

	private QuadrilateralGraphic quad;
	
	static int lastSelection;
	static int selectionBeforeLast1;
	static int selectionBeforeLast2;

	public QuadrilateralAngles(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public QuadrilateralAngles(int difficulty){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		getInstance(difficulty);
		//question and answer is determined in the getInstance constructor
	}
	
	private void getInstance(int difficulty){
		String theQuestion = "\\begin{array}{l}";
		
		int objective=Ops.randomInt(0, 1);
		int questionType=Ops.randomInt(1, 2);
		//type 1 angle sum in a right triangle (rhombus or kite)	
		//type 2 same-side interior angles


			if (questionType==1){//angle sum in a right triangle (rhombus or kite)
				
				int type=QuadrilateralGraphic.RHOMBUS;
				while (type == lastSelection){
					double chooser=Math.random();
					if(chooser<.5){
						type=QuadrilateralGraphic.KITE;
					}
					else {
						type=QuadrilateralGraphic.RHOMBUS;
					}
				}
				quad=new QuadrilateralGraphic(type, true);		
				lastSelection=type;
				
				TriangleSum triangleSum;
				if (difficulty == 1||difficulty == 2) {
					triangleSum = new TriangleSum(Ops.randomInt(30, 70), 90, true, false, false);
				}
				else {
					if(Math.random()<.6){
						if(difficulty!=4){
						triangleSum = new TriangleSum(Ops.randomInt(30, 70), 90, true, true, false);
						}
						else{
							triangleSum = new TriangleSum(Ops.randomInt(30, 70), 90, true, true, true);
						}
					}
					else{
						triangleSum = new TriangleSum(Ops.randomInt(30, 70), 90, true, false, false);
					}
				}

				theQuestion += "\\text{In the diagram below of "+quad.getTypeName()+" }"+quad.getName()+
						"\\text{, }m\\angle "+quad.getComplementaryAngle1()+"="+triangleSum.getAngleAExpression();
				theQuestion += "\\\\\\text{and }m\\angle "+quad.getComplementaryAngle2()+"="+triangleSum.getAngleBExpression()+"\\text{.}";
				//for level one questions, the variable does not need to be plugged in
				if(difficulty==1){
					theQuestion += "\\text{ What is the value of }"+triangleSum.getVariable()+"\\text{?}";
				}
				else{
					theQuestion += "\\text{ What is the value of }m\\angle "+getComplementaryObjective(quad,objective)+"\\text{?}";
				}
				//copy this line to change the size:
				//				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=10cm,interpolation=bicubic]{"+quad.getImageAddress()+"}";

				theQuestion += "\\\\\\text{                                      }\\includegraphics[interpolation=bicubic]{"+quad.getImageAddress()+"}";
				theQuestion += "\\end{array}\n";

				question = theQuestion;
				answer = "\\text{The value of the variable is }" + triangleSum.getSolution() + "\\text{ and }m\\angle "+getComplementaryObjective(quad,objective)+"="+getActualValue(objective, triangleSum);
			}
			else if(questionType==2){//same-side interior angles
				
				int type=QuadrilateralGraphic.PARALLELOGRAM;
				while (type == lastSelection || 
						type == selectionBeforeLast1
						){
					double chooser=Math.random();
					if(chooser<.33){
						type=QuadrilateralGraphic.PARALLELOGRAM;
					}
					else if(chooser<.67){
						type=QuadrilateralGraphic.TRAPEZOID;
					}
					else {
						type=QuadrilateralGraphic.RHOMBUS;
					}
				}
				quad=new QuadrilateralGraphic(type, true);		
				selectionBeforeLast1=lastSelection;
				lastSelection=type;
				
				SupplementaryAngleValues suppAngleValues;
				if (difficulty == 1 || difficulty == 2) {
					int smallerAngleValue = 5 * Ops.randomInt(4, 16);
					suppAngleValues = new SupplementaryAngleValues(smallerAngleValue, false);
				}
				else {
					double determineWhetherEquationIsQuadratic = Math.random();
					if (determineWhetherEquationIsQuadratic<.7){
						suppAngleValues = new SupplementaryAngleValues(Ops.randomInt(20, 80), false);
					}
					else{
						suppAngleValues = new SupplementaryAngleValues(Ops.randomInt(20, 80), true);
					}
				
				}

				theQuestion += "\\text{In the diagram below of "+quad.getTypeName()+" }"+quad.getName()+
						"\\text{, }m\\angle "+quad.getSupplementaryAngle1()+"="+suppAngleValues.smallerAngleExpression();
				theQuestion += "\\\\\\text{and }m\\angle "+quad.getSupplementaryAngle2()+"="+suppAngleValues.largerAngleExpression()+"\\text{.}";
				//for level one questions, the variable does not need to be plugged in
				if(difficulty==1){
					theQuestion += "\\text{ What is the value of }"+suppAngleValues.getVariable()+"\\text{?}";
				}
				else{
					theQuestion += "\\text{ What is the value of }m\\angle "+getSupplementaryObjective(quad, objective)+"\\text{?}";
				}
				//copy this line to change the size:
				//				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=10cm,interpolation=bicubic]{"+quad.getImageAddress()+"}";

				theQuestion += "\\\\\\text{                                      }\\includegraphics[interpolation=bicubic]{"+quad.getImageAddress()+"}";
				theQuestion += "\\end{array}\n";
				question = theQuestion;
				answer = "\\text{The value of the variable is }" + suppAngleValues.getxValue() + "\\text{ and }m\\angle "+getSupplementaryObjective(quad,objective)+"="+getActualValue(objective, suppAngleValues);
			}
	
			else{
				question="";
				answer = "";
			}
	}


	public String getComplementaryObjective(QuadrilateralGraphic quad, int objective){
		if(objective==1){
			return quad.getComplementaryAngle1();
		}
		else{
			return quad.getComplementaryAngle2();
		}
		
	}
	
	public String getSupplementaryObjective(QuadrilateralGraphic quad, int objective){
		if(objective==1){
			return quad.getSupplementaryAngle1();
		}
		else{
			return quad.getSupplementaryAngle2();
		}
		
	}
	
	private int getActualValue(int objective, TriangleSum triangleSum) {
			if(objective ==1){
				return triangleSum.getValueOfAngleA();
			}
			else{
				return triangleSum.getValueOfAngleB();
			}
	}
	
	private int getActualValue(int objective, SupplementaryAngleValues suppAngleValues) {
		if(objective ==1){
			return suppAngleValues.getValueOfSmallerAngle();
		}
		else{
			return suppAngleValues.getValueOfLargerAngle();
		}
}
}