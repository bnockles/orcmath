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
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;



/**
 * @author bnockles
 *
 */
public class TriangleAngleBisector extends TriangleType {


	private Triangle triangle;
	private CoordinateSegment bisectorLine;
	private double[] proportionParts;
	private double[] triangleSides;
	protected int variableValue;
	protected String var;
	private boolean orientation;
	//let each problem be of the form a/b = c/d
	protected double partA;
	protected double partB;
	protected Expression solutionExpression;
	protected String partToFind;
	protected String compPartToFind;

	//variation
	protected int type;
	protected static final int TYPE_PLAIN = 0;
	protected static final int TYPE_LINEAR = 1;
	protected static final int TYPE_QUADRATIC = 2;
	protected static final int TYPE_WHOLE = 3;

	//names
	private String segment1;
	private String segment2;
	private String part1;
	private String part2;

	protected Expression a;
	protected Expression b;
	protected Expression c;
	protected Expression d;
	protected Expression whole;
	/**
	 * @param difficulty
	 */
	public TriangleAngleBisector(int difficulty) {
		super(difficulty);
		initiateKeyTheorems();
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
		getInstance();
	}


	private void initiateKeyTheorems(){
		keyTheorem="The angle bisector of an angle of a triangle divides the opposite side into two segments that are proportional to the other two sides of the triangle.";
		falseTheorem1="If a line passes through two sides of a triangle and is parallel to the third side, then it divides the other two sides proportionally.";
		falseTheorem2="Triangle Inequality";
		falseTheorem3="The cenroid of a triangle divides each median into a ratio of 2:1";
	}

	protected double[] createProportionalSides(){
		//create a proportion first
		double scale = Ops.randomSimpleDouble(1.5, 4.0, difficulty > 2);
		int a = Ops.randomInt(3,15);
		double b = a*scale;
		while((int)b != b){
			a = Ops.randomInt(3,15);
			b = a*scale;
		}

		int aDen = Ops.randomInt(3, 15);
		while(aDen == a){
			aDen = Ops.randomInt(3, 14);
		}

		double bDen = aDen*scale;
		double[] values = {a, aDen, b, bDen};
		return values;
	}

	@Override
	public Triangle createTriangle() {
		orientation = (Math.random()<.5)?true:false;
		segment1 = (orientation)?""+labels[1]+labels[2]:""+labels[2]+labels[0];
		segment2 = (orientation)?""+labels[2]+labels[0]:""+labels[1]+labels[2];
		part1 = (orientation)?""+labels[1]+labels[3]:""+labels[3]+labels[0];
		part2 = (orientation)?""+labels[3]+labels[0]:""+labels[1]+labels[3];

		do{
			proportionParts = createProportionalSides();
			partA = proportionParts[1];
			partB = proportionParts[3];
			triangleSides =new double[3];
			triangleSides[0] = (orientation)?proportionParts[0]:proportionParts[2];
			triangleSides[1] = (orientation)?proportionParts[2]:proportionParts[0];
			triangleSides[2] = proportionParts[1]+proportionParts[3];

		}while(hasDuplicateOrImpossibleLengths(triangleSides));



		//as a result, there will be a small triangle with int side lengths and
		//although the lengths of the given sides will be integer length


		triangle = new Triangle(triangleSides[0], triangleSides[1], triangleSides[2]);
		//				System.out.println("CHECK DIMENSIONS: "+triangleSides[0]+","+triangleSides[1]+","+triangleSides[2]);


		determineBisectorSegment();




		var = Variable.randVar()+"";

		//known1, known2, unknown 1, unknown 2
		Expression[] e = createExpressions();

		int aIndex =(orientation)?0:2;
		int cIndex =(orientation)?2:0;
		a = e[aIndex];
		b = e[1];
		c = e[cIndex];
		d = e[3];


		return triangle;
	}

	protected Expression[] createExpressions() {
		Expression[] e = new Expression[4];
		//set up all values as constants by default
		e[0] = Expression.constantExpression(triangleSides[0]);
		e[1] = Expression.constantExpression(partA);
		e[2] = Expression.constantExpression(triangleSides[1]);
		e[3] = Expression.constantExpression(partB);


		String[] parts = {segment1, part1,segment2,part2};

		double[] constants = {triangleSides[0],partA,triangleSides[1],partB};

		Term xTerm = new Term(var);

		type = makeType();
		if(type == TYPE_PLAIN){
			int rand = (int) (Math.random()*4);
			while((int)constants[rand] != constants[rand]){
				rand = (int) (Math.random()*4);
			}
			variableValue = (int) constants[rand];

			e[rand] = new Expression(xTerm);
			solutionExpression = e[rand];
			partToFind = var;
		}else if(type == TYPE_LINEAR){
			int rand = (int) (Math.random()*4);
			while((int)constants[rand] != constants[rand]){
				rand = (int) (Math.random()*4);
			}
			variableValue = (int) Ops.randomNotZero(-6, 6);
			e[rand] = Expression.linearExpression((int)constants[rand],variableValue,var,difficulty);
			solutionExpression = e[rand];
			partToFind = parts[rand];
		}else if(type == TYPE_WHOLE){
			int rand = (int) (Math.random()*2)*2+1;//value is either 1 or 3
			int randComp = (rand+2)%4;
			e[rand] = new Expression(xTerm);
			variableValue = 1;
			Term wholeValue = new Term(new Fraction(triangleSides[2]));
			Term[] t = {wholeValue, new Term(-1,var)};
			whole = new Expression(wholeValue);
			e[randComp] = new Expression(t);
			solutionExpression = e[rand];
			partToFind = parts[rand];
			compPartToFind = parts[randComp];
		}

		return e;
	}

