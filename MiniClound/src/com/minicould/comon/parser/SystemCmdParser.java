package com.minicould.comon.parser;

import com.minicould.common.data.CompantData;

public class SystemCmdParser implements CmdParser{

	public CompantData parser(String cmd) {
		CompantData compantData = new CompantData();
		cmd = cmd.trim();
		String cmdArray[] = cmd.split(" ");
		
		String tmpParam = null;
		
		for(int i = 0; i < cmdArray.length;i++){
			String tempStr = cmdArray[i].trim();
			if(tempStr != null && !"".equals(tempStr)){
				if(cmdArray[i].startsWith("-")){
					tmpParam = cmdArray[i].substring(1);
					compantData.setValue(tmpParam, null);
				}else{
					if(tmpParam !=null && !"".equals(tmpParam)){
						compantData.setValue(tmpParam, cmdArray[i]);
					}
				}
			}
		}
		return compantData;
	}

}
