/**
 * creatd by lhz on 2017/11/29
 *
 *撤销签收
 */
var checkCode = 0;
var undoSign= {
	_init: function() {
		layui.use('form', function(){
			var form = layui.form;
			form.on('submit(toCheck)', function(data){
				undoSign._checkHandler();  
    		 	return false;
    		}) 
    		form.on('submit(toCancel)', function(data){
    			if(checkCode == 200){
    				undoSign._saveHandler();
    			}
    		 	return false;
    		})
		})
	},
	_checkHandler: function(){
			var waybillId = $.trim($('#waybillId').val());
			var submitData = {ydbhid: waybillId};
			submitData = JSON.stringify(submitData);
			undoSign._ajaxActive(submitData);
	},
	_ajaxActive: function(submitData){  
		 EasyAjax.ajax_Post_Json({
     		dataType: 'json',
             url: base_url + '/transport/sign/searchUndoSign.do',
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
        	 checkCode = data.resultCode;
        	 if(checkCode == 200){
        		 $('.toCancel').removeClass('layui-btn-disabled')
   			  				   .addClass('layui-btn-warm');
        	 }else{
        		 $('.toCancel').removeClass('layui-btn-warm')
	  			  			   .addClass('layui-btn-disabled');
        	 }
         	var dataRes = data.resultInfo;
         	var tableBody = $('#tableList').render(dataRes);
         	var DetailtableBody = $('#detailList').render(dataRes);
         	$('#shoeTableList').html('').html(tableBody);
         	$('#showdetailList').html('').html(DetailtableBody);
         	$('.waybill-info').each(function(index,elem){
         		var valueName = $(elem).data('value');
         			$(elem).val(dataRes[valueName]);
         	})
         	if(dataRes.isGreenWay){
         		$('.isVIP').fadeIn();
         	}else{
         		$('.isVIP').fadeOut();
         	}
         });
	}, 
	_saveHandler: function(){
		var ydbhid = $('#numberID span').html()
			var dataArr = {ydbhid: ydbhid}
			dataArr = JSON.stringify(dataArr);
			undoSign._saveData(dataArr);
	},
	_saveData: function(dataArr){
		 EasyAjax.ajax_Post_Json({
    		dataType: 'json',
            url: base_url + '/transport/sign/undoSign.do',
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
         var code = data.resultCode;
       	 if(code == 300){
       		layer.msg(data.reason);
       	 } else {
       		layer.open({
				content: '运单已撤销签收！',
				yes: function(){
					window.location.href = base_url + '/transport/sign/toUndoSign';
				}
			}) 
       	 }
        	
        });
	}
}
undoSign._init();

