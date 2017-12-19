package myStuff;

import java.util.List;

import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.TextField;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FullFunctionScreen;

public class CatalogScreen extends FullFunctionScreen{
	
	private TextField mainField;
	private Button addbutton;
	

	public CatalogScreen(int width, int height) {
		super(width,height);
		mainField = new TextField(40,40,200,30,"text goes here","description");

	}

	@Override
	public void initAllObjects(List<Visible> viewObjects) {
		
		mainField = new TextField(40,40,200,30,"text goes here","description");

		viewObjects.add(mainField);
		

		addbutton = new Button(5,5,10,10, "someButton", new Action() {
			
			@Override
			public void act() {

				mainField.setText("qw");
				
				
			}
		});
		
	}
	
	private void setScreen(CatalogScreen window) {
		
	}

	public static void main(String[] args) {
		
	}


}
