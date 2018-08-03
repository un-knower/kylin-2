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
      .send-table td{height:auto}
      .list-table tr th, .list-table tr td{border: 1px solid #999;text-align:left;font-family:'宋体';font-size:12px;}
  	  .printBox{height:auto;display:none;}
  	  .bgimg{height:157.8mm;width:100%;margin:0 auto;}
  	  .bgimg ul{margin-top:-0.05mm;} 
  	  .bgimg ul .li-no-text{line-height:22px;height:22px;}
  </style>
</head>
<body>
<div id='rrapp' v-cloak>
        <div class='list-top'>
			<h2 class='head-middle head-tag title send-title'><span style="font-size:25px !important">货物运单</span>
			   <span><button class="layui-btn layui-btn-normal"  @click=print>打印</button></span>
			</h2>
		</div>
		<table class='list-table send-table lookBox' v-for="item in allData" style="margin:0 auto 60px;">
			  <tr>
			   <!--  <td class="firstBox center" style="width:5%"></td> -->
			    <td colspan="4" style="width:20%;text-align: right"><span>托运日期：</span><i>{{item.order.fhshj.substring(2,4)}}</i><span> 年</span><i>{{item.order.fhshj.substring(5,7)}}</i><span>月</span><i>{{item.order.fhshj.substring(8,11)}}</i><span>日</span></td>
			    <td colspan="1" style="width:6%;text-align: right"><span>发运网点</span></td>
			    <td colspan="2" style="width:10%;text-align: right">{{item.order.shhhd}}</td>
			    <td style="width:7%"><span>始发站</span></td> 
			    <td colspan="2" style="width:5%;text-align: right">{{item.order.fazhan}}</td>
			    <td style="width:8%"><span>路由站</span></td>
			    <td style="width:8%">{{item.order.luyouzhan}}</td>
			    <td style="width:10%"><span>(1)目的站</span></td>
			    <td style="width:8%;text-align: right;" >{{item.order.daozhan}}</td>
			    <td style="width:8%"><span>(2)到达网点</span></td>
			    <td style="width:10%;text-align: center;">{{item.order.dzshhd}}</td>
			  </tr>
			  <tr>
			    <td rowspan="4" class="center"><span>(3)<br>托<br>运<br>人</span></td>
			    <td colspan="1"><span>名称</span></td>
			    <td colspan="7"><p style="margin-left:30px;">{{item.order.fhdwmch}}</p></td>
			    <td rowspan="4" class="center"><span>(4)<br>收<br>货<br>人</span></td>
			    <td><span style="padding-top:10px;">名称</span></td>
			    <td colspan="5" style="padding:10px 0 0 30px;">{{item.order.shhrmch}}</td>
			  </tr>
			  <tr>
			    <td rowspan="2" colspan="1"><span>地址</span></td>
			    <td height="25" colspan="7"><p style="margin-left:30px;padding-top:10px;">{{item.order.fhdwdzh}}</p></td>
			    <td rowspan="2"><span>地址</span></td>
			    <!-- <td colspan="5"><p style="margin-left:30px">{{item.order.dhShengfen}}{{item.order.dhChengsi}}<br>
			    {{item.order.dhAddr}}{{item.order.shhrdzh}}</p></td> -->
			    <td colspan="5"><p style="margin-left:30px">{{item.order.dhAddr}}<br>
			    {{item.order.shhrdzh}}</p></td>
			  </tr>
			  <tr>
			    <td colspan="7"></td>
			  </tr> 
			  <tr>
			    <td colspan="1" style="padding-left:30px;"><span>座机电话</span></td>
			    <td colspan="3" style="text-align:right;">{{item.order.fhdwlxdh}}</td>
			    <td><span>手机号</span></td>
			    <td colspan="3" style="text-align:right;">{{item.order.fhdwyb}}</td>
			    <td><span>座机电话</span></td>
			    <td colspan="2" style="text-align:right;">{{item.order.shhrlxdh}}</td>
			    <td><span>手机号码</span></td>
			   <td colspan="2" style="text-align:center;">{{item.order.shhryb}}</td>
			      
			  </tr>
			  <tr>
			    <td rowspan="4" class="center"><span>(5)<br>其<br>他<br>收<br>费<br>项<br>目</span></td>
			    <td colspan="1"><span>付款方式</span></td>
			    <td colspan="4" style="text-align:right;"><span>发付</span><em class="yes">{{item.order.fzfk=="1" ? "√" : ""}}</em><span style="margin-left:50px">到付</span><em class="yes">{{item.order.dzfk=="1" ? "√" : ""}}</em></td>
			    <td  colspan="5"><span>代收款大写</span></td>
			    <td><span>(10)服务方式</span></td>
			    <td colspan="4" style="text-align:right;">
			        <span value="0" class="fwBox" >仓到站</span><em>{{item.order.fwfs=="0" ? "√" : ""}}</em>
			        <span value="3" class="fwBox" style="padding-left:20px;">站到站</span><em>{{item.order.fwfs=="3" ? "√" : ""}}</em>
			        <span value="1" class="fwBox" style="padding-left:20px;">仓到仓</span><em>{{item.order.fwfs=="1" ? "√" : ""}}</em>
			        <span value="2" class="fwBox" style="padding-left:20px;">站到仓</span><em>{{item.order.fwfs=="2" ? "√" : ""}}</em>
			    </td>
			  </tr>
			  <tr>
			    <td colspan="1"><span>返单</span></td>
			    <td colspan="4"><span class="fdBox" value='1'>普通返单</span><em>{{item.order.isfd=="1" ? "√" : ""}}</em><span class="fdBox"  value='2'>电子返单<em>{{item.order.isfd=="2" ? "√" : ""}}</em></span><span class="fdBox"  value='0'>无</span><em>{{item.order.isfd==0 ? "√" : ""}}</em></td>
			    <td colspan="5"><span>返单要求</span>{{item.order.fdyq}}</td>
			    <td><span>(11)运输号码</span></td>
			    <td colspan="4" style="text-align:center;">{{item.order.yshhm}}</td>
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
			    <td style="text-align:left;">{{item.detail[0]!=undefined ? item.detail[0]["jianshu"] : ""}}</td>
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
			    <td style="text-align:left;">{{item.detail[1]!=undefined ? item.detail[1]["jianshu"] : ""}}</td>
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
			    <td style="text-align:left;">{{item.detail[2]!=undefined ? item.detail[2]["jianshu"] : ""}}</td>
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
			    <td style="text-align:left;">&nbsp;</td>
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
			    <td colspan="5">{{item.order.tiebieshuoming}}</td>
			    <td  colspan="4"><span>(9)等托运人指令放货</span><i>{{item.orderEntry.releaseWaiting==0 ? "" : "√"}}</i></td>
			  </tr>
	    </table>
		
		<div class="printBox">
		     <div class="bgimg" v-for="item in allData">
		           <ul>
		              <li style="padding-left:67px;top: 2px;position: relative;">
		                   <div class="inlBox" style="width:135px;"><span>{{item.order.fhshj.substring(2,4)}}</span><span class="marL25">{{item.order.fhshj.substring(5,7)}}</span><span class="marL15">{{item.order.fhshj.substring(8,10)}}</span><span class="marL15">{{item.order.fhshj.substring(11,13)}}</span></div>
		                   <div class="inlBox" style="width:110px;text-indent: 5em;"><span>{{item.order.shhd}}</span></div>
		                   <div class="inlBox" style="width:100px;text-indent: 5em;"><span>{{item.order.fazhan}}</span></div>
		                   <div class="inlBox" style="width:100px;text-indent: 7em;"><span>{{item.order.luyouzhan}}</span></div>
		                   <div class="inlBox" style="width:120px;text-indent: 7.5em;"><span>{{item.order.daozhan}}</span></div>
		                   <div class="inlBox" style="width:130px;text-indent: 8.5em;"><span>{{item.order.dzshhd}}</span></div>
		              </li>
		              <!-- LINE 2 -->
		              <li>
		                <div class="inlBox" style="width:60%;text-indent: 7em;"><span>{{item.order.fhdwmch}}</span></div>
		                <div class="inlBox" style="left:"><span>{{item.order.shhrmch}}</span></div>
		              </li>
		              <!-- <li>
		                <div class="inlBox" style="width:60%;text-indent: 7em;"><span>{{item.order.fhdwdzh}}</span></div>
		                <div class="inlBox" style=""><span>{{item.order.dhShengfen}}{{item.order.dhChengsi}}</span></div>
		                <div class="inlBox" style=""><span>{{item.order.dhAddr}}</span></div>
		              </li>
		              <li>
		                <div class="inlBox" style="width:60%;text-indent: 6em;"><span>&nbsp;</span></div>
		                <div class="inlBox" style="width:40%"><span>{{item.order.shhrdzh}}</span></div>
		              </li> -->
		              <li>
	                      <div class="inlBox" style="width:60%;text-indent: 7em;"><span>{{item.order.fhdwdzh}}</span></div>
	                      <div class="inlBox" style=""><span>{{item.order.dhAddr}}</span></div>
	                  </li>
	                  <li v-if="item.order.shhrdzhLength >= 50">
	                      <div class="inlBox" style="width:52%;text-indent: 6em;"><span>&nbsp;</span></div>
	                      <div class="inlBox" style="width:48%"><span style="font-size:12px !important;">{{item.order.shhrdzh}}</span></div>
	                  </li>
	                  <li v-else>
	                      <div class="inlBox" style="width:60%;text-indent: 6em;"><span>&nbsp;</span></div>
	                      <div class="inlBox" style="width:40%"><span>{{item.order.shhrdzh}}</span></div>
	                  </li>
		              <!-- line 5 -->
		              <li>
		                <div class="inlBox" style="width:30%;text-indent: 7em;"><span >{{item.order.fhdwlxdh}}</span></div>
		                <div class="inlBox" style="width:30%;text-indent: 3em;"><span >{{item.order.fhdwyb}}</span></div>
		                <div class="inlBox" style="width:18%;"><span>{{item.order.shhrlxdh}}</span></div>
		                <div class="inlBox" style="width:18%;text-indent: 4em;"><span>{{item.order.shhryb}}</span></div>
		              </li>
		              <!-- line 6 -->
		              <li class="li-no-text">
		                  <div class="inlBox" style="width:20%;text-indent: 8.5em;"><span>{{item.order.fzfk=="1" ? "√" : ""}}</span></div>
		                  <div class="inlBox" style="width:10%;text-indent:1em;"><span>{{item.order.dzfk=="1" ? "√" : ""}}</span></div>
		                  <div class="inlBox" style="width:20%;text-indent:4em;"><span id="daishoukuan"></span></div>
		                  <div class="inlBox" style="width:10%;text-indent:2em;"><span>{{item.order.dashoukuan}}</span></div>
		                  
		                  <div class="inlBox" style="width:16%;text-indent:7.5em"><span class="s_fwBox" value="0">{{item.order.fwfs=="0" ? "√" : ""}}</span></div>
		                  <div class="inlBox" style="width:6%;text-indent:2.2em;"><span class="s_fwBox" value="3">{{item.order.fwfs=="3" ? "√" : ""}}</span></div>
		                  <div class="inlBox" style="width:6%;text-indent:2.7em;"><span class="s_fwBox" value="1">{{item.order.fwfs=="1" ? "√" : ""}}</span></div>
		                  <div class="inlBox" style="width:6%;text-indent:3.2em;"><span class="s_fwBox" value="2">{{item.order.fwfs=="2" ? "√" : ""}}</span></div>
		              </li>
		              <li class="li-no-text">
		                   <div class="inlBox" style="width:15%;text-indent: 8.8em;"><span class="s_fdBox" value='1'>{{item.order.isfd=="1" ? "√" : ""}}</span></div> 
		                   <div class="inlBox" style="width:6%;text-indent:3.5em;"><span class="s_fdBox" value='2'>{{item.order.isfd=="2" ? "√" : ""}}</span></div>
		                   <div class="inlBox" style="width:6%;text-indent: 1.5em;"><span class="s_fdBox" value='0'>{{item.order.isfd=="0" ? "√" : ""}}</span></div>
		                   <div class="inlBox" style="width:40%;text-indent: 3em;">{{item.order.fdyq}}</div>
		                   <div class="inlBox" style="text-indent: 3em;">{{item.order.yshhm}}</div>
		              </li>
		              <li>
		                  <div class="inlBox" style="width:12%;text-indent: 6.2em;"><span></span></div>   
		                  <div class="inlBox" style="width:22%;text-indent: 4em;"><span>{{item.order.fdnr}}大票+附单</span></div>
		                  <div class="inlBox" style="width:4%;text-indent: 0em;"><span>2</span></div>
		                  <div class="inlBox" style="width:20%;text-indent: 5em;"><span>1</span></div>            
		              </li>
		              <li>
		                   <div class="inlBox" style="width:18%;text-indent: 8.5em;"><span></span></div>
		                   <div class="inlBox" style="width:5%;text-indent: 1em;"><span></span></div>   
		                   <div class="inlBox" style="width:6%;text-indent: 2em;"><span></span></div>   
		                   <div class="inlBox" style="width:6%;text-indent: 3em;"><span></span></div>   
		                   <div class="inlBox" style="width:10%;text-indent: 4em;"><span></span></div>      
		              </li>
		              <li>
		                  <div class="inlBox" style="width:50%;text-indent:1em;">&nbsp;</div>
		                  <div class="inlBox center" style="width:10%;text-indent:1.5em;"><span></span></div>
		              </li>
		              <li>
		                   <div class="inlBox center" style="width:10%;text-indent:1em;"><span>{{item.detail[0]!=undefined ? item.detail[0]["pinming"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["jianshu"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["bzh"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["zhl"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["tiji"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:9%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["tbje"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["yunjia"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:16%;text-indent:5em;"><span>{{item.order.baozhuangfei}}</span></div>
		                   <div class="inlBox center" style="width:16%;text-indent:6.5em;"><span></span></div>
		                   <div class="inlBox center" style="width:3%;text-indent:1em;"><span></span></div>
		                   <div class="inlBox" style="width:15%;text-indent:4em;"><span>{{item.order.zhxbz}}</span></div>
		                   <div class="inlBox" style="width:6%;"><span>{{item.order.cangwei1}}</span></div>
		               </li>
		               <li>
		                   <div class="inlBox center" style="width:10%;text-indent:1em;"><span>{{item.detail[1]!=undefined ? item.detail[1]["yunjia"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[1]!=undefined ? item.detail[1]["jianhsu"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[1]!=undefined ? item.detail[1]["bzh"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[1]!=undefined ? item.detail[1]["zhl"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[1]!=undefined ? item.detail[1]["tiji"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:9%;"><span>{{item.detail[1]!=undefined ? item.detail[1]["tbje"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[1]!=undefined ? item.detail[1]["yunjia"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:16%;text-indent:5em;"><span>{{item.order.zhuangxiefei}}</span></div>
		                   <div class="inlBox wrap center" style="width:10%;"><span></span></div> 
		                   <div class="inlBox wrap center" style="width:29%;"><span>{{item.order.hwzht}}</span></div> 
		              </li>
		              <li>
		                   <div class="inlBox center" style="width:10%;text-indent:1em;"><span>{{item.detail[2]!=undefined ? item.detail[2]["yunjia"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[2]!=undefined ? item.detail[2]["jianshu"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[2]!=undefined ? item.detail[2]["bzh"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[2]!=undefined ? item.detail[2]["zhl"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[2]!=undefined ? item.detail[2]["tiji"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:9%;"><span>{{item.detail[2]!=undefined ? item.detail[2]["tbje"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:5%;"><span>{{item.detail[2]!=undefined ? item.detail[2]["yunjia"] : ""}}</span></div>
		                   <div class="inlBox center" style="width:16%;text-indent:5em;"><span>{{item.order.bandanfei}}</span></div>
		              </li>
		               <li>
		                   <div class="inlBox center" style="width:10%;text-indent:1em;"><span>&nbsp;</span></div>
		                   <div class="inlBox center" style="width:5%;"><span></span></div>
		                   <div class="inlBox center" style="width:5%;"><span></span></div>
		                   <div class="inlBox center" style="width:5%;"><span></span></div>
		                   <div class="inlBox center" style="width:5%;"><span></span></div>
		                   <div class="inlBox center" style="width:9%;"><span></span></div>
		                   <div class="inlBox center" style="width:5%;"><span></span></div>
		                   <div class="inlBox center" style="width:16%;text-indent:5em;"><span></span></div>
		              </li>
		              <li>
		                 <div class="inlBox center" style="width:10%;text-indent:1em;"><span>&nbsp;</span></div>
		                 <div class="inlBox" style="width:34%;text-indent:1em;"><span>{{item.order.tiebieshuoming}}</span></div>
		                 <div class="inlBox" style="width:15%;text-indent:6em;"><span>{{item.orderEntry.releaseWaiting==0 ? "" : "√"}}</span></div>
		              </li>
		              <!-- <li>
	                      <div class="inlBox center" style="width:10%;text-indent:1em;"><span>{{item.detail[0]!=undefined ? item.detail[0]["pinming"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["jianshu"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["bzh"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["zhl"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["tiji"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:9%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["tbje"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>{{item.detail[0]!=undefined ? item.detail[0]["yunjia"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:16%;text-indent:5em;"><span>{{item.order.baozhuangfei}}12</span></div>
	                      <div class="inlBox center" style="width:16%;text-indent:6.5em;"><span></span></div>
	                      <div class="inlBox center" style="width:3%;text-indent:1em;"><span></span></div>
	                      <div class="inlBox" style="width:15%;text-indent:4em;"><span>{{item.order.zhxbz}}12</span></div>
	                      <div class="inlBox" style="width:6%;"><span>{{item.order.cangwei1}}21</span></div>
	                  </li>
	                  <li>
	                      <div class="inlBox center" style="width:10%;text-indent:1em;"><span>12{{item.detail[1]!=undefined ? item.detail[1]["yunjia"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>45{{item.detail[1]!=undefined ? item.detail[1]["jianhsu"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>78{{item.detail[1]!=undefined ? item.detail[1]["bzh"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>9+{{item.detail[1]!=undefined ? item.detail[1]["zhl"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>45{{item.detail[1]!=undefined ? item.detail[1]["tiji"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:9%;"><span>78{{item.detail[1]!=undefined ? item.detail[1]["tbje"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>32{{item.detail[1]!=undefined ? item.detail[1]["yunjia"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:16%;text-indent:5em;"><span>22{{item.order.zhuangxiefei}}</span></div>
	                      <div class="inlBox wrap center" style="width:10%;"><span></span></div>
	                      <div class="inlBox wrap center" style="width:29%;"><span>{{item.order.hwzht}}完好无损</span></div>
	                  </li>
	                  <li>
	                      <div class="inlBox center" style="width:10%;text-indent:1em;"><span>12{{item.detail[2]!=undefined ? item.detail[2]["yunjia"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>78{{item.detail[2]!=undefined ? item.detail[2]["jianshu"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>12{{item.detail[2]!=undefined ? item.detail[2]["bzh"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>12{{item.detail[2]!=undefined ? item.detail[2]["zhl"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>45{{item.detail[2]!=undefined ? item.detail[2]["tiji"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:9%;"><span>36{{item.detail[2]!=undefined ? item.detail[2]["tbje"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:5%;"><span>56{{item.detail[2]!=undefined ? item.detail[2]["yunjia"] : ""}}</span></div>
	                      <div class="inlBox center" style="width:16%;text-indent:5em;"><span>89{{item.order.bandanfei}}</span></div>
	                  </li>
	                  <li>
	                      <div class="inlBox center" style="width:10%;text-indent:1em;"><span>&nbsp;</span></div>
	                      <div class="inlBox center" style="width:5%;"><span></span></div>
	                      <div class="inlBox center" style="width:5%;"><span></span></div>
	                      <div class="inlBox center" style="width:5%;"><span></span></div>
	                      <div class="inlBox center" style="width:5%;"><span></span></div>
	                      <div class="inlBox center" style="width:9%;"><span></span></div>
	                      <div class="inlBox center" style="width:5%;"><span></span></div>
	                      <div class="inlBox center" style="width:16%;text-indent:5em;"><span></span></div>
	                  </li>
	                  <li>
	                      <div class="inlBox center" style="width:10%;text-indent:1em;"><span>&nbsp;</span></div>
	                      <div class="inlBox" style="width:34%;text-indent:1em;"><span>完好无损{{item.order.tiebieshuoming}}</span></div>
	                      <div class="inlBox" style="width:15%;text-indent:6em;"><span>√{{item.orderEntry.releaseWaiting==0 ? "" : "√"}}</span></div>
	                  </li> -->
		           </ul>
		     </div>
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
    	$(".lookBox").hide();
    	$(".printBox").show();
    	//".bgimg").css("background-image","none");
    	//$(".printBox").css("padding-top","55px 0");
    	$(".list-top").css("visibility","hidden");
    
    }
    //自动在打印之后执行
    window.onafterprint = function(){
    	$(".lookBox").show();
    	$(".printBox").hide();
    	$(".list-top").css("visibility","visible");
    	//关闭弹出层
    	//parent.layer.closeAll();
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
    		/* caseinfo:{},
    		caseList: {},
    		fisrtDetail:{},
    		secondDetail:{},
    		thirdDetail:{},
    		orderEntry:{},
    		year:"",
    		month:"",
    		day:"",
    		hour:"", */
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
    								vm.allData[i]['order']['shhrdzhLength'] = getByteLen(vm.allData[i]['order']['shhrdzh'])
    							}
    							/*vm.caseinfo=data.order;
	    						vm.orderEntry=data.orderEntry;
	    						vm.caseList=data.detail;
	     					     if(data.orderEntry.releaseWaiting==0){
	     					    	vm.orderEntry.releaseWaiting=" ";
	     					    }else{
	     					    	vm.orderEntry.releaseWaiting="√";
	     					    }
	    						vm.caseinfo.fhshj=getMyDate(data.order.fhshj);
	    						//时间
	    						vm.year=vm.caseinfo.fhshj.substring(2,4);
	    						vm.month=vm.caseinfo.fhshj.substring(5,7);
	    						vm.day=vm.caseinfo.fhshj.substring(8,10);
	    						vm.hour=vm.caseinfo.fhshj.substring(11,13);
	    					    //付款方式
	    						if(data.order.fzfk==1){vm.caseinfo.fzfk="√"}else{vm.caseinfo.fzfk=""}
	    						if(data.order.dzfk==1){vm.caseinfo.dzfk="√"}else{vm.caseinfo.dzfk=""}
	    						//服务方式
	    						$(".fwBox").each(function(index){
	    							var _value=$(this).attr("value");
	    							if(_value==vm.caseinfo.fwfs){
	    								$(this).next("em").text("√");
	    							}
	    						});
	    						$(".s_fwBox").each(function(index){
	    							var _value=$(this).attr("value");
	    							if(_value==vm.caseinfo.fwfs){
	    								$(this).text("√");
	    							}
	    						});
	    						//返单
	    						$(".fdBox").each(function(index){
	    							var _value=$(this).attr("value");
	    							if(_value==vm.caseinfo.isfd){
	    								$(this).next("em").text("√");
	    							}
	    						});
	    						$(".s_fdBox").each(function(index){
	    							var _value=$(this).attr("value");
	    							if(_value==vm.caseinfo.isfd){
	    								$(this).text("√");
	    							}
	    						});
	    						//货物信息
	   					    	if(vm.caseList[0]!=undefined){
	   					    		vm.fisrtDetail=vm.caseList[0];
	   					    	}
	   					    	if(vm.caseList[1]!=undefined){
	   					    	   vm.secondDetail=vm.caseList[1];
	   					    	}
	   					    	if(vm.caseList[2]!=undefined){
	    					       vm.thirdDetail=vm.caseList[2];
	    					    };
	    						 */
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