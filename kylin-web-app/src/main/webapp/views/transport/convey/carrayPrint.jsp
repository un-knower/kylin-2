<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>运输受理单打印</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/sendSignCheck.css?12"/>
    <style type="text/css">
    	.list-table tr th, .list-table tr td {
		    padding: 2px;
		    height: 20px;
		}
		.newTr td{padding:3px 2px !important}
		.list-info {
           width: auto !important;
         }
    	@media print{
    		*{font-size: 12px;}
    		td{
	    		padding: 2px ;
	    		height: 20px ;
	    	}
	    	.newTr td{padding:3px 2px !important}
	    	.list-top{position: relative;}
	    	#arrivellodiing{
	    		width: 838px;
	    		height: 560px;
	    	}
	    	/* .print-page{margin-top: 0px;} */
    	}
    </style>
</head>
<body>
<div id='arrivellodiing' v-cloak>
   <div class='print-page'>
			<div  class='list-top'>
				<h2 class='head-middle head-tag title send-title'>运输受理单</h2>
			</div>
			<div class='tihuoNum'>
				<p class='head-tag list-info' style='margin-left: 3%;'><i>运输号码：</i>{{otherData.yshhm}}</p>
				<p class='head-tag list-info' style="margin-left:25%"><i>打印时间：</i>{{nowDate}}</p>
				<!-- <p class='head-tag list-info' style="text-align: right;float: right;margin-right: 50px;"><i>编号:</i>{{otherData.cwpzhbh}}</p> -->
			</div>
			<div style='padding: 0 20px;'>
				<table width="600" border="1" class="list-table send-table">
					  <tr>
					    <td style='width: 110px;'><i>营业网点</i></td>
					    <td>{{otherData.shhd}}</td>
					    <td><i>始发站</i></td>
					    <td>{{otherData.fazhan}}</td>
					    <td><i>目的站</i></td>
					    <td style="width:20px"></td>
					    <td colspan="1" style="width:70px">{{otherData.daozhan}}</td>
					  
					    <td style="width:100px"><i>运输方式</i></td>
					    <td colspan="1" style="width:120px">{{otherData.ysfs}}</td>
					   
					  </tr>
					  <tr>
						    <td rowspan="2"><i>发货单位</i></td>
						    <td><i>名称</i></td>
						    <td colspan="5">{{otherData.fhdwmch}}</td>
						
						    <td><i>联系电话</i></td>
						    <td colspan="1">{{otherData.fhdwlxdh}}</td>
						  
						  </tr>
						  <tr>
						    <td><i>地址</i></td>
						    <td colspan="5">{{otherData.fhdwdzh}}</td>
						
						    <td><i>手机/邮编</i></td>
						    <td colspan="1">{{otherData.fhdwyb}}</td>
						  </tr>
						  <tr>
							    <td rowspan="2"><i>收货单位</i></td>
							    <td><i>名称</i></td>
							    <td colspan="5">{{otherData.shhrmch}}</td>
							 
							    <td><i>联系电话</i></td>
							    <td colspan="1">{{otherData.shhrlxdh}}</td>
							
							  </tr>
							  <tr>
							    <td><i>地址</i></td>
							    <td colspan="5">{{otherData.shhrdzh}}</td>
							
							    <td><i>手机/邮编</i></td>
							    <td colspan="1">{{otherData.shhryb}}</td>
							  
							    
							  </tr>
							  <tr>
							    <td rowspan="2"><i>货物名称</i></td>
							    <td rowspan="2"><i>件数</i></td>
							    <td colspan="5"><i>计费(吨/方)</i></td>
							    <td colspan="2"><i>计费(元/吨/方)</i></td>
							  </tr>
							  <tr>
							    <td><i>重量</i></td>
							    <td><i>体积</i></td>
							    <td><i>运费</i></td>
							    <td colspan="2"><i>轻货</i></td>
							    <td colspan="1"><i>重货</i></td>
							    <td ><i>按件</i></td>
							  </tr>
							  <tr>
							    <td>{{otherData.pinming}}</td>
							    <td>{{otherData.jianshu}}</td>
							    <td>{{otherData.zhl}}</td>
							    <td>{{otherData.tiji}}</td>
							    <td>&nbsp;</td>
							    <td colspan="2">{{otherData.qhyj}}</td>
							    <td colspan="1">{{otherData.zhhyj}}</td>
							    <td>{{otherData.ajyj}}</td>
							  
							  </tr>
							  <tr>
							   <td v-if="printData2 != null"> {{printData2.pingMing}} </td>
							    <td v-else>&nbsp;</td>
							    <td v-if="printData2 != null"> {{printData2.jianShu}} </td>
							    <td v-else>&nbsp;</td>
							    <td v-if="printData2 != null"> {{printData2.zhongLiang}} </td>
							    <td v-else>&nbsp;</td>
							    <td v-if="printData2 != null"> {{printData2.tiJi}} </td>
							    <td v-else>&nbsp;</td>
							    <!-- <td v-if="printData2 != null"> {{printData2.conveyFee}} </td> -->
							    <td >&nbsp;</td>
	
							    <td rowspan="9"><i>其<br/>他<br/>费<br/>用</i></td>
							    <td><i>费别</i></td>
							    <td colspan="1"><i>费率</i></td>
							    <td><i>金额</i></td>
							  </tr>
							  <tr>
							   <td v-if="printData3 != null"> {{printData3.pingMing}} </td>
							    <td v-else>&nbsp;</td>
							    <td v-if="printData3 != null"> {{printData3.jianShu}} </td>
							    <td v-else>&nbsp;</td>
							    <td v-if="printData3 != null"> {{printData3.zhongLiang}} </td>
							    <td v-else>&nbsp;</td>
							    <td v-if="printData3 != null"> {{printData3.tiJi}} </td>
							    <td v-else>&nbsp;</td>
							    <td>&nbsp;</td>
							    <td rowspan="2"><i>装卸费</i></td>
							    <td style="text-align:left"><span style="padding-right: 20px;"><i>轻货</i></span>{{otherData.qhzhxfdj}}</td>
							    <td>{{moneyData.lightLoadingFee}}</td><!-- 待定 -->
							  </tr>
							  <tr>
							    <td><i>小计</i></td>
							    <td>{{moneyData.jianShu}}</td>
							    <td>{{moneyData.zhongLiang}}</td>
							    <td>{{moneyData.tiJi}}</td>
							    <td>{{moneyData.conveyFee}}</td>
							    <td style="text-align:left"><span style="padding-right: 20px;"><i>重货</i></span>{{otherData.zhzhxfdj}}</td>
							    <td>{{moneyData.heavyLoadingFee}}</td><!-- 待定 -->
							  </tr>
							  <tr>
							    <td><i>服务方式</i></td>
							    <td colspan='4' v-if="otherData.fwfs===0">仓到站 </td>
							    <td colspan='4' v-else-if="otherData.fwfs===1">仓到仓</td>
							    <td colspan='4' v-else-if="otherData.fwfs===2">站到仓</td>
							    <td colspan='4' v-else-if="otherData.fwfs===3">站到站</td>
							    <td><i>叉车费</i></td>
							    <td colspan="1">{{otherData.zhjxzyf}}</td>
							    <td>{{moneyData.forkliftFee}}</td><!-- 待定 -->
							  </tr>
							  <tr class="newTr">
							    <td rowspan="2"><i>付款方式</i></td>
							    <td><i>现付</i></td>
							    <td>{{otherData.xianjin}}</td>
							    <td><i>到付</i></td>
							    <td>{{otherData.hdfk}}</td>
							    <td><i>保险费</i></td>
							    <td colspan="1">{{otherData.baolu}}</td>
							    <td>{{moneyData.insuranceExpense}}</td>
          						  </tr>
							  <tr  class="newTr">
							    <td><i>月结</i></td>
							    <td>{{otherData.yshk}}</td>
							    <td><i>银收</i></td>
							    <td>{{otherData.yhshr}}</td>
							    <td ><i>提货费</i></td>
							    <td colspan="1">&nbsp;</td>
							    <td>{{otherData.shmshhf}}</td>
							  </tr>
							  <tr>
							  	<!-- <td>优惠金额</td>
							  	<td>{{otherData.youhuijine}}</td> -->
							    <td colspan="5" style="text-align:center"><i style="letter-spacing: 1em;">款未付等通知</i><em v-if="otherData.yshzhkb===1">√</em><em v-else></em></td>
							    <td><i>送货费</i></td> 
							    <td colspan="1">&nbsp;</td>
							    <td>{{otherData.shshmf}}</td>
							  </tr>
							  <tr>
							    <td><i>返单名称</i></td>
							    <td colspan='4'>{{otherData.fdnr}}</td>
							    <td><i>办单货</i></td>
							    <td colspan="1">&nbsp;</td>
							    <td>{{otherData.bdf}}</td>
							  </tr>
							  <tr>
							    <td><i>返单联次</i></td>
							    <td colspan='4'>{{otherData.fdlc}}</td>
							    <td colspan="1">{{otherData.qtfymch}}</td>
							    <td>&nbsp;</td>
							    <td>{{otherData.qtfy}}</td>
							  </tr>
							  <tr>
							    <td><i>发票全称</i></td>
							    <td colspan='4'>{{otherData.fpmc}}</td>
							    <td colspan="2"><i>运杂费合计</i></td>
							    <td colspan="1">&nbsp;</td>
							    <td>{{moneyData.totalFee}}</td>
							  </tr>
							  <tr>
							    <td><i>税务证号</i></td>
							    <td colspan='4'>{{otherData.swzh}}</td>
							    <td colspan="2"><i>代收款金额</i></td>
							    <td colspan="1">&nbsp;</td>
							    <td>{{moneyData.agencyCharge}}</td>
							  </tr>
							  <tr>
							    <td><i>金额大写</i></td>
							    <td colspan='4'>{{otherData.swzh}}</td><!-- 待定 -->
							    <td colspan="2"><i>总金额小写</i></td>
							    <td colspan="1">&nbsp;</td>
							    <td>{{moneyData.totalFee}}</td>
							  </tr>
							</table>
				<!-- <p>第一联提货联（白色）、第二联</p> -->
			</div>
			<div class='tihuoNum' style="margin-top:5px">
				<p class='head-tag' style='margin-left: 3%; width:32%'><i>到站网点：</i>{{otherData.dzshhd}}</p>
				<p class='head-tag' style="width:32%"><i>出纳员：</i>{{otherData.chuna}}</p>
				<p class='head-tag' style="width:32%"><i>单证员：</i>{{otherData.zhipiao}}</p>
			</div>
			<div class='btn-operation list-top list-footer no-print'>
				<button class='layui-btn no-print' @click='_printHandler'>打印</button>
			</div>
		</div>
</div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
    <%@ include file="../../../static/publicFile/publicComponent.jsp"%> 
    <script src="${ctx_static}/transport/convey/js/cost.js?t=1111"></script>
    <script src="${ctx_static}/transport/arrivalsendSignCheck/js/printCarryConvey.js"></script>
    <script>
    //自动在打印之前执行
    window.onbeforeprint = function(){	
       $("i,h2").css("visibility","hidden");
       $(".list-table tr td").css("border","1px solid #fff");
       $(".list-table").attr("border","0");
       $("em").css("border","none");
    }
    //自动在打印之后执行
    window.onafterprint = function(){
    	  $("i,h2").css("visibility","visible");
          $(".list-table tr td").css("border","1px solid #999");
          $(".list-table").attr("border","1");
          $("em").css("border","1px solid #ccc");
    } 
    </script>
</body>
</html>