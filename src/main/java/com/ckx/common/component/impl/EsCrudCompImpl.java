package com.ckx.common.component.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ckx.common.Constant;
import com.ckx.common.component.EsBaseComp;
import com.ckx.common.component.IEsCrudComp;
import com.ckx.model.EsBaseModel;
import com.ckx.model.EsModifyModel;
import com.ckx.model.EsPair;

@Component
public class EsCrudCompImpl extends EsBaseComp implements IEsCrudComp{

	private static final Logger LOGGER = Logger.getLogger(EsCrudCompImpl.class);
	
	/**
	 * 根据ID获取查询信息
	 * @param ebm
	 * @return 该ID对应查询结果
	 */
	public String get(EsBaseModel model){
		GetResponse response = null;
		try {
			response = client.prepareGet(model.getIndex(), model.getType(), model.getId()).get();
		} finally {
			client.close();
		}
		return JSON.toJSON(response.getSource()).toString();
	}
	
//	protected JSONArray queryAll(EsSearchModel model){
//		JSONArray hits;
//		Client client = getConnection();
//		try {
//			SearchRequestBuilder sbuilder = client
//					.prepareSearch(model.getIndex()) // index name
//					.setTypes(model.getType()) // type name
//					.setSearchType(model.getSearchType())
//					.setQuery(model.getQueryBuilder());
//			SearchResponse sr = sbuilder.execute().actionGet();
//			hits = JSON.parseObject(sr.toString()).getJSONObject("hits").getJSONArray("hits");
//		} finally {
//			client.close();
//			client = null;
//		}
//		return hits;
//	}
	
	/**
	 * Es新增or更新方法
	 * 如果参数对象包含Id，则更新，否则新增
	 * @param ecm 插入or更新对象
	 * @return 包含插入(更新)成功后的回执数据
	 * 	example： {"id":"my_id","index":"my_index","type":"my_type"}
	 */
	public String save(EsModifyModel model){
		EsBaseModel ebm;
		try {
			IndexRequestBuilder builder = buildIstBuilder(client, model);
			IndexResponse response = builder.get();
			ebm = new EsBaseModel(response.getIndex(), response.getType(), response.getId());
		} finally {
			client.close();
		}
		return JSON.toJSON(ebm).toString();
	}
	
	public void delete(EsBaseModel model){
		try {
			DeleteRequestBuilder builder = buildDelBuilder(client, model);
			DeleteResponse response = builder.get();
			if(!response.isFound()){
				LOGGER.warn(response.getIndex() + "索引已删除");
			}
			LOGGER.info(response.getIndex() + "->" + response.getType() + "->" + response.getId() + "已删除");
		} finally {
			client.close();
		}
	}
	

	public String update(EsModifyModel model) {
		try {
			UpdateRequestBuilder builder = buildUpdBuilder(client, model);
			client.update(builder.request()).get();
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		} catch (ExecutionException e) {
			LOGGER.error(e.getMessage(),e);
			e.printStackTrace();
		} finally {
			client.close();
		}
		return null;
	}
	
	
	@SuppressWarnings({ "rawtypes" })
	public String bulk(List<EsPair> models) {
		BulkRequestBuilder builder = client.prepareBulk();
		for (EsPair pair : models) {
			if(Constant.ES_INSERT.equalsIgnoreCase(pair.getName())){
				EsModifyModel model = (EsModifyModel) pair.getValue();
				IndexRequestBuilder request = buildIstBuilder(client, model);
				builder.add(request);
			}else if(Constant.ES_UPDATE.equalsIgnoreCase(pair.getName().toUpperCase())){
				EsModifyModel model = (EsModifyModel) pair.getValue();
				UpdateRequestBuilder request = buildUpdBuilder(client, model);
				builder.add(request);
			}else if(Constant.ES_DELETE.equalsIgnoreCase(pair.getName().toUpperCase())){
				EsBaseModel model = (EsBaseModel) pair.getValue();
				DeleteRequestBuilder request = buildDelBuilder(client, model);
				builder.add(request);		
			}
		}
		BulkResponse response = builder.get();
		String info = response.buildFailureMessage();
		if(response.hasFailures()){
			LOGGER.error(info);
			return "FALURE";
		}
		return "SUCCESS";
	}
	
	private DeleteRequestBuilder buildDelBuilder(Client client, EsBaseModel model) {
		DeleteRequestBuilder request = client
				.prepareDelete(model.getIndex(), model.getType(), model.getId());
		return request;
	}

	private UpdateRequestBuilder buildUpdBuilder(Client client, EsModifyModel model) {
		UpdateRequestBuilder request = client
			.prepareUpdate(model.getIndex(), model.getType(), model.getId())
			.setDoc(model.getContent());
		return request;
	}

	private IndexRequestBuilder buildIstBuilder(Client client, EsModifyModel model) {
		IndexRequestBuilder request = client
			.prepareIndex(model.getIndex(), model.getType(), model.getId() == null ? null : model.getId())
			.setSource(model.getContent());
		return request;
	}
	
}
