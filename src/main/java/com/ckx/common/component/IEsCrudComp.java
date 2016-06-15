package com.ckx.common.component;

import java.util.List;

import com.ckx.model.EsBaseModel;
import com.ckx.model.EsModifyModel;
import com.ckx.model.EsPair;

public interface IEsCrudComp {

	public String get(EsBaseModel model);
	
	/**
	 * Es新增or更新方法
	 * 如果参数对象包含Id，则更新，否则新增
	 * @param ecm 插入or更新对象
	 * @return 包含插入(更新)成功后的回执数据
	 * 	example： {"id":"my_id","index":"my_index","type":"my_type"}
	 */
	public String save(EsModifyModel model);
	
	public void delete(EsBaseModel model);

	public String update(EsModifyModel model);
	
	@SuppressWarnings("rawtypes")
	public String bulk(List<EsPair> models);
	
}
