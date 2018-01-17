package danielComponent;

import java.awt.Color;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import guiTeacher.components.*;
import guiTeacher.interfaces.FileRequester;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FullFunctionScreen;

public class CatalogScreen extends FullFunctionScreen implements FileRequester
{

	private Button addButton;
	private FileOpenButton open;
	private static final long serialVersionUID = 1L;
	private TextField descriptionField;
	private TextArea text;
	private Button deleteButton;
	private Button saveButton;
	private CatalogMaker catalog;
	private TextField yearDescription;
	private TextField directorDescription;
	private TextField titleDescription;
	
	public CatalogScreen(int width, int height) 
	{
		super(width, height);
	}
	
	@Override
	public void initAllObjects(List<Visible> viewObjects) 
	{
		catalog = new CatalogMaker();
		
		text = new TextArea(50,50,1000,1000,"Movie Catalog");
		viewObjects.add(text);
		
		titleDescription = new TextField(50, 150, 300, 50, "Jaws", "Movie Title");
		viewObjects.add(titleDescription);
		
		directorDescription = new TextField(50, 250, 300, 50, "Steven Spielberg", "Director");
		viewObjects.add(directorDescription);
		
		yearDescription = new TextField(50, 350, 300, 50, "1975", "Year Released");
		yearDescription.setInputType(TextField.INPUT_TYPE_NUMERIC);
		viewObjects.add(yearDescription);
		
		addButton = new Button(450, 250, 80, 80, "Click Me", new Color(205,11,100), 
		new Action() 
		{
			public void act() 
			{
				addClicked();
			}
		});
		viewObjects.add(addButton);
		
		open = new FileOpenButton(450, 350, 80, 80, null, this);
		viewObjects.add(open);
		
		deleteButton = new Button(450, 450, 80, 80, "Delete", null);
		viewObjects.add(deleteButton);
		
		saveButton = new Button(450, 550, 80, 80, "Save", null);
		viewObjects.add(saveButton);
	}

	protected void addClicked() 
	{
		Movie m = new Movie(titleDescription.getText(), directorDescription.getText(), Integer.parseInt(yearDescription.getText()));
		text.setText(text.getText()+"\n"+m.title+", "+m.director+" ("+m.date+")");
	}

	@Override
	public void setFile(File f) 
	{
		
	}

	@Override
	public JFrame getWindow()
	{
		return null;
	}
}
