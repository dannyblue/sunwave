<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
#checkMailPanel {
	left: 33%;
	width: 80%;
}
</style>
<!-- Modal -->
<div class="modal fade " id="checkMailPanel" class="modal hide fade"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<div class="tools">
					<button data-dismiss="modal" class="close" type="button"></button>
				</div>
				<h4 class="modal-title ">查看内容</h4>
			</div>
			<div class="modal-body">
				<div class="alert alert-error hide">

				<button class="close" data-dismiss="alert"></button>

				</div>
				<form class="form-horizontal " id="checkMailForm" >
					<div class="row-fluid">
					<div class="span12">
						<div class="control-group">
							<label class="control-label">短信内容:</label>
							<div class="controls" data-panel="mailContent"></div>
						</div>
					</div>
				</div>
					<input name="phoneId" type="text" class="hide"/>
					<input name="reset" type="reset" class="hide" />
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn default" data-dismiss="modal">确认</button>
			</div>
		</div>
	</div>
</div>
<script>
	$('#checkMailPanel').on('show', function(event) {
		var modal = $(this);
		var data = checkMailForm.rowdata;
		if(data.msgContent!=''){
			$("[data-panel='mailContent']").html(data.msgContent);	
		}else{
			$("[data-panel='mailContent']").html(' ');
		}
		
	})
	$('#checkMailPanel').on('hidden', function(event) {
		var modal = $(this);
		//重置表单数据和样式
		modal.find('.modal-body input[name=reset]').click();
		modal.find('.help-inline').remove();
		modal.find('.control-group').removeClass('error');
		modal.find('.control-group').removeClass('success');
		if(!modal.find('.alert alert-error').hasClass('hide')){
			modal.find('.alert alert-error').addClass('hide');
		}
		checkMailForm.showtype=null;
		checkMailForm.rowData=null;
	})
	var checkMailForm = $('#checkMailForm');
	checkMailForm
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
					
				}
			});
</script>