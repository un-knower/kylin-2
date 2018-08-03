<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>装载管理</title>
<%@ include file="/views/transport/include/head.jsp"%>
<link href="${ctx_static}/transport/bundle/css/waybill.css?v=1"
	rel="stylesheet" />
<link href="${ctx_static}/transport/bundle/css/loadmanagement.css"
	rel="stylesheet" />
</head>
<body>
	<div class="wrap">
		<div id="content">
			<div class="con-main">
				<div class="part">
					<h2>装载管理</h2>
					<!--运输信息-->
					<div class="sub-part">
						<!--<h4>运输信息</h4>-->
						<div class="sub-part-form">
							<form action="" id="queryForm" method="post" accept-charset="UTF-8">
								<input type="hidden" name="num" id="num"
									value="${searchModel.num}">
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l">运单号:</label> <input type="text"
											class="txt ajax-submit" name="ydbhid" id="orderId"
											value="${not empty searchModel.ydbhid ? searchModel.ydbhid : '运单号/外线电话'}"
											onkeyup="value=value.replace(/[\W]/g,'') "
											onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
											onblur="if(this.value==''){this.value='运单号/外线电话';this.style.color='#888888'}"
											onfocus="if(this.value=='运单号/外线电话'){this.value='';this.style.color='#333'}"
											style='color:${not empty searchModel.ydbhid ? "#333": "#888888"}' />
									</div>
									<div class="form-con col-25">
										<label class="labe-l">运输起始地 :</label> 
											 <input
											type="text" class="ajax-submit txt" name="fazhan"
											value="${searchModel.fazhan }">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">录入时间 :</label> <input type="text"
											class="ajax-submit txt datetime-picker" name="lrdate"
											value="${searchModel.lrdate }">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">车牌号:</label> <input type="text"
											class="ajax-submit txt" name="chxh"
											value="${searchModel.chxh }">
									</div>
								</div>
								<div class="clearfix">
									<div class="form-con col-25">
										<label class="labe-l">外线名称 :</label> <input type="text"
											class="ajax-submit txt" name="wxName"
											value="${searchModel.wxName}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">发车时间 :</label>
										<input type="text" class="ajax-submit txt datetime-picker" name="fcdate" id="fcDate" value="${fcdate}">
									</div>
									<div class="form-con col-25">
										<label class="labe-l">运输目的地 :</label> <input type="text"
											class="ajax-submit txt" name="daozhan"
											value="${searchModel.daozhan }">
									</div>
									<%-- <div class="form-con col-25">
										<label class="labe-l">司机姓名 :</label> 
										<input type="text" class="txt" name="driverName" value="${searchModel.driverName }">
									</div> --%>
									<div class="form-con col-25">
										<label class="labe-l style='text-indent:5px;'">预计到达时间:</label>
										<input type="text" class="ajax-submit txt datetime-picker"
											name="dddate" value="${searchModel.dddate }">
									</div>
								</div>
								<div class="clearfix">
								<div class="form-con col-25">
										<label class="labe-l">客户名称:</label> <input type="text"
											class="ajax-submit txt" name="clientName" id="clientName"
											value="${searchModel.clientName }">
									</div>
									</div>
								
							</form>
							<form role="form" id="queryForm_hidden" method="post">
								<input type="hidden" name="ydbhid" value="${searchModel.ydbhid}" />
								<input type="hidden" name="fazhan"
									value="${searchModel.fazhan }"> <input type="hidden"
									name="lrdate" value="${searchModel.lrdate }"> <input
									type="hidden" name="chxh" value="${searchModel.chxh }">
								<input type="hidden" name="wxName"
									value="${searchModel.wxName }"> <input type="hidden"
									name="fcdate" value="${searchModel.fcdate }"> <input
									type="hidden" name="daozhan" value="${searchModel.daozhan }">
								<input type="hidden" name="dddate"
									value="${searchModel.dddate }"> <input type="hidden"
									id="chXh" name="chXh" /> <input type="hidden" id="fchrq"
									name="fchrq">
							</form>
						</div>
					</div>
				</div>
			</div>
			<div id="quan-select">
				<a href="javascript:;" class="search-4" id="query">查询</a> 
				<!--<a href="${base_url}/transport/bundle/insert" class="add-bundle">添加装载</a>								
				<a href="javascript:;" class="search-4" id="print">导出</a>			  -->					
				
			</div>
			<!--表格-->
			<div class="content-con">
				<div class="con-header">
					<span class="hed11">车牌号</span>
					<!-- <span class="hed22">司机姓名</span>  -->
					<span class="hed44">运单编号</span> <span class="hed88">客户名称</span> <span
						class="hed44">品名</span> <span class="hed55">件数</span> <span
						class="hed66">重量(吨)</span> <span class="hed77">体积(立方)</span> <span
						class="hed88">发车时间</span> <span class="hed99">预计到达时间</span> <span
						class="hed88">运输成本(元)</span>
				</div>
				<div id="biaoge">
					<%-- <c:forEach items="${page.collection}" var="pc" varStatus="pv">
						<c:set var="len" value="${fn:length(pc.detail) }" />
						<table class="td-table">
							<thead>
								<tr class="aaddress">
									<th class="company" colspan="11"><span class="full">录入时间:<fmt:formatDate
												value='${pc.lrsj}' type='date' pattern='yyyy-MM-dd HH:mm' /></span>
										<span class="kuai">${pc.fazhan} - ${pc.daozhan }</span> <a>
											<input type="button" class="foll" value="查看"
											chxh="${pc.chxh}"
											fchrq="<fmt:formatDate value='${pc.fchrq}' type='both' />" />
									</a></th>
								</tr>
							</thead>
							<tbody> --%>
								<%-- <c:forEach items="${pc.detail}" var="b" varStatus="v">
									<c:if test="${ v.index == 0 }">
										<tr class="aaddress">
											<td class="center" rowspan="${ len }"><span
												style="text-align: center;">${pc.chxh}</span></td>
											<!-- <td class="center" rowspan="${ len }"><span>${pc.driverName}</span></td> -->
											<td class="center" style="word-break: break-all;">${b.ydbhid }</td>
											<td class="center">${b.fhdwmch }</td>
											<td class="center">${b.pinming }</td>
											<td class="center">${b.jianshu }</td>
											<td class="center">${b.zhl}</td>
											<td class="center">${b.tiji}</td>
											<td class="center" rowspan="${ len }"><fmt:formatDate
													value='${pc.fchrq }' type='date' pattern='yyyy-MM-dd HH:mm' /></td>
											<td class="center" rowspan="${ len }"><fmt:formatDate
													value='${pc.yjddshj}' type='date'
													pattern='yyyy-MM-dd HH:mm' /></td>
											<td class="center" rowspan="${ len }">${pc.qd_cost + pc.else_cost }</td>
										</tr>
									</c:if>
									<c:if test="${ v.index > 0 }">
										<tr class="aaddress">
											<td class="center">${b.ydbhid }</td>
											<!-- 此处显示的司机暂时没有数据,先用货运人代替一下 -->
											<td class="center">${b.fhdwmch }</td>
											<td class="center">${b.pinming }</td>
											<td class="center">${b.jianshu }</td>
											<td class="center">${b.zhl}</td>
											<td class="center">${b.tiji}</td>
										</tr>
									</c:if>
								</c:forEach> --%>
							<!-- </tbody>
						</table>-->
				</div>
				<div id="layui_div_page" class="col-sm-12">
					<ul class="pagination">
						<li class="disabled"><span> <span aria-hidden="true">共<span class='page-total'></span>条,每页<span class='page-size'></span>条</span></span></li>
						
						<li><a href="javascript:;" num="1" class='isFirstPage' style='display: none'>首页</a></li>
						<li><a href="javascript:;" num="" class='page-prePage isFirstPage' style='display: none'>&laquo;</a></li>
							
						<li class='insert-li'><a href="javascript:;" num="" class='isLastPage nextPage' style='display: none'>&raquo;</a></li>
						<li><a href="javascript:;" num="" class='isLastPage lastPage' style='display: none'>尾页</a></li>
						
						<li class="disabled"><span><span>共<span class='page-pages'></span>页</span></span></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<form class="bgbox"  style="display:none" ><!-- width="400px" -->
	   <ul class="formUl" >
            <li>
                <label for="cost">运输成本：</label>
                <input name="" type="text" id="cost" data=""/>
            </li>
            <li>
                <label for="otherCost">其它成本：</label>
                <input name="" type="text" id="otherCost" data=""/>
            </li>
             <li class="wxBox">
                <label for="wxName">外线公司名称：</label>
                <input name="" type="text" id="wxName" maxlength="15" data=""/>
            </li>
            <li class="wxBox">
                <label for="wxName">外线单号：</label>
                <input name="" type="text" id="wxItem" maxlength="20" data=""/>
            </li>
            
        </ul>
        <input name="" type="hidden" id="carId"/>
        <input name="" type="hidden" id="driveDate"/>
	    <div class="btnbox">
            <input id="submitBtn" class="btn  btnSave" style="margin-right:10px" value="保存" type="button">
            <input id="closeBtn"  class="btn btnNo" style="background:#F1F1F1;color: #333;" value="取消" type="button">
        </div>
	</form>

