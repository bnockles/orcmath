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
import com.orcmath.objects.Ops;

public class Reflection extends Transformation{

	private static boolean zeroLastTime;
	
	public Reflection (double slope, double intercept){
		type = Transformation.REFLECT;
		arg=slope;
		optionalArg=intercept;
		initNotation();
	}

	public Reflection(int difficulty) {
		type = Transformation.REFLECT;
		setEquation(difficulty);
		initNotation();
	}

	private void setEquation(int difficulty) {
		double[] slopes={-1,0,1,DrawableOps.undefinedIdentifier};
		if(difficulty==1){
			if (!zeroLastTime){
				arg=0;
				zeroLastTime=true;
			}
			else {
				arg=DrawableOps.undefinedIdentifier;
				zeroLastTime=false;
			}
			optionalArg=0;//assures that reflection only happens over an axis
		}else if(difficulty==2){
			if (!zeroLastTime){
				arg=0;
				zeroLastTime=true;
			}
			else {
				arg=DrawableOps.undefinedIdentifier;
				zeroLastTime=false;
			}
			optionalArg=Ops.randomInt(-5, 5);//assures that reflection only happens over an axis
		}else {
			arg=slopes[Ops.randomInt(0, slopes.length-1)];
			if(difficulty==3){
				while (arg==-1){
					arg=slopes[Ops.randomInt(0, slopes.length-1)];
				}
			}
			optionalArg=0;
			if(arg==0 || arg == DrawableOps.undefinedIdentifier){
				optionalArg=Ops.randomInt(-5, 5);
			}
		}
	}
}
