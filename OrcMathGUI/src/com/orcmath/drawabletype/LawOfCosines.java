/*******************************************************************************
 * Copyright (c) 2017 Benjamin Nockles
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

import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.Triangle;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Variable;

public class LawOfCosines extends TriangleType {

	protected static final int indexOfKnownSide1 = 0;
	protected static final int indexOfKnownSide2 = 1;
	protected static final int indexOfTargetSide = 2;

	protected String var;
	protected double[] triangleSides;
	protected int knownAngle;
	protected double knownAngleRadians;

	protected double unknownAngle;
	protected double unknownAngleRadians;
	
	protected boolean findSide;//true means side must be found, false means angle must be found
	
	public LawOfCosines(int difficulty, boolean findSide) {
		super(difficulty);
		this.findSide = findSide;
		if(findSide){

				instructions = "Solve for "+labels[0]+labels[1]+".";

			
		}else{

				instructions = "Solve for m\\angle "+labels[0]+labels[2]+labels[1]+".";

			
		}
		getInstance();
		// TODO Auto-generated constructor stub
	}

	public int getMaxSideLength(){
		return (int) (10+Math.pow(2, difficulty));
	}
	
	
	
	@Override
	public Triangle createTriangle() {
		triangleSides = new double[3];


		do{
			int[] sides = createUniqueSideLengths(difficulty,3,getMaxSideLength());
			triangleSides[0] = sides[0];
			triangleSides[1] = sides[1];
			triangleSides[2] = sides[2];

			triangle = new Triangle(triangleSides[0], triangleSides[1], triangleSides[2]);

		}while(hasDuplicateOrImpossibleLengths(triangleSides) || !anglesExceed(triangle, Math.PI/12));
		
		



		var = "x";

		return triangle;
	}

	@Override
	public char[] initLabels() {
		return Variable.randCapVars(3);
	}
	
	public boolean getWhetherInstructionsAreNeverIncluded(){
		return false;
	}
	

	protected void drawAngleLabels(CoordinateImage image) {
		image.drawAngleVertexLabel(labels[indexOfKnownSide1]+"", triangle.getVertexB(), triangle.getVertexA(), triangle.getVertexC());
		image.drawAngleVertexLabel(labels[indexOfKnownSide2]+"", triangle.getVertexA(), triangle.getVertexB(), triangle.getVertexC());
	}
	
	@Override
	public void drawExtras(CoordinateImage image) {
		image.labelTriangleSide(triangle, ""+(int)triangleSides[indexOfKnownSide1], indexOfKnownSide1);
		image.labelTriangleSide(triangle, ""+(int)triangleSides[indexOfKnownSide2], indexOfKnownSide2);	

		
		if(findSide){
			image.drawAngleVertexLabel(labels[indexOfTargetSide]+" = "+Ops.roundDouble(triangle.getAngleC()*180/Math.PI, 1)+"\u00B0", triangle.getVertexA(), triangle.getVertexC(), triangle.getVertexB());
			image.labelTriangleSide(triangle, var, indexOfTargetSide);	
		}else{
			image.drawAngleVertexLabel(labels[indexOfTargetSide]+" = "+var+"\u00B0", triangle.getVertexA(), triangle.getVertexC(), triangle.getVertexB());
			image.labelTriangleSide(triangle, (int)triangleSides[indexOfTargetSide]+"", indexOfTargetSide);
		}
	}

	@Override
	protected String initiateString() {
		return "";
	}

	@Override
	public void initAnswerWork(WorkTable answerWork) {
		String a=(""+labels[1])+(""+labels[2]);//nameOfKnownSide;
		String b=(""+labels[0])+(""+labels[2]);//nameOfUnknownSide;
		String c=(""+labels[0])+(""+labels[1]);//nameOfHardSide;
//		String A=("m\\angle "+labels[1])+(""+labels[0])+(""+labels[2]);//nameOfKnownAngle;
//		String B=("m\\angle "+labels[0])+(""+labels[1])+(""+labels[2]);//nameOfAngleAcrossFromUnknownSide
		String C=("m\\angle "+labels[0])+(""+labels[2])+(""+labels[1]);//nameOfAngleAcrossFromHardSide
		
		answerWork.addLawOfCosineSteps(a,b,c,C, triangleSides[indexOfKnownSide1],triangleSides[indexOfKnownSide2],triangleSides[indexOfTargetSide],Ops.roundDouble(triangle.getAngleC()*180/Math.PI,1), findSide, var);
	}

}
