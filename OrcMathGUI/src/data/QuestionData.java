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
package data;

public class QuestionData{

	String questionAddresses;
	int imageMargin;
	double scaleFactors;
	//some questions have their own instructions every time. (Word problems) Hence, these questions should not get additional, generic instructions
	boolean neverIncludeInstructions;
	String instructions;
	float verticalSpace;
	
	public QuestionData(String address) {
		this.questionAddresses = address;
	}
	public QuestionData() {
		
	}

	public String getAddress() {
		return questionAddresses;
	}

	public String getQuestionAddresses() {
		return questionAddresses;
	}

	public void setQuestionAddresses(String questionAddresses) {
		this.questionAddresses = questionAddresses;
	}

	public int getImageMargin() {
		return imageMargin;
	}

	public void setImageMargin(int imageMargin) {
		this.imageMargin = imageMargin;
	}

	public double getScaleFactors() {
		return scaleFactors;
	}

	public void setScaleFactors(double scaleFactors) {
		this.scaleFactors = scaleFactors;
	}

	public boolean neverIncludeInstructions() {
		return neverIncludeInstructions;
	}

	public void setNeverIncludeInstructions(boolean b) {
		this.neverIncludeInstructions = b;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public float getVerticalSpace() {
		return verticalSpace;
	}

	public void setVerticalSpace(float verticalSpace) {
		this.verticalSpace = verticalSpace;
	}
	
	

}
