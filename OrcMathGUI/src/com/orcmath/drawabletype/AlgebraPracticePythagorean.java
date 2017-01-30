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
package com.orcmath.drawabletype;

import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.PythagoreanEquation;
import com.orcmath.objects.QuadrilateralGraphic;
import com.orcmath.objects.SimplestRadicalForm;
import com.orcmath.objects.SupplementaryAngleValues;
import com.orcmath.objects.Term;
import com.orcmath.objects.TriangleSum;
import com.orcmath.objects.Variable;

public class AlgebraPracticePythagorean extends MultipleChoiceQuestion{




		PythagoreanEquation equation;
		
		

		public AlgebraPracticePythagorean(){
			instructions = "";
			numberOfColumns=1;
			verticalSpacing=100;	
			whetherInstructionsAreNeverIncluded=true;
			scaleFactor=.35;
		}
		
		public AlgebraPracticePythagorean(int difficulty){
			instructions = "";
			numberOfColumns=1;
			verticalSpacing=100;	
			whetherInstructionsAreNeverIncluded=true;
			scaleFactor=.35;
			getInstance(difficulty);
		}
		
		private void getInstance(int difficulty){
			String theQuestion = "\\begin{array}{l}";
			
			int objective=Ops.randomInt(0, 1);//determine which side you are solving for

					if (difficulty == 1||difficulty == 2) {
						equation = new PythagoreanEquation(true,false);
					}
					if (difficulty==3){
						if(Math.random()<.5){//50% of the time, it is a pythagorean triple that is quadratic
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
						else if(Math.random()<.6){//30% of the time it is a linear that is NOT a triple
							equation = new PythagoreanEquation(false,false);
						}
						else{//40% of the time it is not a triple and is quadratic
							equation = new PythagoreanEquation(false,true);
						}
					}
					
					theQuestion += "\\text{In a right triangle, the length of the legs are }"+equation.getSideA()+"\\text{ and }"+equation.getSideB()+"\\text{ while the}";
					theQuestion += "\\\\\\text{length of the hypotenuse is }"+equation.getSideC()+"\\text{.}";
					theQuestion += "\\text{ What is the value of }"+equation.getX()+"\\text{?}";
					theQuestion += "\\end{array}\n";

					question = theQuestion;
					WorkTable answerWork = new WorkTable();
					answerWork.addHorizontalLine();
					answerWork.newLine("\\left("+equation.getSideA()+"\\right)^2+\\left("+equation.getSideB()+"\\right)^2", "\\left("+equation.getSideC()+"\\right)^2", "Pythagorean Theorem");
					answerWork.addPythagoreanTheoremEquationSteps(equation.getSideA(), equation.getSideB(), equation.getSideC(), ""+equation.getX(), equation.getSolution());
					
					answerWork.finish();
					answer=answerWork.getLatexTabular();
		//			answer = "\\text{The value of the variable is }" + equation.getSolution() + "\\text{ and }"+getObjective(quad, equation,objective)+"="+getActualValue(equation,objective);
		}
		
//		public String getObjective(QuadrilateralGraphic quad, PythagoreanEquation eq, int objectiveNumber){
//			if(objectiveNumber==0){
//				if(!eq.isSideAConstant()){
//					return quad.getSideA();
//				}
//				else{
//					return quad.getSideB();
//				}
//			}
//			else{
//				if(!eq.isSideCConstant()){
//					return quad.getHypotenuse();
//				}
//				else{
//					return quad.getSideA();
//				}
//			}
//		}
//		
//		public Term getActualValue(PythagoreanEquation eq, int objectiveNumber){
//			if(objectiveNumber==0){
//				if(!eq.isSideAConstant()){
//					return eq.getSideAActual();
//				}
//				else{
//					return eq.getSideBActual();
//				}
//			}
//			else{
//				if(!eq.isSideCConstant()){
//					return eq.getSideCActual();
//				}
//				else{
//					return eq.getSideAActual();
//				}
//			}
//		}
	

	
}
