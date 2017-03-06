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
package com.orcmath.drawable;

import java.util.Arrays;

import org.scilab.forge.jlatexmath.VRowAtom;

import com.orcmath.drawabletype.DrawableOps;
import com.orcmath.objects.Ops;

public class Triangle {

	
	public static final int DEFAULT_SIZE = 7;//pixels of standard triangle
	
	private double sideA;
	private double sideB;
	private double sideC;
	private double angleA;
	private double angleB;
	private double angleC;
	private CoordinatePoint incenter;
	private CoordinatePoint vertexA;
	private CoordinatePoint vertexB;
	private CoordinatePoint vertexC;
	private CoordinateSegment segmentA;
	private CoordinateSegment segmentB;
	private CoordinateSegment segmentC;
	private int size;
	

	public Triangle(double sidea, double sideb, double sidec) {
		
		this.sideA = sidea;
		this.sideB = sideb;	
		this.sideC = sidec;	
		
		
		
		this.angleA = Ops.lawOfCosines(sideA,sideB,sideC);
		this.angleB = Ops.lawOfCosines(sideB,sideA,sideC);
		this.angleC = Ops.lawOfCosines(sideC,sideA,sideB);
		
		
		incenter = new CoordinatePoint(0, 0);
		size = DEFAULT_SIZE;
		//randomly generates a location for each vertex
		setPoints();
	}
	
	
	public double getAngleA() {
		return angleA;
	}



	public double getAngleB() {
		return angleB;
	}



	public double getAngleC() {
		return angleC;
	}



	public CoordinatePoint getIncenter() {
		return incenter;
	}



	public CoordinatePoint getVertexA() {
		return vertexA;
	}



	public CoordinatePoint getVertexB() {
		return vertexB;
	}



	public CoordinatePoint getVertexC() {
		return vertexC;
	}


	public CoordinateSegment getShortestSide(){
		CoordinateSegment s = segmentA;
		if(segmentB.getDecimalLength() < s.getDecimalLength())s = segmentB;
		if(segmentC.getDecimalLength() < s.getDecimalLength())s = segmentC;
		return s;
	}
	
	/**
	 * returns the smallest angle vertex, which is also the center of dilating the triangle (guarantees triangle fits in grid)
	 * @return
	 */
	public CoordinatePoint getSmallestAngleVertex(){
		CoordinatePoint s = vertexA;
		if(segmentB.getDecimalLength() < segmentA.getDecimalLength() && segmentB.getDecimalLength() < segmentC.getDecimalLength())s = vertexB;
		if(segmentC.getDecimalLength() < segmentA.getDecimalLength() && segmentC.getDecimalLength() < segmentB.getDecimalLength())s = vertexC;
		return s;
	}
	
	public CoordinateSegment getLongestSide(){
		CoordinateSegment s = segmentA;
		if(segmentB.getDecimalLength() > s.getDecimalLength())s = segmentB;
		if(segmentC.getDecimalLength() > s.getDecimalLength())s = segmentC;
		return s;
	}

	public CoordinateSegment getSegmentA() {
		return segmentA;
	}



	public CoordinateSegment getSegmentB() {
		return segmentB;
	}



	public CoordinateSegment getSegmentC() {
		return segmentC;
	}



	public int getSize() {
		return size;
	}



	public String toString(){
		return "Triangle with coordinates "+vertexA+", "+vertexB+","+vertexC+", side lengths "+sideA+", "+sideB+", "+sideC+", and angles: "+(angleA*180/Math.PI)+", "+(angleB*180/Math.PI)+", "+(angleC*180/Math.PI) ;
	}
	
