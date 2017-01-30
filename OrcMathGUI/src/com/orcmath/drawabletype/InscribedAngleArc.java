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

import com.orcmath.drawable.Circle;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.LinearExpression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class InscribedAngleArc  extends DynamicType{



	char[] labels;
	CoordinateImage image;
	Circle circle;
	int randomAngle1;
	int randomAngle2;
	int xValue;
	char variable;
	Expression arcExpression;
	Expression inscribedAngleExpression;

	public InscribedAngleArc(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}

	public InscribedAngleArc(int difficulty){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
		this.difficulty=difficulty;
		getInstance();
		//question and answer is determined in the getInstance constructor
	}

	private void initiateKeyTheorems(){
		keyTheorem="The measure of an inscribed angle is half the measure of its intercepted arc.";
		falseTheorem1="The measure of an inscribed angle is equal to the measure of its intercepted arc.";
		falseTheorem2="The measure of an inscribed angle is twice the measure of its intercepted arc.";
		falseTheorem3="The measure of an inscribed angle is one-third the measure of its intercepted arc.";
	}

	protected void getInstance(){

		image = new CoordinateImage(300, 300, -10, 10, -10, 10);
		drawDynamicImage(image);


		//initiate question string
		String questionText = "In the diagram below, #"+labels[0]+"#, #"+labels[2]+"#, and #"+labels[1]+"# are points on the circle. If #m\\widehat{"+labels[0]+labels[1]+"}\\;=\\;"+arcExpression;
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		/*
		 * IMPORTANT!
		 * This line is the first line of the answer key and must include the key theorem
		 */
		answerWork.newLine("m\\angle "+labels[0]+labels[2]+labels[1], 
				"\\frac{m\\widehat{ "+labels[0]+labels[1]+"}}{2}",
				keyTheorem,//theorem
				"textbf");

		//variations
		if(difficulty==1){
			questionText+="#, what is the measure of #\\angle "+labels[0]+labels[2]+labels[1]+"#, in degrees?";
			answerWork.newLine("m\\angle "+labels[0]+labels[2]+labels[1], "\\frac{"+arcExpression+"}{2}", 
					"Substitute.");
			answerWork.newLine("m\\angle "+labels[0]+labels[2]+labels[1], (Math.abs(randomAngle1-randomAngle2)/2)+"", 
					"Divide by 2 for final answer.");
		}else{
			answerWork.newLine(inscribedAngleExpression.toString(), "\\frac{"+arcExpression+"}{2}", 
					"Substitute.");
			answerWork.newLine("2\\left("+inscribedAngleExpression.toString()+"\\right)", "2\\left(\\frac{"+arcExpression+"}{2}\\right)", 
					"Multiply both sides by 2.");
			Expression leftResult = new Expression(Ops.distribute(new Term(2), inscribedAngleExpression.getTermsOfExpression()));
			answerWork.newLine(leftResult.toString(), arcExpression.toString(), 
					"Distribute");
			answerWork.addLinearEquationSteps(leftResult,arcExpression, variable+"",xValue);
			if(difficulty==2 || difficulty==3){
				questionText+="# and #m\\angle "+labels[0]+labels[2]+labels[1]+"\\;=\\;"+inscribedAngleExpression+"," +
						"# what is the value of "+variable+"?";

			}else{
				questionText+="# and #m\\angle "+labels[0]+labels[2]+labels[1]+"\\;=\\;"+inscribedAngleExpression+"," +
						"# what is the measure of #m\\angle "+labels[0]+labels[2]+labels[1]+"#, in degrees?";
				int m = 1;
				int b = 0;
				if(inscribedAngleExpression.getTermsOfExpression()[0].getType().equals(Term.VARIABLE_TYPE)){
					m=inscribedAngleExpression.getTermsOfExpression()[0].getCoefficient();
					b=inscribedAngleExpression.getTermsOfExpression()[1].getCoefficient();
				}else if(inscribedAngleExpression.getTermsOfExpression().length==1){
					b=inscribedAngleExpression.getTermsOfExpression()[0].getCoefficient();
				}else{
					m=inscribedAngleExpression.getTermsOfExpression()[1].getCoefficient();
					b=inscribedAngleExpression.getTermsOfExpression()[0].getCoefficient();
				}
				if(m!=1){
					answerWork.newLine("m\\angle "+labels[0]+labels[2]+labels[1],
							m+"\\left("+xValue+"\\right)\\;"+answerWork.getLeadingOperation(b), 
							"Plug in the value of "+variable);
				}else if(inscribedAngleExpression.getTermsOfExpression().length==1){

				}else{
					answerWork.newLine("m\\angle "+labels[0]+labels[2]+labels[1],
							xValue+"\\;"+answerWork.getLeadingOperation(b), 
							"Plug in the value of "+variable);	
				}
				answerWork.newLine("m\\angle "+labels[0]+labels[2]+labels[1],(Math.abs(randomAngle1-randomAngle2))/2+"", 
						"Final answer.");
			}
		}

		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}

	public void drawDynamicImage(CoordinateImage image){
		labels = Variable.randCapVars(3);
		circle = new Circle(new CoordinatePoint(5.0, 0));
		randomAngle1 = 0;
		randomAngle2 = Ops.randomInt(40, 270);
		while ((randomAngle2-randomAngle1)%2!=0){
			randomAngle2 +=1;
		}
		int randomAngle3 = Ops.randomInt(randomAngle2+30, 330);
		CoordinatePoint p1 = circle.getPointOnCircle(randomAngle1, true);
		CoordinatePoint p2 = circle.getPointOnCircle(randomAngle2, true);
		CoordinatePoint p3 = circle.getPointOnCircle(randomAngle3, true);

		double angle = Ops.randomDouble(0, Math.PI);
		p1.rotate(angle, false);
		p2.rotate(angle, false);
		p3.rotate(angle, false);

		image.drawCircle(circle);
		image.drawPoint(p1);
		image.drawPoint(p2);
		image.drawPoint(p3);
		image.drawSegment(p1, p3);
		image.drawSegment(p2, p3);		
		int angleDegrees = (int)(angle*180/Math.PI);
		image.drawCircleLabel(""+labels[0], circle, randomAngle1+angleDegrees, true);
		image.drawCircleLabel(""+labels[1], circle, randomAngle2+angleDegrees, true);
		image.drawCircleLabel(""+labels[2], circle, randomAngle3+angleDegrees, true);

		xValue=Ops.randomInt(1, 12);
		variable=Variable.randVar();

		boolean arcContainsVariable=false;
		boolean angleContainsVariable=false;
		if(difficulty==2||difficulty==3){
			if(Math.random()<.5){
				arcContainsVariable=true;
				angleContainsVariable=false;
			}else{
				arcContainsVariable=false;
				angleContainsVariable=true;
			}
		}if(difficulty==4){
			arcContainsVariable=true;
			angleContainsVariable=true;
		}
		arcExpression = new LinearExpression(xValue, variable, Math.abs(randomAngle1-randomAngle2), arcContainsVariable).getExpression();
		inscribedAngleExpression = new LinearExpression(xValue, variable, Math.abs(randomAngle1-randomAngle2)/2, angleContainsVariable).getExpression();


		if(arcContainsVariable&&angleContainsVariable){
			while((inscribedAngleExpression.getTermsOfExpression()[0].getCoefficient()*2==arcExpression.getTermsOfExpression()[0].getCoefficient())){
				inscribedAngleExpression = new LinearExpression(xValue, variable, Math.abs(randomAngle1-randomAngle2)/2, angleContainsVariable).getExpression();
			}
		}

		int location = Math.abs(randomAngle1+randomAngle2)/2+angleDegrees;
		image.drawCircleLatex(arcExpression.toString(), circle, location, true);

		dynamicImage=image.getImage();
	}


}
