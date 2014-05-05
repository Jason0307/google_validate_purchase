<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/base.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/style.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/uploadify.css">
<title>Google Account Config</title>
</head>
<body>
	<div class="page">
		<header id="header">
			<hgroup class="white blank">
				<h1>Config Google Account</h1>
			</hgroup>
		</header>
		<div class="form-wrapper">
			<form class="login-form" action="#" method="POST"
				enctype="multipart/form-data">
				<input id="upload" name="upload" type="file">
			</form>
		</div>
		<c:if test="${null != account}">
			<div align="center" class="account">
				<label>Client Id</label><input type="text" name="clientId"
					value="${account.clientId}" readonly="readonly" /> <label>Client
					Secret</label><input type="text" name="clientSecret"
					value="${account.clientSecret}" readonly="readonly" /> <label>Redirect
					Url</label><input type="text" name="redirectUrl"
					value="${account.redirectUrl}" readonly="readonly" /> <br /> <a
					href="<%=request.getContextPath()%>/google/auth" class="auth">GO
					AUTH</a>
			</div>
		</c:if>
        <div align="center">
		<c:if test="${null != auth}">
			<c:choose>
				<c:when test="${'success' == auth }">
					<h1 style="color:green">Auth Successfully!</h1>
				</c:when>
				<c:otherwise>
					<h1 style="color:red">Auth failure!</h1>
				</c:otherwise>
			</c:choose>
		</c:if>
		</div>
	</div>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery-2.1.0.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/prefixfree.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery.uploadify.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$('#upload').uploadify({
				'buttonText' : 'Add/Update Secret',
				'swf'      : '<%=request.getContextPath()%>/swf/uploadify.swf',
				'uploader' : '<%=request.getContextPath()%>/google/saveAccount',
				'onUploadSuccess' : function(file, data,response) {
									window.location.reload();
				}
			});
		});
	</script>
</body>
</html>