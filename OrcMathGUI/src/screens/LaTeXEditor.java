package screens;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
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
import guiTeacher.components.StyledComponent;
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
		problem.setFont(problem.getMonoFont());
		solution = new TextBox(margin, 4*hSpace+2*labelHeight+boxHeight, getWidth()-2*margin, boxHeight, PLACEHOLDER_TEXT);
		solution.setFont(solution.getMonoFont());
		Button add = new Button(getWidth()-margin-buttonWidth, hSpace, buttonWidth, buttonHeight, "Add", new Color(0,153,70),new Action() {

			@Override
			public void act() {
				//				Problem customProblem = new Problem(problem.getText(), solution.getText());
				OrcMath.createScreen.showGuide(false);
				OrcMath.createScreen.addQuestion(problem.getText(), solution.getText());		
			}
		});
		add.setSize(12);
		add.setCurve(1, 1);
		Button helpImage = new CustomImageButton(getWidth()-2*(margin+buttonWidth), hSpace, 21, 21, new  DrawInstructions() {

			@Override
			public void draw(Graphics2D g, boolean highlight) {
				Color color = !highlight ? new Color(214,226,238) : new Color(234,243,250);//a light blue background
				Color line = !highlight ? new Color(76,95,112) : new Color(113,132,149);//a dark blue line
				try{
					File fontFile = new File("resources/Baumans-Regular.ttf");
					//			File fontFile = new File("resources//DayRoman.ttf");
					Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
					Font baseFont=font.deriveFont(18f);
					g.setFont(baseFont);
				}catch(Exception e){
					e.printStackTrace();
				}
				g.setColor(color);
				g.fillOval(0, 0, 20, 20);
				g.setColor(line);
				g.drawString("?", 6, 17);
				g.drawOval(0, 0, 20, 20);
			}
		},new Action() {

			@Override
			public void act() {
				OrcMath.createScreen.showGuide(!OrcMath.createScreen.getReference().isVisible());
			}
		});
		viewObjects.add(helpImage);

		Button addImage = new CustomImageButton(getWidth()-(int)(1.5*(margin+buttonWidth)), hSpace, 21, 21, new DrawInstructions() {

			@Override
			public void draw(Graphics2D g, boolean highlight) {
				Color lcolor = !highlight ? new Color(234,240,246) : new Color(245,248,252);//a LIGHT blue background
				Color color = !highlight ? new Color(214,226,238) : new Color(234,243,250);//a light blue background
				Color line = !highlight ? new Color(76,95,112) : new Color(113,132,149);//a dark blue line
				Color accentColor= !highlight ? new Color(109,193,193) : new Color(141,210,210);//a green blue
				g.setColor(color);
				int[] x = {0,6,20,20,0};
				int[] y = {4,0,0,20,20};
				Polygon clipping = new Polygon(x, y, 5);
				int[] cx = {0,6,4};
				int[] cy = {4,0,6};
				Polygon corner = new Polygon(cx, cy, 3);
				g.fill(clipping);
				g.setColor(accentColor);
				int min = 5;
				int max = 10;
				g.fillRect(min, min, max, max);
				g.setColor(line);
				g.draw(clipping);
				g.drawRect(min, min, max, max);
				for(int r = min; r<max+min; r+=2){
					g.drawLine(r, min, r, max);
					g.drawLine(min, r, max, r);
				}
				g.setColor(lcolor);
				g.fillOval(7, 7, 10, 10);
				g.setColor(line);
				g.drawOval(7, 7, 10, 10);
				g.setColor(lcolor);
				g.fill(corner);
				g.setColor(line);
				g.draw(corner);
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
						String imageCode = "\\begin{array}{l} \\includegraphics[width=40cm, interpolation=bicubic] {"+file.getAbsolutePath()+"} \\end{array}";
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
				}catch(IllegalArgumentException iae){
					testProblem = "\\text{This question has no content.}";
				}
				
				try{
					Problem.toImage(testSolution);
					
				}catch(ParseException e){
					testSolution = "\\text{Parse Error: "+e.getLocalizedMessage().replaceAll("{", "\\{").replaceAll("}", "\\}")+"}";
				}catch(IllegalArgumentException iae){
					iae.printStackTrace();
					testSolution = "\\text{\"Solution LaTeX\"}";
					
				}
				Problem sample = new Problem(testProblem, testSolution);
				qp.setImage(sample.getAnswerImage());
				if(sample.getAnswerImage()==null){
					System.out.println("No image exists");
				}
				String previewText = (showPreview)?"Preview":"Hide";
				preview.setText(previewText);
				qp.setVisible(!showPreview);
				qp.setBounds(OrcMath.app.getX()+qp.getOriginalX(), OrcMath.app.getY()+qp.getOriginalY(), qp.getWidth(), qp.getHeight());
				showPreview = !showPreview;
			}
		});
		preview.setSize(12);
		problem.setFont(problem.getMonoFont());
		solution.setFont(solution.getMonoFont());

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
