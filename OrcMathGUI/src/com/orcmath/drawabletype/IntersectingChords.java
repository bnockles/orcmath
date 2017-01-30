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

import java.util.ArrayList;
import java.util.Arrays;

import com.orcmath.drawable.Circle;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.CoordinateSegment;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.LinearExpression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class IntersectingChords extends DynamicType{
	

	
	//fields accessed by methods
	
	char[] labels;
	CoordinateImage image;
	Circle circle;
	CoordinatePoint p1;
	CoordinatePoint p2;
	CoordinatePoint p3;
	CoordinatePoint p4;
	CoordinatePoint intersection;
	int randomAngle1;
	int randomAngle2;
	int randomAngle3;
	int randomAngle4;
	String questionType;
	
	//static fields
	public static int MINIMUM_ARC=40;
	public static ArrayList<String> variations = new ArrayList<String>(Arrays.asList("Solve for Interior Angle"));
	static int lastSelection;
	static int selectionBeforeLast1;
	static int selectionBeforeLast2;
	
	public IntersectingChords(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.35;
	}
	
	public IntersectingChords(int difficulty){
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
		keyTheorem="The measure of an angle formed by two intersecting chords is equal to half the sum of the intercepted arcs.";
		falseTheorem1="The measure of an angle formed by two intersecting chords is equal to the sum of the intercepted arcs.";
		falseTheorem2="The measure of an angle formed by two intersecting chords is equal to the difference of the intercepted arcs.";
		falseTheorem3="The measure of an angle formed by two intersecting chords is equal to half the difference of the intercepted arcs.";	
	}
	
	protected void getInstance(){
		//step two, determine the type of question and the objective
		image = new CoordinateImage(700, 300, -10, 10, CoordinateImage.Y_COORDINATE);
		drawDynamicImage(image);

		//step 3, write the question
		questionType=variations.get(Ops.randomInt(0, variations.size()-1));
		if(questionType.equals(variations.get(0))){
			writeQuestionType1();		
		}
	}
	
	
	
	public void writeQuestionType1(){	
		//prepare the labels
		int arc1measure=Math.abs(randomAngle2-randomAngle1);
		int arc2measure=Math.abs(randomAngle4-randomAngle3);
		if((arc1measure+arc2measure)%2==1){
			arc2measure+=1;
		}
		int angleMeasure=(arc1measure+arc2measure)/2;
		int xValue=Ops.randomNotZero(1, 12);
		char variable = Variable.randVar();
		Expression arc1expression;
		Expression arc2expression;
		Expression angleExpression;

		angleExpression = new LinearExpression(xValue, variable, angleMeasure, true).getExpression();
		
		
		//prepare the image
		if(difficulty==1){
			arc1expression = new LinearExpression(xValue, variable, arc1measure, false).getExpression();
			arc2expression = new LinearExpression(xValue, variable, arc2measure, false).getExpression();
			Term[] xTerm = {new Term(1,"x")};
			angleExpression = new Expression(xTerm);
			image.drawCircleLatex(arc1expression.toString(), circle, (randomAngle1+randomAngle2)/2, true);
			image.drawCircleLatex(arc2expression.toString(), circle, (randomAngle3+randomAngle4)/2, true);
		}else if(difficulty==2){
			Term[] xTerm = {new Term(1,"x")};
			angleExpression = new Expression(xTerm);
			arc1expression = new LinearExpression(xValue, variable, arc1measure, false).getExpression();
			arc2expression = new LinearExpression(xValue, variable, arc2measure, false).getExpression();
		}else if(difficulty==3){
			arc1expression = new LinearExpression(xValue, variable, arc1measure, false).getExpression();
			arc2expression = new LinearExpression(xValue, variable, arc2measure, true).getExpression();
			//while loop eliminates equivalent (same line) equations
			while (2*angleExpression.getTermsOfExpression()[0].getCoefficient()==arc2expression.getTermsOfExpression()[0].getCoefficient()){
				angleExpression = new LinearExpression(xValue, variable, angleMeasure, true).getExpression();
			}
			image.drawCircleLatex(arc1expression.toString(), circle, (randomAngle1+randomAngle2)/2, true);
			image.drawCircleLatex(arc2expression.toString(), circle, (randomAngle3+randomAngle4)/2, true);
		}else{
			arc1expression = new LinearExpression(xValue, variable, arc1measure, true).getExpression();
			arc2expression = new LinearExpression(xValue, variable, arc2measure, true).getExpression();
			//while loop eliminates equivalent (same line) equations
			while (2*angleExpression.getTermsOfExpression()[0].getCoefficient()==arc2expression.getTermsOfExpression()[0].getCoefficient()+arc1expression.getTermsOfExpression()[0].getCoefficient()){
				angleExpression = new LinearExpression(xValue, variable, angleMeasure, true).getExpression();
			}
		}
		
		//initiate question string
		String input="In the diagram below, #"+labels[0]+",# #"+labels[1]+",# #"+labels[2]+",# and #"+labels[3]+"# " +
				"are points on the circle. ";
//		input+="If #m\\widehat{ "+labels[0]+labels[1]+"}=" +arc1expression+"# and #m\\widehat{ "+labels[2]+labels[3]+"}=" +arc2expression+"#," +
//				" and #m\\angle "+labels[0]+labels[4]+labels[1]+"="+angleExpression+"#, what is #m\\angle "+labels[0]+labels[4]+labels[1]+"#, in degrees?";
		
		if(difficulty==1||difficulty==2){
			input+="If #m\\widehat{ "+labels[0]+labels[1]+"}=" +arc1measure+"# and #m\\widehat{ "+labels[2]+labels[3]+"}=" +arc2measure+"#," +
					" what is #m\\angle "+labels[0]+labels[4]+labels[1]+"#?";
		}else{
			input+="If #m\\widehat{ "+labels[0]+labels[1]+"}=" +arc1expression+"# and #m\\widehat{ "+labels[2]+labels[3]+"}=" +arc2expression+"#," +
					" and #m\\angle "+labels[0]+labels[4]+labels[1]+"="+angleExpression+"#, what is #m\\angle "+labels[0]+labels[4]+labels[1]+"#, in degrees?";
		}
		

		question = Problem.getLatexLines(input, "#", QUESTION_WIDTH, "text");
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		answerWork.newLine("m\\angle "+labels[0]+labels[4]+labels[1], "\\frac{m\\widehat{ "+labels[0]+labels[1]+"}+m\\widehat{"+labels[2]+labels[3]+"}}{2}", 
				keyTheorem,"textbf");
		answerWork.newLine(angleExpression.toString(), "\\frac{"+arc1expression+"+"+arc2expression+"}{2}", 
				"Substitute.");

		answerWork.newLine(angleExpression.toString(), "\\frac{"+Format.termArrayToString(Ops.combineExpressions(arc1expression,arc2expression))+"}{2}", 
					"Add.");
		if(difficulty==1||difficulty==2){
			answerWork.newLine("m\\angle "+labels[0]+labels[4]+labels[1], angleMeasure+"", 
					"Final answer");
		}else{
			answerWork.newLine("2\\left("+angleExpression.toString()+"\\right)", Format.termArrayToString(Ops.combineExpressions(arc1expression,arc2expression)), 
					"Multiply both sides by 2.");		
			Term[] combinedRight = Ops.combineExpressions(arc1expression,arc2expression);

			Expression leftResult = new Expression(Ops.distribute(new Term(2), angleExpression.getTermsOfExpression()));
			Expression rightResult = new Expression(combinedRight);
			answerWork.newLine(leftResult.toString(), rightResult.toString(), 
					"Distribute");
			answerWork.addLinearEquationSteps(leftResult,rightResult, variable+"",xValue);
			int m = 1;
			int b = 0;
			if(angleExpression.getTermsOfExpression()[0].getType().equals(Term.VARIABLE_TYPE)){
				m=angleExpression.getTermsOfExpression()[0].getCoefficient();
				b=angleExpression.getTermsOfExpression()[1].getCoefficient();
			}else{
				m=angleExpression.getTermsOfExpression()[1].getCoefficient();
				b=angleExpression.getTermsOfExpression()[0].getCoefficient();
			}
			if(m!=1){
				answerWork.newLine("m\\angle "+labels[0]+labels[4]+labels[1],
					m+"\\left("+xValue+"\\right)\\;"+answerWork.getLeadingOperation(b), 
					"Plug in the value of "+variable);
			}else{
				answerWork.newLine("m\\angle "+labels[0]+labels[4]+labels[1],
						xValue+"\\;"+answerWork.getLeadingOperation(b), 
						"Plug in the value of "+variable);	
			}
			answerWork.newLine("m\\angle "+labels[0]+labels[4]+labels[1],angleMeasure+"", 
					"Final answer.");
		}
		
		answerWork.finish();
		answer=answerWork.getLatexTabular();
//		answer = "\\text{The value of the variable is unknown.}";
		
	}
	
	public void writeQuestionType2(){	
		//prepare the image
		if(difficulty==1){
			image.drawArcLabel(circle,randomAngle2,randomAngle1, true);
			image.drawArcLabel(circle,randomAngle4,randomAngle3, true);
		}else{
			image.drawCircleLabel("equation", circle, (randomAngle1+randomAngle2)/2, true);
			image.drawCircleLabel("equation", circle, (randomAngle3+randomAngle4)/2, true);
		}
		
		//initiate question string
		String theQuestion = "\\begin{array}{l}";
		theQuestion += "\\text{This is a test }";
		theQuestion += "\\end{array}\n";

		question = theQuestion;
		answer = "\\text{The value of the variable is unknown.}";
		
	}

	public void drawDynamicImage(CoordinateImage image){
		//names of points
		labels = Variable.randCapVars(5);
		circle = new Circle(new CoordinatePoint(Ops.randomInt(5, 7), 0));
		int split1 = Ops.randomInt(MINIMUM_ARC, 207);//200
		int split2 = Ops.randomInt(split1+MINIMUM_ARC, 258);//250
		int split3 = Ops.randomInt(split2+MINIMUM_ARC, 309);
		randomAngle1 = (int)(Math.random()*(split1-MINIMUM_ARC)+MINIMUM_ARC);//200
		randomAngle2 = (int)(Math.random()*(split2-split1-MINIMUM_ARC))+split1+MINIMUM_ARC;
		if(difficulty<3){
			while ((randomAngle2-randomAngle1)%2!=0){
				randomAngle2 = (int)(Math.random()*(split2-split1-MINIMUM_ARC))+split1+MINIMUM_ARC;
			}
		}
		randomAngle3 = (int)(Math.random()*(split3-split2-MINIMUM_ARC))+split2+MINIMUM_ARC;
		randomAngle4 = (int)(Math.random()*(360-split3-MINIMUM_ARC))+split3+MINIMUM_ARC;
		if(difficulty<3){
			while ((randomAngle4-randomAngle3)%2!=0){
				randomAngle4 = (int)(Math.random()*(360-split3-MINIMUM_ARC))+split3+MINIMUM_ARC;
			}
		}
		p1 = circle.getPointOnCircle(randomAngle1, true);
		p2 = circle.getPointOnCircle(randomAngle2, true);
		p3 = circle.getPointOnCircle(randomAngle3, true);
		p4 = circle.getPointOnCircle(randomAngle4, true);

		image.drawCircle(circle);
		image.drawPoint(p1);
		image.drawPoint(p2);
		image.drawPoint(p3);
		image.drawPoint(p4);
		CoordinateSegment s1=new CoordinateSegment(p1, p3);
		CoordinateSegment s2=new CoordinateSegment(p2, p4);
		image.drawSegment(p1, p3);
		image.drawSegment(p2, p4);	
		intersection = DrawableOps.getIntersectionOfSegments(s1,s2);
		image.drawPoint(intersection);
		image.drawCircleLabel(""+labels[0], circle, randomAngle1, true);
		image.drawCircleLabel(""+labels[1], circle, randomAngle2, true);
		image.drawCircleLabel(""+labels[2], circle, randomAngle3, true);
		image.drawCircleLabel(""+labels[3], circle, randomAngle4, true);
		
		int[] arcMeasures={randomAngle2-randomAngle1, randomAngle3-randomAngle2,randomAngle4-randomAngle3, 360+randomAngle1-randomAngle4};
		Arrays.sort(arcMeasures);
		if(randomAngle2-randomAngle1==arcMeasures[3]){
			image.drawAngleVertexLabel(""+labels[4], p4, intersection, p3);
		}else if(randomAngle3-randomAngle2==arcMeasures[3]){
			image.drawAngleVertexLabel(""+labels[4], p1, intersection, p4);
		}else if(randomAngle4-randomAngle3==arcMeasures[3]){
			image.drawAngleVertexLabel(""+labels[4], p1, intersection, p2);
		}else{
			image.drawAngleVertexLabel(""+labels[4], p2, intersection, p3);
		}
		dynamicImage=image.getImage();


	}
	
}