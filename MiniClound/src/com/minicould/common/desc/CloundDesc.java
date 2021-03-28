package com.minicould.common.desc;

import java.util.List;

public class CloundDesc extends SimpleDesc{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4670532591918641285L;

	public final static int TYPS_INITVALUE = 0;
	
	public final static int TYPS_LIBPATH = 1;
	
	public final static int TYPS_SYSTEMCMD = 2;
	
	public final static int TYPS_COMPANT = 3;
	
	public final static int TYPS_MOUDLE = 4;
	
	private ConfigDesc[] descArray = new ConfigDesc[]{new SystemInitValueDesc(),new CloundlibPathDesc(),
			new SystemCmdList(),new CompantList(),new MoudleList()};

	private String initClass;
	
	
	private class SystemInitValueDesc extends ConfigDesc{
		/**
		 * 
		 */
		private static final long serialVersionUID = -654658758717139318L;
		
	}
	
	private class CloundlibPathDesc extends ConfigDesc{

		/**
		 * 
		 */
		private static final long serialVersionUID = 2478043233828304859L;
		
		
	}
	
	private class SystemCmdList extends ConfigDesc{

		/**
		 * 
		 */
		private static final long serialVersionUID = 6765193302541173666L;

	}
	
	private class CompantList extends ConfigDesc{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3661134225219894142L;
		
	}
	
	private class MoudleList extends ConfigDesc{

		/**
		 * 
		 */
		private static final long serialVersionUID = -5023847892247805602L;
		
	}

	public String getInitClass() {
		return initClass;
	}

	public void setInitClass(String initClass) {
		this.initClass = initClass;
	}
	

	public void add(int type,Desc desc){
		if(type == TYPS_SYSTEMCMD || type == TYPS_LIBPATH || type==TYPS_INITVALUE){
			descArray[type].addDesc(desc.getName(),desc);
		}else{
			descArray[type].addDesc(desc.getName()+desc.getVersion(),desc);
		}
		
		if(type == TYPS_COMPANT 
				|| type ==  TYPS_MOUDLE){
			this.childDescList.add(desc);
		}
	}
	
	
	public void add(int type,List<? extends Desc> desclList){
		for(int i = 0;i < desclList.size();i++ ){
			Desc desc = desclList.get(i);
			add(type,desc);
		}
		
	}
	public Desc get(int type,String name,String version){
		if(type == TYPS_SYSTEMCMD || type == TYPS_LIBPATH || type==TYPS_INITVALUE || version == null){
			return descArray[type].getDesc(name);
		}else{
			return descArray[type].getDesc(name+version);
		}
	}
	
	public ConfigDesc get(int type){
		return descArray[type];
	}
}
