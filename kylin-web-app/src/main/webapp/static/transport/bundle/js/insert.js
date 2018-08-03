/*
 by wuyanping 20171209
 */
String.prototype.replaceAll = function(s1,s2) { 
   return this.replace(new RegExp(s1,"gm"),s2); 
};
var fhsj = new Array();
var fhsjYd = new Array();
var daozhan = "";
var zhzhuanArr = allDaozhan.split(",");
var zhzhuanWdArr = new Array();
var selectType= new Array();
var fromStation=new Array();//起运网点
var allDaozhanArr = new Array();//所有到站
//去掉input首尾空格
$("input[type=text]").bind("blur",function(){
		$(this).val($(this).val().replace(/^ +| +$/g,''));
	});
// 日期选择 
$("input[name='startDate']").flatpickr({
	enableTime : true,
	dateFormat : "Y-m-d H:i:S",
	enableSeconds:true,
	//minDate : 'today',
	time_24hr : true,
	onOpen : function(selectedDates, dateStr,instance) {
		var yjddshj = $("input[name='endDate']").val();
		if (yjddshj) {
			instance.set("maxDate", yjddshj);
		} 
	},
	onChange:function(){
		if($('#startDate').val()!=""){
	    	clearInterval(startD);
		}
	}
});
$("input[name='endDate']").flatpickr({
	enableTime : true,
	dateFormat : "Y-m-d H:i:S",
	enableSeconds:true,
	//minDate : 'today',
	time_24hr : true,
	onOpen : function(selectedDates, dateStr,instance) {
		var fchrq = $("input[name='startDate']").val()
		if (fchrq) {
			instance.set("minDate", fchrq);
		}
	},
	onChange:function(){
		if($('#endDate').val()!=""){
		  clearInterval(endD);
		}
	}
});


var startD=self.setInterval(function(){
	    var start=1;
		var currTime = updateDate(start);
		$('#startDate').val(currTime);
	}, 1000);
var endD=self.setInterval(function(){
	    var start=2;
		var currTime = updateDate(start);
		$('#endDate').val(currTime);
	}, 1000);

function updateDate(index){
    var now = new Date(),
    //格式化日，如果小于9，前面补0
     day = ("0" + now.getDate()).slice(-2),
     day1 = ("0" + (now.getDate()+1)).slice(-2),
     month = ("0" + (now.getMonth() + 1)).slice(-2),
     hours = ("0" + (now.getHours())).slice(-2),
     minutes = ("0" + (now.getMinutes())).slice(-2),
     seconds = ("0" + (now.getSeconds())).slice(-2);
     today = now.getFullYear() + "-" + (month) + "-" + (day)+ " " + (hours)+ ":" + (minutes)+ ":" + (seconds);
     tomorrow = now.getFullYear() + "-" + (month) + "-" + (day1)+ " " + (hours)+ ":" + (minutes)+ ":" + (seconds);
     if(index=="1"){
    	 return today;
     }else{
         return tomorrow;  	 
     }
}

