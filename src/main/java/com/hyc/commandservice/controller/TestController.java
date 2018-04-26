package com.hyc.commandservice.controller;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EhCacheCacheManager ehcacheManager;

    @RequestMapping("/{name}")
    public String hello(@PathVariable("name") String name) {
        if (ehcacheManager == null) {
            logger.error("-------------cache init error");
        } else {
            logger.info("--------------cache init success");
        }
        logger.debug("hello :{}", name);
        return "hello";
    }
}
