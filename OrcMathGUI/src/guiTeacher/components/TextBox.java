package guiTeacher.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * A multi-line TextField (does not scroll) Also incudes a description above the box defining the field
 * @author bnockles
 *
 */
public class TextBox extends TextField{

	private ArrayList<TextLine> lines;
	private int selectLine;
	private int cursorLine;
	private int selectIndexInLine;
	private int cursorIndexInLine;
	final int SPACING = 2;//vertical space between horizontal lines
	private boolean spaceTyped;//when the space button is pressed, algorithm is triggered to determine whether  the currentline formed a word that can be added to the previous line.

	/**
	 * 
	 * @param x x-coordinate within container
	 * @param y y-coordinate within container
	 * @param w width 
	 * @param h height (should be at least 30 for lines to be visible)
	 * @param text
	 */
	public TextBox(int x, int y, int w, int h, String text) {
		super(x,y,w,h,text);
		lines = new ArrayList<TextLine>();
		resetLinesAfter(0);
		update();
	}

	/**
	 * 
	 * @param x x-coordinate within container
	 * @param y y-coordinate within container
	 * @param w width
	 * @param h height (should be at least 30 for lines to be visible)
	 * @param text
	 * @param description shown above this TextBox
	 */
	public TextBox(int x, int y, int w, int h, String text, String description) {
		super(x,y,w,h,text, description);
		lines = new ArrayList<TextLine>();
		resetLinesAfter(0);
		update();
	}

	//used when dragging
	protected void identifyCursorLineUnderMouse(){
		FontMetrics fm = getImage().createGraphics().getFontMetrics(getFont());
		cursorLine = (relativeYClick-getInitialY(fm)+fm.getAscent())/getLineSpace(fm);
		cursorLine = (cursorLine>=lines.size())?lines.size()-1:cursorLine;
		if(!isShiftHeld())selectLine = cursorLine;
	}

	public void act(){
		identifyCursorLineUnderMouse();
		super.act();
	}

	public void resetSelect(){
		selectIndexInLine = cursorIndexInLine;
		selectLine = cursorLine;
		super.resetSelect();
	}

	private void resetLinesAfter(int i) {
		//		int index = indexStartingFromLine(i);
		int index = (lines.size()>0)?lines.get(i).getStartIndex():0;
		//DEBUG
		//			if (lines.size()>0)System.out.println("Attempthing to remove all lines after (and including) \""+lines.get(i).getLine()+"\" which starts at "+lines.get(i).getStartIndex());
		deleteLinesAfter(i);
		String remainingText;
		try{
			remainingText = getText().substring(index);				
		}catch(StringIndexOutOfBoundsException e){
			remainingText = "";	
		}
		String[] paragraphs = remainingText.split("\n",-1);
		//				System.out.println("Paragraphs are "+Arrays.toString(paragraphs));
		int start = 0;
		boolean beganWithNewline=false;
		if(paragraphs.length>0 && paragraphs[0].equals("") ){
			start++;
			beganWithNewline = true;
		}
		for(int pIndex = start; pIndex < paragraphs.length; pIndex++){
			String paragraph = paragraphs[pIndex]; 
			String[] words = paragraph.split(" ",-1);
			String line = "";
			int j = 0;
			if(j < words.length){
				if(beganWithNewline || pIndex != start){
					line = "\n"+words[j++];
				}else{
					line = words[j++];	
				}
			}
			do{
				while(j < words.length && lineFits(line+" "+words[j])){
					line += " "+words[j];
					j++;
				}			
				lines.add(new TextLine(line, index));
				//				System.out.println("line "+line+" has length "+line.length());
				index+=line.length();
				line ="";
			}while(j< words.length);
		}
		if(lines.size()<=0){
			lines.add(new TextLine("", 0));
		}
		//		System.out.println("Lines are "+lines);
	}


	private boolean lineFits(String line) {
		FontMetrics fm = getImage().createGraphics().getFontMetrics(getFont());
		//the space at the end of the line is not considered when checking if a line fits
		if(line.substring(line.length()-1).equalsIgnoreCase(" ")){
			line = line.substring(0,line.length()-1);
		}
		return fm.stringWidth(line) < getWidth()-(X_MARGIN+BORDER)*2;
	}



	private void deleteLinesAfter(int i) {
		while (lines.size() > i){
			lines.remove(i);
		}
	}


