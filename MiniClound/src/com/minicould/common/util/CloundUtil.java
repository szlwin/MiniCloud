package com.minicould.common.util;

import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.minicould.clound.manager.CloundContext;
import com.minicould.clound.manager.CloundManager;
import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.data.CloundData;
import com.minicould.common.data.CmdInfo;
import com.minicould.common.data.ServiceData;
import com.minicould.common.desc.CloundDesc;
import com.minicould.common.desc.LibPathDesc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.result.CompantResult;
import com.minicould.exception.CloundException;
import com.minicould.node.Clound;
import com.minicould.node.ENode;
import com.minicould.node.Moudle;
import com.minicould.node.Node;
import com.minicould.node.SystemCompant;
import com.minicould.tool.load.NodeClassLoader;
import com.minicould.common.desc.MoudleDesc;
public class CloundUtil {
	
	public static String getFile(String className,int type){
		
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    URL url;
		try {
			url = classLoader.loadClass(className).getResource(CloundConstanst.fileName[type]);
		} catch (ClassNotFoundException e) {
			return null;
		}
	    return url.getFile();
	}
	
	public static InputStream getInputStream(SimpleDesc simpleDesc){
	    JarFile zf = null;
	    InputStream inputStream;
	    NodeClassLoader classLoader = simpleDesc.getNodeClassLoader();
	    int type = simpleDesc.getNodeObj().getType();
	    
		try {
			inputStream = classLoader.loadClass(simpleDesc.getClassName()).getResourceAsStream(CloundConstanst.fileName[type]);
			if(inputStream != null)
				return inputStream;
			zf = new JarFile(classLoader.getPath()+simpleDesc.getClassName()+"_"+simpleDesc.getVersion()+".jar");
			JarEntry  ze =  zf.getJarEntry(simpleDesc.getClassName().substring(0,simpleDesc.getClassName().lastIndexOf('.')).replace('.','/' )+"/"+CloundConstanst.fileName[type]);
			inputStream = zf.getInputStream(ze);
		} catch (Exception e) {
			return null;
		}
	    return inputStream;
	}
	
	public static InputStream getInputStream(String name,String version,int type){
		SimpleDesc simpleDesc = getSimpleDesc(name,version,type);
	    NodeClassLoader classLoader = simpleDesc.getNodeClassLoader();
	    JarFile zf = null;
	    InputStream inputStream;
		try {
			zf = new JarFile(classLoader.getPath()+simpleDesc.getClassName()+"_"+simpleDesc.getVersion()+".jar");
			JarEntry  ze =  zf.getJarEntry(simpleDesc.getClassName().substring(0,simpleDesc.getClassName().lastIndexOf('.')).replace('.','/' )+"/"+CloundConstanst.fileName[type]);
			inputStream = zf.getInputStream(ze);
		} catch (Exception e) {
			return null;
		}
	    return inputStream;
	}
	
	public static InputStream getJarInputStream(String filePath,String jarFile,int type){
	
	    JarFile zf = null;
	    InputStream inputStream;
	    String configFilePath = jarFile.substring(0,jarFile.indexOf("_")-1).replace('.', '/');
		try {
			zf = new JarFile(filePath+"/"+jarFile);
			JarEntry  ze =  zf.getJarEntry(configFilePath+"/"+CloundConstanst.fileName[type]);
			inputStream = zf.getInputStream(ze);
		} catch (Exception e) {
			return null;
		}
	    return inputStream;
	}
	
	public static String getLibPath(String name,String version,int type){
		CloundContext context = getCurrentContext();
		CloundDesc desc = (CloundDesc) getSimpleDesc(context.getCname(),context.getCversion(),CloundConstanst.TYPE_CLOUND);
		switch(type){
			case CloundConstanst.TYPE_CLOUND:
				return null;	
			case CloundConstanst.TYPE_SERVICE:
				SimpleDesc mDesc = getSimpleDesc(name.split("/")[0],null,CloundConstanst.TYPE_MOUDLE);
				MoudleDesc moudleDesc = (MoudleDesc) mDesc.getNodeObj().getDesc();
				return ((LibPathDesc)moudleDesc.get(MoudleDesc.TYPS_LIBPATH, LibPathDesc.SERVICE_LIB,moudleDesc.getVersion())).getLibPath();
			case CloundConstanst.TYPE_MOUDLE:
				return ((LibPathDesc)desc.get(CloundDesc.TYPS_LIBPATH, LibPathDesc.MOUDLE_LIB, context.getCversion())).getLibPath();
			case CloundConstanst.TYPE_COMPANT:
				return ((LibPathDesc)desc.get(CloundDesc.TYPS_LIBPATH, LibPathDesc.COMPANT_LIB, context.getCversion())).getLibPath();
			case CloundConstanst.TYPE_SYSCMD:
				return ((LibPathDesc)desc.get(CloundDesc.TYPS_LIBPATH, LibPathDesc.SYSTEM_LIB, context.getCversion())).getLibPath();
		}
		return null;
	}
	
