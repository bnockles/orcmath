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


import com.orcmath.local.MultipleChoice;
import com.orcmath.objects.Ops;
import com.orcmath.type.Type;

public class JustifyConstruction extends Type{

	private String graphic1Address = "PDFs/Graphics/TypeSpecific/Construction/graphic1.png";
	private String graphic2Address = "PDFs/Graphics/TypeSpecific/Construction/graphic2.png";
	private String graphic3Address = "PDFs/Graphics/TypeSpecific/Construction/graphic3.png";
	private String graphic4Address = "PDFs/Graphics/TypeSpecific/Construction/graphic4.png";
	private String graphic5Address = "PDFs/Graphics/TypeSpecific/Construction/graphic5.png";
	private String graphic6Address = "PDFs/Graphics/TypeSpecific/Construction/graphic6.png";
	private String graphic7Address = "PDFs/Graphics/TypeSpecific/Construction/graphic7.png";

	static int lastSelection;
	static int selectionBeforeLast1;
	static int selectionBeforeLast2;
	static int selectionBeforeLast3;
	static int selectionBeforeLast4;
	static int selectionBeforeLast5;
	static int selectionBeforeLast6;
	static int selectionBeforeLast7;
	static int selectionBeforeLast8;
	static int selectionBeforeLast9;
	static int selectionBeforeLast10;
	static int selectionBeforeLast11;
	
	public JustifyConstruction(){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=20;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.30;
	}
	
	public JustifyConstruction(int difficulty){
		instructions = "";
		numberOfColumns=1;
		verticalSpacing=20;	
		whetherInstructionsAreNeverIncluded=true;
		scaleFactor=.30;
		getInstance(difficulty);
		//question and answer is determined in the getInstance constructor
	}
	
	private void getInstance(int difficulty){

		
		//TODO add more graphics and more problem types
		int graphicNumber = Ops.randomInt(1, 12);
		while (graphicNumber == lastSelection || 
				graphicNumber == selectionBeforeLast1 ||
				 graphicNumber == selectionBeforeLast2 ||
				 graphicNumber == selectionBeforeLast3 ||
				 graphicNumber == selectionBeforeLast4 ||
				 graphicNumber == selectionBeforeLast5 ||
				 graphicNumber == selectionBeforeLast5 ||
				 graphicNumber == selectionBeforeLast6 ||
				 graphicNumber == selectionBeforeLast7 ||
				 graphicNumber == selectionBeforeLast8 ||
				 graphicNumber == selectionBeforeLast9 ||
				 graphicNumber == selectionBeforeLast10
				 ) graphicNumber = Ops.randomInt(1, 12);
		
		if (graphicNumber == 1) getGraphic1Instance(1);
		if (graphicNumber == 2) getGraphic1Instance(2);
		if (graphicNumber == 3) getGraphic2Instance(1);
		if (graphicNumber == 4) getGraphic2Instance(2);
		if (graphicNumber == 5) getGraphic3Instance(1);
		if (graphicNumber == 6) getGraphic3Instance(2);
		if (graphicNumber == 7) getGraphic4Instance(1);
		if (graphicNumber == 8) getGraphic5Instance(1);
		if (graphicNumber == 9) getTextInstance(1);
		if (graphicNumber == 10) getGraphic6Instance(1);
		if (graphicNumber == 11) getGraphic6Instance(2);
		if (graphicNumber == 12) getGraphic7Instance(1);

		System.out.println("This problem was selcted : " +graphicNumber);
		
		selectionBeforeLast10 = selectionBeforeLast9;
		selectionBeforeLast9 = selectionBeforeLast8;
		selectionBeforeLast8 = selectionBeforeLast7;
		selectionBeforeLast7 = selectionBeforeLast6;
		selectionBeforeLast6 = selectionBeforeLast5;
		selectionBeforeLast5 = selectionBeforeLast4;
		selectionBeforeLast4 = selectionBeforeLast3;
		selectionBeforeLast3 = selectionBeforeLast2;
		selectionBeforeLast2 = selectionBeforeLast1;
		selectionBeforeLast1 = lastSelection;
		lastSelection = graphicNumber;
	}
		
