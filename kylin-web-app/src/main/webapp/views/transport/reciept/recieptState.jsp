<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>回单上传</title>
<%@ include file="/views/transport/include/head.jsp"%>
<link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
<link rel="stylesheet" href="${ctx_static}/transport/reciept/css/reciept.css"/>
</head>
<body style="overflow-y: scroll;">
	<div id='rrapp' v-cloak>
        <div class='wrap-images'>
			<ul class="items-list clearAfter">
				<li class="fl" v-for="(item,index) in imgList">
					<div class="item-img">
						<img modal="zoomImg" :data-num="index" :src="item.imgAddress">
					</div>
					<p class="item-name">{{item.imgName}}</p>
				</li>
			</ul>
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
   <script type="text/javascript" src="${ctx_static}/transport/reciept/js/recieptState.js"></script>
   
</body>
</html>