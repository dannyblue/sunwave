<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
#dealWidthAlarmPanel {
	left: 33%;
	width: 80%;
}
</style>
<!-- Modal -->
<div class="modal fade " id="dealWidthAlarmPanel" class="modal hide fade"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="tools">
					<button data-dismiss="modal" class="close" type="button"></button>
				</div>
				<h4 class="modal-title ">解除告警</h4>
			</div>
			<div class="modal-body">
				<div class="alert alert-error hide">

				<button class="close" data-dismiss="alert"></button>

				</div>
				<form class="form-horizontal " id="dealWidthAlarmForm" >
					<div class="row-fluid">
					<div class="span8">
						<div class="control-group">
							<label class="control-label" for="dwAlermReason">解除原因:</label>
							<div class="controls">
								<input type="text" id="dwAlermReason" name="dwAlermReason"
									class="m-wrap span12" placeholder=""> <label
									id="dwAlermReason-error" class="error" for="dwAlermReason"></label>
							</div>
						</div>
					</div>
					<div class="span4">
						<div class="control-group">
							<label class="control-label" for="dwAlermPassword">解除密码:</label>
							<div class="controls">
								<input type="password" id="dwAlermPassword"
									name="dwAlermPassword" class="m-wrap span12" placeholder="">
								<label id="dwAlermPassword-error" class="error"
									for="dwAlermPassword"></label>
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
					onclick="javascript:$('#dealWidthAlarmForm').submit()">保存</button>
			</div>
		</div>
	</div>
</div>
<script>
	$('#dealWidthAlarmPanel').on('show', function(event) {
		var modal = $(this);
		if(dealWidthAlarmForm.showtype=="manage"){
			
		}else{
			
		}
	})
	$('#dealWidthAlarmPanel').on('hidden', function(event) {
		var modal = $(this);
		//重置表单数据和样式
		modal.find('.modal-body input[name=reset]').click();
		modal.find('.help-inline').remove();
		modal.find('.control-group').removeClass('error');
		modal.find('.control-group').removeClass('success');
		if(!modal.find('.alert alert-error').hasClass('hide')){
			modal.find('.alert alert-error').addClass('hide');
		}
		dealWidthAlarmForm.showtype=null;
		dealWidthAlarmForm.rowData=null;
	})
	var dealWidthAlarmForm = $('#dealWidthAlarmForm');
	dealWidthAlarmForm
			.validate({
				errorElement : 'span', //default input error message container
				errorClass : 'help-inline', // default input error message class
				focusInvalid : false, // do not focus the last invalid input
				ignore : "",
				rules : {},

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
					$.ajax({
						url : "warn.cancel.do",
						data : {
							id : dataAttr.did,
							otype : dataAttr.otype,
							cancelCause : $(form).find("[name=cancelCause]")
									.val(),
							cancelPassword : $(form).find(
									"[name=cancelPassword]").val()
						},
						method : "POST",
						success : function(result) {
							if (result.error) {
								App.info({
									title : "提示！",
									text : result.error
								})
							} else {
								//解除告警后处理
								$("#myModal").modal('hide');
								App.info({
									title : "提示！",
									text : "解除成功"
								})
							}
						},
						error : function() {
							App.info({
								title : "提示！",
								text : "请求失败"
							})
						}

					})
				}
			});
</script>