//radio控制层 显示隐藏
var insert = {
	init: function() {
		layui.use(['form', 'laydate'], function(){
				form = layui.form;
				layer = layui.layer;
				laydate = layui.laydate;
				form.on('radio(th-status)', function(data){ //提货装载
					$(".sPart:eq(1)").fadeIn();
					$("#selectBox").attr("lay-verify","");
					$("#selectBox").val("行包");
					$(".sPart:eq(0)").hide();
					$(".sPart:eq(0) input").attr("lay-verify","");
					//$("#s_fromAddress,#s_toAddress").attr("lay-verify","required");
					$("#toAddress").attr("lay-verify","arriveStation");//到站校验
					$("#fromAddress").attr("lay-verify","required");//发站校验
					$("input[name='dType']").removeAttr("disabled");
					$(".fromAddress").text("发站：");
					$(".toAddress").text("到站：");
					//$(".fromAddress").text("运输起始地：");	
					//$(".toAddress").text("运输目的地：");
					//运输起始地和目的地input框显示
					$("#fromAddress,#toAddress").show();
					//$("#s_fromAddress,#s_toAddress").hide();
					$("#fromAddress,#toAddress").attr("lay-verify","");
					
					$("#fromStation,#qyStation").attr("lay-verify","fromStation");//起运网点校验
					$(".transStatus:eq(1)").removeAttr("disabled");
					$(".transStatus:eq(1)").next().removeClass("layui-disabled");
					$("#toStation,#station").val("");//#s_toAddress,
					$(".zzwdUl").empty();//清空到达网点
				})
				form.on('radio(gx-status)', function(data){  //干线中转
					$(".sPart:eq(0)").fadeIn();
					$("#station").attr("lay-verify","station");
					$("#getPoint").removeAttr("lay-verify");
					$("input[name='dType']").removeAttr("disabled");
					$(".fromAddress").text("发站：");
					$(".toAddress").text("到站：");
					$("#station,#getPoint").val("");
					$(".zzwdUl").empty();//清空到达网点
					//运输起始地和目的地input框显示
					$("#fromAddress,#toAddress").show();
					//$("#s_fromAddress,#s_toAddress").hide();
					//$("#s_fromAddress,#s_toAddress").attr("lay-verify","");
					
					if($("#direct").next().hasClass("layui-form-radioed")){ //不中转
						$(".sPart:eq(1)").show();
						$("#toAddress").attr("lay-verify","arriveStation");//到站校验
						$("#fromAddress").attr("lay-verify","required");//发站到站校验
						//$(".sPart:eq(1) input").attr("lay-verify","lay-verify");
						$(".subPart:eq(0) input").attr("lay-verify","");
					}else{
						$(".sPart:eq(1)").hide();
						$(".sPart:eq(1) input").attr("lay-verify","");
					}
					$(".transTatus").fadeIn();
					$("#selectBox").attr("lay-verify","selectBox");
					$("#fromStation,#qyStation").attr("lay-verify","fromStation");//起运网点校验
					$("#toStation,#getPoint").attr("lay-verify","getPoint");   //到达网点校验
					
					//干线中转时 默认选择整车
					/*$(".transStatus:eq(1)").next().addClass("layui-disabled");
					$(".transStatus").attr("disabled","true");
					$(".transStatus:eq(0)").prop("checked","true");
					var obj0=$(".transStatus:eq(0)").next().find("i");
					var obj1=$(".transStatus:eq(1)").next().find("i");
					var obj2=$("input[name='zType']").eq(0).next().find("i");
					$(".transStatus:eq(0)").next().addClass("layui-form-radioed");
					$(".part_box:eq(2)").fadeIn();
					$(".part_box:eq(2) input:eq(0)").attr("lay-verify","required");
					if($(".transStatus:eq(1)").next().hasClass("layui-form-radioed")){
						 obj0.text(obj1.text());
						 obj1.text(obj2.text());	
					 }
					 $(".transStatus:eq(1)").next().removeClass("layui-form-radioed");*/
				})
				 form.on('radio(ps-status)', function(data){ //配送运输
					$(".sPart:eq(1)").fadeIn();
					$(".sPart:eq(0)").hide();
					$(".sPart:eq(0) input").attr("lay-verify","");
					$("#toAddress").attr("lay-verify","arriveStation");//到站校验
					$("#fromAddress").attr("lay-verify","required");//发站校验
					$("#toStation").attr("lay-verify","getPoint");   //到达网点校验
					//$(".sPart:eq(1) input").attr("lay-verify","required");
					$("input[name='dType']").removeAttr("disabled");
					$(".fromAddress").text("发站：");
					$(".toAddress").text("到站：");
					$(".transTatus").fadeIn();
					$("#selectBox").attr("lay-verify","selectBox");
					$("#toAddress,#toStation,#station").val("");
					$(".zzwdUl").empty();//清空到达网点
					//配送运输时 默认选择整车
					/*$(".transStatus:eq(1)").next().addClass("layui-disabled");
					$(".transStatus").attr("disabled","true");
					$(".transStatus:eq(0)").prop("checked","true");
					var obj0=$(".transStatus:eq(0)").next().find("i");
					var obj1=$(".transStatus:eq(1)").next().find("i");
					var obj2=$("input[name='zType']").eq(0).next().find("i");
					$(".transStatus:eq(0)").next().addClass("layui-form-radioed");
					$(  ".part_box:eq(2)").fadeIn();	
					$(".part_box:eq(2) input:eq(0)").attr("lay-verify","required");
					if($(".transStatus:eq(1)").next().hasClass("layui-form-radioed")){
						 obj0.text(obj1.text());
						 obj1.text(obj2.text());	
					 }
					 $(".transStatus:eq(1)").next().removeClass("layui-form-radioed");*/
					 $("#fromStation,#qyStation").attr("lay-verify","fromStation");//起运网点校验
					 
				    //发站和到站 input框显示
					$("#fromAddress,#toAddress").show();
					//$("#s_fromAddress,#s_toAddress").hide();
					//$("#s_fromAddress,#s_toAddress").attr("lay-verify","");
					 
				})
				form.on('radio(outline-status)', function(data){ //外线
					$(".part_box:eq(1) input:eq(0)").val("");
					$(".part_box:eq(1) input:eq(1)").val("");
					$(".part_box:eq(1) input:eq(2)").val("");
					$(".part_box:eq(1) em:eq(1)").text("*");
					$(".part_box:eq(1) em:eq(2)").text("*");
					$(".part_box:eq(1) input:eq(1)").attr("lay-verify","required");
					$(".part_box:eq(1) input:eq(2)").attr("lay-verify","required");
					$(".part_box:eq(1)").fadeIn();
					$(".part_box:eq(1) input").attr("lay-verify","required");
					$("#outlineOrder").attr("lay-verify","");

				})
				form.on('radio(waiche-status)', function(data){ //外车
					$(".part_box:eq(1) input:eq(0)").val("临时外车");
					$(".part_box:eq(1) em:eq(1)").text("");
					$(".part_box:eq(1) em:eq(2)").text("");
					$(".part_box:eq(1) input:eq(1)").val("");
					$(".part_box:eq(1) input:eq(2)").val("");
					//$(".part_box:eq(1) input").attr("lay-verify","required");
					$(".part_box:eq(1) input:eq(0)").attr("lay-verify","required");
					$(".part_box:eq(1) input:eq(1)").attr("lay-verify","");
					$(".part_box:eq(1) input:eq(2)").attr("lay-verify","");
					//$(".part_box:eq(1)").hide();
					//$(".part_box:eq(1) input").attr("lay-verify","");
				})
				form.on('radio(ziyou-status)', function(data){ //自有车
					$(".part_box:eq(1) input:eq(0)").val("自有车");
					$(".part_box:eq(1) input:eq(1)").val("");
					$(".part_box:eq(1) input:eq(2)").val("");
					$(".part_box:eq(1) em:eq(1)").text("");
					$(".part_box:eq(1) em:eq(2)").text("");
					$(".part_box:eq(1) input:eq(0)").attr("lay-verify","required");
					$(".part_box:eq(1) input:eq(1)").attr("lay-verify","");
					$(".part_box:eq(1) input:eq(2)").attr("lay-verify","");
					//$(".part_box:eq(1)").hide();
					//$(".part_box:eq(1) input").attr("lay-verify","");
				})
				form.on('radio(full-status)', function(data){  //整车
				//	$(".part_box:eq(2)").fadeIn();
				//	$(".part_box:eq(2) input:eq(0)").attr("lay-verify","required");
				//	$("input[name='dType']").removeAttr("disabled");
				})
				form.on('radio(less-status)', function(data){  //零担
					//$(".part_box:eq(2)").hide();
				 //   $(".part_box:eq(2) input").attr("lay-verify","");
				//	$("input[name='dType']").removeAttr("disabled");
				})
				form.on('radio(transfer)', function(data){ //中转
					$(".subPart:eq(0)").fadeIn();
					$(".sPart:eq(1)").hide();
					$("#fromStation").attr("lay-verify","fromStation") //起运网点校验
					$("#getPoint").attr("lay-verify","getPoint");     //到达网点校验
					$(".subPart:eq(0) input:eq(1)").attr("lay-verify","station");//中转站校验
					$(".sPart:eq(1) input").attr("lay-verify","");
					$("#station,#getPoint").val("");
					
				})
				form.on('radio(direct)', function(data){ //不中转
					$(".sPart:eq(1)").fadeIn();
					$(".subPart:eq(0)").hide();
					$(".subPart:eq(0) input").attr("lay-verify","");
					$("#qyStation").attr("lay-verify","fromStation");//起运网点校验
					$("#toAddress").attr("lay-verify","arriveStation");//到站校验
					$("#fromAddress").attr("lay-verify","required");//发站到站校验
					$("#toStation").attr("lay-verify","getPoint");   //到达网点校验
				//	$(".sPart:eq(1) input").attr("lay-verify","required");
					$("#toAddress,#toStation,#station").val("");
				})
			form.on('submit(save)', function(data){
				var startDate = new Date(Date.parse($("#startDate").val()));//发车时间
				var currDate = new Date();
				//console.log(startDate.getTime()+","+currDate.getTime());
				//if(startDate.getTime()>currDate.getTime()+2000){
				//	$.util.warning('操作提示','发车时间不能大于当前系统时间');
				//	return false;
				//}
				for(var i=0;i<fhsj.length;i++){
					var ydsj = new Date(Date.parse(fhsj[i]));
					if(startDate.getTime()<ydsj.getTime()){
						$.util.warning('操作提示','发车时间不能小于运单（'+fhsjYd[i]+'）<br/>的托运时间（'+fhsj[i]+'）');
						return false;
					}
				}
				
				var orderRows=$(".tabTr").length;
				if(orderRows>1){
					//$("#btn_sub").attr("disabled",true).addClass("layui-btn-disabled"); 
					$.confirm({
					    content: '是否录入成本？',
					    buttons: {
					        specialKey: {
					            text: '录入成本',
					            keys: ['shift', 'alt'],
					            action: function(){
					            	insert.saveHandler(1);
					            }
					        },
					        alphabet: {
					            text: '不录入',
					            keys: ['a', 'b'],
					            action: function(){
					            	insert.saveHandler(0); 
					            }
					        },
					        aaa: {
					            text: '取消',
					            keys: ['a', 'b'],
					            action: function(){
					            }
					        }
					    }
					});
				}else{
					layer.msg("请至少添加一条运单号");
				}
    		 	return false;
    		})
		})
		insert.toInsert();
		insert.Toclear();
		insert.insertPoint();
	},
	toInsert: function(){
		$('#appendTab').on('click', function(){
			var orderIds=$("#orderId").val();
			if($("#orderId").val()==""||$("#orderId").val()==null){
				layer.msg("请输入运单号！");
			}else{
				insert.InsertAjaxActive();  //添加运单号数据到table
			}
		})
	},
	insertPoint:function(){  //获取起运网点及匹配
		  EasyAjax.ajax_Post_Json({
         	  dataType: 'json',
         	  url:base_url + '/transport/adjunct/beginplaceNet',
         	//  data:JSON.stringify(baseValue),
         	  errorReason: true,
         	},function(res){
         	//	console.log(res);
         		var html="";
         		if(res.resultCode==200){
         			for (var i = 0; i < res.latticePoint.length; i++){
         				fromStation[i]=res.latticePoint[i];
         			
            	        html+="<li value='"+res.latticePoint[i]+"'>"+res.latticePoint[i]+"</li>";
            		};
            	//	console.log(fromStation);
            		$(".qywdUl").each(function(i){
            			$(this).empty().append(html); 
            		});
              		$(".pointStation").each(function(){
              			$(this).click(function(e){
              				stopPropagation(e);
              				$(this).siblings("ul").show();
              				var obj=$(this).siblings("ul");
                   		    selectInput(obj);//下拉框  
              			});
              			$(this).on("input propertychange",function(){
           				 var searchName = $(this).val(); 
           				 var selectLi=$(this).siblings("ul").find("li");
	     				    if (searchName == "") {
	     				    	$(this).siblings("ul").find("li").show();
	     				    } else {
	     				      $(selectLi).each(function() {
	     				            var s_name = $(this).attr("value");    
	     				            if (s_name.indexOf(searchName) != -1) {
	     				              $(this).show();
	     				            } else {
	     				              $(this).hide();
	     				            }
	     				          });
	     				    }
     		        	});
              		})
            		 
         		}
         	})
	},
	InsertAjaxActive: function(submitData){
		var ydbhid=$("#orderId").val();
		var iType=$("input[name='zType']:checked").val();
             EasyAjax.ajax_Post_Json({
           	  dataType: 'json',
           	  url:base_url + '/transport/bundle/query/convey/'+ydbhid+'/'+iType,
           	//  data:JSON.stringify(baseValue),
           	  errorReason: true,
           	},function(res){
           		//渲染数据
           	var tableList = $(".operationListApend");
           	 var html = $(".operationList").html(),
             	orderNum="",
             	goods="",
           	    weight="",
           	    carWeight="",
           	    count="",
           	    carCount="",
           	    volume="",
           	    xuhao ="",
           	    carVolume="";
           		var htmlBox="";
           		var index;
           		//var rows=$(".operationListApend").find("tr").length;
           		var rows=$(".tabTr").length;
           		for(index=0;index<rows;index++){
           		}
           		var oldOrderId=[];
           		var newOrderId=$.trim($("#orderId").val());
           		$(".oldOrderId").each(function(i){
           			oldOrderId=$(".oldOrderId:eq("+i+")").attr("data");
           		});
           		var num=oldOrderId.indexOf(newOrderId);  //判断表格是否存在相同运单编号
           		if(num<0){
           			 fhsj.push(res.convey.fhshj);
           			 fhsjYd.push(res.convey.ydbhid);
	           		 html = html.replaceAll("_thisId",index)
				                 .replaceAll("_orderId",'<div  class="oldOrderId" data="'+res.convey.ydbhid+'">'+res.convey.ydbhid+'</div>')
				                 .replaceAll("_address",res.convey.endPlacename)
				                 .replaceAll("_company",res.convey.fhdwmch)
				                 .replaceAll("_userAddress",res.convey.fhdwdzh)
				                 .replaceAll("_tpye",res.convey.ziti)
	           		             .replaceAll("_img",'<img src="'+base_url+'/static/transport/common/images/icon-remove.png" class="imgBox" onclick="del_table(this,\''+res.convey.ydbhid+'\')">');
	           		$(res.convey.detail).each(function(i,item){
	           				var xh = '1';
	           				if(item.xuhao){
	           					xh = item.xuhao;
	           				}
	           				xuhao+='<li class="li_zzxh">'+xh+'</li>';
		           			orderNum+='<li class="li_yd">'+item.ydxzh+'</li>';
		           			goods+='<li class="li_pm">'+item.pinming+'</li>';
		               	    weight+='<li class="li_zl">'+item.zhl+'</li>';
		               	    carWeight+='<li class="sli_zl"><input type="text" class="zcWegiht" value="'+item.zhl+'"></li>';
		                    count+='<li class="li_num">'+item.jianshu+'</li>';
		               	    carCount+='<li class="sli_num"><input type="text" class="zcNum" value="'+item.jianshu+'"></li>';
		               	    volume+='<li class="li_Vo">'+item.tiji+'</li>';
		               	    carVolume+='<li class="sli_Vo"><input type="text" class="zcVolume" value="'+item.tiji+'"></li>';
	           			           
	           		});
	           		if(!xuhao){
	           			xuhao = '';
	           		}
	           		html=html.replaceAll('_num',orderNum) 
	           				.replaceAll('_xuhao',xuhao) 
			           		 .replaceAll('_goods',goods) 
			           		 .replaceAll('_weight',weight) 
			           		 .replaceAll('_carWeight',carWeight) 
			           		 .replaceAll('_count',count) 
			           		 .replaceAll('_carCount',carCount) 
			           		 .replaceAll('_volume',volume) 
			           		 .replaceAll('_carVolume',carVolume) ;
	           		
	           	 tableList.append(html);
	           //	 $("#orderId").val("");
	             insert.toCount();
	             insert.totabLine();
	             insert.toUpdateInp();
	             insert.toDisabled();
	           
                  
       		}else{
       			layer.msg("运单编号"+newOrderId+"不能重复添加")
       		}
         });  	
	},
	toCount:function(){//添加合计
		var htm='<tr class="countBox">'+
				  '<td  colspan="7" style="text-align:right;">合计</td>'+
				  '<td>/</td>'+
				  '<td>/</td>'+
				  '<td class="tab_zl"></td>'+
				  '<td class="stab_zl"></td>'+
				  '<td class="tabNum"></td>'+
				  ' <td class="stabNum"></td>'+
				  '<td class="tab_vo"></td>'+
				  '<td class="stab_vo"></td>'+
				  '<td>/</td>'+
			  '</tr>';	     
		$(".countBox").remove();	
		$(".operationListApend").append(htm);	
		
		var tabZl=0,stab_zl=0,tab_num=0,stab_num=0,tab_vo=0,stab_vo=0;
		
		$(".li_zl").each(function(i){
		   tabZl+=parseFloat($(".li_zl:eq("+i+")").text());
		})
		$(".zcWegiht").each(function(i){
		   stab_zl+=parseFloat($(".zcWegiht:eq("+i+")").val());
		})
		$(".li_num").each(function(i){
		   tab_num+=parseFloat($(".li_num:eq("+i+")").text());
		})
		$(".zcNum").each(function(i){
		   stab_num+=parseFloat($(".zcNum:eq("+i+")").val());
		})
		$(".li_Vo").each(function(i){
		   tab_vo+=parseFloat($(".li_Vo:eq("+i+")").text());
		})
		$(".zcVolume").each(function(i){
		   stab_vo+=parseFloat($(".zcVolume:eq("+i+")").val());
		})
		$(".tab_zl").text(tabZl);
		$(".stab_zl").text(stab_zl);
		$(".tabNum").text(tab_num);
		$(".stabNum").text(stab_num);
		$(".tab_vo").text(tab_vo);
		$(".stab_vo").text(stab_vo);

	},
	totabLine:function(){//添加行数
		var rows=$(".tabTr").length;
   		for(var index=0;index<rows;index++){
   			$("table tr.tabTr:eq("+index+") td:first").text(index+1);
   		}
		
	},
	toUpdateInp:function(){

		$(".zcWegiht").each(function(index){
			 $(this).focus(function(){
				  $(".zcWegiht").removeClass("borderLine");
				   $(this).addClass("borderLine");
			   });
			   $(this).blur(function(){
				  $(this).removeClass("borderLine");
			   });
			   $(this).change(function(){
				   var wl=parseFloat($(this).val());
				   var wlOld=parseFloat($(this).parents("tr").find("td:eq(9)").find("li:eq("+index+")").text());
				   if(!isNaN(parseFloat($(this).val()))){
					   if(wl>wlOld){
						   layer.msg("不可大于货物重量");
						   $(this).val(wlOld);
					   }
					   if(wl<0){
						   layer.msg("输入错误");
						   $(this).val(wlOld);
					   }
				     insert.toCount();
				   }else{
					   layer.msg("请输入数字");
					   $(this).val(wlOld);
				   }
			   });
		});
		
		$(".zcNum").each(function(index){
			$(this).focus(function(){
				  $(".zcNum").removeClass("borderLine");
				  $(this).addClass("borderLine");
			   });
			  $(this).blur(function(){
				  $(this).removeClass("borderLine");
			   });
			   $(this).change(function(){
				   var wl=parseFloat($(this).val());
				   var wlOld=parseFloat($(this).parents("tr").find("td:eq(11)").find("li:eq("+index+")").text());
				   if(!isNaN(parseFloat($(this).val()))){
					   if(wl>wlOld){
						   layer.msg("不可大于货物件数");
						   $(this).val(wlOld);
					   }
					   if(wl<0){
						   layer.msg("输入错误");
						   $(this).val(wlOld);
					   }
					    insert.toCount();
					 }else{
						 layer.msg("请输入数字");
						 $(this).val(wlOld);
				   }
			   });
		});
		$(".zcVolume").each(function(index){
		    	$(this).focus(function(){
		    	   $(".zcVolume").removeClass("borderLine");
				   $(this).addClass("borderLine");
			    });
		    	$(this).blur(function(){
					  $(this).removeClass("borderLine");
				 });
			   $(this).change(function(){
				   var wl=parseFloat($(this).val());
				   var wlOld=parseFloat($(this).parents("tr").find("td:eq(13)").find("li:eq("+index+")").text());
				   if(!isNaN($(this).val())){
						if(wl>wlOld){
						   layer.msg("不可大于货物体积");
						   $(this).val(wlOld);
						 }
						 if(wl<0){
						   layer.msg("输入错误");
						   $(this).val(wlOld);
						 }
					     insert.toCount();
					   }else{
						   layer.msg("请输入数字");
						   $(this).val(wlOld);
					}
			   });
		});

	},
	toDisabled:function(){
		var radioObj=$(".transType");
		for(var i=0;i<radioObj.length;i++){
			var radioObjHt=$(".transType:eq("+i+")");
			//if(radioObjHt.attr("checked")){
			if(radioObjHt.next().hasClass("layui-form-radioed")){
				
			}else{
				radioObjHt.attr("disabled","true");

			}
		}
	},
	saveHandler:function(msg){//保存	 
		 var trRows=$(".operationListApend .tabTr").length;
		 var trRows;
		 var liRows;
		 var fzshhd=""; //起运网点
		 var dzshhd=""; //到达网点
		 var fazhan="";//发站
		 var arriveStation=""; //到站
		var s_ztype=$("input[name='zType']:checked").val();
		var iszzType=$("input[name='transfer']:checked").val();
		
		switch(s_ztype){
			case "0": 
				  if(iszzType=="中转"){
				        fzshhd=$("#fromStation").val();
			        }else{
			    	   fzshhd=$("#qyStation").val();
			    	   dzshhd=$("#toStation").val();
			    	   fazhan=$("#fromAddress").val();
					   arriveStation=$("#toAddress").val();
			       }
				break;
			case "2":
				   fzshhd=$("#qyStation").val();
			       dzshhd=$("#toStation").val();
				   fazhan=$("#fromAddress").val();
				   arriveStation=$("#toAddress").val();
				break;
			default:
				    fzshhd=$("#qyStation").val();
			        dzshhd=$("#toStation").val();
			        fazhan=$("#fromAddress").val();
					arriveStation=$("#toAddress").val();
				break;
		}
		 
		  var b = [];
		  for (var u = 0; u < trRows; u++) {
			   var q={};
			   var json = []; 
			   liRows=$(".operationListApend .tabTr:eq("+u+") .subNum li").length;
			   for (var i = 0; i < liRows; i++) {
			    	var j = {};
			        j.ydxzh = $(".operationListApend .tabTr:eq("+u+")").find(".li_yd").eq(i).text();
			        j.xuhao = $(".operationListApend .tabTr:eq("+u+")").find(".li_zzxh").eq(i).text();
			        j.pinming = $(".operationListApend .tabTr:eq("+u+")").find(".li_pm").eq(i).text();
			        j.zhl = $(".operationListApend .tabTr:eq("+u+")").find(".li_zl").eq(i).text();
			        j.entruckingzhl = $(".operationListApend .tabTr:eq("+u+")").find(".zcWegiht").eq(i).val();
			        j.jianshu = $(".operationListApend .tabTr:eq("+u+")").find(".li_num").eq(i).text();
			        j.entruckingjianshu = $(".operationListApend .tabTr:eq("+u+")").find(".zcNum").eq(i).val();
			        j.tiji = $(".operationListApend .tabTr:eq("+u+")").find(".li_Vo").eq(i).text();
			        j.entruckingtiji = $(".operationListApend .tabTr:eq("+u+")").find(".zcVolume").eq(i).val();
			        json.push(j);
			    }
		        q.ydbhid = $(".operationListApend .tabTr:eq("+u+")").find(".oldOrderId").text();
		        q.endPlacename = $(".operationListApend .tabTr:eq("+u+")").find("td:eq(2)").text();
		        q.fhdwdzh =  $(".operationListApend .tabTr:eq("+u+")").find("td:eq(4)").text();
		        q.fhdwmch =  $(".operationListApend .tabTr:eq("+u+")").find("td:eq(3)").text();
		        q.details=json;
		        b.push(q);
		    }
		  var e = JSON.stringify(b);
	
		var baseValue = {
				iType: $("input[name='zType']:checked").val(),
				clOwner:$("input[name='cType']:checked").val(),
				yslx: $("input[name='dType']:checked").val(),
				wxName: $("#outlineName").val(),
				wxConName: $("#userName").val(),
				wxTel: $("#userPhone").val(),
				wxItem: $("#outlineOrder").val(),
				chxh: $("#carId").val(),
				driverName: $("#driverName").val(),
				driverTel: $("#driverPhone").val(),
				istransfer: $("input[name='transfer']:checked").val(),
				fzshhd:fzshhd,
				dzshhd:dzshhd,
				daozhan: $("#station").val(),
				freightStation: $("#getPoint").val(),
				fazhan: fazhan,
				arriveStation: arriveStation,
				fchrq:$("#startDate").val(),
				yjddshj: $("#endDate").val(),
				qdCost: $("#qdCost").val(),
				elseCost: $("#otherCost").val(),				
				ysfs: $("#selectBox").val(),
				buildIncome:msg,
				conveys:e
	         }
		console.log(baseValue);
		EasyAjax.ajax_Post_Json({
			
			  dataType: 'json',
			  url:base_url + '/transport/bundle/insert/save',
			  data:JSON.stringify(baseValue),
			  contentType : 'application/json',
			  errorReason: true,
			  beforeHandler: function(){
            	 loading = layer.load(0,{
	            		shade: [0.5,'#fff']
	            	});
              },
              afterHandler: function(){
            	 layer.close(loading);
              },defCallback:function(){
            	  $("#btn_sub").removeAttr("disabled").removeClass("layui-btn-disabled"); 
              },
			},function(res){
				//layer.close(loading);
				if(res.resultCode==200){
					layer.msg('保存成功',  {
						icon: 1,
					    time: 2000 //2s后自动关闭
					  }, function(){   //保存成功后页面展示  
						  fhsjYd = new Array();
						  fhsj = new Array();
						  $("#btn_sub").removeAttr("disabled").removeClass("layui-btn-disabled"); 
						  $(".part_box:eq(2) input").val("");
						  $("input[name='zType']").removeAttr("disabled");
						  $(".operationListApend").html("");
					 });
				}else{
					layer.msg(res.reason);
				}
			});
		
	},
	Toclear: function(){
		$('.toDelete').on('click', function(){
			location.reload();
		})
	}

}
insert.init();

