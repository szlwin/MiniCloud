package com.minicould.compant.sys.cmd.init;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.desc.Desc;
import com.minicould.common.desc.InitValueDesc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.util.CloundUtil;
import com.minicould.exception.CloundException;
import com.minicould.exception.ConfigException;
import com.minicould.node.Node;
import com.minicould.node.SystemCompant;
import com.minicould.tool.load.NodeClassLoader;

public abstract class NodeParser<E extends Desc> {

	//private Log log = LogFactory.getLog(NodeParser.class);
	
	protected String name;
	
	protected String version;
	
	protected String filePath;
	
	protected String className;
	
	protected String nameSpace;
	
	protected SystemCompant initCompant;
	
	protected InputStream fileStream;
	
	public abstract E parse() throws ConfigException;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public SystemCompant getInitCompant() {
		return initCompant;
	}

	public void setInitCompant(SystemCompant initCompant) {
		this.initCompant = initCompant;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}
	
	/*protected void initNode(List<SimpleDesc> descList) throws CloundException{
		for(int i = 0; i < descList.size();i++){
			log.info(descList.get(i).getName()+" start");
			descList.get(i)
				.getNodeObj()
				.init();
			log.info(descList.get(i).getName()+" end");
		}
	}*/
	protected List<SimpleDesc> parseNodeFormJar(String libPath,int type) throws InstantiationException, IllegalAccessException, ClassNotFoundException, CloundException{
		
		File file = new File(libPath);
		String[] jarFileArray = file.list();
		List<SimpleDesc> list = new ArrayList<SimpleDesc>(jarFileArray.length);
		
		for(int i = 0; i < jarFileArray.length;i++){
			if(jarFileArray[i].endsWith(".jar")){
				SimpleDesc simpleDesc = parseNodeFormJar(libPath,jarFileArray[i],type);
				list.add(simpleDesc);
			}
			
		}
		return list;
	}
	
	protected SimpleDesc parseNodeFormJar(String libPath,String jarFile,int type) throws InstantiationException, IllegalAccessException, ClassNotFoundException, CloundException{
		String version = getVersion(jarFile);
		String className = parseClassName(jarFile,version);
		
		SimpleDesc simpleDesc = new SimpleDesc();
		simpleDesc.setVersion(version);
		simpleDesc.setClassName(className);
		//simpleDesc.setName(jarFile);
		//InputStream stream = CloundUtil.getJarInputStream(libPath, jarFile, type);
		NodeClassLoader nodeClassLoader = new NodeClassLoader();
		nodeClassLoader.setName(simpleDesc.getClassName());
		nodeClassLoader.setVersion(simpleDesc.getVersion());
		nodeClassLoader.setPath(libPath);
		
		simpleDesc.setNodeClassLoader(nodeClassLoader);
		Node node = (Node) nodeClassLoader.loadClass(simpleDesc.getClassName()).newInstance();
		node.setInitCompant(this.getInitCompant());
		//node.setName(simpleDesc.getName());
		//node.setNameSpace(simpleDesc.getNamespace());
		node.setVersion(version);
		node.setType(type);
		
		node.setStatus(Node.STATUS_INIT);
		simpleDesc.setNodeObj(node);
		node.init(CloundUtil.getInputStream(simpleDesc));
		
		Desc desc = node.getDesc();
		node.setName(desc.getName());
		node.setNameSpace(desc.getName());
		simpleDesc.setName(desc.getName());
		simpleDesc.setNamespace(desc.getName());
		return simpleDesc;
	}
	
	protected List<SimpleDesc> parseSimpleDesc(Element element,String name,String path,int type) throws InstantiationException, IllegalAccessException, ClassNotFoundException, CloundException{
		NodeList nodeList = element.getElementsByTagName(name);
		
		List<SimpleDesc> list = new ArrayList<SimpleDesc>(nodeList.getLength());
		for(int i = 0 ; i < nodeList.getLength();i++){
			Element nodeElement = (Element) nodeList.item(i);
			String nodeName = nodeElement.getElementsByTagName("name").item(0).getTextContent();
			List<String> nodeNameList = new ArrayList<String>();
			if(nodeName.indexOf(',')>0){
				String nodeNameArray[] = nodeName.split(",");
				for(int k = 0 ; k < nodeNameArray.length;k++){
					nodeNameList.add(nodeNameArray[k]);
				}
			}
			else
				nodeNameList.add(nodeName);
			
			for(int j = 0 ; j < nodeNameList.size();j++){
				SimpleDesc simpleDesc = new SimpleDesc();
				
				simpleDesc.setName(nodeNameList.get(j));
				simpleDesc.setNamespace(nodeElement.getElementsByTagName("namespace").item(0).getTextContent());
				simpleDesc.setVersion(nodeElement.getElementsByTagName("version").item(0).getTextContent());
				simpleDesc.setClassName(nodeElement.getElementsByTagName("class").item(0).getTextContent());
				
				NodeClassLoader nodeClassLoader = new NodeClassLoader();
				nodeClassLoader.setName(simpleDesc.getClassName());
				nodeClassLoader.setVersion(simpleDesc.getVersion());
				nodeClassLoader.setPath(path);
				
				simpleDesc.setNodeClassLoader(nodeClassLoader);
				Node node = (Node) nodeClassLoader.loadClass(simpleDesc.getClassName()).newInstance();
				node.setInitCompant(this.getInitCompant());
				node.setName(simpleDesc.getName());
				node.setNameSpace(simpleDesc.getNamespace());
				node.setVersion(simpleDesc.getVersion());
				node.setType(type);
				if(type == CloundConstanst.TYPE_COMPANT 
						|| type == CloundConstanst.TYPE_SYSCMD )
					node.setStatus(Node.STATUS_START);
				else{
					node.setStatus(Node.STATUS_INIT);
				}
				simpleDesc.setNodeObj(node);
				node.init(CloundUtil.getInputStream(simpleDesc));
				
				/*if(!simpleDesc.getName().equals(node.getName()))
					throw new ConfigException("The name:"+simpleDesc.getName()+",the name is not match!");
				
				if(!simpleDesc.getVersion().equals(node.getVersion()) )
					throw new ConfigException("The name:"+simpleDesc.getName()+",the version is not match!");*/
				
				//simpleDesc.setNodeObj(node);
				list.add(simpleDesc);
			}

		}
		return list;
	}
	
	protected List<InitValueDesc> parseInitValue(String version,Element element){
		NodeList nodeList = element.getElementsByTagName("init-value");
		List<InitValueDesc> list = new ArrayList<InitValueDesc>(nodeList.getLength());
		for(int i = 0 ; i < nodeList.getLength();i++){
			Element initElement = (Element) nodeList.item(i);
			InitValueDesc initValueDesc = new InitValueDesc();
			initValueDesc.setName(initElement.getAttribute("key"));
			initValueDesc.setValue(initElement.getAttribute("value"));
			initValueDesc.setVersion(version);
			list.add(initValueDesc);
		}
		return list;
	}
	
	
	private String parseClassName(String jar,String version){
		return jar.substring(0,jar.length()-4-version.length()-1);
	}
	
	private String getVersion(String jar){
		return jar.substring(jar.indexOf('_')+1,jar.lastIndexOf("."));
	}
}