	private void getTextInstance(int questionType) {
		String theQuestion = "\\begin{array}{l}";
		MultipleChoice mc;
		
		if (questionType==1){

				theQuestion += "\\text{One step in a construction uses the endpoints of }\\overline{AB}\\text{ to create arcs with the same radii.}";
				theQuestion += "\\\\\\text{The arcs intersect above and below the segment. What is the relationship of}\\overline{AB}\\text{ and the line}";
				theQuestion += "\\\\\\text{connecting the points of intersection of these arcs?}";

				mc = new MultipleChoice(
						"\\text{collinear}",
						"\\text{congruent}",
						"\\text{parallel}",
						"\\text{perpendicular}",
						"\\text{perpendicular}");
				
				theQuestion += "\\\\\\text{    (1) }" + mc.getChoiceA();
				theQuestion +="\\\\\\text{    (2) }" + mc.getChoiceB();
				theQuestion +="\\\\\\text{    (3) }" + mc.getChoiceC();
				theQuestion +="\\\\\\text{    (4) }" + mc.getChoiceD();
				theQuestion += "\\end{array}";
				
				question = theQuestion;
				answer = mc.getAnswer();
			}
			else{
				question="";
				answer = "";
			}
		
	}

	private void getGraphic1Instance(int questionType){
		String theQuestion = "\\begin{array}{l}";
		MultipleChoice mc;
		
		if (questionType==1){

				theQuestion += "\\text{Look at the construction.}";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=15cm,interpolation=bicubic]{"+graphic1Address+"}";
				theQuestion += "\\\\\n\\\\\\text{The point of a compass is placed at the vertex of }\\angle P\\text{. An arc is drawn intersecting}"; 
				theQuestion += "\\\\\n\\\\\\text{the sides of the angle, making points } A \\text{ and } B \\text{ Which statement must be true?}";
				
				mc = new MultipleChoice(
						"\\overline{PA}<\\overline{PB}",
						"\\overline{PA}\\cong\\overline{PB}",
						"\\overline{AB}<\\overline{PB}",
						"\\overline{AP}\\cong\\overline{AB}",
						"\\overline{PA}<\\overline{PB})");
				
				theQuestion += "\\\\\\text{    (1) }" + mc.getChoiceA();
				theQuestion +="\\\\\\text{    (2) }" + mc.getChoiceB();
				theQuestion +="\\\\\\text{    (3) }" + mc.getChoiceC();
				theQuestion +="\\\\\\text{    (4) }" + mc.getChoiceD();
				theQuestion += "\\end{array}";
				
				question = theQuestion;
				answer = mc.getAnswer();
			}
			else if (questionType==2){
				
				theQuestion += "\\text{Look at the construction.}";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=15cm,interpolation=bicubic]{"+graphic1Address+"}";
				theQuestion += "\\\\\n\\\\\\text{Which is the justification for the statement, }\\overline{PA}\\cong\\overline{PB}\\text{?}"; 
				
				mc = new MultipleChoice(
						"\\overline{PA}\\text{ and }\\overline{PB}\\text{ are radii of the same circle.}",
						"\\overrightarrow{PW}\\text{ bisects}\\angle APB\\text{.}",
						"\\overline{PA}\\text{ and }\\overline{PB}\\text{ are sides of }\\angle APB\\text{.}",
						"\\overline{AB}\\text{ is bisected by }\\overline{PW}",
						"\\overline{PA}\\text{ and }\\overline{PB}\\text{ are radii of the same circle.}");
				
				theQuestion += "\\\\\\text{    (1) }" + mc.getChoiceA();
				theQuestion +="\\\\\\text{    (2) }" + mc.getChoiceB();
				theQuestion +="\\\\\\text{    (3) }" + mc.getChoiceC();
				theQuestion +="\\\\\\text{    (4) }" + mc.getChoiceD();
				theQuestion += "\\end{array}";
				
