package com.ckx.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * 提供连接ES集群方法
 * 考虑集群名称不变，集群可能横向拓展，
 * 所以集群名称在constant类中，node节点在配置文件
 * 配置文件在 classpath:elasticsearch.properties 中
 * 集群名称=集群节点1,集群节点2
 * @author ckx
 *
 */
public enum ESConnection {
		
	INSTANCE; // 定义一个枚举的元素，就代表es连接的一个实例

	private static final Logger LOGGER = Logger.getLogger(ESConnection.class);
	
	private Client client = null;
	
	
	/**
	 * es连接方法.
	 * @return
	 * @throws IOException 
	 */
	public Client getConnect() {
		try {
			Settings settings = buildSettingsParams().build();
			client = TransportClient.builder().settings(settings)
					.build().addTransportAddresses(buildNodesAddr());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return client;
	}
	
	/**
	 * 关闭ES连接
	 */
	public void close(){
		client.close();
	}
	
	// ---------------- 私有方法区 --------------------------
	/**
	 * 定义集群名称，设置集群环境方法
	 * @return
	 */
	private Builder buildSettingsParams() {
		Map<String, String> esParams = new HashMap<String, String>();
		esParams.put("cluster.name", Constant.CLUSTER);
		return Settings.settingsBuilder().put(esParams);
	}
	
	/**
	 * 读取es配置文件
	 * @return
	 * @throws IOException 
	 */
	private static Properties loadEsProperties() throws IOException  {
		Properties props = new Properties();
		InputStream is = ESConnection.class.getResourceAsStream("/elasticsearch.properties");
		props.load(is);
		return props;
	}

	/**
	 * 添加集群节点
	 * @return
	 * @throws IOException 
	 */
	private InetSocketTransportAddress[] buildNodesAddr() throws IOException{
		final Properties PROPS = loadEsProperties();
		String propNodes = PROPS.getProperty(Constant.CLUSTER);
		String[] nodes = propNodes.split(",");
		int nodeSize = nodes.length;
		InetSocketTransportAddress[] nodeAddrss = new InetSocketTransportAddress[nodeSize];
		for (int i = 0; i < nodeSize; i++) {
			String[] addrs = nodes[i].split(":");
			String addr = addrs[0];
			int port = Integer.parseInt(addrs[1]);
			nodeAddrss[i] = new InetSocketTransportAddress(InetAddress.getByName(addr), port);
		}
		return nodeAddrss;
	}

	// MAIN
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() {
				try {
					ESConnection esc = ESConnection.INSTANCE;
					System.out.println("------------" + esc.hashCode());
				} catch (Exception e) {
					LOGGER.error("其他异常", e);
				}
			}
		};
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(r);
			t.start();
		}
		

	};
		
	
}
