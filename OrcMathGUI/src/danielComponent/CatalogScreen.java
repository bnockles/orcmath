package danielComponent;

import java.util.List;

import guiTeacher.components.TextField;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FullFunctionScreen;

public class CatalogScreen extends FullFunctionScreen 
{

	private static final long serialVersionUID = 1L;
	private TextField descriptionField;
	
	public CatalogScreen(int width, int height) 
	{
		super(width, height);
	}

	@Override
	public void initAllObjects(List<Visible> viewObjects) 
	{
		TextField titleDescription = new TextField(50, 50, 300, 50, "sample text", "Movie Title");
		viewObjects.add(titleDescription);
		
		TextField directorDescription = new TextField(50, 150, 300, 50, "sample text", "Director");
		viewObjects.add(directorDescription);
		
		TextField yearDescription = new TextField(50, 250, 300, 50, "sample text", "Year Released");
		viewObjects.add(yearDescription);
	}
}
