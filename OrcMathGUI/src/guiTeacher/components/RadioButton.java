package guiTeacher.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import guiTeacher.Utilities;

public class RadioButton extends Button {

	private boolean selected;
	private ArrayList<RadioButton> peers;
	
	public RadioButton(int x, int y, int w, int h, String text, Color color, Action action) {
		super(x, y, w, h, text, color, action);
		peers = new ArrayList<RadioButton>();
		// TODO Auto-generated constructor stub
	}

	public RadioButton(int x, int y, int w, int h, String text, Action action) {
		super(x, y, w, h, text, action);
		peers = new ArrayList<RadioButton>();
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
			System.out.println("selected button "+getText());
			g.setColor(Color.black);
			g.fillRoundRect(0, 0, getWidth(), getHeight(), curveX, curveY);
		}
		else if(getBackground() != null){
			if(!hover){
					g.setColor(getBackground());
			
			}
			else{
				g.setColor(Utilities.lighten(getBackground(), .4f));
//				g.setColor(getBackground());
			}
			g.fillRoundRect(0, 0, getWidth(), getHeight(), curveX, curveY);
		}else{
			clear();
		}
		g.setColor(Color.BLACK);
		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, curveX, curveY);
	}
	
	public ArrayList<RadioButton> getPeers() {
		return peers;
	}

	public void setPeers(ArrayList<RadioButton> peers) {
		this.peers = peers;
	}
	
	
	

}
