package guiTeacher.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TextBox extends TextField{

	private ArrayList<TextLine> lines;
	private int selectLine;
	private int cursorLine;
	private int selectIndexInLine;
	private int cursorIndexInLine;
	final int SPACING = 2;//vertical space between horizontal lines

	public TextBox(int x, int y, int w, int h, String text) {
		super(x,y,w,h,text);
		lines = new ArrayList<TextLine>();
		resetLinesAfter(0);
		update();
	}

	public TextBox(int x, int y, int w, int h, String text, String description) {
		super(x,y,w,h,text, description);
		lines = new ArrayList<TextLine>();
		resetLinesAfter(0);
		update();
	}

	protected void identifyCursorLineUnderMouse(){
		FontMetrics fm = getImage().createGraphics().getFontMetrics(getFont());
		cursorLine = (relativeY-getInitialY(fm)+fm.getAscent())/getLineSpace(fm);
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
				remainingText = getText().substring(--index);	
			}
			//remaining text always starts a new line, so including a leading '\n' is unecessary
			if(remainingText.startsWith("\n")){
				remainingText = remainingText.replaceFirst("\n", "");
				index+=1;
			}
			String[] paragraphs = remainingText.split("\n",-1);
			for(int pIndex = 0; pIndex < paragraphs.length; pIndex++){
				String paragraph = paragraphs[pIndex]; 
				String[] words = paragraph.split(" ");
				String line = "";
				int j = 0;
//				if(words.length>0)System.out.println("First word in line is \""+words[0]+"\"");
				while(j < words.length){

					//don't print first space on a new line (unless there are many)
					if(words[j].equals(" ")|| words[j].equals("")){
						j++;
					}
					if(j < words.length){
						
						line = words[j++];
					}
					while(j < words.length && lineFits(line+" "+words[j])){
						line += " "+words[j];
						j++;
					}
					lines.add(new TextLine(line, index));
					index+=line.length();
				}
				index +=1;//add 2 to accommodate newline char
			}
		
	}


	private boolean lineFits(String line) {
		FontMetrics fm = getImage().createGraphics().getFontMetrics(getFont());
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
		for(int lineIndex = 0; lineIndex < lines.size(); lineIndex++){
			if(cursorLine == lineIndex){
				cursorIndexInLine = calculateIndexOfClick(lines.get(lineIndex).getLine(), fm, relativeX);
				if(!isShiftHeld())selectIndexInLine = cursorIndexInLine;
				findCursor = false;
				String[] paragraphs = getText().substring(lines.get(cursorLine).getStartIndex()).split("\n",-1);
				System.out.println(paragraphs.length+ " paragraphs beign deleted");
				int cursor = cursorIndexInLine+lines.get(cursorLine).getStartIndex()+paragraphs.length-1;
				setCursor(cursor);
				if(!isShiftHeld()){
					setSelect(cursor);
				}
				break;
			}
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

	public void setText(String s){
		text = s;
		setIndicesToCursor();

		resetLinesAfter(cursorLine);
		update();
		//does not automatically update for the sake of 
	}

	/**
	 * 
	 * @param line line where cursor was
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
		System.out.println("TextBox.java cursor = "+getCursorIndex()+" while cursorIndexInLine = "+cursorIndexInLine);
		while(cursorLine > 0 && cursorIndexInLine<0){
			cursorLine--;
			cursorIndexInLine += lines.get(cursorLine).getLength();

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
			System.out.println("Inserting a new line");
			//			cursorLine++;
			//			lines.add(cursorLine,new TextLine("", lines.get(cursorLine-1).getStartIndex()+lines.get(cursorLine-1).getLength()));
			insert("\n");
		}
	}

	public void insert(String c){
		putCursorBeforeSelect();
		int selectIndex = getSelectIndex();
		int cursorIndex = getCursorIndex();
		if (canInsert(c) && cursorLine == selectLine){
			insertIntoLine(c);
			text=text.substring(0,cursorIndex)+c+text.substring(selectIndex,text.length());
			increaseCursor(c.length());
			update();
		}else{
			String t = getText();
			text = t.substring(0,cursorIndex)+c+t.substring(selectIndex);
			resetLinesAfter(cursorLine);
			increaseCursor(c.length());
			//			if(c.indexOf("\n") >=0){
			//				System.out.println("The insertion contains a newline character");
			//				setIndicesToCursor();
			//			}
		}
		update();
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
		String newContent = currentString.substring(0,cursorIndexInLine)+s+currentString.substring(selectIndexInLine);
		currentLine.setLine(newContent);
	}

	public void remove(int low, int high){

		String t = getText();
		String removal = t.substring(low,high);
		System.out.println("Deletion is \""+removal+"\"");
		putCursorBeforeSelect();
		text = t.substring(0, low)+t.substring(high);
		System.out.println("Value will become \""+text+"\"");
		if(getSelectIndex() == getCursorIndex()){
			System.out.println("SelectIndexInLine used to be "+selectIndexInLine+" and cursorIndexInLine was "+cursorIndexInLine);
			decreaseCursor(1);
			System.out.println("NOW, selectIndexInLine is "+selectIndexInLine+" and cursorIndexInLine is "+cursorIndexInLine);
			if(removal.contains("\n")){
				setIndicesToCursor();
			}
		}else{
			System.out.println("SelectIndexInLien used to be "+selectIndexInLine+" and will become "+cursorIndexInLine);
			selectLine = cursorLine;
			selectIndexInLine = cursorIndexInLine;
			setSelect(getCursorIndex());
		}
		resetLinesAfter(cursorLine);
		update();
	}

	private void highlight(Graphics2D g, FontMetrics fm, int y, String line, int lineIndex, int highlightLineStart, int highlightLineEnd, int highlightStart,int highlightEnd){
		if(lineIndex >= highlightLineStart && (selectLine!=cursorLine || selectIndexInLine != cursorIndexInLine) && lineIndex <= highlightLineEnd){

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
				String line = lines.get(lineIndex).getLine(); 
				highlight(g,fm,y-fm.getHeight(),line,lineIndex,highlightLineStart,highlightLineEnd,highlightStart,highlightEnd);

				if(isCursorShowing() && lineIndex == cursorLine && (cursorLine==selectLine && cursorIndexInLine == selectIndexInLine)){

					drawCursor(g, fm, y);
				}
				g.setColor(getForeground());
				g.drawString(line, X_MARGIN, y);
				y += getLineSpace(fm);
			}
		}
		drawBorder(fm, g);
		g2.drawImage(buffer, 0, 0, null);
	}

	private void drawCursor(Graphics2D g, FontMetrics fm, int y) {
		g.setColor(Color.black);
		int location = (cursorIndexInLine> lines.get(cursorLine).getLine().length())?lines.get(cursorLine).getLine().length():cursorIndexInLine;
		int x = fm.stringWidth(lines.get(cursorLine).getLine().substring(0,location))+X_MARGIN;
		g.drawLine(x, y-fm.getHeight(), x, y);
	}

	@Override
	public boolean setStart(int x, int y) {
		findCursor = true;
		relativeX = x - getX();
		relativeY = y - getY();
		identifyCursorLineUnderMouse();
		update();
		return isEditable();
	}

	@Override
	public void setFinish(int x, int y) {
		findCursor = true;
		relativeX = x - getX();
		relativeY = y - getY();
		identifyCursorLineUnderMouse();
		update();
		//		setShiftHeld(false);
	}

	@Override
	public void setHeldLocation(int x, int y) {
		relativeX = x - getX();
		relativeY = y - getY();
		setShiftHeld(true);
		identifyCursorLineUnderMouse();
		findCursor = true;
		update();
	}

	protected class TextLine{

		private String line;
		private int startIndex;

		protected TextLine(String s, int startIndex){
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
