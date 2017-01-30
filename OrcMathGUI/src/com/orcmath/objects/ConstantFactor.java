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

public class ConstantFactor
{
    int constant;
    int exp;
    
    public ConstantFactor(int base)
    {
        constant = base;
        exp = 1;
    }
    
    public ConstantFactor(int base,int exponent)
    {
        constant = base;
        exp = exponent;
    }
    
    public String toString()
    {
        return "" + constant + "^" + exp;
    }
    
    public void setExponent(int exponent)
    {
        exp = exponent;
    }
    
    public int getExponent()
    {
        return exp;
    }
    
    public int getBase()
    {
    	return constant;
    }
    
    public boolean equals(Object cf) {
        boolean result;
    	if (cf == null) return false;
    	if(!(cf instanceof ConstantFactor)) return false;
    	else{
        ConstantFactor other = (ConstantFactor)cf;
        result = constant == other.constant && exp == other.exp;
    	}
    	return result;
    }
    
    public int hashCode(){
    	return (int)Math.pow(constant, exp);	
    }
    
}