	private void prepare(Graphics2D g){

		g.setColor(Color.WHITE);
		g.fillRoundRect(BORDER+1,BORDER+DESCRIPTION_SPACE+1,getWidth()-2*BORDER-2,getHeight()-2*BORDER-DESCRIPTION_SPACE-2,8,8);
		//		clear();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(getFont());
		g.setColor(Color.black);
	}


	private int getInitialY(FontMetrics fm){
		return 0 + fm.getHeight()+SPACING+BORDER+DESCRIPTION_SPACE;
	}
	private int getLineSpace(FontMetrics fm){
		final int SPACING = 2;
		return fm.getDescent() + fm.getHeight()+SPACING;
	}

	protected void findCursor(FontMetrics fm){
		//		for(int lineIndex = 0; lineIndex < lines.size(); lineIndex++){
		//			if(cursorLine == lineIndex){
		if(cursorLine >= 0){
		int diff= lines.get(cursorLine).getDiff();
		String shownLine = lines.get(cursorLine).getShownLine();
		cursorIndexInLine = calculateIndexOfClick(shownLine, fm, relativeXClick)+diff;

		if(!isShiftHeld())selectIndexInLine = cursorIndexInLine;
		findCursor = false;
		setCursorToIndices();
		}else{
			System.err.println("The cursor is some how out of bounds.");
		}
		//				System.out.println("Found cursor at index "+getCursorIndex()+", on line that starts at "+lines.get(lineIndex).getStartIndex()+" and cursorIndex on line is "+cursorIndexInLine);
		//				break;
		//			}
		//		}
	}

	protected void setCursorToIndices(){
		int start = lines.get(cursorLine).getStartIndex();
		int cursor = cursorIndexInLine+start;//+paragraphs.length-1;
		setCursor(cursor);
		//				System.out.println("Found cursor in line at "+cursorIndexInLine+", start at "+start+", cursor = "+cursor);
		if(!isShiftHeld()){
			setSelect(cursor);
		}
	}

	/**
	 * Sets the cursorLine and selectLine to the location of the cursor
	 */
	protected void setIndicesToCursor(){
		int cursor = getCursorIndex();
		int i=0;
		while(i < lines.size() && cursor > lines.get(i).getLength()){
			cursor-=lines.get(i).getLength();
			i++;
		}
		if(i == lines.size())i = lines.size()-1;
		cursorIndexInLine = cursor;
		cursorLine = i;
		int sCursor = getSelectIndex();
		int j=0;
		while(j < lines.size() && sCursor > lines.get(j).getLength()){
			sCursor-=lines.get(j).getLength();
			j++;
		}
		if(j == lines.size())j = lines.size()-1;
		selectIndexInLine = sCursor;
		selectLine = j;
	}

	/**
	 * Set the text value of this TextBox
	 */
	public void setText(String s){
		text = s;
		setIndicesToCursor();

		resetLinesAfter(cursorLine);
		update();
	}

	/**
	 * 
	 * @param line index of line where cursor was
	 * @param index index of cursor relative to this line
	 * @return an array with the index of the newLine and newIndex on that line. Does not go out of bounds
	 */
	private int[] adjustCursor(int line, int index){
		while(line < lines.size()-1 && index > lines.get(line).getLength()){
			index-=lines.get(line).getLength();
			line++;
		}

		if(index > lines.get(line).getLength()){
			index =  lines.get(line).getLength();
		}
		int[] values = {line, index};
		return values;
	}

	protected void decreaseCursor(int spaces) {
		super.decreaseCursor(spaces);
		cursorIndexInLine -= spaces;


		while(cursorLine > 0 && cursorIndexInLine<0){

			cursorLine--;
			int previousLength = lines.get(cursorLine).getLength();
			cursorIndexInLine += previousLength;
		}
		if(cursorIndexInLine < 0){
			cursorIndexInLine=0;

		}
		if(!isShiftHeld()){

			selectLine = cursorLine;
			selectIndexInLine = cursorIndexInLine;
		}

	}

	protected void increaseCursor(int spaces){
		super.increaseCursor(spaces);
		if(!isShiftHeld()){

			cursorIndexInLine+=spaces;
			int[] values = adjustCursor(cursorLine, cursorIndexInLine);
			cursorLine = values[0];
			cursorIndexInLine = values[1];

			selectIndexInLine = cursorIndexInLine;
			selectLine = cursorLine;
		}else{
			cursorIndexInLine+=spaces;
			int[] values = adjustCursor(cursorLine, cursorIndexInLine);
			cursorLine = values[0];
			cursorIndexInLine = values[1];
		}
	}


