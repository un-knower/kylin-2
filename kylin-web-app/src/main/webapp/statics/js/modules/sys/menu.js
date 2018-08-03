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
	watch:function(){
		var pp=this.showList;
		console.log(pp);
	},
    methods: {
    	addssssss:function(){
    		console.log(999);
    	},
        getMenu: function(menuId){
        	//加载菜单树
            $.get(baseURL + "sys/menu/select", function(r){
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
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
            //layui.use('layer',function(){
	            

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
                contentType: "application/json",
                data: JSON.stringify(vm.menu),
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
                    $("#menuLayer").hide();
                },
                btn2:function(){
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
    console.log(colunms);
    var table = new TreeTable(Menu.id, baseURL + "sys/menu/list", colunms);
    console.log(table);
    console.log(Menu.id);
    console.log(baseURL + "sys/menu/list");
    console.log(colunms	);
    table.setExpandColumn(2);
    table.setIdField("menuId");
    console.log(2);
    table.setCodeField("menuId");
    console.log(3);
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    console.log(4);
    table.init();
    console.log(5);
    Menu.table = table;
    console.log(6);
    
});
