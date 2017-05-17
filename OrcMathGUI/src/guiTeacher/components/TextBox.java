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
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A text editor for multiple-lines of text
 * @author bnockles
 *
 */

public class TextBox extends TextField {

	private int cursorIndexInLine;//index of cursor within line of area, used to move cursor up and down
	private int cursorLine;//the index of the line in which the cursor is
	private int lengthOfLineBeforeCursorLine;
	private int lengthOfLineAfterCursorLine;
	private ArrayList<Line> lines;//The number of characters in each line of the box
	final int SPACING = 2;//vertical space between horizontal lines

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

	protected void setDefaults(){
		super.setDefaults();
		lines = new ArrayList<Line>();
		cursorLine = 0;//this will cause update to fill every line
	}



	public void act(){

		FontMetrics fm = getImage().createGraphics().getFontMetrics();
		cursorLine = (relativeY-getInitialY(fm))/getLineSpace(fm);
		cursorLine = (cursorLine>=lines.size())?lines.size()-1:cursorLine;
		System.out.println("relativeY is "+relativeY+" which places the click on line "+cursorLine);
		super.act();//updates the locati
	}
	//	
	//	public int calculateIndexOfClick(String text, FontMetrics fm, int x){
	//		int check = 0;
	//		if(cursorLine>0){
	//			String clickedLine = 
	//			System.out.println("Clicked line is"+clickedLine+"");
	//			while(check < clickedLine.length() && fm.stringWidth(clickedLine.substring(0,check+1)) < x){
	//				check++;
	//			}
	//
	//			return previousCharacters + check;
	//		}
	//		return check;
	//	}


	/**
	 * 
	 * @return the y coordinate of the top of the first line of text
	 */
	private int getInitialY(FontMetrics fm){
		return 0 + fm.getHeight()+SPACING+DESCRIPTION_SPACE;
	}

	private int getLineSpace(FontMetrics fm){
		final int SPACING = 2;
		return fm.getDescent() + fm.getHeight()+SPACING;
	}

	/**
	 * 
	 * @return index of cursor after delete key is pressed. In TexTBoxes, this returns the last index of the previous line
	 */
	protected void decreaseCursor() {
		super.decreaseCursor();
		cursorIndexInLine--;
		if (cursorIndexInLine < 0){
			if(cursorLine >0){
				cursorLine--;
				cursorIndexInLine = lines.get(cursorLine).getLength()-1; 

			}else{
				cursorIndexInLine = 0;
			}
		}
	}

	protected void increaseCursor(){
		super.increaseCursor();
		cursorIndexInLine++;
		//		if(cursorIndexInLine > lines.get(cursorLine).getLength()+1){
		//			cursorIndexInLine = 1;
		//			cursorLine++;
		//		}
	}

	private void prepare(Graphics2D g){

		g.setColor(Color.WHITE);
		g.fillRoundRect(BORDER+1,BORDER+DESCRIPTION_SPACE+1,getWidth()-2*BORDER-2,getHeight()-2*BORDER-DESCRIPTION_SPACE-2,8,8);
		//		clear();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(getFont());
		g.setColor(Color.black);
	}


