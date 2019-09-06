package com.person.chat.webSocket;

import com.person.auth.pojo.entity.User;
import com.person.auth.service.BaseService;
import com.person.chat.pojo.vo.ChatFriendVO;
import com.person.chat.service.ChatFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/9/5 10:53
 */
@Component
public class FriendMessageService<T> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private BaseService baseService;
    @Autowired
    private ChatFriendService chatFriendService;

    /**
     * @param userId1 登录人Id
     * @param userId2 朋友Id
     * @param path 监听路径
     */
    public void convertAndSendToUser(String userId1, String userId2, String path) {
        this.convertAndSendToUser(userId1, path);
        this.convertAndSendToUser(userId2, path);
    }

    /**
     * 用于ChatFriendVO List
     * @param userId 用户Id
     * @param path 监听路径
     */
    public void convertAndSendToUser(String userId, String path) {
        User user = baseService.findUserById(userId);
        List<ChatFriendVO> list = chatFriendService.listChatFriendByUserId(userId);

        messagingTemplate.convertAndSendToUser(String.valueOf(user), path, list);
    }



    /**
     * @param userId1 登录人Id
     * @param userId2 朋友Id
     * @param path 监听路径
     * @param message 返回对象
     */
    public void convertAndSendToUser(String userId1, String userId2, String path, T message) {
        this.convertAndSendToUser(userId1, path, message);
        this.convertAndSendToUser(userId2, path, message);
    }

    /**
     * @param userId 用户Id
     * @param path 监听路径
     * @param message 返回对象
     */
    public void convertAndSendToUser(String userId, String path, T message) {
        User user = baseService.findUserById(userId);

        messagingTemplate.convertAndSendToUser(String.valueOf(user), path, message);
    }


}
