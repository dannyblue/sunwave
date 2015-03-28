/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunwave.framework.websocket.echo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;

public class DefaultEchoService implements EchoService {

	private final String echoFormat;

	public DefaultEchoService(String echoFormat) {
		this.echoFormat = (echoFormat != null) ? echoFormat : "%s";
	}

	@Override
	public String getMessage(String message,WebSocketSession session) {
		String[] msgArray = message.split("\\|");
		if("CONN".equals(msgArray[0])){
			JSONObject data = (JSONObject)JSONObject.parse(msgArray[1]);
			String username = data.get("username").toString();
			if(username!=null){
				Map<String,Object> sessionMap = new HashMap<String,Object>();
				sessionMap.put("allAreaId", data.get("allAreaId").toString());
				sessionMap.put("session", session);
				dealConn(username,sessionMap);
			}
		}
		return "success";
	}

	public String dealConn(String username,Map<String,Object> sessionMap) {
		EchoWebSocketHandler.SESSION_MAP.put(username, sessionMap);
		System.out.println("websocket用户["+username+"]连接！,连接总数："+EchoWebSocketHandler.SESSION_MAP.keySet().size());
		return "success";
	}

}
