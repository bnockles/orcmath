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
package com.orcmath.objects;

public class QuadrilateralGraphic {

	String imageAddress;
	int type;
	String typeName;
	boolean diagonalsDrawn;
	String name;
	
	String[] congruentSide1;
	String[] congruentSide2;
	int congruentSideVariation;
	String[] congruentAngle1;
	String[] congruentAngle2;
	int congruentAngleVariation;
	String[] supplementaryAngle1;
	String[] supplementaryAngle2;
	int supplementaryAngleVariation;
	boolean hasAPairOfSupplementaryAngles;
	boolean hasAPairOfComplimentaryAngles;
	String[] complementaryAngle1;
	String[] complementaryAngle2;
	int complementaryAngleVariation;
	boolean hasARightTriangle;
	String[] hypotenuse;
	String[] sideA;
	String[] sideB;
	int rightTriangleVariation;
	
	
	
	public static int TRAPEZOID = 0;
	public static int KITE = 1;
	public static int PARALLELOGRAM = 2;
	public static int RHOMBUS = 3;
	public static int RECTANGLE = 4;
	
	//no diagonals 
	private static String TRAPEZOID_1 = "Data/Worksheets/Quadrilaterals/Trapezoid.png";
		private static String[] TRAPEZOID_1_CONGRUENT_SIDE_1 = {"TP","RA"};
		private static String[] TRAPEZOID_1_CONGRUENT_SIDE_2 = {"RA","TP"};
		private static String[] TRAPEZOID_1_CONGRUENT_ANGLE_1 = {"PTR","TPA"};
		private static String[] TRAPEZOID_1_CONGRUENT_ANGLE_2 = {"TRA","RAP"};
		private static String[] TRAPEZOID_1_SUPPLEMENTARY_ANGLE_1 = {"PTR","TRA"};
		private static String[] TRAPEZOID_1_SUPPLEMENTARY_ANGLE_2 = {"TPA","RAP"};
	private static String PARALLELOGRAM_1 = "Data/Worksheets/Quadrilaterals/ParallelogramWXYZ.png";
		private static String[] PARALLELOGRAM_1_CONGRUENT_SIDE_1 = {"WX","XY"};
		private static String[] PARALLELOGRAM_1_CONGRUENT_SIDE_2 = {"YZ","WZ"};
		private static String[] PARALLELOGRAM_1_CONGRUENT_ANGLE_1 = {"WXY","XYZ"};
		private static String[] PARALLELOGRAM_1_CONGRUENT_ANGLE_2 = {"WZY","XWZ"};
		private static String[] PARALLELOGRAM_1_SUPPLEMENTARY_ANGLE_1 = {"ZWX","WXY","XYZ", "YZW"};
		private static String[] PARALLELOGRAM_1_SUPPLEMENTARY_ANGLE_2 = {"WZY","XWZ","WXY","ZYX"};
	private static String PARALLELOGRAM_2 = "Data/Worksheets/Quadrilaterals/ParallelogramWARP.png";
		private static String[] PARALLELOGRAM_2_CONGRUENT_SIDE_1 = {"WA","AR"};
		private static String[] PARALLELOGRAM_2_CONGRUENT_SIDE_2 = {"PR","WP"};
		private static String[] PARALLELOGRAM_2_CONGRUENT_ANGLE_1 = {"PWA","WPR"};
		private static String[] PARALLELOGRAM_2_CONGRUENT_ANGLE_2 = {"PRA","WAR"};
		private static String[] PARALLELOGRAM_2_SUPPLEMENTARY_ANGLE_1 = {"WAR","ARP","RPW", "PWA"};
		private static String[] PARALLELOGRAM_2_SUPPLEMENTARY_ANGLE_2 = {"PWA","WAR","ARP","RPW"};

	
	//diagonals
	private static String PARALLELOGRAM_3 = "Data/Worksheets/Quadrilaterals/ParallelogramWithDiagonals.png";	
		private static String[] PARALLELOGRAM_3_CONGRUENT_SIDE_1 = {"AB","BC","AP","DP"};
		private static String[] PARALLELOGRAM_3_CONGRUENT_SIDE_2 = {"DC","AD","PC","PB"};
		private static String[] PARALLELOGRAM_3_CONGRUENT_ANGLE_1 = {"ADC","DAB","ADB","DAC","BAC","BDC"};
		private static String[] PARALLELOGRAM_3_CONGRUENT_ANGLE_2 = {"ABC","BCD","DBC","ACB","ACD","ABD"};
		private static String[] PARALLELOGRAM_3_SUPPLEMENTARY_ANGLE_1 = {"ABC","BCD","CDA", "DAB"};
		private static String[] PARALLELOGRAM_3_SUPPLEMENTARY_ANGLE_2 = {"DAB","ABC","BCD","CDA"};
	private static String RHOMBUS_1 = "Data/Worksheets/Quadrilaterals/RhombusJKLMWithDiagonals.png";
		private static String[] RHOMBUS_1_CONGRUENT_SIDE_1 = {"JK","KL","JN","MN"};
		private static String[] RHOMBUS_1_CONGRUENT_SIDE_2 = {"LM","JM","NL","NK"};
		private static String[] RHOMBUS_1_CONGRUENT_ANGLE_1 = {"MJN","MLN","JMN","JKN","JMN","LMN"};
		private static String[] RHOMBUS_1_CONGRUENT_ANGLE_2 = {"KJN","KLN","LMN","LKN","JKN","LKN"};
		private static String[] RHOMBUS_1_SUPPLEMENTARY_ANGLE_1 = {"JKL","KLM","LMJ", "MJK"};
		private static String[] RHOMBUS_1_SUPPLEMENTARY_ANGLE_2 = {"KLM","LMJ","MJK","JKL"};
		private static String[] RHOMBUS_1_COMPLEMENTARY_ANGLE_1 = {"JMN","NJK","LKN", "MLN"};
		private static String[] RHOMBUS_1_COMPLEMENTARY_ANGLE_2 = {"MJN","JKN","KLN","LMN"};
		private static String[] RHOMBUS_1_HYPOTENUSE ={"JK","KL","LM","JM"};
		private static String[] RHOMBUS_1_A ={"JN","KN","LN","MN"};
		private static String[] RHOMBUS_1_B ={"KN","LN","MN","JN"};
	private static String RHOMBUS_2 = "Data/Worksheets/Quadrilaterals/RhombusWithDiagonals.png";
		private static String[] RHOMBUS_2_CONGRUENT_SIDE_1 = {"MA","HT","HQ","MQ"};
		private static String[] RHOMBUS_2_CONGRUENT_SIDE_2 = {"MH","AT","QA","QT"};
		private static String[] RHOMBUS_2_CONGRUENT_ANGLE_1 = {"MHT","HMA","MHQ","MAQ","HMQ","HTQ"};
		private static String[] RHOMBUS_2_CONGRUENT_ANGLE_2 = {"MAT","HTA","THQ","TAQ","AMQ","ATQ"};
		private static String[] RHOMBUS_2_SUPPLEMENTARY_ANGLE_1 = {"MAT","ATH","THM", "HMA"};
		private static String[] RHOMBUS_2_SUPPLEMENTARY_ANGLE_2 = {"HMA","MAT","ATH","THM"};
		private static String[] RHOMBUS_2_COMPLEMENTARY_ANGLE_1 = {"MHQ","THQ","TAQ", "AMQ"};
		private static String[] RHOMBUS_2_COMPLEMENTARY_ANGLE_2 = {"QMH","HTQ","ATQ","MAQ"};
		private static String[] RHOMBUS_2_HYPOTENUSE ={"MA","AT","HT","HM"};
		private static String[] RHOMBUS_2_A ={"MQ","AQ","TQ","HQ"};
		private static String[] RHOMBUS_2_B ={"AQ","TQ","HQ","MQ"};
	private static String RHOMBUS_3 = "Data/Worksheets/Quadrilaterals/Rhombus3.png";
		private static String[] RHOMBUS_3_CONGRUENT_SIDE_1 = {"HS","RS","RH","RB"};
		private static String[] RHOMBUS_3_CONGRUENT_SIDE_2 = {"SB","SM","MB","HM"};
		private static String[] RHOMBUS_3_CONGRUENT_ANGLE_1 = {"HRB","RBM","RBS","HRS","HMS","RHS"};
		private static String[] RHOMBUS_3_CONGRUENT_ANGLE_2 = {"HMB","RHM","MBS","BRS","BMS","MHS"};
		private static String[] RHOMBUS_3_SUPPLEMENTARY_ANGLE_1 = {"RHM","HMB","MBR", "BRH"};
		private static String[] RHOMBUS_3_SUPPLEMENTARY_ANGLE_2 = {"HMB","MBR", "BRH","RHM"};
		private static String[] RHOMBUS_3_COMPLEMENTARY_ANGLE_1 = {"HRS","RBS","BMS", "SHM"};
		private static String[] RHOMBUS_3_COMPLEMENTARY_ANGLE_2 = {"RHS","SRB","SBM","SMH"};
		private static String[] RHOMBUS_3_HYPOTENUSE ={"HM","BM","BR","HR"};
		private static String[] RHOMBUS_3_A ={"HS","MS","BS","RS"};
		private static String[] RHOMBUS_3_B ={"MS","BS","RS","HS"};
	private static String RHOMBUS_4 = "Data/Worksheets/Quadrilaterals/Rhombus4.png";
	private static String KITE_1 = "Data/Worksheets/Quadrilaterals/Kite1.png";
		private static String[] KITE_1_CONGRUENT_SIDE_1 = {"AB","LB"};
		private static String[] KITE_1_CONGRUENT_SIDE_2 = {"BL","BA"};
		private static String[] KITE_1_CONGRUENT_ANGLE_1 = {"SAI","ASI","LSI"};
		private static String[] KITE_1_CONGRUENT_ANGLE_2 = {"SLI","LSI","ASI"};
		private static String[] KITE_1_COMPLEMENTARY_ANGLE_1 = {"SAB","BIA","BIL", "LSB"};
		private static String[] KITE_1_COMPLEMENTARY_ANGLE_2 = {"ASB","BAI","BLI","SLB"};
		private static String[] KITE_1_HYPOTENUSE ={"AS","AI","IL","LS"};
		private static String[] KITE_1_A ={"AB","BI","BL","BS"};
		private static String[] KITE_1_B ={"BS","AI","BI","BL"};
	private static String KITE_2 = "Data/Worksheets/Quadrilaterals/KiteKITEWithDiagonals.png";
		private static String[] KITE_2_CONGRUENT_SIDE_1 = {"AE","AI"};
		private static String[] KITE_2_CONGRUENT_SIDE_2 = {"AI","AE"};
		private static String[] KITE_2_CONGRUENT_ANGLE_1 = {"KET","EKT","ETK"};
		private static String[] KITE_2_CONGRUENT_ANGLE_2 = {"KIT","IKT","ITK"};
		private static String[] KITE_2_COMPLEMENTARY_ANGLE_1 = {"KEA","KIA","ITA", "ATE"};
		private static String[] KITE_2_COMPLEMENTARY_ANGLE_2 = {"AKE","AKI","TIA","TEA"};
		private static String[] KITE_2_HYPOTENUSE ={"KI","IT","ET","EK"};
		private static String[] KITE_2_A ={"KA","AI","AT","AE"};
		private static String[] KITE_2_B ={"AI","AT","AE","KA"};
	private static String KITE_3 = "Data/Worksheets/Quadrilaterals/Kite3.png";
	private static String TRAPEZOID_2 = "Data/Worksheets/Quadrilaterals/TrapezoidABCDWithDiagonals.png";
		private static String[] TRAPEZOID_2_CONGRUENT_SIDE_1 = {"AC","DB"};
		private static String[] TRAPEZOID_2_CONGRUENT_SIDE_2 = {"BD","CA"};
		private static String[] TRAPEZOID_2_CONGRUENT_ANGLE_1 = {"ADC","DAB","CBA","CDA"};
		private static String[] TRAPEZOID_2_CONGRUENT_ANGLE_2 = {"BCD","ABC","BAD","BCD"};
		private static String[] TRAPEZOID_2_SUPPLEMENTARY_ANGLE_1 = {"ADC","CBA"};
		private static String[] TRAPEZOID_2_SUPPLEMENTARY_ANGLE_2 = {"DAB","BCD"};
	private static String TRAPEZOID_3 = "Data/Worksheets/Quadrilaterals/TrapezoidWithDiagonals.png";
		private static String[] TRAPEZOID_3_CONGRUENT_SIDE_1 = {"TA","PR"};
		private static String[] TRAPEZOID_3_CONGRUENT_SIDE_2 = {"RP","AT"};
	private static String RECTANGLE_1 = "Data/Worksheets/Quadrilaterals/RectangleABCD.png";
		private static String[] RECTANGLE_1_CONGRUENT_SIDE_1 = {"AP","DP","AP","AC"};
		private static String[] RECTANGLE_1_CONGRUENT_SIDE_2 = {"PC","PB","PC","BD"};
		private static String[] RECTANGLE_1_CONGRUENT_ANGLE_1 = {"PAB","PAD","PBC","PCD","BAP","PBA"};
		private static String[] RECTANGLE_1_CONGRUENT_ANGLE_2 = {"PBA","PDA","PCB","PDC","PCD","PDC"};
		private static String[] RECTANGLE_1_HYPOTENUSE ={"AC","BD"};
		private static String[] RECTANGLE_1_A ={"AD","BC"};
		private static String[] RECTANGLE_1_B ={"CD","CD"};