	protected void putCursorBeforeSelect(){
		int selectIndex = getSelectIndex();
		int cursorIndex = getCursorIndex();
		if (selectIndex<cursorIndex){
			int temp = cursorIndex;
			int tempLine = cursorLine;
			int tempInLine = cursorIndexInLine;
			cursorIndex = selectIndex;
			cursorLine=selectLine;
			cursorIndexInLine = selectIndexInLine;

			selectIndex = temp;
			selectIndexInLine = tempInLine;
			selectLine = tempLine;
		}
		setCursor(cursorIndex);
		setSelect(selectIndex);
	}

	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			insert("\n");
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN){
			int change = (e.getKeyCode() == KeyEvent.VK_UP)?-1:1;
			verticalMove(change);
		}


		//				else if(e.getKeyCode() == KeyEvent.VK_UP){
		//					cursorLine=(cursorLine>0)?cursorLine-1:cursorLine;
		//					if(cursorIndexInLine>lines.get(cursorLine).getLength()){
		//						cursorIndexInLine = lines.get(cursorLine).getLength();
		//					}
		//					if(!isShiftHeld()){
		//						selectLine = cursorLine;
		//						selectIndexInLine = cursorIndexInLine;
		//					}
		//					setCursorToIndices();
		//					update();
		//				}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
		//					cursorLine=(cursorLine<lines.size()-1)?cursorLine+1:cursorLine;
		//					if(cursorIndexInLine>lines.get(cursorLine).getLength()){
		//						cursorIndexInLine = lines.get(cursorLine).getLength();
		//					}
		//					if(!isShiftHeld()){
		//						selectLine = cursorLine;
		//						selectIndexInLine = cursorIndexInLine;
		//					}
		//					setCursorToIndices();
		//		
		//				}
	}

	protected void verticalMove(int change) {
		int moveTo = cursorLine+change;
		if(moveTo>=0 && moveTo < lines.size()){

			TextLine line = lines.get(cursorLine);
			TextLine target = lines.get(moveTo);
			FontMetrics fm = getImage().createGraphics().getFontMetrics(getFont());
			int width = fm.stringWidth(line.getShownLine().substring(0,cursorIndexInLine-line.getDiff()));
			int count = 0+target.getDiff();
			while(count<target.getShownLine().length() && fm.stringWidth(target.getShownLine().substring(0,count-target.getDiff()))<width){
				count++;
			}
			cursorLine = moveTo;
			cursorIndexInLine = count;
			if(!isShiftHeld()){
				selectLine = cursorLine;
				selectIndexInLine = cursorIndexInLine;
			}
			setCursorToIndices();
		}
	}

	//called when 'cut' is performed
	protected void deleteAndInsert(String s){
		insert(s);
		selectIndexInLine = cursorIndexInLine;
		selectLine = cursorLine;
	}

	public void insert(String c){
		putCursorBeforeSelect();
		int selectIndex = getSelectIndex();
		int cursorIndex = getCursorIndex();
		if (canInsert(c) && cursorLine == selectLine){
			insertIntoLine(c);
			//			System.out.println(cursorIndex+", "+selectIndex);
			text=text.substring(0,cursorIndex)+c+text.substring(selectIndex,text.length());
			//			System.out.println("insert() method text is \""+text+"\"\nAND LINES ARE:\n"+lines);

			if(spaceTyped && cursorLine > 0){
				resetLinesAfter(cursorLine-1);
				increaseCursor(c.length());
				setIndicesToCursor();
				//				cursorIndexInLine-=1;
				//				selectIndexInLine-=1;
			}else{
				increaseCursor(c.length());
				increaseLineStarts(cursorLine, c.length());
				update();				

			}
		}else{
			String t = getText();
			text = t.substring(0,cursorIndex)+c+t.substring(selectIndex);
			resetLinesAfter(cursorLine);
			//			System.out.println("insert() method text is \""+text+"\"\nAND LINES ARE:\n"+lines);
			increaseCursor(c.length());
		}
		update();
		//				System.out.println("This is the text body: \""+getText()+"\"");
		//		System.out.println("These are the lines: "+lines);
	}

	/**
	 * 
	 * @param afterLine2 all lines after this, exclusive are updated
	 * @param length amount of spaces to increase
	 */
	private void increaseLineStarts(int afterLine, int length) {
		for(int i = afterLine+1; i< lines.size(); i++){
			lines.get(i).increaseStart(length);
		}
	}

	/**
	 * @precondition: @cursorLine = @selectLine and @cursorIndex <= @selectIndex
	 * @param s insertion string
	 * @return
	 */
	private boolean canInsert(String s) {
		if (s.equals("\n"))return false;

		String currentLine = lines.get(cursorLine).getLine();
		int checki = (cursorIndexInLine>=0)?cursorIndexInLine:0;
		int checkj = (selectIndexInLine>=0)?selectIndexInLine:0;
		checki = (checki <= currentLine.length())?checki:currentLine.length();
		checkj = (checkj <= currentLine.length())?checkj:currentLine.length();
		String newContent = currentLine.substring(0,checki)+s+currentLine.substring(checkj);
		return lineFits(newContent);
	}

	/**
	 * @precondition: @cursorLine = @selectLine and @cursorIndex <= @selectIndex
	 * @param s insertion string
	 */
	private void insertIntoLine(String s) {
		TextLine currentLine = lines.get(cursorLine);
		String currentString = currentLine.getLine();
		int start = cursorIndexInLine;//getCursorIndex() - currentLine.getStartIndex();
		int end = selectIndexInLine;//start + selectIndexInLine - cursorIndexInLine;

		String newContent = currentString.substring(0,start)+s+currentString.substring(end);
		currentLine.setLine(newContent);
	}

	public void remove(int low, int high){

		String t = getText();
		String removal = t.substring(low,high);
		//				System.out.println("Deletion is \""+removal+"\"\nThe cursorIndex in line is "+cursorIndexInLine+" and high is "+high+" while cursorIndex is "+getCursorIndex());
		putCursorBeforeSelect();
		text = t.substring(0, low)+t.substring(high);


		if(getSelectIndex() == getCursorIndex()){
			decreaseCursor(1);
			if(removal.contains("\n")){
				setIndicesToCursor();
				//				System.out.println("Removed lines contained newline char. line is now "+cursorLine+" while index is "+cursorIndexInLine);
			}
		}else{
			selectLine = cursorLine;
			selectIndexInLine = cursorIndexInLine;
			setSelect(getCursorIndex());
		}
		int resetFrom = (cursorLine>0)?cursorLine-1:cursorLine;
		int pl = lines.get(resetFrom).getLength();
		resetLinesAfter(resetFrom);
		int al = lines.get(resetFrom).getLength();
		if(pl != al){
			setIndicesToCursor();
		}
		//finally, check to see if cursor is out of bounds
		if(cursorLine >= lines.size()){
			System.out.println("Used a line of code you thought was worthless. Line 405");
			cursorLine = lines.size()-1;
		}
		else 
			if(cursorIndexInLine > lines.get(cursorLine).getLength()){
				cursorIndexInLine-=lines.get(cursorLine).getLength();
				cursorLine++;
				selectLine=cursorLine;
				selectIndexInLine = cursorIndexInLine;
			}
		update();
		//		System.out.println("This is the text body: \""+getText()+"\"");
	}

	private void highlight(Graphics2D g, FontMetrics fm, int y, String line, int lineIndex, int highlightLineStart, int highlightLineEnd, int highlightStart,int highlightEnd){
		if(lineIndex >= highlightLineStart && (selectLine!=cursorLine || selectIndexInLine != cursorIndexInLine) && lineIndex <= highlightLineEnd){
			//adjust highlight to match shown line
			highlightStart-=lines.get(lineIndex).getDiff();
			highlightEnd-=lines.get(lineIndex).getDiff();

			if(lineIndex == highlightLineStart && lineIndex == highlightLineEnd){
				highlight(g, fm, highlightStart,highlightEnd, line, y);
			}
			if(lineIndex > highlightLineStart && lineIndex == highlightLineEnd){
				highlight(g, fm, 0,highlightEnd, line, y);
			}
			if(lineIndex > highlightLineStart && lineIndex < highlightLineEnd){
				highlight(g, fm, 0,line.length(), line, y);
			}
			if(lineIndex == highlightLineStart && lineIndex < highlightLineEnd){
				highlight(g, fm, highlightStart,line.length(), line, y);
			}
		}
	}

	public void update(Graphics2D g2){

		BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = buffer.createGraphics();
		prepare(g);
		FontMetrics fm = g.getFontMetrics();
		if(lines != null){
			int y = getInitialY(fm);
			if(findCursor)findCursor(fm);

			int highlightLineStart;
			int highlightStart;
			int highlightLineEnd;
			int highlightEnd;

			if(selectLine < cursorLine){
				highlightLineStart = selectLine;
				highlightStart = selectIndexInLine;
				highlightLineEnd = cursorLine;
				highlightEnd = cursorIndexInLine;
			}else if (selectLine == cursorLine){
				highlightLineStart = selectLine;
				highlightLineEnd = cursorLine;
				highlightStart = (selectIndexInLine< cursorIndexInLine)?selectIndexInLine:cursorIndexInLine;
				highlightEnd = (selectIndexInLine> cursorIndexInLine)?selectIndexInLine:cursorIndexInLine;
			}else{
				highlightLineStart = cursorLine;
				highlightLineEnd = selectLine;
				highlightEnd = selectIndexInLine;
				highlightStart = cursorIndexInLine;
			}

			for(int lineIndex = 0; lineIndex < lines.size(); lineIndex++){
				String line = lines.get(lineIndex).getShownLine();
				highlight(g,fm,y-fm.getHeight(),line,lineIndex,highlightLineStart,highlightLineEnd,highlightStart,highlightEnd);

				if(isCursorShowing() && lineIndex == cursorLine && (cursorLine==selectLine && cursorIndexInLine == selectIndexInLine)){

					drawCursor(g, fm, y);
				}
				g.setColor(getForeground());
				g.drawString(line, X_MARGIN, y);
				y += getLineSpace(fm);
			}
		}
		//		drawBorder(g);
		g2.drawImage(buffer, 0, 0, null);
	}

	protected void selectAll(){
		super.selectAll();
		setIndicesToCursor();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		try{
			String line = lines.get(cursorLine).getLine();			

			if(c == ' ' && (cursorIndexInLine < line.indexOf(' ', 1))){
				spaceTyped = true;
				//			System.out.println("The first space in a line was typed.");
			}else{
				spaceTyped = false;
			}
			super.keyTyped(e);
		}catch (Exception ex){
			System.out.println("The cursor is out of bounds. Click to redefine location.");
		}
	}

	protected void setCursors(int cursorPoint, int selectPoint) {
		super.setCursors(cursorPoint, selectPoint);
		setIndicesToCursor();
	}

	private void drawCursor(Graphics2D g, FontMetrics fm, int y) {
		g.setColor(Color.black);
		int end = cursorIndexInLine-lines.get(cursorLine).getDiff();
		if(end < 0){

			//			System.out.println("Cursor needs to be shown behind invisible characters. cursorIndexInLine is "+cursorIndexInLine+" while diff is "+lines.get(cursorLine).getDiff());
		}
		try{
			int x = fm.stringWidth(lines.get(cursorLine).getShownLine().substring(0,end))+X_MARGIN;			
			g.drawLine(x, y-fm.getHeight(), x, y);
		}catch (StringIndexOutOfBoundsException e){
			System.out.println("Cursor moved out of bounds in line. Click to redraw");
		}
	}

	@Override
	public boolean setStart(int x, int y) {
		findCursor = true;
		relativeXClick = x - getX();
		relativeYClick = y - getY();
		relativeX = relativeXClick;
		relativeY = relativeXClick;
		identifyCursorLineUnderMouse();
		update();
		return isEditable() && !isShiftHeld();
	}

	@Override
	public void setFinish(int x, int y) {
		findCursor = true;
		relativeXClick = x - getX();
		relativeYClick = y - getY();
		relativeX = relativeXClick;
		relativeY = relativeXClick;
		identifyCursorLineUnderMouse();
		update();
		ignoreDismiss = false;
	}

	@Override
	public void setHeldLocation(int x, int y) {
		relativeXClick = x - getX();
		relativeYClick = y - getY();
		relativeX = relativeXClick;
		relativeY = relativeXClick;
		setShiftHeld(true);
		identifyCursorLineUnderMouse();
		findCursor = true;
		update();
	}

	protected class TextLine{

		private String line;
		private int startIndex;
		private String shownLine;//initial Space is never shown

		protected TextLine(String s, int startIndex){
			this.startIndex = startIndex;
			setLine(s);
		}

		public void increaseStart(int length) {
			startIndex+=length;
		}

		public int getDiff() {
			return line.length()-shownLine.length();
		}

		public String getShownLine(){
			return shownLine;
		}

		public String getLine() {
			return line;
		}

		public void setLine(String line) {
			this.line = line;
			this.shownLine = line.replaceFirst("\n", "");
			if(shownLine.length()>0 && shownLine.substring(0, 1).equals(" ")){
				this.shownLine = shownLine.substring(1);
			}
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

		public String toString(){
			return "\""+line+"\" -starts at index "+startIndex+", length "+line.length();
		}
	}

	public void setDimensions(int width, int height) {
		super.setDimensions(width, height);
		resetLinesAfter(0);
	}

}
