package com.minicould.compant.sys.cmd.init;

import java.io.InputStream;

import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.data.CompantData;
import com.minicould.common.desc.CompantDesc;
import com.minicould.common.desc.Desc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.result.CompantResult;
import com.minicould.common.util.CloundUtil;
import com.minicould.comon.parser.CmdParser;
import com.minicould.compant.AbstractSystemCompant;
import com.minicould.exception.CloundException;
import com.minicould.exception.ConfigException;

public class InitCommand extends AbstractSystemCompant{

	protected static Class<?> nodeParser[] = new Class[5];
	
	static{
		nodeParser[CloundConstanst.TYPE_CLOUND] = CloundParser.class;
		nodeParser[CloundConstanst.TYPE_MOUDLE] = MoudleParser.class;
		nodeParser[CloundConstanst.TYPE_COMPANT] = CompantParser.class;
		nodeParser[CloundConstanst.TYPE_SERVICE] = ServiceParser.class;
		nodeParser[CloundConstanst.TYPE_SYSCMD] = CompantParser.class;
	}
	
	@SuppressWarnings("unchecked")
	public CompantResult execute(CompantData param)  throws CloundException{
		
		Object typeObj = param.getValue("type");
		int type;
		if(typeObj instanceof String)
			type = Integer.parseInt((String)typeObj);
		else
			type = (Integer) typeObj;
		NodeParser<Desc> parser = null;
		try {
			parser = (NodeParser<Desc>) nodeParser[type].newInstance();
		} catch (Exception e) {
			throw new CloundException(e);
		} 
		
		parser.setName((String) param.getValue("name"));
		parser.setVersion((String) param.getValue("version"));
		
		String className = (String) param.getValue("class");
		String namespace = (String) param.getValue("namespace");
		if(className == null){
			SimpleDesc desc = CloundUtil.getSimpleDesc(parser.getName(), parser.getVersion(), type);
			className = desc.getClassName();
			
			if(namespace == null){
				namespace = desc.getNamespace();
			}
		}
		
		if("init".equals(parser.getName())){
			parser.setFileStream(CloundUtil.getInputStream(className,type));
		}else{
			InputStream stream = (InputStream) param.getValue("configStream");
			if(stream == null)
				parser.setFileStream(CloundUtil.getInputStream(parser.getName(),parser.getVersion(), type));
			else
				parser.setFileStream(stream);
		}

		//parser.setFilePath(CloundUtil.getFile(className,type));
		parser.setClassName(className);
		
		parser.setNameSpace(namespace);
		
		parser.setInitCompant(this);
		
		Desc desc =  null;
			
		try {
			desc = parser.parse();
		} catch (ConfigException e) {
			throw new CloundException(e);
		}

		CompantResult compantResult = new CompantResult();
		compantResult.setValue(desc);
		compantResult.setSuccess(true);
		
		return compantResult;
	}
	
	public void init() throws CloundException{
		CompantData compantData = new CompantData();
		compantData.setValue("type", String.valueOf(CloundConstanst.TYPE_SYSCMD));
		compantData.setValue("version", "1.0");
		compantData.setValue("name", "init");
		compantData.setValue("namespace", "init");
		compantData.setValue("class", this.getClass().getName());
		
		CompantResult result = this.execute(compantData);
		
		if(result.isSuccess()){
			desc = (Desc) result.getValue();
		}
		CompantDesc compantDesc = (CompantDesc)desc;
		parser = (CmdParser) CloundUtil.getInstance(compantDesc.getParserClass());
		compantDesc.setParserClass(null);
	}

	public void remove() throws CloundException {
		// TODO Auto-generated method stub
		
	}
}
