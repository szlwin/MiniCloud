package com.minicould.tool.load;

import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.List;

public class DycClassLoader extends SecureClassLoader{

	private List<JarClassLoader> loaderList = new ArrayList<JarClassLoader>(20);

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> classObj = find(name);
		
		if(classObj == null)
			throw new ClassNotFoundException();
		
		return classObj;
	}
	
	public void addJar(String path){
		synchronized(loaderList){
			JarClassLoader jarClassLoader = new JarClassLoader(path);
			loaderList.add(0,jarClassLoader);
			
		}
	}
	
	private Class<?> find(String name) throws ClassNotFoundException{
		System.out.println("test: "+name);
		synchronized(loaderList){
			for(int i = 0; i < loaderList.size();i++){
				JarClassLoader cLoader = loaderList.get(i);
				if(cLoader != null){
					Class<?> classObj = loaderList.get(i).loadClass(name);
					if(classObj != null){
						System.out.println(name);
						return classObj;
					}
				}
			}
			return null;
		}
	}
}
