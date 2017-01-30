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

import com.orcmath.drawable.ReferenceFormula;
import com.orcmath.objects.Ops;

public class SurfaceAreaSphere extends UseFormula{
	
	double nameVersion;//the description of the object, i.e. "ice cream cone" versus "paper cup"
	
	public SurfaceAreaSphere(int difficulty){
		instructions = "";
		verticalSpacing=25;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;
		//for all formula problems, include the following
		setDiagram();
		useRadius = false;
		if(Math.random()>.5 || difficulty==1) useRadius=true;	
		nameVersion = Math.random();
		setFormula(new ReferenceFormula(ReferenceFormula.SPHERE_SURFACE_AREA));
		determineObjectDescription();
		formula.chooseRandomUnknown(difficulty);
		getInstance();
	}

	
	
	private void determineObjectDescription() {	
		int[] valueNames = {ReferenceFormula.RADIUS};//the array of labels
		if(nameVersion<.25) {
			object = " sphere";//IMPORTANT! Keep space here so than 'n' can be added to words that start with a vowel
			objectShort = "the sphere";
			chooseUnits();
			double[] values ={Ops.randomInt(2, 12)};//the array of values, height and radius, volume is calculated automatically
			formula.setValues(valueNames,values);
		}
		else if(nameVersion<.5) {
			object = " glass vase has a spherical shape. The sphere";
			objectShort = "the vase";
			unit = "inch";
			units="inches";
			double[] values ={Ops.roundDouble(Ops.randomDouble(4, 9), 1)};//the array of values, height and radius, volume is calculated automatically
			formula.setValues(valueNames,values);
		}
		else if(nameVersion<.75) {
			object = " globe";
			objectShort = "the globe";
			unit = "inch";
			units="inches";
			double[] values ={Ops.roundDouble(Ops.randomDouble(5, 10),1)};//the array of values, height and radius, volume is calculated automatically
			formula.setValues(valueNames,values);
		}
		else {
			object = " sporting goods manufacturer produces inflatable balls. A kickball produced by the company";
			objectShort = "the kickball";
			unit = "cm";
			units="cm";
			double[] values ={Ops.roundDouble(Ops.randomDouble(10, 20),1)};//the array of values, height and radius, volume is calculated automatically
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
		if (formula.getUnknown()==ReferenceFormula.SURFACE_AREA){
			s+="A"+object+" has a "+provideRadiusOrDiameter(useRadius)+". What is the surface area of "+objectShort+" to the nearest tenth?";
		}else if (formula.getUnknown()==ReferenceFormula.RADIUS){
			s+="A"+object +" has a surface area of "+formula.getValueString(ReferenceFormula.SURFACE_AREA)+" square "+units+". What " +
							"is the length of the "+radiusOrDiameter(useRadius)+" to the nearest tenth?";
			if(!useRadius)alternateObjective=OBJECTIVE_DIAMETER;
		}else{
			s+="the initQuestion() method does not have a variation of the text for this objective";
		}
		return s;
	}
	
}