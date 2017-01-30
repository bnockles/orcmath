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
import com.orcmath.objects.SimplestRadicalForm;

public class CoordinateSegment {

	private CoordinatePoint endpoint1;
	private CoordinatePoint endpoint2;
	private double decimalLength;
	private SimplestRadicalForm radicalLength;
	private double[] linearEquationCoefficients;
	
	public CoordinateSegment(CoordinatePoint p1, CoordinatePoint p2){
		endpoint1=p1;
		endpoint2=p2;
		decimalLength=DrawableOps.getDistance(p1, p2);
		radicalLength=new SimplestRadicalForm(2, (int)(Math.pow((p1.getxCoordinate()-p2.getxCoordinate()),2)+Math.pow((p1.getyCoordinate()-p2.getyCoordinate()),2)));
		linearEquationCoefficients=DrawableOps.getLinearEquation(p1, p2);
	}

	public CoordinatePoint getEndpoint1() {
		return endpoint1;
	}

	public CoordinatePoint getEndpoint2() {
		return endpoint2;
	}

	public double getDecimalLength() {
		return decimalLength;
	}

	public SimplestRadicalForm getRadicalLength() {
		return radicalLength;
	}

	public double[] getLinearEquationCoefficients() {
		return linearEquationCoefficients;
	}
	
	
}
