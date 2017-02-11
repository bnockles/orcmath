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
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

public class TextBox extends TextField {

	private int cursorIndexInLine;//index of cursor within line of area, used to move cursor up and down
	private int lengthOfLineBeforeCursorLine;
	private int lengthOfLineAfterCursorLine;
	
	public TextBox(int x, int y, int w, int h, String text) {
		super(x,y,w,h,text);
		cursorIndexInLine = getCursorIndex();
		lengthOfLineAfterCursorLine = -1;
		lengthOfLineBeforeCursorLine = -1;
	}
	
	public TextBox(int x, int y, int w, int h, String text, String description) {
		super(x,y,w,h,text, description);
		cursorIndexInLine = getCursorIndex();
		lengthOfLineAfterCursorLine = -1;
		lengthOfLineBeforeCursorLine = -1;
	}

	
	public void update(Graphics2D g){
		clear();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(getFont());
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.black);
		if(getText() != null){
			String[] words = getText().split(" ");
			int count = 0;//keeps track of the number of characters that have been inserted
			lengthOfLineBeforeCursorLine= -1;//-1 signifies there is no line above the cursor
			lengthOfLineAfterCursorLine = -1;//-1 signifies there is no line below the cursor
			boolean cursorDrawn = false;
			if(words.length >0){
				int i = 0;
				final int SPACING = 2;
				int y = 0 + fm.getHeight()+SPACING+DESCRIPTION_SPACE;
				String line = "";
				while(i < words.length){
					while(i < words.length && fm.stringWidth(line) + fm.stringWidth(words[i]) < getWidth()-X_MARGIN){
						line += words[i]+" ";
						i++;
					}
					//moves the cursor if this line was clicked
					if(findCursor && relativeY < y && relativeY > y - fm.getHeight()-SPACING-DESCRIPTION_SPACE){
						int check = 0;
						while(check < line.length() && fm.stringWidth(line.substring(0,check+1)) < relativeX){
							check++;
						}
						setCursor(count + check);
						findCursor = false;
						cursorShowing  = true;
					}
					
					if(y < getHeight()){
						g.drawString(line, X_MARGIN, y);
						if(isCursorShowing() && getCursorIndex() <=count+line.length() && !cursorDrawn){
							g.setColor(Color.black);
//							if(cursorIndex> getText().length())cursorIndex = getText().length();
							int x = fm.stringWidth(line.substring(0,getCursorIndex()-count))+X_MARGIN;
							cursorDrawn = true;
							cursorIndexInLine = getCursorIndex() - count;
							g.drawLine(x, y, x, y - fm.getHeight());
						}else{							
							if(!cursorDrawn){
								lengthOfLineBeforeCursorLine = line.length();
							}else if(lengthOfLineAfterCursorLine == -1){
								lengthOfLineAfterCursorLine = line.length();
							}
							
							count += line.length();
						}
						y += fm.getDescent() + fm.getHeight()+SPACING;
						line = "";
					}else{
						break;//print no more text
					}
				}
			}
		}
		drawBorder(fm, g);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		int c = getCursorIndex();
		if(e.getKeyCode() == KeyEvent.VK_UP && lengthOfLineBeforeCursorLine != -1){
			int newc = c-lengthOfLineBeforeCursorLine;
			newc =(newc < c-cursorIndexInLine)?newc:c-cursorIndexInLine-1;
			setCursor(newc);
			update();
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN && lengthOfLineAfterCursorLine != -1){
			int newc = c+lengthOfLineAfterCursorLine;
			newc = (newc < getText().length())? newc:getText().length();
			setCursor(newc);
			update();
		}
	}


}