	private void setPoints(){
		
		CoordinatePoint[] vertices = {vertexA,vertexB,vertexC};
		//0 is top, 1 is bottom left, 2 is bottom right
		int[] indices = {0,1,2};

		vertices[0] = new CoordinatePoint(0, size);
		double smallestAngle = this.angleA;
		double other1 = this.angleB;
		double other2 = this.angleC;

		//A' is A, B' is B, C' is C
		
		if(this.angleB < this.angleA){
			smallestAngle = angleB;
			other1 = angleA;
			coordSwap(indices,0,1);
			//A' is B, B' is A
		}
		if(this.angleC < smallestAngle){
			double temp = smallestAngle;
			smallestAngle = angleC;
			angleC = temp;
			coordSwap(indices, 0, 2);
		}
		
		double abSlope = 1/Math.tan(smallestAngle/2);
		double bIncenterSlope = 1/Math.tan(smallestAngle/2+other1/2);
		double bX = (vertices[0].getyCoordinate()-abSlope*vertices[0].getxCoordinate())/(bIncenterSlope-abSlope);
		vertices[1] = new CoordinatePoint(bX, bX*bIncenterSlope);
		double acSlope = -1/Math.tan(smallestAngle/2);
		double cIncenterSlope = -1/Math.tan(smallestAngle/2+other2/2);
		double cX = (vertices[0].getyCoordinate()-acSlope*vertices[0].getxCoordinate())/(cIncenterSlope-acSlope);
		vertices[2] = new CoordinatePoint(cX, cX*cIncenterSlope);
		
		dilateToFill(vertices);
		
		vertexA = vertices[indices[0]];
		vertexB = vertices[indices[1]];
		vertexC = vertices[indices[2]];
		
		double angle = Math.random()*2*Math.PI;
		vertexA = DrawableOps.rotate(vertexA, angle);
		vertexB = DrawableOps.rotate(vertexB, angle);
		vertexC = DrawableOps.rotate(vertexC, angle);
		
		
			segmentA = new CoordinateSegment(vertexB, vertexC);
			segmentB = new CoordinateSegment(vertexA, vertexC);
			segmentC = new CoordinateSegment(vertexB, vertexA);
			

	}
	
	private void coordSwap(int[] points, int i, int j){
		int temp = points[i];
		points[i]=points[j];
		points[j] = temp;
	}
	
	/**
	 * Dialates the triangle so that the result fills the available space
	 */
	private void dilateToFill(CoordinatePoint[] vertices) {
		CoordinatePoint vertexA = vertices[0];
		CoordinatePoint vertexB = vertices[1];
		CoordinatePoint vertexC = vertices[2];
		
		double bottomEdge = -size;
		double leftEdge = -size;
		double rightEdge = size;
		while(1.1*(vertexB.getxCoordinate())>leftEdge && 1.1*(vertexC.getxCoordinate())<rightEdge && 1.1*(vertexB.getyCoordinate()-vertexA.getyCoordinate())+vertexA.getyCoordinate()>bottomEdge && 1.1*(vertexC.getyCoordinate()-vertexA.getyCoordinate())+vertexA.getyCoordinate()>bottomEdge){
			vertexB = new CoordinatePoint(1.1*(vertexB.getxCoordinate()), 1.1*(vertexB.getyCoordinate()-vertexA.getyCoordinate())+vertexA.getyCoordinate());
			vertexC = new CoordinatePoint(1.1*(vertexC.getxCoordinate()), 1.1*(vertexC.getyCoordinate()-vertexA.getyCoordinate())+vertexA.getyCoordinate());
		}
	}
	
	

	public void setSize(int size){
		this.size = size;
		setPoints();
	}

	public double getSideA() {
		return sideA;
	}
	
	public double getSideB() {
		return sideB;
	}
	
	public double getSideC() {
		return sideC;
	}


	public CoordinateSegment getSide(int sideIndex) {
		if(sideIndex == 0)return segmentA;
		if(sideIndex == 1)return segmentB;
		if(sideIndex == 2)return segmentC;
		return null;
	}


	public CoordinatePoint getVertex(int sideIndex) {
		if(sideIndex == 0)return vertexA;
		if(sideIndex == 1)return vertexB;
		if(sideIndex == 2)return vertexC;
		return null;
	}

}
