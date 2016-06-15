package com.ckx.model;

public class EsPair<T extends EsBaseModel> {
	
	private String name;
	private T value;
	
	@SuppressWarnings("unused")
	private EsPair(){}
	
	public EsPair(String name, T value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
