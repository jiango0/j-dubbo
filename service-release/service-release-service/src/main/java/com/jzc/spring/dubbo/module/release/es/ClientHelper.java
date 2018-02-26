package com.jzc.spring.dubbo.module.release.es;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHelper {


	private static final Logger logger = LoggerFactory.getLogger(ClientHelper.class);
	private static Settings settings;

	private static Map<String, Client> clientMap = new ConcurrentHashMap<String, Client>();

	private static List<InetSocketTransportAddress> ips;



	static {
		logger.info("init es configs start");
		init();
		logger.info("init es configs end");
	}

	/**
	 * 获得配置文件
	 * @param filePath
	 * @return
	 */
	public static InputStream getClassPathStream(String filePath) {
		return ClientHelper.class.getClassLoader().getResourceAsStream(filePath);
	}
	
	/**
	 * 初始化
	 */
	public static void init() {
		//加载文件
		Properties prop = new Properties();
		try {
			prop.load(getClassPathStream("es.properties"));
		} catch (Exception e) {
			logger.error("加载es.properties:",e);
			throw new RuntimeException(e);
		}
		//初始化es环境
		try{
			Settings.Builder settingsBuilder = Settings.builder();
			ips = new ArrayList<>();
			String key;
			String value;
			for (Map.Entry<Object, Object> entry : prop.entrySet()) {
				key = String.valueOf(entry.getKey());
				value = String.valueOf(entry.getValue());
				if (StringUtils.isEmpty(key))
					continue;
				if (key.startsWith("es.ip")) {
					// 添加client
					ips.add(getAddress(value));
				} else if (key.startsWith("client.transport") || key.startsWith("cluster")) {
					// 添加settings
					settingsBuilder.put(key, value);
				}
			}
			/*settingsBuilder.put("transport.type", "netty3");
			settingsBuilder.put("http.type", "netty3");*/
			settings = settingsBuilder.build();
			addClient(settings);
		}catch (Exception e) {
			logger.error("初始化es配置失败:",e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得连接地址
	 * @param address
	 * @return
	 */
	private static InetSocketTransportAddress getAddress(String address) {
		String host = address.substring(0, address.indexOf(":"));
		int port = Integer.parseInt(address.substring(address.indexOf(":") + 1));
		try {
			return new InetSocketTransportAddress(InetAddress.getByName(host), port);
		} catch (Exception e) {
			logger.error("get es ip fail:",e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得连接
	 * @return
	 */
	public static Client getClient() {
		return clientMap.get(settings.get("cluster.name"));
	}

	/**
	 * 设置连接
	 * @param setting
	 */
	public static void addClient(Settings setting) {
		PreBuiltTransportClient client = null;
		try {
			client = new PreBuiltTransportClient(settings);
			for (InetSocketTransportAddress ip : ips) {
				client.addTransportAddress(ip);
			}
		} catch (Exception e) {
			logger.error("set client link fail:",e);
			throw new RuntimeException(e);
		}
		clientMap.put(setting.get("cluster.name"), client);
	}
}
