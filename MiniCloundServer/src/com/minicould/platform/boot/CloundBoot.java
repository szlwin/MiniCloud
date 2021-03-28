package com.minicould.platform.boot;

import com.minicould.clound.manager.CloundManager;
import com.minicould.platform.service.CloundService;

public class CloundBoot {

	private static CloundService cloundService;
	
	public static void main(String args[]){
		
		if(args[0].equals("start")){
			try {

				CloundManager.getInstance().config(args[1]);
				cloundService =  new CloundService();
				cloundService.init();
				cloundService.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
