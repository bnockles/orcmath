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
import com.orcmath.drawable.TrigTriangle;
import com.orcmath.local.Problem;
import com.orcmath.local.WorkTable;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Fraction;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;

/**
 * @author bnockles
 *
 */
public class TrigBasic extends DynamicType {

	protected String[] variationsOnInstructions= {"Solve for the unknowns.", ""};

	protected CoordinateImage image;
	protected char[] labels;
	protected TrigTriangle triangle;
	protected char[] vars;
	protected int varIndex;//increases as vars are used
	String aLabel;
	String bLabel;
	String cLabel;

	/**
	 * 
	 */
	public TrigBasic(int difficulty) {
		this.difficulty=difficulty;
		labels = initLabels();
		this.triangle = createTriangle();
		getInstance();
		instructions = variationsOnInstructions[(int) (Math.random()*variationsOnInstructions.length)];
	}


	public char[] initLabels() {
		return Variable.randCapVars(5);
	}

	public TrigTriangle createTriangle() {




		double a;
		double b;
		boolean variant = Math.random() <.5;
		//as a result, there will be a small triangle with int side lengths and
		//although the lengths of the given sides will be integer length
		do{
			a = Ops.randomInt(1, 60);
			b = Ops.randomInt(1, 60);

		if(variant){
			triangle = new TrigTriangle(a, b, Math.sqrt(a*a+b*b));
		}else{
			double hyp = (a>b)?a:b;
			double leg = (a>b)?b:a;
			triangle = new TrigTriangle(leg, Math.sqrt(hyp*hyp - leg*leg), hyp);
		}
		}while(triangle.getAngleA()>(75*Math.PI/180.0) || triangle.getAngleB()>(75*Math.PI/180.0) || (variant && a==b));

		
		setUnknowns();

		vars = Variable.randVars(2);
		setInstructionVariation();
		
		return triangle;
	}
	
	protected void setInstructionVariation(){
		variationsOnInstructions[1]="Solve for "+vars[0]+" and "+vars[1]+".";
	}
	
	protected void setUnknowns(){
		int unknownIndex = (int)(Math.random()*3);
		int unknownIndex2 = (unknownIndex+1)%3;
		triangle.setUnknown(unknownIndex);
		triangle.setUnknown(unknownIndex2);
	}



	protected String initiateString(){
		return "";
	}

	protected void drawAngleLabels(){
		image.drawAngleVertexLabel(labels[0]+" = "+getAngleRoundedDegrees(triangle.getAngleA())+"°", triangle.getVertexB(), triangle.getVertexA(), triangle.getVertexC());
		image.drawAngleVertexLabel(labels[1]+" = "+getAngleRoundedDegrees(triangle.getAngleB())+"°", triangle.getVertexA(), triangle.getVertexB(), triangle.getVertexC());
		image.drawAngleVertexLabel(labels[2]+"", triangle.getVertexA(), triangle.getVertexC(), triangle.getVertexB());
	}
	
	protected void drawDynamicImage(CoordinateImage image){
		//names of points
//		image.addGrid(5, 5);
		image.drawTriangle(triangle);
		drawAngleLabels();

		aLabel = assignLabel(0);
		bLabel = assignLabel(1);
		cLabel = assignLabel(2);

		image.drawSegmentLatex(aLabel.toString(), triangle.getVertexB(), triangle.getVertexC(), true, true);
		image.drawSegmentLatex(bLabel.toString(), triangle.getVertexA(), triangle.getVertexC(), true, true);
		image.drawSegmentLatex(cLabel.toString(), triangle.getVertexA(), triangle.getVertexB(), true, true);
		image.drawRightAngleBox(triangle.getVertexA(), triangle.getVertexC(), triangle.getVertexB());
	
//		if(triangle.getAngleA() > Math.PI/8) image.drawAngleMeasure(getAngleRoundedDegrees(triangle.getAngleA()),triangle.getVertexB(),triangle.getVertexA(),triangle.getVertexC());
//		if(triangle.getAngleB() > Math.PI/8) image.drawAngleMeasure(getAngleRoundedDegrees(triangle.getAngleB()),triangle.getVertexA(),triangle.getVertexB(),triangle.getVertexC());
//		System.out.println(triangle);
		dynamicImage = image.getImage();
	}

