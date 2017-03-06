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
package components;

import java.awt.Color;
import java.util.ArrayList;

import guiTeacher.components.Accordion;
import guiTeacher.components.AccordionTab;
import guiTeacher.components.Action;
import guiTeacher.components.SearchBox;
import guiTeacher.components.StyledComponent;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.Visible;
import main.OrcMath;

public class TopicAccordion extends Accordion {

	private QuestionPreview qp;
	private SearchBox searchBox;
	
	public static final int CONTENT_HEIGHT = 310;
	
	public TopicAccordion(FocusController fc, int x, int y, int w, SearchBox searchBox) {
		super(x, y, w);
		this.searchBox = searchBox;
		qp = new QuestionPreview(x+w, y);
		String[] algebraTopics = {"Algebra: Linear Equations",
				"Algebra: Linear Equations (Multiple Choice)",
				"Algebra: Quadratic Equations",
				"Algebra: Ratio Sums",
				"Algebra: Pythagorean Theorem Equations",
				"Rational Equations: Solve Proportions (Involving Linear Equations)",
				"Polynomials: Factoring polynomials",
				"Polynomials: Factoring by grouping",
				"Polynomials: Factoring GCF",
				"Polynomials: Factoring by grouping with GCF",
				"Polynomials: Factoring trinomial by grouping",
				"Polynomials: Factoring trinomial by grouping and GCF",
				"Polynomials: Factoring higher degree polynomials",//does not contain explained answer key
				"Polynomials: Solve any quadratic",
				"Rational Expressions: Multiply and divide rational expressions",
				"Rational Expressions: Identifying the LCD",
				"Rational Expressions: Add and subtract rational expressions",//key is not entirely complete
				"Rational Expressions: Simplify complex rational expressions",
				"Rational Expressions: Simplify polynomials with fractional coefficients",
				"Rational Expressions: Distribute polynomials with fractional coefficients",
				"Rational Equations: Solve Proportions (Involving Linear Equations)",
				"Logarithms: Solving Equations (Level 1)",
				"Radical Expressions: Simplify radicals",
				"Radical Expressions: Add radicals",
				"Radical Expressions: Rationalize denominators",	
				"Complex Numbers: Rationalize Imaginary Denominators",
				"Complex Numbers: Multiply Complex Numbers",
				"Complex Numbers: Simplify powers of i",
//		 		"Rational Expressions: Simplify polynomials",
				};
		String[] geometryTopics = {
				"Transformations: Reflection",
			  	"Transformations: Rotation",
			  	"Transformations: Dilation",
			  	"Transformations: Translation",
			  	"Transformations: Compositions",
				
				"Formulas: Surface Area of a Sphere",
				"Formulas: Volume of a Cone",
				"Formulas: Volume of a Pyramid",
//				"Formulas: Volume of a Sphere",
				"Formulas: Surface Area of a Cylinder",
				"Formulas: Surface Area of a Cone",
				"Formulas: Volume of a Cylinder",
				"Similarity: Side Splitter Theorem",
				"Similarity: Side Splitter Theorem with Quadratics",
				"Similarity: Triangle Angle Bisector Theorem",
				"Circles: Secant Segments",							//check
				"Circles: Chord Lengths",
				
				"Circles: Parallel Chords",								//check
				"Circles: Tangent Lines",							//check
				"Circles: Inscribed Angles with a Common Arc",				//check
				
				"Circles: Distance Between Center and Chord",				//check
				"Circles: Pythagorean Theorem with Tangent Lines",			//check
				"Circles: Central Angles",							//check
				"Circles: Inscribed Quadrilaterals",				//check
				"Circles: Intersecting Chords",
				"Circles: Inscribed Angles",//check
				"Circles: Secant Angles",
				"Circles: Angles Formed by a Chord and a Tangent",
				"Coordinate Geometry: Calculate distance",
				"Coordinate Geometry: Calculate midpoint",
				"Coordinate Geometry: Determine the relationship between two lines",
				"Coordinate Geometry: Write the equation of a line that is perp. or par. to a given line",
//				"Geometry Basics: Use algebra to calculate the measure of an angle",
				"Quadrilaterals: Use algebra to calculate angle measures in a quadrilateral",
				"Quadrilaterals: Algebraically solve using Pythagorean Theorem applications",              //no key!
				"Quadrilaterals: Algebraically solve using Pythagorean Theorem applications Using Quadratic Equations",
				"Quadrilaterals: Use algebra to calculate measures of congruent parts in a quadrilateral",
				"Geometric relationships",
//				"Geometric Constructions: Justifications",
//				"Geometric Constructions: Justifications",
//				"Geometric Constructions",
//				"Locus: Sketch locus in a coordinate plane",
				"Locus: Identify the number of points"};		//check
		
		String[] functions = {
				"Graphing: Quadratics given domain",
				"Graphing: Radicals given domain",
				"Graphing: Absolute value given domain",
//				"Graphing: Piecewise functions",
//				"Graphing: Determine continuity of piecewise function, including removeable discontinuity",
//				"Graphing: Determine continuity of piecewise function",
				"Graphing: Determine continuity of piecewise function (algebraically)",
				"Graphing: Removeable Discontinuity",
				"Graphing: Identify Max and Min",
				"Systems of linear and quadratic equations",
				"Systems of linear and quadratic equations by graphing",
				"Systems of linear and quadratic equations, (x,y) solution",
				"Systems of linear and quadratic equations, (x,y) solution, check work",
				"Trig: Right Triangle Sides",
				"Trig: Right Triangle Angles",
				"Trig: Law of Cosines (Sides)",
				"Trig: Law of Cosines (Angles)",
				"Trig: Law of Sines",
				"Functions: Identify functions",
				"Functions: Use inverse variation to solve for unknown values",

		};
		
		String[] nothing = {};
		
		searchBox.setTarget(addTab(fc, "Search Results", nothing));
		addTab(fc, "Simplifying & Solving", algebraTopics);
		addTab(fc, "Geometry", geometryTopics);
		addTab(fc, "Functions", functions);
		
		update();
		
		
	}
	
	public boolean isHovered(int x, int y){
		boolean b = super.isHovered(x, y);
		if(qp.isInitialized())qp.setVisible(b);
		return b;
	}
	
	private AccordionTab addTab(FocusController fc, String name, String[] types){
		ArrayList<Visible> links = new ArrayList<Visible>();
		final int LINE_HEIGHT = 22;
		int yLoc = LINE_HEIGHT;
		for(String s: types){
			Type l = new Type(10,yLoc,getWidth()-20,LINE_HEIGHT,s,new Action() {
				
				@Override
				public void act() {
					String[] tableData = {"1",s,"1"};
					OrcMath.createScreen.addQuestion(tableData);
				}
			},qp);
			searchBox.addToCollection(l);
			l.setSize(13);
			l.setLinkColor(Color.BLACK);
			l.setAlign(StyledComponent.ALIGN_LEFT);
			links.add(l);
			yLoc+=LINE_HEIGHT;
		}
		
		QuestionListSubscreen screen = new QuestionListSubscreen(fc, this, getWidth(), CONTENT_HEIGHT, links);
		return addTab(name, screen);
		
	}
	

}
