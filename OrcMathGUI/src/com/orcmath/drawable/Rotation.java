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


public class Rotation extends Transformation{

	private static double lastMeasure;
	
	public Rotation (double angle){
		type = Transformation.ROTATE;
		arg=angle;
		initNotation();
	}

	public Rotation() {
		type = Transformation.ROTATE;
		arg=randomAngle();
		while (arg==lastMeasure){
			arg=randomAngle();
		}
		lastMeasure=arg;
		initNotation();
	}
	
	private double randomAngle() {
		double angle;
		int multiplier = Ops.randomInt(1, 3);
		angle = 90.0*multiplier;
		if(angle==270){
			if(Math.random()<.5){
				angle =-90;
			}
		}
		return angle;
	}
}
