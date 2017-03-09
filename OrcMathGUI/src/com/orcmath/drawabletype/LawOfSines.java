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
import com.orcmath.objects.Expression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Variable;

/**
 * @author bnockles
 *
 */
public class LawOfSines extends TriangleType {

	protected static final int indexOfKnownSide = 0;
	protected static final int indexOfUnknownSide = 1;

	protected String var;
	protected double[] triangleSides;
	protected int knownAngle;
	protected double knownAngleRadians;

	protected double unknownAngle;
	protected double unknownAngleRadians;

	protected boolean findSide;//true means side must be found, false means angle must be found

	/**
	 * @param difficulty
	 */
	public LawOfSines(int difficulty) {
		super(difficulty);
		findSide = Math.random() < .5;
		getInstance();
		if(findSide){
			if(difficulty >2){
				instructions = "Solve for "+labels[0]+labels[1]+".";
			}else{
				instructions = "Solve for "+labels[0]+labels[2]+".";

			}
		}else{
			if(difficulty >2){
				instructions = "Solve for "+labels[0]+labels[2]+labels[1]+".";
			}else{
				instructions = "Solve for "+labels[0]+labels[1]+labels[2]+".";

			}
		}
		// TODO Auto-generated constructor stub
	}

	public int getMaxSideLength(){
		return (int) (10+Math.pow(2, difficulty));
	}

	/* (non-Javadoc)
	 * @see com.orcmath.drawabletype.TriangleType#createTriangle()
	 */
	@Override
	public Triangle createTriangle() {
		triangleSides = new double[3];

		triangleSides[indexOfKnownSide] = Ops.roundDouble(3+Math.random()*getMaxSideLength(), 1);
		knownAngle = Ops.randomInt(15, 75);
		knownAngleRadians = knownAngle*Math.PI/180;
		double sum = 0;
		do{
			triangleSides[indexOfUnknownSide] = Ops.roundDouble(3+Math.random()*getMaxSideLength(), 1);
			unknownAngleRadians = (Math.sin(knownAngleRadians)/triangleSides[indexOfKnownSide]*triangleSides[indexOfUnknownSide]);
			sum = knownAngleRadians+unknownAngleRadians;

		}while(Math.abs(triangleSides[indexOfUnknownSide]- triangleSides[indexOfKnownSide])<1 || sum > 165*Math.PI/180 || sum <30*Math.PI/180);

		unknownAngle = Ops.roundDouble(unknownAngleRadians*180/Math.PI,1);
		triangleSides[2] = Ops.roundDouble(triangleSides[indexOfKnownSide]/Math.sin(knownAngleRadians)*Math.sin(Math.PI-knownAngleRadians-unknownAngleRadians),1);

		System.out.println("chosen "+triangleSides[indexOfKnownSide]+", and "+triangleSides[indexOfUnknownSide]+", the unknown angle is "+unknownAngle);


		//as a result, there will be a small triangle with int side lengths and
		//although the lengths of the given sides will be integer length


		triangle = new Triangle(triangleSides[0], triangleSides[1], triangleSides[2]);
		System.out.println("unknown angle = "+unknownAngle+"TRIANGLE"+triangle);

		var = "x";

		return triangle;
	}

	/* (non-Javadoc)
	 * @see com.orcmath.drawabletype.TriangleType#initLabels()
	 */
	@Override
	public char[] initLabels() {
		return Variable.randCapVars(3);
	}

	public boolean getWhetherInstructionsAreNeverIncluded(){
		return false;
	}
	
