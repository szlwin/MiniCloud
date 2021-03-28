package com.minicould.compant.sys.cmd.init;

import java.net.URL;
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
import com.minicould.exception.ConfigException;

public class CompantParser extends NodeParser<CompantDesc>{

	private final static String VERSION_CONFIG = "version-config";
	
	private final static String VERSION_CONFIG_PROPERTY_VERSION = "version";
	
	Log log = LogFactory.getLog(CloundParser.class);
	
	@Override
	public CompantDesc parse() throws ConfigException {
		log.info("The compant:"+this.name+",version:"+this.version+" parse start!");
		CompantDesc compantDesc = new CompantDesc();
		DocumentBuilder builder;
		Document doc;
		//compantDesc.setName(this.name);
		compantDesc.setVersion(this.version);
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(fileStream);
			
			Element element = (Element) doc.getElementsByTagName("compant").item(0);
			String name = element.getAttribute("name");
			
			if(this.name == null){
				setName(name);
			}
			/*else{
				if(!name.equals(getName()) ){
					log.error("The compant name is not match!");
					throw new ConfigException("The compant name is not match!");
				}
			}*/
			compantDesc.setName(getName());
			
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
				log.error("The compant version is not match!");
				throw new ConfigException("The compant version is not match!");
			}
			
			NodeList initValueList  = versionElement.getElementsByTagName("init-values");
			if(initValueList.getLength() > 0){
				List<InitValueDesc> desclList = parseInitValue(version,(Element) initValueList.item(0));
				compantDesc.add(CompantDesc.TYPS_INITVALUE, desclList);
			}
			
			String parserClassName = parseCmdParserClassName(versionElement);
			if(parserClassName != null){
				compantDesc.setParserClass(parserClassName);
			}
			
			NodeList paramValueList  = versionElement.getElementsByTagName("params-mapping");
			if(paramValueList.getLength() > 0){
				List<ParamValueDesc> desclList = parseParamValue(version,(Element) paramValueList.item(0));
				compantDesc.add(CompantDesc.TYPS_PARAMVALUE, desclList);
			}
		}catch (Exception e) {
			log.error("The compant:"+name+",version:"+version+" parse error!",e);
			throw new ConfigException("The compant:"+name+",version:"+version+" parse error!",e);
		}
		
		log.info("The compant:"+name+",version:"+version+" parse success!");
		return compantDesc;
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
	
	private String parseCmdParserClassName(Element element){
		NodeList nodeList = element.getElementsByTagName("cmd-parser");
		if(nodeList.getLength() > 0){
			return nodeList.item(0).getTextContent();
		}
		return null;
	}
	public  static void main(String args[]) throws ConfigException, ClassNotFoundException{
		CompantParser compantParser = new CompantParser();
		
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    //System.out.print(classLoader.l);
	    URL url = classLoader.loadClass("com.minicould.compant.sys.cmd.start.StartCommand").getResource("compant.xml");
	    compantParser.setFilePath(url.getPath());
	    compantParser.setName("start");
	    compantParser.setNameSpace("start");
	    compantParser.setVersion("1.0");
	    compantParser.parse();
		
	}
	
}
