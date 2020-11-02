package com.poc.outageEventAlerts.config;


import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  @Value("${socket.read.timeout}")
  private int readTimeOut;

  @Value("${socket.connect.timeout}")
  private int connectTimeout;

  @Value("${socket.connectionRequestTimeout.timeout}")
  private int connectionRequestTimeout;

  @Value("${maxConnectionPerHost}")
  private int maxConnectionPerHost;

  @Value("${maxConnection}")
  private int maxConnection;

  @Bean(name = "restTemplate")
  public RestTemplate restTemplate() {

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(connectTimeout);
    factory.setReadTimeout(readTimeOut);
    factory.setConnectionRequestTimeout(connectionRequestTimeout);
    PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
    connManager.setMaxTotal(maxConnection);
    connManager.setDefaultMaxPerRoute(maxConnectionPerHost);
    CloseableHttpClient httpClient = HttpClients.custom().useSystemProperties().setConnectionManager(connManager).build();
    factory.setHttpClient(httpClient);

    RestTemplate restTemplate = new RestTemplate(factory);

    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

    return restTemplate;
  }

}

