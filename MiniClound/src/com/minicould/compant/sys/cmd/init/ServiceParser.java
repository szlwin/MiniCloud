package com.minicould.compant.sys.cmd.init;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.minicould.common.desc.CompantDesc;
import com.minicould.common.desc.InitValueDesc;
import com.minicould.common.desc.ParamValueDesc;
import com.minicould.common.desc.ServiceDesc;
import com.minicould.exception.ConfigException;

public class ServiceParser extends NodeParser<ServiceDesc>{

	private final static String VERSION_CONFIG = "version-config";
	
	private final static String VERSION_CONFIG_PROPERTY_VERSION = "version";
	
	Log log = LogFactory.getLog(ServiceParser.class);
	
	@Override
	public ServiceDesc parse() throws ConfigException {
		//log.info("The service:"+this.name+",version:"+this.version+" parse start!");
		ServiceDesc serviceDesc = new ServiceDesc();
		DocumentBuilder builder;
		Document doc;
		//serviceDesc.setName(this.name);
		serviceDesc.setVersion(this.version);
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(fileStream);
			
			Element element = (Element) doc.getElementsByTagName("service").item(0);
			String name = element.getAttribute("name");
			
			if(this.name == null){
				setName(name);
			}else{
				if(!name.equals(getName()) ){
					log.error("The service name is not match!");
					throw new ConfigException("The service name is not match!");
				}
			}
			serviceDesc.setName(name);
			
			NodeList nodeList = doc.getElementsByTagName(VERSION_CONFIG);
			
			Element versionElement = null;
			String version = null;
			for(int i = 0; i < nodeList.getLength();i++){
				Element tempElement  = (Element) nodeList.item(0);
				String tempVersion = tempElement.getAttribute(VERSION_CONFIG_PROPERTY_VERSION);
				
				if(tempVersion.equals(getVersion())){
					versionElement = tempElement;
					version = tempVersion;
				}
			}
			
			if(version == null ){
				log.error("The service version is not match!");
				throw new ConfigException("The service version is not match!");
			}
			
			NodeList initValueList  = versionElement.getElementsByTagName("init-values");
			if(initValueList.getLength() > 0){
				List<InitValueDesc> desclList = parseInitValue(version,(Element) initValueList.item(0));
				serviceDesc.add(CompantDesc.TYPS_INITVALUE, desclList);
			}
			
			
			NodeList paramValueList  = versionElement.getElementsByTagName("params-mapping");
			if(paramValueList.getLength() > 0){
				List<ParamValueDesc> desclList = parseParamValue(version,(Element) paramValueList.item(0));
				serviceDesc.add(CompantDesc.TYPS_PARAMVALUE, desclList);
			}
		}catch (Exception e) {
			log.error("The service:"+name+",version:"+version+" parse error!",e);
			throw new ConfigException("The service:"+name+",version:"+version+" parse error!",e);
		}
		
		log.info("The service:"+name+",version:"+version+" parse success!");
		return serviceDesc;
	}
	
	private List<ParamValueDesc> parseParamValue(String version,Element element){
		NodeList nodeList = element.getElementsByTagName("param");
		List<ParamValueDesc> list = new ArrayList<ParamValueDesc>(nodeList.getLength());
		for(int i = 0 ; i < nodeList.getLength();i++){
			Element initElement = (Element) nodeList.item(i);
			ParamValueDesc paramValueDesc = new ParamValueDesc();
			paramValueDesc.setName(initElement.getAttribute("key"));
			paramValueDesc.setVersion(version);
			list.add(paramValueDesc);
		}
		return list;
	}

}
