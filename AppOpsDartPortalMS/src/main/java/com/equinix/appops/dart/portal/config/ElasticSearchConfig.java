package com.equinix.appops.dart.portal.config;

import java.net.InetAddress;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class ElasticSearchConfig {

	@Value("${elasticsearch.host}")
	private String EsHost;

	@Value("${elasticsearch.port}")
	private int EsPort;

	@Value("${elasticsearch.clustername}")
	private String EsClusterName;

	@Bean(name = "elasticClient")
	public Client client() throws Exception {

		String hosts[] = EsHost.split(",");
		for(String host : hosts){
			System.out.println("Elastic host => "+ host);
		}
		Settings esSettings = Settings.builder()
				.put("cluster.name", EsClusterName)
				.put("client.transport.sniff", true)
				.build();
		
		TransportClient client = new PreBuiltTransportClient(esSettings);
		//client.addTransportAddress(new TransportAddress(InetAddress.getByName(EsHost), EsPort));
		if(hosts.length==1){
			client.addTransportAddress(new TransportAddress(InetAddress.getByName(hosts[0]), EsPort));
		}else if(hosts.length==5){
			client.addTransportAddresses(new TransportAddress(InetAddress.getByName(hosts[0]), EsPort)
					,new TransportAddress(InetAddress.getByName(hosts[1]), EsPort)
					,new TransportAddress(InetAddress.getByName(hosts[2]), EsPort));
		}else if(hosts.length==2){
			client.addTransportAddresses(new TransportAddress(InetAddress.getByName(hosts[0]), EsPort)
					,new TransportAddress(InetAddress.getByName(hosts[1]), EsPort));
		}
		
		System.out.println("Elastic client " + client.toString() /* + client.nodeName() */);
		return client;
	}
	
	@Bean(name = "restHighLevelClient")
	public RestHighLevelClient restHighLevelClient() throws Exception {

		String hosts[] = EsHost.split(",");
		for(String host : hosts){
			System.out.println("Elastic host => "+ host);
		}
		
		RestClientBuilder builder = null;
		if(hosts.length==1){
			builder = RestClient.builder(new HttpHost(InetAddress.getByName(hosts[0]), 9200, "http"));
		}else if(hosts.length==5){
			builder = RestClient.builder(new HttpHost(InetAddress.getByName(hosts[0]), 9200, "http"), new HttpHost(InetAddress.getByName(hosts[1]), 9200, "http"),
                        new HttpHost(InetAddress.getByName(hosts[2]), 9200, "http"));
		}else if(hosts.length==2){
			builder = RestClient.builder(new HttpHost(InetAddress.getByName(hosts[0]), 9200, "http"), new HttpHost(InetAddress.getByName(hosts[1]), 9200, "http"));
		}
		// Setting Other Properties In RestClientBuiler.
		setRestClientBuilderProperties(builder);
		
		// Creating RestHighLevelClient from RestClientBuiler.
		RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
		System.out.println("Elastic client " + restHighLevelClient.toString() /* + client.nodeName() */);
		return restHighLevelClient;
	}
	
	private void setRestClientBuilderProperties(RestClientBuilder builder) {
		builder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS)
		/*.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
			@Override
			public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
				return requestConfigBuilder.setSocketTimeout(100000);
			}
		})*/;
	}

}