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
		ygzl : {},
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
		del : function() {
			var roleIds = getSelectedRows();
			if (roleIds == null) {
				return;
			}

			confirm('确定要删除选中的记录？', function() {
				$.ajax({
					type : "POST",
					url : baseURL + "sys/role/delete",
					contentType : "application/json",
					data : JSON.stringify(roleIds),
					success : function(r) {
						if (r.code == 0) {
							alert('操作成功', function() {
								vm.reload();
							});
						} else {
							alert(r.msg);
						}
					}
				});
			});
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
			//获取选择的菜单
			var grid = $("#search_grid").val();
			vm.sysygzl.grid = grid;
			var nodes = menu_ztree.getCheckedNodes(true);
			var menuIdList = new Array();
			for (var i = 0; i < nodes.length; i++) {
				menuIdList.push(nodes[i].menuId);
			}
			vm.sysygzl.menuIdList = menuIdList;

			var url = vm.sysygzl.grid == null ? "sys/role/save"
					: "sys/role/update";
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
		getDataTree : function(roleId) {
			//加载菜单树
			$.get(baseURL + "sys/dept/list", function(r) {
				data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r);
				//展开所有节点
				data_ztree.expandAll(true);
			});
		},

		deptTree : function() {
			layer.open({
				type : 1,
				offset : '50px',
				skin : 'layui-layer-molv',
				title : "选择部门",
				area : [ '300px', '450px' ],
				shade : 0,
				shadeClose : false,
				content : jQuery("#deptLayer"),
				btn : [ '确定', '取消' ],
				btn1 : function(index) {
					var node = dept_ztree.getSelectedNodes();
					//选择上级部门
					vm.role.deptId = node[0].deptId;
					vm.role.deptName = node[0].name;

					layer.close(index);
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