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

import java.awt.Component;

import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;

public class ReferenceFormula {

	int type;
	String referenceSheetRepresentation;
	String baseDescription;
	int unknown;
	int unknownExp;
	int variationOnLateralArea;//this variable is set whenever the lateral area formula is used but surface area is solved for
	int variationOnSlantHeight;
	int[] variableComponents;
	boolean useCoefficient;
	boolean baseRequired;
	boolean heightRequired;
	boolean slantLengthRequired;
	boolean otherVariables;
	
	double length;
	double width;
	double height;
	double radius;
	double slantHeight;
	double volume;
	double surfaceArea;
	double lateralArea;
	double lateralAreaPlusBase;
	
	public static int LENGTH=10;
	public static int WIDTH=11;
	public static int HEIGHT=12;
	public static int RADIUS=13;
	public static int SLANT_HEIGHT=14;
	public static int VOLUME=15;	
	public static int SURFACE_AREA=16;	
	public static int LATERAL_AREA=17;	
	public static int LATERAL_AREA_PLUS_FACE=18;	
	
	
	public static int CYLINDER_VOLUME = 1;
	public static int PYRAMID_VOLUME = 2;
	public static int RIGHT_CIRCULAR_CONE_VOLUME = 3;
	public static int SPHERE_VOLUME = 4;
	public static int CYLINDER_LATERAL_AREA= 5;
	public static int RIGHT_CIRCULAR_CONE_LATERAL_AREA = 6;
	public static int SPHERE_SURFACE_AREA=7;
	
	public ReferenceFormula(int type){
		this.type=type;
		if (type==CYLINDER_VOLUME){
			baseRequired=true;
			heightRequired=true;
			variableComponents = new int[3];
			variableComponents[0]=VOLUME;
			variableComponents[1]=RADIUS;
			variableComponents[2]=HEIGHT;
			referenceSheetRepresentation="V=Bh";
			baseDescription="circular";
		}else if (type==PYRAMID_VOLUME || type ==RIGHT_CIRCULAR_CONE_VOLUME){
			baseRequired=true;
			heightRequired=true;
			referenceSheetRepresentation="V=\\frac{1}{3}Bh";
			if(type==PYRAMID_VOLUME){
				variableComponents = new int[4];
				variableComponents[0]=VOLUME;
				variableComponents[1]=WIDTH;
				variableComponents[2]=LENGTH;
				variableComponents[3]=HEIGHT;
				baseDescription="rectangular";
			}else{//CONE
				variableComponents = new int[3];
				variableComponents[0]=VOLUME;
				variableComponents[1]=RADIUS;
				variableComponents[2]=HEIGHT;
				baseDescription="circular";
			}
		}else if(type==SPHERE_VOLUME){
			baseRequired=false;
			heightRequired=false;
			otherVariables=true;
			referenceSheetRepresentation="V=\\frac{4}{3}\\pi r^3";
			variableComponents = new int[2];
			variableComponents[0]=VOLUME;
			variableComponents[1]=RADIUS;
			baseDescription="no base";
		}else if(type==CYLINDER_LATERAL_AREA){
			heightRequired=true;
			otherVariables=true;
			referenceSheetRepresentation="L=2\\pi rh";
			variableComponents = new int[3];
			variableComponents[0]=LATERAL_AREA;
			variableComponents[1]=RADIUS;
			variableComponents[2]=HEIGHT;
			baseDescription="circular";
		}else if(type==RIGHT_CIRCULAR_CONE_LATERAL_AREA){
			slantLengthRequired=true;
			otherVariables=true;
			referenceSheetRepresentation="L=\\pi rl";
			variableComponents = new int[3];
			variableComponents[0]=LATERAL_AREA;
			variableComponents[1]=RADIUS;
			variableComponents[2]=SLANT_HEIGHT;
			baseDescription="circular";
		}else if(type==SPHERE_SURFACE_AREA){
			otherVariables=true;
			referenceSheetRepresentation="SA=4\\pi r^2";
			variableComponents = new int[2];
			variableComponents[0]=SURFACE_AREA;
			variableComponents[1]=RADIUS;
			baseDescription="no base";
		}
		else{
			referenceSheetRepresentation="";
		}
	}

