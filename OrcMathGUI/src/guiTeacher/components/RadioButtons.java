package guiTeacher.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import guiTeacher.interfaces.Clickable;

/**
 * A simplified implementation of a group of RadioButton. Visually, this group has a label above it defining the overall group of Buttons 
 * @author bnockles
 *
 */
public class RadioButtons extends StyledComponent implements Clickable{

	public static final int STYLE_HORIZONTAL = 0;
	public static final int STYLE_VERTICAL = 1;
	public static final int BUTTON_HEIGHT = 18;
	
	private String name;
	private int[][] centers;//locations of the centers of each radio button
	private RButton[] buttons;
	private int style;
	private int xRelative;
	private int yRelative;
	private Action action;
	
	/**
	 * 
	 * @param x x-coordinate within context of parent ComponentContainer
	 * @param y y-coordinate within context of parent ComponentContainer
	 * @param w width of all the buttons
	 * @param h height of all the buttons
	 * @param name Description above all buttons
	 * @param descriptions the desciption for each individual button
	 * @param defaultButton corresponds to the index of one of the descriptions in the previous parameter, this defines the initial RadioButton
	 * @param style can be RadioButtons.STYLE_HORIZONTAL or _VERTICAL
	 */
	public RadioButtons(int x, int y, int w, int h, String name, String[] descriptions, int defaultButton, int style) {
		super(x, y, w, h);
		this.name = name;
		buttons = new RButton[descriptions.length];
		centers = new int[descriptions.length][2];
		this.style = style;
		int i = 0;
		for(String s: descriptions){
			buttons[i] = new RButton(s, false);
			i++;
		}
		buttons[defaultButton].setValue(true);
		xRelative = 0;
		yRelative = 0;
		this.action = null;
		update();
	}

	@Override
	public void update(Graphics2D g) {
		clear();
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.BLACK);
		g.drawString(name, 0, fm.getHeight());
		int x = 0;
		int y = fm.getHeight()+fm.getDescent()+3;
		int b = 2;
		g.setStroke(new BasicStroke(b));
		for(int i = 0; i < buttons.length; i++){
			g.setColor(getStaticBorderColor());
			g.drawOval(x, y, BUTTON_HEIGHT, BUTTON_HEIGHT);
			if(buttons[i].isValue()){
				g.fillOval(x+b*2, y+b*2, BUTTON_HEIGHT-b*3-1, BUTTON_HEIGHT-b*3-1);
			}
			g.setColor(Color.black);
//			Utilities.drawText(g, buttons[i].getDescription(),x, y, getWidth(),BUTTON_HEIGHT, TextLabel.ALIGN_LEFT);
			g.drawString(buttons[i].getDescription(), x+BUTTON_HEIGHT+5, y+fm.getHeight());
			centers[i][0] = x+BUTTON_HEIGHT/2;
			centers[i][1] = y+BUTTON_HEIGHT/2;
			if(style == STYLE_HORIZONTAL){
				x+=getWidth()/buttons.length;
			}else{
				y+=getHeight()/buttons.length;
			}
		}
	}
	
	public class RButton{
		private String description;
		private boolean value;
		
		public RButton(String description, boolean value){
			this.description = description;
			this.value = value;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public boolean isValue() {
			return value;
		}

		public void setValue(boolean value) {
			this.value = value;
		}
		
		
	}

	@Override
	public boolean isHovered(int x, int y) {
		boolean h =  x > getX() && x < getX() + getWidth() && y > getY() && y < getY() + getHeight();
		if(h){
			xRelative = x-getX();
			yRelative = y-getY();
		}
		return h;
	}

	@Override
	public void act() {
		for(int i = 0; i < buttons.length; i++){
		if (onButton(i)) clearAllBut(i);
		}
		if(action!=null)action.act();
		update();
	}

	private void clearAllBut(int x) {
		buttons[x].setValue(true);
		for(int i = 0; i < buttons.length; i++){
			if(i!=x)buttons[i].setValue(false);
		}
	}

	private boolean onButton(int i){
		return (Math.sqrt(Math.pow(xRelative-centers[i][0], 2)+Math.pow(yRelative-centers[i][1], 2))< BUTTON_HEIGHT/2);
	}
	
	@Override
	public void setAction(Action a) {
		this.action = a;
	}

	/**
	 * 
	 * @return the description associated with the currently selected RadioButton
	 */
	public String getCheckedValue() {
		for(int i = 0; i < buttons.length; i++){
			if(buttons[i].isValue())return buttons[i].getDescription();
		}
		System.out.println("There is an error in the RadioButton class. No item is checked.");
		return buttons[0].getDescription();
	}

	/**
	 * 
	 * @return the index of the currently selected RadioButton
	 */
	public int getIndexOfChecked() {
		for(int i = 0; i < buttons.length; i++){
			if(buttons[i].isValue())return i;
		}
		System.out.println("There is an error in the RadioButton class. No item is checked.");
		return 0;
	}

}
