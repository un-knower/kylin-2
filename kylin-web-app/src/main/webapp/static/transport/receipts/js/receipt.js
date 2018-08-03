/**
 * creatd by wyp on 2018/1/13
 *
 *生成分理收据
 */

//自动在打印之前执行
window.onbeforeprint = function(){		
	$(".lookDiv").hide();
	$(".printDiv").show();
}


//自动在打印之后执行
window.onafterprint = function(){
	$(".lookDiv").show();
	$(".printDiv").hide();
} 


//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r != null) return unescape(r[2]); return null; //返回参数值
}

var  ydbhid=getUrlParam("ydbhid"),
     ydzh=getUrlParam("ydzh"),
	 ydxzh=getUrlParam("ydxzh"),
	 hdfk=getUrlParam("hdfk"),
	 dsk=getUrlParam("dsk");
	 xuhao=getUrlParam("xuhao");
var vm = new Vue({
	el:'#rrapp',
	data:{
		caseinfo: {},
		tabinfo: {},
		flag:true,
		show:true,
		printMaster: {},
		printList: {}
	},
	  watch:{
		  caseinfo:function(){
			  this.$nextTick(function(){
				  $("#waybillId").val(ydbhid);
				  this.toCount();//添加合计
				 // this.getCode();
                  this.selectMoneyType();	
                  this.s_payMoney();
                  this.checkSelect();
			  });
		  }
	  },
	methods: {	
		getInfo: function(){
			var baseValue={
					ydbhid:ydbhid,
				     ydzh:ydzh,
					 ydxzh:ydxzh,
					 hdfk:hdfk,
					 dsk:dsk,
					 xuhao:xuhao
			 };
			  layui.use(['layer','form'], function(){
		    		var layer = layui.layer;
					EasyAjax.ajax_Post_Json({
						  dataType: 'json',
						  url:base_url + '/transport/financialReceipts/collectMoney/search',
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
								vm.caseinfo=vm.printList=res.resultInfo.financialReceiptsDetailList;
								vm.tabinfo=vm.printMaster=res.resultInfo.financialReceiptsMaster;
								vm.tabinfo.jksj=getMyDate(res.resultInfo.financialReceiptsMaster.jksj);
								$(vm.caseinfo).each(function(i,item){
									if(item.shsje==null){
										item.shsje=0;
									}
								})
								
								if(res.resultInfo.isGenerate==0){//未生成分理收据
									vm.flag=true;	
								}else{
									vm.flag=false;
									$("#saveBtn").unbind("click").attr("disabled",true);
								}
							}else{
								vm.show=false;
								$(".btnBox a").addClass("layui-btn-disabled");
								$(".btnBox button").addClass("layui-btn-disabled").unbind("click").attr("disabled",true);
								layer.msg(res.reason);
							}
						});
					  }); 
			
		},
		//应收金额下拉选择
		selectMoneyType:function(){	  
			if(vm.flag){
				$(".s_selectInput").each(function(i){
			    	$(this).click(function(e){
			    		stopPropagation(e);
			    		$(".s_selectUl").hide();
			    		$(this).siblings(".s_selectUl").show();
							$(".s_selectUl").eq(i).find("li").click(function(){
						   		var _target = $(this).parent("ul").siblings(".s_selectInput");
						   		_target.val($(this).text());
						   		$(this).parent("ul").hide();		
					       });						   
			    	});
			    });
				$(document).click(function(){
					$(".s_selectUl").hide();
			   	});
			}else{
				 $(".s_selectInput").each(function(i){
					 $(this).unbind("click").attr("readonly","readonly");
	             });
				 return false
			}
		},
		//选择
		checkSelect:function(){
            $(".tips_type").each(function(){
            	$(this).unbind("click").bind("click",function(e){
            		stopPropagation(e);
            		$(this).prev("input").trigger("click");
            	});
            })
			
		},
		//获取客户编码   需求变动 该方法未被调用
		getCode:function(){				
			if(vm.flag){
				EasyAjax.ajax_Post_Json({
					  dataType: 'json',
					  url:base_url + '/transport/adjunct/listCustomerByGs',
					  errorReason: true,
					  async: true,
					},function(res){
						 var taBox="";
						//console.log(res);
						$(res.resultInfo).each(function(i,item){
							 taBox+="<li value="+item.customerKey+" data="+item.customerName+">"+item.customerKey+"</li>";
						});
		                
						$(".selectUl").each(function(i){
							 $(".selectUl").eq(i).append(taBox);
						});
		                $(".selectInput").each(function(i){
		                	$(this).click(function(e){
		                		stopPropagation(e);
			        			if($(".selectUl:eq("+i+") li").length>0){
			        			   $(".selectUl:eq("+i+")").show();
			        			}
			        			
		                	});
		                });
		        		var obj=$(".selectUl");
		        		selectInput(obj);//下拉框	
		                
		                
					});
			}else{
				  $(".selectInput").each(function(i){
					 $(this).unbind("click").attr("readonly","readonly");
	              });
				  return false;
			}
		},
		//新增
		add:function(){	
			if(vm.flag){
				 var newTr = $('.tableList').html();
				 var tr="<tr class='addList trList'>"+newTr+"</tr>";
				 $(".totals").before(tr);
				 $(".driverCode").text(vm.caseinfo[0].yshm);
				 $(".userCode").text(vm.caseinfo[0].khbm);
				 $(".userCompany").text(vm.caseinfo[0].khmc);
				 this.selectTxt();
				 this.chooseHandler();
				 this.inpCount();
				 this.selectMoneyType();
				 
				 $(".selectInput").each(function(i){
	             	$(this).click(function(e){
	             		stopPropagation(e);
	             		  $(".selectUl").hide();
		        			if($(".selectUl:eq("+i+") li").length>0){
		        			   $(".selectUl:eq("+i+")").show();
		        			}
		        			
	             	});
	             });
	     		var obj=$(".selectUl");
	     		//selectInput(obj);//下拉框	
	     		vm.checkSelect();
			}else{
				return false;
			}
		},
		//select收费项目选择框
		selectTxt:function(){
			$(".feeType").each(function(){
				$(this).change(function(){ 
					$(this).siblings("input").val($(this).val());
				});
			})
		},
		//合计
		toCount:function(){
			var countMoney=0;
			var newMoney=0;
			$(".getMoney").each(function(i){
				  countMoney+=parseFloat($(".getMoney:eq("+i+") input").val());			 
			});
			
			if(vm.flag){
				$(".addList .money").each(function(i){
					   newMoney+=parseFloat($(".addList .money:eq("+i+")").val());
				});
					
				$("#totals").text((countMoney+newMoney).toFixed(2));
			}else{
				$("#totals,.printTotal").text(countMoney.toFixed(2));
			}
			changeMoney($("#totals").text(),$(".chineseMoney"));//添加大写合计	
			changeMoney($(".printTotal").text(),$(".printChinese"));//添加打印大写合计	
		
		},
		//实收金额统计
		s_payMoney:function(){
			var money=0;
			if(!vm.flag){
				$(".payMoney").each(function(i){
					money+=	parseFloat($(".payMoney:eq("+i+") span").text()); 
				});
				$("#re_totals").text(money.toFixed(2));
				changeMoney($("#re_totals").text(),$(".re_chineseMoney"));//添加大写合计	
			}
		},
		//输入金额重新计算合计
		inpCount:function(){
			$(".money").each(function(index){
				 $(this).focus(function(e){
					 var old=$(this).val();
					 $(".money").removeClass("borderLine");
					  $(this).addClass("borderLine");
					  if(old==0){
						  $(this).val("")
					  }
				   });
				   $(this).blur(function(e){
					  var old=$(this).val();
					  $(this).removeClass("borderLine");
					  if(old==""){
						  $(this).val(0);
					  }
				   });
				   $(this).change(function(e){
					   var wl=parseFloat($(this).val());
					   if(!isNaN($(this).val())){
						   if(wl<0){
							   layer.msg("输入错误");
							   $(this).val(0);
						   }
						 $(this).val(wl.toFixed(2));
					     vm.toCount();//重新计算
					   }else{
						   layer.msg("请输入正确的金额");
						   $(this).val(0);
						   vm.toCount();//重新计算
					   }
				   });
			});		
			
		},
		//选中行
		chooseHandler:function(){
			 $(".addList").each(function(index){
			    	$(this).click(function(e){
			    		//stopPropagation(e);
			    		$(".addList").removeClass("gery");
			    		$(this).addClass("gery");
			    	});
			    })
		},
		//删除行
		deleteHandler:function(){
			if(vm.flag){
				 var len=$(".gery").length;
				 var trlen=$(".inpTab .addList").length;
				 if(trlen>0){
					 if(len>0){
						 layer.confirm('',{
								btn: ['确定', '取消'],
								title:'提示信息',
								content:'确认删除选中行吗？',		
								shadeClose: true //开启遮罩关闭
							 },function(){
								 $(".gery").remove();
								 vm.toCount();//重新计算
								layer.closeAll();
							 });
					 }else{
						 layer.msg("请先选中删除行！");
					 }
				 }else{
					 layer.msg("不可删除已生成的收费项目");
				 }
			}else{
				  return false;
			}
		},
		//打印
		print:function(){
			if(!vm.flag){
				window.print();
				//print_page();
			}else{
				return false;
			}
		},
		//收钱
		collect:function(){
			if(!vm.flag){
				var sjid=$(".companyId").text();
				var baseValue={
						sjid:sjid
				  }
				EasyAjax.ajax_Post_Json({
					  dataType: 'json',
					  url:base_url + '/transport/financialReceipts/payMoney/search',
					  data:JSON.stringify(baseValue),
					  contentType : 'application/json',
					  errorReason: true
					},function(res){
						if(res.resultCode==200){
							if(res.resultInfo.isPayMoney==0){  //未收钱 0    1
								window.location.href=base_url+"/transport/financialReceipts/payMoney?sjid="+sjid;
							}else{
								layer.msg("该订单已收钱")
							}  	
				        }else{
				        	layer.msg(res.reason);
				        }
					});	
				
			}else{
			layer.msg("已收钱，不能再次操作")
			   return false;
			}
		},
		 //保存数据
		 saveHandler:function(){
			 if(vm.flag){
				var  trRows=$(".inpTab tbody tr.trList").length;
				 var baseInfo = [];
				 var master={};
				 master.fiwtCwpzhbh=$("#fiwtCwpzhbh").val();
				 master.fiwtYdbhid=$("#fiwtYdbhid").val();
				 master.type=$("#s_type").val();
				 master.ver=$("#ver").val();
				 master.jkdw=$(".jkdw").text();
				 master.jksj=new Date($(".dateTime").text());
				 master.beiZhu=$(".beizhu").val(); 
				  for (var u = 0; u < trRows; u++) {
					   var q={}; 
					   var trLen= $(".inpTab tbody .trList:eq("+u+")");
				        q.shfxm = trLen.find("td:eq(0) input").val();
				        q.shfje = trLen.find("td:eq(1) input").val();
				        q.shftype = trLen.find("td:eq(2) input").val();
				        q.yshm = trLen.find("td:eq(3)").text();
				        q.beizhu = trLen.find("td:eq(4) input").val();
				        q.khbm = trLen.find("td:eq(7)").text();
				        q.khmc = trLen.find("td:eq(8)").text();
				        baseInfo.push(q);
				    }						  
			var baseValue={
					financialReceiptsMaster:master,
					financialReceiptsDetailList:baseInfo
			    }
			vm.flag = false;
	 		EasyAjax.ajax_Post_Json({
					  dataType: 'json',
					  url:base_url + '/transport/financialReceipts/saveFinancialReceipts',
					  data:JSON.stringify(baseValue),
					  contentType : 'application/json',
					  errorReason: true,
					  async: true,
					  beforeHandler: function(){
		            	 loading = layer.load(0,{
			            		shade: [0.5,'#fff']
			            	});
		              },
		              afterHandler: function(){
		            	 layer.close(loading);
		              }
					},function(res){
						console.log(res);
						if(res.resultCode==200){
							layer.msg('保存成功',  {
								icon: 1,
							    time: 2000 //2s后自动关闭
							  }, function(){   //保存成功后页面展示  
								  $(".inpTab .addList ").hide();
							      vm.getInfo(); 
							      vm.toCount();
							      vm.s_payMoney();
							 });
						} else {
							vm.flag = true;
						}
					}); 
			}else{ 
				return false;
			}
	 }
	}
});