	protected void drawAngleLabels(CoordinateImage image) {

	}
	/* (non-Javadoc)
	 * @see com.orcmath.drawabletype.TriangleType#drawExtras(com.orcmath.drawable.CoordinateImage)
	 */
	@Override
	public void drawExtras(CoordinateImage image) {
		image.labelTriangleSide(triangle, ""+triangleSides[indexOfKnownSide], indexOfKnownSide);	

		image.drawAngleVertexLabel(labels[indexOfKnownSide]+" = "+(knownAngle)+"\u00B0", triangle.getVertexB(), triangle.getVertexA(), triangle.getVertexC());
		if(findSide){
			if(difficulty>2){
				image.labelTriangleSide(triangle, var, 2);
				image.drawAngleVertexLabel(labels[2]+"", triangle.getVertexA(), triangle.getVertexC(), triangle.getVertexB());
			}else{
				image.drawAngleVertexLabel(labels[2]+"", triangle.getVertexA(), triangle.getVertexC(), triangle.getVertexB());
				image.labelTriangleSide(triangle, var, indexOfUnknownSide);
			}
			image.drawAngleVertexLabel(labels[indexOfUnknownSide]+" = "+unknownAngle+"\u00B0", triangle.getVertexA(), triangle.getVertexB(), triangle.getVertexC());
			//			image.drawSegmentLatex(var, triangle.getVertexA(), triangle.getVertexC(), false, true);
		}else{
			image.labelTriangleSide(triangle, ""+triangleSides[indexOfUnknownSide], indexOfUnknownSide);
			//			image.drawSegmentLatex(""+triangleSides[indexOfUnknownSide], triangle.getVertexA(), triangle.getVertexC(), false, true);
			if(difficulty >2){
				image.drawAngleVertexLabel(labels[1]+"", triangle.getVertexA(), triangle.getVertexB(), triangle.getVertexC());
				image.drawAngleVertexLabel(labels[2]+" = "+var+"\u00B0", triangle.getVertexA(), triangle.getVertexC(), triangle.getVertexB());
			}else{
				image.drawAngleVertexLabel(labels[2]+"", triangle.getVertexA(), triangle.getVertexC(), triangle.getVertexB());
				image.drawAngleVertexLabel(labels[indexOfUnknownSide]+" = "+var+"\u00B0", triangle.getVertexA(), triangle.getVertexB(), triangle.getVertexC());
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.orcmath.drawabletype.TriangleType#initiateString()
	 */
	@Override
	protected String initiateString() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.orcmath.drawabletype.TriangleType#initAnswerWork(com.orcmath.local.WorkTable)
	 */
	@Override
	public void initAnswerWork(WorkTable answerWork) {
		String a=(""+labels[1])+(""+labels[2]);//nameOfKnownSide;
		String b=(""+labels[0])+(""+labels[2]);//nameOfUnknownSide;
		String c=(""+labels[0])+(""+labels[1]);//nameOfHardSide;
		String A=(""+labels[1])+(""+labels[0])+(""+labels[2]);//nameOfKnownAngle;
		String B=(""+labels[0])+(""+labels[1])+(""+labels[2]);//nameOfAngleAcrossFromUnknownSide
		String C=(""+labels[0])+(""+labels[2])+(""+labels[1]);//nameOfAngleAcrossFromHardSide

		if(difficulty > 2){
			if(findSide){
				answerWork.newLine("180", "m\\angle "+A+"+ m\\angle "+B+"+ m\\angle "+C, "Triangle Angle Sum");
				answerWork.newLine("180", knownAngle+" +  "+unknownAngle+" + m\\angle "+C, "Substitute.");
				answerWork.newLine("180 - "+knownAngle+" - "+unknownAngle, "m\\angle "+C, "Subtract.");
				double angle = 180 - knownAngle - unknownAngle;
				answerWork.newLine((angle)+"", "m\\angle "+C, "Simplify.");
				answerWork.addLawOfSinesSteps(a,A,c,C, triangleSides[indexOfKnownSide],knownAngle,triangleSides[2],angle, findSide, var);
			}else{
				double result = answerWork.addLawOfSinesSteps(a,A,b,B, triangleSides[indexOfKnownSide],knownAngle,triangleSides[indexOfUnknownSide],unknownAngle, findSide, "m\\angle "+B);
				System.out.println("Label CCCCC!");
				answerWork.newLine("180", "m\\angle "+A+" + m\\angle "+B+" + m\\angle "+C, "Triangle Angle Sum");
				answerWork.newLine("180", knownAngle+" +  "+result+" + " +var, "Substitute.");
				answerWork.newLine("180 - "+knownAngle+" - "+result, var, "Subtract.");
				answerWork.newLine((180 - knownAngle - result)+"", var, "Simplify.");
			}
		}else{
			answerWork.addLawOfSinesSteps(a,A,b,B, triangleSides[indexOfKnownSide],knownAngle,triangleSides[indexOfUnknownSide],unknownAngle, findSide, var);

		}

	}

}
