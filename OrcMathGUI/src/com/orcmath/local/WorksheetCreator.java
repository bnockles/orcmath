/*******************************************************************************
 * Copyright (c) 2012-2017 Benjamin Nockles
 *
 * This file is part of OrcMath.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License 
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.orcmath.local;

import java.awt.*; 
import java.applet.*;
import javax.swing.*;


import com.itextpdf.text.DocumentException;

import java.awt.event.*;
import java.io.IOException;

public class WorksheetCreator extends Applet implements ActionListener, MouseMotionListener{   
    
    TextField searchBar;
    Button searchButton;

    DefaultListModel listModel;
    JList topicSelection;
//     String[] problemTypes = {
//     "Quadratics: Identify roots by factoring. (a=1)",
//     "Quadratics: Identify roots by factoring. (a�� 1)",
//     "Quadratics: Identify (real) roots using the quadratic formula.",
//     "Quadratics: Identify (real & complex) roots using the quadratic formula.",
//     "Radical Expressions: Simplify radicals",
//     "Rational Expressions: Simplify polynomials"
//      };

    DefaultListModel selected;
    JList selectedTopics;
    JScrollPane listScroller;
    JScrollPane selectScroller;
    Button addButton;  
    Button removeButton;
    TextField numberOfProblemsField;
    TextField numberOfWorksheetsField; 
    CheckboxGroup difficultySettings;  
    Checkbox radio1; 
    Checkbox radio2; 
    Checkbox radio3;
    Checkbox radio4;
    Checkbox varyDifficulty; 
    Button createButton;
    
    //Booleans to determine behavior of applet
    boolean mouseOverDifficulty;
    boolean mouseOverVaryDifficulty;
    boolean mouseOverSelection;

    //Constants (to determine appearance)
    Font regular = new Font("Helvetica",Font.PLAIN, 12);
    Font description = new Font("Helvetica",Font.PLAIN, 10);
    int entireWidth = 600;
    int topicListMargin = 490;
    int entireHeight = 400; 
    int verticalLocation = 100;
    int horizontalLocation = 0;
    int radioButtonLeft = horizontalLocation + 20;
    int radioButtonRight = horizontalLocation + 140;
    int radioButtonTop = verticalLocation+130;
    int radioButtonBottom = verticalLocation + 105;
    int column2 =  horizontalLocation+150;
    int DescriptionBoxY = verticalLocation + 170;
    
    Graphics bufferGraphics;
    Image offscreen;

    public void init() 
    { 
 // Tell the applet not to use a layout manager. 
       setLayout(null); 

       searchBar = new TextField("Type keywords to search worksheet topics", 20);
       searchButton = new Button ("Search");
        
       //This part makes a list that a user can click on
       
//       listModel = new DefaultListModel();
//       for(int index=0; index<Problem.problemTypes.length; index++){
//           listModel.addElement(Problem.problemTypes[index]);
//           System.out.println(Problem.problemTypes[index]);
//       }
       topicSelection = new JList(Problem.problemTypes);
       //topicSelection = new JList(listModel);

       //The following two lines are actually defaults, but I've added them for future 
       //reference and to facilitate an easier change. The alternatives to MULTIPL... is 
       //SINGLE_SELECTION of SINGLE_INTERVAL_SELECTION. The alternatives to VERT...
       //is HORIZONTAL_WRAP or VERTICAL_WRAP

       topicSelection.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
       topicSelection.setLayoutOrientation(JList.VERTICAL);

       //The next line makes the list display the maximum number of items on the screen
       //With a positive int, it will tell the scrollPane how many items to display.  
       topicSelection.setVisibleRowCount(-1);

       //Since the JList (above) is going to be large, this puts it inside of a window that
       //will allow a user to scroll to see more selections. This part is not mandatory,
       //but without it the list could go off the screen.
       listScroller = new JScrollPane(topicSelection);

        selected = new DefaultListModel();
        selectedTopics = new JList(selected);
        selectScroller = new JScrollPane(selectedTopics);
        addButton = new Button (">ADD");
        removeButton = new Button("<REMOVE");
 // text and length of the field 
       numberOfProblemsField = new TextField("6",20);    
       numberOfWorksheetsField = new TextField("1",20); 
 // initialize the radio buttons group 
         difficultySettings = new CheckboxGroup(); 
 // first radio button. Gives the label text, tells to which 
 // group it belongs and sets the default state (unselected) 
         radio1 = new Checkbox("Level 1", difficultySettings,true); 
         radio2 = new Checkbox("Level 2", difficultySettings,false); 
         radio3 = new Checkbox("Level 3", difficultySettings,false); 
         radio4 = new Checkbox("Level 4", difficultySettings,false); 
 // Label and state of the checkbox 
         varyDifficulty = new Checkbox("Vary difficulty",false);
         createButton = new Button("Create Worksheet");

 // now we will specify the positions of the GUI components. 
 // this is done by specifying the x and y coordinate and 
 //the width and height. 

         searchBar.setBounds(horizontalLocation+20,verticalLocation,300, 20);
         searchButton.setBounds (horizontalLocation+305, verticalLocation, 145, 20);
         listScroller.setBounds(horizontalLocation+40,verticalLocation,450, 100); //I am not using the setBounds method because I already have the size
         addButton.setBounds(topicListMargin-20, verticalLocation,20,50);
         removeButton.setBounds(topicListMargin-20, verticalLocation+50,20,50);
         selectScroller.setBounds(topicListMargin, verticalLocation,300,100);
         numberOfProblemsField.setBounds(column2,verticalLocation+130,20,20); 
         numberOfWorksheetsField.setBounds(column2,verticalLocation+150,20,20);
         radio1.setBounds(radioButtonLeft,radioButtonTop,15,15); 
         radio2.setBounds(radioButtonLeft,radioButtonTop + 20,15,15);
         radio3.setBounds(radioButtonLeft,radioButtonTop + 40, 15, 15);
         radio4.setBounds(radioButtonLeft,radioButtonTop + 60, 15, 15);
         varyDifficulty.setBounds(radioButtonLeft,radioButtonTop + 80,15,15);
         createButton.setBounds(horizontalLocation+100,verticalLocation+230,160,30); 

 // now that all is set we can add these components to the applet 
     setSize(entireWidth, entireHeight);
     add(searchBar);
     add(listScroller);
     add(addButton);
     add(removeButton);
     add(selectScroller); 
     add(numberOfProblemsField);
     add(numberOfWorksheetsField);
     add(radio1); 
     add(radio2);
     add(radio3);
     add(radio4);
     add(varyDifficulty);
     add(createButton); 

     // Attach actions to the components 
     createButton.addActionListener(this);
     searchButton.addActionListener(this);
     addButton.addActionListener(this);
     removeButton.addActionListener(this);
     addMouseMotionListener(this); 
     
     offscreen = createImage(entireWidth,entireHeight); 
     bufferGraphics = offscreen.getGraphics(); 
    }

    public void paint(Graphics g) { 
        bufferGraphics.clearRect(0,0,entireWidth,entireHeight); 
        bufferGraphics.setFont(regular);
        bufferGraphics.drawString("Level 1",horizontalLocation+40, verticalLocation+142 );
        bufferGraphics.drawString("Level 2",horizontalLocation+40, verticalLocation+162 ); 
        bufferGraphics.drawString("Level 3",horizontalLocation+40, verticalLocation+182 );
        bufferGraphics.drawString("Level 4",horizontalLocation+40, verticalLocation+202 );
        bufferGraphics.drawString("Gradually increase difficulty",horizontalLocation+40, verticalLocation+222 );
        bufferGraphics.drawString("Number of problems per worksheet ",horizontalLocation+175, verticalLocation+144 );
        bufferGraphics.drawString("Total number of unique worksheets",horizontalLocation+175, verticalLocation+166 );
        
        bufferGraphics.setFont(description);

        if (mouseOverDifficulty){
        bufferGraphics.drawString("Levels determine the complexity of each problem.",column2, DescriptionBoxY);
        }
        if (mouseOverVaryDifficulty){
        bufferGraphics.drawString("Selecting this box will generate worksheets that always begin at level 1 and ",column2, DescriptionBoxY);
        bufferGraphics.drawString("gradually increase in difficulty, maxing out at whatever level is selected above. ",column2, DescriptionBoxY + 12);
        }
        if (mouseOverSelection){
        bufferGraphics.drawString("Selecting more than one problem type will create a worksheet",column2, DescriptionBoxY);       
        bufferGraphics.drawString("with a random (but evenly dispersed) mix of questions", column2, DescriptionBoxY + 12);
        bufferGraphics.drawString("taken from the selection",column2, DescriptionBoxY+24);
        }
        
        //the next line takes the drawing that was made and puts it on the screen
        g.drawImage(offscreen,0,0,this);

}

         //the overwrites the method that wipes the screen before repainting. Here, 
         //the method calls to simply repaint and this is okay because the "wiping" is done in the bufferGraphics
         public void update(Graphics g) 
     { 
          paint(g); 
     } 
 

    
    public void actionPerformed(ActionEvent evt)  
    { 
        if (evt.getSource() == searchButton){
        bufferGraphics.drawString("Search hasn't been setup yet!",column2, DescriptionBoxY);
//        	String keyword = searchBar.getText();
//        ArrayList<String> reducedList = new ArrayList<String>();
//        for (int index=0; index<listModel.getSize(); index++){
//        	reducedList.add((String) listModel.get(index));
//        }
//        for (int index=0; index<reducedList.size(); index++){
//            if (!reducedList.get(index).contains(keyword)) reducedList.remove(index);
//            listModel.
        }
        
        if (evt.getSource() == addButton && !selected.contains(topicSelection.getSelectedValue()))           
            selected.addElement(topicSelection.getSelectedValue());
        
        if (evt.getSource() == removeButton){
            if (selected.getSize()==0) removeButton.setEnabled(false);
            else selected.remove(selected.indexOf(selectedTopics.getSelectedValue()));
            //selectedTopics.setSelectedIndex(index);//I thought this line was for replacing the selection (since an item was removed) but it turns out this line created an error
            }
    
        if (evt.getSource() == createButton)  {
             String[] problemType = new String[selected.getSize()];
             for (int index = 0; index < selected.getSize(); index++)
                 problemType[index] = (String)selected.get(index);
        int difficulty = 1;
        boolean variableDifficulty=false;
        
        int numberOfProblems=Integer.parseInt(numberOfProblemsField.getText());
        int numberOfWorksheets=Integer.parseInt(numberOfWorksheetsField.getText());
             
             
             if (radio1.getState()) difficulty=1;
             if (radio2.getState()) difficulty=2;
             if (radio3.getState()) difficulty=3;
             if (radio4.getState()) difficulty=4;
             if (varyDifficulty.getState()) variableDifficulty=true;
             
             try {
               Worksheet.create(problemType, difficulty, variableDifficulty, numberOfProblems, numberOfWorksheets);
           } catch (DocumentException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
         }
       } 
 
     public void mouseMoved(MouseEvent mouseEvent){} 
     public void mouseDragged(MouseEvent mouseEvent){
     	if (mouseEvent.getX()>radioButtonLeft && mouseEvent.getX()<radioButtonRight
             && mouseEvent.getY()>radioButtonTop && mouseEvent.getY()<radioButtonBottom)
             mouseOverDifficulty = true;
             else mouseOverDifficulty=false;
        if (mouseEvent.getX()>radioButtonLeft && mouseEvent.getX()<radioButtonRight
            && mouseEvent.getY()>radioButtonBottom && mouseEvent.getY()<radioButtonBottom+20)
            mouseOverVaryDifficulty = true;
            else mouseOverVaryDifficulty = false;
        if (mouseEvent.getX()>topicListMargin && mouseEvent.getX()<topicListMargin+300
            && mouseEvent.getY()>verticalLocation && mouseEvent.getY()<verticalLocation+100)
            mouseOverSelection = true;
            else  mouseOverSelection = false;
            repaint();
     } 

   }