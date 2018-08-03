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
    <style type="text/css">
      @page{margin:25mm 0mm 0 0mm }
      .print_head{padding:20px 0 0;}
      .top-num, .top-beizhu{width:96%;font-weight:600;height:24px;margin:0 auto;display:flex;display:-webkit-flex;align-items:center;justify-content:space-between;}
      .top-num{width:95%;justify-content:flex-end;}
      .send-title{position:relative;}
      .send-title img{position:absolute;left:30px;width:210px;}
      .print-title{font-size:30px !important;font-weight:bolder;margin-left:130px;}
      .print-little{font-size:32px;font-weight:600;font-family:'微软雅黑';letter-spacing:0;}
      .send-table td{height:auto}
      .list-table tr th, .list-table tr td{border: 1px solid #000;text-align:center;font-family:'宋体';font-size:12px;}
  	  .list-table .empyt-tr td{border:0;}
  	  .lookBox em{border-color:#000;}
  </style>
</head>
<body>
<div id='rrapp' v-cloak>
        <div  class='list-top print_head'>
			<div class='head-middle head-tag title send-title' style="position:relative;">
				<img src="${ctx_static}/transport/common/images/yc_new.jpg">
				<span class="print-title">货物明细单</span>
				<span class="print-little">*{{caseinfo.ydbhid}}*</span>
			    <span class="print-span"><button class="layui-btn layui-btn-normal" @click=print>打印</button></span>
			</div>
		</div>
		<div class="top-num">NO：{{caseinfo.ydbhid}}</div>
		<div class="top-beizhu">
			<span>欢迎使用www.ycgwl.com查询货物在途情况！货物签收后我们将以短信通知发货人！</span>
			<span style="width:120px;">远成集团：印刷</span>
		</div>
		<table class='list-table send-table lookBox' style="margin:0 auto;width:96%;">
			  <tr>
				    <td colspan="4" style="width:20%;"><span>托运日期：</span><i>{{year}}</i><span> 年</span><i>{{month}}</i><span>月</span><i>{{day}}</i><span>日</span></td>
				    <td colspan="1" style="width:6%;"><span>发运网点</span></td>
				    <td colspan="2" style="width:10%;">{{caseinfo.fazhan}}</td>
				    <td style="width:7%"><span>始发站</span></td> 
				    <td colspan="2" style="width:5%;">{{caseinfo.fazhan}}</td>
				    <td style="width:8%"><span>路由站</span></td>
				    <td style="width:8%">&nbsp;</td>
				    <td style="width:8%"><span>(1)目的站</span></td>
				    <td style="width:8%;">{{caseinfo.daozhan}}</td>
				    <td style="width:8%"><span>(2)到达网点</span></td>
				    <td style="width:10%;">{{caseinfo.dzshhd}}</td>
				  </tr>
				  <tr>
				    <td rowspan="4" class="center" style="width:3%;"><span>(3)<br>托<br>运<br>人</span></td>
				    <td colspan="1" style="width:6%;"><span>名称</span></td>
				    <td colspan="7"><p>{{caseinfo.fhdwmch}}</p></td>
				    <td rowspan="4" class="center"><span>(4)<br>收<br>货<br>人</span></td>
				    <td><span style="width:6%;">名称</span></td>
				    <td colspan="5">{{caseinfo.shhrmch}}</td>
				  </tr>
				  <tr>
				    <td rowspan="2" colspan="1" style="width:6%;"><span>地址</span></td>
				    <td height="25" rowspan="2" colspan="7"><p>{{caseinfo.fhdwdzh}}</p></td>
				     <td rowspan="2" style="width:6%;"><span>地址</span></td>
				    <td rowspan="2" colspan="5"><p>{{caseinfo.dhShengfen}}{{caseinfo.dhChengsi}}<br>
				    {{caseinfo.dhAddr}}{{caseinfo.shhrdzh}}</p></td>
				  </tr>
				  <tr class="empyt-tr">
				    <td colspan="7"></td>
				  </tr> 
				  <tr>
				    <td colspan="1" style="width:6%;"><span>座机电话</span></td>
				    <td colspan="3">{{caseinfo.fhdwlxdh}}</td>
				    <td style="width:6%;"><span>手机</span></td>
				    <td colspan="3">{{caseinfo.fhdwyb}}</td>
				    <td style="width:6%;"><span>座机电话</span></td>
				    <td colspan="2">{{caseinfo.shhrlxdh}}</td>
				    <td style="width:6%;"><span>手机</span></td>
				   <td colspan="2">{{caseinfo.shhryb}}</td>
				  </tr>
			  <tr>
			    <td colspan="2"><span>税务证号</span></td>
			    <td colspan="6">{{caseinfo.tuoyunrenshuihao}}</td>
			    <td colspan="3"><span>税务证号</span></td>
			    <td colspan="5">{{caseinfo.shouhuorenshuihao}}</td>
			  </tr>
			  <tr>
			    <th>序号</th>
			    <th colspan="2">品名</th>
			    <th colspan="2">型号</th>
			    <th colspan="1" style="width:8%;">件数</th>
			    <th colspan="1" style="width:8%;">包装</th>
			    <th colspan="2">重量(吨)</th>
			    <th colspan="3">体积(立方)</th>
			    <th colspan="1">声明价值</th>
			    <th colspan="1">保价金额</th>
			    <th colspan="1">计费方式</th>
			    <th colspan="1">运价</th>
			  </tr>
			  <tr v-for="(item,index) in caseList">
			    <td>{{index+1}}</td>
			    <td colspan="2">{{item.pinming}}</td>
			    <td colspan="2">{{item.xh}}</td>
			    <td colspan="1">{{item.jianshu}}</td>
			    <td colspan="1">{{item.bzh}}</td>
			    <td colspan="2">{{item.zhl}}</td>
			    <td colspan="3">{{item.tiji}}</td>
			    <td colspan="1">{{item.decprice}}</td>
			    <td colspan="1">{{item.tbje}}</td>
			    <td colspan="1">{{item.jffsText}}</td>
			    <td colspan="1">{{item.yunjia}}</td>
			  </tr>
			  <tr>
			    <td colspan="5">合计</td>
			    <td colspan="1">{{totalJianshu}}</td>
			    <td colspan="1"></td>
			    <td colspan="2">{{totalZhl}}</td>
			    <td colspan="3">{{totalTiji}}</td>
			    <td colspan="1">{{totalDecprice}}</td>
			    <td colspan="1">{{totalTbje}}</td>
			    <td colspan="1"></td>
			    <td colspan="1">{{totalYunjia}}</td>
			  </tr>
	    </table>
</div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
    <script type="text/javascript"> 
    
    //自动在打印之前执行
    window.onbeforeprint = function(){	
    	$(".print-span").hide();
    }

    //自动在打印之后执行
    window.onafterprint = function(){
    	$(".print-span").show();
    }
     
    //获取url中的参数
    function getUrlParam(name) {
    	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    	if (r != null) return unescape(r[2]); return null; //返回参数值
    }
  
    var vm = new Vue({
    	el:'#rrapp',
    	data:{
    		caseinfo:{},
    		caseList: {},
    		totalJianshu:0,
    		totalZhl:0,
    		totalTiji:0,
    		totalTbje:0,
    		totalDecprice:0,
    		totalYunjia:0,
    		year:"",
    		month:"",
    		day:"",
    		hour:"",
    		show:true
    	},
    	 created: function () {
    		    // `this` 指向 vm 实例
    		    this.searchInfo();
    		  },
    	  watch:{},
    	  methods: {
    		  searchInfo:function(){
    			  layui.use('layer', function(){
    			  var baseValue=getUrlParam("ydbhid");
    				$.ajax({
    					url: base_url + '/transport/convey/convey/printSearch/'+baseValue,
    					type: 'post',
    					dataType: 'JSON',
    					contentType: 'application/json',
    					//data: JSON.stringify(baseValue),
    					beforeSend: function(){
    						 loading = layer.load(0,{
    							shade: [0.5,'#fff']
    						}) 
    					},
    					complete:function(){
    						layer.close(loading);
    					},
    					success: function(data){
    						vm.caseinfo=data.order;
    						vm.caseinfo.fhshj=getMyDate(data.order.fhshj);
    						//时间
    						vm.year=vm.caseinfo.fhshj.substring(2,4);
    						vm.month=vm.caseinfo.fhshj.substring(5,7);
    						vm.day=vm.caseinfo.fhshj.substring(8,10);
    						vm.hour=vm.caseinfo.fhshj.substring(11,13);
    						vm.caseList=data.detail;
    						for(var k in data.detail){
    							if(data.detail[k]['jffs'] == "0"){
    								vm.caseList[k]['jffsText'] = '重货';
    							}else if(data.detail[k]['jffs'] == "1"){
    								vm.caseList[k]['jffsText'] = '轻货';
    							}else if(data.detail[k]['jffs'] == "2"){
    								vm.caseList[k]['jffsText'] = '按件';
    							}
    							if(!data.detail[k]['jianshu']){
    								vm.totalJianshu += 0;
    							}else{
    								vm.totalJianshu += Number(data.detail[k]['jianshu']);
    							}
    							if(!data.detail[k]['zhl']){
    								vm.totalZhl += 0;
    							}else{
    								vm.totalZhl += Number(data.detail[k]['zhl']);
    							}
    							if(!data.detail[k]['tiji']){
    								vm.totalTiji += 0;
    							}else{
    								vm.totalTiji += Number(data.detail[k]['tiji']);
    							}
    							if(!data.detail[k]['tbje']){
    								vm.totalTbje += 0;
    							}else{
    								vm.totalTbje += Number(data.detail[k]['tbje']);
    							}
    							if(!data.detail[k]['decprice']){
    								vm.totalDeprice += 0;
    							}else{
    								vm.totalDecprice += Number(data.detail[k]['decprice']);
    							}
    							if(!data.detail[k]['yunjia']){
    								vm.totalYunjia += 0;
    							}else{
    								vm.totalYunjia += Number(data.detail[k]['yunjia'])
    							}
    						}
    					},
    					error: function(data){
    						console.log(data);
    					}
    				});
    			  });
    		  },
    		  //打印
    		  print:function(){ 				
                    window.print();
    			},
    		    
    		  },
    	  mounted:function(){
  
    	  }
    	  
      })
  
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