package com.minicould.tool.load;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipReadJar {
	
	public List<ClassInfo> readJar(String filePath) throws IOException{
		List<ClassInfo> list = new ArrayList<ClassInfo>();
		ZipInputStream zis = null;
		ZipEntry ze =null ;
		try {
			zis = new ZipInputStream(new FileInputStream (filePath));
			ze = zis.getNextEntry();
			
	        while(ze != null){
	            if(isClassFile(ze)){
	            	
	            	byte[] byteArray = readByte(zis,ze);
	                
	                ClassInfo classInfo = new ClassInfo();
	                classInfo.setByteArray(byteArray);
	                classInfo.setName(convertName(ze.getName()));
	                list.add(classInfo);
	            }
	            ze = zis.getNextEntry();
	        }
		} catch (IOException e) {
			throw e;
		}finally{
			 try {
				zis.close();
			} catch (IOException e) {
			}
		}
		return list;
	}
	
	private boolean isClassFile(ZipEntry ze){
		return !ze.isDirectory() && ze.getName().endsWith(".class");
	}
	
	private byte[] readByte(ZipInputStream zis,ZipEntry ze) throws IOException{
        ByteArrayOutputStream  byteArrayOutputStream  = new ByteArrayOutputStream();
        
        //byte byteTempArray[] = new byte[1024];
        int bufferSize = 4096;

        byte[] buffer = new byte[bufferSize];

        int bytesNumRead = 0;

        while ((bytesNumRead = zis.read(buffer)) != -1) {
        	byteArrayOutputStream.write(buffer, 0, bytesNumRead);
        }

        byte[] byteArray = byteArrayOutputStream.toByteArray();
        
        try{
        	byteArrayOutputStream.close();
        }catch(IOException e){
        	
        }

        return byteArray;
	}
	
	private String convertName(String name){
		String className =  name.substring(0,name.length()-6).replace('/', '.');
		return className;
	}
	
    public static void main(String args[]) throws FileNotFoundException, IOException{
        ZipInputStream zis = new ZipInputStream(new FileInputStream ("E:/miniclound/clound/com.minicould.clound.def.DefaultClound_1.0.jar"));//生成读取ZIP文件的流
        ZipEntry ze = zis.getNextEntry();//取得下一个文件项
        while(ze != null){
            if(!ze.isDirectory()){
            	System.out.println(ze.getName());
            }
            ze = zis.getNextEntry();
        }
        //long size = ze.getSize();//取得这一项的大小

       // FileOutputStream fos = new FileOutputStream("c://"+ze.getName());//产生输出文件对象
        //for(int i= 0;i<size;i++){//循环读取文件并写入输出文件对象
        //    byte c = (byte)zis.read();
        //    fos.write(c);
        //}
        //fos.close();
        List list = new ArrayList();
        list.add(new byte[]{});
        byte[] b = (byte[]) list.get(0);
        zis.close();
        
        
        JarFile jzf = new JarFile("E:/miniclound/clound/com.minicould.clound.def.DefaultClound_1.0.jar");
		//JarEntry  ze = new JarEntry(simpleDesc.getClassName().substring(0,simpleDesc.getClassName().lastIndexOf('.')).replace(".", File.separator)+File.separator+CloundConstanst.fileName[type]);
		JarEntry  jze =  jzf.getJarEntry("com/minicould/clound/def/clound.xml");
		                                  //com\minicould\clound\def\clound.xml
		InputStream inputStream = jzf.getInputStream(jze);
		System.out.println(inputStream == null);
    }
}

