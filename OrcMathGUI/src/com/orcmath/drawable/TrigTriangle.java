package com.orcmath.drawable;

import com.orcmath.objects.Expression;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.SimplestRadicalForm;
import com.orcmath.objects.Term;

public class TrigTriangle extends Triangle{

	private boolean[] knownSides;
	private double[] lengths;
	
	
	public TrigTriangle(double sidea, double sideb, double sidec) {
		super(sidea,sideb,sidec);
		double[] lengths= {sidea,sideb,sidec};
		this.lengths = lengths;
		this.knownSides = new boolean[3];
	}
	
	public void setUnknown(int i){
		if(i>=0 && i < 3) knownSides[i]=true;
	}

	public boolean isUnknown(int i){
		return knownSides[i];
	}
	
	public Expression getSideExpression(int i){
		if((int)lengths[i] == lengths[i]){
			Term[] t = {new Term((int)(lengths[i]))};
			
			return new Expression(t);
		}
		else if(Math.abs((int)(Math.pow(lengths[i],2)+.5) - Math.pow(lengths[i],2)) <.1){
			Term[] t = {new Term(new SimplestRadicalForm(2,(int)(Math.pow(lengths[i],2)+.5)))};
			
			return new Expression(t);
		}
		Term[] t = {new Term(new Fraction((lengths[i])))};
		
		return new Expression(t);
	}

	public String getSideExpressionRounded(int sideIndex, int decimals) {
		if((int)lengths[sideIndex] == lengths[sideIndex]){
			return ""+(int)(lengths[sideIndex]);
		}
		else {
			
			return ""+Ops.roundDouble(lengths[sideIndex], decimals);
		}

	}

	
}
