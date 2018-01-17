package guiTeacher.userInterfaces;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


import guiTeacher.interfaces.FileRequester;


/**
 * An instance of this class is created by FileOpenButton. This class handles the file selection interface. See FileOpenButton
 * @author bnockles
 *
 */
public class FileLoader {




	public FileLoader(FileRequester ui){
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES","csv","csv");
		fc.setFileFilter(filter);
		//In response to a button click:
		int returnVal = fc.showOpenDialog(ui.getWindow());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			ui.setFile(file);

		} else {

		}
	}
	
	public static List<String> getFileAsLines(File file){
		List<String> content = new ArrayList<String>();
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			String line = "";
			int foundContent = 0;
			BufferedReader br = new BufferedReader(fileReader);
			while ((line = br.readLine()) != null) {

					content.add(line);

					/*
					 * useful later:
					 
					String[] row = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);//split only a comma that has an even number of quotes ahead of it

					*/


			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return content;

	}




}
