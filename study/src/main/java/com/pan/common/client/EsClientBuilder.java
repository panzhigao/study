package com.pan.common.client;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author 作者
 * @version 创建时间：2018年4月3日 上午11:37:54
 * 类说明
 */
public class EsClientBuilder {
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 9200;
	private static final String SCHEMA = "http";
	private static HttpHost http = new HttpHost(HOST, PORT,SCHEMA);
    private static RestHighLevelClient client;

    public RestHighLevelClient  build(){
        client = new RestHighLevelClient(RestClient.builder(http).setDefaultHeaders(new Header[]{}));
        return client;
    }
	
    public  void close() {
        if (client != null) {
            try {
            	client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
            	try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
    }
}
