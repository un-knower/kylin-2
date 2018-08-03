/**
 * creatd by lhz on 2017/12/6
 *
 *异常操作日志报表
 */

var operationLog = {
	_init: function() {

		layui.use('form', function(){
			var form = layui.form;
			form.on('submit(toCheck)', function(data){
				operationLog._LogAjaxActive(1,10);
				return false;
			});
		})
		operationLog._importHandler();
	},
	_LogAjaxActive: function(num,size,satus){
		var waybillId = $('#waybillId').val();
		var operation_menu = $('.layui-select-title input').val();
		var beginTime = $('#startDate').val();
		var endTime = $('#endDate').val();
		if(beginTime == null || beginTime == ''){
			var date = new Date();
			var dateTime = formatDate(date,'本月年月前日');
			beginTime = dateTime;
		}
		if(endTime == null || endTime == ''){
			var date = new Date();
			var dateTime = formatDate(date,'本月年月前日');
			endTime = dateTime;
		}
		var disDate = dateInterval(beginTime,endTime);
		if(disDate < 0){
			layer.msg('开始时间不能大于结束时间',{icon: 5,anim: 6});
			return false;
		}
		var pageSize = size || 10; 
		if(satus == 0){
			var date = new Date();
			var dateTime = formatDate(date,'本月年月前日');
			beginTime = endTime = dateTime;
			$('#startDate').attr('placeholder',dateTime);
			$('#endDate').attr('placeholder',dateTime);
		}else{
			/*$('#startDate').attr('placeholder','请输入开始时间');
			$('#endDate').attr('placeholder','请输入结束时间');*/
			$('#startDate').attr('placeholder',dateTime);
			$('#endDate').attr('placeholder',dateTime);
		}
		if(waybillId.length > 6){
			//运单编号
			var submitData = {
					ydbhid: waybillId,
					operatingMenu: operation_menu,
					operatingTimeBegin: beginTime,
					operatingTimeEnd: endTime,
					num: num,
					size: pageSize
			};
		}else{
			//财凭号
			var submitData = {
					cwpzhbh: waybillId,
					operatingMenu: operation_menu,
					operatingTimeBegin: beginTime,
					operatingTimeEnd: endTime,
					num: num,
					size: pageSize
			};
		}
		submitData = JSON.stringify(submitData);
		 EasyAjax.ajax_Post_Json({
     		 dataType: 'json',
             url: base_url + '/transport/exceptionLog/search.do',
             errorReason: true,
             data: submitData,
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
        	 var result = data.resultInfo;
        	 /*$.each(result.collection, function(index,elem){
        		 console.log(elem.operatingTime);
        		 elem.operatingTime = formatDate(parseInt(elem.operatingTime),'本月年月日');
        	 })*/
        	 page(result.collection, result.pages, '#tableList', '#showLogList', result.num, operationLog._LogAjaxActive, result.total, result.size);
        	 if(result.collection.length>0){
        		 $('.toDelete').show();
        		 $('#page').show();
        	 }else{
        		 $('.toDelete').hide();
        		 $('#page').hide();
        		 layer.msg('暂无数据');
        	 }
         })
	},
	_importHandler: function(){
		$('.toDelete').unbind('click').on('click', function(){
			operationLog._confirmAjaxActive();
		})
	},
	_confirmAjaxActive: function(){
		var ydbhid = $('#waybillId').val();
		var operatingMenu = $('.layui-select-title input').val();
		var operatingTimeBegin = $('#startDate').val();
		var operatingTimeEnd = $('#endDate').val();
		var place_holder = $('#startDate').attr('placeholder');
		if(place_holder.indexOf('-') != -1){
			var date = new Date();
			var dateTime = formatDate(date,'本月年月前日');
			operatingTimeBegin = operatingTimeEnd = dateTime;
		} 
		if(ydbhid.length > 6){
			var submitData = {
					ydbhid: ydbhid,
					operatingMenu: operatingMenu,
					operatingTimeBegin: operatingTimeBegin,
					operatingTimeEnd: operatingTimeEnd
			}
		}else{
			var submitData = {
					cwpzhbh: ydbhid,
					operatingMenu: operatingMenu,
					operatingTimeBegin: operatingTimeBegin,
					operatingTimeEnd: operatingTimeEnd
			}
		}
		var url = base_url + '/transport/exceptionLog/exportExceptionLog.do?';
		for(var o in submitData){
			if(submitData[o] != ''){
				url = url + o + '=' + submitData[o]+'&';
			}
		}
		if(url.indexOf('&') != -1){
			url = url.substring(0,url.lastIndexOf('&'));
		}
		window.location.href = url;
	}
}
layui.use('layer', function(){
	var layer = layui.layer ;
	operationLog._init();
	operationLog._LogAjaxActive(1,10,0);
});