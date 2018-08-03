/*
 * created by yl on 2018/5/17
 *  录入客户价格登记
 */    

	layui.use(['element', 'layer', 'form'], function(){
		var element = layui.element;
		var form = layui.form;
		element.init();
		form.render();
	});

	var vm = new Vue({
		el:"#rrapp",
		data:{ 
			company:{},        //初始化传参 
			saveCompany:{},    //保存传参
			allCompany:[],     //所有公司
			companyList:[],    //所有公司渲染
			daoZhanList:[],    //已添加线路列表渲染
			noDaoZhanList:[],  //未添加线路列表渲染
			isSelect:true,     //选择公司不可点
			isQuery:false,     //不显示查询按钮
			isAddList:false,   //不显示添加公司列表
			leftNum:0,         //左边选中个数
			rightNum:0,        //右边选中个数
			addNum:0,          //点击添加删除次数
			isAdd:true,        //是否添加
			isDel:true,        //是否删除
			isSave:true       //是否保存
		},
		watch:{
			isQuery:function(){
				var _this = this;
				this.$nextTick(function(){
					_this.companyList = _this.allCompany;
					console.log(_this.companyList)
				});
			},
			leftNum:function(){
				console.log("添加按钮",vm.leftNum);
				console.log("保存",vm.addNum);
				if(vm.leftNum < 1){
					vm.isAdd = true;
				}/*else{
					vm.isSave = false;
				}
				if(vm.leftNum < 1 && vm.addNum == 0){
					vm.isSave = true;
				}*/
			},
			rightNum:function(){
				console.log("删除按钮",vm.rightNum);
				console.log("保存",vm.addNum);
				if(vm.rightNum < 1){
					vm.isDel = true;
				}/*else{
					vm.isSave = false;
				}
				if(vm.rightNum < 1 && vm.addNum == 0){
					vm.isSave = true;
				}*/
			},
			addNum:function(){
				if(vm.addNum > 0){
					vm.isSave = false;
				}
			}
		},
		created:function(){
			this._initDOM();
		},
		mounted: function(){
			layui.use(['layer','form'],function(){
				var form = layui.form;
				form.on('submit(inquire)', function(data){
				 	console.log(data);
				 	vm.company = data.field;
					//请求所有公司列表接口
				 	vm._ajaxRequest(vm.company);
				 	vm.isAddList = true;
					return false;
				}); 
			});
		},
		methods:{
			_initDOM:function(){
				var _this = this;
				layui.use('layer',function(){
					_this.company.company = userCompany;
					_this._ajaxRequest(_this.company);
					//判断当前登录公司是不是总公司
					if(userCompany != "总公司"){
						_this.companyList.push(userCompany);
						_this.isAddList = true;
					}else{
						_this.isSelect = false;
						_this.isQuery = true;
						_this.companyList = _this.allCompany;
					}
					return false;
				});
				
			},
			//表单提交请求已添加线路和未添加线路
			_ajaxRequest:function(company){
				var _this = this;
				$.ajax({
					url: base_url + '/customer/selectDaoZhanBaseData',
	                type: 'post',
	                dataType: 'JSON',
	                contentType: 'application/json',
	                async:false,
	                data: JSON.stringify(company),
	                beforeSend: function(){
	                    loading = layer.load(0,{
	                      shade: [0.5,'#fff']
	                   }) 
	                },
	                complete:function(){
	                   layer.close(loading);
	                },
	                success: function(data){
	                	console.log(data);
	                	if(data.resultCode != "200"){
	                		layer.open({title:'提示信息',content:data.reason});
	                	}else{
	                		if(data.data.allCompany != ""){
	                			var arr = [];
	                			for(var k in data.data.allCompany){
	                				arr.push(data.data.allCompany[k].companyName);
	                			}
		                		_this.allCompany = arr;
	                		}
	                		_this.daoZhanList = data.data.daoZhan;
	                		_this.noDaoZhanList = data.data.noDaoZhan;
	                	}
	                	
	                },
	                error:function(data){
	                	console.log(data);
	                }
				});
			},
			//选择要添加的
			selectAdd:function(index){
				var flag = $(".add-ul li").eq(index).hasClass("current");
				if(flag){
					$(".add-ul li").eq(index).removeClass("current");
					$(".add-ul li").eq(index).attr("num","00");
					vm.leftNum --;
				}else{
					$(".add-ul li").eq(index).addClass("current");
					$(".add-ul li").eq(index).attr("num","11");
					vm.leftNum ++;
				}
				if(vm.leftNum > 0){
					vm.isAdd = false;
				}
			},
			//添加线路
			addCircuit:function(){
				var _this = this;
				vm.leftNum = 0;
				vm.addNum ++;
				console.log("添加",vm.addNum);
				$(".add-ul li").each(function(){
					if($(this).attr("num") == "11"){
						var ele = $(this).html();
						//当前公司添加线路添加一个
						_this.daoZhanList.unshift(ele);
						//未添加线路删除一个
						_this.noDaoZhanList.splice($.inArray(ele, _this.noDaoZhanList), 1);
						$(this).removeClass("current");
						$(this).removeAttr("num");
						console.log(_this.noDaoZhanList);
					}
				});
			},
			//选择要删除的
			selectDel:function(index){
				var flag = $(".del-ul li").eq(index).hasClass("current");
				if(flag){
					$(".del-ul li").eq(index).removeClass("current");
					$(".del-ul li").eq(index).attr("num","00");
					vm.rightNum --;
				}else{
					$(".del-ul li").eq(index).addClass("current");
					$(".del-ul li").eq(index).attr("num","11");
					vm.rightNum ++;
				}
				if(vm.rightNum > 0){
					vm.isDel = false;
				}
			},
			//删除线路
			delCircuit:function(){
				var _this = this;
				vm.rightNum = 0;
				vm.addNum ++;
				console.log("删除",vm.addNum);
				$(".del-ul li").each(function(){
					if($(this).attr("num") == "11"){
						var ele = $(this).html();
						//未添加线路添加一个
						_this.noDaoZhanList.unshift(ele);
						//当前公司添加线路删除一个
						_this.daoZhanList.splice($.inArray(ele, _this.daoZhanList), 1);
						$(this).removeClass("current");
						$(this).removeAttr("num");
					}
				});
			},
			//保存修改
			saveChange:function(){
				var _this = this;
	            vm.saveCompany.daoZhan = _this.daoZhanList.join(",");
	            vm.saveCompany.company = vm.company.company;
	            layui.use('layer',function(){
	            	$.ajax({
						url: base_url + '/customer/editDaoZhanBaseData',
						type:'post',
						dataType:'json',
						contentType:'application/json',
		                data: JSON.stringify(_this.saveCompany),
		                beforeSend: function(){
		                    loading = layer.load(0,{
		                      shade: [0.5,'#fff']
		                   }) 
		                },
		                complete:function(){
		                	layer.close(loading);
		                },
		                success:function(data){
		                	if(data.resultCode == "200"){
		                		layer.msg("保存成功！");
		                		_this.isSave = true;
		                		vm.addNum = 0;
		                	}else{
		                		layer.open({title:"提示信息",content:data.reason});
		                	}
		                },
		                error:function(data){
		                	
		                }
					});
	            });
				
			}
		}
	});
	