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
package com.orcmath.objects;

public class Quadrilateral {
	String name;
	boolean diagonalsAreDrawn;
	boolean hasSupplementaryAngles;
	boolean hasComplementaryAngles;
	int congruentAngleVersion;
	int congruentSegmentVersion;
	int supplementaryVersion;
	int complementaryVersion;
	
	String[] congruentAngle1;
	String[] congruentAngle2;
	String[] congruentSegment1;
	String[] congruentSegment2;
	String[] complementaryAngle1;
	String[] complementaryAngle2;
	String[] supplementaryAngle1;
	String[] supplementaryAngle2;
	
	
	public String getName(){
		return name;
	}

	public boolean isHasSupplementaryAngles() {
		return hasSupplementaryAngles;
	}

	public boolean isHasComplementaryAngles() {
		return hasComplementaryAngles;
	}

	public int getCongruentAngleVersion() {
		return congruentAngleVersion;
	}

	public int getCongruentSegmentVersion() {
		return congruentSegmentVersion;
	}

	public boolean isDiagonalsAreDrawn() {
		return diagonalsAreDrawn;
	}

	public int getSupplementaryVersion() {
		return supplementaryVersion;
	}

	public int getComplementaryVersion() {
		return complementaryVersion;
	}

	public String[] getCongruentAngle1() {
		return congruentAngle1;
	}

	public String[] getCongruentAngle2() {
		return congruentAngle2;
	}

	public String[] getCongruentSegment1() {
		return congruentSegment1;
	}

	public String[] getCongruentSegment2() {
		return congruentSegment2;
	}

	public String[] getComplementaryAngle1() {
		return complementaryAngle1;
	}

	public String[] getComplementaryAngle2() {
		return complementaryAngle2;
	}

	public String[] getSupplementaryAngle1() {
		return supplementaryAngle1;
	}

	public String[] getSupplementaryAngle2() {
		return supplementaryAngle2;
	}
	
	
}
