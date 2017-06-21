package data;

import com.orcmath.local.Problem;

import guiTeacher.components.Action;
import guiTeacher.components.Link;

/**
 * This class is the data required to build a custom problem.
 * It primarily contains the LaTeX for the problem and solution and other values 
 * that are needed to generate the images
 * @author bnockles
 *
 */
public class CustomProblemData extends Link {

	private String problemLaTeX;
	private String solutionLaTeX;
	
	public CustomProblemData(String problemLatex, String solutionLatex, int index, Action a) {
		super(0,0,70,20,Problem.CUSTOM_TAG+ " "+(index), a);
		this.problemLaTeX = problemLatex;
		this.solutionLaTeX = solutionLatex;
	}

	public String getProblemLaTeX() {
		return problemLaTeX;
	}

	public void setProblemLaTeX(String problemLaTeX) {
		this.problemLaTeX = problemLaTeX;
	}

	public String getSolutionLaTeX() {
		return solutionLaTeX;
	}

	public void setSolutionLaTeX(String solutionLaTeX) {
		this.solutionLaTeX = solutionLaTeX;
	}
	
	
	
	

}
