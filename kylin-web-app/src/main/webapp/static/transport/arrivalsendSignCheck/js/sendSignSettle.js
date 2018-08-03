/*
 * creatd by wyp on 2018/2/1
 *
 *送货派车签收结算
 */

var s_type=1;
var vm = new Vue({
	el:'#rrapp',
	data:{
		flag: true,
		caseinfo: {},
		tabinfo: {},
		orderList:{},
		tjinfo:{}
			
	},
	watch:{
		  caseinfo:function(){
			  this.$nextTick(function(){
					var addTrLen=$(".addTr").length;
					if(addTrLen<1){
						var html=$(".newTr").eq(0).html();
						var newList="<tr class='addTr'>"+html+"</tr>"
						$(".updateTr").before(newList);
						var s_html=$(".newTr").eq(1).html();
						var s_newList="<tr class='s_addTr'>"+s_html+"</tr>"
						$(".s_updateTr").before(s_newList);
					};
					this._dataHandler();//调用日期
					this._rowsHandler();
				
					$("#thtime").val($("#thtime").attr("data"));
					$("#khqstime").val($("#khqstime").attr("data"));

					$(".cftime").each(function(i){
						$(this).val($(this).attr("data"));
					});
					$(".ddtime").each(function(i){
						$(this).val($(this).attr("data"));
					});
					$(".qdfhtime").each(function(i){
						$(this).val($(this).attr("data"));
					});
			  });
		  }
	},
	methods: {	
		getInfo: function(obj,type){
			var baseValue={pcdid:$("#waybillId").val()}
			console.log(baseValue);
			  layui.use(['layer','form'], function(){
		    		var layer = layui.layer;
					EasyAjax.ajax_Post_Json({
						  dataType: 'json',
						  url:base_url + '/transport/carOut/deliveryAccounting/search',
						  data:JSON.stringify(baseValue),
						  contentType : 'application/json',
						  errorReason: true,
						  beforeHandler: function(){
			                	loading = layer.load(0,{
			                		shade: [0.5,'#fff']
			                	})
			                },
			                afterHandler: function(){
			                	layer.close(loading);
			                }
						},function(res){
							console.log(res);
							if(res.resultCode==200){
								vm.caseinfo=res.resultInfo.transportCarOut; //
								vm.orderList=res.resultInfo.transportCarOutDetailSecondList; //运单信息
								vm.tabinfo=res.resultInfo.transportCarOutDetailFourthList;//司机信息
								vm.tjinfo=res.resultInfo.transportCarOutDetailFifthlList; //统计员信息
								//时间格式化
								vm.caseinfo.thTime=getMyDate(res.resultInfo.transportCarOut.khqsTime);
								vm.caseinfo.khqsTime=getMyDate(res.resultInfo.transportCarOut.khqsTime);
								$(res.resultInfo.transportCarOutDetailFourthList).each(function(i,item){
									item.cftime=getMyDate(item.cftime);
									item.ddtime=getMyDate(item.ddtime);
								})
								
								$(res.resultInfo.transportCarOutDetailFifthlList).each(function(i,item){
									item.qdfhtime=getMyDate(item.qdfhtime);
								})
								$("#orderId").val($("#waybillId").val());								
								
							}else{
								layer.msg(res.reason);
							}
						});
					  }); 

		},
		//新增
		addHandler:function(){
			var orderId=$("#orderId").val();
			if(orderId!=""){
				var len=$(".addTr").length;
				if(len<4){
					var html=$(".newTr").eq(0).html();
					var newList="<tr class='addTr'>"+html+"</tr>"
					$(".updateTr").before(newList);
					this._dataHandler();//调用日期
					this._rowsHandler();//计算rowspan
				}else{
					layer.msg("最多只能添加4条数据！");
				}
			}else{
				layer.msg("请先查询运单编号")
			}
		},
		//新增统计员结算
		addAccountHandler:function(){
			var orderId=$("#orderId").val();
			if(orderId!=""){
				var len=$(".s_addTr").length;
				if(len<4){
					var html=$(".newTr").eq(1).html();
					var newList="<tr class='s_addTr'>"+html+"</tr>"
					$(".s_updateTr").before(newList);
				}else{
					layer.msg("最多只能添加4条数据！");
				}
				this._dataHandler();//调用日期
			}else{
				layer.msg("请先查询运单编号")
			}


		},
		//删除
		delite:function(_this){
			del_Box(_this,".addTr");		   
				 
		},
		delHandler:function(_this){
			del_Box(_this,".s_addTr");		   
				 
		}, //调用laydate时间
		_dataHandler:function(){
			$('.date').removeAttr("lay-key");
		    layui.use('laydate', function(){
	    		var laydate = layui.laydate;
	    		lay('.date').each(function(){
	    			laydate.render({
	    				elem: this,
	    				theme: '#009688',
	    				type: 'datetime',
	    				trigger: 'click',
	    				format: 'yyyy-MM-dd HH:mm' //可任意组合
	    			});
	    		});	
	 	  
	    	})
		},
		_rowsHandler:function(){
			var reLen=$(".s_addTr").length;
			$(".rowsHsr").attr("rowspan",reLen);
			$(".s_addTr").each(function(i){
				$(".s_addTr").eq(i+1).find("td:last").remove();
			});
			
		},
        //校验
		filedCheck:function(){
			var flag=true;
			var totals=parseFloat($("#totals").text());
			var re_totals=parseFloat($("#re_totals").text());
			if(totals!=re_totals){
				flag=false;
			}
			return flag;
		},
		 //保存数据
		 saveHandler:function(){
			var firstInfo={},driverInfo=[],baseInfo = [];
			 firstInfo.id=$("#orderId").val();//派车单号
			 firstInfo.thTime=new Date($("#thtime").val());//仓库提货时间
			 firstInfo.khqs=$("#khqs").val();//客户签收
			 firstInfo.zxbz=$("#zxbz").val();//装卸班组
			 firstInfo.khqsTime=new Date($("#khqstime").val());//客户签收时间
			 firstInfo.cgyqm=$("#cgyqm").val();//仓库员签名
			 firstInfo.sjComm=$("#sjComm").val();//
			 firstInfo.tjComm=$("#tjComm").val();//
			 
			 //司机信息
			  var  driverRows=$(".addTr").length;
			  for (var i = 0; i < driverRows; i++) {
				   var trInf={}; 
				   var trLen= $(".addTr:eq("+i+")");
				   trInf.ch = trLen.find("td:eq(0) input").val();//车号
				   trInf.sj = trLen.find("td:eq(1) input").val();//
				   trInf.cftime= new Date(trLen.find("td:eq(2) input").val());//
				   trInf.cflmb= trLen.find("td:eq(3) input").val();
				   trInf.ddtime= new Date(trLen.find("td:eq(4) input").val()); 
				   trInf.ddlmb= trLen.find("td:eq(5) input").val();
				   trInf.tzlqf= trLen.find("td:eq(6) input").val();//
				   trInf.tzfk= trLen.find("td:eq(7) input").val();//
				   trInf.tzwxf= trLen.find("td:eq(8) input").val();//
				   trInf.jylmb= trLen.find("td:eq(9) input").val();//
				   trInf.jyss= trLen.find("td:eq(10) input").val();//
				   trInf.jyje= trLen.find("td:eq(11) input").val();//
				   driverInfo.push(trInf);
			    }
			  
		    //统计员信息
			  var  baseRows=$(".s_addTr").length;
			  for (var i = 0; i < baseRows; i++) {
				   var trInf={}; 
				   var trLen= $(".s_addTr:eq("+i+")");
				   trInf.clxz = trLen.find("td:eq(0) select").find("option:selected").val();//
				   trInf.ch = trLen.find("td:eq(1) input").val();//
				   trInf.yfbz= trLen.find("td:eq(2) input").val();//
				   trInf.yfjs= trLen.find("td:eq(3) input").val();
				   trInf.tc= trLen.find("td:eq(4) input").val(); 
				   trInf.qdfhtime= new Date(trLen.find("td:eq(5) input").val());
				   baseInfo.push(trInf);
			    }
			 var baseValue={
					transportCarOut:firstInfo,
					transportCarOutDetailFourthList:driverInfo,   //司机信息
					transportCarOutDetailFifthlList:baseInfo  //统计员信息
			 }	  	
			console.log(baseValue);
			EasyAjax.ajax_Post_Json({
				  dataType: 'json',
				  url:base_url + '/transport/carOut/deliveryAccounting/save',
				  data:JSON.stringify(baseValue),
				  contentType : 'application/json',
				  errorReason: true,
				  beforeHandler: function(){
	                loading = layer.load(0,{
	                	shade: [0.5,'#fff']
	                	})
	                },
	                afterHandler: function(){
	                	layer.close(loading);
	                }
				},function(res){
					if(res.resultCode==200){
						layer.msg(res.reason,  {
							icon: 1,
						    time: 2000 //2s后自动关闭
						  }, function(){   //保存成功后页面展示
							  location.reload();
							  $(window).scrollTop(0);
						 });
						
					}else{
						layer.msg(res.reason);
					}
				});
					
	   }	
	},
	mounted: function(){

	}
});


