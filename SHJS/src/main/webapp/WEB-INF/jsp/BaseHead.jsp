<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="el" uri="/eltag"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
	<div class="header navbar navbar-inverse navbar-fixed-top" style=" height: 42px; ">

		<!-- BEGIN TOP NAVIGATION BAR -->

		<div class="navbar-inner">

			<div class="container-fluid">

				<!-- BEGIN LOGO -->

				<a class="brand" data-func="sendM">

					<img src="resources/images/frame/police.png" alt="logo" width="86" height="14" style="height:35px" />
					<p>手机信号管控系统</p>
	
				</a>

				<!-- END LOGO -->

				<!-- BEGIN RESPONSIVE MENU TOGGLER -->
				<a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">

					<img src="resources/images/frame/menu-toggler.png" alt="">

				</a>
				
				<!-- END RESPONSIVE MENU TOGGLER -->            

				<!-- BEGIN TOP NAVIGATION MENU -->              

				<ul class="nav pull-right">

					<!-- BEGIN NOTIFICATION DROPDOWN -->   
					<c:set var="booleanI" value="${fn:length(initData.invadeList4MUD)>0}"></c:set>
					<li class="dropdown <c:if test='${booleanI}'>redBling</c:if>" id="header_notification_bar" data-type="WARN_LOGIN" >
						<a href="#" class="dropdown-toggle <c:if test='${booleanI}'>redBreath</c:if>" data-toggle="dropdown">
						<i class="iconsp-mobile"></i>
						<!-- <span class="badge" data-type="alertCount">6</span> -->
							<c:if test="${booleanI}">
							<span class="badge" data-Count="WARN_LOGIN">${fn:length(initData.invadeList4MUD)}</span>
							</c:if>
						</a>

						<ul class="<c:if test="${booleanI}">dropdown-menu</c:if> extended inbox">
							<li class="disno">
								<p>有<b data-Count="WARN_LOGIN">${fn:length(initData.invadeList4MUD)}</b>条未处理非法手机</p>
							</li>
							<c:if test="${booleanI}">
							<c:forEach items="${initData.invadeList4MUD}" var="item" varStatus="status">
							<li class='<c:if test="${status.index>3}">disno</c:if>' data-info="WARN_LOGIN" data-did="${item.invadeId}"  data-imsi="${item.imsi}" data-date="${item.recordDate}" data-posinfo="${item.posinfo}"  data-operator="${item.operator}" data-name="入侵告警"  data-loginimei="${item.imei}" >
								<a href="#myModal" data-toggle="modal" data-optype='1'>
								<span class="photo"><img src="resources/images/frame/operator/op${item.operator}.png" alt="" /></span>
								<span class="subject">
									<span class="from" >${item.imsi}</span>
									<br/>
									<span class="time">${item.recordDate}</span>
								</span>
								<span class="message">
									<b>[${item.slArea.areaName}]:</b>&nbsp${item.posinfo}
								</span>  
								</a>
							</li>
							</c:forEach>
							</c:if>
							<li class="external disno">
								<a href="surveillance.invade.initList.do" data-href="surveillance.invade.initList.do" data-id="invade" class="all">查看所有 <i class="m-icon-swapright"></i></a>
							</li>
						</ul>

					</li>

					<!-- END NOTIFICATION DROPDOWN -->

					<!-- END INBOX DROPDOWN -->
					<!-- BEGIN TODO DROPDOWN -->
					<c:set var="booleanC" value="${fn:length(initData.callList4MU)>0}"></c:set>
					<li class="dropdown <c:if test='${booleanC}'>redBling</c:if>" id="header_notification_bar" data-type="WARN_CALL" >
						<a href="#" class="dropdown-toggle <c:if test='${booleanC}'>redBreath</c:if>" data-toggle="dropdown">
						<i  class="iconsp-call"></i>
						<!-- <span class="badge" data-type="alertCount">6</span> -->
							<c:if test="${booleanC}">
							<span class="badge" data-Count="WARN_CALL">${fn:length(initData.callList4MU)}</span>
							</c:if>
						</a>

						<ul class="<c:if test="${booleanC}">dropdown-menu</c:if> extended inbox">
							<li class="disno">
								<p>有<b data-Count="WARN_CALL">${fn:length(initData.callList4MU)}</b>条非法通话</p>
							</li>
							<c:if test="${booleanC}">
							<c:forEach items="${initData.callList4MU}" var="item" varStatus="status">
							<li class='<c:if test="${status.index>3}">disno</c:if>' data-info="WARN_CALL" data-Did="${item.callId}"  data-imsi="${item.imsi}" data-date="${item.recordDate}" data-posinfo="${item.posinfo}" data-operator="${item.operator}" data-name="通话告警"  data-targnum="${item.targNum}">
								<a href="#myModal" data-toggle="modal" data-optype='2'>
								<span class="photo"><img src="resources/images/frame/operator/op${item.operator}.png" alt="" /></span>
								<span class="subject">
									<span class="from" > ${item.imsi}</span>
									<br/>
									
									<span class="time">${item.recordDate}</span>
								</span>
								<span class="message">
								<b>[${item.slArea.areaName}]:</b>&nbsp${item.posinfo}
								</span>  
								</a>
							</li>
							</c:forEach>
							</c:if>
							<li class="external disno">
								<a data-href="surveillance.call.initList.do" data-id="call" class="all">查看所有 <i class="m-icon-swapright"></i></a>
							</li>
						</ul>

					</li>

					<!-- BEGIN INBOX DROPDOWN -->
					<!-- 短信 -->
					<c:set var="booleanM" value="${fn:length(initData.msgList4MU)>0}"></c:set>
					<li class="dropdown <c:if test='${booleanM}'>redBling</c:if>" id="header_notification_bar" data-type="WARN_SMS" >
						<a href="#" class="dropdown-toggle <c:if test='${booleanM}'>redBreath</c:if>" data-toggle="dropdown">
						<i class="icon-envelope"></i>
						<!-- <span class="badge" data-type="alertCount">6</span> -->
							<c:if test="${booleanM}">
							<span class="badge" data-Count="WARN_SMS">${fn:length(initData.msgList4MU)}</span>
							</c:if>
						</a>

						<ul class="<c:if test="${booleanM}">dropdown-menu</c:if> extended inbox">
							
							<li class="disno">
								<p>有<b data-Count="WARN_SMS">${fn:length(initData.msgList4MU)}</b>条未处理非法短信</p>
							</li>
							<c:if test="${booleanM}">
							<c:forEach items="${initData.msgList4MU}" var="item" varStatus="status">
							<li class='<c:if test="${status.index>3}">disno</c:if>' data-info="WARN_SMS" data-DId="${item.msgId}" data-imsi="${item.imsi}" data-date="${item.recordDate}" data-posinfo="${item.posinfo}" data-operator="${item.operator}" data-name="短信告警" data-targnum="${item.targNum}" data-content="${item.msgContent}">
								<a href="#myModal" data-toggle="modal" data-optype='3'>
								<span class="photo"><img src="resources/images/frame/operator/op${item.operator}.png" alt="" /></span>
								<span class="subject">
									<span class="from" >${item.imsi}</span>
									<br/>
									<span class="time">${item.recordDate}</span>
								</span>
								<span class="message">
								<b>[${item.slArea.areaName}]:</b>&nbsp${item.posinfo}
								</span>  
								</a>
							</li>
							</c:forEach>
							</c:if>
							<li class="external disno">
								<a  data-href="surveillance.msg.initList.do" data-id="msg" class="all">查看所有 <i class="m-icon-swapright"></i></a>
							</li>
						</ul>

					</li>
					<!-- END TODO DROPDOWN -->

					<!-- BEGIN USER LOGIN DROPDOWN -->

					<li class="dropdown user">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<img alt="" src="resources/images/upload/userface/avatar1_small.jpg" />
						<span class="username">${userShowName}</span>
						<i class="icon-angle-down"></i>
						</a>
						<ul class="dropdown-menu">
							<li><a href="extra_profile.html"><i class="icon-user"></i><span class="userSpan">修改密码</span></a></li>
							<shiro:hasRole name="normal"><li><a href="go2MapE.do" target="_blank"><i class="icon-map-marker"></i><span class="userSpan">编辑地图</span></a></li></shiro:hasRole>
							<%-- <shiro:hasRole name="manager"></shiro:hasRole> --%>
							<li class="divider"></li>
							<!-- <li><a href="go2lock.do"><i class="icon-lock"></i><span class="userSpan">锁屏</span></a></li> -->
							<li><a href="logout.do"><i class="icon-key"></i><span class="userSpan">注销</span></a></li>
						</ul>
					</li>
				</ul>
				<audio controls="controls" id="mp3" autoplay=true class="disno">
					<source src="" type="audio/mp3" />
					Your browser does not support this audio format.
				</audio>
			</div>
		</div>
	</div>