	public boolean getWhetherInstructionsAreNeverIncluded(){
		return false;
	}
	
	public String getInstructions(){
		return variationsOnInstructions[0];
	}
	
	protected int getAngleRoundedDegrees(double x){
		return (int)(x*180/Math.PI+.5);
	}
	
	protected String assignLabel(int sideIndex){
		if(triangle.isUnknown(sideIndex)){
			Term[] t = {new Term(""+vars[varIndex])};
			varIndex++;
			return new Expression(t).toString();
		}else{
			return triangle.getSideExpressionRounded(sideIndex,1);
		}
	}

	protected void getInstance(){
		//step two, determine the type of question and the objective
		image = new CoordinateImage(475, 475, -10, 10, -10, 10);
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

	public void initAnswerWork(WorkTable answerWork){
		int aa = (int)(triangle.getAngleA()*180/Math.PI+.5);
		double aar = aa*Math.PI/180;
		int ab = (int)(triangle.getAngleB()*180/Math.PI+.5);
		double abr = ab*Math.PI/180;
		if(!triangle.isUnknown(2)){
			System.out.println("answers 0");
			answerWork.newLine("\\cos "+aa, "\\frac{"+bLabel+"}{"+cLabel+"}", "Cosine = \\frac{adj}{hyp}");
			answerWork.newLine(cLabel+"\\cos "+aa, bLabel, "Multiply");
			answerWork.newLine(Ops.roundDouble(triangle.getSideC()*Math.cos(aar),1)+"", bLabel, "Simplify");
			
			answerWork.addHorizontalLine();
			answerWork.newLine("\\sin "+aa, "\\frac{"+aLabel+"}{"+cLabel+"}", "Cosine = \\frac{adj}{hyp}");
			answerWork.newLine(cLabel+"\\sin "+aa, aLabel, "Multiply");
			answerWork.newLine(Ops.roundDouble(triangle.getSideC()*Math.sin(aar),1)+"", aLabel, "Simplify");
		}
		if(!triangle.isUnknown(1)){
			System.out.println("answers 1");
			answerWork.newLine("\\tan "+aa, "\\frac{"+aLabel+"}{"+bLabel+"}", "Tangent = \\frac{opp}{adj}");
			answerWork.newLine(bLabel+"\\tan" +aa, aLabel, "Multiply");
			answerWork.newLine(Ops.roundDouble(triangle.getSideB()*Math.tan(aar),1)+"", aLabel, "Simplify");
			
			answerWork.addHorizontalLine();
			answerWork.newLine("\\cos"+aa, "\\frac{"+bLabel+"}{"+cLabel+"}", "Cosine = \\frac{adj}{hyp}");
			answerWork.newLine(cLabel+"\\cos"+aa, bLabel, "Multiply");
			answerWork.newLine(cLabel, "\\frac{"+bLabel+"}{\\cos"+aa+"}", "Divide");
			answerWork.newLine(cLabel , Ops.roundDouble(triangle.getSideB()/Math.cos(aar),1)+"", "Simplify");
		}
		if(!triangle.isUnknown(0)){
			System.out.println("answers 2");
			answerWork.newLine("\\sin"+aa, "\\frac{"+aLabel+"}{"+cLabel+"}", "Sine = \\frac{opp}{hyp}");
			answerWork.newLine(cLabel+"\\sin"+aa, aLabel, "Mutiply");
			answerWork.newLine(cLabel, "\\frac{"+aLabel+"}{\\sin"+aa+"}", "Divide");
			answerWork.newLine(cLabel , Ops.roundDouble(triangle.getSideA()/Math.sin(aar),1)+"", "Simplify");
			answerWork.addHorizontalLine();
			
			answerWork.newLine("\\tan"+ab, "\\frac{"+bLabel+"}{"+aLabel+"}", "Tangent = \\frac{opp}{adj}");
			answerWork.newLine(aLabel+"\\tan "+ab, bLabel, "Mutiply");
			answerWork.newLine(Ops.roundDouble(triangle.getSideA()*Math.tan(abr),1)+"", bLabel, "Simplify");
		}
	}


}
