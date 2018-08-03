<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>取货（派车）签收单打印</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <link rel="stylesheet" href="${ctx_static}/transport/arrivalsendSignCheck/css/sendSignCheck.css?12"/>
    <style type="text/css">
     @page{margin:5mm 0mm 0 0mm }
     @media print{
     .list-info {width: 30%;}
   		.send-table td { height:20px!important;padding:0 0px!important;font-size:12px}
    	.send-table td p{font-size:12px}
    	.list-top{position: relative;}
    	.print-page{width:650px;margin:0 auto}
   	  }
    </style>
</head>
<body>
<div id='arrivellodiing' v-cloak>
   <div class='print-page'>
			<div  class='list-top'>
				<h2 class='head-middle head-tag title send-title'>取货（派车）签收单</h2>
			</div>
			<div class='tihuoNum'>
				<p class='head-tag list-info' style='margin-left: 3%;'>订单号：{{allData.orderEntity.id}}</p>
				<p class='head-tag list-info'>公司：{{allData.orderEntity.gs}}</p>
				<p class='head-tag list-info'>NO.{{allData.orderEntity.id}}</p>
			</div>
			<div style='padding: 0 20px;'>
				<table class='list-table send-table'  width="650">
					<thead></thead>
					<tbody>
						<tr>
							<td rowspan='9' class='danzh' style="width:20px">客服员填写</td>
							<td style="width:60px">销售员</th>
							<td colspan='2' style="width:100px">{{allData.orderEntity.kfy}}</td>
							<td style="width:50px">客户编码</td>	
							<td colspan='1' style="width:70px">{{allData.orderEntity.khbm}}</td>
							<td colspan='2' style='width:140px'>预计取货时间</td>
							<td colspan='3' style="width:160px">{{allData.orderEntity.jhqhtime}}</td>
						</tr>
						<tr>
							<td>发运网点</td>
							<td colspan='2'> 
								{{allData.orderEntity.yywd}}
							</td>
							<td>发票:</td>	
							<td v-if="allData.orderEntity.isXuYaoFaPiao == 1" >是&nbsp;税号:{{allData.orderEntity.tuoyunrenshuihao}}</td>
							<td v-else="allData.orderEntity.isXuYaoFaPiao == 0" >否&nbsp;税号:{{allData.orderEntity.tuoyunrenshuihao}}</td>	
