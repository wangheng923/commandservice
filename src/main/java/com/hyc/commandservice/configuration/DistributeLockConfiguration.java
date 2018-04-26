package com.hyc.commandservice.configuration;

import com.hyc.commandservice.lock.DistributeLock;
import com.hyc.commandservice.lock.RedisDistributeLock;
import com.hyc.commandservice.lock.ZookeeperDistributeLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: Redis分布式锁实现
 * @Author: wangheng2
 * @Date: 2018年04月26日
 * @Modified By:
 */
@Configuration
@ConfigurationProperties(prefix = "distribute.lock")
public class DistributeLockConfiguration {

    /***
     * 锁实现方式
     */
    private String way;

    /***
     * 锁过期时间（毫秒）
     */
    private long expireTime;

    /***
     * 重试次数
     */
    private int retryTime;

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
    @ConditionalOnProperty(name = "distribute.lock.way", havingValue = "redis")
    public DistributeLock getRedisDistributeLock() {
        RedisDistributeLock redisDistributeLock = new RedisDistributeLock();
        redisDistributeLock.setExpireTime(this.expireTime);
        redisDistributeLock.setRetryTime(this.retryTime);
        return redisDistributeLock;
    }

    @Bean
    @ConditionalOnProperty(name = "distribute.lock.way", havingValue = "zookeeper")
    public DistributeLock getZookeeperDistributeLock() {
        System.out.println("Zookeeper expireTime: " + expireTime + " retryTime:" + retryTime);
        return new ZookeeperDistributeLock();
    }
}
