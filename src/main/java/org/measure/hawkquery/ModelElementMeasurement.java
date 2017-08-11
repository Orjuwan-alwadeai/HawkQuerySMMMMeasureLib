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

import org.hawk.service.api.ModelElement;
import org.measure.smm.measure.defaultimpl.measurements.DefaultMeasurement;

public class ModelElementMeasurement extends DefaultMeasurement {
	public ModelElementMeasurement(){

	}
	
	public void setValue( ModelElement value){
		addValue("value",value);
	}
	
	public  ModelElement getValue(){
		return ( ModelElement) getValues().get("value");
	}
	
	
	@Override
	public String getLabel() {
		return getValues().get("value").toString();
	}  
/*	public String id; // optional
	  public String repositoryURL; // optional
	  public String file; // optional
	  public String metamodelUri; // optional
	  public String typeName; // optional
	  public List<AttributeSlot> attributes; // optional 
	  AttributeSlot {
		  public String name; // required
		  public SlotValue value; // optional
	  }
	  
	  
	  
	  public List<ReferenceSlot> references; // optional
	 
	  ReferenceSlot {
		  public String name; // required
		  public int position; // optional
		  public List<Integer> positions; // optional
		  public String id; // optional
		  public List<String> ids; // optional
		  public List<MixedReference> mixed; // optional
	  }
	  
	  MixedReference {
		  String id;
		  int position
	  }
	  public List<ContainerSlot> containers; // optional
	  ContainerSlot {
		  public String name; // required
		  public List<ModelElement> elements; // required
	  }
	  
	public ModelElementMeasurement() {
		Map<String,Object> datas = getValues();
		datas.put("cpu", cpu);
		datas.put("mem", mem);
		datas.put("disks", disk);
	}
	
	
	
	
	@Override
	public String getLabel() {
		return "cpu : " + getValues().get("cpu") + "%, mem : " 
				+ getValues().get("mem")+ "%, disks : " 
				+ getValues().get("disks") +"%" ;
	}*/
}
