package code;

/*
 * Copyright (c) 2013, Erika Nana
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Project 1 nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Erika Nana ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Erika Nana BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.util.ArrayList;

/**
 * Represents the data of either an arc or vertex.
 * @author Original Author Erika Nana
 * 
 */
public class Data {
	
	/** The data. */
	ArrayList<String> data;
	
	/**
	 * Instantiates new data.
	 */
	public Data() {
		data = new ArrayList<String>();
	}
	
	/**
	 * Gets the data and returns it in a formatted string.
	 *
	 * @return the data
	 */
	public String getData() {
		String returnString = new String();
		if (data.isEmpty()) {
			return null;
		}
		if (data.size() == 1) {
			return data.get(0);
		}
		for (int i= 0; i < data.size(); i++) {
			if(i == data.size() - 1) {
				return returnString.concat(data.get(i));
			}
			returnString = returnString.concat(data.get(i) + ", ");
		}
		return returnString;
	}
	
	/**
	 * Add data to overall data.
	 *
	 * @param attribute the attribute
	 */
	public void addData(String attribute) {
		data.add(attribute);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getData();
	}
	
	/**
	 * Checks if data is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}
}
