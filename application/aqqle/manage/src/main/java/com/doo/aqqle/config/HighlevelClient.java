package com.doo.aqqle.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;
import java.util.List;

@Configuration
public class HighlevelClient extends Client{
    @Bean
    public RestHighLevelClient getHighClient() throws UnknownHostException {

// Create the HLRC
        RestHighLevelClient high_client = new RestHighLevelClientBuilder(getHttpClient())
                .setApiCompatibilityMode(true)
                .build();
        return high_client;
    }
}
