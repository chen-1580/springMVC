package com.ckx.model;

import org.elasticsearch.common.Nullable;

public class EsBaseModel {

	private String index;
	
	private String type;
	
	private String id;
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@SuppressWarnings("unused")
	private EsBaseModel(){
		super();
	};
	
	public EsBaseModel(String index, String type,@Nullable String id) {
		this.index = index;
		this.type = type;
		this.id = id;
	}
}
