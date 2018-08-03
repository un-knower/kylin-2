<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<html>
<head>
<title>新增用户信息</title>
<%@ include file="/views/security/include/bootstrap.css.jsp"%>
</head>
<body>
<legend class="nav-title">
	<ol class="breadcrumb">
	  <li>权限管理</li><li>用户管理</li><li>新建用户</li><a href="javascript:history.back();" class="pull-right">返回</a>
	</ol>
</legend>
<form class="form-horizontal" role="form" action="${base_url}/security/user/insert/save" method="post">
	<div class="col-sm-offset-3 col-sm-6" >
		<div class="panel panel-default">
			<div class="panel-heading">
		      <h3 class="panel-title">账号基本信息</h3>
		   </div>
			<div class="panel-body">
				<div class="form-group">
					<label for="userId" class="col-sm-2 control-label">工号</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="userId" name="user.account" placeholder="请输入工号" value="${userModel.account}" >
					</div>
				</div>
				<div class="form-group">
					<label for="userName" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="userName" name="user.userName" placeholder="请输入姓名" value="${userModel.userName}">
					</div>
				</div>
				<div class="form-group">
					<label for="company" class="col-sm-2 control-label">公司</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="company" name="user.company" placeholder="请输入公司" value="${userModel.company}">
					</div>
				</div>
				<div class="form-group">
					<label for="subCompany" class="col-sm-2 control-label">分公司</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="subCompany" name="user.subCompany" placeholder="请输入分公司" value="${userModel.subCompany}">
					</div>
				</div>
				<div class="form-group">
					<label for="enable" class="col-sm-2 control-label">状态</label>
					<div class="col-sm-9">
						<div class="radio">
						   <c:choose>
								<c:when test="${userModel.enable == 0 || userModel.enable == 2}">
								   <label>
							   	  		<input type="radio" name="enable" id="radio_enable_1" value="1">启用状态
								   </label>
								   <label>
								      	<input type="radio" name="enable" id="radio_enable_2" value="2" checked>禁用状态
								   </label>
								</c:when>
								<c:otherwise>
								   <label>
							   	  		<input type="radio" name="enable" id="radio_enable_1" value="1" checked>启用状态
								   </label>
								   <label>
								      	<input type="radio" name="enable" id="radio_enable_2" value="2">禁用状态
								   </label>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="roles" class="col-sm-2 control-label">角色</label>
					<div class="col-sm-9">
						<!-- data-actions-box="true" -->
						<select multiple class="selectpicker form-control" id="roles" name="roleKeys" title="请选择角色"  data-live-search="true">
						 	<c:forEach items="${ roles }" var="r">
								<c:choose>
									<c:when test="${fn:contains(userModel.roleKeys, r.roleId)}">
									   <option value="${r.roleId}" selected="selected">${r.roleName}</option>
									</c:when>
									<c:otherwise>
									   <option value="${r.roleId}">${r.roleName}</option>
									</c:otherwise>
								</c:choose>
						    </c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-9 col-sm-2">
						<button type="submit" class="btn btn-default pull-right">确认新建</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
<%@ include file="/views/security/include/bootstrap.js.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	/*
	try {
		var roleString = $('.selectpicker').attr("val");
		if(roleString && roleString.length > 0){
			$('.selectpicker').selectpicker('val', roleString.split(','));//选中
		}
	} catch (e) { }
	*/
	$('form').bootstrapValidator({
        container: 'tooltip',
        trigger: 'blur',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	'user.account': {
                validators: {
                    stringLength: {min: 4, message: '工号不能少于5个字符'},
                    notEmpty: {message: '工号不能为空'}
                }
            },
        	'user.userName': {
                validators: {
                    notEmpty: {message: '姓名不能为空'}
                }
            },
            'company': {
                validators: {
                    notEmpty: {message: '公司名称不能为空'}
                }
            },
            'enable': {
                validators: {
                    notEmpty: {message: '账号状态不能为空'}
                }
            }
        }
    });
});
</script>
</html>