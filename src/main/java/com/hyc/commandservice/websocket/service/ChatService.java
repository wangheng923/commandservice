package com.hyc.commandservice.websocket.service;

import com.hyc.commandservice.service.BaseService;
import net.sf.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: wangheng2
 * @Date: 2018/4/24
 * @Modified By:
 */
@Service
public class ChatService extends BaseService {

    //    @Autowired
//    private ChatRecordService chatRecordService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private EhCacheCacheManager ehcacheManager;

//	@Value("${fileStoreRoot.imageWebUrl}")
//	private String downloadPath;
//	@Value("${imageRootUrl}")
//	private String imageRootUrl;
//
//	@Autowired
//	private AppDao appDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public void sendOfflineMsgs(Message message) {
//        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
//        String accountId = (String) sha.getSessionAttributes().get(ChatConsts.WEBSCOKET_USERNAME);
//        ChatRecord chatRecord = new ChatRecord();
//        chatRecord.setToUserId(accountId);
//        chatRecord.setIsTransport(TransportConsts.CHAT_UNSEND);
//        ArrayList<ChatRecord> list = chatRecordService.findOffLineList(chatRecord);
//        ArrayList<ChatRecordResponse> voList = new ArrayList<ChatRecordResponse>();
//        if (!list.isEmpty()) {
//            for (ChatRecord record : list) {
//                ChatRecordResponse vo = new ChatRecordResponse();
//                vo.setChatId(record.getChatId());
//                vo.setContent(record.getContent());
//                vo.setFromUserId(record.getFromUserId());
//                vo.setToUserId(record.getToUserId());
//                vo.setType(SmsTypeConsts.OFFLINE_SMS);
//                vo.setCreateTime(DateUtils.formatDateTime(record.getCreateTime()));
//                voList.add(vo);
//            }
//            messagingTemplate.convertAndSendToUser(accountId, "/message", voList);
//            for (ChatRecord cr : list) {
//                // 更新记录为已发送
//                cr.setIsTransport(TransportConsts.CHAT_SEND);
//                chatRecordService.update(cr);
//            }
//        }
    }

    /**
     * 发送一对一聊天信息
     *
     * @param msg
     */
    public void sendChatMsg(String msg) {
//        if (StringUtils.isNotBlank(msg)) {
//            ChatRecord po = null;
//            try {
//                po = JsonUtils.fromJson(msg, ChatRecord.class);
//            } catch (Exception e) {
//                logger.error("websocket msg parse error : {}", msg);
//                return;
//            }
//            logger.debug("receive msg content:{}", msg);
//            // 保存到数据库
//            updateAndSaveChatRecord(po);
//
//            // 向用户发送消息,第一个参数是接收人、第二个参数是浏览器订阅的地址，第三个是消息本身
//            messagingTemplate.convertAndSendToUser(po.getFromUserId(), "/message", po);
//
//            Cache cache = ehcacheManager.getCacheManager().getCache("webSocketUserSessionCache");
//            if (cache.get(po.getToUserId()) == null) {
//                logger.debug("target user:{} offline ,save msg to db", po.getToUserId());
//                return;
//            }
//            // 向用户发送消息,第一个参数是接收人、第二个参数是浏览器订阅的地址，第三个是消息本身
//            messagingTemplate.convertAndSendToUser(po.getToUserId(), "/message", po);
//            // 保存到数据库
//            po.setIsTransport(TransportConsts.CHAT_SEND);
//            chatRecordService.update(po);
//        }
    }


    /**
     * 发送公告消息
     */
    public void sendNoticeMsg() {

    }

    /**
     * 发送系统通知
     */
    public void sendSystemMsg() {
        Cache cache = ehcacheManager.getCacheManager().getCache("webSocketUserSessionCache");
        List keys = cache.getKeys();
    }

    /**
     * 发送审核消息
     */
    public void sendAuditMsg() {

    }

//    private ChatRecord updateAndSaveChatRecord(ChatRecord chatRecord) {
//        chatRecord.setChatId(seqCreator.createSeq(new Date()));
//        chatRecord.setType(SmsTypeConsts.NORMAL_SMS);
//        chatRecord.setIsTransport(TransportConsts.CHAT_UNSEND);
//        chatRecord.setRead("0");
//        chatRecordService.save(chatRecord);
//        return chatRecord;
//    }
}
