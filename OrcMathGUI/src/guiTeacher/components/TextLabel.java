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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import guiTeacher.Utilities;
import guiTeacher.interfaces.TextComponent;

public class TextLabel extends StyledComponent implements TextComponent{

	//FIELDS
		private String text;
		private Font font;
		private float size;
		private Color textColor;
		private int align; 
		
		public TextLabel(int x, int y, int w, int h, String text) {
			super(x, y, w, h);
			this.text = text;
			font = getBaseFont();
			size = 20;
			textColor = super.getTextColor();
			align = super.getBodyAlign();
			update();
		}

		public void setCustomAlign(int i){
			this.align = i;
			update();
		}
		
		public Color getTextColor() {
			return textColor;
		}



		public void setCustomTextColor(Color textColor) {
			this.textColor = textColor;
			update();
		}



		public void setText(String s){
			this.text = s;
			update();
		}
		
		public String getText(){
			return text;
		}
		
		public void setSize(float size){
			this.size = size;
			setFont(font.deriveFont(size));
		}
		
		public void setFont(Font font){
			this.font = font;
			update();
		}
		
		public Font getFont(){
			return font;
		}
		
		public float getSize(){
			return size;
		}
		
		
		@Override
		public void update(Graphics2D g) {
			clear();
//			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(this.getTextColor());
			g.setFont(getFont());
			FontMetrics fm = g.getFontMetrics();
			if(text != null){
//				Utilities.drawText(g, text, 0, getWidth(), getHeight(), align);
				if(align==ALIGN_LEFT) g.drawString(text, 0, fm.getHeight());
				else if(align == ALIGN_CENTER)g.drawString(text, (getWidth()-fm.stringWidth(text))/2, fm.getHeight());
				else g.drawString(text, getWidth()-fm.stringWidth(text), fm.getHeight());
			}
		}

		@Override
		public boolean isEditable() {
			return false;
		}

		@Override
		public void setEditable(boolean b) {
			//does nothing. TExtLabels are never editable
		}

		


}
