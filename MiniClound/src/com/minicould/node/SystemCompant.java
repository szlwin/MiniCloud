package com.minicould.node;

import com.minicould.common.data.CmdInfo;
import com.minicould.common.desc.SimpleDesc;
import com.minicould.common.result.CompantResult;
import com.minicould.exception.CloundException;

public interface SystemCompant extends Compant{

	public CompantResult execute(CmdInfo cmdInfo) throws CloundException;
	
	public CompantResult execute(SimpleDesc desc) throws CloundException;
}
