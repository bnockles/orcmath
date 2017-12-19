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

import java.awt.Color;
import java.util.ArrayList;

import com.orcmath.drawable.CoordinateImage;
import com.orcmath.drawable.CoordinatePoint;
import com.orcmath.drawable.DrawableFormat;
import com.orcmath.drawable.Transformation;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Format;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Variable;

public class PerformTransform extends DynamicType{

	char[] labels;
	String figureName;
	String imageName;
	ArrayList<CoordinatePoint> figure = new ArrayList<CoordinatePoint>();
	ArrayList<Transformation> transformation=new ArrayList<Transformation>();
	CoordinateImage image;
	
	protected void addTransformation(int transformationType, double arg, double optionalArg){
		transformation.add(new Transformation(transformationType, arg,optionalArg));
	}
	
	protected void addTransformation(Transformation t){
		transformation.add(t);
	}
	
	private void initiateKeyTheorems(){
		if (transformation.size()==1){
			Transformation t = transformation.get(0);
			keyTheorem=Transformation.getLiteralDescription(t, t.getArg(), t.getOptionalArg());
			if(t.getArg()!=t.getOptionalArg()){
				falseTheorem1=Transformation.getLiteralDescription(t, t.getOptionalArg(), t.getArg());
			}else{
				falseTheorem1=Transformation.getLiteralDescription(new Transformation((t.getType()+1)%4, t.getArg(), t.getOptionalArg()), t.getArg(),t.getOptionalArg());
			}
			falseTheorem2=Transformation.getLiteralDescription(new Transformation((t.getType()+2)%4, t.getArg(), t.getOptionalArg()), t.getArg(),t.getOptionalArg());
			falseTheorem3=Transformation.getLiteralDescription(new Transformation((t.getType()+3)%4, t.getArg(), t.getOptionalArg()), t.getArg(),t.getOptionalArg());

		}else{//for compositions of transformations
			
		}
	}
	
	protected void getInstance(){
		initFigure();
		initiateKeyTheorems();
		int max = determineMaximum();
		labels =Variable.randCapVars(figure.size());
//		only use these lines to override
//		labels[0]='A';
//		labels[1]='B';
//		labels[2]='C';
//		labels[3]='D';
		image = new CoordinateImage(550, 550, -max, max, -max, max);
		drawDynamicImage(image);
		
		String questionText ="In the diagram below, #"+figureName+"# is drawn with coordinates, #";
		for(int index = 0; index< figure.size(); index++){
			questionText+=labels[index]+"("+Format.doubleToString(figure.get(index).getxCoordinate())+","+
					Format.doubleToString(figure.get(index).getyCoordinate())+")";
			if(index+2<figure.size()){
				questionText+=",# #";
			}else if(index+2==figure.size()){
				questionText+=",# and #";
			}else{
				questionText+=".# ";
			}
		}
		questionText +="Draw and label the coordinates of #"+imageName+",# the image of #"+figureName+"# though ";
		questionText +=describeTransformation();
		
		WorkTable answerWork = new WorkTable();
		answerWork.addHorizontalLine();
		answerWork.addTransformationSteps(image, figure, transformation, answerImages);
		
		
		//always include the following three lines
		question = Problem.getLatexLines(questionText, "#", QUESTION_WIDTH, "text");
		answerWork.finish();//cannot omit this or there will be an error!
		answer=answerWork.getLatexTabular();
	}

	private int determineMaximum() {
		int max = 10;
		ArrayList<CoordinatePoint> testFigure = new ArrayList<CoordinatePoint>();
		for(int index1=0; index1<figure.size(); index1++){
			testFigure.add(new CoordinatePoint(figure.get(index1).getxCoordinate(), figure.get(index1).getyCoordinate()));
		}
		for(int index=0; index<transformation.size(); index++){
			Transformation t=transformation.get(index);
			if(t.getType()==Transformation.DILATE){	
				for(int index1=0; index1<testFigure.size(); index1++){
					testFigure.get(index1).dilate(t.getArg(), t.getOptionalArg());
				}
			}else if(t.getType()==Transformation.ROTATE){
				for(int index1=0; index1<testFigure.size(); index1++){
					testFigure.get(index1).rotate(t.getArg(), true);
				}
			}else if(t.getType()==Transformation.TRANSLATE){
				for(int index1=0; index1<testFigure.size(); index1++){
					testFigure.get(index1).translate(t.getArg(),t.getOptionalArg());
				}				
			}else if(t.getType()==Transformation.REFLECT){
				for(int index1=0; index1<testFigure.size(); index1++){
					testFigure.get(index1).reflect(t.getArg(),t.getOptionalArg());
				}
			}
			for(int vertex = 0; vertex< testFigure.size(); vertex++){
				double testMax;
				double testMaxY;
				testMax=Math.abs(testFigure.get(vertex).getxCoordinate());
				testMaxY=Math.abs(testFigure.get(vertex).getyCoordinate());
				if(testMaxY>testMax)testMax=testMaxY;
				if(testMax>max){
					testMax=5*(int)(testMax/5+.9);
					max=(int)testMax;
				}
			}
		}
		return max;
	}

