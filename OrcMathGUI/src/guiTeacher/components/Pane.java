package guiTeacher.components;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import guiPlayer.CustomPane;
import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.ComponentContainer;
import guiTeacher.userInterfaces.Screen;

public class Pane extends ComponentContainer implements Clickable {

	protected ArrayList<Clickable> clickables;
	protected BufferedImage contentImage;

	private int x;
	private int y;
	protected int xRelative;
	protected int yRelative;
	protected final FocusController parentScreen;
	protected Component containingComponent;//some components like Accordion contain ScrollapblePanes

	public Pane(FocusController focusController, int x, int y,int width, int height) {
		super(width, height);
		this.x = x;
		this.y = y;
		this.parentScreen=focusController;
		setVisible(true);
		containingComponent = null;
		update();		
	}

	public Pane(FocusController focusController, int x, int y,int width, int height, ArrayList<Visible> initWithObjects) {
		super(width, height, initWithObjects);
		this.x = x;
		this.y = y;
		this.parentScreen=focusController;
		setVisible(true);
		containingComponent = null;
		update();
	}
	
	public void addObject(Visible v){
		super.addObject(v);
		if(v instanceof Clickable){
			clickables.add((Clickable)v);
		}
	}
	
	public void remove(Visible v){
		if(v instanceof Clickable){
			clickables.remove(v);
		}
		super.remove(v);
	}

	public void removeAll(){
		super.removeAll();
		clickables = new ArrayList<Clickable>();
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public boolean isAnimated() {
		return false;
	}

	@Override
	public void unhoverAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isHovered(int x, int y) {
		boolean hov =x > getX() && y > getY() && x<getX()+getWidth() && y < getY()+getHeight();
		if(hov){
			xRelative = x - getX();
			yRelative = y - getY();

		}
		return hov;
	}

	@Override
	public void act() {

		for(Clickable c: clickables){
			if(c.isHovered(xRelative, yRelative)){
				c.act();
				update();
				if(containingComponent != null)containingComponent.update();
				break;
			}
		}

	}

	@Override
	public void hoverAction() {
		for(Clickable c:clickables){
			//update Buttons
			boolean buttonStateChange = false;
			if(c instanceof Button){
				buttonStateChange = ((Button)c).isHovered();
			}
			if(c.isHovered(xRelative, yRelative)){
				c.hoverAction();
				if(c instanceof Button && buttonStateChange != ((Button)c).isHovered()){
					update();
					if(containingComponent != null)containingComponent.update();
				}

				//				don't break because sometime objects have tasks after hovering is complete
			}
		}
	}

	@Override
	public void setAction(Action a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initObjects(List<Visible> viewObjects) {
		initAllObjects(viewObjects);
		clickables = new ArrayList<Clickable>();
		for(Visible v: viewObjects){
			if(v instanceof Clickable){
				clickables.add((Clickable)v);
			}
		}
	}
	
	public void move(int newX, int newY, int durationMS){
		Visible.move(this, newX, newY, durationMS);
	}
	
	
	/**
	 * override by subclasses to add objects manually
	 * @param viewObjects
	 */
	public void initAllObjects(List<Visible> viewObjects){

	}

}
