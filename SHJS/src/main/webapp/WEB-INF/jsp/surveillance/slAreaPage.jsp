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

<title>非法手机</title>

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

							<li><a href="#">系统</a> <i class="icon-angle-right"></i></li>

							<li><a href="#top">监区管理</a></li>

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
										<i class="icon-edit"></i>监区列表
									</div>

									<div class="actions">
										<a data-op="add" class="btn green" data-toggle="modal" href="#sysAreaPanel"><i class="icon-plus"></i>&nbsp&nbsp新增</a>
										<a data-op="empty" class="btn red"><i class="icon-trash"></i>&nbsp&nbsp删除</a>
									</div>

								</div>

								<div class="portlet-body">
									<div class="row-fluid clearfix">
										<form class="span12" action="javascript:" id="portlet-search">
											<div class="span3">
												<label class="control-label" for="q_nserie">监区名称:</label>
												<input type="text" class="m-wrap small" id="q_areaName"
															name="areaName" value="">
											</div>
											<div class="span3">
												<button class="span12 btn blue pull-right"  id="btnSearch" style="padding:0;">搜索</button>
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
			var publicRowIndex = '';
			jQuery(document)
					.ready(
							function() {

								App.init(); // initlayout and core plugins
								//激活menu
								$(".page-sidebar-menu li").removeClass("active");
								$("#menu_system").addClass("active");
								//mmgrid
								var state="";
								//使用SQL查询，sortName 与数据库字段必须一致
								var cols = [
										{
											title : '区域名称',
											name : 'areaName',
											width : 100,
											align : 'center',
											sortable : true,
											sortName : 'area_Name'
										},{
											title : '上级区域',
											name : '',
											width : 100,
											align : 'center',
											sortable : true,
											sortName : 'parent_id',
											renderer : function(val, record, index) {
												return record.slArea==null?null:record.slArea.areaName;
											}
										},
										{
											title : 'X坐标',
											name : 'gpsX',
											hidden:true,
											width : 100,
											align : 'center',
											sortable : true,
											sortName : 'gps_X'
										},
										{
											title : 'Y坐标',
											name : 'gpsY',
											hidden:true,
											width : 100,
											align : 'center',
											sortable : true,
											sortName : 'gps_Y'
										},
										{
											title : '同级排序',
											name : 'areaOrder',
											width : 100,
											align : 'center',
											sortable : true,
											sortName : 'area_Order'
										},
										{
											title : '操作',
											name : '',
											width : 150,
											align : 'center',
											lockWidth : true,
											lockDisplay : true,
											renderer : function(val, record, index) {
												if(record.areaId!=1){
													return '<button data-op="update"  class="btn blue" data-toggle="modal" href="#sysAreaPanel">修改</button> <button data-op="delete"  class="btn red">删除</button>'
												}else{
													return "<span class=\"label label-important\">无权限操作！</span>";
												}
											}
										}];

								var mmg = $('.mmg').mmGrid({
									height : 450,
									cols : cols,
									url : 'surveillance.slArea.getList.do',
									method : 'post',
									remoteSort : true,
									root : 'data',
									sortName : 'area_id',
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
											areaName : $('#q_areaName').val()
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
													}
													//修改
													else if($(e.target).data("op")=="update"){
														e.stopPropagation(); //阻止事件冒泡
														sysAreaForm.showtype="update";
														sysAreaForm.rowdata=item;
														sysAreaForm.rowIndex=rowIndex;
													}
													else if($(e.target).data("op")=="manage"){
														e.stopPropagation();
														//成功后进行的操作
														publicRowIndex = rowIndex;
														
													}
													else if ($(e.target).data("op")=="delete"
															&& confirm('你确定要删除该行记录吗?')) {
														e.stopPropagation(); //阻止事件冒泡
														$
																.ajax({
																	url : 'surveillance.slArea.remove.do',
																	type : 'post',
																	dataType : 'json',
																	data:{ids:item.areaId},
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
														ids += selectedRows[i].areaId
																+ ",";
													}
													if (ids != ""
															&& confirm("你确定要删除这些记录吗?")) {
														e.stopPropagation(); //阻止事件冒泡
														ids = ids.substring(0,
																ids.length - 1);
														$
																.ajax({
																	url : "surveillance.slArea.remove.do",
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
								
		});
		</script>

		<!-- END JAVASCRIPTS -->
		
		<!-- BEGIN  MODAL FORM-->
		<jsp:include page="../overlayPanel.jsp"></jsp:include>
		<jsp:include page="slAreaForm.jsp"></jsp:include>
		<!-- END  MODAL FORM-->
</body>
<!-- END BODY -->
</html>