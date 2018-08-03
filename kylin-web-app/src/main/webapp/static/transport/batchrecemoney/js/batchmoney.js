/**
 * Created by lhz on 2017/10/11
 *
 * 收钱
 */
/*json字段转化*/
var flag = false;
var toStop = false;
var elemArr = [], waybillID = [], arrivePlace = [], customerName = [], replayData, checkflag = true;
var dataChange = {'公司' : 'company',
				  '年份' : 'year',        
				  '财凭号' : 'wealthNo'
				}
read_excell();
$('.load-model').click(function(){/*下载模板*/
	window.location.href = ctx_static+'/upload/template/batchreceive.xlsx';
})
/*********************导出**************/
$('.toExport').click(function(){
	window.location.href = base_url+'/transport/finance/downloadFailBatchReceiveMoney';
	//$(this).addClass('layui-btn-disabled');
})
$('.toExport-success').click(function(){
		window.location.href = base_url+'/transport/finance/downloadSuccBatchReceiveMoney';
		//$(this).addClass('layui-btn-disabled');
})
/******批量签收公共方法*********/
var batchMoneyPublic = {
	/*中英文字段转换---中转英*/
	_eachDataName: function(data){
		var excellDataTansfer = [];
		$.each(data, function(index, ele){
        	var dataDetail = {};
        	for(var key in ele){
        		var beforeKey = ele[key];
        		key = dataChange[key]; 
        		dataDetail[key] = beforeKey;
            }
        	excellDataTansfer.push(dataDetail);
        })
		return excellDataTansfer;
	},
	/*数组查重*/
	_removeDuplicatedItem: function(ar) {
	    var tmp = {},
	        ret = [];
	    for (var i = 0, j = ar.length; i < j; i++) {
	        if (!tmp[ar[i]]) {
	            tmp[ar[i]] = 1;
	            ret.push(ar[i]);
	        }
	    }
	    return ret;
	}
}