	private int makeType() {
		if(difficulty == 1)return TYPE_PLAIN;
		if(Math.random() < .5 )return TYPE_LINEAR;
		else return TYPE_WHOLE;
	}


	private void determineBisectorSegment() {
		double[] bisector = DrawableOps.getLinearEquation(triangle.getIncenter(), triangle.getVertexC());
		double[] oppositeSide = DrawableOps.getLinearEquation(triangle.getVertexA(), triangle.getVertexB());
		CoordinatePoint p = DrawableOps.getIntersectionOfLines(bisector, oppositeSide);
		bisectorLine = new CoordinateSegment(p, triangle.getVertexC());

	}


	/* (non-Javadoc)
	 * @see com.orcmath.drawabletype.TriangleType#initLabels()
	 */
	@Override
	public char[] initLabels() {
		return Variable.randCapVars(4);
	}

	/* (non-Javadoc)
	 * @see com.orcmath.drawabletype.TriangleType#drawExtras(com.orcmath.drawable.CoordinateImage)
	 */
	@Override
	public void drawExtras(CoordinateImage image) {
		image.drawSegment(bisectorLine);
		image.drawAngleVertexLabel(""+labels[3], triangle.getVertexC(), bisectorLine.getEndpoint1(), triangle.getVertexC());


	}



	/* (non-Javadoc)
	 * @see com.orcmath.drawabletype.TriangleType#initiateString()
	 */
	@Override
	protected String initiateString() {



		String s = "In the diagram below of #\\triangle "+labels[0]+labels[1]+labels[2]+"#, #\\overline{"+labels[2]+labels[3]+"}# bisects #\\angle "+labels[0]+labels[2]+labels[1]+"#."; 
		if(type!=TYPE_WHOLE){
			s+= "If #"+segment1+" = "+a+"#, #"+segment2+" = "+c+"#, #"+part1+"="+b+",# and #"+part2+" = "+ d+"#, find the measure of #"+partToFind+"#.";
		}
		else{
			s+= "If #"+segment1+" = "+a+"#, #"+segment2+" = "+c+"#, and #"+labels[0]+labels[1]+"="+whole+"#, find the measure of #"+partToFind+"#.";
		}
		return s;
	}



	/* (non-Javadoc)
	 * @see com.orcmath.drawabletype.TriangleType#initAnswerWork(com.orcmath.local.WorkTable)
	 */
	@Override
	public void initAnswerWork(WorkTable answerWork) {
		if(type==TYPE_WHOLE){
			answerWork.newTextLine("Let #"+partToFind+"="+var+"#, then #"+compPartToFind+"="+whole+"-"+var+"#.");		
		}
		answerWork.newLine("\\frac{"+segment1+"}{"+part1+"}", 
				"\\frac{"+segment2+"}{"+part2+"}",
				keyTheorem,//theorem
				"textbf");
		answerWork.newLine("\\frac{"+a+"}{"+b+"}", 
				"\\frac{"+c+"}{"+d+"}",
				"Substitute");
		Expression[] ratio1Simp;
		Expression[] ratio2Simp;
		double[] ratioValues1 = {triangleSides[0],partA};
		double[] ratioValues2 = {triangleSides[1],partB};
		if(Ops.areInts(ratioValues1)){
			ratio1Simp = Ops.simplifyRatio(a, b);

		}else{
			ratio1Simp = new Expression[2];
			ratio1Simp[0] = a;
			ratio1Simp[1] = b;
		}
		if(Ops.areInts(ratioValues2)){
			ratio2Simp = Ops.simplifyRatio(c, d);

		}else{
			ratio2Simp = new Expression[2];

			ratio2Simp[0] = c;
			ratio2Simp[1] = d;
		}
		if(!("\\frac{"+ratio1Simp[0]+"}{"+ratio1Simp[1]+"}").equals("\\frac{"+a+"}{"+b+"}") || !("\\frac{"+ratio2Simp[0]+"}{"+ratio2Simp[1]+"}").equals("\\frac{"+c+"}{"+d+"}")){
			answerWork.newLine("\\frac{"+ratio1Simp[0]+"}{"+ratio1Simp[1]+"}", 
					"\\frac{"+ratio2Simp[0]+"}{"+ratio2Simp[1]+"}",
					"Simplify.");
		}

		if(type != TYPE_QUADRATIC){
			answerWork.addSolveProportionLinearSteps(ratio1Simp[0], ratio1Simp[1], ratio2Simp[0], ratio2Simp[1], var, variableValue);
			if(type == TYPE_LINEAR || type == TYPE_PLAIN){
				answerWork.plugIn(var, variableValue, solutionExpression);
			}
		}
	}





	private boolean isPlainType() {
		return type == TYPE_PLAIN;
	}

}
