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
package org.measure.hawkquery.impl;

import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class ByteMeasurement extends DefaultMeasurement {
	public ByteMeasurement(){
	}
	
	public void setValue(Byte value){
		addValue("value",value);
	}
	
	public Byte getValue(){
		return (Byte) getValues().get("value");
	}
	
	@Override
	public String getLabel() {
		return getValues().get("value").toString();
	}
}
