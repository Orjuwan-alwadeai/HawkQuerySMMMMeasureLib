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

import org.hawk.service.api.ModelElement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class ModelElementMeasurement extends DefaultMeasurement {
	ModelElement localValue;

	
	public ModelElementMeasurement(){

	}

	/**
	 * sets ModelElement measurement as (key, value) elements
	 * @param value ModelElement object
	 */
	public void setValue(ModelElement value){
		localValue = value;
		
		if (value.isSetId()) {
			addValue("id", value.getId());
		}

		if (value.isSetRepositoryURL()) {
			addValue("repositoryURL", value.getRepositoryURL());
		}

		if (value.isSetFile()) {
			addValue("file", value.getFile());
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
		
		if (value.isSetContainers()) {
			addValue("containers", String.valueOf(value.getContainers()));
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
