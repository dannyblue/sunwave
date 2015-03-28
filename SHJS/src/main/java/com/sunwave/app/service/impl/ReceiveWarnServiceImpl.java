package com.sunwave.app.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.sunwave.app.dao.SlAreaDAO;
import com.sunwave.app.dao.SlCallDAO;
import com.sunwave.app.dao.SlInvadeDAO;
import com.sunwave.app.dao.SlMsgDAO;
import com.sunwave.app.model.SlArea;
import com.sunwave.app.model.SlCall;
import com.sunwave.app.model.SlInvade;
import com.sunwave.app.model.SlMsg;
import com.sunwave.app.model.SysUser;
import com.sunwave.app.service.ReceiveWarnService;
import com.sunwave.framework.websocket.echo.EchoWebSocketHandler;

@Service("receiveWarnService")
@Transactional
public class ReceiveWarnServiceImpl implements ReceiveWarnService {

	@Autowired
	private SlMsgDAO slMsgDAO;
	@Autowired
	private SlCallDAO slCallDAO;
	@Autowired
	private SlInvadeDAO slInvadeDAO;
	@Autowired
	private SlAreaDAO slAreaDAO;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings({ "unchecked" })
	@Override
	public Map<String, Object> saveWarn(String msg) {
		log.info("收到侦测服务器消息："+msg);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// msg="WARN_SMS\t0001\t8\t13800000001\t13800000002\t2015-03-02 10:20:20\t1\t医务楼\t发送一条短信";
			// msg="WARN_CALL\t0001\t8\t13800000001\t13800000002\t2015-03-02 10:20:20\t1\t医务楼";
			//msg = "WARN_LOGIN\t0001\t8\t13800000002\t123123123\t2015-03-02 10:20:20\t1\t测试3";
			String msgType = msg.substring(0, msg.indexOf("\t"));
			msg = msg.substring(msg.indexOf("\t") + 1, msg.length());
			// String sessionId=msg.substring(0,msg.indexOf("\t"));
			msg = msg.substring(msg.indexOf("\t") + 1, msg.length());
			Integer areaId = Integer
					.valueOf(msg.substring(0, msg.indexOf("\t")));
			msg = msg.substring(msg.indexOf("\t") + 1, msg.length());
			String[] msgList = msg.split("\r\n");
			Subject currentUser = SecurityUtils.getSubject();
			Map<String, Object> initData = (Map<String, Object>) currentUser
					.getSession().getAttribute("initData");
			if ("WARN_LOGIN".equals(msgType)) {
				for (String etStr : msgList) {
					String[] split = etStr.split("\t");
					if (split.length == 5) {
						SlInvade slInvade = new SlInvade();
						slInvade.setImsi(split[0]);
						slInvade.setImei(split[1]);
						slInvade.setRecordDate(new Timestamp(sdf
								.parse(split[2]).getTime()));
						slInvade.setOperator(Integer.parseInt(split[3]));
						String posinfo = split[4].replaceAll("\t", "");
						posinfo = posinfo.replaceAll("\r", "");
						posinfo = posinfo.replaceAll("\n", "");
						slInvade.setPosinfo(posinfo);
						slInvade.setCancelState(0);
						SlArea slArea = (SlArea)slAreaDAO.findById(SlArea.class, areaId);
						slInvade.setSlArea(slArea);
						slInvadeDAO.save(slInvade);
						// 调用websocket广播消息，暂未处理目标权限
						String jsonString = JSON.toJSONString(slInvade);
						sendMsg(areaId,"WARN_LOGIN|" + jsonString);
						// 保存非法入侵
					}
				}
			} else if ("WARN_SMS".equals(msgType)) {
				for (String etStr : msgList) {
					String[] split = etStr.split("\t");
					if (split.length == 6) {
						SlMsg slMsg = new SlMsg();
						slMsg.setImsi(split[0]);
						slMsg.setTargNum(split[1]);
						slMsg.setRecordDate(new Timestamp(sdf.parse(split[2])
								.getTime()));
						slMsg.setOperator(Integer.parseInt(split[3]));
						String posinfo = split[4].replaceAll("\t", "");
						posinfo = posinfo.replaceAll("\r", "");
						posinfo = posinfo.replaceAll("\n", "");
						slMsg.setPosinfo(posinfo);
						slMsg.setMsgContent(split[5]);
						slMsg.setCancelState(0);
						SlArea slArea = (SlArea)slAreaDAO.findById(SlArea.class, areaId);
						slMsg.setSlArea(slArea);
						slMsg.setSlArea(slArea);
						slMsgDAO.save(slMsg);
						// initData.put("unCancelCount",(Integer)initData.get("unCancelCount")+1);
						// List<SlMsg> list =
						// (List<SlMsg>)initData.get("msgList4MU");
						// list.add(slMsg);
						// initData.put("msgList4MU", list);
						// 调用websocket广播消息，暂未处理目标权限
						String jsonString = JSON.toJSONString(slMsg);
						sendMsg(areaId,"WARN_SMS|" + jsonString);
					}
				}
			} else if ("WARN_CALL".equals(msgType)) {
				for (String etStr : msgList) {
					String[] split = etStr.split("\t");
					if (split.length == 5) {
						SlCall slCall = new SlCall();
						slCall.setImsi(split[0]);
						slCall.setTargNum(split[1]);
						slCall.setRecordDate(new Timestamp(sdf.parse(split[2])
								.getTime()));
						slCall.setOperator(Integer.parseInt(split[3]));
						String posinfo = split[4].replaceAll("\t", "");
						posinfo = posinfo.replaceAll("\r", "");
						posinfo = posinfo.replaceAll("\n", "");
						slCall.setPosinfo(posinfo);
						slCall.setCancelState(0);
						SlArea slArea = (SlArea)slAreaDAO.findById(SlArea.class, areaId);
						slCall.setSlArea(slArea);
						slCallDAO.save(slCall);
						// 调用websocket广播消息，暂未处理目标权限
						String jsonString = JSON.toJSONString(slCall);
						sendMsg(areaId,"WARN_CALL|" + jsonString);
					}
				}
			} else if ("SET_WHITE".equals(msgType)) {

			} else if ("RESAULT".equals(msgType)) {

			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("error", "接收告警异常！");
		}
		result.put("success", true);
		return result;
	}

