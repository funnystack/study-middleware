package com.funny.combo.tools.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfiguration {

    /**
     * 集群地址
     */
    @Value("${es.hostName}")
    private String hostName;
    /**
     * 端口
     */
    @Value("${es.port}")
    private Integer port;

    @Value("${es.scheme}")
    private String scheme;


    /****
     * 暂时用TransportClient调用
     * @return
     */
    @Bean
    public RestHighLevelClient client() {

        if(StringUtils.isEmpty(hostName) || port == null || StringUtils.isEmpty(scheme)) {
            return null;
        }

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostName, port, scheme))
                        .setMaxRetryTimeoutMillis(10000)
                        .setFailureListener(new RestClient.FailureListener() {
                            @Override
                            public void onFailure(HttpHost host) {
                                //todo 如果监听失败怎么办？发消息？

                            }
                        }).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                    @Override
                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                        return requestConfigBuilder.setSocketTimeout(100000);
                    }
                })
        );
        return client;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}
