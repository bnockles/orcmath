package guiTeacher.interfaces;

public interface Task {

	/**
	 * 
	 * @return number of tasks to be completed
	 */
	double getProgress();
	/**
	 * 
	 * @return total number of tasks to complete
	 */
	int getTotal();
	
	void start();
	
	void reset();
	
	boolean isFinished();
	
	String getDescriptionOfCurrentTask();
	
}
