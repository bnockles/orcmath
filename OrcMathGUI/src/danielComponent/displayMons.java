package danielComponent;

import java.awt.Color;
import java.awt.Graphics2D;

import guiTeacher.components.Component;

public class displayMons extends Component {

	public displayMons() 
	{
		super(70, 50, 100, 100);
	}

	@Override
	public void update(Graphics2D g) 
	{
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, getWidth(), getHeight());
	}

}
