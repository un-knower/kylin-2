
	function rename(classname,flag){
		var count = 0;
		$("."+classname).each(function(){
			$(this).attr("name",'details['+(count)+'].'+classname);
			count+=1;
			if(flag) $(this).val(count);
		});
	}
	
	function checkXuhao(){
		rename('ydxzh',true);
		rename('xh',false);
		rename('pinming',false);
		rename('jianshu',false);
		rename('bzh',false);
		rename('zhl',false);
		rename('tiji',false);
		rename('tbje',false);
		rename('zchl',false);
		rename('zzxl',false);
		rename('qchl',false);
		rename('qzxl',false);
		rename('jffs',false);
		rename('yunjia',false);
	}
	
var mgr = {
	rules: function(object, select){
		var tr = $(object).parent().parent();
		if(!select){
			select = $(tr).find("select");
		}
		var v = Number($(select).val());
		$(tr).find("input").each(function(){
			var nameValue = $(this).attr("name");
			if(mgr.rexp('jianshu', nameValue)){
				//if(v == 2){
					$(this).rules("add", {required: true});
				//}else{
				//	$(this).rules("add", {required: false});
				//}
			}
			if(mgr.rexp('zhl', nameValue)){
				//if(v == 0){
					$(this).rules("add", {required: true});
				//}else{
				//	$(this).rules("add", {required: false});
				//}
			}
			if(mgr.rexp('tiji', nameValue)){
				//if(v == 1){
					$(this).rules("add", {required: true});
				//}else{
				//	$(this).rules("add", {required: false});
				//}
			}
		});
		//$(object).rules("add", {number: true, min: 0, required: true});
	},
	sum:function(object, hjinput){
		$(object).on("change", function(){
			var cname = $(this).attr("name"), sumValue = 0;
			var cnameArr = cname.split('.');
			var nameArr = new  Array();
			$("#details-tbody tr").each(function(){
				$(this).find("input").each(function(){
					nameArr = $(this).attr("name").split('.');
					if(cnameArr[1] == nameArr[1]){
						var $val = Number($(this).val())*10000;
						sumValue = (sumValue*10000 + $val)/10000;
					}
				});
			});
			if(sumValue || sumValue==0){
				$(hjinput).val(sumValue);
			}else{
				$(hjinput).val();
			}
		});
	},
	rexp:function(name, tname){
		eval("var regx = /^details\\[\\d\\]\\." +name + "$/;");
		return regx.test(tname);
	},
	bindEvent:function(){
		$("#details-tbody tr").each(function(){
			$(this).find("input, select").each(function(){
				var nameValue = $(this).attr("name");
				//alert(nameValue)
				//货物细则合计
				if(mgr.rexp('jianshu', nameValue)){
					mgr.sum(this, "#jianshuHj");
					$(this).rules("add", {number: true, min: 0});
					mgr.rules(this);
				}
				if(mgr.rexp('zhl', nameValue)){
					mgr.sum(this, "#zhlHj");
					$(this).rules("add", {number: true, min: 0,check_decimal_point:true});
					mgr.rules(this);
				}
				if(mgr.rexp('tiji', nameValue)){
					mgr.sum(this, "#tijiHj");
					$(this).rules("add", {number: true, min: 0,check_decimal_point:true});
					mgr.rules(this);
				}
				if(mgr.rexp('tbje', nameValue)){
					mgr.sum(this, "#tbjeHj");
					$(this).rules("add", {number: true, min: 0,check_decimal_point:true});
				}
				if(mgr.rexp('zzxl', nameValue)){
					mgr.sum(this, "#zzxlHj");
					$(this).rules("add", {number: true, min: 0,check_decimal_point:true});
				}
				if(mgr.rexp('qzxl', nameValue)){
					mgr.sum(this, "#qzxlHj");
					$(this).rules("add", {number: true, min: 0,check_decimal_point:true});
				}
				if(mgr.rexp('zchl', nameValue)){
					mgr.sum(this, "#zchlHj");
					$(this).rules("add", {number: true, min: 0,check_decimal_point:true});
				}
				if(mgr.rexp('qchl', nameValue)){
					mgr.sum(this, "#qchlHj");
					$(this).rules("add", {number: true, min: 0,check_decimal_point:true});
				}
				if(mgr.rexp('pinming', nameValue)){
					$(this).rules("add", {required: true});
				}
				if(mgr.rexp('tbje', nameValue)){
					$(this).rules("add", {number: true, min: 0});
				}
				if(mgr.rexp('qzxl', nameValue)){
					$(this).rules("add", {number: true, min: 0});
				}
				if(mgr.rexp('zzxl', nameValue)){
					$(this).rules("add", {number: true, min: 0});
				}
				if(mgr.rexp('yunjia', nameValue)){
					$(this).rules("add", {number: true, min: 0});
				}
				if(mgr.rexp('qchl', nameValue)){
					$(this).rules("add", {number: true, min: 0});
				}
				if(mgr.rexp('zchl', nameValue)){
					$(this).rules("add", {number: true, min: 0});
				}
//				if(mgr.rexp('jffs', nameValue)){
//					var select = this;
//					$(this).on("change", function(){
//						$(this).parent().parent().find("input").each(function(){
//							if(mgr.rexp('jianshu', $(this).attr("name"))){
//								mgr.rules(this, select);
//							}
//							if(mgr.rexp('zhl', $(this).attr("name"))){
//								mgr.rules(this, select);
//							}
//							if(mgr.rexp('tiji', $(this).attr("name"))){
//								mgr.rules(this, select);
//							}
//						});
//					});
//				}
			});
		});
	}	
}


