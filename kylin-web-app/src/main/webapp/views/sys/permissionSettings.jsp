<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link rel="stylesheet" href="${ctx_statics}/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx_statics}/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx_statics}/plugins/jqgrid/ui.jqgrid-bootstrap.css">
<link rel="stylesheet" href="${ctx_statics}/plugins/ztree/css/metroStyle/metroStyle.css">
<link rel="stylesheet" href="${ctx_statics}/css/main.css">
<link rel="stylesheet" href="${ctx_statics}/plugins/layui/css/layui.css" />
<link rel="stylesheet" href="${ctx_statics}/css/bootstrap-table.min.css">
<link rel="stylesheet" href="${ctx_statics}/plugins/treegrid/jquery.treegrid.css">

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
<style>
	.layui-label-chang{width:120px;padding:9px 0;}
	.layui-input-block{margin-left:5px;float:left;}
	.layui-inline-search{margin-left:30px;}
	.layui-disabled{background-color:#eee;color:#000 !important;}
</style>
</head>
<body>
<div id="rrapp" v-cloak>
	<div class="panel panel-default">
		<form class="form-horizontal">
			<div class='layui-form-item'>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label layui-label-chang'>请输入工号：</label>
						<div class='layui-input-block'>
							<input class="layui-input" type="text" id="search_grid"/>
						</div>
					</div>
				</div>
				<div class="layui-inline layui-inline-search">
					<button class="layui-btn" @click="search_grid" type="button" style="top: -3px;position:relative;">查询</button>
				</div>
			</div>
			<div class='layui-form-item'>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label layui-label-chang'>姓名：</label>
						<div class='layui-input-block'>
							<input class="layui-input layui-disabled" type="text" v-model="ygzl.userName" readonly="readonly"/>
						</div>
					</div>
				</div>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label'>性别：</label>
						<div class='layui-input-block'>
							<input class="layui-input layui-disabled" type="text" v-model="ygzl.xb" readonly="readonly"/>
						</div>
					</div>
				</div>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label layui-label-chang'>所属公司：</label>
						<div class='layui-input-block'>
							<input class="layui-input layui-disabled" type="text" v-model="ygzl.company" readonly="readonly"/>
						</div>
					</div>
				</div>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label '>部门：</label>
						<div class='layui-input-block'>
							<input class="layui-input layui-disabled" type="text" v-model="ygzl.bm" readonly="readonly"/>
						</div>
					</div>
				</div>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label '>职位：</label>
						<div class='layui-input-block'>
							<input class="layui-input layui-disabled" type="text" v-model="ygzl.zw" readonly="readonly"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="form-inline clearfix" style="margin-top:30px;margin-left:26px;">
				<div class="form-group col-md-6">
					<strong class="col-sm-5 control-label">功能权限</strong>
					<div class="col-sm-10">
						<ul id="menuTree" class="ztree"></ul>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label"></div>
				<shiro:hasPermission name="sys:menu:delete">
					<input type="button" class="btn btn-primary" @click="saveOrUpdate" value="提交"/>
				 </shiro:hasPermission>
				<!-- &nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/> -->
			</div>
		</form>
	</div>
</div>

<!-- 选择部门 -->
<div id="deptLayer" style="display: none;padding:10px;">
	<ul id="deptTree" class="ztree"></ul>
</div>

<script>
//菜单树
var menu_ztree;
var menu_setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "menuId",
			pIdKey : "parentId",
			rootPId : -1
		},
		key : {
			url : "nourl"
		}
	},
	check : {
		enable : true,
		nocheckInherit : true
	}
};

//部门结构树
var dept_ztree;
var dept_setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "deptId",
			pIdKey : "parentId",
			rootPId : -1
		},
		key : {
			url : "nourl"
		}
	}
};

//数据树
var data_ztree;
var data_setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "deptId",
			pIdKey : "parentId",
			rootPId : -1
		},
		key : {
			url : "nourl"
		}

	},
	check : {
		enable : true,
		nocheckInherit : true,
		chkboxType : {
			"Y" : "",
			"N" : ""
		}
	}
};

var vm = new Vue({
	el : '#rrapp',
	data : {
		q : {
			roleName : null
		},
		/*  showList: true,*/
		title : null,
		role : {
			deptId : null,
			deptName : null
		},
		ygzl : {
			username:'',
			
		},
		sysygzl : {},
	},
	methods : {
		query : function() {
			vm.reload();
		},
		 add : function() {

			this.getMenuTree(null);
		},
		update : function(grid) {
			if (grid == null) {
				return;
			}
			vm.getMenuTree(grid);
		},
		
		getRole : function(grid) {
			$.get(baseURL + "sys/user/permission/" + grid, function(r) {
				vm.sysygzl = r.sysYgzl;

				//vm.role = r.role;

				//勾选角色所拥有的菜单
				var menuIds = vm.sysygzl.menuIdList;
				for (var i = 0; i < menuIds.length; i++) {
					var node = menu_ztree.getNodeByParam("menuId", menuIds[i]);
					menu_ztree.checkNode(node, true, false);
				}

				/* //勾选角色所拥有的部门数据权限
				 var deptIds = vm.role.deptIdList;
				 for(var i=0; i<deptIds.length; i++) {
				     var node = data_ztree.getNodeByParam("deptId", deptIds[i]);
				     data_ztree.checkNode(node, true, false);
				 }*/

				/* vm.getDept();*/
			});
		}, 
		saveOrUpdate : function() {
			if(!vm.ygzl.username && !vm.ygzl.xb && !vm.ygzl.company && !vm.ygzl.bm && !vm.ygzl.zw ){
				layer.open({title:'提示信息',content:'请先查询员工的信息，再进行提交'});
				return false;
			}
			//获取选择的菜单
			var grid = $("#search_grid").val();
			vm.sysygzl.grid = grid;
			var nodes = menu_ztree.getCheckedNodes(true);
			var menuIdList = new Array();
			for (var i = 0; i < nodes.length; i++) {
				menuIdList.push(nodes[i].menuId);
			}
			vm.sysygzl.menuIdList = menuIdList;

			var url = vm.sysygzl.grid == null ? "sys/userMenu/save"
					: "sys/userMenu/update";
			$.ajax({
				type : "POST",
				url : baseURL + url,
				contentType : "application/json",
				data : JSON.stringify(vm.sysygzl),
				success : function(r) {
					if (r.code === 0) {
						layer.msg('操作成功', {
							aim : 0
						}, function() {
							vm.reload();
						})
					} else {
						layer.msg(r.msg, {
							aim : 0
						});
					}

				}
			});
		},
		 getMenuTree : function(grid) {
			//加载菜单树
			$.get(baseURL + "sys/menu/list", function(r) {
				menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r);
				//展开所有节点
				menu_ztree.expandAll(true);

				if (grid != null) {
					vm.getRole(grid);
				}
			});
		}, 
		reload : function() {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
				postData : {
					'roleName' : vm.q.roleName
				},
				page : page
			}).trigger("reloadGrid");
		},

		search_grid : function() {
			var grid = $("#search_grid").val();
			$.ajax({
				type : "POST",
				url : baseURL + "sys/user/searchByGrid",
				contentType : "application/json",
				data : JSON.stringify(grid),
				success : function(r) {
					if (r.code == 400) {
						layer.msg(r.msg, {
							aim : 0
						}, function() {
							vm.reload();
						})
					} else if (r.code == 200) {
						vm.ygzl = r.ygzl;
					}

				}
			});
			this.update(grid);
		},

	},
	created : function() {
		this.add();

	}
});
</script>
</body>
</html>