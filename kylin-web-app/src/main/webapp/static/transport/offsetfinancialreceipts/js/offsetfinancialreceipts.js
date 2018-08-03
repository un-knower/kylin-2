var vm = new Vue({
	el:'#rrapp',
	data:{
		flag: true,
		caseinfo: {},
		tabinfo: {},
		istoRed: false
	},
	watch:{
		  caseinfo:function(){
			  this.$nextTick(function(){
				  this.toCount();//添加合计
			  });
		  }
	},
	methods: {	
		getInfo: function(obj,type,level){
			var baseValue;
			if(type==1){
				 baseValue={sjid:obj}
			}else{
				 baseValue={ydbhid:obj}
			}
			if(obj==""||obj==null){
				return false
			}
			var _self = this;
			var url = '';
			if(level&&level==1){
				url = '/transport/financialReceipts/searchByNoRight';//没有权限
			}else if(level&&level==2){
				url = '/transport/financialReceipts/searchByCommon';//普通权限
			}else if(level&&level==3){
				url = '/transport/financialReceipts/searchByManager';//财务经理
			}
			console.log(baseValue);
			$.ajax({
			    type: "POST",
			    url:base_url + url,
			    data:JSON.stringify(baseValue),
			    dataType: "json",
				contentType : 'application/json',
			    success: function(res){
			        if(res.resultCode==200){
			        	_self.istoRed = true;
			        	vm.caseinfo=res.resultInfo.financialReceiptsDetailList;
						vm.tabinfo=res.resultInfo.financialReceiptsMaster;
						vm.tabinfo.jksj=getMyDate(res.resultInfo.financialReceiptsMaster.jksj);
//						$("#searchType").val(s_type);
			        }else{
						vm.caseinfo=res.resultInfo={};
						vm.tabinfo=res.resultInfo={};
			        	layer.msg(res.reason);
			        }	    	
			     },
			    error: function() {
			    	layer.msg("服务器异常");
			    } 				 				    
			});	

		},
 
		//合计
		toCount:function(){
			var countMoney=0;
			var newMoney=0;
			$(".amount").each(function(i){
				var cash=$(".amount").eq(i).parents("tr").find(".cash").text();
				var bank=$(".amount").eq(i).parents("tr").find(".bank").text();
				var transfer=$(".amount").eq(i).parents("tr").find(".transfer").text();
				var amout=parseFloat(cash)+parseFloat(bank)+parseFloat(transfer);	
			   countMoney+=parseFloat($(".getMoney:eq("+i+")").text());	
			   newMoney+=parseFloat($(".amount:eq("+i+")").text());
			});			
		    $("#totals").text(countMoney.toFixed(2));//借方金额
			changeMoney($("#totals").text(),$(".chineseMoney"));//添加大写合计	
			
			$("#re_totals").text(newMoney.toFixed(2));//贷方金额
			changeMoney($("#re_totals").text(),$(".re_chineseMoney"));//添加大写合计	
		},

		 //冲红
		 saveHandler:function(){
			if($(".trList").length<=0){
				layer.msg("请先查询收据号或运单编号");
				return false;
			}	
//			var _self = this;
			var baseValue={
					sjid:parseInt($("#sjid").text()),
					offsetReason:$('#tips').val()
			}	  		
			$.ajax({
			    type: "POST",
			    url:base_url + '/transport/financialReceipts/offsetFinancialReceipts/offset',
			    data:JSON.stringify(baseValue),
			    dataType: "json",
				contentType : 'application/json',
			    success: function(res){
//			    	_self.istoRed = false;
			    	layer.msg('收据冲红成功，生成的红票收据号为：' + res.resultInfo + '，请重新生成新的分理收据！',  {
						icon: 1,
					    time: 2000 //2s后自动关闭
					  }, function(){   //保存成功后页面展示
						  window.location.href = base_url + '/transport/financialReceipts/toOffsetFinancialReceipts';				     
					 });    	
			     },
			    error: function() {
			    	layer.msg("服务器异常");
			    } 				 				    
			});						
		 }	
	}
});


var insert = {
		init: function() {
			layui.use(['form', 'laydate'], function(){
				form = layui.form;
				layer = layui.layer;
				form.on('radio(sj_radio)', function(data){ //收据号
					$("#waybillId").attr("lay-verify","number");
				})
				form.on('radio(yd_radio)', function(data){ //运单编号
					$("#waybillId").attr("lay-verify","required");
				})
		  		 //查询	
				form.on('submit(searchBtn)', function(data){
					var orderId=$("#waybillId").val();
//			     	var s_type=$("input:radio[name='s_type']:checked").val();
			     	var type=0;
			     	var classId=$(".sj_radio").next("div").hasClass("layui-form-radioed");
					if(classId){
						type=1;
					}else{
						type=2;
					}
					$("#searchType").val(type);
					var permissLevel = $("#permissLevel").val();
					vm.getInfo(orderId,type,permissLevel);
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