/*****业务逻辑公共部分**********/
var operationPublic = {
	_dataOperation: function(data, nodataShow, lineNum){
		/*
		 * data: 后台返回的成功或失败的集合
		 * nodataShow: 没有数据时的显示
		 * lineNum: 显示表格当前数据
		 * */
	    	if(nodataShow == '.falseData'){
	    		if($('.sign-fail').length !=0){
	    			$(nodataShow).hide();
	    			$('.toExport').show();
		    		$('.false-table').show();
	    		}else{
	    			$(nodataShow).show();
	    			$('.toExport').hide();
	    			$('.false-table').hide();
	    		}
	    	}else{
	    		if($('.sign-success').length !=0){
	    			$(nodataShow).hide();
	    			$('.toExport-success').show();
	                $('.success-table').show();
	    		}else{
	    			$(nodataShow).show();
	    			$('.success-table').hide();
	    			$('.toExport-success').hide();
	    		}
	    		
	    	}
	    	var lineNumber = $(lineNum).html();
	    	var number = parseInt(lineNumber) + data.length;
	    	$(lineNum).html(number);
	},
	_ajaxBatchMoney: function(submitData, index, submitDataLen){/*一键签收接口*/
//		$('.stopReceive').removeClass('layui-btn-disabled');
		//$('.stopReceive').unbind().on('click', function(){
			//toStop = true;
		//});
		elemArr = [];
		if(index == 0){
			submitData[index].isNewDoc = true;
		}else{
			submitData[index].isNewDoc = false;
		}
        elemArr.push(submitData[index]);
        var ajaxData = JSON.stringify(elemArr);
        /*if(submitState){
        	submitState = false;*/
        	EasyAjax.ajax_Post_Json({
        		dataType: 'json',
                url: base_url + '/transport/finance/saveBatchReceiveMoney',
                data: ajaxData,
                async: false,
                beforeHandler: function(){
                	/*loading = layer.load(0,{
                		shade: [0.5,'#fff']
                	})*/
                },
                afterHandler: function(){
                	/*layer.close(loading);*/
                }/**,
                errorHandler: function(){
                	flag = false;
                	layer.open({
                		content: '操作失败,请检查数据。'
                	})
                }**/
            },
            function (data) {
            	if($('.batchSign-data').length == 1){
            		$('.ifImportNodata').find('.no-data-copy').html('提交成功');
                    $('.ifImportNodata').fadeIn();
                    $('#demo').hide();
            	}
//            	$('.batchSign-data').eq(0).addClass('signActive').slideUp();
        		var falseData = data.failMessage;
                $('#false-data').append($('#false-tbody').render(falseData));
                operationPublic._dataOperation (falseData, '.falseData', '.fail-data');
                var successData = data.succMessage;
                $('#success-data').append($('#success-tbody').render(successData));
                operationPublic._dataOperation (successData, '.if-successNodata', '.success-data');
//                $('.batchSign-data').eq(0).remove();
                index ++;
                if(index < submitDataLen){
                	$('.batchSign-data').eq(0).remove();
                	$('.import-data-number').html($('.batchSign-data').length-1);//数据行数展示
                	setTimeout(function(){
                		//if(!toStop){
                			operationPublic._ajaxBatchMoney(submitData,index, submitDataLen);
                		//}else{
                			//layer.msg('您已暂停批量收钱');
                			//toStop = false;
//                			$('.toSubmit').removeClass('layui-btn-disabled');
                			//$('.stopReceive').addClass('layui-btn-disabled');
                		//}
                    },1000);
                }
            });
        	
	}
}
function read_excell(){//导入
    $('#excell-file').on('change', function(e){
    	falseFlag = successFlag = true;
    	$('.toExport, .toExport-success').removeClass('layui-btn-disabled');
        var files = e.target.files;/*目标文件*/
//        $('.all-data').html(0);
        var fileReader = new FileReader();
        $('.ifImportNodata').hide();
           fileReader.onload = function(ev){/*文件后缀名只能是.xlsx或.xls*/
            try {
                var data = ev.target.result,
                    workbook = XLSX.read(data, {
                        type: 'binary',
                        cellText :false,
                        cellNF : false,
                        cellDates: true
                    }),//以二进制流的方式读取整份excell表格对象
                    persons = []; //存储获取到的数据
            }catch (e){
            	layer.msg('文件类型不正确！');
                return;
            }
            //表格的表格范围，可用于判断表头数量是否正确
            var fromTo = '';
            //遍历每张表读取
            for (var sheet in workbook.Sheets) {
                if(workbook.Sheets.hasOwnProperty(sheet)){
                    fromTo = workbook.Sheets[sheet]['!ref'];
                    persons = persons.concat( XLSX.utils.sheet_to_json (
                                               workbook.Sheets[sheet],
                                               {dateNF:'YYYY-MM-DD'}
                    ));
                    // break;   // 如果只取第一张表sheet1，就取消注释这行
                }
            }
            if(persons.length > 100){
            	layer.msg('excell表格超出100行，请重新输入！');
            	return;
            }
            var importdata = batchMoneyPublic._eachDataName(persons)/*字段转化，因为excell导入进来是中文无法识别*/
            var tableValue = $('#import-tbody').render(importdata);
            $('#import-data').html(tableValue);
            $('#demo').show();
            $('.import-data-number').html(persons.length);//数据行数展示
            flag = true;/*提交按钮功能*/
            InfotoSubmit(importdata);
            $('.toSubmit').removeClass('layui-btn-disabled');
        };
        // 以二进制方式打开文件
        fileReader.readAsBinaryString(files[0]);
        document.getElementById("myForm").reset();
    })
}

/*提交操作*/
function InfotoSubmit(dataArr){
    $('.toSubmit').unbind().on('click', function() {
    	if(flag){
    		var carryNum = /^\d*$/;
    		flag = false;
    		$('.clear-data').html('');
    		$('.clear-datanum').html(0);
        	$(this).addClass('layui-btn-disabled');
        	var submitData = dataArr;
        	var submitDataLen = submitData.length;
        	for(var i=0;i<submitData.length;i++){
        		if(!submitData[i].wealthNo){
        			layer.msg('存在财凭号为空的行');
    				return false;
        		}else if(!carryNum.test(submitData[i].wealthNo)){
    				layer.msg('存在财凭号'+submitData[i].wealthNo+'不是数字格式');
    				return false;
    			}
        	}
    		operationPublic._ajaxBatchMoney(submitData,0,submitDataLen);
    	}
      })
}