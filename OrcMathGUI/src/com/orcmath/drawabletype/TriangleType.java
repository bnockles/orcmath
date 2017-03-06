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
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Ops;

public abstract class TriangleType extends DynamicType {

	
	protected CoordinateImage image;
	protected char[] labels;
	protected Triangle triangle;
	
	
	public TriangleType(int difficulty) {
		this.difficulty=difficulty;
		labels = initLabels();
		this.triangle = createTriangle();
	}
	
	public void setTriangle(Triangle t){
		this.triangle = t;
	}
	
	public abstract Triangle createTriangle();
	
	public abstract char[] initLabels();

	public boolean anglesExceed(Triangle triangle, double minAngle){
		
		return triangle.getAngleA()>minAngle && triangle.getAngleB() > minAngle && triangle.getAngleC() > minAngle;
	}
	
	public static int[] createUniqueSideLengths(int difficulty, int min, int max){
		int side1 = Ops.randomInt(min, max);
		int side2 = Ops.randomInt(min, max);
		while(Math.abs(side1-side2) >10 || side1 == side2){
			side2 = Ops.randomInt(min, max);
		}
		int side3 = Ops.randomInt((int)(Math.abs(side1-side2)+1), (int)(Math.abs(side1+side2)-1));
		while(side3 == side2 || side3 == side1){
			side3 = Ops.randomInt((int)(Math.abs(side1-side2)+1), (int)(Math.abs(side1+side2)-1));
		}
		

		int[] sides = {side1, side2, side3};
		return sides;
	
	}
	
	protected boolean hasDuplicateOrImpossibleLengths(int[] sides) {
		boolean duplicate =  sides[0] == sides[1] || sides[1]==sides[2]|| sides[2]==sides[0];
		boolean impossible = sides[0] + sides[1] <= sides[2] ||sides[0] + sides[2] <= sides[1] || sides[1] + sides[2] <= sides[0];
		return duplicate || impossible;
	}

	protected boolean hasDuplicateOrImpossibleLengths(double[] sides) {
		boolean duplicate =  sides[0] == sides[1] || sides[1]==sides[2]|| sides[2]==sides[0];
		boolean impossible = sides[0] + sides[1] <= sides[2] ||sides[0] + sides[2] <= sides[1] || sides[1] + sides[2] <= sides[0];
		return duplicate || impossible;
	}

	protected void drawAngleLabels(CoordinateImage image){
		image.drawAngleVertexLabel(labels[0]+"", triangle.getVertexB(), triangle.getVertexA(), triangle.getVertexC());
		image.drawAngleVertexLabel(labels[1]+"", triangle.getVertexA(), triangle.getVertexB(), triangle.getVertexC());
		image.drawAngleVertexLabel(labels[2]+"", triangle.getVertexA(), triangle.getVertexC(), triangle.getVertexB());
	}
	
	protected void drawDynamicImage(CoordinateImage image){
		//names of points
		image.drawTriangle(triangle);
		drawAngleLabels(image);
		drawExtras(image);
		dynamicImage = image.getImage();
	}
	
	/**
	 * Called after triangle is drawn, draws any extra features
	 * @param image
	 */
	public abstract void drawExtras(CoordinateImage image);

	protected abstract String initiateString();
	
	protected void getInstance(){
		//step two, determine the type of question and the objective
		image = new CoordinateImage(525, 525, -10, 10, -10, 10);
		drawDynamicImage(image);
		//from the image, take point names that are used in the question text

		
				String questionText =initiateString();
		
				WorkTable answerWork = new WorkTable();
				answerWork.addHorizontalLine();
				initAnswerWork(answerWork);

			
				question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
				answerWork.finish();//cannot omit this or there will be an error!
				answer=answerWork.getLatexTabular();
	}

	public abstract void initAnswerWork(WorkTable answerWork);

	
}