$(function(){
	var trIndex = -2;
	if(hejijine && hejijine*1>0){
		setTimeout("getValue()",1500);
	}
	//插件按钮调用
	//$('.checkBox').hcheckbox();
	//$('.radioBox').hradio();

	//点击按钮添加
	$('.handle-add').on('click',function(){
		$('.modify-status').val('1');
		creatLi();
	})
	//点击图标添加
	$(".addTr").on('click',function(){
		$('.modify-status').val('1');
		creatLi();
	})
	//点击添加背景色
	$("#details-tbody").delegate('.edit-input','click',function(ev){
		var oTr = $(this).parent().parent();
		trIndex = $(this).closest("tr").index();
		oTr.css('backgroundColor','#ddecff');
		oTr.siblings().css('backgroundColor','#fff');
	})
	
	//点击别处取消背景色
	$(document).click(function(ev){
		var target1 = $("#details-tbody");
		var target2 = $(".handle-delete");
		if(!target1.is(ev.target) && target1.has(ev.target).length === 0){
			target1.children().css('backgroundColor','#fff');
			$(".handle-delete").css('background-color','#ccc');
		}else{
			$(".handle-delete").css('background-color','#FF0000');
		}
	})
	
	//点击按钮删除
	$(".handle-delete").on('click',function(){
		$('.modify-status').val('1');
		if(trIndex != -2){
			if($("#details-tbody tr").length < 2){
				$.util.warning('操作提示', '至少保留一项数据');
			}else{
				$("#details-tbody tr").eq(trIndex).remove();
				console.log($("#details-tbody tr").eq(trIndex));
				$('#details-tbody>tr').each(function(index,item){
					$(item.children[0]).find("span").html(index + 1);
				})
				//删除完，让trIndex = -2，这样必须得再次选中，才能删除，否则，会一直删除
				trIndex = -2;
			}
		}
		checkXuhao();
	})
	//input,select值改变时，说明，是不想删除的，只是想编辑，所以，操作完成后让trIndex = -2;
	//$("#details-tbody").delegate('.edit-input','input',function(){
	//	trIndex = -2;
	//})
	
	
	//小数位校验
	jQuery.validator.addMethod("check_decimal_point", function(value){
		if(value.toString().indexOf(".")!=-1&&value.split(".")[1].length>4){
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
	}, 
	'');
	jQuery.validator.addMethod("check_in_dzshhd", function(value){
		if(value!=""){
		    if($.inArray(value, dzshhdList)==-1){
		    	return false;
		    }else {
		    	return true;
		    }
		}else {
			if(clickNum > 0){
				return false;
			}else{
				return true;
			}
		}
	}, '');
	jQuery.validator.addMethod("isrequired", function(value){
		if(value == "" || value == null){
			if(monneyClick > 0){
				return false;
			}else{
				return true;
			}
		}
	}, '');
	//表单验证
	$("#form").validate({
		rules: {
			/*'order.ydbhid': { required: true, check_ydbhid:true },//运单号*/
			'order.fhshj': { date:true},//托运时间
			'order.fazhan': { required: true},//始发站
			'order.daozhan': { required: true},//到站
			//'order.dzshhd':{check_in_dzshhd: true},//到站网点
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
			'certify.cost': { number: true, min: 0},//合计费用
			'certify.tohome': { number: true, min: 0},//上门取货费
			'certify.invoice': { number: true, min: 0},//办单费
			'certify.receipt': { number: true, min: 0},//代收贷款
			'certify.piecework': { number: true, min: 0},//按件运价
			'certify.lightprice': { number: true, min: 0},//轻货运价
			'certify.weightprice': { number: true, min: 0},//重货运价
			'certify.delivery': { number: true, min: 0},//送货上门费
			'certify.other': { number: true, min: 0 },//其他费用
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
	
	//设置所有的select选中值
	$("select").each(function(i){
		if($(this).attr("value")){
			$(this).val($(this).attr("value"));
		}
	});
	//设置所有的单选多选选中值
	$("input:radio,input:checkbox").each(function(i){
		if($(this).val() == $(this).attr("real")){
			$(this).attr("checked",'checked');
		}
	});
	//清空
	$("#clearall").click(function() {
		$.util.confirm("操作提示","是否确认清空所有内容？", function check(){
            if(1){
            	location.replace(location.href);
            }
        } );
	});
	
	//合计input获得焦点时计算合计费用
	$(document).on("change",".txt,.edit-input",function(o){
		if(!o.target.name || o.target.name == 'certify.cost'){
			return false;
		}
		getValue();
	});
	if(!$('.datetime-picker').val()){
		$('.datetime-picker').val(moment().format('YYYY-MM-DD'));
	}
	
	/**
	 * 功能描述：点击提交表单
	 * 作者：yanxf 
	 * 时间：2017-05-05
	 */
	$(".btn-submit").on("click",function(){
		
		var waybill_dsk = $("#daishou").val();
		var finance_dsk = $("#daishou-hidden").val();
		if (waybill_dsk== null || waybill_dsk==''){
			if (finance_dsk >0){
				$("#daishou").val($("#daishou-hidden").val());
				
				$.util.warning('操作提示', '代收款将会被重置成一致!');
				return false;
			}
		}else if(finance_dsk== null || finance_dsk==''){
			if (waybill_dsk >0){
			$("#daishou-hidden").val($("#daishou").val());
			$.util.warning('操作提示', '代收款将会被重置成一致!');
			return false;
			}
		}else if (waybill_dsk >0 && finance_dsk>0){
			if(Number(waybill_dsk)!=Number(finance_dsk)){
				$("#daishou-hidden").val($("#daishou").val());
				$.util.warning('操作提示', '代收款将会被重置成一致!');
				return false;
			}
		}
		var rate = $("input[name='price.premiumRate']").val();
		if(rate&&rate>1){
			$.util.warning('操作提示', '保率不能大于1');
			return false;
		}
		var catchgoodsfee = $('.catch-goods').val();
		var sendgoodsfee = $('.send-goods').val();
		var modifyCertify = $('#modifyCertify').val();
		if(modifyCertify==1){
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
			var modify_order= $('#modify_order').val();
			if(modify_order*1==0){//如果补录财务凭证时，不修改运单，那么去掉校验的class名称：form
				$(".part:eq(0) .must").each(function(){
					$(this).siblings("input").rules("remove");
				})
			}else{
			}
		}
		$("#form").find("input:hidden[name='saveLogo']").val($(this).attr("value"));
		$('.select-box').removeAttr("disabled");
		$.when($("#form").submit()).then(function(){
			if($('.error').length == 0){
				$('.select-box').removeAttr("disabled");
			}else{
				$('.select-box').attr("disabled",'true');
			}
		});
		/*$("#loading").show();*/
	});
	mgr.bindEvent();

	//点击图标删除
	$('#details-tbody').delegate('.removeTr','click',function(){
		$('.modify-status').val('1');
		if($("#details-tbody tr").length < 2){
			$.util.warning('操作提示', '至少保留一项数据');
		}else{
			var tr = $(this).parent().parent();
			tr.remove();
			$('#details-tbody>tr').each(function(index,item){
				$(item.children[0]).find("span").html(index + 1);
			})
		}
		checkXuhao();
	})
});
function creatLi(){
	var trL = parseInt($('#details-tbody > tr').length);
	var pinming = "";
	var MaxxizeId = 0;
	$('.xizeId').each(function(index,elem){
		var inputValue = $(elem).val();
		if(MaxxizeId <= inputValue){
			MaxxizeId = inputValue;
		}
	})
	MaxxizeId ++;
	console.log(MaxxizeId);
	if(pinmingList){
		pinming +=	'<td><input type="text" class="edit-input pinming" name="details['+MaxxizeId+'].pinming"/></td>';
	}else{
		pinming +=	'<td><input type="text" class="edit-input pinming" name="details['+MaxxizeId+'].pinming"/></td>';
	}
	var str = '<tr>'+										
				'<td><input type="text" readonly="readonly" class="ydxzh" value="'+(trL+1)+'" name="details['+(trL+1)+'].ydxzh"/></td>'+
				pinming+
				'<td><input type="text" class="edit-input xh" name="details['+(trL+1)+'].xh"/></td>'+
				'<td><input type="text" class="edit-input jianshu" name="details['+(trL+1)+'].jianshu"/></td>'+
				'<td><input type="text" class="edit-input bzh" name="details['+(trL+1)+'].bzh"/></td>'+
				'<td><input type="text" class="edit-input zhl" name="details['+(trL+1)+'].zhl"/></td>'+
				'<td><input type="text" class="edit-input tiji" name="details['+(trL+1)+'].tiji"/></td>'+
				'<td><input type="text" class="edit-input tbje" name="details['+(trL+1)+'].tbje"/></td>'+
				'<td><input type="text" class="edit-input zchl" name="details['+(trL+1)+'].zchl"/></td>'+
				'<td><input type="text" class="edit-input zzxl" name="details['+(trL+1)+'].zzxl"/></td>'+
				'<td><input type="text" class="edit-input qchl" name="details['+(trL+1)+'].qchl"/></td>'+
				'<td><input type="text" class="edit-input qzxl" name="details['+(trL+1)+'].qzxl"/></td>'+
				'<td>'+
					'<select class="edit-input table-select jffs" name="details['+(trL+1)+'].jffs">'+
						'<option value="0">重货</option>'+
						'<option value="1">轻货</option>'+
						'<option value="2">按件</option>'+
					'</select>'+
				'</td>'+
				'<td><input type="text" class="edit-input yunjia" name="details['+(trL+1)+'].yunjia"/></td>'+
				'<td class="noBorder"><a href="javascript:;" class="removeTr"></a></td>'
			'</tr>';
	$(str).appendTo($('#details-tbody'));
	mgr.bindEvent();
	forEachinput ();
	checkXuhao();
}


function forEachinput(){
	if(companyName && companyName.indexOf("四川远成")!=-1){
		$(".pinming").easyAutocomplete({
			minCharNumber: 1,//至少需要1个字符
			url: function(phrase) {
				return base_url + "/transport/adjunct/pinming?something=" + encodeURI(encodeURI(phrase));
			},
			getValue: function(element) {
				return element;
			}
		});
	}
}
/*自动计算*/
function getValue(){
	var something = {
			weightprice : $("input[name='certify.weightprice']").val(),//重货计费
			lightprice : $("input[name='certify.lightprice']").val(),//轻货计费
			piecework : $("input[name='certify.piecework']").val(),//按件计费
			receipt : $("input[name='certify.receipt']").val(),//代收款
			invoice : $("input[name='certify.invoice']").val(),//办单费
			tohome : $("input[name='certify.tohome']").val(),//上门取货费用
			delivery : $("input[name='certify.delivery']").val(),//送货上门费
			other : $("input[name='certify.other']").val(),//其他费用
			baoxianfei : $("input[name='certify.premiumFee']").val(),//保险费
			//baozhuangfei : $("input[name='order.baozhuangfei']").val(),//包装费 
			zhuangxiefei : $("input[name='certify.zhuangxiefei']").val(),//装卸费
			//bandanfei : $("input[name='order.bandanfei']").val()//办单费
		};
	var items = [];
	$("#details-tbody tr").each(function(){
		var item = {};
		$(this).find("input,select").each(function(){
			var nameValue = $(this).attr("name");
			if(mgr.rexp('jianshu', nameValue)){
				item.jianshu = $(this).val();
			}else if(mgr.rexp('zhl', nameValue)){
				item.zhl= $(this).val();
			}else if(mgr.rexp('tiji', nameValue)){
				item.tiji= $(this).val();
			}else if(mgr.rexp('jffs', nameValue)){
				item.jffs = $(this).val();
			}else if(mgr.rexp('tbje', nameValue)){
				item.tbje = $(this).val();
			}else if(mgr.rexp('zzxl', nameValue)){
				item.zzxl = $(this).val();
			}else if(mgr.rexp('qzxl', nameValue)){
				item.qzxl = $(this).val();
			}else if(mgr.rexp('qchl', nameValue)){
				item.qchl = $(this).val();
			}else if(mgr.rexp('zchl', nameValue)){
				item.zchl = $(this).val();
			}
			
			/*
			if(gegExp.compile('details\\[\\d+\\]\\.jianshu').test(nameValue)){
				item.jianshu = $(this).val();
			}else if(gegExp.compile('details\\[\\d+\\]\\.zhl').test(nameValue)){
				item.zhl= $(this).val();
			}else if(gegExp.compile('details\\[\\d+\\]\\.tiji').test(nameValue)){
				item.tiji= $(this).val();
			}else if(gegExp.compile('details\\[\\d+\\]\\.jffs').test(nameValue)){
				item.jffs = $(this).val();
			}
			*/
		});
		items.push(item);
	});
	
		var baoxianfei = 0.0;
		var baolv = $("input[name='price.premiumRate']").val();//保率
		var lightLoadPrice = $("input[name='price.qhzhxfdj']").val();//轻货装卸单价
		var heavyLoadPrice = $("input[name='price.zhzhxfdj']").val();//重货装卸单价
		var forkliftPrice = $("input[name='price.zhjxzyf']").val();//叉车费单价
		var jshhj = $("input:radio[name='order.jshhj']:checked").val();//取整
		var loadfeeTotal = 0.0;
		var forklift = 0.0;//叉车费
		var cost = 0.0;//合计费用（总）
		var transCost = 0.0;//运费合计
		var ydly = 0;//这个字段在hwyd表里，不清楚什么意思，一般为0或null，也有可能是2
		var calcSumTransportFees = 0;
		var baojiajine = 0.0;
		$.each(items, function(n, v){
			if(v.tbje){
				baojiajine += Number(v.tbje);
			}
			//运费合计
			transCost += totalCostForFinance.calcTotalTransport(Number(jshhj),Number(v.jffs),Number(v.zhl),Number(something.weightprice),Number(v.tiji),Number(something.lightprice),Number(something.piecework),Number(v.jianshu));	
			if(v.qzxl && lightLoadPrice) loadfeeTotal += totalCostForFinance.calcLightLoadingFee(Number(jshhj),Number(lightLoadPrice),Number(v.qzxl));//轻装卸费
			if(v.zzxl && heavyLoadPrice) loadfeeTotal += totalCostForFinance.calcHeavyLoadingFee(Number(jshhj),Number(heavyLoadPrice),Number(v.zzxl));//重装卸费
			if(v.qchl) forklift += totalCostForFinance.calcForkliftCharge(Number(jshhj),Number(forkliftPrice),0,Number(v.qchl));//叉车费
			if(v.zchl) forklift += totalCostForFinance.calcForkliftCharge(Number(jshhj),Number(forkliftPrice),Number(v.zchl),0);//叉车费
        });
		if(baolv) baoxianfei += totalCostForFinance.calcInsuranceExpense(ydly,Number(baolv),Number(baojiajine),Number(jshhj)); //保险费=保价金额*保率
		$("input[name='certify.forkliftFee']").val(forklift);
		$("input[name='certify.premiumFee']").val(baoxianfei.toFixed(2));//保险费显示
		$("input[name='certify.zhuangxiefei']").val(loadfeeTotal);//装卸费显示
		calcSumTransportFees = totalCostForFinance.calcSumTransportFees(Number(jshhj),forklift,loadfeeTotal,baoxianfei,Number(something.delivery),Number(something.tohome),Number(something.other),transCost,Number(something.invoice));//运杂费合计
		cost = totalCostForFinance.calcTransportationAcceptanceSheet(calcSumTransportFees,Number(something.receipt));
		$("input[name='certify.cost']").val(cost.toFixed(2));
		$("input[name='certify.transportTotalFee']").val(transCost.toFixed(2));
}