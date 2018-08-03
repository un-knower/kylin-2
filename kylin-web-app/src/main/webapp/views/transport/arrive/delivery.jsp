<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>设置等托放货</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel="stylesheet" href="${ctx_static}/transport/delivery/css/delivery.css?v=2"/>
</head>
<body style="overflow-y: scroll;">
   <span class='layui-breadcrumb breadcrumb'>
		<a href='javaScript:void(0);'>当前位置</a>&gt;
		<a href='javaScript:void(0);'>设置等托放货</a>
   </span>
<style type="text/css">
  [v-cloak] {display: none;} 
</style>
<div id="rrapp" >
   <form class='layui-form '>
		<div class='layui-form-item'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label '>运单编号：</label>
					<div class='layui-input-block'>
						<input id='waybillNum' type='text' class='layui-input'>		
					</div>
				</div>
			</div>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label '>等待放货时间：</label>
					<div class='layui-input-block'>
					    <select  name="interest" id="selectDate">
                             <option value="3">最近三天</option>
                             <option value="7">最近一周</option>
                             <option value="14">最近两周</option>
                             <option value="30">最近一个月</option>
                             <option value="">所有</option>
                        </select>
					</div>
				</div>
			</div>
			 <div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label slabel' >公司：</label>	
					 <c:choose>
						<c:when test = "${CURR_COMPANY_NAME eq '总公司'}">
						 <div class='layui-input-block'>
							<select  class='company' id="companyN">
								<option value="YC">总公司</option>
								<c:if test="${COMPANY_LIST != NULL}">
									<c:forEach items="${COMPANY_LIST }" var="i" varStatus="status">
										<option value="${i.companyCode}" data="${i.companyCode}">${i.companyName}</option>
									</c:forEach>
								</c:if>
							</select> 
						</div>
						</c:when>
						<c:otherwise>
							<div class='layui-input-block'>
								<input id='companyN' data-value='${CURR_COMPANY_CODE}' value="${CURR_COMPANY_NAME}" type='text' class='layui-input' readonly>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<!-- <div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label'>等待放货时间：</label>
					<div class='layui-input-block' style="margin-left:50px">
					    <input type="radio" name="s_type" value="1" title="等待放货" checked="">
                        <input type="radio" name="s_type" value="2" title="所有">
					</div>
				</div>
			</div> -->
			<div class="layui-inline">
		       <button class="layui-btn toCheck"  lay-submit  lay-filter='toCheck' id='toCheck' style="top:-3px" >查询</button>
		    </div>   
		</div>
	</form>
	<hr>
	<!-- <a class="layui-btn" style="margin-left:4%"@click="insertDate">新增</a> -->
	<div style="margin:0 auto 10px auto;width:98%" class="layui-form">
	    <table class="layui-hide" id="test" lay-filter="demo"></table>
	    <div id="laypage"></div> 
	    <script type="text/html" id="operate">
           <a class="layui-btn tabBtn waitBtn"  value='' onclick="vm.revocation(this,1)">等待放货</a>
		   <a class="layui-btn tabBtn backBtn" onclick="vm.revocation(this,3)">撤销</a>
        </script>
        <script type="text/html" id="s_operate">
           <a class="layui-btn tabBtn noticeBtn"  onclick="vm.revocation(this,2)">通知放货</a>
		   <a class="layui-btn tabBtn s_backBtn"  onclick="vm.revocation(this,4)">撤销</a>
        </script>
	</div>
    <div class="newBox" style="margin:20px 0;display:none">
         <div class='layui-form-item'>
			<div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label '>运单编号：</label>
					<div class='layui-input-block'>
					    <input id='orderId'  type='text' class='layui-input' >
					</div>
				</div>
			</div>
		    <div class='layui-inline'>
				<div class='input-div'>
					<label class='layui-form-label slabel' >公司：</label>					
					 <c:choose>
						<c:when test = "${CURR_COMPANY_NAME eq '总公司'}">
						 <div class='layui-input-block'>
							<select  class='company' id="companyName">
								<option value="YC">总公司</option>
								<c:if test="${COMPANY_LIST != NULL}">
									<c:forEach items="${COMPANY_LIST }" var="i" varStatus="status">
										<option value="${i.companyCode}" data="${i.companyCode}">${i.companyName}</option>
									</c:forEach>
								</c:if>
							</select> 
						</div>
						</c:when>
						<c:otherwise>
							<div class='layui-input-block'>
								<input id='companyName' data-value='${CURR_COMPANY_CODE}' value="${CURR_COMPANY_NAME}" type='text' class='layui-input' readonly>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="layui-inline">
		       <button class="layui-btn toCheck"  id='searchBtn' style="top:-3px" @click="searchOrder">查询</button>
		    </div>     
     </div>
     <table class="layui-table" style="margin:0 auto;width:100%">
       <tr>
           <td>发站：</td>
           <td>{{orderList.fazhan}}</td>
           <td>运单编号：</td>
           <td>{{orderList.ydbhid}}</td>
           <td>状态：</td>
           <td v-if="orderList.ddfhzt==1">等待放货</td>
           <td v-else>未等待放货</td>
       </tr>
       <tr>
          <td colspan="6">
                                   备注：<br>
	       <textarea placeholder="暂无数据" class="layui-textarea" id="beizhu">
	        
	       </textarea>
          </td>
        </tr>
     </table>
     <div style="margin:20px auto 0 auto;text-align: center">
         <a class="layui-btn layui-btn-normal" v-if="flag" @click="saveHandler">保存</a>
          <a class="layui-btn layui-btn-disabled" v-if="!flag" >保存</a>
      </div>
</div>
</div>
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
	    var currTime="";
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
   <script type="text/javascript" src="${ctx_static}/transport/delivery/js/delivery.js?v=0"></script>
   <script>
   layui.use(['layer','form'], function(){
	   var form = layui.form;
   });
   </script>

</body>
</html>