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

		descriptionField = new TextField(50, 50, 30, 70, "sample");
	}
}
