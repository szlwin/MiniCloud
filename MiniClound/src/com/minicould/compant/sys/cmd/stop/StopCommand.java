package com.minicould.compant.sys.cmd.stop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.data.CompantData;
import com.minicould.common.desc.MoudleDesc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.result.CompantResult;
import com.minicould.common.util.CloundUtil;
import com.minicould.compant.AbstractSystemCompant;
import com.minicould.exception.CloundException;
import com.minicould.node.Moudle;
import com.minicould.node.Node;

public class StopCommand extends AbstractSystemCompant{

	private Log log = LogFactory.getLog(StopCommand.class);
	
	public CompantResult execute(CompantData param) throws CloundException {
		String name = (String) param.getValue("name");
		String version = (String) param.getValue("version");
		int type = Integer.parseInt((String) param.getValue("type"));
		
		String mn = (String) param.getValue("mn");
		String mv = (String) param.getValue("mv");
		
		if(mn == null || mv == null){
			mn = "";
			mv = "";
		}
		
		Node node = null;
		if(type == CloundConstanst.TYPE_SERVICE){
			Moudle moudle = (Moudle) CloundUtil.getNode(mn, mv, CloundConstanst.TYPE_MOUDLE);
			node = ((SimpleDesc)((MoudleDesc)moudle.getDesc()).get(MoudleDesc.TYPS_SERVICE, name, version)).getNodeObj();
		}else{
			node = CloundUtil.getNode(name, version, type);
		}
		
		return execute(node);
	}
	
	public CompantResult execute(SimpleDesc desc) throws CloundException {
		return execute(desc.getNodeObj());
	}

	private CompantResult execute(Node node) throws CloundException{
		CompantResult compantResult = new CompantResult();
		
		if(node.getStatus() == Node.STATUS_START){
			node.setStatus(Node.STATUS_STOP);
			log.debug("stop " + node.getName()+" "+ node.getVersion()+" success");
			node.doNext(this.name);
		}else{
			log.debug("stop " + node.getName()+" "+ node.getVersion()+" error,the status is not start!");
		}

		compantResult.setSuccess(true);
		compantResult.setValue("success");
		return compantResult;
	}

	public void remove() throws CloundException {
		// TODO Auto-generated method stub
		
	}
}
