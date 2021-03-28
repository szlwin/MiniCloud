package com.minicould.clound.node;

import java.util.List;

import com.minicould.common.desc.Desc;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.util.CloundUtil;
import com.minicould.exception.CloundException;
import com.minicould.node.CNode;

public abstract class AbstractCNode<E,V> extends AbstractNode implements CNode<E,V>{

	public void doNext(String name) throws CloundException{
		List<Desc> childDesc = desc.getChildDesc();
		
		for(int i = 0 ; i < childDesc.size();i++){
			CloundUtil.execCmd(name, (SimpleDesc) childDesc.get(i));
		}
	}
}
