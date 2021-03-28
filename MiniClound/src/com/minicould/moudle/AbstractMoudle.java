package com.minicould.moudle;

import com.minicould.clound.node.AbstractCNode;
import com.minicould.common.data.MoudleData;
import com.minicould.common.data.ServiceData;
import com.minicould.common.desc.Desc;
import com.minicould.common.desc.MoudleDesc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.result.CompantResult;
import com.minicould.common.result.Result;
import com.minicould.common.util.CloundUtil;
import com.minicould.exception.CloundException;
import com.minicould.node.Moudle;
import com.minicould.node.Service;
public abstract class AbstractMoudle extends AbstractCNode<MoudleData,Result> implements Moudle{
	
	public void execute(MoudleData param) throws CloundException {
		executeV(param);	
	}

	public Result executeV(MoudleData param) throws CloundException {
		
		if(!CloundUtil.check(this.status)){
			CompantResult compantResult = new CompantResult();
			compantResult.setSuccess(false);
			compantResult.setErrorMsg("The node "+name+" "+version+" is not start!");
		}
		
		MoudleDesc mDesc = (MoudleDesc) desc;
		String cmd = param.getCmd();
		
		if("service".equals(cmd)){
			SimpleDesc serviceDesc = (SimpleDesc)mDesc.get(MoudleDesc.TYPS_SERVICE, param.getName(), param.getVersion());
			Service service = (Service) serviceDesc.getNodeObj();
			
			if(!CloundUtil.check(service.getStatus())){
				Result result = new Result();
				result.setSuccess(false);
				result.setErrorMsg("The node "+service.getName()+" "+service.getVersion()+" is not start!");
				return result;
			}
			
			return service.execute((ServiceData) param.getData());
			//try {
			//	Method method = service.getClass().getMethod("execute",ServiceData.class);
			//	return (ServiceResult) method.invoke(service, new Object[]{param.getData()});
			//} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
			//return service.execute((ServiceData)param.getData());
		}
		Result result = new Result();
		result.setSuccess(false);
		result.setErrorMsg("The cmd: "+cmd+" is can't execute for the moudle:"+this.name+" " + this.version);
		return result;
	}
	
	public void remove() throws CloundException {
		// TODO Auto-generated method stub
		
	}
	
	public synchronized void load(int type,Desc serviceDesc) throws CloundException{
		((MoudleDesc)desc).add(type, serviceDesc);
	}
}
