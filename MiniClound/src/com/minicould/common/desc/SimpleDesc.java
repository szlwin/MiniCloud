package com.minicould.common.desc;

import com.minicould.node.Node;
import com.minicould.tool.load.NodeClassLoader;

public class SimpleDesc extends Desc{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2781707531329332721L;
	
	protected String namespace;
	
	protected String className;
	
	protected Node nodeObj;

	protected NodeClassLoader nodeClassLoader;
	
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Node getNodeObj() {
		return nodeObj;
	}

	public void setNodeObj(Node nodeObj) {
		this.nodeObj = nodeObj;
	}

	public NodeClassLoader getNodeClassLoader() {
		return nodeClassLoader;
	}

	public void setNodeClassLoader(NodeClassLoader nodeClassLoader) {
		this.nodeClassLoader = nodeClassLoader;
	}
	
	
}
