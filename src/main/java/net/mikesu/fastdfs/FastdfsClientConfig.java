package net.mikesu.fastdfs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

public class FastdfsClientConfig {
	
	public static final int DEFAULT_CONNECT_TIMEOUT = 5; // second
	public static final int DEFAULT_NETWORK_TIMEOUT = 30; // second
	
	private int connectTimeout = DEFAULT_CONNECT_TIMEOUT * 1000;
	private int networkTimeout = DEFAULT_NETWORK_TIMEOUT * 1000;
	
	private int maxTotal = 20;
	
	private boolean testOnBorrow = false;
	
	private int maxWaitMillis = connectTimeout;
	
	private List<String> trackerAddrs = new ArrayList<String>();
	
	public FastdfsClientConfig() {
		
	}
	
	public FastdfsClientConfig(String confFile) throws ConfigurationException {
		if(confFile.startsWith("classpath:")){
			confFile = confFile.substring(10);
		}
		Configuration config = new PropertiesConfiguration(confFile);
		this.connectTimeout = config.getInt("connectTimeout", DEFAULT_CONNECT_TIMEOUT)*1000;
		this.networkTimeout = config.getInt("connectTimeout", DEFAULT_NETWORK_TIMEOUT)*1000;
		//TODO
		List<Object> trackerServers = config.getList("tracker_server");
		for(Object trackerServer:trackerServers){
			trackerAddrs.add((String)trackerServer);
		}
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getNetworkTimeout() {
		return networkTimeout;
	}

	public void setNetworkTimeout(int networkTimeout) {
		this.networkTimeout = networkTimeout;
	}

	public List<String> getTrackerAddrs() {
		return trackerAddrs;
	}

	public void setTrackerAddrs(List<String> trackerAddrs) {
		this.trackerAddrs = trackerAddrs;
	}
	
	private GenericKeyedObjectPoolConfig createPool(){
		GenericKeyedObjectPoolConfig poolConfig = new GenericKeyedObjectPoolConfig();
		poolConfig.setMaxTotal(maxTotal);
		poolConfig.setJmxEnabled(false);
		poolConfig.setTestOnBorrow(testOnBorrow);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		return poolConfig;
	}

	public GenericKeyedObjectPoolConfig getTrackerClientPoolConfig(){
		return this.createPool();
	}
	

	public GenericKeyedObjectPoolConfig getStorageClientPoolConfig(){
		return this.createPool();
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}
}
