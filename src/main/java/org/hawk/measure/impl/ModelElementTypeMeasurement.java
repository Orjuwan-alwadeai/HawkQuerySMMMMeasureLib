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

import org.hawk.service.api.ModelElementType;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class ModelElementTypeMeasurement extends DefaultMeasurement {
	
	ModelElementType localValue;
	public ModelElementTypeMeasurement(){
	}
	
	/**
	 * sets ModelElementType measurement as (key, value) elements
	 * @param value ModelElementType object
	 */
	public void setValue(ModelElementType value){
		localValue = value; 
		if (value.isSetId()) {
			addValue("id", value.getId());
		}

		if (value.isSetMetamodelUri()) {
			addValue("metamodelUri", value.getMetamodelUri());
		}

		if (value.isSetTypeName()) {
			addValue("typeName", value.getTypeName());
		}

		if (value.isSetAttributes()) {
			addValue("attributes", String.valueOf(value.getAttributes()));
		}

		if (value.isSetReferences()) {
			addValue("references", String.valueOf(value.getReferences()));
		}
	}
	
	/**
	 * @return string representation of measurement value
	 * 
	 */
	@Override
	public String getLabel() {
		return localValue.toString();
	}
}
