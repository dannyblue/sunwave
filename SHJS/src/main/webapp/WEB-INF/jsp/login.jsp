<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->

<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->

<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->

<!-- BEGIN HEAD -->

<head>

	<meta charset="utf-8" />

	<title>登陆</title>

	<meta content="width=device-width, initial-scale=1.0" name="viewport" />

	<meta content="" name="description" />

	<meta content="" name="author" />

	<!-- BEGIN GLOBAL MANDATORY STYLES -->

	<link href="resources/css/common/bootstrap.min.css" rel="stylesheet" type="text/css"/>

	<link href="resources/css/common/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>

	<link href="resources/css/common/font-awesome.css" rel="stylesheet" type="text/css"/>

	<link href="resources/css/common/style-metro.css" rel="stylesheet" type="text/css"/>

	<link href="resources/css/common/style.css" rel="stylesheet" type="text/css"/>

	<link href="resources/css/common/style-responsive.css" rel="stylesheet" type="text/css"/>

	<link href="resources/css/common/default.css" rel="stylesheet" type="text/css" id="style_color"/>

	<link href="resources/css/common/uniform.default.css" rel="stylesheet" type="text/css"/>

	<!-- END GLOBAL MANDATORY STYLES -->

	<!-- BEGIN PAGE LEVEL STYLES -->

	<link href="resources/css/frame/login/login.css" rel="stylesheet" type="text/css"/>

	<!-- END PAGE LEVEL STYLES -->

	<link rel="shortcut icon" href="resources/images/favicon.ico" />
	<style>
		.copyright{position: fixed;bottom: 15px;left: 50%;-webkit-transform: translateX(-50%);-moz-transform: translateX(-50%);-o-transform: translateX(-50%);-ms-transform: translateX(-50%);transform: translateX(-50%);}
	</style>

</head>

<!-- END HEAD -->

<!-- BEGIN BODY -->

<body class="login">

	<!-- BEGIN LOGO -->

	<div class="logo">

		<!-- <img src="resources/images/frame/logo.png" alt="" />  -->

	</div>

	<!-- END LOGO -->

	<!-- BEGIN LOGIN -->

	<div class="content">

		<!-- BEGIN LOGIN FORM -->

		<form class="form-vertical login-form" action="login.do" method="post">

			<h3 class="form-title">登陆</h3>

			<div class="alert alert-error <c:if test="${message==null}">hide</c:if>">

				<button class="close" data-dismiss="alert"></button>

				<span>信息提示:</span>
				${message }
			</div>

			<div class="control-group">

				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->

				<label class="control-label visible-ie8 visible-ie9">用户名:</label>

				<div class="controls">

					<div class="input-icon left">

						<i class="icon-user"></i>

						<input class="m-wrap placeholder-no-fix" type="text" placeholder="用户名" id="username" name="username" value="admin"/>

					</div>

				</div>

			</div>

			<div class="control-group">

				<label class="control-label visible-ie8 visible-ie9">密码:</label>

				<div class="controls">

					<div class="input-icon left">

						<i class="icon-lock"></i>

						<input class="m-wrap placeholder-no-fix" type="password" placeholder="密码" id="password" name="password" value="123456"/>

					</div>

				</div>

			</div>
				
			<div class="control-group">

				<label class="control-label visible-ie8 visible-ie9">验证码:</label>

				<div class="controls">

					<div class="input-icon left">

						<i class="icon-pencil"></i>

						<input class="m-wrap placeholder-no-fix" type="text" placeholder="验证码" id="randomCode" name="randomCode" style="width:50%" />

						<img src="getRandomCode.do" id="rc" onclick="changeCode()" title="看不清验证码？请点击验证码图片刷新。" alt="" style="CURSOR:pointer;height: 34px;display: inline-block;width: 96px;">

					</div>

				</div>

			</div>

			<div class="form-actions">

				<label class="checkbox">

				<input type="checkbox" name="remember" value="1"/>记住密码

				</label>

				<button  class="btn green pull-right">

				登陆 <i class="m-icon-swapright m-icon-white"></i>

				</button>            

			</div>

			<div class="forget-password">

				<h4>忘记密码?</h4>

				<p>

					别担心,点击 <a href="javascript:;" class="" id="forget-password">这里</a>

					重置你的密码.

				</p>

			</div>

		</form>

		<!-- END LOGIN FORM -->        

		<!-- BEGIN FORGOT PASSWORD FORM -->

		<form class="form-vertical forget-form" >

			<h3 class="">找回密码</h3>

			<p>在下面填写你的Email地址重置密码。</p>

			<div class="control-group">

				<div class="controls">

					<div class="input-icon left">

						<i class="icon-envelope"></i>

						<input class="m-wrap placeholder-no-fix" type="text" placeholder="Email" name="email" />

					</div>

				</div>

			</div>

			<div class="form-actions">

				<button type="button" id="back-btn" class="btn">

				<i class="m-icon-swapleft"></i> 返回

				</button>

				<button type="submit" class="btn green pull-right">

				完成 <i class="m-icon-swapright m-icon-white"></i>

				</button>            

			</div>

		</form>

		<!-- END FORGOT PASSWORD FORM -->

	</div>

	<!-- END LOGIN -->

	<!-- BEGIN COPYRIGHT -->

	<!-- <div class="copyright">
	
		2013 &copy; Metronic. Admin Dashboard Template.
	
	</div> -->

	<!-- END COPYRIGHT -->

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->

	<!-- BEGIN CORE PLUGINS -->

	<script src="resources/js/common/jquery-1.10.1.min.js" type="text/javascript"></script>

	<script src="resources/js/common/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>

	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->

	<script src="resources/js/common/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>      

	<script src="resources/js/common/bootstrap.min.js" type="text/javascript"></script>

	<!--[if lt IE 9]>

	<script src="resources/js/common/excanvas.min.js"></script>

	<script src="resources/js/common/respond.min.js"></script>  

	<![endif]-->   

	<script src="resources/js/common/jquery.slimscroll.min.js" type="text/javascript"></script>

	<script src="resources/js/common/jquery.blockui.min.js" type="text/javascript"></script>  

	<script src="resources/js/common/jquery.cookie.min.js" type="text/javascript"></script>

	<script src="resources/js/common/jquery.uniform.min.js" type="text/javascript" ></script>

	<!-- END CORE PLUGINS -->

	<!-- BEGIN PAGE LEVEL PLUGINS -->

	<script src="resources/js/frame/login/jquery.validate.min.js" type="text/javascript"></script>

	<!-- END PAGE LEVEL PLUGINS -->

	<!-- BEGIN PAGE LEVEL SCRIPTS -->

	<script src="resources/js/common/app.js" type="text/javascript"></script>

	<script src="resources/js/frame/login/login.js" type="text/javascript"></script>      

	<!-- END PAGE LEVEL SCRIPTS --> 

	<script>
	
	function changeCode()  
	  {  
		  var bigimg = document.getElementById("rc"); 	 
		  bigimg.src="getRandomCode.do?random="+new Date();
	  } 
	
		jQuery(document).ready(function() {     

		  App.init();

		  Login.init();
		  
		});

	</script>

	<!-- END JAVASCRIPTS -->

<script type="text/javascript">  var _gaq = _gaq || [];  _gaq.push(['_setAccount', 'UA-37564768-1']);  _gaq.push(['_setDomainName', 'keenthemes.com']);  _gaq.push(['_setAllowLinker', true]);  _gaq.push(['_trackPageview']);  (function() {    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;    ga.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'stats.g.doubleclick.net/dc.js';    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);  })();</script></body>

<!-- END BODY -->

</html>