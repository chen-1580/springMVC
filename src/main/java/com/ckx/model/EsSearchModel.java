package com.ckx.model;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class EsSearchModel{

	private String[] indices;			//查询数据库数组
	
	private String[] types;				//查询表数组
	
	private SearchType searchType = SearchType.DFS_QUERY_THEN_FETCH;	//类型条件
	
	private QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();	//查询条件
	
	private QueryBuilder queryFilter;	//查询过滤条件
			
	private int pageIndex = 0;			//分页页码
	
	private int pageSize = 10;			//分页大小
	
	private boolean explain = true;		//是否解释
	
	private float boost = 1f;
	
	private String sortField;
	
	private String sortType = "desc";
	
	private boolean highLight = false;
	

	public String[] getIndices() {
		return indices;
	}

	public void setIndices(String... indices) {
		this.indices = indices;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String... types) {
		this.types = types;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	/**设置查询条件 
	 * <pre>
	 * QUERY_THEN_FETCH：	
	 * 		先向所有的shared发出请求，各分片只返回排序和排名相关的信息(不包括document)
	 * 		然后按照各shard返回的分数进行排序并取前size个文档，之后再去相关的shared取document
	 * 		这对于有许多shareds的所有来说很便利，返回结果不会有重复
	 * QUERY_AND_FETCH：
	 * 		最快的处理方式，它向索引的所有分片shareds都发出查询请求，每个shared分别返回已定数量的结果
	 * DFS_QUERY_THEN_FETCH：
	 * DFS_QUERY_AND_FETCH：
	 * SCAN：
	 * 		当进行了没有进行任何排序的检索是，执行浏览操作。
	 * COUNT:
	 * 		结算结果的数量。
	 * </pre>
	 */
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

	public QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	/**
	 * <pre>
	 * 匹配查找：
	 * 	QueryBuilder.MatchQuery(字段名称，包含的字段值)			
	 * 	--例：QueryBuilder.MatchQuery("userName","陈")：匹配“userName”字段包含“陈”的文档
	 * 匹配文档中所有字段：
	 * 	QueryBuilder.MatchAllQuery()
	 * 匹配多个包含匹配元素的字段：
	 * 	QueryBuilder.multiMatchQuery(匹配字段，字段1，字段2...)
	 * 	--例：QueryBuilder.multiMatchQuery("赵","userName","address")	//匹配“userName”,“address”中包含“赵”的
	 * 组合条件查询
	 * 	QueryBuilders.boolQuery()
	 * 	--例：QueryBuilders.boolQuery()
	 * 			.must(termQuery("userName","赵"))
	 * 			.must(termQuery("userName","钱"))
	 * 			.mustNot(termQuery("userName","孙"))
	 * 			.should(termQuery("gender","男"))
	 * 精确查找
	 * 	QueryBuilders.termQuery(字段名称，查找字段值);
	 * 	--例：QueryBuilders.termQuery("userName","陈"); 
	 * 通配符查找
	 * 	QueryBuilders.wildcardQuery(字段名称，包含通配符的字段)
	 * 	--例：QueryBuilders.wildcardQuery("userName","陈?")
	 * 	--含两个通配符:?,* 
	 * 		?:匹配1个字符串
	 * 		*:匹配多个字符串
	 * 根据lucene查询语法查找
	 * 	QueryBuilders.queryString("luceneGrannar")
	 * 	--例：QueryBuilders.queryString("+赵-钱") 			//包含赵但不包含钱的元素
	 * 模糊查找
	 * 	QueryBuilders.moreLikeThis(字段名称)
	 * 	--例：QueryBuilders.moreLikeThis("userName")			//匹配文档(字段)
	 * 		.likeText("一起出发")			//匹配元素
	 * 		.minTermFreq(3)			//出现最少次数
	 * </pre>
	 * @param queryBuilder :
	 */
	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	public QueryBuilder getQueryFilter() {
		return queryFilter;
	}

	public void setQueryFilter(QueryBuilder queryFilter) {
		this.queryFilter = queryFilter;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean getExplain() {
		return explain;
	}

	public void setExplain(boolean explain) {
		this.explain = explain;
	}
	
	public float getBoost() {
		return boost;
	}

	public void setBoost(float boost) {
		this.boost = boost;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public boolean getHighLight() {
		return highLight;
	}

	public void setHighLight(boolean highLight) {
		this.highLight = highLight;
	}

	@SuppressWarnings("unused")
	private EsSearchModel(){}

	public EsSearchModel(String[] indices, String[] types) {
		this.indices = indices;
		this.types = types;
	}
	
}
