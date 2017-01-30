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

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class MakeProblems {
//	  to switch between the test folder and the real folder, change the directory
//	  static private String directory = System.getProperty( "user.home" )+"/Documents/Programming/Workspace/NewProject/war/WEB-INF/problems/";
	  static private String directory = System.getProperty( "user.home" )+"/Documents/Programming/Workspace/Mathematics/PDFs/";
	  
	  static int numberOfProblems=2;
	  protected static String folderName = "circle";//name of the directory folder
	  protected static String problemType = "Formulas: Surface Area of a Cylinder";
	  protected static String[] problemTypes = {
		  	"Geometry: Identify Theorem and Solve",
			"Geometry: Identify Theorem Only",
		  	
			"Formulas: Volume of a Cylinder",
			"Formulas: Volume of a Pyramid",
			"Formulas: Volume of a Cone",
			"Formulas: Volume of a Sphere",
			"Formulas: Surface Area of a Cylinder",
			"Formulas: Surface Area of a Cone",
			"Formulas: Surface Area of a Sphere",
			
		  	"Transformations: Translation",
		  	"Transformations: Reflection",
		  	"Transformations: Rotation",
		  	"Transformations: Dilation",
		  	"Transformations: Compositions",
		  	
		  	"Circles: Secant Segments",
		  	"Circles: Chord Lengths",
		  	"Circles: Central Angles",
		  	"Circles: Parallel Chords",
		  	"Circles: Inscribed Angles",
		  	"Circles: Intersecting Chords",
		  	"Circles: Secant Angles",
		  	"Circles: Inscribed Quadrilaterals",
		  	"Circles: Tangent Lines",
		  	"Circles: Angles Formed by a Chord and a Tangent",
		  	"Circles: Distance Between Center and Chord",
		  	"Circles: Pythagorean Theorem with Tangent Lines",
		  	"Circles: Inscribed Angles with a Common Arc",
		  	
		  	
		  	"Polynomials: Factoring polynomials",//done, added
			"Polynomials: Factoring polynomials (with rational coefficients)",//done, added
			"Polynomials: Factoring higher degree polynomials",//done, added
			"Functions: Identify functions",//done, added
			"Functions: Use inverse variation to solve for unknown values",//done, added
			"Polynomials: Solve any quadratic",//done, added
			"Rational Expressions: Multiply and divide rational expressions",//done
			"Rational Expressions: Add and subtract rational expressions",//done
			"Rational Expressions: Simplify complex rational expressions",
			"Rational Expressions: Simplify polynomials with fractional coefficients",
			"Rational Expressions: Distribute polynomials with fractional coefficients",
			
			"Coordinate Geometry: Calculate distance",//done, added
			"Coordinate Geometry: Calculate midpoint",//done, added
			"Coordinate Geometry: Determine the relationship between two lines",//done, added
			"Geometry Basics: Use algebra to calculate the measure of an angle",//done, added
			"Quadrilaterals: Use algebra to calculate angle measures in a quadrilateral",//done, added
			"Quadrilaterals: Algebraically solve using Pythagorean Theorem applications",//done, added
			"Quadrilaterals: Use algebra to calculate measures of congruent parts in a quadrilateral",//COULD NOT FINISH
			
		    "Coordinate Geometry: Write the equation of a line that is perp. or par. to a given line",
		    "Coordinate Geometry: Write the equation of a line that is perpendicular to a given line",
		    "Coordinate Geometry: Write the equation of a line that is parallel to a given line",
			"Geometric relationships",//done, added
			"Geometric Constructions: Justifications",//DOES NOT CONTAIN ORIGINAL WORK
			"Geometric Constructions",//DOES NOT CONTAIN ORIGINAL WORK
			"Locus: Sketch locus in a coordinate plane",//done, added
			"Locus: Identify the number of points",//done, added
			
			"Radical Expressions: Simplify radicals",//done, added
			"Radical Expressions: Add radicals",//done, added
			"Radical Expressions: Rationalize denominators",//done, added
			
			"Complex Numbers: Rationalize Imaginary Denominators",//done, added
			"Complex Numbers: Multiply Complex Numbers",//done, added
			"Complex Numbers: Simplify powers of i",//done, added
			"Rational Expressions: Simplify polynomials"
			};


	//no constructor (therefore, all fields must be static :)

	//methods  
	  public static void main(String[] args) throws IOException {
		  //make the directory for the questions
		  boolean success = (new File(directory+folderName+"/1/questions/")).mkdirs();
		  if (success) {
			  System.out.println("Directories: " 
					  + directory+folderName+"/1/questions/" + " created");
		  }
		  //make the directory for the questions
		  success = (new File(directory+folderName+"/2/questions/")).mkdirs();
		  if (success) {
			  System.out.println("Directories: " 
					  + directory+folderName+"/2/questions/" + " created");
		  }
		  //make the directory for the questions
		  success = (new File(directory+folderName+"/3/questions/")).mkdirs();
		  if (success) {
			  System.out.println("Directories: " 
					  + directory+folderName+"/3/questions/" + " created");
		  }
		  //make the directory for the questions
		  success = (new File(directory+folderName+"/4/questions/")).mkdirs();
		  if (success) {
			  System.out.println("Directories: " 
					  + directory+folderName+"/4/questions/" + " created");
		  }
		  //make the directory for the answers
		  success = (new File(directory+folderName+"/1/answers/")).mkdirs();
		  if (success) {
			  System.out.println("Directories: " 
					  + directory + " created");
		  }
		  //make the directory for the answers
		  success = (new File(directory+folderName+"/2/answers/")).mkdirs();
		  if (success) {
			  System.out.println("Directories: " 
					  + directory + " created");
		  }
		  //make the directory for the answers
		  success = (new File(directory+folderName+"/3/answers/")).mkdirs();
		  if (success) {
			  System.out.println("Directories: " 
					  + directory + " created");
		  }
		  //make the directory for the answers
		  success = (new File(directory+folderName+"/4/answers/")).mkdirs();
		  if (success) {
			  System.out.println("Directories: " 
					  + directory + " created");
		  }

		  makeProblems();
	  }
	  
	  
	  public static void makeProblems() throws IOException{
//		  String questionFileNameStem = directory+folderName+"/"+difficulty+"/"+"questions/question";
//		  String answerFileNameStem = directory+folderName+"/"+difficulty+"/"+"answers/answer";
		  int numberCompleted = 1;
		  for(int difficulty = 1; difficulty<5; difficulty++){
			  String questionFileNameStem = directory+folderName+"/"+difficulty+"/questions/question";
			  String answerFileNameStem = directory+folderName+"/"+difficulty+"/answers/answer";
			  for (int index=0; index<numberOfProblems;index++){
				  System.out.println("Number of problems made: "+numberCompleted);
				  numberCompleted++;
				  Problem p;
				  p = new Problem(problemType,difficulty);
				  File questionFile = new File(questionFileNameStem+(index+1)+".png");
				  File answerFile = new File(answerFileNameStem+(index+1)+".png");
				  ImageIO.write(p.getQuestionImage(), "png", questionFile.getAbsoluteFile());
				  ImageIO.write(p.getAnswerImage(), "png", answerFile.getAbsoluteFile());
			  }
		  }
	  }
	  


	//  // iText allows to add metadata to the PDF which can be viewed in your Adobe
	//  // Reader
	//  // under File -> Properties
	//  private static void addMetaData(Document document) {
//	    document.addTitle("My first PDF");
//	    document.addSubject("Using iText");
//	    document.addKeywords("Java, PDF, iText");
//	    document.addAuthor("Benjamin Nockles");
//	    document.addCreator("Benjamin Nockles");
	//  }


	
}
