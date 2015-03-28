<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
#phonePanel {
	left: 33%;
	width: 80%;
}
</style>
<!-- Modal -->
<div class="modal fade " id="phonePanel" class="modal hide fade"
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
				<form class="form-horizontal " id="phoneForm" >
					<div class="row-fluid">
						<div class="span4">
							<div class="control-group">
								<label class="control-label">手机号码:</label>
								<div class="controls">
									<input name="phoneNum" type="text" class="m-wrap small" />
								</div>
							</div>
						</div>
						<div class="span4">
							<div class="control-group">
								<label class="control-label">归属区域:</label>
								<div class="controls">
									<select id="parentId" class="m-wrap" name="slArea.areaId">
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
						<div class="span4">
							<div class="control-group">
								<label class="control-label">归属人:</label>
								<div class="controls">
									<input name="ownerName" type="text" class="m-wrap small"/>
								</div>
							</div>
						</div>
						<div class="span4">
							<div class="control-group">
								<label class="control-label">运营商:</label>
								<div class="controls">
									<select class="m-wrap" name="operator">
											<option value="0">移动</option>
											<option value="1">联通</option>
											<option value="2">电信</option>
									</select>

								</div>
							</div>
						</div>
						<div class="span4">
							<div class="control-group">
								<label class="control-label">白名单:</label>
								<div class="controls">

									<div class="basic-toggle-button">

										<input name="lockBtn" type="checkbox" class="toggle" />

									</div>

								</div>
							</div>
						</div>
					</div>
					<input name="phoneId" type="text" class="hide"/>
					<input name="reset" type="reset" class="hide" />
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn default" data-dismiss="modal">取消</button>
				<button type="button" class="btn green"
					onclick="javascript:$('#phoneForm').submit()">保存</button>
			</div>
		</div>
	</div>
</div>
<script>
	var btn=$('.basic-toggle-button').toggleButtons({
		style: {
	        // Accepted values ["primary", "danger", "info", "success", "warning"] or nothing
	        enabled: "danger",
	        disabled: "info"
	    }
	});
	$('#phonePanel').on('show', function(event) {
		var modal = $(this);
		if(phoneForm.showtype=="update"){
			modal.find('.modal-title').text("【白名单】--修改");
			var data=phoneForm.rowdata;
			var input=modal.find('.modal-body input[type=text]')
			for(var i in input){
				if(data[input[i].name]!=null){
					input[i].value=data[input[i].name];
				}
			}
			
			if(data.isWhite==1){
				$(btn).toggleButtons("setState",true);
			}else{
				$(btn).toggleButtons("setState",false);
			}
			modal.find(".modal-body select[name='slArea.areaId']").val(data.slArea.areaId);
			modal.find(".modal-body select[name='operator']").val(data.operator);
		}else{
			modal.find('.modal-title').text("【白名单】--新增");
			$(btn).toggleButtons("setState",true);
		}
	})
	$('#phonePanel').on('hidden', function(event) {
		var modal = $(this);
		//重置表单数据和样式
		modal.find('.modal-body input[name=reset]').click();
		modal.find('.help-inline').remove();
		modal.find('.control-group').removeClass('error');
		modal.find('.control-group').removeClass('success');
		if(!modal.find('.alert alert-error').hasClass('hide')){
			modal.find('.alert alert-error').addClass('hide');
		}
		$(btn).toggleButtons("setState",false);
		phoneForm.showtype=null;
		phoneForm.rowData=null;
	})
	var phoneForm = $('#phoneForm');
	phoneForm
			.validate({
				errorElement : 'span', //default input error message container
				errorClass : 'help-inline', // default input error message class
				focusInvalid : false, // do not focus the last invalid input
				ignore : "",
				rules : {
					phoneNum : {
						required : true,
						number:true,
						minlength:15
					},
					ownerName : {
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
					var isWhite=$(btn).toggleButtons("status")==true?1:0;
					$('#phonePanel').find(".modal-body input[name='slArea.areaName']").val($('#phonePanel').find(".modal-body select[name='slArea.areaId'] option:selected").text());
					if(phoneForm.showtype=="update"){
						$.ajax({
							url : "surveillance.phone.update.do",
							type : 'POST',
							data : $('#phoneForm').serialize()+"&isWhite="+isWhite,
							success : function(result) {
								if(result.error!=null){
									App.info({
										title:"提示",
										text:result.error
									});
								}else{
									$('#phonePanel').modal('hide');
									dataGrid.updateRow(result.data,phoneForm.rowIndex);
									App.info({
										title:"提示",
										text:"区域保存成功"
									});
								}
							},
							error:function(v){
							}
						});
					}else{
						$.ajax({
							url : "surveillance.phone.save.do",
							type : 'POST',
							data : $('#phoneForm').serialize()+"&isWhite="+isWhite,
							success : function(result) {
								if(result.error!=null){
									App.info({
										title:"提示",
										text:result.error
									});
								}else{
									$('#phonePanel').modal('hide');
									dataGrid.addRow(result.data,phoneForm.rowIndex);
									App.info({
										title:"提示",
										text:"区域保存成功"
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