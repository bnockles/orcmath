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

import com.orcmath.drawable.*;
import com.orcmath.objects.Ops;

public class PerformCompTransform extends PerformTransform{

	double angle;
	
	public PerformCompTransform(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
	}
	
	public PerformCompTransform(int difficulty){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=0;	
		whetherInstructionsAreNeverIncluded=true;
		this.difficulty=difficulty;	
		addTransformation(getRandomTransformation());
		addTransformation(getRandomTransformation());
		getInstance();
	}
	
	public Transformation getRandomTransformation(){
		int[] loadedTypes= new int[transformation.size()];
		for(int index=0; index<transformation.size(); index++){
			loadedTypes[index]=transformation.get(index).getType();
		}
		boolean containsTType=true;
		Transformation t;
		double chooser=Math.random();
		if(chooser<.3){
			t=new Translation();
		}else if(chooser<.5){
			t=new Dilation();
		}else if(chooser<.75){
			t = new Rotation();
		}else{
			t=new Reflection(difficulty);
		}	
		//determines if there is a repeat in transformation types
		while(containsTType){
			containsTType=false;
			for(int type:loadedTypes){
				if(t.getType()==type)containsTType=true;
			}
			if(containsTType) t = getRandomTransformation();
		}
		return t;
	}
}