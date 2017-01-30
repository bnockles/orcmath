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
import java.awt.Graphics2D;

import guiTeacher.interfaces.TextComponent;


public class SimpleTableRow {

	private SimpleTable table;
	private TextComponent[] values;
	private HoverButton hoverButton;
	private boolean hovered;
	
	public SimpleTableRow(SimpleTable table, String[] values, boolean[] editable, int[] widths, int height ) {
		this.table = table;
		this.values = new TextComponent[values.length];
		this.hoverButton = new HoverButton();
		for(int i=0; i < this.values.length; i++ ){
			if(editable[i]){
				this.values[i] = new TextField(0, 0, widths[i], height, values[i]);

			}else{
				this.values[i] = new TextLabel(0, 0, widths[i], height, values[i]);
				
			}
		}
	}
	
	
	public void setInputType(int column, int type){
		TextField tf = (TextField)(values[column]);
		tf.setInputType(type);
		
	}

	public String getValue(int i){
		return values[i].getText();
	}
	
	public TextComponent[] getValues(){
		return values;
	}

	public void columnClicked(int clickedColumn, int contextX, int contextY) {
		if(values[clickedColumn].isEditable()){
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
		}
		
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
	
}
