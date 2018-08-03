<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<html>
<head>
<title>新增资源信息</title>
<%@ include file="/views/security/include/bootstrap.css.jsp"%>
</head>
<body>
<legend class="nav-title">
	<ol class="breadcrumb">
	  <li>权限管理</li><li>资源管理</li><li>新建资源</li><a href="javascript:history.back();" class="pull-right">返回</a>
	</ol>
</legend>
<form class="form-horizontal" role="form" action="${base_url}/security/res/insert/save" method="post">
	<div class="col-sm-offset-3 col-sm-6" >
		<div class="panel panel-default">
			<div class="panel-heading">
		      <h3 class="panel-title">资源基本信息</h3>
		   </div>
			<div class="panel-body">
				<div class="form-group">
					<label for="parentId" class="col-sm-2 control-label">父菜单</label>
					<div class="col-sm-9">
						<select class="selectpicker form-control" id="parentId" name="res.parentId" title="请选择父菜单" data-live-search="true">
						 	<c:forEach items="${ ress }" var="r">
								<c:if test="${r.resourceId  == resModel.parentId}">
								   <c:if test="${r.parentId  == 0}">
								   		<option value="${r.resourceId}" selected="selected">${r.text}</option>
								   </c:if>
								   <c:if test="${r.parentId  > 0}">
								   		<option class="c-indent" value="${r.resourceId}" selected="selected">${r.text}</option>
								   </c:if>
								</c:if>
								<c:if test="${r.resourceId  != resModel.parentId}">
								   <c:if test="${r.parentId  == 0}">
								   		<option value="${r.resourceId}">${r.text}</option>
								   </c:if>
								   <c:if test="${r.parentId  > 0}">
								   		<option class="c-indent" value="${r.resourceId}" >${r.text}</option>
								   </c:if>
								</c:if>
						    </c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="text" class="col-sm-2 control-label">资源名称</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="text" name="res.text" placeholder="输入资源名称" value="${resModel.text}">
					</div>
				</div>
				<div class="form-group">
					<label for="resurl" class="col-sm-2 control-label">资源标识</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="resurl" name="res.resurl" placeholder="示例: /security/user/manage/*" value="${resModel.resurl}">
					</div>
				</div>
				<div class="form-group">
					<label for="url" class="col-sm-2 control-label">资源地址</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="url" name="res.url" placeholder="示例: /security/user/manage" value="${resModel.url}">
					</div>
				</div>
				<div class="form-group">
					<label for="enable" class="col-sm-2 control-label">状态</label>
					<div class="col-sm-9">
						<div class="radio">
						   <c:choose>
								<c:when test="${resModel.enable == 0 || resModel.enable == 2}">
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
        	'res.text': {
                validators: {
                    stringLength: {min: 4, max:10, message: '资源名称不能少于4个字符且不能超过10个字符'},
                    notEmpty: {message: '资源名称不能为空'}
                }
            },
        	'res.resurl': {
                validators: {
                    notEmpty: {message: '资源标识不能为空'}
                }
            },
        	'res.url': {
                validators: {
                    notEmpty: {message: '资源地址不能为空'}
                }
            },
            'enable': {
                validators: {
                    notEmpty: {message: '资源状态不能为空'}
                }
            }
        }
    });
});
</script>
</html>