package screens;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import org.scilab.forge.jlatexmath.ParseException;

import com.orcmath.local.Problem;

import components.QuestionPreview;
import guiTeacher.components.Action;
import guiTeacher.components.Button;
import guiTeacher.components.Component;
import guiTeacher.components.CustomImageButton;
import guiTeacher.components.FullFunctionPane;
import guiTeacher.components.Link;
import guiTeacher.components.ScrollablePane;
import guiTeacher.components.TextBox;
import guiTeacher.components.TextLabel;
import guiTeacher.interfaces.DrawInstructions;
import guiTeacher.interfaces.FocusController;
import guiTeacher.interfaces.KeyedComponent;
import guiTeacher.interfaces.Visible;
import main.OrcMath;

public class LaTeXEditor extends FullFunctionPane{


	public static final String PLACEHOLDER_TEXT = "\\text{Enter LaTeX for solution}";


	private QuestionPreview qp;
	private Link preview;
	private boolean showPreview;
	private TextBox problem;
	private TextBox solution;
	private ImageIcon imageToAdd;//for uploading images

	/**
	 * 
	 */
	private static final long serialVersionUID = 741785310939312719L;

	public LaTeXEditor(FocusController focusController, Component topicAccordion, int x, int y, int w, int h) {
		super(focusController, topicAccordion,x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	public void initAllObjects(List<Visible> viewObjects){
		qp = new QuestionPreview(getX()+getWidth(), getY(),700,500);
		int buttonWidth = 55;
		int buttonHeight = 20;
		int margin = 2;
		int labelHeight = 25;
		int hSpace = 2;
		int boxWidth = getWidth()-2*margin;
		int boxHeight = (getHeight()- 2*labelHeight-5*hSpace)/2;

		problem = new TextBox(margin, 2*hSpace+labelHeight, boxWidth, boxHeight, "Sample: x^{\\frac{1}{2}}=\\sqrt{x}");
		solution = new TextBox(margin, 4*hSpace+2*labelHeight+boxHeight, getWidth()-2*margin, boxHeight, PLACEHOLDER_TEXT);
		
		Button add = new Button(getWidth()-margin-buttonWidth, hSpace, buttonWidth, buttonHeight, "Add", new Color(0,153,70),new Action() {

			@Override
			public void act() {
				//				Problem customProblem = new Problem(problem.getText(), solution.getText());
				OrcMath.createScreen.addQuestion(problem.getText(), solution.getText());		
			}
		});
		add.setSize(12);
		add.setCurve(1, 1);
		Button addImage = new CustomImageButton(getWidth()-2*(margin+buttonWidth), hSpace, 20, 20, new DrawInstructions() {
			
			@Override
			public void draw(Graphics2D g, boolean highlight) {
				Color color = highlight ? Color.blue : Color.black;
				g.setColor(color);
				g.drawRect(1, 1, 18, 18);
				g.drawOval(4, 4, 14, 14);
			}
		}, new Action() {
			
			@Override
			public void act() {
				final JFileChooser fc = new JFileChooser();
//				fc.addChoosableFileFilter(new ImageFilter());
				//In response to a button click:
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        imageToAdd = (new ImageIcon(ImageIO.read(file)));
                        System.out.println("Loaded an image with width = "+imageToAdd.getIconWidth());
                        String imageCode = "\\begin{array}{l}\\includegraphics[width=40cm,interpolation=bicubic]{"+file.getAbsolutePath()+"}\\end{array}";
                        problem.insert(imageCode);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
			}
		});
		viewObjects.add(addImage);
		
		preview = new Link(getWidth()-3*(margin+buttonWidth), hSpace, buttonWidth, buttonHeight, "Preview",new Action() {

			@Override
			public void act() {
				String testProblem =problem.getText();
				String testSolution =solution.getText();
				try{
					Problem.toImage(testProblem);
				}catch(ParseException e){
//					e.printStackTrace();
					String message = e.getLocalizedMessage().replaceAll(Pattern.quote("{"), " \\\\{ ").replaceAll(Pattern.quote("}"), " \\\\} ");
					System.out.println(message);
					testProblem = "\\text{Parse Error: "+message+"}";
				}
				try{
					Problem.toImage(testSolution);
					Problem sample = new Problem(testProblem, testSolution);
					qp.setImage(sample.getAnswerImage());
					if(sample.getAnswerImage()==null){
						System.out.println("No image exists");
					}
				}catch(ParseException e){
					testSolution = "\\text{Parse Error: "+e.getLocalizedMessage().replaceAll("{", "\\{").replaceAll("}", "\\}")+"}";
				}
				String previewText = (showPreview)?"Preview":"Hide";
				preview.setText(previewText);
				qp.setVisible(!showPreview);
				qp.setBounds(OrcMath.app.getX()+qp.getOriginalX(), OrcMath.app.getY()+qp.getOriginalY(), qp.getWidth(), qp.getHeight());
				showPreview = !showPreview;
			}
		});
		preview.setSize(12);
		problem.setFont(new Font("Times New Roman", Font.PLAIN,14));
		solution.setFont(new Font("Times New Roman", Font.PLAIN,14));

		TextLabel problemLabel = new TextLabel(margin,hSpace,boxWidth-2*buttonWidth-margin,labelHeight,"Problem LaTeX");
		TextLabel solutionLabel = new TextLabel(margin,3*hSpace+labelHeight+boxHeight,boxWidth,labelHeight,"Solution LaTeX");
		viewObjects.add(add);
		viewObjects.add(preview);
		viewObjects.add(problem);
		viewObjects.add(solution);
		viewObjects.add(solutionLabel);
		viewObjects.add(problemLabel);
	}

	public void close(){
		qp.setVisible(false);
	}

	public void act(){
		if(!preview.isHovered() && showPreview){
			qp.setVisible(false);
			preview.setText("Preview");
			showPreview = false;
		}
		super.act();

	}

	public void setProblemText(String problemLaTeX) {
		problem.setText(problemLaTeX);
	}
	
	public void setSolutionText(String solutionLaTeX) {
		solution.setText(solutionLaTeX);
	}




}
