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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;


import javax.imageio.ImageIO;





import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.orcmath.objects.*;
import com.orcmath.type.*;



public class AnswerSheet extends MathPage{
	
//fields
  private int identifier;
	
  private static int numberOfProblems;
  private int numberOfColumns;

  
  
  int[] imageMargins;
  float[]verticalSpace;
  double[] scaleFactors;
  boolean[] neverIncludeInstructions;
  public static String answerAddress = "PDFs/Graphics/answer"; 
  
  private static Font iDNumberFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);


//methods 
  
  public AnswerSheet(int id,int[] topMargins,double[] scales, int columns, int problems, int pages) throws IOException {

	  identifier=id;
//	  content=answersImages;
	  numberOfColumns=columns;
	  numberOfProblems=problems;
	  
	  imageMargins = new int[numberOfProblems*pages];
//	  imageMargins = new int[problems];
	  verticalSpace = new float[numberOfProblems*pages];
//	  verticalSpace = new float[problems];
	  scaleFactors = new double[numberOfProblems*pages];
//	  scaleFactors = new double[problems];
	  neverIncludeInstructions = new boolean[numberOfProblems*pages];
//	  neverIncludeInstructions = new boolean[problems];
	  for (int index=0; index<numberOfProblems*pages;index++){
//	  for (int index=0; index<problems;index++){
//		  answerAddresses[index]=answerAddress+(index+1)+".png";
//		  File file = new File(answerAddress+(index+1)+".png");
//		  ImageIO.write(content[index], "png", file.getAbsoluteFile());
//		  System.out.println("Out of: "+numberOfProblems*pages);
//		  System.out.println("AnswerSheet index = " + index+ " and topMargin = "+topMargins[index]);
		  imageMargins[index]=topMargins[index%topMargins.length];
		  verticalSpace[index]=10;
		  scaleFactors[index]=scales[index%scales.length];
		  neverIncludeInstructions[index]=false;
	  }
  }
  
  
public PdfPTable getAnswersTable(int pageNumber, String[] filenames) throws DocumentException, IOException{
	PdfPTable table = new PdfPTable(2*numberOfColumns);
	table.setWidthPercentage(100);
	table.getDefaultCell().setBorder(0);
	table.getDefaultCell().setPadding(0);
	table.getDefaultCell().setPaddingBottom(20);
	//table.getDefaultCell().setPaddingBottom((float)(content[0].getHeight()*scaleFactors[0]));
	addTableHeader(table, "ANSWERS", titleBold);
	PdfPCell id = new PdfPCell(new Phrase("" + (identifier+pageNumber),smallBold));
	id.setPadding(0);
	id.setPaddingBottom(5);
	id.setBorder(0);
	id.setHorizontalAlignment(Element.ALIGN_CENTER);
	id.setColspan(2*numberOfColumns);
	table.addCell(id);
	
	int[] imageMarginsPage = new int[numberOfProblems];
	for (int index = 0; index< numberOfProblems; index ++){
		imageMarginsPage[index] = imageMargins[(index+numberOfProblems*(pageNumber-1))];
	}
	
	addNumberedTableOfImages(table, filenames, imageMarginsPage, scaleFactors, false, neverIncludeInstructions, null, verticalSpace, false, 0);
	float[] columnWidths;
	if (numberOfColumns==1) columnWidths = new float[] {1f, 20f};
	else if (numberOfColumns == 2) columnWidths = new float[] {1f, 10f,1f, 10f};//TODO write a method to determine these numbers
	else{
		columnWidths = new float[] {1f, 10f,1f, 10f,1f,10f};
	}
	table.setWidths(columnWidths);
	return table;
}
    
}

