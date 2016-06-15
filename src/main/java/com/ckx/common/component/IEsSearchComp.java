package com.ckx.common.component;

import java.util.List;

import com.ckx.model.EsSearchModel;

public interface IEsSearchComp {
	
	public String search(EsSearchModel model);

	public String multiSearch(List<EsSearchModel> model);
	
	
}
