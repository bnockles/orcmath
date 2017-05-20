package guiTeacher.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TextBoxReplacement extends TextField{

	private ArrayList<TextLine> lines;
	private int selectLine;
	private int cursorLine;
	private int selectIndexInLine;
	private int cursorIndexInLine;
	final int SPACING = 2;//vertical space between horizontal lines

	public TextBoxReplacement(int x, int y, int w, int h, String text) {
		super(x,y,w,h,text);
		lines = new ArrayList<TextLine>();
		resetLinesAfter(0);
		update();
	}

	public TextBoxReplacement(int x, int y, int w, int h, String text, String description) {
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

	private void resetLinesAfter(int i) {
		//		int index = indexStartingFromLine(i);
		int index = (lines.size()>0)?lines.get(i).getStartIndex():0;
		System.out.println("index starts at "+index+"entire text is "+getText());
		deleteLinesAfter(i);
		String remainingText = getText().substring(index);
		String[] words = remainingText.split(" ");
		int j = 0;
		while(j < words.length){

			//don't print first space on a new line (unless there are many)
			if(words[j] == ""){
				j++;
			}
			String line = words[j++];
			while(j < words.length && lineFits(line+" "+words[j])){
				line += " "+words[j];
				j++;
			}
			lines.add(new TextLine(line, index));
			index+=line.length();
		}
	}


	private boolean lineFits(String line) {
		FontMetrics fm = getImage().createGraphics().getFontMetrics(getFont());
		return fm.stringWidth(line) < getWidth()-(X_MARGIN+BORDER)*2;
	}


	/**
	 * 
	 * @param i
	 * @return index in context of text that contains TextLine i
	 */
	private int indexStartingFromLine(int i) {
		if (i-1<0){
			return 0;
		}else{
			String previousLine = lines.get(i-1).getLine();
			int start = getText().indexOf(previousLine)+previousLine.length();
			return start;
		}
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
		return 0 + fm.getHeight()+SPACING+DESCRIPTION_SPACE;
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
				int cursor = cursorIndexInLine+lines.get(cursorLine).getStartIndex();
				setCursor(cursor);
				if(!isShiftHeld()){
					setSelect(cursor);
				}
				break;
			}
		}
	}

	public void setText(String s){
		text = s;
		resetLinesAfter(cursorLine);
		update();
		//does not automatically update for the sake of 
	}

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
			selectIndexInLine+=spaces;
			int[] values = adjustCursor(selectLine, selectIndexInLine);
			selectLine = values[0];
			selectIndexInLine = values[1];
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
		}
		update();
	}

	/**
	 * @precondition: @cursorLine = @selectLine and @cursorIndex <= @selectIndex
	 * @param s insertion string
	 * @return
	 */
	private boolean canInsert(String s) {
		String currentLine = lines.get(cursorLine).getLine();
		String newContent = currentLine.substring(0,cursorIndexInLine)+s+currentLine.substring(selectIndexInLine);
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
		putCursorBeforeSelect();
		text = t.substring(0, low)+t.substring(high);
		if(getSelectIndex() == getCursorIndex()){
			decreaseCursor(1);
			//			if(cursorLine > 0 && cursorIndexInLine==0){
			//				cursorLine--;
			//				cursorIndexInLine = lines.get(cursorLine).getLength();
			//				selectLine = cursorLine;
			//				selectIndexInLine = cursorIndexInLine;
			//				setCursor(getCursorIndex()-1);
			//				setSelect(getCursorIndex());
			//			}else if(cursorIndexInLine > 0){
			//				cursorIndexInLine--;
			//				selectLine--;
			//				setCursor(getCursorIndex()-1);
			//				setSelect(getCursorIndex());
			//			}
		}else{
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
		//			if(cursorIndex> getText().length())cursorIndex = getText().length();
		int x = fm.stringWidth(lines.get(cursorLine).getLine().substring(0,cursorIndexInLine))+X_MARGIN;
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
			System.out.println("Line created with startIndex = "+startIndex);
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
