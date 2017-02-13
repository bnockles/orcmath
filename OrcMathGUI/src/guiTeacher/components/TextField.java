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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.KeyedComponent;
import guiTeacher.interfaces.TextComponent;

public class TextField extends StyledComponent implements KeyedComponent,Clickable, Runnable, TextComponent{

	//FIELDS
	private String text;
	private Font font;
	private int size;
	
	public static final int CURSOR_INTERVAL = 500;
	public static final int DESCRIPTION_SPACE = 15;
	public static final int X_MARGIN = 5;
	public static final int BORDER = 2;
	public static final Color BORDER_COLOR = new Color(80,80,80);
	private boolean running;
	protected boolean cursorShowing;
	private int cursorIndex;
	protected int relativeX;
	protected int relativeY;
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
	
	public TextField(int x, int y, int w, int h, String text) {
		super(x, y-DESCRIPTION_SPACE, w, h+DESCRIPTION_SPACE);
		this.text = text;
		setDefaults();
		description = null;
		update();
	}
	
	public TextField(int x, int y, int w, int h, String text, String description) {
		super(x, y-DESCRIPTION_SPACE, w, h+DESCRIPTION_SPACE);
		this.text = text;
		setDefaults();
		this.description = description;
		update();
	}
	
	private void setDefaults(){
		inputType = INPUT_TYPE_PLAIN;
		inputRangeMin =0;
		inputRangeMax =0;
		inputLength = 0;
		editable = true;
		font = getBaseFont();
		size = 20;
		running = false;
		cursorIndex = getText().length();
	}

	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		update();
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
	
	@Override
	public void update(Graphics2D g) {
		clear();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(getForeground());
		g.setFont(getFont());
		FontMetrics fm = g.getFontMetrics();
		if(getText() != null) g.drawString(getText(), X_MARGIN, getHeight()-fm.getDescent());
		if(findCursor){
			int check = 0;
			while(check < getText().length() && fm.stringWidth(getText().substring(0,check+1)) < relativeX){
				check++;
			}
			cursorIndex = check;
			findCursor = false;
			cursorShowing  = true;
		}
		if(cursorShowing && running){
			g.setColor(Color.black);
			int base = getHeight()-fm.getDescent();
//			if(cursorIndex> getText().length())cursorIndex = getText().length();
			int x = fm.stringWidth(getText().substring(0,cursorIndex))+X_MARGIN;
			g.drawLine(x, base, x, base - fm.getHeight());
		}
		drawBorder(fm, g);
	}
	
	public void drawBorder(FontMetrics fm, Graphics2D g){
		Color bc = (running)? getActiveBorderColor():getInactiveBorderColor();
		g.setColor(bc);
		g.setStroke(new BasicStroke(BORDER));
		g.drawRoundRect(BORDER,BORDER+DESCRIPTION_SPACE,getWidth()-2*BORDER,getHeight()-2*BORDER-DESCRIPTION_SPACE,8,8);
		if(description != null){
			g.setColor(getForeground());
			g.drawString(description, BORDER, DESCRIPTION_SPACE-fm.getDescent());
		
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		String t = getText();
		if(inputType == INPUT_TYPE_PLAIN && c >=32 && c <127){
			
			text = t.substring(0,cursorIndex)+c+t.substring(cursorIndex,t.length());
			cursorIndex++;
			update();
		}else if(inputType == INPUT_TYPE_NUMERIC && (c== 46 || c >=48 && c <57)){
			
			text = t.substring(0,cursorIndex)+c+t.substring(cursorIndex,t.length());
			cursorIndex++;
			update();
			
		}else if(c==127 || c==8){
			//delete is pressed
//			System.out.println("Delete was pressed while cursor was at position "+cursorIndex);
			if(cursorIndex-1 >=0){
				text = t.substring(0, cursorIndex-1)+t.substring(cursorIndex,t.length());
				cursorIndex= (cursorIndex >0)?cursorIndex-1:0;
//				System.out.println("Cursor now at position "+cursorIndex);
			}
			update();
		}else if(inputType == INPUT_TYPE_CUSTOM && t.length()<inputLength && c>=inputRangeMin && c<=inputRangeMax){
			text = t.substring(0,cursorIndex)+c+t.substring(cursorIndex,t.length());
			cursorIndex++;
			update();
		}
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			cursorIndex = (cursorIndex >0)?cursorIndex-1:0;
			update();
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			cursorIndex = (cursorIndex <getText().length())?cursorIndex+1:getText().length();
			update();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	protected void setCursor(int x){
		if(x<0)cursorIndex = 0;
		else if(x>getText().length())cursorIndex = getText().length();
		else cursorIndex = x;
	}
	
	public boolean isHovered(int x, int y) {
		boolean b =  x > getX() && x < getX() + getWidth() && y > getY() && y < getY() + getHeight();
		if(b){
			relativeX = x - getX();
			relativeY = y - getY();
		}
		return b;
	}


	public void setFocus(boolean b) {
		if(b && !running && editable){
			
			running = true;
			Thread cursor = new Thread(this);
			cursor.start();
		}else if(!b){
			running = false;
		}
	}
	
	public void setText(String s){
		this.text = s;
		update();
	}
	
	public String getText(){
		return text;
	}
	
	public void setSize(int size){
		this.size = size;
		update();
	}
	
	public void setFont(Font font){
		this.font = font;
	}
	
	public Font getFont(){
		return font;
	}
	
	public int getSize(){
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
		findCursor = true;
	}

	public boolean isRunning(){
		return running;
	}
	
	@Override
	public void setAction(Action a) {
		// TODO Auto-generated method stub
		
	}
	
	
}
