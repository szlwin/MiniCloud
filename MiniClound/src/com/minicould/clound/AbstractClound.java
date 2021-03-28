package com.minicould.clound;

import java.io.InputStream;

import com.minicould.clound.node.AbstractCNode;
import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.data.CmdInfo;
import com.minicould.common.data.CompantData;
import com.minicould.common.desc.CloundDesc;
import com.minicould.common.desc.Desc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.result.CompantResult;
import com.minicould.common.result.Result;
import com.minicould.common.util.CloundUtil;
import com.minicould.exception.CloundException;
import com.minicould.node.Clound;

public abstract class AbstractClound extends AbstractCNode<CmdInfo,Result> implements Clound{
	
	public void execute(CmdInfo cmdInfo) throws CloundException {
		executeV(cmdInfo);
	}

	public Result executeV(CmdInfo cmdInfo) throws CloundException {
		if(!CloundUtil.check(this.status)){
			CompantResult compantResult = new CompantResult();
			compantResult.setSuccess(false);
			compantResult.setErrorMsg("The node "+name+" "+version+" is not start!");
		}
		 
		return CloundUtil.execCmd(cmdInfo);
	}

	
	public void init() throws CloundException {
		init(null);
	}

	
	public void init(InputStream stream) throws CloundException {
		CompantData compantData = new CompantData();
		compantData.setValue("type", CloundConstanst.TYPE_CLOUND);
		compantData.setValue("version", this.version);
		compantData.setValue("name", this.name);
		compantData.setValue("class", this.getClass().getName());
		compantData.setValue("namespace", this.getNameSpace());
		compantData.setValue("configStream", stream);
		
		CompantResult result = this.initCompant.execute(compantData);
		
		if(result.isSuccess()){
			desc = (Desc) result.getValue();
		}
		SimpleDesc sDesc = new SimpleDesc();
		sDesc.setName("init");
		sDesc.setNamespace("init");
		sDesc.setNodeObj(initCompant);
		((CloundDesc)desc).add(CloundDesc.TYPS_SYSTEMCMD, sDesc);
	}
	
	public void remove() throws CloundException {
		//System.out.println("123");
	}
	
	public synchronized void load(int type,Desc desc) throws CloundException{
		((CloundDesc)desc).add(type, desc);
	}
}
