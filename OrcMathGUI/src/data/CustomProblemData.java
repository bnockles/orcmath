package data;


/**
 * This class is the data required to build a custom problem.
 * It primarily contains the LaTeX for the problem and solution and other values 
 * that are needed to generate the images
 * @author bnockles
 *
 */
public class CustomProblemData {

	private String problemLaTeX;
	private String solutionLaTeX;
	
	public CustomProblemData(String problemLatex, String solutionLatex) {
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
