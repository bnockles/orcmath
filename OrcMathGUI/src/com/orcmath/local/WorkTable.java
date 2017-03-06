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
package com.orcmath.local;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Arrays;

import com.orcmath.drawable.Circle;
import com.orcmath.drawable.Cone;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.DrawableFormat;
import com.orcmath.drawable.Graph;
import com.orcmath.drawable.ReferenceFormula;
import com.orcmath.drawable.Transformation;
import com.orcmath.drawabletype.DrawableOps;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.OrderedPair;
import com.orcmath.objects.SimplestRadicalForm;
import com.orcmath.objects.Term;
import com.orcmath.objects.TermFromString;
import com.orcmath.type.Type;

public class WorkTable {


	String latexTabular;
	
	int explanationWidth=Type.QUESTION_WIDTH/2;
	String symbol="=";
	Expression leftExpression;
	Expression rightExpression;
	Term[] leftTerms;
	Term[] rightTerms;
	boolean positiveSolutionsOnly;
	boolean willCheckWork;
	CoordinateImage image;//only used for questions that have a sing coordinate plane
	
	public CoordinateImage getCoordinatePlane() {
		return image;
	}

	public boolean isPositiveSolutionsOnly() {
		return positiveSolutionsOnly;
	}

	public void setPositiveSolutionsOnly(boolean positiveSolutionsOnly) {
		this.positiveSolutionsOnly = positiveSolutionsOnly;
	}

	public WorkTable(){
		latexTabular="\\begin{tabular}{rclc}";
		positiveSolutionsOnly=false;
	}

	public void newLine(String leftLatex, String rightLatex, String explanation, String textStyle){		
		String text=Problem.getLatexLines(explanation, "#", explanationWidth, textStyle);
		latexTabular+=leftLatex+" & "+symbol+" & "+rightLatex+ " & "+text+"\\\\";
	}
	
	public void newLine(String leftLatex, String rightLatex, String explanation){		
		String text=Problem.getLatexLines(explanation, "#", explanationWidth, "textsf");
		latexTabular+=leftLatex+" & "+symbol+" & "+rightLatex+ " & "+text+"\\\\";
	}
	
	public void newTextLine(String text){		
		String line=Problem.getLatexLines(text, "#", explanationWidth*2-50, "textsf");
		latexTabular+="\\multicolumn{4}{c}{"+line+"}\\\\";
	}
	
	public void newRawCodeLine(String text){
		finish();
		latexTabular+="\\\\\\begin{tabular}{c}"+text+"\\end{tabular}";
		latexTabular+="\\\\\\begin{tabular}{rclc}";
	}
	
	public void addHorizontalLine(){		
		latexTabular+="\\hline ";
	}
	
	public void addSinInverseSteps(String var, String opp, String hyp, String oppLength, String hypLength,
			double answer) {
		plugIntoTrigInverse("sin", "Sine = \\frac{opp}{hyp}", var, opp, hyp, oppLength, hypLength, answer);
	}
	
	public void addCosInverseSteps(String var, String adj, String hyp, String adjLength, String hypLength,
			double answer) {
		plugIntoTrigInverse("cos", "Cosine = \\frac{adj}{hyp}", var, adj, hyp, adjLength, hypLength, answer);
	}
	
	private void plugIntoTrigInverse(String function,String definition, String var, String numName, String denName, String numerator, String denominator, double answer){
		newLine("\\"+function+" "+var, "\\frac{"+numName+"}{"+denName+"}", definition);
		newLine("\\"+function+" "+var, "\\frac{"+numerator+"}{"+denominator+"}", "Substitute.");
		newLine(var+"", "\\"+function+"^{-1}\\left(\\frac{"+numerator+"}{"+denominator+"}\\right)", "Simplify.");
//		newLine(var+"", (Math.asin(oppLength/hypLength))*180/Math.PI+"", "Evaluate.");
		newLine(var+"", answer+"^{\\circ}", "Evaluate.");
	}
	
	public void addTanInverseSteps(String var, String opp, String adj, String oppLength, String adjLength, double answer) {
		plugIntoTrigInverse("tan", "Tangent = \\frac{opp}{adj}", var, opp, adj, oppLength, adjLength, answer);
		
	}
	
	public void addMidpointSteps(String name,int x1,int y1, int x2, int y2, boolean findMidpoint){
		newLine(name, "\\left(\\frac{x_1+x_2}{2},\\frac{y_1+y_2}{2}\\right)", "Midpoint formula");
		if(findMidpoint){
			newLine(name, "\\left(\\frac{"+x1+"+"+x2+"}{2},\\frac{"+y1+"+"+y2+"}{2}\\right)", "Plug in coordinates of ("+x1+","+y1+") and ("+x2+","+y2+")");
			int a = x1+x2;
			int b = y1+y2;
			newLine(name, "\\left(\\frac{"+a+"}{2},\\frac{"+b+"}{2}\\right)", "Add.");
			newLine(name, "\\left("+new Fraction(a, 2).toString()+","+new Fraction(b, 2).toString()+"\\right)", "Simplify.");
		}else{
			newLine("\\left("+x2+","+y2+"\\right)", "\\left(\\frac{"+x1+"+x}{2},\\frac{"+y1+"+y}{2}\\right)", "Plug in coordinates of ("+x1+","+y1+") and the given MIDPOINT ("+x2+","+y2+")");
			addHorizontalLine();
			newLine(""+x2, "\\frac{"+x1+"+x}{2}", "Isolate just the x-coordinate.");
			newLine("2\\left("+x2+"\\right)", "2\\left(\\frac{"+x1+"+x}{2}\\right)", "Multiply by 2 on both sides.");
			newLine(""+2*x2, x1+"+x", "Simplify.");
			setSymbol("\\hspace{1}");
			newLine(getLeadingOperation(x1),getLeadingOperation(x1), "Additive inverse.");
			setSymbol("=");
			newLine(""+(2*x2-x1), "x", "Value of x");
			addHorizontalLine();
			newLine(""+y2, "\\frac{"+y1+"+y}{2}", "Isolate just the y-coordinate.");
			newLine("2\\left("+y2+"\\right)", "2\\left(\\frac{"+y1+"+y}{2}\\right)", "Multiply by 2 on both sides.");
			newLine(""+2*y2, y1+"+y", "Simplify.");
			setSymbol("\\hspace{1}");
			newLine(getLeadingOperation(y1),getLeadingOperation(y1), "Additive inverse.");
			setSymbol("=");
			newLine(""+(2*y2-y1), "y", "Value of y");
			newLine("Endpoint", "\\left("+(2*x2-x1)+","+(2*y2-y1)+"\\right)", "Final answer.");
		}
	}
	public void addDistanceFormulaStepsRadical(int x1,int y1, int x2, int y2){
		newLine("d", "\\sqrt{\\left(x_1-x_2\\right) ^2+\\left(y_1-y_2\\right) ^2}", "Distance formula");
		newLine("d", "\\sqrt{\\left("+x1+"-"+x2+"\\right) ^2+\\left("+y1+"-"+y2+"\\right) ^2}", "Plug in coordinates of ("+x1+","+y1+") and ("+x2+","+y2+")");
		newLine("d", "\\sqrt{\\left("+(x1-x2)+"\\right) ^2+\\left("+(y1-y2)+"\\right)^2}", "Simplify.");
		double a = Math.pow((x1-x2), 2);
		double b = Math.pow((y1-y2), 2);
		newLine("d", "\\sqrt{"+(a+"+"+b)+"}", "Square");
		newLine("d", "\\sqrt{"+(a+b)+"}", "Add.");
		SimplestRadicalForm answer = new SimplestRadicalForm(2,(int)(a+b));
		newLine("d", answer.toString()+"", "Simplify radical.");
	}
	
	public void addPythagoreanTheoremEquationSteps(Expression sideA, Expression sideB, Expression sideC, String variable, int solution) {
		String describeMultiplication = "Multiply.";		
		if(sideA.getTermsOfExpression().length>1 || sideB.getTermsOfExpression().length>1 || sideC.getTermsOfExpression().length>1){
			newLine("\\left("+sideA+"\\right)\\left("+sideA+"\\right)+\\left("+sideB+"\\right)\\left("+sideB+"\\right)",
					"\\left("+sideC+"\\right)\\left("+sideC+"\\right)", 
					"Expand.");
			describeMultiplication="Distribute.";
		}
		Term[] aAfterSquaring = Ops.distribute(sideA.getTermsOfExpression(), sideA.getTermsOfExpression());
		Term[] bAfterSquaring = Ops.distribute(sideB.getTermsOfExpression(), sideB.getTermsOfExpression());
		Term[] cAfterSquaring = Ops.distribute(sideC.getTermsOfExpression(), sideC.getTermsOfExpression());

		int numberOfABTermsUnsimplified = aAfterSquaring.length+bAfterSquaring.length;
		int numberOfCTermsUnsimplified = cAfterSquaring.length;
		sideA = new Expression(aAfterSquaring);
		sideB = new Expression(bAfterSquaring);
		ArrayList<Expression> aAndB = new ArrayList<Expression>();
		
		aAndB.add(sideA);
		aAndB.add(sideB);
		newLine(Format.combineExpressionsOperation(aAndB),
				Format.termArrayToString(cAfterSquaring), 
				describeMultiplication);
		Term[] aAndBCombined = Ops.combineExpressions(sideA, sideB);
		cAfterSquaring=Ops.combineLikeTerms(cAfterSquaring);
		if(aAndBCombined.length<numberOfABTermsUnsimplified || cAfterSquaring.length<numberOfCTermsUnsimplified)
			newLine(Format.termArrayToString(aAndBCombined),
					Format.termArrayToString(cAfterSquaring), 
					"Combine like terms.");
		addQuadraticEquationSteps(new Expression(aAndBCombined), new Expression(cAfterSquaring), variable, solution);
	}

	public Expression[] crossMultiply(Expression num1Expression,
			Expression den1Expression, Expression num2Expression,
			Expression den2Expression){
		newLine("\\left("+den2Expression+"\\right)"+"\\left("+num1Expression+"\\right)", "\\left("+den1Expression+"\\right)"+"\\left("+num2Expression+"\\right)", 
				"Rewritten, equivalent form (after multiplying by the reciprocals of each denominator on both sides.)");
	
		Expression leftResult =new Expression(Ops.distribute(den2Expression.getTermsOfExpression(), num1Expression.getTermsOfExpression()));
		Expression rightResult =new Expression(Ops.distribute(den1Expression.getTermsOfExpression(), num2Expression.getTermsOfExpression()));
		
		newLine(leftResult+"", ""+rightResult, 
				"Simplify.");
		Expression[] result = {leftResult, rightResult};
		return result;
	}
	
