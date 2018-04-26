package com.hyc.commandservice.lock;

/**
 * @Description:
 * @Author: wangheng2
 * @Date: 2018年04月26日
 * @Modified By:
 */

public class ZookeeperDistributeLock implements DistributeLock {
    @Override
    public boolean tryLock(String key) {
        return false;
    }

    @Override
    public boolean tryLock(String key, long time) {
        return false;
    }

    @Override
    public boolean tryLock(String key, long time, int retry) {
        return false;
    }

    @Override
    public long releaseLock(String key) {
        return 0;
    }
}