	public QuadrilateralGraphic(int type, boolean diagonalsDrawn){
		this.type=type;
		this.diagonalsDrawn=diagonalsDrawn;
		hasARightTriangle=false;
		if(type==TRAPEZOID){
			typeName="trapezoid";
			hasAPairOfComplimentaryAngles=false;
			hasAPairOfSupplementaryAngles=true;

		}
		else if(type==KITE){
			typeName="kite"; 
			hasAPairOfComplimentaryAngles=true;
			hasAPairOfSupplementaryAngles=false;
		}
		else if(type==PARALLELOGRAM){
			typeName="parallelogram";
			hasAPairOfComplimentaryAngles=false;
			hasAPairOfSupplementaryAngles=true;
		}
		else if(type==RHOMBUS){
			typeName="rhombus";
			hasAPairOfComplimentaryAngles=true;
			hasAPairOfSupplementaryAngles=true;
		}
		else if(type==RECTANGLE){
			typeName="rectangle";
			hasAPairOfComplimentaryAngles=false;
			hasAPairOfSupplementaryAngles=false;
		}
		selectAnImage();
	}
	
	public void selectAnImage(){
		double chooser = Math.random();
		if(type==TRAPEZOID){
			if (diagonalsDrawn){
				if(chooser<.5){
					imageAddress=TRAPEZOID_2;
					name="TRAP";
					congruentSide1=TRAPEZOID_2_CONGRUENT_SIDE_1;
					congruentSide2=TRAPEZOID_2_CONGRUENT_SIDE_2;
					congruentAngle1=TRAPEZOID_2_CONGRUENT_ANGLE_1;
					congruentAngle2=TRAPEZOID_2_CONGRUENT_ANGLE_2;
					supplementaryAngle1=TRAPEZOID_2_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=TRAPEZOID_2_SUPPLEMENTARY_ANGLE_2;
					complementaryAngle1=null;
					complementaryAngle2=null;
				}
				else{
					imageAddress=TRAPEZOID_3;
					name="TRAP";
					congruentSide1=TRAPEZOID_3_CONGRUENT_SIDE_1;
					congruentSide2=TRAPEZOID_3_CONGRUENT_SIDE_2;
					congruentAngle1=TRAPEZOID_1_CONGRUENT_ANGLE_1;
					congruentAngle2=TRAPEZOID_1_CONGRUENT_ANGLE_2;
					supplementaryAngle1=TRAPEZOID_1_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=TRAPEZOID_1_SUPPLEMENTARY_ANGLE_2;
					complementaryAngle1=null;
					complementaryAngle2=null;
				}
			}
			else{
				imageAddress=TRAPEZOID_1;
				name="TRAP";
				congruentSide1=TRAPEZOID_1_CONGRUENT_SIDE_1;
				congruentSide2=TRAPEZOID_1_CONGRUENT_SIDE_2;
				congruentAngle1=TRAPEZOID_1_CONGRUENT_ANGLE_1;
				congruentAngle2=TRAPEZOID_1_CONGRUENT_ANGLE_2;
				supplementaryAngle1=TRAPEZOID_1_SUPPLEMENTARY_ANGLE_1;
				supplementaryAngle2=TRAPEZOID_1_SUPPLEMENTARY_ANGLE_2;
				complementaryAngle1=null;
				complementaryAngle2=null;
			}
		}
		else if(type==KITE){//all kites have the diagonals drawn
			hasARightTriangle=true;
			if(chooser<.33){
				imageAddress=KITE_2;
				name="KITE";
				congruentSide1=KITE_2_CONGRUENT_SIDE_1;
				congruentSide2=KITE_2_CONGRUENT_SIDE_2;
				congruentAngle1=KITE_2_CONGRUENT_ANGLE_1;
				congruentAngle2=KITE_2_CONGRUENT_ANGLE_2;
				supplementaryAngle1=null;
				supplementaryAngle2=null;
				complementaryAngle1=KITE_2_COMPLEMENTARY_ANGLE_1;
				complementaryAngle2=KITE_2_COMPLEMENTARY_ANGLE_2;
				hypotenuse=KITE_2_HYPOTENUSE;
				sideA=KITE_2_A;
				sideB=KITE_2_B;				
			}
			else if (chooser<.67){
				imageAddress=KITE_1;
				name="SAIL";
				congruentSide1=KITE_1_CONGRUENT_SIDE_1;
				congruentSide2=KITE_1_CONGRUENT_SIDE_2;
				congruentAngle1=KITE_1_CONGRUENT_ANGLE_1;
				congruentAngle2=KITE_1_CONGRUENT_ANGLE_2;
				supplementaryAngle1=null;
				supplementaryAngle2=null;
				complementaryAngle1=KITE_1_COMPLEMENTARY_ANGLE_1;
				complementaryAngle2=KITE_1_COMPLEMENTARY_ANGLE_2;	
				hypotenuse=KITE_1_HYPOTENUSE;
				sideA=KITE_1_A;
				sideB=KITE_1_B;
			}
			else{
				imageAddress=KITE_3;
				name="KITE";
				congruentSide1=KITE_2_CONGRUENT_SIDE_1;
				congruentSide2=KITE_2_CONGRUENT_SIDE_2;
				congruentAngle1=KITE_2_CONGRUENT_ANGLE_1;
				congruentAngle2=KITE_2_CONGRUENT_ANGLE_2;
				supplementaryAngle1=null;
				supplementaryAngle2=null;
				complementaryAngle1=KITE_2_COMPLEMENTARY_ANGLE_1;
				complementaryAngle2=KITE_2_COMPLEMENTARY_ANGLE_2;	
				hypotenuse=KITE_2_HYPOTENUSE;
				sideA=KITE_2_A;
				sideB=KITE_2_B;
			}
		}
		else if(type==PARALLELOGRAM){
			if (diagonalsDrawn){
				if(chooser<.5){
					imageAddress=PARALLELOGRAM_3;
					name="ABCD";
					congruentSide1=PARALLELOGRAM_3_CONGRUENT_SIDE_1;
					congruentSide2=PARALLELOGRAM_3_CONGRUENT_SIDE_2;
					congruentAngle1=PARALLELOGRAM_3_CONGRUENT_ANGLE_1;
					congruentAngle2=PARALLELOGRAM_3_CONGRUENT_ANGLE_2;				
					supplementaryAngle1=PARALLELOGRAM_3_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=PARALLELOGRAM_3_SUPPLEMENTARY_ANGLE_2;	
					complementaryAngle1=null;
					complementaryAngle2=null;
				}
				else{
					imageAddress=PARALLELOGRAM_3;//change this once another graphic is made
					name="ABCD";
					congruentSide1=PARALLELOGRAM_3_CONGRUENT_SIDE_1;
					congruentSide2=PARALLELOGRAM_3_CONGRUENT_SIDE_2;
					congruentAngle1=PARALLELOGRAM_3_CONGRUENT_ANGLE_1;
					congruentAngle2=PARALLELOGRAM_3_CONGRUENT_ANGLE_2;				
					supplementaryAngle1=PARALLELOGRAM_3_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=PARALLELOGRAM_3_SUPPLEMENTARY_ANGLE_2;	
					complementaryAngle1=null;
					complementaryAngle2=null;
				}
			}
			else{
				if(chooser<.5){
					imageAddress=PARALLELOGRAM_1;
					name="WXYZ";
					congruentSide1=PARALLELOGRAM_1_CONGRUENT_SIDE_1;
					congruentSide2=PARALLELOGRAM_1_CONGRUENT_SIDE_2;
					congruentAngle1=PARALLELOGRAM_1_CONGRUENT_ANGLE_1;
					congruentAngle2=PARALLELOGRAM_1_CONGRUENT_ANGLE_2;				
					supplementaryAngle1=PARALLELOGRAM_1_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=PARALLELOGRAM_1_SUPPLEMENTARY_ANGLE_2;	
					complementaryAngle1=null;
					complementaryAngle2=null;
					
				}
				else{
					imageAddress=PARALLELOGRAM_2;
					name="WARP";
					congruentSide1=PARALLELOGRAM_2_CONGRUENT_SIDE_1;
					congruentSide2=PARALLELOGRAM_2_CONGRUENT_SIDE_2;
					congruentAngle1=PARALLELOGRAM_2_CONGRUENT_ANGLE_1;
					congruentAngle2=PARALLELOGRAM_2_CONGRUENT_ANGLE_2;				
					supplementaryAngle1=PARALLELOGRAM_2_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=PARALLELOGRAM_2_SUPPLEMENTARY_ANGLE_2;	
					complementaryAngle1=null;
					complementaryAngle2=null;
					
				}
			}
		}
		else if(type==RHOMBUS){
			if (diagonalsDrawn){
				hasARightTriangle=true;
				if(chooser<.25){
					imageAddress=RHOMBUS_1;
					name="JKLM";
					congruentSide1=RHOMBUS_1_CONGRUENT_SIDE_1;
					congruentSide2=RHOMBUS_1_CONGRUENT_SIDE_2;
					congruentAngle1=RHOMBUS_1_CONGRUENT_ANGLE_1;
					congruentAngle2=RHOMBUS_1_CONGRUENT_ANGLE_2;				
					supplementaryAngle1=RHOMBUS_1_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=RHOMBUS_1_SUPPLEMENTARY_ANGLE_2;	
					complementaryAngle1=RHOMBUS_1_COMPLEMENTARY_ANGLE_1;
					complementaryAngle2=RHOMBUS_1_COMPLEMENTARY_ANGLE_2;
					hypotenuse=RHOMBUS_1_HYPOTENUSE;
					sideA=RHOMBUS_1_A;
					sideB=RHOMBUS_1_B;
				}
				else if(chooser<.5){
					imageAddress=RHOMBUS_3;
					name="RHMB";
					congruentSide1=RHOMBUS_3_CONGRUENT_SIDE_1;
					congruentSide2=RHOMBUS_3_CONGRUENT_SIDE_2;
					congruentAngle1=RHOMBUS_3_CONGRUENT_ANGLE_1;
					congruentAngle2=RHOMBUS_3_CONGRUENT_ANGLE_2;				
					supplementaryAngle1=RHOMBUS_3_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=RHOMBUS_3_SUPPLEMENTARY_ANGLE_2;	
					complementaryAngle1=RHOMBUS_3_COMPLEMENTARY_ANGLE_1;
					complementaryAngle2=RHOMBUS_3_COMPLEMENTARY_ANGLE_2;
					hypotenuse=RHOMBUS_3_HYPOTENUSE;
					sideA=RHOMBUS_3_A;
					sideB=RHOMBUS_3_B;
				}
				else if(chooser<.75){
					imageAddress=RHOMBUS_4;
					name="RHMB";
					congruentSide1=RHOMBUS_3_CONGRUENT_SIDE_1;
					congruentSide2=RHOMBUS_3_CONGRUENT_SIDE_2;
					congruentAngle1=RHOMBUS_3_CONGRUENT_ANGLE_1;
					congruentAngle2=RHOMBUS_3_CONGRUENT_ANGLE_2;				
					supplementaryAngle1=RHOMBUS_3_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=RHOMBUS_3_SUPPLEMENTARY_ANGLE_2;	
					complementaryAngle1=RHOMBUS_3_COMPLEMENTARY_ANGLE_1;
					complementaryAngle2=RHOMBUS_3_COMPLEMENTARY_ANGLE_2;
					hypotenuse=RHOMBUS_3_HYPOTENUSE;
					sideA=RHOMBUS_3_A;
					sideB=RHOMBUS_3_B;
				}
				else{
					imageAddress=RHOMBUS_2;
					name="MATH";
					congruentSide1=RHOMBUS_2_CONGRUENT_SIDE_1;
					congruentSide2=RHOMBUS_2_CONGRUENT_SIDE_2;
					congruentAngle1=RHOMBUS_2_CONGRUENT_ANGLE_1;
					congruentAngle2=RHOMBUS_2_CONGRUENT_ANGLE_2;				
					supplementaryAngle1=RHOMBUS_2_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=RHOMBUS_2_SUPPLEMENTARY_ANGLE_2;	
					complementaryAngle1=RHOMBUS_2_COMPLEMENTARY_ANGLE_1;
					complementaryAngle2=RHOMBUS_2_COMPLEMENTARY_ANGLE_2;
					hypotenuse=RHOMBUS_2_HYPOTENUSE;
					sideA=RHOMBUS_2_A;
					sideB=RHOMBUS_2_B;
				}
			}
			else{//diagonals of a rhombus are not drawn
				if(chooser<.5){
					imageAddress=RHOMBUS_1;//change this out, this has diagonals
					name="JKLM";
					congruentSide1=RHOMBUS_1_CONGRUENT_SIDE_1;
					congruentSide2=RHOMBUS_1_CONGRUENT_SIDE_2;
					congruentAngle1=RHOMBUS_1_CONGRUENT_ANGLE_1;
					congruentAngle2=RHOMBUS_1_CONGRUENT_ANGLE_2;				
					supplementaryAngle1=RHOMBUS_1_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=RHOMBUS_1_SUPPLEMENTARY_ANGLE_2;	
					complementaryAngle1=RHOMBUS_1_COMPLEMENTARY_ANGLE_1;
					complementaryAngle2=RHOMBUS_1_COMPLEMENTARY_ANGLE_2;
					hasAPairOfComplimentaryAngles=true;
				}
				else{
					imageAddress=RHOMBUS_2;//change this out, this has diagonals
					name="MATH";
					congruentSide1=RHOMBUS_2_CONGRUENT_SIDE_1;
					congruentSide2=RHOMBUS_2_CONGRUENT_SIDE_2;
					congruentAngle1=RHOMBUS_2_CONGRUENT_ANGLE_1;
					congruentAngle2=RHOMBUS_2_CONGRUENT_ANGLE_2;				
					supplementaryAngle1=RHOMBUS_2_SUPPLEMENTARY_ANGLE_1;
					supplementaryAngle2=RHOMBUS_2_SUPPLEMENTARY_ANGLE_2;	
					complementaryAngle1=RHOMBUS_2_COMPLEMENTARY_ANGLE_1;
					complementaryAngle2=RHOMBUS_2_COMPLEMENTARY_ANGLE_2;
				}
			}
		}
		else if(type==RECTANGLE){
			//there are no graphics of rectangles without diagonals, as such a rectangle does not have enough unknowns
			hasARightTriangle=true;
			if(chooser<.5){
				imageAddress=RECTANGLE_1;
				name="ABCD";
				congruentSide1=RECTANGLE_1_CONGRUENT_SIDE_1;
				congruentSide2=RECTANGLE_1_CONGRUENT_SIDE_2;
				congruentAngle1=RECTANGLE_1_CONGRUENT_ANGLE_1;
				congruentAngle2=RECTANGLE_1_CONGRUENT_ANGLE_2;
				supplementaryAngle1=null;
				supplementaryAngle2=null;
				complementaryAngle1=null;
				complementaryAngle2=null;
				hypotenuse=RECTANGLE_1_HYPOTENUSE;
				sideA=RECTANGLE_1_A;
				sideB=RECTANGLE_1_B;			
			}
			else{
				imageAddress=RECTANGLE_1;//change this, duplicate
				name="ABCD";
				congruentSide1=RECTANGLE_1_CONGRUENT_SIDE_1;
				congruentSide2=RECTANGLE_1_CONGRUENT_SIDE_2;
				congruentAngle1=RECTANGLE_1_CONGRUENT_ANGLE_1;
				congruentAngle2=RECTANGLE_1_CONGRUENT_ANGLE_2;
				supplementaryAngle1=null;
				supplementaryAngle2=null;
				complementaryAngle1=null;
				complementaryAngle2=null;
				hypotenuse=RECTANGLE_1_HYPOTENUSE;
				sideA=RECTANGLE_1_A;
				sideB=RECTANGLE_1_B;
			}
		}
		congruentSideVariation=Ops.randomInt(0, congruentSide1.length-1);
		congruentAngleVariation=Ops.randomInt(0, congruentAngle1.length-1);
		if(hasAPairOfSupplementaryAngles){
			supplementaryAngleVariation=Ops.randomInt(0, supplementaryAngle1.length-1);
		}
		if(hasAPairOfComplimentaryAngles){
			complementaryAngleVariation=Ops.randomInt(0, complementaryAngle1.length-1);
		}
		if(hasARightTriangle){
			rightTriangleVariation=Ops.randomInt(0, hypotenuse.length-1);
		}
	}
	
