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

import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants; 
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.orcmath.drawabletype.*;
import com.orcmath.objects.Expression;
import com.orcmath.objects.Ops;
import com.orcmath.objects.Term;
import com.orcmath.objects.Variable;
import com.orcmath.type.*;


public class Problem {

	/** These were the fields of the static class Problem
//	static String question;
//	static String instructions;
//	static String answer;
//	static MultipleChoice MCAnswer;
//	static Object graphic;
	 * 
	 */
	String keyTheorem;
	String question;
	String instructions;
	String answer;
	MultipleChoice MCAnswer;
	BufferedImage questionImage;
	BufferedImage answerImage;
	static int latexFontSize = 28;//usually 26
	int numberOfColumns;
	int verticalSpacing;
	int questionNeedsThisMuchExtraSpaceOnTop;
	int answerNeedsThisMuchExtraSpaceOnTop;
	double scaleFactor;
	//	double varySize = .9;
	private boolean whetherInstructionsAreNeverIncluded;
	//String questionAddress = "PDFs/question.png"; 
	//String answerAddress = "PDFs/answer.png"; 

	public static final String	CUSTOM_TAG="Custom";
	public static final String	GRAPHICS_TAG="~!~";
	public static final String	ALLOWBREAK_TAG="~br~";
	public static final String[] problemTypes = {
			"Algebra: Linear Equations",
			"Algebra: Linear Equations (Multiple Choice)",
			"Algebra: Quadratic Equations",
			"Algebra: Ratio Sums",
			"Algebra: Pythagorean Theorem Equations",
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

			"Similarity: Side Splitter Theorem",
			"Similarity: Side Splitter Theorem w/ Quadratics",

			"Circles: Secant Segments",
			"Circles: Chord Lengths",

			"Circles: Central Angles",
			"Circles: Parallel Chords",	
			"Circles: Tangent Lines",
			"Circles: Inscribed Angles with a Common Arc",

			"Circles: Distance Between Center and Chord",
			"Circles: Pythagorean Theorem with Tangent Lines",
			"Circles: Chord Length Given Distance from Center",

			"Circles: Angles Formed by a Chord and a Tangent",
			"Circles: Inscribed Angles",
			"Circles: Intersecting Chords",
			"Circles: Inscribed Quadrilaterals",
			"Circles: Secant Angles",

			"Graphing: Quadratics given domain",
			"Graphing: Radicals given domain",
			"Graphing: Absolute value given domain",
			"Graphing: Piecewise functions",
			"Graphing: Determine continuity of piecewise function",
			"Graphing: Determine continuity of piecewise function (algebraically)",
			"Graphing: Removeable Discontinuity",

			"Graphing: Identify Max and Min",

			"Polynomials: Factoring polynomials",
			"Polynomials: Factoring GCF",
			"Polynomials: Solve any quadratic",
			"Systems of linear and quadratic equations",
			"Systems of linear and quadratic equations, (x,y) solution",
			"Systems of linear and quadratic equations by graphing",
			"Polynomials: Factoring by grouping",
			"Polynomials: Factoring by grouping with GCF",
			"Polynomials: Factoring trinomial by grouping",
			"Polynomials: Factoring trinomial by grouping and GCF",
			"Polynomials: Factoring polynomials (with rational coefficients)",
			"Functions: Use inverse variation to solve for unknown values",
			"Quadratics: Identify roots by factoring. (a=1)",
			"Quadratics: Identify roots by factoring. (a�1)",
			"Quadratics: Identify (real) roots using the quadratic formula.",
			"Quadratics: Identify (real & complex) roots using the quadratic formula.",
			"Radical Expressions: Simplify radicals",
			"Radical Expressions: Add radicals",
			"Radical Expressions: Rationalize denominators",
			"Rational Expressions: Identifying the LCD",
			"Rational Expressions: Add and subtract rational expressions",
			"Rational Expressions: Multiply and divide rational expressions",
			"Rational Expressions: Distribute polynomials with fractional coefficients",
			"Rational Expressions: Simplify polynomials",
			"Rational Expressions: Simplify complex rationals expressions",
			"Rational Expressions: Simplify polynomials with fractional coefficients",
			"Rational Equations: Solve Proportions (Involving Linear Equations)",
			"Complex Numbers: Simplify powers of i",
			"Complex Numbers: Rationalize Imaginary Denominators",
			"Complex Numbers: Multiply Complex Numbers",
			"Logarithms: Solving Equations (Level 1)",
			"Geometry Basics: Use algebra to calculate the measure of an angle",
			"Quadrilaterals: Use algebra to calculate measures of congruent parts in a quadrilateral",
			"Quadrilaterals: Use algebra to calculate angle measures in a quadrilateral",
			"Quadrilaterals: Algebraically solve using Pythagorean Theorem applications",
			"Quadrilaterals: Algebraically solve using Pythagorean Theorem applications Using Quadratic Equations",
			"Coordinate Geometry: Calcluate distance",
			"Coordinate Geometry: Calcluate midpoint",
			"Coordinate Geometry: Determine the relationship between two lines",
			"Coordinate Geometry: Write the equation of a line that is perp. or par. to a given line",
			"Coordinate Geometry: Write the equation of a line that is perpendicular to a given line",
			"Coordinate Geometry: Write the equation of a line that is parallel to a given line",
			"Geometric relationships",
			"Geometric Constructions: Justifications",
			"Geometric Constructions",
			"Locus: Sketch locus in a coordinate plane"
	};

