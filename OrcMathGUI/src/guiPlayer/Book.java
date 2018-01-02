package guiPlayer;

import java.awt.Color;
import java.awt.Graphics2D;

import guiTeacher.components.Action;
import guiTeacher.components.Component;
import guiTeacher.interfaces.Clickable;

public class Book extends Component implements Clickable{

	private String title;
	private String author;
	private int pages;
	private Color color;
	private boolean selected;
	private boolean onShelf;


	public Book(String title, String author, int pages) {
		super(0, 0, 300, 10+pages/12);
		this.title = title;
		this.author = author;
		this.pages = pages;
		this.selected = false;
		this.color = new Color(100+(int)(100*Math.random()),100+(int)(100*Math.random()),100+(int)(100*Math.random()));
		update();
	}

	
	public String toString(){
		return title+","+author+","+pages;
	}
	
	@Override
	public void update(Graphics2D g) {
		g.setColor(new Color(100,200,255));
		if(selected){
			g.fillOval(1, getHeight()/2-10, 20, 20);
			
		}
		else {
			g.setColor(Color.white);
			g.fillOval(1, getHeight()/2-10, 20, 20);
			g.setColor(new Color(100,200,255));
			g.drawOval(1, getHeight()/2-10, 20, 20);
		}
		g.setColor(this.color);
		g.fillRect(22, 0, getWidth()-23, getHeight()-1);
		g.setColor(Color.black);
		g.drawString(title, 26, getHeight()/2-6);
		g.drawRect(22, 0, getWidth()-23, getHeight()-1);
	}


	@Override
	public boolean isHovered(int x, int y) {
		return x>getX() && x < getX()+getWidth() && y > getY() && y < getY() + getHeight();
	}


	@Override
	public void act() {
		selected = !selected;
		update();
	}

	

	public boolean isOnShelf() {
		return onShelf;
	}


	public void setOnShelf(boolean onShelf) {
		this.onShelf = onShelf;
	}


	@Override
	public void setAction(Action a) {
		// TODO Auto-generated method stub
		
	}


	public boolean isSelected() {
		return selected;
	}

}
