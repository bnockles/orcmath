package guiTeacher.interfaces;

/**
 * Must be implement in order to use a progress bar
 * @author bnockles
 *
 */
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
	
	/**
	 * called when the task is started
	 */
	void start();
	
	/**
	 * called if the task has to be repeated
	 */
	void reset();
	
	/**
	 * called when the task is finished
	 */
	boolean isFinished();
	
	/**
	 * 
	 * @return String describing what part of the task is being done
	 */
	String getDescriptionOfCurrentTask();
	
}
