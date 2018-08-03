/*
 * created by wyp on 2018/4/2
 *  等托放货指令
 */



var vm = new Vue({
	el:'#rrapp',
	data:{
		orderList:{},
		ddfhzt:'',
		tzfhzt:{},
		count:"",
		curr:"",
		flag:false,
		fazhan:"",
		collection: {

		}
		
	},
	watch:{
		collection:function(){
			  this.$nextTick(function(){
				//  alert($(".layui-table-main").find("table").find("tbody").html());
			  });
		  }
	},
	methods: {
		searchList:function(){
			layui.use('form',function(){
				var form = layui.form;
				form.on('submit(toCheck)', function(){
					vm.getList(1);
					return false;
				})
			})
		},
		
		getList: function(num){
			var baseValue={
//					type:$('input[name="s_type"]:checked ').val(),
					companyName:$("#companyN").val(),
					timeFrame:$("#selectDate").val(),
//					timeFrame: '',
					waybillNum:$("#waybillNum").val(),
					num:num,
					size:10
			};
			sessionStorage.setItem("data",baseValue);
			  layui.use(['layer','form'], function(){
		    	var layer = layui.layer;
					EasyAjax.ajax_Post_Json({
						  dataType: 'json',
						  url:base_url + '/transport/delivery/query/historyRecord',
						  data:JSON.stringify(baseValue),
						 // contentType:'application/json',
						  errorReason: true,
						  beforeHandler: function(){
			                /*	loading = layer.load(0,{
			                		shade: [0.5,'#fff']
			                	})*/
			                },
			                afterHandler: function(){
			                /*	layer.close(loading);*/
			                }
						},function(res){
							$(".layui-layer").hide();
							if(res.data!=undefined){
								
					    		vm.count=res.data.total;//总页数
						    	vm.curr=res.data.num;//当前页
						    	$(res.data.collection).each(function(i,item){
						    		item.fhshj=getMyDate(item.fhshj);
						    	});
						    	$("#laypage").show();
						    	vm.tableInf(res.data.collection);
						    	
							}else{
								vm.count=0;//总页数
						    	$("#laypage").hide();
						    	vm.tableInf(vm.collection);
							}
							 
						});
					  }); 
			  
		},
		tableInf:function(data){ 
			$(".layui-layer").hide();
			 layui.use(['table','layer','form'], function(){  
				  var table = layui.table
				  ,laypage = layui.laypage
				  ,form = layui.form;
				  layer.closeAll();
				  table.render({
				    elem: '#test'
				    ,data:data
				    ,/*cols: [[
				      {field:'ydbhid', align:"center",width:150,title: '运单编号'}
				      ,{field:'fazhan',align:"center", width:95, title: '始发站'}
				      ,{field:'shhd',  align:"center",width:95,title: '发站网点'}
				      ,{field:'daozhan', align:"center",width:95,title: '到站'}
				      ,{field:'dzshhd', align:"center",width:95,title: '到站网点'}
				      ,{field:'fhshj',  align:"center",width:170,title: '发货时间'}
				      ,{field:'ddfhczshijian',align:"center",width:170,title: '等待放货时间'}
				      ,{field:'tzfhczshijian',align:"center",width:170, title: '通知放货时间'}
				      ,{field:'ddfhzt',align:"center",width:170, title: '等待放货设置',templet: '#operate', style:"text-align:center"}
				      ,{field:'tzfhzt',align:"center",width:170,title: '通知放货设置',templet: '#s_operate',style:"text-align:center"}
				    ]],*/
				    cols: [[
					      {field:'ydbhid', align:"center",title: '运单编号'}
					      ,{field:'fazhan',align:"center", title: '始发站'}
					      ,{field:'shhd',  align:"center",title: '发站网点'}
					      ,{field:'daozhan', align:"center",title: '到站'}
					      ,{field:'dzshhd', align:"center",title: '到站网点'}
					      ,{field:'fhshj',  align:"center",title: '发货时间'}
					      ,{field:'ddfhczshijian',align:"center",title: '等待放货时间'}
					      ,{field:'tzfhczshijian',align:"center", title: '通知放货时间'}
					      ,{field:'ddfhzt',align:"center", title: '等待放货设置',templet: '#operate', style:"text-align:center"}
					      ,{field:'tzfhzt',align:"center",title: '通知放货设置',templet: '#s_operate',style:"text-align:center"}
					    ]]
				 //   ,limit: 10
					,cellMinWidth: 100
				//	,limits: [10]
				    ,done: function(res, curr, count){
						 laypage.render({  
		                        elem:'laypage'  
		                        ,count:vm.count  
		                        ,curr:vm.curr 
		                        ,limit:10  
		                        ,layout: ['prev', 'page', 'next', 'skip','count','limit']  
		                        ,jump:function (obj,first) {  
		                            if(!first){  
		                            	var baseValue=sessionStorage.getItem("data");
		                                curnum = obj.curr; 
		                                sessionStorage.setItem("num",curnum);
		                                vm.getList(curnum);  
		                            }  
		                        }  
		                    })  						
						
						  //等待放货通知
						  $(".waitBtn").each(function(){
								var code=$(this).parent().parent().attr("data-content");
								var _this=$(this);
								if(code==1){//等待放货装载 ，可操作撤销
									_this.addClass("layui-btn-disabled");
									//通知放货
									_this.parents("tr").find(".noticeBtn").removeClass("layui-btn-disabled");
									_this.parents("tr").find(".s_backBtn").addClass("layui-btn-disabled");
								}else{//撤销状态，可操作等待放货
									_this.removeClass("layui-btn-disabled");
									_this.parents("tr").find(".backBtn,.noticeBtn,.s_backBtn").addClass("layui-btn-disabled");
								}
						  });
						  
						  $(".noticeBtn").each(function(){
								var code=$(this).parent().parent().attr("data-content");
								var _this=$(this);
								if(code==2){
									_this.addClass("layui-btn-disabled");
									_this.parents("tr").find(".backBtn").addClass("layui-btn-disabled");
									//撤销
									_this.parents("tr").find(".s_backBtn").removeClass("layui-btn-disabled");
								}
						  })
					}
				   // ,page: true
				  });
				  var s_html= $(".layui-table-body").find("table").find("tbody").html();
				  if(s_html==""){
				      $(".layui-table-body").addClass("center").html("暂无数据");
				  }
				});
		},
		//按钮操作
		revocation:function(obj,type){
			//  $(".backBtn").each(function(){
				 if(!$(obj).hasClass("layui-btn-disabled")) {
					 
					// $(obj).unbind("click").bind("click",function(){
			            var waybillNum=$(obj).parents("tr").find("td:eq(0)").text();
			    			var baseValue={
			    					waybillNum:waybillNum,
			    					waitType:type
			    			};
			    			  layui.use(['layer','form'], function(){  
			    		    	var layer = layui.layer;
			    		    	layer.closeAll();
			    		    	$(".layui-layer").hide();
			    					EasyAjax.ajax_Post_Json({
			    						  dataType: 'json',
			    						  url:base_url + '/transport/delivery/modify/setWaitingForDelivery',
			    						  data:JSON.stringify(baseValue),
			    						  contentType:'application/json',
			    						  errorReason: true,
			    						  beforeHandler: function(){
			    			                	/*loading = layer.load(0,{
			    			                		shade: [0.5,'#fff']
			    			                	})*/
			    			                },
			    			                afterHandler: function(){
			    			                	/*layer.close(loading);*/
			    			                }
			    						},function(res){
			    							var num=sessionStorage.getItem("num");
			    							vm.getList(num);
			    							//console.log(res);

			    						});
			    					  }); 
			          
				 }else{
					$(obj).unbind("click");
				 }
		
		},//新增数据
		insertDate:function(obj){
			layui.use(['layer','form'], function(){
		    	var layer = layui.layer;
		    	layer.open({
		    		  type: 1, 
		    		  closeBtn: 1, //显示关闭按钮
		    		  title: "新增等托放货运单",
		    		  area: ['900px','550px'],
		    		  shadeClose: false,
		    		  content: $('.newBox') //这里content是一个普通的String
		    		});
		    	
			});
			 
		}
		,//查询等托放货指令
		searchOrder:function(){
			var baseValue={
					companyName:$("#companyName").val(),
					waybillNum:$("#orderId").val()
			};
			
			 layui.use(['layer','form'], function(){
		    	var layer = layui.layer;
					EasyAjax.ajax_Post_Json({
						  dataType:'json',
						  url:base_url + '/transport/delivery/query/findWaitingForDelivery',
						  data:JSON.stringify(baseValue),
						  contentType:'application/json',
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
								vm.orderList=res.data;
								vm.fazhan=res.data.fazhan;
								if(res.data.ddfhzt==1){
									vm.flag=false;
								}else{
									vm.flag=true;
								}
							}
							//console.log(res);
						});
					  }); 
		},
		//保存等待放货
		saveHandler:function(){
			var baseValue={
					ydbhid:$("#orderId").val(),
					ddfhbeizhu:$("#beizhu").val(),
					fazhan:vm.fazhan
			};
			 layui.use(['layer','form'], function(){
		    	var layer = layui.layer;
					EasyAjax.ajax_Post_Json({
						  dataType:'json',
						  url:base_url + '/transport/delivery/saveWaitingForDelivery',
						  data:JSON.stringify(baseValue),
						  contentType:'application/json',
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
								vm.flag=false;
								layer.msg(res.reason, {icon: 1}, function(){
									vm.searchOrder();
									var num=sessionStorage.getItem("num");
	    							vm.getList(num);
								});
								
							}
						});
					  }); 
		}

	},
	mounted: function(){
		this.searchList()
	}
});


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