	public void setValues(int[] valueNames, double[] values) {
		//set all values according to type
		for(int index=0; index<valueNames.length; index++){
			if(valueNames[index]==LENGTH) length=values[index];
			else if(valueNames[index]==WIDTH) width=values[index];
			else if(valueNames[index]==HEIGHT) height=values[index];
			else if(valueNames[index]==RADIUS) radius=values[index];
			else if(valueNames[index]==SLANT_HEIGHT) slantHeight=values[index];
		}
		//calculates surface area and volume, based on input
		if(type==CYLINDER_VOLUME) volume = Math.PI*Math.pow(radius, 2)*height;
		else if(type==PYRAMID_VOLUME)volume = length*width*height/3;
		else if(type==RIGHT_CIRCULAR_CONE_VOLUME)volume = Math.PI*Math.pow(radius, 2)*height/3;	
		else if(type==SPHERE_VOLUME)volume = 4*Math.PI*Math.pow(radius, 3)/3;	
		else if(type==RIGHT_CIRCULAR_CONE_LATERAL_AREA){
			lateralArea= Math.PI*radius*slantHeight;
			lateralAreaPlusBase=lateralArea+Math.PI*Math.pow(radius, 2);
			height = Math.sqrt(Math.pow(slantHeight, 2)-Math.pow(radius, 2));
		}else if(type==CYLINDER_LATERAL_AREA){
			lateralArea= 2*Math.PI*radius*height;
			double base = Math.PI*Math.pow(radius, 2);
			lateralAreaPlusBase=lateralArea+base;
			surfaceArea= lateralAreaPlusBase+base;
		}else if(type==SPHERE_SURFACE_AREA)surfaceArea= 4*Math.PI*Math.pow(radius, 2);
	}

	//chooses a variable that will be defined as the unknown
	public void chooseRandomUnknown(int difficulty) {
		if(difficulty==1 || difficulty==2){
			unknown = variableComponents[0];
		}else{
			int make1ToExcludeVolumeFromSelection = 1;
			unknown = variableComponents[Ops.randomInt(make1ToExcludeVolumeFromSelection, variableComponents.length-1)];
			while (type!=SPHERE_SURFACE_AREA && (unknown==RADIUS && variationOnLateralArea!=LATERAL_AREA)){
				unknown = variableComponents[Ops.randomInt(make1ToExcludeVolumeFromSelection, variableComponents.length-1)];
			}
		}
	}
	
	public int[] getVariableComponents(){
		return variableComponents;
	}
	
	public String getLeft(){
		if (variableComponents[0]==VOLUME)return "V";
		else if(variableComponents[0]==SURFACE_AREA)return "SA";
		else if(variableComponents[0]==LATERAL_AREA)return "L";
		else return "unidentified getLeft()";
	}
	
	public String getPluggedInLeft(){
		if(unknown==variableComponents[0]){
			if(unknown==VOLUME)return "V";
			else if(unknown==SURFACE_AREA)return "SA";
			else if(unknown==LATERAL_AREA)return "L";
			else return "unidentified in getPluggedInLeft() method of ReferenceFormula";
		}else{
			return getValueString(variableComponents[0]);
		}
	}
	
	public String getPluggedInRight(){
		String s = "";
		s+=getCoefficientString();
		if(baseRequired)s+=getPluggedInBaseFormula();
		if(otherVariables)s+="\\left("+getOtherVariablesPluggedIn()+"\\right)";
		if(heightRequired)s+="\\left("+getValueString(HEIGHT)+"\\right)";
		return s;
	}
	
	public String getOtherVariablesPluggedIn(){
		String s="";
		if(type==SPHERE_VOLUME){
			s+=getValueString(RADIUS)+"^3";
		}
		if(type==CYLINDER_LATERAL_AREA){
			s+=getValueString(RADIUS);
		}
		if(type==RIGHT_CIRCULAR_CONE_LATERAL_AREA){
			s+=getValueString(RADIUS)+"\\right)\\left("+getValueString(SLANT_HEIGHT);
		}
		if(type==SPHERE_SURFACE_AREA){
			s+=getValueString(RADIUS)+"^2";
		}
		return s;
	}
	
	public String getSimplifiedRight(){
		return getSimplifiedRightValue()+getUnknownStringAndExponent();
		
	}
	
