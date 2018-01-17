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
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.Dragable;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.TextComponent;
import guiTeacher.userInterfaces.Screen;

/**
 * Serves as a basic table with scrolling, drag-to-move rows and editing values
 * @author bnockles
 *
 */
public class SimpleTable extends StyledComponent implements Clickable, Dragable{

	//Since clicking on a table moves focus, the table must have access to the direction of focus
	private FocusController parentScreen;

	protected TableHeader columns;
	protected ArrayList<SimpleTableRow> rows;
	private SimpleTableRow hoveredRow;
	
	
	protected BufferedImage trashClosed;
	protected BufferedImage trashOpen;
	private boolean trashClickedOnce;//helps for identifying double-click on trash
	private SimpleTableRow movingRow;
	private Component movementGhost;
	private int hoverStartY;
	private int origIndex;
	private int moveToIndex;
	private boolean trashHovered; 
	private boolean multilineRows;
	
	protected int xRelative;
	protected int yRelative;
	private Color highlight;
	private int lastHeight;//a variable that clears the table when its size is reduced (so old rows get deleted)

	public static final int HOVER_BUTTON_WIDTH= 12;
	public static final int EDIT_COLUMN = 20;
	public static final int X_MARGIN = 2;

	/**
	 * 
	 * @param fc the FocusController (Screen) in which this table is positioned 
	 * @param x the x coordinate within the container
	 * @param y the y coordinate within the container
	 * @param h the height of this table (which is scrollable) (width is summed from columns widths)
	 * @param columns a TableHeader object defining each column of the SimpleTable
	 */
		public SimpleTable(FocusController fc, int x, int y, int h, TableHeader columns) {
			super(x, y, SimpleTable.widthFromColumns(columns), h);
			setUp(fc, getWidth(), columns, false);
		}
	
	private static int widthFromColumns(TableHeader header) {
		int sum = EDIT_COLUMN;
		for(int i : header.getColumnWidths()){
			sum += i;
		}
		return sum;
	}

	/**
 * 
 * @param fc the FocusController (Screen) in which this table is positioned 
 * @param x the x coordinate within the container
 * @param y the y coordinate within the container
 * @param w the width of this table (columns will be clipped to this width)
 * @param h the height of this table (which is scrollable)
 * @param columns a TableHeader object defining each column of the SimpleTable
 */
	public SimpleTable(FocusController fc, int x, int y, int w, int h, TableHeader columns) {
		super(x, y, w, h);
		setUp(fc, w, columns, false);
	}
	
	/**
	 * 
 * @param fc the FocusController (Screen) in which this table is positioned 
 * @param x the x coordinate within the container
 * @param y the y coordinate within the container
 * @param w the width of this table (columns will be clipped to this width)
 * @param h the height of this table (which is scrollable)
 * @param columns a TableHeader object defining each column of the SimpleTable
	 * @param multiline whether or not rows expand for multiple lines of text
	 */
	public SimpleTable(FocusController fc, int x, int y, int w, int h, TableHeader columns, boolean multiline) {
		super(x, y, w, h);
		setUp(fc, w, columns, multiline);
	}
	
	private void setUp(FocusController fc,int w,TableHeader columns, boolean multiline){
		highlight = getHighlightColor();
		multilineRows = multiline;
		hoveredRow = null;
		parentScreen =fc;
		trashHovered = false;
		this.columns = columns;
		initTrashIcons();
		movementGhost = new Component(0,0,w,columns.getRowHeight()) {
			
			@Override
			public void update(Graphics2D g) {
				g.setColor(new Color(100,100,100,100));
				g.fillRect(0, 0, w, columns.getRowHeight());
			}
		};
		movementGhost.update();
		rows= new ArrayList<SimpleTableRow>();
		update();
	}

