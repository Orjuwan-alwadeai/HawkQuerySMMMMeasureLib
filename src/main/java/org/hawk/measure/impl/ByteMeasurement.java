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

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class ByteMeasurement extends DefaultMeasurement {
	public ByteMeasurement(){
	}
	
	/**
	 * set the measurement value
	 * @param value measurement value
	 * 
	 */
	public void setValue(Byte value){
		addValue("value",value);
	}
	
	/**
	 * returns the measurement value
	 * @return measurement value
	 * 
	 */
	public Byte getValue(){
		return (Byte) getValues().get("value");
	}
	
	/**
	 * @return string representation of measurement value
	 * 
	 */
	@Override
	public String getLabel() {
		return getValues().get("value").toString();
	}
}
