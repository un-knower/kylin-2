<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>到站资料维护</title>
<%@ include file="/views/transport/include/head.jsp"%>
<link rel="stylesheet" href="${ctx_static}/publicFile/css/public.css" /> 
<link rel="stylesheet" href="${ctx_static}/transport/basicData/css/basicData.css" />
</head>
<body style="overflow-y: scroll;">
	<span class='layui-breadcrumb breadcrumb'> 
		<a>当前位置 &gt;</a><a>到站资料维护</a>
	</span>
	<div id="rrapp" v-cloak>
		<form class='layui-form'>
			<div class='layui-form-item'>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label'>选择公司：</label>
						<div class='layui-input-block'>
						    <select name="company" id="selectCompany" :disabled="isSelect">
	                             <option v-for="item in companyList" :value="item">{{item}}</option>
	                        </select>
						</div>
					</div>
				</div>
				<div class="layui-inline" v-show="isQuery">
			       <button class="layui-btn inquire" type="button" lay-submit lay-filter="inquire" id='inquire' style="top:-3px" >查询</button>
			    </div>
			</div>
		</form>
		<div class="company-list" v-show="isAddList">
			<div class="add-list">
				<div class="add-company">
					<h4>未添加线路</h4>
					<ul class="add-ul">
						<li v-for="(item,index) in noDaoZhanList" :index="index+1" @click="selectAdd(index)">{{item}}</li>
					</ul>
				</div>
				<div class="add-btn">
			       <button class="layui-btn" id='inquire' style="top:-3px" :disabled="isAdd" @click="addCircuit">添加线路</button>
			   	   <br/>
			       <button class="layui-btn" id='inquire' style="top:-3px" :disabled="isDel" @click="delCircuit">删除线路</button>
				</div>
				<div class="add-company">
					<h4>当前公司所有线路</h4>
					<ul class="del-ul">
						<li v-for="(item,index) in daoZhanList" :index="index+1" @click="selectDel(index)">{{item}}</li>
					</ul>
				</div>
			</div>
			<div class="layui-inline list-save-btn">
		       <button class="layui-btn" type="button" id='savwBtn' :disabled="isSave" @click="saveChange" style="top:-3px" >保存修改</button>
		    </div>
		</div>
		
		
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
   <script type="text/javascript" src="${ctx_static}/transport/basicData/js/arriveBasicData.js"></script>
</body>
</html>