//列表里的删除
	function del_table(obj,ydbhid){
 	 var htmDiv=$(obj).parents("tr");
     var tabRows=$(".operationListApend").find("tr").length;
			   if(tabRows>2){   
   				 layer.confirm('',{
   					btn: ['确定', '取消'],
   					title:'提示信息',
   					content:'确认删除吗？',		
   					shadeClose: true //开启遮罩关闭
   				 },function(){
   					htmDiv.remove();
   					insert.toCount();
   				    insert.totabLine();
   					layer.closeAll();
   					for(var i=0;i<fhsj.length;i++){
   						if(fhsjYd[i]==ydbhid+''){
   							fhsjYd[i]='';
   							fhsj[i]='';
   						}
   					}
   				 });
			   }else{
				   layer.msg("至少保留一条数据");
			   }			   
		  };
	 
 
//运输方式
$("#selectBox").on("input propertychange",function(){
	 searchTrans();
});

function searchTrans() {
    var searchName = $("#selectBox").val(); 
    if (searchName == "") {
    	$(".ysUl li").show();
    } else {
      $(".ysUl li").each(function() {
            var s_name = $(this).attr("value");    
            if (s_name.indexOf(searchName) != -1) {
              $(this).show();
            } else {
              $(this).hide();
            }
          });
    }
  }

