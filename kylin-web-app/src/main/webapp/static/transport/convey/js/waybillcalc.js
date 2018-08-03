var mgr = {
	rules: function(object, select){
		var tr = $(object).parent().parent();
		if(!select){
			select = $(tr).find("select");
		}
		//var v = Number($(select).val());
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
					$(this).rules("add", {number: true, min: 0, required: true});
					mgr.rules(this);
				}
				if(mgr.rexp('zhl', nameValue)){
					mgr.sum(this, "#zhlHj");
					$(this).rules("add", {number: true, min: 0,check_decimal_point:true, required: true});
					mgr.rules(this);
				}
				if(mgr.rexp('tiji', nameValue)){
					mgr.sum(this, "#tijiHj");
					$(this).rules("add", {number: true, min: 0,check_decimal_point:true, required: true});
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
			});
		});
	}	
}


$(function(){
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
	//合计input获得焦点时计算合计费用
	$(document).on("change",".txt,.edit-input",function(o){
		if(!o.target.name || o.target.name == 'certify.cost'){
			return false;
		}
		var something = {
			weightprice : $("input[name='certify.weightprice']").val(),//重货计费
			lightprice : $("input[name='certify.lightprice']").val(),//轻货计费
			piecework : $("input[name='certify.piecework']").val(),//按件计费
			receipt : $("input[name='certify.receipt']").val(),//代收款
			invoice : $("input[name='certify.invoice']").val(),//办单费
			tohome : $("input[name='certify.tohome']").val(),//上门取货费用
			delivery : $("input[name='certify.delivery']").val(),//送货上门费
			other : $("input[name='certify.other']").val(),//其他费用
			baoxianfei : $("input[name='certify.premiumFee']").val()//保险费
			//baozhuangfei : $("input[name='order.baozhuangfei']").val(),//包装费 
			//zhuangxiefei : $("input[name='certify.zhuangxiefei']").val(),//装卸费
			//bandanfei : $("input[name='certify.invoice']").val()//办单费
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
//		
//		var fwfs = -1;//服务方式
//		$("input[name='order.fwfs']").each(function(i,a){
//			if($(a).is(':checked')){
//				fwfs = i;
//			}
//		})
//		if(fwfs>=0){
//			//站到仓，站到站 上门收货费=0
//			if(fwfs==2 || fwfs==3){
//				something.tohome = 0;
//			}
//			//仓到站，站到站送货上门费=0
//			if(fwfs==0 || fwfs==3){
//				something.delivery = 0;
//			}
//		}
		
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
	});
	if(!$('.datetime-picker').val()){
		$('.datetime-picker').val(moment().format('YYYY-MM-DD HH:mm'));
	}
	
	//日期选择空间
	$('.datetime-picker').each(function(i,val) { 
		$(this).flatpickr({ maxDate: new Date(), minDate:'1970-01-01 00:00', enableTime: true, enableSeconds: false });
	});
	mgr.bindEvent();
	
});


