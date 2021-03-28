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

import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.desc.InitValueDesc;
import com.minicould.common.desc.LibPathDesc;
import com.minicould.common.desc.MoudleDesc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.exception.ConfigException;

public class MoudleParser extends NodeParser<MoudleDesc>{

	private Log log = LogFactory.getLog(MoudleParser.class);
	
	@Override
	public MoudleDesc parse() throws ConfigException {
		//log.info("The moudle:"+this.name+",version:"+this.version+" parse start!");
		MoudleDesc moudleDesc = new MoudleDesc();
		DocumentBuilder builder;
		Document doc;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(fileStream);
			
			Element moudleElement = (Element) doc.getElementsByTagName("moudle").item(0);
			String name = moudleElement.getAttribute("name");
			
			
			if(this.name == null){
				setName(name);
			}else{
				if(!name.equals(getName()) ){
					log.error("The moudle name is not match!");
					throw new ConfigException("The moudle name is not match!");
				}
			}
			
			
			Element versionElement = (Element) doc.getElementsByTagName("version-config").item(0);
			
			String version = versionElement.getAttribute("version");
			
			if(!version.equals(getVersion()) ){
				log.error("The moudle version is not match!");
				throw new ConfigException("The moudle version is not match!");
			}
			
			moudleDesc.setName(name);
			moudleDesc.setNamespace(name);
			moudleDesc.setVersion(version);
			moudleDesc.setClassName(this.getClassName());
			//moudleDesc.setNodeObj((Node)Class.forName(this.getClassName()).newInstance());
			
			NodeList systemInitValueList  = versionElement.getElementsByTagName("moudle-init-values");
			if(systemInitValueList.getLength() > 0){
				List<InitValueDesc> desclList = parseInitValue(version,(Element) systemInitValueList.item(0));
				moudleDesc.add(MoudleDesc.TYPS_INITVALUE, desclList);
			}
			
			NodeList libPathList  = versionElement.getElementsByTagName("moudle-lib-path");
			if(libPathList.getLength() == 0){
				log.error("The moudle:"+name+",the lib path is not config!");
				throw new ConfigException("The moudle:"+name+",the lib path is not config!");
			}
			
			List<LibPathDesc> libDescList = parseLibPath(version,(Element) libPathList.item(0));
			moudleDesc.add(MoudleDesc.TYPS_LIBPATH, libDescList);
			
			NodeList serviceList = versionElement.getElementsByTagName("service-list");
			
			if(serviceList.getLength() > 0){
				List<SimpleDesc> serviceDescList = parseSimpleDesc((Element) serviceList.item(0),"service-info",
						((LibPathDesc)moudleDesc.get(MoudleDesc.TYPS_LIBPATH, LibPathDesc.SERVICE_LIB, version)).getLibPath(),
						CloundConstanst.TYPE_SERVICE);
				moudleDesc.add(MoudleDesc.TYPS_SERVICE, serviceDescList);
				
				//initNode(serviceDescList);
			}else{
				List<SimpleDesc> serviceDescList = parseNodeFormJar(((LibPathDesc)moudleDesc.get(MoudleDesc.TYPS_LIBPATH, LibPathDesc.SERVICE_LIB, version)).getLibPath()
						,CloundConstanst.TYPE_SERVICE);
				moudleDesc.add(MoudleDesc.TYPS_SERVICE, serviceDescList);
			}
			


		} catch (Exception e) {
			log.error("The moudle:"+name+",version:"+version+" parse error!",e);
			throw new ConfigException("The moudle:"+name+",version:"+version+" parse error!",e);
		}
		
		log.info("The moudle:"+name+",version:"+version+" parse success!");
		return moudleDesc;
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
				libPathDesc.setName(LibPathDesc.SERVICE_LIB);
				list.add(libPathDesc);
			}
		}
		return list;
	}

}