	protected void initFigure() {
//		if(false){//override the figure method to make a custom figure
//			figure.add(new CoordinatePoint(1, 3));
//			figure.add(new CoordinatePoint(5, 5));
//			figure.add(new CoordinatePoint(6, 8));
//			figure.add(new CoordinatePoint(1, 8));
//			
//		}
//		else{
			if(difficulty==1||difficulty==2){
				int x=Ops.randomNotZero(-6, 6);
				int y=Ops.randomNotZero(-6, 6);
				figure.add(new CoordinatePoint(x, y));
			}else{
				int xBearing = Ops.randomInt(-5, 5);
				int yBearing = Ops.randomInt(-5, 5);
				if(difficulty==2){
					xBearing = 5;
					yBearing = 3;
				}
				Transformation t = transformation.get(transformation.size()-1);
				if((difficulty==2 || difficulty==3) && 
						t.getType()==Transformation.REFLECT 
						&& t.getArg()==1){
					xBearing = Ops.randomInt(-5, 0);
					yBearing = Ops.randomInt(0, 5);
					if(Math.random()>.5){
						xBearing=-xBearing;
						yBearing=-yBearing;
					}
				}
				if (Math.random()>.5){//quadrilateral	
					figure.add(new CoordinatePoint(xBearing+Ops.randomInt(1, 5), yBearing+Ops.randomInt(1, 5)));
					figure.add(new CoordinatePoint(xBearing+Ops.randomInt(-5, -1), yBearing+Ops.randomInt(1, 5)));
					figure.add(new CoordinatePoint(xBearing+Ops.randomInt(-5, -1), yBearing+Ops.randomInt(-5, -1)));
					figure.add(new CoordinatePoint(xBearing+Ops.randomInt(1, 5), yBearing+Ops.randomInt(-5, -1)));
				}else{//triangle
					figure.add(new CoordinatePoint(xBearing+Ops.randomInt(1, 5), yBearing+Ops.randomInt(1, 5)));
					figure.add(new CoordinatePoint(xBearing+Ops.randomInt(-5, -1), yBearing+Ops.randomInt(1, 5)));
					figure.add(new CoordinatePoint(xBearing+Ops.randomInt(1, 5), yBearing+Ops.randomInt(-5, -2)));
				}
			}
//		}
	}

	private void drawDynamicImage(CoordinateImage image) {
		if(image.getxMax()>10 && image.getxMax()<20){
			image.addGrid(1, 1);
			image.addAxes(5, 5, true);
		}else if (image.getxMax()>15){
			image.addGrid(2, 2);
			image.addAxes(10, 10, true);
		}else{
			image.addGrid(1, 1);
			image.addAxes(1, 1, false);
		}
		figureName="";
		imageName="";
		for(int index = 0; index< figure.size(); index++){
			image.drawPoint(figure.get(index));
			figure.get(index).setLabel(""+labels[index]);
			figureName+=labels[index];
			imageName+=labels[index];
			for(Transformation t:transformation){
				imageName+="'";
			}
			image.setThickness(3);
			if(index>0){
				image.drawSegment(figure.get(index-1), figure.get(index));
			}
			if(index==figure.size()-1 && figure.size()>1){
				image.drawSegment(figure.get(index), figure.get(0));
			}
		}
		image.setThickness(1);
		image.labelFigure(figure);
		
		dynamicImage=image.getImage();
	}

	private String describeTransformation() {
		String transformationText =DrawableFormat.transformationArrayToString(transformation);
		return "the transformation, #"+transformationText+".";
	}
	
	
}
