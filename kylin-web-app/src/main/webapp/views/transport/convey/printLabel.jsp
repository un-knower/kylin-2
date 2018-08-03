<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>&nbsp;</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/sendSignCheck.css?12"/>
    <link rel="stylesheet" href="${ctx_static}/transport/convey/css/printCode.css"/>
</head>
<body>
<div id='rrapp' v-cloak>
        <div  class='list-top'>
			<h2 class='head-middle head-tag title send-title'><span style="font-size:25px !important">客户标签</span>
			   
			</h2>
			
			<div class="layui-form-item">
			    <span class="itemsName">打印份数</span>
			    <div class="layui-input-block">
			      <input type="radio" name="selRadio" id="selBtn1" class="selectBtn"><span>自动份数（按件计算，默认最多打印100份）</span>
			      <input type="radio" name="selRadio" id="selBtn2" class="selectBtn"><span>指定份数</span>
			      <input type="text" class="selText" v-model="assFirst">-<input type="text" class="selText" v-model="assSecond">
			      <input type="radio" name="selRadio" id="selBtn3" class="selectBtn"><span>指定打印第几份</span>
			      <input type="text" id="selText3" class="selText" v-model="assCurrent">
			    </div>
			    <button class="layui-btn layui-btn-normal" id="printBtn" @click='createBarcode(".barcode",orderNum,"B")' >打印</button>
			</div>
			
		</div>
		<!-- 表格 -->
		<table class='list-table send-table lookBox' style="margin:0 auto;width:60%;">
			  <tr >
			  	<th><span>序号</span></th>
			  	<th><span>品名</span></th>
			  	<th><span>件数</span></th>
			  	<th><span>型号</span></th>
			  </tr>
			  <tr v-for="(v,k) in tableList" >
			    <td><span>{{k+1}}</span></td>
			    <td><span>{{v.pinming}}</span></td>
			    <td><span>{{v.jianshu}}</span></td>
			    <td><span>{{v.xh}}</span></td>
			  </tr>
	    </table>
	    <br/>
	    <br/>
	    <br/>
	    <!-- 打印预览 -->
	    <table class="tablePreview lookBox" style="margin:0 auto;width:30%;">
			  <tr>
			    <td style="width:10%;text-align: left;"><span class="preLabelName">远成物流</span></td>
			    <td style="width:40%;text-align: center;"><span>{{sysTime}}</span></td>
			    <td style="width:10%;text-align: left;"><span class="preLabelName">件数</span></td>
			    <td style="width:40%;text-align: center;"><span>{{totalJianShu}}</span></td>
			  </tr>
			  <tr>
			    <td style="width:10%;text-align: left;"><span class="preLabelName">始发城市</span></td>
			    <td style="width:40%;text-align: center; "><span>{{previewList.beginPlacename}}</span></td>
			    <td style="width:10%;text-align: left; "><span class="preLabelName">目的城市</span></td>
			    <td style="width:40%;text-align: center; "><span>{{previewList.endPlacename}}</span></td>
			  </tr>
			  <tr>
			    <td style="width:10%;text-align: left;"><span class="preLabelName">始发站点</span></td>
			    <td style="width:40%;text-align: center;"><span>{{previewList.fazhan}}</span></td>
			    <td style="width:10%;text-align: left;"><span class="preLabelName">目的站点</span></td>
			    <td style="width:40%;text-align: center; "><span>{{previewList.dzshhd}}</span></td>
			  </tr>
			  <tr style="boder-bottom:1px solid #eee;">
			    <td colspan="4" style="width:100%;text-align: center;font-weight:600;">*<span>{{orderNum}}</span>-<span>1</span>*</td>
			  </tr>
			   <tr>
			    <td style="width:10%;text-align: left;"><span class="preLabelName">配送地址</span></td>
			    <td colspan="3" style="width:90%;text-align: center;"><span>{{previewList.fhdwdzh}}</span></td>
			  </tr>
	    </table>
	    
	    <!-- 打印页面 -->
		<div class="printBox" style="display:none;">
			<ul class="printUl" v-for="(v,k) in circulJianShu">
	              <li class="dataBox">
	                   <div class="labelTime"><span>{{sysTime}}</span></div>
	                   <div class="labelName"><span style="line-height:30px;">件数</span></div>
	                   <div class="labelValue"><span><b class="currentNum">{{k+1}}</b><b>/</b><b>{{totalJianShu}}</b></span></div>
	              </li>
	              <li class="dataBox">
	                   <div class="labelName"><span>始发城市</span></div>
	                   <div class="labelValue"><span>{{previewList.beginPlacename}}</span></div>
	                   <div class="labelName"><span>目的城市</span></div>
	                   <div class="labelValue"><span>{{previewList.endPlacename}}</span></div>
	              </li>
	              <li class="dataBox">
	                   <div class="labelName"><span>始发站点</span></div>
	                   <div class="labelValue"><span>{{previewList.fazhan}}</span></div>
	                   <div class="labelName"><span>目的站点</span></div>
	                   <div class="labelValue"><span>{{previewList.dzshhd}}</span></div>
	              </li>
	              <li class="codeBox">
	              	   <div class="barcode"></div>
	                   <div class="barCodeNum"><span>*</span><span>{{orderNum}}</span><span>-</span><span class="currentNum">{{k+1}}</span><span>*</span></div>
	              </li>
	              <li class="dataBox">
	                   <div class="labelName"><span>配送地址</span></div>
	                   <div class="addValue"><span>{{previewList.fhdwdzh}}</span></div>
	              </li>
             </ul>
		</div>
		
