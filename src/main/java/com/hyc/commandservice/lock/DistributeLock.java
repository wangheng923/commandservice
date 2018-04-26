package com.hyc.commandservice.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: wangheng2
 * @Date: 2018年04月25日
 * @Modified By:
 */
public interface DistributeLock {

    boolean tryLock(final String key);

    boolean tryLock(final String key, long time);

    boolean tryLock(final String key, long time, int retry);

    long releaseLock(final String key);

}
