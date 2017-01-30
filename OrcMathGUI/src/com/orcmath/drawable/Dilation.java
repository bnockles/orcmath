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

import com.orcmath.objects.Ops;

public class Dilation extends Transformation{

	private static double lastFactor;
	
	public Dilation(double scale) {
		type = Transformation.DILATE;
		arg=scale;
		optionalArg=scale;
		initNotation();
	}
	
	public Dilation(double xScale, double yScale) {
		type = Transformation.DILATE;
		arg=xScale;
		optionalArg=yScale;
		initNotation();
	}
	
	public Dilation (){
		type = Transformation.DILATE;
		double[] scales ={.25,.5,1.0/3.0,2,3}; 
		double scale = scales[Ops.randomInt(0, scales.length-1)];
		while (scale==lastFactor){
			scale = scales[Ops.randomInt(0, scales.length-1)];
		}
		lastFactor=scale;
		System.out.println("This scale factor was selected "+scale);
		arg=scale;
		optionalArg=scale;
		initNotation();
	}


}