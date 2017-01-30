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

import com.orcmath.drawable.Reflection;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

/**
 * This is called "Level 1" because the equations being solved end up being linear
 * @author bnockles
 *
 */

public class SolveLogaritmicLevel1 extends DynamicType{

	char variable;
	int base;
	int exponent;
	int result;
	int xValue;
	boolean involvesMult;
	
	//currently there is no difference in the level
//	public SolveLogaritmicLevel1(){
//		variable = Variable.randVar();
//		instructions = "Solve for "+variable;
//		numberOfColumns=1;
//		verticalSpacing=0;	
//		whetherInstructionsAreNeverIncluded=false;
//	}
	
	public SolveLogaritmicLevel1(){
		variable = Variable.randVar();
		instructions = "Solve for "+variable;
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=false;
		this.difficulty=difficulty;
		getInstance();
	}
	
	public void getInstance(){
		base=Ops.randomInt(2, 5);
		if(base<4){
			exponent=Ops.randomInt(2, 5);
		}else{
			exponent=Ops.randomInt(2, 3);
		}
		result = (int)Math.pow(base, exponent);
		int sqrt = (int)Math.sqrt(result);
		xValue = Ops.randomNotZero(sqrt-5, sqrt+5);

		int scalar;
		int linearValue;
		if(Math.random()<.5){
			involvesMult=true;
			scalar= Ops.randomInt(2, 10);
			while(result%scalar!=0)scalar= Ops.randomInt(2, 10);
			linearValue=result/scalar;
		}else{
			involvesMult=false;
			scalar= Ops.randomInt(2, 10);
			linearValue=result*scalar;
		}
			
		int coefficient;
		int bValue;

		int range = linearValue/xValue;
		coefficient=Ops.randomNotZero(range-10, range+10);
		bValue = linearValue-coefficient*xValue;
		Term[] linearTerms = {new Term(coefficient,""+variable), new Term(bValue)};
		Expression linearExpression = new Expression(linearTerms);
		Expression resultExpression = new Expression(new Term(result));
		
		String expressionLog= "\\log_{"+base+"}\\left("+linearExpression+"\\right)";
		String constantLog= "\\log_{"+base+"}"+scalar;

		
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		Term[] simplifedAfterApplyingRule;
		if(involvesMult){
			if(Math.random()>.5){
				question = expressionLog+"+"+constantLog+"="+result;
				answerWork.newLine("\\log_"+base+"\\left("+linearExpression.toString()+"\\right)\\left("+scalar+"\\right)", exponent+"", "Rewrite using Multiplication Property of Logs.");
			}else{
				question = constantLog+"+"+expressionLog+"="+result;
				answerWork.newLine("\\log_"+base+"\\left("+scalar+"\\right)\\left("+linearExpression.toString()+"\\right)", exponent+"", "Rewrite using Multiplication Property of Logs.");
			}
			answerWork.newLine("\\left("+scalar+"\\right)\\left("+linearExpression.toString()+"\\right)", base+"^{"+exponent+"}", "Rewrite using Definition of Logarithm");
			answerWork.newLine("\\left("+scalar+"\\right)\\left("+linearExpression.toString()+"\\right)", resultExpression.toString(), "Simplify");
			simplifedAfterApplyingRule= Ops.distribute(new Term(scalar), linearExpression.getTermsOfExpression());
		}else{
			question = expressionLog+"-"+constantLog+"="+result;
			answerWork.newLine("\\log_"+base+"\\frac{"+linearExpression.toString()+"}{"+scalar+"}", exponent+"", "Rewrite using Division Property of Logs.");
			answerWork.newLine("\\frac{"+linearExpression.toString()+"}{"+scalar+"}", base+"^{"+exponent+"}", "Rewrite using Definition of Logarithm");
			answerWork.newLine("\\frac{"+linearExpression.toString()+"}{"+scalar+"}", resultExpression.toString(), "Simplify");
			answerWork.newLine("\\left("+scalar+"\\right)\\frac{"+linearExpression.toString()+"}{"+scalar+"}", resultExpression.toString()+"\\left("+scalar+"\\right)", "Multiply both sides by "+scalar);
			simplifedAfterApplyingRule= linearExpression.getTermsOfExpression();
			resultExpression=new Expression(Ops.multiplyTerms(new Term(scalar), new Term(result)));
		}
		answerWork.setCheckWork(false);
		Expression simplifiedExpression = new Expression(simplifedAfterApplyingRule);
		answerWork.newLine(simplifiedExpression.toString(), resultExpression.toString(), "Simplify.");
		answerWork.addLinearEquationSteps(simplifiedExpression, resultExpression, ""+variable, xValue);
		answerWork.finish();
		answer=answerWork.getLatexTabular();
	}
}
