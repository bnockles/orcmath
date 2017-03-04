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

import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

/**
 * @author bnockles
 *
 */
public class SideSplitterQuadratics extends SideSplitter {

	protected final static int TYPE_2X = 2;

	/**
	 * @param difficulty
	 */
	public SideSplitterQuadratics(int difficulty) {
		super(difficulty);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isPlainType(){
		return difficulty < 3 || type == TYPE_2X;
	}

	/**
	 * quadratics are much more difficult to factor with large c values
	 */
	protected int getMaxSideLength(){
		return 9+(int)(difficulty*1.5);
	}

	@Override
	protected Expression[] createExpressions() {
		Expression[] e = new Expression[4];
		//known1, known2, unknown 1, unknown 2
		//		int i =(knownSide==1)?1:2;
		//		int j =(knownSide==1)?2:1;
		int i = 1;
		int j = 2;
		e[0] = Expression.constantExpression(smallTriangleSides[j]);
		e[3] = Expression.constantExpression((int)(largeTriangleSides[i] - smallTriangleSides[i]));
		if(difficulty < 3){
			//			e[2] = Expression.constantExpression(smallTriangleSides[i]);
			//			e[1] = Expression.constantExpression((int)(largeTriangleSides[j] - smallTriangleSides[j]));
			Term[] ts = new Term[1];
			var = Variable.randVar()+"";
			ts[0] = new Term(var);
			if(Math.random() < .5){//top is unknown
				findTopPart = true;
				e[2] = new Expression(ts);
				variableValue = smallTriangleSides[i];
				Term[] ts2 = new Term[2];
				ts2[0] = new Term(var);
				ts2[1] = new Term((int)(largeTriangleSides[j] - smallTriangleSides[j])-variableValue);
				e[1] = new Expression(ts2);
			}else{
				findTopPart = false;
				e[1] = new Expression(ts);
				variableValue = (int)(largeTriangleSides[j] - smallTriangleSides[j]);
				Term[] ts2 = new Term[2];
				ts2[0] = new Term(var);
				ts2[1] = new Term((int)(smallTriangleSides[i]-variableValue));
				e[2] = new Expression(ts2);
			}
		}else if(type == TYPE_WHOLE){
			Term[] ts = new Term[2];
			ts[0] = new Term((int)(largeTriangleSides[i]));
			ts[1] = new Term(-1,var);

			Term[] ts2 = new Term[2];
			ts2[0] = new Term(var);
			ts2[1] = new Term((int)(largeTriangleSides[j]-smallTriangleSides[j]-(largeTriangleSides[i]-smallTriangleSides[i])));
			e[1] = new Expression(ts2);
			e[2] = new Expression(ts);

			e[3] = new Expression(new Term(var));
			variableValue = (int)(largeTriangleSides[i]-smallTriangleSides[i]);
		}else if(type == TYPE_2X){
			Term[] ts = new Term[1];
			var = Variable.randVar()+"";
			ts[0] = new Term(var);
			if(Math.random() < .5){//top is unknown
				findTopPart = true;
				e[2] = new Expression(ts);
				variableValue = smallTriangleSides[i];
				Term[] ts2 = new Term[2];
				int coef = 2;
				ts2[0] = new Term(coef,var);
				ts2[1] = new Term((int)(largeTriangleSides[j] - smallTriangleSides[j])-coef*variableValue);

				e[1] = new Expression(ts2);



			}else{
				findTopPart = false;
				e[1] = new Expression(ts);
				variableValue = (int)(largeTriangleSides[j] - smallTriangleSides[j]);
				Term[] ts2 = new Term[2];
				int coef = 2;
				ts2[0] = new Term(coef,var);
				ts2[1] = new Term((int)(smallTriangleSides[i]-coef*variableValue));
				e[2] = new Expression(ts2);
			}
			Term[] ts3 = new Term[2];
			ts3[0] = ts[0];
			ts3[1] = new Term((int)(smallTriangleSides[j]-variableValue));
			e[0] = new Expression(ts3);

			Term[] ts4 = new Term[2];
			ts4[0] = ts[0];
			ts4[1] = new Term((int)(largeTriangleSides[i] - smallTriangleSides[i])-variableValue);
			e[3] = new Expression(ts4);
		}
		return e;
	}

	protected double makeAScaleFactor(){
		return Ops.randomSimpleDouble(2.5, 3.5+(difficulty/3), difficulty>1);
	}
	
	@Override
	protected String getPartToFindName(){
		String partToFind = (findTopPart)?segment2:part1;
		return partToFind;
	}

	/**
	 * For when difficulty is more than 2
	 */

	protected int randomType(){
		double typeTest = Math.random();
		if(typeTest < .5 ) return TYPE_WHOLE;
		else return TYPE_2X;
	}

	protected String initiateString() {
		setSegmentNames();

		if(isPlainType()){

			return "In the diagram below of #\\triangle "+labels[0]+labels[1]+labels[2]+"#, #\\overline{"+labels[cut1LabelIndex]+labels[cut2LabelIndex]+"}# is paralel to #\\overline{"+labels[base1LabelIndex]+labels[base2LabelIndex]+"}#."
					+ "If #"+segment1+" = "+c+","+segment2+" = "+a+", "+part2+"="+b+",# and #"+part1+" = "+ d+"#, find the value of #"+var+"#.";
		}else{
			String text = "In the diagram below of #\\triangle "+labels[0]+labels[1]+labels[2]+"#, #\\overline{"+labels[cut1LabelIndex]+labels[cut2LabelIndex]+"}# is paralel to #\\overline{"+labels[base1LabelIndex]+labels[base2LabelIndex]+"}#.";
			//			int i =(knownSide==1)?1:2;
			int i = 1;
			int j = 2;
			if(type == TYPE_WHOLE){
				int diff = (int)((largeTriangleSides[j]-smallTriangleSides[j]) - (largeTriangleSides[i]-smallTriangleSides[i]));
				String des =(diff>0)?"longer":"shorter";

				text += " If #"+segment1+" = "+c+", "+part1+"# is #"+ (int)(Math.abs(diff))+"# units "+des+" than #"+part2+"#, and #"+whole2+"= "+(int)(largeTriangleSides[i])+",# find the measure of #"+part2+"#.";
			}

			return text;

		}
	}

	@Override
	public void solveEquation(WorkTable answerWork, Expression[] ratio1Simp, Expression[] ratio2Simp){
		Term[] quadratic = answerWork.addSolveProportionQuadraticSteps(ratio1Simp[0], ratio1Simp[1], ratio2Simp[0], ratio2Simp[1], var, variableValue);

			int x2 = Ops.getTermOfDegree(quadratic, 2).getCoefficient();
			int x = Ops.getTermOfDegree(quadratic, 1).getCoefficient();
			int cons = Ops.getTermOfDegree(quadratic, 0).getCoefficient();

			int root1 = (int) ((-x+Math.sqrt(x*x-4*x2*cons))/(2*x2));
			int root2 = (int) ((-x-Math.sqrt(x*x-4*x2*cons))/(2*x2));

			Expression[] test = {a,b,c,d};
			int i = 0;
			while (i< test.length && Ops.plugIn(var, root1, test[i]) >0){
				i++;
			}
			int j = 0;
			while (j< test.length && Ops.plugIn(var, root2, test[j]) >0){
				j++;
			}
			if(i==test.length && j == test.length){
				answerWork.newRawCodeLine("\\text{Both }"+root1+" and "+root2+" are answers that yield positive segment lengths when plugged in.}");
			}else{
				int solution =(i==4)?root1:root2;
				int nonsolution =(i==4)?root2:root1;
				answerWork.newRawCodeLine(solution+"\\text{ is the only solution because, when plugged in, it yields positive lengths, whereas "+nonsolution+" does not.}");
			}
		
	}

}
