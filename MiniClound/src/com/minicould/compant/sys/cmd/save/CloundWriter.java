package com.minicould.compant.sys.cmd.save;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;

import com.minicould.common.desc.CloundDesc;
import com.minicould.common.desc.ConfigDesc;
import com.minicould.common.desc.Desc;
import com.minicould.common.desc.InitValueDesc;
import com.minicould.common.desc.LibPathDesc;
import com.minicould.common.desc.SimpleDesc;

public class CloundWriter {

	private CloundDesc desc;
	private Document doc;
	
	public CloundWriter(CloundDesc desc,Document doc){
		this.desc = desc;
		this.doc = doc;
	}
	
	public void write(){
		Element cElement = doc.addElement("clound-config")
				.addElement("clound").addAttribute("name", desc.getName())
					.addAttribute("namespace", desc.getNamespace())
					.addElement("version-config").addAttribute("version", desc.getVersion());
		
		writeInitValue(cElement);
		
		writeLibPath(cElement);
		
		Element systemCmdElement = cElement.addElement("system-cmd-list");
		writeSimple(systemCmdElement,CloundDesc.TYPS_SYSTEMCMD,"cmd-info");
		
		if(!isEmpty(CloundDesc.TYPS_COMPANT)){
			Element compantElement = cElement.addElement("compant-list");
			writeSimple(compantElement,CloundDesc.TYPS_COMPANT,"compant-info");
		}

		if(!isEmpty(CloundDesc.TYPS_MOUDLE)){
			Element moudleElement = cElement.addElement("moudle-list");
			writeSimple(moudleElement,CloundDesc.TYPS_MOUDLE,"moudle-info");
		}
	}
	
	
	private void writeInitValue(Element element){
		ConfigDesc cDesc = desc.get(CloundDesc.TYPS_INITVALUE);
		Map<String,Desc> map = cDesc.getAll();
		if(!map.isEmpty()){
			Element initElement = element.addElement("system-init-values");
			
			writeInitMap(initElement,map);
		}
	}
	
	
	private void writeInitMap(Element element,Map<String,Desc> map){
		Set<String> keySet = map.keySet();
		Iterator<String> it = keySet.iterator();
		
		while(it.hasNext()){
			String key =  it.next();
			InitValueDesc initValueDesc = (InitValueDesc) map.get(key);
			element.addElement("init-value").addAttribute("key", key)
			.addAttribute("value", initValueDesc.getVakue());
		}
	}
	
	private void writeLibPath(Element element){
		ConfigDesc cDesc = desc.get(CloundDesc.TYPS_LIBPATH);
		Map<String,Desc> map = cDesc.getAll();
		if(!map.isEmpty()){
			Element libElement = element.addElement("clound-lib-path");
			
			libElement.addElement("system-lib-path").addText(
					((LibPathDesc)map.get(LibPathDesc.SYSTEM_LIB))
					.getLibPath());
			
			libElement.addElement("compant-lib-path").addText(
					((LibPathDesc)map.get(LibPathDesc.COMPANT_LIB))
					.getLibPath());
			
			libElement.addElement("moudle-lib-path").addText(
					((LibPathDesc)map.get(LibPathDesc.MOUDLE_LIB))
					.getLibPath());
		}
	}
	
	private void writeSimple(Element element,int type,String nodeName){
		
		ConfigDesc cDesc = desc.get(type);
		Map<String,Desc> map = cDesc.getAll();
		
		Writer writer = new Writer();
		Set<String> keySet = map.keySet();
		Iterator<String> it = keySet.iterator();
		
		while(it.hasNext()){
			String key =  it.next();
			SimpleDesc simpleDesc = (SimpleDesc) map.get(key);
			writer.write(simpleDesc, element, nodeName);
		}
	}
	
	private boolean isEmpty(int type){
		ConfigDesc cDesc = desc.get(type);
		Map<String,Desc> map = cDesc.getAll();
		
		return map.isEmpty();
	}
}
