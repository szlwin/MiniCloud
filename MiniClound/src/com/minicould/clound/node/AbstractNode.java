package com.minicould.clound.node;

import java.io.InputStream;

import com.minicould.common.data.CompantData;
import com.minicould.common.desc.Desc;
import com.minicould.common.result.CompantResult;
import com.minicould.exception.CloundException;
import com.minicould.node.Node;
import com.minicould.node.SystemCompant;

public abstract class AbstractNode implements Node{

	protected int status;
	
	protected String version;

	protected String name;
	
	protected String nameSpace;
	
	protected SystemCompant initCompant;
	
	protected int type;
	
	protected Desc desc;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setInitCompant(SystemCompant compant){
		this.initCompant = compant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Desc getDesc(){
		return desc;
	}
	
	public void init() throws CloundException {
		init(null);
	}
	
	public void init(InputStream stream) throws CloundException {
		CompantData compantData = new CompantData();
		compantData.setValue("type", this.type);
		compantData.setValue("version", this.version);
		compantData.setValue("name", this.name);
		compantData.setValue("namespace", this.nameSpace);
		compantData.setValue("class", this.getClass().getName());
		compantData.setValue("configStream", stream);
		CompantResult result = this.initCompant.execute(compantData);
		
		if(result.isSuccess()){
			desc = (Desc) result.getValue();
		}

	}
}
