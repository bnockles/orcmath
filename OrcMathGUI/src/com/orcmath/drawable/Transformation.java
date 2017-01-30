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
package com.orcmath.drawable;

import com.orcmath.drawabletype.DrawableOps;
import com.orcmath.objects.Format;

public class Transformation {

	protected int type;
	protected String notation;
	protected double arg;
	protected double optionalArg;
	
	public static int TRANSLATE = 0;
	public static int REFLECT = 1;
	public static int ROTATE = 2;
	public static int DILATE = 3;

	public Transformation(){
		
	}
	
	public Transformation(int transformationType, double arg,double optionalArg) {
		this.type=transformationType;
		this.arg=arg;
		this.optionalArg=optionalArg;
		notation = initNotation();
	}


	protected String initNotation() {
		notation = "";
		String argString = Format.doubleToString(this.arg);
		String optionalArgString = Format.doubleToString(this.optionalArg);
		if(type==TRANSLATE){
			notation ="T_{\\left("+argString+","+optionalArgString+"\\right)}";
		}else if(type==ROTATE){
			notation ="R_{"+argString+"^\\circ }";
		}else if(type==REFLECT){
			if(arg==DrawableOps.undefinedIdentifier){
				if(optionalArg==0)notation ="r_{y-\\text{axis}}";
				else notation ="r_{x="+optionalArgString+"}";
			}else if(arg==1){
				if(optionalArg==0){
					notation ="r_{y=x}";
				}else if(optionalArg>0){
					notation ="r_{y=x+"+optionalArgString+"}";
				}else{
					notation ="r_{y=x"+optionalArgString+"}";
				}
			}else if(arg==-1){
				if(optionalArg==0){
					notation ="r_{y=-x}";
				}else if(optionalArg>0){
					notation ="r_{y=-x+"+optionalArgString+"}";
				}else{
					notation ="r_{y=-x"+optionalArgString+"}";
				}
			}else if(arg==0){
				if(optionalArg==0)notation ="r_{x-\\text{axis}}";
				else notation ="r_{y="+optionalArgString+"}";
			}else{
				notation ="r_{y="+argString+"x+"+optionalArgString+"}";
			}
		}else if(type==DILATE){
			if(optionalArg==arg){
				notation ="D_{"+argString+"}";
			}else{
				notation ="D_{\\left("+argString+","+optionalArgString+"\\right)}";
			}			
		}	
		return notation;
	}

	public static String getLiteralDescription(Transformation t, double arg1, double arg2){
		String s="";
		String arg1String =Format.doubleToString(arg1);
		String arg2String =Format.doubleToString(arg2);
		if(t.getType()==Transformation.DILATE){
			s+="A dilation with a scale factor of ";
			if(arg1==arg2){
				s+=arg1String;
			}else{
				s+=arg1String+" in the x-axis and "+arg2String+" in the y-axis.";
			}
		}else if(t.getType()==Transformation.ROTATE){
			s+="A rotation of "+arg1String+" degrees. ";
		}else if(t.getType()==Transformation.TRANSLATE){
			s+="A translation of "+Format.doubleToString(Math.abs(arg1))+" unit";
			if(arg1>0){
				if(arg1!=1)s+="s";
				s+=" RIGHT and "+Format.doubleToString(Math.abs(arg2))+" unit";
			}
			else {
				if(arg1!=-1)s+="s";
				s+=" LEFT and "+Format.doubleToString(Math.abs(arg2))+" unit";
			}
			if(arg2>0){
				if(arg2!=1)s+="s";
				s+=" UP.";
			}
			else {
				if(arg2!=-1)s+="s";
				s+=" DOWN.";
			}		
		}else if(t.getType()==Transformation.REFLECT){
			s+="A reflection over the line ";
			String lineName="";
			if(arg1==DrawableOps.undefinedIdentifier){
				lineName+="x="+arg2String;
			}else if(arg1==1){
				if(arg2==0){
					lineName+="y=x";
				}else if(arg2>0){
					lineName+="y=x+"+arg2String;
				}else{
					lineName+="y=x"+arg2String;
				}
			}else if(arg1==-1){
				if(arg2==0){
					lineName+="y=-x";
				}else if(arg2>0){
					lineName+="y=-x+"+arg2String;
				}else{
					lineName+="y=-x"+arg2String;
				}
			}else if(arg1==0){
				lineName+="y="+arg2String;
			}else{
				lineName+="y="+arg1String+"x+"+arg2String;
			}
			s+=lineName;
		}
		return s;
	}
	
	public int getType(){
		return type;
	}
	
	public String getTypeName(){
		if(type==TRANSLATE)return "translation";
		else if(type==ROTATE)return "rotation";
		else if(type==REFLECT)return "reflection";
		else return "dilation";
	}
	
	public double getArg() {
		return arg;
	}

	public void setArg(double arg) {
		this.arg = arg;
	}

	public double getOptionalArg() {
		return optionalArg;
	}

	public void setOptionalArg(double optionalArg) {
		this.optionalArg = optionalArg;
	}

	public String getNotation(){
		return notation;
	}
	
	
	
}
