package com.minicould.compant.sys.cmd.init;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.desc.CloundDesc;
import com.minicould.common.desc.InitValueDesc;
import com.minicould.common.desc.LibPathDesc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.exception.ConfigException;

public class CloundParser extends NodeParser<CloundDesc>{
	
	private final static String CLOUND = "clound";
	
	private final static String CLOUND_PROPERTY_NAME = "name";
	
	private final static String CLOUND_PROPERTY_NAMESPACE = "namespace";
	
	private final static String VERSION_CONFIG = "version-config";
	
	private final static String VERSION_CONFIG_PROPERTY_VERSION = "version";
	
	private static Map<String,String> libMap = new HashMap<String,String>();
	
	static{
		libMap.put("system-lib-path", LibPathDesc.SYSTEM_LIB);
		libMap.put("compant-lib-path", LibPathDesc.COMPANT_LIB);
		libMap.put("moudle-lib-path", LibPathDesc.MOUDLE_LIB);
	}
	
	private Log log = LogFactory.getLog(CloundParser.class);
	
	public CloundDesc parse() throws ConfigException{
		
		log.info("The clound:"+this.name+",version:"+this.version+" parse start!");
		CloundDesc cloundDesc = new CloundDesc();
		DocumentBuilder builder;
		Document doc;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(fileStream);
			
			Element cloundElement = (Element) doc.getElementsByTagName(CLOUND).item(0);
			String name = cloundElement.getAttribute(CLOUND_PROPERTY_NAME);
			String namespace = cloundElement.getAttribute(CLOUND_PROPERTY_NAMESPACE);
			
			if(!name.equals(getName()) ){
				log.error("The clound name is not match!");
				throw new ConfigException("The clound name is not match!");
			}
			
			if(!namespace.equals(getNameSpace())){
				log.error("The clound name space is not match!");
				throw new ConfigException("The clound name space is not match!");
			}
			
			Element versionElement = (Element) doc.getElementsByTagName(VERSION_CONFIG).item(0);
			
			String version = versionElement.getAttribute(VERSION_CONFIG_PROPERTY_VERSION);
			
			if(!version.equals(getVersion()) ){
				log.error("The clound version is not match!");
				throw new ConfigException("The clound version is not match!");
			}
			
			cloundDesc.setName(name);
			cloundDesc.setNamespace(namespace);
			cloundDesc.setVersion(version);
			cloundDesc.setClassName(this.getClassName());
			//cloundDesc.setNodeObj((Node)Class.forName(this.getClassName()).newInstance());
			
			NodeList systemInitValueList  = versionElement.getElementsByTagName("system-init-values");
			if(systemInitValueList.getLength() > 0){
				List<InitValueDesc> desclList = parseInitValue(version,(Element) systemInitValueList.item(0));
				cloundDesc.add(CloundDesc.TYPS_INITVALUE, desclList);
			}
			
			NodeList libPathList  = versionElement.getElementsByTagName("clound-lib-path");
			if(libPathList.getLength() == 0){
				log.error("The clound:"+name+",the lib path is not config!");
				throw new ConfigException("The clound:"+name+",the lib path is not config!");
			}
			
			List<LibPathDesc> libDescList = parseLibPath(version,(Element) libPathList.item(0));
			cloundDesc.add(CloundDesc.TYPS_LIBPATH, libDescList);
			
			NodeList cmdList = versionElement.getElementsByTagName("system-cmd-list");
			
			if(cmdList.getLength() == 0){
				log.error("The clound:"+name+",the cmd is not config!");
				throw new ConfigException("The clound:"+name+",the cmd is not config!");
			}
			
			List<SimpleDesc> cmdDescList = parseSimpleDesc((Element) cmdList.item(0),"cmd-info",
					((LibPathDesc)cloundDesc.get(CloundDesc.TYPS_LIBPATH, LibPathDesc.SYSTEM_LIB, version)).getLibPath(),
					CloundConstanst.TYPE_SYSCMD);
			cloundDesc.add(CloundDesc.TYPS_SYSTEMCMD, cmdDescList);
			
			
			/*List<SimpleDesc> cmdDescList = parseNodeFormJar(((LibPathDesc)cloundDesc.get(CloundDesc.TYPS_LIBPATH, LibPathDesc.SYSTEM_LIB, version)).getLibPath()
					,CloundConstanst.TYPE_SYSCMD);
			cloundDesc.add(CloundDesc.TYPS_SYSTEMCMD, cmdDescList);*/
			
			//NodeList compantList = versionElement.getElementsByTagName("compant-list");
			
			/*if(compantList.getLength() > 0){
				List<SimpleDesc> compantDescList = parseSimpleDesc((Element) compantList.item(0),"compant-info",
						((LibPathDesc)cloundDesc.get(CloundDesc.TYPS_LIBPATH, LibPathDesc.COMPANT_LIB, version)).getLibPath(),
						CloundConstanst.TYPE_COMPANT);
				cloundDesc.add(CloundDesc.TYPS_COMPANT, compantDescList);
				
				//initNode(compantDescList);
			}*/
			
			List<SimpleDesc> compantDescList = parseNodeFormJar(((LibPathDesc)cloundDesc.get(CloundDesc.TYPS_LIBPATH, LibPathDesc.COMPANT_LIB, version)).getLibPath()
					,CloundConstanst.TYPE_COMPANT);
			cloundDesc.add(CloundDesc.TYPS_COMPANT, compantDescList);
			
			NodeList moudleList = versionElement.getElementsByTagName("moudle-list");
			
			if(moudleList.getLength() != 0){
				List<SimpleDesc> moudleDescList = parseSimpleDesc((Element) moudleList.item(0),"moudle-info",
						((LibPathDesc)cloundDesc.get(CloundDesc.TYPS_LIBPATH, LibPathDesc.MOUDLE_LIB, version)).getLibPath(),
						CloundConstanst.TYPE_MOUDLE);
				cloundDesc.add(CloundDesc.TYPS_MOUDLE, moudleDescList);
			}else{
				//initNode(moudleDescList);
				List<SimpleDesc> moudleDescList = parseNodeFormJar(((LibPathDesc)cloundDesc.get(CloundDesc.TYPS_LIBPATH, LibPathDesc.MOUDLE_LIB, version)).getLibPath()
						,CloundConstanst.TYPE_MOUDLE);
				cloundDesc.add(CloundDesc.TYPS_MOUDLE, moudleDescList);
			}
		} catch (Exception e) {
			log.error("The clound:"+name+",version:"+version+" parse error!",e);
			throw new ConfigException("The clound:"+name+",version:"+version+" parse error!",e);
		}
		
