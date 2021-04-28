package com.wan.common.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/webSocket")
public class WebSocket {
	
	private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();
    private final Logger log = LoggerFactory.getLogger(WebSocket.class);
    
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        log.info("【websocket消息】有新的连接，总数：" + webSocketSet.size());
    }
    
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("【websocket消息】连接断开，总数：" + webSocketSet.size());
    }
    
    @OnMessage
    public void onMessage(String message){
    	log.info("【websocket消息】收到客户端发来的消息：" + message);
    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        log.error("【websocket消息】发生错误");
    }
    
    public void sendMessage(String message){
        for(WebSocket webSocket: webSocketSet){
        	log.info("【websocket消息】广播消息：" + message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("【websocket消息】服务端发送消息给客户端失败：" + e);
            }
        }
    }

}
