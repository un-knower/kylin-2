<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<html>
<head>
<title>编辑角色信息</title>
<%@ include file="/views/security/include/bootstrap.css.jsp"%>
</head>
<body>
<legend class="nav-title">
	<ol class="breadcrumb">
	  <li>权限管理</li><li>角色管理</li><li>编辑角色</li><a href="javascript:history.back();" class="pull-right">返回</a>
	</ol>
</legend>
<form class="form-horizontal" role="form" action="${base_url}/security/role/edit/save" method="post">
<div class="col-sm-offset-3 col-sm-6" >
		<div class="panel panel-default">
			<div class="panel-heading">
		      <h3 class="panel-title">角色基本信息</h3>
		   </div>
			<div class="panel-body">
				<div class="form-group">
					<label for="roleName" class="col-sm-2 control-label">角色名称</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="roleName" name="role.roleName" placeholder="角色名称" value="${roleModel.roleName}"/>
					</div>
				</div>
				<div class="form-group">
					<label for="description" class="col-sm-2 control-label">角色描述</label>
					<div class="col-sm-9">
						<textarea class="form-control" id="description" name="role.description" placeholder="角色描述" >${roleModel.description}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label for="enable" class="col-sm-2 control-label">状态</label>
					<div class="col-sm-9">
						<div class="radio">
						   <c:choose>
								<c:when test="${roleModel.enable == 0 || roleModel.enable == 2}">
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
					<label for="resKeys" class="col-sm-2 control-label">资源</label>
					<div class="col-sm-9">
						<select multiple class="selectpicker form-control" id="resKeys" name="resKeys" title="资源" data-live-search="true">
						 	<c:forEach items="${ ress }" var="r">
								<c:choose>
									<c:when test="${r.roleId != null && r.roleId > 0}">
										<option value="${r.resourceId}" selected="selected">${r.text}</option>
									</c:when>
									<c:otherwise>
									   <option value="${r.resourceId}">${r.text}</option>
									</c:otherwise>
								</c:choose>
								<c:if test="${r.children!= null && fn:length(r.children) > 0}">
									<c:forEach items="${ r.children }" var="c">
										<c:choose>
											<c:when test="${c.roleId != null && c.roleId > 0}">
												<option class="c-indent" value="${c.resourceId}" selected="selected">${c.text}</option>
											</c:when>
											<c:otherwise>
											   <option class="c-indent" value="${c.resourceId}" >${c.text}</option>
											</c:otherwise>
										</c:choose>
										<c:if test="${c.children!= null && fn:length(c.children) > 0}">
											<c:forEach items="${ c.children }" var="m">
												<c:choose>
													<c:when test="${m.roleId != null && m.roleId > 0}">
														<option class="cc-indent" value="${m.resourceId}" selected="selected">${m.text}</option>
													</c:when>
													<c:otherwise>
													   <option class="cc-indent" value="${m.resourceId}" >${m.text}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
									</c:forEach>
								</c:if>
						    </c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-9 col-sm-2">
						<input type="hidden" name="role.roleId" value="${roleModel.roleId}">
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
	$('form').bootstrapValidator({
        container: 'tooltip',
        trigger: 'blur',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	'role.roleName': {
                validators: {
                    stringLength: {min: 4, message: '角色名称不能少于4个字符'},
                    notEmpty: {message: '角色名称不能为空'}
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