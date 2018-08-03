/**
 * creatd by lhz on 2017/10/30
 *
 *到货
 */
var slectType = '整车' /*设置查询类型*/
	,getMessage = {}
	,getStoreHouse = {}
	,finishTime
	,ydbhid = []
	,flag = false;
var message = [];
var arrive = {
	init: function() {
		layui.use(['form', 'laydate'], function(){
				form = layui.form;
				layer = layui.layer;
				laydate = layui.laydate;
			form.on('radio(Vehicle-status)', function(data){
					$('.Vehicle-condition').removeAttr('disabled').attr({'placeholder': '请输入'});
					$('#waybillId').val('').attr({'placeholder': '未选择单票时不能输入','disabled': ''});
					slectType = '整车';
			})
			form.on('radio(single-status)', function(data){
					$('#waybillId').removeAttr('disabled').attr({'placeholder': '请输入'});
					$('.Vehicle-condition').val('').attr({'placeholder': '未选择整车时不能输入','disabled': ''});
					slectType = '单票';
			})
			form.on('checkbox(select-all)',function(data){
				if(data.elem.checked){
					$('.layui-form-checkbox').addClass("layui-form-checked");
				}else{
					$('.layui-form-checkbox').removeClass("layui-form-checked");
				}
			})
		})
		arrive.toArriveHandler();
		arrive.saveHandler();
		arrive.Toclear();
	},
	toArriveHandler: function(){
		$('.toArrive').on('click', function(){
			var submitData;
			$('.save-success').hide();
			if(slectType == '单票'){
				var waybillId = $.trim($('#waybillId').val());
				if(waybillId == '') {
					layer.open({
						content: '运单编号不能为空！'
					})
					return;
				}
				submitData = {ydbhid: waybillId, type: slectType};
			}else{
				var carDate = $.trim($('#carDate').val());
				var carBrand = $.trim($('#carId').val());
				if(carDate == '' || carBrand == '') {
					layer.open({
						content: '查询条件不能为空！'
					})
					return;
				}
				submitData = {chxh: carBrand, fchrq: carDate, type: slectType};
			} 
			$('#showcarInformationList').html("");
			$("#orderId").val(waybillId);
		    $("#searchType").val(slectType);
		    $("#startDate").val(carDate);
		    $("#s_carId").val(carBrand);
			submitData = JSON.stringify(submitData);
			arrive.ArriveAjaxActive (submitData);
			

		})
	},
	ArriveAjaxActive: function(submitData){
		 EasyAjax.ajax_Post_Json({
     		 dataType: 'json',
             url: base_url + '/transport/goodArrive/arrive/search',
             errorReason: true,
             data: submitData
         },
         function (data) {
        	 message =data.message;
        	getMessage = {};
        	$.each(data.message, function(index, ele){
        		ydbhid = [];
    			$.each(ele.conveys, function(a,items){
            		if(items.isArrived == '未到站'){
        				ydbhid.push(items.ydbhid);
            		}
    			})
    			if(ele.chxh == null){
    				getMessage['noCarNum'] = ydbhid;
    			}else{
    				getMessage[ele.chxh] = ydbhid;
    			}
        	})
        	$('#select-storeHouse').html('<option value="">直接选择或搜索选择</option>');
        	$('#select-storeHouse').append($('#optionsList').render(data.storeHouse));
        	$.each(data.storeHouse, function(index, item){
        		getStoreHouse[item.ckmc] = item.ckbh;
        	});
        	$.each(data.message, function(index, item){/*循环每一条message*/
        		item.conveyLen = 0;
        		$.each(item.conveys, function(index_, elem_){/*循环每一条conveys*/
            		item.conveyLen += elem_.detail.length;
            	})
        	})
        	layui.use('form',function(){
        		var form = layui.form;
        		var tableDetail = $('#tableList').render(data.message);
             	$('#showcarInformationList').html(tableDetail);
        		form.render();
        		
    			$(".tdCheck div").click(function(){ //全选
    				var items =$("input[name='checks']");
    				var checkAll=$("input[name='checkAll']:checked");
    				for(var i = 0, j = items.length; i < j; i++) {
    					if(checkAll.length>0){
    						$("#showcarInformationList .layui-unselect").addClass("layui-form-checked");
    						items[i].checked=true;
    					}else{
    						$("#showcarInformationList .layui-unselect").removeClass("layui-form-checked");
    						items[i].checked=false;
    					}
    				}
    				
    			});
        	})
         	$('.check-arrive').removeAttr('disabled').attr({'placeholder': '请输入'});
         	finishTime = setInterval(function(){
         		var currTime = getCurrTime ();
         		$('#arriveDate').attr('placeholder', currTime);
         	}, 1000)
         	flag = true;
         	form.render(null,'arriv-time');
         });
	},
	saveHandler: function(){
		$('.toSave').on('click', function(){
			if(flag){
				var storeName = $('.layui-select-title input').val();
				var arriveTime = $('#arriveDate').val();
				var carDate = $.trim($('#carDate').val());
				var d = dateInterval(carDate,arriveTime);
				var params = [];
				if($('.layui-form-checked').length  == 0){
					layer.open({
						content: '请勾选要收货的车牌号'
					});
					return false;
				}else{
					var bool = true;
					$('#showcarInformationList').find('.layui-form-checked').each(function(index,elem){
						var carNumber = $(elem).parent().parent().next().find('span').html();
						if(carNumber == ''){
							var waybillIds = getMessage['noCarNum'];
						}else{
							var waybillIds = getMessage[carNumber];
						}
						if(waybillIds.length == 0){
							layer.open({
								content: '存在已到站运单，请重新勾选'
							});
							bool = false;
						}else{
							params.push({
								chxh : carNumber,
								ydbhids : waybillIds
							})
						}
					})
					if(!bool){
						return false;
					}
				}
				if(arriveTime == ''){
					var currTime = getCurrTime ();
					arriveTime = currTime;
				}
				if(d<0){
					layer.open({
						content: '到货日期小于发车日期，请重新输入！'
					})
					return false;
				}
				if($('#select-storeHouse option').length > 1){
					if(storeName == ''){
						layer.open({
							content: '请选择到货仓库！'
						})
						return false;
					}
				}
				
				var saveData = {parama: params, dhsj: arriveTime, storeHouse: storeName};
				saveData = JSON.stringify(saveData);
				arrive.confirmAjaxActive (saveData);
			}else{
				layer.open({
					content: '请先输入正确的查询条件！'
				})
				return;
			}
		})
	},
	confirmAjaxActive: function(saveData){
		 EasyAjax.ajax_Post_Json({
    		 dataType: 'json',
            url: base_url + '/transport/goodArrive/arrive/save',
            data: saveData,
            errorReason: true
        },
        function (data) {
        	$('#showcarInformationList').html('');
        	$('.save-success').show();
        });
	},
	Toclear: function(){
		$('.toDelete').on('click', function(){
			arrive.clearHandler();
		})
	},
	clearHandler: function(){
		$('#select-storeHouse').html('<option value="">直接选择或搜索选择</option>');
		$('.clear-input').val('');
		$('#showcarInformationList').html('');
		flag = false;
		$('.save-success').hide();
	}
}
arrive.init();

