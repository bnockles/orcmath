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


public class VolumePyramid extends UseFormula{
		
		int baseEquation;
		double nameVersion;
		
		public VolumePyramid(int difficulty){
			instructions = "";
			verticalSpacing=25;	
			whetherInstructionsAreNeverIncluded=true;
			this.difficulty=difficulty;
			//for all formula problems, include the following
			setDiagram();
			nameVersion = Math.random();
			setFormula(new ReferenceFormula(ReferenceFormula.PYRAMID_VOLUME));
			formula.chooseRandomUnknown(difficulty);
			chooseUnits();
			determineObjectDescription();
			determineShortObjectDescription();
			getInstance();
		}

		private void determineObjectDescription() {	
			int[] valueNames = {ReferenceFormula.LENGTH, ReferenceFormula.WIDTH, ReferenceFormula.HEIGHT};//the array of labels
			if(nameVersion<.5) {
				object = " rectangular pyramid";//IMPORTANT! Keep space here so than 'n' can be added to words that start with a vowel
				double[] values ={Ops.randomInt(4, 12),Ops.randomInt(4, 12),Ops.randomInt(10, 30)};//the array of values, height and radius, volume is calculated automatically
				formula.setValues(valueNames,values);
			}
			else {
				object = " grocery store manager stacks cans of a new energy drink in the form of a rectangular pyramid. The pyramid";
				unit = "inch";
				units="inches";
				double[] values ={Ops.randomInt(16, 40),Ops.randomInt(16, 40),Ops.randomInt(20, 55)};//the array of values, height and radius, volume is calculated automatically
				formula.setValues(valueNames,values);
			}
		}
		
		private void determineShortObjectDescription() {	
			if(nameVersion<.5) objectShort = "the pyramid";//IMPORTANT! Keep space here so than 'n' can be added to words that start with a vowel
			else objectShort = "the pyramid formed by the tower of cans";
		}
		
		protected String initQuestion() {
			String s="";
			if (formula.getUnknown()==ReferenceFormula.VOLUME){
				s+="A"+object+" has a width of "+formula.getValueString(ReferenceFormula.WIDTH)+" "+units +
						", length of "+formula.getValueString(ReferenceFormula.LENGTH)+" "+units+" and a height of "+
						formula.getValueString(ReferenceFormula.HEIGHT)+" "+units+". What is the volume " +
								"of "+objectShort+", to the nearest tenth?";
			}else if (formula.getUnknown()==ReferenceFormula.HEIGHT){
				s+="A"+object+" has a volume of "+formula.getValueString(ReferenceFormula.VOLUME)+" cubic "+units+". " +
						"If the base has a width of "+formula.getValueString(ReferenceFormula.WIDTH)+" "+units +
						" and a length of "+formula.getValueString(ReferenceFormula.LENGTH)+" "+units+" , what is the height of "+
						objectShort+", to the nearest tenth?";
			}else if (formula.getUnknown()==ReferenceFormula.WIDTH){
				s+="A"+object+" has a volume of "+formula.getValueString(ReferenceFormula.VOLUME)+" cubic "+units+". " +
						"If the base has a length of "+formula.getValueString(ReferenceFormula.LENGTH)+" "+units+
						" and the height of the entire"+objectShort.replaceFirst("the ", "") +" is "+formula.getValueString(ReferenceFormula.HEIGHT)+" "+units+", what is the width of" +
								" the base, to the nearest tenth?";
			}
			else{
				s+="A"+object+" has a volume of "+formula.getValueString(ReferenceFormula.VOLUME)+" cubic "+units+". " +
						"If the base has a width of "+formula.getValueString(ReferenceFormula.WIDTH)+" "+units+
						" and the height of the entire "+objectShort.replaceFirst("the ", "") +" is "+formula.getValueString(ReferenceFormula.HEIGHT)+
						" "+units+", what is the length of" +
								" the base, to the nearest tenth?";
			}
			return s;
		}
		
	}
