package com.ckx.model;

import java.util.Map;

public class EsModifyModel extends EsBaseModel{

	private Map<String, Object> content;
	
	public EsModifyModel(String index, String type, String id, Map<String, Object> content) {
		super(index, type, id);
		this.content = content;
	}

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

}