	public String getImageAddress(){
		return imageAddress;
	}
	
	public boolean hasDiagonals(){
		if (type!=RECTANGLE&&type!=KITE){//all kites and rectangles have diagonals, regardless of the request by the method caller
			return diagonalsDrawn;
		}
		else return true;
	}
	
	public int getType(){
		return type;
	}
	
	public String getTypeName(){
		return typeName;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCongruentSide1(){
		return congruentSide1[congruentSideVariation];
	}
	
	public String getCongruentSide2(){
		return congruentSide2[congruentSideVariation];
	}
	
	public String getCongruentAngle1(){
		return congruentAngle1[congruentAngleVariation];
	}
	
	public String getCongruentAngle2(){
		return congruentAngle2[congruentAngleVariation];
	}
	
	public String getSupplementaryAngle1(){
		if(hasAPairOfSupplementaryAngles)	return supplementaryAngle1[supplementaryAngleVariation];
		else return null;
	}
	
	public String getSupplementaryAngle2(){
		if (hasAPairOfSupplementaryAngles) return supplementaryAngle2[supplementaryAngleVariation];
		else return null;
	}
	
	public String getComplementaryAngle1(){
		if(hasAPairOfComplimentaryAngles) return complementaryAngle1[supplementaryAngleVariation];
		else return null;
	}
	
	public String getComplementaryAngle2(){
		if(hasAPairOfComplimentaryAngles) return complementaryAngle2[supplementaryAngleVariation];
		else return null;
	}
	
	public String getSideA(){
		if(hasARightTriangle) return sideA[rightTriangleVariation];
		else return null;
	}
	
	public String getSideB(){
		if(hasARightTriangle) return sideB[rightTriangleVariation];
		else return null;
	}
	
	public String getHypotenuse(){
		if(hasARightTriangle) return hypotenuse[rightTriangleVariation];
		else return null;
	}
}
