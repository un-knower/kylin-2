//操作对象
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
	waixianNameFocusId:"#wxName",//外线名称focusId
	waixianContactFocusId:"#wxConName",//外线联系人focusId（自动填充）
	waixianTelFocusId:"#wxTel",////外线联系电话focusId（自动填充）
	waixianDataUrl:base_url+ "/transport/adjunct/foreignRoute?foreignName=",//外线名称下拉提示ajax URL
	
	//车牌号
	chepaihaoFocusId:"#cxh",//车牌号
	driverNameFocusId:"#driverName",//司机姓名（自动填充）
	driverTelFocusId:"#driverTel",////司机电话（自动填充）
	chepaihaoDataUrl:base_url+ "/transport/adjunct/vehicleInfo?chxh=",//外线名称下拉提示ajax URL
	
	//中转站
	transferStationFocusId:"#transfer_station",
	transferStationDataUrl:base_url+ "/transport/adjunct/daozhan?daozhan=",//外线名称下拉提示ajax URL
	
	//中转网点
	transferStationPositionFocusId:"#transfer_station_point",
	transferStationPositionDataUrl:base_url+ "/transport/adjunct/latticePoint",//外线名称下拉提示ajax URL
	
	//运单号(原外接单号)
	waijiedanhaoFocusId:"#waijiedanhao",//外接单号
	waijiedanhaoDataUrl:base_url+ "/transport/bundle/manage/conveyKey/",
	
	
	
	insert : function(item) {
		if (item.ydbhid) {
			if ($("#tbody input[value='" + item.ydbhid + "']").length > 0) {
				$.util.danger('操作提示', '运单编号' + item.ydbhid + '不能重复添加');
			} else {	
				var iType = item.iType;
				if(iType){//装载过
					var currPageType = $("input:radio[name='"+mgr.loadmode+"']:checked").val();
					if(currPageType==mgr.loadmode_pick && iType == mgr.loadmode_pick){//已提货不能做提货
						$.util.danger('操作提示', '该运单已提货，请重新选择运单');
						return;
					}else if(currPageType==mgr.loadmode_transport && iType == mgr.loadmode_transport){//已配送不能做配送
						$.util.danger('操作提示', '该运单已配送，请重新选择运单');
						return;
					}else if(currPageType==mgr.loadmode_pick && iType == mgr.loadmode_transfer){//已中转不能做提货
						$.util.danger('操作提示', '该运单已中转，请重新选择运单');
						return;
					}else if(currPageType==mgr.loadmode_pick && iType == mgr.loadmode_transport){//已配送的不能做提货
						$.util.danger('操作提示', '该运单已配送，请重新选择运单');
						return;
					}
				}else{
					$.util.danger('操作提示', '该运单是历史运单，不能装载');
					return;
				}
				var index = $('.index').length;
				var listSize = item.detailList.length;
				var appendTr = '<tr class="together_yundan_'+(index+1)+'">'
				+ '<td rowspan="'+(listSize+1)+'" class="index">'+ (index+1)+'</td>'
				+ '<td rowspan="'+(listSize+1)+'"><input type="text" name="ydbhids" class="edit-input" readonly="readonly" value="'+ item.ydbhid+ '"/></td>'
				+ '<td rowspan="'+(listSize+1)+'"><input type="text" class="edit-input" readonly="readonly" value="'+ item.fhdwdzh+ '"/></td>'
				+ '<td rowspan="'+(listSize+1)+'"><input type="text" class="edit-input" readonly="readonly" value="'+ item.endPlacename+ '"/></td>'
				+ '<td rowspan="'+(listSize+1)+'"><input type="text" class="edit-input" readonly="readonly" value="'+ item.fhdwmch+ '"/></td>'
				+ '<td></td><td></td><td></td><td></td><td rowspan="'+(listSize+1)+'"><a href="javascript:;" class="removeTr"></a></td></tr>';
				for(var i=0;i<listSize;i++){
					var zhl = typeof(item.detailList[i].zhl)=="undefined"?0:item.detailList[i].zhl;
					var js = typeof(item.detailList[i].jianshu)=="undefined"?0:item.detailList[i].jianshu;
					var tj = typeof(item.detailList[i].tiji)=="undefined"?0:item.detailList[i].tiji;
					appendTr += '<tr class="together_yundan_'+(index+1)+'">';
					appendTr += '<td><input type="text" class="edit-input" readonly="readonly" value="'+ item.detailList[i].pinming+ '"/></td>';
					appendTr += '<td><input type="text" class="edit-input" name="zhongliang_name" readonly="readonly" value="'+ zhl+ '"/></td>';
					appendTr += '<td><input type="text" class="edit-input" name="jianshu_name" readonly="readonly" value="'+ js+ '"/></td>';
					appendTr += '<td><input type="text" class="edit-input" name="tiji_name" readonly="readonly" value="'+ tj+ '"/></td>';
					appendTr += '</tr>';
				}
				$(appendTr).appendTo($('#tbody'));
				mgr.sumTotal();//合计
			}
		}
	},
	sumTotal : function() {
		var index = $('tr[class^="together_yundan_"]').length;
		if (index == undefined || index == null) {
			index = 0;
		}
		var sumJianshu = 0;
		var sumZhongliang = 0;
		var sumTiji = 0;
		var needSum = false;
		$("input[name='zhongliang_name']").each(function() {
			if(typeof($(this).val()) != "undefined"){
				sumZhongliang = $(this).val()*1+sumZhongliang*1;
			}
			needSum = true;
		});
		$("input[name='jianshu_name']").each(function() {
			if(typeof($(this).val()) != "undefined"){
				sumJianshu = $(this).val()*1+sumJianshu*1;
			}
			needSum = true;
		});
		$("input[name='tiji_name']").each(function() {
			if(typeof($(this).val()) != "undefined"){
				sumTiji = $(this).val()*1+sumTiji*1;
			}
			needSum = true;
		});
		$("#heji").remove();
		if(needSum){
			$('<tr id="heji"><td colspan="5" style="text-align:right;">合计：</td><td>/</td>'
					+ '<td><input type="text" class="edit-input" readonly="readonly" value="'
					+ sumZhongliang.toFixed(3)
					+ '"/></td>'
					+ '<td><input type="text" class="edit-input" readonly="readonly" value="'
					+ sumJianshu
					+ '"/></td>'
					+ '<td><input type="text" class="edit-input" readonly="readonly" value="'
					+ sumTiji.toFixed(3)
					+ '"/></td>'
					+ '<td><input type="text" class="edit-input" readonly="readonly" value="/"/></td>'
					+ '</tr>').appendTo($('#tbody'));
		}
	},
	modifyRule : function() {
		var waixian = $("#waixian").is(':checked');// 外车是否选中
		var lingdan = $("#lingdan").is(':checked');// 零担是否选中
		if (!waixian) {
			$("#xiao").find("input").each(function() {
				$(this).rules("remove");
			});
		} else {
			$("#xiao").find("input").each(function() {
				/*$(this).rules("add", {
					required : true
				});*/
				$("#wxName").rules("add", {required : true});
				$("#wxConName").rules("add", {required : true});
				$("#wxId").rules("add", {required : true});
				$("#wxTel").rules("add", {required : true , IsMobilePhoneNumber : true});
			});
		}
		if (lingdan) {
			$("#cheliang").find("input").each(function() {
				$(this).rules("remove");
			});
		} else {
			$("#cheliang").find("input").each(function() {
				$(this).rules("add", {
					required : true
				});
//				$("#cxh") .rules("add", {required : true});
//				$("#driverTel").rules("add", {IsMobilePhoneNumber : true});
			});
		}
	},
	zhongzhuanOnChange :function(){
		var type = $("input:radio[name='"+mgr.istransfer+"']:checked").val();
		if(mgr.is_transfer_status==type){//中转
			$("#yunshumudidi").css("display","none");
			$("#yunshuqishidi").css("display","none");
			$("#zhongzhuanwangdian").css("display","block");
			$("#zhongzhuanzhan").css("display","block");
		}else{//不中转
			$("#yunshumudidi").css("display","block");
			$("#yunshuqishidi").css("display","block");
			$("#zhongzhuanwangdian").css("display","none");
			$("#zhongzhuanzhan").css("display","none");
		}
	},
	resumeLoadmode:function(){
		if(mgr.curr_mode_type==mgr.loadmode_pick){//提
			$("#tihuo").prop("checked",true);
		}else if(mgr.curr_mode_type==mgr.loadmode_transfer){//干
			$("#ganxian").prop("checked",true);
		}else if(mgr.curr_mode_type==mgr.loadmode_transport){//配
			$("#peisong").prop("checked",true);
		}
		$.util.warning('操作提示','运单表中加载信息后不能更改装载方式！');
	}
};