	@Override
	public Map<String, Object> cancelWarn(Integer id, Integer otype,
			String cancelCause, String cancelPassword) {
		Map<String, Object> result = new HashMap<String, Object>();
		Subject currentUser = SecurityUtils.getSubject();
		SysUser loginUser = (SysUser) currentUser.getSession().getAttribute(
				"loginUser");
		UsernamePasswordToken token = new UsernamePasswordToken(
				loginUser.getUsername(), cancelPassword);
		try {
			currentUser.login(token);
		} catch (Exception e) {
			result.put("error", "密码错误！");
			return result;
		}
		Integer areaId=null;
		String posinfo="";
		if(otype==1){
			SlInvade slInvade = (SlInvade) slInvadeDAO.findById(SlInvade.class,
					id);
			slInvade.setCancelCause(cancelCause);
			slInvade.setCancelState(1);
			slInvadeDAO.update(slInvade);
			areaId=slInvade.getSlArea().getAreaId();
			posinfo=slInvade.getPosinfo();
		}else if(otype==2){
			SlCall slCall = (SlCall) slCallDAO.findById(SlCall.class, id);
			slCall.setCancelCause(cancelCause);
			slCall.setCancelState(1);
			slCallDAO.update(slCall);
			areaId=slCall.getSlArea().getAreaId();
			posinfo=slCall.getPosinfo();
		}else if(otype==3){
			SlMsg slMsg = (SlMsg) slMsgDAO.findById(SlMsg.class, id);
			slMsg.setCancelCause(cancelCause);
			slMsg.setCancelState(1);
			slMsgDAO.update(slMsg);
			areaId=slMsg.getSlArea().getAreaId();
			posinfo=slMsg.getPosinfo();
		}
		String msg="CANCEL_WARN_" + otype + "|" + "{id:"+id+",areaId:"+areaId+",posinfo:'"+posinfo+"'}";
		sendMsg(areaId,msg);
		return result;
	}
	
	public static void sendMsg(Integer areaId,String msg){
		//TODO 过滤定位失败消息
		for (String key : EchoWebSocketHandler.SESSION_MAP.keySet()) {
			Map<String, Object> sessionMap = EchoWebSocketHandler.SESSION_MAP.get(key);
			if(sessionMap.get("allAreaId").toString().indexOf(","+areaId+",")!=-1){
				WebSocketSession webSocketSession=(WebSocketSession)sessionMap.get("session");
				TextMessage message = new TextMessage(webSocketSession.getId().toString()+"|"+msg);
				System.out.println("发送["+key+"]websocket信息："+msg);
				try {
					webSocketSession.sendMessage(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
