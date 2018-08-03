<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>录入客户基本信息</title>
<%@ include file="/views/transport/include/head.jsp"%>
<link rel="stylesheet" href="${ctx_static}/publicFile/css/public.css" /> 
<link rel="stylesheet" href="${ctx_static}/transport/basicData/css/basicData.css" />
</head>
<body style="overflow-y: scroll;">
	<span class='layui-breadcrumb breadcrumb'> 
		<a href='javaScript:void(0);'>当前位置 &gt;</a> <a href='javaScript:void(0);'>录入客户基本信息</a>
	</span>
	<div id="rrapp" v-cloak>
		<form class='layui-form'>
			<div class='layui-form-item'>
				<div class='layui-inline'>
					<div class='input-div input-label-box'>
						<label class='layui-form-label'>选择公司：</label>
						<div class='layui-input-block'>
						    <select name="company" id="selectCompany" :disabled="isSelect">
	                             <option v-for="item in companyList" value="上海">{{item}}</option>
	                        </select>
						</div>
					</div>
				</div>
				<div class="layui-inline" v-show="isQuery">
			       <button class="layui-btn inquire" lay-submit lay-filter='inquire' id='inquire' style="top:-3px" >查询</button>
			    </div>
			</div>
		</form>
		
	</div>
	<%@ include file="/views/transport/include/floor.jsp"%>
	<script>
   		var base_url = '${base_url}';
   		var ctx_static = '${ctx_static}';
   		//获取当前登录的用户名 
		var userName = '${currentUser.userName}';
		//获取当前登录的公司 
		var userCompany = '${currentUser.company}';
		//获取当前登录的账号 
		var userAccount = '${currentUser.account}';
   </script>
   <script type="text/javascript" src="${ctx_static}/transport/basicData/js/clientBaseInfo.js"></script>
</body>
</html>