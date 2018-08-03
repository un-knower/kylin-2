<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>送货派车签收单打印</title>
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
				<h2 class='head-middle head-tag title send-title'>送货(派车)签收单</h2>
			</div>
			<div class='tihuoNum'>
				<p class='head-tag list-info' style='margin-left: 28%;'>公司：{{allData.carOutInfo.gs}}</p>
				<p class='head-tag list-info'>NO.{{allData.carOutInfo.id}}</p>
			</div>
			<div style='padding: 0 20px;'>
				<table class='list-table send-table' width="650">
					<thead></thead>
					<tbody>
						<tr>
							<td rowspan='8' class='danzh' style='width: 20px;'>单证号保存栏</td>
							<td colspan="2" style='width:60px;'>运输号码</th>
							<td colspan='4' style='width: 160px;'>{{allData.carOutInfo.yshm}}</td>
							<td colspan="2" style='width: 100px;'>业务类型</td>	
							<td colspan='3' style='width: 150px;'>{{allData.carOutInfo.ywlx}}</td>
							<td style='width: 100px;'>单证员</td>
						</tr>
						<tr>
							<td colspan="2">发货单位</td>
							<td colspan='4'> 
								{{allData.carOutInfo.fhdw}}
							</td>
							<td colspan="2">装车发站</td>	
							<td colspan='3'><!-- /**/ -->
								{{allData.carOutInfo.fhlxr}}
							</td>
							<td>{{allData.carOutInfo.dzy}}</td>
						</tr>
						<tr>
							<td colspan="2">收货单位</td>
							<td colspan='4'>
								{{allData.carOutInfo.shdw}}
							</td>
							<td colspan="2">联系人</td>	
							<td colspan='3'>
								{{allData.carOutInfo.shlxr}}
							</td>
							<td>仓库提货时间</td>
						</tr>
						<tr>
							<td colspan="2">送货地址</td>
							<td colspan='4'>
								{{allData.carOutInfo.shdz}}
							</td>
							<td colspan="2">电话</td>	
							<td colspan='3'>
								{{allData.carOutInfo.shdh}}
							</td>
							<td>
								{{allData.carOutInfo.thTime}}
							</td>
						</tr>
						<tr>
							<td colspan='8' rowspan='4' style='position: relative;padding: 0;'>
								<table style='position: absolute;top: 0;border:none; width: 100%;'>
									<tbody>
										<tr>
											<td colspan='2' class="goodsTd1">货物名称</td>
											<td class="goodsTd">车牌号</td>
											<td class="goodsTd">件数</td>	
											<td class="goodsTd">重量</td>
											<td class="goodsTd">体积</td>
											<td class="goodsTd6">返单</td>
										</tr>
										<tr class='goodsDetailTr' v-for="item in allData.goodsDetailInfo">
											<td colspan='2' class="goodsTdInfo1">{{item.hwmc}}</td>
											<td>{{item.ch}}</td>
											<td>{{item.js}}</td>	
											<td>{{item.zl}}</td>
											<td>{{item.tj}}</td>
											<td v-if="allData.fd" class="goodsTdInfo2">是</td>
											<td v-if="!allData.fd" class="goodsTdInfo2">否</td>
										</tr>
									</tbody>
								</table>
							</td>
							<td colspan="1">到付金额</td>
							<td>代收款</td>
							<td>实发件数</td>
							<td>装卸班组</td>
						</tr>
						<tr>
							<td colspan="1" rowspan='3'>&nbsp;</td>
							<td rowspan='3'>&nbsp;</td>
							<td rowspan='3'>&nbsp;</td>
							<td class="jhshTime">{{allData.carOutInfo.zxbz}}</td>
						</tr>
						<tr>
							<td>仓管员签名</td>
						</tr>
						<tr>
							<td>{{allData.carOutInfo.cgyqm}}</td>
						</tr>
						<tr>
							<td rowspan='5'>调度员填写</td>
							<td colspan="2">车辆性质</td>
							<td colspan="2">车号</td>
							<td >车型</td>
							<td colspan='2'>司机</td>                
							<td>派车调度</td>
							<td colspan="1">{{allData.carOutInfo.pcdd}}</td>
							<td>派车时间</td>
							<td colspan="2">{{allData.carOutInfo.pcpcTime}}</td>
						</tr>
						<tr>
							<td colspan="2">{{allData.vehicleinfofrist.clxz}}</td>
							<td colspan="2">{{allData.vehicleinfofrist.ch}}</td>
							<td colspan="1">{{allData.vehicleinfofrist.cx}}</td>
							<td>{{allData.vehicleinfofrist.sj}}</td>
							<td>{{allData.vehicleinfofrist.sj}}</td>
							<td>起始地</td>
							<td colspan="1">{{allData.carOutInfo.pcqsd}}</td>
							<td>送货地</td>
							<td colspan="2">{{allData.carOutInfo.pcshd}}</td>
						</tr>
						<tr>
 						    <td colspan="2">{{allData.vehicleinfosecond.clxz}}</td>
							<td colspan="2">{{allData.vehicleinfosecond.ch}}</td>
							<td colspan="1">{{allData.vehicleinfosecond.cx}}</td>
							<td>{{allData.vehicleinfosecond.sj}}</td>
							<td>{{allData.vehicleinfosecond.sj}}</td>  
							<td>送货里程</td>
							<td colspan='4'>{{allData.carOutInfo.pcshlc}}</td>
						</tr>
						<tr>
 							<td colspan="2">{{allData.vehicleinfothird.clxz}}</td>
							<td colspan="2">{{allData.vehicleinfothird.ch}}</td>
							<td colspan="1">{{allData.vehicleinfothird.cx}}</td>
							<td>{{allData.vehicleinfothird.sj}}</td> 
							<td>{{allData.vehicleinfothird.sj}}</td> 
							<td rowspan='2'>特别说明</td>
							<td rowspan='2' colspan='4'>{{allData.carOutInfo.pcComm}}</td>
						</tr>
						<tr>
							<td colspan="2">{{allData.vehicleinfofourth.clxz}}</td>
							<td colspan="2">{{allData.vehicleinfofourth.ch}}</td>
							<td colspan="1">{{allData.vehicleinfofourth.cx}}</td>
							<td>{{allData.vehicleinfofourth.sj}}</td>
							<td>{{allData.vehicleinfofourth.sj}}</td>
						</tr>
						<tr>
							<td rowspan='5'>交接双方</td>
							<td colspan='5'>客户签收（签名或盖章）</td>
							<td colspan='3'>车号</td>
							<td colspan='4'>司机</td>
						</tr>
						<tr style='height: 85px;'>
							<td colspan='5'>&nbsp;</td>
							<td colspan='3'>&nbsp;</td>
							<td colspan='4'>&nbsp;</td>
						</tr>
					</tbody>
				</table>
				<!-- <p>第一联提货联（白色）、第二联</p> -->
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
    <script src="${ctx_static}/transport/arrivalsendSignCheck/js/printSendSign.js?v=3"></script>
</body>
</html>