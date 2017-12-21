package guiPlayer;

import guiTeacher.GUIApplication;

public class CatalogMakerGUI extends GUIApplication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7548071104587737267L;

	public CatalogMakerGUI(int width, int height) {
		super(width, height);
		setVisible(true);
	}
	
	public static void main(String[] args){
		CatalogMakerGUI catalog = new CatalogMakerGUI(800, 550);
		Thread runner = new Thread(catalog);
		runner.start();
	}

	@Override
	public void initScreen() {
		CatalogScreen screen = new CatalogScreen(getWidth(), getHeight());
		setScreen(screen);
	}

}
