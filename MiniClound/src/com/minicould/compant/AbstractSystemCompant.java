package com.minicould.compant;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.minicould.common.data.CmdInfo;
import com.minicould.common.data.CompantData;
import com.minicould.common.desc.CompantDesc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.result.CompantResult;
import com.minicould.common.util.CloundUtil;
import com.minicould.comon.parser.CmdParser;
import com.minicould.exception.CloundException;
import com.minicould.node.SystemCompant;

public abstract class AbstractSystemCompant extends AbstractCompant implements SystemCompant{
	
	protected CmdParser parser;
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	public CompantResult execute(CmdInfo cmdInfo) throws CloundException{
		CompantData cmdData = parser.parser(cmdInfo.getArg());
		//cmdData.setValue("cn", cmdInfo.getCname());
		//cmdData.setValue("cv", cmdInfo.getCversion());
		return execute(cmdData);
	}
	
	public void init(InputStream stream) throws CloundException {
		super.init(stream);
		CompantDesc compantDesc = (CompantDesc)desc;
		
		parser = (CmdParser) CloundUtil.getInstance(
				compantDesc.getParserClass());
		compantDesc.setParserClass(null);
	}
	
	public CompantResult execute(SimpleDesc desc) throws CloundException{
		return null;
	}
}
