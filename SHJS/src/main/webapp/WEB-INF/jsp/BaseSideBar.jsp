<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
	<div class="page-sidebar nav-collapse collapse">

			<!-- BEGIN SIDEBAR MENU -->        

			<ul class="page-sidebar-menu">

				<li>

					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->

					<div class="sidebar-toggler hidden-phone" style="margin-bottom:10px;"></div>

					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->

				</li>

				<li id="menu_home" class="start">

					<a  href="index.do" data-value='全部'>

					<i class="icon-home"></i> 

					<span class="title">首&nbsp&nbsp页</span>
					
					<span class="selected"></span>

					</a>

				</li>
				
				
				<li id="menu_record" class="">

					<a href="javascript:;">

					<i class="icon-eye-open"></i> 

					<span class="title">侦测记录</span>

					<span class=""></span>

					</a>

					<ul class="sub-menu">

						<li >

							<a href="surveillance.invade.initList.do" data-href="surveillance.invade.initList.do" data-id="invade">

							非法手机</a>

						</li>

						<li >

							<a href="surveillance.call.initList.do" data-href="surveillance.call.initList.do" data-id="call">

							非法通话</a>

						</li>
						<li >

							<a href="surveillance.msg.initList.do" data-href="surveillance.msg.initList.do" data-id="msg">

							非法短信</a>

						</li>

					</ul>

				</li>
				
				<li id="menu_white" class="">

					<a href="surveillance.phone.initList.do">

					<i class="icon-book"></i> 

					<span class="title">白名单</span>

					<span class=" "></span>

					</a>

				</li>
				
			<shiro:hasAnyRoles name="admin,manager">				
				<li id="menu_system" class="last">

					<a href="javascript:;" >

					<i class="icon-cogs"></i> 

					<span class="title">系统</span>
					<span class=" "></span>
					</a>
					<ul class="sub-menu">
					
						<li >

							<a href="surveillance.slArea.initList.do" >监区管理</a>

						</li>
						<li >

							<a href="surveillance.sysUser.initList.do" >用户管理</a>

						</li>
					
					</shiro:hasAnyRoles>
						
					</ul>

				</li>

				</ul>

			<!-- END SIDEBAR MENU -->

		</div>