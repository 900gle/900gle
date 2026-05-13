package com.doo.aqqle.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.List;
public class Client {

    @Value("#{'${elasticsearch.host}'.split(',')}")
    protected List<String> hosts;

    @Value("${elasticsearch.port}")
    protected int port;

    @Value("${elasticsearch.id}")
    protected String id;

    @Value("${elasticsearch.password}")
    protected String password;

    @Bean
    protected RestClient getHttpClient(){
        // Create the low-level client
        RestClient httpClient = RestClient.builder(
                new HttpHost(hosts.get(0), port)
        ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(getCredentialsProvider());
            }
        }).build();
        return httpClient;
    }

    protected CredentialsProvider getCredentialsProvider() {
        CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(id, password));
        return credentialsProvider;
    }
}
