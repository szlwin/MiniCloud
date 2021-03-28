package com.minicould.clound.manager;

import java.util.Map;

import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.desc.CloundConfigDesc;
import com.minicould.common.desc.CloundDesc;
import com.minicould.common.desc.MoudleDesc;
import com.minicould.exception.ConfigException;
import com.minicould.node.Node;
import com.minicould.common.desc.SimpleDesc;

public class CloundManager {

	private static final ThreadLocal<CloundContext> contextLocal = new ThreadLocal<CloundContext>();
	
	private Map<String,CloundConfigDesc> cloundDescMap;
	
	private static final CloundManager cloundManager = new CloundManager();
	
	private CloundManager(){
		
	}
	
	public static CloundManager getInstance(){
		return cloundManager;
	}
	
	public void config(String filePath) throws ConfigException{
		ConfigParser configParser = new ConfigParser();
		cloundDescMap = configParser.parse(filePath);
	}
	
	public Node getNode(String name,String version,int type,String cname,String cversion){
		Node node = getCloundNode(cname,cversion);
		if(node == null && name.equals("init")){
			return ((CloundConfigDesc)getCloundNodeDesc(cname,cversion)).getInitCompant();
		}
		CloundDesc desc = (CloundDesc) node.getDesc();
		switch(type){
			case CloundConstanst.TYPE_CLOUND:
				return node;
			case CloundConstanst.TYPE_COMPANT:
				return ((SimpleDesc)desc.get(CloundDesc.TYPS_COMPANT, name, version)).getNodeObj();
			case CloundConstanst.TYPE_SYSCMD:
				if(desc == null){
					return cloundDescMap.get(cname+cversion).getInitCompant();
				}
				return ((SimpleDesc)desc.get(CloundDesc.TYPS_SYSTEMCMD, name, version)).getNodeObj();
			case CloundConstanst.TYPE_MOUDLE:
				return ((SimpleDesc)desc.get(CloundDesc.TYPS_MOUDLE, name, version)).getNodeObj();
			case CloundConstanst.TYPE_SERVICE:
				String[] nameArray =  name.split("/");
				MoudleDesc moudleDesc = (MoudleDesc) desc.get(CloundDesc.TYPS_MOUDLE, nameArray[0], null);
				return ((SimpleDesc)moudleDesc.get(MoudleDesc.TYPS_SERVICE, nameArray[1], version)).getNodeObj();
		}
		return null;
		
	}
	
	public SimpleDesc getDesc(String name,String version,int type,String cname,String cversion){
		Node node = getCloundNode(cname,cversion);
		CloundDesc desc = (CloundDesc) node.getDesc();
		switch(type){
			case CloundConstanst.TYPE_CLOUND:
				return desc;
			case CloundConstanst.TYPE_COMPANT:
				return ((SimpleDesc)desc.get(CloundDesc.TYPS_COMPANT, name, version));
			case CloundConstanst.TYPE_SYSCMD:
				return ((SimpleDesc)desc.get(CloundDesc.TYPS_SYSTEMCMD, name, version));
			case CloundConstanst.TYPE_MOUDLE:
				return ((SimpleDesc)desc.get(CloundDesc.TYPS_MOUDLE, name, version));
			case CloundConstanst.TYPE_SERVICE:
				String[] nameArray =  name.split("/");
				SimpleDesc simpleDesc = (SimpleDesc) desc.get(CloundDesc.TYPS_MOUDLE, nameArray[0], null);
				MoudleDesc moudleDesc = (MoudleDesc) simpleDesc.getNodeObj().getDesc();
				return (SimpleDesc)moudleDesc.get(MoudleDesc.TYPS_SERVICE, nameArray[1], version);
				
		}
		return null;
		
	}
	
	private Node getCloundNode(String name,String version){
		if(name == null || version == null)
			return null;
		return cloundDescMap.get(name+version).getNodeObj();
	}
	
	private SimpleDesc getCloundNodeDesc(String name,String version){
		return cloundDescMap.get(name+version);
	}
	
	public static void main(String[] args) throws ConfigException{
		CloundManager.getInstance().config("");
	}
	
	public void setCurrentContext(CloundContext context){
		//if(contextLocal.get() == null){
			contextLocal.set(context);
		//}
	}
	
	public CloundContext getCurrentContext(){
		return contextLocal.get();
	}
}
