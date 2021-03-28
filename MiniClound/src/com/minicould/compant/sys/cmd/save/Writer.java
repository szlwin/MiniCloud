package com.minicould.compant.sys.cmd.save;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;

import com.minicould.common.desc.SimpleDesc;

public class Writer {
	
	private Map<String,String> map = new HashMap<String,String>();
	
	public void write(SimpleDesc desc,Element element,String nodeName){
		//¹ýÂËinit
		if(desc.getClassName()==null){
			return;
		}
		
		if(!desc.getName().equals(desc.getNamespace())){
			if(!map.containsKey(desc.getNamespace())){
				Element nodeElement = element.addElement(nodeName);
				nodeElement.addElement("name").addText(desc.getNamespace());
				nodeElement.addElement("namespace").addText(desc.getNamespace());
				nodeElement.addElement("version").addText(desc.getVersion());
				nodeElement.addElement("class").addText(desc.getClassName());
				
				map.put(desc.getNamespace(), null);
			}
		}else{
			Element nodeElement = element.addElement(nodeName);
			nodeElement.addElement("name").addText(desc.getName());
			nodeElement.addElement("namespace").addText(desc.getNamespace());
			nodeElement.addElement("version").addText(desc.getVersion());
			nodeElement.addElement("class").addText(desc.getClassName());
		}
	}
}
