package com.hyc.commandservice.websocket.controller;//package com.hyc.secondhand.module.websocket;

import com.hyc.commandservice.websocket.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ChatService chatService;

    @SuppressWarnings("rawtypes")
    @SubscribeMapping("offlineMsg")
    public void connectedAndGetOfflineMsgs(Message message) {
        chatService.sendOfflineMsgs(message);
    }

    @MessageMapping("/sendChatMsg")
    public void sendChatMsg(String msg) throws Exception {
        chatService.sendChatMsg(msg);
    }

    // /**
    // *
    // * @param message
    // * @return
    // */
    // @MessageMapping("/sendChatMsgToAll")
    // public void sendChatMsgToAll(String msg) {
    // if (StringUtils.isNotBlank(msg)) {
    // ChatRecord po = null;
    // try {
    // po = JsonUtils.fromJson(msg, ChatRecord.class);
    // } catch (Exception e) {
    // logger.warn("websocket msg parse error : {}", msg);
    // return;
    // }
    // logger.info("receive msg content:{}", msg);
    // // 保存到数据库
    // updateAndSaveChatRecord(po);
    //
    // // 向用户发送消息,第一个参数是接收人、第二个参数是浏览器订阅的地址，第三个是消息本身
    // messagingTemplate.convertAndSend(po);
    // // 保存到数据库
    // po.setIsTransport(TransportConsts.CHAT_SEND);
    // chatRecordService.update(po);
    // }
    // }
    //
    // /**
    // *
    // * @param message
    // * @return
    // */
    // @MessageMapping("/sendChatMsgToAll2")
    // @SendTo("/topic/systemMsg")
    // public ChatRecord sendChatMsgToAll2(String msg) {
    // if (StringUtils.isNotBlank(msg)) {
    // ChatRecord po = null;
    // try {
    // po = JsonUtils.fromJson(msg, ChatRecord.class);
    // } catch (Exception e) {
    // logger.warn("websocket msg parse error : {}", msg);
    // return null;
    // }
    // logger.info("receive msg content:{}", msg);
    // // 保存到数据库
    //// updateAndSaveChatRecord(po);
    ////
    //// // 向用户发送消息,第一个参数是接收人、第二个参数是浏览器订阅的地址，第三个是消息本身
    //// messagingTemplate.convertAndSend(po);
    //// // 保存到数据库
    //// po.setIsTransport(TransportConsts.CHAT_SEND);
    //// chatRecordService.update(po);
    // return po;
    // }
    // return null;
    // }

    // 处理发送消息的错误,json是sendChatMsg方法的参数，传递到这里
    @MessageExceptionHandler
    public void handleExceptions(Exception e, String json) {
        logger.error("Error handling message: {}" + e);
//		ChatRecord po = JsonUtils.fromJson(json, ChatRecord.class);
        logger.error("msg: {}" + json);
        // 把错误信息发回给发送人
        // messagingTemplate.convertAndSendToUser(po.getFromUserId(),
        // "/queue/contactErrors" + po.getChatId(), errorJson);
    }

}
