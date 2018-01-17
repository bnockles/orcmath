package guiTeacher.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import guiTeacher.Utilities;

/**
 * An individual Radio Button. When assigned to peers, only one RadioButton can be selected at a time. Call addPeer to pair with other Buttons. See also the RadioButtons class, which elegantly handles large number of RadioButton
 * @author bnockles
 *
 */
public class RadioButton extends Button {

	private boolean selected;
	private ArrayList<RadioButton> peers;
	private static final Color DEFAULT_SELECT_COLOR = new Color(80,80,80);
	private Color selectedColor;
	
	/**
	 * 
	 * @param x x-coordinate within context of parent ComponentContainer
	 * @param y y-coordinate within context of parent ComponentContainer
	 * @param w pixel width
	 * @param h pixel height
	 * @param text Description of the RadioButton's function
	 * @param color
	 * @param action
	 */
	public RadioButton(int x, int y, int w, int h, String text, Color color, Action action) {
		super(x, y, w, h, text, color, action);
		peers = new ArrayList<RadioButton>();
		update();
		// TODO Auto-generated constructor stub
	}

	public RadioButton(int x, int y, int w, int h, String text, Action action) {
		super(x, y, w, h, text, action);
		peers = new ArrayList<RadioButton>();
		update();
		// TODO Auto-generated constructor stub
	}
	
	public void addPeer(RadioButton rb){
		peers.add(rb);
	}
	
	public void addPeers(List<RadioButton> rb){
		peers.addAll(rb);
	}
	
	public void act(){
		for(RadioButton rb: peers){
			rb.setSelected(false);
		}
		selected = true;
		super.act();
		update();
		
	}
	
	

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	public BufferedImage getImage(){
		
		if(isHovered() && !selected)return getHoveredImage();
		else return getNonhoveredImage();
	}
	
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		update();
	}

	public void drawShape(Graphics2D g, boolean hover){
		if(!hover && selected){
			Color sC = selectedColor == null ? DEFAULT_SELECT_COLOR:selectedColor;
			g.setColor(sC);
			g.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, curveX, curveY);
		}
		else if(getBackground() != null){
			if(!hover){
					g.setColor(getBackground());
			
			}
			else{
				g.setColor(Utilities.lighten(getBackground(), .4f));
//				g.setColor(getBackground());
			}
			g.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, curveX, curveY);
		}else{
			clear();
		}
		g.setColor(Color.BLACK);
		g.drawRoundRect(2, 2, getWidth()-5, getHeight()-5, curveX, curveY);
	}
	
	public ArrayList<RadioButton> getPeers() {
		return peers;
	}

	public void setPeers(ArrayList<RadioButton> peers) {
		this.peers = peers;
	}
	
	
	

}