	public void update(Graphics2D g2){
		BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = buffer.createGraphics();
		prepare(g);
		FontMetrics fm = g.getFontMetrics();

		if(getText() != null){
			boolean cursorDrawn = false;
			int y = getInitialY(fm);
			int position = 0;//tracks for start of all lines that aren't in memory and is used for adding new lines
			int addLineIndex = 0;//add complete lines to lines ArrayList as they are drawn

			//draw all lines that are above the cursor
			while(addLineIndex< cursorLine){
				//				System.out.println(addLineIndex+", Auto-printing "+lines.get(addLineIndex).getLine());
				g.drawString(lines.get(addLineIndex).getLine(), X_MARGIN, y);

				position+=lines.get(addLineIndex).getLength();
				y += getLineSpace(fm);
				addLineIndex++;
			}

			String remainingText ="";
			try{
				remainingText = getText().substring(position);
			}catch(StringIndexOutOfBoundsException e){
				System.out.println("The line has length < 0, "+lines.get(addLineIndex));
			}
			String[] words = remainingText.split(" ");



			//			int count = 0;//keeps track of the number of characters that have been inserted
			//			lengthOfLineBeforeCursorLine= -1;//-1 signifies there is no line above the cursor
			//			lengthOfLineAfterCursorLine = -1;//-1 signifies there is no line below the cursor



			if(words.length >0){
				int i = 0;
				String line = "";
				while(i < words.length){
					while(i < words.length && fm.stringWidth(line) + fm.stringWidth(words[i]) < getWidth()-X_MARGIN){
						line += words[i]+" ";
						i++;
					}


					if(findCursor && relativeY < y && relativeY > y - fm.getHeight()-SPACING-DESCRIPTION_SPACE){

						cursorIndexInLine = calculateIndexOfClick(line, fm, relativeX);

						setCursor(cursorIndexInLine+position);
						//						System.out.println("In line, cursor is at "+cursorIndexInLine+", but in body cursor at "+getCursorIndex());
						findCursor = false;
						cursorShowing  = true;
					}

					if(y < getHeight()){
						g.drawString(line, X_MARGIN, y);
						//						if(!cursorDrawn){
						//							System.out.println("Cursor not drawn but index = "+cursorIndexInLine+" and line length is "+line.length());
						//						}
						if(isCursorShowing()  && !cursorDrawn){
							if(cursorIndexInLine <= line.length()){
								g.setColor(Color.black);
								////							if(cursorIndex> getText().length())cursorIndex = getText().length();

								//check to see if cursor has reached a new line



								//								System.out.println("Cursor drawn at index "+cursorIndexInLine);
								int x = fm.stringWidth(line.substring(0,cursorIndexInLine))+X_MARGIN;
								//							System.out.println("Drawing at "+x+", while x margin is "+X_MARGIN+" and cursor index is "+getCursorIndex());
								cursorDrawn = true;
								//							cursorIndexInLine = getCursorIndex() - count;
								g.drawLine(x, y, x, y - fm.getHeight());

							}else{
								//								cursorIndexInLine =(position!=0)? getCursorIndex()%position: getCursorIndex();//minue 1 to compensate for auto-increase

								if(getCursorIndex()-position > line.length()){
//									cursorIndexInLine = getCursorIndex()-position;
									//									setCursor(cursorIndexInLine+position);
									//									cursorIndexInLine = (cursorIndexInLine%line.length());
									System.out.println("Line does skip");
									cursorLine++;
									//									cursorLine++;
								}
								else{
									cursorIndexInLine = (position>0)? getCursorIndex()%(position) : getCursorIndex();
//									System.out.println("Line does not skip");
//									cursorLine = addLineIndex;
								}

							}
						}
						y += getLineSpace(fm);
						Line newLine = new Line(line, position);
						if(addLineIndex<lines.size()){
							lines.set(addLineIndex, newLine);
						}else{
							lines.add(newLine);
						}
						addLineIndex++;
						position+=newLine.getLength();


						line = "";
					}else{
						break;//print no more text
					}
				}



			}
			while(lines.size()>addLineIndex){
				lines.remove(addLineIndex);
			}
		}
		drawBorder(fm, g);
		g2.drawImage(buffer, 0, 0, null);
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

	protected class Line{

		private String line;
		private int startIndex;

		protected Line(String s, int startIndex){
			this.line = s;
			this.startIndex = startIndex;
		}

		public String getLine() {
			return line;
		}

		public void setLine(String line) {
			this.line = line;
		}

		public int getStartIndex() {
			return startIndex;
		}

		public void setStartIndex(int startIndex) {
			this.startIndex = startIndex;
		}

		public int getLength(){
			return line.length();
		}
	}

}
