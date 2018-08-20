<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>运输执行</title>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!-- font-awesome-fa -->
    <link href="${ctx_static}/common/font-awesome-fa/font-awesome.min.css" rel="stylesheet" media="screen">
    <!-- layui -->
    <link rel="stylesheet" type="text/css" href="${ctx_static}/common/layui/css/layui.css">
    <!-- animate -->
    <link href="${ctx_static}/common/animate.min.css" type="text/css" rel="stylesheet">
    <!-- Custom -->
    <link rel="stylesheet" type="text/css" href="${ctx_static}/common/reset.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx_static}/login/css/login.css"/>
</head>

<body>
<!-- wrap -->
<div class="wrap animated flipInX">
    <!-- header -->
    <div class="header">
        <div class="logo">
            <a href="#"><img src="${ctx_static}/common/images/logo.png"/></a>
        </div>
    </div>
    <!-- form -->
    <div class="form">
        <form role="form" method="post" id="loginForm" action="${base_url}/login">
            <div class="form-group">
                <label for="userName" class="form-label"><i class="fa fa-user"></i></label>
                <input type="text" placeholder="请输入账号名称" name="username" class="form-control" id="userName"/>
            </div>
            <div class="form-group">
                <label for="password" class="form-label"><i class="fa fa-lock"></i></label>
                <input type="password" placeholder="请输入密码" name="password" class="form-control" id="password"/>
            </div>
            <!-- <div class="form-group">
                <label for="company" class="form-label"><i class="fa fa-lock"></i></label>
                <select style="border: none" class="selectBox form-control" id="company" name="company">
                </select>
            </div> -->
            <div class="form-group">
                <!--
                <input type="submit" value="Login">
                -->
                <a href="javascript:void(0);" class="form-btn" id="submit">登录</a>
            </div>
            <div class="error"></div>
        </form>
    </div>
    <div id="message">
        <c:if test="${success_message != null }">
            <div id="success_message" style="display: none;">${ success_message }</div>
        </c:if>
        <c:if test="${warning_message != null }">
            <div id="warning_message" style="display: none;">${ warning_message }</div>
        </c:if>
        <c:if test="${error_message != null }">
            <div id="error_message" style="display: none;">${ error_message }</div>
        </c:if>
    </div>
    <!-- </div> -->
</div>
<script type="text/javascript" src="${ctx_static}/common/jquery/jquery-1.12.2.min.js"></script>

<script type="text/javascript" src="${ctx_static}/common/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx_static}/common/jquery/easyCookie.js"></script>
<script type="text/javascript" src="${ctx_static}/common/jquery/json2.js"></script>
<script type="text/javascript" src="${ctx_static}/common/jquery/system-config.js"></script>
<script type="text/javascript" src="${ctx_static}/common/layui/layui.js"></script>

<!-- 引入layer模块 -->
<script type="text/javascript">
    layui.use('layer', function () {
        var layer = layui.layer;
    });
</script>
<script type="text/javascript" src="${ctx_static}/common/jquery/easy.ajax.js"></script>
<script type="text/javascript" src="${ctx_static}/common/jquery/placeholder.js"></script>
<script type="text/javascript" src="${ctx_static}/login/js/login.js"></script>
</body>
</html>