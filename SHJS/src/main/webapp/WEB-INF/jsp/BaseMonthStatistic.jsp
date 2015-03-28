<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="el" uri="/eltag"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="span4 rect">
	<div class="row-fluid">
		<div class="span12">
			<h3>本月告警</h3>
		</div>
	</div>
	<div class="row-fluid" style="margin-top:6px;">
		<div name="unCancelCount" class="span6"><span>未处理告警</span><p data-value4M="未处理告警">${initData.unCancelCount}</p></div>
		<div name="invadeList4M" class="span6" ><span>非法手机</span><p data-value4M="WARN_LOGIN">${fn:length(initData.invadeList4MUD)}</p></div>
	</div>
	<div class="row-fluid" style="margin-top:6px;">
		<div name="callList4M" class="span6" ><span>非法通话</span><p data-value4M="WARN_CALL">${fn:length(initData.callList4MU)}</p></div>
		<div name="msgList4M" class="span6" ><span>非法短信</span><p data-value4M="WARN_SMS">${fn:length(initData.msgList4MU)}</p></div>
	</div>
</div>