package com.minicould.common.desc;

import java.util.List;

public class MoudleDesc extends SimpleDesc{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6548042687214328081L;

	public final static int TYPS_INITVALUE = 0;
	
	public final static int TYPS_LIBPATH = 1;
	
	public final static int TYPS_SERVICE = 2;
	
	private ConfigDesc[] descArray = new ConfigDesc[]{new SystemInitValueDesc(),new MoudlelibPathDesc(),new ServiceList()};

	private String initClass;
	
	
	private class SystemInitValueDesc extends ConfigDesc{
		/**
		 * 
		 */
		private static final long serialVersionUID = -654658758717139318L;
		
	}
	
	private class MoudlelibPathDesc extends ConfigDesc{

		/**
		 * 
		 */
		private static final long serialVersionUID = 2478043233828304859L;
		
		
	}
	
	private class ServiceList extends ConfigDesc{

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
		descArray[type].addDesc(desc.getName()+desc.getVersion(),desc);
		if(type == TYPS_SERVICE)
			this.childDescList.add(desc);
	}
	
	
	public void add(int type,List<? extends Desc> desclList){
		for(int i = 0;i < desclList.size();i++ ){
			Desc desc = desclList.get(i);
			add(type,desc);
		}
		
	}
	public Desc get(int type,String name,String version){
		return descArray[type].getDesc(name+version);
	}
}
