package com.ckx.enums;

public enum CrudSchema {

	INSERT, UPDATE, DELETE;
	
	public static void main(String[] args) {
		System.out.println(CrudSchema.INSERT.toString().getClass());
	}

}
