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
import com.orcmath.objects.Format;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.LinearExpression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.QuadraticEquation;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class ChordTangentAngle extends DynamicType{
	
	
	
	CoordinateImage image;
	char[] labels;
	Circle circle;
	CoordinatePoint pointOfTangency;
	CoordinatePoint p;
	CoordinatePoint exterior;
	int angle;
	int xValue;
	char variable;
	Expression arcExpression;
	Expression angleExpression;
	Expression outsideAngleExpression;
	private int actualMeasure;
	boolean subtractOutsideFrom360;
	boolean majorAngle;
	boolean linear;
		
	public ChordTangentAngle(){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
	}
	
	public ChordTangentAngle(int difficulty){
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=45;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;
		getInstance();
	}
	
	private void initiateKeyTheorems(){
		keyTheorem="The measure of an angle formed by a tangent and a chord is half the measure of the intercepted arc.";
		falseTheorem1="The measure of an angle formed by a tangent and a chord is twice the measure of the intercepted arc.";
		falseTheorem2="The measure of an angle formed by a tangent and a chord is equal to the measure of the intercepted arc.";
		falseTheorem3="The measure of an angle formed by a tangent and a chord is half the measure of the non-intercepted arc.";
	}
	
	
	public String getOutsideArc(){
		if(majorAngle) return "m\\widehat{"+labels[0]+labels[1]+"}";
		else return "m\\widehat{"+labels[0]+labels[3]+labels[1]+"}";
	}
	
	public String getArc(){
		if(!majorAngle) return "m\\widehat{"+labels[0]+labels[1]+"}";
		else return "m\\widehat{"+labels[0]+labels[3]+labels[1]+"}";
	}
	
	protected void getInstance(){		
		image = new CoordinateImage(750, 300, -10, 10, CoordinateImage.Y_COORDINATE);
		subtractOutsideFrom360=true;
		if(Math.random()<.5)subtractOutsideFrom360=false;
		drawDynamicImage(image);

		String questionText = "In the diagram below, #"+labels[0]+"# and #"+labels[1]+"# are points on the circle.";
		if(angle==180){
			questionText += " If #\\overline{"+labels[1]+labels[0]+"}# is a diameter,";
		}else{
			if(subtractOutsideFrom360){
				questionText += " If #"+getOutsideArc()+"\\;=\\;"+outsideAngleExpression+"#";
			}else{
				questionText += " If #"+getArc()+"\\;=\\;"+arcExpression+"#";
			}
		}
		if(difficulty!=1){
			questionText += " and #m\\angle "+labels[1]+labels[0]+labels[2]+"\\;=\\;"+angleExpression+",#";
		}else{
			questionText +=",";
		}
		if(angle ==180 || difficulty==1 || (difficulty==4 && linear)){
			questionText +=" what is #m\\angle "+labels[1]+labels[0]+labels[2]+"?";
		}else{
			questionText +=" what is the value of #"+variable+"?";
		}
		
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		/*
		 * IMPORTANT!
		 * This line is the first line of the answer key and must include the key theorem
		 */
		answerWork.newLine("m\\angle "+labels[1]+labels[0]+labels[2], 
				"\\frac{"+getArc()+"}{2}",
				keyTheorem,//theorem
				"textbf");
		if(angle==180){
			answerWork.newLine(getArc(), 
					"180",
					"Since #\\overline{"+labels[1]+labels[0]+"}# is a diameter, it inscribes a 180^\\circ arc.");
			answerWork.newLine("m\\angle "+labels[1]+labels[0]+labels[2], 
					"\\frac{180}{2}",
					"Substitution.");
			answerWork.newLine("m\\angle "+labels[1]+labels[0]+labels[2], 
					"90^\\circ",
					"Final answer.");
		}else{
			Term[] resultingExpression;
			if(subtractOutsideFrom360){
							answerWork.newLine(getArc(),
									"360-"+getOutsideArc(),
									"To find #"+getArc()+",# the measure of the inscribed arc, we can subtract #"+getOutsideArc()+"# from the measure of the whole circle");
				Term ts=new Term(360);
				answerWork.newLine(getArc(),
						"360-\\left("+Format.termArrayToString(outsideAngleExpression.getTermsOfExpression())+"\\right)",
						"Substitute.");
				resultingExpression =Ops.combineLikeTerms(Ops.addTermToExpression(ts,new Expression(Ops.distribute(new Term(-1), outsideAngleExpression.getTermsOfExpression()))));
				answerWork.newLine(getArc(),
						Format.termArrayToString(resultingExpression),
						"Combine Like Terms.");
				answerWork.addHorizontalLine();
				answerWork.newLine("m\\angle "+labels[1]+labels[0]+labels[2], 
						"\\frac{"+getArc()+"}{2}",
						"Original theorem");
			}else{
				resultingExpression=arcExpression.getTermsOfExpression();
			}
			answerWork.newLine(""+angleExpression, 
					"\\frac{"+Format.termArrayToString(resultingExpression)+"}{2}",
					"Substitution.");
			if(difficulty==1){
				answerWork.newLine(""+angleExpression, 
						Format.termArrayToString(Ops.distribute(new Term(new Fraction(1,2)), resultingExpression)),
						"Divide.");
			}else{
				answerWork.newLine("2\\left("+angleExpression+"\\right)", 
						"\\frac{2\\left("+Format.termArrayToString(resultingExpression)+"\\right)}{2}",
						"Multiply by 2 on both sides.");
				Expression doubledExpression = new Expression(Ops.distribute(new Term(2), angleExpression.getTermsOfExpression()));
				answerWork.newLine(""+doubledExpression, 
						""+Format.termArrayToString(resultingExpression),
						"Cancel the 2.");
				answerWork.setPositiveSolutionsOnly(true);
				if(linear)answerWork.addLinearEquationSteps(doubledExpression, new Expression(resultingExpression), ""+variable, xValue);
				else answerWork.addQuadraticEquationSteps(doubledExpression, new Expression(resultingExpression), ""+variable, xValue);
				if(difficulty==4 && linear){
					answerWork.plugIn(""+variable, xValue, angleExpression, "m\\angle "+labels[1]+labels[0]+labels[2]);
				}
			}
			//variations
		}


		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	
	public void drawDynamicImage(CoordinateImage image){
		
		labels = Variable.randCapVars(4);
		circle = new Circle(new CoordinatePoint(7.0, 0));
		angle = Ops.randomInt(40, 270);
		if(angle%2==1)angle++;
		int location = (270+angle)%360;	
		if(angle>180)majorAngle=true;
		else majorAngle=false;
		int location2;
		if(majorAngle) location2 = angle-180;
		else location2 = angle/2+90;
		CoordinatePoint p = circle.getPointOnCircle(location, true);
		pointOfTangency = circle.getPointOnCircle(270, true);
		exterior = new CoordinatePoint(8, -7.0);
		CoordinatePoint referencePoint = circle.getPointOnCircle(location2, true);
		
		double theta = Ops.randomDouble(0, 2*Math.PI);
		p.rotate(theta, false);
		pointOfTangency.rotate(theta, false);
		exterior.rotate(theta, false);
		referencePoint.rotate(theta, false);
		
		if(Math.abs(exterior.getxCoordinate())>image.getxMax() || 
				Math.abs(exterior.getyCoordinate())>image.getyMax()){
			double scale = Math.abs(image.getxMax()/exterior.getxCoordinate());
			if(Math.abs(image.getyMax()/exterior.getyCoordinate())<scale) scale = Math.abs(image.getyMax()/exterior.getyCoordinate());
			circle.dilate(scale, scale);
			p.dilate(scale, scale);
			pointOfTangency.dilate(scale, scale);
			exterior.dilate(scale, scale);
			referencePoint.dilate(scale, scale);
		}
		
		image.drawCircle(circle);
		image.drawPoint(p);
		image.drawPoint(pointOfTangency);
		image.drawPoint(exterior);
		if(subtractOutsideFrom360 || majorAngle){
			image.drawPoint(referencePoint);
			image.drawCircleLabel(""+labels[3], circle, referencePoint);
		}
		image.drawSegment(exterior, pointOfTangency);
		image.drawSegment(pointOfTangency, p);		
		image.drawCircleLabel(""+labels[0], circle, pointOfTangency);
		image.drawCircleLabel(""+labels[1], circle, p);
		image.drawCircleLabel(""+labels[2], circle, exterior);
		
		actualMeasure=angle/2;
		xValue=Ops.randomInt(1, 12);
		variable=Variable.randVar();
		initializeExpressions();
		dynamicImage=image.getImage();
	}

	private void initializeExpressions() {
		linear = true;
		if (difficulty==1){
			if(subtractOutsideFrom360){
				Term term = new Term(360-2*actualMeasure);
				outsideAngleExpression=new Expression(term);
				Term term2 = new Term(angle);
				arcExpression=new Expression(term2);
				Term trivial = new Term("x");
				angleExpression=new Expression(trivial);
			}else{
				Term term = new Term(360-2*actualMeasure);
				outsideAngleExpression=new Expression(term);
				Term term2 = new Term(angle);
				arcExpression=new Expression(term2);
				Term trivial = new Term("x");
				angleExpression=new Expression(trivial);
			}
		}else if (difficulty==2){
			boolean variableB=true;
			if(Math.random()<.5)variableB=false;
			if(subtractOutsideFrom360){
				outsideAngleExpression=new LinearExpression(xValue, variable, 360-angle, variableB).getExpression();
				arcExpression=null;
				angleExpression=new LinearExpression(xValue, variable, actualMeasure, !variableB).getExpression();
			}else{
				outsideAngleExpression=null;
				arcExpression=new LinearExpression(xValue, variable, angle,variableB).getExpression();
				angleExpression=new LinearExpression(xValue, variable, actualMeasure, !variableB).getExpression();
			}
		}else if (difficulty==3){
			if(subtractOutsideFrom360){
				outsideAngleExpression=new LinearExpression(xValue, variable, 360-angle, true).getExpression();
				arcExpression=null;
				angleExpression=new LinearExpression(xValue, variable, actualMeasure, true).getExpression();
			}else{
				outsideAngleExpression=null;
				arcExpression=new LinearExpression(xValue, variable, angle, true).getExpression();
				angleExpression=new LinearExpression(xValue, variable, actualMeasure, true).getExpression();
			}
		}else{
			if(Math.random()>.7)linear=false;
			if(linear){
				if(subtractOutsideFrom360){
					outsideAngleExpression=new LinearExpression(xValue, variable, 360-angle, true).getExpression();
					arcExpression=null;
					angleExpression=new LinearExpression(xValue, variable, actualMeasure, true).getExpression();
				}else{
					outsideAngleExpression=null;
					arcExpression=new LinearExpression(xValue, variable, angle, true).getExpression();
					angleExpression=new LinearExpression(xValue, variable, actualMeasure, true).getExpression();
				}
			}else{
				if(subtractOutsideFrom360){
					outsideAngleExpression=new QuadraticEquation(360-angle, variable, xValue, true, false).getRightSideExpression();
					arcExpression=null;
					angleExpression=new LinearExpression(xValue, variable, actualMeasure, false).getExpression();
				}else{
					outsideAngleExpression=null;
					arcExpression=new QuadraticEquation(angle, variable, xValue, true, false).getRightSideExpression();
					angleExpression=new LinearExpression(xValue, variable, actualMeasure, true).getExpression();
				}
			}
		}
	}

	
}
