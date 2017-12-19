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
		
	}

	public static void main(String[] args)
	{
		CatalogMakerGUI gui = new CatalogMakerGUI(700, 500);
		Thread go = new Thread(gui);
		go.start();
	}
	

}
