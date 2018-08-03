<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>菜单管理</title>
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
</head>
<body>
	<div id="rrapp" v-cloak>
		<div v-show="showList">
			<div class="grid-btn">
				 <%-- <shiro:hasPermission name="sys:menu:save">  --%>
				   <a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
           <%--    </shiro:hasPermission>  --%>
				
			<shiro:hasPermission name="sys:menu:update"> 
				   <a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
			 </shiro:hasPermission>
				
				<shiro:hasPermission name="sys:menu:delete"> 
				   <a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
				 </shiro:hasPermission>
			</div>


			<table id="menuTable" data-mobile-responsive="true"
				data-click-to-select="true">
				<thead>
					<tr>
						<th data-field="selectItem" data-checkbox="true"></th>
					</tr>
				</thead>
			</table>
		</div>

		<div v-show="!showList" class="panel panel-default">
			<div class="panel-heading">{{title}}</div>
			<form class="form-horizontal">
				<div class="form-group">
					<div class="col-sm-2 control-label">类型</div>
					<label class="radio-inline"> <input type="radio"
						name="type" value="0" v-model="menu.type" /> 目录
					</label> <label class="radio-inline"> <input type="radio"
						name="type" value="1" v-model="menu.type" /> 菜单
					</label> <label class="radio-inline"> <input type="radio"
						name="type" value="2" v-model="menu.type" /> 按钮
					</label>
				</div>
				<div class="form-group">
					<div class="col-sm-2 control-label"><label style="color:red">*</label>菜单名称</div>
					<div class="col-sm-10">
						<input type="text" class="form-control" v-model="menu.name"
							placeholder="菜单名称或按钮名称" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-2 control-label">上级菜单</div>
					<div class="col-sm-10">
						<input type="text" class="form-control" style="cursor: pointer;"
							v-model="menu.parentName" @click="menuTree" readonly="readonly"
							placeholder="一级菜单" />
					</div>
				</div>
				<div v-if="menu.type == 1" class="form-group">
					<div class="col-sm-2 control-label"><label style="color:red">*</label>菜单URL</div>
					<div class="col-sm-10">
						<input type="text" class="form-control" v-model="menu.url"
							placeholder="菜单URL" />
					</div>
				</div>
				<div v-if="menu.type == 1" class="form-group">
					<div class="col-sm-2 control-label">菜单编码</div>
					<div class="col-sm-10">
						<input type="text" class="form-control" v-model="menu.menuCode"
							placeholder="菜单编码" />
						<!-- <code style="margin-top:4px;display: block;">菜单编码</code> -->
					</div>
				</div>
				<div v-if="menu.type == 1 || menu.type == 2" class="form-group">
					<div class="col-sm-2 control-label">授权标识</div>
					<div class="col-sm-10">
						<input type="text" class="form-control" v-model="menu.perms"
							placeholder="多个用逗号分隔，如：user:list,user:create" />
					</div>
				</div>
				<div v-if="menu.type != 2" class="form-group">
					<div class="col-sm-2 control-label">排序号</div>
					<div class="col-sm-10">
						<input type="number" class="form-control" v-model="menu.orderNum"
							placeholder="排序号" />
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-2 control-label"></div>
					<input type="button" class="btn btn-primary" @click="saveOrUpdate"
						value="确定" /> &nbsp;&nbsp;<input type="button"
						class="btn btn-warning" @click="reload" value="返回" />
				</div>
			</form>
		</div>
	</div>

	<!-- 选择菜单 -->
	<div id="menuLayer" style="display: none; padding: 10px;">
		<ul id="menuTree" class="ztree"></ul>
	</div>

