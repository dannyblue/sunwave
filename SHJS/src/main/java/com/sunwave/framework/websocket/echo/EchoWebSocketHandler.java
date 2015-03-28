package com.sunwave.framework.websocket.echo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Echo messages by implementing a Spring {@link WebSocketHandler} abstraction.
 */
public class EchoWebSocketHandler extends TextWebSocketHandler {

	private final EchoService echoService;
	
	public static Map<String,Map<String,Object>> SESSION_MAP = new HashMap<String,Map<String,Object>>();
	
	//消息缓存，用于失败重发	
	//private static Map<String,List<String>> MESSAGE_CACH = new HashMap<String,List<String>>();

	@Autowired
	public EchoWebSocketHandler(EchoService echoService) {
		this.echoService = echoService;
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		echoService.getMessage(message.getPayload(),session);
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		TextMessage message = new TextMessage(session.getId()+"|CONN|{'status':'success'}");
		session.sendMessage(message);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		for(String key:SESSION_MAP.keySet()){
			Map<String,Object> m = SESSION_MAP.get(key);
			WebSocketSession object = (WebSocketSession)m.get("session");
			if(object.getId().equals(session.getId())){
				SESSION_MAP.remove(key);
				System.out.println("websocket用户["+key+"]断开连接！,连接总数："+SESSION_MAP.keySet().size());
			}
		}
	}
	
}