	public Term[] addSolveProportionQuadraticSteps(Expression num1Expression,
			Expression den1Expression, Expression num2Expression,
			Expression den2Expression, String variable, int xValue) {
		
		Expression[] result = crossMultiply(num1Expression, den1Expression, num2Expression, den2Expression);
		addQuadraticEquationSteps(result[0], result[1], variable, xValue);
		return Ops.subtractExpressions(result[0], result[1]);
	}
	
	public void addSolveProportionLinearSteps(Expression num1Expression,
			Expression den1Expression, Expression num2Expression,
			Expression den2Expression, String variable, int xValue) {
		
		Expression[] result = crossMultiply(num1Expression, den1Expression, num2Expression, den2Expression);
		
		addLinearEquationSteps(result[0], result[1], ""+variable, xValue);
	
		
		int numValue1;
		int denValue1;
		int numValue2;
		int denValue2;
		
		if (willCheckWork){
			newTextLine("Check your work!");
			if(num1Expression.containsAVariable()){
				newTextLine("Left numerator:");
				numValue1=plugIn(""+variable, xValue, num1Expression);
				addHorizontalLine();
			}else{
				numValue1=num1Expression.getTermsOfExpression()[0].getCoefficient();
			}
			if(den1Expression.containsAVariable()){
				newTextLine("Left denominator:");
				denValue1=plugIn(""+variable, xValue, den1Expression);
				addHorizontalLine();
			}else{
				denValue1=den1Expression.getTermsOfExpression()[0].getCoefficient();
			}
			if(num2Expression.containsAVariable()){
				newTextLine("Right numerator:");
				numValue2=plugIn(""+variable, xValue, num2Expression);
				addHorizontalLine();
			}else{
				numValue2=num2Expression.getTermsOfExpression()[0].getCoefficient();
			}
			if(den2Expression.containsAVariable()){
				newTextLine("Right denominator:");
				denValue2=plugIn(""+variable, xValue, den2Expression);
				addHorizontalLine();
			}else{
				denValue2=num1Expression.getTermsOfExpression()[0].getCoefficient();
			}
			int lcd=numValue1;
			boolean isLCD=false;
			while(!isLCD){
				if(denValue1%lcd==0 && numValue1%lcd==0){
					isLCD=true;
				}else{
					lcd--;			
				}
			}
			int numValueSimplified=numValue1/lcd;
			int denValueSimplified=denValue1/lcd;
			
			newLine("\\frac{"+numValue1+"}{"+denValue1+"}", "\\frac{"+numValueSimplified+"}{"+denValueSimplified+"}", "Left ratio");
			newLine("\\frac{"+numValue2+"}{"+denValue2+"}", "\\frac{"+numValueSimplified+"}{"+denValueSimplified+"}", "Right ratio");
			newTextLine("The ratios are equivalent");
		}
		
	}

	public void addQuadraticEquationSteps(Expression leftExpress, Expression rightExpress, String variable, int solution) {
		this.leftExpression=leftExpress;
		this.rightExpression=rightExpress;
		Term addOnBothSides;
		leftTerms = leftExpression.getTermsOfExpression();
		rightTerms = rightExpression.getTermsOfExpression();
		int leftCons = 0;
		//chooses which squared term will be added to both sides
		addOnBothSides = getTermToAddOrSubtract(leftTerms, rightTerms, 2, true);
		//subtracts a squared term from both sides, if one is non-zero.
		if(addOnBothSides.getCoefficient()!=0){
			performAdditiveInverse(addOnBothSides, getWidth("\\;\\;+\\;"+Math.abs(leftCons)),0);
		}
		if(Ops.getDegreeOfTermArray(leftTerms)<=1 && Ops.getDegreeOfTermArray(rightTerms)<=1){
			Expression linearLeft = new Expression(leftTerms);
			Expression linearRight= new Expression(rightTerms);
			addLinearEquationSteps(linearLeft, linearRight, variable, solution);
		}else{
			addOnBothSides = getTermToAddOrSubtract(leftTerms, rightTerms, 1, false);
			if(addOnBothSides.getCoefficient()!=0){
				performAdditiveInverse(addOnBothSides, getWidth("\\;\\;+\\;"+Math.abs(leftCons)),0);
			}
			addOnBothSides = getTermToAddOrSubtract(leftTerms, rightTerms, 0, false);
			if(addOnBothSides.getCoefficient()!=0){
				performAdditiveInverse(addOnBothSides, getWidth("\\;\\;+\\;"+Math.abs(leftCons)),0);
			}
			
			/*
			 * At this point, the equation should be simplified
			 *if the square root is all that is needed, we do that,
			 *otherwise we proceed to factor/calculate quadratic formula
			 */
			if (leftTerms.length==1 && rightTerms.length==1){
				newLine("\\sqrt{"+Format.termArrayToString(leftTerms)+"}", "\\sqrt{"+Format.termArrayToString(rightTerms)+"}", 
						"Square root both sides.");
				Term sideARoot = Ops.root(leftTerms[0], 2);
				Term sideBRoot = Ops.root(rightTerms[0], 2);
				newLine(sideARoot.toString(), sideBRoot.toString(), 
						"Simplify.");
			}else{
				Term gcfLeft = Ops.findGCF(leftTerms);
				Term gcfRight = Ops.findGCF(rightTerms);
				if(gcfLeft.getCoefficient()>1 && gcfLeft.getCoefficient()>gcfRight.getCoefficient()){
					Term gcfInverseLeft = new Term(new Fraction(new Term(1),gcfLeft));
					leftTerms = Ops.distribute(gcfInverseLeft, leftTerms);
					setSymbol("\\hspace{1}");
					newLine("\\overline{"+gcfLeft.toString()+"}", "\\overline{"+gcfLeft.toString()+"}", 
							"Divide by the GCF.");
					setSymbol("=");
					newLine(Format.termArrayToString(leftTerms), Format.termArrayToString(rightTerms), 
							"Simplify.");
				}else if (gcfRight.getCoefficient()>1){
					Term gcfInverseRight = new Term(new Fraction(new Term(1),gcfRight));
					rightTerms = Ops.distribute(gcfInverseRight, rightTerms);
					setSymbol("\\hspace{1}");
					newLine("\\overline{"+gcfRight.toString()+"}", "\\overline{"+gcfRight.toString()+"}", 
							"Divide by the GCF.");
					setSymbol("=");
					newLine(Format.termArrayToString(leftTerms), Format.termArrayToString(rightTerms), 
							"Simplify.");
				}
				addHorizontalLine();
				//for the sake of simplicity, represent the equation by putting the zero on the right and the terms on the left

				Term[] takeFrom = leftTerms;
				if(Ops.getDegreeOfTermArray(rightTerms)==2) takeFrom=rightTerms;
				newLine(Format.termArrayToString(takeFrom), "0", 
						"This is a quadratic equation because it contains a squared term");
				addSolvingForQuadraticRootsSteps(takeFrom, false);//NOTE: "false" solve for x and not y
			}
		}
	}
	
	public void setLeftExpression(Expression leftExpression){//for checking solution in original work
		this.leftExpression=leftExpression;
	}
	
	public void setRightExpression(Expression rightExpression){//for checking solution in original work
		this.rightExpression=rightExpression;
	}
	
	public void addSolvingForQuadraticRootsSteps(Term[] takeFrom, boolean orderedPairSolution){
//		System.out.println("WorkTable: These are the terms: "+Format.termArrayToString(takeFrom));
		Term aTerm = new Term(0);
		for(int index = 0; index<takeFrom.length; index++){
			if(takeFrom[index].getDegree()==2)aTerm=Term.getCopy(takeFrom[index]);
		}
		Term bTerm = new Term(0);
		for(int index = 0; index<takeFrom.length; index++){
			if(takeFrom[index].getDegree()==1)bTerm=Term.getCopy(takeFrom[index]);
		}
		Term cTerm = new Term(0);
		for(int index = 0; index<takeFrom.length; index++){
			if(takeFrom[index].getDegree()==0)cTerm=Term.getCopy(takeFrom[index]);
		}
		if(aTerm.getCoefficient()!=0)
		addSolvingForQuadraticRootsSteps(aTerm, bTerm, cTerm, orderedPairSolution);
	}
	
