package guiPlayer;

import guiPlayer.CatalogMakerGUI;
import guiPlayer.CatalogScreen;
import guiTeacher.GUIApplication;

public class CustomGUI extends GUIApplication {

	public CustomGUI(int width, int height) {
		super(width, height);
		setVisible(true);
	}

	@Override
	public void initScreen() {
		CustomScreen screen = new CustomScreen(getWidth(), getHeight());
		setScreen(screen);
		
	}

	public static void main(String[] args) {
		CustomGUI s = new CustomGUI(800, 550);
		Thread runner = new Thread(s);
		runner.start();
	}

}