	public double getSimplifiedRightValue(){
		double d = 1.0;
		d=d*getCoefficientValue();
		if(baseRequired)d=d*getSimplifiedBaseFormula();
		if(heightRequired && unknown !=HEIGHT)d=d*height;
		if(otherVariables){
			if(type==SPHERE_VOLUME && unknown!=RADIUS){
				d*=Math.pow(radius, 3);
			}
			if(type==CYLINDER_LATERAL_AREA && unknown!=RADIUS){
				d*=radius;
			}
			if(type==RIGHT_CIRCULAR_CONE_LATERAL_AREA){
				if(unknown!=RADIUS)d*=radius;
				if(unknown!=SLANT_HEIGHT)d*=slantHeight;
			}
			if(type==SPHERE_SURFACE_AREA && unknown!=RADIUS){
				d*=Math.pow(radius, 2);
			}
		}
		return Ops.roundDouble(d, 3);	
	}

	public String getRepresentation() {
		return referenceSheetRepresentation;
	}
	
	public boolean requiresBase(){
		return baseRequired;
	}
	
	public String getRepresentationPageView(){
		String s= "\\begin{tabular}{|c|l|}\\hline ";
		s+="\\hspace{45px}\\text{"+getTypeTitle()+"}\\hspace{45px}&"+getRepresentation()+"\\hspace{75px}\\\\";
		if(baseRequired)s+="\\; &\\text{where }B\\text{ is the area of the base}\\\\";
		if(slantLengthRequired)s+="\\; &\\text{where }l\\text{ is the slant height}\\\\";
		s+="\\hline\\end{tabular}";
		return s;
	}
	
	public double getValue(int component){
		if(component==LENGTH)return length;
		else if(component==WIDTH)return width;
		else if(component==HEIGHT)return height;
		else if(component==RADIUS)return radius;
		else if(component==SLANT_HEIGHT)return slantHeight;
		else if(component==VOLUME)return volume;
		else if(component==SURFACE_AREA)return surfaceArea;
		else if(component==LATERAL_AREA)return lateralArea;
		else return 0;
	}
	
	public String getValueString(int component){
		if(unknown!=component){
			double value;
			if(component==LENGTH)value=Ops.roundDouble(length, 1);
			else if(component==WIDTH)value=Ops.roundDouble(width,1);
			else if(component==HEIGHT) value=Ops.roundDouble(height,1);
			else if(component==RADIUS)value=Ops.roundDouble(radius,1);
			else if(component==SLANT_HEIGHT)value=Ops.roundDouble(slantHeight,1);
			else if(component==VOLUME)value=Ops.roundDouble(volume,1);
			else if(component==SURFACE_AREA)value=Ops.roundDouble(surfaceArea,1);
			else if(component==LATERAL_AREA)value=Ops.roundDouble(lateralArea,1);
			else if(component==LATERAL_AREA_PLUS_FACE)value=Ops.roundDouble(lateralAreaPlusBase,1);
			else value=0;
			if(Ops.isInteger(value))return ""+(int)value;
			else return ""+value;
		}else{
			return getValueVariable(component);
		}
	}
	
	public String getValueVariable(int component){
		String value="";
		if(component==LENGTH)value="l";
		else if(component==WIDTH)value="w";
		else if(component==HEIGHT) value="h";
		else if(component==RADIUS)value="r";
		else if(component==SLANT_HEIGHT)value="l";
		else if(component==VOLUME)value="V";
		else if(component==SURFACE_AREA)value="SA";
		else if(component==LATERAL_AREA)value="L";
		else value="";
		return ""+value;
	}
	
	public void setVariationOnLateralArea(int alternateObjective){
		variationOnLateralArea=alternateObjective;
	}
	
	public int getVariationOnLateralArea(){
		return variationOnLateralArea;
	}

	public void setVariationOnSlantHeight(int alternateObjective){
		variationOnSlantHeight=alternateObjective;
	}
	
	public int getVariationOnSlantHeight(){
		return variationOnSlantHeight;
	}
	
	public int getUnknown() {
		return unknown;
	}

	public void setUnknown(int unknown) {
		this.unknown = unknown;
	}
	