//通用删除
function del_Box(_this,obj){
 	 var htmDiv=$(_this).parents("tr");
     var tabRows=$(obj).length;
	  if(tabRows>1){   
		 layer.confirm('',{
			btn: ['确定', '取消'],
			title:'提示信息',
			content:'确认删除吗？',		
			shadeClose: true //开启遮罩关闭
		 },function(){
			layer.closeAll();
			htmDiv.remove();
		 });
	   }else{
		   layer.msg("至少保留一条数据");
	   }			   
		 
}

var insert = {
		init: function() {
			layui.use(['form', 'laydate'], function(){
				form = layui.form;
				layer = layui.layer;
		  		 //查询	
				form.on('submit(searchBtn)', function(data){
					var orderId=$("#waybillId").val();
					vm.getInfo();
	    		 	return false;
	    		})
	            //保存			
				form.on('submit(save)', function(data){
					var orderId=$("#orderId").val();
					if(orderId!=""){
						vm.saveHandler();
					}else{
						layer.msg("请先查询派车单号")
					}
	    		 	return false;
	    		})				
			});
			
		}
}

insert.init();
     //时间戳 毫秒转换日期
     function getMyDate(str){ 
    	 if(str==null){
    		 return "";
    	 }else{
	         var subDate = new Date(str), 
	         subYear = subDate.getFullYear(),  
	         subMonth = subDate.getMonth()+1,  
	         subDay = subDate.getDate(),  
	         subHour = subDate.getHours(),  
	         subMin = subDate.getMinutes(),  
	       //  subSen = subDate.getSeconds(),  
	         subTime = subYear +'-'+ getzf(subMonth) +'-'+ getzf(subDay) +' '+ getzf(subHour) +':'+ getzf(subMin);//最后拼接时间  
	         return subTime;  
         }
     };  
     //补0操作  
     function getzf(num){  
         if(parseInt(num) < 10){  
             num = '0'+num;  
         }  
         return num;  
     }  