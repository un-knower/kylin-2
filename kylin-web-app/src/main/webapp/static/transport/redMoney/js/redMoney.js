/**
 * creatd by lhz on 2017/12/8
 *
 *财凭冲红
 */
var tabledata;
var redMoney = {
	_init: function() {
		/*初始化当前默认年份*/
		var theDate = new Date();
		var theYEAR = theDate.getFullYear();
		year = theYEAR;
		$('#select-year').val(theYEAR);
		$('#select-year').attr('placeholder',theYEAR);
		layui.use('form', function(){
			var form = layui.form;
			form.on('submit(toCheck)', function(data){
	         	$('#showdetailList').html('');
	         	$('#tips').val('');
				if($('.layui-anim-upbit dd').length > 0){
					var companyName = $('.layui-select-title input').val();
					var companyId = $('.layui-this').attr('lay-value');
				}else{
					var companyName = $('#companyN').val();
					var companyId = $('#companyN').data('value');
				}
				var selectYear = $('#select-year').val();
				if(selectYear == null || selectYear == ''){
					var theDate = new Date();
					var theYEAR = theDate.getFullYear();
					year = theYEAR;
					selectYear = year;
				}
				var wealthId = $('#waybillId').val();
				var transportId = $('#transportCode').val();
				submitData = {year: selectYear, wealthNo: wealthId, companyCode: companyId, companyName: companyName,transportCode:transportId}
				submitData = JSON.stringify(submitData);
				var permissLevel = $("#permissLevel").val();
				redMoney._ajaxActive(submitData,permissLevel);  
			 	return false;
			}) 
		});
		$('#toClear').on('click',function(){
			$('.change-status').unbind('change');
			$('#tips').attr({'readonly':'','placeholder':'暂无数据'});
			$('#toRed').addClass('layui-btn-disabled').unbind('click');
			redMoney._clearHandler();
		});
		$('#print').on('click', function(){
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
		})
	},
	_ajaxActive: function(submitData,level){
		console.log(level);
		var url = '';
		if(level&&level==1){
			url = '/transport/finance/searchOffsetWealthInfo';//没有权限
		}else if(level&&level==2){
			url = '/transport/finance/searchOffsetWealthInfoRightIsCommon';//普通权限
		}else if(level&&level==3){
			url = '/transport/finance/searchOffsetWealthInfoRightWithGrouper';//单证组长
		}else if(level&&level==4){
			url = '/transport/finance/searchOffsetWealthInfoRightWithManager';//财务经理
		}
		 EasyAjax.ajax_Post_Json({
     		dataType: 'json',
             url: base_url + url,
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
         	var dataResult = data.entity;
         	tabledata = data.entity; /*为保存红票财凭号*/
         	sessionStorage.setItem('conveyPrint',JSON.stringify(tabledata));
         	var entity = $('#tableList').render(data.entity);
         	$('#showdetailList').html(entity);
         	$('.change-status').unbind('change').on('change',function(){
         		$('#toRed').addClass('layui-btn-disabled').unbind('click')
         				   .on('click', function(){
         					  layer.msg('你已修改查询条件，请重新查询');
         				   });
    		})
         	if(dataResult.canModify){
         		//可以冲红
                 	$('#tips').removeAttr('readonly').attr('placeholder','请输入..');
         			$('#toRed').removeClass('layui-btn-disabled');
         			$('#toRed').unbind('click').on('click',function(){
         				var descrip = $('#tips').val();
 						if(!descrip || descrip.length<2){
 							layer.msg('请输入冲红的原因',{icon:5,anim:6});
 	         				return false;
 						}
         				layer.open({
         					content: '你是否要对这张财务凭证进行冲红？',
         					btn: ['确认','取消'],
         					yes: function(index,layero){
         						if($('.layui-anim-upbit dd').length > 0){
         							var companyName = $('.layui-select-title input').val();
         							var companyId = $('.layui-this').attr('lay-value');
         						}else{
         							var companyName = $('#companyN').val();
         							var companyId = $('#companyN').data('value');
         						}
         						var selectYear = $('#select-year').val();
         						var dataArr = {
         								year: selectYear, 
         								wealthNo: dataResult.wealthCode, 
         								companyCode: companyId, 
         								companyName: companyName,
         								description: descrip,
         								receiptNo: dataResult.receiptNo,
         								offsetStatus: dataResult.offsetStatus,
         								transportCode: dataResult.transportCode
         						}
         						dataArr = JSON.stringify(dataArr);
         						redMoney._saveData (dataArr);
         					},
         					btn2: function(index,layero){
         						
         					}
         				})
         			})
         	}else{//不可以冲红
         			$('#toRed').addClass('layui-btn-disabled');
         			$('#toRed').unbind('click').on('click',function(){
         				layer.open({
         					content: '这张财务凭证不能进行冲红'
         				})
         			})
         			$('#tips').attr({'readonly':'','placeholder':'暂不可编辑'});
         	}
         });
	},
	_saveData: function(dataArr){
		 EasyAjax.ajax_Post_Json({
    		dataType: 'json',
            url: base_url + '/transport/finance/offsetWealthInfo',
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
    		layer.open({
        		content: data.reason
        	})
        	if(data.resultCode==200){
        		$('#toRed').unbind('click').addClass('layui-btn-disabled');
        		$('#tips').attr({'readonly':''});
        		tabledata.redWealthCode = data.redWealthCode;
        		var entity = $('#tableList').render(tabledata);
             	$('#showdetailList').html(entity);
        	}
    		
        });
	},
	_clearHandler: function(){
		$('.clear-data').val('');
		$('#showdetailList').html('');
	}
}
redMoney._init();