		log.info("The clound:"+name+",version:"+version+" parse success!");
		
		return cloundDesc;
	}
	
	private List<LibPathDesc> parseLibPath(String version,Element element){
		NodeList nodeList  = element.getChildNodes();
		List<LibPathDesc> list = new ArrayList<LibPathDesc>(3);
		for(int i = 0 ; i < nodeList.getLength();i++){
			org.w3c.dom.Node node = nodeList.item(i);
			if(node instanceof Element){
				Element libElement = (Element)node;
				LibPathDesc libPathDesc = new LibPathDesc();
				libPathDesc.setLibPath(libElement.getTextContent());
				libPathDesc.setVersion(version);
				libPathDesc.setName(libMap.get(libElement.getNodeName()));
				list.add(libPathDesc);
			}
		}
		return list;
	}
	

	
	public  static void main(String args[]) throws ConfigException, ClassNotFoundException, FileNotFoundException{
		CloundParser cloundParser = new CloundParser();
		
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    //System.out.print(classLoader.l);
	    URL url = classLoader.loadClass("com.minicould.clound.def.DefaultClound").getResource("clound.xml");
	    cloundParser.setFilePath(url.getPath());
		cloundParser.setName("defaultClound");
		cloundParser.setNameSpace("defaultClound");
		cloundParser.setVersion("1.0");
		cloundParser.setClassName("com.minicould.clound.def.DefaultClound");
		cloundParser.setFileStream(new FileInputStream(url.getPath()));
		cloundParser.setInitCompant(new InitCommand());
		cloundParser.parse();
		System.out.print("");
	}
	
}
