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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;

import guiTeacher.GUIApplication;
import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.TextComponent;


public class SimpleTableRow {

	private SimpleTable table;
	private TextComponent[] values;
	private boolean[] editable;
	private HoverButton hoverButton;
	private boolean hovered;

	public SimpleTableRow(SimpleTable table, String[] values, boolean[] editable, int[] widths, int height ) {
		this.table = table;
		this.values = new TextComponent[values.length];
		this.hoverButton = new HoverButton();
		this.editable = editable;
		setContent(false, values, widths, height);
	}
	
	public SimpleTableRow(SimpleTable table, String[] values, boolean[] editable, int[] widths, int height, boolean multiline) {
		this.table = table;
		this.values = new TextComponent[values.length];
		this.hoverButton = new HoverButton();
		this.editable = editable;
		setContent(multiline, values, widths, height);
	}
	
	private void setContent(boolean multiline, String[] values, int[] widths, int height){
		for(int i=0; i < this.values.length; i++ ){
			if(editable[i]){
				if (!multiline) {
					this.values[i] = new TextField(0, 0, widths[i], height, values[i]);
				}
				else {
					this.values[i] = new TextBox(0, 0, widths[i], height, values[i]);
				}

			}else{
				if(!multiline){
					this.values[i] = new TextLabel(0, 0, widths[i], height, values[i]);
				}else{
					this.values[i] = new TextArea(0, 0, widths[i], height, values[i]);
				}

			}
		}
	}

	/**
	 * if false, changes textFields to TextLabels
	 * vice-versa if true
	 * @param column index of column being changed
	 * @param canEdit
	 */
	public void resetEdit(int column, boolean canEdit, boolean multiLine){
		TextComponent former = this.values[column];
		if(!multiLine){
			if(editable[column] && !canEdit){
				this.values[column] = new TextLabel(former.getX(),former.getY(),former.getWidth(),former.getHeight(),former.getText());
			}else if(!editable[column] && canEdit){
				this.values[column] = new TextField(former.getX(),former.getY(),former.getWidth(),former.getHeight(),former.getText());
			}
		}else{
			if(editable[column] && !canEdit){
				this.values[column] = new TextArea(former.getX(),former.getY(),former.getWidth(),former.getHeight(),former.getText());
			}else if(!editable[column] && canEdit){
				this.values[column] = new TextBox(former.getX(),former.getY(),former.getWidth(),former.getHeight(),former.getText());
			}
		}
	}

	/**
	 * Can make the input at this column accept integers or strings
	 * @param column
	 * @param type
	 */
	public void setInputType(int column, int type){
		TextField tf = (TextField)(values[column]);
		tf.setInputType(type);

	}

	public void setColumn(int column, TextComponent component){
		values[column] = component;
	}

	public String getValue(int i){
		return values[i].getText();
	}

	public TextComponent[] getValues(){
		return values;
	}

	/**
	 * changes the cursor accordingly. If other hover actions are needed, this method need to be edited.
	 * @param hoveredColumn the column being hovered
	 * @param contextX
	 * @param contextY
	 */
	public void columnHovered(int hoveredColumn, int contextX, int contextY){

		if(values[hoveredColumn] instanceof TextField && values[hoveredColumn].isEditable()){
			GUIApplication.mainFrame.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		}else if(values[hoveredColumn] instanceof Link) {
			GUIApplication.mainFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}else{
			GUIApplication.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public void columnClicked(int clickedColumn, int contextX, int contextY) {
		if(values[clickedColumn] instanceof TextField && values[clickedColumn].isEditable()){
			table.getFocusController().moveFocus((TextField)values[clickedColumn]);
			//updates the table while the element is being edited
			Thread tableUpdate = new Thread(new Runnable() {

				@Override
				public void run() {
					while(table.getFocusController().getFocusedComponent() == values[clickedColumn]){
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						table.update();
					}
				}
			});
			tableUpdate.start();
		}else if (values[clickedColumn] instanceof Clickable){
			Clickable c = (Clickable)values[clickedColumn];
			c.act();
		}

	}

	public boolean isHovered(){
		return hovered;
	}

	public void setHover(boolean b) {
		hovered = b;
		hoverButton.setHovered(b);
	}


	public void drawButton(Graphics2D g, int x, int y) {
		hoverButton.draw(g, x, y);
	}

	public class HoverButton{

		private boolean hovered;

		public HoverButton(){
			hovered = false;
		}

		public void draw(Graphics2D g, int x, int y){

			if(hovered){
				g.setColor(new Color(80,80,80));
			}else{
				g.setColor(new Color(180,180,180));
			}
			g.drawOval(x,y,SimpleTable.HOVER_BUTTON_WIDTH,SimpleTable.HOVER_BUTTON_WIDTH);
		}

		public void setHovered(boolean b){
			hovered = b;
		}

	}

}
