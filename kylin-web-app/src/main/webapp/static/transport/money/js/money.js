/**
 * creatd by lhz on 2017/11/1
 *
 *月结收钱
 */
var year;
var printCode,printName,printNo,printYear;
var companyName;
var money = {
	init: function() {
		/*初始化当前默认年份*/
		var theDate = new Date();
		var theYEAR = theDate.getFullYear();
		year = theYEAR;
		$('#select-year').val(theYEAR);
		$('#select-year').attr('placeholder',theYEAR);
		layui.use('form', function(){
			var form = layui.form;
			form.on('submit(toCheck)', function(data){
				$('#print').unbind("click").addClass('layui-btn-disabled');
				$('#printA4').unbind("click").addClass('layui-btn-disabled');
				if($('.layui-anim-upbit dd').length > 0){
					var companyId = $('.layui-select-title input').val();
					 companyName = $('.layui-this').attr('lay-value');
				}else{
					var companyId = $('#companyN').val();
					 companyName = $('#companyN').data('value');
				}
				//var companyId = $('#companyId').val();
				var selectYear = $('#select-year').val();
				var wealthId = $('#wealthId').val();
				var transportId = $('#transportCode').val();
				submitData = {year: selectYear, wealthNo: wealthId, companyCode: companyId,transportCode:transportId}
				submitData = JSON.stringify(submitData);
				money.ajaxActive (submitData,true);  
			 	return false;
			}) 
		})
		$('#transportCode').change(function(){
			$('#wealthId').val('');
		});
		
	},
	inputHandler: function(totalMoney,dsk,decimalPlace){
		$('.Vehicle-condition').unbind('input').on('input',function(){
			var cashIncome = Number($('#CashIncome').val());
			var CashDelivery = Number($('#CashDelivery').val());
			var BankIncome = Number($('#BankIncome').val());
			var Monthly = Number($('#Monthly').val());
			if(cashIncome == null){
				cashIncome = 0;
			}
			if(CashDelivery == null){
				CashDelivery = 0;
			}
			if(BankIncome == null){
				BankIncome = 0;
			}
			if(Monthly == null){
				Monthly = 0;
			}
			var unWealth = totalMoney-cashIncome-BankIncome-CashDelivery-Monthly-dsk;
			unWealth = unWealth.toFixed(decimalPlace);
			$('#unWealth').val(unWealth);
		})
	},
	ajaxActive: function(submitData,boolFlag){
		 EasyAjax.ajax_Post_Json({
     		dataType: 'json',
             url: base_url + '/transport/finance/searchReceiveMoney',
             data: submitData,
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
         	var Msg = data.message;
         	$('#Collection').val(Msg.dsk);/*代收款*/
         	$('#totalMoney').val(Msg.totalMoney);/*代收款*/
         	$('#Monthly').val(Msg.yshk);/*月结*/
         	$('#BankIncome').val(Msg.yhshr);/*银行收入*/
         	$('#CashDelivery').val(Msg.hdfk);/*货到付款*/
         	$('#CashIncome').val(Msg.xianjin);/*现金收入*/
         	$('#unWealth').val(Msg.yshzhk);/*款未付*/
         	$('#wealthId').val(Msg.wealthNo);
         	if(Msg.wealth && typeof(Msg.wealth)!='undefined'){
         		$('#wealthId').val(Msg.wealth);/*财凭证证号*/
         	}
         	printCode=Msg.companyCode;
         	printName=companyName;
         	printNo=Msg.wealthNo;
         	printYear=Msg.year;
         	
         	if(Msg.resultCode != 200){
         		layer.open({
	           		 content: Msg.resultMsg
	           	})
	           	return false;
         	}
             var transportId = $('#transportCode').val();
	         var submitData = {year:printYear, wealthNo:printNo.toString() , companyCode: printCode, companyName:printName , transportCode:transportId}
			 submitData = JSON.stringify(submitData);
	         money._ajaxActive(submitData);
             if(!Msg.canModify){
            	 $('.toSave').removeClass('layui-btn-normal').addClass('layui-btn-disabled');
            	 $('.Vehicle-condition').attr({'placeholder':'暂无数据','readonly':true});
            	 $('.toSave, .Vehicle-condition').unbind('click').on('click',function(){
            		 if(Msg.canotModifyReason == null){
            			 layer.open({
                    		 content: '暂时无法保存修改'
                    	 })
            		 }else{
        				 layer.open({
                    		 content: Msg.canotModifyReason
                    	 })
            		 }
            	 })
             }else{
            	 /*if(boolFlag){
            		 layer.open({
                		 content: Msg.resultMsg
                	 })
            	 }*/
            	 money.inputHandler(Msg.totalMoney,Msg.dsk,Msg.decimalPlace);
            	 $('.toSave').removeClass('layui-btn-disabled').addClass('layui-btn-normal');
            	 $('.Vehicle-condition').unbind('click').removeAttr('readonly').attr('placeholder','请输入');
            	 $('.toSave').unbind('click').on('click',function(){
            		 var Mdsk = $('#Collection').val();/*代收款*/
                 	 var Myshk = $('#Monthly').val();/*月结*/
                 	 var Myhshr = $('#BankIncome').val();/*银行收入*/
                 	 var Mhdfk = $('#CashDelivery').val();/*货到付款*/
                 	 var Mxianjin = $('#CashIncome').val();/*现金收入*/
                 	 var Myshzhk = $('#unWealth').val();/*款未付*/
                 	 var dataArr = {
                 			 year: Msg.year
                 			 ,companyCode: Msg.companyCode
                 			 ,wealthNo: Msg.wealthNo
                 			 ,yshzhk: Myshzhk
                 			 ,xianjin: Mxianjin
                 			 ,hdfk: Mhdfk
                 			 ,dsk: Mdsk
                 			 ,yshk: Myshk
                 			 ,yhshr: Myhshr
                 			 ,transportCode: Msg.transportCode
                 			 ,chuna: Msg.chuna
                 	 }
                 	 dataArr = JSON.stringify(dataArr);
                 	 money.saveData(dataArr);
            	 })
             }
         });
	},
	_ajaxActive: function(submitData){
		 EasyAjax.ajax_Post_Json({
    		dataType: 'json',
            url: base_url + '/transport/finance/searchWealthPrint',
            data: submitData,
            errorReason: false,//显示返回信息
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
        	var dataResult = data.entity;
        	$('#print').removeClass('layui-btn-disabled');
        	$('#printA4').removeClass('layui-btn-disabled');
		 	tabledata = data.entity; /*为保存红票财凭号*/
		 	sessionStorage.setItem('conveyPrintInf',JSON.stringify(tabledata));
		 	var ss=sessionStorage.getItem('conveyPrintInf');
		 	console.log(ss+'mmm');
        	money._print(); 
        })
	},
	_print:function(){
		$('#print').unbind("click").click(function(){
			layer.open({
				  type: 2,
				  title: "运输受理单",
				  closeBtn: 1, //显示关闭按钮
				  shade: [0],
				  area: ['90%', '90%'],
				  //offset: 'rb', //右下角弹出
				 // time: 2000, //2秒后自动关闭
				  anim: 2,
				  content: [ base_url + "/transport/convey/carrayPrint", 'yes'], //iframe的url，no代表不显示滚动条
				  end: function () {
					  $("body").css ('overflow-y','auto');
		            }
				});
		});
		$('#printA4').unbind("click").click(function(){
			layer.open({
				  type: 2,
				  title: "运输受理单",
				  closeBtn: 1, //显示关闭按钮
				  shade: [0],
				  area: ['90%', '90%'],
				  //offset: 'rb', //右下角弹出
				 // time: 2000, //2秒后自动关闭
				  anim: 2,
				  content: [ base_url + "/transport/convey/carrayPrintA4", 'yes'], //iframe的url，no代表不显示滚动条
				  end: function () {
					  $("body").css ('overflow-y','auto');
		            }
				});
		});
	},
	saveData: function(dataArr){
		 EasyAjax.ajax_Post_Json({
    		dataType: 'json',
            url: base_url + '/transport/finance/saveReceiveMoney',
            data: dataArr,
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
        	var messageData = data.message;
        	if(messageData.resultCode != 200){
        		layer.open({
            		content: messageData.resultMsg
            	})
        	}else{
        		layer.open({
            		content: '保存成功'
            	})
            	money.ajaxActive (submitData,false);
        	}
        });
	}
}
money.init();