				question = theQuestion;
				answer = mc.getAnswer();
			}
			else{
				question="";
				answer = "";
			}
		}

	private void getGraphic2Instance(int questionType){
		String theQuestion = "\\begin{array}{l}";
		MultipleChoice mc;
		
		if (questionType==1){

				theQuestion += "\\text{Look at the construction of a perpendicular bisector.}";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=11cm,interpolation=bicubic]{"+graphic2Address+"}";
				theQuestion += "\\\\\n\\\\\\text{Using the construction, which statment justifies }\\overline{AD}\\cong\\overline{BD}\\text{?}"; 
				
				mc = new MultipleChoice(
						"\\overline{AD}\\text{ and }\\overline{BD}\\text{ share the point } D.",
						"\\overline{AD}\\text{ and }\\overline{BD}\\text{ are perpendicular.}",
						"\\overline{AD}\\text{ and }\\overline{BD}\\text{ are sides of }\\angle ADB.",
						"\\overline{AD}\\text{ and }\\overline{BD}\\text{ are radii of congruent circles.}",
						"\\overline{AD}\\text{ and }\\overline{BD}\\text{ are radii of congruent circles.}");
				
				theQuestion += "\\\\\\text{    (1) }" + mc.getChoiceA();
				theQuestion +="\\\\\\text{    (2) }" + mc.getChoiceB();
				theQuestion +="\\\\\\text{    (3) }" + mc.getChoiceC();
				theQuestion +="\\\\\\text{    (4) }" + mc.getChoiceD();
				theQuestion += "\\end{array}";
				
				question = theQuestion;
				answer = mc.getAnswer();
			}
			else if (questionType==2){
				
				theQuestion += "\\text{Look at the construction.}";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=11cm,interpolation=bicubic]{"+graphic2Address+"}";
				theQuestion += "\\\\\n\\\\\\overleftrightarrow{DE}\\text{ is the perpendicular bisector of }\\overline{AB}\\text{. Which statement justifies}"; 
				theQuestion += "\\\\\\text{this construction?}";
				
				mc = new MultipleChoice(
						"\\overline{DE}\\text{ and }\\overline{AB}\\text{ are perpendicular to each other.}",
						"D \\text{ and }E\\text{ are equidistant from the endpoints of }\\overline{AB}.",
						"\\overline{DE}\\text{ and }\\overline{AB}\\text{ intersect at a 90}\\degree\\text{ angle}",
						"\\angle DCB\\text{ is congruent to }\\angle DCA.",
						"D \\text{ and }E\\text{ are equidistant from the endpoints of }\\overline{AB}.");
				
				theQuestion += "\\\\\\text{    (1) }" + mc.getChoiceA();
				theQuestion +="\\\\\\text{    (2) }" + mc.getChoiceB();
				theQuestion +="\\\\\\text{    (3) }" + mc.getChoiceC();
				theQuestion +="\\\\\\text{    (4) }" + mc.getChoiceD();
				theQuestion += "\\end{array}";
				
				question = theQuestion;
				answer = mc.getAnswer();
			}
			else{
				question="";
				answer = "";
			}
		}
	
	
	private void getGraphic3Instance(int questionType){
		String theQuestion = "\\begin{array}{l}";
		MultipleChoice mc;
		
		if (questionType==1){

				theQuestion += "\\text{Look at the construction of an equilateral triangle.}";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=12cm,interpolation=bicubic]{"+graphic3Address+"}";
				theQuestion += "\\\\\n\\\\\\text{Which is an intersection of the congruent circles?}"; 
				
				mc = new MultipleChoice(
						"\\overline{BC}",
						"\\text{Points } B\\text{ and }C",
						"\\Delta ABC",
						"\\text{Point } A",
						"\\text{Point } A");
				
				theQuestion += "\\\\\\text{    (1) }" + mc.getChoiceA();
				theQuestion +="\\\\\\text{    (2) }" + mc.getChoiceB();
				theQuestion +="\\\\\\text{    (3) }" + mc.getChoiceC();
				theQuestion +="\\\\\\text{    (4) }" + mc.getChoiceD();
				theQuestion += "\\end{array}";
				
				question = theQuestion;
				answer = mc.getAnswer();
			}
			else if (questionType==2){
				
				theQuestion += "\\text{Look at the construction of an equilateral triangle.}";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=12cm,interpolation=bicubic]{"+graphic3Address+"}";
				theQuestion += "\\\\\n\\\\\\text{To prove }ABC\\text{ is an equilateral triangle, it must be shown that all sides are congruent.}"; 
				theQuestion += "\\\\\\text{Which statement justifies }\\overline{AB}\\cong\\overline{BC}\\cong\\overline{CA}";
				
				mc = new MultipleChoice(
						"\\overline{AB}\\text{, }\\overline{BC}\\text{, and }\\overline{CA}\\text{ are all radii of congruent circles.}",
						"\\overline{AB}\\text{, }\\overline{BC}\\text{, and }\\overline{CA}\\text{ are sides of an equilateral triangle.}",
						"\\overline{AB}\\text{, }\\overline{BC}\\text{, and }\\overline{CA}\\text{ have equal lengths.}",
						"\\overline{AB}\\text{, }\\overline{BC}\\text{, and }\\overline{CA}\\text{ are congruent by SSS triangle congruence.}",
						"\\overline{AB}\\text{, }\\overline{BC}\\text{, and }\\overline{CA}\\text{ are all radii of congruent circles.}");
				
				theQuestion += "\\\\\\text{    (1) }" + mc.getChoiceA();
				theQuestion +="\\\\\\text{    (2) }" + mc.getChoiceB();
				theQuestion +="\\\\\\text{    (3) }" + mc.getChoiceC();
				theQuestion +="\\\\\\text{    (4) }" + mc.getChoiceD();
				theQuestion += "\\end{array}";
				
				question = theQuestion;
				answer = mc.getAnswer();
			}
			else{
				question="";
				answer = "";
			}
		}
	

	private void getGraphic4Instance(int questionType){
		String theQuestion = "\\begin{array}{l}";
		MultipleChoice mc;
		
		if (questionType==1){

				theQuestion += "\\text{Look at the construction of a perpendicular bisector.}";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=15cm,interpolation=bicubic]{"+graphic4Address+"}";
				theQuestion += "\\\\\n\\\\\\text{Which statement is not always true?}"; 
				
				mc = new MultipleChoice(
						"\\overline{LK}\\parallel\\overline{JM}",
						"\\overline{LM}\\perp\\overline{JK}",
						"\\overline{JK}\\cong\\overline{LM}",
						"\\overline{JN}\\cong\\overline{NK}",
						"\\overline{JK}\\cong\\overline{LM}");
				
				theQuestion += "\\\\\\text{    (1) }" + mc.getChoiceA();
				theQuestion +="\\\\\\text{    (2) }" + mc.getChoiceB();
				theQuestion +="\\\\\\text{    (3) }" + mc.getChoiceC();
				theQuestion +="\\\\\\text{    (4) }" + mc.getChoiceD();
				theQuestion += "\\end{array}";
				
				question = theQuestion;
				answer = mc.getAnswer();
			}
			else{
				question="";
				answer = "";
			}
		}
	
	private void getGraphic5Instance(int questionType){
		String theQuestion = "\\begin{array}{l}";
		MultipleChoice mc;
		
		if (questionType==1){

				theQuestion += "\\text{The diagram below shows the construction of a line through point } P \\text{ perpendicular}";
				theQuestion += "\\\\\\text{to line }m.";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=15cm,interpolation=bicubic]{"+graphic5Address+"}";
				theQuestion += "\\\\\n\\\\\\text{Which statement is demonstrated by this construction?}"; 
				
				mc = new MultipleChoice(
						"If a line is parallel to a line that is perpendicular to a third line, then the line}\\\\\\text{         is also perpendicular to the third line.}",
						"The set of points equidistant from the endpoints of a line segment is the}\\\\\\text{         perpendicular bisector of the segment.}",
						"Two lines are perpendicular if they are equidistant from a given point.}",
						"Twolinesareperpendiculariftheyintersecttoformaverticalline.}",
						"\\text{The set of points equidistant from the endpoints of a line segment is the perpendicular bisector of the segment.}");
				
				theQuestion += "\\\\\\text{    (1) " + mc.getChoiceA();
				theQuestion +="\\\\\\text{    (2) " + mc.getChoiceB();
				theQuestion +="\\\\\\text{    (3) " + mc.getChoiceC();
				theQuestion +="\\\\\\text{    (4) " + mc.getChoiceD();
				theQuestion += "\\end{array}";
				
				question = theQuestion;
				answer = mc.getAnswer();
			}
			else{
				question="";
				answer = "";
			}
		}
	
	private void getGraphic6Instance(int questionType){
		String theQuestion = "\\begin{array}{l}";
		MultipleChoice mc;
		
		if (questionType==1){

				theQuestion += "\\text{Based on the construction below, which statement must be true?}";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=15cm,interpolation=bicubic]{"+graphic6Address+"}";
				theQuestion += "\\\\\n\\\\"; 
				
				mc = new MultipleChoice(
						"m\\angle ABF = \\frac{1}{2}m\\angle CBF",
						"m\\angle ABF = m\\angle ABC",
						"m\\angle ABF = m\\angle CBF",
						"m\\angle CBF = \\frac{1}{2}m\\angle ABF",
						"m\\angle ABF = m\\angle CBF");
				
				theQuestion += "\\\\\\text{    (1) }" + mc.getChoiceA();
				theQuestion +="\\\\\\text{    (2) }" + mc.getChoiceB();
				theQuestion +="\\\\\\text{    (3) }" + mc.getChoiceC();
				theQuestion +="\\\\\\text{    (4) }" + mc.getChoiceD();
				theQuestion += "\\end{array}";
				
				question = theQuestion;
				answer = mc.getAnswer();
			}
		else if (questionType==2){

			theQuestion += "\\text{The diagram below shows the construction of the bisector of }\\angle ABC";
			theQuestion += "\\\\\\text{                                      }\\includegraphics[width=12cm,interpolation=bicubic]{"+graphic6Address+"}";
			theQuestion += "\\\\\n\\\\\\text{Which statement is not true?}"; 
			
			mc = new MultipleChoice(
					"m\\angle EBF = \\frac{1}{2}m\\angle ABC",
					"m\\angle DBF = \\frac{1}{2}m\\angle ABC",
					"m\\angle EBF = m\\angle ABC",
					"m\\angle DBF = m\\angle EBF",
					"m\\angle EBF = m\\angle ABC");
			
			theQuestion += "\\\\\\text{    (1) }" + mc.getChoiceA();
			theQuestion +="\\\\\\text{    (2) }" + mc.getChoiceB();
			theQuestion +="\\\\\\text{    (3) }" + mc.getChoiceC();
			theQuestion +="\\\\\\text{    (4) }" + mc.getChoiceD();
			theQuestion += "\\end{array}";
			
			question = theQuestion;
			answer = mc.getAnswer();
		}
			else{
				question="";
				answer = "";
			}
	}
	
	private void getGraphic7Instance(int questionType){
		String theQuestion = "\\begin{array}{l}";
		MultipleChoice mc;
		
		if (questionType==1){

				theQuestion += "\\text{Which geometric principle is used to justify the construction below?}";
				theQuestion += "\\\\\\text{                                      }\\includegraphics[width=15cm,interpolation=bicubic]{"+graphic7Address+"}";
				theQuestion += "\\\\\n\\\\"; 
				
				mc = new MultipleChoice(
						"A line perpendicular to one of two parallel lines is perpendicular to the}\\\\\\text{         other.}",
						"Two lines are perpendicular if they intersect to form congruent adjacent}\\\\\\text{         angles.}",
						"When two lines are intersected by a transversal and alternate interior}\\\\\\text{         angles are congruent, the lines are parallel.}",
						"When two lines are intersected by a transversal and the corresponding}\\\\\\text{         angles are congruent, the lines are parallel.}",
						"\\text{When two lines are intersected by a transversal and the corresponding}\\\\\\text{         angles are congruent, the lines are parallel.}");
				
				theQuestion += "\\\\\\text{    (1) " + mc.getChoiceA();
				theQuestion +="\\\\\\text{    (2) " + mc.getChoiceB();
				theQuestion +="\\\\\\text{    (3) " + mc.getChoiceC();
				theQuestion +="\\\\\\text{    (4) " + mc.getChoiceD();
				theQuestion += "\\end{array}";
				
				question = theQuestion;
				answer = mc.getAnswer();
			}
			else{
				question="";
				answer = "";
			}
		}
	
}
