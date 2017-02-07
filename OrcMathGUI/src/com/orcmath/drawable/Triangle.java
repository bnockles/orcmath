package com.orcmath.drawable;

import org.scilab.forge.jlatexmath.VRowAtom;

import com.orcmath.objects.Ops;

public class Triangle {

	
	public static final int DEFAULT_SIZE = 8;//pixels of standard triangle
	
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
		if(sideb < sidea){
			this.sideA = sideb;
			this.sideB = sidea;
		}else{
			this.sideB = sideb;			
		}
		if(sidec < sidea){
			this.sideA = sidec;
			this.sideC = sidea;
		}else{
			this.sideC = sidec;			
		}
		
		
		this.angleA = Ops.lawOfCosines(sideA,sideB,sideC);
		this.angleB = Ops.lawOfCosines(sideB,sideA,sideC);
		this.angleC = Ops.lawOfCosines(sideC,sideA,sideC);
		
		
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
		return "Triangle with side lengths "+sideA+", "+sideB+", "+sideC+", and angles: "+(angleA*180/Math.PI)+", "+(angleB*180/Math.PI)+", "+(angleC*180/Math.PI) ;
	}
	
	private void setPoints(){
		vertexA = new CoordinatePoint(0, size);
		double abSlope = 1/Math.tan(angleA/2);
		double bIncenterSlope = 1/Math.tan(angleA/2+angleB/2);
		double bX = (vertexA.getyCoordinate()-abSlope*vertexA.getxCoordinate())/(bIncenterSlope-abSlope);
		vertexB = new CoordinatePoint(bX, bX*bIncenterSlope);
		double acSlope = -1/Math.tan(angleA/2);
		double cIncenterSlope = -1/Math.tan(angleA/2+angleC/2);
		double cX = (vertexA.getyCoordinate()-acSlope*vertexA.getxCoordinate())/(cIncenterSlope-acSlope);
		vertexC = new CoordinatePoint(cX, cX*cIncenterSlope);
//		dilateToFill();
		segmentA = new CoordinateSegment(vertexB, vertexC);
		segmentB = new CoordinateSegment(vertexA, vertexC);
		segmentC = new CoordinateSegment(vertexB, vertexA);
	}
	
	/**
	 * Dialates the triangle so that the result fills the available space
	 */
	private void dilateToFill() {
		double bottomEdge = -size;
		double leftEdge = -size;
		double rightEdge = size;
		while(1.1*(vertexB.getxCoordinate())>leftEdge && 1.1*(vertexC.getxCoordinate())<rightEdge && 1.1*(vertexB.getyCoordinate()-vertexA.getyCoordinate()+vertexA.getyCoordinate())>-bottomEdge && 1.1*(vertexC.getyCoordinate()-vertexA.getyCoordinate()+vertexA.getyCoordinate())>-bottomEdge){
			vertexB = new CoordinatePoint(1.1*(vertexB.getxCoordinate()), 1.1*(vertexB.getyCoordinate()-vertexA.getyCoordinate())+vertexA.getyCoordinate());
			vertexC = new CoordinatePoint(1.1*(vertexC.getxCoordinate()), 1.1*(vertexC.getyCoordinate()-vertexA.getyCoordinate())+vertexA.getyCoordinate());
			System.out.println(vertexB);
			System.out.println(vertexC);
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

}
