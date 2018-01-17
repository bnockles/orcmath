package guiPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import guiTeacher.components.*;
import guiTeacher.interfaces.FileRequester;
import guiTeacher.interfaces.Visible;
import guiTeacher.userInterfaces.FullFunctionScreen;

public class CatalogScreen extends FullFunctionScreen implements FileRequester{

	private TextField[] fields; 
	private CatalogMaker catalog;
	private Button addButton;
	private Button saveButton;
	private FileOpenButton openButton;
//	private TextArea books;
	
	public CatalogScreen(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initAllObjects(List<Visible> viewObjects) {
		StyledComponent.setButtonOutline(true);
		catalog = new CatalogMaker();
		fields = new TextField[3];
		String[] titles = {"Title","Author","Pages"};
		for(int i = 0; i < fields.length; i++){
			fields[i] = new TextField(40, 40+60*i, 270, 30, "",titles[i]);
			viewObjects.add(fields[i]);
			if(i==2){
				fields[i].setInputType(TextField.INPUT_TYPE_NUMERIC);
			}
		}
//		books = new TextArea(250,40,200,180,"");
		addButton = new Button(40, 300, 100, 30, "Add", new Action() {
			
			@Override
			public void act() {
				addBook();
			}
		});
		viewObjects.add(addButton);
		saveButton = new Button(40, 340, 100, 30, "Save", new Action() {
			
			@Override
			public void act() {
				catalog.save();
			}
		});
		viewObjects.add(saveButton);
		openButton = new FileOpenButton(145, 340, 100, 30, null, this);
		viewObjects.add(openButton);
		Button deleteButton = new Button(145,300,100,30,"Delete", new Action(){

			@Override
			public void act() {
				deleteSelectedBooks();
			}


			
		});
		viewObjects.add(deleteButton);
//		viewObjects.add(books);
	}
	
	public void deleteSelectedBooks() {
		//In ArrayList, to delete you must use a standard for loop
		
		for(int i=0; i < catalog.getBooks().size(); i++){
			Book b = catalog.getBook(i);
			if(b.isSelected()){
				//remove from screen
				remove(b);
				//remove from database
				catalog.removeBook(b);
				//compensate for deletion
				i--;
			}
		}
		arrangeStack();
	}
	
	/**
	 * Iterates through all books on shelf and adjusts y-values so books appear stacked.
	 */
	private void arrangeStack(){
		int y = getHeight();
		for(Book c: catalog.getBooks()){
			if(c.isOnShelf()){
				y-=c.getHeight();
				c.setY(y);
			}
		}
		
	}
	
	public void addBook(){
		int pages = 0;
		try{
			pages = Integer.parseInt(fields[2].getText());
			Book newBook = new Book (fields[0].getText(),fields[1].getText(),pages);
			catalog.addBook(newBook);
//			books.setText(books.getText()+fields[0].getText()+"\n");
			for(TextField t: fields){
				t.setText("");
				t.update();
			}
			addBookToStack(newBook);
		}catch(NumberFormatException n){
			fields[2].setText("Enter a valid number of pages");
		}
	}
	
	/**
	 * Adds a book that is already in the catalog to the visual shelf
	 * @param b
	 */
	private void addBookToStack(Book b){
		//x value for all books
		int x = 460;
		b.setX(x);
		//add book to screen
		addObject(b);
		//mark book as having been added
		b.setOnShelf(true);
		//adjust y-values of all books
		arrangeStack();
	}

	@Override
	public void setFile(File f) {
		catalog.read(f);
//		books.setText("");
		for(Book b: catalog.getBooks()){
//			books.setText(books.getText()+b+"\n");
			addBookToStack(b);
		}
		
	}

	@Override
	public JFrame getWindow() {
		// TODO Auto-generated method stub
		return null;
	}

}
