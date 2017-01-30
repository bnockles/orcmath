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
package com.orcmath.drawabletype;


import com.orcmath.drawable.Cone;
import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.Cylinder;
import com.orcmath.drawable.Pyramid;
import com.orcmath.drawable.ReferenceFormula;
import com.orcmath.drawable.Sphere;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Format;


public class UseFormula extends DynamicType{

	CoordinateImage image;//optional

	
	int variable;
	Expression baseExpression;
	Expression heightExpression;
	Expression radiusExpression;
	Expression slantHeightExpression;

	ReferenceFormula formula;
	String unit;
	String units;
	boolean includeDiagram;
	int alternateObjective=0;//a boolean to identify when something other than what is expected is found (i.e. diameter instead of radius)
	boolean useRadius=true;//this boolean is used in spheres, cylinders and cones
	boolean useHeight=true;//this boolean is only for cones
	String object;
	String objectShort;
	
	public static int OBJECTIVE_DIAMETER=10;
	
	protected void setFormula(ReferenceFormula formula){
		this.formula=formula;
	}
		
	protected void setDiagram() {
		if((difficulty==3 && Math.random()<.5) || difficulty<3) includeDiagram=true;
	}
	
	private void initiateKeyTheorems(){
		keyTheorem=formula.getRepresentation();
		falseTheorem1=formula.getCommonError(1);
		falseTheorem2=formula.getCommonError(2);
		falseTheorem3=formula.getCommonError(3);
	}
	
	protected void getInstance(){
		initiateKeyTheorems();	
		String questionText =initQuestion();
		if(includeDiagram){
			//TODO code for when diagrams are included in the question
			image = new CoordinateImage(500, 500, -10, 10, -10, 10);
			drawDynamicImage(image);
		}
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
//		if(alternateObjective!=0){
//			if(getAlternateObjective()==OBJECTIVE_DIAMETER){
//				answerWork.newTextLine("We are asked find the diameter. Recall that the measure of the diameter of a circle is twice the measure of the radius.");
//			}
//		}
		answerWork.addGeometryFormulaSteps(formula, useRadius,useHeight, answerImages);
		if(alternateObjective!=0){
			if(getAlternateObjective()==OBJECTIVE_DIAMETER){
				answerWork.newLine("d", "2\\left("+formula.getValue(formula.getUnknown())+"\\right)", "Since the measure of the diameter is twice the radius, we must multiply our answer by 2.");
				int answer = (int) (2*formula.getValue(formula.getUnknown()));
				answerWork.newLine("d", ""+answer, "Final answer.");
			}
		}
		
		
		//always include the following three lines
		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}

	protected int getAlternateObjective() {
		return alternateObjective;
	}

	protected void chooseUnits() {
		double choice=Math.random();
		if(choice<.2){
			unit="centimeter";
			units="centimeters";
		}else if(choice<.4){
			unit="inch";
			units="inches";
		}else if(choice<.6){
			unit="foot";
			units="feet";
		}else if(choice<.8){
			unit="yard";
			units="yards";
		}else{
			unit="meter";
			units="meters";
		}	
	}

	protected String initQuestion() {
		return "";
	}
	
	private void drawDynamicImage(CoordinateImage image) {
		if(formula.getType()==ReferenceFormula.CYLINDER_LATERAL_AREA || formula.getType()==ReferenceFormula.CYLINDER_VOLUME){
			Cylinder drawnCyliner = new Cylinder(formula.getValue(ReferenceFormula.RADIUS), formula.getValue(ReferenceFormula.HEIGHT));
			drawnCyliner.draw(image, CoordinateImage.ORIGIN);
			drawnCyliner.labelHeight(image, formula.getValueString(ReferenceFormula.HEIGHT));
			if(alternateObjective!=0){
				drawnCyliner.labelDiameter(image, "d");
			}else{
				if(useRadius) drawnCyliner.labelRadius(image, formula.getValueString(ReferenceFormula.RADIUS));
				else drawnCyliner.labelDiameter(image, ""+2*formula.getValue(ReferenceFormula.RADIUS));
			}
		}
		else if(formula.getType()==ReferenceFormula.PYRAMID_VOLUME){
			Pyramid drawnPyramid = new Pyramid(formula.getValue(ReferenceFormula.LENGTH),formula.getValue(ReferenceFormula.WIDTH),formula.getValue(ReferenceFormula.HEIGHT));
			drawnPyramid.draw(image,CoordinateImage.ORIGIN);
			drawnPyramid.labelHeight(image, formula.getValueString(ReferenceFormula.HEIGHT));
			drawnPyramid.labelLength(image, formula.getValueString(ReferenceFormula.LENGTH));
			drawnPyramid.labelWidth(image, formula.getValueString(ReferenceFormula.WIDTH));
		}
		else if(formula.getType()==ReferenceFormula.RIGHT_CIRCULAR_CONE_VOLUME || formula.getType()==ReferenceFormula.RIGHT_CIRCULAR_CONE_LATERAL_AREA){
			Cone drawnCone = new Cone(formula.getValue(ReferenceFormula.RADIUS), formula.getValue(ReferenceFormula.HEIGHT));
			drawnCone.draw(image,CoordinateImage.ORIGIN, false);
			if(useHeight && formula.getUnknown()!=ReferenceFormula.SLANT_HEIGHT)drawnCone.labelHeight(image, formula.getValueString(ReferenceFormula.HEIGHT));
			else if(useHeight && formula.getUnknown()==ReferenceFormula.SLANT_HEIGHT)drawnCone.labelHeight(image, "h");
			else drawnCone.labelSlant(image, formula.getValueString(ReferenceFormula.SLANT_HEIGHT));
			if(alternateObjective!=0){
				drawnCone.labelDiameter(image, "d");
			}else{
				if(useRadius)
					drawnCone.labelRadius(image, formula.getValueString(ReferenceFormula.RADIUS));
				else drawnCone.labelDiameter(image, ""+2*formula.getValue(ReferenceFormula.RADIUS));
			}
		}
		else if(formula.getType()==ReferenceFormula.SPHERE_VOLUME || formula.getType()==ReferenceFormula.SPHERE_SURFACE_AREA){
			Sphere drawnSphere = new Sphere(formula.getValue(ReferenceFormula.RADIUS));
			drawnSphere.draw(image,CoordinateImage.ORIGIN);
			if(getAlternateObjective()!=0){
				drawnSphere.labelDiameter(image, "d");
			}else{
				if(useRadius)
					drawnSphere.labelRadius(image, formula.getValueString(ReferenceFormula.RADIUS));
				else drawnSphere.labelDiameter(image, ""+2*formula.getValue(ReferenceFormula.RADIUS));
			}
		}
		dynamicImage=image.getImage();
	}
	
	
}
