package guiPlayer;

import java.awt.Graphics2D;

import guiTeacher.components.Component;

public class Book extends Component {

	private String title;
	private String author;
	private int pages;
	


	public Book(String title, String author, int pages) {
		super(0, 0, 300, 1*pages);
		this.title = title;
		this.author = author;
		this.pages = pages;
	}

	
	public String toString(){
		return title+","+author+","+pages;
	}
	
	@Override
	public void update(Graphics2D g) {
		// TODO Auto-generated method stub

	}

}
