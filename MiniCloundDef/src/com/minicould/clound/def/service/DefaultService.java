package com.minicould.clound.def.service;

import com.minicould.common.data.ServiceData;
import com.minicould.common.result.ServiceResult;
import com.minicould.exception.CloundException;
import com.minicould.service.AbstractService;

public class DefaultService extends AbstractService{

	public ServiceResult execute(ServiceData param) throws CloundException {
		ServiceResult serviceResult = new ServiceResult();
		serviceResult.setSuccess(true);
		serviceResult.setValue("Hello " + param.getValue("name"));
		return serviceResult;
	}

	public void remove() throws CloundException {
		// TODO Auto-generated method stub
		
	}
}