</div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
	<script src="${ctx_static}/transport/convey/js/printCode.js"></script>
    <script type="text/javascript"> 
    var baseValue=getUrlParam("ydbhid");
     //自动在打印之前执行
     window.onbeforeprint = function(){	
    	$(".lookBox").hide();
    	$(".list-top").hide();
    	$(".printBox").show();
    
    }

    //自动在打印之后执行
    window.onafterprint = function(){
    	$(".lookBox").show();
    	$(".list-top").show();
    	$(".printBox").hide();
    }  
     
  //获取url中的参数
    function getUrlParam(name) {
    	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    	if (r != null) return unescape(r[2]); return null; //返回参数值
    }
    var baseValue=getUrlParam("ydbhid");
    var vm = new Vue({
    	el:'#rrapp',
    	data:{
    		flag:true,
    		assFirst:000,   //指定份数first
    		assSecond:000,   //指定份数Second
    		assCurrent:1,    //指定打印第几份
    		orderNum:0,   //订单编号
    		sysTime:"",   //时间
    		totalJianShu:0,  //总件数
    		circulJianShu:0,  //循环件数
    		tableList:[],  //表格列表
    		previewList:{}  //预览列表
    	},
    	created: function () {
    		// `this` 指向 vm 实例
    		this.searchInfo();
    	},
 		watch:{  
 				
        },
    	methods: {
    		  searchInfo:function(){
    			  var _this=this;

  			  	  var baseValue=getUrlParam("ydbhid");
  			  	  this.orderNum = baseValue;
    			  layui.use('layer', function(){
    				$.ajax({
    					url: base_url + '/labelPrint/findCustomerLabel/'+baseValue,
    					type: 'get',
    					dataType: 'JSON',
    					contentType: 'application/json',
    					data: JSON.stringify(baseValue),
    					beforeSend: function(){
    						 loading = layer.load(0,{
    							shade: [0.5,'#fff']
    						}) 
    					},
    					complete:function(){
    						layer.close(loading);
    					},
    					success: function(data){
    						_this.tableList = data.data.list;
    						//获取数据列表第一个对象
    						_this.previewList = data.data.list[0];
    						_this.totalJianShu = data.data.totalJianShu;
    						//时间
    						var str = new Date();
    						 _this.sysTime=getMyDate(str);
    						//循环件数
    						_this.circulJianShu = data.data.totalJianShu;
    						
    						
    					},
    					error: function(data){
    						console.log(data);
    					}
    				});
    			  });
    		  },
    		  createBarcode:function(showDiv,textValue,barcodeType){
    			  if($(".selectBtn:checked").length == "0"){
    				  layer.open({
					  	title: '打印份数提示',
					  	content: '请选择打印份数'
					  }); 
    			  }
    			  
    			  var totalNum = Number(this.totalJianShu);
				  if($("#selBtn1").is(":checked")){
	  					$(".printUl").show();
	  					if(totalNum > 100){
	  						$(".printUl:gt(99)").hide();
	  					}
	  					showBarCode(showDiv,textValue,barcodeType);
	  			  }else if($("#selBtn2").is(":checked")){
					  $("#selText3").removeAttr("disabled");
					  $(".printUl").show();
					  var fNum = Number(this.assFirst);
					  var sNum = Number(this.assSecond);
					  var countNum = sNum - fNum + 1;
					  if(fNum > 0 && fNum < totalNum && sNum > fNum && sNum <= totalNum && countNum < 101){ 
						var printEqUp = this.assFirst - 1;
						var printEqDown = this.assSecond - 1;
						$(".printUl:lt("+printEqUp+")").hide();
						$(".printUl:gt("+printEqDown+")").hide();
						showBarCode(showDiv,textValue,barcodeType);
						this.assFirst = 0;
						this.assSecond = 0;
					  }else if(countNum > 100){
						  layer.msg('每次最多可打印100份');
					  }else{
						  layer.msg('请输入正确打印份数');
					  }
    			  }else if($("#selBtn3").is(":checked")){
  					  $("#selText3").removeAttr("disabled");
  					  var cNum = this.assCurrent;
  					  if(cNum > 0 && cNum <= totalNum){
  						var printEq = this.assCurrent - 1;
  						$(".printUl").hide().eq(printEq).show();
  						showBarCode(showDiv,textValue,barcodeType);
  						this.assCurrent = 0;
  					  }else{
  						layer.msg("请输入数字1-"+totalNum);
  					  }
    			 } 
    			  
    			  
    			  
    		  }
    		  
    		  
    	  },
    	  mounted:function(){
  
    	  }
    	  
      })
    //条形码
    function showBarCode(showDiv,textValue,barcodeType){
    	$(showDiv).each(function(){
			 var htmlStr = code128(textValue,barcodeType);
			 $(this).html(htmlStr);
		}) 
        /* bdhtml=$("#rrapp").html(); 
    	console.log("bdhtml",bdhtml)
        sprnstr="<!--startprint-->";      
        eprnstr="<!--endprint-->";      
        prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);      
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr)); 
    	console.log("prnhtml",prnhtml)     
        window.document.body.innerHtml=$(".printBox").html();   */ 
        window.print();      
             
    }
    
    //createBarcode('div128',baseValue,'B');
    //时间戳 毫秒转换日期
    function getMyDate(str){  
   	 if(str==null){
   		 return "";
   	 }else{
	         var subDate = new Date(str), 
	         subYear = subDate.getFullYear(),  
	         subMonth = subDate.getMonth()+1,  
	         subDay = subDate.getDate(),  
	         subHour = subDate.getHours(),  
	         subMin = subDate.getMinutes(),  
	         subSen = subDate.getSeconds(),  
	         subTime = subYear +'-'+ getzf(subMonth) +'-'+ getzf(subDay) +' '+ getzf(subHour) +':'+ getzf(subMin)+':'+ getzf(subSen);//最后拼接时间  
	         return subTime;  
   	 }
    };  
    //补0操作  
    function getzf(num){  
        if(parseInt(num) < 10){  
            num = '0'+num;  
        }  
        return num;  
    } 
    </script>
</body>
</html>