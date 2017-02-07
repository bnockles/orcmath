package com.orcmath.drawabletype;

import java.util.ArrayList;

import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.CoordinateSegment;
import com.orcmath.drawable.Triangle;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public class SideSplitter extends TriangleType{

	Triangle triangle;
	CoordinateSegment parallelLine;
	double[] smallTriangleSides;
	double[] largeTriangleSides;
	double scaleFactor;
	
	String segment1;
	String segment2;
	String whole1;
	String whole2;
	String part1;
	String part2;
	

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

	@Override
	public Triangle createTriangle() {
		smallTriangleSides = new double[3];
		largeTriangleSides = new double[3];
		double side1;
		double side2;
		double side3;
		if(difficulty < 4){
			side1 = Ops.randomInt(3, 15);
			side2 = Ops.randomInt(3, 15);
			while(Math.abs(side1-side2) >10 || side1 == side2){
				side2 = Ops.randomInt(3, 15);
			}
			side3 = Ops.randomInt((int)(Math.abs(side1-side2)+1), (int)(Math.abs(side1+side2)-1));
			
			if(difficulty==1){
				scaleFactor = Ops.randomInt(2, 5);
			}else if(difficulty == 2){
				scaleFactor = Ops.randomSimpleDouble(2, 5, false);
			}else if(difficulty > 2){
				scaleFactor = Ops.randomSimpleDouble(2, 5, true);
			}
		}else{
			side1 = Ops.randomSimpleDouble(3,15,false);
			side2 = Ops.randomSimpleDouble(3,15,false);
			while(Math.abs(side1-side2) >10 || side1 == side2){
				side2 = Ops.randomSimpleDouble(3, 15,false);
			}
			side3 = Ops.randomSimpleDouble((int)(Math.abs(side1-side2)+1), (int)(Math.abs(side1+side2)-1),false);
			
			scaleFactor = Ops.randomSimpleDouble(2, 6, true);
		}

		triangle = new Triangle(side1*scaleFactor, side2*scaleFactor, side3*scaleFactor);
		smallTriangleSides[0] = triangle.getSideA()/scaleFactor;
		smallTriangleSides[1] = triangle.getSideB()/scaleFactor;
		smallTriangleSides[2] = triangle.getSideC()/scaleFactor;
		largeTriangleSides[0] = triangle.getSideA();
		largeTriangleSides[1] = triangle.getSideB();
		largeTriangleSides[2] = triangle.getSideC();
		
		determineParallelSegment();
		return triangle;
	}

	private void determineParallelSegment() {
		CoordinateSegment s = triangle.getSegmentA();
		CoordinatePoint vA = triangle.getVertexA();
		CoordinatePoint s1 = new CoordinatePoint((s.getEndpoint1().getxCoordinate()-vA.getxCoordinate())/scaleFactor+vA.getxCoordinate(), (s.getEndpoint1().getyCoordinate()-vA.getyCoordinate())/scaleFactor+vA.getyCoordinate());
		CoordinatePoint s2 = new CoordinatePoint((s.getEndpoint2().getxCoordinate()-vA.getxCoordinate())/scaleFactor+vA.getxCoordinate(), (s.getEndpoint2().getyCoordinate()-vA.getyCoordinate())/scaleFactor+vA.getyCoordinate());
		parallelLine = new CoordinateSegment(s1, s2);
	}

	protected String initiateString() {
		segment1 = Character.toString(labels[0])+Character.toString(labels[4]);
		segment2 = Character.toString(labels[0])+Character.toString(labels[3]);
		whole1 = Character.toString(labels[0])+Character.toString(labels[2]);
		whole2 = Character.toString(labels[0])+Character.toString(labels[1]);
		part1 = Character.toString(labels[4])+Character.toString(labels[2]);
		part2 = Character.toString(labels[3])+Character.toString(labels[1]);
		
		
		
		
		return "In the diagram below of #\\triangle "+labels[0]+labels[1]+labels[2]+"#, #\\overline{"+labels[3]+labels[4]+"}# is paralel to #\\overline{"+labels[1]+labels[2]+"}#."
				+ "If #"+segment1+" = "+smallTriangleSides[1]+","+segment2+" = "+smallTriangleSides[2]+",# and #"+part1+" = "+ (largeTriangleSides[1]-smallTriangleSides[1])+"#, find the measure of #"+part2+"#.";
	}

	@Override
	public char[] initLabels() {
		return Variable.randCapVars(5);
	}
	
	public void drawExtras(CoordinateImage image){
		image.drawSegment(parallelLine);
		image.drawAngleVertexLabel(""+labels[3], triangle.getVertexB(), parallelLine.getEndpoint1(), parallelLine.getEndpoint2());
		image.drawAngleVertexLabel(""+labels[4], triangle.getVertexC(), parallelLine.getEndpoint2(), parallelLine.getEndpoint1());
		image.addGrid(1, 1);
		image.addAxes(1, 1, false);
	}

	@Override
	public void initAnswerWork(WorkTable answerWork) {
		answerWork.newLine("\\frac{"+segment1+"}{"+segment2+"}", 
				"\\frac{"+part1+"}{"+part2+"}",
				keyTheorem,//theorem
				"textbf");
		
		Expression segment1Expresion = new Expression(new Term(new Fraction(smallTriangleSides[1])));
		Expression segment2Expresion = new Expression(new Term(new Fraction(smallTriangleSides[2])));
		Expression whole1Expresion = new Expression(new Term(new Fraction(largeTriangleSides[1]-smallTriangleSides[1])));
		
		answerWork.addSolveProportionLinearSteps(segment1Expresion, whole1Expresion, segment2Expresion, new Expression(new Term("x")), "x", (int)(largeTriangleSides[2]-smallTriangleSides[2]));
	}
	



}
