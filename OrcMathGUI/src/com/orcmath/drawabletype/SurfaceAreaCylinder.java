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

public class SurfaceAreaCylinder extends UseFormula{
	
	
	int areaVariation;//0-lateral area, 1-surface area, 2-lateral area + one side
	double nameVersion;//the description of the object, i.e. "ice cream cone" versus "paper cup"
	
	public SurfaceAreaCylinder(int difficulty){
		instructions = "";
		verticalSpacing=25;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;
		//for all formula problems, include the following
		setDiagram();
		areaVariation=ReferenceFormula.SURFACE_AREA;
		if((Math.random()<.3 || difficulty==1) && difficulty!=2) areaVariation=ReferenceFormula.LATERAL_AREA;
		useRadius = false;
		if(Math.random()>.5 || difficulty==1) useRadius=true;
		nameVersion = Math.random();
		setFormula(new ReferenceFormula(ReferenceFormula.CYLINDER_LATERAL_AREA));
		if(difficulty!=2)formula.chooseRandomUnknown(difficulty);
		else formula.chooseRandomUnknown(1);//at difficulty 2, total surface are is solved for instead of height/radius/etc
		formula.setVariationOnLateralArea(areaVariation);
		chooseUnits();
		determineObjectDescription();
		getInstance();
	}

	
	
	private void determineObjectDescription() {	
		int[] valueNames = {ReferenceFormula.RADIUS, ReferenceFormula.HEIGHT};//the array of labels
		if(areaVariation==ReferenceFormula.LATERAL_AREA){
			if(nameVersion<.3) {
				object = " cylinder";//IMPORTANT! Keep space here so than 'n' can be added to words that start with a vowel
				objectShort = " the lateral face of the cylinder";
				double[] values ={Ops.randomInt(1, 12),Ops.randomInt(1, 20)};//the array of values, height and radius, volume is calculated automatically
				formula.setValues(valueNames,values);
			}
			else if(nameVersion<.7) {
				object = " canning company produces labels for the soup cans they prouduce. The labels wraps around " +
						"the lateral face of the cylindrical cans. Assume the label does not overlap. Each can";
				objectShort = " the label";
				unit = "inch";
				units="inches";
				double[] values ={Ops.roundDouble(Ops.randomDouble(2, 4),1),Ops.roundDouble(Ops.randomDouble(3, 6),1)};//the array of values, height and radius, volume is calculated automatically
				formula.setValues(valueNames,values);
			}
			else{
				object = " factory needs to cut a sheet of metal for the lateral facing of a cylindrical water tower. The water tower";
				objectShort = " the sheet of metal";
				unit = "foot";
				units="feet";
				double[] values ={Ops.randomInt(6, 11),Ops.randomInt(5, 15)};//the array of values, height and radius, volume is calculated automatically
				formula.setValues(valueNames,values);
			}
		}else{
			if(nameVersion<.2) {
				object = " cylinder";//IMPORTANT! Keep space here so than 'n' can be added to words that start with a vowel
				objectShort = " the cylinder";
				double[] values ={Ops.randomInt(1, 12),Ops.randomInt(1, 20)};//the array of values, height and radius, volume is calculated automatically
				formula.setValues(valueNames,values);
			}
			else if(nameVersion<.4) {
				object = " cylindrical can";
				objectShort = " the can";
				unit = "inch";
				units="inches";
				double[] values ={Ops.randomInt(2, 5),Ops.randomInt(2, 8)};//the array of values, height and radius, volume is calculated automatically
				formula.setValues(valueNames,values);
			}
			else if(nameVersion<.6) {
				object = " container in the shape of a cylinder";
				objectShort = " the container";
				unit = "foot";
				units="feet";
				double[] values ={Ops.roundDouble(Ops.randomDouble(2, 4),1),Ops.roundDouble(Ops.randomDouble(3, 6),1)};//the array of values, height and radius, volume is calculated automatically
				formula.setValues(valueNames,values);
			}
			else if(nameVersion<.8) {
				object = " new product is going to be packaged in cardboard, cylindrical box.";
				if(Math.random()<.5 && difficulty>2){
					object+=" The container is designed to be closed on only one side. It";
					areaVariation=ReferenceFormula.LATERAL_AREA_PLUS_FACE;
				}else{
					object+=" The container is designed to be closed on both sides. It";
				}
				objectShort = " the box";
				unit = "inch";
				units="inches";
				double[] values ={Ops.roundDouble(Ops.randomDouble(2, 6),1),Ops.randomInt(5, 18)};//the array of values, height and radius, volume is calculated automatically
				formula.setValues(valueNames,values);
			}
			else {
				object = " factory manufactures cans in the shape of cylinders.";
				if(Math.random()<.5 && difficulty>2){
					object+=" The cans are designed to be closed on only one side. Each can";
					areaVariation=ReferenceFormula.LATERAL_AREA_PLUS_FACE;
				}else{
					object+=" The cans are designed to be closed on both sides. Each can";
				}
				objectShort = " each can";
				unit = "inch";
				units="inches";
				double[] values ={Ops.roundDouble(Ops.randomDouble(2, 4),1),Ops.roundDouble(Ops.randomDouble(2, 4),1)};//the array of values, height and radius, volume is calculated automatically
				formula.setValues(valueNames,values);
			}
		}
	}
	
