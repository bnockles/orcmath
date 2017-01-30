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
package com.orcmath.objects;

public class PythagoreanTriple {

	int[] ex1={3,4,5};
	int[] ex2={5,12,13};
	int[] ex3={7,24,25};
	int[] ex4={8,15,17};
	int[] ex5={9,40,41};
	int[][] examples= {ex1,
			ex2,
			ex3,
			ex4, 
			ex5
			};
	
	int[] selectedExample;
	
	public PythagoreanTriple(int maxMultiplier){
		int[] startsWith = examples[Ops.randomInt(0, examples.length-1)];
		int multiplier =1;
		multiplier = Ops.randomInt(1, maxMultiplier);
		selectedExample= new int[3];
		for(int index = 0; index<3; index++){
			selectedExample[index]=startsWith[index]*multiplier;
		}
		
	}

	public int[] getSelectedExample() {
		return selectedExample;
	}
	
	
	
}
