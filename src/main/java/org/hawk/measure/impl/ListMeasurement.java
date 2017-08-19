/*******************************************************************************
 * Copyright (c) 2017 Aston University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Orjuwan Al-Wadeai - Hawk Query SMMM Measure Implementation
 ******************************************************************************/
package org.hawk.measure.impl;

import java.util.ArrayList;
import java.util.List;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class ListMeasurement extends DefaultMeasurement {
	List<IMeasurement> measurementList = new ArrayList<IMeasurement>();
	
	public ListMeasurement(){
		setList(measurementList);
	}
	
	public void setList(List<IMeasurement> value){
		addValue("value", value);
	}
	
	@SuppressWarnings("unchecked")
	public List<IMeasurement> getList(){
		return (List<IMeasurement>) getValues().get("value");
	}
	
	@SuppressWarnings("unchecked")
	public void add(IMeasurement measurement) {
		((List<IMeasurement>) getValues().get("value")).add(measurement);
	}
	
	@Override
	public String getLabel() {
		String label = "";
		for(IMeasurement measurement : measurementList) {
			if(!label.isEmpty()) {
				label += ", ";
			}
			
			label += measurement.getLabel();	
		}
		
		return "[" + label + "]";
	}
}
