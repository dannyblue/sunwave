package com.sunwave.app.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunwave.app.dao.SlPhoneDAO;
import com.sunwave.app.model.SlPhone;
import com.sunwave.app.model.SysUser;
import com.sunwave.app.service.PhoneService;

@Service("phoneService")
@Transactional
public class PhoneServiceImpl implements PhoneService{
	
	@Autowired
	private SlPhoneDAO slPhoneDAO;

	@SuppressWarnings({"unchecked" })
	@Override
	public Map<String, Object> set() {
		Map<String,Object> result= new HashMap<String,Object>();
		Subject currentUser = SecurityUtils.getSubject();
		SysUser user = (SysUser)currentUser.getSession().getAttribute("loginUser");
		SlPhone slPhone = new SlPhone();
//		slPhone.setIsWhite(1);
		Map<String, Object> findByPagination = slPhoneDAO.findByPagination(user.getSlArea().getAreaId(), slPhone, null, null, "");//iSlWListDAO.findAll();
		List<SlPhone> phoneList = (List<SlPhone>) findByPagination.get("data");
		boolean noStop=true;
		//往设备发送socket信息
		try {  
            //1.建立客户端socket连接，指定服务器位置及端口  
            Socket socket =new Socket("10.7.3.3",5577); 
            socket.setSoTimeout(15*1000);
            //2.得到socket读写流  
            OutputStream os=socket.getOutputStream();  
            PrintWriter pw=new PrintWriter(os);  
            //输入流  
            InputStream is=socket.getInputStream();  
            BufferedReader br=new BufferedReader(new InputStreamReader(is));  
            //3.利用流按照一定的操作，对socket进行读写操作  
            StringBuilder info= new StringBuilder("SET_WHITE\t0001");
            for(SlPhone w:phoneList){
            	String phoneNum = w.getPhoneNum();
            	info.append("\t"+phoneNum+"\t"+w.getOperator()+"\t"+w.getIsWhite()+"\r\n");
            }
            pw.write(info.toString());  
            pw.flush();  
            socket.shutdownOutput();  
            //接收服务器的相应  
            String reply=null;  
            while(!((reply=br.readLine())==null)&&noStop){
                System.out.println("接收服务器的信息："+reply);  
            }  
            result.put("data", reply);
            //4.关闭资源  
            br.close();  
            is.close();  
            pw.close();  
            os.close();  
            socket.close();  
        }catch(SocketTimeoutException e){
        	noStop=false;
        	result.put("error", "连接服务器超时！");
            e.printStackTrace(); 
            return result;
        } catch (UnknownHostException e) {
        	noStop=false;
        	result.put("error", "无法连接白名单服务器！");
            e.printStackTrace(); 
            return result;
        } catch (IOException e) {  
        	noStop=false;
        	result.put("error", "白名单下发异常！");
            e.printStackTrace(); 
            return result;
        }
		return result;
	}

	@Override
	public Map<String, Object> findPageInfo(Integer areaId,SlPhone slPhone, Integer page,
			Integer limit, String sort) {
		if(sort!=null&&sort.split("\\.").length==2){
			String[] split = sort.split("\\.");
			sort=" order by t."+split[0]+" "+split[1];
		}else{
			sort="";
		}
		Map<String, Object> findByPagination = slPhoneDAO.findByPagination(areaId,slPhone,page,limit,sort);
		return findByPagination;
	}

	@Override
	public boolean removeByIds(String ids) {
		String[] idList = ids.split(",");
		for(String id:idList){
			Object slPhone = slPhoneDAO.findById(SlPhone.class, Integer.valueOf(id));
			if(slPhone!=null)
				slPhoneDAO.delete(slPhone);
		}
		return true;
	}

	@Override
	public Map<String, Object> save(SlPhone slPhone) {
		Map<String,Object> result= new HashMap<String,Object>();
		slPhoneDAO.save(slPhone);
		result.put("data", slPhone);
		return result;
	}

	@Override
	public Map<String, Object> update(SlPhone slPhone) {
		Map<String,Object> result= new HashMap<String,Object>();
		slPhoneDAO.update(slPhone);
		result.put("data", slPhone);
		return result;
	}
	
}
