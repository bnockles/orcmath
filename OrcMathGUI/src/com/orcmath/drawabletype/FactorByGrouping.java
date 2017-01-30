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

import com.orcmath.local.WorkTable;
import com.orcmath.objects.FactorablePolynomial;
import com.orcmath.objects.Format;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Polynomial;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;
import com.orcmath.type.Type;

public class FactorByGrouping extends Type{

	boolean gcd;
	boolean threeTerms;
	String[] variationsOnInstructions= {"Factor.","Rewrite as the product of two binomials"};
	
	//this constructor is used for formating from static files
	public FactorByGrouping(){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=80;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
	}
	
	public FactorByGrouping(int difficulty, boolean threeTerms, boolean includeGCD){
		instructions = variationsOnInstructions[Ops.randomInt(0, (variationsOnInstructions.length)-1)];;
		numberOfColumns=2;
		verticalSpacing=80;	
		whetherInstructionsAreNeverIncluded=false;
		scaleFactor=.35;
		this.threeTerms=threeTerms;
		gcd=includeGCD;
		getInstance(difficulty);
		//question and answer is determined in the getInstance constructor
	}

	

	public void getInstance(int diff){
		//roots is a term array because only terms can be integers or rationals or radicals, for that matter
		//root1 = n/d  root2=m/e
		//(dx-n)(ex-m)  = dx(ex-m)-n(ex-m)  =  dex^2 - dmx - nex + nm  
		//= dex^2 + (-dm-ne)x +nm                                       <--follow this fomula
		int d=1;
		int n=0;
		int e=1;
		int m=0;
		Term[] roots = new Term[2];
		while(d*m+n*e==0 || d*m-n*e==0){
			for (int rootIndex = 0; rootIndex < roots.length; rootIndex++){
				Term aRoot;
				//first and second difficulty: the first root is an integer
				//or if integerRoots is true
				if ((diff == 1 || (diff==2&& rootIndex==0))){
					int theRootValue = Ops.randomInt(1, diff*5+1);
					//50% of the time the root is negative
					if (Math.random()>.5) theRootValue = theRootValue * (-1);
					//15% of the time the polynomial is a difference of squares
					aRoot = new Term (theRootValue);
					if(rootIndex==0){
						n=theRootValue;
						d=1;
					}
					else {
						m=theRootValue;
						e=1;
					}
				}
				//at the 3rd and 4th difficulty, there is a chance of having a rational root
				else{
					int rootNumerator;
					if (rootIndex==0) rootNumerator = Ops.randomInt(1, diff*3+1);
					else rootNumerator = Ops.randomInt(2, diff*2+1);
					//50% of the time the root is negative
					if (Math.random()>.5) rootNumerator = rootNumerator * (-1);
					int rootDenominator = 13;
					while(rootDenominator==13||rootDenominator==7){
						rootDenominator = Ops.randomInt(2, diff*2+3);
					}
					Fraction theRootValue = new Fraction (rootNumerator, rootDenominator);
					aRoot = new Term (theRootValue);
					if(rootIndex==0){
						n=theRootValue.getNumer();
						d=theRootValue.getDenom();
					}
					else {
						m=theRootValue.getNumer();
						e=theRootValue.getDenom();
					}
				}
				roots[rootIndex]=aRoot;
			}
		}
		System.out.println("d="+d+", e="+e+", n="+n+", m="+m);
		int gcdFactor = 1;
		if(gcd){
			gcdFactor=Ops.randomInt(2, 2*diff+1);
		}
		char variable = Variable.randVar();
		Term gcdTerm = new Term(gcdFactor,variable+"^"+Ops.randomInt(1, diff*2));
		//root1 = n/d  root2=m/e
				//(dx-n)(ex-m)  = dx(ex-m)-n(ex-m)  
		//=  dex^2 - dmx - nex + nm  
		//= dex^2 + (-dm-ne)x +nm<--follow this fomula
		Term[] fourTerms = new Term[4];
		fourTerms[0]=new Term(d*e,variable+"^2");
		fourTerms[1]=new Term(-d*m,variable+"");
		fourTerms[2]=new Term(-n*e,variable+"");
		fourTerms[3]=new Term(n*m);
		
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		
		String dString =""+d;
		if(d==1)dString="";
		String eString =""+e;
		if(e==1)eString="";
		
		if(threeTerms){
			int[] threeTerms = new int[3];
			threeTerms[0]=d*e;
			threeTerms[1]=(-d*m-n*e);
			threeTerms[2]=m*n;
			Polynomial three =new Polynomial(threeTerms, variable);
			if(gcd){
				
				question=Format.termArrayToString(Ops.distribute(gcdTerm, three.getTermArray()));
				answerWork.newLine(question, gcdTerm+"\\left("+three.toString()+"\\right)", "Factor out the GCF");
				answerWork.newLine(gcdTerm+"\\left("+three.toString()+"\\right)", gcdTerm+"\\left("+Format.termArrayToString(fourTerms)+"\\right)", "Rewrite the " +
						"middle term as the sum of factors whose product is equal to " +
						"the product of the first and last terms' coefficients. (In this case, "+(-d*m)+"("+(-n*e)+")="+(n*m)+"("+(d*e)+") )");
			}else{
				question=three.toString();
				answerWork.newLine(three.toString(), Format.termArrayToString(fourTerms), "Rewrite the " +
						"middle term as the sum of factors whose product is equal to " +
						"the product of the first and last terms' coefficients. (In this case, "+(-d*m)+"\\left("+(-n*e)+"\\right)="+(n*m)+"\\left("+(d*e)+"\\right)");
			}
		}else{
			if(gcd){
				question=Format.termArrayToString(Ops.distribute(new Term(gcdFactor,variable+"^"+Ops.randomInt(1, diff*2)), fourTerms));
				answerWork.newLine(question, gcdTerm+"\\left("+Format.termArrayToString(fourTerms)+"\\right)", "Factor out the GCF");
			}else{
				question=Format.termArrayToString(fourTerms);
			}
		}
		String gcdPrefix="";
		String gcdSuffix="";
		String gcdNone="";
		if(gcd){
			gcdPrefix=gcdTerm.toString()+"\\left(";
			gcdSuffix="\\right)";
			gcdNone=gcdTerm.toString();
		}
		//dx(ex-m)-n(ex-m)
		answerWork.newLine(gcdPrefix+Format.termArrayToString(fourTerms)+gcdSuffix, gcdPrefix+dString+variable+"\\left("+eString+variable+Format.getStringWithSign(-m)+"\\right)"+Format.getStringWithSign(-n)+
				"\\left("+eString+variable+Format.getStringWithSign(-m)+"\\right)"+gcdSuffix, "Factor by groups");
		//(dx-n)(ex-m)

		answerWork.newLine(gcdPrefix+Format.termArrayToString(fourTerms)+gcdSuffix, gcdNone+"\\left("+
		dString+variable+Format.getStringWithSign(-n)+"\\right)\\left("+
		eString+variable+Format.getStringWithSign(-m)+"\\right)", "Factor out \\left("+
		eString+variable+Format.getStringWithSign(-m)+"\\right).");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}
	
}
