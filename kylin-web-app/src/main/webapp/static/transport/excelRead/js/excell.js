﻿/**
 * Created by lhz on 2017/10/11
 *
 * 运单导入操作
 */
/*json字段转化*/

var elemArr = [], returnfalseArr = [], returntrueArr = [], failoperation = [], indexArr = [];
var flag = false, waybillID = [], arrivePlace = [], customerName = [], checkflag = true;
var errorres = true;
var otherfee = /^\d{0,8}\.{0,1}(\d{1,2})?$/;

var checkNUM = 0, companyName;
//
var columns = [
		'运单编号','始发站','到站','始发地','目的地','发站网点','到站网点','托运时间','客户名称','客户编码','行业类别','收货人名称','手机',
        '省市区','地址','服务方式','承运人','等待托运人指令发货','运输方式','运输天数','是否返单','返单要求','付费方式','客户单号',
        '品名', '型号','件数','包装','重量（吨）','体积（立方）','代收货款','保价金额','计费方式','运价','特别说明','是否录入财凭','保率',
        '是否取整','重货运价','轻货运价','按件运价','办单费','上门取货费','送货上门费','其他费用','模版版本号'
];
var fwfs =[];
var dataChange = {
			'运单编号' : 'ydbhid',
			'始发站' : 'fazhan',        
			'到站' : 'daozhan',       
			'始发地' : 'beginPlacename',       
			'目的地' : 'endPlacename',
			'发站网点' : 'shhd',
			'到站网点' : 'dzshhd',
			'托运时间' : 'fhshj',        
			'客户名称' : 'fhdwmch',      
			'地址' : 'shhrdzh',
        	'客户编码' : 'khbm',          
        	'行业类别' : 'fhkhhy',      
        	'等待托运人指令发货':'isReleaseWaiting',
        	'收货人名称' : 'shhrmch',        
        	'手机' : 'shhryb',       		
        	'省市区' : 'shrProvinces',      
        	'服务方式' : 'fwfs',         
        	'承运人' : 'hyy',         		
        	'运输方式' : 'ysfs',          
        	'运输天数' : 'daodatianshu',  
        	'件数' : 'jianshu',          
        	'保价金额' : 'tbje',
        	'是否返单' : 'isfd',        
        	'返单要求' : 'fdyq',
        	'付费方式' : 'fffs',          
        	'办单费' : 'invoice',    
        	'客户单号' : 'khdh',	
        	'重货运价' : 'weightprice',
        	'品名' : 'pinming',         	
        	'型号' : 'xh',             
        	'包装' : 'bzh',         
        	'重量（吨）' : 'zhl',         		
        	'体积（立方）' : 'tiji',        
            '计费方式' : 'jffs',           
            '运价' : 'yunjia', 
            '特别说明': 'tiebieshuoming',
            'LAY_TABLE_INDEX' : 'index',
            '轻货运价': 'lightprice',   
            '按件运价': 'piecework',
            '是否取整' :'isRound',
            '是否录入财凭' :'withFinance',
            '保率' :'premiumRate',
            '代收货款' : 'receipt',        
            '上门取货费':'tohome',        
            '送货上门费': 'delivery',     		
            '其他费用' : 'other',
            '模版版本号' :'version'
}
var element;
layui.use('element', function(){
	element = layui.element;
})
read_excell();
isTimeOut(true);
$('.load-model').click(function(){/*下载模板*/
	window.location.href = ctx_static+'/upload/template/template.xls';
})
/*导出*/
var falseFlag = true, successFlag = true;
$('.toExport').click(function(){
	if(falseFlag){
		falseFlag = false;
		window.location.href = base_url+'/transport/convey/exportExcel/falseConvey';
		$(this).addClass('layui-btn-disabled');
	}
})
$('.toExport-success').click(function(){
	if(successFlag){
		successFlag = false;
		window.location.href = base_url+'/transport/convey/exportExcel/successConvey';
		$(this).addClass('layui-btn-disabled');
	}
})
//判断登录是否失效
function isTimeOut(bool){
	var isL = true;
	EasyAjax.ajax_Post_Json({
		dataType: 'json',
        url: base_url + '/check/authentication',
        cancelPage: true,
        alertMsg: bool,
        async: false
    },
    function (data) {
    	companyName = data.company;
    	if(data.resultCode == 400){
    		isL = false;
    		if(bool){
    			layui.use(['form','element'],function(){
        			var form = layui.form,
        				element = layui.element;
        			layer.open({
        				content: '登录失效，将影响您的操作<br/>是否重新登陆？',
        				btn: ['是','否'],
        				shade: [1,'#fff'],
        				yes: function(){
        					window.top.location.href = base_url + "/views/loginPage.jsp";
        				},
        				btn2: function(){
        					
        				}
        			})
        		})
    		}
    	}else{
    		isL = true;
    	}
    });
	return isL;
}
function read_excell(){//导入
	/*try{*/    $('#excell-file').on('change', function(e){
		var isLogin = isTimeOut(false);
		if(!isLogin){
			return false;
		}
		falseFlag = successFlag = true;
		checkNUM = 0;
		$('.toExport, .toExport-success').removeClass('layui-btn-disabled');
	    var files = e.target.files;/*目标文件*/
	    $('.all-data').html(0);
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
	            alert('文件类型不正确');
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
	        	layer.msg('excell表格超出100行，请重新导入！');
	        	return;
	        }
	        $.each(persons, function(i,k){
	        	if(k.其他费用 != undefined && !isNaN(Number(k.其他费用))){
	        		k.其他费用 = parseFloat(k.其他费用).toFixed(2);
	        	}
	        })
	        tableRender(persons, '#demo', true);/*表格渲染*/
	        $('.import-data-number').html(persons.length);//数据行数展示
	    	$('.toCheck').fadeIn();
	    	$('.toCheck').removeClass('layui-btn-disabled');
	    	$('.toSubmit').addClass('layui-btn-disabled');
	    	flag = false;
	    };
	    // 以二进制方式打开文件
	    fileReader.readAsBinaryString(files[0]);
	    document.getElementById("myForm").reset();
	})
	

}
var versionCheck = false;
var hasFinance = [];
/*table操作*/
function tableRender (dataArr,container, ifImport){
    layui.use(['table', 'element'],function() {
        var table = layui.table;
        tablelns = table.render({
            elem: container
            ,data:dataArr
            ,cols: [[ //标题栏
                 /*{edit: 'text', field: '校验', title: '校验', width: 50, fixed: true}*/
                {edit: 'text', field: '运单编号', title: '运单编号', width: 100, fixed: true}
                ,{edit: 'text', field: '始发站', title: '始发站', width: 100}
                ,{edit: 'text', field: '到站', title: '到站', width: 100}
                ,{edit: 'text', field: '始发地', title: '始发地', width: 100}
                ,{edit: 'text', field: '目的地', title: '目的地', width: 100}
    			,{edit: 'text', field: '发站网点', title: '发站网点', width: 100}
                ,{edit: 'text', field: '到站网点', title: '到站网点', width: 100}
                ,{edit: 'text', field: '托运时间', title: '托运时间', width: 150}
                ,{edit: 'text', field: '客户名称', title: '客户名称', width: 100}
                ,{edit: 'text', field: '客户编码', title: '客户编码', width: 100}
                ,{edit: 'text', field: '行业类别', title: '行业类别', width: 100}
                ,{edit: 'text', field: '收货人名称', title: '收货人名称', width: 100}
                ,{edit: 'text', field: '手机', title: '手机', width: 100}
                ,{edit: 'text', field: '省市区', title: '省市区', width: 100}
                ,{edit: 'text', field: '地址', title: '地址', width: 100}
                ,{edit: 'text', field: '服务方式', title: '服务方式', width: 100}
                ,{edit: 'text', field: '承运人', title: '承运人', width: 100}
                ,{edit: 'text', field: '等待托运人指令发货', title: '等待托运人指令发货', width: 100}
                ,{edit: 'text', field: '运输方式', title: '运输方式', width: 100}
                ,{edit: 'text', field: '运输天数', title: '运输天数', width: 100}
                ,{edit: 'text', field: '是否返单', title: '是否返单', width: 100}
                ,{edit: 'text', field: '返单要求', title: '返单要求', width: 100}
                ,{edit: 'text', field: '付费方式', title: '付费方式', width: 100}
                ,{edit: 'text', field: '客户单号', title: '客户单号', width: 100}
                ,{edit: 'text', field: '品名', title: '品名', width: 100}
                ,{edit: 'text', field: '型号', title: '型号', width: 100}
                ,{edit: 'text', field: '件数', title: '件数', width: 100}
                ,{edit: 'text', field: '包装', title: '包装', width: 100}
                ,{edit: 'text', field: '重量（吨）', title: '重量（吨）', width: 100}
                ,{edit: 'text', field: '体积（立方）', title: '体积（立方）', width: 100}
                ,{edit: 'text', field: '代收货款', title: '代收货款', width: 100}
                ,{edit: 'text', field: '保价金额', title: '保价金额', width: 100}
                ,{edit: 'text', field: '计费方式', title: '计费方式', width: 100}
                ,{edit: 'text', field: '运价', title: '运价', width: 100}
                ,{edit: 'text', field: '特别说明', title: '特别说明', width: 150}
                ,{edit: 'text', field: '是否录入财凭', title: '是否录入财凭', width: 100}
                ,{edit: 'text', field: '保率', title: '保率', width: 100}
                ,{edit: 'text', field: '是否取整', title: '是否取整', width: 100}
                ,{edit: 'text', field: '重货运价', title: '重货运价', width: 100}
                ,{edit: 'text', field: '轻货运价', title: '轻货运价', width: 100}
                ,{edit: 'text', field: '按件运价', title: '按件运价', width: 100}
                ,{edit: 'text', field: '办单费', title: '办单费', width: 100}
                ,{edit: 'text', field: '上门取货费', title: '上门取货费', width: 100}
                ,{edit: 'text', field: '送货上门费', title: '送货上门费', width: 100}
                ,{edit: 'text', field: '其他费用', title: '其他费用', width: 100}
                ,{edit: 'text', field: '模版版本号', title: '模版版本号', width: 100}
            ]]
            ,align: 'center'
            ,skin: 'row' //表格风格
            /*,even: true*/
//            ,page: true //是否显示分页
//            ,limits: [10, 15, 20]//自定义每页条数
            ,limit: 100 //每页默认显示的数量
            ,done: function(res, curr, count){
            	var import_table = $('.layui-table-main').eq(0).find('tr');
            	if(ifImport){
            		flag = false;
                	$('.toCheck').removeClass('layui-btn-disabled');
                	$('.toCheck').fadeIn();
            		var it = $(".layui-table-main").eq(0).find('table tr');
                	$(it).each(function(index,ele){
                		$(this).attr('id','lineIndex_'+index)
                		var ittd = $(this).find('td');
                		$(ittd).each(function(Num,ele){
                			$(this).attr('id','line_'+index+'_col_'+Num);
                			$(this).addClass('everyTD');
                		})
                	})
                	tdClick();
                	eachtableData(); 
            	}
            	/*if(replayData != undefined){
            		reshowData();
            		return;
            	}*/
            }
        });
        
        
        //实时校验
        table.on('edit(excell-demo)', function(obj){
            var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键值
            ,field = obj.field //得到字段
            ,index = data.LAY_TABLE_INDEX; //得到所在行的索引
            $('.toCheck').fadeIn();
        	$('.toCheck').removeClass('layui-btn-disabled');
        	$('.toSubmit').addClass('layui-btn-disabled');
        	flag = false;
            if(indexArr.indexOf(index) == -1){
                indexArr.push(index);
              //  failoperation.push(dataArr[index]);
            }
            var carryNum = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
            if(field == '运单编号'){
//            	$('.layui-table-fixed').eq(0).find('tbody .laytable-cell-10001-运单编号').eq(index).removeClass('borderError');
            	if(value==undefined || value.length ==10 || value.length ==12){
            		waybillID[index] = value;
            		checkflag = true;
            		$('.layui-table-fixed').eq(0).find('tbody .laytable-cell-10001-运单编号').eq(index).removeClass('borderError');
            		/*checkFeild (waybillID,arrivePlace,customerName,dataArr);提交到后台校验*/
            	}else if(value.length == 0){
            		value == null;
            		waybillID[index] = null;
            		/*checkFeild (waybillID,arrivePlace,customerName,dataArr);提交到后台校验*/
            		checkflag = true;
            		$('.layui-table-fixed').eq(0).find('tbody .laytable-cell-10001-运单编号').eq(index).removeClass('borderError');
            		return;
            	}else{
            		layer.msg('运单编号仅支持10位或12位数字', {icon: 5,anim:6});
            		checkflag = false;
            		$('.layui-table-fixed').eq(0).find('tbody .laytable-cell-10001-运单编号').eq(index).addClass('borderError');
            	}
            }
            if(field == '始发站'){
            	if(value==undefined || value == ''){
            		$('#line_'+index+'_col_'+columns.indexOf('始发站')).find('div').addClass('borderError');
            		layer.msg('始发站不能为空', {icon: 5,anim:6});
            		checkflag = false;
            	}else if(value.length > 20){
            		$('#line_'+index+'_col_'+columns.indexOf('始发站')).find('div').addClass('borderError');
            		layer.msg('始发站长度不能超过20', {icon: 5,anim:6});
            		checkflag = false;
            	}else if(value != companyName){
            		$('#line_'+index+'_col_'+columns.indexOf('始发站')).find('div').addClass('borderError');
            		layer.msg('始发站与账号所在公司不一致', {icon: 5,anim:6});
            		checkflag = false;
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('始发站')).find('div').removeClass('borderError');
            	}
            }
            if(field == '到站'){
            	if(value==undefined ||value.length == 0){
            		$('#line_'+index+'_col_'+columns.indexOf('到站')).find('div').addClass('borderError');
            		layer.msg('到站不能为空', {icon: 5,anim:6});
            		checkflag = false;
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('到站')).find('div').removeClass('borderError');
            		/*arrivePlace[index] = value;*/
            		/*checkFeild (waybillID,arrivePlace,customerName,dataArr);提交到后台校验*/
            	}
            }
            if(field == '始发地'){
            	if(value==undefined || value == ''){
            		$('#line_'+index+'_col_'+columns.indexOf('始发地')).find('div').addClass('borderError');
            		layer.msg('始发地不能为空', {icon: 5,anim:6});
            		checkflag = false;
            	}else if(value.length > 20){
            		$('#line_'+index+'_col_'+columns.indexOf('始发地')).find('div').addClass('borderError');
            		layer.msg('始发地长度不能超过20', {icon: 5,anim:6});
            		checkflag = false;
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('始发地')).find('div').removeClass('borderError');
            	}
            }
            
            if(field == '目的地'){
            	if(value==undefined || value == ''){
            		$('#line_'+index+'_col_'+columns.indexOf('目的地')).find('div').addClass('borderError');
            		layer.msg('目的地不能为空', {icon: 5,anim:6});
            		checkflag = false;
            	}else if(value.length > 20){
            		$('#line_'+index+'_col_'+columns.indexOf('目的地')).find('div').addClass('borderError');
            		layer.msg('目的地长度不能超过20', {icon: 5,anim:6});
            		checkflag = false;
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('目的地')).find('div').removeClass('borderError');
            	}
            }
            if(field == '发站网点'){
        		if(value!=undefined &&value.length > 20){
            		$('#line_'+index+'_col_'+columns.indexOf('发站网点')).find('div').addClass('borderError');
            		layer.msg('发站网点的长度不能超过20个字', {icon: 5,anim:6});
            		checkflag = false;
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('发站网点')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '到站网点'){
        		if(value!=undefined &&value.length > 20){
            		$('#line_'+index+'_col_'+columns.indexOf('到站网点')).find('div').addClass('borderError');
            		layer.msg('到站网点的长度不能超过20个字', {icon: 5,anim:6});
            		checkflag = false;
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('到站网点')).find('div').removeClass('borderError');
            	}
            }
        	var a = /^(\d{4})\/(\d{2})\/(\d{2})\s{1,}\d\d:\d\d$/;
        	if(field == '托运时间'){
            	if(value==undefined || value == ''){
            		$('#line_'+index+'_col_'+columns.indexOf('托运时间')).find('div').addClass('borderError');
            		layer.msg('托运时间不能为空', {icon: 5,anim:6});
            		checkflag = false;
            	}else if(!a.test(value)){
            		$('#line_'+index+'_col_'+columns.indexOf('托运时间')).find('div').addClass('borderError');
            		layer.msg('请填写正确的日期格式，<br/>为年月日时分，<br/>如：YYYY/MM/DD HH:mm', {icon: 5,anim:6});
            		checkflag = false;
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('托运时间')).find('div').removeClass('borderError');
            		
            	}
            }
        	if(field == '客户名称'){
            	if(value==undefined || value == ''){
            		layer.msg('客户名称不能为空', {icon: 5,anim:6});
            		$('#line_'+index+'_col_'+columns.indexOf('客户名称')).find('div').addClass('borderError');
            		checkflag = false;
            	}else if(value.length>100){
            		layer.msg('客户名称长度不能超过100', {icon: 5,anim:6});
            		$('#line_'+index+'_col_'+columns.indexOf('客户名称')).find('div').addClass('borderError');
            		checkflag = false;
            	}else{
            		checkflag = true;
            		customerName[index] = value;
            		/*checkFeild (waybillID,arrivePlace,customerName,dataArr);提交到后台校验*/
            		$('#line_'+index+'_col_'+columns.indexOf('客户名称')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '客户编码'){
            	if(value!=undefined && value != ''){
            		if(value.length>20){
                		layer.msg('客户编码长度不能超过20', {icon: 5,anim:6});
                		$('#line_'+index+'_col_'+columns.indexOf('客户编码')).find('div').addClass('borderError');
                		checkflag = false;
                	}else{
                		checkflag = true;
                		$('#line_'+index+'_col_'+columns.indexOf('客户编码')).find('div').removeClass('borderError');
                	}
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('客户编码')).find('div').removeClass('borderError');
            	} 
            }
        	if(field == '收货人名称'){
            	if(value==undefined || value.length > 40){
            		layer.msg('收货人名称必填，且长度不超过40', {icon: 5,anim:6});
            		$('#line_'+index+'_col_'+columns.indexOf('收货人名称')).find('div').addClass('borderError');
            		checkflag = false;
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('收货人名称')).find('div').removeClass('borderError');
            	}
            }
        	var regexp = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
	 		var phoneReg = /^1\d{10}$/;
	 		if(field == '手机'){
            	/*if(value==undefined || value == ''){
            		layer.msg('手机号码不能为空', {icon: 5,anim:6});
            		$('#line_'+index+'_col_'+columns.indexOf('手机')).find('div').addClass('borderError');
            		checkflag = false;
            	}else{
            		if(!regexp.test(value)){
    	 				if(!phoneReg.test(value)){
    	 					layer.msg('手机号码格式不正确', {icon: 5,anim:6});
    	 					$('#line_'+index+'_col_'+columns.indexOf('手机')).find('div').addClass('borderError');
    	 					checkflag = false;
    		 			}else{
    		 				checkflag = true;
    		 				$('#line_'+index+'_col_'+columns.indexOf('手机')).find('div').removeClass('borderError');
    		 			}
    	 			}else{
    	 				checkflag = true;
    	 				$('#line_'+index+'_col_'+columns.indexOf('手机')).find('div').removeClass('borderError');
    	 			}
            	}*/
            	if($.trim(value)){
            		if(!regexp.test($.trim(value))){
    	 				if(!phoneReg.test($.trim(value))){
    	 					layer.msg('手机号码格式不正确', {icon: 5,anim:6});
    	 					$('#line_'+index+'_col_'+columns.indexOf('手机')).find('div').addClass('borderError');
    	 					checkflag = false;
    		 			}else{
    		 				checkflag = true;
    		 				$('#line_'+index+'_col_'+columns.indexOf('手机')).find('div').removeClass('borderError');
    		 			}
    	 			}else{
    	 				checkflag = true;
    	 				$('#line_'+index+'_col_'+columns.indexOf('手机')).find('div').removeClass('borderError');
    	 			}
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('手机')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '省市区'){
            	if(value==undefined || value == ''){
            		layer.msg('省市区不能为空', {icon: 5,anim:6});
            		$('#line_'+index+'_col_'+columns.indexOf('省市区')).find('div').addClass('borderError');
            		checkflag = false;
            	}else if(value.length > 40){
            		layer.msg('省市区长度不能超过40', {icon: 5,anim:6});
            		$('#line_'+index+'_col_'+columns.indexOf('省市区')).find('div').addClass('borderError');
            		checkflag = false;
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('省市区')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '地址'){
            	if(value==undefined || value == ''){
            		layer.msg('地址不能为空', {icon: 5,anim:6});
            		$('#line_'+index+'_col_'+columns.indexOf('地址')).find('div').addClass('borderError');
            		checkflag = false;
            	}else if(value.length > 40){
            		layer.msg('地址长度不能超过40', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('地址')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('地址')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '承运人'){
            	if(value!=undefined && value != ''&&value.length>4){
            		layer.msg('承运人不能超过4个字', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('承运人')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('承运人')).find('div').removeClass('borderError');
            	}
            }

            if(field == '等待托运人指令发货'){
        		/*console.log("实时校验");
        		console.log($.trim(value));*/
            	if($.trim(value)){
            		if($.trim(value) != '是' && $.trim(value) != '否'){
            			checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('等待托运人指令发货')).find('div').addClass('borderError');
            		}else{
                		checkflag = true;
                		$('#line_'+index+'_col_'+columns.indexOf('等待托运人指令发货')).find('div').removeClass('borderError');
                	}
        		}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('等待托运人指令发货')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '运输方式'){
            	if(value==undefined || value == ''){
            		layer.msg('运输方式不能为空', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('运输方式')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('运输方式')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '运输天数'){
            	if(!carryNum.test(value)){
            		layer.msg('运输天数只能填写数字且大于0', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('运输天数')).find('div').addClass('borderError');
            	}else if(Number(value)<0){
            		layer.msg('运输天数不能小于0', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('运输天数')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('运输天数')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '是否返单'){
            	if(value==undefined || value == ''){
            		layer.msg('是否返单不能为空', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('是否返单')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('是否返单')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '返单要求'){ 
            	if(value!=undefined && value.length > 40){
            		layer.msg('返单要求内容长度不能大于40', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('返单要求')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('返单要求')).find('div').removeClass('borderError');
            	}
            }
//        	if(field == '客户单号'){
//            	if(!carryNum.test(value)){
//            		/*layer.msg('客户单号只能填写数字且为整数', {icon: 5,anim:6});
//            		checkflag = false;
//            		$('#line_'+index+'_col_21').find('div').addClass('borderError');*/
//            	}else if(Number(value)>30){
//            		/*layer.msg('客户单号应该大于30', {icon: 5,anim:6});
//            		checkflag = false;
//            		$('#line_'+index+'_col_21').find('div').addClass('borderError');*/
//            	}else{
//        		if(value==undefined || value == ''){
//            		checkflag = true;
//            		$('#line_'+index+'_col_21').find('div').removeClass('borderError');
//            	}
//            }
        	if(field == '品名'){
            	if(value==undefined || value == ''){
            		layer.msg('品名不能为空', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('品名')).find('div').addClass('borderError');
            	}else if(value.length > 7){
            		layer.msg('品名内容长度不能超过7个字', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('品名')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('品名')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '型号'){
        		if(value!=undefined && value.length > 20){
            		layer.msg('型号长度不能超过20个字', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('型号')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('型号')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '件数'){
            	if(value==undefined || value == ''){
            		layer.msg('件数不能为空', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('件数')).find('div').addClass('borderError');
            	}else if(!carryNum.test(value)){
            		layer.msg('件数只能填写数字', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('件数')).find('div').addClass('borderError');
            	}else if(Number(value)>100000){
            		layer.msg('件数不能超过100000件', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('件数')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('件数')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '包装'){
            	if(value!=undefined && value.length>6){
            		layer.msg('包装内容长度不能超过6个字', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('包装')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('包装')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '重量（吨）'){
            	if(value==undefined || value == ''){
            		layer.msg('重量不能为空', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('重量（吨）')).find('div').addClass('borderError');
            	}else if(Number(value)>100000 ){
            		layer.msg('重量不能超过100000', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('重量（吨）')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('重量（吨）')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '体积（立方）'){
            	if(value==undefined || value == ''){
            		layer.msg('体积不能为空', {icon: 5,anim:6});
            		$('#line_'+index+'_col_'+columns.indexOf('体积（立方）')).find('div').addClass('borderError');
            		checkflag = false;
            	}else if(Number(value)>100000 ){
            		layer.msg('体积不能超过100000', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('体积（立方）')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('体积（立方）')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '保价金额'){
            	if(value!=undefined && !carryNum.test(value) ){
            		layer.msg('保价金额只能填写数字且不小于0', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('保价金额')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('保价金额')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '计费方式'){
            	if(value==undefined || value == '' ){
            		layer.msg('计费方式不能为空', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('计费方式')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('计费方式')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '运价'){
        		if(value!=undefined && value != ''){
        			if(!carryNum.test(value)){
                		layer.msg('运价只能填写数字', {icon: 5,anim:6});
                		checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('运价')).find('div').addClass('borderError');
                	}else{
                		checkflag = true;
                		$('#line_'+index+'_col_'+columns.indexOf('运价')).find('div').removeClass('borderError');
                	}
        		}else{
        			checkflag = true;
        			$('#line_'+index+'_col_'+columns.indexOf('运价')).find('div').removeClass('borderError');
        		}
            }
        	if(field == '代收货款'){
        		if(value!=undefined && value != ''){
        			if(!carryNum.test(value)){
                		layer.msg('代收货款只能填写整数且大于0', {icon: 5,anim:6});
                		checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('代收货款')).find('div').addClass('borderError');
                	}else{
                		checkflag = true;
                		$('#line_'+index+'_col_'+columns.indexOf('代收货款')).find('div').removeClass('borderError');
                	}
        		}else{
        			checkflag = true;
        			$('#line_'+index+'_col_'+columns.indexOf('代收货款')).find('div').removeClass('borderError');
        		}
            }
        	
        	//根据是否录入财务凭证来校验字段
        	if(field == '是否录入财凭'){
        		if(value!=undefined &&value != '' && value=='是'){
        			hasFinance[index] = true;
        		}else{
        			hasFinance[index] = false;
        		}
        	}
        	if(field == '服务方式'){
            	if(value==undefined || value == ''){
            		layer.msg('服务方式不能为空', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('服务方式')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('服务方式')).find('div').removeClass('borderError');
            	}
            }
        	if(field == '付费方式'){
        		if(value==undefined || value == ''){
        			layer.msg('付费方式不能为空', {icon: 5,anim:6});
        			checkflag = false;
        			$('#line_'+index+'_col_'+columns.indexOf('付费方式')).find('div').addClass('borderError');
        		}else{
        			checkflag = true;
        			$('#line_'+index+'_col_'+columns.indexOf('付费方式')).find('div').removeClass('borderError');
        		}
        	}
        	if(hasFinance[index] && field == '其他费用'){
        		if(value!=undefined &&value != ''){
        			if(!otherfee.test(parseFloat(value))){//且不能小于0
                		layer.msg('其他费用只能填写带两位小数数字', {icon: 5,anim:6});
                		checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').addClass('borderError');
                	}else if(value<0){//且不能小于0
                		layer.msg('其他费用不能小于0', {icon: 5,anim:6});
                		checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').addClass('borderError');
                	}else{
                		checkflag = true;
                		 if(!/\./.test(value)){
                			 value += '.00';  
                		 }else{
                			 value = Number(value).toFixed(2);
                		 }
                		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('.layui-table-edit').val(value);
                		//$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('.laytable-cell-10001-其他费用').removeClass('borderError');
                		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').removeClass('borderError');
                	}
        		}else{
        			layer.msg('其他费用不能为空', {icon: 5,anim:6});
        			$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').addClass('borderError');
        			checkflag = false;
        		}
        	 }
        	 //录入财凭为否且其他费用有值
        	 if(!hasFinance[index] && field == '其他费用'){
        		if(value!=undefined &&value != ''){
        			if(!otherfee.test(parseFloat(value))){//且不能小于0
                		layer.msg('其他费用只能填写带两位小数数字', {icon: 5,anim:6});
                		checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').addClass('borderError');
                	}else if(value<0){//且不能小于0
                		layer.msg('其他费用不能小于0', {icon: 5,anim:6});
                		checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').addClass('borderError');
                	}else{
                		checkflag = true;
                		 if(!/\./.test(value)){
                			 value += '.00';  
                		 }else{
                			 value = Number(value).toFixed(2);
                		 }
                		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('.layui-table-edit').val(value);
                		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').removeClass('borderError');
                	}
        		}else{
        			checkflag = true;
        			$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').removeClass('borderError');
        		}
        	 }
        	if(hasFinance[index] && field == '上门取货费'){
        		if(value!=undefined &&value != ''){
        			if(!carryNum.test(value)){
                		layer.msg('上门取货费是数字', {icon: 5,anim:6});
                		checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('上门取货费')).find('div').addClass('borderError');
                	}else{
                		if(fwfs[index] && (fwfs[index]=='站到仓' || fwfs[index]=='站到站') && value>0){
                			checkflag = false;
            				layer.msg('站到仓、站到站，上门取货费必须为0', {icon: 5,anim:6});
            			}else{
            				$('#line_'+index+'_col_'+columns.indexOf('上门取货费')).find('div').removeClass('borderError');
            				checkflag = true;
            			}
                	}
        		}else{
        			checkflag = true;
        			$('#line_'+index+'_col_'+columns.indexOf('上门取货费')).find('div').removeClass('borderError');
        		}
            }
        	if(hasFinance[index] && field == '送货上门费'){
        		if(value!=undefined &&value != ''){
        			if(!carryNum.test(value)){
                		layer.msg('送货上门费是数字', {icon: 5,anim:6});
                		checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('送货上门费')).find('div').addClass('borderError');
                	}else{
                		if(fwfs[index] && (fwfs[index]=='仓到站' || fwfs[index]=='站到站') && value>0){
                			checkflag = false;
            				layer.msg('仓到站、站到站，送货上门费必须为0', {icon: 5,anim:6});
            				$('#line_'+index+'_col_'+columns.indexOf('送货上门费')).find('div').addClass('borderError');
            			}else{
            				$('#line_'+index+'_col_'+columns.indexOf('送货上门费')).find('div').removeClass('borderError');
            				checkflag = true;
            			}
                	}
        		}else{
        			checkflag = true;
        			$('#line_'+index+'_col_'+columns.indexOf('送货上门费')).find('div').removeClass('borderError');
        		}
            }
        	if(hasFinance[index] && field == '重货运价'){
        		if(value!=undefined &&value != ''){
        			if(!carryNum.test(value)){
                		layer.msg('重货运价只能填写数字且大于0', {icon: 5,anim:6});
                		checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('重货运价')).find('div').addClass('borderError');
                	}else{
                		checkflag = true;
                		$('#line_'+index+'_col_'+columns.indexOf('重货运价')).find('div').removeClass('borderError');
                	}
        		}else{
        			checkflag = true;
        			$('#line_'+index+'_col_'+columns.indexOf('重货运价')).find('div').removeClass('borderError');
        		}
            }
        	if(hasFinance[index] && field == '轻货运价'){
        		if(value!=undefined &&value != ''){
        			if(!carryNum.test(value)){
                		layer.msg('轻货运价只能填写数字且大于0', {icon: 5,anim:6});
                		checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('轻货运价')).find('div').addClass('borderError');
                	}else{
                		checkflag = true;
                		$('#line_'+index+'_col_'+columns.indexOf('轻货运价')).find('div').removeClass('borderError');
                	}
        		}else{
        			checkflag = true;
        			$('#line_'+index+'_col_'+columns.indexOf('轻货运价')).find('div').removeClass('borderError');
        		}
            } 
        	if(hasFinance[index] && field == '按件运价'){
        		if(value!=undefined &&value != ''){
        			if(!carryNum.test(value)){
                		layer.msg('按件运价只能填写数字且大于0', {icon: 5,anim:6});
                		checkflag = false;
                		$('#line_'+index+'_col_'+columns.indexOf('按件运价')).find('div').addClass('borderError');
                	}else{
                		checkflag = true;
                		$('#line_'+index+'_col_'+columns.indexOf('按件运价')).find('div').removeClass('borderError');
                	}
        		}else{
        			checkflag = true;
        			$('#line_'+index+'_col_'+columns.indexOf('按件运价')).find('div').removeClass('borderError');
        		}
            }
        	if(hasFinance[index] && field == '办单费'){
            	if(value!=undefined && value!='' && !carryNum.test(value)){
            		layer.msg('办单费只能填写数字且大于0', {icon: 5,anim:6});
            		checkflag = false;
            		$('#line_'+index+'_col_'+columns.indexOf('办单费')).find('div').addClass('borderError');
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('办单费')).find('div').removeClass('borderError');
            	}
            }
        	if(hasFinance[index] && field == '保率'){
            	if(value!=undefined && value!='' && !carryNum.test(value)){
            		layer.msg('保率只能填写数字且小于等于0', {icon: 5,anim:6});
            		checkflag = false;
            	}else{
            		checkflag = true;
            		$('#line_'+index+'_col_'+columns.indexOf('保率')).find('div').removeClass('borderError');
            	}
            }
         });
        
        
        /*导入时校验*/
        function eachtableData(obj) {
        //try{
        	arrivePlace = [];
        	waybillID = [];
        	$.each(dataArr, function(index,ele){
        		for(items in ele){
        			if(items.indexOf('重量')>-1){
        				var weight = ele[items];
        			}
        			if(items.indexOf('体积')>-1){
        				var volumn = ele[items];
        			}
        		}
        		if(ele.模版版本号!= undefined){
                 	if(ele.模版版本号!=undefined && (ele.模版版本号=='20180604' || ele.模版版本号==20180604)){
                 		versionCheck = true;
                 	}
                 }
        		
            	/*判断运单编号*/
            	if(ele.运单编号 != undefined){
            		if(ele.运单编号.length == 10 || ele.运单编号.length == 12){
            			waybillID[index] = ele.运单编号;/*导入时的第一步校验*/
            		}else{
            			layer.open({
            				content: '运单编号仅支持10位或12位数字'
            			});
            			$('.layui-table-fixed').eq(0).find('tbody .laytable-cell-10001-运单编号').eq(index).addClass('borderError');
            		}
            	}
            	/*校验始发站*/
            	if(ele.始发站 == undefined || ele.始发站 != companyName || ele.始发站.length > 20 || ele.始发站.length == 0){
            		$('#line_'+index+'_col_'+columns.indexOf('始发站')).find('div').addClass('borderError');
            	}
            	/*校验到站*/
            	if(ele.到站 == undefined){
            		$('#line_'+index+'_col_'+columns.indexOf('到站')).find('div').addClass('borderError');
            	}else{
            		arrivePlace[index] = ele.到站;
            	}
            	/*校验到达网点*/
            	if(ele.到站网点 != undefined){
            		if(ele.到站网点.length > 20){
                		$('#line_'+index+'_col_'+columns.indexOf('到站网点')).find('div').addClass('borderError');
                	}
            	}
            	/*校验托运时间*/
            	var a = /^(\d{4})\/(\d{2})\/(\d{2})\s{1,}\d\d:\d\d$/;;
            	if(ele.托运时间 == undefined || !a.test(ele.托运时间)){
            		$('#line_'+index+'_col_'+columns.indexOf('托运时间')).find('div').addClass('borderError');
            	}
            	/*校验客户名称*/
            	if(ele.客户名称 == undefined || ele.客户名称.length>100){
            		$('#line_'+index+'_col_'+columns.indexOf('客户名称')).find('div').addClass('borderError');
            	}else{
            		customerName[index] = ele.客户名称;
            	}
            	/*校验客户编码*/
            	if(ele.客户编码 != undefined){
            		if(ele.客户编码.length > 20){
                		$('#line_'+index+'_col_'+columns.indexOf('客户编码')).find('div').addClass('borderError');
                	}
            	}
            	/*校验名称*/
            	if(ele.收货人名称 == undefined || ele.收货人名称.length>40){
            		$('#line_'+index+'_col_'+columns.indexOf('收货人名称')).find('div').addClass('borderError');
            	}
            	/*校验手机*/
            	var regexp = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
		 		var phoneReg = /^1\d{10}$/;
		 		/*if(ele.手机 == undefined){
		 			$('#line_'+index+'_col_'+columns.indexOf('手机')).find('div').addClass('borderError');
		 		}else{
		 			if(!regexp.test(ele.手机)){
		 				if(!phoneReg.test(ele.手机)){
		 					$('#line_'+index+'_col_'+columns.indexOf('手机')).find('div').addClass('borderError');
    		 			}
		 			}
		 		}*/
		 		if($.trim(ele.手机)){
		 			if(!regexp.test($.trim(ele.手机))){
		 				if(!phoneReg.test($.trim(ele.手机))){
		 					$('#line_'+index+'_col_'+columns.indexOf('手机')).find('div').addClass('borderError');
    		 			}
		 			}
		 		}
		 		/*校验省市区*/
		 		if(ele.省市区==undefined || ele.省市区.length>40){
		 			$('#line_'+index+'_col_'+columns.indexOf('省市区')).find('div').addClass('borderError');
		 		}
		 		/*校验始发地*/
		 		if(ele.始发地==undefined){
		 			$('#line_'+index+'_col_'+columns.indexOf('始发地')).find('div').addClass('borderError');
		 		}
		 		/*校验目的地*/
		 		if(ele.目的地==undefined){
		 			$('#line_'+index+'_col_'+columns.indexOf('目的地')).find('div').addClass('borderError');
		 		}
		 		/*校验省市区*/
		 		if(ele.省市区==undefined || ele.省市区.length>40){
		 			$('#line_'+index+'_col_'+columns.indexOf('省市区')).find('div').addClass('borderError');
		 		}
            	/*校验地址*/
		 		if(ele.地址 == undefined || ele.地址.length>40){
		 			$('#line_'+index+'_col_'+columns.indexOf('地址')).find('div').addClass('borderError');
		 		}
            	/*校验服务方式*/
	            if(ele.服务方式 == undefined || ele.服务方式 == ''){
	               $('#line_'+index+'_col_'+columns.indexOf('服务方式')).find('div').addClass('borderError');
	            }else if(ele.服务方式=='仓到站' || ele.服务方式=='仓到仓' || ele.服务方式=='站到仓' || ele.服务方式=='站到仓'){
	               fwfs[index] = ele.服务方式;
	            }
            	/*校验承运人*/
		 		if(ele.承运人!=undefined && ele.承运人.length>4){
                	$('#line_'+index+'_col_'+columns.indexOf('承运人')).find('div').addClass('borderError');
		 		}

        		/*console.log("导入时校验");
        		console.log(ele.等待托运人指令发货);*/
		 		/*校验等待托运人指令发货*/
            	if($.trim(ele.等待托运人指令发货)){
            		if($.trim(ele.等待托运人指令发货) != '是' && $.trim(ele.等待托运人指令发货) != '否'){
                		$('#line_'+index+'_col_'+columns.indexOf('等待托运人指令发货')).find('div').addClass('borderError');
            		}
        		}
            	/*校验运输方式*/
		 		if(ele.运输方式==undefined){
		 			$('#line_'+index+'_col_'+columns.indexOf('运输方式')).find('div').addClass('borderError');
		 		}
            	/*校验运输天数*/
            	var carryNum = /^\d*$/;
            	if(ele.运输天数 != undefined){
            		if(ele.运输天数 < 0 || !carryNum.test(ele.运输天数)){
                		$('#line_'+index+'_col_'+columns.indexOf('运输天数')).find('div').addClass('borderError');
                	}
            	}
            	/*校验是否返单*/
            	if(ele.是否返单 == undefined){
            		$('#line_'+index+'_col_'+columns.indexOf('是否返单')).find('div').addClass('borderError');
            	}
            	/*校验返单要求*/
            	if(ele.返单要求 != undefined){
            		if(ele.返单要求.length > 40){
                		$('#line_'+index+'_col_'+columns.indexOf('返单要求')).find('div').addClass('borderError');
                	}
            	}
            	/*校验付费方式*/
            	if(ele.付费方式 == undefined){
            		$('#line_'+index+'_col_'+columns.indexOf('付费方式')).find('div').addClass('borderError');
            	}
            	/*校验办单费*/
            	if(ele.办单费 != undefined){
            		if(Number(ele.办单费)<0){
                		$('#line_'+index+'_col_'+columns.indexOf('办单费')).find('div').addClass('borderError');
                	}
            	}
            	/*校验客户单号*/
            	if(ele.客户单号 != undefined){
            		/*if(!carryNum.test(ele.客户单号) || Number(ele.客户单号)>30 || Number(ele.客户单号)%1 != 0){
                		$('#line_'+index+'_col_21').find('div').addClass('borderError');
                	}*/
            	}
            	/*校验品名*/
            	if(ele.品名==undefined || ele.品名.length > 7){
                		$('#line_'+index+'_col_'+columns.indexOf('品名')).find('div').addClass('borderError');
                }
            	
            	/*校验型号*/
            	if(ele.型号 != undefined){
            		if(ele.型号.length>20){
                		$('#line_'+index+'_col_'+columns.indexOf('型号')).find('div').addClass('borderError');
                	}
            	}
            	/*校验件数*/
            	if(Number(ele.件数) == undefined || !carryNum.test(Number(ele.件数)) || Number(ele.件数)>100000 ){
            		$('#line_'+index+'_col_'+columns.indexOf('件数')).find('div').addClass('borderError');
            	}
            	/*校验包装*/
            	if(ele.包装 != undefined){
            		if(ele.包装.length>6){
                		$('#line_'+index+'_col_'+columns.indexOf('包装')).find('div').addClass('borderError');
                	}
            	}
            	/*校验重量（吨）*/
            	if(weight==undefined || Number(weight)>100000 || Number(weight)<0){
            		$('#line_'+index+'_col_'+columns.indexOf('重量（吨）')).find('div').addClass('borderError');
            	}
            	/*校验体积（立方）*/
            	if(volumn == undefined || Number(volumn)>100000 || Number(volumn)<0){
            		$('#line_'+index+'_col_'+columns.indexOf('体积（立方）')).find('div').addClass('borderError');
            	}
            	/*校验保价金额*/
            	if(ele.保价金额 != undefined){
            		if(!carryNum.test(ele.保价金额) || Number(ele.保价金额)<0){
                		$('#line_'+index+'_col_'+columns.indexOf('保价金额')).find('div').addClass('borderError');
                	}
            	}
            	/*校验计费方式*/
            	if(ele.计费方式 == undefined){
            		$('#line_'+index+'_col_'+columns.indexOf('计费方式')).find('div').addClass('borderError');
            	}
            	/*校验运价*/
            	if(ele.运价 != undefined){
            		if(!carryNum.test(ele.运价)){
                		$('#line_'+index+'_col_'+columns.indexOf('运价')).find('div').addClass('borderError');
                	}
            	}
            	
            	if(ele.是否录入财凭 != undefined && ele.是否录入财凭=='是'){
            		//console.log("我是彩屏录入导入时校验")
            		hasFinance[index] = true;
	            	/*校验重货运价*/
	            	if(ele.重货运价 != undefined){
	            		if(Number(ele.重货运价)<0){
	                		$('#line_'+index+'_col_'+columns.indexOf('重货运价')).find('div').addClass('borderError');
	                	}
	            	}
	            	/*校验轻货运价*/
	            	if(ele.轻货运价 != undefined){
	            		if(Number(ele.轻货运价)<0){
	                		$('#line_'+index+'_col_'+columns.indexOf('轻货运价')).find('div').addClass('borderError');
	                	}
	            	}
	            	/*校验按件运价*/
	            	if(ele.按件运价 != undefined){
	            		if(Number(ele.按件运价)<0){
	                		$('#line_'+index+'_col_'+columns.indexOf('按件运价')).find('div').addClass('borderError');
	                	}
	            	}
	            	/*校验代收货款*/
	            	if(ele.代收货款 != undefined){
	            		if(Number(ele.代收货款)<0){
	                		$('#line_'+index+'_col_'+columns.indexOf('代收货款')).find('div').addClass('borderError');
	                	}
	            	}
	            	
	            	/*校验上门取货费*/
	            	if(ele.上门取货费 != undefined){
	            		if(Number(ele.上门取货费)<0){
	                		$('#line_'+index+'_col_'+columns.indexOf('上门取货费')).find('div').addClass('borderError');
	                	}else if(Number(ele.上门取货费)>0){
	                		if(fwfs[index] && (fwfs[index]=='站到仓' || fwfs[index]=='站到站')){
	                			checkflag = false;
	                			$('#line_'+index+'_col_'+columns.indexOf('上门取货费')).find('div').addClass('borderError');
	            				layer.msg('站到仓、站到站，上门取货费必须为0', {icon: 5,anim:6});
	            			}else{
	            				$('#line_'+index+'_col_'+columns.indexOf('上门取货费')).find('div').removeClass('borderError');
	            				checkflag = true;
	            			}
	                	}
	            	}
	            	/*校验送货上门费*/
	            	if(ele.送货上门费 != undefined){
	            		if(Number(ele.送货上门费)<0){
	                		$('#line_'+index+'_col_'+columns.indexOf('送货上门费')).find('div').addClass('borderError');
	                	}else if(Number(ele.送货上门费)>0){
	                		if(fwfs[index] && (fwfs[index]=='仓到站' || fwfs[index]=='站到站')){
	                			checkflag = false;
	            				layer.msg('仓到站、站到站，送货上门费必须为0', {icon: 5,anim:6});
	            				$('#line_'+index+'_col_'+columns.indexOf('送货上门费')).find('div').addClass('borderError');
	            			}else{
	            				$('#line_'+index+'_col_'+columns.indexOf('送货上门费')).find('div').removeClass('borderError');
	            				checkflag = true;
	            			}
	                	}
	            	}
	            	/*校验其他费用*/
	            	if(ele.其他费用 == undefined){
	            		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').addClass('borderError');
	            	}else if(!otherfee.test(Number(ele.其他费用))){
	            		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').addClass('borderError');
	            	}else if(Number(ele.重货运价) == 0 && Number(ele.轻货运价) == 0 && Number(ele.按件运价) == 0 && Number(ele.代收货款) == 0 && Number(ele.办单费) == 0 && Number(ele.上门取货费)==0 && Number(ele.送货上门费)==0){
	            		if(Number(ele.其他费用)<0){
	                		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').addClass('borderError');
	                	}
	            	}
            	}else{
            		hasFinance[index] = false;
            		//console.log("我是导入时校验且录入财评为否")
            		/*校验其他费用*/
	            	if(!otherfee.test(Number(ele.其他费用))){
	            		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').addClass('borderError');
	            	}else{
	            		$('#line_'+index+'_col_'+columns.indexOf('其他费用')).find('div').removeClass('borderError');
	            	}
            	}
            })
//        }catch(err){//捕获异常信息err
//        	 layer.msg('系统将为您自动下载excl新模板 请用其导入数据!',  {
//					//icon: 0,
//				    time: 3500 
//				  }, function(){
//			    		window.location.href = ctx_static+'/upload/template/template.xls';
//
//				 });
//        	 
//    		//layer.msg('系统将为您自动下载excl新模板 请用其导入数据!',3000);
//        	 return;
//    	}
        }
        //alert("1");
        checkFeild (dataArr);
        InfotoSubmit(dataArr);
    })
}
/*ajax操作*/
var ajaxOperation = {
		_ajaxBatch: function(submitData, index, submitDataLen){
			/*
			 * submitData: 要提交的所有数据
			 * index： 当前索引
			 * submitDataLen: 提交数据总长度
			 * */
			elemArr = [];
			if(index == 0){
				submitData[index].isNewDoc = true;
			}else{
				submitData[index].isNewDoc = false;
			}
	        elemArr.push(submitData[index]);
	        var ajaxData = JSON.stringify(elemArr);
	        	EasyAjax.ajax_Post_Json({
	        		dataType: 'json',
	                url: base_url + '/transport/convey/insert/save/item',
	                data: ajaxData,
	                async: true,
	                errorHandler: function(){
	                	flag = false;
	                	var import_table = $('.layui-table-main').eq(0).find('tr');
	                	var fixed_col = $('.layui-table-fixed').eq(0).find('.layui-table-body tr');
	                	/*失败时依然需要操作*/
	                	returnfalseArr.push(submitData[index]);
	                	ajaxOperation._dataOperation (returnfalseArr, '.if-failNodata', '.fail-data', '#false-list');
	                	/*导入数据展示操作*/
	                	import_table.eq(0).remove();    
	                	fixed_col.eq(0).remove();
	                	$('.import-data-number').html(import_table.length-1);//数据行数展示
	                	if($('.import-data-number').html() == 0){
	                		$('.ifImportNodata').find('.no-data-copy').html('提交成功');
	                		$('.toCheck').hide();
	                        $('.ifImportNodata').fadeIn();
	                        $('#demo').next().hide();
	                	}
	                	index ++;
		                if(index < submitDataLen){
		                	setTimeout(function(){
		                		ajaxOperation._ajaxBatch(submitData,index, submitDataLen);
		                    },500);
		                }
	                }
	            },
	            function (data) {
	            	var import_table = $('.layui-table-main').eq(0).find('tr');
	            	var fixed_col = $('.layui-table-fixed').eq(0).find('.layui-table-body tr');
	            	if(data.success){
	            		/*成功的操作*/
		            	returntrueArr.push(submitData[index]);
			            ajaxOperation._dataOperation(returntrueArr, '.if-successNodata', '.success-data', '#success-list');
	            	}else{
	            		/*失败的操作*/
		            	returnfalseArr.push(submitData[index]);
		            	ajaxOperation._dataOperation (returnfalseArr, '.if-failNodata', '.fail-data', '#false-list');
	            	}
	                /*ajaxOperation._dataOperation (returntrueArr, '.if-successNodata', '.success-data');*/
	                /*导入数据展示操作*/
                	import_table.eq(0).remove();
                	fixed_col.eq(0).remove();
                	$('.import-data-number').html(import_table.length-1);//数据行数展示
                	if($('.import-data-number').html() == 0){
                		$('.ifImportNodata').find('.no-data-copy').html('提交成功');
                		$('.toCheck').hide();
                        $('.ifImportNodata').fadeIn();
                        $('#demo').next().hide();
                	}
                	/*逐条提交*/
	                index ++;
	                if(index < submitDataLen){
	                	setTimeout(function(){
	                		ajaxOperation._ajaxBatch(submitData,index, submitDataLen);
	                    },500);
	                }
	            });
		},/*成功失败的操作*/
		_dataOperation: function(data, dataitem, copyFont, tableContent){
			/*
			 * data: 要渲染的数据
			 * dataitem: 无数据时显示
			 * copyFont: 显示数据条数
			 * tableContent: 要渲染的table条数
			 * */
			var failData = [];/*中文字段转化英文*/
		    $.each(data, function(index, ele){
		    	var dataDetail = {};
		    	for(var item in ele){
		    		var databasic = ele[item];
		    		for(var key in dataChange){
		    			if(item == dataChange[key]){
		    				item = key;
		    				dataDetail[item] = databasic;
		    			}
		    		}
		    	}
		    	failData.push(dataDetail);
		    })
		    if(data.length != 0){
		    	tableRender (failData,tableContent, false);
		    	$(dataitem).hide();
		    	if(dataitem == '.if-failNodata'){
		    		$('.toExport').show();
		    	}else{
		    		$('.toExport-success').show();
		    		
		    	}
		    	$(copyFont).html(failData.length);
		    }else{
		    	$(dataitem).show();
		    	$(copyFont).html(0);
		    	if(dataitem == '.if-failNodata'){
		    		$('.toExport').hide();
		    		
		    	}else{
		    		$('.toExport-success').hide();
		    		
		    	}
		    }
		}
}
/*提交操作*/
function InfotoSubmit(dataArr){
    $('.toSubmit').unbind().on('click', function() {
    	//console.log("说明!!");
    	if(flag){
    		var isLogin = isTimeOut(false);
        	if(!isLogin){
        		return false;
        	}
    		flag = false;
    		returntrueArr = [];returnfalseArr = [];
        	$(this).addClass('layui-btn-disabled');
        	var submitDate = [];
            $.each(dataArr, function(index, ele){
            	var dataDetail = {};
            	for(var key in ele){
            		var beforeKey = ele[key];
            		key = dataChange[key]; 
            		dataDetail[key] = beforeKey;
                }
            	submitDate.push(dataDetail);
            })
            var submitDataLen = submitDate.length;
            ajaxOperation._ajaxBatch(submitDate,0,submitDataLen);
    	}
      })
}
/*数组查重*/
function removeDuplicatedItem(ar) {
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
/*校验*/
var checkHandlers = {
		_Beforecheck: function(commitData){
			/*点击校验提交到后台前本地会再校验一次*/
			$.each(commitData, function(index,ele){
				for(items in ele){
        			if(items.indexOf('重量')>-1){
        				var weight = ele[items];
        			}
        			if(items.indexOf('体积')>-1){
        				var volumn = ele[items];
        			}
        		}
	        	/*判断运单编号*/
	        	if(ele.运单编号 != undefined &&  ele.运单编号 != ""){
	        		if(ele.运单编号.length == 10 || ele.运单编号.length == 12){
	        			if($('.layui-table-fixed').eq(0).find('tbody .laytable-cell-10001-运单编号').eq(index).hasClass('borderError')){
	        				layer.msg('还有未修改的运单编号', {icon: 5,anim:6});
	        				checkflag = false;
	        			}
	        		}else{
	        			layer.msg('还有未修改的运单编号', {icon: 5,anim:6});
	        			checkflag = false;
	        		}
	        	}
	        	if(ele.始发站 != companyName || ele.始发站 == undefined || ele.始发站.length > 20 || ele.始发站.length == 0){
	        		layer.msg('请检查始发站', {icon: 5,anim:6});
	        		checkflag = false;
            	}
	        	if(ele.到站 == undefined){
	        		layer.msg('还有未填写的到站地点', {icon: 5,anim:6});
	        		checkflag = false;
	        	}
	        	/*校验到达网点*/
            	if(ele.到站网点 != undefined){
            		if(ele.到站网点.length > 20){
            			layer.msg('请检查到站网点', {icon: 5,anim:6});
    	        		checkflag = false;
                	}
            	}
            	/*校验托运时间*/
            	var a = /^(\d{4})\/(\d{2})\/(\d{2})\s{1,}\d\d:\d\d$/;;
            	if(ele.托运时间 == undefined || !a.test(ele.托运时间)){
            		layer.msg('请检查托运时间', {icon: 5,anim:6});
	        		checkflag = false;
            	}
            	/*校验客户名称*/
            	if(ele.客户名称 == undefined || ele.客户名称.length>100){
            		layer.msg('请检查客户名称', {icon: 5,anim:6});
	        		checkflag = false;
            	}
            	/*校验客户编码*/
            	if(ele.客户编码 != undefined){
            		if(ele.客户编码.length > 20){
                		layer.msg('请检查客户编码', {icon: 5,anim:6});
    	        		checkflag = false;
                	}
            	}
            	/*校验名称*/
            	if(ele.收货人名称 == undefined || ele.收货人名称.length>40){
            		layer.msg('收货人名称不能为空且长度不超过40', {icon: 5,anim:6});
	        		checkflag = false;
            	}
            	/*校验手机*/
            	var regexp = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
		 		var phoneReg = /^1\d{10}$/;
		 		/*if(ele.手机 == undefined){
		 			layer.msg('请检查手机格式', {icon: 5,anim:6});
	        		checkflag = false;
		 		}else{
		 			if(!regexp.test(ele.手机)){
		 				if(!phoneReg.test(ele.手机)){
		 					layer.msg('请检查手机', {icon: 5,anim:6});
			        		checkflag = false;
    		 			}
		 			}
		 		}*/
	 			if($.trim(ele.手机)){
		 			if(!regexp.test($.trim(ele.手机))){
		 				if(!phoneReg.test($.trim(ele.手机))){
		 					layer.msg('请检查手机', {icon: 5,anim:6});
			        		checkflag = false;
    		 			}
		 			}
		 		}
		 		/*校验省市区*/
		 		if(ele.省市区==undefined){
		 			layer.msg('省市区不能为空', {icon: 5,anim:6});
		 		}else{
	            	if(ele.省市区 == undefined || ele.省市区.length>40){
	            		layer.msg('请检查省市区', {icon: 5,anim:6});
		        		checkflag = false;
	            	}
		 		}
            	/*校验地址*/
		 		if(ele.地址!=undefined){
	            	if(ele.地址 == undefined || ele.地址.length>40){
	            		layer.msg('请检查地址', {icon: 5,anim:6});
		        		checkflag = false;
	            	}
		 		}
            	/*校验服务方式*/
            	if(ele.服务方式 == undefined || ele.服务方式 == ''){
            		layer.msg('服务方式不能为空', {icon: 5,anim:6});
	        		checkflag = false;
            	}else if(ele.服务方式=='仓到站' || ele.服务方式=='仓到仓' || ele.服务方式=='站到仓' || ele.服务方式=='站到仓'){
            		fwfs[index] = ele.服务方式;
            	}
            	/*校验承运人*/
            	if(ele.承运人 != undefined){
            		if(ele.承运人.length>4){
            			layer.msg('承运人长度不能超过4', {icon: 5,anim:6});
    	        		checkflag = false;
                	}
            	}
        		/*console.log("点击校验之前本覅校验");
        		console.log(ele.等待托运人指令发货);*/
            	/*校验等待托运人指令发货*/
            	if($.trim(ele.等待托运人指令发货)){
            		//console.log("等待托运人指令发货");
            		if($.trim(ele.等待托运人指令发货) != '是' && $.trim(ele.等待托运人指令发货) != '否'){
            			layer.msg('等待托运人指令发货只能填是或否', {icon: 5,anim:6});
    	        		checkflag = false;
            		}
        		}
            	/*校验运输方式*/
            	if(ele.运输方式 == undefined){
            		layer.msg('运输方式不能为空', {icon: 5,anim:6});
	        		checkflag = false;
            	}
            	/*校验始发地*/
            	if(ele.始发地 == undefined){
            		layer.msg('始发地不能为空', {icon: 5,anim:6});
            		checkflag = false;
            	}
            	/*校验目的地*/
            	if(ele.目的地 == undefined){
            		layer.msg('目的地不能为空', {icon: 5,anim:6});
            		checkflag = false;
            	}
            	/*校验运输天数*/
            	var carryNum = /^\d*$/;
            	if(ele.运输天数 != undefined){
            		if(ele.运输天数 < 0 || !carryNum.test(ele.运输天数)){
            			layer.msg('请检查运输天数', {icon: 5,anim:6});
    	        		checkflag = false;
                	}
            	}
            	/*校验是否返单*/
            	if(ele.是否返单 == undefined){
            		layer.msg('是否返单不能为空', {icon: 5,anim:6});
	        		checkflag = false;
            	}
            	/*校验返单要求*/
            	if(ele.返单要求 != undefined){
            		if(ele.返单要求.length > 40){
            			layer.msg('返单要求长度不能超过40', {icon: 5,anim:6});
    	        		checkflag = false;
                	}
            	}
            	/*校验付费方式*/
            	if(ele.付费方式 == undefined){
            		layer.msg('付费方式不能为空', {icon: 5,anim:6});
	        		checkflag = false;
            	}
            	/*校验客户单号*/
            	if(ele.客户单号 != undefined){
            		/*if(!carryNum.test(ele.客户单号) || Number(ele.客户单号)>30 || Number(ele.客户单号)%1 != 0){
            			layer.msg('请检查客户单号', {icon: 5,anim:6});
    	        		checkflag = false;
                	}*/
            	}
            	/*校验品名*/
            	if(ele.品名==undefined || ele.品名.length > 7){
            		layer.msg('请检查品名', {icon: 5,anim:6});
	        		checkflag = false;
            	}
            	/*校验型号*/
            	if(ele.型号 != undefined){
            		if(ele.型号.length>20){
            			layer.msg('型号长度不能超过20个字', {icon: 5,anim:6});
    	        		checkflag = false;
                	}
            	}
            	/*校验件数*/
            	if(!carryNum.test(Number(ele.件数)) || Number(ele.件数)>100000 || Number(ele.件数) == '' ){
            		layer.msg('请检查件数', {icon: 5,anim:6});
	        		checkflag = false;
            	}
            	/*校验包装*/
            	if(ele.包装 != undefined){
            		if(ele.包装.length>6){
            			layer.msg('包装不能超过6个字', {icon: 5,anim:6});
    	        		checkflag = false;
                	}
            	}
            	/*校验重量（吨）*/
            	if(weight==undefined||Number(weight)>100000 || Number(weight)<0){
            		layer.msg('请检查重量', {icon: 5,anim:6});
	        		checkflag = false;
            	}
            	/*校验体积（立方）*/
            	if(volumn==undefined||Number(volumn)>100000 || Number(volumn)<0 || volumn == ''){
            		layer.msg('请检查体积', {icon: 5,anim:6});
	        		checkflag = false;
            	}
            	/*校验保价金额*/
            	if(ele.保价金额 != undefined){
            		if(Number(ele.保价金额)<0){
            			layer.msg('请检查保价金额', {icon: 5,anim:6});
    	        		checkflag = false;
                	}
            	}
            	/*校验计费方式*/
            	if(ele.计费方式 == undefined || ele.计费方式 == ''){
            		layer.msg('计费方式不能为空', {icon: 5,anim:6});
	        		checkflag = false;
            	}
            	
            	/*校验其他费用*/
            	if(ele.是否录入财凭 != undefined && ele.是否录入财凭=='是'){
            		//console.log("我是提交校验之前的前台校验");
            		hasFinance[index] = true;
            		if(ele.其他费用 == undefined || ele.其他费用 == ''){
                		layer.msg('其他费用不能为空', {icon: 5,anim:6});
    	        		checkflag = false;
                	}else if(!otherfee.test(Number(ele.其他费用))){
                		layer.msg('其他费用只能填写带两位小数数字', {icon: 5,anim:6});
    	        		checkflag = false;
                	}else if(ele.其他费用<0){//且不能小于0
                		layer.msg('其他费用不能小于0', {icon: 5,anim:6});
                		checkflag = false;
                	}else if(Number(ele.重货运价) == 0 && Number(ele.轻货运价) == 0 && Number(ele.按件运价) == 0 && Number(ele.代收货款) == 0 && Number(ele.办单费) == 0 && Number(ele.上门取货费)==0 && Number(ele.送货上门费)==0){
                		if(Number(ele.其他费用)<0){
                			layer.msg('其他费用应该大于0', {icon: 5,anim:6});
        	        		checkflag = false;
                    	}
                	}else{
                		/*if(Number(ele.其他费用)==0){
                			layer.msg('其他费用应该大于0', {icon: 5,anim:6});
        	        		checkflag = false;
                    	}*/
                	}
            		
            		/*校验代收货款*/
                	if(ele.代收货款 != undefined){
                		if(Number(ele.代收货款)<0){
                			layer.msg('请检查代收货款', {icon: 5,anim:6});
        	        		checkflag = false;
                    	}
                	}
            		
            		/*校验重货运价*/
                	if(ele.重货运价 != undefined){
                		if(Number(ele.重货运价)<0){
                			layer.msg('请检查重货运价', {icon: 5,anim:6});
        	        		checkflag = false;
                    	}
                	}
                	/*校验轻货运价*/
                	if(ele.轻货运价 != undefined){
                		if(Number(ele.轻货运价)<0){
                			layer.msg('请检查轻货运价', {icon: 5,anim:6});
        	        		checkflag = false;
                    	}
                	}
                	/*校验按件运价*/
                	if(ele.按件运价 != undefined){
                		if(Number(ele.按件运价)<0){
                			layer.msg('请检查按件运价', {icon: 5,anim:6});
        	        		checkflag = false;
                    	}
                	}
                	/*校验上门取货费*/
                	if(ele.上门取货费 != undefined){
                		if(Number(ele.上门取货费)<0){
                			layer.msg('请检查上门取货费', {icon: 5,anim:6});
        	        		checkflag = false;
                    	}else if(Number(ele.上门取货费)>0){
	                		if(fwfs[index] && (fwfs[index]=='站到仓' || fwfs[index]=='站到站') && value>0){
	                			checkflag = false;
	                			$('#line_'+index+'_col_'+columns.indexOf('上门取货费')).find('div').addClass('borderError');
	            				layer.msg('站到仓、站到站，上门取货费必须为0', {icon: 5,anim:6});
	            			}else{
	            				$('#line_'+index+'_col_'+columns.indexOf('上门取货费')).find('div').removeClass('borderError');
	            				checkflag = true;
	            			}
	                	}
                	}
                	/*校验送货上门费*/
                	if(ele.送货上门费 != undefined){
                		if(Number(ele.送货上门费)<0){
                			layer.msg('请检查送货上门费', {icon: 5,anim:6});
        	        		checkflag = false;
                    	}else if(Number(ele.送货上门费)>0){
	                		if(fwfs[index] && (fwfs[index]=='仓到站' || fwfs[index]=='站到站') && value>0){
	                			checkflag = false;
	            				layer.msg('仓到站、站到站，送货上门费必须为0', {icon: 5,anim:6});
	            				$('#line_'+index+'_col_'+columns.indexOf('送货上门费')).find('div').addClass('borderError');
	            			}else{
	            				$('#line_'+index+'_col_'+columns.indexOf('送货上门费')).find('div').removeClass('borderError');
	            				checkflag = true;
	            			}
	                	}
                	}
                	/*校验办单费*/
                	if(ele.办单费 != undefined){
                		if(Number(ele.办单费)<0){
                			layer.msg('请检查办单费', {icon: 5,anim:6});
        	        		checkflag = false;
                    	}
                	}
                	/*校验办单费*/
                	if(ele.保率 != undefined){
                		if(Number(ele.保率)>1 ||Number(ele.保率)<0 ){
                			layer.msg('保率不能小于0或大于1', {icon: 5,anim:6});
        	        		checkflag = false;
                    	}
                	}
            	}else{
            		hasFinance[index] = false;
            		if(!otherfee.test(Number(ele.其他费用))){
                		layer.msg('其他费用只能为数字或含两位小数', {icon: 5,anim:6});
    	        		checkflag = false;
                	}
            	}
	        })
	        if(!checkflag){
	        	//layer.msg('其他费用应该大于0');
				return false;
			}else{
				return true;
			}
		}
		,_checkAjax: function(i,obj,len,tabledata){
			var submitdata = {ydbhid: obj[i].运单编号, daozhan: obj[i].到站, fhdwmch: obj[i].客户名称, khbm: obj[i].客户编码,fhshj :obj[i].托运时间}
			submitdata = JSON.stringify(submitdata);
				EasyAjax.ajax_Post_Json({
    	    		dataType: 'json',
    	            url: base_url + '/transport/convey/batch/checkData',
    	            data: submitdata,
    	            cancelPage: true,
    	            alertMsg: true,
    	            async: true,
    	            beforeHandler: function(){
                    	/*loading = layer.load(0,{
                    		shade: [0.5,'#fff']
                    	})*/
                    }
    	        },
    	        function (data) {
    	        	var needAlert = false;
    	        	failoperation = [];/*如果存在返回错误的情况*/
    	        	if(data.resultCode == 400){
    	        		failoperation.push(obj[i]);
    	        		errorres = false;
    	        	}else{
    	        		if(data.ydbhid){/*运单编号*/
	       	        		 var replayData = data.ydbhid;
	       	        		 if(obj[i].LAY_TABLE_INDEX){
	       	        			tabledata[obj[i].LAY_TABLE_INDEX].运单编号 = data.ydbhid/*数据重新赋值*/
	       	        			reshowData(obj[i].LAY_TABLE_INDEX,data.ydbhid);
	       	        		 }else{
	       	        			tabledata[i].运单编号 = data.ydbhid/*数据重新赋值*/
	       	        			reshowData(i,data.ydbhid);
	       	        		 }
	       	        	 }
	       	        	 if(data.daozhan){/*到站*/
	       	        		 errorData = data.daozhan;
	       	        		 reshowArriveData(obj[i].到站,data.daozhan);
	       	        		 errorres = false;
	       	        	 }
	       	        	 if(data.fhdwmch){/*客户名称*/
	       	        		 TemporaryCustomer = data.fhdwmch;
	       	        		 TemporaryCustomerData(obj[i].客户名称,data.fhdwmch);
	       	        		 errorres = false;
	       	        	 }
	       	        	 if(data.khbm){/*客户编码*/
	       	        		 CustomerCode = data.khbm;
	       	        		 CustomerCodeData(i,data.khbm);
	       	        		 errorres = false;
	       	        	 }
	       	        	 if(data.fhshj){/*发货时间*/
	       	        		errorres = false;
	       	        		needAlert = true;
	       	        	 }
	    	        }
    	        	if(errorres && i == len-1){
       	        		$('.toSubmit').removeClass('layui-btn-disabled');
   	        			$('.bar-process').fadeOut(function(){
   	        				element.progress('demo', '0%');
   	        				layer.open({
	       		        		 content: '已通过校验，<br/>请提交'
	       		        	});
   	        			});
       	        		 flag = true;
       	        		 $('.toCheck').addClass('layui-btn-disabled');
       	        	 }
       	        	 if(!errorres && i == len-1){
       	        		if(needAlert && data.fhshj){
       	        			$('.bar-process').fadeOut(function(){
    	       	        		element.progress('demo', '0%');
    	       	        		layer.msg(data.fhshj, {
    	       	        			icon: 5,
    	       	        			anim:6
    	       	        		});
           	        		});
       	        		}else{
       	        			$('.bar-process').fadeOut(function(){
    	       	        		element.progress('demo', '0%');
    	       	        		layer.open({
    	       		        		 content: '存在失败数据，<br/>请检查数据并重新提交'
    	       		        	});
           	        		});
       	        		}
       	        	 }
       	        	i ++;
	                if(i < len){
	                	setTimeout(function(){
	                		checkHandlers._checkAjax(i,obj, len, tabledata)
	                    },100);
	                }
	                processNum += parseInt(percent); /*进度条*/
    	        	if(processNum > 99){
						element.progress('demo', '99%');
					}else{
						element.progress('demo', processNum + '%');
					}
    	        });
		}
}

function checkFeild(tabledata){
	$('.toCheck').unbind('click').on('click',function(){
		if(!versionCheck){
			layer.msg('批量导入模版不是最新，请下载最新模版！', {icon: 5,anim:6});
		}else if(!flag){
			checkNUM ++;
			var isLogin = isTimeOut(false);
	    	if(!isLogin){
	    		return false;
	    	}
			errorres = true;
			processNum = 0;
			if(failoperation.length != 0 && checkNUM > 1 ){
				var commitData = failoperation;
			}else{
				var commitData = tabledata;
			}
			percent = Math.ceil(100 / commitData.length);
			element.progress('demo', '0%');
			var checkResult = checkHandlers._Beforecheck(commitData);/*获取到本地校验结果true / false*/
			if(!checkResult){
				return false;
			}
			$('.bar-process').fadeIn();
			var first_data = commitData[0];/*从第一条数据开始提交*/
			var commitdataLen = commitData.length;/*数据长度*/
			checkHandlers._checkAjax(0,commitData, commitdataLen, tabledata);
		}
	})
}

/*运单编号重复元素处理*/
function reshowData(elem,reason) {
	$('.layui-table-fixed').eq(0).find('tbody .layui-table-cell').each(function(index,ele){ 
			if(index == elem){
				if(reason.indexOf('运单号') == -1){
					$(ele).html(reason);
					$('.layui-table-fixed').eq(0).find('tbody .laytable-cell-10001-运单编号').eq(index).removeClass('borderError');
				}else{
					$(ele).addClass('borderError').unbind('click')
				 		.one('click',function(){
		   				  layer.msg(reason,{
			        		   anim: 6,
			        		   icon: 5
			        	  });
					  });
					errorres = false;
				}
				 return false;
	    	}
	 })
}
/*到站问题元素处理*/
function reshowArriveData(ele,reason) {
	var tableArrive = $('.layui-table-fixed').eq(0).prev().find('td[data-field="到站"]');
	$(tableArrive).each(function(index, elem){
		var eachHtml = $(elem).find('div');
			 if(eachHtml.html() == ele){
				 eachHtml.addClass('borderError').unbind('click')
				 		.one('click',function(){
		   				  layer.msg(reason,{
			        		   anim: 6,
			        		   icon: 5
			        	  });
					  });
	    	}
	})
}

/*客户名称问题元素处理*/
function TemporaryCustomerData(ele,reason) {
	var tableArrive = $('.layui-table-fixed').eq(0).prev().find('td[data-field="客户名称"]');
	$(tableArrive).each(function(index, elem){
		var eachHtml = $(elem).find('div');
			 if(eachHtml.html() == ele){
				 eachHtml.addClass('borderError').unbind('click')
				 		.one('click',function(){
			   				  layer.msg(reason,{
				        		   anim: 6,
				        		   icon: 5
				        	  });
						 });
	    	}
	})
}

/*客户编码问题处理*/
function CustomerCodeData(ele,reason) {
	var tableArrive = $('.layui-table-fixed').eq(0).prev().find('td[data-field="客户编码"]');
	$(tableArrive).each(function(index, elem){
		var eachHtml = $(elem).find('div');
			 if(index == ele){
				 eachHtml.addClass('borderError').unbind('click')
				 		.one('click',function(){
			   				  layer.msg(reason,{
				        		   anim: 6,
				        		   icon: 5
				        	  });
						 });
	    	}
	})
}


/***********************键盘控制表格上下左右切换键***************************/
	var currentLine; 
	document.onkeydown = function(e)  
	{ 
	  e = window.event || e; 
	  switch(e.keyCode)  
	  { 
	  	case 9:
	  		e.preventDefault();
	    	Ordinate ++;
	    	changeItem(true);
	    	break;
	    case 38: 
	    	Abscissa --;
	      changeItem(true); 
	      break; 
	    case 40: 
	    	Abscissa ++; 
	      changeItem(true); 
	      break; 
	    case 39:
	    	Ordinate ++;
	    	changeItem(true);
	    	break;
	    case 37:
	    	Ordinate --;
	    	changeItem(true);
	      break; 
	  } 
	} 
	function selectTR(item, trLength)
	{
		  /*trLength 获取所有的tr集合*/
		  /*获取当前元素的父元素*/
		  v_ = item.parent(); //tr
		  /*获取当前元素的id名*/
		  v_id = item.attr('id');// td的id
		  /*截取id名的索引*/
		  v_idarr =  v_id.split("_");
		  Abscissa = parseInt(v_idarr[1]); /**横坐标  行*/
		  Ordinate = parseInt(v_idarr[3]); /**纵坐标  列*/
	 //alert(currentLine);
	      changeItem(false);
	}
	//改变选择项目 
	function changeItem(bool) 
	{ 
		var trItems = $(".layui-table-main").eq(0).find('table').find('tr');
		if(Abscissa < 0) 
	    	  Abscissa = trItems.length - 1; 
		if(Abscissa == trItems.length) 
	    	  Abscissa = 0; 
		var currTR = $('#lineIndex_'+Abscissa);
		var currTDLength = currTR.find('td').length;
		if(Ordinate < 1) 
			Ordinate = currTDLength - 1; 
		if(Ordinate == currTDLength) 
			Ordinate = 1; 
		var currDOM = $('#line_'+Abscissa+'_col_'+Ordinate);
		trItems.removeClass('layui-table-hover');
		currTR.addClass('layui-table-hover');
		if(bool){
			currDOM.find('div').click();
		}
	} 

function tdClick() {
	trLength = $(".layui-table-main").eq(0).find('table').find('tr'); /*tr坐标*/
	$(trLength).each(function(index,item){
		var idItems = $(item).children();
		idItems.each(function(index,elem){
			$(elem).on('click',function(){
				var item = $(this);
				selectTR(item, trLength)
			})
		})
	})
}