	protected void initTrashIcons(){
		trashClickedOnce = false;
		trashClosed = new BufferedImage(EDIT_COLUMN,columns.getRowHeight(),BufferedImage.TYPE_INT_ARGB);
		trashOpen = new BufferedImage(EDIT_COLUMN,columns.getRowHeight(),BufferedImage.TYPE_INT_ARGB);
		drawTrash(trashClosed.createGraphics(), false);
		drawTrash(trashOpen.createGraphics(), true);
	}
	
	public void clearRows(){
		rows.removeAll(rows);
		clear();
	}
	
	/**
	 * Adds a row to the table. The method automatically creates a SimpleTableRow with all necessary parameters to fit the rows
	 * @param values
	 */
	public void addRow(String[] values) {
		if(values.length == columns.getColumnDescriptions().length){	
			rows.add(new SimpleTableRow(this, values, columns.getColumnEditable(), columns.getColumnWidths(), columns.getRowHeight(), multilineRows));
			clear();//updates height
		}
	}
	
	public ArrayList<SimpleTableRow> getRows(){
		return rows;
	}
	
	/**
	 * Sets the content of a row to a custom text element. 
	 * This method changes the most recently-added row
	 * It is intended primarily to change text labels into links
	 * @param column
	 * @param component
	 */
	public void setColumnContent(int column, TextComponent component) {
		SimpleTableRow row = rows.get(rows.size()-1);
		row.setColumn(column, component);
		update();
	}
	
	/**
	 * changes the editable value of the last-added row
	 * @param edit
	 */
	public void setRowEdit(boolean[] edit) throws MatchingLengthException{
		if(edit.length == columns.getColumnEditable().length){
			SimpleTableRow row = rows.get(rows.size()-1);
			for(int i = 0; i< edit.length; i++){
				row.resetEdit(i, edit[i], multilineRows);
			}
		}else{
			throw new MatchingLengthException("You cannot change the mutability of a row using a boolean array of length "+edit.length+" because there are "+columns.getColumnEditable().length+" columns. ");
		}
	}

	
	public class MatchingLengthException extends Exception{

		public MatchingLengthException(String string) {
			super(string);
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = -2264179106739745725L;
		
	}
	
	
	public Color getHighlight() {
		return highlight;
	}

	public void setHighlight(Color highlight) {
		this.highlight = highlight;
	}

	public int getHeight(){
		if(columns != null){
			return (1+rows.size())*columns.getRowHeight();
		}else{
			
			return super.getHeight();
		}
	}

	private void drawTrash(Graphics2D g, boolean trashHovered){
		g.setColor(new Color(122,124,139));
		
		
		//base
		int bx =2;
		int by = 8;
		int lidWidth = EDIT_COLUMN-bx*2;
		int rim = 2;
		int rimh=2;
		int slant = 2;
		int bh = columns.getRowHeight()-2;
		int[] xs = {bx, bx+lidWidth,bx+lidWidth,bx+lidWidth-rim,bx+lidWidth-rim-slant,bx+rim+slant,bx+rim,bx};
		int[] ys = {by,by,by+rimh,by+rimh,bh,bh,by+rimh,by+rimh};
		Polygon trashBase = new Polygon(xs, ys, xs.length);
		g.fill(trashBase);
		
		//lid
		if(trashHovered){
			int lx = bx-1;
			int ly = by-1;
			
			int[] xls = {lx,lx+lidWidth+1,lx+lidWidth-2,lx+lidWidth/2-1,lx+1};
			int[] yls = {ly,ly-4,ly-6,ly-5,ly-3};
			Polygon trashLid = new Polygon(xls, yls, xls.length);
			g.fill(trashLid);
		}else{
			int lx = bx-1;
			int ly = by-1;
			int[] xls = {lx,lx+lidWidth+2,lx+lidWidth,lx+lidWidth/2+1,lx+1};
			int[] yls = {ly,ly,ly-2,ly-4,ly-2};
			Polygon trashLid = new Polygon(xls, yls, xls.length);
			g.fill(trashLid);
			
		}
	}
	
