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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import guiTeacher.GUIApplication;
import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.Dragable;
import guiTeacher.interfaces.KeyedComponent;
import guiTeacher.interfaces.TextComponent;
import main.OrcMath;

public class TextField extends StyledComponent implements KeyedComponent,Clickable, Runnable, Dragable, TextComponent{

	//FIELDS
	protected BufferedImage borderImage;
	protected String text;
	private Font font;
	private Font labelFont;
	private float size;
	private boolean readOnly;
	private boolean drawBorder;
	private int verticalAlign;
	private ArrayList<TextFieldSaveState> history;//keeps track of recent changes
	private boolean changeMade;//a boolean keeps track of if change was made since last time history was updated
	private int historyCount;//number of intervals since last update of history. Once count == _HISTORY_UPDATE_LIMIT, an update of history is performed
	private int historyIndex;//index of where we are in history, (AKA number of times command-Z is pressed)
	private boolean commandHeld;//used to identify whether a shortcut is used
	private boolean shiftHeld;
	protected boolean ignoreDismiss;//when true, does not turn off shift held when clicked

	private static final int _HISTORY_LIMIT = 10;
	private static final int _HISTORY_UPDATE_LIMIT = 3;//how many intervals of _CURSOR_INTERVAL to wait before updating history



	public static final int CURSOR_INTERVAL = 500;
	public static final int DESCRIPTION_SPACE = 15;
	public static final int X_MARGIN = 5;
	public static final int BORDER = 2;
	public static final Color BORDER_COLOR = new Color(80,80,80);
	private boolean running;
	protected boolean cursorShowing;
	private int cursorIndex;
	private int selectIndex;
	protected int relativeX;//used during hover
	protected int relativeY;//used during hover
	protected int relativeXClick;//used for finding cursor
	protected int relativeYClick;//used for finding cursor
	protected boolean findCursor;//turns true when the update method is required to look for where a click corresponds to the cursor
	private boolean editable;
	private int inputType;
	private int inputRangeMin;
	private int inputRangeMax;
	private int inputLength;
	private String description;

	public static final int INPUT_TYPE_PLAIN = 0;
	public static final int INPUT_TYPE_NUMERIC =1;
	public static final int INPUT_TYPE_CUSTOM =2;
	public static final int TOP = -1;
	public static final int CENTER = 0;
	public static final int BOTTOM = 1;

	public TextField(int x, int y, int w, int h, String text) {
		super(x, y-DESCRIPTION_SPACE, w, h+DESCRIPTION_SPACE);
		this.text = text;
		description = null;
		setDefaults();
		update();
	}

	public TextField(int x, int y, int w, int h, String text, String description) {
		super(x, y-DESCRIPTION_SPACE, w, h+DESCRIPTION_SPACE);
		this.text = text;
		this.description = description;
		setDefaults();
		update();
	}

	protected void setDefaults(){
		history=new ArrayList<TextFieldSaveState>();
		historyCount = 0;
		changeMade = false;
		drawBorder = true;
		readOnly = false;
		verticalAlign = BOTTOM;
		inputType = INPUT_TYPE_PLAIN;
		inputRangeMin =0;
		inputRangeMax =0;
		inputLength = 0;
		editable = true;
		font = getBaseFont();
		labelFont = getBaseFont();
		size = 20;
		running = false;
		cursorIndex = getText().length();
		selectIndex = cursorIndex;
		history.add(new TextFieldSaveState(this.text,cursorIndex,cursorIndex));
		resetBorder();
	}

	public BufferedImage getImage(){
		if(drawBorder){
			BufferedImage canvas = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = canvas.createGraphics();
			g.drawImage(super.getImage(), 0, 0, null);
			g.drawImage(borderImage, 0, 0, null);
			return canvas;
		}else{
			return super.getImage();
		}
	}



	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isDrawBorder() {
		return drawBorder;
	}

	public void setDrawBorder(boolean drawBorder) {
		this.drawBorder = drawBorder;
	}

	public int getVerticalAlign() {
		return verticalAlign;
	}

	public void setVerticalAlign(int verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		resetBorder();
	}

	public void setInputType(int min, int max, int length) {
		inputType = INPUT_TYPE_CUSTOM;
		inputRangeMin = min;
		inputRangeMax = max;
		inputLength = length;
	}

	public void setInputType(int type){
		inputType = type;
	}

	public boolean isEditable(){
		return editable;
	}

	public void setEditable(boolean b){
		editable = b;
	}

