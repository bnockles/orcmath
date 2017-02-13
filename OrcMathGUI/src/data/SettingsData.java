/*******************************************************************************
 * Copyright (c) 2017 Benjamin Nockles
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
package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import components.UpdateNotification;
import main.OrcMath;

public class SettingsData {

	private static final String saveFile = "resources/settings";
	private String directory;
	private String teacherName;
	private boolean overrideVerticalSpacing;
	private int vSpacing;
	//TODO: Add to save file
	private int columns;
	private int pages;
	private boolean instructionsForEachProblem;
	private boolean includeHeader;
	private boolean includeMainInstructions;
	private int worksheetType;
	private ArrayList<UpdateNotification> updateNotifications;
	private boolean presentOutdatedNotifcationAfterLoad;
	private boolean shuffleOrder;
	
	public SettingsData() {
		
		updateNotifications = new ArrayList<UpdateNotification>();
		presentOutdatedNotifcationAfterLoad = false;
		try {
			loadData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void checkForUpdates(String[] versionLog){
		if(versionLog != null && versionLog.length >0){
			String mostRecentUpdate = versionLog[versionLog.length-1];
			//remove opening and closing "
			mostRecentUpdate=mostRecentUpdate.substring(1, mostRecentUpdate.length()-1);
			String[] aboutRecentVersion = mostRecentUpdate.split("\",\"");
			UpdateNotification recordFound = null;
			for(UpdateNotification un : updateNotifications){
				//if this version is new...
				if(un.getTitle().equals(aboutRecentVersion[1])){
					recordFound = un;
					break;
				}
			}
			if(recordFound != null){
				if(recordFound.getResponse().equals(UpdateNotification.RESPONSE_IGNORED)){
					Date today = new Date(System.currentTimeMillis());
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					try {
						Date lastResponse = sdf.parse(recordFound.getDate());
						long diffInMillies = today.getTime() - lastResponse.getTime();
						if(diffInMillies > 604800000){
							OrcMath.createScreen.presentUpdateNotification(recordFound);
						}
					} catch (ParseException e) {
						OrcMath.createScreen.presentUpdateNotification(recordFound);
					}
				}else if(!recordFound.getResponse().equals(UpdateNotification.RESPONSE_NEW)){
					OrcMath.createScreen.presentUpdateNotification(recordFound);
				}
			}else{
				OrcMath.createScreen.presentUpdateNotification(new UpdateNotification(aboutRecentVersion));
			}
		}
	}


	private void loadData() throws FileNotFoundException {
		FileReader fileReader = new FileReader(saveFile);
		BufferedReader br = null;
		String line = "";

		try {

			br = new BufferedReader(fileReader);
			int lineNumber = 0;
			while ((line = br.readLine()) != null) {

				System.out.println("Line number "+lineNumber+": "+line);
				try{
					String[] row = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);//split only a comma that has an even number of quotes ahead of it
					//remove quotes
					for(int i=0; i < row.length; i++){
						row[i] = row[i].substring(1, row[i].length()-1);
					}
					if(lineNumber ==1){
						directory=row[0];
						teacherName=row[1];
						overrideVerticalSpacing = Boolean.parseBoolean(row[2]);
						vSpacing = Integer.parseInt(row[3]);
						columns = Integer.parseInt(row[4]);
						pages = Integer.parseInt(row[5]);
						instructionsForEachProblem = Boolean.parseBoolean(row[6]);
						worksheetType = Integer.parseInt(row[7]);
						includeHeader = Boolean.parseBoolean(row[8]);
						includeMainInstructions = Boolean.parseBoolean(row[9]);
						//NOTE: In all later versions, it is not guaranteed that there are more than 9 fields
						if(row.length>10)shuffleOrder = Boolean.parseBoolean(row[10]);
						else{
							shuffleOrder = false;
						}
						
					}if(lineNumber >1){
						updateNotifications.add(new UpdateNotification(row));
						
					}
					lineNumber++;
				}catch(Exception e){
					
					directory="";
					teacherName="Mr. Nockles";
					overrideVerticalSpacing = false;
					vSpacing = 40;
					columns = 1;
					pages = 1;
					instructionsForEachProblem = false;
					worksheetType = 0;
					includeHeader = true;
					includeMainInstructions = true;
					shuffleOrder = false;
					
//					presentOutdatedNotifcationAfterLoad=true;
				}

			}
			fileReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean presentOutdatedNotification(){
		return presentOutdatedNotifcationAfterLoad;
	}


	public String getDirectory() {
		return directory;
	}


	public void saveData(){
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(saveFile);
			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter.write("\"Directory\",\"Teacher Name\"\n");	
			bufferedWriter.write("\""+OrcMath.createScreen.getSaveDirectory()+"\","
					+ "\""+teacherName+"\","
							+ "\""+overrideVerticalSpacing+"\","
									+ "\""+vSpacing+"\",\""
											+ columns+"\",\""
													+ pages+"\",\""
															+ instructionsForEachProblem+"\",\""
																	+ worksheetType+"\",\""
																			+ includeHeader+"\",\""
																					+ includeMainInstructions+"\",\""
																							+ shuffleOrder+"\"\n");//has loaded for the first time
			for(UpdateNotification un: updateNotifications){
				bufferedWriter.write(un.getSaveLine()+"\n");
			}
			// Always close files.
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String getTeacherName() {
		return teacherName;
	}
	
	public void setTeacherName(String name) {
		teacherName = name;
	}


	public int getVSpacing() {
		return vSpacing;
	}
	
	public void setVSpacing(int vSpacing){
		this.vSpacing = vSpacing;
	}


	public boolean isOverrideVerticalSpacing() {
		return overrideVerticalSpacing;
	}


	public void setOverrideVerticalSpacing(boolean overrideVerticalSpacing) {
		this.overrideVerticalSpacing = overrideVerticalSpacing;
	}

	public void recordResponse(UpdateNotification un) {
		if(updateNotifications.contains(un)){
			saveData();
		}else{
			updateNotifications.add(un);
			saveData();
		}
	}

	public boolean isIncludeHeader() {
		return includeHeader;
	}

	public boolean isIncludeMainInstructions() {
		return includeMainInstructions;
	}

	public void setIncludeMainInstructions(boolean includeMainInstructions) {
		this.includeMainInstructions = includeMainInstructions;
	}

	public void setIncludeHeader(boolean includeHeader) {
		this.includeHeader = includeHeader;
	}

	public boolean isShuffleOrder() {
		return shuffleOrder;
	}

	public void setShuffleOrder(boolean shuffleOrder) {
		this.shuffleOrder = shuffleOrder;
	}
	
	



}