	public String getUnknownName(){
		if(unknown==LENGTH)return "length";
		else if(unknown==WIDTH)return "width";
		else if(unknown==HEIGHT) return "height";
		else if(unknown==RADIUS)return "radius";
		else if(unknown==SLANT_HEIGHT)return "slant height";
		else if(unknown==VOLUME) return "volume";
		else if(unknown==SURFACE_AREA)return "surface area";
		else if(unknown==LATERAL_AREA){
			if(variationOnLateralArea==LATERAL_AREA)return "lateral area";
			else if(variationOnLateralArea==SURFACE_AREA)return "surface area";
			else if(variationOnLateralArea==LATERAL_AREA_PLUS_FACE)return "lateral area and one face";
			else return "ERROR: nothing has been identified as an objective";
		}
		else return "ERROR: nothing has been identified as an objective";
	}
	
	public String getUnknownStringAndExponent(){
		String s = getValueVariable(unknown);
		if(type==SPHERE_VOLUME && unknown==RADIUS)s="r^3";
		else if(type==SPHERE_SURFACE_AREA && unknown==RADIUS)s="r^2";
		else if((type==CYLINDER_VOLUME || type==RIGHT_CIRCULAR_CONE_VOLUME) && unknown==RADIUS)s="r^2";
		return s;
	}
	
	public int getUnknownExp(){
		int s = 1;
		if(type==SPHERE_VOLUME && unknown==RADIUS)s=3;
		else if(type==SPHERE_SURFACE_AREA && unknown==RADIUS)s=2;
		else if((type==CYLINDER_VOLUME || type==RIGHT_CIRCULAR_CONE_VOLUME) && unknown==RADIUS)s=2;
		return s;
	}
	
	public int getType(){
		return type;
	}
	
	public String getTypeName(){
		if(type==CYLINDER_VOLUME || type== PYRAMID_VOLUME || type == RIGHT_CIRCULAR_CONE_VOLUME || type==SPHERE_VOLUME)return "volume";
		else if(type==SPHERE_SURFACE_AREA) return "surface area";
		else return "lateral surface area";
	}
	
	public String getTypeTitle(){
		if(type==CYLINDER_VOLUME)return "Cylinder";
		else if(type==PYRAMID_VOLUME)return "Pyramid";
		else if(type==RIGHT_CIRCULAR_CONE_VOLUME || type==RIGHT_CIRCULAR_CONE_LATERAL_AREA)return "Right Circular Cone";
		else if(type==SPHERE_VOLUME || type==SPHERE_SURFACE_AREA)return "Sphere";
		else if(type==CYLINDER_LATERAL_AREA)return "Right Circular Cylinder";
		else return "ERROR: No definition in getTypeTitle()";
	}

	public String getSolidName(){
		if(type==CYLINDER_VOLUME || type == CYLINDER_LATERAL_AREA)return "cylinder";
		else if(type==RIGHT_CIRCULAR_CONE_LATERAL_AREA || type ==RIGHT_CIRCULAR_CONE_VOLUME)return "cone";
		else if(type==PYRAMID_VOLUME)return "pyramid";
		else return "undefined in getSolidName() method";
	}
	
	public String getBaseDescription(){
		return baseDescription;
	}
	
	public String getBaseName(){
		if(type==CYLINDER_VOLUME || 
				type == CYLINDER_LATERAL_AREA || 
				type==RIGHT_CIRCULAR_CONE_LATERAL_AREA || 
				type ==RIGHT_CIRCULAR_CONE_VOLUME)return "circle";
		else if(type==PYRAMID_VOLUME)return "rectangle";
		else return "undefined in getBaseName() method";
	}
	
	public String getBaseFormula(){
		if(type==CYLINDER_VOLUME || 
				type == CYLINDER_LATERAL_AREA || 
				type==RIGHT_CIRCULAR_CONE_LATERAL_AREA || 
				type ==RIGHT_CIRCULAR_CONE_VOLUME)return "\\pi r^2";
		else if(type==PYRAMID_VOLUME)return "lw";
		else {
			if(baseRequired)return "undefined in getBaseFormula() method";
			else return "";
		}
	}
	