/*********计算日期天数********/
function dateInterval(beginTime,endTime) {
	//换算成毫秒
    var minutes = 1000 * 60
    var hours = minutes * 60
    var days = hours * 24
    
    var date1 = beginTime.replace('-',',');
    var date2 = endTime.replace('-',',');
    
    var day1 = Date.parse(date1);
    var day2 = Date.parse(date2);
    d = day2 - day1;
    d = d / days;
    
    return d;
  }

/*update by wyp*/

var dhsj="",
    storeHouse="";
//撤销签收
function revoke(obj,dhshijian){
	  dhsj=$.trim($('#arriveDate').val());
	  var xuhaos = [];
	  //console.log(xuhaos);
	  $.each(message, function(index, ele){
			$.each(ele.conveys, function(a,items){
				$.each(items.detail, function(b,detail){
					if(items.dhsj == dhshijian){
		      			xuhaos.push(detail.xuhao);
		      		}
				});
			})
  	});
	  
	  storeHouse=$.trim($("#select-storeHouse").val());
	  if(dhsj.length==0){
		   dhsj=$("#arriveDate").attr("placeholder");
	   }
 	 var htmDiv=$(obj).parents("tr");
	 layer.confirm('',{
		btn: ['确定', '取消'],
		title:'提示信息',
		content:'确认撤销到货吗？',		
		shadeClose: true //开启遮罩关闭
	 },function(){
		 var chxh=$(obj).parents("tr").find("td.carBrand").find("span").text();
		 var ydbhids=$(obj).parents("tr").find(".ydbihd").text();
		 var b = [];
		 var q={};
 			 q.chxh=chxh;
   		     q.ydbhids=ydbhids;
		     b.push(q);
		 var baseValue = {
				 dhsj:dhsj,
				 xuhaos :JSON.stringify(xuhaos),
				 storeHouse:storeHouse,
				 parama: JSON.stringify(b)
				    }
		 //console.log(baseValue);
		 layer.closeAll();
		   EasyAjax.ajax_Post_Json({
			   dataType: 'json',
		       url:base_url + '/transport/goodArrive/delete',
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
			   layer.msg('撤销成功',  {
					icon: 1,
				    time: 2000 //2s后自动关闭
				  }, function(){
					  searchList();//保存成功后查询
				 });
			 
		   });
	 });	
}
//确认到货
function arrival(obj){
      dhsj=$.trim($('#arriveDate').val());
	  storeHouse=$.trim($("#select-storeHouse").val());
	 var len= $("#select-storeHouse").find("option").length; 
	 if(storeHouse.length>0 || len<=1){
		 if(dhsj.length==0){
			 dhsj=$("#arriveDate").attr("placeholder");
		   }
		 layer.confirm('',{
			btn: ['确定', '取消'],
			title:'提示信息',
			content:'确认到货吗？',		
			shadeClose: true //开启遮罩关闭
		 },function(){		  
				 var chxh=$(obj).parents("tr").find("td.carBrand").find("span").text();
				 var ydbhids=$(obj).parents("tr").find(".ydbihd").text();
				 if(chxh == '' || chxh == null){
					 chxh = $.trim($('.carBrand').find("span").text());
				 }
				 var b = [];
				 var q={};
		 			 q.chxh=chxh;
		   		     q.ydbhids=ydbhids;
				     b.push(q);
				 var baseValue = {
						 dhsj:dhsj,
						 storeHouse:storeHouse,
						 parama: JSON.stringify(b)
				}
				 //console.log(baseValue);
				 layer.closeAll();
				   EasyAjax.ajax_Post_Json({
					   dataType: 'json',
				       url:base_url + '/transport/goodArrive/arrive/save',
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
							  searchList();//保存成功后查询
						 });
					 
				   });
			 
		 });		 
	 }else{
		 layer.msg("请先选择到货仓库");
     }	
}


