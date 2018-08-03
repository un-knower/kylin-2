//校验数据
var WaybillCheckData = {
	alerttimes : 0,//页面加载后，运单号校验是否存在的弹出框只出现一次的计数器
	//页面加载后初始化的数据校验
	initDataCheck:function() {
		//上门取货费校验
		$('.catch-goods').on('change', function(){
			if(!$('.zdc').is(':checked') && !$('.zdz').is(':checked') && !$('.cdz').is(':checked') && !$('.cdc').is(':checked')){
				layer.msg('请选择服务方式，再填写上门取货/送货费用',{anim:6,icon:5});
				$('.catch-goods').val(0);
				return false;
			}
			var catchfee = $('.catch-goods').val();
			if($('.zdc').is(':checked') || $('.zdz').is(':checked')){
				if(catchfee != 0){
					layer.msg('站到仓、站到站，上门收货费必须为0',{anim:6,icon:5});
					$('.catch-goods').val(0);
				}
			}
		});
		//送货上门费校验
		$('.send-goods').on('change', function(){
			if(!$('.zdc').is(':checked') && !$('.zdz').is(':checked') && !$('.cdz').is(':checked') && !$('.cdc').is(':checked')){
				layer.msg('请选择服务方式，再填写上门取货/送货费用',{anim:6,icon:5});
				$('.send-goods').val(0);
				return false;
			}
			var catchfee = $('.send-goods').val();
			if($('.cdz').is(':checked') || $('.zdz').is(':checked')){
				if(catchfee != 0){
					layer.msg('仓到站、站到站，送货上门费必须为0',{anim:6,icon:5});
					$('.send-goods').val(0);
				}
			}
		});
		/*运单号是否存在校验*/
		$("#ydbhid").on("blur",function(){
			if($("#ydbhid").val().length>=10){
				$.ajax({
					url:base_url + "/transport/convey/checkTransportCode?transportCode="+encodeURI(encodeURI($("#ydbhid").val())),
					type:'get',
					dataType:"json",
					success:function(data){
						if(data.resultCode!='200'){
							//$("#ydbhid").css("border","2px solid orange");
							//if(this.alerttimes==0){
								layer.msg('该运单号已经存在！',{anim:6,icon:5});
								//this.alerttimes+=1;
							//}
						}//else{
//							$("#ydbhid").css("border","1px solid #d3d6de");
//						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
			            if(XMLHttpRequest.readyState == 0) {
			            } else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
			            	layer.msg('服务器忙，请重试！',{anim:6,icon:5});
			            } else {
			            	layer.msg('系统异常，请联系系统管理员!',{anim:6,icon:5});
			            }
			        }
			   });
			}
		});
		
		//保存按钮以及校验
		$("#btn-save").on("click",function(){
			if($("#form").valid()&&checkInputSelect()){
				var rate = $("input[name='price.premiumRate']").val();
				$("#form").find("input:hidden[name='saveLogo']").val($(this).attr("value"));
				var daozhan = $("#daozhan").val();
				var catchgoodsfee = $('.catch-goods').val();
				var hasFinance = $('#hasFinance').val();
				var sendgoodsfee = $('.send-goods').val();
				var startDate = new Date(Date.parse($("#fhshj").val()));//托运时间
				var currDate = new Date();
				if(startDate.getTime() < currDate.getTime()-3600*24*3*1000){
					$.util.warning('操作提示', '托运时间只能录入当前系统时间前三天内');
					return false;
				}
				if(startDate.getTime() > currDate.getTime()+1800*1000){
					$.util.warning('操作提示', '托运时间不能大于系统当前时间');
					return false;
				}
				if(rate&&rate>1){
					$.util.warning('操作提示', '保率不能大于1');
					return false;
				}else if(!daozhan || daozhan=='' || daozhan.length==0){
					$.util.warning('操作提示', '请选择到站');
					return false;
				}else if(hasFinance==1){
					if($('.cdc').is(':checked')){
						if(!catchgoodsfee || catchgoodsfee==''){
							$('.catch-goods').focus();
							$.util.warning('操作提示', '服务方式是仓到仓，上门取货费必填');
							return false;
						}
						if(!sendgoodsfee || sendgoodsfee==''){
							$.util.warning('操作提示', '服务方式是仓到仓，送货上门费必填');
							$('.send-goods').focus();
							return false;
						}
					}else if($('.cdz').is(':checked')){
						if(!catchgoodsfee || catchgoodsfee==''){
							$('.catch-goods').focus();
							$.util.warning('操作提示', '服务方式是仓到站，上门取货费必填');
							return false;
						}
					}else if($('.zdc').is(':checked')){//服务方式是站到仓
						if(!sendgoodsfee || sendgoodsfee==''){
							$.util.warning('操作提示', '服务方式是站到仓，送货上门费必填');
							$('.send-goods').focus();
							return false;
						}
					}
					var waybill_dsk = $("#waybill_dsk").val();
					var finance_dsk = $("#finance_dsk").val();
					if (waybill_dsk== null || waybill_dsk==''){
						if (finance_dsk >0){
							$("#waybill_dsk").val($("#finance_dsk").val());
							$.util.warning('操作提示', '代收款将会被重置成一致!');
							return false;
						}
					}else if(finance_dsk== null || finance_dsk==''){
						if (waybill_dsk >0){
						$("#finance_dsk").val($("#waybill_dsk").val());
						$.util.warning('操作提示', '代收款将会被重置成一致!');
						return false;
						}
					}else if (waybill_dsk >0 && finance_dsk>0){
						if(Number(waybill_dsk)!=Number(finance_dsk)){
							$("#finance_dsk").val($("#waybill_dsk").val());
							$.util.warning('操作提示', '代收款将会被重置成一致!');
							return false;
						}
					}
//					var curTime = new Date();
//					var fhshj = new Date(Date.parse($("#fhshj").val()));
//					 
//					if(curTime<fhshj){
//						$.util.warning('操作提示', '托运时间不能大于当前系统时间');
//					}

					/*if(waybill_dsk!=finance_dsk){
						$.util.warning('操作提示', '财凭的代收款必须和运单的代收款请保持一致！');
						$("#finance_dsk").val($("#waybill_dsk").val());
						return false;
					}*/
					
				}	
				$("#form").submit();
				$("#loading").show();
			}
		});
		
		
		//小数位校验
		jQuery.validator.addMethod("check_decimal_point", function(value){
			if(value.toString().indexOf(".")!=-1&&value.split(".")[1].length>3){
				return false;
			}else{
				return true;
			}
		}, '');
		//运单的校验:10位或者12位
		jQuery.validator.addMethod("check_ydbhid", function(value){
			 var regex = /(^\d{10}$)|(^\d{12}$)/; //正规表达式对象
			 if(regex.test(value)){	
				 return true; 
			 }else{
				 return false;
			 }
		}, '');
		jQuery.validator.addMethod("check_length", function(value){
			 if(value.length<9){	
				 return true; 
			 }else{
				 return false;
			 }
		},''); 
		jQuery.validator.addMethod("check_in_dzshhd", function(value){
			if(value!=""){
			    if($.inArray(value, dzshhdList)==-1){
			    	return false;
			    }else {
			    	return true;
			    }
			}else {
				return true;
			}
		}, '');
		
		//表单验证
		$("form").validate({
			rules: {
				'order.ydbhid': { required: true, check_ydbhid:true },//运单号
				'order.fhshj': { date:true},//托运时间
				'order.fazhan': { required: true},//始发站
				'order.daozhan': { required: true},//到站
				//'order.dzshhd':{check_in_dzshhd: true},
				'certify.otherFeeName' :{check_length:true},
				'order.beginPlacename': { required: true},//始发地
				'order.endPlacename': { required: true},//目的地
				'order.fhdwmch': { required: true},//客户名称
				//'order.khbm': { required: true},//客户编码
				'order.fhdwdzh': { required: true},//客户地址
				'order.fhdwyb': { number: true, minlength: 11, mobile:true},//客户手机号
				'order.shhrmch': { required: true,maxlength:20},//收货人名称
				'order.shhryb': { required: false, number: true, minlength: 11, mobile:true },//收货人手机
				'shrProvinces': { required: true,maxlength:10},//收货人省市区
				'order.shhrdzh': { required: true},//收货人地址
				'order.isfd': { required: true},//是否返单
				'order.fzfk': { required: function(){
						return !$("input[name='order.dzfk']").is(":checked");
					}
				},//付费方式
				'order.dzfk': { required: function(){
						return !$("input[name='order.fzfk']").is(":checked");
					}
				},
				'order.ysfs': { required: true},//运输方式
				'order.fwfs': { required: true},//服务方式
				'order.zyfs': { required: true},//作业方式
				'order.daodatianshu': { digits:true, min: 0, max:30},//运输天数
				'order.hyy': { maxlength:4},//承运人
				'certify.cost': { number: true, min: 0, required: true},//合计费用
				'certify.tohome': { number: true, min: 0},//上门取货费
				'certify.invoice': { number: true, min: 0},//办单费
				'certify.receipt': { number: true, min: 0},//代收贷款
				'certify.piecework': { number: true, min: 0},//按件运价
				'certify.lightprice': { number: true, min: 0},//轻货运价
				'certify.weightprice': { number: true, min: 0},//重货运价
				'certify.delivery': { number: true, min: 0},//送货上门费
				'certify.other': { number: true, min: 0 , required: false},//其他费用
				'certify.invoice': { number: true, min: 0},//办单费 
				'certify.zhuangxiefei': { number: true, min: 0},//装卸费
				'certify.premiumFee': { number: true, min: 0},//保险费
				//'order.baozhuangfei': { number: true, min: 0},//包装费
				'order.hwzht' : { maxlength : 50},//包装说明
				'order.tiebieshuoming' : { maxlength : 100},//特别说明
				'order.fdyq' : { maxlength : 50},//返单要求
				'order.tyrqm' : { maxlength : 4},//托运人签名
				'order.hyy' : { maxlength : 4}//承运人签名
			},
			errorPlacement:function(errors, element){
				if($(element).is("input[type='checkbox']") || $(element).is("input[type='radio']")){
					$(element).parent().parent().addClass("error");
				}
			},
			success:function(errors, element){
				if($(element).is("input[type='checkbox']") || $(element).is("input[type='radio']")){
					$(element).parent().parent().removeClass("error");
				}
			}
		});

		jQuery.validator.addMethod("mobile", function(value, element,params) { 
			var length = value.length; 
			var mobile = /^1\d{10}$/ ;
			return this.optional(element) || (length == 11 && mobile.test(value)); 
		}, "手机号码格式错误");
	}
} 