/*
 * created by yl on 2018/5/17
 *  周期性结款客户设置
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
			khbmText:'',      //客户编码
			khmcText:'',   //客户名称
			queryData:{    //查询传参
				khbm:'',      //客户编码
				khmc:'',   //客户名称
			}, 
			queryResult:{},  //查询渲染列表
			saveList:{},    //保存数据
			isShowResult:false,  //是否显示基本信息表格
			isShowSave:false,  //是否显示保存按钮
		},
		watch:{
			queryResult:function(){
				this.$nextTick(function(){
					$(".amend-input").each(function(){
						$(this).on("input propertychange",function(){ 
							vm.isShowSave = true; 
						}) 
					});
					$("#rrapp input").attr("autocomplete","off");
					layui.use(['layer', 'form','laydate'], function(){
						var laydate = layui.laydate;
						var form = layui.form;
						//合同开始日期
						laydate.render({
							elem: '#contractstartdate',
							type: 'datetime',
							done: function(value, date, endDate){
							    vm.queryResult.contractstartdate = value;
							    vm.isShowSave = true;
							}
						});
						//合同制终止日期
						laydate.render({
							elem: '#contractenddate',
							type: 'datetime',
							done: function(value, date, endDate){
							    vm.queryResult.contractenddate = value;
							    vm.isShowSave = true;
							}
						});
						//初次对账单生成日期
						laydate.render({
							elem: '#firststatementdate',
							type: 'datetime',
							done: function(value, date, endDate){
							    vm.queryResult.firststatementdate = value;
							    vm.isShowSave = true;
							}
						});
						//下次对账单生成日期
						laydate.render({
							elem: '#laststatementdate',
							type: 'datetime',
							done: function(value, date, endDate){
							    vm.queryResult.laststatementdate = value;
							    vm.isShowSave = true;
							}
						});
						//结款方式
						form.on('select(uclearingcode)', function(data){
							if(data.value != vm.queryResult.uclearingcode){
								vm.isShowSave = true;
							}else{
								vm.isShowSave = false;
							}
						});
						//是否启用此客户资料
						form.on('radio(rec_flag)', function(data){
							if(data.value != vm.queryResult.rec_flag){
								vm.isShowSave = true;
							}else{
								vm.isShowSave = false;
							}
						});
						//是否预付款
						form.on('radio(isyfk)', function(data){
							if(data.value != vm.queryResult.isyfk){
								vm.isShowSave = true;
							}else{
								vm.isShowSave = false;
							}
						});
						//是否返单结算
						form.on('radio(ufandan)', function(data){
							if(data.value != vm.queryResult.ufandan){
								vm.isShowSave = true;
							}else{
								vm.isShowSave = false;
							}
						});
						//周期性结算
						form.on('radio(isyuejie)', function(data){
							if(data.value != vm.queryResult.isyuejie){
								vm.isShowSave = true;
							}else{
								vm.isShowSave = false;
							}

						});
						//客户状态
						form.on('radio(recordstatus)', function(data){
							if(data.value != vm.queryResult.recordstatus){
								vm.isShowSave = true;
							}else{
								vm.isShowSave = false;
							}
						});
						
					});
				});
			},
		},
		created(){
		},
		mounted(){
			this.saveChange();
		},
		methods:{
			queryInfo(){
				var _this = this;
				layui.use('form', function(){
					var element = layui.element;
					var form = layui.form;
					vm.queryData.khbm = vm.queryData.khbm.trim();
					vm.queryData.khmc = vm.queryData.khmc.trim();
					if(!vm.queryData.khbm && !vm.queryData.khmc){
						vm.isShowResult = false;
						layer.msg('请输入客户编码或客户名称进行查询！');
						return
					}
					$.ajax({
						url: base_url + '/customer/findRecurringCustomers',
		                type: 'post',
		                dataType: 'JSON',
		                contentType: 'application/json',
		                data: JSON.stringify(vm.queryData),
		                beforeSend(){
		                    loading = layer.load(0,{
		                      shade: [0.5,'#fff']
		                   }) 
		                },
		                complete(){
		                   layer.close(loading);
		                },
		                success(data){
		                	if(data.resultCode != "200"){
		                		layer.open({title:'提示信息',content:data.reason});
		                		vm.isShowResult = false;
		                		vm.isShowSave = false;
		                	}else{
		                		vm.isShowResult = true;
		                		
		                		//有无合同
			                	if($.trim(data.data.ywht) == '1'){
			                		$(".ywht_yes").prop("checked","checked");
			                	}else if($.trim(data.data.ywht) == '0'){
			                		$(".ywht_no").prop("checked","checked");
			                	}
			                	//客户状态
			                	if($.trim(data.data.khzt) == '合作良好'){
			                		$(".zt_good").prop("checked","checked");
			                	}else if($.trim(data.data.khzt) == '问题客户'){
			                		$(".zt_issue").prop("checked","checked");
			                	}else if($.trim(data.data.khzt) == '流失客户'){
			                		$(".zt_loss").prop("checked","checked");
			                	}
			                	//理赔属性
			                	if($.trim(data.data.lipeisx) == '1'){
			                		$(".lipeisx_1").prop("checked","checked");
			                	}else if($.trim(data.data.lipeisx) == '2'){
			                		$(".lipeisx_2").prop("checked","checked");
			                	}else if($.trim(data.data.lipeisx) == '3'){
			                		$(".lipeisx_3").prop("checked","checked");
			                	}
			                	//客户类型
			                	if($.trim(data.data.khlxbh) == '1'){
			                		$(".khlxbh_max").prop("checked","checked");
			                	}else if($.trim(data.data.khlxbh) == '2'){
			                		$(".khlxbh_mid").prop("checked","checked");
			                	}else if($.trim(data.data.khlxbh) == '3'){
			                		$(".khlxbh_min").prop("checked","checked");
			                	}
			                	//准运证明
			                	if($.trim(data.data.zyzm) == '1'){
			                		$(".zyzm_yes").prop("checked","checked");
			                	}else if($.trim(data.data.zyzm) == '0'){
			                		$(".zyzm_no").prop("checked","checked");
			                	}
			                	//运输方式
			                	$("input[data-name='yw_ky']").prop("checked",data.data.yw_ky);
			                	$("input[data-name='yw_xb']").prop("checked",data.data.yw_xb);
			                	$("input[data-name='yw_wd']").prop("checked",data.data.yw_wd);
			                	$("input[data-name='yw_xy']").prop("checked",data.data.yw_xy);
			                	$("input[data-name='yw_hk']").prop("checked",data.data.yw_hk);
			                	$("input[data-name='yw_jzx']").prop("checked",data.data.yw_jzx);
			                	$("input[data-name='yw_cj']").prop("checked",data.data.yw_cj);
			                	$("input[data-name='yw_sy']").prop("checked",data.data.yw_sy);
			                	$("input[data-name='yw_fl']").prop("checked",data.data.yw_fl);
			                	$("input[data-name='yw_gjys']").prop("checked",data.data.yw_gjys);
			                	$("input[data-name='yw_zz']").prop("checked",data.data.yw_zz);
			                	$("input[data-name='yw_cc']").prop("checked",data.data.yw_cc);
			                	$("input[data-name='yw_wx']").prop("checked",data.data.yw_wx);
			                	//客户周期性借款信息
			                	//是否启用此客户资料
		                		if($.trim(data.data.rec_flag) == '1'){
		                			$(".yesStart").prop("checked","checked");
			                	}else if($.trim(data.data.rec_flag) == '0'){
			                		$(".noStart").prop("checked","checked");
			                	}
		                		//周期性结款设置
		                		var uclearingcode = $.trim(data.data.uclearingcode);
		                		if(uclearingcode){
		                			$("#selMoney").val(uclearingcode);
		                		}else{
		                			$("#selMoney").prepend('<option value="" selected="selected" disabled>请选择结款方式</option>');
		                		}
		                		//是否预付款
		                		if($.trim(data.data.isyfk) == '1'){
		                			$(".yesYfk").prop("checked","checked");
			                	}else if($.trim(data.data.rec_flag) == '0'){
			                		$(".noYfk").prop("checked","checked");
			                	}
			                	//客户状态
			                	if($.trim(data.data.recordstatus) == '1'){
			                		$(".yesValid").prop("checked","checked");
			                	}else if($.trim(data.data.recordstatus) == '0'){
			                		$(".noValid").prop("checked","checked");
			                	}
			                	//是否返单结算
			                	if($.trim(data.data.ufandan) == '1'){
			                		$(".yesReord").prop("checked","checked");
			                	}else if($.trim(data.data.ufandan) == '0'){
			                		$(".noReord").prop("checked","checked");
			                	}
			                	//周期性结算 
			                	if($.trim(data.data.isyuejie) == '1'){
			                		$(".yesYuejie").prop("checked","checked");
			                	}else if($.trim(data.data.isyuejie) == '0'){
			                		$(".noYuejie").prop("checked","checked");
			                	}
			                	
		                		vm.queryResult = data.data;
		                		form.render();
		                		$(".layui-select-tips").remove();
		                	}
		                	
		                	
		                	
		                },
		                error(error){
		                	console.log(error);
		                }
					});
				});
			},
			saveChange(){
				layui.use('form',function(){
					var form = layui.form;
					//保存详情的表单验证
					vm.saveVerify();
					form.on('submit(saveBtn)', function(data){
						vm.saveList = data.field;
						vm.saveList.id = vm.queryResult.id;

						//获取录入详情数据并请求
						vm.ajaxSave(vm.saveList);
						return false;
					});
				});
			},
			//保存请求接口
			ajaxSave(para){
				$.ajax({
					url: base_url + '/customer/editRecurringCustomers',
		            type: 'post',
		            dataType: 'JSON',
		            contentType: 'application/json',
		            data: JSON.stringify(para),
		            beforeSend: function(){
		                loading = layer.load(0,{
		                  shade: [0.5,'#fff']
		               }) 
		            },
		            complete:function(){
		               layer.close(loading);
		            },
		            success: function(data){
		            	if(data.resultCode=="200"){
		            		layer.open({
		            			title:'提示信息',
		            			content:'保存成功！',
		            			end: function () {
		            				vm.isShowSave = false;
		                        }
		            		});
		            		
		            	}else{
		            		layer.open({
		            			title:'提示信息',
		            			content:data.reason
		            		});
		            	}
		            },
		            error:function(data){
		            	console.log(data);
		            }
				});
				
			},
			//保存验证
			saveVerify(){
				layui.use('form',function(){
					var form = layui.form;
					var laydate = layui.laydate;
					form.verify({
				        //只能填数字
						onlyNum: function(value,item){ 
							//非负整数（包括0）
				        	var reg = /^([1-9]\d*|[0]{1,1})$/;
				        	if($.trim(value)!=""){
				        		if(!reg.test(value)){  
						            return $(item).attr("dataname")+'只能为数字！';  
						        }
				        	}
				        },
				        //联系方式正则
				        phoneNum:function(value,item){
				        	var reg = /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^0?[1][358][0-9]{9}$)/;
				        	if($.trim(value)){
				        		if(!reg.test(value)){  
						            return $(item).attr("dataname")+'格式不正确';  
						        }
				        	}
				        },
				        //邮箱验证正则
				        emailNum:function(value,item){
				        	var reg = /^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$|^1[3|4|5|7|8]\d{9}$/;
				        	if($.trim(value)){
				        		if(!reg.test(value)){  
						            return $(item).attr("dataname")+'格式不正确!';  
						        }
				        	}
				        }
					});
				});
			}
			
		}
	});
	