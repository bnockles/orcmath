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
package guiTeacher;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import guiTeacher.components.StyledComponent;

public class Utilities {

	public static final int SPACING = 2;
	
	public static final void drawText(Graphics2D g, String text, int xAlign, int y, int width, int height, int align){
		FontMetrics fm = g.getFontMetrics();
		if(text != null){
			String[] words = text.split(" ");
			if(words.length >0){
				int i = 0;
				
				y = y + fm.getHeight();
				String line = "";
				while(i < words.length){
					while(i < words.length && fm.stringWidth(line) + fm.stringWidth(words[i]) < width){
						line += words[i]+" ";
						i++;
					}
			
					if(y < height){
						/**
						 * draws string according to specified alignment
						 */
						int startX = xAlign;//default is left
						if(align == StyledComponent.ALIGN_CENTER){
							startX = xAlign - fm.stringWidth(line)/2;
						}else if(align == StyledComponent.ALIGN_RIGHT){
							startX = xAlign - fm.stringWidth(line);
						}
						g.drawString(line, startX, y);
						y += fm.getDescent() + fm.getHeight()+SPACING;
						line = "";
					}else{
						break;//print no more text
					}
				}
			}
		}

	}
	
	public static final void drawText(Graphics2D g, String text, int yTop, int width, int height, int align){
		if(align == StyledComponent.ALIGN_CENTER){
			drawText(g, text, width/2,yTop, width, height, align);
		}else if(align == StyledComponent.ALIGN_RIGHT){
			drawText(g, text, width,yTop, width, height, align);
		}else{
			drawText(g, text, 0,yTop, width, height, align);
		}
	}
	
	/**
	 * 
	 * @param color the Color being lightened
	 * @param amount 1.0f for white, 0.0f for no change 
	 * @return a color that has been lightened by amount
	 */
	public static Color lighten(Color color, float amount){
		int red = (int)((color.getRed() * (1-amount)/255+amount)*255);
		int green = (int)((color.getGreen() * (1-amount)/255+amount)*255);
		int blue = (int)((color.getBlue() * (1-amount)/255+amount)*255);
		return new Color(red, green, blue);
	}
	
}
