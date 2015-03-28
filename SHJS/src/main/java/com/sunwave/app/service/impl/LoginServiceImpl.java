package com.sunwave.app.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sunwave.app.dao.LoginDao;
import com.sunwave.app.dao.SlCallDAO;
import com.sunwave.app.dao.SlInvadeDAO;
import com.sunwave.app.dao.SlMsgDAO;
import com.sunwave.app.dao.SysUserDAO;
import com.sunwave.app.model.SlArea;
import com.sunwave.app.model.SlCall;
import com.sunwave.app.model.SlInvade;
import com.sunwave.app.model.SlMsg;
import com.sunwave.app.model.SysUser;
import com.sunwave.app.service.LoginService;

@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginDao loginDao;
	@Autowired
	private SysUserDAO sysUserDAO;
	@Autowired
	private SlMsgDAO slMsgDAO;
	@Autowired
	private SlCallDAO slCallDAO;
	@Autowired
	private SlInvadeDAO slInvadeDAO;


	@Override
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
	public Map<String, Object> initUserData(SysUser user) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Integer areaId = user.getSlArea().getAreaId();
		Integer areaGrade = user.getSlArea().getAreaGrade();
		//一个月内告警数据
		String timeT="M";
		List<SlInvade> invadeList4M = slInvadeDAO.findInvadeByT2A(areaId, timeT,areaGrade);
		dataMap.put("invadeList4M", invadeList4M);
		List<SlCall> callList4M = slCallDAO.findCallByT2A(areaId, timeT,areaGrade);
		dataMap.put("callList4M", callList4M);
		List<SlMsg> msgList4M = slMsgDAO.findMsgByT2A(areaId, timeT,areaGrade);
		dataMap.put("msgList4M", msgList4M);
		//未处理告警
		List<SlInvade> invadeList4MU=new ArrayList<SlInvade>();
		//非法手机map，去重
		Map<String,SlInvade> invadeMap4MUD = new HashMap<String,SlInvade>();
		List<SlInvade> invadeList4MUD=new ArrayList<SlInvade>();
		List<SlCall> callList4MU =new ArrayList<SlCall>();
		List<SlMsg> msgList4MU =new ArrayList<SlMsg>();
		//一个月内未处理告警数据
		int unCancelCount=0;
		for(SlInvade slInvade :invadeList4M){
			if(slInvade.getCancelState()==0){
				invadeList4MU.add(slInvade);
				if(invadeMap4MUD.get(slInvade.getImsi())!=null){
					SlInvade old=invadeMap4MUD.get(slInvade.getImsi());
//					System.out.println(old.getRecordDate());
//					System.out.println(slInvade.getRecordDate());
					if(old.getRecordDate().getTime()<=slInvade.getRecordDate().getTime()){
						unCancelCount++;
						invadeMap4MUD.put(slInvade.getImsi(),slInvade);
						invadeList4MUD.add(slInvade);
					}
				}else{
					unCancelCount++;
					invadeMap4MUD.put(slInvade.getImsi(),slInvade);
					invadeList4MUD.add(slInvade);
				}
			}
		}
		for(SlCall slCall :callList4M){
			if(slCall.getCancelState()==0){
				unCancelCount++;
				callList4MU.add(slCall);
			}
		}
		for(SlMsg slMsg :msgList4M){
			if(slMsg.getCancelState()==0){
				unCancelCount++;
				msgList4MU.add(slMsg);
			}
		}
		dataMap.put("unCancelCount", unCancelCount);
		dataMap.put("invadeList4MU", invadeList4MU);
		dataMap.put("invadeList4MUD", invadeList4MUD);
		dataMap.put("callList4MU", callList4MU);
		dataMap.put("msgList4MU", msgList4MU);
		//本年度告警数据
		timeT="Y";
		List<SlInvade> invadeList4Y = slInvadeDAO.findInvadeByT2A(areaId, timeT,areaGrade);
		List<SlCall> callList4Y = slCallDAO.findCallByT2A(areaId, timeT,areaGrade);
		List<SlMsg> msgList4Y = slMsgDAO.findMsgByT2A(areaId, timeT,areaGrade);
		dataMap.put("invadeList4Y", invadeList4Y);
		dataMap.put("callList4Y", callList4Y);
		dataMap.put("msgList4Y", msgList4Y);
		List<SlArea> areaSet=new ArrayList<SlArea>();
		if(user.getUserType()==1){//管理局权限信息
			areaSet = loginDao.getAreasByParentId(user.getSlArea().getAreaId(),user.getSlArea().getAreaId());
			dataMap.put("areaSet", areaSet);
			int unCancelCountY=0;
			//一个月内未处理告警数据
			for(SlInvade slInvade :invadeList4Y){
				if(slInvade.getCancelState()==0){
					unCancelCountY++;
				}
			}
			for(SlCall slCall :callList4Y){
				if(slCall.getCancelState()==0){
					unCancelCountY++;
				}
			}
			for(SlMsg slMsg :msgList4Y){
				if(slMsg.getCancelState()==0){
					unCancelCountY++;
				}
			}
			dataMap.put("unCancelCountY", unCancelCountY);
		}else {//监狱权限信息
			areaSet.add(user.getSlArea());
			dataMap.put("areaSet", areaSet);
			TreeSet<Object> callMsgSet = new TreeSet<Object>(new MyComparator2());
			for(SlInvade slInvade:invadeList4Y){
				callMsgSet.add(slInvade);
			}
			for(SlCall slCall:callList4Y){
				callMsgSet.add(slCall);
			}
			for(SlMsg slMsg:msgList4Y){
				callMsgSet.add(slMsg);
			}
			List<Map<String,Object>> callMsgList = new ArrayList<Map<String,Object>>();
			for(Object o:callMsgSet){
				//融合数据到一个列表
				Map<String,Object> callMsgMap = new HashMap<String,Object>();
				if(o.getClass()==SlCall.class){
					callMsgMap.put("classType", "非法通话");
				}else if(o.getClass()==SlMsg.class){
					callMsgMap.put("classType", "非法短信");
				}else if(o.getClass()==SlInvade.class){
					callMsgMap.put("classType", "非法手机");
				}
				callMsgMap.put("ob", o);
				callMsgList.add(callMsgMap);
			}
			dataMap.put("callMsgList", callMsgList);
		}
		//获取监狱列表
		String allAreaId=",";
		for(SlArea area:areaSet){
			allAreaId+=area.getAreaId()+",";
		}
		dataMap.put("allAreaId", allAreaId);
		return dataMap;
	}
	
	//地区排序比较器
	class MyComparator implements Comparator<SlArea> {

		@Override
		public int compare(SlArea o1, SlArea o2) {
			if(o1.getAreaOrder()>o2.getAreaOrder()){
				return 1;
			}else if(o1.getAreaOrder()==o2.getAreaOrder()){
				return 0;
			}else{
				return -1;
			}
		}
		
	}
	
	//非法通话、短信综合日期排序比较器
		class MyComparator2 implements Comparator<Object> {

			@Override
			public int compare(Object o1, Object o2) {
				Date date1=null;
				Date date2=null;
				if(o1.getClass()==SlCall.class){
					SlCall o =  (SlCall) o1;
					date1 = o.getRecordDate();
				}else if(o1.getClass()==SlMsg.class){
					SlMsg o = (SlMsg) o1;
					date1 = o.getRecordDate();
				}else if(o1.getClass()==SlInvade.class){
					SlInvade o = (SlInvade) o1;
					date1 = o.getRecordDate();
				}
				
				if(o2.getClass()==SlCall.class){
					SlCall o =  (SlCall) o2;
					date2 = o.getRecordDate();
				}else if(o2.getClass()==SlMsg.class){
					SlMsg o =  (SlMsg) o2;
					date2 = o.getRecordDate();
				}else if(o2.getClass()==SlInvade.class){
					SlInvade o =  (SlInvade) o2;
					date2 = o.getRecordDate();
				}
				if(date1==null){
					return -1;
				}
				if(date2==null){
					return 1;
				}
				if(date1.getTime()<=date2.getTime()){
					return 1;
				}else{
					return -1;
				}
			}
			
		}
		
	@Override
	public Map<String, Object> findCCData(SysUser user,String dataType, String ccType,
			String tType) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Integer areaId = user.getSlArea().getAreaId();
		List<Object[]> InvadeByM = slInvadeDAO.findInvadeByM(areaId,ccType);
		List<Object[]> callByM = slCallDAO.findCallByM(areaId,ccType);
		List<Object[]> msgByM = slMsgDAO.findMsgByM(areaId,ccType);
		Map< Integer,Integer> cc4M1=new HashMap<Integer,Integer>();
		Map< Integer,Integer> cc4M2=new HashMap<Integer,Integer>();
		Map< Integer,Integer> cc4M3=new HashMap<Integer,Integer>();
		List<Integer> invadeYByM=new ArrayList<Integer>();
		List<Integer> callYByM=new ArrayList<Integer>();
		List<Integer> msgYByM=new ArrayList<Integer>();
		List<String> xByM=new ArrayList<String>();
		if("T".equals(ccType)){
			//按月份统计告警数
			for(Object[] ob:InvadeByM){
				cc4M1.put(Integer.parseInt(String.valueOf(ob[0])), Integer.parseInt(String.valueOf(ob[1])));
			}
			for(Object[] ob:callByM){
				cc4M2.put(Integer.parseInt(String.valueOf(ob[0])), Integer.parseInt(String.valueOf(ob[1])));
			}
			for(Object[] ob:msgByM){
				cc4M3.put(Integer.parseInt(String.valueOf(ob[0])), Integer.parseInt(String.valueOf(ob[1])));
			}
			for(int i=1;i<=12;i++){
				xByM.add(i+"月");
				if(cc4M1.get(i)==null){
					invadeYByM.add(0);
				}else{
					invadeYByM.add(cc4M1.get(i));
				}
				if(cc4M2.get(i)==null){
					callYByM.add(0);
				}else{
					callYByM.add(cc4M2.get(i));
				}
				if(cc4M3.get(i)==null){
					msgYByM.add(0);
				}else{
					msgYByM.add(cc4M3.get(i));
				}
			}
		}else if("A".equals(ccType)){
			//按地区统计告警数
			List<SlArea> slAreas = loginDao.getAreasByParentId(user.getSlArea().getAreaId(),user.getSlArea().getAreaId());
			for(Object[] ob:InvadeByM){
				cc4M1.put(Integer.parseInt(String.valueOf(ob[0])), Integer.parseInt(String.valueOf(ob[2])));
			}
			for(Object[] ob:callByM){
				cc4M2.put(Integer.parseInt(String.valueOf(ob[0])), Integer.parseInt(String.valueOf(ob[2])));
			}
			for(Object[] ob:msgByM){
				cc4M3.put(Integer.parseInt(String.valueOf(ob[0])), Integer.parseInt(String.valueOf(ob[2])));
			}
			for(SlArea slArea:slAreas){
				if(slArea.getAreaGrade()==2){//只统计监区
					xByM.add(slArea.getAreaName());
					Integer areaId2 = slArea.getAreaId();
					if(cc4M1.get(areaId2)==null){
						invadeYByM.add(0);
					}else{
						invadeYByM.add(cc4M1.get(areaId2));
					}
					if(cc4M2.get(areaId2)==null){
						callYByM.add(0);
					}else{
						callYByM.add(cc4M2.get(areaId2));
					}
					if(cc4M3.get(areaId2)==null){
						msgYByM.add(0);
					}else{
						msgYByM.add(cc4M3.get(areaId2));
					}
				}
			}
		}else if("O".equals(ccType)){
			//按运营商计告警数
			for(Object[] ob:InvadeByM){
				cc4M1.put(Integer.parseInt(String.valueOf(ob[0])), Integer.parseInt(String.valueOf(ob[1])));
			}
			for(Object[] ob:callByM){
				cc4M2.put(Integer.parseInt(String.valueOf(ob[0])), Integer.parseInt(String.valueOf(ob[1])));
			}
			for(Object[] ob:msgByM){
				cc4M3.put(Integer.parseInt(String.valueOf(ob[0])), Integer.parseInt(String.valueOf(ob[1])));
			}
			Map<Integer,String> oprators = new HashMap<Integer,String>();
			oprators.put(0,"移动");
			oprators.put(1,"联通");
			oprators.put(2,"电信");
			for(int i=0;i<3;i++){
				xByM.add(oprators.get(i));
				if(cc4M1.get(i)==null){
					invadeYByM.add(0);
				}else{
					invadeYByM.add(cc4M1.get(i));
				}
				if(cc4M2.get(i)==null){
					callYByM.add(0);
				}else{
					callYByM.add(cc4M2.get(i));
				}
				if(cc4M3.get(i)==null){
					msgYByM.add(0);
				}else{
					msgYByM.add(cc4M3.get(i));
				}
			}
		}
		dataMap.put("xByM", xByM);
		dataMap.put("invadeYByM", invadeYByM);
		dataMap.put("callYByM", callYByM);
		dataMap.put("msgYByM", msgYByM);
		return dataMap;
	}

}
