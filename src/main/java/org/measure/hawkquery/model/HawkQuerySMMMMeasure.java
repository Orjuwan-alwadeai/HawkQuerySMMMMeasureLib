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
package org.measure.hawkquery.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.measure.hawkquery.impl.HawkQueryConstants;
import org.measure.smm.measure.model.MeasureType;
import org.measure.smm.measure.model.MeasureUnite;
import org.measure.smm.measure.model.SMMMeasure;
import org.measure.smm.measure.model.ScopeProperty;

public class HawkQuerySMMMMeasure extends SMMMeasure {
	private String serverUrl;
	private String username;
	private String password;

	private String instanceName;

	private String queryLanguage;
	private String query;

	/** queryOptions */
	/**  (optional)	The default namespaces to be used to resolve ambiguous unqualified types. */
	//private String defaultNamespaces; 		
	private List<String> defaultNamespaces = new ArrayList<String>(); 

	/**  (optional)	If set and not empty, the mentioned metamodels, types and features will not be fetched. The string '*' can be used to refer to all types within a metamodel or all fields within a type. */
	private Map<String,Map<String,Set>> effectiveMetamodelExcludes = new HashMap<String, Map<String,Set>>(); 	

	/**  (optional)	If set and not empty, only the specified metamodels, types and features will be fetched. Otherwise, everything that is not excluded will be fetched. The string '*' can be used to refer to all types within a metamodel or all fields within a type. */
	private Map<String,Map<String,Set>>  effectiveMetamodelIncludes = new HashMap<String, Map<String,Set>>(); 	

	/**  (optional)	The file patterns for the query (e.g. *.uml). */
	private List<String> filePatterns = new ArrayList<String>(); 									

	/**  (optional)	Whether to include attributes (true) or not (false) in model element results. */
	private boolean includeAttributes; 									

	/**  (optional)	Whether to include all the child elements of the model element results (true) or not (false). */
	private boolean includeContained; 									

	/**  (optional)	Whether to include derived attributes (true) or not (false) in model element results. */
	private boolean includeDerived; 									

	/**  (optional)	Whether to include node IDs (true) or not (false) in model element results. */
	private boolean includeNodeIDs; 									

	/**  (optional)	Whether to include references (true) or not (false) in model element results. */
	private boolean includeReferences;

	/**  (optional)	The repository for the query (or * for all repositories). */
	private List<String> repository = new ArrayList<String>();  			

	public HawkQuerySMMMMeasure() {
		super();
		this.setName("HawkQuerySMMMMeasureLib");
		this.setDescription("HawkQuerySMMMMeasureLib");
		this.setType(MeasureType.DIRECT);
		this.setUnite(MeasureUnite.Numeric);

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
		replaceScopeProperty(HawkQueryConstants.SERVER_URL/*, ScopePropertyType.STRING*/, serverUrl, "Server URL inclusing Protocol");
		this.serverUrl = serverUrl;
	}

	public void setUsername(String username) {
		replaceScopeProperty(HawkQueryConstants.USERNAME/*, ScopePropertyType.STRING*/, username, "username");
		this.username = username;
	}

	public void setPassword(String password) {
		replaceScopeProperty(HawkQueryConstants.PASSWORD/*, ScopePropertyType.STRING*/, password, "passeword");
		this.password = password;
	}

	public void setInstanceName(String instanceName) {
		replaceScopeProperty(HawkQueryConstants.INSTANCE_NAME/*, ScopePropertyType.STRING*/, instanceName, "Hawk instance to Query");
		this.instanceName = instanceName;
	}

	public void setQueryLanguage(String queryLanguage) {
		replaceScopeProperty(HawkQueryConstants.QUERY_LANGUAGE/*, ScopePropertyType.STRING*/, queryLanguage, "Query Language");
		this.queryLanguage = queryLanguage;
	}

	public void setQuery(String query) {
		replaceScopeProperty(HawkQueryConstants.QUERY/*, ScopePropertyType.STRING*/, query, "Query Logic");
		this.query = query;
	}