	@Override
	public void update(Graphics2D g2) {	
		BufferedImage buffer = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = buffer.createGraphics();
		if(getHeight() < lastHeight){
			//if rows have been deleted, this clears the residual ghost of their image
			clear();
		}
		lastHeight = getHeight();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		if(trashOpen != null){
			if(trashHovered) g.drawImage(trashOpen, 0, 0, null);
			else g.drawImage(trashClosed, 0, 0, null);
		}
		GradientPaint tTob = new GradientPaint(0,0, getTabColor(), 0,getTabHeight(), getTabShade());
		
		Paint current = g.getPaint();
		g.setPaint(tTob);
		g.fillRect(EDIT_COLUMN, 0, getWidth(), columns.getRowHeight());
		g.setPaint(current);
		g.setColor(getForeground());
		g.setFont(getTabFont());
		FontMetrics fm = g.getFontMetrics();


		int rh = columns.getRowHeight();

		int y = rh-fm.getDescent();

		//draw up, left, right borders
		int tBorder = getTableBorder();
		BasicStroke borderStroke = new BasicStroke(tBorder);
		BasicStroke hoverStroke = new BasicStroke(3);
		if(tBorder >0){
			g.setStroke(borderStroke);
			g.drawLine(EDIT_COLUMN,0,getWidth(), 0);
			g.drawLine(getWidth()-tBorder,0,getWidth()-tBorder, rh*(rows.size()+1));
			//draw vertical borders
			int borderX=EDIT_COLUMN;
			for(int w: columns.getColumnWidths()){
				g.drawLine(borderX,0,borderX, rh*(rows.size()+1));
				borderX+=w;
			}
		}
		drawHeaderRow(g, columns.getColumnDescriptions(), y);
		int borderY = rh;
		if(tBorder>0){
			g.drawLine(EDIT_COLUMN, borderY-tBorder, getWidth(), borderY-tBorder);
		}
		y+=fm.getDescent();
		int hoverx=(EDIT_COLUMN-HOVER_BUTTON_WIDTH)/2;
		int hovery = (columns.getRowHeight()-HOVER_BUTTON_WIDTH)/2;
		for(int j = 0; j < rows.size(); j++){
			SimpleTableRow row = rows.get(j);
			int x = EDIT_COLUMN;
			
			//draw hover button
			g.setStroke(hoverStroke);
			row.drawButton(g,hoverx,(y+hovery));
			g.setStroke(borderStroke);
			
			//draw highlight when hovered
			if(row.isHovered()){
				Color previous= g.getColor();
				g.setColor(getHighlight());
				g.fillRect(EDIT_COLUMN, y, getWidth(), columns.getRowHeight());
				g.setColor(previous);
			}
			
			for(int i = 0; i < row.getValues().length; i++){
				TextComponent tc = row.getValues()[i];
				int useY = y;
				int increase = columns.getColumnWidths()[i];
				if(tc instanceof TextField){
					useY-=TextField.DESCRIPTION_SPACE;
				}else if(tc instanceof TextLabel){
					x+=2;
					increase-=2;
					
				}
				
				g.drawImage(tc.getImage(), x, useY, null);
				x+=increase;
			}
			//			drawRow(g, str.getValues(), y);
			borderY+=rh;
			if(tBorder>0){
				g.drawLine(EDIT_COLUMN, borderY-tBorder, getWidth(), borderY-tBorder);
			}
			//add a hard line to show where insert will happen
			if(moveToIndex-1 == j && moveToIndex <=origIndex){
				g.setStroke(new BasicStroke(2));
				Color c = g.getColor();
				g.setColor(getTabColor());
				g.drawLine(EDIT_COLUMN, borderY-2, getWidth(), borderY-2);
				g.setStroke(borderStroke);
				g.setColor(c);
			}else if(moveToIndex==j && moveToIndex > origIndex){
				g.setStroke(new BasicStroke(2));
				Color c = g.getColor();
				g.setColor(getTabColor());
				g.drawLine(EDIT_COLUMN, borderY-2, getWidth(), borderY-2);
				g.setStroke(borderStroke);
				g.setColor(c);
			}
			y+=columns.getRowHeight();
		}
		//if borders run off page, draw horizontal line along bottom
		if(borderY>getHeight() && tBorder>0){
			g.drawLine(EDIT_COLUMN, getHeight()-tBorder, getWidth(), getHeight()-tBorder);
		}
		g2.drawImage(buffer, 0, 0, null);
	}


