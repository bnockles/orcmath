package danielComponent;

import java.awt.Color;
import java.awt.Graphics2D;

import guiTeacher.components.AnimatedComponent;
import guiTeacher.components.Component;

public class Movie extends Component {


	String title;
	String director;
	int date;	
	
	public Movie(String title, String director, int date) 
	{
		
		super(170, 120, 25, 30);
	//	addSequence("resources/Cloud-FF5.png", 175, 0, 0, 22, 30, 7);
	//	Thread thread = new Thread(this);
	//	thread.start();
		this.title = title;
		this.director = director;
		this.date = date;
		update();
	}

	public String toString()
	{
		return title + "," + director + "," + date;
	}
	
	public void update(Graphics2D g) 
	{
		
	//	g.setColor(Color.BLUE);
	//	g.fillRect(0, 0, getWidth(), getHeight());
		
	//	super.update(g);
		
		
		
	}

}
