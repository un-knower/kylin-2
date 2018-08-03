/**
 * creatd by lhz on 2017/11/3
 *
 *签收
 */
var receivesign= {
	_init: function() {
		layui.use('form', function(){
			var form = layui.form;
			form.on('submit(toCheck)', function(data){
				receivesign._checkHandler();  
    		 	return false;
    		}) 
    		form.on('submit(save)', function(data){
    			receivesign._saveHandler(); 
    		 	return false;
    		})
		})
	},
	_checkHandler: function(){
			var waybillId = $('#waybillId').val();
			var submitData = {ydbhid: waybillId};
			submitData = JSON.stringify(submitData);
			receivesign._ajaxActive(submitData);
	},
	_ajaxActive: function(submitData){
		 EasyAjax.ajax_Post_Json({
     		dataType: 'json',
             url: base_url + '/transport/sign/search',
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
         	var dataRes = data.resultInfo;
         	var qsztInfo=dataRes.qszt;
         	
         	var tableBody = $('#tableList').render(dataRes);
         	$('#shoeTableList').html('').html(tableBody);
         	
         	$('.waybill-info').each(function(index,elem){
         		var valueName = $(elem).data('value');
         		if(valueName == 'qszt' ){
         			switch(dataRes[valueName]){
	         			case 0:
	         				$(elem).val('未签收');
	         				break;
	         			case 11:
	         				$(elem).val('完好签收');
	         				break;
	         			case 12:
	         				$(elem).val('延误签收');
	         				break;
	         			case 1:
	         				$(elem).val('破损签收');
	         				break;
	         			case 2:
	         				$(elem).val('短少签收');
	         				break;
	         			case 3:
	         				$(elem).val('综合事故');
	         				break;
	         			case 21:
	         				$(elem).val('客户要求延时');
	         				break;
	         			case 20:
	         				$(elem).val('其他');
	         				break;
	         			case 221:
	         				$(elem).val('客户拒收-破损');
	         				break;
	         			case 222:
	         				$(elem).val('客户拒收-短少');
	         				break;
	         			case 223:
	         				$(elem).val('客户拒收-延时');
	         				break;
	         			case 224:
	         				$(elem).val('客户拒收-串货');
	         				break;
	         			case 225:
	         				$(elem).val('客户拒收-其它');
	         				break;
	         			case 22:
	         				$(elem).val('客户拒收');
	         				break;
         			}
         			qsztInfo=$(elem).val();
         		}else{
         			$(elem).val(dataRes[valueName]);
         		}
         	});
         	$("#sign-select").siblings(".layui-form-select").find(".layui-select-title").find(".layui-input").val(qsztInfo);
         	$("#sign-select").val($("#sign-state").val());
         	if(dataRes.isGreenWay){
         		$('.isVIP').fadeIn();
         	}else{
         		$('.isVIP').fadeOut();
         	}
         	if(dataRes.isSign){
         		$('.toSave').attr('disabled','').addClass('layui-btn-disabled');
         		$('.Vehicle-condition').attr({'placeholder':'暂无数据','readonly':''});
         		layer.msg('此运单已经签收！');
         		$('#sign-state').show();
         		$('.layui-form-select').hide();
         	}else{
        		$('.layui-select-title input').attr({'placeholder':'未签收'})
        									  .val('未签收');
         		$('.toSave').removeAttr('disabled').removeClass('layui-btn-disabled');
         		$('.qsTime').val(dataRes.qsTime);
             	$('.Vehicle-condition').removeAttr('readonly').attr('placeholder', '请输入...');
             	$('#sign-state').hide();
         		$('.layui-form-select').show();
         		if(dataRes.isUndo){
         			$('#signTime').hide();
         			$('#cancel-signTime').show();
         			layer.msg('该运单可以签收，但不能更改签收时间。已撤销过签收且首次签收时间不是当前月，签收时间将保留最后一次签收时间且不可更改');
         			$('#cancel-signTime').attr("readonly",true);
         		}else{
         			$('#signTime').show();
         			$('#cancel-signTime').hide();
         		}
         	}
         	var signTime = data.resultInfo.qsTime; // 签收时间
        	/* signType 等于1为手动签收  不支持修改签收状态 ;null为系统签收 支持修改签收状态   */
        	var signType = data.resultInfo.signType; // 签收类型
        	
        	if(signType!=1){
        		var newSignTime=new Date(signTime);
        		newSignTime.setHours(newSignTime.getHours()+2);
        		var nowTime=new Date();
        		if(nowTime<newSignTime){
        			//$('.layui-select-title input').val();
        			//alert($('#sign-select').val());
        			$(".toSave").removeAttr("disabled");
        			$("#sign-state").hide();
        			$('.layui-form-select').show();
        			$("#sign-select").removeAttr("readonly");
        			$(".toSave").removeClass("layui-btn-disabled");
        			
        			$('#damageNum').attr("readonly",false);
        			$('#shortNum').attr("readonly",false);
        			$('#tips').attr("readonly",false);
        			$('#lururen').attr("readonly",false);
        			$('#signPhone').attr("readonly",false);
        		}
        		//return newSignTime;
        	}
         });
	},
	_saveHandler: function(){
		var ydbhid = $('#numberID span').html()
			,qszt = $('#sign-select').val()
			,psjianshu = $('#damageNum').val()
			,dsjianshu = $('#shortNum').val()
			,qsr = $('#signPeople').val()
			,qsTime = $('#signTime').val()
			,lrTime = $('#lrTime').val()
			,comm = $('#tips').val()
			,qsrphone  = $('#signPhone').val();
		if(psjianshu == '' || psjianshu == null){
			psjianshu = 0;
		}
		if(dsjianshu == '' || dsjianshu == null){
			dsjianshu = 0;
		}
		if(qszt.indexOf('未签收') > -1){
			layer.open({
				content: '不能保存未签收'
			})
			return false;
		}else{
			qszt = $('.layui-this').attr('lay-value');
			var dataArr = {
					ydbhid: ydbhid, qszt: qszt, psjianshu : psjianshu
					,dsjianshu : dsjianshu		,qsr : qsr
					,qsTime : qsTime	,comm : comm	,qsrphone : qsrphone
					,lrTime : lrTime
			}
			dataArr = JSON.stringify(dataArr);
			receivesign._saveData(dataArr);
		}
		
	},
	_saveData: function(dataArr){
		 EasyAjax.ajax_Post_Json({
    		dataType: 'json',
            url: base_url + '/transport/sign/save.do',
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
        	console.log(data);
        	layer.open({
				content: '签收成功！',
				yes: function(){
					window.location.href = base_url + '/transport/sign/receivesign';
				}
			})
        });
	}
}
receivesign._init();

