package com.funny.combo.tools.common;

/***
 *  模版数据注入，类似JDBC，方便迁移
 *  @author smart
 */
public class ESTemplate {
    /**
     * 集群名称
     */
    private String clusterName;
    /**
     * 集群地址
     */
    private String hostName;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 连接池
     */
    private String poolSize;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
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

    public String getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(String poolSize) {
        this.poolSize = poolSize;
    }
}
