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

import com.orcmath.objects.BalancedEquation;
import com.orcmath.objects.ComplimentaryAngleValues;
import com.orcmath.objects.Ops;
import com.orcmath.objects.SupplementaryAngleValues;

public class BasicAngleAlgebra extends Type{

	private String graphic1Address = "PDFs/Graphics/TypeSpecific/BasicAngleAlgebra/graphic1.png";
	private String graphic2Address = "PDFs/Graphics/TypeSpecific/BasicAngleAlgebra/graphic2.png";
	private String graphic3Address = "PDFs/Graphics/TypeSpecific/BasicAngleAlgebra/graphic3.png";
	
	public BasicAngleAlgebra(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public BasicAngleAlgebra(int difficulty){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=100;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		getInstance(difficulty);
	}
	
	private void getInstance(int difficulty){
		String theQuestion = "\\begin{array}{l}";
		
		//TODO add more graphics and more problem types
		int graphicNumber = Ops.randomInt(1, 3);
		int questionType;

		if (graphicNumber ==1){
			questionType = Ops.randomInt(1, 2);
			if (questionType==1){
				
				ComplimentaryAngleValues angleExpressions;
				if (difficulty == 1 || difficulty == 2) {
					int smallerAngleValue = 5 * Ops.randomInt(2, 8);
					angleExpressions = new ComplimentaryAngleValues(smallerAngleValue);
				}
				else angleExpressions = new ComplimentaryAngleValues(Ops.randomInt(10, 40));

				theQuestion += "\\text{In the diagram below, }\\overrightarrow{BD}\\text{ is perpendicular to }\\overrightarrow{BF}\\text{ at point }B\\text{ and the value of}";
				theQuestion += "\\\\\\angle DBE = "+angleExpressions.smallerAngleExpression()+"\\text{ while the value of }\\angle EBF = "+angleExpressions.largerAngleExpression()+"\n\\\\\n\\\\";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=10cm,interpolation=bicubic]{"+graphic1Address+"}";
				theQuestion += "\\\\\n\\\\\n\\\\\\text{What is the measure of }\\angle DBE\\text{?}"; 
				theQuestion += "\\end{array}\n";
				
				question = theQuestion;
				answer = "\\text{The value of the variable is }" + angleExpressions.getxValue() + "\\text{ and m}\\angle DBE\\; =\\; " + angleExpressions.getValueOfSmallerAngle();
			}
			else if (questionType==2){
				
				ComplimentaryAngleValues angleExpressions;
				if (difficulty == 1 || difficulty == 2) {
					int smallerAngleValue = 5 * Ops.randomInt(2, 8);
					angleExpressions = new ComplimentaryAngleValues(smallerAngleValue);
				}
				else angleExpressions = new ComplimentaryAngleValues(Ops.randomInt(10, 40));

				theQuestion += "\\text{In the diagram below, }\\overrightarrow{BE}\\text{ intersects }\\overleftrightarrow{ABC}\\text{ at point }B\\text{. The value of}";
				theQuestion += "\\\\\\angle FBC = "+angleExpressions.smallerAngleExpression()+"\\text{ while the value of }\\angle EBF = "+angleExpressions.largerAngleExpression()+"\n\\\\\n\\\\";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=10cm,interpolation=bicubic]{"+graphic1Address+"}";
				theQuestion += "\\\\\n\\\\\n\\\\\\text{What must the value of }"+ angleExpressions.getX() + "\\text{ be such that }\\overrightarrow{BE}\\perp\\overleftrightarrow{ABC}\\text{?}"; 
				theQuestion += "\\end{array}\n";
				
				question = theQuestion;
				answer = angleExpressions.getX() + "\\text{ = }" + angleExpressions.getxValue();
			}
			else{
				question="";
				answer = "";
			}
		}
		else if (graphicNumber ==2){
			questionType = Ops.randomInt(1, 2);
			if (questionType==1){
				
				BalancedEquation angleExpressions;
				if (difficulty == 1 || difficulty == 2) {
					int smallerAngleValue = 5 * Ops.randomInt(2, 8);
					angleExpressions = new BalancedEquation(smallerAngleValue);
				}
				else angleExpressions = new BalancedEquation(Ops.randomInt(10, 40));

				theQuestion += "\\text{In the diagram below, }\\overrightarrow{QP}\\text{ and }\\overrightarrow{QR}\\text{ are opposite rays. }\\overrightarrow{QS}\\text{ bisects }\\angle PQT\\text{. If}";
				theQuestion += "\\\\m\\angle PQS\\;=\\;" + angleExpressions.getExpression1()+"\\text{ and }m\\angle SQT = "+angleExpressions.getExpression2()+"\\text{, what is }m\\angle PQS\\text{?}";
				theQuestion += "\\\\\\text{                           (The diagram is not to scale.)}\n\\\\\n\\\\";
				theQuestion += "\\\\\\text{                                  }\\includegraphics[width=10cm,interpolation=bicubic]{"+graphic2Address+"}";
				theQuestion += "\\end{array}\n";
				
				question = theQuestion;
				answer = "\\text{The value of the variable is }" + angleExpressions.getxValue() + "\\text{ and m}\\angle PQS\\; =\\; " + angleExpressions.getValueOfOneSide();
			}
			else if (questionType ==2){
				SupplementaryAngleValues angleExpressions;
				if (difficulty == 1 || difficulty == 2) {
					int smallerAngleValue = 5 * Ops.randomInt(4, 16);
					angleExpressions = new SupplementaryAngleValues(smallerAngleValue,false);
				}
				else angleExpressions = new SupplementaryAngleValues(Ops.randomInt(20, 80),false);

				theQuestion += "\\text{In the diagram below, }\\overrightarrow{QP}\\text{ and }\\overrightarrow{QR}\\text{ are opposite rays. If }m\\angle PQT\\;=\\;" + angleExpressions.smallerAngleExpression();
				theQuestion += "\\\\\\text{ and }m\\angle TQR = "+angleExpressions.largerAngleExpression()+"\\text{, what is }m\\angle TQR\\text{?}";
				theQuestion += "\\\\\\text{                           (The diagram is not to scale.)}\n\\\\\n\\\\";
				theQuestion += "\\\\\\text{                                  }\\includegraphics[width=10cm,interpolation=bicubic]{"+graphic2Address+"}";
				theQuestion += "\\end{array}\n";
				
				question = theQuestion;
				answer = "\\text{The value of the variable is }" + angleExpressions.getxValue() + "\\text{ and m}\\angle TQR\\; =\\; " + angleExpressions.getValueOfLargerAngle();
			}
			else{
				question="";
				answer = "";
			}
		}
		else if (graphicNumber ==3){
			questionType = Ops.randomInt(1, 2);
			if (questionType==1){
				
				BalancedEquation angleExpressions;
				if (difficulty == 1 || difficulty == 2) {
					int smallerAngleValue = 5 * Ops.randomInt(2, 8);
					angleExpressions = new BalancedEquation(smallerAngleValue);
				}
				else angleExpressions = new BalancedEquation(Ops.randomInt(10, 40));

				theQuestion += "\\text{In the diagram below, }\\overrightarrow{ZW}\\perp\\overleftrightarrow{XZU}\\text{ and }\\overleftrightarrow{YV}\\text{ intesects }\\overleftrightarrow{XZU}\\text{ at }Z\\text{ If}";
				theQuestion += "\\\\m\\angle XZY\\;=\\;" + angleExpressions.getExpression1()+"\\text{ and }m\\angle VZU = "+angleExpressions.getExpression2()+"\\text{, what is }m\\angle WZV\\text{?}";
				theQuestion += "\\\\\\text{                           (The diagram is not to scale.)}\n\\\\\n\\\\";
				theQuestion += "\\\\\\text{                                  }\\includegraphics[width=10cm,interpolation=bicubic]{"+graphic3Address+"}";
				theQuestion += "\\end{array}\n";
				
				question = theQuestion;
				answer = "\\text{The value of the variable is }" + angleExpressions.getxValue() + "\\text{ and }m\\angle WZU\\; =\\; " + (90-angleExpressions.getValueOfOneSide());
			}
			else if (questionType==2){
				
				BalancedEquation angleExpressions;
				if (difficulty == 1 || difficulty == 2) {
					int smallerAngleValue = 5 * Ops.randomInt(2, 8);
					angleExpressions = new BalancedEquation(smallerAngleValue);
				}
				else angleExpressions = new BalancedEquation(Ops.randomInt(10, 40));

				theQuestion += "\\text{In the diagram below, }\\overrightarrow{ZW}\\perp\\overleftrightarrow{XZU}\\text{ and }\\overleftrightarrow{YV}\\text{ intesects }\\overleftrightarrow{XZU}\\text{ at }Z\\text{ If}";
				theQuestion += "\\\\m\\angle XZY\\;=\\;" + angleExpressions.getExpression1()+"\\text{ and }m\\angle VZU = "+angleExpressions.getExpression2()+"\\text{, what is }m\\angle XZY\\text{?}";
				theQuestion += "\\\\\\text{                           (The diagram is not to scale.)}\n\\\\\n\\\\";
				theQuestion += "\\\\\\text{                                  }\\includegraphics[width=10cm,interpolation=bicubic]{"+graphic3Address+"}";
				theQuestion += "\\end{array}\n";
				
				question = theQuestion;
				answer = "\\text{The value of the variable is }" + angleExpressions.getxValue() + "\\text{ and }m\\angle XZY\\; =\\; " + angleExpressions.getValueOfOneSide();
			}
			else{
				question="";
				answer = "";
			}
		}
		else{
			question="";
			answer = "";
		}
	}
	
}