//保存成功查询运单
 function searchList(){
	  var submitData;
	  var orderId=$("#orderId").val(),
	      searchType=$("#searchType").val(),
	      startDate=$("#startDate").val(),
	      xuhaoV=$("#xuhao").val(),
	      s_carId=$("#s_carId").val();
	  
	 $('#showcarInformationList').html("");
	 if(searchType == '单票'){
		 submitData = {ydbhid: orderId, type: searchType,xuhao:xuhaoV};
	 }else{
		submitData = {chxh: s_carId, fchrq: startDate, type: searchType,xuhao:xuhaoV};
	  }
		submitData = JSON.stringify(submitData);
		arrive.ArriveAjaxActive (submitData);//查询
 }
 // 批量到货
 function batchGoodArrive(){
	 var xuhaos = [];
	var regN = new RegExp("\n","g");//g,表示全部替换。
	var regT = new RegExp("\t","g");//g,表示全部替换。
	var regEmpty = new RegExp(" ","g");//g,表示全部替换。
 	var len = $("input[name='checks']").length;
 	for(var i=0;i<len;i++){
 		 var ischecked = $("input[name='checks']")[i].checked;
 		 if(ischecked){
 			var xuhao = $.trim($("input[name='checks']:checked").eq(i).parents("tr").find(".xuhao").text());
 			xuhao.replace(regN,"");
 			xuhao.replace(regT,"");
 			xuhao.replace(regEmpty,"");
 			xuhaos.push(xuhao);
 		 }
 	}
	var parameter=pitch(1);
	if (null == parameter){
		return;
	}
	parameter.xuhaos = xuhaos.join(",");
	var comfirm = layer.open({
		content:'你确定要批量确认到货？',
		btn:['确定','取消'],
		yes: function(){
			_batchOperationAjax(comfirm,parameter,"/transport/goodArrive/batchGoodArrive"); 
			layer.close(comfirm);
		}
	})
	}
 
 // 批量撤销到货
