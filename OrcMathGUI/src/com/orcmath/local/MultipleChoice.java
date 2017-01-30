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
package com.orcmath.local;

import java.util.Collections;
import java.util.Arrays;;


public class MultipleChoice {
	int numberOfChoices;
	String[] mixedChoices;
	String choiceA;
	String choiceB;
	String choiceC;
	String choiceD;
	String choiceE;
	String answer;
	String latexTabular;
	String correctSelction;
	
	public MultipleChoice(String a, String b, String c, String d, String answer){
		numberOfChoices = 4;
		mixedChoices = new String[numberOfChoices];
		mixedChoices[0] = a;
		mixedChoices[1] = b;
		mixedChoices[2] = c;
		mixedChoices[3] = d;
		Collections.shuffle(Arrays.asList(mixedChoices));
		choiceA = mixedChoices[0];
		choiceB = mixedChoices[1];
		choiceC = mixedChoices[2];
		choiceD = mixedChoices[3];
		this.answer = answer;
		correctSelction="";
		if(choiceA.equals(answer)){
			correctSelction="[1]";
		}else if(choiceB.equals(answer)){
			correctSelction="[2]";
		}else if(choiceC.equals(answer)){
			correctSelction="[3]";
		}else if(choiceD.equals(answer)){
			correctSelction="[4]";
		}else if(choiceE.equals(answer)){
			correctSelction="[5]";
		}
	}
	
	public String getCorrectSelction() {
		return correctSelction;
	}

	public String getMultipleChoiceTable(String question, boolean twoColumns, int width){
		if (width<200)width=200;
		if(!twoColumns){
			int textWidth=width-15;
			latexTabular="\\begin{tabular}{rl}";
			question=Problem.getLatexLines(question, "#%&", width, "text");
			latexTabular+="\\multicolumn{2}{l}{"+question+"}\\\\";
			for(int index=0; index<4; index++){
				String text=Problem.getLatexLines(mixedChoices[index], "#%&", textWidth, "text");
				latexTabular+="["+(index+1)+"] & "+text+"\\\\";
			}
		}else{
			int textWidth=(width-30)/2;
			latexTabular="\\begin{tabular}{rlrl}";
			question=Problem.getLatexLines(question, "#%&", width, "text");
			latexTabular+="\\multicolumn{4}{l}{"+question+"}\\\\";
			String[] formattedTexts=new String[4];
			for(int index=0; index<4; index++){
				formattedTexts[index]=Problem.getLatexLines(mixedChoices[index], "#%&", textWidth, "text");
			}
			latexTabular+="[1] & "+formattedTexts[0]+" & [3] & "+formattedTexts[2]+"\\\\";
			latexTabular+="[2] & "+formattedTexts[1]+" & [4] & "+formattedTexts[3]+"\\\\";		
		}
		latexTabular+="\\\\\\end{tabular}";
		return latexTabular;
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getNumberOfChoices() {
		return numberOfChoices;
	}

	public String getChoiceA() {
		return choiceA;
	}

	public void setChoiceA(String choiceA) {
		this.choiceA = choiceA;
	}

	public String getChoiceB() {
		return choiceB;
	}

	public void setChoiceB(String choiceB) {
		this.choiceB = choiceB;
	}

	public String getChoiceC() {
		return choiceC;
	}

	public void setChoiceC(String choiceC) {
		this.choiceC = choiceC;
	}

	public String getChoiceD() {
		return choiceD;
	}

	public void setChoiceD(String choiceD) {
		this.choiceD = choiceD;
	}

	public String getChoiceE() {
		return choiceE;
	}

	public void setChoiceE(String choiceE) {
		this.choiceE = choiceE;
	}
}
