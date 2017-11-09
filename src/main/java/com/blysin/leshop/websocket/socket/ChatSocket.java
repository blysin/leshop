package com.blysin.leshop.websocket.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blysin.leshop.common.constant.CommonConstants;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Blysin
 * @since 2017/11/2
 */
@ServerEndpoint(value = "/socket/chat")
@Component
public class ChatSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCounts = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的ChatSocket对象。
    private static CopyOnWriteArraySet<ChatSocket> webSocketSet = new CopyOnWriteArraySet<ChatSocket>();

    private static ConcurrentHashMap<String,Session> sessionMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中

        System.out.println(session.getRequestParameterMap().get("uid"));

        System.out.println("有新连接加入！当前在线人数为" + onlineCounts.addAndGet(1));
        try {
            sendInfo("当前在线人数："+onlineCounts.get());
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }



    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);  //从set中删除
        System.out.println("有一连接关闭！当前在线人数为" + onlineCounts.decrementAndGet());
        sendInfo("当前在线人数："+onlineCounts.get());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自"+session.getId()+"的消息:" + message);

        //群发消息
        for (ChatSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (ChatSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }
}
