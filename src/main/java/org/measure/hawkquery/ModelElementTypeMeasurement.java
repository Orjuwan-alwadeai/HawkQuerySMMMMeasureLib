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
package org.measure.hawkquery;

import org.hawk.service.api.ModelElementType;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class ModelElementTypeMeasurement extends DefaultMeasurement {
	public ModelElementTypeMeasurement(){

	}
	
	public void setValue( ModelElementType value){
		addValue("value",value);
	}
	
	public  ModelElementType getValue(){
		return ( ModelElementType) getValues().get("value");
	}
	
	
	@Override
	public String getLabel() {
		return getValues().get("value").toString();
	}
	
	
	
	
	/*  public String id; // required
	  public String metamodelUri; // required
	  public String typeName; // required
	  public List<SlotMetadata> attributes; // optional
	  public List<SlotMetadata> references; // optional
	  
	  SlotMetadata {

		  public String name; // required
		  public String type; // required
		  public boolean isMany; // required
		  public boolean isOrdered; // required
		  public boolean isUnique; // required
	  }
	  */
	  
}
