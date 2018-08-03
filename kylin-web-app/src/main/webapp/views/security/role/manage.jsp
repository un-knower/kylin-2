<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<html>
<head>
<title>角色管理</title>
<%@ include file="/views/security/include/bootstrap.css.jsp"%>
</head>
<body>
<legend class="nav-title">
	<ol class="breadcrumb">
	  <li>权限管理</li><li>角色管理</li>
	  <a href="javascript:history.back();" class="pull-right">返回</a>
	</ol>
</legend>
	<div class="col-sm-12">
		<form class="form-inline" role="form" action="${base_url}/security/role/manage/search" method="post">
			<div class="form-group">
				<label for="input-name">角色名称</label> 
				<input type="text" class="form-control" id="input-name" name="role.roleName" placeholder="角色名称" value="${roleModel.roleName }">
			</div>
			<div class="form-group">
				<label for="select-enable">角色状态</label> 
				<select name="enable" class="form-control" id="select-enable">
					<c:choose>
						<c:when test="${roleModel.enable == 1}">
							<option value="0">选择状态</option>
							<option value="1" selected="selected">启用</option>
							<option value="2">禁用</option>
						</c:when>
						<c:when test="${roleModel.enable == 2}">
							<option value="0">选择状态</option>
							<option value="1">启用</option>
							<option value="2" selected="selected">禁用</option>
						</c:when>
						<c:otherwise>
							<option value="0" selected="selected">选择状态</option>
							<option value="1">启用</option>
							<option value="2">禁用</option>
						</c:otherwise>
					</c:choose>
				</select>
			</div>
			<input type="hidden" name="num" value="${ roleModel.num }" />
			<button type="submit" class="btn btn-info" id="submit_sreach">提交查询</button>
			<sec:authorize url="/security/role/insert">
				<a href="${base_url}/security/role/insert" class="btn btn-info pull-right" role="button">新建角色</a>
			</sec:authorize>
		</form>
	</div>
	<div class="col-sm-12">
		<sec:authorize url="/security/role/edit"><c:set var="has_edit" value="true" /></sec:authorize>
		<sec:authorize url="/security/role/enable"><c:set var="has_enable" value="true" /></sec:authorize>
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="5%">编号</th>
					<th width="15%">角色名称</th>
					<th>角色类型</th>
					<th width="10%">状态</th>
					<th width="12%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ page.collection }" var="u">
					<tr>
						<td>${u.roleId}</td>
						<td>${u.roleName}</td>
						<td>${u.description}</td>
						<td>
							<c:if test="${ u.enable == true }">
								<span class="label label-success" name="enable_${u.roleId}">启用状态</span>
							</c:if>
							<c:if test="${ u.enable == false }">
								<span class="label label-danger" name="enable_${u.roleId}">禁用状态</span>
							</c:if>
						</td>
						<td class="authority">
							<div class="btn-group btn-group-xs">
							    <c:if test="${has_edit == true }">
									<a href="${base_url}/security/role/edit/${u.roleId}" class="btn btn-default" role="button">编辑</a>
								</c:if>
							    <c:if test="${has_enable == true }">
									<c:if test="${ u.enable == true }">
										<button type="button" class="btn btn-default" name="btn_change" value="${base_url}/security/role/enable/${u.roleId}">禁用</button>
									</c:if>
									<c:if test="${ u.enable == false }">
										<button type="button" class="btn btn-default" name="btn_change" value="${base_url}/security/role/enable/${u.roleId}">启用</button>
									</c:if>
								</c:if>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${page.pages > 1 }">
		<div id="layui_div_page">
			<ul class="pagination">
			  <c:if test="${page.isFirstPage == false }">
			  	<li><a href="javascript:;" num="1">首页</a></li>
			  	<li><a href="javascript:;" num="${page.prePage}">前一页</a></li>
			  </c:if>
			  <c:forEach items="${page.navigatepageNums }" var="p">
			  	<c:if test="${p == page.num}">
			  		<li class="active"><a href="javascript:;" num="${p}">${p}</a></li>
			  	</c:if>
			  	<c:if test="${p != page.num}">
			  		<li><a href="javascript:;" num="${p}">${p}</a></li>
			  	</c:if>
			  </c:forEach>
			  <c:if test="${page.isLastPage == false }">
			  	<li><a href="javascript:;" num="${page.nextPage}">下一页</a></li>
			  	<li><a href="javascript:;" num="${page.pages}">尾页</a></li>
			  </c:if>
			</ul>
		</div>
		</c:if>
	</div>
</body>
<%@ include file="/views/security/include/bootstrap.js.jsp"%>
<script type="text/javascript" src="${ctx_static}/security/role/manage.js"></script>
</html>