function batchRepealGoodArrive(){
	var parameter=pitch(0);
	if (null == parameter){
		return;
	}
	var comfirm = layer.open({
		content:'你确定要批量撤销到货？',
		btn:['确定','取消'],
		yes: function(){
			_batchOperationAjax(comfirm,parameter,"/transport/goodArrive/batchRepealGoodArrive"); 
			layer.close(comfirm);
		}
	})
} 
 
function _batchOperationAjax(comfirm,parameter,url){
	EasyAjax.ajax_Post_Json({
		   dataType: 'json',
	       url:base_url + url,
	       data:JSON.stringify(parameter),
	       errorReason: true,
	       beforeHandler: function(){
               loading = layer.load(0,{
               	shade: [0.5,'#fff']
               	})
            },
           afterHandler: function(){
           	layer.close(loading);
            $("input[name='checkAll']").attr("checked",false);;
            searchList();//保存成功后查询
           }
		}, function (data) {
	    	  if(data.resultCode == 200){
	    		   layer.msg(data.reason,{
						icon: 1,
					    time: 2000 //2s后自动关闭
					  }, function(){
						  $("input[name='checkAll']").attr("checked",false);;
						  $("#checkAll").siblings("div").removeClass("layui-form-checked");
						  //searchList();//保存成功后查询
					 });
	    	   }
		   })
	   
	   
}

function pitch(status){
	 var len=$("input[name='checks']:checked").length;
	 var parm = {};
	 var storeHouse=$.trim($('#select-storeHouse').val());
	 if(1==status){ //到货
		 if(""==storeHouse){
			 layer.msg("请选择到站仓库");
			 return null;
		 } 
	 } 
	 if (len>0){	 
		  var arriveTime = $('#arriveDate').val();
		  var carDate = $.trim($('#carDate').val());
		  var d = dateInterval(carDate,arriveTime);
		  if(arriveTime == ''){
				var currTime = getCurrTime ();
				arriveTime = currTime;
			}
			if(d<0){
				layer.open({
					content: '到货日期小于发车日期，请重新输入！'
				})
				return null;
			}
		  parm.dhsj=arriveTime;
		  parm.storeHouse=$.trim($('#select-storeHouse').val());		 		  		  		  		  
		  parm.chxh=$.trim($("input[name='checks']").eq(0).parents("tr").find(".carBrand").text());
		  var ydbh;
		  for (var u = 0; u < len; u++) {
			  parm.ydbhids=$("input[name='checks']:checked").eq(u).parents("tr").find(".ydbihd").text();
			  if(parm.ydbhids){
				  if(ydbh && ydbh!=''){
					  ydbh=parm.ydbhids+","+ydbh; 
				  }else{
					  ydbh=parm.ydbhids; 
				  }		
			  }
		  }
		  parm.ydbhids=ydbh;
	 }else {
		 layer.msg("请选择数据!");
		 return;
		 
	 }
	 return parm;
}
 