	private String provideRadiusOrDiameter(boolean trueForRadius){
		if(trueForRadius){
			return "radius of "+formula.getValueString(ReferenceFormula.RADIUS)+" "+units;
		}else{
			return "diameter of "+2*formula.getValue(ReferenceFormula.RADIUS)+" "+units;
		}
	}
	
	private String radiusOrDiameter(boolean trueForRadius){
		if(trueForRadius){
			return "radius";
		}else{
			return "diameter";
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
			s+="A"+object+" has a "+provideRadiusOrDiameter(useRadius)+" and a height of "+
					formula.getValueString(ReferenceFormula.HEIGHT)+" "+units;
			if(areaVariation==ReferenceFormula.LATERAL_AREA){
				s+=". What is the area of"+objectShort+" to the nearest tenth?";
			}else{
				s+=". What is the total surface area of"+objectShort+" to the nearest tenth?";
			}
		}else if (formula.getUnknown()==ReferenceFormula.HEIGHT){
			s+="A"+object;
			if(areaVariation==ReferenceFormula.LATERAL_AREA){
				s+=" has a lateral surface area of "+formula.getValueString(ReferenceFormula.LATERAL_AREA)+" square "+units+". ";
			}else if(areaVariation==ReferenceFormula.LATERAL_AREA_PLUS_FACE){
				s+=" has a surface area of "+formula.getValueString(ReferenceFormula.LATERAL_AREA_PLUS_FACE)+" square "+units+". ";
			}else{
				s+=" has a total surface area of "+formula.getValueString(ReferenceFormula.SURFACE_AREA)+" square "+units+". ";				
			}
			s+="If the base has a "+provideRadiusOrDiameter(useRadius)+", what is the height of"+objectShort+" to the nearest tenth?";
		}else if (formula.getUnknown()==ReferenceFormula.RADIUS){
			s+="A"+object;
			if(areaVariation==ReferenceFormula.LATERAL_AREA){
				s+=" has a lateral surface area of "+formula.getValueString(ReferenceFormula.LATERAL_AREA)+" square "+units+". ";
			}else if(areaVariation==ReferenceFormula.LATERAL_AREA_PLUS_FACE){
				s+=" has a surface area of "+formula.getValueString(ReferenceFormula.LATERAL_AREA_PLUS_FACE)+" square "+units+". ";
			}else{
				s+=" has a total surface area of "+formula.getValueString(ReferenceFormula.SURFACE_AREA)+" square "+units+". ";				
			}
			s+="If the entire"+objectShort.replace("the ", "").replace("each ", "")+" has a height of "+formula.getValueString(ReferenceFormula.HEIGHT)+" "+units+", what " +
							"is the length of the "+radiusOrDiameter(useRadius)+" to the nearest tenth?";
			if(!useRadius)alternateObjective=OBJECTIVE_DIAMETER;
		}else{
			s+="the initQuestion() method does not have a variation of the text for this objective";
		}
		return s;
	}
	
}
