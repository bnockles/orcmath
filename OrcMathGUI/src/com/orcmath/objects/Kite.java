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

public class Kite extends KiteFamily{
	
	public Kite(char vertexAngle1, char congruentAngle1, char vertexAngle2, char congruentAngle2, char intersectionOfDiagonals){
		name = ""+vertexAngle1+congruentAngle1+vertexAngle2+congruentAngle2;
		diagonalsAreDrawn=true;
		hasComplementaryAngles=true;
		complementaryAngle1=new String[4];
		complementaryAngle2=new String[4];
		complementaryAngle1[0]=""+intersectionOfDiagonals+vertexAngle1+congruentAngle2;
		complementaryAngle1[1]=""+intersectionOfDiagonals+congruentAngle1+vertexAngle1;
		complementaryAngle1[2]=""+intersectionOfDiagonals+vertexAngle2+congruentAngle1;
		complementaryAngle1[3]=""+intersectionOfDiagonals+congruentAngle2+vertexAngle2;
		complementaryAngle2[0]=""+intersectionOfDiagonals+congruentAngle2+vertexAngle1;
		complementaryAngle2[1]=""+intersectionOfDiagonals+vertexAngle1+congruentAngle1;
		complementaryAngle2[2]=""+intersectionOfDiagonals+congruentAngle1+vertexAngle2;
		complementaryAngle2[3]=""+intersectionOfDiagonals+vertexAngle2+congruentAngle2;
		
		hypotenuse=new String[4];
		hypotenuse[0]=""+vertexAngle1+congruentAngle2;
		hypotenuse[1]=""+congruentAngle1+vertexAngle1;
		hypotenuse[2]=""+vertexAngle2+congruentAngle1;
		hypotenuse[3]=""+congruentAngle2+vertexAngle2;
		sideA= new String[4];
		sideA[0]=""+intersectionOfDiagonals+congruentAngle2;
		sideA[1]=""+intersectionOfDiagonals+vertexAngle1;
		sideA[2]=""+intersectionOfDiagonals+congruentAngle1;
		sideA[3]=""+intersectionOfDiagonals+vertexAngle2;
		sideB= new String[4];
		sideB[0]=""+intersectionOfDiagonals+vertexAngle1;
		sideB[1]=""+intersectionOfDiagonals+congruentAngle1;
		sideB[2]=""+intersectionOfDiagonals+vertexAngle2;
		sideB[3]=""+intersectionOfDiagonals+congruentAngle2;
		
		rightTriangleVersion=Ops.randomInt(0, 3);
		complementaryVersion=Ops.randomInt(0, 3);
		
		congruentSegment1=new String[2];
	}
	
	public Kite(char acuteVertex, char point2, char point3, char point4){
		name = ""+acuteVertex+point2+point3+point4;
		diagonalsAreDrawn=false;		
	}
}
