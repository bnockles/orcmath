package testing;

import guiTeacher.GUIApplication;
import guiTeacher.userInterfaces.Screen;

public class AliceMain extends GUIApplication{

	public AliceMain(int width, int height) {
		
		super(width, height);
		setVisible(true);
	}

	public static void main(String[] args){
		AliceMain catalog = new AliceMain(800, 550);
		Thread runner = new Thread(catalog);
		runner.start();
	}

	@Override
	public void initScreen() {
		
		 screen = new AliceMain(getWidth(), getHeight());
		setScreen(screen);
		
	}

}
