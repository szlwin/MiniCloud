package com.minicould.tool.load;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class JarClassLoader{
	
	private URLClassLoader uRLClassLoader;

	private String path;
	
	public JarClassLoader(String path){
		this.path = path;
	}
	/*
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> classObj  = this.findLoadedClass(name);
		
		return classObj;
	}*/
	
	public Class<?> loadClass(String name) throws ClassNotFoundException{
		if(uRLClassLoader == null){
			try {
				loadJar();
			} catch (MalformedURLException e) {
				throw new ClassNotFoundException("The jar path is error!");
			}
		}
		return uRLClassLoader.loadClass(name);
	}
	
	public void loadJar() throws MalformedURLException{
		File file = new File(path);
		uRLClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
	}
	
	/*
	public void loadJar() throws IOException{
		File file = new File(path);
		String[] fileArray = file.list();
		
		for(int i = 0 ; i < fileArray.length; i++){
			if(fileArray[i].endsWith(".jar")){
				ZipReadJar zipReadJar = new ZipReadJar();
				List<ClassInfo> classInfoList = zipReadJar.readJar(path+"/"+fileArray[i]);
				loadJarClass(classInfoList);
			}
		}
	}
	
	private void loadJarClass(List<ClassInfo> classInfoList){
		for(int i = 0; i< classInfoList.size();i++){
			ClassInfo classInfo = classInfoList.get(i);
			byte[] classByte = classInfo.getByteArray();
			Class<?> classObj = this.defineClass(classInfo.getName(),classByte, 0, classByte.length);
			this.resolveClass(classObj);
		}
	}*/
}
