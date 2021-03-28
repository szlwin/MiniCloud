package com.minicould.compant.sys.cmd.service;

import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.data.CmdInfo;
import com.minicould.common.data.CompantData;
import com.minicould.common.data.MoudleData;
import com.minicould.common.data.ServiceData;
import com.minicould.common.result.CompantResult;
import com.minicould.common.result.Result;
import com.minicould.common.util.CloundUtil;
import com.minicould.compant.AbstractSystemCompant;
import com.minicould.exception.CloundException;
import com.minicould.node.Moudle;

public class ServiceCommand extends AbstractSystemCompant{

	public CompantResult execute(CompantData param) throws CloundException {
		String name = (String) param.getValue("name");
		String version = (String) param.getValue("version");
		String mName = (String) param.getValue("mn");
		String mVersion = (String) param.getValue("mv");
		String paramStr = (String) param.getValue("param");
		
		Moudle moudle = (Moudle) CloundUtil.getNode(mName, mVersion, CloundConstanst.TYPE_MOUDLE);
		//Service service = (Service) CloundUtil.getServiceNode(name, version, mName, mVersion);
		ServiceData serviceData = null;
		if(paramStr!=null){
			serviceData = CloundUtil.parse(paramStr);
		}
		
		MoudleData moudleDta = new MoudleData();
		moudleDta.setName(name);
		moudleDta.setVersion(version);
		moudleDta.setCmd(this.name);
		moudleDta.setData(serviceData);
		
		Result result = moudle.executeV(moudleDta);
		
		CompantResult compantResult = new CompantResult();
		
		copy(result,compantResult);
		return compantResult;
	}
	
	public CompantResult execute(CmdInfo cmdInfo) throws CloundException{
		CompantData cmdData = parser.parser(cmdInfo.getArg());
		//cmdData.setValue("cn", cmdInfo.getCname());
		//cmdData.setValue("cv", cmdInfo.getCversion());
		return execute(cmdData);
	}
	
	private void copy(Result result,CompantResult compantResult){
		compantResult.setSuccess(result.isSuccess());
		compantResult.setErrorMsg(result.getErrorMsg());
		compantResult.setValue(result.getValue());
	}

	public void remove() throws CloundException {
		// TODO Auto-generated method stub
		
	}

}