	private void drawHeaderRow(Graphics2D g, String[] values, int y) {
		FontMetrics fm = g.getFontMetrics();
		int x = X_MARGIN+EDIT_COLUMN;
		int[] widths = columns.getColumnWidths();
		Color cur = g.getColor();
		g.setColor(getHeaderColor());
		if(widths.length == values.length){
			for(int i = 0; i < widths.length; i++){
				if(fm.stringWidth(values[i]) < widths[i]-X_MARGIN*2){
					g.drawString(values[i], x, y);
				}else{
					int cutoff = 0;
					while(cutoff <= values[i].length() && fm.stringWidth(values[i].substring(0, cutoff)) < widths[i]-X_MARGIN*2){
						cutoff++;
					}
					g.drawString(values[i].substring(0, cutoff), x, y);
				}
				x+=widths[i];
			}
		}
		g.setColor(cur);
	}

	public String[] getAllColumnValuesAtIndex(int columnIndex){
		String[] values = new String[rows.size()];
		int i=0;
		for(SimpleTableRow r: rows)
		{
			values[i]=r.getValue(columnIndex);
			i++;
		}
		return values;
	}

	public int[] getAllColumnIntValuesAtIndex(int columnIndex) {
		int[] values = new int[rows.size()];
		int i=0;
		for(SimpleTableRow r: rows)
		{
			try{
				values[i]=Integer.parseInt(r.getValue(columnIndex));
			}catch(NumberFormatException e){
				values[i] = 1;
			}
			i++;
		}
		return values;
	}

	@Override
	public boolean isHovered(int x, int y) {
		boolean hovered = x>getX() && x < getX()+getWidth() && y >getY() && y < getY() +getHeight();
		if(hovered){
			xRelative = x - getX();
			yRelative = y - getY();
		}

		return hovered;
	}

	@Override
	public void hoverAction() {
		if(xRelative < EDIT_COLUMN){
			int hoverRow = yRelative/columns.getRowHeight()-1;
			if(hoverRow >=0 && hoverRow < rows.size()){
				if(hoveredRow!= rows.get(hoverRow)){
					if(xRelative < EDIT_COLUMN && hoveredRow != null){
						hoveredRow.setHover(false);					
					}
					hoveredRow = rows.get(hoverRow);
					if(xRelative < EDIT_COLUMN){
						hoveredRow.setHover(true);					
					}

				}
			}else if(hoveredRow != null){
				hoveredRow.setHover(false);
				hoveredRow = null;
			}
			update();
		}else if(xRelative > EDIT_COLUMN){
			unHover();
			changeMouse();
		}else {
			unHover();
		}

	}
	
	/**
	 * changes mouse cursor depending on what is being hovered
	 */
	private void changeMouse(){
			int hoveredRow = yRelative/columns.getRowHeight();
			int hoveredColumn = 0;
			int widthToClick = 0;
			int[] widths = columns.getColumnWidths();
			while(hoveredColumn<widths.length && widthToClick+widths[hoveredColumn]<xRelative){
				widthToClick+=columns.getColumnWidths()[hoveredColumn];
				hoveredColumn++;
			}
			//		System.out.println("xRelative = "+xRelative+", yRelative = "+yRelative+", SimpleTable.java clickedRow = "+clickedRow+", clickedColumn = "+clickedColumn);
			if(hoveredColumn >= widths.length){
				hoveredColumn = widths.length-1;
			}
			if(hoveredRow !=0 && hoveredRow-1 < rows.size()){
				//NOTE: The following method call only set the pointer. Other hovering actions (i.e. hover text) are ignored
				rows.get(hoveredRow-1).columnHovered(hoveredColumn, xRelative-(hoveredRow+1)*columns.getRowHeight(),yRelative-widthToClick);
				update();
			}
		
	}

