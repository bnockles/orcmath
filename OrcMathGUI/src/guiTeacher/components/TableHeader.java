/*******************************************************************************
 * Copyright (c) 2016-2017 Benjamin Nockles
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
package guiTeacher.components;

/**
 * A TableHeader is required to create a Table. It defines the number and label of every column
 * @author bnockles
 *
 */

public class TableHeader  {

	private String[] columnDescriptions;
	private boolean[] columnEditable;
	private int[] columnWidths;
	private int rowHeight;
	
	/**
	 * 
	 * @param columnValues = labels for each column
	 * @param columnWidths = pixel width of each column
	 * @param columnEditable = determines whether user can input values for each row of each column
	 * @param rowHeight = the pixel height of the header
	 */
	public TableHeader(String[] columnValues, int[] columnWidths, boolean[] columnEditable, int rowHeight) {
		this.columnDescriptions = columnValues;
		this.columnWidths = columnWidths;
		this.columnEditable = columnEditable;
		this.rowHeight = rowHeight;
	}
	
	public boolean[] getColumnEditable(){
		return columnEditable;
	}
	
	public boolean columnIsEditable(int column){
		return columnEditable[column];
	}
	
	public void setEditable(int column, boolean value){
		columnEditable[column] = value;
	}
	
	public String[] getColumnDescriptions() {
		return columnDescriptions;
	}

	public int getRowHeight() {
		return rowHeight;
	}

	public int[] getColumnWidths() {
		return columnWidths;
	}


}
