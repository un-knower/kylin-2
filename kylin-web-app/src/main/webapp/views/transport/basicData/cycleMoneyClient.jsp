<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>结款和基础设置</title>
<%@ include file="/views/transport/include/head.jsp"%>
<link rel="stylesheet" href="${ctx_static}/publicFile/css/public.css" /> 
<link rel="stylesheet" href="${ctx_static}/transport/basicData/css/basicData.css" />
</head>
<body style="overflow-y: scroll;">
	<span class='layui-breadcrumb breadcrumb'> 
		<a>当前位置 &gt;</a><a>周期性结款客户设置</a>
	</span>
	<div id="rrapp" v-cloak>
		<form class='layui-form rrapp-form'>
			<!-- 查询条件 -->
			<div class='layui-form-item'>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label'>客户编码：</label>
						<div class='layui-input-block'>
							<input type='text' v-model="queryData.khbm" autocomplete="off" id='khbmQuery' class='layui-input' placeholder="请输入客户编码">
						</div>
					</div>
				</div>
				<div class='layui-inline'>
					<div class='input-div'>
						<label class='layui-form-label'>客户名称：</label>
						<div class='layui-input-block'>
							<input type='text' v-model="queryData.khmc" autocomplete="off" id='khmcQuery' class='layui-input' placeholder="请输入客户名称">
						</div>
					</div>
				</div>
				<div class="layui-inline query-btn">
			       <button class="layui-btn query" id='query' @click.prevent="queryInfo">查询</button>
			    </div>
			</div>
			<!-- 客户全部信息信息 -->
			<div v-show="isShowResult">
				<hr/>
				<div class="main-wrap">
					<!-- 客户基本信息 -->
					<div class="table-title">
						<h4>客户基本信息</h4>
						<span>（下表中红色字体部分表示可以修改）</span>
					</div>
					<table class='layui-table'>
						<tr>
							<td class="tag-name">客户名称</td>
							<td colspan="5">{{queryResult.khmc}}</td>
							<td class="tag-name">客户区域</td>
							<td>{{queryResult.khchsh}}</td>
						</tr>
						<tr>
							<td class="tag-name">通讯地址</td>
							<td colspan="5">{{queryResult.khtxdz}}</td>
							<td class="tag-name">客户编码</td>
							<td>{{queryResult.khbm}}</td>
						</tr>
						<tr>
							<td class="tag-name">联系人</td>
							<td>{{queryResult.khlxr}}</td>
							<td class="tag-name">职务</td>
							<td>{{queryResult.lxrzw}}</td>
							<td class="tag-name">电话</td>
							<td>{{queryResult.khdh}}</td>
							<td class="tag-name">货物名称</td>
							<td>{{queryResult.pinming}}</td>
						</tr>
						<tr>
							<td class="tag-name">手机</td>
							<td colspan="2">{{queryResult.khsj}}</td>
							<td class="tagRed-name" colspan="2">是否启用此客户资料</td>
							<td colspan="3">
								<input type="radio" lay-filter='rec_flag' class="yesStart" name="rec_flag" value="1"  title="是">
			      				<input type="radio" lay-filter='rec_flag' class="noStart" name="rec_flag" value="0" title="否">
							</td>
						</tr>
						<tr>
							<td class="tagRed-name">结款方式</td>
							<td colspan="7">
								<div class='layui-input-block layui-input-selmoney'>
								    <select name="uclearingcode" lay-filter="uclearingcode" id="selMoney">
			                             <option value="C1">周期-月结付款</option>
			                             <option value="C2">周期-周结付款</option>
			                             <option value="C3">周期-天结付款</option>
			                             <option value="C4">周期-返单月结</option>
			                             <option value="C5">周期-半月付款</option>
			                             <option value="N1">非周期-现金结款</option>
			                             <option value="N2">非周期-货到付款</option>
			                             <option value="N3">非周期-预付款</option>
			                             <option value="Y1">一次性结款</option>
			                        </select>
								</div>
								<div class='input-div'>
									<label class='layui-form-label layui-label-quantity'>数量（如为月结，输入2，表示两个月结一次）：</label>
									<div class='layui-input-block'>
										<input type='num' name="quantity" v-model="queryResult.quantity" dataname="数量" lay-verify="onlyNum" class='layui-input layui-input-quantity amend-input'>
									</div>
								</div>
								<div class='input-div'>
									<label class='layui-form-label layui-label-isyfk'>是否预付款：</label>
									<div class='layui-input-block'>
										<input type="radio" class="yesYfk" lay-filter='isyfk' name="isyfk" value="1"  title="是">
			      						<input type="radio" class="noYfk" lay-filter='isyfk' name="isyfk" checked='' value="0" title="否">
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="tag-name">有无合同</td>
							<td colspan="3">
								<input type="radio" class="ywht_yes" value="1"  title="有">
			      				<input type="radio" class="ywht_no" value="0" title="无">
								<div style="position:absolute;width:100%;height:100%;z-index:5;top:0;left:0;"></div>
							</td>
							<td class="tag-name">合同申请报告号</td>
							<td colspan="3">{{queryResult.hetonghao}}</td>
						</tr>
						<tr>
							<td class="tag-name">发票名称</td>
							<td colspan="3">{{queryResult.fpmc}}</td>
							<td class="tag-name">税号</td>
							<td colspan="3">{{queryResult.swzh}}</td>
						</tr>
						<tr>
							<td class="tag-name">运输方式</td>
							<td colspan="7">
							    <div class="layui-input-block layui-input-checkbox">
							      <input type="checkbox" data-name="yw_ky" lay-skin="primary" title="铁路快件">
							      <input type="checkbox" data-name="yw_xb" lay-skin="primary" title="行包">
							      <input type="checkbox" data-name="yw_wd" lay-skin="primary" title="五定">
							      <input type="checkbox" data-name="yw_xy" lay-skin="primary" title="行邮">
							      <input type="checkbox" data-name="yw_hk" lay-skin="primary" title="航空">
							      <input type="checkbox" data-name="yw_jzx" lay-skin="primary" title="集装箱">
							      <input type="checkbox" data-name="yw_cj" lay-skin="primary" title="城际配送">
							      <input type="checkbox" data-name="yw_sy" lay-skin="primary" title="水运">
							      <input type="checkbox" data-name="yw_fl" lay-skin="primary" title="分理">
							      <input type="checkbox" data-name="yw_gjys" lay-skin="primary" title="国际运输">
							      <input type="checkbox" data-name="yw_zz" lay-skin="primary" title="中转">
							      <input type="checkbox" data-name="yw_cc" lay-skin="primary" title="仓储">
							      <input type="checkbox" data-name="yw_wx" lay-skin="primary" title="外线">
							    </div>
								<div style="position:absolute;width:100%;height:100%;z-index:5;top:0;left:0;"></div>
							</td>
						</tr>
						<tr>
							<td class="tag-name">客户状态</td>
							<td colspan="3">
								<input type="radio" class='zt_good' value="合作良好"  title="合作良好">
			      				<input type="radio" class='zt_issue' value="问题客户"  title="问题客户">
			      				<input type="radio" class='zt_loss' value="流失客户" title="流失客户">
								<div style="position:absolute;width:100%;height:100%;z-index:5;top:0;left:0;"></div>
							</td>
							<td class="tag-name">理赔属性</td>
							<td colspan="3">
								<input type="radio" class='lipeisx_1' value="1"  title="现场赔付">
			      				<input type="radio" class='lipeisx_2' value="2"  title="频繁赔偿">
			      				<input type="radio" class='lipeisx_3' value="3" title="其它">
								<div style="position:absolute;width:100%;height:100%;z-index:5;top:0;left:0;"></div>
							</td>
						</tr>
						<tr>
							<td class="tag-name">返单要求</td>
							<td colspan="7">{{queryResult.fdyq}}</td>
						</tr>
					</table>
					<!-- 客户周期性结款信息 -->
					<div class="table-title">
						<h4>客户周期性结款信息</h4>
						<!-- <span>（下表中灰色背景框部分表示禁止修改）</span> -->
					</div>
					<table class='layui-table cycle-info'>
						<tr>
							<td class="tag-name" rowspan="2">账户信息</td>
							<td class="tag-name">开户行地址</td>
							<td colspan="6"><input type="text" class="amend-input" name="basicaccount" v-model="queryResult.basicaccount"></td>
						</tr>
						<tr>
							<td class="tag-name">银行账号</td>
							<td colspan="2"><input type="text" class="amend-input" name="bankaccountno" dataname="银行账号" lay-verify="onlyNum" v-model="queryResult.bankaccountno"></td>
							<td class="tag-name">营业执照号码</td>
							<td colspan="3"><input type="text" class="amend-input" name="businessregistrationno" v-model="queryResult.businessregistrationno"></td>
						</tr>
						<tr>
							<td class="tag-name" rowspan="4">联系方式</td>
							<td class="tag-name">总机号</td>
							<td colspan="2"><input type="text" name="switchboardno" class="amend-input" v-model="queryResult.switchboardno"></td>
							<td class="tag-name">公司网址</td>
							<td colspan="3"><input type="text" class="amend-input" name="companyurl" v-model="queryResult.companyurl"></td>
						</tr>
						<tr>
							<td class="tag-name">发货联系人</td>
							<td colspan="2"><input type="text" class="amend-input" name="shipper" v-model="queryResult.shipper"></td>
							<td class="tag-name">对账联系人</td>
							<td colspan="3"><input type="text" class="amend-input" name="checker" v-model="queryResult.checker"></td>
						</tr>
						<tr>
							<td class="tag-name">发货人电话</td>
							<td colspan="2"><input type="text" class="amend-input" name="shippertel" dataname="发货人电话" lay-verify="phoneNum" v-model="queryResult.shippertel"></td>
							<td class="tag-name">对账人电话</td>
							<td colspan="3"><input type="text" class="amend-input" name="checkertel" dataname="对账人电话" lay-verify="phoneNum" v-model="queryResult.checkertel"></td>
						</tr>
						<tr>
							<td class="tag-name">发货人邮件</td>
							<td colspan="2"><input type="text" class="amend-input" dataname="发货人邮件" name="shipperemail" lay-verify="emailNum" v-model="queryResult.shipperemail"></td>
							<td class="tag-name">对账人邮件</td>
							<td colspan="3"><input type="text" class="amend-input" dataname="对账人邮件" name="checkeremail" lay-verify="emailNum" v-model="queryResult.checkeremail"></td>
						</tr>
						<tr>
							<td class="tag-name" rowspan="2">结算信息</td>
							<td class="tag-name">结算代码</td>
							<td colspan="2"><span class="not-fill">{{queryResult.clearingcode}}</span></td>
							<td class="tagRed-name">是否返单结算</td>
							<td colspan="3">
								<input type="radio" class='yesReord' lay-filter='ufandan' name="ufandan" value="1"  title="是">
			      				<input type="radio" class='noReord' lay-filter='ufandan' name="ufandan" value="0" title="否">
							</td>
						</tr>
						<tr>
							<td class="tag-name">结算描述</td>
							<td colspan="2"><span class="not-fill"></span></td>
							<td class="tag-name">周期性结算</td>
							<td colspan="3">
								<input type="radio" class='yesYuejie' lay-filter='isyuejie' name="isyuejie" value="1"  title="是">
			      				<input type="radio" class='noYuejie' lay-filter='isyuejie' name="isyuejie" value="0" title="否">
							</td>
						</tr>
						<tr>
							<td class="tag-name" rowspan="2">合同信息</td>
							<td class="tag-name">合同开始日期</td>
							<td colspan="2"><input type="text" class="amend-input" id="contractstartdate" name="contractstartdate" v-model="queryResult.contractstartdate"></td>
							<td class="tag-name">初次对账单生成日期</td>
							<td colspan="3"><input type="text" class="amend-input" id="firststatementdate" name="firststatementdate" v-model="queryResult.firststatementdate"></td>
						</tr>
						<tr>
							<td class="tag-name">合同终止日期</td>
							<td colspan="2"><input type="text" class="amend-input" id="contractenddate" name="contractenddate" v-model="queryResult.contractenddate"></td>
							<td class="tag-name">下次对账单生成日期</td>
							<td colspan="3"><input type="text" class="amend-input" id="laststatementdate" name="laststatementdate" v-model="queryResult.laststatementdate"></td>
						</tr>
						<tr>
							<td class="tagRed-name">客户状态</td>
							<td colspan="3">
								<input type="radio" class="yesValid" lay-filter='recordstatus' name="recordstatus" value="1"  title="有效">
			      				<input type="radio" class="noValid" lay-filter='recordstatus' name="recordstatus" value="0" title="无效">
							</td>
							<td class="tagRed-name">应收款预警（天）</td>
							<td><input type="text" class="amend-input" name="warningday" dataname="应收款预警（天）" lay-verify="onlyNum" v-model="queryResult.warningday"></td>
							<td class="tagRed-name">合同预警（天）</td>
							<td><input type="text" class="amend-input" name="waringdaycont" dataname="合同预警（天）" lay-verify="onlyNum" v-model="queryResult.waringdaycont"></td>
						</tr>
					</table>
					<!-- 客户资料及合作记录 -->
					<div class="table-title">
						<h4>客户资料及合作记录</h4>
						<span>（下表属于查看类表格，禁止修改）</span>
					</div>
					<table class='layui-table'>
						<tr>
							<td class="tag-name">客户类别</td>
							<td colspan="5">{{queryResult.guesttype}}</td>
							<td class="tag-name">法人代表</td>
							<td colspan="2">{{queryResult.frdb}}</td>
						</tr>
						<tr>
							<td class="tag-name">客户类型</td>
							<td colspan="5">
								<input type="radio" class='khlxbh_max' value="大型客户"  title="大型客户">
			      				<input type="radio" class='khlxbh_mid' value="中型客户" title="中型客户">
			      				<input type="radio" class='khlxbh_min' value="小型客户" title="小型客户">
								<div style="position:absolute;width:100%;height:100%;z-index:5;top:0;left:0;"></div>
							</td>
							<td class="tag-name">准运证明</td>
							<td colspan="2">
								<input type="radio" class='zyzm_yes' value="1"  title="是">
			      				<input type="radio" class='zyzm_no' value="0" title="否">
								<div style="position:absolute;width:100%;height:100%;z-index:5;top:0;left:0;"></div>
							</td>
						</tr>
						<tr>
							<td class="tag-name">负责人</td>
							<td>{{queryResult.fzr}}</td>
							<td class="tag-name">职位</td>
							<td>{{queryResult.fzrzw}}</td>
							<td class="tag-name">电话</td>
							<td>{{queryResult.fzrdh}}</td>
							<td class="tag-name">生日</td>
							<td colspan="2">{{queryResult.fzrsr}}</td>
						</tr>
						<tr>
							<td class="tag-name">仓库地址</td>
							<td colspan="5">{{queryResult.ckdz}}</td>
							<td class="tag-name">电话</td>
							<td colspan="2">{{queryResult.cgydh}}</td>
						</tr>
						<tr>
							<td class="tag-name">主营产品</td>
							<td colspan="8">{{queryResult.hwmch}}</td>
						</tr>
						<tr>
							<td class="tag-name">主要流向</td>
							<td colspan="8">{{queryResult.hwliux}}</td>
						</tr>
						<tr>
							<td class="tag-name">客户简介</td>
							<td colspan="8">{{queryResult.khjj}}</td>
						</tr>
						<tr>
							<td class="tag-name">销售代表</td>
							<td colspan="2">{{queryResult.swdb}}</td>
							<td class="tag-name">登记日期</td>
							<td colspan="2">{{queryResult.djrq}}</td>
							<td class="tag-name">开始合作日期</td>
							<td colspan="2">{{queryResult.ksrq}}</td>
						</tr>
					</table>
					<div class="layui-inline save-wrap" v-show="isShowSave">
				       <button class="layui-btn saveBtn" lay-submit lay-filter="saveBtn" id='saveBtn'>保存修改</button>
				    </div>
				</div>
			</div>
			
		</form>
		
		
	</div>
	<%@ include file="/views/transport/include/floor.jsp"%>
	<%-- <script src="${ctx_static}/publicFile/layui/layui.all.js"></script> --%>
	<script>
   		var base_url = '${base_url}';
   		var ctx_static = '${ctx_static}';
   		//获取当前登录的用户名 
		var userName = '${currentUser.userName}';
		//获取当前登录的公司 
		var userCompany = '${currentUser.company}';
		//获取当前登录的账号 
		var userAccount = '${currentUser.account}';
   </script>
   <script type="text/javascript" src="${ctx_static}/transport/basicData/js/cycleMoneyClient.js"></script>
</body>
</html>