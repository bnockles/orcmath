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

import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;

/**
 * @author bnockles
 *
 */
public class TrigInverseBasic extends TrigBasic {

	protected int vertexToFind;
	
	/**
	 * @param difficulty
	 */
	public TrigInverseBasic(int difficulty) {
		super(difficulty);
		//note directions come from super class
	}
	
	
	//only one side needs to be unknown
	protected void setUnknowns(){
		int unknownIndex = (int)(Math.random()*3);
		triangle.setUnknown(unknownIndex);
		vertexToFind = (int) (Math.random()*2);
		//control for testing:
		unknownIndex = 1;
		vertexToFind = 1;
	}
	
	protected void setInstructionVariation(){
		variationsOnInstructions[1]="Solve for "+vars[0]+".";
	}
	
	protected String assignLabel(int sideIndex){
		if(triangle.isUnknown(sideIndex)){
			return "";
		}else{
			if(difficulty<3){
				return triangle.getSideExpressionRounded(sideIndex,1)+"";

			}else{
				
				return triangle.getSideExpression(sideIndex).toString();
			}
		}
	}
	
	
	protected void drawAngleLabels(){
		if(vertexToFind == 0){
			image.drawAngleVertexLabel(labels[0]+" = "+vars[0]+"°", triangle.getVertexB(), triangle.getVertexA(), triangle.getVertexC());
			image.drawAngleVertexLabel(labels[1]+"", triangle.getVertexA(), triangle.getVertexB(), triangle.getVertexC());
			
		}else{
			image.drawAngleVertexLabel(labels[0]+"", triangle.getVertexB(), triangle.getVertexA(), triangle.getVertexC());
			image.drawAngleVertexLabel(labels[1]+" = "+vars[0]+"°", triangle.getVertexA(), triangle.getVertexB(), triangle.getVertexC());
		}
		image.drawAngleVertexLabel(labels[2]+"", triangle.getVertexA(), triangle.getVertexC(), triangle.getVertexB());
	}
	
	public void makeStepsForUnknown(WorkTable answerWork, int oppIndex, String opp, String adj,  String oppLength, String adjLength, double answer){
		String hyp = (labels[1]+"")+(labels[0]+"");
		String hypLength =(difficulty<3)?triangle.getSideExpressionRounded(2, 1): triangle.getSideExpression(2).toString();
		if(triangle.isUnknown(2)){
			answerWork.addTanInverseSteps(vars[0]+"",opp,adj,oppLength,adjLength,answer);
		}else{
			if(triangle.isUnknown(oppIndex)){
				answerWork.addCosInverseSteps(vars[0]+"", adj, hyp, adjLength, hypLength, answer);
			}else{
				//use sine or cosine
				answerWork.addSinInverseSteps(vars[0]+"", opp, hyp, oppLength, hypLength, answer);
				
			}
		}
	}



	public void initAnswerWork(WorkTable answerWork){
		double aa = Ops.roundDouble(triangle.getAngleA()*180/Math.PI,1);
//		double aar = aa*Math.PI/180;//angle A rounded
		double ab = Ops.roundDouble(triangle.getAngleB()*180/Math.PI,1);
//		double abr = ab*Math.PI/180;//angle B rounded
		String side0 = (difficulty < 3)?triangle.getSideExpressionRounded(0,1):triangle.getSideExpression(0).toString();
		String side1 = (difficulty < 3)?triangle.getSideExpressionRounded(1,1):triangle.getSideExpression(1).toString();
		
		if(vertexToFind == 0){
			makeStepsForUnknown(answerWork, 0,(labels[1]+"")+(labels[2]+""), (labels[2]+"")+(labels[0]+""), side0, side1, aa);
		}else{
			makeStepsForUnknown(answerWork, 1,(labels[2]+"")+(labels[0]+""), (labels[2]+"")+(labels[1]+""), side1, side0, ab);
		}
	}


	


}
