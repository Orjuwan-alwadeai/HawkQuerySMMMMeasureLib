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
package org.hawk.measure.model;

import java.util.ArrayList;
import java.util.List;

import org.hawk.measure.impl.HawkQueryConstants;
import org.measure.smm.measure.model.MeasureType;
import org.measure.smm.measure.model.MeasureUnite;
import org.measure.smm.measure.model.SMMMeasure;
import org.measure.smm.measure.model.ScopeProperty;

public class HawkQuerySMMMMeasure extends SMMMeasure {
	private List<String> defaultNamespaces = new ArrayList<String>(); 

	private List<String> filePatterns = new ArrayList<String>(); 									

	private List<String> repository = new ArrayList<String>();  			

	public HawkQuerySMMMMeasure() {
		super();
		this.setName("HawkQueryMeasure");
		this.setDescription("Generic Query for Hawk Server tool");
		this.setType(MeasureType.DIRECT);
		this.setUnite(MeasureUnite.Undefined);

		this.setServerUrl("http://localhost:8080/thrift/hawk/tuple");
		this.setUsername("");
		this.setPassword("");
		this.setInstanceName("");
		this.setQueryLanguage("org.hawk.epsilon.emc.EOLQueryEngine");
		this.setQuery("");
		this.addDefaultNamespace("");
		this.addFilePattern("");
		this.setIncludeAttributes(true);
		this.setIncludeContained(true);
		this.setIncludeDerived(true);
		this.setIncludeNodeIDs(false);
		this.setIncludeReferences(true);
		this.addRepository("");
	}
	
	public HawkQuerySMMMMeasure(String name, String description) {
		this();
		this.setName(name);
		this.setDescription(description);
	}

	public void setServerUrl(String serverUrl) {
		replaceScopeProperty(HawkQueryConstants.SERVER_URL, serverUrl, "Server URL inclusing Protocol");
	}

	public void setUsername(String username) {
		replaceScopeProperty(HawkQueryConstants.USERNAME, username, "username");
	}

	public void setPassword(String password) {
		replaceScopeProperty(HawkQueryConstants.PASSWORD, password, "passeword");
	}

	public void setInstanceName(String instanceName) {
		replaceScopeProperty(HawkQueryConstants.INSTANCE_NAME, instanceName, "Hawk instance to Query");
	}

	public void setQueryLanguage(String queryLanguage) {
		replaceScopeProperty(HawkQueryConstants.QUERY_LANGUAGE, queryLanguage, "Query Language");
	}

	public void setQuery(String query) {
		replaceScopeProperty(HawkQueryConstants.QUERY, query, "Query Logic");
	}

	public void setDefaultNamespaces(String defaultNamespaces) {
		replaceScopeProperty(HawkQueryConstants.DEFAULT_NAMESPACES, defaultNamespaces, "(optional) The default namespaces to be used to resolve ambiguous unqualified types.");
	}

	public void setIncludeAttributes(boolean includeAttributes) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_ATTRIBUTES, String.valueOf(includeAttributes), "");
	}

	public void setIncludeContained(boolean includeContained) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_CONTAINED, String.valueOf(includeContained), "");
	}

	public void setIncludeDerived(boolean includeDerived) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_DERIVED, String.valueOf(includeDerived), "");
	}

	public void setIncludeNodeIDs(boolean includeNodeIDs) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_NODE_IDs, String.valueOf(includeNodeIDs), "");
	}

	public void setIncludeReferences(boolean includeReferences) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_REFERENCES, String.valueOf(includeReferences), "");
	}

	public void addDefaultNamespace(String newValue) {
		this.defaultNamespaces.add(newValue);
		String property = listToCommaSeperatedString(this.defaultNamespaces);
		replaceScopeProperty(HawkQueryConstants.DEFAULT_NAMESPACES, property, "(optional) The default namespaces to be used to resolve ambiguous unqualified types.");
	}
	
	public void addFilePattern(String newValue) {
		this.filePatterns.add(newValue);
		String property = listToCommaSeperatedString(this.filePatterns);
		replaceScopeProperty(HawkQueryConstants.FILE_PATTERNS, property, "(optional)	The file patterns for the query (e.g. *.uml)");
	}
	
	public void addRepository(String newValue) {
		this.repository.add(newValue);
		String property = listToCommaSeperatedString(this.repository);
		replaceScopeProperty(HawkQueryConstants.REPOSITORY, property, "(optional)	The repository for the query (or * for all repositories)");
	}

	private ScopeProperty addScopeProperty(String name, String defaultValue, String description) {

		ScopeProperty property = new ScopeProperty();

		property.setName(name);
		property.setDefaultValue(defaultValue);
		property.setDescription(description);

		this.getScopeProperties().add(property);

		return property;
	}

	private void replaceScopeProperty(String name, String defaultValue, String description) {
		
		for(ScopeProperty property : this.getScopeProperties()) {
			if(property.getName().equals(name)) {
				// replace
				property.setDefaultValue(defaultValue);
				property.setDescription(description);
				return;
			}
		}
		
		// else add
		addScopeProperty(name, defaultValue, description);
		
	}
	
	private String listToCommaSeperatedString(List<String> list) {
		StringBuffer property = new StringBuffer();
		
		for(String item : list) {
			if(property.length() > 0) {
				property.append(',');
			}
			property.append(item);
		}
		return property.toString();
	}
}
