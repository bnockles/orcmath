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
	private int selectIndexInLine;
	private int selectLine;//the index of the line in which the selectIndex is
	private boolean selectOnly;//true when dragging (keeps cursorIndex fixed)
	private int lengthOfLineBeforeCursorLine;
	private int lengthOfLineAfterCursorLine;
	private ArrayList<Line> lines;//The number of characters in each line of the box
	final int SPACING = 2;//vertical space between horizontal lines

	public TextBox(int x, int y, int w, int h, String text) {
		super(x,y,w,h,text);
		resetInline();
		lengthOfLineAfterCursorLine = -1;
		lengthOfLineBeforeCursorLine = -1;
	}

	public TextBox(int x, int y, int w, int h, String text, String description) {
		super(x,y,w,h,text, description);
		resetInline();
		lengthOfLineAfterCursorLine = -1;
		lengthOfLineBeforeCursorLine = -1;
	}

	protected void setDefaults(){
		super.setDefaults();
		lines = new ArrayList<Line>();
		cursorLine = 0;//this will cause update to fill every line
		selectLine = 0;
	}



	public void act(){

		FontMetrics fm = getImage().createGraphics().getFontMetrics();
		cursorLine = (relativeY-getInitialY(fm))/getLineSpace(fm);
		cursorLine = (cursorLine>=lines.size())?lines.size()-1:cursorLine;
		selectLine = cursorLine;
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
	 * sets cursorInLine and selectInLine to correspond with cursor
	 */
	protected void resetInline(){
		String allText = getText();
		String[] words = allText.split(" ");
		FontMetrics fm = getImage().createGraphics().getFontMetrics();
		int lineCount = 0;

		int lastCompleteLines = 0;
		if(words.length >0){
			int i = 0;
			String line = "";
			A:while(i < words.length){
				line+=words[i++];
				while(i < words.length && lineFits(fm,line+" "+words[i])){
					line += " "+words[i];
					i++;
				}
				

				System.out.println("Last complete line was of length "+lastCompleteLines+" and the line being added is \""+line+"\" with length "+line.length()+". The cursor is "+getCursorIndex()+".");
				System.out.println("The combined length is "+(lastCompleteLines + line.length())+".");
				if(lastCompleteLines + line.length()+lineCount >= getCursorIndex()){
					setCursorInLine(lineCount, getCursorIndex()-lastCompleteLines);
					
					System.out.println("Last complete line had length "+lastCompleteLines+"Cursor on line"+cursorLine+". selectIndexInLine = "+selectIndexInLine+" moved");
					break A;
				}
				lineCount++;
				lastCompleteLines+=line.length();

				line = "";
			}
//			if(getCursorIndex() >= lastCompleteLines){
//				setCursorInLine(lineCount, getCursorIndex()-lastCompleteLines);
//			}


		}


	}

	private void setCursorInLine(int line, int index){
		cursorLine = line;
		cursorIndexInLine = index;
		if(!isShiftHeld()){
		selectLine = line;
		selectIndexInLine = index;
		}
	}

	private boolean lineFits(FontMetrics fm, String line) {
		return fm.stringWidth(line) < getWidth()-X_MARGIN*2;
	}

	public void remove(int low, int high){
		super.remove(low, high);
		resetInline();
	}

	/**
	 * 
	 * @return index of cursor after delete key is pressed. In TexTBoxes, this returns the last index of the previous line
	 */
	protected void decreaseCursor(int spaces) {
		super.decreaseCursor(spaces);
		//		if(spaces > 1){
//		resetInline();//manages deletion of multiple lines
		//		}else{
						cursorIndexInLine--;
						if (cursorIndexInLine < 0){
							if(cursorLine >0){
								cursorLine--;
								cursorIndexInLine = lines.get(cursorLine).getLength()-1; 
				
							}else{
								cursorIndexInLine = 0;
							}
						}
						if(!isShiftHeld()){
							selectIndexInLine = cursorIndexInLine;
							selectLine= cursorLine;
						}
		//		}
	}

	protected void increaseCursor(int spaces){
		super.increaseCursor(spaces);
		//int prior = selectIndexInLine;
//		resetInline();
		//		if(Math.abs(prior - selectIndexInLine) > 1){
		//			System.out.println("selectIndex is larger than expected "+prior+" and "+selectIndexInLine);
		//		}
				if(!isShiftHeld()){
					cursorIndexInLine+=spaces;
					selectIndexInLine+=spaces;
				}else{
					selectIndexInLine += spaces;
		
				}
		//		//note: In update method, out of bounds cursor automatically shifts to next line

	}

	//	protected boolean increaseCursor(){
	//		boolean canIncrease = super.increaseCursor();
	//		if(canIncrease){
	//			cursorIndexInLine++;
	//			selectIndexInLine++;
	//			return true;
	//		}else{
	//			return false;
	//		}
	//		
	//		
	//		
	//	}

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

				if(selectLine <= addLineIndex){
					int start = (addLineIndex>selectLine)?0:selectIndexInLine;
					String line = lines.get(addLineIndex).getLine();
					int end = line.length();
					highlight(g, fm, start,end, line, y);
				}
				//				System.out.println(addLineIndex+", Auto-printing "+lines.get(addLineIndex).getLine());
				g.setColor(getForeground());
				g.drawString(lines.get(addLineIndex).getLine(), X_MARGIN, y);

				position+=lines.get(addLineIndex).getLength();
				y += getLineSpace(fm);
				addLineIndex++;
			}



			String remainingText ="";
			remainingText = getText().substring(position);
			String[] words = remainingText.split(" ");



			if(words.length >0){
				int i = 0;
				String line = "";
				line+= words[i++];
				while(i < words.length){


					while(i < words.length && lineFits(fm,line+" "+words[i])){
						line += " "+words[i];
						i++;
					}


					if(findCursor && relativeY < y && relativeY > y - fm.getHeight()-SPACING-DESCRIPTION_SPACE){

						if(!selectOnly){//when NOT DRAGGING
							cursorIndexInLine = calculateIndexOfClick(line, fm, relativeX);
							setCursor(cursorIndexInLine+position);
							if(!isShiftHeld()){
								setSelect(getCursorIndex());
								selectIndexInLine = cursorIndexInLine;
							}
						}else{//when DRAGGING (i.e.only selector is moving)

							selectIndexInLine = calculateIndexOfClick(line, fm, relativeX);
							setSelect(selectIndexInLine+position);
							selectOnly = false;
						}




						//						System.out.println("In line, cursor is at "+cursorIndexInLine+", but in body cursor at "+getCursorIndex());
						findCursor = false;
						cursorShowing  = true;
					}

					if(y < getHeight()){
						if(selectIndexInLine!=cursorIndexInLine){
							int xStart = (selectIndexInLine< cursorIndexInLine)?selectIndexInLine:cursorIndexInLine;
							//			xStart+=X_MARGIN;
							int xEnd = (selectIndexInLine> cursorIndexInLine)?selectIndexInLine:cursorIndexInLine;
//							System.out.println("Select Index is "+selectIndexInLine);

							highlight(g,fm,xStart,xEnd,getText(),y-fm.getHeight());
						}
						g.setColor(Color.black);
						g.drawString(line, X_MARGIN, y);
						//						if(!cursorDrawn){
						//							System.out.println("Cursor not drawn but index = "+cursorIndexInLine+" and line length is "+line.length());
						//						}
						if(addLineIndex == cursorLine && isCursorShowing()  && !cursorDrawn && getSelectIndex() == getCursorIndex()){
							if(cursorIndexInLine <= line.length()){

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
									//									cursorIndexInLine-= line.length();
									cursorLine++;
									//									cursorLine++;
								}
								else{
									cursorIndexInLine = getCursorIndex()-(position);
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



			}//end
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



	@Override
	public void setFinish(int x, int y) {
		findCursor = true;
		selectOnly = true;
		super.setFinish(x, y);
	}

	@Override
	public void setHeldLocation(int x, int y) {
		findCursor = true;
		selectOnly = true;
		super.setHeldLocation(x, y);
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
