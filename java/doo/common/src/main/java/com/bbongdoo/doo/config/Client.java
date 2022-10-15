package com.bbongdoo.doo.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class Client {

    @Value("#{'${elasticsearch.host}'.split(',')}")
    private List<String> hosts;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.id}")
    private String id;

    @Value("${elasticsearch.password}")
    private String password;

    @Bean
    public  RestHighLevelClient getClient() throws UnknownHostException {

        List<HttpHost> hostList = new ArrayList<>();
        for (String host : this.hosts) {
            hostList.add(new HttpHost(InetAddress.getByName(host), port, "http"));
        }

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        hostList.toArray(new HttpHost[hostList.size()])
                ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(getCredentialsProvider());
                    }
                }));
        return client;
    }

    public CredentialsProvider getCredentialsProvider(){
        CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(id, password));



        return credentialsProvider;
    }

}
