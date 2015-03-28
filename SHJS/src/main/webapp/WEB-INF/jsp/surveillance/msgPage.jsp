<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="el" uri="/eltag"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->

<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->

<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->

<!-- BEGIN HEAD -->

<head>

<meta charset="utf-8" />

<title>非法短信</title>

<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<meta content="" name="description" />

<meta content="" name="author" />

<!-- INCLUDE GLOBAL STYLES -->
	<jsp:include page="../BaseGStyle.jsp"></jsp:include>

<!-- BEGIN PAGE LEVEL STYLES -->
<!-- MMGRID STYLES -->
<link rel="stylesheet" type="text/css" href="resources/css/frame/mmGrid/mmGrid.css" />
<link rel="stylesheet" type="text/css" href="resources/css/frame/mmGrid/mmGrid-bootstrap.css" />
<link rel="stylesheet" type="text/css" href="resources/css/frame/mmGrid/mmPaginator.css" />
<link rel="stylesheet" type="text/css" href="resources/css/frame/mmGrid/mmPaginator-bootstrap.css" />

<!-- END PAGE LEVEL STYLES -->

<!-- <link rel="shortcut icon" href="media/image/favicon.ico" /> -->

</head>

<!-- END HEAD -->

<!-- BEGIN BODY -->

<body class="page-header-fixed page-sidebar-closed">

	<!-- HEADER INCLUDE-->
	<jsp:include page="../BaseHead.jsp"></jsp:include>
	

	<!-- BEGIN CONTAINER -->
	<div class="page-container" id="pageBox">

		<!-- INCLUDE SIDEBAR -->
		<jsp:include page="../BaseSideBar.jsp"></jsp:include>

		<div class="page-content">

			<!-- BEGIN PAGE CONTAINER-->

			<div class="container-fluid" id="mainPage">

				<!-- BEGIN PAGE HEADER-->

				<div class="row-fluid">

					<div class="span12">

						<!-- INCLUDE STYLE CUSTOMIZER -->
						<%-- <jsp:include page="../BaseStyleConfig.jsp"></jsp:include>  --%>

						<!-- BEGIN PAGE TITLE & BREADCRUMB-->

						<!-- <h3 class="page-title">

							非法手机
							<small> 新增 删除 异常处理</small>

						</h3> -->

						<ul class="breadcrumb">

							<li><i class="icon-home"></i> <a href="index.do">首页</a> <i
								class="icon-angle-right"></i></li>

							<li><a href="#">侦测记录</a> <i class="icon-angle-right"></i></li>

							<li><a href="#top">非法短信</a></li>

						</ul>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<!-- END PAGE HEADER-->

				<div id="dashboard">

					<!-- BEGIN DASHBOARD STATS -->

					<div class="row-fluid">

						<div class="span12">

							<!-- BEGIN EXAMPLE TABLE PORTLET-->

							<div class="portlet box blue">

								<div class="portlet-title">

									<div class="caption">
										<i class="icon-edit"></i>非法短信
									</div>

									<div class="actions">
										<a data-op="empty" class="btn red"><i class="icon-trash"></i>&nbsp&nbsp删除</a>
									</div>

								</div>

								<div class="portlet-body">
									<div class="row-fluid clearfix">
										<form class="span12" action="javascript:0" id="portlet-search">
											<div class="controls span2">
												<label class="control-label" for="q_imsi">手机:</label>
												<input type="text" class="m-wrap small" name="imsi" value="" id="q_imsi">
											</div>
											<div class="controls span2">
													<label class="control-label" for="q_posinfo">位置:</label>
													<input type="text" class="m-wrap small" name="posinfo" value="" id="q_posinfo">
											</div>
											<div class="controls span6">
													<label class="control-label" for="q_recordDateS">时间:</label>
													<input type="text" id="q_recordDateS" name="recordDateS" class="m-wrap medium" place-holder="请选择.." onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'q_recordDateE\')||\'2020-10-01\'}'})" />
													<label class="control-label" for="q_recordDateE">--</label>
													<input type="text" id="q_recordDateE" name="recordDateE" class="m-wrap medium" place-holder="请选择.." onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'q_recordDateS\')}',maxDate:'2020-10-01'})" />
											</div>
											<div class="controls span2">
													<button class="btn blue pull-right span12" id="btnSearch" style="padding:0;">搜索</button>
											</div>
										</form>
									</div>
									<div class="clearfix">

										<table id="mmg" class="mmg">
											<tr>
												<th rowspan="" colspan=""></th>
											</tr>
										</table>

										<div id="pg" style="text-align: right;"></div>

									</div>

								</div>

							</div>

							<!-- END EXAMPLE TABLE PORTLET-->

						</div>

					</div>

					<!-- END PAGE CONTENT -->

				</div>

				<!-- END PAGE CONTAINER-->

			</div>

			<!-- END PAGE -->

		</div>
	</div>

	<!-- END SIDEBAR -->

		<!-- BEGIN PAGE -->

		

		<!-- END CONTAINER -->

		<!-- FOOTER INCLUDE -->
		<jsp:include page="../BaseFooter.jsp"></jsp:include>

		<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->

		<!-- GJS INCLUDE -->
		<jsp:include page="../BaseGJs.jsp"></jsp:include>

		<!-- BEGIN PAGE LEVEL PLUGINS -->

		<!-- MMGRID -->
		<script src="resources/js/frame/mmGrid/mmGrid.js"></script>
		<script src="resources/js/frame/mmGrid/mmPaginator.js"></script>

		<!-- END PAGE LEVEL PLUGINS -->

		<!-- BEGIN PAGE LEVEL SCRIPTS -->

		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		
		<!-- END PAGE LEVEL SCRIPTS -->

		<script>
			var dataGrid;
			var dataAttr = {};
			function beAppDom(type){
				if($("[data-type="+type+"]").hasClass('redBling')){
					$("[data-type="+type+"] .dropdown-menu li").first().removeClass("disno");
					$("[data-type="+type+"] .dropdown-menu li").last().removeClass("disno");
				}else{
					$("[data-type="+type+"] .dropdown-menu li").first().addClass("disno");
					$("[data-type="+type+"] .dropdown-menu li").last().addClass("disno");
				}
			}
			jQuery(document)
					.ready(
							function() {

								App.init(); // initlayout and core plugins
								beAppDom('WARN_LOGIN');
								beAppDom('WARN_CALL');
								beAppDom('WARN_SMS');
								//激活menu
								$(".page-sidebar-menu li").removeClass("active");
								$("#menu_record").addClass("active");
								//mmgrid
								var state="";
								//使用SQL查询，sortName 与数据库字段必须一致
				                var cols = [
				                    { title:'告警时间', name:'recordDate' ,width:150, align:'center', sortable: true,sortName:'record_date', type: 'number',renderer: function(val){
				                		var date = new Date(val).Format('yyyy-MM-dd hh:mm:ss');
				                    	return  	date
				                    }
				                    },{ title:'运营商', name:'operator' ,width:100, align:'center', sortable: true,sortName:'operator',renderer: function(val){
				                    	switch (val){
					                    	case 0 :
					                    		return '移动';
					                    	case 1 :
					                    		return '联通';
					                    	case 2 :
					                    		return '电信';
					                    	default :
					                    		return '';
				                    	}
				                    }
				                    },{ title:'卡号', name:'imsi' ,width:100, align:'center', sortable: true,sortName:'imsi', type: 'number'
				                    },{ title:'对方号码', name:'targNum' ,width:100, align:'center', sortable: true,sortName:'targ_num', type: 'number'
				                    },{ title:'区域位置', name:'posinfo' ,width:100, align:'center', sortable: true,sortName:'posinfo'
				                    },{ title:'归属监狱', name:'areaName' ,width:100, align:'center', sortable: true,sortName:'posinfo', renderer: function(val,item,rowIndex){
				                        return item.slArea.areaName;
				                    	}
				                    },{ title:'处理状态', name:'cancelState' ,width:100, align:'center', sortable: true,sortName:'cancel_state',renderer: function(val){
				                    	state = val;
				                    	switch (val){
				                    	case 0 :
				                    		return '未处理';
				                    	case 1 :
				                    		return '已处理';
				                    	default :
				                    		return '';
				                	}
				                }
				                    },{ title:'操作', name:'' ,width:200, align:'center', lockWidth:true, lockDisplay: true, renderer: function(val){
				                        	
				                         	switch (state){
					                    	case 0 :
					                    		return  '<button href="#checkMailPanel" data-toggle="modal" data-op="view"  class="btn blue">内容</button>&nbsp&nbsp<button href="#dealWidthAlarmPanel" data-toggle="modal" data-op="manage"  class="btn green">处理</button>&nbsp&nbsp<button data-op="delete"  class="btn red">删除</button>';
					                    	case 1 :
					                    		return  '<button href="#checkMailPanel" data-toggle="modal" data-op="view"  class="btn blue">内容</button>&nbsp&nbsp<button data-op="delete"  class="btn red">删除</button>';	
					                    	default :
					                    		return '';
					                    	}
				                    
				                    
				                    }}
				                ];

								var mmg = $('.mmg').mmGrid({
									height : 450,
									cols : cols,
									url : 'surveillance.msg.getList.do',
									method : 'post',
									remoteSort : true,
									root : 'data',
									sortName : 'record_date',
									sortStatus : 'desc',
									multiSelect : true,
									checkCol : true,
									fullWidthRows : true,
									autoLoad : true,
									plugins : [ $('#pg').mmPaginator({}) ],
									params : function() {
										//如果这里有验证，在验证失败时返回false则不执行AJAX查询。
										return {
											page : 1,
											imsi : $('#q_imsi').val(),
											posinfo : $('#q_posinfo').val(),
											recordDateS : $('#q_recordDateS').val(),
											recordDateE : $('#q_recordDateE').val()
										}
									}
								});

								mmg
										.on(
												'cellSelected',
												function(e, item, rowIndex,
														colIndex) {
													//查看
													if ($(e.target).data("op")=="view") {
														e.stopPropagation(); //阻止事件冒泡
														console.log($(this))
														checkMailForm.showtype="view";
														checkMailForm.rowdata=item;
														checkMailForm.rowIndex=rowIndex;
													}
													//修改
													else if($(e.target).data("op")=="update"){
														e.stopPropagation(); //阻止事件冒泡
													}
													else if($(e.target).data("op")=="manage"){
														e.stopPropagation();
														//成功后进行的操作
													}
													else if ($(e.target).data("op")=="delete"
															&& confirm('你确定要删除该行记录吗?')) {
														e.stopPropagation(); //阻止事件冒泡
														$
																.ajax({
																	url : 'surveillance.msg.remove.do',
																	type : 'post',
																	dataType : 'json',
																	data:{ids:item.msgId},
																	success : function(
																			v) {
																		mmg
																				.removeRow(rowIndex);
																		App
																				.info({
																					title : "提示",
																					text : "删除成功"
																				});
																	}
																});
													}
												})
										.on('loadSuccess', function(e, data) {
											//这个事件需要在数据加载之前注册才能触发
										})
										.on(
												'rowInserted',
												function(e, item, index) {
												}).on(
												'rowUpdated',
												function(e, oldItem, newItem,
														index) {
												}).on(
												'rowRemoved',
												function(e, item, index) {
												});

								$('[data-op="empty"]')
										.on(
												'click',
												function(e) {
													var selectedIndexes = mmg
															.selectedRowsIndex();
													var selectedRows = mmg
															.selectedRows();
													var ids = "";
													for ( var i in selectedRows) {
														ids += selectedRows[i].msgId
																+ ",";
													}
													if (ids != ""
															&& confirm("你确定要删除这些记录吗?")) {
														e.stopPropagation(); //阻止事件冒泡
														ids = ids.substring(0,
																ids.length - 1);
														$
																.ajax({
																	url : "surveillance.msg.remove.do",
																	method : "post",
																	dataType : "json",
																	data : {
																		ids : ids
																	},
																	success : function(
																			v) {
																		mmg
																				.removeRow(selectedIndexes);
																		App
																				.info({
																					title : "提示",
																					text : "删除成功"
																				});
																	}
																})
													} else {
														return false;
													}
												});


								//搜索
								$('#btnSearch').on('click', function() {
									//点击查询时页码置为1
									mmg.load();
								});
								
								//赋值给全局变量
								dataGrid = mmg;
								
								//验证表单
								
			$("#confirmationFormMini")
										.validate(
												{
													/* debug:true, */
													rules : {
														miniReason : {
															required : true,
															minlength:6
														},
														miniPassword : {
															required : true
														}
													}
												});
							});
		</script>

		<!-- END JAVASCRIPTS -->
		
		<!-- BEGIN  MODAL FORM-->

		<jsp:include page="../overlayPanel.jsp"></jsp:include>
		<jsp:include page="dealWithAlermForm.jsp"></jsp:include>
		<jsp:include page="checkMailForm.jsp"></jsp:include>
		<!-- END  MODAL FORM-->
</body>
<!-- END BODY -->
</html>