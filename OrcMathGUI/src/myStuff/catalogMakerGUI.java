package myStuff;

import guiTeacher.GUIApplication;

public class catalogMakerGUI extends GUIApplication {

	public catalogMakerGUI(int width, int height) {
		super(width, height);
		setVisible(true);
	}

	@Override
	public void initScreen() {
		CatalogScreen window = new CatalogScreen(getWidth(),getHeight());
		setScreen(window);		
	}

	public static void main(String[] args) {
		catalogMakerGUI sample = new catalogMakerGUI(800, 550);
		Thread go = new Thread(sample);
		go.start();
	}

}
