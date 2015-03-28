<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="myModal" data-otype='0' class="modal hide fade" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true"></button>
		<h3 id="myModalLabel">标题</h3>
	</div>
	<form id="confirmationFormNormal" action="#" class="form-horizontal"
		data-form="">
		<div class="modal-body">
			<h3 class="form-section" data-display="true">告警信息</h3>
			<div class="row-fluid info" data-display="true">
				<div class="span4">
					<div class="control-group">
						<label class="control-label">设备号码:</label>
						<div class="controls" data-panel="phoneNo"></div>
					</div>
				</div>
				<div class="span4">
					<div class="control-group">
						<label class="control-label">位置区域:</label>
						<div class="controls" data-panel="area"></div>
					</div>
				</div>
				<div class="span4">
					<div class="control-group">
						<label class="control-label">时间:</label>
						<div class="controls" data-panel="date"></div>
					</div>
				</div>
			</div>
			<div class="row-fluid info" data-display="true">
				<div class="span4">
					<div class="control-group">
						<label class="control-label">运营商:</label>
						<div class="controls" data-panel="operator"></div>
					</div>
				</div>
				<div class="span4 ">
					<div class="control-group">
						<label class="control-label">设备编号:</label>
						<div class="controls" data-panel="cardNo"></div>
					</div>
				</div>

			</div>
			<div class="row-fluid info" data-display="true">
				<div class="span4">
					<div class="control-group">
						<label class="control-label">对方号码:</label>
						<div class="controls" data-panel="callPhone"></div>
					</div>
				</div>
				<div class="span8">
					<div class="control-group">
						<label class="control-label">信息内容:</label>
						<div class="controls" data-panel="content"></div>
					</div>
				</div>
			</div>
			<h3 class="form-section">解除告警</h3>
			<div class="row-fluid">
				<div class="span8">
					<div class="control-group">
						<label class="control-label" for="relieveReason">解除原因:</label>
						<div class="controls">
							<input type="text" id="relieveReason" name="cancelCause"
								class="m-wrap span12" placeholder=""> <label
								id="relieveReason-error" class="error" for="relieveReason"></label>
						</div>
					</div>
				</div>
				<div class="span4">
					<div class="control-group">
						<label class="control-label" for="cancelCause">解除密码:</label>
						<div class="controls">
							<input type="password" id="relievePassword" name="cancelPassword"
								class="m-wrap span12" placeholder=""> <label
								id="relievePassword-error" class="error" for="relievePassword"></label>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-primary" data-dismiss="modal"
				aria-hidden="true">取消</button>
			<input class="submit btn green" id="relieveSubmit" type="submit"
				value="解除" data-op="update" />
		</div>
	</form>
</div>
<script>
	$("[data-optype]").on("click", function() {
		var val = $(this).data('optype');
		var li = $(this).parent('li');
		dataAttr.otype = val;
		dataAttr.info = li.data('info');
		dataAttr.did = li.data('did');
		dataAttr.imsi = li.data('imsi');
		dataAttr.date = li.data('date');
		dataAttr.posinfo = li.data('posinfo');
		dataAttr.operator = li.data('operator');
		dataAttr.name = li.data('name');
		dataAttr.loginimei = li.data('loginimei');
		dataAttr.targnum = li.data('targnum');
		dataAttr.content = li.data('content');
	});

	$('#myModal')
			.on(
					'show',
					function() {
						var val = dataAttr.otype;
						$('[data-panel="phoneNo"]').html(dataAttr.imsi);
						$('[data-panel="area"]').html(dataAttr.posinfo);
						$('[data-panel="date"]').html(dataAttr.date);
						$('[data-panel="operator"]').html(
								operatorType(dataAttr.operator));
						$("#relieveReason").val("");
						$("#relievePassword").val("");
						if (val == 1) {
							$('#myModalLabel').html('非法手机');
							$('[data-panel="cardNo"]').parent().hasClass(
									'disno') ? $('[data-panel="cardNo"]')
									.parent().removeClass('disno') : '';
							$('[data-panel="cardNo"]').html(dataAttr.loginimei);
							$('[data-panel="callPhone"]').parent().hasClass(
									'disno') ? '' : $(
									'[data-panel="callPhone"]').parent()
									.addClass('disno');
							$('[data-panel="content"]').parent().hasClass(
									'disno') ? '' : $('[data-panel="content"]')
									.parent().addClass('disno');
						} else if (val == 2) {
							$('#myModalLabel').html('非法通话');
							$('[data-panel="cardNo"]').parent().hasClass(
									'disno') ? '' : $('[data-panel="cardNo"]')
									.parent().addClass('disno');
							$('[data-panel="callPhone"]').parent().hasClass(
									'disno') ? $('[data-panel="callPhone"]')
									.parent().removeClass('disno') : '';
							$('[data-panel="callPhone"]')
									.html(dataAttr.targnum);
							$('[data-panel="content"]').parent().hasClass(
									'disno') ? '' : $('[data-panel="content"]')
									.parent().addClass('disno');
						} else if (val == 3) {
							$('#myModalLabel').html('非法短信');
							$('[data-panel="cardNo"]').parent().hasClass(
									'disno') ? '' : $('[data-panel="cardNo"]')
									.parent().addClass('disno');
							$('[data-panel="callPhone"]').parent().hasClass(
									'disno') ? $('[data-panel="callPhone"]')
									.parent().removeClass('disno') : '';
							$('[data-panel="callPhone"]')
									.html(dataAttr.targnum);
							$('[data-panel="content"]').parent().hasClass(
									'disno') ? $('[data-panel="content"]')
									.parent().removeClass('disno') : '';
							$('[data-panel="content"]').html(dataAttr.content);
						} else {
							alert("无数据")
						}
						;
					});
	
	$('#myModal').on('hidden', function(event) {
		var modal = $(this);
		//重置表单数据和样式
		modal.find('.modal-body input[name=reset]').click();
		modal.find('.help-inline').remove();
		modal.find('.control-group').removeClass('error');
		modal.find('.control-group').removeClass('success');
		if(!modal.find('.alert alert-error').hasClass('hide')){
			modal.find('.alert alert-error').addClass('hide');
		}
	})
	$("#confirmationFormNormal").validate(
			{
				errorElement : 'span', //default input error message container
				errorClass : 'help-inline', // default input error message class
				focusInvalid : false, // do not focus the last invalid input
				ignore : "",
				rules : {
					cancelCause : {
						required : true
					},
					cancelPassword : {
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