package com.mydukan.elasticSearch.config;
 
import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ElasticSearchConfig extends AbstractFactoryBean {
   
	private RestHighLevelClient restHighLevelClient;
    
	@Value("${elasticsearch.host.port}")
	private String elasticSearchHost;
	
	@Value("${elasticsearch.host.port}")
	private int elasticSearchPort;
	
	@Override
    public Class getObjectType() {
        return RestHighLevelClient.class;
    }
    @Override
    protected RestHighLevelClient createInstance() throws Exception {
        try {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(elasticSearchHost, elasticSearchPort, "http")
                            //new HttpHost("localhost", 9201, "http")
                    )
            );
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return restHighLevelClient;
    }
    @Override
    public void destroy(){
        try {
            restHighLevelClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}