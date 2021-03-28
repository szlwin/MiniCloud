package com.minicould.clound.manager;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.minicould.common.desc.CloundConfigDesc;
import com.minicould.common.util.CloundUtil;
import com.minicould.exception.CloundException;
import com.minicould.exception.ConfigException;
import com.minicould.node.Node;
import com.minicould.node.SystemCompant;
import com.minicould.tool.load.NodeClassLoader;

public class ConfigParser {

	//private Log log = LogFactory.getLog(ConfigParser.class);
	
	public Map<String,CloundConfigDesc> parse(String filePath) throws ConfigException{
		
		Map<String,CloundConfigDesc> descMap = new HashMap<String,CloundConfigDesc>();
		DocumentBuilder builder;
		Document doc;
		
			try {
				builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				doc = builder.parse(filePath);
				NodeList cloundInfo  = doc.getElementsByTagName("clound-info");
				
				for(int i = 0; i < cloundInfo.getLength();i++){
					CloundConfigDesc desc = parseSimpleDesc((Element)cloundInfo.item(i));
					descMap.put(desc.getName()+desc.getVersion(), desc);
				}
			} catch (Exception e) {
				throw new ConfigException(e);
			}
			
	
		return descMap;
		
	}
		
		
	private CloundConfigDesc parseSimpleDesc(Element nodeElement) throws InstantiationException, IllegalAccessException, ClassNotFoundException, CloundException{
		
		CloundConfigDesc simpleDesc = new CloundConfigDesc();
		
		simpleDesc.setName(nodeElement.getElementsByTagName("name").item(0).getTextContent());
		simpleDesc.setNamespace(nodeElement.getElementsByTagName("namespace").item(0).getTextContent());
		simpleDesc.setVersion(nodeElement.getElementsByTagName("version").item(0).getTextContent());
		simpleDesc.setClassName(nodeElement.getElementsByTagName("class").item(0).getTextContent());
		simpleDesc.setPath(nodeElement.getElementsByTagName("path").item(0).getTextContent());
		
		NodeClassLoader nodeClassLoader = new NodeClassLoader();
		nodeClassLoader.setName(simpleDesc.getClassName());
		nodeClassLoader.setVersion(simpleDesc.getVersion());
		nodeClassLoader.setPath(simpleDesc.getPath());
		
		simpleDesc.setNodeClassLoader(nodeClassLoader);
		Node node = (Node) nodeClassLoader.loadClass(simpleDesc.getClassName()).newInstance();
		
		String initClass = nodeElement.getElementsByTagName("init-compant").item(0).getTextContent();
		SystemCompant initCompant = (SystemCompant) Class.forName(initClass).newInstance();
		initCompant.init();
		
		node.setInitCompant(initCompant);
		node.setName(simpleDesc.getName());
		node.setNameSpace(simpleDesc.getNamespace());
		node.setVersion(simpleDesc.getVersion());
		node.setStatus(Node.STATUS_INIT);
		simpleDesc.setNodeObj(node);
		simpleDesc.setNodeClassLoader(nodeClassLoader);
		simpleDesc.setInitCompant(initCompant);
		
		node.init(CloundUtil.getInputStream(simpleDesc));
		return simpleDesc;
	}
}
