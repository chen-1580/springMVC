package com.ckx.common.component.impl;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Component;

import com.ckx.common.component.EsBaseComp;
import com.ckx.common.component.IEsSearchComp;
import com.ckx.model.EsSearchModel;

@Component
public class EsSearchCompImpl extends EsBaseComp implements IEsSearchComp{
	
	public String search(EsSearchModel model) {
		SearchResponse response =  buildSearchRequest(model).get();
		String result = buildResultStr(response);
		return result;
	}

	public String multiSearch(List<EsSearchModel> models) {
		MultiSearchRequestBuilder builder = client.prepareMultiSearch();
		List<String> lists = new ArrayList<String>();
		for (EsSearchModel model : models) {
			builder.add(buildSearchRequest(model));
		}
		MultiSearchResponse response = builder.get();
		for (Item item : response) {
			String result = buildResultStr(item.getResponse());
			if(result != null){
				lists.add(result);
			}
		}
		System.out.println("start--------------------------------");
		System.out.println(lists.toString());
		System.out.println("end--------------------------------");
		return lists.toString();
	}
	
	private SearchRequestBuilder buildSearchRequest(EsSearchModel model) {
		SearchRequestBuilder builder = client.prepareSearch(model.getIndices()).setTypes(model.getTypes())
				.setSearchType(model.getSearchType()).setQuery(model.getQueryBuilder())
				.setPostFilter(model.getQueryFilter()).setFrom(model.getPageIndex()).setSize(model.getPageSize())
				.setExplain(model.getExplain());
		return builder;
	}

	private String buildResultStr(SearchResponse response) {
		List<String> results = new ArrayList<String>();
		SearchHits searchHits = response.getHits();
		if(searchHits.getTotalHits() == 0){
			return null;
		}
		for (SearchHit hit : searchHits) {
			results.add(hit.sourceAsString());
		}
		return results.toString();
	}

}
