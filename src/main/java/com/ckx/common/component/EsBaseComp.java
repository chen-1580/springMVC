package com.ckx.common.component;

import org.elasticsearch.client.Client;
import org.springframework.stereotype.Component;

import com.ckx.common.ESConnection;

@Component
public class EsBaseComp {
	
	protected final Client client = ESConnection.INSTANCE.getConnect();
	
}