	public void addSolvingForQuadraticRootsSteps(Term aTerm, Term bTerm, Term cTerm, boolean orderedPairSolution){
	
		Term[] quadraticTerms = new Term[3];
		quadraticTerms[0]=aTerm;
		quadraticTerms[1]=bTerm;
		quadraticTerms[2]=cTerm;
		Term gcd = Ops.findGCF(quadraticTerms);
		
		
		aTerm.setCoefficient(aTerm.getCoefficient()/gcd.getCoefficient());
		bTerm.setCoefficient(bTerm.getCoefficient()/gcd.getCoefficient());
		cTerm.setCoefficient(cTerm.getCoefficient()/gcd.getCoefficient());
		String variableAlone = ""+aTerm.getVariableFactorization().get(0).getBase();
		
		if(gcd.getCoefficient()!=1){
			newLine(Format.termArrayToString(quadraticTerms), "0", "Factor out the GCF, "+gcd.toString());
		}
		
		String quadraticTermsWithTrivialCoefs = aTerm.getCoefficient()+aTerm.getVariables()+
				bTerm.getCoefficient()+bTerm.getVariables()+
				cTerm.getCoefficient();
		
		int descriminant = (int)Math.pow(bTerm.getCoefficient(),2)-4*aTerm.getCoefficient()*cTerm.getCoefficient();
		
		String bSimplified = "";
		if(bTerm.getCoefficient()!=0){
			bSimplified=(-1*bTerm.getCoefficient())+"";
		}
		char vari = aTerm.getVariableFactorization().get(0).getBase();
		if(Ops.isSquare(descriminant)){//can be factored!
			int root1 = (-bTerm.getCoefficient()+(int)Math.sqrt(descriminant))/(2*aTerm.getCoefficient());
			int root2 = (-bTerm.getCoefficient()-(int)Math.sqrt(descriminant))/(2*aTerm.getCoefficient());
			if(bTerm.getCoefficient()==0){
				if(Ops.isSquare(aTerm.getCoefficient()) && cTerm.getCoefficient()<0  && Ops.isSquare(Math.abs(cTerm.getCoefficient()))){
					newTextLine("This is a difference of perfect squares.");
					int aSqrt = (int)Math.sqrt(aTerm.getCoefficient());
					Term aSqrtTerm = new Term(aSqrt,bTerm.getVariables());
					int cSqrt = (int)Math.sqrt(Math.abs(cTerm.getCoefficient()));
					
					Term cSqrtTerm = new Term(cSqrt,vari+"");
					newLine("\\left("+cSqrtTerm+"+"+aSqrtTerm+"\\right)\\left("+cSqrtTerm+"-"+aSqrtTerm+"\\right)", "0", "Factoring a difference of perfect squares");
					setSymbol("\\hspace{3}");
					if(!orderedPairSolution){
						newLine(vari+"="+root1, vari+"="+root2, "Solution.");
					}else{
						newLine(vari+"="+root1, vari+"="+root2, "x values.");
						addHorizontalLine();
						setSymbol("=");
						int y1 = plugIn(vari+"", root1, rightExpression);
						int y2 = plugIn(vari+"", root2, rightExpression);
						setSymbol("\\hspace{3}");
						newLine("\\left("+root1+","+y1+"\\right)", "\\left("+root2+","+y2+"\\right)", "Soutions.");
						
					}
					setSymbol("=");
				}else{
					if(cTerm.getCoefficient()!=0){
						newLine(aTerm.toString(), ""+(-1*cTerm.getCoefficient()), "Subtract "+cTerm.getCoefficient()+" from both sides.");
					}
					Term aCeofReciprocal = new Term(new Fraction(1,aTerm.getCoefficient()));
					Term left = Ops.multiplyTerms(aCeofReciprocal, aTerm);
					Term right = Ops.multiplyTerms(aCeofReciprocal, cTerm);
//					right.setSign(true);
					if(aTerm.getCoefficient()!=1){
						newLine(left.toString(),right.toString(), "Divide "+aTerm.getCoefficient()+" on both sides.");
					}
					setSymbol("\\hspace{3}");
					newLine(bTerm.getVariables()+"="+root1, bTerm.getVariables()+"="+root2, "Take the square root. (Recall that there must be two roots, positive and negative.)");
					setSymbol("=");
				}
			}else if(aTerm.getCoefficient()!=1){//factor by parts
				if(cTerm.getCoefficient()==0){
					newTextLine("Factor out "+bTerm.getVariables());
					String factor1 = "";
					String factor2 = "";
					if(root1!=0)factor1=getLeadingOperation(-1*root1);
					if(root2!=0)factor2=getLeadingOperation(-1*root2);
					
					newLine("\\left("+bTerm.getVariables()+factor1+"\\right)\\left("+bTerm.getVariables()+factor2+"\\right)",
							""+0,
							"Factored form");
					
					
					Term x1=new Term(new Fraction((-1)*bTerm.getCoefficient(),aTerm.getCoefficient()));
					Term x2=new Term(0);

					setSymbol("\\hspace{1}");

					if(!orderedPairSolution){
						newLine(vari+"="+root1, vari+"="+root2, "Solution.");
					}else{
						newLine(vari+"="+root1, vari+"="+root2, "x values.");
						addHorizontalLine();
						setSymbol("=");
						int y1 = plugIn(vari+"", root1, rightExpression);
						int y2 = plugIn(vari+"", root2, rightExpression);
						setSymbol("\\hspace{3}");
						newLine("\\left("+root1+","+y1+"\\right)", "\\left("+root2+","+y2+"\\right)", "Soutions.");
						
					}
					setSymbol("=");
				}
				int product = aTerm.getCoefficient()*cTerm.getCoefficient();
				newTextLine("To factor by parts: Multiply a, ("+aTerm.getCoefficient()+") and c ("+cTerm.getCoefficient()+")to get "+
						(aTerm.getCoefficient()*cTerm.getCoefficient())+". We need to find factors of "+product+
						" that add to get "+bTerm.getCoefficient()+".");
				setSymbol("\\hspace{3}");
				Term partA=new Term(1);
				Term partB=new Term(1);
				int max = Math.abs(product/2);
				if(product<0) max = Math.abs(product);
				for(int cFactor=1; cFactor<=max; cFactor++){
					double k =(double)product/(double)cFactor;
					if(Ops.isInteger(k) && cFactor+(int)k==bTerm.getCoefficient()){
						newLine(cFactor+"\\left("+k+"\\right)="+product, 
								cFactor+"+\\left("+k+"\\right)="+(cFactor+(int)k), 
								cFactor+" and "+(int)k+ " work!");
						partA=new Term(cFactor, bTerm.getVariables());
						partB=new Term((int)k, bTerm.getVariables());
						break;
					}
//					if(Ops.isInteger(z) && (-1)*cFactor+(int)z==bTerm.getCoefficient()){
//						newLine((-1)*cFactor+"\\left("+z+"\\right)="+product, 
//								(-1)*cFactor+"+\\left("+z+"\\right)="+((-1)*cFactor+(int)k), 
//								(-1)*cFactor+" and "+(int)z+ " work!B");
//						partA=new Term((-1)*cFactor, bTerm.getVariables());
//						partB=new Term((int)z, bTerm.getVariables());
//						break;
//					}
//					if(Ops.isInteger(k) && cFactor+(int)k==bTerm.getCoefficient()){
//						newLine(cFactor+"\\left("+k+"\\right)="+product, 
//								cFactor+"+\\left("+k+"\\right)="+(cFactor+(int)k), 
//								cFactor+" and "+(int)k+ " work!C");
//						partA=new Term(cFactor, bTerm.getVariables());
//						partB=new Term((int)k, bTerm.getVariables());
//						break;
//					}
					double z=(-1)*k;
					if(Ops.isInteger(z) && (-1)*cFactor+(int)z==bTerm.getCoefficient()){
						newLine((-1)*cFactor+"\\left("+z+"\\right)="+product, 
								(-1)*cFactor+"+\\left("+z+"\\right)="+((-1)*cFactor+(int)z), 
								(-1)*cFactor+" and "+(int)z+ " work!");
						partA=new Term((-1)*cFactor, bTerm.getVariables());
						partB=new Term((int)z, bTerm.getVariables());
						break;
					}
				}
				
				setSymbol("=");
				
				Term[] part1={aTerm,partA};
				Term[] part2={cTerm,partB};
				Term dTerm=Ops.findGCF(part1);
				if(!aTerm.isPositive)dTerm.setSign(false);
				Term eTerm=Ops.findGCF(part2);
				if(!partB.isPositive)eTerm.setSign(false);
				Term dTermInverse=new Term(new Fraction(new Term(1),dTerm));
				Term eTermInverse=new Term(new Fraction(new Term(1),eTerm));
				newLine("\\left("+aTerm+getLeadingOperation(partA)+ "\\right)\\;+\\;\\left(" +partB+ getLeadingOperation (cTerm)+"\\right)",""+0,"Split into parts");

				Term aTermQuotient=Ops.multiplyTerms(dTermInverse,aTerm);
				Term cTermQuotient=Ops.multiplyTerms(eTermInverse,cTerm);
				Term partAQuotient=Ops.multiplyTerms(dTermInverse,partA);
				Term partBQuotient=Ops.multiplyTerms(eTermInverse,partB);


				newLine(dTerm+"\\left("+aTermQuotient+getLeadingOperation(partAQuotient)+ "\\right)"+ getLeadingOperation(eTerm)+"\\left(" +partBQuotient+ getLeadingOperation (cTermQuotient)+"\\right)",""+0,"Factor.");

				newLine("\\left("+dTerm+ getLeadingOperation(eTerm)+ "\\right)\\left(" +aTermQuotient+ getLeadingOperation (cTermQuotient)+"\\right)",""+0,"Combine factors.");

				Term x1=new Term(new Fraction((-1)*eTerm.getCoefficient(),dTerm.getCoefficient()));
				Term x2=new Term(new Fraction((-1)*cTermQuotient.getCoefficient(),aTermQuotient.getCoefficient()));

				setSymbol("\\hspace{1}");

				newLine(bTerm.getVariables()+"="+x1, bTerm.getVariables()+"="+x2, "x Solution.");
				setSymbol("=");
				
			}else{//factor as usual
				if(cTerm.getCoefficient()!=0){
					newTextLine("Try factors of "+cTerm.getCoefficient()+" that add to get "+bTerm.getCoefficient());
					setSymbol("\\hspace{3}");
					int max = Math.abs(cTerm.getCoefficient()/2);
					if(!cTerm.getSign()) max = Math.abs(cTerm.getCoefficient());
					for(int cFactor=1; cFactor<=max; cFactor++){
						double k =(double)cTerm.getCoefficient()/(double)cFactor;
						if(Ops.isInteger(k) && cFactor+(int)k==bTerm.getCoefficient()){
							newLine(cFactor+"\\left("+(int)k+"\\right)="+cTerm.getCoefficient(), 
									cFactor+"+\\left("+(int)k+"\\right)="+(cFactor+(int)k), 
									cFactor+" and "+(int)k+" multiply to get "+cTerm.getCoefficient()+" and add to get "+bTerm.getCoefficient());
						}
					}
//					addHorizontalLine();
					setSymbol("=");
					newLine("\\left("+bTerm.getVariables()+getLeadingOperation(-1*root1)+"\\right)\\left("+bTerm.getVariables()+getLeadingOperation(-1*root2)+"\\right)",
							""+0,
							"Factored form");
				}else{
					newTextLine("Factor out "+bTerm.getVariables());
					String factor1 = "";
					String factor2 = "";
					if(root1!=0)factor1=getLeadingOperation(-1*root1);
					if(root2!=0)factor2=getLeadingOperation(-1*root2);
					
					newLine("\\left("+bTerm.getVariables()+factor1+"\\right)\\left("+bTerm.getVariables()+factor2+"\\right)",
							""+0,
							"Factored form");

				}
				setSymbol("\\hspace{3}");
				if(!orderedPairSolution){
					newLine(vari+"="+root1, vari+"="+root2, "Solution.");
				}else{
					newLine(vari+"="+root1, vari+"="+root2, "x values.");
					addHorizontalLine();
					setSymbol("=");
					int y1 = plugIn(vari+"", root1, rightExpression);
					int y2 = plugIn(vari+"", root2, rightExpression);
					setSymbol("\\hspace{3}");
					newLine("\\left("+root1+","+y1+"\\right)", "\\left("+root2+","+y2+"\\right)", "Soutions.");
					
				}		
				setSymbol("=");
				}
			if(positiveSolutionsOnly){
				String finalAnswer = "\\{";
				if(root1>=0)finalAnswer+=""+root1;
				if(root1>=0 && root2>=0)finalAnswer+="\\;,\\;";
				if(root2>=0)finalAnswer+=""+root2;
				finalAnswer+="\\}";
				newLine(""+bTerm.getVariables(),
						finalAnswer,
						"Final answer. (Only positive solutions can be used in this situation.)");
			}
		}else if(descriminant>0 || descriminant==0){//cannot be factored, but is real
			if(bTerm.getCoefficient()==0){
				if(cTerm.getCoefficient()!=0){
					newLine(aTerm.toString(), ""+(-1*cTerm.getCoefficient()), "Subtract "+cTerm.getCoefficient()+" from both sides.");
				}
				Term aCeofReciprocal = new Term(new Fraction(1,aTerm.getCoefficient()));
				Term left = Ops.multiplyTerms(aCeofReciprocal, aTerm);
				Term right = Ops.multiplyTerms(aCeofReciprocal, cTerm);
				right.setSign(true);
				if(aTerm.getCoefficient()!=1){
					newLine(left.toString(),right.toString(), "Divide "+aTerm.getCoefficient()+" on both sides.");
				}
				setSymbol("\\hspace{3}");
				newLine(variableAlone+"=\\sqrt{"+right+"}",
						variableAlone+"=-\\sqrt{"+right+"}",
						"Take square root of both sides");
//				right.setSign(true);
				newLine(variableAlone+"="+right.getSimplifiedSquareRootString(),
						variableAlone+"=-"+right.getSimplifiedSquareRootString(),
						"Simplify");
				setSymbol("=");
			}else{
			newTextLine("Use the quadratic fromula:");
			newLine("\\frac{-b\\pm\\sqrt{b^2-4ac}}{2a}",
					"x",
					"Quadratic fromula");
			newLine("\\frac{-\\left("+bTerm.getCoefficient()+"\\right)\\pm\\sqrt{\\left("+bTerm.getCoefficient()+"\\right)^2-4\\left("+aTerm.getCoefficient()+"\\right)\\left("+cTerm.getCoefficient()+"\\right)}}{2\\left("+aTerm.getCoefficient()+"\\right)}",
					"x",
					"Substitution");
			newLine("\\frac{"+bSimplified+"\\pm\\sqrt{"+descriminant+"}}{"+(2*aTerm.getCoefficient())+"}",
					"x",
					"Substitution");
			if(descriminant==0){
				newLine("\\frac{"+bSimplified+"}{"+(2*aTerm.getCoefficient())+"}",
						"x",
						"Simplified answer");
			}
			}
		}else{//imaginary roots
			if(bTerm.getCoefficient()==0){
				if(cTerm.getCoefficient()!=0){
					newLine(aTerm.toString(), ""+(-1*cTerm.getCoefficient()), "Subtract "+cTerm.getCoefficient()+" from both sides.");
					cTerm.setSign(!cTerm.getSign());
				}
				Term aCeofReciprocal = new Term(new Fraction(1,aTerm.getCoefficient()));
				Term left = Ops.multiplyTerms(aCeofReciprocal, aTerm);
				Term right = Ops.multiplyTerms(aCeofReciprocal, cTerm);
				if(aTerm.getCoefficient()!=1){
					newLine(left.toString(),right.toString(), "Divide "+aTerm.getCoefficient()+" on both sides.");
				}
				setSymbol("\\hspace{3}");
				newLine(variableAlone+"=\\sqrt{"+right+"}",
						variableAlone+"=-\\sqrt{"+right+"}",
						"Take square root of both sides");
//				right.setSign(true);
				String rightSide = right.getSimplifiedSquareRootString();
				if(!rightSide.equals("1")){
					newLine(variableAlone+"="+rightSide,
							variableAlone+"=-"+rightSide,
						"Substitute }i=\\sqrt{-1}\\text{ and simplify");
				}else{
					newLine(variableAlone+"=",
							variableAlone+"=-",
							"Substitute }i=\\sqrt{-1}\\text{ and simplify");
				}
				
				setSymbol("=");
			}else{
			
			
			newTextLine("Use the quadratic fromula:");
			newLine("\\frac{-b\\pm\\sqrt{b^2-4ac}}{2a}",
					"x",
					"Quadratic fromula");
			newLine("\\frac{-\\left("+bTerm.getCoefficient()+"\\right)\\pm\\sqrt{\\left("+bTerm.getCoefficient()+"\\right)^2-4\\left("+aTerm.getCoefficient()+"\\right)\\left("+cTerm.getCoefficient()+"\\right)}}{2\\left("+aTerm.getCoefficient()+"\\right)}",
					"x",
					"Substitution");
			newLine("\\frac{"+bSimplified+"\\pm\\sqrt{"+descriminant+"}}{"+(2*aTerm.getCoefficient())+"}",
					"x",
					"Substitution");
			}
			
		}
		if(bTerm.getCoefficient()!=0 && !Ops.isSquare(Math.abs(descriminant))){//finish simplifying quad. formula
			int largestSquareFactor =1;
			int simp=1;
			System.out.print("largest square factor of "+descriminant+" is ");
			for(int test=2; Math.pow(test, 2)<=Math.abs(descriminant); test++){
				if(descriminant%Math.pow(test, 2)==0){
					largestSquareFactor=(int) Math.pow(test, 2);
					simp=test;
					System.out.println(largestSquareFactor);
				}
			}
			boolean simplifiable=false;
			String negativeOneFactor = "";	
			String iResult="";
			if(descriminant<0){
				negativeOneFactor="\\sqrt{-1}";
				iResult="i";
				simplifiable=true;
				descriminant=-descriminant;
			}
			String perfectSquareFactor="";
			String sqrtResult="";
			if(largestSquareFactor>1){
				simplifiable=true;
				perfectSquareFactor="\\sqrt{"+largestSquareFactor+"}";
				sqrtResult=""+simp;
				
			}
			if(simplifiable){
				
				newLine("\\frac{"+bSimplified+"\\pm "+perfectSquareFactor+negativeOneFactor+"\\sqrt{"+(descriminant/largestSquareFactor)+"}}{"+(2*aTerm.getCoefficient())+"}",
						"x",
						"Factor out largest perfect square.");
				newLine("\\frac{"+bSimplified+"\\pm "+sqrtResult+iResult+"\\sqrt{"+(descriminant/largestSquareFactor)+"}}{"+(2*aTerm.getCoefficient())+"}",
						"x",
						"Simplify.");
			}
		}
		if(willCheckWork && Ops.isSquare(descriminant)){
//			char vari = aTerm.getVariableFactorization().get(0).getBase();
			addHorizontalLine();
			
			int root1=(-bTerm.getCoefficient()+(int)Math.sqrt(descriminant))/(2*aTerm.getCoefficient());
			int root2=(-bTerm.getCoefficient()-(int)Math.sqrt(descriminant))/(2*aTerm.getCoefficient());
			try{
				newTextLine("Check your work by plugging same solutions into the left side of the equation!");
				newTextLine("Plug in "+root1);
				int y1 = plugIn(vari+"", root1, leftExpression);	
				newTextLine("Plug in "+root2);
				int y2 = plugIn(vari+"", root2, leftExpression);	
				String explanation = "When we plug "+root1+" into both the left and right side, we get "+y1;
				if(root1!=root2)explanation+=" and when we plug in "+root2+" to both sides, we get "+y2+". This means that our solutions, ("+root1+", "+y1+") and ("+root2+", "+y2+") are correct.";
				else explanation+=".  This means that our solution, ("+root1+", "+y1+") is correct.";
				newTextLine(explanation);
			}catch(NullPointerException e){
				newTextLine("Check your work!");
				newTextLine("Plug in "+root1);
				plugIn(vari+"", root1, new Expression(quadraticTerms));
				addHorizontalLine();
				newTextLine("Plug in "+root2);
				plugIn(vari+"", root2, new Expression(quadraticTerms));
				newTextLine("Both roots, when plugged in, yield zero.");
			}
		}

	}

	
	
