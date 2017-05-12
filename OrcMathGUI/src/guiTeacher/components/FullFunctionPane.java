package guiTeacher.components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.KeyedComponent;
import guiTeacher.interfaces.Visible;

/**
 * This class is like a FullFunctionScreen but a "subscreen" version.
 * In otehr words, it is not a FocusManager and there can be multiple instances on one single screen
 * 
 */

public abstract class FullFunctionPane extends ScrollablePane implements KeyedComponent, KeyListener, Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5672237385004823171L;
	private ArrayList<KeyedComponent> keyedComponents;
	private KeyedComponent activeKeyedComponent;
	private boolean running;
	
	public FullFunctionPane(FocusController focusController, Component parentComponent, int x, int y, int w, int h) {
		super(focusController, parentComponent, new ArrayList<Visible>(),x, y, w, h);
		// TODO Auto-generated constructor stub
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
				kc.setFocus(true);
				if(activeKeyedComponent!= null) activeKeyedComponent.setFocus(false);
				activeKeyedComponent = kc;
				break;
			}
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
			containingComponent.update();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		update();
	}
	
	

}
