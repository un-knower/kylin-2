/*
 * creatd by wyp on 2018/2/1
 *
 *送货派车签收结算
 */

var s_type=1;
var vm = new Vue({
	el:'#arrivellodiing',
	data:{
		flag: true,
		caseinfo: {},
		tabinfo: {},
		orderList:{},
		driverList:{},
		tjList:{},
		s_driverList:"",
		s_tjList:"",
		addArr:{'ch':'','sj':'','cftime':'','cflmbs':'','ddtime':'','ddlmbs':'','tzlqf':'','tzfk':'','tzwxf':'','tzlmbs':'','tzjyss':'','tzjyje':''}, //新增的表单字段 
		s_addArr:{'clxz':'','ch':'','yfbz':'','yfjs':'','tc':'','qsdfhtime':''}
	},
	watch:{
		driverList:function(){
			  this.$nextTick(function(){
				  var addTrLen=$(".newTr").length;
					if(addTrLen<1){
						var html=$(".s_newTr").eq(0).html();
						var newList="<tr class='newTr'>"+html+"</tr>"
						$(".driverBox").before(newList);
						
						var s_html=$(".s_newTr").eq(1).html();
						var s_newList="<tr class='tjTr'>"+s_html+"</tr>"
						$(".tjBox").before(s_newList);
					};
				  
				  this._dataHandler();//调用日期
					$(".cftime").each(function(i){
						$(this).val($(this).attr("data"));
					});
					$(".ddtime").each(function(i){
						$(this).val($(this).attr("data"));
					});
					$(".qsdfhtime").each(function(i){
						$(this).val($(this).attr("data"));
					});
			  });
		  },
	   tjList:function(){
		  this.$nextTick(function(){
			  this._dataHandler();//调用日期
		  });
	  }
	},
	beforeCreate: function () {
	   // console.log('调用了beforeCreat钩子函数');
	  },
	created: function () {
		          this.getInfo()
    },
	methods: {	
		getInfo: function(){
			var baseValue={pcid:$("#pcid").val()}
			  layui.use(['layer','form'], function(){
		    		var layer = layui.layer;
		    		$.ajax({
		    			type : "post",
		    			dataType: 'json',
		    			url :base_url + '/transport/bundleArrive/searchPickGoodsAccount',
		    			data:JSON.stringify(baseValue),
		    			 contentType : 'application/json',
		    			 beforeSend: function(){
			                	loading = layer.load(0,{
			                		shade: [0.5,'#fff']
			                	})
			             },
			             complete: function(){
			                	layer.close(loading);
			             },
		    			success : function(res){
		    				console.log(res);
		    				if(res.resultCode==200){
								vm.caseinfo=res.T_CAR_IN_INFO;  //运单信息
								vm.caseinfo.xdtime=res.T_CAR_IN_INFO.xdtime.substring(0,19);
								vm.driverList=res.T_CAR_IN_DETAIL_THREE_LIST;//司机信息
								vm.tjList=res.T_CAR_IN_DETAIL_FIVE_LIST;
								//时间格式化                    
								$(res.T_CAR_IN_DETAIL_THREE_LIST).each(function(i,item){
									item.cftime=getMyDate(item.cftime);
									item.ddtime=getMyDate(item.ddtime);
								});
								$(res.T_CAR_IN_DETAIL_FIVE_LIST).each(function(i,item){
									item.qsdfhtime=getMyDate(item.qsdfhtime);
								})
							}else{
								layer.msg(res.reason);
								vm.flag=false;
							}
		    			},
		    	        error : function(XMLHttpRequest, textStatus, errorThrown) {
		    	            if(XMLHttpRequest.readyState == 0) {
		    	            //here request not initialization, do nothing
		    	            } else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
		    	                alert("服务器忙，请重试!");
		    	            } else {
		    	                alert("系统异常，请联系系统管理员!");
		    	            }
		    	        }
		    		});
				}); 
		},
		  //新增统计员信息
		s_addHandler:function(){  
			if(vm.flag){
				var len=$(".tjTr").length;
				if(len<4){
					var html=$(".s_newTr").eq(1).html();
					var newList="<tr class='tjTr'>"+html+"</tr>"
					$(".tjBox").before(newList);
					this._dataHandler();//调用日期
				 }else{
					 layer.msg("最多可添加4条数据")
				 }
			}
        },  
		//新增
		addHandler:function(){
			if(vm.flag){
				var len=$(".newTr").length;
				if(len<4){
					var html=$(".s_newTr").eq(0).html();
					var newList="<tr class='newTr'>"+html+"</tr>"
					$(".driverBox").before(newList);
					this._dataHandler();//调用日期 				
				 }else{
					 layer.msg("最多可添加4条数据")
				 }
			}
		},

		//删除
		delite:function(_this){
			//var _this=event.currentTarget;
			del_Box(_this,".newTr");		   
				 
		},
		delHandler:function(_this){
			//var _this=event.currentTarget;
			del_Box(_this,".tjTr");		   
				 
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
	    				format: 'yyyy-MM-dd HH:mm:ss', //可任意组合
    					done: function(value, date, endDate){ //选择日期完毕的回调
	    				},
	    			});
	    		});	
	 	  
	    	})
		},
		 //保存数据
		 saveHandler:function(){
			 var firstInfo={},driverInfo=[],baseInfo = [];
			 firstInfo.id=$("#pcid").val();//派车单号
			 firstInfo.sjcomm=$("#sjcomm").val();
			 firstInfo.tjybz=$("#tjybz").val();
			 firstInfo.tjyhsrgrid=$("#tjyhsrgrid").val();
			 firstInfo.tjyhsr=$("#tjyhsr").val();
			 
			 //司机信息
			  var  driverRows=$(".newTr").length;
			  for (var i = 0; i < driverRows; i++) {
				   var trInf={}; 
				   var trLen= $(".newTr:eq("+i+")");
				   trInf.ch = trLen.find("td:eq(0) input").val();//车号
				   trInf.sj = trLen.find("td:eq(1) input").val();//
				   trInf.cftime=new Date(trLen.find("td:eq(2) input").val());//
				   trInf.cflmbs= trLen.find("td:eq(3) input").val();
				   trInf.ddtime=new Date(trLen.find("td:eq(4) input").val()); 
				   trInf.ddlmbs= trLen.find("td:eq(5) input").val();
				   trInf.tzlqf= trLen.find("td:eq(6) input").val();//
				   trInf.tzfk= trLen.find("td:eq(7) input").val();//
				   trInf.tzwxf= trLen.find("td:eq(8) input").val();//
				   trInf.tzlmbs= trLen.find("td:eq(9) input").val();//
				   trInf.tzjyss= trLen.find("td:eq(10) input").val();//
				   trInf.tzjyje= trLen.find("td:eq(11) input").val();//
				   driverInfo.push(trInf);
			    }
			  
		    //统计员信息
			  var  baseRows=$(".tjTr").length;
			  for (var i = 0; i < baseRows; i++) {
				   var trInf={}; 
				   var trLen= $(".tjTr:eq("+i+")");
				   trInf.clxz = trLen.find("td:eq(0) select").find("option:selected").val();//
				   trInf.ch = trLen.find("td:eq(1) input").val();//
				   trInf.yfbz= trLen.find("td:eq(2) input").val();//
				   trInf.yfjs= trLen.find("td:eq(3) input").val();
				   trInf.tc= trLen.find("td:eq(4) input").val(); 
				   trInf.qsdfhtime=new Date(trLen.find("td:eq(5) input").val());
				   baseInfo.push(trInf);
			    }
			 var baseValue={
					 tcarPick:firstInfo,
					 queryTCarDetailThreeList:driverInfo,   //司机信息
					 queryTCarDetailFiveList:baseInfo   //统计员信息
			 }	  	
			console.log(baseValue);
			EasyAjax.ajax_Post_Json({  
				  dataType: 'json',
				  url:base_url + '/transport/bundleArrive/savePickGoodsAccount',
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
						layer.msg(res.message,  {
							icon: 1,
						    time: 2000 //2s后自动关闭
						  }, function(){   //保存成功后页面展示
							//  location.reload();
							//  $(window).scrollTop(0);
						 });
						
					}else{
						layer.msg(res.reason);
					}
				});
					
	   }	
	}
});


//通用删除
function del_Box(_this,obj){
 	 var htmDiv=$(_this).parent("td").parent("tr");
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

	            //保存			
				form.on('submit(save)', function(data){
					vm.saveHandler();
	    		 	return false;				
			    });	
				
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
	         subSen = subDate.getSeconds(),  
	         subTime = subYear +'-'+ getzf(subMonth) +'-'+ getzf(subDay) +' '+ getzf(subHour) +':'+ getzf(subMin)+':'+ getzf(subSen);//最后拼接时间  
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