//列表默认加载和事件绑定
$(document).ready(function() { 
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
	
	var daozhan = "";
	var zhzhuanArr = new Array();
	//3.中转站输入时自动下拉提示
	$(mgr.transferStationFocusId).easyAutocomplete({
		minCharNumber : 1,// 至少需要1个字符
		url : function(phrase) {
			return mgr.transferStationDataUrl+encodeURI(encodeURI(phrase));
		},
		getValue : function(element) {
			zhzhuanArr.push(element);
			return element;
		},
		list : {
			onSelectItemEvent : function() {
				var item = $(mgr.transferStationFocusId).getSelectedItemData();
				$(mgr.transferStationFocusId).val(item);
				daozhan = item;
			}
		},
		requestDelay : 500
	});
	
	//4.中转网点输入时自动下拉提示
	var zhzhuanWdArr = new Array();
	$(mgr.transferStationPositionFocusId).easyAutocomplete({
		minCharNumber : 1,// 至少需要1个字符
		url : function(phrase) {
			var param = "?daozhan="+daozhan+"&latticePoint="+phrase;
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
	});
	
	//5.运单号输入时按回车自动加载
	$(mgr.waijiedanhaoFocusId).keydown(function(e){
		if(e.keyCode==13){//回车
			if($(mgr.waijiedanhaoFocusId).val()!=""){
			$.ajax({
				type:"GET",
				dataType: "json",
				url: mgr.waijiedanhaoDataUrl+encodeURI($(mgr.waijiedanhaoFocusId).val()),
				data: {},
				success:function (data){
					if(data.ydbhid){
						mgr.insert(data);
						$(mgr.waijiedanhaoFocusId).val("");
					}else if(data.indexOf("'error':'")){
						var error = data.replace("{'error':'","").replace("'}","");
						$.util.danger('操作提示',error);
					}
		        },
		        error:function (data){
		        	if(data.warning_message){
						$.util.danger('操作提示',data.warning_message);
					}else if(data.error_message){
						$.util.danger('操作提示',data.error_message);
					}
		        }
			});
			}
		}
	});	
	
	/**表单按钮联动效果*/
	//1.提货、干线、配送选择控制输入框
	$("input:radio[name='"+mgr.loadmode+"']").change(function() {
		var indexLength = $('.index').length;
		if(indexLength==0){
			var type = $("input:radio[name='"+mgr.loadmode+"']:checked").val();
			if (mgr.loadmode_transfer == type) {//干线中转
				mgr.curr_mode_type = type;
				$("#istransfer_yes").attr('checked', 'checked');
				$("#sf_zhongzhuan").css("display","block");
				$("#yunshuqishidi").css("margin-left","2%")
				mgr.zhongzhuanOnChange();
			} else if(mgr.loadmode_pick == type || mgr.loadmode_transport == type){//提货、配送
				mgr.curr_mode_type = type;
				$("#yunshuqishidi").css("margin-left","0%")
				$("#zhongzhuanwangdian").css("display","none");
				$("#zhongzhuanzhan").css("display","none");
				$("#sf_zhongzhuan").css("display","none");
				$("#yunshumudidi").css("display","block");
				$("#yunshuqishidi").css("display","block");
			}
			mgr.modifyRule();
		}else{
			setTimeout("mgr.resumeLoadmode()",200);
		}
	});
	//2.中转不中转，联动控制输入框
	$("input:radio[name='"+mgr.istransfer+"']").change(function() {
		mgr.zhongzhuanOnChange();
		mgr.modifyRule();
	});
	//3.外线，外车，自有车选择控制输入框
	$("input:radio[name='"+mgr.carrmode+"']").change(function() {
		var type = $("input:radio[name='"+mgr.carrmode+"']:checked").val();
		if (type ==  mgr.carrmode_waixian) {
			$("#xiao").css("display", "block");
		} else {
			$("#xiao").css("display", "none");
		}
		mgr.modifyRule();
	});
	//4. 整车、零担选择控制输入框
	$("input:radio[name='"+mgr.transtype+"']").change(function() {
		var type = $("input:radio[name='"+mgr.transtype+"']:checked").val();
		if (type == mgr.transtype_zhengche) {// 整车
			$("#cheliang").css("display", "block");
		} else if(type == mgr.transtype_lingdan){
			$("#cheliang").css("display", "none");
		}
		mgr.modifyRule();
	});
	
	//5.删除运单
	$('#tbody').delegate('.removeTr','click',function(){
		var indexLength = $('.index').length;
		if (indexLength == undefined || indexLength == null) {
			indexLength = 0;
		}
		if(indexLength==1){
			$.util.warning('操作提示', '至少保留一项数据');
		}else{
			var tr = $(this).parent().parent();
			tr.remove();
			var className = tr.attr("class");
			$("."+className).remove();
			$('.index').each(function(index,item){
				item.innerHTML = index+1;
			})
		}
		mgr.sumTotal();
	})
					
	$(".alert-search").click(function() {
		mgr.ajaxSearch($(this).attr("clean"));
	});
	// 日期选择空间
	$("input[name='bundle.fchrq']").flatpickr({
		enableTime : true,
		dateFormat : "Y-m-d H:i",
		minDate : 'today',
		time_24hr : true,
		onOpen : function(selectedDates, dateStr,instance) {
			var yjddshj = $("input[name='bundle.yjddshj']").val();
			if (yjddshj) {
				instance.set("maxDate", yjddshj);
			} else {
				instance.set("maxDate", moment().add(30, 'days').format('YYYY-MM-DD'));
			}
		}
	});
	$("input[name='bundle.yjddshj']").flatpickr({
		enableTime : true,
		dateFormat : "Y-m-d H:i",
		minDate : 'today',
		time_24hr : true,
		onOpen : function(selectedDates, dateStr,instance) {
			var fchrq = $("input[name='bundle.fchrq']").val()
			if (fchrq) {
				instance.set("minDate", fchrq);
			}
			instance.set("maxDate", moment().add(40,'days').format('YYYY-MM-DD'));
		}
	});
	//装载方式扩大选中区域
	$(".loadmodearea").click(function(){
		$(this).children("input:radio[name='"+mgr.loadmode+"']").prop("checked",true);
	});
	//承运方式扩大选中区域
	$(".carrmodearea").click(function(){
		$(this).children("input:radio[name='"+mgr.carrmode+"']").prop("checked",true);
	});
	//运输类型扩大选中区域
	$(".transtypearea").click(function(){
		$(this).children("input:radio[name='"+mgr.transtype+"']").prop("checked",true);
	});
	//清空
	$("#clearall").click(function() {
		$.util.confirm("操作提示","是否确认清空所有内容？", function check(){
            if(1){
            	//window.location.reload();
            	location.replace(location.href);
            	}
        	} );
	});
	
	// 设置所有的select选中值
	$("select").each(function(i) {
		if ($(this).attr("value")) {
			$(this).val($(this).attr("value"));
		}
	});
	// 设置所有的单选多选选中值
	$("input:radio,input:checkbox").each(function(i) {
		if ($(this).val() == $(this).attr("real")) {
			$(this).attr("checked", 'checked');
		}
	});
	//保存
	$(".btn-submit").on("click", function() {
		if ($('.index').length == 0) {
			$.util.danger('操作提示', '请至少装载一个运单！');
		}else{
			$("#flag").val($(this).attr("value"));
			if ($("#bundleForm").valid()) {
				$("#bundleForm").submit();
				$("#loading").show();
			}
		}
	});
	
	//jquery自定义方法
	jQuery.validator.addMethod("check_in_zhongzhuanzhan_array", function(value){
		if(value!=""){
			if($.inArray(value, zhzhuanArr)==-1){
				return false;
			}else {
				return true;
			}
		}else {
			return true;
		}
	}, '');
	
	//jquery自定义方法
	jQuery.validator.addMethod("check_in_zhongzhuanwangdian_array", function(value){
		if(value!=""){
		    if($.inArray(value, zhzhuanWdArr)==-1){
		    	return false;
		    }else {
		    	return true;
		    }
		}else {
			return true;
		}
	}, '');
	//电话验证
	jQuery.validator.addMethod("IsMobilePhoneNumber", function(value){
		if(value!=""){
			var regex = /^1[34578]\d{9}$/; 
			return value.match(regex);
		}else{
			return true;
		}
	},'');
	
	
	// 表单验证
	$("#bundleForm").validate({
		rules : {
			'bundle.clOwner' : {required : true},// 外线或者外车
			'bundle.wxName' : {required : true},// 外线名称
			'bundle.wxConName' : {required : true},// 外线联系人
			'bundle.wxTel' : {required : true , IsMobilePhoneNumber : true},// 外线电话
			'bundle.chxh' : {required : true},// 车牌号
			'bundle.driverName' : {required : true},// 司机姓名
			'bundle.driverTel' : {required : true},// 司机电话
			'bundle.fazhan' : {required : true,maxlength : 5},// 运输起始地
			'bundle.daozhan' : {required : true,maxlength : 4},// 运输目的地
			'freightStation' : {required : true,maxlength : 4,check_in_zhongzhuanzhan_array:true},// 中转站
			'bundle.dzshhd' : {maxlength : 4,check_in_zhongzhuanwangdian_array:true},// 中转站
			'bundle.fchrq' : {required : true},// 发车日期
			'bundle.yjddshj' : {required : true},// 预计到达时间
			'bundle.qdCost' : {required : true,number : true,min : 0},// 运输成本
			'bundle.elseCost' : {required : true,number : true,min : 0},// 其他费用
			'bundle.wxItem' : {required : true},// 外线单号
			'bundle.ysfs' : {required : true},
		},
		errorPlacement : function(errors, element) {
		}
	});
	mgr.modifyRule();
	debuge: true;
});

