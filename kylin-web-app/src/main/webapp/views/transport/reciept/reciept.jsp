<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>回单上传</title>
<%@ include file="/views/transport/include/head.jsp"%>
<link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
<link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/excell.css?12"/>
<link rel="stylesheet" href="${ctx_static}/transport/reciept/css/reciept.css"/>
</head>
<body style="overflow-y: scroll;">
	<div id="wrap">
		<span class='layui-breadcrumb breadcrumb'> 
			<a>当前位置  &gt;</a><a>回单上传</a>
		</span>
		<div class='layui-process layui-process-big' lay-showpercent='true' lay-filter='processbar'>
			<div class='layui-process-bar layui-bg-red' layui-percent='0%'></div>
		</div>
	    <fieldset class="layui-elem-field layui-field-title info-title" style="margin-top: 20px;">
	        <legend>信息选择</legend> 
	    </fieldset>
	    <div style='float: left;'>
		    <div class="loading-model">
		        <label>上传图片：</label>
		        <button class="layui-btn layui-btn-warm select-file info-btn">
		            选择图片
		            <form id="myForm">
		                <input type="file" id="excell-file" @change="onFileChange" accept="image/gif,image/jpeg,image/jpg,image/png" multiple >
		            </form>
		        </button>
		        <!-- <span id="file-name">未选择文件</span> -->
		    </div>
	    </div>
	    <div class='info-detail'>
	    	<h1>导入提示：</h1>
	    	<ol style='margin-top: 10px;'>
	    		<li>1.需导入的图片以运单编号命名；</li>
	    		<li>2.如一个运单编号需要上传多张图片，请在运单编号后加“-x”；</li>
	    		<li>3.可以一次选择多张图片，每次选择不超过50张；</li>
	    		<li>4.第二次上传图片会清空第一次上传的图片，每次提交不超过50条数据。</li>
	    	</ol>
	    </div>
	    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;clear: both;">
	        <legend>信息显示</legend>
	    </fieldset>
	    <div class='layui-tab submit-line'>
		    <ul class="layui-tab-title">
		        <button class="layui-btn submit-data" disabled @click="saveImages">提交</button>
		    </ul>
		    <div class='layui-tab-content'>
		    	<div style='position:relative;'>
		    		<!-- <button class="layui-btn" v-show="isCheck" v-cloak>校验</button> -->
		    		<div class='loading'><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop" style='font-size: 50px;color: #FF5722;'>&#xe63d;</i></div>
			    	<div v-show="isCheck" class="ifImportNodata nodata" style="text-align: center; margin-top: 20%;">
				        <img src="${ctx_static}/transport/excelRead/images/nodata.jpg" style="width: 7%">
				        <span class="no-data-copy">暂无数据</span>
				    </div>
				    <div class="content-wrap" v-if="isShowList" v-cloak>
				    	<div class="hint-info">共选择<span>{{totalNum}}</span>张图片，校验失败<span class="checkSuccess">{{checkFalse}}</span>张，上传成功<span class="receipt-qualified">{{trueNum}}</span>张，上传失败<span class="checkSuccess">{{falseNum}}</span>张</div>
			    		<table class="layui-table table-show" id="demo">
			    			<thead>
				              <tr>
				                <th>图片</th>
				                <th>图片名称</th>
				                <th width="25%">运单编号</th>
				                <th>是否校验成功</th>
				                <th>是否提交成功</th>
				                <th width="10%">操作</th>
				              </tr>
				            </thead>
				            <tbody class="list-render">
				              <tr v-for="(item,index) in dataList">
				                <td class="reciept-img"><img :src="item.src" @click="recieptImg(index)"></td>
				                <td>{{item.photoName}}</td>
				                <td class="receipt-input">
				                	<input type="text" class="waybillNum" disabled v-model="item.waybillNum">
				                	<span class="alter-btn" @click="alterNum(index)">修改</span>
				                	<span class="sure-btn" @click="sureNum(index)">确定</span>
				                </td>
				                <td class="receipt-qualified"><span :class="{checkSuccess:item.description=='运单号不存在！'}">{{item.description}}</span></td>
				                <td class="receipt-qualified"><span :class="{checkSuccess:item.submitResult=='提交失败！'}">{{item.submitResult}}</span></td>
				                <td class="receipt-qualified"><button class="layui-btn layui-btn-danger delete-btns" @click="delFile(index)">删除</button></td>
				              </tr>
				              
				            </tbody>
			    		</table>
				    </div>
				    
		    	</div>
		    </div>
	    </div>
	    <div class='bar-process'>
	    	<div class='wrap-process'>
			    <p><img class='loading_pic' alt="" src="${ctx_static}/transport/excelRead/images/loading.gif"></p>
		    	<div class="layui-progress layui-progress-big" lay-showpercent="true" lay-filter="demo">
					<div class="layui-progress-bar layui-bg-red" lay-percent="0%"></div>
				</div>
			</div>
	    </div>
	</div>
	
	<%@ include file="/views/transport/include/floor.jsp"%>
	<script src="${ctx_static}/publicFile/layui/layui.all.js"></script>
	<script>
   		var base_url = '${base_url}';
   		var ctx_static = '${ctx_static}';
   		//获取当前登录的用户名 
		var userName = '${currentUser.userName}';
		//获取当前登录的公司 
		var userCompany = '${currentUser.company}';
		//获取当前登录的账号 
		var userAccount = '${currentUser.account}';
		/* B是Byte的缩写，B就是Byte，也就是字节（Byte）；b是bit的缩写，b就是bit，也就是比特位（bit）。B与b不同，注意区分，KB是千字节，Kb是千比特位。  
		1MB（兆字节）=1024KB（千字节）=1024*1024B(字节)=1048576B(字节)；  
		8bit（比特位）=1Byte（字节）；  
		1024Byte（字节）=1KB(千字节)；  
		1024KB（千字节）=1MB（兆字节）;  
		1024MB=1GB;  
		1024GB=1TB;   */

   </script>
   <script type="text/javascript" src="${ctx_static}/transport/reciept/js/reciept.js"></script>
   
</body>
</html>