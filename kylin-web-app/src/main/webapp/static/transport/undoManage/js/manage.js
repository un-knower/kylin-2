/**
 * creatd by wyp on 2017/11/29
 *
 *撤销装载
 */

var info = {
		init: function() {
			layui.use(['form', 'laydate'], function(){
					form = layui.form;
					layer = layui.layer;
					laydate = layui.laydate;
					form.on('submit(searchBtn)', function(data){
						var obj=$.trim($("#waybillId").val());
						info.searchTaskList(obj);  
		    		 	return false;
		    		});
					form.on('submit(btn_sub)', function(data){
						form.render('select'); 
						var inpCount=""
						var orderRows=$("#showcarInformationList tr").length;
						if(orderRows>0){
							var len=$("input[type='checkbox']:checked").length;
							if(len>0){
								info.saveHandler();
							}else{
								layer.msg("请至少选择一条运单号");
							}
						  
						}else{
							layer.msg("请至少添加一条运单");
						}
						return false;
		    		})
					
			})
			
		},
		searchTaskList: function(obj){ //查询数据			   
			    var ydbhid=obj;
				var baseValue = {
						 ydbhid: ydbhid
					    }
			   EasyAjax.ajax_Post_Json({
				   dataType: 'json',
			       url:base_url + '/transport/undo/query/'+ydbhid,
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
					//渲染数据
				   $("#ydbhid").val(ydbhid);
				    var tableDetail = $('#tableList').render(res.data);
				    $('#showcarInformationList').html("").html(tableDetail);
				    layui.use('form', function(){
		                 var form = layui.form;
		                 form.render('checkbox');
		             });
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
				    			    
				    info.toupdateDate();			 
			   });
		},
		toupdateDate:function(){//更新时间
			$(".timeBox").each(function(index){
				$(".timeBox").eq(index).text(getMyDate(parseInt($(".timeBox").eq(index).text())));
			});
		},
		
		saveHandler: function(){   //数据保存	
			 var len=$("input[name='checks']:checked").length;
			 var baseValue = [];
			 if (len>0){				
				  for (var u = 0; u < len; u++) {
					  var q={};
					  q.xuhao=$("input[name='checks']:checked").eq(u).parents("tr").find("td:eq(1)").attr("dataId");
					  baseValue.push(q);
				  }
			 }
			// console.log(baseValue);
			   EasyAjax.ajax_Post_Json({
				   dataType: 'json',
			       url:base_url + '/transport/undo/delete',
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
				   layer.msg('撤销成功！',  {
						icon: 1,
					    time: 2000 //2s后自动关闭
					  }, function(){
						  $('#showcarInformationList').html("");
						  var obj= $("#ydbhid").val()
						  info.searchTaskList(obj); 
					 });
				 
			   });
			
		
		}

	}

info.init();

//时间戳 毫秒转换日期
function getMyDate(str){  
    var subDate = new Date(str), 
    subYear = subDate.getFullYear(),  
    subMonth = subDate.getMonth()+1,  
    subDay = subDate.getDate(),  
    subHour = subDate.getHours(),  
    subMin = subDate.getMinutes(),  
    subSen = subDate.getSeconds(),  
    subTime = subYear +'-'+ getzf(subMonth) +'-'+ getzf(subDay) +' '+ getzf(subHour) +':'+ getzf(subMin) +':'+getzf(subSen);//最后拼接时间  
    return subTime;  
};  
//补0操作  
function getzf(num){  
    if(parseInt(num) < 10){  
        num = '0'+num;  
    }  
    return num;  
}  


