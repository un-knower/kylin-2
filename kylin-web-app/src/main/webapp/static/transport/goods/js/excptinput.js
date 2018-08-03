/**
 * create by wyp on 2017/12/11
 *
 *异常录入
 */
var info = {
		init: function() {
			layui.use(['form', 'laydate'], function(){
					form = layui.form;
					layer = layui.layer;
					laydate = layui.laydate;
					form.verify({
						address: function(value, item){
							if(value.split("-").length<3){
								return  "请选择正确的地址！"
							}
						  }
					});
					form.on('submit(searchBtn)', function(data){
						$("#tache").val("");	
						$(".layui-form-select").find("dl").find("dd").removeClass("layui-this");
						info.searchTaskList();  
		    		 	return false;
		    		});
					form.on('submit(btn_sub)', function(data){
						var inpCount=""
						var orderRows=$("#showcarInformationList tr").length;
						if(orderRows>0){
							$(".inpNum").each(function(index){
								 inpCount+=parseInt($(".inpNum").eq(index).val());
							});
							if(inpCount==0){
								layer.msg("请录入异常数据！");
							}else{								
								info.saveHandler(); //保存运单号
							    return false;
							}
						  
						}else{
							layer.msg("请至少添加一条运单号");
						}
						return false;
		    		})
					
			})
			
		},
		searchTaskList: function(){
		    var transportCode=$("#waybillId").val();
			var baseValue = {
					transportCode: transportCode
				    }
			 EasyAjax.ajax_Post_Json({
		     		dataType: 'json',
		             url: base_url + '/transport/goods/searchException',
		             data: JSON.stringify(baseValue),
		             errorReason: true,
		             beforeHandler: function(){
		            	 loading = layer.load(0,{
			            		shade: [0.5,'#fff']
			            	});
		             },
		             afterHandler: function(){
		            	 layer.close(loading);
		             }
		         },
		         function (data) {
		        	var carId="";
		         	var dataRes = data.message;
		         	$(".formBox input").val("");
		         	$("#ydbhid").val(transportCode);
		         	$(dataRes).each(function(i,item){
		         		$("#handover").val(item.handOver);
		         		$("#fromStation").val(item.faZhan);
		         		$("#toStation").val(item.daoZhan);
		         		$("#customer").val(item.customerName);
		         		$("#customerId").val(item.customerOrderCode);
		         		var driverName=item.driverName;
		         		var driverPhone=item.driverTelephone;
		         		if(driverName==null ||driverName==""){
		         			driverName="";
		         		}
		         		if(driverPhone==null ||driverPhone==""){
		         			driverPhone="";
		         		}
		         		if(dataRes.length==1){
		         			 $("#carId").val(item.carNo);
	                    	 $("#driverName").val(driverName);
	                    	 $("#driverPhone").val(driverPhone);
	                    	 $("#carId").next().hide();
		         		}else{
		         			 $("#carId").val(dataRes[0].carNo);
	                    	 $("#driverName").val(dataRes[0].driverName);
	                    	 $("#driverPhone").val(dataRes[0].driverTelephone);
		         			 $("#carId").next().show();
		         		     carId+="<li dataName='"+driverName+"' dataId='"+driverPhone+"'>"+item.carNo+"</li>";
		         		}
		         	});
                      $(".selectUl").empty().append(carId);
                      $("#carId,.layui-edge").click(function(e){
              			stopPropagation(e);
              			$(".selectUl").show();
              		});	
                      $(".selectUl  li").mouseover(function(){
         				  $("#carId").val($(this).text());
                    	  $("#driverName").val($(this).attr("dataName"));
                    	  $("#driverPhone").val($(this).attr("dataId"));
         			});
                    selectInput( $(".selectUl"));//下拉框
                    //表格渲染数据
		         	var tableBody = $('#tableList').render(dataRes);
		         	$('#showcarInformationList').html('').html(tableBody);	         	
		         	info.appendRows();//添加行数		         	
		         	info.toUpdateInp(); 
		         });

		},
		appendRows:function(){//添加行数
			var rows=$("#showcarInformationList tr").length;
         	for(var i=0;i<rows;i++){
				$("#showcarInformationList tr:eq("+i+") td:eq(0)").text(i+1);
			}
		},
		toUpdateInp:function(){ //表格input验证
			var trBox=$("#showcarInformationList tr");
			$(".inpNum").each(function(index){
				$(this).focus(function(){
					if($(this).val()=="0"){
						$(this).val("");
					}
				   });
				  $(this).blur(function(){
					  if($(this).val()==""){
						  $(this).val("0");
						  info.toTotal(this);
					  }
				  });
				
				$(this).on("change",function(){		
					  var thisCount=0;
					  var thisNum=$(this).val();
					  var total=parseFloat($(this).parents("tr").find("td:eq(5)").text());					  
					  var re = /^\d+$/;
					  if (!re.test(thisNum)){ 
						  if($(this).val()==""){
							  $(this).val("0");
							  info.toTotal(this);
						  }else{
						    layer.msg("请输入正确数字");
						     $(this).val("0");
						  }
					  }else{
						  if(thisNum>total){
							  layer.msg("不可大于总件数");
							  $(this).val("0");
						  }if(parseInt(thisNum)==0){
							  $(this).val("0");
						  } 
						  $(this).val(parseInt($(this).val())).blur;
						  info.toTotal(this);
					   }
					  
				 });					
				
			});		
			
		},toTotal:function(obj){ //异常总数统计
			 var thisCount=0;
			 var total=parseFloat($(obj).parents("tr").find("td:eq(5)").text());	  
			 var ds0= parseFloat($(obj).parents("tr").find("td:eq(6)").find("input").val()); 
			 var ps= parseFloat($(obj).parents("tr").find("td:eq(7)").find("input").val());
			 var ss= parseFloat($(obj).parents("tr").find("td:eq(8)").find("input").val());
			 var wr= parseFloat($(obj).parents("tr").find("td:eq(9)").find("input").val());
			 var ds1= parseFloat($(obj).parents("tr").find("td:eq(10)").find("input").val());
			 var bz= parseFloat($(obj).parents("tr").find("td:eq(11)").find("input").val());
			 var ys= parseFloat($(obj).parents("tr").find("td:eq(12)").find("input").val());
			 var ch= parseFloat($(obj).parents("tr").find("td:eq(13)").find("input").val());
			 var qt= parseFloat($(obj).parents("tr").find("td:eq(14)").find("input").val());
			 var myCount=new Array(ds0,ps,ss,wr,ds1,bz,ys,ch,qt)  
			      for (var i=myCount.length-1; i>=0; i--) {
			    	  thisCount += myCount[i];  
			      }
                  if(thisCount>total){
               	     layer.msg("异常总数大于货物总件数！请核实后再输")
               	     $(obj).val("0").blur;
                  }else{
               	     $(obj).parents("tr").find(".abnormalNum").text(thisCount);  
                  }	
			
		},
		saveHandler: function(){   //数据保存	
			var trRows=$("#showcarInformationList tr").length;
			 var b = [];
			  for (var i = 0; i < trRows; i++) {
				   var q={};
				   
				   var objCount="";
					  var objNum=$("#showcarInformationList tr:eq("+i+")").find(".inpNum");
					  objNum.each(function(u){
						  objCount+=parseInt(objNum.eq(u).val());
						});
					 if(objCount>0){
						q.pinMing=$("#showcarInformationList tr:eq("+i+")").find("td:eq(1)").text();
						q.packing=$("#showcarInformationList tr:eq("+i+")").find("td:eq(2)").text();
						q.jianShu=$("#showcarInformationList tr:eq("+i+")").find("td:eq(5)").text()
				        q.ds = $("#showcarInformationList tr:eq("+i+")").find(".lack").val();
				        q.pl = $("#showcarInformationList tr:eq("+i+")").find(".damage").val();
				        q.ssh = $("#showcarInformationList tr:eq("+i+")").find(".wet").val();
				        q.wr = $("#showcarInformationList tr:eq("+i+")").find(".pollute").val();
				        q.frozenhurt = $("#showcarInformationList tr:eq("+i+")").find(".freeze").val();
				        q.flmb = $("#showcarInformationList tr:eq("+i+")").find(".deterioration").val();
				        q.delayed = $("#showcarInformationList tr:eq("+i+")").find(".delayed").val();
				        q.chuanhuo = $("#showcarInformationList tr:eq("+i+")").find(".errorGoods").val();
				        q.other = $("#showcarInformationList tr:eq("+i+")").find(".other").val();   
				        q.excepquantity = $("#showcarInformationList tr:eq("+i+")").find(".abnormalNum").text();
				        q.salvage = $("#showcarInformationList tr:eq("+i+")").find(".incomplete").val();
				        q.bz = $("#showcarInformationList tr:eq("+i+")").find(".description").val();
				        b.push(q);
					 }  
			    }	
			 var baseValue = {
					     ydbhid:$("#ydbhid").val(),
					     customerName:$("#customer").val(),
					     jiaoJie:$("#handover").val(),
					     jieShou :$("#sendee").val(),
					     faZhan:$("#fromStation").val(),
					     daoZhan:$("#toStation").val(),
					     customerNo:$("#customerId").val(),
					     chxh:$("#carId").val(),
					     driveName:$("#driverName").val(),
					     driveTele:$("#driverPhone").val(),
					     hjbh: $("#tache").val(),
					     scene: $("#address").val(),
					     happenDate: $("#startDate").val(),
					     resultInfo: JSON.stringify(b)
					    }
			 console.log(baseValue);
			   EasyAjax.ajax_Post_Json({
				   dataType: 'json',
			       url:base_url + '/transport/goods/saveException',
			       data:JSON.stringify(baseValue),
			       errorReason: true,
			       beforeHandler: function(){
			      	 loading = layer.load(0,{
			          		shade: [0.5,'#fff']
			          	});
			       },
			       afterHandler: function(){
			      	 layer.close(loading);
			       }
			   },function(res){
				   layer.msg('保存成功',  {
						icon: 1,
					    time: 2000 //2s后自动关闭
					  }, function(){
						location.reload();
					 });
				 
			   });
			
		
		}

	}

info.init();

function stopPropagation(e) {//把事件对象传入
	if (e.stopPropagation){ //支持W3C标准 doc
		e.stopPropagation();
	}else{ //IE8及以下浏览器
		e.cancelBubble = true;
	}
}
//下拉框 
function selectInput(obj){
	obj.find("li").click(function(){
		var _target = $(this).parent("ul").siblings(".selectInput");
		_target.val($(this).text());
		$(this).parent("ul").hide();		
	});

	$(document).click(function(){
		obj.hide();
	});
}


//列表里的删除
function del_table(obj){
	 var htmDiv=$(obj).parents("tr");
 var tabRows=$("#showcarInformationList").find("tr").length;
		   if(tabRows>1){   
				 layer.confirm('',{
					btn: ['确定', '取消'],
					title:'提示信息',
					content:'确认删除吗？',		
					shadeClose: true //开启遮罩关闭
				 },function(){
					htmDiv.remove();
				    info.appendRows();
					layer.closeAll();
				 });
		   }else{
			   layer.msg("至少保留一条数据");
		   }			   
	  };
 