	public String getPluggedInBaseFormula(){
		if(type==CYLINDER_VOLUME || 
				type == CYLINDER_LATERAL_AREA || 
				type==RIGHT_CIRCULAR_CONE_LATERAL_AREA || 
				type ==RIGHT_CIRCULAR_CONE_VOLUME)return "\\pi\\left("+getValueString(RADIUS)+"\\right)^2";
		else if(type==PYRAMID_VOLUME)return "\\left("+getValueString(LENGTH)+"\\right)\\left("+getValueString(WIDTH)+"\\right)";
		else {
			if(baseRequired)return "undefined in getPluggedInBaseFormula() method";
			else return "";
		}
	}
	
	public double getSimplifiedBaseFormula(){
		double d=1.0;
		if(type==CYLINDER_VOLUME || 
				type == CYLINDER_LATERAL_AREA || 
				type==RIGHT_CIRCULAR_CONE_LATERAL_AREA || 
				type ==RIGHT_CIRCULAR_CONE_VOLUME){
			d=d*Math.PI;
			if(unknown!=RADIUS)d=d*radius*radius;
		}
		if(type==PYRAMID_VOLUME){
			if(unknown==LENGTH)d=width;
			else if(unknown==WIDTH)d=length;
			else d=length*width;
		}
			return d;
	}
	
	public String getSimplifiedBaseFormulaString(int numberOfBases){
		String s=""+Ops.roundDouble(numberOfBases*getSimplifiedBaseFormula(), 2);
		if(type==CYLINDER_VOLUME || 
				type == CYLINDER_LATERAL_AREA || 
				type==RIGHT_CIRCULAR_CONE_LATERAL_AREA || 
				type ==RIGHT_CIRCULAR_CONE_VOLUME){
			if(unknown==RADIUS)s+="r^2";
		}
		if(type==PYRAMID_VOLUME){
			if(unknown==LENGTH)s+="l";
			else if(unknown==WIDTH)s+="w";
		}
		return s;
	}
	
	public String getCoefficientString(){
		String s="";
		if(type==PYRAMID_VOLUME || type==RIGHT_CIRCULAR_CONE_VOLUME)s+= "\\frac{1}{3}";
		else if (type==SPHERE_VOLUME) s+= "\\frac{4}{3}\\pi";
		else if (type==CYLINDER_LATERAL_AREA) s+= "2\\pi";
		else if (type==RIGHT_CIRCULAR_CONE_LATERAL_AREA) s+= "\\pi";
		else if(type==SPHERE_SURFACE_AREA)s+="4\\pi";
		return s;
	}
	
	public double getCoefficientValue(){
		if(type==PYRAMID_VOLUME || type==RIGHT_CIRCULAR_CONE_VOLUME)return 1.0/3.0;
		else if (type==SPHERE_VOLUME) return 4.0/3.0*Math.PI;
		else if (type==CYLINDER_LATERAL_AREA) return 2.0*Math.PI;
		else if (type==RIGHT_CIRCULAR_CONE_LATERAL_AREA) return Math.PI;
		else if(type==SPHERE_SURFACE_AREA)return 4.0*Math.PI;
		else return 1.0;
	}
	
	public String getHeightVariable(){
		if(heightRequired)return "h";
		else return "";
	}
	
	public String getCommonError(int errorNumber){//for multiple choice, this method returns the most common distractors
		if (type==CYLINDER_VOLUME){
			if(errorNumber==1){
				return "V=bh\\\\\\text{where }b\\text{ is the length of the base}";
			}else if(errorNumber==2){
				return "V=\\frac{1}{3}Bh\\\\\\text{where }B\\text{ is the area of the base}";
			}else{		
				return "V=\\frac{bh}{2}\\\\\\text{where }b\\text{ is the length of the base}";
			}
		}else if (type==PYRAMID_VOLUME || type ==RIGHT_CIRCULAR_CONE_VOLUME){
			if(errorNumber==1){
				return "V=Bh\\\\\\text{where }B\\text{ is the area of the base}";
			}else if(errorNumber==2){
				return "V=\\frac{4}{3}Bh\\\\\\text{where }B\\text{ is the area of the base}";
			}else{		
				return "V=\\frac{1}{2}Bh\\\\\\text{where }B\\text{ is the area of the base}";
			}
		}else{
			return "";
		}
	}
}
