package com.hyc.commandservice.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Component
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PresenceChannelInterceptor.class);

    @Autowired
    private EhCacheCacheManager ehcacheManager;

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        if (ehcacheManager == null) {
            logger.error("-------------cache init error");
        } else {
            logger.info("--------------cache init success");
        }
//		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
//		// ignore non-STOMP messages like heartbeat messages
//		if (sha.getCommand() == null) {
//			return;
//		}
//		// 这里的sessionId和accountId对应HttpSessionIdHandshakeInterceptor拦截器的存放key
//		String sessionId = (String) sha.getSessionAttributes().get(ChatConsts.WEBSCOKET_SESSIONID);
//		String accountId = (String) sha.getSessionAttributes().get(ChatConsts.WEBSCOKET_USERNAME);
//		// 判断客户端的连接状态
//		switch (sha.getCommand()) {
//		case CONNECT:
//			connect(sessionId, accountId);
//			break;
//		case CONNECTED:
//			break;
//		case SEND:
//			break;
//		case DISCONNECT:
//			disconnect(sessionId, accountId, sha);
//			break;
//		default:
//			break;
//		}
    }

    /**
     * 连接成功缓存用户
     *
     * @param sessionId
     * @param accountId
     */
    private void connect(String sessionId, String accountId) {
        logger.debug(" STOMP Connect [accountId :" + accountId + ", sessionId: " + sessionId + "]");
//		Cache cache = ehcacheManager.getCacheManager().getCache("webSocketUserSessionCache");
//		Element element = new Element(accountId, sessionId);
//		cache.put(element);
    }

    /**
     * 断开连接删除用户
     *
     * @param sessionId
     * @param accountId
     */
    private void disconnect(String sessionId, String accountId, StompHeaderAccessor sha) {
        logger.debug("STOMP Disconnect [accountId :" + accountId + ", sessionId: " + sessionId + "]");
//		sha.getSessionAttributes().remove(ChatConsts.WEBSCOKET_SESSIONID);
//		sha.getSessionAttributes().remove(ChatConsts.WEBSCOKET_USERNAME);
//		Cache cache = ehcacheManager.getCacheManager().getCache("webSocketUserSessionCache");
//		cache.remove(accountId);
    }

}