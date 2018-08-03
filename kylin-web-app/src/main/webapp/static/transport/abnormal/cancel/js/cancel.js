/**
 * creatd by lhz on 2017/12/4
 *
 *废除运单
 */
var cancelwaybill= {
	_init: function() {
		layui.use('form', function(){
			var form = layui.form;
			form.on('submit(toCheck)', function(data){
				cancelwaybill._checkHandler();  
    		 	return false;
    		}) 
    		/*form.on('submit(save)', function(data){
    			cancelwaybill._saveHandler(); 
    		 	return false;
    		})*/
		})
	},
	_checkHandler: function(){
			var waybillId = $('#waybillId').val();
			if($('.layui-anim-upbit dd').length > 0){
				var companyName = $('.layui-select-title input').val();
			}else{
				var companyName = $('#companyN').val();
			}
			var companyId = $('.layui-this').attr('lay-value');
			var submitData = {companyCode: companyId, companyName: companyName, transportCode: waybillId};
			submitData = JSON.stringify(submitData);
			cancelwaybill._ajaxActive(submitData);
	},
	_ajaxActive: function(submitData){
		 EasyAjax.ajax_Post_Json({
     		dataType: 'json',
             url: base_url + '/transport/convey/searchCancelWaybill',
             data: submitData,
             errorReason: true,
             cancelPage: true,
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
         	var dataRes = data.entity;
         	$('#damageNum').val(dataRes.transportCode);
         	$('#shortNum').val(dataRes.origStation);
         	$('#tips').val(dataRes.description);
         	switch (dataRes.cancelStatus){
         		case 0:
         			$('#waybillStatus').attr('data-value','0').val('正常');
         			break;
         		case 1:
         			$('#waybillStatus').attr('data-value','1').val('已作废');
         			break;
         		case 2:
         			$('#waybillStatus').attr('data-value','2').val('已取消作废');
         			break;
         	}
         	if(dataRes.resumeButton){
         		$('#toCancel').removeClass('layui-btn-disabled')
				  .unbind('click').on('click', function(){
					  cancelwaybill._cancelHandler(0);
				  })
         		
         	}else{
         		$('#toCancel').addClass('layui-btn-disabled').unbind('click');
         	}
         	if(dataRes.cancelButton){
         		$('#toVoid').removeClass('layui-btn-disabled')
							.unbind('click').on('click', function(){
		         			cancelwaybill._cancelHandler(1);
		         		})
         	}else{
         		$('#toVoid').addClass('layui-btn-disabled').unbind('click');
         	}
         	if(dataRes.canEditDesc){
         		$('#tips').removeAttr('readonly').attr('placeholder','请输入..');
         	}else{
         		$('#tips').attr({'readonly':'','placeholder':'暂不可编辑'});
         	}
         	
         });
	},
	_cancelHandler: function(statusCode){
		var status = statusCode;
		var transCode = $('#damageNum').val();
		var tips = $('#tips').val();
		var station = $('#shortNum').val();
		var dataArr = {
				cancelStatus: status,
				transportCode: transCode,
				description: tips,
				origStation: station
		}
		dataArr = JSON.stringify(dataArr);
		cancelwaybill._cancelData(dataArr);
	},
	_cancelData: function(dataArr){
		 EasyAjax.ajax_Post_Json({
    		dataType: 'json',
            url: base_url + '/transport/convey/cancelWaybill',
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
        	var dataRes = data.entity;
        	$('#tips').val(dataRes.description);
        	switch (dataRes.cancelStatus){
     		case 0:
     			$('#waybillStatus').attr('data-value','0').val('正常');
     			break;
     		case 1:
     			$('#waybillStatus').attr('data-value','1').val('已作废');
     			break;
     		case 2:
     			$('#waybillStatus').attr('data-value','2').val('已取消作废');
     			break;
        	}
        	if(dataRes.resumeButton){
        		$('#toCancel').removeClass('layui-btn-disabled').addClass('layui-btn')
							  .unbind('click').on('click', function(){
								  cancelwaybill._cancelHandler(0);
							  })
         	}else{
         		$('#toCancel').addClass('layui-btn-disabled').unbind('click')
         	}
         	if(dataRes.cancelButton){
         		$('#toVoid').removeClass('layui-btn-disabled')
							.unbind('click').on('click', function(){
		         			cancelwaybill._cancelHandler(1);
		         		})
         		
         	}else{
         		$('#toVoid').addClass('layui-btn-disabled').unbind('click')
         	}
         	if(dataRes.canEditDesc){
         		$('#tips').removeAttr('readonly').attr('placeholder','请输入..');
         	}else{
         		$('#tips').attr({'readonly':'','placeholder':'暂不可编辑'});
         	}
         	layer.open({
         		content: '操作成功'
         	})
        });
	}
}
cancelwaybill._init();

