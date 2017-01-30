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
/*
 * Level 1 is lateral area only
 * Level 2 is surface area
 * Level 3/4 may solve for a different variable
 */


import com.orcmath.drawable.ReferenceFormula;
import com.orcmath.objects.Ops;

public class SurfaceAreaCone extends UseFormula{
	
	boolean lateralAreaOnly;
	boolean closedOnOneSideOnly;
	String nameOfAreaObjective;
	double nameVersion;//the description of the object, i.e. "ice cream cone" versus "paper cup"
	
	public SurfaceAreaCone(int difficulty){
		instructions = "";
		verticalSpacing=25;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;
		//for all formula problems, include the following
		setDiagram();
		lateralAreaOnly=false;
		if((Math.random()>.3 || difficulty==1) && difficulty!=2) lateralAreaOnly=true;
		useRadius = false;
		if(Math.random()>.5 || difficulty==1) useRadius=true;	
		nameVersion = Math.random();
		setFormula(new ReferenceFormula(ReferenceFormula.RIGHT_CIRCULAR_CONE_LATERAL_AREA));
		formula.setVariationOnSlantHeight(ReferenceFormula.HEIGHT);
		if(Math.random()>.5 || difficulty==1) {
			useHeight=false;
			formula.setVariationOnSlantHeight(ReferenceFormula.SLANT_HEIGHT);
		}
		determineObjectDescription();//may alter defaults
		formula.chooseRandomUnknown(difficulty);
		if(lateralAreaOnly)formula.setVariationOnLateralArea(ReferenceFormula.LATERAL_AREA);
		else formula.setVariationOnLateralArea(ReferenceFormula.LATERAL_AREA_PLUS_FACE);
		getInstance();
	}

	
	
	private void determineObjectDescription() {	
		int[] valueNames = {ReferenceFormula.RADIUS, ReferenceFormula.SLANT_HEIGHT};//the array of labels
		if(nameVersion<.3) {
			object = " right circular cone";//IMPORTANT! Keep space here so than 'n' can be added to words that start with a vowel
			objectShort = "the cone";
			chooseUnits();
			if(lateralAreaOnly)nameOfAreaObjective="lateral area";
			else nameOfAreaObjective="total surface area";
			double radius=Ops.randomInt(3, 12);
			double slant=Ops.randomInt((int)Math.ceil(radius), 25);
			double[] values ={radius,slant};//the array of values, height and radius, volume is calculated automatically
			formula.setValues(valueNames,values);
		}
		else if(nameVersion<.7) {
			object = "n ice cream store sells ice cream in wafer cones. Each wafer cone can be represented as the lateral face of a cone. An individual cone";
			objectShort = "the wafer cone";
			unit = "inch";
			units="inches";
			lateralAreaOnly=true;
			nameOfAreaObjective="total area";
			double radius=Ops.randomDouble(1, 2.5);
			int slant=Ops.randomInt((int)Math.ceil(radius), 8);
			double[] values ={Ops.roundDouble(radius, 1),slant};
			formula.setValues(valueNames,values);
		}
		else {
			object = " paper supplies company produces cups in the shape of cones for water dispensers. The cups can be represented as the lateral face of a cone. Each cone";
			objectShort="each cup";
			unit = "cm";
			units="cm";
			lateralAreaOnly=true;
			nameOfAreaObjective="total area";
			double radius=Ops.randomDouble(2, 3.5);
			double slant=Ops.randomDouble(radius, 8);
			double[] values ={Ops.roundDouble(radius, 1),Ops.roundDouble(slant, 1)};
			formula.setValues(valueNames,values);
		}
	}
	
	private String provideRadiusOrDiameter(boolean trueForRadius){
		if(trueForRadius){
			return "radius of "+formula.getValueString(ReferenceFormula.RADIUS)+" "+units;
		}else{
			return "diameter of "+2*formula.getValue(ReferenceFormula.RADIUS)+" "+units;
		}
	}
	
	private String provideHeightOrSlant(){
		if(useHeight){
			return "height of "+formula.getValueString(ReferenceFormula.HEIGHT)+" "+units;
		}else{
			return "slant length of "+formula.getValueString(ReferenceFormula.SLANT_HEIGHT)+" "+units;
		}
	}
	
	private String radiusOrDiameter(boolean trueForRadius){
		if(trueForRadius){
			return "radius";
		}else{
			return "diameter";
		}
	}
	
	private String findHeightOrSlant(boolean findHeight){
		if(useHeight){
			return "height";
		}else{
			return "slant height";
		}
	}
	
	protected int getAlternateObjective() {
		if(!useRadius)
		return OBJECTIVE_DIAMETER;
		else return 0;
	}
	
	protected String initQuestion() {
		String s="";
		if (formula.getUnknown()==ReferenceFormula.LATERAL_AREA){
			s+="A"+object+" has a "+provideRadiusOrDiameter(useRadius)+" and a "+provideHeightOrSlant();
			if(lateralAreaOnly){
				s+=". What is the "+nameOfAreaObjective+" of "+objectShort+" to the nearest tenth?";
			}else{
				s+=". What is the "+nameOfAreaObjective+" of "+objectShort+" to the nearest tenth?";
			}
		}else if (formula.getUnknown()==ReferenceFormula.SLANT_HEIGHT){
			s+="A"+object;
			if(lateralAreaOnly){
				s+=" has a "+nameOfAreaObjective+" of "+formula.getValueString(ReferenceFormula.LATERAL_AREA)+" square "+units+". ";
			}else{
				s+=" has a "+nameOfAreaObjective+" of "+formula.getValueString(ReferenceFormula.LATERAL_AREA_PLUS_FACE)+" square "+units+". ";				
			}
			boolean findHeight = useHeight;
			if(difficulty>2 && Math.random()>.5)findHeight=!useHeight;
			s+="If the base has a "+provideRadiusOrDiameter(useRadius)+", what is the "+findHeightOrSlant(findHeight)+" of "+objectShort+" to the nearest tenth?";
		}else if (formula.getUnknown()==ReferenceFormula.RADIUS){
			s+="A"+object;
			if(lateralAreaOnly){
				s+=" has a lateral surface area of "+formula.getValueString(ReferenceFormula.LATERAL_AREA)+" square "+units+". ";
			}else{
				s+=" has a total surface area of "+formula.getValueString(ReferenceFormula.LATERAL_AREA_PLUS_FACE)+" square "+units+". ";				
			}
			s+="If the entire"+objectShort.replace("the ", "").replace("each ", "")+" has a "+provideHeightOrSlant()+", what " +
							"is the length of the "+radiusOrDiameter(useRadius)+" to the nearest tenth?";
			if(!useRadius)alternateObjective=OBJECTIVE_DIAMETER;
		}else{
			s+="the initQuestion() method does not have a variation of the text for this objective";
		}
		return s;
	}
	
}

