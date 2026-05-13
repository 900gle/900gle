//package com.doo.aqqle.config;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.json.jackson.JacksonJsonpMapper;
//import co.elastic.clients.transport.ElasticsearchTransport;
//import co.elastic.clients.transport.rest_client.RestClientTransport;
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.RestHighLevelClientBuilder;
//
//import java.io.IOException;
//
//public class ClientTest {
//
//
//    public static void main(String[] args) {
//
//
//        // Create the low-level client
//        RestClient httpClient = RestClient.builder(
//                new HttpHost("localhost", 9200)
//        ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                    @Override
//                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                        return httpClientBuilder.setDefaultCredentialsProvider(getCredentialsProvider());
//                    }
//                }).build();
//
//
//
//
//
//// Create the HLRC
//        RestHighLevelClient hlrc = new RestHighLevelClientBuilder(httpClient)
//                .setApiCompatibilityMode(true)
//                .build();
//
//// Create the Java API Client with the same low level client
//        ElasticsearchTransport transport = new RestClientTransport(
//                httpClient,
//                new JacksonJsonpMapper()
//        );
//
//        ElasticsearchClient esClient = new ElasticsearchClient(transport);
//
//        try {
//            esClient.indices().create(c -> c.index("products"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static CredentialsProvider getCredentialsProvider(){
//        CredentialsProvider credentialsProvider =
//                new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials("elastic", "dlengus"));
//
//
//
//        return credentialsProvider;
//    }
//}