<script>
var base_url = '${base_url}'
layui.use('layer',function(){
	
});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "menuId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        menu:{
            parentName:null,
            parentId:0,
            type:0,
            orderNum:0
        }
    },
    created : function() {
    	var ss=this.showList;
    	console.log(!ss);
    	
	},
	
    methods: {
        getMenu: function(menuId){
        	//加载菜单树
        	console.log("加载树形菜单");
        	console.log("加载树形菜单");
            $.get(baseURL + "sys/menu/select", function(r){
            	console.log(r);
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
                console.log(ztree);
                var node = ztree.getNodeByParam("menuId", vm.menu.parentId);
                ztree.selectNode(node);
                vm.menu.parentName = node.name;
            });
        },
        add: function(){
        	console.log(555555);
            vm.showList = false;
            vm.title = "新增";
            vm.menu = {parentName:null,parentId:0,type:0,orderNum:0};
            vm.getMenu();
        },
        update: function () {
            var menuId = getMenuId();
            if(menuId == null){
                return ;
            }

            $.get(baseURL + "sys/menu/info/"+menuId, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.menu = r.menu;

                vm.getMenu();
            });
        },
        del: function () {
            var menuId = getMenuId();
            if(!menuId){
                return
            }
        		var open = layer.open({
		    		   title:'提示信息',
		               content:'确定要删除选中的记录？', 
		               shade:0.3, 
		               shadeClose:1, 
		               moveType:1, 
		               btn:['确定','取消'], 
		               btn1:function(){ 
		            	  $.ajax({
	                         type: "POST",
	                         url: baseURL + "sys/menu/delete",
	                         data: "menuId=" + menuId,
	                         success: function(r){
	                             if(r.code === 0){
	                                 layer.msg('操作成功',{aim:0}, function(){
	                                     vm.reload();
	                                 });
	                             }else{
	                                 layer.msg(r.msg,{aim:0});
	                             }
	                         }
	                      });
		               },
		               btn2:function(){ 
		            	   
		               }
		           });

        		/*confirm('确定要删除选中的记录？', function(){ 
                     $.ajax({
                         type: "POST",
                         url: baseURL + "sys/menu/delete",
                         data: "menuId=" + menuId,
                         success: function(r){
                             if(r.code === 0){
                                 alert('操作成功', function(){
                                     vm.reload();
                                 });
                             }else{
                                 alert(r.msg);
                             }
                         }
                     });
                });*/
           //});
           
        },
        saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

             var url = vm.menu.menuId == null ? "sys/menu/save" : "sys/menu/update";
            $.ajax({
                type: "POST",
                url:  baseURL + url,
                async:false,
                contentType: "application/json",
                data: JSON.stringify(vm.menu),
                success: function(r){
                	console.log(456);
                	
                	
                    if(r.code === 0){
                        layer.msg('操作成功',{aim:0}, function(){
                            vm.reload();
                        });
                    }else{
                    	layer.msg(r.msg,{aim:0});
                    }
                },
                error: function(error){
                	console.log(132);
                	console.log(error);
                	if(error.responseJSON.code != 200){
                		layer.msg(error.responseJSON.msg,{anim:6,icon:5});
                	}
                }
            }); 
        },
        menuTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    vm.menu.parentId = node[0].menuId;
                    vm.menu.parentName = node[0].name;
                    
                    layer.close(index);
                    //$("#menuLayer").hide();
                },
                btn2:function(){
                	//$("#menuLayer").hide();
                },
                end: function(){ 
                	$("#menuLayer").hide();  
                }  
            });
        },
        reload: function () {
            vm.showList = true;
            Menu.table.refresh();
        },
        validator: function () {
            if(isBlank(vm.menu.name)){
                layer.msg("菜单名称不能为空",{aim:0});
                return true;
            }

            //菜单
            if(vm.menu.type === 1 && isBlank(vm.menu.url)){
            	layer.msg("菜单URL不能为空",{aim:0});
                return true;
            }
        }
    }
});


var Menu = {
    id: "menuTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '菜单ID', field: 'menuId', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '菜单名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '上级菜单', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '图标', field: 'icon', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
            return item.icon == null ? '' : '<i class="'+item.icon+' fa-lg"></i>';
        }},
        {title: '类型', field: 'type', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
            if(item.type === 0){
                return '<span class="label label-primary">目录</span>';
            }
            if(item.type === 1){
                return '<span class="label label-success">菜单</span>';
            }
            if(item.type === 2){
                return '<span class="label label-warning">按钮</span>';
            }
        }},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '菜单URL', field: 'url', align: 'center', valign: 'middle', sortable: true, width: '160px'}
        /*{title: '授权标识', field: 'perms', align: 'center', valign: 'middle', sortable: true}*/]
    return columns;
};


function getMenuId () {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
    	layer.msg("请选择一条记录",{aim:0});
        return false;
    } else {
        return selected[0].id;
    }
}


$(function () {
    var colunms = Menu.initColumn();
    var table = new TreeTable(Menu.id, baseURL + "sys/menu/list", colunms);
    table.setExpandColumn(2);
    table.setIdField("menuId");
    table.setCodeField("menuId");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    Menu.table = table;
    
});

</script>


</body>
</html>