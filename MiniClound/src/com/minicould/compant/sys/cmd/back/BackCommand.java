package com.minicould.compant.sys.cmd.back;

import java.util.HashMap;
import java.util.Map;

import com.minicould.clound.manager.CloundContext;
import com.minicould.common.contanst.CloundConstanst;
import com.minicould.common.data.CompantData;
import com.minicould.common.desc.CloundDesc;
import com.minicould.common.desc.MoudleDesc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.result.CompantResult;
import com.minicould.common.util.CloundUtil;
import com.minicould.compant.AbstractSystemCompant;
import com.minicould.exception.CloundException;
import com.minicould.node.Clound;
import com.minicould.node.Moudle;

public class BackCommand extends AbstractSystemCompant{
			
	private static Map<String,SimpleDesc> descMap = new HashMap<String,SimpleDesc>();
	
	public CompantResult execute(CompantData param) throws CloundException {
		
		String name = (String) param.getValue("name");
		String version = (String) param.getValue("version");
		int type = Integer.parseInt((String) param.getValue("type"));
		
		String mn = (String) param.getValue("mn");
		String mv = (String) param.getValue("mv");
		if(mn == null || mv == null){
			mn = "";
			mv = "";
		}
		
		SimpleDesc sDesc = null;
		
		String serName = mn+mv+"/"+name+version;
		
		CompantResult compantResult = new CompantResult();
		
		if(type == CloundConstanst.TYPE_SYSCMD){
			compantResult.setSuccess(false);
			compantResult.setValue("The system command can't be back or restore!");
		}
		
		if(this.name.equals("back")){
			if(type == CloundConstanst.TYPE_SERVICE){
				sDesc = CloundUtil.getSimpleDesc(serName, null, type);
			}else{
				sDesc = CloundUtil.getSimpleDesc(name, version, type);
			}
			return back(name,version,mn,mv,sDesc);
		}
		
		if(this.name.equals("restore")){
			return restore(name,version,mn,mv);
		}

		
		compantResult.setSuccess(false);
		return compantResult;
	}
	
	private CompantResult back(String name,String version,String mn,String mv,SimpleDesc sDesc){
		CloundContext cloundContext = CloundUtil.getCurrentContext();
		String key = cloundContext.getCname()+cloundContext.getCversion()+"/"+mn+mv+"/"+name+version;
		descMap.put(key, sDesc);

		CompantResult compantResult = new CompantResult();
		compantResult.setSuccess(true);
		return compantResult;
	}
	
	private CompantResult restore(String name,String version,String mn,String mv) throws CloundException{
		CloundContext cloundContext = CloundUtil.getCurrentContext();
		String key = cloundContext.getCname()+cloundContext.getCversion()+"/"+mn+mv+"/"+name+version;
		SimpleDesc sDesc = descMap.get(key);
		
		if(type == CloundConstanst.TYPE_SERVICE){
			Moudle moudle = (Moudle) CloundUtil.getNode(mn, mv, CloundConstanst.TYPE_MOUDLE);
			moudle.load(MoudleDesc.TYPS_SERVICE, sDesc);
		}else{
			Clound clound = (Clound) CloundUtil.getNode(cloundContext.getCname(), cloundContext.getCversion(), CloundConstanst.TYPE_CLOUND);
			clound.load(parseType(type), sDesc);
		}
		
		CompantResult compantResult = new CompantResult();
		compantResult.setSuccess(true);
		descMap.remove(key);
		return compantResult;
	}

	private int parseType(int type){
		switch(type){
			case CloundConstanst.TYPE_COMPANT:
				return CloundDesc.TYPS_COMPANT;
			case CloundConstanst.TYPE_MOUDLE:
				return CloundDesc.TYPS_MOUDLE;			
		}
		
		return -1;
	}

	public void remove() throws CloundException {
		// TODO Auto-generated method stub
		
	}
}
