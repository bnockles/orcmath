package com.orcmath.drawabletype;

import java.util.ArrayList;

import com.orcmath.drawable.Circle;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.Triangle;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

public abstract class TriangleType extends DynamicType {

	
	protected CoordinateImage image;
	protected char[] labels;
	protected Triangle triangle;
	
	
	public TriangleType(int difficulty) {
		this.difficulty=difficulty;
		this.triangle = createTriangle();
	}
	
	public void setTriangle(Triangle t){
		this.triangle = t;
	}
	
	public abstract Triangle createTriangle();
	
	public abstract char[] initLabels();

	protected void drawDynamicImage(CoordinateImage image){
		//names of points
		labels = initLabels();
		image.drawTriangle(triangle);
		image.drawAngleVertexLabel(labels[0]+"", triangle.getVertexB(), triangle.getVertexA(), triangle.getVertexC());
		image.drawAngleVertexLabel(labels[1]+"", triangle.getVertexA(), triangle.getVertexB(), triangle.getVertexC());
		image.drawAngleVertexLabel(labels[2]+"", triangle.getVertexA(), triangle.getVertexC(), triangle.getVertexB());
		drawExtras(image);
		dynamicImage = image.getImage();
	}
	
	/**
	 * Called after triangle is drawn, draws any extra features
	 * @param image
	 */
	public abstract void drawExtras(CoordinateImage image);

	protected abstract String initiateString();
	
	protected void getInstance(){
		//step two, determine the type of question and the objective
		image = new CoordinateImage(600, 600, -10, 10, -10, 10);
		drawDynamicImage(image);
		//from the image, take point names that are used in the question text

		
				String questionText =initiateString();
		
				WorkTable answerWork = new WorkTable();
				answerWork.addHorizontalLine();
				initAnswerWork(answerWork);
//				answerWork.newLine("\\left("+product1Part1+"\\right)"+"\\left("+product1Part2+"\\right)", 
//						"\\left("+product2Part1+"\\right)"+"\\left("+product2Part2+"\\right)",
//						keyTheorem,//theorem
//						"textbf");
//				ArrayList<Expression> leftSide= new ArrayList<Expression>();
//				ArrayList<Expression> rightSide= new ArrayList<Expression>();
				//there is usually two types of questions, questions where part 2 is added to part 1, and questions where i
//				if(part2IsAddedToPart1){
//					leftSide.add(expressionProduct1Part1);
//					rightSide.add(expressionProduct2Part1);
//				}
//				leftSide.add(expressionProduct1Part2);
//				rightSide.add(expressionProduct2Part2);
//				answerWork.newLine("\\left("+expressionProduct1Part1+"\\right)"+"\\left("+Format.combineExpressionsOperation(leftSide)+"\\right)", 
//						"\\left("+expressionProduct2Part1+"\\right)"+"\\left("+Format.combineExpressionsOperation(rightSide)+"\\right)",
//						"Substitution");
//				Term[] left=expressionProduct1Part2.getTermsOfExpression();
//				Term[] right=expressionProduct2Part2.getTermsOfExpression();
//				if(part2IsAddedToPart1){
//					left = Ops.combineExpressions(expressionProduct1Part1, expressionProduct1Part2);
//					right = Ops.combineExpressions(expressionProduct2Part1, expressionProduct2Part2);
//		
//					answerWork.newLine("\\left( "+expressionProduct1Part1+"\\right)\\left( "+Format.termArrayToString(left)+"\\right)", 
//							"\\left( "+expressionProduct2Part1+"\\right)\\left( "+Format.termArrayToString(right)+"\\right)",
//							"Combine like terms");
//				}
//		
//				left = Ops.distribute(expressionProduct1Part1.getTermsOfExpression(), left);
//				right = Ops.distribute(expressionProduct2Part1.getTermsOfExpression(), right);
//				answerWork.newLine(Format.termArrayToString(left), 
//						Format.termArrayToString(right),
//						"Distribute");
//		
//		
//				if(left.length==4 || right.length==4){
//					left = Ops.combineLikeTerms(left);
//					right = Ops.combineLikeTerms(right);//combine like terms, if the result after factoring is quadratic (four terms)
//					answerWork.newLine(Format.termArrayToString(left), Format.termArrayToString(right),"Combine like terms");
//				}
//		
//		
//		
//		
//				if(Ops.getDegreeOfTermArray(left)<2 && Ops.getDegreeOfTermArray(right)<2) answerWork.addLinearEquationSteps(new Expression(left), new Expression(right), ""+variable, xValue);
//				else answerWork.addQuadraticEquationSteps(new Expression(left), new Expression(right), ""+variable, xValue);
		
				//variations
		
//				questionText+="# If #"+product1Part1+"="+expressionProduct1Part1+",# ";
//				if(!product1IsSquare){
//					questionText+="#"+product1Part2+"="+expressionProduct1Part2+",# ";
//				}
//				if(product2IsSquare){
//					questionText+="and #"+product2Part1+"="+expressionProduct2Part1+",#";
//				}else{
//					questionText+="#"+product2Part1+"="+expressionProduct2Part1+",# and #"+product2Part2+"="+expressionProduct2Part2+",#";
//				}
//		
//				questionText+=" what is the value of #"+variable+"?";
		
		
				question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
				answerWork.finish();//cannot omit this or there will be an error!
				answer=answerWork.getLatexTabular();
	}

	public abstract void initAnswerWork(WorkTable answerWork);

	
}
