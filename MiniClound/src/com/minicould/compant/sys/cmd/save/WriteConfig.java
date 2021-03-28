package com.minicould.compant.sys.cmd.save;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.minicould.common.desc.CloundDesc;

public class WriteConfig {

	private CloundDesc desc;
	
	private String filePath;
	
	public WriteConfig(CloundDesc desc){
		this.desc = desc;
	}
	
	public void write() throws IOException{
		XMLWriter writer = createWriter(); 
		Document doc = DocumentHelper.createDocument();
		
        OutputFormat format = OutputFormat.createPrettyPrint();  
        writer = new XMLWriter(new FileOutputStream(getFilePath()), format);
        
		CloundWriter cloundWriter = new CloundWriter(desc,doc);
		
		cloundWriter.write();
		
		writer.write(doc);
		writer.close();
	}
	
	public String getFilePath(){
		return filePath;
	}
	
	private XMLWriter createWriter() throws IOException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		filePath = desc.getName()+"_"+desc.getVersion()+"_"+format.format(new Date())+".xml";
		XMLWriter writer = new XMLWriter(new FileWriter(filePath)); 
		return writer;
		
	}
	
}