	public Problem(String string, int difficulty) {
		//public static void main(String[] args) { //what used to be here
		int diff = difficulty;

		if (string.equals("Algebra: Linear Equations")){
			AlgebraPracticeLinear newProblem = new AlgebraPracticeLinear(diff, false);
			getProblemData(newProblem);
		}

		else if (string.equals("Algebra: Linear Equations (Multiple Choice)")){
			AlgebraPracticeLinear newProblem = new AlgebraPracticeLinear(diff, true);
			getProblemData(newProblem);
		}

		else if (string.equals("Algebra: Quadratic Equations")){
			QuadLinSystem newProblem = new QuadLinSystem(diff, false, true, true);
			getProblemData(newProblem);
		}

		else if (string.equals("Algebra: Ratio Sums")){
			AlgebraPracticeRatios newProblem = new AlgebraPracticeRatios(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Algebra: Pythagorean Theorem Equations")){
			AlgebraPracticePythagorean newProblem = new AlgebraPracticePythagorean(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Geometry: Identify Theorem and Solve")){
			IdentifyTheorem newProblem = new IdentifyTheorem(diff, true);
			getProblemData(newProblem);
		}

		else if (string.equals("Geometry: Identify Theorem Only")){
			IdentifyTheorem newProblem = new IdentifyTheorem(diff, false);
			getProblemData(newProblem);
		}


		else if (string.equals("Formulas: Volume of a Cylinder")){
			VolumeCylinder newProblem = new VolumeCylinder(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Formulas: Volume of a Pyramid")){
			VolumePyramid newProblem = new VolumePyramid(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Formulas: Volume of a Cone")){
			VolumeCone newProblem = new VolumeCone(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Formulas: Volume of a Sphere")){
			VolumeSphere newProblem = new VolumeSphere(diff);
			getProblemData(newProblem);
		}


		else if (string.equals("Formulas: Surface Area of a Cylinder")){
			SurfaceAreaCylinder newProblem = new SurfaceAreaCylinder(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Formulas: Surface Area of a Cone")){
			SurfaceAreaCone newProblem = new SurfaceAreaCone(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Formulas: Surface Area of a Sphere")){
			SurfaceAreaSphere newProblem = new SurfaceAreaSphere(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Transformations: Translation")){
			PerformTranslate newProblem = new PerformTranslate(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Transformations: Reflection")){
			PerformReflect newProblem = new PerformReflect(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Transformations: Rotation")){
			PerformRotate newProblem = new PerformRotate(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Transformations: Dilation")){
			PerformDilate newProblem = new PerformDilate(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Transformations: Compositions")){
			PerformCompTransform newProblem = new PerformCompTransform(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Circles: Secant Segments")){
			SecantLengths newProblem = new SecantLengths(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Similarity: Side Splitter Theorem")){
			SideSplitter newProblem = new SideSplitter(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Similarity: Side Splitter Theorem with Quadratics")){
			SideSplitter newProblem = new SideSplitterQuadratics(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Similarity: Triangle Angle Bisector Theorem")){
			TriangleAngleBisector newProblem = new TriangleAngleBisector(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Circles: Chord Lengths")){
			ChordLengths newProblem = new ChordLengths(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Circles: Central Angles")){
			CentralAngles newProblem = new CentralAngles(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Circles: Parallel Chords")){
			ParallelChords newProblem = new ParallelChords(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Circles: Inscribed Angles")){
			InscribedAngleArc newProblem = new InscribedAngleArc(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Circles: Intersecting Chords")){
			IntersectingChords newProblem = new IntersectingChords(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Circles: Secant Angles")){
			SecantAngles newProblem = new SecantAngles(diff);
			getProblemData(newProblem);
		}


		else if (string.equals("Circles: Inscribed Quadrilaterals")){
			InscribedQuadrilateral newProblem = new InscribedQuadrilateral(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Circles: Tangent Lines")){
			TangentLines newProblem = new TangentLines(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Circles: Angles Formed by a Chord and a Tangent")){
			ChordTangentAngle newProblem = new ChordTangentAngle(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Circles: Distance Between Center and Chord")){
			ChordDistanceFromCenter newProblem = new ChordDistanceFromCenter(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Circles: Pythagorean Theorem with Tangent Lines")){
			TangentPythagorean newProblem = new TangentPythagorean(diff);
			getProblemData(newProblem);
		}


		else if (string.equals("Circles: Inscribed Angles with a Common Arc")){
			InscribedAngleSameArc newProblem = new InscribedAngleSameArc(diff);
			getProblemData(newProblem);
		}


		else if (string.equals("Graphing: Quadratics given domain")){
			GraphingGivenDomain newProblem = new GraphingGivenDomain(diff,GraphingGivenDomain.ONLY_QUADRATICS);
			getProblemData(newProblem);
		}

		else if (string.equals("Graphing: Radicals given domain")){
			GraphingGivenDomain newProblem = new GraphingGivenDomain(diff, GraphingGivenDomain.ONLY_RADICAL);
			getProblemData(newProblem);
		}

		else if (string.equals("Graphing: Absolute value given domain")){
			GraphingGivenDomain newProblem = new GraphingGivenDomain(diff, GraphingGivenDomain.ONLY_ABSOLUTE_VALUE);
			getProblemData(newProblem);
		}


		else if (string.equals("Graphing: Piecewise functions")){
			PiecewiseFunctions newProblem = new PiecewiseFunctions(diff, false, false, false, true);
			getProblemData(newProblem);
		}

		else if (string.equals("Graphing: Determine continuity of piecewise function")){
			PiecewiseFunctions newProblem = new PiecewiseFunctions(diff, true, false, false, true);
			getProblemData(newProblem);
		}

		else if (string.equals("Graphing: Removeable Discontinuity")){
			PiecewiseFunctions newProblem = new PiecewiseFunctions(diff, true, true);
			getProblemData(newProblem);
		}

		else if (string.equals("Graphing: Determine continuity of piecewise function (algebraically)")){
			PiecewiseFunctions newProblem = new PiecewiseFunctions(diff, true, false, false, false);
			getProblemData(newProblem);
		}


		else if (string.equals("Graphing: Identify Max and Min")){
			IdentifyRelativeMaxMin newProblem = new IdentifyRelativeMaxMin(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Polynomials: Factoring by grouping")){
			FactorByGrouping newProblem = new FactorByGrouping(diff,false, false);
			getProblemData(newProblem);
		}

		else if (string.equals("Polynomials: Factoring by grouping with GCF")){
			FactorByGrouping newProblem = new FactorByGrouping(diff, false, true);
			getProblemData(newProblem);
		}

		else if (string.equals("Polynomials: Factoring trinomial by grouping")){
			FactorByGrouping newProblem = new FactorByGrouping(diff, true, false);
			getProblemData(newProblem);
		}

		else if (string.equals("Polynomials: Factoring trinomial by grouping and GCF")){
			FactorByGrouping newProblem = new FactorByGrouping(diff, true, true);
			getProblemData(newProblem);
		}

		else if (string.equals("Polynomials: Factoring polynomials")){
			Factor newProblem = new Factor(diff, true);
			getProblemData(newProblem);
		}


		else if (string.equals("Polynomials: Factoring GCF")){
			Factor newProblem = new Factor(diff, true, true, diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Polynomials: Factoring polynomials (with rational coefficients)")){
			Factor newProblem = new Factor(diff, false);
			getProblemData(newProblem);
		}


		else if (string.equals("Polynomials: Solve any quadratic")){
			SolveQuadratic newProblem = new SolveQuadratic(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Systems of linear and quadratic equations")){
			QuadLinSystem newProblem = new QuadLinSystem(diff, true, false, false);
			getProblemData(newProblem);
		}

		else if (string.equals("Systems of linear and quadratic equations, (x,y) solution")){
			QuadLinSystem newProblem = new QuadLinSystem(diff, true, true, false);
			getProblemData(newProblem);
		}

		else if (string.equals("Systems of linear and quadratic equations by graphing")){
			QuadLinSystem newProblem = new QuadLinSystem(diff);
			getProblemData(newProblem);
		}


		else if (string.equals("Systems of linear and quadratic equations, (x,y) solution, check work")){
			QuadLinSystem newProblem = new QuadLinSystem(diff, true, true, true);
			getProblemData(newProblem);
		}

		else if (string.equals("Polynomials: Factoring higher degree polynomials")){
			Factor newProblem = new Factor(diff, true, false, diff);
			getProblemData(newProblem);
		}


		else if (string.equals("Functions: Identify functions")){
			IdentifyFunction newProblem = new IdentifyFunction(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Functions: Use inverse variation to solve for unknown values")){
			InverseVariation newProblem = new InverseVariation(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Trig: Right Triangle Sides")){
			TrigBasic newProblem = new TrigBasic(diff);
			getProblemData(newProblem);
		}
		
		else if (string.equals("Trig: Right Triangle Angles")){
			TrigInverseBasic newProblem = new TrigInverseBasic(diff);
			getProblemData(newProblem);
		}
		
		else if (string.equals("Trig: Law of Sines")){
			LawOfSines newProblem = new LawOfSines(diff);
			getProblemData(newProblem);
		}
		
		else if (string.equals("Trig: Law of Cosines (Sides)")){
			LawOfCosines newProblem = new LawOfCosines(diff,true);
			getProblemData(newProblem);
		}
		else if (string.equals("Trig: Law of Cosines (Angles)")){
			LawOfCosines newProblem = new LawOfCosines(diff,false);
			getProblemData(newProblem);
		}
		
		else if (string.equals("Rational Expressions: Simplify polynomials")){
			SimplifyExpression newProblem = new SimplifyExpression(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Rational Expressions: Simplify polynomials with fractional coefficients")){
			SimplifyExpressionFractions newProblem = new SimplifyExpressionFractions(diff);
			getProblemData(newProblem);
		}	

		else if (string.equals("Rational Expressions: Identifying the LCD")){
			AddRationalExpressionsFactoring newProblem = new AddRationalExpressionsFactoring(false, diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Rational Expressions: Add and subtract rational expressions")){
			AddRationalExpressionsFactoring newProblem = new AddRationalExpressionsFactoring(true, diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Rational Expressions: Multiply and divide rational expressions")){
			MultiplyDivideRationals newProblem = new MultiplyDivideRationals(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Rational Expressions: Distribute polynomials with fractional coefficients")){
			DistributeRationals newProblem = new DistributeRationals(diff);
			getProblemData(newProblem);
		}

		else if (string.equals("Rational Expressions: Simplify complex rational expressions")){
			SimplifyComplexFraction newProblem = new SimplifyComplexFraction(diff);
			getProblemData(newProblem);
		}



		else if (string.equals("Rational Equations: Solve Proportions (Involving Linear Equations)")){
			SolveProportionLinear newProblem = new SolveProportionLinear(diff);
			getProblemData(newProblem);
		}

		else if(string.equals("Radical Expressions: Simplify radicals")){
			SimplifyRadical newProblem = new SimplifyRadical(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Radical Expressions: Add radicals")){
			AddRadical newProblem = new AddRadical(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Radical Expressions: Rationalize denominators")){
			RationalizeDenominator newProblem = new RationalizeDenominator(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Complex Numbers: Simplify powers of i")){
			SimplifyPowersOfi newProblem = new SimplifyPowersOfi(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Complex Numbers: Rationalize Imaginary Denominators")){
			RationalizeImaginaryDenominator newProblem = new RationalizeImaginaryDenominator(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Complex Numbers: Multiply Complex Numbers")){
			MultiplyComplexNumbers newProblem = new MultiplyComplexNumbers(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Logarithms: Solving Equations (Level 1)")){
			SolveLogaritmicLevel1 newProblem = new SolveLogaritmicLevel1();	
			getProblemData(newProblem);
		}

		else if(string.equals("Geometry Basics: Use algebra to calculate the measure of an angle")){
			BasicAngleAlgebra newProblem = new BasicAngleAlgebra(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Quadrilaterals: Use algebra to calculate measures of congruent parts in a quadrilateral")){
			CongruentQuadrilateralParts newProblem = new CongruentQuadrilateralParts(diff);	
			getProblemData(newProblem);
		}


		else if(string.equals("Quadrilaterals: Use algebra to calculate angle measures in a quadrilateral")){
			QuadrilateralAngles newProblem = new QuadrilateralAngles(diff);	
			getProblemData(newProblem);
		}


		else if(string.equals("Quadrilaterals: Algebraically solve using Pythagorean Theorem applications")){
			RightTriangleSides newProblem = new RightTriangleSides(diff, false);	
			getProblemData(newProblem);
		}

		else if(string.equals("Quadrilaterals: Algebraically solve using Pythagorean Theorem applications Using Quadratic Equations")){
			RightTriangleSides newProblem = new RightTriangleSides(diff, true);	
			getProblemData(newProblem);
		}

		else if(string.equals("Coordinate Geometry: Calculate distance")){
			DistanceFormula newProblem = new DistanceFormula(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Coordinate Geometry: Calculate midpoint")){
			Midpoint newProblem = new Midpoint(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Geometric relationships")){
			GeometricRelationship newProblem = new GeometricRelationship(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Geometric Constructions: Justifications")){
			JustifyConstruction newProblem = new JustifyConstruction(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Geometric Constructions")){
			Construction newProblem = new Construction(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Locus: Sketch locus in a coordinate plane")){
			SketchXYLocus newProblem = new SketchXYLocus(diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Locus: Identify the number of points")){
			LocusSolution newProblem = new LocusSolution(diff);	
			getProblemData(newProblem);
		}


		else if(string.equals("Coordinate Geometry: Determine the relationship between two lines")){
			DetermineRelationshipOfLines newProblem = new DetermineRelationshipOfLines(diff);	
			getProblemData(newProblem);
		}

		//    	else if(string.equals("Coordinate Geometry: Write the equation of a line that is perp. or par. to a given line")){
		//        	WriteLinearEquation newProblem = new WriteLinearEquation(diff);	
		//        	getProblemData(newProblem);
		//        	}

		else if(string.equals("Coordinate Geometry: Write the equation of a line that is perp. or par. to a given line")){
			EquationParallelPerp newProblem;
			if(Math.random()>.5) newProblem = new EquationParallelPerp(true, false, diff);	
			else newProblem = new EquationParallelPerp(false, false, diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Coordinate Geometry: Write the equation of a line that is perpendicular to a given line")){
			EquationParallelPerp newProblem = new EquationParallelPerp(false, false, diff);	
			getProblemData(newProblem);
		}

		else if(string.equals("Coordinate Geometry: Write the equation of a line that is parallel to a given line")){
			EquationParallelPerp newProblem = new EquationParallelPerp(true, false, diff);	
			getProblemData(newProblem);
		}

		else{
			instructions = "No vaid problem type entered!";
			question = "No vaid problem type entered!";
			answer = "No vaid problem type entered!";  	
		}

	}

	public Problem(String problemLaTaX, String solutionLaTeX){
		keyTheorem = null;
		instructions = "";
		question = "\\begin{array}{l}";
		question += problemLaTaX+"\\end{array}";
		answer = "\\begin{array}{l}";
		answer += problemLaTaX + "\\\\";
		answer += solutionLaTeX;
		answer += "\\end{array}";
		numberOfColumns=1;
		verticalSpacing=40;
		scaleFactor = .9;
		whetherInstructionsAreNeverIncluded = true;    	
		questionImage = toImage(question);
		answerImage = toImage(answer);
	}

	public void getProblemData(Type newProblem){
		keyTheorem =newProblem.getKeyTheorem();
		instructions = newProblem.getInstructions();
		question = "{" + newProblem.getQuestion() + "}";
		answer = "\\begin{array}{l}";
		answer += newProblem.getQuestion() + "\\\\";
		answer += newProblem.getAnswer();
		answer += "\\end{array}";
		//answer = "{" + newProblem.getAnswer() + "}";
		numberOfColumns=newProblem.getNumberOfColumns();
		verticalSpacing=newProblem.getVerticalSpacing();
		scaleFactor = newProblem.getScaleFactor();
		whetherInstructionsAreNeverIncluded = newProblem.getWhetherInstructionsAreNeverIncluded();    	
		questionImage = toImage(question);
		answerImage = toImage(answer);
	}
	

	public void getProblemData(DynamicType newProblem){
		keyTheorem =newProblem.getKeyTheorem();
		instructions = newProblem.getInstructions();
		question = "{" + newProblem.getQuestion() + "}";
		String lineUnderImage="{" + newProblem.getLineUnderImage()+ "}";
		//answer = "{" + newProblem.getAnswer() + "}";
		String[] answerLines = newProblem.getAnswer().split(GRAPHICS_TAG);
		for(int index=0; index<answerLines.length; index++){
			answerLines[index]="\\begin{array}{l}"+answerLines[index]+"\\end{array}";
		}


		numberOfColumns=newProblem.getNumberOfColumns();
		verticalSpacing=newProblem.getVerticalSpacing();
		scaleFactor = newProblem.getScaleFactor();
		whetherInstructionsAreNeverIncluded = newProblem.getWhetherInstructionsAreNeverIncluded();
		questionImage = toImage(question, newProblem.getDynamicImage(),lineUnderImage,new String[0],new ArrayList<BufferedImage>());
		answerImage = toImage(question, newProblem.getDynamicImage(),lineUnderImage,answerLines,newProblem.getAnswerImages());
	}

	public static Term randomTerm(int coefficientMin, int coefficientMax, int numberOfExponents, int exponentMin, int exponentMax){
		//We are now creating the term that will be simplified from the radical
		//it has a coefficient:
		int coefficient = Ops.randomInt(coefficientMin, coefficientMax);//a number between 2 and 10
		//it has a string of variables
		ArrayList<Variable> variables = new ArrayList<Variable>();
		for (int index = 0; index<numberOfExponents; index ++){
			char var = Variable.randVar();// calls the method that selects a random variable, omitting I and O
			int exp = Ops.randomInt(exponentMin, exponentMax);
			variables.add(new Variable (var, exp));
		}
		Term term = new Term(coefficient, variables);//in this case, variables is an ArrayList
		return term;

	}

	public static Term randomTerm(int coefficientMin, int coefficientMax, int numberOfExponents, int exponentMin, int exponentMax, char[] fromThisSelectionOfVars){
		//We are now creating the term that will be simplified from the radical
		//it has a coefficient:
		int coefficient = Ops.randomInt(coefficientMin, coefficientMax);//a number between 2 and 10
		//it has a string of variables
		ArrayList<Variable> variables = new ArrayList<Variable>();
		for (int index = 0; index<numberOfExponents; index ++){
			char var = fromThisSelectionOfVars[Ops.randomInt(0, fromThisSelectionOfVars.length-1)];// calls the method that selects a random variable, omitting I and O
			int exp = Ops.randomInt(exponentMin, exponentMax);
			variables.add(new Variable (var, exp));
		}
		//variables = Term.combineDuplicateVariables(variables);
		Term term = new Term(coefficient, Term.combineDuplicateVariables(variables));//in this case, variables is an ArrayList
		return term;

	}

	//returns string that creates new line when too large
	public static String getLatexLines(String singleLineLatexWithSplitTokens, String splitToken,int width, String textStyle){
		String theQuestion = "\\begin{array}{l}";
		String thisLine = "\\begin{array}{l}";
		String[] parsedInput = singleLineLatexWithSplitTokens.split(splitToken);
		for(int index=0; index<parsedInput.length; index++){
			if(index%2==0){

				String[] parsedWords = parsedInput[index].split(" ");


				theQuestion+="\\"+textStyle+"{";
				thisLine+="\\"+textStyle+"{";
				for(int wordIndex=0;wordIndex<parsedWords.length;wordIndex++){
					theQuestion+=parsedWords[wordIndex]+" ";
					thisLine+=parsedWords[wordIndex]+" ";
					if(wordIndex+1<parsedWords.length){
						String testLine = thisLine+parsedWords[wordIndex+1]+ "}\\end{array}";
						//						System.out.println("thisLine reads, "+thisLine+" and the width is "+toImage(testLine).getWidth());
						if (toImage(testLine).getWidth()>width){
							theQuestion+="}\\\\\\"+textStyle+"{";
							thisLine = "\\begin{array}{l}\\"+textStyle+"{";
						}
					}
				}
				theQuestion+="}";
				thisLine+="}";
			}else{
				String testWidth = thisLine+parsedInput[index]+ "\\end{array}";
				//				System.out.println("thisLine reads, "+testWidth+" and the width is "+toImage(testWidth).getWidth());
				if (toImage(testWidth).getWidth()>width){
					theQuestion+="\\\\"+parsedInput[index]+"\\;";
					thisLine = "\\begin{array}{l}"+parsedInput[index]+"\\;";
				}else{
					theQuestion+=parsedInput[index]+"\\;";
					thisLine+=parsedInput[index]+"\\;";
				}
			}
		}
		theQuestion+="\\end{array}";
		//		thisLine+="\\end{array}";
		return theQuestion;
	}

	public static BufferedImage toImage(String s) {
		while(s.contains("�")){
			System.out.println("CONVERTING"+s);
			s = s.replace("�", "-");    		
		}
		TeXFormula formula = new TeXFormula(s);
		TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, latexFontSize);//used to be 20

		icon.setInsets(new Insets(0, 0, 0, 0));//changes the spacing of the border
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0,0,icon.getIconWidth(),icon.getIconHeight());
		JLabel jl = new JLabel();
		jl.setForeground(new Color(0, 0, 0));
		icon.paintIcon(jl, g2, 0, 0);
		return image;
	}

	/*
	 * this method is called when a DynamicType question is built
	 * It consists of LaTeX question, image, LaTeX line under the question, and LaTeX answer
	 */

	public static BufferedImage toImage(String latex, BufferedImage g, String extraLine, String[] latexAnswer, ArrayList<BufferedImage> answerImages){
		//makes the question from LaTeX
		while(latex.contains("�")){
			System.out.println("FOUND UNKNOWN CHARACTER, REPLACING WITH '-'. CHECK OUTPUT");
			latex =latex.replace("�", "-");
		}
		System.out.println("Problem class toImage "+latex);
		TeXFormula formula = new TeXFormula(latex);
		TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, latexFontSize);
		//makes the extraLine from LaTeX
		TeXFormula formula2 = new TeXFormula(extraLine);
		TeXIcon icon2 = formula2.createTeXIcon(TeXConstants.STYLE_DISPLAY, latexFontSize);
		//makes an arrayList of answerLines
		ArrayList<TeXIcon> answerIcons = new ArrayList<TeXIcon>();
		for(String string : latexAnswer){
			//				System.out.println(string);
			while(string.contains("�")){
				System.out.println("FOUND UNKNOWN CHARACTER, REPLACING WITH '-'. CHECK OUTPUT");
				string = string.replace("�", "-");
			}
			answerIcons.add(new TeXFormula(string).createTeXIcon(TeXConstants.STYLE_DISPLAY, latexFontSize));
		}
		//figures out how the graphic will fit
		int graphicsHeight=0;
		int graphicsWidth=0;
		if(g!=null){
			graphicsHeight=g.getHeight();
			graphicsWidth=g.getWidth();
		}
		//determines the width of the ENTIRE image (basically, entire width is equal to width of largest icon)
		int absoluteWidth=icon.getIconWidth();
		for(TeXIcon answerIcon : answerIcons){
			if(answerIcon.getIconWidth()>icon.getIconWidth())absoluteWidth=answerIcon.getIconWidth();
		}
		if(graphicsWidth>absoluteWidth)absoluteWidth=graphicsWidth;
		if(icon2.getIconWidth()>absoluteWidth)absoluteWidth=icon2.getIconWidth();
		for(BufferedImage images : answerImages){
			if(images.getWidth()>icon.getIconWidth())absoluteWidth=images.getWidth();
		}
		//set the height to the sum of the heights of EVERYTHING
		int heightOfAnswerObjects=0;
		for(TeXIcon texIcon : answerIcons){
			heightOfAnswerObjects += texIcon.getIconHeight();
		}
		for(BufferedImage answerImage : answerImages){
			heightOfAnswerObjects += answerImage.getHeight();
		}
		int absoluteHeight=icon.getIconHeight()+graphicsHeight+heightOfAnswerObjects;
		if(!extraLine.equals("")){
			absoluteHeight+=icon2.getIconHeight();
		}
		//okay! Image is ready, objects are ready
		int heightTracker=0;
		icon.setInsets(new Insets(0, 0, 0, 0));//changes the spacing of the border
		BufferedImage image = new BufferedImage(absoluteWidth, absoluteHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0,0,absoluteWidth,absoluteHeight);
		//the text
		JLabel jl = new JLabel();
		jl.setForeground(new Color(0, 0, 0));
		//paint the question text
		icon.paintIcon(jl, g2, 0, heightTracker);
		heightTracker+=icon.getIconHeight();
		//the image
		if(g!=null){
			int startImageG = (absoluteWidth-graphicsWidth)/2;
			g2.drawImage(g, null, startImageG, heightTracker);
			heightTracker+=g.getHeight();
		}
		//the line after the image
		if(!extraLine.equals("")){
			JLabel jl2 = new JLabel();
			jl2.setForeground(new Color(0, 0, 0));
			icon2.paintIcon(jl2, g2, 0, heightTracker);
			heightTracker+=icon2.getIconHeight();
		}
		//the answer
		try{
			for(int index= 0; index<answerIcons.size(); index++){
				JLabel jl2 = new JLabel();
				jl2.setForeground(new Color(0, 0, 0));
				answerIcons.get(index).paintIcon(jl2, g2, 0, heightTracker);
				heightTracker+=answerIcons.get(index).getIconHeight();
				int startImageG = (absoluteWidth-answerImages.get(index).getWidth())/2;
				g2.drawImage(answerImages.get(index), null, startImageG, heightTracker);
				heightTracker+=answerImages.get(index).getHeight();
			}

		}catch (Exception e) {
			System.out.println("There was an error when loading the answer. Check the Problem class. For now, answers will not be present in the answer key");
		}

		return image;
	}


	public String getQuestion() {
		return question;
	}

	public String getKeyTheorem(){
		System.out.println("Key theorem = "+keyTheorem);
		return keyTheorem;
	}

	public String getInstructions() {
		return instructions;
	}



	public String getAnswer() {
		return answer;
	}

	public double getScaleFactor() {
		//		return scaleFactor;
		return .33;//usually .35
	}

	public BufferedImage getQuestionImage() {
		return questionImage;
	}

	public BufferedImage getAnswerImage(){
		return answerImage;
	}

	public void makeAnswerImage(String filename) {
		File file = new File(filename);
		try {
			ImageIO.write(answerImage, "png", file.getAbsoluteFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public int getNumberOfColumns() {
		return numberOfColumns;
	}



	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}



	public int getVerticalSpacing() {
		return verticalSpacing;
	}



	public void setVerticalSpacing(int verticalSpacing) {
		this.verticalSpacing = verticalSpacing;
	}


	public int getQuestionNeedsThisMuchExtraSpaceOnTop() {
		return questionImage.getHeight()-40;
		//		return questionNeedsThisMuchExtraSpaceOnTop;
	}



	public int getAnswerNeedsThisMuchExtraSpaceOnTop() {
		return answerImage.getHeight()-40;
		//		return answerNeedsThisMuchExtraSpaceOnTop;
	}

	public boolean getWhetherInstructionsAreNeverIncluded() {
		return whetherInstructionsAreNeverIncluded;
	}

	public static Expression createLinearExpression(int coefMax, int bValueMax, int xValue, int finalValue, String variable) {
		boolean canSatisfyPreferences=false;
		int coef=1;;
		int b=bValueMax+1;

		int attemptNumber=1;
		while(attemptNumber<11 && Math.abs(b)>Math.abs(bValueMax)){
			coef=Ops.randomNotZero(-coefMax, coefMax);
			b=finalValue-coef*xValue;
			attemptNumber++;
		}

		if(Math.abs(b)>Math.abs(bValueMax)){
			System.out.println("Problem Class: the method \"createLinearExpression\" " +
					"could not satisfy your request of generating a linear expression that has an x-value of "+xValue+
					", coefficient less than "+coefMax+" and bValue less than "+bValueMax+" after 10 attempts. The value of b is instead set to "+b);
		}
		Term[] terms;
		if(b!=0){
			terms=new Term[2];
			if(b>0&&coef<0){
				terms[0]=new Term(b);
				terms[1]=new Term(coef,variable);
			}else{
				terms[0]=new Term(coef,variable);
				terms[1]=new Term(b);
			}

		}else{
			terms=new Term[1];
			terms[0]=new Term(coef,variable);
		}
		return new Expression(terms);
	}

}