	private void unHover(){
		if(hoveredRow != null){
			hoveredRow.setHover(false);
		hoveredRow = null;
		update();
		}
	}
	
	
	@Override
	public void act() {
		int clickedRow = yRelative/columns.getRowHeight();
		if(yRelative < columns.getRowHeight() && xRelative < EDIT_COLUMN){
			if(!trashClickedOnce){
				trashClickedOnce = true;
				trashHovered=true;
				Thread waitForDoubleClick = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(400);
							trashClickedOnce=false;
							trashHovered = false;
							update();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				waitForDoubleClick.start();
				update();		
			}else{
				rows.removeAll(rows);
				trashHovered = false;
				trashClickedOnce=false;
				update();
			}
		}
		if(xRelative >EDIT_COLUMN){
			int clickedColumn = 0;
			int widthToClick = 0;
			int[] widths = columns.getColumnWidths();
			while(clickedColumn<widths.length && widthToClick+widths[clickedColumn]<xRelative){
				widthToClick+=columns.getColumnWidths()[clickedColumn];
				clickedColumn++;
			}
			//		System.out.println("xRelative = "+xRelative+", yRelative = "+yRelative+", SimpleTable.java clickedRow = "+clickedRow+", clickedColumn = "+clickedColumn);
			if(clickedColumn >= widths.length){
				clickedColumn = widths.length-1;
			}
			if(clickedRow !=0 && clickedRow-1 < rows.size()){
				//in rows, index 0 is actually row 1 of the table (under the header)
				rows.get(clickedRow-1).columnClicked(clickedColumn, xRelative-(clickedRow+1)*columns.getRowHeight(),yRelative-widthToClick);
			}
		}

	}

	public FocusController getFocusController(){
		return parentScreen;
	}

	public void setInputType(int column, int inputTypeNumeric) {
		if(columns.columnIsEditable(column)){

			for(SimpleTableRow str: rows){
				str.setInputType(column, inputTypeNumeric);
			}
		}

	}

	@Override
	public boolean setStart(int x, int y) {
		if(hoveredRow != null && xRelative > getX() && xRelative < EDIT_COLUMN){
			movingRow = hoveredRow;
			origIndex = rows.indexOf(movingRow);
			hoverStartY=y;
			movementGhost.setX(x);
			movementGhost.setY(y);
			((Screen)parentScreen).addObject(movementGhost);
			return true;
		}
		return false;
	}

	@Override
	public void setFinish(int x, int y) {
		//remove ghost
		((Screen)parentScreen).remove(movementGhost);
		//close trash
		if(trashHovered){
			rows.remove(origIndex);
			trashHovered=false;
		}else{
			
			//calculate difference between starty and finishy
			
			
			if(moveToIndex>=0 && moveToIndex<rows.size()+1)rows.add(moveToIndex,rows.remove(origIndex));
		}
		moveToIndex = -1;
		update();
	}

	@Override
	public void setHeldLocation(int x, int y) {
			//draw ghost 
			movementGhost.setX(x);
			movementGhost.setY(y);
			//calculate moveToIndex
			int rowDiff = (y-hoverStartY)/columns.getRowHeight();
			moveToIndex = origIndex+rowDiff;
			if(origIndex+rowDiff <0){
				moveToIndex = 0;
				trashHovered = true;
			}
			else if(origIndex+rowDiff > rows.size()){
				moveToIndex = rows.size()-1;
				trashHovered = false;
			}else{
				trashHovered = false;
			}
			
			update();
			
	}

	@Override
	public void setAction(Action a) {
		// TODO Auto-generated method stub
		
	}

	

}
