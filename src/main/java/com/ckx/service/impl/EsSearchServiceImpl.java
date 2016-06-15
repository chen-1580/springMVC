package com.ckx.service.impl;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ckx.common.component.IEsSearchComp;
import com.ckx.model.EsSearchModel;
import com.ckx.service.IEsSearchService;

@Service
public class EsSearchServiceImpl implements IEsSearchService {

	@Autowired
	private IEsSearchComp searchComp;

	public String search(String matchKey, String matchValue) {
		MatchQueryBuilder builder = QueryBuilders.matchQuery(matchKey, matchValue);
		EsSearchModel model = new EsSearchModel(new String[] { "17chufa" }, new String[] { "b2c_travel_goods" });
		model.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		model.setQueryBuilder(builder); 
		model.setPageSize(5);
		return searchComp.search(model);
	}
}
