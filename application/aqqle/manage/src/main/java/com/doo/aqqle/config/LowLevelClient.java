package com.doo.aqqle.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
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
public class LowLevelClient extends Client {

    @Bean
    public ElasticsearchClient getLowClient() throws UnknownHostException {
        // Create the Java API Client with the same low level client
        ElasticsearchTransport transport = new RestClientTransport(
                getHttpClient(),
                new JacksonJsonpMapper()
        );

        ElasticsearchClient low_client = new ElasticsearchClient(transport);
        return low_client;
    }

}
