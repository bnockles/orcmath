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
import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;

public class VolumeCylinder extends UseFormula{
	
	
	double nameVersion;
	
	public VolumeCylinder(int difficulty){
		instructions = "";
		verticalSpacing=25;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;
		//for all formula problems, include the following
		setDiagram();
		nameVersion = Math.random();
		setFormula(new ReferenceFormula(ReferenceFormula.CYLINDER_VOLUME));
		formula.chooseRandomUnknown(difficulty);
		chooseUnits();
		determineObjectDescription();
		determineShortObjectDescription();
		getInstance();
	}

	private void determineObjectDescription() {	
		int[] valueNames = {ReferenceFormula.RADIUS, ReferenceFormula.HEIGHT};//the array of labels
		if(nameVersion<.2) {
			object = "cylinder";//IMPORTANT! Keep space here so than 'n' can be added to words that start with a vowel
			double[] values ={Ops.randomInt(1, 12),Ops.randomInt(1, 20)};//the array of values, height and radius, volume is calculated automatically
			formula.setValues(valueNames,values);
		}
		else if(nameVersion<.4) {
			object = "cylindrical can";
			unit = "inch";
			units="inches";
			double[] values ={Ops.randomInt(2, 5),Ops.randomInt(2, 8)};//the array of values, height and radius, volume is calculated automatically
			formula.setValues(valueNames,values);
		}
		else if(nameVersion<.6) {
			object = "container in the shape of a cylinder";
			unit = "foot";
			units="feet";
			double[] values ={Ops.roundDouble(Ops.randomDouble(2, 4),1),Ops.roundDouble(Ops.randomDouble(3, 6),1)};//the array of values, height and radius, volume is calculated automatically
			formula.setValues(valueNames,values);
		}
		else if(nameVersion<.8) {
			object = "new product is going to be packaged in cardboard, cylindrical box. The package";
			unit = "inch";
			units="inches";
			double[] values ={Ops.roundDouble(Ops.randomDouble(2, 6),1),Ops.randomInt(5, 18)};//the array of values, height and radius, volume is calculated automatically
			formula.setValues(valueNames,values);
		}
		else {
			object = "factory manufactures cans in the shape of cylinders. Each can";
			unit = "inch";
			units="inches";
			double[] values ={Ops.roundDouble(Ops.randomDouble(2, 4),1),Ops.roundDouble(Ops.randomDouble(2, 4),1)};//the array of values, height and radius, volume is calculated automatically
			formula.setValues(valueNames,values);
		}
		if(Math.random()>.5){
			useRadius=true;
		}else {
			useRadius=false;
		}
	}
	
	private void determineShortObjectDescription() {	
		if(nameVersion<.2) objectShort = "the cylinder";//IMPORTANT! Keep space here so than 'n' can be added to words that start with a vowel
		else if(nameVersion<.4) objectShort = "the can";
		else if(nameVersion<.6) objectShort = "the container";
		else if(nameVersion<.8) objectShort = "the package";
		else objectShort = "each can";
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
		if (formula.getUnknown()==ReferenceFormula.VOLUME){
			s+="A "+object+" has a "+provideRadiusOrDiameter(useRadius)+" and a height of "+
					formula.getValueString(ReferenceFormula.HEIGHT)+" "+units+". What is the volume " +
							"of "+objectShort+" to the nearest tenth?";
		}else if (formula.getUnknown()==ReferenceFormula.HEIGHT){
			s+="A "+object+" has a volume of "+formula.getValueString(ReferenceFormula.VOLUME)+" cubic "+units+". " +
					"If the base has a "+provideRadiusOrDiameter(useRadius)+", what is the height of "+objectShort+" to the nearest tenth?";
		}else{
			s+="A "+object+" has a volume of "+formula.getValueString(ReferenceFormula.VOLUME)+" cubic "+units+". " +
					"If the entire "+objectShort.replace("the ", "").replace("each ", "")+" has a height of "+formula.getValueString(ReferenceFormula.HEIGHT)+" "+units+", what " +
							"is the length of the "+radiusOrDiameter(useRadius)+" to the nearest tenth?";
			if(!useRadius)alternateObjective=OBJECTIVE_DIAMETER;
		}
		return s;
	}
	
}