	public void setDefaultNamespaces(String defaultNamespaces) {
		replaceScopeProperty(HawkQueryConstants.DEFAULT_NAMESPACES/*, ScopePropertyType.STRING*/, defaultNamespaces, "(optional) The default namespaces to be used to resolve ambiguous unqualified types.");
		//this.defaultNamespaces = defaultNamespaces;
	}

	public void setEffectiveMetamodelExcludes(Map<String, Map<String, Set>> effectiveMetamodelExcludes) {
		addScopeProperty("effectiveMetamodelExcludes"/*, ScopePropertyType.STRING*/, "*", "");
		//this.effectiveMetamodelExcludes = effectiveMetamodelExcludes;
	}

	public void setEffectiveMetamodelIncludes(Map<String, Map<String, Set>> effectiveMetamodelIncludes) {
		addScopeProperty("effectiveMetamodelIncludes"/*, ScopePropertyType.STRING*/, "*", "");
		//this.effectiveMetamodelIncludes = effectiveMetamodelIncludes;
	}

	public void setIncludeAttributes(boolean includeAttributes) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_ATTRIBUTES/*, ScopePropertyType.STRING*/, String.valueOf(includeAttributes), "");
		this.includeAttributes = includeAttributes;
	}

	public void setIncludeContained(boolean includeContained) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_CONTAINED/*, ScopePropertyType.STRING*/, String.valueOf(includeContained), "");
		this.includeContained = includeContained;
	}

	public void setIncludeDerived(boolean includeDerived) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_DERIVED/*, ScopePropertyType.STRING*/, String.valueOf(includeDerived), "");
		this.includeDerived = includeDerived;
	}

	public void setIncludeNodeIDs(boolean includeNodeIDs) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_NODE_IDs/*, ScopePropertyType.STRING*/, String.valueOf(includeNodeIDs), "");
		this.includeNodeIDs = includeNodeIDs;
	}

	public void setIncludeReferences(boolean includeReferences) {
		replaceScopeProperty(HawkQueryConstants.INCLUDE_REFERENCES/*, ScopePropertyType.STRING*/, String.valueOf(includeReferences), "");
		this.includeReferences = includeReferences;
	}

	public void addDefaultNamespace(String newValue) {
		this.defaultNamespaces.add(newValue);
		String property = listToCommaSeperatedString(this.defaultNamespaces);
		replaceScopeProperty(HawkQueryConstants.DEFAULT_NAMESPACES/*, ScopePropertyType.STRING*/, property, "(optional) The default namespaces to be used to resolve ambiguous unqualified types.");
	}
	
	public void addFilePattern(String newValue) {
		this.filePatterns.add(newValue);
		String property = listToCommaSeperatedString(this.filePatterns);
		replaceScopeProperty(HawkQueryConstants.FILE_PATTERNS/*, ScopePropertyType.STRING*/, property, "(optional)	The file patterns for the query (e.g. *.uml)");
	}
	
	public void addRepository(String newValue) {
		this.repository.add(newValue);
		String property = listToCommaSeperatedString(this.repository);
		replaceScopeProperty(HawkQueryConstants.REPOSITORY/*, ScopePropertyType.STRING*/, property, "(optional)	The repository for the query (or * for all repositories)");
	}

	private ScopeProperty addScopeProperty(String name, /*ScopePropertyType type,*/ String defaultValue, String description) {

		ScopeProperty property = new ScopeProperty();

		property.setName(name);
//		property.setType(type);
		property.setDefaultValue(defaultValue);
		property.setDescription(description);

		this.getScopeProperties().add(property);

		return property;
	}

	private void replaceScopeProperty(String name, /*ScopeProperty type,*/ String defaultValue, String description) {
		
		for(ScopeProperty property : this.getScopeProperties()) {
			if(property.getName().equals(name)) {
				// replace
				//property.setType(type);
				property.setDefaultValue(defaultValue);
				property.setDescription(description);
				return;
			}
		}
		
		// else add
		addScopeProperty(name, /*type, */defaultValue, description);
		
	}

//	private void addScopeProperty(String name, ScopePropertyEnum enumType, String defaultValue, String description) {
//		ScopeProperty property = addScopeProperty(name, ScopePropertyType.ENUM, defaultValue, description);
//
//		property.setEnumType(enumType);
//	}
	
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
