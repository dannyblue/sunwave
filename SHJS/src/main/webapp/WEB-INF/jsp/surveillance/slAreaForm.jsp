<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
#sysAreaPanel {
	left: 33%;
	width: 80%;
}
</style>
<!-- Modal -->
<div class="modal fade " id="sysAreaPanel" class="modal hide fade"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="tools">
					<button data-dismiss="modal" class="close" type="button"></button>
				</div>
				<h4 class="modal-title ">区域</h4>
			</div>
			<div class="modal-body">
				<div class="alert alert-error hide">

				<button class="close" data-dismiss="alert"></button>

				</div>
				<form class="form-horizontal " id="sysAreaForm" >
					<div class="row-fluid">
						<div class="span4">
							<div class="control-group">
								<label class="control-label">区域名称:</label>
								<div class="controls">
									<input name="areaName" type="text" class="m-wrap small" />
								</div>
							</div>
						</div>
						<div class="span4">
							<div class="control-group">
								<label class="control-label">上级区域:</label>
								<div class="controls">
									<select id="parentId" class="m-wrap" name="slArea.areaId">
										<c:forEach items="${initData.areaSet}" var="item"
											varStatus="status">
											<option value="${item.areaId}">${item.areaName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span4">
							<div class="control-group">
								<label class="control-label">同级排序:</label>
								<div class="controls">
									<input name="areaOrder" type="text" class="m-wrap small"/>
								</div>
							</div>
						</div>
					</div>
					<input name="areaId" type="text" class="hide"/>
					<input name="areaGrade" type="text" class="hide"/>
					<input name="areaType" type="text" class="hide"/>
					<input name="reset" type="reset" class="hide" />
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn default" data-dismiss="modal">取消</button>
				<button type="button" class="btn green"
					onclick="javascript:$('#sysAreaForm').submit()">保存</button>
			</div>
		</div>
	</div>
</div>
<script>
	$('#sysAreaPanel').on('show', function(event) {
		var modal = $(this);
		if(sysAreaForm.showtype=="update"){
			modal.find('.modal-title').text("【区域】--修改");
			var data=sysAreaForm.rowdata;
			var input=modal.find('.modal-body input[type=text]')
			for(var i in input){
				if(data[input[i].name]!=null){
					input[i].value=data[input[i].name];
				}
			}
			if(data.sysArea!=null){
				modal.find('.modal-body select[name="sysArea.areaId"]').val(data.sysArea.areaId);
			}
		}else{
			modal.find('.modal-title').text("【区域】--新增");
		}
	})
	$('#sysAreaPanel').on('hidden', function(event) {
		var modal = $(this);
		//重置表单数据和样式
		modal.find('.modal-body input[name=reset]').click();
		modal.find('.help-inline').remove();
		modal.find('.control-group').removeClass('error');
		modal.find('.control-group').removeClass('success');
		if(!modal.find('.alert alert-error').hasClass('hide')){
			modal.find('.alert alert-error').addClass('hide');
		}
		sysAreaForm.showtype=null;
		sysAreaForm.rowData=null;
	})
	var sysAreaForm = $('#sysAreaForm');
	sysAreaForm
			.validate({
				errorElement : 'span', //default input error message container
				errorClass : 'help-inline', // default input error message class
				focusInvalid : false, // do not focus the last invalid input
				ignore : "",
				rules : {
					areaName : {
						required : true
					},
					areaOrder : {
						required : true,
						digits : true
					}
				},

				invalidHandler : function(event, validator) { //display error alert on form submit              
				},

				highlight : function(element) { // hightlight error inputs
					$(element).closest('.help-inline').removeClass('ok'); // display OK icon
					$(element).closest('.control-group').removeClass('success')
							.addClass('error'); // set error class to the control group
				},

				unhighlight : function(element) { // revert the change dony by hightlight
					$(element).closest('.control-group').removeClass('error'); // set error class to the control group
				},

				success : function(label) {
					label.addClass('valid').addClass('help-inline ok') // mark the current input as valid and display OK icon
					.closest('.control-group').removeClass('error').addClass(
							'success'); // set success class to the control group
				},

				submitHandler : function(form) {
					if(sysAreaForm.showtype=="update"){
						$.ajax({
							url : "surveillance.slArea.update.do",
							type : 'POST',
							data : $('#sysAreaForm').serialize(),
							success : function(v) {
								$('#sysAreaPanel').modal('hide');
								dataGrid.load();
								App.info({
									title:"提示",
									text:"区域保存成功"
								});
							},
							error:function(v){
							}
						});
					}else{
						$.ajax({
							url : "surveillance.slArea.save.do",
							type : 'POST',
							data : $('#sysAreaForm').serialize(),
							success : function(v) {
								$('#sysAreaPanel').modal('hide');
								dataGrid.load();
								App.info({
									title:"提示",
									text:"区域保存成功"
								});
							},
							error:function(v){
							}
						});
					}
					
				}
			});
</script>