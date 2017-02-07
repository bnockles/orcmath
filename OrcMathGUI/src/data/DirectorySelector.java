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

import java.io.File;

import javax.swing.JFileChooser;

import main.OrcMath;


public class DirectorySelector {

	public DirectorySelector() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//In response to a button click:
		int returnVal = fc.showOpenDialog(OrcMath.app);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			OrcMath.createScreen.setDirectory(file);
			OrcMath.settings.setDirectoryText(file.getAbsolutePath());
			OrcMath.sd.saveData();
//			ui.setCsv(new AttendanceCsv(ui, file));
		} else {

		}
	}

}