	public void addLinearEquationSteps(Expression leftExpress,Expression rightExpress,String variable, int solution) {
		int space;//this variable is used to determine empty horizontal space
		this.leftExpression=leftExpress;
		this.rightExpression=rightExpress;
		Term addOnBothSides;
		leftTerms = leftExpression.getTermsOfExpression();
		rightTerms = rightExpression.getTermsOfExpression();
		int leftCons = 0;
		//chooses which variable term will be added to both sides
		addOnBothSides = getTermToAddOrSubtract(leftTerms, rightTerms, 1, true);
		//finds how much space is needed
		for(int check=0; check<leftTerms.length; check++){
			if(leftTerms[check].getType().equals(Term.CONSTANT_TYPE)) {
				leftCons=leftTerms[check].getCoefficient();
			}
		}
		//subtracts a variable term from both sides, if one is non-zero.
		if(addOnBothSides.getCoefficient()!=0){
			performAdditiveInverse(addOnBothSides, getWidth("\\;\\;+\\;"+Math.abs(leftCons)),0);
		}
		
		//chooses the constant that will be added to both sides
		boolean leftContainsVar=false;
		for(Term t:leftTerms){
			if (t.getType()!=Term.CONSTANT_TYPE){
//				System.out.println("WorkTable: found a variable on the left "+t);
				leftContainsVar=true;
				break;
			}
		}
		if(leftContainsVar){
			for(Term t:leftTerms){
//				System.out.println("WorkTable: Considering whether or not to add or subtract "+t);
				if (t.getType()==Term.CONSTANT_TYPE){
//					System.out.println("WorkTable: Determined  "+t+" should be cancelled");
					addOnBothSides=Term.getCopy(t);
					break;
				}
				else addOnBothSides=new Term(0);
			}
		}else{
			for(Term t:rightTerms){
				if (t.getType()==Term.CONSTANT_TYPE){
//					System.out.println("WorkTable: Determined  "+t+" should be cancelled (term on right side)");
					addOnBothSides=Term.getCopy(t);
					break;
				}
				else addOnBothSides=new Term(0);
			}
		}
//		System.out.println("WorkTable: "+Arrays.toString(leftTerms)+" = "+Arrays.toString(rightTerms)+", Conclusion "+addOnBothSides+" should be cancelled.");
		//determines spacing
		String right1stTerm = "-";
		boolean aVariableTermOnRightSide=false;//helps align constant under other constant
		for(int check=0; check<rightTerms.length; check++){
			if(rightTerms[check].containsVariable()) {
				right1stTerm=rightTerms[check].toString();
				aVariableTermOnRightSide=true;
			}
		}

		//subtracts a constant term from both sides, if one is non-zero.
		if(addOnBothSides.getCoefficient()!=0){
			if(aVariableTermOnRightSide) space = getWidth(right1stTerm+"\\;+");
			else space=0;
			performAdditiveInverse(addOnBothSides, 0,space);
		}else{//since the only constants are zero, we remove trivial terms (otherwise +0 will show up in the final line)
			ArrayList<Term> nonTrivialTerms = new ArrayList<Term>();
			for(int check=0; check<rightTerms.length; check++){
				if(rightTerms[check].getCoefficient()!=0) nonTrivialTerms.add(rightTerms[check]);
				}
			rightTerms = Term.ArrayListToTermArray(nonTrivialTerms);
			nonTrivialTerms = new ArrayList<Term>();
			for(int check=0; check<leftTerms.length; check++){
				if(leftTerms[check].getCoefficient()!=0) nonTrivialTerms.add(leftTerms[check]);
				}
			leftTerms = Term.ArrayListToTermArray(nonTrivialTerms);			
			}
		//chooses which constant we will divide by
		int coefficient=1;
		for(int check=0; check<leftTerms.length; check++){
			
			if(leftTerms[check].containsVariable()) {
				coefficient=leftTerms[check].getCoefficient();
			}
		}
		for(int check=0; check<rightTerms.length; check++){
			if(rightTerms[check].containsVariable()) {
				coefficient=rightTerms[check].getCoefficient();
			}
		}
		System.out.println("WorkTable.java coefficient to divide by is "+coefficient);
		if (coefficient!=1){
			setSymbol("\\hspace{1}");
			newLine("\\overline{"+coefficient+"}", "\\overline{"+coefficient+"}", 
				"Divide both sides by "+coefficient);
			setSymbol("=");
			Term frac=new Term(new Fraction(1, coefficient));
			leftTerms=Ops.distribute(frac, leftTerms);
			rightTerms=Ops.distribute(frac, rightTerms);
			
			newLine(Format.termArrayToString(leftTerms), Format.termArrayToString(rightTerms), 
					"Simplify.");
		}
		addHorizontalLine();
		
		int leftSideValue=0;
		int rightSideValue=0;
		if (willCheckWork){
			newTextLine("Check your work!");
			if(leftExpress.containsAVariable()){
				newTextLine("Left side:");
				leftSideValue=plugIn(""+variable, solution, leftExpress);
				addHorizontalLine();
			}else{
				leftSideValue=leftExpress.getTermsOfExpression()[0].getCoefficient();
			}
			if(rightExpress.containsAVariable()){
				newTextLine("Right side:");
				rightSideValue=plugIn(""+variable, solution, rightExpress);
				addHorizontalLine();
			}else{
				rightSideValue=rightExpress.getTermsOfExpression()[0].getCoefficient();
			}
			
			newTextLine("This equation is true for the value, }"+variable+"\\text{="+solution+".");
		}
	}

