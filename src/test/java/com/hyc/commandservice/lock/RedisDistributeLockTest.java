package com.hyc.commandservice.lock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertNotNull;

/**
 * @Description:
 * @Author: wangheng2
 * @Date: 2018年05月02日
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDistributeLockTest {

    @Autowired
    private DistributeLock distributeLock;

    @Test
    public void tryLock() throws Exception {
        assertNotNull(distributeLock);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    boolean testlock = distributeLock.tryLock("testlock", 5000, 2);
                    if (testlock) {
                        System.out.println(Thread.currentThread().getName() + "获得锁");
                        Random random = new Random();
                        int delay = random.nextInt(5);

                        Thread.sleep(delay * 1000);
                        System.out.println(Thread.currentThread().getName() + "释放锁");
                        distributeLock.releaseLock("testlock");
                    } else {
                        System.out.println(Thread.currentThread().getName() + "加锁失败");
                    }
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + "中断");
                } finally {
                    countDownLatch.countDown();
                }
            }, "Thread-" + i).start();
        }
        countDownLatch.await();
    }

}