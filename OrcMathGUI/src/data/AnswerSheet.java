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

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class AnswerSheet extends Worksheet{

	private QuestionData[] data;
	
	public AnswerSheet(int identifier, QuestionData[] questionData, int numberOfProblems, int numberOfPages) {
		super();
		setIdentifier(identifier);
		setNumberOfColumns(1);
		setNumberOfProblems(numberOfProblems);
		setNumberOfPages(numberOfPages);
		setOverrideVerticalSpacing(false);
		this.data = questionData;
	}
	
	public PdfPTable getAnswersTable(int pageNumber,int startInclusive, int finishExclusive) throws DocumentException, IOException{
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.getDefaultCell().setBorder(0);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setPaddingBottom(20);
		//table.getDefaultCell().setPaddingBottom((float)(content[0].getHeight()*scaleFactors[0]));
		addTableHeader(table, "ANSWERS", titleBold);
		PdfPCell id = new PdfPCell(new Phrase("" + (getIdentifier()+pageNumber),smallBold));
		id.setPadding(0);
		id.setPaddingBottom(5);
		id.setBorder(0);
		id.setHorizontalAlignment(Element.ALIGN_CENTER);
		id.setColspan(2);
		table.addCell(id);
		
		QuestionData[] subset= new QuestionData[finishExclusive-startInclusive];
		for(int i = 0; i < subset.length; i++){
			subset[i] = data[startInclusive+i];
		}
		
		
		addNumberedTableOfImages(table, subset,1);
		float[] columnWidths;
		columnWidths = new float[] {1f, 20f};
		
		table.setWidths(columnWidths);
		return table;
	}
	
	protected static void addTableHeader(PdfPTable table, String text, Font font){
		PdfPCell header = new PdfPCell(new Phrase(text,font));
	    header.setPadding(5);
	    header.setColspan(2);
	    header.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(header);
	}

}
