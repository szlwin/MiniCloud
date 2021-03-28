package com.minicould.common.desc;

import java.util.List;


public class CompantDesc extends Desc{

	private ConfigDesc[] descArray = new ConfigDesc[]{new CompantInitValueDesc(),new CompantParamValueDesc()};
	
	public final static int TYPS_INITVALUE = 0;
	
	public final static int TYPS_PARAMVALUE = 1;
	
	private String parserClass;
	
	private class CompantInitValueDesc extends ConfigDesc{

		/**
		 * 
		 */
		private static final long serialVersionUID = 8425642767583025087L;
		
	}
	
	private class CompantParamValueDesc extends ConfigDesc{

		/**
		 * 
		 */
		private static final long serialVersionUID = 6497321733694430052L;
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 2668665932771448003L;
	
	
	public void add(int type,Desc desc){
		descArray[type].addDesc(desc.getName(),desc);
	}
	
	
	public void add(int type,List<? extends Desc> desclList){
		for(int i = 0;i < desclList.size();i++ ){
			Desc desc = desclList.get(i);
			add(type,desc);
		}
		
	}
	public Desc get(int type,String name){
		return descArray[type].getDesc(name);
	}


	public String getParserClass() {
		return parserClass;
	}


	public void setParserClass(String parserClass) {
		this.parserClass = parserClass;
	}
	

}
