<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<html>
<head>
<title>403</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link href="${ctx_static}/common/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx_static}/security/custom.css" rel="stylesheet" />
<style type="text/css">
.mydiv {
	width: 300px;
	height: 200px;
	position: absolute;
	left: 50%;
	top: 50%;
	margin: -100px 0 0 -150px
}
</style>
</head>

<body>
	<div class="mydiv">
		<blockquote class="layui-elem-quote">没有权限访问该资源,请联系管理员</blockquote>
	</div>
</body>
<script src="${ctx_static}/common/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="${ctx_static}/common/bootstrap/bootstrap.min.js" type="text/javascript"></script>
</html>