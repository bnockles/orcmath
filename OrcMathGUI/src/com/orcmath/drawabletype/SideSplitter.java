/*******************************************************************************
 * Copyright (c) 2017 Benjamin Nockles
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

import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.CoordinateSegment;
import com.orcmath.drawable.Triangle;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class SideSplitter extends TriangleType{

	private Triangle triangle;
	private CoordinateSegment parallelLine;
	protected int[] smallTriangleSides;
	protected double[] largeTriangleSides;
	private double scaleFactor;
	//	private int knownSide;


	protected int vertexLabelIndex;
	protected int base1LabelIndex;
	protected int base2LabelIndex;
	protected int cut1LabelIndex;
	protected int cut2LabelIndex;

	protected static final int TYPE_DIFFERENCE = 0;
	protected static final int TYPE_WHOLE = 1;
	protected int type; 
	protected boolean findTopPart;

	protected int variableValue;
	protected String var;
	//let each problem be of the form a/b = c/d
	protected Expression a;
	protected Expression b;
	protected Expression c;
	protected Expression d;


	protected String segment1;
	protected String segment2;
	protected String whole1;
	protected String whole2;
	protected String part1;
	protected String part2;


	public SideSplitter(int difficulty) {
		super(difficulty);
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
		getInstance();
	}

	private void initiateKeyTheorems(){
		keyTheorem="If a line passes through two sides of a triangle and is parallel to the third side, then it divides the other two sides proportionally.";
		falseTheorem1="The line segment connecting the midpoints of two sides of a triangle is parallel to the third side and falf its length.";
		falseTheorem2="Triangle Angle Bisector Theorem";
		falseTheorem3="Centroid Ratio Theorem";
	}

	protected int getMaxSideLength(){
		return 15;
	}
	
	protected double makeAScaleFactor(){
		return Ops.randomSimpleDouble(2.5, 4.5, difficulty>2);
	}
	
	@Override
	public Triangle createTriangle() {
		smallTriangleSides = new int[3];
		largeTriangleSides = new double[3];

		do{
			int[] sides = createUniqueSideLengths(difficulty,3,getMaxSideLength());
			smallTriangleSides[0] = sides[0];
			smallTriangleSides[1] = sides[1];
			smallTriangleSides[2] = sides[2];

			//		knownSide = Ops.randomInt(1, 1);//0 or 1
			//choose which of the two sides 
			//create a scaleFactor

			scaleFactor = makeAScaleFactor();

			makeIntegerthroughScaling();
		}while(hasDuplicateOrImpossibleLengths(smallTriangleSides));



		//as a result, there will be a small triangle with int side lengths and
		//although the lengths of the given sides will be integer length


		triangle = new Triangle(smallTriangleSides[0]*scaleFactor, smallTriangleSides[1]*scaleFactor, smallTriangleSides[2]*scaleFactor);
		//		System.out.println("CHECK DIMENSIONS: "+smallTriangleSides[0]+","+smallTriangleSides[1]+","+smallTriangleSides[2]);

		largeTriangleSides[0] = triangle.getSideA();
		largeTriangleSides[1] = triangle.getSideB();
		largeTriangleSides[2] = triangle.getSideC();

		determineParallelSegment();

		//define values
		type = randomType();


		var = "x";

		//known1, known2, unknown 1, unknown 2
		Expression[] e = createExpressions();

		c = e[0];
		d = e[1];
		a = e[2];
		b = e[3];

		return triangle;
	}


	protected int randomType(){
		double typeTest = Math.random();
		if(typeTest < 0){
			return TYPE_DIFFERENCE;
		}else{
			return TYPE_WHOLE;
		}		
	}


	private void makeIntegerthroughScaling(){
		if((int)scaleFactor != scaleFactor){
			int index = indexOfNonInteger();
			while(index != -1){
				smallTriangleSides[index]++;
				index = indexOfNonInteger();
			}
		}
	}

	private int indexOfNonInteger() {
		for(int i = 0; i <smallTriangleSides.length; i++){
			int sideLength = smallTriangleSides[i];
			if((int)(scaleFactor * sideLength) != (scaleFactor * sideLength))return i;
		}
		return -1;
	}

	protected Expression[] createExpressions() {
		Expression[] e = new Expression[4];
		//known1, known2, unknown 1, unknown 2
		//		int i =(knownSide==1)?1:2;
		//		int j =(knownSide==1)?2:1;
		int i = 1;
		int j = 2;
		e[0] = Expression.constantExpression(smallTriangleSides[j]);
		e[1] = Expression.constantExpression((int)(largeTriangleSides[j] - smallTriangleSides[j]));
		if(isPlainType()){
			e[2] = Expression.constantExpression(smallTriangleSides[i]);
			e[3] = Expression.constantExpression((int)(largeTriangleSides[i] - smallTriangleSides[i]));
			Term[] ts = new Term[1];
			var = Variable.randVar()+"";
			ts[0] = new Term(var);
			if(Math.random() < .5){//top is unknown
				findTopPart = true;
				e[2] = new Expression(ts);
				variableValue = smallTriangleSides[i];
			}else{
				findTopPart = false;
				e[3] = new Expression(ts);
				variableValue = (int)(largeTriangleSides[i] - smallTriangleSides[i]);
			}
		}else if(type == TYPE_DIFFERENCE){
			Term[] ts = new Term[2];
			ts[0] = new Term(var);
			if(Math.random() < .5){//top is unknown
				findTopPart = true;
				ts[1] = new Term((int)(2*smallTriangleSides[i]-largeTriangleSides[i]));
				e[3] = new Expression(ts[0]);
				e[2] = new Expression(ts);
				variableValue = (int)(largeTriangleSides[i]-smallTriangleSides[i]);
			}else{//bottom is unknown
				findTopPart = false;
				ts[1] = new Term((int)(largeTriangleSides[i]-2*smallTriangleSides[i]));	
				e[2] = new Expression(ts[0]);
				e[3] = new Expression(ts);
				variableValue = (int)(smallTriangleSides[i]);
			}
		}else if(type == TYPE_WHOLE){
			Term[] ts = new Term[2];
			ts[0] = new Term((int)(largeTriangleSides[i]));
			ts[1] = new Term(-1,var);

			e[2] = new Expression(ts);
			e[3] = new Expression(new Term(var));
			variableValue = (int)(largeTriangleSides[i]-smallTriangleSides[i]);
		}
		return e;
	}
	
	protected boolean isPlainType(){
		return difficulty < 2;
	}

	private void determineParallelSegment() {
		CoordinateSegment s;
		CoordinatePoint vA;

		vertexLabelIndex = 0;
		base1LabelIndex = 1;
		cut1LabelIndex = 3;
		base2LabelIndex = 2;
		cut2LabelIndex = 4;
		s = triangle.getSegmentA();
		vA = triangle.getVertexA();


		CoordinatePoint s1 = new CoordinatePoint((s.getEndpoint1().getxCoordinate()-vA.getxCoordinate())/scaleFactor+vA.getxCoordinate(), (s.getEndpoint1().getyCoordinate()-vA.getyCoordinate())/scaleFactor+vA.getyCoordinate());
		CoordinatePoint s2 = new CoordinatePoint((s.getEndpoint2().getxCoordinate()-vA.getxCoordinate())/scaleFactor+vA.getxCoordinate(), (s.getEndpoint2().getyCoordinate()-vA.getyCoordinate())/scaleFactor+vA.getyCoordinate());
		parallelLine = new CoordinateSegment(s1, s2);
	}

	/**
	 * Used in difficulty 1 questions
	 * @return String of segment length to be found
	 */
	protected String getPartToFindName(){
		String partToFind = (findTopPart)?segment2:part2;
		return partToFind;
	}

	protected void setSegmentNames(){
		segment1 = Character.toString(labels[vertexLabelIndex])+Character.toString(labels[cut1LabelIndex]);
		segment2 = Character.toString(labels[vertexLabelIndex])+Character.toString(labels[cut2LabelIndex]);
		whole1 = Character.toString(labels[vertexLabelIndex])+Character.toString(labels[base1LabelIndex]);
		whole2 = Character.toString(labels[vertexLabelIndex])+Character.toString(labels[base2LabelIndex]);
		part1 = Character.toString(labels[cut1LabelIndex])+Character.toString(labels[base1LabelIndex]);
		part2 = Character.toString(labels[cut2LabelIndex])+Character.toString(labels[base2LabelIndex]);

	}

	protected String initiateString() {
		setSegmentNames();


		if(isPlainType()){
			String partToFind = getPartToFindName();

			return "In the diagram below of #\\triangle "+labels[0]+labels[1]+labels[2]+"#, #\\overline{"+labels[cut1LabelIndex]+labels[cut2LabelIndex]+"}# is paralel to #\\overline{"+labels[base1LabelIndex]+labels[base2LabelIndex]+"}#."
			+ "If #"+segment1+" = "+c+","+segment2+" = "+a+", "+part2+"="+b+",# and #"+part1+" = "+ d+"#, find the measure of #"+partToFind+"#.";

		}else{
			String text = "In the diagram below of #\\triangle "+labels[0]+labels[1]+labels[2]+"#, #\\overline{"+labels[cut1LabelIndex]+labels[cut2LabelIndex]+"}# is paralel to #\\overline{"+labels[base1LabelIndex]+labels[base2LabelIndex]+"}#.";
			//			int i =(knownSide==1)?1:2;
			int i = 1;
			if(type == TYPE_DIFFERENCE && !isPlainType()){
				text += " If #"+segment1+" = "+c+","+part1+" = "+ d+"#, and #";
				if(findTopPart){
					text += segment2+"# is "+(int)(largeTriangleSides[i]-2*smallTriangleSides[i])+" units shorter than #"+ part2+",# find the measure of #"+segment2+"#.";
				}else{

					text += part2+"# is "+(int)(largeTriangleSides[i]-2*smallTriangleSides[i])+" units longer than #"+ segment2+",# find the measure of #"+part2+"#.";
				}
			}else{
				text += " If #"+segment1+" = "+c+","+part1+" = "+ d+"#, and #"+whole2+"= "+(int)(largeTriangleSides[i])+",# find the measure of #"+part2+"#.";
			}

			return text;

		}
	}

	@Override
	public char[] initLabels() {
		return Variable.randCapVars(5);
	}

	public void drawExtras(CoordinateImage image){
		image.drawSegment(parallelLine);
		image.drawAngleVertexLabel(""+labels[3], triangle.getVertexB(), parallelLine.getEndpoint1(), parallelLine.getEndpoint2());
		image.drawAngleVertexLabel(""+labels[4], triangle.getVertexC(), parallelLine.getEndpoint2(), parallelLine.getEndpoint1());
	}

	@Override
	public void initAnswerWork(WorkTable answerWork) {
		answerWork.newLine("\\frac{"+segment2+"}{"+part2+"}", 
				"\\frac{"+segment1+"}{"+part1+"}",
				keyTheorem,//theorem
				"textbf");
		if(type == TYPE_WHOLE && !isPlainType()){
			//			int i =(knownSide==1)?1:2;
			int i = 1;
			answerWork.newRawCodeLine("\\text{Let }"+segment2+"="+a+"&\\text{ and }&"+ part2 + "="+b +"&\\text{ since }"+segment1+"+"+segment2+"="+(int)(largeTriangleSides[i]));
		}
		answerWork.newLine("\\frac{"+a+"}{"+b+"}", 
				"\\frac{"+c+"}{"+d+"}",
				"Substitute.");
		Expression[] ratio1Simp = Ops.simplifyRatio(a, b);
		Expression[] ratio2Simp = Ops.simplifyRatio(c, d);
		answerWork.newLine("\\frac{"+ratio1Simp[0]+"}{"+ratio1Simp[1]+"}", 
				"\\frac{"+ratio2Simp[0]+"}{"+ratio2Simp[1]+"}",
				"Simplify.");
		solveEquation(answerWork,ratio1Simp, ratio2Simp);

	}

	/**
	 * 
	 * @param answerWork table where answers are printed
	 * @param ratio1Simp left ratio (two Expressions)
	 * @param ratio2Simp right ration (two Expressions)
	 */
	public void solveEquation(WorkTable answerWork, Expression[] ratio1Simp, Expression[] ratio2Simp){
		answerWork.addSolveProportionLinearSteps(ratio1Simp[0], ratio1Simp[1], ratio2Simp[0], ratio2Simp[1], var, variableValue);
		if(!isPlainType() ){
			if(type == TYPE_DIFFERENCE){
				if(findTopPart){
					answerWork.plugIn(var, variableValue, a);
				}else{
					answerWork.plugIn(var, variableValue, b);			
				}
			}else if(type == TYPE_WHOLE){
				answerWork.newLine(var,variableValue+"",
						"Answer.");
			}


		}
	}




}
