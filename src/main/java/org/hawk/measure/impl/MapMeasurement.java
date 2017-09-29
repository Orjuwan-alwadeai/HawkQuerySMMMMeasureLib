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

import java.util.Map.Entry;

import org.measure.smm.measure.api.IMeasurement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class MapMeasurement extends DefaultMeasurement {
	public MapMeasurement(){
	}

	
	/**
	 * adds a new (key, measurement object) to map
	 * @param key string key 
	 * @param measurement object value
	 */
	public void add(String key, IMeasurement measurement) {
		getValues().put(key, measurement);
	}

	/**
	 * @return string representation of measurement value
	 * 
	 */
	@Override
	public String getLabel() {
		String label = "";
		for(Entry<String, Object> entry : getValues().entrySet()) {
			if(!label.isEmpty()) {
				label += ", ";
			}
			label +=  "(";
			label +=  entry.getKey();
			label +=  ", ";
			if(entry.getValue() instanceof IMeasurement) {
			label += (((IMeasurement) entry.getValue())).getLabel();
			} else {
				label += entry.getValue().toString();
			}
			label +=  ")";

		}

		return "{" + label + "}";
	}
}
