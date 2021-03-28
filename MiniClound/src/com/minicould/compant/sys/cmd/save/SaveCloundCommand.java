package com.minicould.compant.sys.cmd.save;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.data.CompantData;
import com.minicould.common.desc.CloundDesc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.result.CompantResult;
import com.minicould.common.util.CloundUtil;
import com.minicould.compant.AbstractSystemCompant;
import com.minicould.compant.sys.cmd.start.StartCommand;
import com.minicould.exception.CloundException;
import com.minicould.node.Node;

public class SaveCloundCommand extends AbstractSystemCompant{

	private Log log = LogFactory.getLog(SaveCloundCommand.class);
	
	public CompantResult execute(CompantData param) throws CloundException {
		String name = (String) param.getValue("name");
		String version = (String) param.getValue("version");
		
		Node node = CloundUtil.getNode(name, version, CloundConstanst.TYPE_CLOUND);
		
		return execute(node);
	}
	
	public CompantResult execute(SimpleDesc desc) throws CloundException {
		return execute(desc.getNodeObj());
	}

	private CompantResult execute(Node node) throws CloundException{
		CompantResult compantResult = new CompantResult();
		
		CloundDesc cDesc = (CloundDesc)node.getDesc();
		
		WriteConfig writeConfig = new WriteConfig(cDesc);
		try {
			
			writeConfig.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		compantResult.setSuccess(true);
		compantResult.setValue(writeConfig.getFilePath());
		return compantResult;
	}

	public void remove() throws CloundException {
		// TODO Auto-generated method stub
		
	}
}
