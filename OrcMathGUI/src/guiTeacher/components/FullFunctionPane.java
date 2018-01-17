package guiTeacher.components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import guiTeacher.interfaces.Clickable;
import guiTeacher.interfaces.Dragable;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.KeyedComponent;
import guiTeacher.interfaces.Visible;

/**
 * This class is like a FullFunctionScreen but a "subscreen" version.
 * In other words, it is not a FocusManager and there can be multiple instances on one single screen
 * 
 */

public class FullFunctionPane extends ScrollablePane implements KeyedComponent, KeyListener, Dragable, Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5672237385004823171L;
	private ArrayList<KeyedComponent> keyedComponents;
	private KeyedComponent activeKeyedComponent;
	private boolean running;
	private Dragable draggedItem;

	public FullFunctionPane(FocusController focusController, Component parentComponent, int x, int y, int w, int h) {
		super(focusController, parentComponent, new ArrayList<Visible>(),x, y, w, h);
	}

	public FullFunctionPane(FocusController focusController, int x, int y, int w, int h) {
		super(focusController, null, new ArrayList<Visible>(),x, y, w, h);
	}

	
	@Override
	public void initObjects(List<Visible> viewObjects) {
		super.initObjects(viewObjects);
		keyedComponents = new ArrayList<KeyedComponent>();
		for(Visible v: viewObjects){
			if(v instanceof KeyedComponent){
				keyedComponents.add((KeyedComponent)v);
			}
		}

	}

	public void addObject(Visible v){
		super.addObject(v);
		if(v instanceof KeyedComponent){
			keyedComponents.add((KeyedComponent)v);
		}
	}



	public void remove(Visible v){
		super.remove(v);
		keyedComponents.remove(v);
	}

	public void act() {
		super.act();
		for(KeyedComponent kc: keyedComponents){
			if(kc.isHovered(xRelative, yRelative)){
				moveFocus(kc);
				
			}
		}

	}


	public void moveFocus(KeyedComponent k){
		if(k != activeKeyedComponent){

			if(activeKeyedComponent!=null)activeKeyedComponent.setFocus(false);
			k.setFocus(true);
			activeKeyedComponent = k;
		}
	}

	@Override
	public boolean setStart(int x, int y) {

		boolean hoverOverDragable = false;
		for(Clickable c: clickables){
			if(c.isVisible() && c.isHovered(x-getX()+contentX, y-getY()+contentY)){
				if(c instanceof Dragable){
					Dragable item = (Dragable)c;
					if(item.setStart(x-getX()+contentX,y-getY()+contentY)){
						draggedItem = item;
						hoverOverDragable = true;
					}
					if(item instanceof KeyedComponent){
						KeyedComponent kc = (KeyedComponent)item;
						moveFocus(kc);
					}
					break;
				}
			}
		}
		return hoverOverDragable;
	}

	@Override
	public void setFinish(int x, int y) {
		if(draggedItem != null)draggedItem.setFinish(x - getX()+contentX, y - getY()+contentY);
	}

	@Override
	public void setHeldLocation(int x, int y) {
		//		System.out.println("<FullFunctionPane> Coordinates of drag are "+(x - getX())+", "+(y - getY()));
		if(draggedItem != null){
			draggedItem.setHeldLocation(x-getX()+contentX,y-getY()+contentY);
		}

	}


	@Override
	public void keyTyped(KeyEvent e) {
		if(activeKeyedComponent != null){
			activeKeyedComponent.keyTyped(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(activeKeyedComponent != null){
			activeKeyedComponent.keyPressed(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(activeKeyedComponent != null){
			activeKeyedComponent.keyReleased(e);
		}
	}

	public void setFocus(boolean b) {
		super.setFocus(b);
		if(b && !running){
			if(activeKeyedComponent!= null) activeKeyedComponent.setFocus(b);
			running = true;
			Thread updatePanel = new Thread(this);
			updatePanel.start();
		}else if(!b){
			running = false;
			if(activeKeyedComponent != null) activeKeyedComponent.setFocus(false);
		}
	}

	public void run(){
		while(running){
			update();		
			if(containingComponent != null) containingComponent.update();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		update();
	}



}