//运输方式查询
function driverList(){
	EasyAjax.ajax_Post_Json({
	  dataType: 'json',
	  url:base_url + '/transport/bundle/conveyWays',
	//  data:JSON.stringify(baseValue),
	  errorReason: true
	},function(res){
		var html="";
		$(res.conveyWays).each(function(i,item){
			selectType[i]=item.wayName;
		    html+='<li value="'+item.wayName+'">'+item.wayName+'</li>';
		});
		$(".ysUl").empty().append(html);
		
		$("#selectBox,.tips_type").click(function(e){
			stopPropagation(e);
			$(".ysUl").show();
		});	
		var obj=$(".ysUl");
		 selectInput(obj);//下拉框
  });  	
}



$("#getPoint").on("input propertychange",function(){  //输入匹配
	searchPoint();
});

function searchPoint() {
   var searchName = $("#getPoint").val(); 
   if (searchName == "") {
   	$(".zzwdUl li").show();
   } else {
     $(".zzwdUl li").each(function() {
           var s_name = $(this).attr("value");    
           if (s_name.indexOf(searchName) != -1) {
             $(this).show();
           } else {
             $(this).hide();
           }
         });
   }
 }

//中转站网点-到达网点 搜索匹配信息
function getPoint(){
	var daozhan=""
	if($("#toAddress").is(":visible")){
		daozhan=encodeURI(encodeURI($("#toAddress").val()));
	}else if($("#station").is(":visible")){
		daozhan=encodeURI(encodeURI($("#station").val()));
	}else if($("#s_toAddress").is(":visible")){
		daozhan=encodeURI(encodeURI($("#s_toAddress").val()));
	}
	
	//var 
	EasyAjax.ajax_Post_Json({
	  dataType: 'json',
	  url:base_url + '/transport/adjunct/latticePoint/'+daozhan,
	  errorReason: true,
	},function(res){
		//渲染数据
		var html="";
		for (var i = 0; i < res.latticePoint.length; i++){
	        html+="<li value='"+res.latticePoint[i]+"'>"+res.latticePoint[i]+"</li>";
		};
	    zhzhuanWdArr=res.latticePoint;	
		$(".zzwdUl").each(function(i){
			$(this).empty().append(html); 
		});
  		$(".zzStatin").each(function(){
  			$(this).click(function(e){
  				stopPropagation(e);
  				$(this).siblings("ul").show();
  				var obj=$(this).siblings("ul");
       		    selectInput(obj);//下拉框  
  			});
  			$(this).on("input propertychange",function(){
				 var searchName = $(this).val(); 
				 var selectLi=$(this).siblings("ul").find("li");
				    if (searchName == "") {
				    	$(this).siblings("ul").find("li").show();
				    } else {
				      $(selectLi).each(function() {
				            var s_name = $(this).attr("value");    
				            if (s_name.indexOf(searchName) != -1) {
				              $(this).show();
				            } else {
				              $(this).hide();
				            }
				          });
				    }
	        	});
  		})
	    
	});
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


function stopPropagation(e) {//把事件对象传入
	if (e.stopPropagation){ //支持W3C标准 doc
		e.stopPropagation();
	}else{ //IE8及以下浏览器
		e.cancelBubble = true;
	}
}

/////####
var mgr = {
		loadmode:"bundle.iType",//装载方式name匹配
		carrmode:"bundle.clOwner",//承运方式name匹配
		transtype:"yslx",//运输类型name匹配
		istransfer:"istransfer",//是否中转name匹配

		loadmode_pick:2,//装载方式-提货装载值
		loadmode_transfer:0,//装载方式-干线中转值
		loadmode_transport:1,//装载方式-配送运输值
			
		carrmode_waixian:"外线",//承运方式-外线值
		carrmode_waiche:"外车",//承运方式-外车值
		carrmode_owncar:"自有车",//承运方式-自有车值
				
		transtype_zhengche:"整车",//运输类型-整车值
		transtype_lingdan:"零担",//运输类型-零担值
		
		is_transfer_status:1,//中转
		no_transfer_status:0,//不中转

		curr_mode_type:0,//控制装载方式
		
		//下拉提示ID定义
		//外线名称
		waixianNameFocusId:"#outlineName",//外线名称focusId
		waixianContactFocusId:"#userName",//外线联系人focusId（自动填充）
		waixianTelFocusId:"#userPhone",////外线联系电话focusId（自动填充）
		waixianDataUrl:base_url + '/transport/bundle/wxName/',//外线名称下拉提示ajax URL
		//waixianDataUrl:base_url+ "/transport/adjunct/foreignRoute?foreignName=",//外线名称下拉提示ajax URL
		
		//车牌号
		chepaihaoFocusId:"#carId",//车牌号
		driverNameFocusId:"#driverName",//司机姓名（自动填充）
		driverTelFocusId:"#driverPhone",////司机电话（自动填充）
		chepaihaoDataUrl:base_url + '/transport/bundle/chxh/',//外线名称下拉提示ajax URL
		
		//中转站
		transferStationFocusId:"#station",
		transferStationDataUrl:base_url + '/transport/adjunct/daozhan/',//外线名称下拉提示ajax URL
		//到站
		transferStationEnd:"#toAddress",
		//运输目的地
		//transferStationTo:"#s_toAddress",
		//中转网点
		transferStationPositionFocusId:"#getPoint",
		transferStationPositionDataUrl:base_url + '/transport/adjunct/latticePoint/',//外线名称下拉提示ajax URL
		
		
}

//列表默认加载和事件绑定
$(document).ready(function() { 
	
	driverList();//运输方式
	
	/**输入自动提示和补全*/
	
	//1.外线名称输入时自动下拉提示
	$(mgr.waixianNameFocusId).easyAutocomplete({
		minCharNumber : 1,// 至少需要1个字符
		url : function(phrase) {
			return mgr.waixianDataUrl+encodeURI(encodeURI(phrase));
		},
		getValue : function(element) {
			return element.gysGs;
		},
		list : {
			onSelectItemEvent : function(iuput,select) {
				var item = $(mgr.waixianNameFocusId).getSelectedItemData();
				$(mgr.waixianNameFocusId).val(item.gysGs);
				$(mgr.waixianContactFocusId).val(item.gysLxr);
				$(mgr.waixianTelFocusId).val(item.gysDhsj);
			},
			onLoadEvent : function() {
				$('#eac-container-wxName').css('left',$(mgr.waixianNameFocusId).position().left);
			}
		},
		requestDelay : 500
	});
	
	//2.车牌号输入时自动下拉提示
	$(mgr.chepaihaoFocusId).easyAutocomplete({
		minCharNumber : 1,// 至少需要1个字符
		url : function(phrase) {
			return mgr.chepaihaoDataUrl+encodeURI(encodeURI(phrase));
		},
		getValue : function(element) {
			return element.baseCphm;
		},
		// listLocation: "objects",
		list : {
			onSelectItemEvent : function() {
				var item = $(mgr.chepaihaoFocusId).getSelectedItemData();
				$(mgr.chepaihaoFocusId).val(item.baseCphm);
				$(mgr.driverNameFocusId).val(item.personSj1xm);
				$(mgr.driverTelFocusId).val(item.personSj1tel);

			}
		},
		requestDelay : 500
	});
	
	//3.中转站输入时自动下拉提示
	$(mgr.transferStationFocusId).easyAutocomplete({
		minCharNumber : 1,// 至少需要1个字符
		url : function(phrase) {
			return mgr.transferStationDataUrl+encodeURI(encodeURI(phrase));
		},
		getValue : function(element) {
			$("#getPoint").val("");
			getPoint();
			return element;
		},
		list : {
			onSelectItemEvent : function() {
				var item = $(mgr.transferStationFocusId).getSelectedItemData();
				$(mgr.transferStationFocusId).val(item);
				//daozhan = item;
			}
		},
		requestDelay : 500
	});
	
	//到站输入时自动下拉提示
	$(mgr.transferStationEnd).easyAutocomplete({
		minCharNumber : 1,// 至少需要1个字符
		url : function(phrase) {
			return mgr.transferStationDataUrl+encodeURI(encodeURI(phrase));
		},
		getValue : function(element) {
			$("#toStation").val("");
			getPoint();
			return element;
		},
		list : {
			onSelectItemEvent : function() {
				//var item = $(mgr.transferStationFocusId).getSelectedItemData();
				//$(mgr.transferStationFocusId).val(item);
				//daozhan = item;
			}
		},
		requestDelay : 500
	});
	//.运输目的地输入时自动下拉提示
//	$(mgr.transferStationTo).easyAutocomplete({
//		minCharNumber : 1,// 至少需要1个字符
//		url : function(phrase) {
//			return mgr.transferStationDataUrl+encodeURI(encodeURI(phrase));
//		},
//		getValue : function(element) {
//			$("#toStation").val("");
//		//zhzhuanArr.push(element);
//			return element;
//		},
//		list : {
//			onSelectItemEvent : function() {
//				getPoint();
//				//var item = $(mgr.transferStationFocusId).getSelectedItemData();
//				//$(mgr.transferStationFocusId).val(item);
//				//daozhan = item;
//			}
//		},
//		requestDelay : 500
//	});

	//4.中转网点输入时自动下拉提示
/*
	$(mgr.transferStationPositionFocusId).easyAutocomplete({
		minCharNumber : 1,// 至少需要1个字符
		url : function(phrase) {
			var param = daozhan+"/"+phrase;
			return mgr.transferStationPositionDataUrl+encodeURI(encodeURI(param));
		},
		getValue : function(element) {
			zhzhuanWdArr.push(element);
			return element;
		},
		list : {
			onSelectItemEvent : function() {
				var item = $(mgr.transferStationPositionFocusId).getSelectedItemData();
				$(mgr.transferStationPositionFocusId).val(item);
			},
			onLoadEvent : function() {
				$('#eac-container-waijiedanhao').css('left',$(mgr.transferStationPositionFocusId).position().left);
			}
		},
		requestDelay : 500
	});*/
	
	
	layui.use(['form', 'laydate'], function(){
		console.log(zhzhuanArr);
		form = layui.form;
		form.verify({
			station: function(value, item){
				if(value!=""){
					if($.inArray(value, zhzhuanArr)==-1){
						return "中转站不存在！";
					}
				}else{
					  return '中转站不能为空';
				}
			  },//到站
			  arriveStation: function(value, item){
					if(value!=""){
						if($.inArray(value, zhzhuanArr)==-1){
							return "到站不存在！";
						}
					}else{
						  return '到站不能为空';
					}
				  },
			  getPoint: function(value, item){
					/*if(value!=""){
						if($.inArray(value, zhzhuanWdArr)==-1){
							value="";
							return "中转网点不存在！";
						}
					}*/
			},
			selectBox: function(value, item){
				if(value!=""){
					if($.inArray(value, selectType)==-1){
						return "运输方式不存在！";
					}
				}else{
					  return '运输方式不能为空';
				}
		   },
		   fromStation: function(value, item){
			/*	if(value!=""){
					if($.inArray(value, fromStation)==-1){
						value="";
						return "起运网点不存在！";
					}
				}*/
		},
		});
	});
	
	//运单号支持回车
	$("#orderId").keydown(function(event){
		if(event.keyCode==13){
		   $("#appendTab").click();
		}
		}); 
	
	//禁用回车提交表单
	$("form.layui-form").keydown(function(event){
		if(event.keyCode==13){
			return false
		 }
	 }); 
});


//function checkChxh(){
//	var isExist = true;
//	var chxh = $("#carId").val();
//	var startDate = $("#startDate").val();
//	var baseValue = {"chxh":chxh,"zhchrq":startDate};
//	EasyAjax.ajax_Post_Json({
//   	  dataType: 'json',
//   	  url:base_url + '/transport/bundle/isExistChxhAndZhchrq',
//      data:JSON.stringify(baseValue),
//   	  errorReason: true
//   	},function(res){
//   		if(res.isExist){
//   			layer.msg("该车牌号和发车时间已经存在装载清单，若不希望合并请使用不同的车牌号和发车时间");
//   		}
//   	});
//}
