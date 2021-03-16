package com.chen.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ElasticsearchProperties。
 *
 * @author xinhaichen
 */
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchProperties {

    /**
     * clusterName。
     */
    private String clusterName;

    /**
     * host。
     */
    private String host;

    /**
     * port。
     */
    private Integer port;

    /**
     * username。
     */
    private String username;

    /**
     * password。
     */
    private String password;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
