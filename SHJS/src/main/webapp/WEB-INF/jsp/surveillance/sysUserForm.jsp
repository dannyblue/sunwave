<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
#sysUserPanel {
	left: 33%;
	width: 80%;
}
</style>
<!-- Modal -->
<div class="modal fade " id="sysUserPanel" class="modal hide fade"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="tools">
					<button data-dismiss="modal" class="close" type="button"></button>
				</div>
				<h4 class="modal-title ">用户</h4>
			</div>
			<div class="modal-body">
				<div class="alert alert-error hide">

				<button class="close" data-dismiss="alert"></button>

				</div>
				<form class="form-horizontal " id="sysUserForm" >
					<div class="row-fluid">
						<div class="span3">
							<div class="control-group">
								<label class="control-label">用户名称:</label>
								<div class="controls">
									<input name="username" type="text" class="m-wrap small" />
								</div>
							</div>
						</div>
						<div class="span3">
							<div class="control-group">
								<label class="control-label">显示名称:</label>
								<div class="controls">
									<input name="userShowName" type="text" class="m-wrap small" />
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
								<label class="control-label">归属单位:</label>
								<div class="controls">
									<select class="m-wrap" name="slArea.areaId" id="q_areaId">
										<c:forEach items="${initData.areaSet}" var="item"
											varStatus="status">
											<option value="${item.areaId}">${item.areaName}</option>
										</c:forEach>
									</select>
									<input type="hidden" name="slArea.areaName"/>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span6">
								<div class="control-group">
									<label class="control-label">角色:</label>
									<div class="controls">
										<select name="sysRole" data-placeholder="请选择" multiple="multiple" tabindex="6">

										</select>
									</div>
								</div>
							</div>
						<div class="span3">
							<div class="control-group">
								<label class="control-label">锁定:</label>
								<div class="controls">

									<div class="basic-toggle-button">

										<input name="lockBtn" type="checkbox" class="toggle" />

									</div>

								</div>
							</div>
						</div>
					</div>
					<input name="id" type="text" class="hide"/>
					<input name="reset" type="reset" class="hide" />
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn default" data-dismiss="modal">取消</button>
				<button type="button" class="btn green"
					onclick="javascript:$('#sysUserForm').submit()">保存</button>
			</div>
		</div>
	</div>
</div>
<script>
	var selects = $('#sysUserPanel').find(".modal-body select");
	for(var i=0;i<selects.length;i++){
		var select = selects[i];
		if(select.name!=null&&GlobalData[select.name]!=null){
			for(var j in GlobalData[select.name]){
				var data=GlobalData[select.name][j];
				$(select).append("<option value="+data[0]+">"+data[1]+"</option>");
			}
		}
	}
	var btn=$('.basic-toggle-button').toggleButtons({
		style: {
	        // Accepted values ["primary", "danger", "info", "success", "warning"] or nothing
	        enabled: "danger",
	        disabled: "info"
	    }
	});
	$('#sysUserPanel').on('show', function(event) {
		var modal = $(this);
		var modal = $(this);
		if(sysUserForm.showtype=="update"){
			modal.find('.modal-title').text("【用户】--修改");
			var data=sysUserForm.rowdata;
			var input=modal.find('.modal-body input[type=text]')
			for(var i in input){
				if(data[input[i].name]!=null){
					input[i].value=data[input[i].name];
				}
			}
			if(data.roleId!=null){
				modal.find(".modal-body select[name=sysRole] option").each(function(){
					if((","+data.roleId+",").indexOf(","+$(this).val()+",")!=-1){
						$(this).attr("selected","selected");
					}
		        });
				modal.find(".modal-body select[name=sysRole]").trigger("liszt:updated");
				modal.find(".modal-body select[name=sysRole]").chosen();
			}
			if(data.locked==1){
				$(btn).toggleButtons("setState",true);
			}else{
				$(btn).toggleButtons("setState",false);
			}
			modal.find(".modal-body select[name='slArea.areaId']").val(data.slArea.areaId);
		}else{
			modal.find('.modal-title').text("【用户】--新增");
			$('#sysUserPanel').find(".modal-body select[name=sysRole]").chosen();
		}
	})
	$('#sysUserPanel').on('hidden', function(event) {
		var modal = $(this);
		//重置表单数据和样式
		modal.find('.modal-body input[name=reset]').click();
		modal.find('.help-inline').remove();
		modal.find('.control-group').removeClass('error');
		modal.find('.control-group').removeClass('success');
		if(!modal.find('.alert alert-error').hasClass('hide')){
			modal.find('.alert alert-error').addClass('hide');
		}
		$('#sysUserPanel').find(".modal-body select[name=sysRole] option:selected").each(function(){
				$(this).removeAttr("selected");
        });
		$('#sysUserPanel').find(".modal-body select[name=sysRole]").trigger("liszt:updated");
		$('#sysUserPanel').find(".modal-body select[name=sysRole]").chosen();
		$(btn).toggleButtons("setState",false);
		sysUserForm.showtype=null;
		sysUserForm.rowData=null;
	})
	var sysUserForm = $('#sysUserForm');
	sysUserForm
			.validate({
				errorElement : 'span', //default input error message container
				errorClass : 'help-inline', // default input error message class
				focusInvalid : false, // do not focus the last invalid input
				ignore : "",
				rules : {
					username : {
						required : true
					},
					userShowName : {
						required : true
					},
					sysRole:{
						required : true
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
					var roleId="";
					var select = $('#sysUserPanel').find(".modal-body select[name=sysRole] option:selected").each(function(){
			            roleId+=$(this).val()+",";
			        });
					roleId=roleId.substring(0,roleId.length-1);
					var locked=$(btn).toggleButtons("status")==true?1:0;
					if(sysUserForm.showtype=="update"){
						$('#sysUserPanel').find(".modal-body input[name='slArea.areaName']").val($('#sysUserPanel').find(".modal-body select[name='slArea.areaId'] option:selected").text());
						$.ajax({
							url : "surveillance.sysUser.update.do",
							type : 'POST',
							data : $('#sysUserForm').serialize()+"&roleIdQ="+roleId+"&locked="+locked,
							success : function(result) {
								if(result.error!=null){
									App.info({
										title:"提示",
										text:result.error
									});
								}else{
									$('#sysUserPanel').modal('hide');
									dataGrid.updateRow(result.data,sysUserForm.rowIndex);
									App.info({
										title:"提示",
										text:"用户保存成功"
									});
								}
							},
							error:function(v){
							}
						});
					}else{
						$.ajax({
							url : "surveillance.sysUser.save.do",
							type : 'POST',
							data : $('#sysUserForm').serialize()+"&roleIdQ="+roleId+"&locked="+locked,
							success : function(result) {
								if(result.error!=null){
									App.info({
										title:"提示",
										text:result.error
									});
								}else{
									$('#sysUserPanel').modal('hide');
									dataGrid.load();
									App.info({
										title:"提示",
										text:"用户保存成功"
									});
								}
							},
							error:function(v){
							}
						});
					}
					
				}
			});
</script>