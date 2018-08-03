/**
 * creatd by wyp on 2018/1/13
 *
 *生成分理交钱
 */

	//获取url中的参数
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		if (r != null) return unescape(r[2]); return null; //返回参数值
	}

var sjid=getUrlParam("sjid");
var s_type=1;
var vm = new Vue({
	el:'#rrapp',
	data:{
		flag: true,
		caseinfo: {},
		tabinfo: {}
	},
	watch:{
		  caseinfo:function(){
			  this.$nextTick(function(){
				  this.toCount();//添加合计
			  });
		  }
	},
	methods: {	
		getInfo: function(obj,type){
			var baseValue;
			if(type==1){
				 s_type=1;
				 baseValue={sjid:obj}
			}else{
				s_type=2
				 baseValue={ydbhid:obj}
			}
			if(obj==""||obj==null){
				return false
			}
			console.log(baseValue);
			$.ajax({
			    type: "POST",
			    url:base_url + '/transport/financialReceipts/payMoney/search',
			    data:JSON.stringify(baseValue),
			    dataType: "json",
				contentType : 'application/json',
			    success: function(res){
			        if(res.resultCode==200){
			        	sjid = res.resultInfo.financialReceiptsMaster.id;
			        	vm.caseinfo=res.resultInfo.financialReceiptsDetailList;
						vm.tabinfo=res.resultInfo.financialReceiptsMaster;
						vm.tabinfo.jksj=getMyDate(res.resultInfo.financialReceiptsMaster.jksj);
						if(res.resultInfo.isPayMoney==0){
							vm.flag=true;
						}else{
							vm.flag=false;
						} 
						$("#searchType").val(s_type);
			        }else{
						vm.caseinfo=res.resultInfo={};
						vm.tabinfo=res.resultInfo={};
						vm.flag=false;
			        	layer.msg(res.reason);
			        }
			    	
			     },
			    error: function() {
			    	layer.msg("服务器异常");
			    } 				 				    
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
		//合计
		toCount:function(){
			var countMoney=0;
			var newMoney=0;
			$(".amount").each(function(i){
				var cash=$(".amount").eq(i).parents("tr").find(".cash").val();
				var bank=$(".amount").eq(i).parents("tr").find(".bank").val();
				var transfer=$(".amount").eq(i).parents("tr").find(".transfer").val();
				var amout=parseFloat(cash)+parseFloat(bank)+parseFloat(transfer);
				$(".amount").eq(i).text(amout);				
			   countMoney+=parseFloat($(".getMoney:eq("+i+")").text());	
			   var s =$(".amount:eq("+i+")").text();
			   newMoney+=isNaN(parseFloat(s))==true?0:parseFloat(s);
			});
			this.inpCount();			

		    $("#totals").text(countMoney.toFixed(2));//借方金额
			changeMoney($("#totals").text(),$(".chineseMoney"));//添加大写合计	
			
			$("#re_totals").text(newMoney.toFixed(2));//贷方金额
			changeMoney($("#re_totals").text(),$(".re_chineseMoney"));//添加大写合计	
		},
		//输入金额重新计算合计
		inpCount:function(){
			$(".money").each(function(index){
				 $(this).focus(function(){
					 var old=$(this).val();
					 $(".money").removeClass("borderLine");
					  $(this).addClass("borderLine");
					  if(old==0){
						  $(this).val("")
					  }
				   });
				   $(this).blur(function(){
					  var old=$(this).val();
					  $(this).removeClass("borderLine");
					  if(old==""){
						  $(this).val(0);
					  }
				   });
				   $(this).change(function(){
					   var wl=parseFloat($(this).val());
					   var counts=parseFloat($(this).parents("tr").find(".getMoney").text());
					   var payMoney=parseFloat($(this).parents("tr").find(".amount").text());
					   if(!isNaN($(this).val())){
							   if(wl<0){
								   layer.msg("输入错误！");
								   $(this).val(0);
							   }else if(wl>counts){
								   $(this).val(0);
								   layer.msg("不可大于应收金额！");
							   }else if( payMoney>counts){
								   $(this).val(0);
								   layer.msg("实收金额不可大于应收金额！"); 
							   }
						     vm.toCount();//重新计算
			
					   }else{
						   layer.msg("请输入正确的金额");
						   $(this).val(0);
						   vm.toCount();//重新计算
					   }
				   });
			});		
			
		},

		 //保存数据
		 saveHandler:function(){
			 if(vm.flag){
				if($(".trList").length<=0){
					layer.msg("请先查询收据号或运单编号");
					return false;
				}
				if(vm.filedCheck()){
						var  trRows=$(".inpTab tbody tr.trList").length;
						 var baseInfo = [];
						 var master={};
						 master.id=sjid;//收据号
						 master.fiwtYdbhid=$("#fiwtYdbhid").val();
						 
						  for (var u = 0; u < trRows; u++) {
							   var trInf={}; 
							   var trLen= $(".inpTab tbody .trList:eq("+u+")");
							   trInf.shfje = trLen.find("td:eq(1)").text();//应收金额
							   trInf.xjsr = trLen.find("td:eq(3) input").val();//现金
							   trInf.yhsr= trLen.find("td:eq(4) input").val();//银行
							   trInf.zzsr= trLen.find("td:eq(5) input").val();//转账
							   trInf.shsje= trLen.find("td:eq(6)").text(); //实收金额
							   trInf.sqbz= trLen.find("td:eq(7) input").val();//交款责任人
							   trInf.xzh= trLen.find("td:eq(7) span").text();//细则号						   
						        baseInfo.push(trInf);
						    }	
					var baseValue={
							financialReceiptsMaster:master,
							financialReceiptsDetailList:baseInfo
					}	  	
					
					$.ajax({
					    type: "POST",
					    url:base_url + '/transport/financialReceipts/financialReceiptsPayMoney',
					    data:JSON.stringify(baseValue),
					    dataType: "json",
						contentType : 'application/json',
					    success: function(res){
					    	layer.msg('操作成功',  {
								icon: 1,
							    time: 2000 //2s后自动关闭
							  }, function(){   //保存成功后页面展示
								  var type=parseInt($("#searchType").val());
								  var payMoneyObj;
								  if(type == 1){
									  payMoneyObj = sjid; 
								  }else if(type == 2){
									  payMoneyObj = $("#fiwtYdbhid").val().trim(); 
								  }
								  vm.getInfo(payMoneyObj,type);					     
							 });
					    	
					     },
					    error: function() {
					    	layer.msg("服务器异常");
					    } 				 				    
					});	
					
				}else{
					layer.msg("实收金额错误，请核实后重新输入")
				}
		}else{
			return false;
		}
	 }
	}
});

//if(getUrlParam("sjid")!=null){
	 vm.getInfo(sjid,1);
//}

var insert = {
		init: function() {
			layui.use(['form', 'laydate'], function(){
				form = layui.form;
				layer = layui.layer;
				//自定义验证规则
		  		  form.verify({
		  			money: function(value){
		  		      if(isNaN(value) || parseFloat(value)<0){ 
		  		           return "请重新输入正确的金额"; 
		  		      }
		  		    }
		  		  });
					form.on('radio(sj_radio)', function(data){ //收据号
						$("#waybillId").attr("lay-verify","number");
					})
					form.on('radio(yd_radio)', function(data){ //运单编号
						$("#waybillId").attr("lay-verify","required");
					})
		  		 //查询	
		  		//var classId=$(".sj_radio").next("div").hasClass("layui-form-radioed");
				form.on('submit(searchBtn)', function(data){
					var orderId=$("#waybillId").val();
			     	var s_type=$("input:radio[name='s_type']:checked").val();
			     	var type=0;
			     	var classId=$(".sj_radio").next("div").hasClass("layui-form-radioed");
					if(classId){
						type=1;
					}else{
						type=2;
					}
					$("#searchType").val(type);
					$("#ydbhid").val(orderId);
					vm.getInfo(orderId,type);
	    		 	return false;
	    		})
	            //保存			
				form.on('submit(save)', function(data){
					vm.saveHandler();
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
	 