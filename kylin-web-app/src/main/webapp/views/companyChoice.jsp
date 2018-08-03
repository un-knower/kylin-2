
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx_statics}/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx_statics}/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx_statics}/plugins/jqgrid/ui.jqgrid-bootstrap.css">
<link rel="stylesheet" href="${ctx_statics}/plugins/ztree/css/metroStyle/metroStyle.css">
<link rel="stylesheet" href="${ctx_statics}/css/main.css">
<link rel="stylesheet" href="${ctx_statics}/plugins/layui/css/layui.css" />
<link rel="stylesheet" href="${ctx_statics}/css/bootstrap-table.min.css">
<link rel="stylesheet" href="${ctx_statics}/plugins/treegrid/jquery.treegrid.css">
<!-- Custom -->
<link rel="stylesheet" type="text/css" href="${ctx_static}/common/reset.css" />
<link rel="stylesheet" type="text/css" href="${ctx_static}/login/css/login.css" />

<script src="${ctx_statics}/libs/jquery.min.js"></script>
<script src="${ctx_statics}/plugins/layer/layer.js"></script>
<script src="${ctx_statics}/plugins/layui/layui.js"></script>
<script src="${ctx_statics}/plugins/layui/layui.all.js"></script>
<script src="${ctx_statics}/libs/bootstrap.min.js"></script>
<script src="${ctx_statics}/libs/vue.min.js"></script>
<script src="${ctx_statics}/plugins/jqgrid/grid.locale-cn.js"></script>
<script src="${ctx_statics}/plugins/jqgrid/jquery.jqGrid.min.js"></script>
<script src="${ctx_statics}/plugins/ztree/jquery.ztree.all.min.js"></script>
<script src="${ctx_statics}/js/commons.js"></script>
<script src="${ctx_statics}/libs/bootstrap-table.min.js"></script> 
<script src="${ctx_statics}/plugins/treegrid/jquery.treegrid.min.js"></script>
<script src="${ctx_statics}/plugins/treegrid/jquery.treegrid.bootstrap3.js"></script>
<script src="${ctx_statics}/plugins/treegrid/jquery.treegrid.extension.js"></script>
<script src="${ctx_statics}/plugins/treegrid/tree.table.js"></script>
<title>选择公司</title>
</head>
<body>
	<div class="wrap animated flipInX" id="kyapp">
		<!-- header -->
		<div class="header">
			<div class="logo">
				<a><img src="${ctx_static}/common/images/logo.png" /></a>
			</div>
		</div>
		<!-- form -->
		<div class="form">
			<form  id="form-submit" action='${base_url}/sys/user/headInfo' method="POST">
				<div class="form-group" style="background-color:#fff;">
					<label for="company" class="form-label"><i class="fa fa-lock"></i></label>
					<select style="border: none" name="coms" class="selectBox form-control" id="company" name="company">
						<c:forEach items="${companyList}" var="item">
							<option value="${item.gs}">${item.gs}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<input type="submit" class="form-btn" value="确定"/>
				</div>
			</form>
		</div>
	</div>
	
<!--
<script type="text/javascript">
var base_url = '${base_url}';
var vm = new Vue({
	el:'#kyapp',
	data:{
		user:{},
		menuList:{},
		main:"main.html",
		password:'',
		newPassword:'',
        navTitle:"控制台",
        company:""
	},
	created: function(){
		
		this.getUser();
	},
	
	mounted:function(){
		
	},
	methods: {
		getUser: function(){
			console.log("获取用户信息");
			    $.ajax({
	                type: "POST",
	                url:  base_url + "/sys/user/info",
	                contentType: "application/json",
	                success: function(r){
	                	vm.user = r.user;
	                	vm.grid=vm.user.account;
	                	$.ajax({
	        				url:base_url + "/sys/user/companyList?account="+vm.user.account,
	        				success:function(r){	
	        					console.log(r);
	        					console.log(r.companyList[0]);
	        					
	        					var html="";
	        					if(r.companyList){
	        						for (var i = 0; i < r.companyList.length; i++){
	        							if(r.companyList[i].gs == vm.company){
	        								html+="<option selected='selected' value='"+vm.company+"'>"+vm.company+"</option>";
	        							}else{
	        								html+="<option value='"+r.companyList[i].gs+"'>"+r.companyList[i].gs+"</option>";
	        							}
	        						}
	        					}
	        					  $("#company").empty().append(html);
	        				},
	        				error : function(XMLHttpRequest, textStatus, errorThrown) {
	        		            if(XMLHttpRequest.readyState == 0) {
	        		            //here request not initialization, do nothing
	        		            } else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
	        		                layer.msg("服务器忙，请重试!");
	        		            } else {
	        		                layer.msg("系统异常，请联系系统管理员!");
	        		            }
	        		        }
	        			});
	                }
	            });
		},
		
	},
	updated: function(){
		//路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
	},
});

</script>

-->
</body>
</html>