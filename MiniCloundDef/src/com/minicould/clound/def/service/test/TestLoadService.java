package com.minicould.clound.def.service.test;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.util.IndexedDocumentFactory;

import com.minicould.common.data.ServiceData;
import com.minicould.common.result.ServiceResult;
import com.minicould.exception.CloundException;
import com.minicould.service.AbstractService;

public class TestLoadService extends AbstractService{

	public ServiceResult execute(ServiceData param) throws CloundException {
		IndexedDocumentFactory IndexedDocumentFactory = new IndexedDocumentFactory();
		//System.out.println(param.getValue("name"));
		//System.out.println("test ok");
		ServiceResult serviceResult = new ServiceResult();
		serviceResult.setSuccess(true);
		serviceResult.setValue("Load " + param.getValue("name"));
		return serviceResult;
		//return null;
	}
	public void executeV(ServiceData param) throws CloundException {
		
		Map map = new HashMap();
		System.out.println(param.getValue("name"));
	}
	public void remove() throws CloundException {
		// TODO Auto-generated method stub
		
	}
}
