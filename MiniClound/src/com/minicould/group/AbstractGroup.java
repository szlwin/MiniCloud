package com.minicould.group;

import java.io.InputStream;

import com.minicould.clound.node.AbstractNode;
import com.minicould.common.data.CloundData;
import com.minicould.common.desc.Desc;
import com.minicould.common.result.Result;
import com.minicould.exception.CloundException;
import com.minicould.node.Group;

public abstract class AbstractGroup extends AbstractNode implements Group{

	public void execute(CloundData param) throws CloundException {
		// TODO Auto-generated method stub
		
	}

	public Result executeV(CloundData param) throws CloundException {
		// TODO Auto-generated method stub
		return null;
	}

	public void init() throws CloundException {
		// TODO Auto-generated method stub
		
	}

	public void init(InputStream stream) throws CloundException {
		// TODO Auto-generated method stub
		
	}
	
	public void remove() throws CloundException {
		// TODO Auto-generated method stub
		
	}
	
	public Desc getDesc(){
		return null;
	}

}