	public void addSolveRatioSteps(int[] coefficients, int total){
		String writtenOut="";
		int totalCoef=0;
		for(int i=0; i<coefficients.length-1; i++){
			writtenOut+=coefficients[i]+"x+";
			totalCoef+=coefficients[i];
		}
		writtenOut+=coefficients[coefficients.length-1]+"x";
		totalCoef+=coefficients[coefficients.length-1];
		newLine(writtenOut, total+"", "Rewrite the sum as an equation using the ratio as coefficients.");
		newLine(totalCoef+"x", total+"", "Combine terms");
		setSymbol("\\hspace{1}");
		newLine("\\overline{"+totalCoef+"}", "\\overline{"+totalCoef+"}", 
			"Divide both sides by "+totalCoef);
		setSymbol("=");
		Term frac=new Term(new Fraction(1, totalCoef));
		
		newLine("x", total/totalCoef+"", "Simplify. This is the value of an individual \"part\"");
		newTextLine("Plug in x to find the value of each number");
		int x=total/totalCoef;
		writtenOut="";
		for(int i=0; i<coefficients.length; i++){
			newLine(coefficients[i]+"\\left("+x+"\\right)", coefficients[i]*x+"", "Plug x="+x+" into the "+Format.getOrdinalNumber(i+1)+" part of the ratio.");
			if(i+1<coefficients.length)writtenOut+=coefficients[i]*x+"+";
		}
		writtenOut+=coefficients[coefficients.length-1]*x;
		newLine(writtenOut, total+"", "Check.");
	}
	

	
	public static CoordinateImage copyImage(CoordinateImage original){
		CoordinateImage image= new CoordinateImage(original.getWidth(),
				original.getHeight(), 
				original.getxMin(), 
				original.getxMax(),
				original.getyMin(), 
				original.getyMax(),
				copyBufferedImage(original.getImage())
				);
		return image;
	}
	
