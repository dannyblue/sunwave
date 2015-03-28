    <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="el" uri="/eltag"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8" />
<title>管理局首页</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="" name="author" />
<!-- INCLUDE GLOBAL STYLES -->
<jsp:include page="BaseGStyle.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL STYLES -->
</head>
<body class="page-header-fixed page-sidebar-closed"
	style="overflow:hidden;">
	<!-- BEGIN HEADER -->
	<jsp:include page="BaseHead.jsp"></jsp:include>
	<!-- END HEADER -->

	<!-- BEGIN OVERLAY -->
	<!-- END OVERLAY -->

	<!-- BEGIN CONTAINER -->
	<div class="page-container row-fluid" id="pageBox">

		<!-- BEGIN SIDEBAR -->
		<jsp:include page="BaseSideBar.jsp"></jsp:include>
		<!-- END SIDEBAR -->

		<!-- BEGIN PAGE -->
		<div class="page-content" style="height: 700px; overflow: hidden;">

			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->



			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->

			<!-- BEGIN PAGE CONTAINER-->

			<div class="container-fluid" style="padding:0" id="mainPage">
				<div class="span12" style="overflow:hidden;">
					<div class="container-fluid" id="mainBox">
						<div class="row-fluid" style="margin-top:6px;<shiro:hasAnyRoles name="normal">display:none;</shiro:hasAnyRoles>" id="mainTab">
							<div class="span12 ">
								<ul class="nav nav-tabs" id="myTab">
									<shiro:hasAnyRoles name="admin,manager">
									<li class="active"><a href="#panel1" data-toggle="tab"
										data-area="home">首页</a></li>
									</shiro:hasAnyRoles>
										<c:forEach items="${initData.areaSet}" var="item"
											varStatus="status">
											<c:if test="${item.areaGrade==2}">
												<li><a href="#panel2" data-toggle="tab"
													data-area="${item.areaId}">${item.areaName}</a></li>
											</c:if>
										</c:forEach>
								</ul>
							</div>
						</div>
						
						<div class="tab-content">
						<shiro:hasAnyRoles name="admin,manager">
							<div class="tab-pane active" id="panel1">
								<div class="row-fluid">
									<div class="span8">
										<button class="btn green" data-btnType="A">分监区统计</button>
										<button class="btn black" data-btnType="T">分月统计</button>
										<div class="map" id="mainChart"
											style="background-color:#fff;height:445px;position:relative">

										</div>
									</div>
									<jsp:include page="BaseMonthStatistic.jsp"></jsp:include>
								</div>
								<div class="row-fluid" style="margin-top:6px">
									<div class="span8">
										<div class="portlet-body">
											<div class="portlet box" id="statistics">
												<div class="portlet-title">
													<div class="caption">
														<i class="icon-bar-chart"></i>全年数据汇总
													</div>
												</div>
												<div class="portlet-body">
													<table class="table table-striped table-hover">
														<thead>
															<tr>
																<th>未处理告警</th>
																<th>非法手机</th>
																<th>非法通话</th>
																<th>非法短信</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td data-value4Y="totalUncancel">${initData.unCancelCountY}</td>
																<td data-value4Y="WARN_LOGIN">${fn:length(initData.invadeList4Y)}</td>
																<td data-value4Y="WARN_CALL">${fn:length(initData.callList4Y)}</td>
																<td data-value4Y="WARN_SMS">${fn:length(initData.msgList4Y)}</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
									<div class="span4" style=" margin-top: -35px; ">
										<div id="opratorChart" style="width:389px;height:180px"></div>
									</div>
								</div>
							</div>
							</shiro:hasAnyRoles>
							<div class="tab-pane" id="panel2">
								<div class="span12" style="overflow:hidden;">
									<div class="container-fluid" id="mainBox">
										<div class="row-fluid"
											style="margin-top:6px;overflow: hidden;">
											<div class="span8"
												style="position:relative;box-sizing:border-box;border:1px solid rgba(0,0,0,0.3);overflow: hidden;">
												<div id="mapTitle">
													<p>平面示意图</p>
												</div>
												<div style="width:100%;height:365px;" id="map"></div>
											</div>
											<jsp:include page="BaseMonthStatistic.jsp"></jsp:include>
										</div>
										<div class="row-fluid" style="margin-top:10px">
											<div class="span12">
												<div class="portlet-body" style="display: block;">
													<div class="portlet box black">
														<div class="portlet-title">
															<div class="caption">
																<i class="icon-signal"></i>非法记录
															</div>
															<div class="tools">
																<a href="javascript:;" class="icon-trash"></a>
															</div>
														</div>
														<div style="padding:0 10px">
															<table class="table table-striped table-hover">
																<thead>
																	<tr>
																		<th class="span2">非法记录类型</th>
																		<th>时间</th>
																		<th class="span3">手机号码</th>
																		<th class="span3">被呼叫号码</th>
																		<th class="span4">报警位置</th>
																	</tr>
																</thead>
															</table>
														</div>
														<div id='recordTable' data-box="WARN_LOGIN"
															class="portlet-body" data-id="box"
															style="max-height: 110px;overflow-y:scroll;position:relative">
															<table class="table table-striped table-hover">
																<tbody>
																	<c:forEach begin="1" end="3" varStatus="status">
																		<tr>
																			<td>&nbsp</td>
																			<td>&nbsp</td>
																			<td>&nbsp</td>
																			<td>&nbsp</td>
																			<td>&nbsp</td>
																		</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- END PAGE CONTAINER-->

	</div>


	<!-- END PAGE -->

	</div>

	<!-- END CONTAINER -->

	<!-- BEGIN FOOTER -->
	<jsp:include page="BaseFooter.jsp"></jsp:include>
	<!-- END FOOTER -->

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->

	<!-- BEGIN CORE PLUGINS -->
	<jsp:include page="BaseGJs.jsp"></jsp:include>
	<!-- END CORE PLUGINS -->

	<!-- WEBSOCKET 脚本 -->
	<script src="resources/js/websocket/sockjs-0.3.min.js"></script>

	<!-- HIGHCHARTS 脚本 -->
	<script src="resources/js/frame/chart/highcharts.js"></script>
	<script src="resources/js/frame/chart/highcharts-3d.js"></script>


	<!-- END PAGE LEVEL SCRIPTS -->

	<script>
	var dataAttr = {};
	jQuery(document).ready(function() {
		//全局地图变量
		var graph;
		//全局属性变量
		var imsiList = [];
		//全局计数器记录当前监区的告警条数
		var activeCount = 0;
		//激活menu
		$(".page-sidebar-menu li").removeClass("active");
		$("#menu_home").addClass("active");
		var activeTab = "home";
		App.init();
		var ws = null;
		var url = null;
		var transports = [];

		function setConnected(connected) {}

		function connect() {
			if (!url) {
				return;
			}

			ws = (url.indexOf('sockjs') != -1) ?
				new SockJS(url, undefined, {
					protocols_whitelist: transports
				}) : new WebSocket(url);

			ws.onopen = function() {
				setConnected(true);
				var username = "${username}";
				var allAreaId = "${initData.allAreaId}";
				var msg = "CONN|{username:'" + username + "',allAreaId:'" + allAreaId + "'}";
				console.log(msg)
				ws.send(msg);
			};
			ws.onmessage = function(event) {
				console.log(event.data);
				freshDom(event.data);
			};
			ws.onclose = function(event) {
				setConnected(false);
			};
		}

		function disconnect() {
			if (ws != null) {
				ws.close();
				ws = null;
			}
			setConnected(false);
		}

		function echo() {
			if (ws != null) {
				var message = "testMessage";
				//        log('Sent: ' + message);
				ws.send(message);
			} else {
				alert('connection not established, please connect.');
			}
		}

		function updateUrl(urlPath) {
			if (urlPath.indexOf('sockjs') != -1) {
				url = urlPath;
			} else {
				if (window.location.protocol == 'http:') {
					url = 'ws://' + window.location.host + urlPath;
				} else {
					url = 'wss://' + window.location.host + urlPath;
				}
			}
		}

		function updateTransport(transport) {
			transports = (transport == 'all') ? [] : [transport];
		}

		function log(message) {
			alert(message);
		}

		//连接
		updateUrl('/SHJS/echo');
		connect();
		//加载数据
		//disconnect();

		function rajax(type) {
			$.ajax({
				url: "cc.findCCdata.do",
				data: {
					ccType: type //A 分监区统计，T 分月份统计
				},
				success: function(content) {
					var xByM = content.xByM;
					var invadeYByM = content.invadeYByM;
					var callYByM = content.callYByM;
					var msgYByM = content.msgYByM;


					$("#mainChart").highcharts({
						chart: {
							type: 'column'
						},
						title: {
							text: '年度告警数据统计'
						},
						subtitle: {
							text: (type == 'A') ? '分监区统计' : '分月统计'
						},
						xAxis: {
							categories: xByM
						},
						yAxis: {
							min: 0,
							title: {
								text: '告警数量'
							}
						},
						tooltip: {
							headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
							pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
								'<td style="padding:0"><b>{point.y}</b></td></tr>',
							footerFormat: '</table>',
							shared: true,
							useHTML: true
						},
						plotOptions: {
							column: {
								pointPadding: 0.2,
								borderWidth: 0
							}
						},
						series: [{
							name: '非法手机',
							data: invadeYByM

						}, {
							name: '非法通话',
							data: callYByM

						}, {
							name: '非法短信',
							data: msgYByM

						}]
					});
				}
			});
		}

		function beAppDom(type) {
				if ($("[data-type=" + type + "]").hasClass('redBling')) {
					$("[data-type=" + type + "] .dropdown-menu li").first().removeClass("disno");
					$("[data-type=" + type + "] .dropdown-menu li").last().removeClass("disno");
				} else {
					$("[data-type=" + type + "] .dropdown-menu li").first().addClass("disno");
					$("[data-type=" + type + "] .dropdown-menu li").last().addClass("disno");
				}
			}
		function appendDom(pushDob, pushType) {

				var $al = $("[data-type=" + pushType + "]");
				var $ab = $("[data-box=" + pushType + "]");
				var $tbody = $("[data-box=" + pushType + "] tbody");
				var html = "",
					str = "";
				var dataArea = $("#myTab li.active a").data("area");
				activeCount++;
				//提示告警信息
				////判断之前是否存在告警
				if ($al.find("ul").hasClass("dropdown-menu")) {
					$al.find("ul li").first().removeClass("disno");
					$al.find("ul li").last().removeClass("disno");
				} else {
					$al.find("ul").addClass("dropdown-menu").find(".disno").removeClass("disno");
					$al.addClass("redBling");
					$al.children("a.dropdown-toggle").addClass("redBreath").append('<span class="badge" data-Count="' + pushType + '">0</span>');
				}
				for (var i = 0; i < imsiList.length; i++) {
					/* if (imsiList[i] == pushDob.imsi && pushType == 'WARN_LOGIN') {

					} else { */
						$("#mp3").attr('src', 'resources/others/sound/alarm.mp3')
						var alarmCount = $("[data-Count=" + pushType + "]").html();
						alarmCount++;
						alarmCount = $("[data-Count=" + pushType + "]").html(alarmCount);
						imsiList.push(pushDob.imsi);
						var time = pushDob.recordDate;
						time = new Date(time).Format("yyyy-MM-dd hh:mm:ss");
						//判断类型;
						var dataOpType;
						if (pushType == 'WARN_LOGIN')
							dataOpType = 1;
						else if (pushType == 'WARN_CALL')
							dataOpType = 2;
						else if (pushType == 'WARN_SMS')
							dataOpType = 3;
						else
							console.log("不识别该告警");
						str = '<li data-did=' + pushDob.invadeId + ' data-info=' + pushType + ' data-loginimsi="' + pushDob.imsi + '" data-logindate="' + time + '" data-posinfo="' + pushDob.posinfo + '" data-loginimei="' + pushDob.imei + '" data-operator="' + pushDob.operator + '" data-name="入侵告警"><a href="#myModal" data-toggle="modal" data-optype=' + dataOpType + '><span class="photo"><img src="resources/images/frame/operator/op' + pushDob.operator + '.png" alt="" /></span><span class="subject"><span class="from" >' + pushDob.imsi + '</span><br/><span class="time">' + time + '</span></span><span class="message"><b>[' + pushDob.slArea.areaId + ']:</b>' + pushDob.posinfo + '</span></a></li>';
						var liArray = $al.find("ul li").not(":first").not(".external");
						if (liArray.length > 3) {
							$al.find("ul li").first().after(str);
							liArray.eq(3).addClass("disno");
						} else {
							$al.find("ul li").first().after(str);
						}
						$("[data-did=" + pushDob.invadeId + "] a").on("click", function() {
							var val = $(this).data('optype');
							var li = $(this).parent('li');
							dataAttr.otype = val;
							dataAttr.info = li.data('info');
							dataAttr.did = li.data('did');
							dataAttr.imsi = li.data('loginimsi');
							dataAttr.date = li.data('logindate');
							dataAttr.posinfo = li.data('posinfo');
							dataAttr.operator = li.data('operator');
							dataAttr.name = li.data('name');
							dataAttr.loginimei = li.data('loginimei');
							dataAttr.targnum = li.data('targnum');
							dataAttr.content = li.data('content');
						});
						//判断当前页面
						if (dataArea == "home") {
							//本月告警数据累加
							var value4M = $('[data-value4M="未处理告警"]').html();
							value4M++;
							$('[data-value4M="未处理告警"]').html(value4M);
							var invade4M = $('[data-value4M=' + pushType + ']').html();
							invade4M++;
							$('[data-value4M=' + pushType + ']').html(invade4M);
							//年度告警数据累加
							var value4Y = $('[data-value4Y="totalUncancel"]').html();
							value4Y++;
							$('[data-value4Y="totalUncancel"]').html(value4Y);
							var invada4Y = $('[data-value4Y=' + pushType + ']').html();
							invada4Y++;
							$('[data-value4Y=' + pushType + ']').html(invada4Y);
							//刷新图表
							rajax('A');
						} else if (dataArea == pushDob.slArea.areaId) {
							//本月告警数据累加
							var value4M = $('[data-value4M="未处理告警"]').html();
							value4M++;
							$('[data-value4M="未处理告警"]').html(value4M);
							var invade4M = $('[data-value4M=' + pushType + ']').html();
							invade4M++;
							$('[data-value4M=' + pushType + ']').html(invade4M);
							//本月告警数据添加到表单
							var classType = pushType;
							if (classType == "WARN_LOGIN") {
								classType = '<i class="iconsp-mobile"></i>&nbsp非法手机';
							} else if (classType == "WARN_SMS") {
								classType = '<i class="icon-envelope"></i>&nbsp非法短信';
							} else if (classType == "WARN_CALL") {
								classType = '<i class="iconsp-call"></i>&nbsp非法通话';
							} else {
								classType = "未识别";
							}
							var recordDate = pushDob.recordDate;
							recordDate = new Date(recordDate).Format("yyyy-MM-dd hh:mm:ss");
							var imsi = pushDob.imsi;
							var targNum = pushDob.targNum;
							if (targNum == undefined) {
								targNum = '';
							}
							var posinfo = pushDob.posinfo;
							html += '<tr><td class="span2">' + classType + '</td><td>' + recordDate + '</td><td class="span3">' + imsi + '</td><td class="span3">' + targNum + '</td><td class="span4">' + posinfo + '</td></tr>';
							if (activeCount < 3) {
								$("#recordTable tbody tr").last().remove();
							}
							$("#recordTable tbody").prepend(html);
							//刷新地图
							var cells = graph.getModel().cells;
							for (var i in cells) {
								if (graph.getLabel(cells[i]) == pushDob.posinfo) {
									if (pushType == "WARN_LOGIN") {
										graph.setCellWarning(cells[i], '<b>非法用户:</b>:手机号码为<b>' + imsi + '</b>于<b>' + recordDate + '</b>出现在<b>' + posinfo + '</b>');
									} else if (pushType == "WARN_CALL") {
										graph.setCellWarning(cells[i], '<b>非法通话:</b>:手机号码为<b>' + imsi + '</b>于<b>' + recordDate + '</b>出现在<b>' + posinfo + '</b>');
									} else if (pushType == "WARN_SMS") {
										graph.setCellWarning(cells[i], '<b>非法短信:</b>:手机号码为<b>' + imsi + '</b>于<b>' + recordDate + '</b>出现在<b>' + posinfo + '</b>');
									} else {}
								};
							}
						}
					//}
				}
			} 
		
			//取消告警
			function removeDom(pushDob, pushType) {
				for (var i = 0; i < imsiList.length; i++) {
					if(imsiList[i]==pushDob.imsi){
						imsiList.splice(i, 1);
					}
				}
				if (pushType == 'CANCEL_WARN_1') {
					pushType = 'WARN_LOGIN';
				} else if (pushType == 'CANCEL_WARN_2') {
					pushType = 'WARN_CALL';
				} else if (pushType == 'CANCEL_WARN_3') {
					pushType = 'WARN_SMS';
				};
				var $al = $("[data-type=" + pushType + "]");
				var $ab = $("[data-box=" + pushType + "]");
				var $tbody = $("[data-box=" + pushType + "] tbody");
				var html = "",
					str = "";
				var dataArea = $("#myTab li.active a").data("area");
				activeCount--;
				//判断当前页面
				if (dataArea == "home") {
					//本月告警数据递减
					var value4M = $('[data-value4M="未处理告警"]').html();
					value4M--;
					$('[data-value4M="未处理告警"]').html(value4M);
					var value4Y = $('[data-value4y="totalUncancel"]').html();
					value4Y--;
					$('[data-value4y="totalUncancel"]').html(value4Y);
					//刷新图表
					rajax('A');
				} else if (dataArea == pushDob.areaId) {
					//本月告警数据递减
					var value4M = $('[data-value4M="未处理告警"]').html();
					value4M--;
					$('[data-value4M="未处理告警"]').html(value4M);
					/*var invade4M = $('[data-value4M='+pushType+']').html();
                    invade4M--;
                    $('[data-value4M='+pushType+']').html(invade4M); */
					//告警数据从表单删除

					//刷新地图
					var cells = graph.getModel().cells;
					for (var i in cells) {
						if (graph.getLabel(cells[i]) == pushDob.posinfo) {
							graph.clearCellOverlays(cells[i]);
						};
					}
				}
				//提示告警信息
				////判断当前是否是最后一条告警
				var alarmCount = $("[data-Count=" + pushType + "]").html();
				alarmCount--;
				alarmCount = $("[data-Count=" + pushType + "]").html(alarmCount);
				var focusLi = $('[data-did=' + pushDob.id + ']');
				var focusUl = focusLi.parent('ul');
				if (focusLi.siblings('.disno').length != 0) {
					focusLi.siblings('.disno')[0].className = '';
				}
				focusLi.remove();
				var $li = focusUl.find('li');
				if ($li.length == 2) {
					focusUl.removeClass('dropdown-menu');
					focusUl.find('li').first().addClass('disno');
					focusUl.find('li').last().addClass('disno');
					$al.removeClass("redBling");
					$al.find('.redBreath').removeClass('redBreath');
					$("[data-Count=" + pushType + "]").remove()
				}
			}

		function freshDom(dd) {
			//根据告警归属区域
			var pushData = dd.split("|");
			var pushType = pushData[1],
				pushDob = eval('(' + pushData[2] + ')');
			if (pushDob.status == "success") {
				beAppDom('WARN_LOGIN');
				beAppDom('WARN_CALL');
				beAppDom('WARN_SMS');
			} else if (pushType == 'WARN_LOGIN' || pushType == 'WARN_CALL' || pushType == 'WARN_SMS') {
				appendDom(pushDob, pushType);
			} else if (pushType == 'CANCEL_WARN_1' || pushType == 'CANCEL_WARN_2' || pushType == 'CANCEL_WARN_3') {
				removeDom(pushDob, pushType);
			} else {}
		}
		$(".icon-trash").on('click', function() {
			html = "";
			for (var i = 0; i < 3; i++) {
				html += '<tr><td>&nbsp</td><td>&nbsp</td><td>&nbsp</td><td>&nbsp</td><td>&nbsp</td></tr>'
			}
			$(this).parents(".portlet").find("tbody").html(html);
		});
		//mainChart ajax 请求数据
		$('[data-btnType="A"]').on("click", function() {
			$(this).removeClass('black').addClass('green');
			$(this).siblings().removeClass("green").addClass("black");
			rajax('A');
		});
		$('[data-btnType="T"]').on("click", function() {
			$(this).removeClass('black').addClass('green');
			$(this).siblings().removeClass("green").addClass("black");
			rajax('T');
		});

		//opratorChart ajax 请求数据
		$.ajax({
			url: "cc.findCCdata.do",
			data: {
				ccType: "O" //A 分地区统计，T 分月份统计
			},
			success: function(content) {
				var xByM = content.xByM;
				var invadeYByM = content.invadeYByM;
				var callYByM = content.callYByM;
				var msgYByM = content.msgYByM;
				var data = new Array();
				for (var i in xByM) {
					if (invadeYByM[i] != 0) {
						data.push([xByM[i], invadeYByM[i]])
					}
				}
				$("#opratorChart").highcharts({
					chart: {
						type: 'bar'
					},
					title: {
						text: null
					},
					subtitle: {
						text: null
					},
					xAxis: {
						categories: ['非法手机', '非法通话', '非法短信']
					},
					yAxis: {
						min: 0,
						title: {
							text: null
						}
					},
					legend: {
						backgroundColor: '#FFFFFF',
						reversed: true
					},
					plotOptions: {
						series: {
							stacking: 'normal'
						}
					},
					series: [{
						name: xByM[2],
						data: [invadeYByM[2], callYByM[2], msgYByM[2]]
					}, {
						name: xByM[1],
						data: [invadeYByM[1], callYByM[1], msgYByM[1]]
					}, {
						name: xByM[0],
						data: [invadeYByM[0], callYByM[0], msgYByM[0]]
					}]
				});
			}
		});

		$('#myTab a').on('show', function() {
			var areaId = $(this).data("area");
			activeTab = areaId;
			var areaData;
			$.ajax({
				type: "GET",
				url: "getAreaData.do",
				data: {
					areaId: areaId
				},
				dataType: "json",
				success: function(result) {
					var str;
					for (var i = 0; i < 3; i++) {
						str += "<tr><td>&nbsp</td><td>&nbsp</td><td>&nbsp</td><td>&nbsp</td><td>&nbsp</td></tr>"
					}
					$("#recordTable tbody").html(str);
					areaData = result.data;
					areaInit(areaId, areaData);
				}
			});
		}); 
			<shiro:hasAnyRoles name="normal">
			$('#myTab a[href="#panel2"]').tab('show'); 
			</shiro:hasAnyRoles>

		function areaInit(areaId, areaData) {
			var str = '';
			$("[data-value4M='未处理告警']").html(areaData.unCancelCount);
			$("[data-value4M='WARN_LOGIN']").html(areaData.invadeList4MUD.length);
			$("[data-value4M='WARN_CALL']").html(areaData.callList4MU.length);
			$("[data-value4M='WARN_SMS']").html(areaData.msgList4MU.length);
			if (activeTab != "home") {
				//监区列表数据处理
				for (var i = 0; i < areaData.callMsgList.length; i++) {
					classType = areaData.callMsgList[i].classType;

					if (classType == "非法手机") {
						classType = '<i class="iconsp-mobile"></i>&nbsp非法手机';
					} else if (classType == "非法通话") {
						classType = '<i class="iconsp-call"></i>&nbsp非法通话';
					} else if (classType == "非法短信") {
						classType = '<i class="icon-envelope"></i>&nbsp非法短信';
					} else {
						classType = " ";
					}
					recordDate = areaData.callMsgList[i].ob.recordDate;
					recordDate = new Date(recordDate).Format("yyyy-MM-dd hh:mm:ss");
					imsi = areaData.callMsgList[i].ob.imsi;
					targNum = areaData.callMsgList[i].ob.targNum;
					if (targNum == undefined) {
						targNum = '';
					}
					posinfo = areaData.callMsgList[i].ob.posinfo;
					str += '<tr><td class="span2">' + classType + '</td><td>' + recordDate + '</td><td class="span3">' + imsi + '</td><td class="span3">' + targNum + '</td><td class="span4">' + posinfo + '</td></tr>';
					if (i < 3) {
						$("#recordTable tbody tr").first().remove();
					}
				}
				$("#recordTable tbody").prepend(str);
				//地图数据处理
				var mapdiv = document.getElementById("map");

				$("#map").empty();
				var invadeList = [];
				var callList = [];
				var msgList = [];
				var listLength;
				if (areaData.invadeList4MU.length != 0) {
					invadeList.push(areaData.invadeList4MU);
				}
				if (areaData.callList4MU.length != 0) {
					callList.push(areaData.callList4MU);
				}
				if (areaData.msgList4MU.length != 0) {
					msgList.push(areaData.msgList4MU);
				}

				listLength = areaData.invadeList4MU.length + areaData.callList4MU.length + areaData.msgList4MU.length;
				$.ajax({
					type: "GET",
					url: "mxeditors/getConfig4User.do",
					data: {
						areaId: areaId
					},
					dataType: "json",
					success: function(data) {
						if (data != null) {
							graph = MxMonitor.init(mapdiv, data);
							var cells = graph.getModel().cells;
							//加载地图告警
							/* for(var i in cells){
								if(graph.getLabel(cells[i])=='医务楼'){
									graph.setCellWarning(cells[i], '<b>非法用户:</b>:'+1111);
								};
							} */
							for (var i in cells) {
								for (var j = 0; j < invadeList.length; j++) {
									if (graph.getLabel(cells[i]) == invadeList[j][0].posinfo) {
										var invadeTime = invadeList[j][0].recordDate;
										invadeTime = new Date().Format("yyyy-MM-dd hh:mm:ss");
										graph.setCellWarning(cells[i], '<b>非法用户:</b>:手机号码为<b>' + invadeList[j][0].imsi + '</b>于<b>' + invadeTime + '</b>出现在<b>' + invadeList[j][0].posinfo + '</b>');
									};
								}
								for (var j = 0; j < callList.length; j++) {
									if (graph.getLabel(cells[i]) == callList[j][0].posinfo) {
										var callListTime = callList[j][0].recordDate;
										callListTime = new Date().Format("yyyy-MM-dd hh:mm:ss");
										graph.setCellWarning(cells[i], '<b>非法通话:</b>:手机号码为<b>' + callList[j][0].imsi + '</b>于<b>' + callListTime + '</b>出现在<b>' + callListList[j][0].posinfo + '</b>');
									};
								}
								for (var j = 0; j < msgList.length; j++) {
									if (graph.getLabel(cells[i]) == msgList[j][0].posinfo) {
										var msgTime = msgList[j][0].recordDate;
										msgTime = new Date().Format("yyyy-MM-dd hh:mm:ss");
										graph.setCellWarning(cells[i], '<b>非法短信:</b>:手机号码为<b>' + msgList[j][0].imsi + '</b>于<b>' + msgTime + '</b>出现在<b>' + msgList[j][0].posinfo + '</b>');
									};
								}
							}

						} else {
							$("#map").html("图配置数据加载错误，请联系我们！")
						}
						//初始化页面数据
					}
				});

			} else {
				//统计图刷新
				rajax('A');
				//全年数据刷新
				$("[data-value4Y='totalUncancel']").html(areaData.unCancelCountY);
				$("[data-value4Y='WARN_LOGIN']").html(areaData.invadeList4Y.length);
				$("[data-value4Y='WARN_CALL']").html(areaData.callList4Y.length);
				$("[data-value4Y='WARN_SMS']").html(areaData.msgList4Y.length);
				/* var  callList4Y = areaData.callList4Y.length,
				invadeList4Y = areaData.invadeList4Y.length,
				msgList4Y = areaData.msgList4Y.length,
				unCancelCountY = areaData.unCancelCountY.length */
				$("[data-value4M='未处理告警']").html(areaData.unCancelCountM);
				$("[data-value4M='WARN_LOGIN']").html(areaData.invadeList4MUD.length);
				$("[data-value4M='WARN_CALL']").html(areaData.callList4MU.length);
				$("[data-value4M='WARN_SMS']").html(areaData.msgList4MU.length);
			}

		}

		rajax('A');
	});
	</script>
	
	<!-- END JAVASCRIPTS -->
	<!-- WEBSOCKET END-->
	<jsp:include page="overlayPanel.jsp"></jsp:include>
	<!-- MXGRAGH MAP -->
	<script type="text/javascript">
	mxBasePath = 'mxgraph';//定义mxgraph根目录
</script>
	<script type="text/javascript" src="mxgraph/js/mxClient.js"></script>
	<script type="text/javascript"
		src="resources/js/frame/login/mxMonitor.js"></script>
</body>
</html>