	public static InputStream getInputStream(String name,String version,String jarPath,int type){
		String libPath = getLibPath(name,version,type);
	    JarFile zf = null;
	    InputStream inputStream;
	    String filePath = jarPath.substring(0,jarPath.length()-4).split("_")[0].replace(".", "/");
	    int index = filePath.lastIndexOf('/');
	    filePath = filePath.substring(0,index);
		try {
			zf = new JarFile(libPath+"/"+jarPath);
			JarEntry  ze =  zf.getJarEntry(filePath+"/"+CloundConstanst.fileName[type]);
			inputStream = zf.getInputStream(ze);
		} catch (Exception e) {
			return null;
		}
	    return inputStream;
	}
	
	public static InputStream getInputStream(String className,int type){
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    InputStream inputStream;
		try {
			inputStream = classLoader.loadClass(className).getResourceAsStream(CloundConstanst.fileName[type]);
		} catch (ClassNotFoundException e) {
			return null;
		}
	    return inputStream;
	}
	
	public static CmdInfo parseCmd(String cmd){
		CmdInfo cmdInfo = new CmdInfo();
		cmd = cmd.trim();
		StringBuffer argBuffer = new StringBuffer();
		
		String[] cmdArray = cmd.split(" ");
		
		cmdInfo.setName(cmdArray[0]);
		cmdInfo.setCname(cmdArray[1]);
		cmdInfo.setCversion(cmdArray[2]);
		
		for(int i = 3; i < cmdArray.length; i++){
			argBuffer.append(cmdArray[i]);
			argBuffer.append(" ");
		}
		cmdInfo.setArg(argBuffer.toString());
		return cmdInfo;
	}
	
	public static CompantResult execCmd(String cmdName,SimpleDesc desc) throws CloundException{
		SystemCompant sysCompant = (SystemCompant) getNode(cmdName,null,CloundConstanst.TYPE_SYSCMD);
		
		return sysCompant.execute(desc);
	}
	
	public static CompantResult execCmd(String cmd) throws CloundException{
		CmdInfo cmdInfo = parseCmd(cmd);
		
		CloundContext cloundContext = new CloundContext();
		cloundContext.setCname(cmdInfo.getCname());
		cloundContext.setCversion(cmdInfo.getCversion());
		setCurrentContext(cloundContext);
		
		Clound clound = (Clound) getNode(cmdInfo.getCname(),cmdInfo.getCversion(),CloundConstanst.TYPE_CLOUND);

		return (CompantResult) clound.executeV(cmdInfo);
	}
	
	public static CompantResult execCmd(CmdInfo cmdInfo) throws CloundException{
		SystemCompant sysCompant = (SystemCompant) getNode(cmdInfo.getName(),null,CloundConstanst.TYPE_SYSCMD);
		return sysCompant.execute(cmdInfo);
	}
	
	public static ENode getENode(String name,String version,int type,String cname,String cversion){
		return null;
		
	}
	
	public static SystemCompant getCmd(String name){
		return (SystemCompant) getNode(name,null,CloundConstanst.TYPE_SYSCMD);
	}
	
	public static Node getNode(String name,String version,int type){
		CloundContext context = getCurrentContext();
		return CloundManager.getInstance().getNode(name,version,type,context.getCname(),context.getCversion());
	}
	
	public static Node getServiceNode(String name,String version,String mName,String mVersion){
		CloundContext context = getCurrentContext();
		Moudle moudle = (Moudle) CloundManager.getInstance().getNode(name,version,CloundConstanst.TYPE_MOUDLE,
				context.getCname(),context.getCversion());
		 SimpleDesc serviceDesc = (SimpleDesc) ((MoudleDesc)moudle.getDesc()).get(MoudleDesc.TYPS_SERVICE, name, version);
		 return serviceDesc.getNodeObj();
	}
	
	public static SimpleDesc getSimpleDesc(String name,String version,int type){
		CloundContext context = getCurrentContext();
		return CloundManager.getInstance().getDesc(name,version,type,context.getCname(),context.getCversion());
	}
	
	public static SystemCompant getSystemCompant(String cname,String cversion,String name){
		return null;
		
	}
	
	public static void setCurrentContext(CloundContext context){
		CloundManager.getInstance().setCurrentContext(context);
	}
	
	public static CloundContext getCurrentContext(){
		return CloundManager.getInstance().getCurrentContext();
	}
	
	public static Object getInstance(String className){
		try {
			return Class.forName(className).newInstance();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public static ServiceData parse(String cmd){
		ServiceData serviceData = new ServiceData();
		cmd = cmd.substring(1,cmd.length()-1);
		cmd = cmd.trim();
		String cmdArray[] = cmd.split(",");
		for(int i = 0; i < cmdArray.length;i++){
			String cmdInfo[] = cmdArray[i].split(":");
			String cmdKey = cmdInfo[0].trim();
			String cmdValue = cmdInfo[1].trim();
			serviceData.setValue(cmdKey, cmdValue);
		}
		return serviceData;
	}
	
	public static void parseCloundData(String paramStr,CloundData cloundData){
		paramStr = paramStr.substring(1,paramStr.length()-1);
		paramStr = paramStr.trim();
		String cmdArray[] = paramStr.split(",");
		for(int i = 0; i < cmdArray.length;i++){
			String cmdInfo[] = cmdArray[i].split(":");
			String cmdKey = cmdInfo[0].trim();
			String cmdValue = cmdInfo[1].trim();
			cloundData.setValue(cmdKey, cmdValue);
		}
	}
	
	public static boolean check(int status){
		return status == Node.STATUS_START;
	}
}