<!-- 							<td colspan="1">税号:{{allData.orderEntity.tuoyunrenshuihao}}</td> -->
							<td colspan="2">业务类型</td>
							<td v-if="allData.orderEntity.ywlx == -1"  colspan="3">慢件</td>
							<td v-else-if="allData.orderEntity.ywlx == 0" colspan="3">普件</td>	
							<td v-else-if="allData.orderEntity.ywlx == 1" colspan="3">快件</td>	
							<td v-else="allData.orderEntity.ywlx == 2" colspan="3">特快</td>	
						</tr>
						<tr>
							<td>运输方式</td>
							<td colspan='2'>
								{{allData.orderEntity.ysfs}}
							</td>
							<td>目的站</td>	
							<td colspan='1'>
								{{allData.orderEntity.hwdaozhan}}
							</td>
							<td colspan='2'>到达网点</td>
							<td colspan='3'>
								{{allData.orderEntity.hwdaozhanWangDian}}
							</td>
						</tr>
						<tr>
							<td rowspan='5'>托运人</td>
							<td>名称</td>
							<td colspan='4'>
								{{allData.orderEntity.fhr}}
							</td>
							<td>服务方式</td>	
							
							<td v-if="allData.orderEntity.fwfs == 0">仓到站</td>
							<td v-else-if="allData.orderEntity.fwfs == 1">仓到仓</td>
							<td v-else-if="allData.orderEntity.fwfs == 2">站到仓</td>
							<td v-else="allData.orderEntity.fwfs == 3">站到站</td>	
							<td>是否返单</td>	
							<td v-if="allData.orderEntity.isfd == 0">不返单</td>
							<td v-else="allData.orderEntity.isfd == 1">返单</td>
						</tr>
						<tr>
							<td>省市</td>
							<td colspan='4'              >
								{{allData.orderEntity.fhShengFen}}
							</td>
							<td>市县区</td>	
							<td>
								{{allData.orderEntity.fhChengShi}}
							</td>
							<td>代客包装</td>	
							<td v-if="allData.orderEntity.daikebaozhuang == 0">重新打包装</td>
							<td v-else-if="allData.orderEntity.daikebaozhuang == 1">远成专用箱</td>
							<td v-else="allData.orderEntity.daikebaozhuang == 2">否</td>

						</tr>
						<tr>
							<td>取货地址</td>
							<td colspan='4'>
								{{allData.orderEntity.qhadd}}
							</td>
							<td>作业方式</td>	
							<td v-if="allData.orderEntity.zyfs == 0">人工作业</td>
							<td v-else="allData.orderEntity.zyfs == 1">机械作业</td>
							<td>代收款</td>	
							<td>
								{{allData.orderEntity.dsk}}
							</td>
						</tr>
						<tr>
							<td>联系人</td>
							<td colspan='4'>
								{{allData.orderEntity.lxr}}
							</td>
							<td>付款方式</td>	
							<td v-if="allData.orderEntity.fuKuanFangShi == 0">发站付款</td>
							<td v-else="allData.orderEntity.fuKuanFangShi == 1">发站付款</td>	
							<td>保价金额</td>	
							<td>
								{{allData.orderEntity.bbb}}<!-- 字段待定 -->
							</td>
						</tr>
						<tr>
							<td>手机号码</td>
							<td colspan='2'>
								{{allData.orderEntity.shhryb}}
							</td>
							<td>座机号码</td>	
							<td colspan='1'>
								{{allData.orderEntity.shhrlxdh}}
							</td>
							<td colspan='4'>
							返单(名称<span style="display: inline-block;width: 50px;border-bottom:1px solid;text-align:center;">{{allData.orderEntity.fdnr}}</span>
						   	联次<span style="display: inline-block;width: 50px;border-bottom:1px solid;text-align:center;">{{allData.orderEntity.fdlc}}</span>
						          份数<span style="display: inline-block;width: 50px;border-bottom:1px solid;text-align:center;">{{allData.orderEntity.fdsl}}</span>)</td>
						</tr>
						<tr>
							<td colspan='5' style='position: relative;padding: 0;'>
								<table style='top: 0;border:none; width: 100%;'>
									<tbody>
										<tr>
											<td colspan='2' class="goodsTd1">货物名称</td>
											<td class="goodsTd">包装</td>
											<td class="goodsTd">件数</td>	
											<td class="goodsTd">重量</td>
											<td class="goodsTd">体积</td>
										</tr>
										<tr class='goodsDetailTr' v-for="item in allData.goodsList">
											<td colspan='2' class="goodsTdInfo1">{{item.hwmc}}</td>
											<td>{{item.bz}}</td>
											<td>{{item.jianshu}}</td>	
											<td>{{item.zl}}</td>
											<td>{{item.tj}}</td>
										</tr>
									</tbody>
								</table>
							</td>
							<td>客服说明</td>
							<td colspan='2'>{{allData.orderEntity.khsm}}</td>
							<td colspan='2'>
								<p><input type="checkbox" disabled id="songhuoshanglou" class="orderEntity"/>送货上楼</p>
						    	<p><input type="checkbox" disabled id="daikezhuangxie" class="orderEntity"/>代客装卸</p>
							</td>
						</tr>
						<tr>
							<td rowspan='4'>调度填写</td>
							<td>车辆性质</td>
							<td colspan="2">车号</td>
							<td colspan="1">车型</td>
							<td colspan='2'>司机</td>                
							<td>派车调度</td>
							<td colspan="1">{{allData.orderEntity.ddpcdd}}</td>
							<td>派车时间</td>
							<td colspan="1">{{allData.orderEntity.ddpctime}}</td>
						</tr>
						<tr>
							<td>{{allData.vehicleinfofrist.clxz}}</td>
							<td colspan="2">{{allData.vehicleinfofrist.ch}}</td>
							<td colspan="1">{{allData.vehicleinfofrist.cx}}</td>
							<td>{{allData.vehicleinfofrist.sj}}</td>
							<td>{{allData.vehicleinfofrist.sj}}</td>
							<td>起始地</td>
							<td colspan="1">{{allData.orderEntity.ddqsd}}</td>
							<td>取货地</td>
							<td colspan="1">{{allData.orderEntity.ddqhd}}</td>
						</tr>
						<tr>
 						    <td>{{allData.vehicleinfosecond.clxz}}</td>
							<td colspan="2">{{allData.vehicleinfosecond.ch}}</td>
							<td colspan="1">{{allData.vehicleinfosecond.cx}}</td>
							<td>{{allData.vehicleinfosecond.sj}}</td>
							<td>{{allData.vehicleinfosecond.sj}}</td>  
							<td>取货里程</td>
							<td colspan='3'>{{allData.orderEntity.lc}}</td>
						</tr>
						<!-- <tr>
 							<td>{{allData.vehicleinfothird.clxz}}</td>
							<td colspan="2">{{allData.vehicleinfothird.ch}}</td>
							<td colspan="2">{{allData.vehicleinfothird.cx}}</td>
							<td>{{allData.vehicleinfothird.sj}}</td> 
							<td>{{allData.vehicleinfothird.sj}}</td> 
						</tr>
						<tr>
							<td>{{allData.vehicleinfofourth.clxz}}</td>
							<td colspan="2">{{allData.vehicleinfofourth.ch}}</td>
							<td colspan="2">{{allData.vehicleinfofourth.cx}}</td>
							<td>{{allData.vehicleinfofourth.sj}}</td>
							<td>{{allData.vehicleinfofourth.sj}}</td>
						</tr> -->
						<tr>
							<td rowspan='1'>特别说明</td>
							<td rowspan='1' colspan='9'>{{allData.orderEntity.ddcomm}}</td>
						</tr>
						<tr>
							<td rowspan='3'>交接双方</td>
							<td colspan='2'>运单号码</td>
							<td>品名</td>
							<td>包装</td>
							<td>件数</td>
							<td>重量</td>
							<td>体积</td>
							<td colspan='1'>车号</td>
							<td colspan='2'>异常记录</td>
						</tr>
						<tr v-for=''>
							<td colspan='2'></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td colspan='1'></td>
							<td colspan='2'></td>
						</tr>
						<tr>
							<td>司机签名</                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         td>
							<td colspan='5'></td>
							<td colspan='1'>操作员签名</td>
							<td colspan='3'></td>
						</tr>
					</tbody>
				</table>
				<!-- <p>第一联提货联（白色）、第二联</p> -->
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
    <script src="${ctx_static}/transport/arrivalsendSignCheck/js/printReceiptSign.js?v=3"></script>
</body>
</html>