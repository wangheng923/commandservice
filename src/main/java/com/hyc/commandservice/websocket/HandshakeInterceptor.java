package com.hyc.commandservice.websocket;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Map;

/**
 * @Description: websocket拦截器
 * @Author: wangheng2
 * @Date: 2018/4/24
 * @Modified By:
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    private Logger logger = Logger.getLogger(getClass());

    public HandshakeInterceptor() {
        super();
        setCopyHttpSessionId(true);
    }

    public HandshakeInterceptor(Collection<String> attributeNames) {
        super(attributeNames);
        setCopyHttpSessionId(true);
    }

    /**
     * @Description: 握手拦截, session信息
     * @Author: wangheng2
     * @Date: 2018/4/24
     * @param: request
     * @param: serverHttpResponse
     * @param: webSocketHandler
     * @param: map
     * @Modified By:
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
//				Object obj = session.getAttribute(LoginInterceptor.USER_SESSION_KEY);
//				String clientId = null;
//				if (obj instanceof Employee) {
//					Employee employee = (Employee) obj;
//					clientId = employee.getEmployeeId();
//				} else if (obj instanceof Police) {
//					Police police = (Police) obj;
//					clientId = police.getPoliceId();
//				}
//				if(null == clientId)
//				{
//					return false;
//				}
//				// 使用userName区分WebSocketHandler，以便定向发送消息
//				if (StringUtils.isNotBlank(clientId)) {
//					map.put(ChatConsts.WEBSCOKET_USERNAME, clientId);
//					map.put(ChatConsts.WEBSCOKET_SESSIONID, session.getId());
//				}
            }
        }
        return super.beforeHandshake(request, serverHttpResponse, webSocketHandler, map);
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler, Exception e) {
    }
}