	public void run(){
		cursorShowing = true;
		while(running){
			update();	
			historyCount++;
			if(changeMade && historyCount >=_HISTORY_UPDATE_LIMIT){
				historyCount=0;
				history.add(0,new TextFieldSaveState(text, cursorIndex, selectIndex));
				if(history.size()>_HISTORY_LIMIT){
					history.remove(_HISTORY_LIMIT);
				}
				changeMade = false;
			}
			try {
				Thread.sleep(CURSOR_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cursorShowing = !cursorShowing;
		}
		cursorShowing = false;
		update();
	}

	//	public void update(){
	//		if(updateDescription){
	//			clear();
	//			super.update();
	//			updateDescription = false;
	//		}else{
	//			super.update();			
	//		}
	//	}

	public int calculateIndexOfClick(String s, FontMetrics fm, int x){
		int check = 0;
		while(check < s.length() && fm.stringWidth(s.substring(0,check+1)) < x){
			check++;
		}
		//		System.out.println(check + " for string "+s+" with length "+s.length());
		return check;
	}

	protected void highlight(Graphics2D g, FontMetrics fm, int startIndex, int endIndex, String line, int y) {
		g.setColor(new Color(200,200,255));
		startIndex = (startIndex < line.length())?startIndex:line.length();
		int xStart = fm.stringWidth(line.substring(0,startIndex));
		int xEnd =(endIndex < line.length())?fm.stringWidth(line.substring(0,endIndex)):fm.stringWidth(line);
		xStart+=X_MARGIN;
		xEnd+=X_MARGIN;
		g.fillRect(xStart, y, xEnd-xStart, fm.getHeight());
	}

	protected int getTop(FontMetrics fm){
		if(verticalAlign == BOTTOM)return getHeight()-fm.getDescent();
		else if(verticalAlign == CENTER)return DESCRIPTION_SPACE+(getHeight()-DESCRIPTION_SPACE)/2;
		else return BORDER+DESCRIPTION_SPACE+fm.getAscent();
	}

	@Override
	public void update(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(getBackgroundColor());
		int spaceHeight = getHeight()-DESCRIPTION_SPACE;
		g.fillRoundRect(1,DESCRIPTION_SPACE,getWidth()-2,spaceHeight,8,8);
		g.setFont(getFont());
		FontMetrics fm = g.getFontMetrics();
		if(findCursor){
			cursorIndex = calculateIndexOfClick(getText(), fm, relativeXClick);
			if(!shiftHeld){
				selectIndex = cursorIndex;
			}
			findCursor = false;
			cursorShowing  = true;
		}
		int top = getTop(fm);
		if(selectIndex != cursorIndex){

			int xStart = (selectIndex< cursorIndex)?selectIndex:cursorIndex;
			//			xStart+=X_MARGIN;
			int xEnd = (selectIndex> cursorIndex)?selectIndex:cursorIndex;

			//			int top = BORDER+DESCRIPTION_SPACE+fm.getDescent();
			highlight(g,fm,xStart,xEnd,getText(),top-fm.getHeight());


		}
		g.setColor(getForeground());
		if(getText() != null) g.drawString(getText(), X_MARGIN, top);

		try{
		if(!isReadOnly() && cursorShowing && running && selectIndex == cursorIndex){
			g.setColor(Color.black);
			int base = getHeight()-fm.getDescent();
			//			if(cursorIndex> getText().length())cursorIndex = getText().length();
			int x = fm.stringWidth(getText().substring(0,cursorIndex))+X_MARGIN;
			g.drawLine(x, base, x, base - fm.getHeight());
		}
		}catch(StringIndexOutOfBoundsException siobe){
		}
		//		drawBorder(g);
	}

	public void drawBorder(Graphics2D g){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		FontMetrics fm = g.getFontMetrics(getLabelFont());
		Color bc = (running)? getActiveBorderColor():getInactiveBorderColor();
		g.setColor(bc);
		g.setStroke(new BasicStroke(BORDER));
		g.drawRoundRect(BORDER,BORDER+DESCRIPTION_SPACE,getWidth()-2*BORDER,getHeight()-2*BORDER-DESCRIPTION_SPACE,8,8);
		if(description != null){
			g.setColor(getForeground());
			g.setFont(getLabelFont());
			g.drawString(description, BORDER, DESCRIPTION_SPACE-fm.getDescent());

		}
	}

	protected void selectAll(){
		selectIndex = 0;
		cursorIndex = getText().length();
		update();
	}

	protected void deleteAndInsert(String s){
		insert(s);
	}

	private void shortcut(char c){
		boolean removeAfterCopy=false;
		if(c=='x'){
			removeAfterCopy = true;
			c = 'c';
		}
		if(c == 'z'){
			if(historyIndex < history.size()-1)historyIndex++;
			TextFieldSaveState state = history.get(historyIndex);

			setText(state.text);
			setCursors(state.cursorPoint, state.selectPoint);

		}else if(c == 'v'){
			try {
				String data = (String) Toolkit.getDefaultToolkit()
						.getSystemClipboard().getData(DataFlavor.stringFlavor);
				insert(data);
			} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
				//only thrown if text is not pastable. Does not need to be reported
				e.printStackTrace();
			} 

		}else if(c == 'a'){
			selectAll();
		}else if(c =='c'){
			int low = (selectIndex < cursorIndex)?selectIndex:cursorIndex;
			int high = (selectIndex > cursorIndex)?selectIndex:cursorIndex;
			if(low != high){

				StringSelection stringSelection = new StringSelection(getText().substring(low,high));
				Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
				clpbrd.setContents(stringSelection, null);
			}
		}
		if(removeAfterCopy){
			deleteAndInsert("");
		}

	}



	protected void setCursors(int cursorPoint, int selectPoint) {
		setCursor(cursorPoint);
		setSelect(selectPoint);
	}

	protected void increaseCursor(int spaces){
		cursorIndex+=spaces;
		if(cursorIndex>getText().length())cursorIndex = getText().length()-1;
		if(!shiftHeld){

			selectIndex=cursorIndex;
		}
	}

	public void insert(String c){

		int low = (selectIndex< cursorIndex)?selectIndex:cursorIndex;
		int high = (selectIndex> cursorIndex)?selectIndex:cursorIndex;
		String t = getText();
		text = t.substring(0,low)+c+t.substring(high,t.length());
		if(high-low > 0) {
			setCursor(cursorIndex-(high-low));
		}
		selectIndex = cursorIndex;
		increaseCursor(c.length());
		update();
	}
	public void remove(int low, int high){
		String t = getText();

		text = t.substring(0, low)+t.substring(high,t.length());
		System.out.println("(TextFiled.java) Deleting. seectIndex = "+selectIndex+", cursoIndex = "+cursorIndex+", low = "+low+", high = "+high);
		//check to see if cursor moves back or stays in place
		if(selectIndex <= cursorIndex){
			cursorIndex = cursorIndex-(high-low);
			selectIndex = cursorIndex;			
		}else{
			selectIndex = cursorIndex;	
		}

		//		if(low<high-1){
		//			//when deleting more than one 
		//		}else{
		//			decreaseCursor();
		//		}

		update();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(!isReadOnly()){
			char c = e.getKeyChar();
			shiftHeld = false;//disable shift held, otherwise cursor behaves like arrow key function



			String t = getText();
			changeMade = true;
			historyIndex = 0;
			historyIndex=0;
			if(inputType == INPUT_TYPE_PLAIN && c >=32 && c <127){
				insert(""+c);

			}else if(inputType == INPUT_TYPE_NUMERIC && (c== 46 || c >=48 && c <=57)){

				insert(""+c);

			}else if(c==127 || c==8){
				//delete is pressed
				int low = (selectIndex< cursorIndex)?selectIndex:cursorIndex;
				int high = (selectIndex> cursorIndex)?selectIndex:cursorIndex;
				if(high > text.length())high = text.length();//work around THIS IS A WORK AROUND. IT SHOULDN'T BE NECESSARY. WHY iS HIGH OUTTA BOUNDS?
				//			System.out.println("(TextField) deleting with low "+low+", high "+high);
				//when selector is not over more than one letter
				if(low == high && low>0){
					low=high-1;
				}else{//save after deleting multiple characters
					history.add(0,new TextFieldSaveState(text, cursorIndex, selectIndex));
				}
				remove(low,high);

			}else if(inputType == INPUT_TYPE_CUSTOM && t.length()<inputLength && c>=inputRangeMin && c<=inputRangeMax){
				insert(c+"");
			}
		}
	}

	public int getSelectIndex(){
		return selectIndex;
	}

	public boolean isShiftHeld(){
		return shiftHeld;
	}

	/**
	 * 
	 * @return index of cursor after delete key is pressed. In TexTBoxes, this returns the last index of the previous line
	 */
	protected void decreaseCursor(int spaces) {
		cursorIndex = (cursorIndex -spaces >0)?cursorIndex-spaces:0;
		if(!shiftHeld){
			selectIndex = cursorIndex;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//		System.out.println(e.getKeyChar());
		if(commandHeld){
			shortcut(e.getKeyChar());
		}
		else if(e.getKeyCode() == KeyEvent.VK_META){
			commandHeld = true;
		}else if(e.getKeyCode() == KeyEvent.VK_SHIFT){
			shiftHeld = true;
			ignoreDismiss=true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(!ignoreDismiss)shiftHeld=false;
			int change = 1;
			if(!shiftHeld && cursorIndex > selectIndex){
				change = cursorIndex-selectIndex;
				if(cursorIndex+change < 0){
					change = cursorIndex;
				}
			}else if(!shiftHeld && cursorIndex < selectIndex){
				change = 0;
			}
			decreaseCursor(change);
			update();
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(!ignoreDismiss)shiftHeld=false;
			int change = 1;
			if(!shiftHeld && cursorIndex < selectIndex){
				change = -cursorIndex+selectIndex;
				if(cursorIndex+change > getText().length()){
					change = getText().length()-cursorIndex;
				}
			}else if(!shiftHeld && cursorIndex > selectIndex){
				change = 0;
			}

			increaseCursor(change);
			update();


		}


	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_META){
			commandHeld = false;
		}else if(e.getKeyCode() == KeyEvent.VK_SHIFT){
			shiftHeld = false;
			ignoreDismiss=false;
		}
	}

	protected void setSelect(int x){
		if(x<0)selectIndex = 0;
		else if(x>getText().length())selectIndex = getText().length();
		else selectIndex = x;
	}

	protected void setCursor(int x){
		if(x<0)cursorIndex = 0;
		else if(x>getText().length())cursorIndex = getText().length();
		else cursorIndex = x;
	}

	public boolean isHovered(int x, int y) {
		boolean b =  x > getX() && x < getX() + getWidth() && y > getY()+DESCRIPTION_SPACE && y < getY() + getHeight();
		if(b){
			relativeX = x - getX();
			relativeY = y - getY();
			setLeft(false);
			GUIApplication.mainFrame.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		}else if(!hasLeft()){
			setLeft(true);
			GUIApplication.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		return b;
	}


	public String toString(){
		return "TextBox with description + \""+description+"\"";
	}

	public void setFocus(boolean b) {
		if(b && !running && editable){
			//			System.out.println("Focus set on "+toString());
			running = true;
			Thread cursor = new Thread(this);
			cursor.start();
			resetBorder();
		}else if(!b){
			//			System.out.println("Focus removed from "+toString());
			running = false;
			resetSelect();
			resetBorder();

		}
	}

	/**
	 * sets the select cursor to the cursor
	 */
	public void resetSelect(){
		selectIndex = cursorIndex;
		shiftHeld = false;
		update();
	}

	public void setText(String s){
		this.text = s;
	}
	

	public String getText(){
		return text;
	}

	public void setSize(float size){
		this.size = size;
		font = font.deriveFont(size);
		update();
	}

	public void setLabelFont(Font font){
		this.labelFont = font;
		resetBorder();
	}



	public Font getLabelFont() {
		return labelFont;
	}

	public void setFont(Font font) {
		this.font = font;
		update();
	}

	public Font getFont(){
		return font;
	}

	public void resetBorder(){
		borderImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		drawBorder(borderImage.createGraphics());
	}

	public float getSize(){
		return size;
	}

	public boolean isCursorShowing(){
		return cursorShowing;
	}

	public int getCursorIndex(){
		return cursorIndex;
	}

	@Override
	public void act() {
		if(!ignoreDismiss){
			shiftHeld = false;			
		}
		relativeXClick = relativeX;
		relativeYClick = relativeY;
		findCursor = true;//when updating, calls the method that checks for the location of the cursor
	}

	public boolean isRunning(){
		return running;
	}

	@Override
	public void setAction(Action a) {
		// TODO Auto-generated method stub

	}

	protected class TextFieldSaveState{

		String text;
		int cursorPoint;
		int selectPoint;

		protected TextFieldSaveState(String text, int cursor, int selectPoint){
			this.text = text;
			this.cursorPoint = cursor;
			this.selectPoint = selectPoint;
		}

	}

	public void setShiftHeld(boolean b){
		shiftHeld = b;
	}

	@Override
	public boolean setStart(int x, int y) {
		findCursor = true;

		update();
		return editable && !shiftHeld;
	}

	@Override
	public void setFinish(int x, int y) {
		FontMetrics fm = getImage().createGraphics().getFontMetrics(getFont());
		selectIndex = calculateIndexOfClick(getText(), fm, x-getX()-X_MARGIN);
		relativeX = x - getX();
		if(!shiftHeld) ignoreDismiss = false;
		update();
	}

	@Override
	public void setHeldLocation(int x, int y) {
		FontMetrics fm = getImage().createGraphics().getFontMetrics(getFont());
		selectIndex = calculateIndexOfClick(getText(), fm, x-getX()-X_MARGIN);
		relativeX = x - getX();
		update();
	}
	
	public void setDimensions(int width, int height) {
		super.setDimensions(width, height);
		resetBorder();
	}


}