vm.getInfo();

var insert = {
		init: function() {
			layui.use(['form', 'laydate'], function(){
				form = layui.form;
				layer = layui.layer;
				//自定义验证规则
		  		  form.verify({
		  			money: function(value){
		  		      if(isNaN(value) || parseFloat(value)<=0){ 
		  		           return "请重新输入正确的金额"; 
		  		      }
		  		    }
		  		  });  			
	            //保存			
				form.on('submit(save)', function(data){
					vm.saveHandler();
					$(".addTabBox").hide();
	    		 	return false;
	    		})
				
				
			});
			
		}
}

insert.init();

//小写转大写 
 function changeMoney(value,obj){
        var Num = value;  
        if(Num=="") {  
                      //输入框删减为空时，将大写金额的内容值设为原始状态，当然也可以根据需求进行修改  
        	obj.text("零元整");  
            return false;  
        }  
        part = String(Num).split(".");  
        newchar = "";  
        for(i=part[0].length-1;i>=0;i--){  
            if(part[0].length > 10){  
                alert("位数过大，无法计算");//前面如果有验证位数的，此处判断可去掉  
                return false;  
            }  
            tmpnewchar = ""  
            perchar = part[0].charAt(i);  
            switch(perchar){  
                case "0": tmpnewchar="零" + tmpnewchar ;break;  
                case "1": tmpnewchar="壹" + tmpnewchar ;break;  
                case "2": tmpnewchar="贰" + tmpnewchar ;break;  
                case "3": tmpnewchar="叁" + tmpnewchar ;break;  
                case "4": tmpnewchar="肆" + tmpnewchar ;break;  
                case "5": tmpnewchar="伍" + tmpnewchar ;break;  
                case "6": tmpnewchar="陆" + tmpnewchar ;break;  
                case "7": tmpnewchar="柒" + tmpnewchar ;break;  
                case "8": tmpnewchar="捌" + tmpnewchar ;break;  
                case "9": tmpnewchar="玖" + tmpnewchar ;break;  
            }  
            switch(part[0].length-i-1){  
                case 0: tmpnewchar = tmpnewchar +"元" ;break;  
                case 1: if(perchar!=0)tmpnewchar= tmpnewchar +"拾" ;break;  
                case 2: if(perchar!=0)tmpnewchar= tmpnewchar +"佰" ;break;  
                case 3: if(perchar!=0)tmpnewchar= tmpnewchar +"仟" ;break;  
                case 4: tmpnewchar= tmpnewchar +"万" ;break;  
                case 5: if(perchar!=0)tmpnewchar= tmpnewchar +"拾" ;break;  
                case 6: if(perchar!=0)tmpnewchar= tmpnewchar +"佰" ;break;  
                case 7: if(perchar!=0)tmpnewchar= tmpnewchar +"仟" ;break;  
                case 8: tmpnewchar= tmpnewchar +"亿" ;break;  
                case 9: tmpnewchar= tmpnewchar +"拾" ;break;  
            }  
            newchar = tmpnewchar + newchar;  
        }  
        if(Num.indexOf(".")!=-1){  
            if(part[1].length > 2) {  
                part[1] = part[1].substr(0,2)  
            }  
            for(i=0;i<part[1].length;i++){  
                tmpnewchar = ""  
                perchar = part[1].charAt(i)  
                switch(perchar){  
                    case "0": tmpnewchar="零" + tmpnewchar ;break;  
                    case "1": tmpnewchar="壹" + tmpnewchar ;break;  
                    case "2": tmpnewchar="贰" + tmpnewchar ;break;  
                    case "3": tmpnewchar="叁" + tmpnewchar ;break;  
                    case "4": tmpnewchar="肆" + tmpnewchar ;break;  
                    case "5": tmpnewchar="伍" + tmpnewchar ;break;  
                    case "6": tmpnewchar="陆" + tmpnewchar ;break;  
                    case "7": tmpnewchar="柒" + tmpnewchar ;break;  
                    case "8": tmpnewchar="捌" + tmpnewchar ;break;  
                    case "9": tmpnewchar="玖" + tmpnewchar ;break;  
                }  
                if(i==0)tmpnewchar =tmpnewchar + "角";  
                if(i==1)tmpnewchar = tmpnewchar + "分";  
                newchar = newchar + tmpnewchar;  
            }  
        }  
        while(newchar.search("零元") != -1){  
            newchar = newchar.replace("零零", "零");  
            newchar = newchar.replace("零亿", "亿");  
            newchar = newchar.replace("亿万", "亿");  
            newchar = newchar.replace("零万", "万");  
            newchar = newchar.replace("零元", "元");  
            newchar = newchar.replace("零角", "");  
            newchar = newchar.replace("零分", "");  
        }  
        if(newchar.charAt(newchar.length-1) == "元" || newchar.charAt(newchar.length-1) == "角"){  
            newchar = newchar+"整";  
        }  
        obj.text(newchar);  
     };  

     
     function stopPropagation(e) {//把事件对象传入
    		if (e.stopPropagation){ //支持W3C标准 doc
    			e.stopPropagation();
    		}else{ //IE8及以下浏览器
    			e.cancelBubble = true;
    		}
    	}
     
     

   //下拉框 
   function selectInput(obj){
	   obj.each(function(i){
		   obj.eq(i).find("li").click(function(){
		   		var _target = $(this).parent("ul").siblings(".selectInput");
		   		var _name =$(this).parents("tr").find(".userName");
		   		_target.val($(this).text());
		   		_name.text($(this).attr("data"));
		   		$(this).parent("ul").hide();		
		   	});
	   });
   	

   	$(document).click(function(){
   		obj.hide();
   	});
   }
 
   
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
   
   function tihuodan(){
	   window.parent.tihuodan_search();
   }
   
   function songhuodan(){
	   window.parent.songhuodan_search();
   }