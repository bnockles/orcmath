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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;



public class question {
	public static void main(String[] args) {
		InetAddress ip;
		try {

		ip = InetAddress.getLocalHost();
		System.out.println("Current IP address : " + ip.getHostAddress());

		NetworkInterface network = NetworkInterface.getByInetAddress(ip);

		byte[] mac = network.getHardwareAddress();

		System.out.print("Current MAC address : ");

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		}
		System.out.println(sb.toString());

	} catch (UnknownHostException e) {

		e.printStackTrace();

	} catch (SocketException e){

		e.printStackTrace();

	}
	}
	
	public static void blah() {
		ArrayList<String> solutions = new ArrayList<String>();
		for (int indexA = 1; indexA<10; indexA++){
			for (int indexB = 1; indexB<10; indexB++){
				for (int indexC = 1; indexC<10; indexC++){
					for (int indexD = 1; indexD<10; indexD++){
						for (int indexE = 1; indexE<10; indexE++){
							for (int indexF = 1; indexF<10; indexF++){
								if (indexA!=indexB && 
										indexA!=indexC &&
										indexA!=indexD &&
										indexA!=indexE &&
										indexA!=indexF &&
										indexB!=indexC &&
										indexB!=indexD &&
										indexB!=indexE &&
										indexB!=indexF &&
										indexC!=indexD &&
										indexC!=indexE &&
										indexC!=indexF &&
										indexD!=indexE &&
										indexD!=indexF &&
										indexE!=indexF){
									int digit1 = indexA*100+indexB*10+indexC;
									int digit2 = indexD*100+indexE*10+indexF;
									int digit3 = digit1+digit2;
									
									int z = digit3%10;
									int y = (digit3%100-z)/10;
									int x = (digit3-y-z)/100;
									//System.out.println(""+x);
									if (x<10){
										//System.out.println("" + digit1 +" + " + digit2 + " = " + digit3);
										if (	z!=indexA &&
												z!=indexB &&
												z!=indexC &&
												z!=indexD &&
												z!=indexE &&
												z!=indexF &&
												z!=y &&
												z!=x &&
												y!=indexA &&
												y!=indexB &&
												y!=indexC &&
												y!=indexD &&
												y!=indexE &&
												y!=indexF &&
												y!=x&&
												x!=indexA &&
												x!=indexB &&
												x!=indexC &&
												x!=indexD &&
												x!=indexE &&
												x!=indexF &&
												indexA!=0 &&
												indexB!=0 &&
												indexC!=0 &&
												indexD!=0 &&
												indexE!=0 &&
												indexF!=0 &&
												x!=0 &&
												y!=0 &&
												z!=0){

											String s = "" + digit1 +" + " + digit2 + " = " + digit3 + " and the sum = "+(x+y+z);
											solutions.add(s);
										}
									}
								}
			
							}
						}
					}
				}
			}
		}
		for (int index = 0; index<solutions.size(); index++){
			System.out.println(solutions.get(index));
		}
		System.out.println("There are " + solutions.size()/2 + " non-trivial solutions!");
	}
}
