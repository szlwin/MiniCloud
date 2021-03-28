package com.minicould.compant.sys.cmd.load;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.minicould.clound.manager.CloundContext;
import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.data.CloundData;
import com.minicould.common.data.CompantData;
import com.minicould.common.data.ServiceData;
import com.minicould.common.desc.CloundDesc;
import com.minicould.common.desc.MoudleDesc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.result.CompantResult;
import com.minicould.common.result.Result;
import com.minicould.common.util.CloundUtil;
import com.minicould.compant.AbstractSystemCompant;
import com.minicould.exception.CloundException;
import com.minicould.node.Clound;
import com.minicould.node.ENode;
import com.minicould.node.Moudle;
import com.minicould.node.Node;
import com.minicould.tool.load.NodeClassLoader;

public class LoadCommand extends AbstractSystemCompant{

	private static Map<String,SimpleDesc> descMap = new HashMap<String,SimpleDesc>();
	
	public CompantResult execute(CompantData param) throws CloundException {
		String name = (String) param.getValue("name");
		String version = (String) param.getValue("version");
		
		String mn = (String) param.getValue("mn");
		String mv = (String) param.getValue("mv");
		
		if(mn == null || mv == null){
			mn = "";
			mv = "";
		}
		int type = Integer.parseInt((String) param.getValue("type"));
		
		if(type == CloundConstanst.TYPE_SYSCMD){
			CompantResult compantResult = new CompantResult();
			compantResult.setSuccess(false);
			compantResult.setValue("The system command can't be execute by this command!");
		}
		
		if(this.name.equals("test")){
			String paramStr = (String) param.getValue("param");
			return test(name,version,mn,mv,paramStr);
		}
		
		if(this.name.equals("change")){
			return change(name,version,mn,mv);
		}
		
		if(this.name.equals("load")){
			
			String jar = (String) param.getValue("jar");
			return load(name,version,mn,mv,type,jar);
		}
		
		return null;
	}
	
	private CompantResult load(String name,String version,String mn,String mv,int type,String jar) throws CloundException{
		InputStream inputStream = null;
		String libPath = null;
		if(type == CloundConstanst.TYPE_SERVICE){
			String serName = mn+mv+"/"+name+version;
			inputStream = CloundUtil.getInputStream(serName, null, jar,type);
			libPath = CloundUtil.getLibPath(serName,null,type);
		}else{
			inputStream = CloundUtil.getInputStream(name, version, jar,type);
			libPath = CloundUtil.getLibPath(name,version,type);
		}
		
		String className = parseClassName(jar,version);
		SimpleDesc simpleDesc = new SimpleDesc();
		simpleDesc.setName(name);
		simpleDesc.setVersion(version);
		simpleDesc.setNamespace(name);
		simpleDesc.setClassName(className);
		
		NodeClassLoader nodeClassLoader = new NodeClassLoader();
		nodeClassLoader.setName(className);
		nodeClassLoader.setVersion(version);
		nodeClassLoader.setPath(libPath);
		
		Node node;
		try {
			node = (Node) nodeClassLoader.loadClass(className).newInstance();
		} catch (Exception e) {
			throw new CloundException(e);
		}
		node.setInitCompant(CloundUtil.getCmd("init"));
		node.setName(name);
		node.setVersion(version);
		node.setNameSpace(name);
		node.setType(type);
		node.setStatus(Node.STATUS_INIT);
		simpleDesc.setNodeObj(node);
		simpleDesc.setNodeClassLoader(nodeClassLoader);
		node.init(inputStream);
		//CompantData compantData = new CompantData();
		//compantData.setValue("name", name);
		//compantData.setValue("version", version);
		//compantData.setValue("namespace", name);
		//compantData.setValue("class", parseClassName(jar,version));
		//compantData.setValue("configStream", inputStream);
		//compantData.setValue("type", type);
		
		//CompantResult compantResult = initCmd.execute(compantData);
		
		CloundContext cloundContext = CloundUtil.getCurrentContext();
		String key = cloundContext.getCname()+cloundContext.getCversion()+"/"+mn+mv+"/"+name+version;
		
		descMap.put(key, simpleDesc);
		
		CompantResult compantResult = new CompantResult();
		compantResult.setSuccess(true);
		return compantResult;
	}
	
	private CompantResult test(String name,String version,String mn,String mv,String paramStr) throws CloundException{
		CloundContext cloundContext = CloundUtil.getCurrentContext();
		String key = cloundContext.getCname()+cloundContext.getCversion()+"/"+mn+mv+"/"+name+version;
		SimpleDesc sDesc = descMap.get(key);
		
		Node node = sDesc.getNodeObj();
		
		CompantResult compantResult = new CompantResult();
		
		if(!(node instanceof ENode)){
			compantResult.setSuccess(false);
			compantResult.setErrorMsg("The node can't be test");
			return compantResult;
		}
		
		CloundData cloundData = null;
		
		if(paramStr!=null && !"".equals(paramStr)){
			if(node.getType() == CloundConstanst.TYPE_COMPANT)
				cloundData = new CompantData();
			else
				cloundData = new ServiceData();
			
			CloundUtil.parseCloundData(paramStr, cloundData);
		}
			
		
		Result result = (Result) ((ENode)node).execute(cloundData);
		
		compantResult.setErrorMsg(result.getErrorMsg());
		compantResult.setValue(result.getValue());
		compantResult.setSuccess(result.isSuccess());
		return compantResult;
	}
	
	private CompantResult change(String name,String version,String mn,String mv) throws CloundException{
		CloundContext cloundContext = CloundUtil.getCurrentContext();
		String key = cloundContext.getCname()+cloundContext.getCversion()+"/"+mn+mv+"/"+name+version;
		SimpleDesc sDesc = descMap.get(key);
		
		sDesc.getNodeObj().setStatus(Node.STATUS_START);
		
		if(sDesc.getNodeObj().getType() == CloundConstanst.TYPE_SERVICE){
			Moudle moudle = (Moudle) CloundUtil.getNode(mn, mv, CloundConstanst.TYPE_MOUDLE);
			moudle.load(MoudleDesc.TYPS_SERVICE, sDesc);
		}else{
			Clound clound = (Clound) CloundUtil.getNode(cloundContext.getCname(), cloundContext.getCversion(), CloundConstanst.TYPE_CLOUND);
			clound.load(parseType(sDesc.getNodeObj().getType()), sDesc);
		}
		
		CompantResult compantResult = new CompantResult();
		compantResult.setSuccess(true);
		descMap.remove(key);
		return compantResult;
	}
	
	private String parseClassName(String jar,String version){
		return jar.substring(0,jar.length()-4-version.length()-1);
	}

	private int parseType(int type){
		switch(type){
			case CloundConstanst.TYPE_COMPANT:
				return CloundDesc.TYPS_COMPANT;
			case CloundConstanst.TYPE_MOUDLE:
				return CloundDesc.TYPS_MOUDLE;				
		}
		
		return -1;
	}

	public void remove() throws CloundException {
		// TODO Auto-generated method stub
		
	}
}
