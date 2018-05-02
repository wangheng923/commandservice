package com.hyc.commandservice.configuration;

import com.hyc.commandservice.lock.DistributeLock;
import com.hyc.commandservice.lock.RedisDistributeLock;
import com.hyc.commandservice.lock.ZookeeperDistributeLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 分布式锁配置
 * @Author: wangheng2
 * @Date: 2018年04月26日
 * @Modified By:
 */
@Configuration
@ConfigurationProperties(prefix = "distribute.lock")
public class DistributeLockConfiguration {

    /***
     * 锁实现方式, 默认redis
     */
    private String way = "redis";

    /***
     * 锁过期时间（毫秒）, 默认5秒
     */
    private long expireTime = 5000;

    /***
     * 重试次数, 默认3次
     */
    private int retryTime = 3;

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(int retryTime) {
        this.retryTime = retryTime;
    }

    @Bean
    @ConditionalOnProperty(name = "distribute.lock.way", havingValue = "redis", matchIfMissing = true)
    public DistributeLock getRedisDistributeLock() {
        RedisDistributeLock redisDistributeLock = new RedisDistributeLock();
        redisDistributeLock.setExpireTime(this.expireTime);
        redisDistributeLock.setRetryTime(this.retryTime);
        return redisDistributeLock;
    }

    @Bean
    @ConditionalOnProperty(name = "distribute.lock.way", havingValue = "zookeeper")
    public DistributeLock getZookeeperDistributeLock() {
        return new ZookeeperDistributeLock();
    }
}
