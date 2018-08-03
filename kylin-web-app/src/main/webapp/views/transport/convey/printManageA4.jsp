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
      .print-headline{text-align:center;}
      .print_head{padding:20px 0 0;}
      .top-num, .top-beizhu{width:96%;font-weight:600;height:24px;margin:0 auto;display:flex;display:-webkit-flex;align-items:center;justify-content:space-between;}
      .top-num{width:91%;justify-content:flex-end;}
      .send-title{position:relative;}
      .send-title img{position:absolute;left:30px;width:210px;}
      .print-title{font-size:30px !important;font-weight:bolder;}
      .print-little{font-size:16px;letter-spacing:2px;font-family:'宋体';}
      .send-table td{height:auto}
      .list-table tr th, .list-table tr td{border: 1px solid #000;text-align:center;font-family:'宋体';font-size:12px;}
  	  .list-table .empyt-tr td{border:0;}
  	  .lookBox em{border-color:#000;font-size:18px;}
  </style>
</head>
<body>
<div id='rrapp' v-cloak>
	<div class='list-top print-headline'>
		<button class="layui-btn layui-btn-normal" @click=print>打印</button>
	</div>
	<div v-for="item in allData" style="height:20cm;">
		<div class='list-top print_head' style="position:relative;">
			<div class='head-middle head-tag title send-title' style="position:relative;">
				<img src="${ctx_static}/transport/common/images/yc_new.jpg">
				<span class="print-title">货物运单</span>
				<span class="print-little">国内适用</span>
			</div>
		</div>
		<div class="top-num">NO：{{item.order.ydbhid}}</div>
		<div class="top-beizhu">
			<span>欢迎使用www.ycgwl.com查询货物在途情况！货物签收后我们将以短信通知发货人！</span>
			<span style="width:120px;">远成集团：印刷</span>
		</div>
		<table class='list-table send-table lookBox' style="margin:0 auto;width:96%;">
			  <tr>
			    <td colspan="4" style="width:20%;"><span>托运日期：</span><i>{{item.order.fhshj.substring(2,4)}}</i><span> 年</span><i>{{item.order.fhshj.substring(5,7)}}</i><span>月</span><i>{{item.order.fhshj.substring(8,10)}}</i><span>日</span></td>
			    <td colspan="1" style="width:6%;"><span>发运网点</span></td>
			    <td colspan="2" style="width:10%;">{{item.order.shhhd}}</td>
			    <td style="width:7%"><span>始发站</span></td> 
			    <td colspan="2">{{item.order.fazhan}}</td>
			    <td style="width:8%"><span>路由站</span></td>
			    <td style="width:8%">{{item.order.luyouzhan}}</td>
			    <td style="width:10%"><span>(1)目的站</span></td>
			    <td style="width:8%;" >{{item.order.daozhan}}</td>
			    <td style="width:8%"><span>(2)到达网点</span></td>
			    <td style="width:10%;">{{item.order.dzshhd}}</td>
			  </tr>
			  <tr>
			    <td rowspan="4" class="center"><span>(3)<br>托<br>运<br>人</span></td>
			    <td colspan="1"><span>名称</span></td>
			    <td colspan="7"><p>{{item.order.fhdwmch}}</p></td>
			    <td rowspan="4" class="center"><span>(4)<br>收<br>货<br>人</span></td>
			    <td><span>名称</span></td>
			    <td colspan="5">{{item.order.shhrmch}}</td>
			  </tr>
			  <tr>
			    <td rowspan="2" colspan="1"><span>地址</span></td>
			    <td height="25" rowspan="2" colspan="7"><p>{{item.order.fhdwdzh}}</p></td>
			     <td rowspan="2"><span>地址</span></td>
			    <td rowspan="2" colspan="5"><p>{{item.order.dhAddr}}<br>{{item.order.shhrdzh}}</p></td>
			  </tr>
			  <tr class="empyt-tr">
			    <td colspan="7"></td>
			  </tr> 
			  <tr>
			    <td colspan="1"><span>座机电话</span></td>
			    <td colspan="3">{{item.order.fhdwlxdh}}</td>
			    <td><span>手机号码</span></td>
			    <td colspan="3">{{item.order.fhdwyb}}</td>
			    <td><span>座机电话</span></td>
			    <td colspan="2">{{item.order.shhrlxdh}}</td>
			    <td><span>手机号码</span></td>
			    <td colspan="2">{{item.order.shhryb}}</td>
			  </tr>
			  <tr>
			    <td rowspan="4" class="center"><span>(5)<br>其<br>他<br>收<br>费<br>项<br>目</span></td>
			    <td colspan="1"><span>付款方式</span></td>
			    <td colspan="4"><span>发付</span><em class="yes">{{item.order.fzfk=="1" ? "√" : ""}}</em><span style="margin-left:50px">到付</span><em class="yes">{{item.order.dzfk=="1" ? "√" : ""}}</em></td>
			    <td  colspan="5"><span>代收款大写</span></td>
			    <td><span>(10)服务方式</span></td>
			    <td colspan="4">
			        <span value="0" class="fwBox" >仓到站</span><em>{{item.order.fwfs=="0" ? "√" : ""}}</em>
			        <span value="3" class="fwBox" style="padding-left:20px;">站到站</span><em>{{item.order.fwfs=="3" ? "√" : ""}}</em>
			        <span value="1" class="fwBox" style="padding-left:20px;">仓到仓</span><em>{{item.order.fwfs=="1" ? "√" : ""}}</em>
			        <span value="2" class="fwBox" style="padding-left:20px;">站到仓</span><em>{{item.order.fwfs=="2" ? "√" : ""}}</em>
			    </td>
			  </tr>
			  <tr>
			    <td colspan="1"><span>返单</span></td>
			    <td colspan="4"><span class="fdBox" value='1'>普通返单</span><em>{{item.order.isfd=="1" ? "√" : ""}}</em><span class="fdBox" value='2'>电子返单<em>{{item.order.isfd=="2" ? "√" : ""}}</em></span><span class="fdBox" value='0'>无</span><em>{{item.order.isfd=="0" ? "√" : ""}}</em></td>
			    <td colspan="5"><span>返单要求</span>{{item.order.fdyq}}</td>
			    <td><span>(11)运输号码</span></td>
			    <td colspan="4">{{item.order.yshhm}}</td>
			  </tr>
			  <tr>
			    <td colspan="10"><span>代收返单</span><em></em><span>返单名称&nbsp;&nbsp;</span><a style="text-align:center;">大票+附单<abbr style="margin:0 100px 0 20px">2</abbr><abbr style="margin-right:30px">1</abbr></a></td>
			    <td rowspan="3"><span>(12)复核栏</span></td>
			    <td><span>件数</span></td>
			    <td colspan="3" class="center">&nbsp;</td>
			  </tr>
			  <tr>
			    <td colspan="10"><span>代客包装：纸箱</span><em></em><span>木箱</apan><em></em><span>编织袋</span><em></em><span>代客装卸</span><em></em><span>送货上楼</span><em></em><span>(服务价目请阅读背书条款栏)</span></td>
			    <td><span>重量</span></td>
			    <td colspan="3" class="center">&nbsp;</td>
			  </tr>
			  <tr>
			    <td colspan="2"><span>(6)品名</span></td>
			    <td><span>件数</span></td>
			    <td><span>包装</span></td>
			    <td><span>重量</span></td>
			    <td><span>体积</span></td>
			    <td><span>保价金额</span></td>
			    <td><span>运费</span></td>
			    <td colspan="2" class="center"><span>保价费</span></td>
			    <td class="center">&nbsp;</td>
			    <td><span>体积</span></td>
			    <td colspan="3" class="center">&nbsp;</td>
			  </tr>
			  <tr>
			    <td colspan="2" class="center">{{item.detail[0]!=undefined ? item.detail[0]["pinming"] : ""}}</td>
			    <td>{{item.detail[0]!=undefined ? item.detail[0]["jianshu"] : ""}}</td>
			    <td class="center">{{item.detail[0]!=undefined ? item.detail[0]["bzh"] : ""}}</td>
			    <td class="center">{{item.detail[0]!=undefined ? item.detail[0]["zhl"] : ""}}</td>
			    <td class="center">{{item.detail[0]!=undefined ? item.detail[0]["tiji"] : ""}}</td>
			    <td class="center">{{item.detail[0]!=undefined ? item.detail[0]["tbje"] : ""}}</td>
			    <td class="center">{{item.detail[0]!=undefined ? item.detail[0]["yunjia"] : ""}}</td>
			    <td colspan="2" class="center"><span>包装费</span></td>
			    <td colspan="1" class="center">{{item.order.baozhuangfei}}</td>
			    <td><span>(13)作业方式</span></td>
			    <td colspan="4">
			    	<span value="0" class="" style="padding-left:30px;">人工</span><em></em>
			        <span value="3" class="" style="padding-left:20px;">机械</span><em></em>
			        <span value="3" class="" style="padding-left:20px;">班组：</span>
			        <span value="3" class="" style="padding-left:20px;">仓位：</span>
			    </td>
			  </tr>
			  <tr>
			    <td colspan="2"  class="center">{{item.detail[1]!=undefined ? item.detail[1]["pinming"] : ""}}</td>
			    <td style="text-align:center;">{{item.detail[1]!=undefined ? item.detail[1]["jianshu"] : ""}}</td>
			    <td class="center">{{item.detail[1]!=undefined ? item.detail[1]["bzh"] : ""}}</td>
			    <td class="center">{{item.detail[1]!=undefined ? item.detail[1]["zhl"] : ""}}</td>
			    <td class="center">{{item.detail[1]!=undefined ? item.detail[1]["tiji"] : ""}}</td>
			    <td class="center">{{item.detail[1]!=undefined ? item.detail[1]["tbje"] : ""}}</td>
			    <td class="center">{{item.detail[1]!=undefined ? item.detail[1]["yunjia"] : ""}}</td>
			    <td colspan="2" class="center"><span>装卸费</span></td>
			    <td colspan="1" class="center">{{item.order.zhuangxiefei}}</td>
			    <td rowspan="4"><span>(14)货物/包装状态描述</span></td>
			    <td colspan="4" style="padding-left:40px;" rowspan="4">&nbsp;</td>
			  </tr>
			  <tr>
			    <td colspan="2"  class="center">{{item.detail[2]!=undefined ? item.detail[2]["pinming"] : ""}}</td>
			    <td>{{item.detail[2]!=undefined ? item.detail[2]["jianshu"] : ""}}</td>
			    <td class="center">{{item.detail[2]!=undefined ? item.detail[2]["bzh"] : ""}}</td>
			    <td class="center">{{item.detail[2]!=undefined ? item.detail[2]["zhl"] : ""}}</td>
			    <td class="center">{{item.detail[2]!=undefined ? item.detail[2]["tiji"] : ""}}</td>
			    <td class="center">{{item.detail[2]!=undefined ? item.detail[2]["tbje"] : ""}}</td>
			    <td class="center">{{item.detail[2]!=undefined ? item.detail[2]["yunjia"] : ""}}</td>
			    <td colspan="2" class="center"><span>办单费</span></td>
			    <td colspan="1" class="center">{{item.order.bandanfei}}</td> 
			  </tr>
			  <tr>
			    <td colspan="2" ><span>合计</span></td>
			    <td style="text-align:center;">&nbsp;</td>
			    <td class="center">&nbsp;</td>
			    <td class="center">&nbsp;</td>
			    <td class="center">&nbsp;</td>
			    <td class="center">&nbsp;</td>
			    <td class="center">&nbsp;</td>
			    <td colspan="2" class="center"><span>总金额</span></td>
			    <td colspan="1" class="center">&nbsp;</td>
			  </tr>
			  <tr>
			    <td colspan="2"><span>(8)特别说明</span></td>
			    <td colspan="5" height="70">{{item.order.tiebieshuoming}}</td>
			    <!-- <td colspan="5" height="70">哈哈哈你好你是猪你是大肥猪你是大花猪金刚葫芦娃你好你是猪你是大肥猪你是大花猪金刚葫芦娃你好你是猪你是大肥猪你是大花猪金刚葫芦娃你好你是猪你是大肥猪你是大花猪金刚葫芦娃你好你是猪你是大肥猪你是大花猪金刚葫芦娃</td>  -->
			    <td colspan="4"><span>(9)等托运人指令放货</span><em class="yes">{{item.orderEntry.releaseWaiting==0 ? "" : "√"}}</em></td>
			  </tr>
	    </table>
	</div>
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
    	$(".print-headline").hide();
    }

    //自动在打印之后执行
    window.onafterprint = function(){
    	$(".print-headline").show();
    	//关闭弹出层
    	parent.layer.closeAll(); 
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
    		allData:[],
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
    			  	var baseValue = getUrlParam("ydbhid");
    				$.ajax({
    					url: base_url + '/transport/convey/convey/printSearchBatch/'+baseValue,
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
    						if(data.resultCode != "200"){
    							layer.open({title:'提示信息',content:data.reason});
    						}else{
    							vm.allData = data.transportOrderInfo;
    							for(var i in vm.allData){
    								vm.allData[i]['order']['fhshj'] = getMyDate(vm.allData[i]['order']['fhshj']);
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