	public static BufferedImage copyBufferedImage(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	
	public void addGraphs(ArrayList<Graph> graphs,double  windowLeft, double windowRight, double windowDown,double windowUp, ArrayList<BufferedImage> images){
		image= new CoordinateImage(450, 450, windowLeft, windowRight, windowDown,windowUp);
		for(Graph g:graphs){
			addGraph(g, windowLeft, windowRight, windowDown,windowUp,image);
		}
		addGraphic(image, images);
	}
	
	public void addGraph(Graph graph, ArrayList<BufferedImage> images) {
		image= new CoordinateImage(550, 550, graph.getWindowLeft(), graph.getWindowRight(), graph.getWindowDown(),graph.getWindowUp());
		addGraph(graph,graph.getWindowLeft(), graph.getWindowRight(), graph.getWindowDown(),graph.getWindowUp(),image);
		addGraphic(image, images);
	}
	
	public void addGraph(Graph graph, double  windowLeft, double windowRight, double windowDown,double windowUp,CoordinateImage image) {
		ArrayList<CoordinatePoint> plottedPoints=graph.getSolutionSet();
		image.setColor(Color.black);	
		image.setThickness(2);
		int xScale=(int)(windowRight/10);
		if(xScale<(int)(Math.abs(windowLeft/10)))xScale=Math.abs((int)(windowLeft/10));
		int yScale=(int)(windowUp/10);
		if(yScale<(int)(Math.abs(windowDown/10)))yScale=Math.abs((int)(windowDown/10));
		image.addAxes(xScale, yScale, true);
		image.addGrid(xScale, yScale);
		if(graph.isRestrictedLow()){
			if(graph.isLowInclusive())image.drawPoint(graph.getLeftEndpoint());
			else image.drawOpenPoint(graph.getLeftEndpoint());
		}
		if(graph.isRestrictedHigh()){
			if(graph.isHighInclusive())image.drawPoint(graph.getRightEndpoint());
			else image.drawOpenPoint(graph.getRightEndpoint());
		}
		for(int index1=0; index1<plottedPoints.size(); index1++){
			if(index1>0){
				image.drawSegment(plottedPoints.get(index1-1), plottedPoints.get(index1));
			}
		}
		image.setThickness(1);
		
		
	}
	
	public void addTransformationSteps(CoordinateImage original, ArrayList<CoordinatePoint> figure, ArrayList<Transformation> transformation, ArrayList<BufferedImage> images) {
		image= copyImage(original);
		ArrayList<CoordinatePoint> originalFigure=new ArrayList<CoordinatePoint>();
		Color[] colors = {Color.red,new Color(200,50,200),Color.green};
		int colorIndex = 0;
		if(transformation.size()>1){
			String intro="";
			intro+="The transformation #"+DrawableFormat.transformationArrayToString(transformation)+
					"# is a composition of transformations. Since the `\\circ' symbol means `of', we " +
					"must perform this transformation in REVERSE order because it is a ";
			for(int index=transformation.size()-1; index>=0; index--){
				intro+=transformation.get(index).getTypeName();
				if(index-1>=0)intro+=" OF a ";
			}
			intro+=".";
			newTextLine(intro);
			addHorizontalLine();
		}
		for(int index=0; index<transformation.size(); index++){
			String intro="";
			originalFigure=new ArrayList<CoordinatePoint>();
			for(CoordinatePoint p:figure){
				originalFigure.add(new CoordinatePoint(p.getxCoordinate(), p.getyCoordinate()));
			}
			Transformation t=transformation.get(index);
			String arg1String = Format.doubleToString(t.getArg());
			String arg2String = Format.doubleToString(t.getOptionalArg());
			intro+="The notation, #"+t.getNotation()+"# stands for";
			
			if(t.getType()==Transformation.DILATE){
				intro+=" a dilation with a scale factor of ";
				if(t.getArg()==t.getOptionalArg()){
					intro+=arg1String+". This is done by multiplying the coordinates by "+
							arg1String+".";
				}else{
					intro+=arg1String+" in the x-axis and "+arg2String+" in the y-axis. This is " +
							"done by multiplying the x-coordinates by "+arg1String+" and the " +
							"y-coordinates by "+arg2String;
				}
				for(int index1=0; index1<figure.size(); index1++){
					figure.get(index1).dilate(t.getArg(), t.getOptionalArg());
				}
			}else if(t.getType()==Transformation.ROTATE){
				intro+=" a rotation of "+arg1String+" degrees. ";
				if(t.getArg()<0){
					intro+="Because "+arg1String+" is negative, we must remember that the rotation occurs " +
							"in a CLOCKWISE direction. ";
				}else{
					intro+="Rotation by a positive angle measure always occurs in a COUNTER-clockwise direction. ";
				}
				intro+="Although there are formulas for rotation by this amount, the simplest method (and easiest to " +
						"not forget) involves rotating the paper "+arg1String+" degrees to invision how the image of the points moves:";
				for(int index1=0; index1<figure.size(); index1++){
					figure.get(index1).rotate(t.getArg(), true);
				}
			}else if(t.getType()==Transformation.TRANSLATE){
				intro+=" a translation of (x+"+arg1String+",y+"+arg2String+"). " +
						"This will result in sliding the figure "+Format.doubleToString(Math.abs(t.getArg()))+" unit";
				if(t.getArg()>0){
					if(t.getArg()!=1)intro+="s ";
					intro+="RIGHT and "+Format.doubleToString(Math.abs(t.getOptionalArg()))+" unit ";
				}
				else {
					if(t.getArg()!=-1)intro+="s ";
					intro+="LEFT and "+Format.doubleToString(Math.abs(t.getOptionalArg()))+" unit ";
				}
				if(t.getOptionalArg()>0){
					if(t.getOptionalArg()!=1)intro+="s ";
					intro+="UP.";
				}
				else {
					if(t.getOptionalArg()!=-1)intro+="s ";
					intro+="DOWN.";
				}
				for(int index1=0; index1<figure.size(); index1++){
					figure.get(index1).translate(t.getArg(),t.getOptionalArg());
				}				
			}else if(t.getType()==Transformation.REFLECT){
				intro+=" a reflection over the line ";
				String lineName="";
				if(t.getArg()==DrawableOps.undefinedIdentifier){
					lineName+="x="+arg2String;
				}else if(t.getArg()==1){
					if(t.getOptionalArg()==0){
						lineName+="y=x";
					}else if(t.getOptionalArg()>0){
						lineName+="y=x+"+arg2String;
					}else{
						lineName+="y=x"+arg2String;
					}
				}else if(t.getArg()==-1){
					if(t.getOptionalArg()==0){
						lineName+="y=-x";
					}else if(t.getOptionalArg()>0){
						lineName+="y=-x+"+arg2String;
					}else{
						lineName+="y=-x"+arg2String;
					}
				}else if(t.getArg()==0){
					lineName+="y="+arg2String;
				}else{
					lineName+="y="+arg1String+"x+"+arg2String;
				}
				intro+=lineName+". This is done by sketching the line, "+lineName+", then drawing perpendicular " +
						"segments from each point to the line, such that the segments are mirrored on the " +
						"opposite side of the line.";
				for(int index1=0; index1<figure.size(); index1++){
					figure.get(index1).reflect(t.getArg(),t.getOptionalArg());
				}
			}
			newTextLine(intro);
			/* 
			 * Perform the transformation
			 */
			image.setColor(colors[colorIndex]);
			image.setThickness(5);
			colorIndex=(colorIndex+1)%3;
			for(int index1=0; index1<figure.size(); index1++){
				image.drawPoint(figure.get(index1));
				figure.get(index1).setLabel(figure.get(index1).getLabel()+"'");
				if(index1>0){
					image.drawSegment(figure.get(index1-1), figure.get(index1));
				}
				if(index1==figure.size()-1 && figure.size()>1){
					image.drawSegment(figure.get(index1), figure.get(0));
				}
			}
			image.labelFigure(figure);
			image.setColor(Color.black);
			image.setThickness(2);
			/*
			 * End transformation
			 */
			CoordinateImage tempCopy= copyImage(image);//image copies itself again, in case another iteration is made.
			addTransformationAccents(t, image, originalFigure, figure);
			addGraphic(image, images);
			image=tempCopy;
			String text = "The coordinates of the image are #";
			for(int vertex = 0; vertex< figure.size(); vertex++){
				text+=figure.get(vertex).getLabel()+"\\left("+Format.doubleToString(figure.get(vertex).getxCoordinate())+","+
						Format.doubleToString(figure.get(vertex).getyCoordinate())+"\\right)";
				if(vertex+2<figure.size()){
					text+=",# #";
				}else if(vertex+2==figure.size()){
					text+=",# and #";
				}else{
					text+=".# ";
				}
			}
			newTextLine(text);
			addHorizontalLine();
		}
	}
	
	private void addTransformationAccents(Transformation t, CoordinateImage image, ArrayList<CoordinatePoint> originalFigure, ArrayList<CoordinatePoint> figure) {
		image.setThickness(4);
		image.setColor(Color.blue);
		if(t.getType()==Transformation.DILATE){
			for(int index1 = 0; index1< figure.size(); index1++){
				image.drawSegment(CoordinateImage.ORIGIN,originalFigure.get(index1));
				image.drawSegment(CoordinateImage.ORIGIN,figure.get(index1));
			}
		}else if(t.getType()==Transformation.ROTATE){
			image.drawSegment(CoordinateImage.ORIGIN,originalFigure.get(0));
			image.drawSegment(CoordinateImage.ORIGIN,figure.get(0));
		}else if(t.getType()==Transformation.TRANSLATE){
			for(int index1 = 0; index1< figure.size(); index1++){
				CoordinatePoint reference = new CoordinatePoint(figure.get(index1).getxCoordinate(), originalFigure.get(index1).getyCoordinate());
				if(t.getArg()!=0)image.drawVector(originalFigure.get(index1), reference);
				if(t.getOptionalArg()!=0)image.drawVector(reference, figure.get(index1));
			}
		}else if(t.getType()==Transformation.REFLECT){
			double[] equation = {t.getArg(),t.getOptionalArg()};
			image.drawLinearGraph(equation);
			for(int index1 = 0; index1< figure.size(); index1++){
				image.drawVector(originalFigure.get(index1),figure.get(index1));
				image.drawVector(figure.get(index1),originalFigure.get(index1));
			}
		}
		image.setColor(Color.black);
		image.setThickness(2);
		
	}

	/**
	 * for an image to be added to the table, the data has to have a tag in it that closes the table and 
	 * alerts the compiler that an image is added at this location, then the table resumes
	 * @param image - a coordinate plane to be inserted at this point
	 * @param images - the running list of images that will all be inserted
	 */
	private void addGraphic(CoordinateImage image, ArrayList<BufferedImage> images) {
		finish();
		latexTabular+=Problem.GRAPHICS_TAG;
		images.add(image.getImage());
		begin();	
	}
	
	private Term getTermToAddOrSubtract(Term[] leftTerms, Term[] rightTerms, int degree, boolean asLinear){
		Term addOnBothSides;
		Term leftControl = new Term(0);//these two terms contribute to deciding which SIDE of the equation should be
		Term rightControl = new Term(0);//subtracted from. If a higher degree term is on the left side, we should subtract
		Term leftCandidate = new Term(0);//from the right
		Term rightCandidate = new Term(0);
		for(int check=0; check<leftTerms.length; check++){
			if(leftTerms[check].getDegree()==degree) {
				leftCandidate=Term.getCopy(leftTerms[check]);
			}if(leftTerms[check].getDegree()==degree+1) {
				leftControl=leftTerms[check];
			}
			
		}
		for(int check=0; check<rightTerms.length; check++){
			if(rightTerms[check].getDegree()==degree) rightCandidate=Term.getCopy(rightTerms[check]);
			if(rightTerms[check].getDegree()==degree+1) {
				rightControl=rightTerms[check];
			}
		}
		int leftCoef=leftCandidate.getCoefficient();
		int rightCoef=rightCandidate.getCoefficient();
		if (asLinear){//returns the term that will isolate x
			if(Math.abs(leftCoef)>Math.abs(rightCoef)){
				if(leftCoef>0) addOnBothSides=rightCandidate;
				else addOnBothSides=leftCandidate;
			}else{
				if(rightCoef>0) addOnBothSides=leftCandidate;
				else addOnBothSides=rightCandidate;
			}
			return addOnBothSides;
		}else{//returns the term that will get zero on one side
			if(Math.abs(rightControl.getCoefficient())>0){
				addOnBothSides=leftCandidate;
			}else if(Math.abs(leftControl.getCoefficient())>0){
				addOnBothSides=rightCandidate;
			}else{
				addOnBothSides=getTermToAddOrSubtract(leftTerms, rightTerms, degree, true);
			}
			return addOnBothSides;
		}
	}
	
	private void performAdditiveInverse(Term addOnBothSides, int leftSpace, int rightSpace){
		addOnBothSides.setSign(!addOnBothSides.getSign());
		leftTerms = leftExpression.getTermsOfExpression();
		rightTerms =rightExpression.getTermsOfExpression();
		
		setSymbol("\\hspace{1}");
		newLine("\\underline{"+getLeadingOperation(addOnBothSides)+"}\\hspace{"+leftSpace+"}", "\\hspace{"+rightSpace+"}\\underline{"+getLeadingOperation(addOnBothSides)+"}", 
				"Additive inverse");
		setSymbol("=");
		leftTerms=Ops.addTermToExpression(addOnBothSides, leftExpression);
		rightTerms = Ops.addTermToExpression(addOnBothSides, rightExpression);

		leftExpression=new Expression(leftTerms);
		rightExpression=new Expression(rightTerms);
		newLine(Format.termArrayToString(leftTerms), Format.termArrayToString(rightTerms), 
				"Simplify");
	}
	
	public void begin(){
		latexTabular+="\\begin{tabular}{rclc}";
	}
	
	public void finish(){
		latexTabular+="\\\\\\end{tabular}";
	}
	
	public String getLatexTabular() {
		return latexTabular;
	}
	
	private String getLinedText(String unLinedText){
		String[] parsedWords = unLinedText.split(" ");
		String linedText="\\text{";
		String thisLine="\\text{";
		for(int wordIndex=0;wordIndex<parsedWords.length;wordIndex++){
			linedText+=parsedWords[wordIndex]+" ";
			thisLine+=parsedWords[wordIndex]+" ";
			if(wordIndex+1<parsedWords.length){
				String testLine = thisLine+parsedWords[wordIndex+1]+ "}\\end{array}";
				//						System.out.println("thisLine reads, "+testLine+" and the width is "+toImage(thisLine).getWidth());
				if (Problem.toImage(testLine).getWidth()>explanationWidth){
					linedText+="}\\\\\\text{";
					thisLine = "\\begin{array}{l}\\text{";
				}
			}
		}
		linedText+="}";
		return linedText;
	}
	
	public static String getLeadingOperation(int toAddOrSubtract){
		if (toAddOrSubtract>0)return "+"+toAddOrSubtract;
		else return toAddOrSubtract+"";
	}
	
	public static String getLeadingOperation(Term toAddOrSubtract){
		if (toAddOrSubtract.getSign())return "+"+toAddOrSubtract;
		else return toAddOrSubtract+"";
	}
	
	public void setSymbol(String symbol){
		this.symbol=symbol;
	}
	
	public static int getWidth(String code){
		return Problem.toImage(code).getWidth();
	}

	public int plugIn(String variable, int xValue, Expression expression) {
		Term[] terms = expression.getTermsOfExpression();//Note:  
		/**
		 * a NullPointerException on the above line
		 * signifies that no "leftExpression" and "rightExpression" have been set
		 * in the class calling for work to be shown, there is a boolean that calls for the solution to be plugged into the 
		 * original equation. Either change the boolean to "false" or before showing the work, set the 
		 * leftExpression and rightExpression by calling the respective methods
		 */
		String resolvedTerms = "";
		int solution=0;
		String pluggedIn = "";
		for(int index = 0; index< terms.length; index++){
			pluggedIn+=terms[index].toString().replaceAll(variable, "\\\\left( "+xValue+"\\\\right)");
			if(index<terms.length-1) {
				if(terms[index+1].isPositive)pluggedIn+="+";
			}
		}
		newLine(expression.toString(), pluggedIn,"Plug in "+xValue+".");
		for(int index = 0; index< terms.length; index++){
			int coefficient = terms[index].getCoefficient();
			int degree = terms[index].getDegree();
			int valueRaised = (int)Math.pow(xValue, degree);
			int value = coefficient*valueRaised;
			solution+=value;
			resolvedTerms+="\\left("+value+"\\right)";
			if(index<terms.length-1) resolvedTerms+="+";
		}
		newLine(resolvedTerms,""+solution, "Evaluate.");
		return solution;
	}
	
	public int plugIn(String variable, int xValue, Expression expression, String measure) {
		newLine(expression.toString(), measure, "Original equation.");
		Term[] terms = expression.getTermsOfExpression();
		String resolvedTerms = "";
		int solution=0;
		String pluggedIn = "";
		for(int index = 0; index< terms.length; index++){
			pluggedIn+=terms[index].toString().replaceAll(variable, "\\\\left( "+xValue+"\\\\right)");
			if(index<terms.length-1) {
				if(terms[index+1].isPositive)pluggedIn+="+";
			}
		}
		newLine(pluggedIn, measure, "Plug in "+xValue+".");
		for(int index = 0; index< terms.length; index++){
			int coefficient = terms[index].getCoefficient();
			int degree = terms[index].getDegree();
			int valueRaised = (int)Math.pow(xValue, degree);
			int value = coefficient*valueRaised;
			solution+=value;
			resolvedTerms+="\\left("+value+"\\right)";
			if(index<terms.length-1) resolvedTerms+="+";
		}
		newLine(resolvedTerms, measure, "Evaluate.");
		newLine(""+solution, measure, "Answer.");
		return solution;
	}

	public void addGeometryFormulaSteps(ReferenceFormula formula, boolean useRadius, boolean useHeight, ArrayList<BufferedImage> images) {
		newTextLine("We need to solve for "+formula.getUnknownName()+". Formulas involving "+formula.getTypeName()+" are listed in the back of the exam on the reference sheet:");
		newRawCodeLine(formula.getRepresentationPageView());
		if(!useRadius && formula.getUnknown()!=ReferenceFormula.RADIUS){
			newTextLine("This formula requires the radius (#r#), but the DIAMETER is listed in the question. Recall that #d=2r#. Therefore:");
			newLine("r", "\\frac{"+(2*formula.getValue(ReferenceFormula.RADIUS))+"}{2}", "Divide diameter by 2.");
			newLine("r", formula.getValue(ReferenceFormula.RADIUS)+"", "Simplify.");
			addHorizontalLine();
		}
		if(formula.getType()==ReferenceFormula.RIGHT_CIRCULAR_CONE_LATERAL_AREA && useHeight && formula.getUnknown()!=ReferenceFormula.SLANT_HEIGHT){
			newTextLine("This formula requires the slant height (#l#), but the HEIGHT is listed in the question. Since this is a RIGHT circulat cone, the height forms a right angle with the base. Therefore:");
			CoordinateImage image = new CoordinateImage(200, 200, -5, 5, -2,10);
			image.drawLatex("\\text{image not to scale}", -6, 7);
			Cone cone = new Cone(2, 4);
			cone.draw(image, CoordinateImage.ORIGIN, true);
			cone.labelHeight(image, "h");
			cone.labelSlant(image, "l");
			cone.labelRadius(image, "r");
			addGraphic(image, images);
			newLine("l^2", "r^2+h^2", "By the Pythagorean Theorem");
			newLine("l^2", formula.getValue(ReferenceFormula.RADIUS)+"^2+"+formula.getValueString(ReferenceFormula.HEIGHT)+"^2", "Plug in known values.");
			newLine("l^2", Ops.roundDouble((Math.pow(formula.getValue(ReferenceFormula.RADIUS),2)+Math.pow(formula.getValue(ReferenceFormula.HEIGHT),2)),1)+"", "Evaluate.");
			newLine("l", Ops.roundDouble(Math.sqrt((Math.pow(formula.getValue(ReferenceFormula.RADIUS),2)+Math.pow(formula.getValue(ReferenceFormula.HEIGHT),2))),1)+"", "Square root both sides.");
			addHorizontalLine();
		}
		if(formula.requiresBase()){
			newLine("B",formula.getBaseFormula(),"Since this formula involves the base, we will make use of the formula for the base of a "+formula.getSolidName()+", which is a "+formula.getBaseName()+". The formula for the area of a "+formula.getBaseName()+" must be memorized, as it is not listed on the reference sheet.");
			newLine(formula.getLeft(),formula.getCoefficientString()+"\\left("+formula.getBaseFormula()+"\\right)"+formula.getHeightVariable(),"Substitute the fomula for base into the orignal formula for "+formula.getTypeName());
		}
		//for when information is provided OTHER than what the formula says (i.e. surface area is given but lateral SA formula is used)
		if((formula.getType()==ReferenceFormula.RIGHT_CIRCULAR_CONE_LATERAL_AREA || formula.getType()==ReferenceFormula.CYLINDER_LATERAL_AREA) && (formula.getUnknown()!=formula.getVariableComponents()[0] && formula.getVariationOnLateralArea()!=ReferenceFormula.LATERAL_AREA)){
			System.out.println("True A");
			int basesToAdd=1;
			if(formula.getVariationOnLateralArea()==ReferenceFormula.SURFACE_AREA) basesToAdd=2;
			startFromSurfaceArea(formula,basesToAdd);
		}else if(formula.getUnknown()!=formula.getVariableComponents()[0]){//for when lateral surface area is provided
			System.out.println("True B");
			newLine(formula.getPluggedInLeft(), formula.getPluggedInRight(), "Plug given values into the "+formula.getTypeName()+" formula.");			
			newLine(formula.getPluggedInLeft(), formula.getSimplifiedRight(), "Evaluate.");
			newLine("\\overline{"+formula.getSimplifiedRightValue()+"}", "\\overline{"+formula.getSimplifiedRightValue()+"}", "Multiplicative inverse");
			double simplified = formula.getValue(formula.getVariableComponents()[0])/formula.getSimplifiedRightValue();
			
			double simplifiedString=Ops.roundDouble(simplified,3);
				
			if(formula.getUnknownExp()!=1){
				newLine(""+simplifiedString, formula.getUnknownStringAndExponent(), "Simplify");
				double root = formula.getUnknownExp();
				
				if(formula.getUnknownExp()==2)
					newLine("\\sqrt{"+simplifiedString+"}", "\\sqrt{"+formula.getUnknownStringAndExponent()+"}", "Take the square root of both sides.");
				else newLine("\\sqrt[3]{"+simplifiedString+"}", "\\sqrt[3]{"+formula.getUnknownStringAndExponent()+"}", "Take the cubed root of both sides.");
				double answer=Math.pow(simplified,1/root);
				String answerString;
				if(Format.doubleToString(answer).length()<8)answerString=Format.doubleToString(answer);
				else answerString=Ops.roundDouble(answer,3)+"";
				newLine(answerString, formula.getValueVariable(formula.getUnknown()), "Value of the "+formula.getUnknownName()+".");
			}else{
				setSymbol("\\approx");
				newLine(""+Ops.roundDouble(simplifiedString,1), formula.getUnknownStringAndExponent(), "Rounded answer.");
			}
			if(formula.getVariationOnSlantHeight()==ReferenceFormula.HEIGHT){
				newTextLine("So far we have found slant height, but the questions is asking for the HEIGHT. Since this is a RIGHT circulat cone, the height forms a right angle with the base. Therefore:");
				CoordinateImage image = new CoordinateImage(200, 200, -5, 5, -2,10);
				image.drawLatex("\\text{Image not to scale}", -5.8, 7);
				Cone cone = new Cone(2, 3.6);
				cone.draw(image, CoordinateImage.ORIGIN, true);
				cone.labelHeight(image, "h");
				cone.labelSlant(image, "l");
				cone.labelRadius(image, "r");
				addGraphic(image, images);
				newLine("l^2", "r^2+h^2", "By the Pythagorean Theorem");
				newLine(formula.getValue(ReferenceFormula.SLANT_HEIGHT)+"^2", formula.getValue(ReferenceFormula.RADIUS)+"^2+h^2", "Plug in known values.");
				newLine(Math.pow(formula.getValue(ReferenceFormula.SLANT_HEIGHT), 2)+"", Ops.roundDouble(Math.pow(formula.getValue(ReferenceFormula.RADIUS),2),3)+"+h^2", "Evaluate.");
				setSymbol("\\hspace{1}");
				newLine("-"+Ops.roundDouble(Math.pow(formula.getValue(ReferenceFormula.RADIUS),2),3),"-"+Ops.roundDouble(Math.pow(formula.getValue(ReferenceFormula.RADIUS),2),3), "Additive inverse.");
				setSymbol("=");
				double d =(Math.pow(formula.getValue(ReferenceFormula.SLANT_HEIGHT),2)-Math.pow(formula.getValue(ReferenceFormula.RADIUS),2));
				newLine(d+"", "h^2", "Simplify.");
				newLine(Ops.roundDouble(Math.sqrt(d), 1)+"", "h", "Square root both sides and round.");
				addHorizontalLine();
			}
		}else{//no additional work is necessary, 
			System.out.println("True C");
			newLine(formula.getPluggedInLeft(), formula.getPluggedInRight(), "Plug given values into the "+formula.getTypeName()+" formula.");
			//for when lateral surface area is not the final objective
			if(formula.getUnknown()==ReferenceFormula.LATERAL_AREA &&(formula.getVariationOnLateralArea()==ReferenceFormula.SURFACE_AREA||formula.getVariationOnLateralArea()==ReferenceFormula.LATERAL_AREA_PLUS_FACE)){
				newLine(formula.getPluggedInLeft(), formula.getSimplifiedRightValue()+"", "Evaluate.");
				int basesToAdd=1;
				if(formula.getVariationOnLateralArea()==ReferenceFormula.SURFACE_AREA) basesToAdd=2;
				calculateSurfaceArea(formula, basesToAdd);
			}else{
				newLine(formula.getPluggedInLeft(), Ops.roundDouble(formula.getSimplifiedRightValue(),1)+"", "Evaluate and round.");
			}
		}
		
	}
	
	private void startFromSurfaceArea(ReferenceFormula formula, int numberOfBases){	
		newLine("L", formula.getPluggedInRight(), "Plug values into formula for LATERAL surface area.");
		String s="The above shows how to plug into the formula for the LATERAL surface area, but the problem provides us with TOTAL area. The difference is, \"lateral\" means only the sides, whereas \"total\" includes the area of ";
		String t;
		String expressionRepresentingBases;
		String sumOfBases;
		if(numberOfBases==1){
			s+="a base.";
			t="This is the area of the base.";
			expressionRepresentingBases="B";
			sumOfBases=formula.getSimplifiedBaseFormulaString(1);
		}else{
			s+="the bases.";
			t="This is the area of one of the bases.";
			expressionRepresentingBases="2B";
			sumOfBases=formula.getSimplifiedBaseFormulaString(2);
		}
		newTextLine(s);
		newLine("B", formula.getBaseFormula(), "Equation for the area of a "+formula.getBaseName() +". (This is not listed on the reference sheet, you must have this memorized.)");
		newLine("B", formula.getPluggedInBaseFormula(), "Plug in known values.");		
		newLine("B", formula.getSimplifiedBaseFormulaString(1), t);
		if(numberOfBases!=1){
			newLine("2B", formula.getSimplifiedBaseFormulaString(2), "Area of both bases");
		}
		newLine("SA", "L+"+expressionRepresentingBases, "Total surface area, which combines both the lateral and base areas.");
		newLine("SA", formula.getPluggedInRight()+"+"+sumOfBases, "Plug in values from above calculations.");
		String pluggedInArea= formula.getValueString(formula.getVariationOnLateralArea());
		newLine(pluggedInArea, formula.getSimplifiedRight()+"+"+sumOfBases+"", "Simplify.");
		try{
		double areaMinusBase = Ops.roundDouble(Double.parseDouble(pluggedInArea)-Double.parseDouble(sumOfBases),3);
		setSymbol("\\hspace{1}");
		newLine("-"+sumOfBases, "-"+sumOfBases+"", "Additive inverse.");
		setSymbol("=");
		newLine(""+areaMinusBase, formula.getSimplifiedRight()+"", "Subtract.");
		setSymbol("\\hspace{1}");
		newLine("\\overline{"+formula.getSimplifiedRightValue()+"}", "\\overline{"+formula.getSimplifiedRightValue()+"}", "Multiplicative inverse.");
		setSymbol("=");
		double quotient = areaMinusBase/formula.getSimplifiedRightValue();
		setSymbol("\\approx");
		newLine(""+Ops.roundDouble(formula.getValue(formula.getUnknown()),1), formula.getValueString(formula.getUnknown()), "Divide and round.");
		}catch(NumberFormatException e){
			newTextLine("line 902 of WorkTable error");
		}
		}
	
	private void calculateSurfaceArea(ReferenceFormula formula, int numberOfBases){
		addHorizontalLine();
		String s="Although we have found the lateral surface area, this question is asking us to find the ENTIRE area, ";
		String t;
		String expressionRepresentingBases;
		double sumOfBases;
		if(numberOfBases==1){
			s+="which includes one "+formula.getBaseDescription()+" base.";
			t="This is the area of the base.";
			expressionRepresentingBases="B";
			sumOfBases=Ops.roundDouble(formula.getSimplifiedBaseFormula(),3);
		}else{
			s+="which includes "+numberOfBases+" "+formula.getBaseDescription()+" bases.";
			t="This is the area of one of the bases.";
			expressionRepresentingBases="2B";
			sumOfBases=Ops.roundDouble(2*formula.getSimplifiedBaseFormula(),3);
		}
		newTextLine(s);
		newLine("B", formula.getBaseFormula(), "Equation for the area of a "+formula.getBaseName() +". (This is not listed on the reference sheet, you must have this memorized.)");
		newLine("B", formula.getPluggedInBaseFormula(), "Plug in known values.");		
		newLine("B", ""+Ops.roundDouble(formula.getSimplifiedBaseFormula(), 3), t);
		if(numberOfBases!=1){
			newLine("2B", ""+sumOfBases, "Area of both bases");
		}
		newLine("SA", "L+"+expressionRepresentingBases, "Total surface area, which combines both the lateral and base areas.");
		newLine("SA", "\\left("+formula.getSimplifiedRightValue()+"\\right)+\\left("+sumOfBases+"\\right)", "Plug in values from above calculations.");
		newLine("SA", Ops.roundDouble(formula.getSimplifiedRightValue()+sumOfBases,1)+"", "Simplify and round.");
	}

	public void setCheckWork(boolean b){
		willCheckWork=b;
	}
	
	public void setExplanationWidth(int width){
		explanationWidth=width;
	}

	public void addGeometricMeanSteps(Term[] componentA, Term[] componentB, Term[] geometricMean, char variable, int actualMeasure) {
		newLine(Format.termArrayToString(componentA)+"("+Format.termArrayToString(componentB)+")", 
				"("+Format.termArrayToString(geometricMean)+")^2",
				"Substitution.");
		Term[] distributed=Ops.distribute(componentA, componentB);
		Term[] squared=Ops.distribute(geometricMean, geometricMean);
		newLine(Format.termArrayToString(distributed), 
				Format.termArrayToString(squared),
				"Distribute.");
		addQuadraticEquationSteps(new Expression(distributed), new Expression(squared), ""+variable, actualMeasure);
		
	}

	public double addLawOfSinesSteps(String a, String A, String b, String B, double knownSide, double knownAngle,
			double unknownSide, double unknownAngle, boolean findSide, String var) {
		if(findSide){
			newLine("\\frac{"+a+"}{\\sin\\left(m\\angle "+A+"\\right)}", "\\frac{"+b+"}{\\sin\\left(m\\angle "+B+"\\right)}", "Law of sines");
			newLine("\\frac{"+knownSide+"}{\\sin\\left("+knownAngle+"\\right)}", "\\frac{"+var+"}{\\sin"+unknownAngle+"}", "Substitute");
			newLine("\\frac{"+knownSide+"}{\\sin\\left("+knownAngle+"\\right)}\\left(\\sin"+unknownAngle+"\\right)", var, "Multiply by \\left(\\sin"+unknownAngle+"\\right)");
			double result = Ops.roundDouble(knownSide/Math.sin(knownAngle*Math.PI/180)*Math.sin(unknownAngle*Math.PI/180), 1); 
			newLine(result+"", var, "Evaluate.");
			return result;
		}else{
			newLine("\\frac{\\sin\\left(m\\angle "+A+"\\right)}{"+a+"}", "\\frac{\\sin\\left(m\\angle "+B+"\\right)}{"+b+"}", "Law of sines");
			newLine("\\frac{\\sin\\left( "+knownAngle+"\\right)}{"+knownSide+"}", "\\frac{\\sin "+var+"}{"+unknownSide+"}", "Substitute.");
			newLine("\\frac{\\sin\\left( "+knownAngle+"\\right)}{"+knownSide+"}\\left("+unknownSide+"\\right)", "\\sin "+var, "Multiply by \\left("+unknownSide+"\\right)");
			double val = Ops.roundDouble(Math.sin(knownAngle*Math.PI/180)/knownSide*unknownSide,4); 
			newLine(val+"", "\\sin "+var, "Evaluate.");
			double result = Ops.roundDouble(Math.asin(val)*180/Math.PI, 1);
			newLine(result+"", var, "Take \\sin^{-1} of both sides.");
			return result;
		}
		
	}

	public double addLawOfCosineSteps(String a, String b,  String c, String C, double aLength, double bLength,
			double cLength, double cMeasure, boolean findSide, String var) {
		newLine("a^2+b^2-2ab\\cos C","c^2","Law of Cosines (General form)");
		newLine("\\left("+a+"\\right)^2+\\left("+b+"\\right)^2-2\\left("+a+"\\right)\\left("+b+"\\right)\\cos\\left("+C+"\\right)","\\left("+c+"\\right)^2","Law of Cosines");
		if(findSide){
			newLine("\\left("+aLength+"\\right)^2+\\left("+bLength+"\\right)^2-2\\left("+aLength+"\\right)\\left("+bLength+"\\right)\\cos\\left("+cMeasure+"\\right)","\\left("+c+"\\right)^2","Substitute.");
			double part1 = Ops.roundDouble(2*aLength*bLength*Math.cos(cMeasure*Math.PI/180),4);
			String opp =(part1<0)?"+":"-";
			newLine((aLength*aLength)+"+"+bLength*bLength+opp+Math.abs(part1),"\\left("+c+"\\right)^2","Evaluate.");
			double part2 = aLength*aLength+bLength*bLength-part1;
			newLine((part2)+"","\\left("+c+"\\right)^2","Simplify.");
			double result = Ops.roundDouble(Math.sqrt(part2), 1);
			newLine(result+"",c,"Square root.");
			return result;
		}else{
			newLine("\\left("+aLength+"\\right)^2+\\left("+bLength+"\\right)^2-2\\left("+aLength+"\\right)\\left("+bLength+"\\right)\\cos\\left("+C+"\\right)","\\left("+cLength+"\\right)^2","Substitute.");
			double part1 = 2*aLength*bLength;
			newLine((aLength*aLength)+"+"+bLength*bLength+"- "+part1+"\\left(\\cos \\left("+C+"\\right)\\right)",cLength*cLength+"","Evaluate.");
			setSymbol("\\hspace{1}");
			newLine("-"+(aLength*aLength)+"-"+bLength*bLength,"-"+(aLength*aLength)+"-"+bLength*bLength,"Subtract");
			setSymbol("=");
			double part2 = (cLength*cLength - (aLength*aLength)-bLength*bLength);
			newLine("- "+part1+"\\left(\\cos \\left("+C+"\\right)\\right)",part2+"","Simplify");
			setSymbol("\\hspace{1}");
			newLine("\\overline{-"+part1+"}","\\overline{-"+part1+"}","Divide");
			setSymbol("=");
			newLine("\\cos \\left("+C+"\\right)",Ops.roundDouble(-part2/part1,4)+"","Simplify");
			double result = Ops.roundDouble(Math.acos(Ops.roundDouble(-part2/part1,4))*180/Math.PI,1);
			newLine(C,"\\cos^{-1}\\left("+Ops.roundDouble(-part2/part1,4)+"\\right)","Take \\cos^{-1} of both sides.");
			newLine(C,result+"","Simplify");
			return result;
		}
		
	}


	
	

}