</body>
<%@ include file="/views/transport/include/floor.jsp"%>
<script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
<script src="${ctx_static}/transport/excelRead/js/jsrender-min.js"></script>
<script type="text/x-jsrender" id='table-list'>
	<table class="td-table">
		<thead>
			<tr class="aaddress">
				<th class="company" colspan="11">
					<span class="full">录入时间:
						{{:lrsj}}
					</span>
					<span class="kuai">{{:fazhan}} - {{:daozhan}}</span>
					<a>
						<input type="button" class="foll"  value="查看" onclick="check('{{:chxh}}','{{:fchrq}}');"/>
				{{if buildIncome == 1}}
					<shiro:hasPermission name="income:modify:noright"> 
						<input type="button" class="folls"  value="修改成本"  data-wx="{{:cl_owner}}" onclick="edit('{{:qd_cost}}','{{:else_cost}}','{{:chxh}}','{{:fchrq}}',this,'{{:wx_name}}','{{:wx_item}}',1);"/>
					</shiro:hasPermission>
					<shiro:hasPermission name="income:modify:common"> 
						<input type="button" class="folls"  value="修改成本"  data-wx="{{:cl_owner}}" onclick="edit('{{:qd_cost}}','{{:else_cost}}','{{:chxh}}','{{:fchrq}}',this,'{{:wx_name}}','{{:wx_item}}',2);"/>
					</shiro:hasPermission>
					<shiro:hasPermission name="income:modify:manager"> 
						<input type="button" class="folls"  value="修改成本"  data-wx="{{:cl_owner}}" onclick="edit('{{:qd_cost}}','{{:else_cost}}','{{:chxh}}','{{:fchrq}}',this,'{{:wx_name}}','{{:wx_item}}',3);"/>
					</shiro:hasPermission>
				{{/if}}
						
				{{if buildIncome == 0 }}
					<shiro:hasPermission name="income:bundle:save"> 
						<input type="button" class="folls"  value="生成成本"   data-wx="{{:cl_owner}}" onclick="additional('{{:qd_cost}}','{{:else_cost}}','{{:chxh}}','{{:fchrq}}',this,'{{:wx_name}}','{{:wx_item}}');"/>
					</shiro:hasPermission>
				{{/if}}
 </a>
				</th>
			</tr>
		</thead>
	<tbody>
		<tr class="aaddress">
			<td class="center" rowspan={{:len}}>
				<span style="text-align: center;">{{:chxh}}</span>
			</td>
		{{for detail}}
			{{if #getIndex() == 0}}
					<td class="center" style="word-break: break-all;">{{:ydbhid}}</td>
					<td class="center">{{:fhdwmch}}</td>
					<td class="center">{{:pinming}}</td>
					<td class="center">{{:jianshu}}</td>
					<td class="center">{{:zhl}}</td>
					<td class="center">{{:tiji}}</td>
			{{/if}}
		{{/for}}
			<td class="center" rowspan={{:len}}>
				{{:fchrq}}
			</td>
			<td class="center" rowspan={{:len}}>
				{{:yjddshj}}
			</td>
			<td class="center" rowspan={{:len}}>{{:totlecost}}</td>
	</tr>
	{{for detail}}
			{{if #getIndex() > 0}}
				<tr class="aaddress">
					<td class="center">{{:ydbhid}}</td>
					<td class="center">{{:fhdwmch}}</td>
					<td class="center">{{:pinming}}</td>
					<td class="center">{{:jianshu}}</td>
					<td class="center">{{:zhl}}</td>
					<td class="center">{{:tiji}}</td>
				</tr>
			{{/if}}
	{{/for}}
</tbody>
</table>
</script>
<script type="text/javascript">
$("#clientName").easyAutocomplete({
	minCharNumber: 1,//至少需要1个字符
	url: function(phrase) {
		return base_url + "/transport/adjunct/customer?something=" + encodeURI(encodeURI(phrase));
	},
	getValue: function(element) {
		return element.customerName;
	},
	//listLocation: "objects",
	list: {
		onSelectItemEvent : function(){
        	var item = $("#clientName").getSelectedItemData();
        	$("#clientName").val(item.customerName);

        },
        onLoadEvent:function(){
        	$('#eac-container-fhdwmch').css('width', $("#clientName").outerWidth());
        }
    },
	requestDelay : 500
});
	var base_url = '${base_url}', static_url = '${ctx_static}';
	layui.use('layer', function(){
		layer = layui.layer;
	})
	function getLocalTime(nS) {  
		 var   now=new Date(nS);
		 var   year=now.getFullYear();     
         var   month=now.getMonth()+1;     
         var   date=now.getDate();     
         var   hour=now.getHours();     
         var   minute=now.getMinutes(); 
         var   seconds=now.getSeconds();
         return   year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+seconds;           
	}
	/*获取ajax查询字段*/
	function ajaxSubmit(num) {
		num = num || 1;
		var dataArr = {};
		dataArr['num'] = num;
		$('.ajax-submit').each(function(index, elem) {
			var name = $(elem).attr('name');
			var value = $(elem).val();
			dataArr[name] = value;
		})
		dataArr = JSON.stringify(dataArr);
		$.ajax({
			url : base_url + '/transport/bundle/manage/query',
			type : 'post',
			dataType : 'JSON',
			contentType : 'application/json',
			data : dataArr,
			beforeSend : function() {
				loading = layer.load(0, {
					shade : [ 0.5, '#fff' ]
				})
			},
			success : function(data) {
				if ($('.page-num')) {
					$('.page-num').remove();
				}
				layer.close(loading);
				$('.page-total').html(data.page.total);
				$('.page-size').html(data.page.size);
				$('.page-pages').html(data.page.pages);
				$.each(data.page.collection, function(q,w){
					var len = w.detail.length;
					w.len = len;
					w.fchrq = getLocalTime(w.fchrq);
					w.lrsj = getLocalTime(w.lrsj);
					w.yjddshj = getLocalTime(w.yjddshj);
					w.totlecost = parseFloat(w.qd_cost) + parseFloat(w.else_cost);
					w.qd_cost=parseFloat(w.qd_cost);
					w.else_cost=parseFloat(w.else_cost);			
				})
				
				$('#biaoge').html($('#table-list').render(data.page.collection));
				if (data.page.pages > 1) {
					if (!data.page.isFirstPage) {
						$('.isFirstPage').show();
						$('.page-prePage').attr('num', data.page.prePage);
					} else {
						$('.isFirstPage').hide();
					}
					$.each(data.page.navigatepageNums, function(i, e) {
						if (e == data.page.num) {
							$('.insert-li').before(
									'<li class="active page-num"><a href="javascript:;" num='+e+'>'
											+ e + '</a></li>');
						} else {
							$('.insert-li').before(
									'<li class="page-num"><a href="javascript:;" num='+e+'>'
											+ e + '</a></li>');
						}

					})
					if (!data.page.isLastPage) {
						$('.isLastPage').show();
						$('.nextPage').attr('num', data.page.nextPage);
						$('.lastPage').attr('num', data.page.pages);
					} else {
						$('.isLastPage').hide();
					}
				}else{
					$('.isLastPage').hide();
				}
				eachPage();
				//watcheach();
			},
			error : function(data) {
				layer.close(loading);
			}
		})
	}
	
	/*新增 当输入运单编号时  日期清空  by wyp*/
 	$("#orderId").on("input propertychange",function(){  
		if($.trim($(this).val())!=""){
			$("#fcDate").val("");
		}else{
			$('#fcDate').val(moment().subtract(31, 'days').format('YYYY-MM-DD') +' 至 '+ moment().add(1, 'days').format('YYYY-MM-DD'));
		}
	}); 
		
	function eachPage(){
		$(".pagination a").each(function(i){	
			$(this).unbind('click').on('click', function(){
					$("#queryForm input[name='num']").val($(this).attr("num"));
					ajaxSubmit($(this).attr("num"));
				    /* $("#form").submit();  */
			})
		});
	}
	function watcheach(){
		$("#biaoge input[type='button']").each(function() {
			$(this).unbind('click').on('click',function() {
				$("#fchrq").val($(this).attr("fchrq"));
				$("#chXh").val($(this).attr("chxh"));
				check($(this).attr("chxh"),$(this).attr("fchrq"));
				
				//$("#queryForm_hidden").attr("action",base_url+ "/transport/bundle/manage/check.do");
				//$("#queryForm_hidden").submit();
			})
		})
	}
	$(document).ready(function() {
						//日期选择空间
						$('.datetime-picker').each(function(i, val) {
							$(this).flatpickr({
								mode : "range"
							});
						});
						/* //日期选择空间
						$('.datetime-picker').each(function(i,val) { 
							$(this).flatpickr({ mode: "range" ,minDate: "1970-01-01"});
						}); */

						/**
						 * 功能描述：点击保存提交表单
						 * 作者：yanxf 
						 * 时间：2017-05-05
						 */
						$("#query").click(function() { //点击弹出框出现
							$("#queryForm input[name='num']").val("1");
							/* $("#queryForm").submit(); */
							ajaxSubmit();
						});
			
					});

	function check(chXh,fchrq){
		var width=$(document).width();
		$("body").css ("overflow-y","hidden");
		$("body").width(width)
		layer.open({
			  type: 2,
			  title: "查看与修改页面（车牌号:"+chXh+"，发车时间："+fchrq+"）",
			  closeBtn: 1, //显示关闭按钮
			  shade: [0],
			  area: ['990px','80%'],
			  //offset: 'rb', //右下角弹出
			 // time: 2000, //2秒后自动关闭
			  anim: 2,
			  content: [base_url + "/transport/bundle/manage/check/"+encodeURI(encodeURI(chXh))+"/"+fchrq, 'yes'], //iframe的url，no代表不显示滚动条
			  end: function () {
				  $("body").css ('overflow-y','auto');
				  $("body").width(width)
	            }
		});
	}
	
	//表单弹窗
	function edit(cost,ele,chxh,fchrq,obj,wxname,wxitem,level){
		var url = '';
		if(level&&level==1){
			url = '/transport/bundle/modifyIncomeNoRight';//没有权限
		}else if(level&&level==2){
			url = '/transport/bundle/modifyIncomeCommon';//普通权限
		}else if(level&&level==3){
			url = '/transport/bundle/modifyIncomeManager';//财务经理
		}
		var width=$(document).width();
		$("body").css ("overflow-y","hidden");
		$("body").width(width);
		$("#carId").val(chxh);
		$("#driveDate").val(fchrq);
		$("#wxName").val(wxname).attr("data",wxname);
		$("#wxItem").val(wxitem).attr("data",wxitem);
		$("#cost").val(cost).attr("data",cost);
	    $("#otherCost").val(ele).attr("data",ele);
	    var wxinf=$(obj).attr("data-wx");
	    var height="";
	    if(wxinf=="外线"){
	    	$(".wxBox").show();
	    	height='350px'
	    } else{
	    	$(".wxBox").hide();
	    	height='240px';
	    }
		  layer.open({
			  type: 1,
			  title: "修改成本",
			  closeBtn: 1, //显示关闭按钮	
			  shade: [0],
			  area: ['450px',height],
			  
			  anim: 2,
			  content: $('.bgbox'),
			  end: function () {
				  $("body").css ('overflow-y','auto');
	            }
		});
		 getCost(chxh,fchrq,base_url + url);
	}
	//补录成本
	function additional (cost,ele,chxh,fchrq,obj,wxname,wxitem){
		var width=$(document).width();
		$("body").css ("overflow-y","hidden");
		$("body").width(width);
		$("#carId").val(chxh);
		$("#driveDate").val(fchrq);
		$("#wxName").val(wxname).attr("data",wxname);
		$("#wxItem").val(wxitem).attr("data",wxitem);
		$("#cost").val(cost).attr("data",cost); 
	    $("#otherCost").val(ele).attr("data",ele);
	    var wxinf=$(obj).attr("data-wx");
	    var height="";
	    if(wxinf=="外线"){
	    	$(".wxBox").show();
	    	height='350px'
	    } else{
	    	$(".wxBox").hide();
	    	height='240px';
	    	
	    }
		layer.open({
			  type: 1,
			  title: "补录成本",
			  closeBtn: 1, //显示关闭按钮	
			  shade: [0],
			  area: ['450px',height],
			  anim: 2,
			  content: $('.bgbox'),
			  end: function () {
				  $("body").css ('overflow-y','auto');
	            }
		});
		getCost(chxh,fchrq,base_url + '/transport/bundle/income/save');
	}
	
	//表单提交
	function getCost(chxh,fchrq,url){
		$('#submitBtn').unbind('click').on('click',function(){
			
			//var outerOrder=$.trim($("#outerOrder").val())
			if( $.trim($("#cost").val())=="" || $.trim($("#otherCost").val())==""){
				layer.msg("不能为空！");
				return false;
			}
			
			if(isNaN($("#cost").val()) || parseFloat($("#cost").val())<0 ){
				layer.msg("请输入正确的数字！");	
				return false;
			}
			if(isNaN($("#otherCost").val()) || parseFloat($("#otherCost").val())<0){
				layer.msg("请输入正确的数字！");	
				return false;
			}	
			var cost=$("#cost").attr("data");
			var otherCost=$("#otherCost").attr("data");
		/* 	if (outerOrder==''){
				if(!re.test(outerOrder)){
					layer.msg("请输入正确外线单号！");	
					return false;
				}
			} */
			
			/* if(parseFloat($.trim($("#cost").val()))==cost && parseFloat($.trim($("#otherCost").val()))==otherCost){
				layer.msg("成本未做修改！");	
				return false;
			}	
			 */
			var baseValue= {
					chxh:$("#carId").val(),
					fchrq:$("#driveDate").val(),
					qdCost:$.trim($("#cost").val()),
					elseCost:$.trim($("#otherCost").val()),
					wxName:$.trim($("#wxName").val()),
					wxItem:$.trim($("#wxItem").val()),
				};
			 
			//background:#F1F1F1;; 
			$('.btnSave').css({'background':'#F1F1F1','color':'#333'});//#EEE0E5
			$(".btnSave").attr("disabled","disabled"); 
			$.ajax({
				type : "post",
				url :url,
				data :JSON.stringify(baseValue),
				dataType : 'JSON',
				contentType : 'application/json',
				success : function(data) {
					if(data.resultCode=="200"){					
						layer.closeAll(); 
						layer.msg('保存成功！',  {
							icon: 1,
							time:1000
						  },function(){
							  $("#queryForm input[name='num']").val("1");
							  ajaxSubmit();			  
						  });	
					}else{
						layer.closeAll(); 
						layer.msg(data.reason);
					}
					$('.btnSave').css({'background':'#358FF0','color':'#fff'});//#EEE0E5
					$(".btnSave").removeAttr("disabled");
				},
		        error : function(XMLHttpRequest, textStatus, errorThrown) {
		            if(XMLHttpRequest.readyState == 0) {
		            //here request not initialization, do nothing
		            } else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
		                alert("服务器忙，请重试!");
		            } else {
		                alert("系统异常，请联系系统管理员!");
		            }
		            $('.btnSave').css({'background':'#358FF0','color':'#fff'});//#EEE0E5
					$(".btnSave").removeAttr("disabled");
		        }
			});	
	});
	
}
	  //表单关闭
	   $("#closeBtn").click(function() {
		   layer.closeAll();

	   });
	  $("#print").click(function(){
		  var url = base_url + "/transport/bundle/manage/print";
		  $("#queryForm").attr("action",url);
		  $("#queryForm").submit();
	  })

</script>
</html>