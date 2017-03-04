package guiTeacher.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import guiTeacher.Utilities;
import guiTeacher.interfaces.Task;

public class ProgressBar extends StyledComponent {

	private Task task;
	private Color barColor;
	private Color incompleteColor;

	public ProgressBar(int x, int y, int w, int h, Task task) {
		super(x, y, w, h);
		setVisible(false);//does not appear until task begins
		barColor = getTabColor();
		incompleteColor = getBackgroundColor();
		this.task = task;
		update();
	}




	public Color getBarColor() {
		return barColor;
	}




	public void setBarColor(Color barColor) {
		this.barColor = barColor;
	}




	public Color getIncompleteColor() {
		return incompleteColor;
	}




	public void setIncompleteColor(Color color) {
		this.incompleteColor = color;
	}




	@Override
	public void update(Graphics2D g) {
		if(task !=null){
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setFont(getBaseFont());
			g.setColor(getTextColor());

			Utilities.drawText(g, task.getDescriptionOfCurrentTask(), 0, getWidth(), getHeight(), StyledComponent.ALIGN_LEFT);
			g.setColor(incompleteColor);
			FontMetrics fm = g.getFontMetrics();
			int y = fm.getDescent()+fm.getHeight();
			int edge = 6;
			g.fillRoundRect(0, y, getWidth()-1, getHeight()-1-y,edge,edge);
			g.setColor(barColor);
			g.fillRoundRect(0, y, (int)(getWidth()*(task.getProgress()/task.getTotal()))-1, getHeight()-1-y,edge,edge);
			g.setColor(Color.BLACK);
			g.drawRoundRect(0, y, getWidth()-1, getHeight()-1-y,edge,edge);
		}
	}

	public void startTask(Action toDoOnCompletion){
		setVisible(true);
		Thread runTask = new Thread(new Runnable() {

			@Override
			public void run() {
				task.start();
				while(!task.isFinished()){
					update();
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				task.reset();
				
				setVisible(false);
				update();
				if(toDoOnCompletion != null){
					toDoOnCompletion.act();
				}
			}
		});
		runTask.start();
	}
	

}
