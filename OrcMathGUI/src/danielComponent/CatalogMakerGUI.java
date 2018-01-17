package danielComponent;

import guiTeacher.GUIApplication;

public class CatalogMakerGUI extends GUIApplication 
{

	public CatalogMakerGUI(int width, int height) 
	{
		super(width, height);
		setVisible(true);
	}

	@Override
	public void initScreen()
	{
		CatalogScreen screen = new CatalogScreen(getWidth(), getHeight());
		setScreen(screen);
	}

	public static void main(String[] args)
	{
		CatalogMakerGUI gui = new CatalogMakerGUI(700, 700);
		Thread go = new Thread(gui);
		go.start();
	}
	

}
