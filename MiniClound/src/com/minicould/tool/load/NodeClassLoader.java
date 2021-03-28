package com.minicould.tool.load;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.minicould.common.data.ServiceData;
import com.minicould.common.result.ServiceResult;
import com.minicould.exception.CloundException;
import com.minicould.node.Service;

public class NodeClassLoader{
	
	private String path;
	
	private String name;
	
	private String version;
	
	private URLClassLoader uRLClassLoader;
	
	private synchronized Class<?> load() throws ClassNotFoundException{
		if(uRLClassLoader == null){
			File file = new File(path+"/"+name+"_"+version+".jar");
			URL url = null;
			try {
				 url = file.toURI().toURL();
			} catch (MalformedURLException e) {
				throw new ClassNotFoundException("The jar path is error!");
			}
			//uRLClassLoader = new URLClassLoader(new URL[]{url});
			DycClassLoader dycClassLoader = LoaderFactory.getDefaultClassLoader();
			uRLClassLoader = new URLClassLoader(new URL[]{url},
					dycClassLoader);
		}
		Class<?> nodeObjClass = uRLClassLoader.loadClass(name);
		//nodeObjClass = uRLClassLoader.getParent().loadClass(name);
		/*
		ZipReadJar zipReadJar = new ZipReadJar();
		Class<?> nodeObjClass = null;
		try {
			List<ClassInfo> classInfoList = zipReadJar.readJar(path+"/"+name+"_"+version+".jar");
			for(int i = 0; i< classInfoList.size();i++){
				ClassInfo classInfo = classInfoList.get(i);
				byte[] classByte = classInfo.getByteArray();
				if(classInfo.getName().equals(name)){
					nodeObjClass = this.defineClass(classInfo.getName(),classByte, 0, classByte.length);
				}else{
					this.defineClass(classInfo.getName(),classByte, 0, classByte.length);
				}
			}
		} catch (IOException e) {
			throw new ClassNotFoundException("The jar file error");
		}
		resolveClass(nodeObjClass);*/
		return nodeObjClass;
	}
	
	/*
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> classObj  = this.findLoadedClass(name);
		if(classObj != null){
			return classObj;
		}
		return load();
	}*/
	
	public Class<?> loadClass(String name) throws ClassNotFoundException{
		if(uRLClassLoader == null){
			return load();
		}
		return uRLClassLoader.loadClass(name);
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

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

    //protected String findLibrary(String libname) {
     //   return "E:/miniclound/lib"+libname;
    //}
    
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, CloundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, IOException, InterruptedException{
		//JarClassLoader jarClassLoader = new JarClassLoader();
		//jarClassLoader.setPath("");
		//jarClassLoader.loadJar();
		//Test test = new Test();
		NodeClassLoader nodeClassLoader = new NodeClassLoader();
		nodeClassLoader.setPath("E:/miniclound/clound/default_1.0/moudlelib/testMoudle_1.1");
		nodeClassLoader.setName("com.minicould.clound.def.service.test.TestLoadService");
		nodeClassLoader.setVersion("1.0");
		
		//nodeClassLoader.findLibrary(libname);
		//Service service = (Service) classObj.newInstance();
		//ServiceResult serviceResult = service.execute(new ServiceData());
		//System.out.println(serviceResult.getValue());

		//nodeClassLoader.resolveClass(classObj);
		//Class<?> classObj1 = nodeClassLoader.loadClass("com.minicould.common.data.ServiceData",true);
		//Class<?> classObj2 = nodeClassLoader.loadClass("com.minicould.common.result.ServiceResult",true);
		//Class<?> classObj3 = nodeClassLoader.loadClass("com.minicould.exception.CloundException",true);
		//Class<?> classObj4 = nodeClassLoader.loadClass("com.minicould.service.AbstractService",true);
		DycClassLoader dycClassLoader = LoaderFactory.getDefaultClassLoader();
		//dycClassLoader.clearAssertionStatus();
		//Thread.sleep(1000);
		dycClassLoader.addJar("E:/miniclound/common/dom4j-1.6.1.jar");
		//dycClassLoader.loadClass("java.lang.String");
		//Thread.currentThread().setContextClassLoader(dycClassLoader);
		//dycClassLoader.findClass("com.minicould.clound.def.service.test.TestLoadService");
		Class<?> classObj = nodeClassLoader.loadClass("com.minicould.clound.def.service.test.TestLoadService");
		if(classObj == null)
			classObj = nodeClassLoader.loadClass("com.minicould.clound.def.service.test.TestLoadService");
		//NodeClassLoader nodeClassLoader1 = new NodeClassLoader();
		//nodeClassLoader1.setPath("D:/eworkspace/MiniClound/lib/");
		//nodeClassLoader1.findClass("Test").newInstance();
		//classObj.newInstance();
		
		//Service service = (Service) classObj.newInstance();
		//ServiceResult serviceResult = service.execute(new ServiceData());
		//Method method = service.getClass().getMethod("execute",new Class[]{ServiceData.class});
		//ServiceData serviceData = new ServiceData();
		//serviceData.setValue("name", "test");
		//ServiceResult serviceResult = (ServiceResult) method.invoke(service,new Object[]{serviceData});
		
		Service service = (Service) classObj.newInstance();
		ServiceResult serviceResult = service.execute(new ServiceData());
		//Test test1 = new Test();
		//Class[] classA =  classObj.getDeclaredClasses();
		//Method method[] = classObj.getMethods();
		//ClassLoader.getSystemClassLoader().loadClass(name);
		System.out.println("123");
		
		ClassLoader.getSystemClassLoader();
		
	}
	
}
