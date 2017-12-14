package guiTeacher.components;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.KeyedComponent;
import guiTeacher.interfaces.Visible;

public class TypingPane extends Pane implements KeyListener, KeyedComponent, Runnable {

	private ArrayList<KeyedComponent> keyedComponents;
	private KeyedComponent activeKeyedComponent;
	private boolean running;
	
	public TypingPane(FocusController focusController, int x, int y, int width, int height) {
		super(focusController, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public TypingPane(FocusController focusController, int x, int y, int width, int height,
			ArrayList<Visible> initWithObjects) {
		super(focusController, x, y, width, height, initWithObjects);
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
