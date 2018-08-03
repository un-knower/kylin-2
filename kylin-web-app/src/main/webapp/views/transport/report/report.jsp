<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ include file="/views/transport/include/head.jsp"%>
    <title>报表查询</title>
    <link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
    <link rel='stylesheet' href="${ctx_static}/publicFile/css/public.css">
    <style>
		.centerBtn{text-align: center;margin-top:15px;}
		.centerBtn button{padding: 0 60px;}

	   .layui-input-block select, .layui-input-block option{appearance:none;-moz-appearance:none;-webkit-appearance:none;}
	   #currentPlace, #sumReport{display:inline-block;}
	   #sumReport{padding-right: 8px;line-height: 22px;font-size: 16px;color: #333!important;}
	   .layui-table tr th{background:rgb(93,211,241);}
	   .layui-table tr th, .layui-table tr{text-align:center;}
	   .layui-table td, .layui-table th{padding:9px 5px;}
	   .layui-table tr:hover{background:#f5f5f5;}
	   .layui-table td{word-break:break-all;}

	   
	   .layui-form-item .layui-inline{margin-right:0;}
	   .layui-form-label{width:10%;}
	   .layui-input-block{width:87%;}
	   .layui-btn-disabled, .layui-btn-disabled:active, .layui-btn-disabled:hover{color:#333;}
	   /* .reportQueryChild .input-div{float:left;} */
	   .reportQueryChild .input-div{display:inline-block;}
	   .childBox .input-div{display:inline-block;}
	   .childBox .layui-form-label{width:initial;}
	   #reportQueryW{text-align: center;}
	   .reportQueryChild .layui-form-label{width:initial;}
	   .reportQueryChild .layui-input-block{width:100px;}
		.delReport .layui-icon-delete{font-size:24px;margin-left:10px;cursor: pointer;}
		.one01{display:none!important;}
		.delReport{position: relative;top: -15px;}
		.layui-tab-title .layui-this{color:#fff;background-color:#009688}
		.reportList .layui-tab-title{border-bottom: 1px solid #009688;}
		.childBox .layui-input-block{width:100px;}
		.childBox{text-align: center;}
		@media screen and (max-width: 1600px){
			.reportQueryChild .input-div{float:left;width:13%;}
			.reportQueryChild .layui-input-block{width:87%;}
			.input-div.delReport{width:initial;position: relative;top: 42px;}
		}
    </style>
</head>
<body>
<div class="reportList">
   		 		<!-- 父报表维护 -->
				<div class="reportParent">
					<div>
						<span id="currentPlace" class="layui-breadcrumb breadcrumb no-print">
							<a href="javaScript:void(0);">当前位置:</a>&gt;
					 		<a href="javaScript:void(0);">报表配置</a>
						 </span>
						 <button class="layui-btn " id='addBtn'>新增</button>
						 <button class="layui-btn" id='disableBtn' data="0">锁定</button>
					 </div>
					<div class="layui-form">
						 <div class="layui-form-item ">
						 	<div class="layui-inline layui-col-md12">
						 		<div class="input-div">
									<label class='layui-form-label'>已创建的报表:</label>
									<div class='layui-input-block addReport'>
										<select class='layui-input'  lay-filter="test" id="reportSelect" name="modules" lay-verify="" lay-search="">
											<option value="" >点击修改报表</option>
										</select>
									</div>
								</div>
							</div>
						</div>
					</div>
						  
					<div id="saveReport" class="layui-form-item">
						<div>
							<div class="clearfix">
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>报表id:</label>
									<div class="layui-input-block">
										<input class='layui-input layui-btn-disabled' type="text" name="reportId" id="reportId" readonly="readonly" value=""/>
									</div>
								</div>
							</div>
				
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>报表名称:</label>
									<div class="layui-input-block">
										<input class='layui-input' type="text" name="reportName" id="reportName" placeholder="请输入中文" value=""/>
									</div>
								</div>
							</div>
				
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>报表展示头:</label>
									<div class="layui-input-block">
										<textarea class='layui-textarea' class="layui-textarea" name="reportHeaderNames" id="reportHeaderNames"   placeholder="请输入中文" value=""class="layui-textarea" value=""></textarea>
									</div>
								</div>
							</div>
				
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>报表展示头key:</label>
									<div class="layui-input-block">
										<textarea class='layui-textarea' class="layui-textarea" type="text" name="reportHeaders" id="reportHeaders" placeholder="请输入英文" value=""class="layui-textarea" value=""></textarea>
									</div>
								</div>
							</div>
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>报表sql:</label>
									<div class="layui-input-block">
										<!--<input class='layui-input' type="text" name="reportSelectSql" id="reportSelectSql" value=""/>-->
										<textarea placeholder="" id="reportSelectSql" name="reportSelectSql" class="layui-textarea" value=""></textarea>
									</div>
								</div>
							</div>
							
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>报表合计字段:</label>
									<div class="layui-input-block">
										<input class='layui-input' type="text" name="reportSumKey" id="reportSumKey"  value=""/>
									</div>
								</div>
							</div>
				
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>报表合计sql:</label>
									<div class="layui-input-block">
										<!--<input class='layui-input' type="text" name="reportSumSql" id="reportSumSql" value=""/>-->
										<textarea placeholder="" id="reportSumSql" name="reportSumSql" class="layui-textarea" value=""></textarea>
									</div>
								</div>
							</div>
							
							
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>报表排序key:</label>
									<div class="layui-input-block">
										<input class='layui-input' type="text" name="orderByKey" id="orderByKey" value=""/>
									</div>
								</div>
							</div>
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>分组数据key:</label>
									<div class="layui-input-block">
										<input class='layui-input' type="text" name="groupByKey" id="groupByKey" value=""/>
									</div>
								</div>
							</div>
				
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>指定公司数据字段:</label>
									<div class="layui-input-block">
										<input class='layui-input' type="text" name="dataBaseKey" id="dataBaseKey"  value=""/>
									</div>
								</div>
							</div>
				
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>需要替换的sql:</label>
									<div class="layui-input-block">
										<!--<input class='layui-input' type="text" name="replaceSql" id="replaceSql"  value=""/>-->
										<textarea placeholder="" id="replaceSql" name="replaceSql" class="layui-textarea" value=""></textarea>
									</div>
								</div>
							</div>
							
							<div class="layui-inline layui-col-md12" style="height:1px;background:#ccc;margin: 40px 0;"></div>
							<div class="layui-inline layui-col-md12">
								<div class="input-div">
									<label class='layui-form-label'>报表查询条件:</label>
									<div class="layui-input-block">
										<input style="width:96%;display:inline-block" class='layui-input layui-btn-disabled' readonly="readonly" type="text" name="reportQueryKeys" id="reportQueryKeys" value=""/>
										<i class="layui-icon layui-icon-add-circle" id="layuiAdd" style="float:right;font-size:34px;cursor: pointer;">&#xe61f;</i> 
									</div>
								</div>
							</div>
							
							</div>
							<div id="reportQueryW" class="clearfix">
								<div class="reportQueryChild reportQueryChildBox layui-form clearfix">
									<div class="input-div">
										<label class='layui-form-label'>查询条件名称:</label>
										<div class="layui-input-block">
											<input class='layui-input' type="text" name="" id="" value=""/>
											<input type="hidden" class='layui-input' type="text" name="one01" id="" value=""/>
										</div>
									</div>
									<div class="input-div">
										<label class='layui-form-label'>提交时参数名:</label>
										<div class="layui-input-block">
											<input class='layui-input' type="text" name="" id="" value=""/>
										</div>
									</div>
									<div class="input-div">
										<label class='layui-form-label'>查询条件默认值:</label>
										<div class="layui-input-block">
											<input class='layui-input' type="text" name="" id="" value=""/>
										</div>
									</div>
									<div class="input-div">
										<label class='layui-form-label'>查询条件类型:</label>
										<div class="layui-input-block">
											<select class='layui-input'>
												<option value="" >请选择</option>
												<option value="1" >select</option>
												<option value="2" >input</option>
												<option value="3" >radio</option>
												<option value="4" >checkBox</option>
												<option value="5" >data</option>
											</select>
										</div>
									</div>
									<div class="input-div">
										<label class='layui-form-label'>查询条件数值来源:</label>
										<div class="layui-input-block">
											<input class='layui-input' type="text" name="" id="" value=""/>
										</div>
									</div>
									<div class="input-div">
										<label class='layui-form-label'>查询条件数值:</label>
										<div class="layui-input-block">
											<input class='layui-input' type="text" name="" id="" value=""/>
										</div>
									</div>
									<div class="input-div delReport" style="visibility: hidden!important;">
										<i class="layui-icon layui-icon-delete"></i> 
									</div>
								</div>
							</div>
							
							<!-- 子报表维护 -->
							<div class="layui-inline layui-col-md12" style="height:1px;background:#ccc;margin: 40px 0;"></div>
							<div class="childBox clearfix">
								<div class="input-div">
									<label class='layui-form-label'>reportConfigId:</label>
									<div class="layui-input-block">
										<input class='layui-input' type="text" name="childConfigId" id="childConfigId" value=""/>
									</div>
								</div>
								<div class="input-div">
										<label class='layui-form-label'>子报表链接:</label>
										<div class="layui-input-block">
											<input class='layui-input' type="text" name="linkUrl" id="linkUrl" placeholder="" value=""/>
										</div>
									</div>
									<div class="input-div">
										<label class='layui-form-label'>参数:</label>
										<div class="layui-input-block">
											<input class='layui-input' type="text" name="params" id="params" placeholder="" value=""/>
										</div>
									</div>
									<div class="input-div">
										<label class='layui-form-label'>按钮名称:</label>
										<div class="layui-input-block">
											<input class='layui-input' type="text" name="childName" id="childName" placeholder="" value=""/>
										</div>
									</div>
									
									<div class="input-div">
										<label class='layui-form-label'>子报表ID:</label>
										<div class="layui-input-block">
											<input class='layui-input' type="text" name="subReportId" id="subReportId" placeholder="" value=""/>
										</div>
									</div>
									<div class="input-div">
										<label class='layui-form-label'>子报表查询条件:</label>
										<div class="layui-input-block">
											<input class='layui-input' type="text" name="subParams" id="subParams" placeholder="" value="" ></textarea>
										</div>
									</div>
									
							</div>
							
							<div class="centerBtn">
								<button class="layui-btn saveBtn" type="button" value="保存" onclick="saveReport()">保存</button>
							</div>
						</div>
				</div>
					
				</div>
				   		 
</div>
	<%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
    var base_url = '${base_url}';
    var ctx_static = '${ctx_static}';
  //我们强烈推荐你在代码最外层把需要用到的模块先加载
    layui.use(['layer','form'], function(){
		layer = layui.layer;
		var form = layui.form;
        form.render();
	});
    
    var cases="";
    $(function(){
    	reportSelect($("#reportSelect"));
	})
	//报表下拉框数据
	function reportSelect(thisDom){
		$.ajax({
			   url:base_url+'/report/report/data',
			   dataType:"json",
			   contentType:'application/json',
			   type:'post',
			   success: function(data){
				   console.log(data);
					$.each(data.reportList, function(i,k){
						$(thisDom).append('<option value='+k.reportId+'>'+k.reportName+'</option>');
					});
					layui.use('form', function(){
					  var form = layui.form;
					  form.render();
					});
					
			   },
			   error:function(XMLHttpRequest, textStatus, errorThrown){
			   		if(XMLHttpRequest.readyState == 0) {}
			   		else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
			   			layer.alert("服务器忙，请重试!");
					} else {
						layer.alert("系统异常，请联系系统管理员!");
					}
			   }
		});
	}

    //切换下拉报表名称
    function changeReport(obj){
    	//findReport($(obj).val());
    	findReport($(obj).attr('lay-value'));
    }
/*     //下拉菜单
    $('#reportSelect').change(function(){
    	changeReport(this);
    }) */
    layui.use('form', function(){
    	  var form = layui.form;
    	  form.on('select(test)', function(data){
        	  changeReport(this);
       		});   
    	});
    
  //禁用输入
	
    $('#disableBtn').click(function(){
    	if($('#disableBtn').hasClass('layui-btn-disabled')){
    		layer.msg('启用可以修改报表模式');
    		$(this).attr('data','0');
    		$('#disableBtn').css({'background':'#009688'});
    		$('#disableBtn').removeClass('layui-btn-disabled');
    		$(this).text('锁定');
        	$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").removeClass('layui-btn-disabled');
    		$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").prop('readonly',false);
   	  	}else{
	   	  	layer.msg('启用禁止修改报表模式');
			$(this).attr('data','1');
			$('#disableBtn').css({'background':'#ccc'})
			$('#disableBtn').addClass('layui-btn-disabled');
			$(this).text('解锁');
	    	$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").addClass('layui-btn-disabled');
			$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").prop('readonly',true)
   	  	}
    }) 
    
    //保存报表数据
    function saveReport(){
    	if($('#reportName').val()==""&&$('#reportHeaderNames').val()==""&&$('#reportHeaders').val()==""&&$('#reportHeaders').val()==""&&$('#orderByKey').val()==""&&$('#reportSumKey').val()==""&&$('#groupByKey').val()==""&&$('#dataBaseKey').val()==""){
    		layer.alert('请填写正确的数据');
    		return false;
    	}
/* 		var yyy=[];
    	$('.reportQueryChild.reportQueryChildBox').each(function(v,k){
    		var ssss=$('.reportQueryChild.reportQueryChildBox').eq(v).find('input').val();
    		yyy.push(ssss);
    	})
		for(var i=0;i<yyy.length;i++){
			if(yyy[i]==""){
				layer.alert("id不能为空");
				return false;
			}
		} */
       var rConfig= {};
       var reportConfig ={};
       reportConfig.reportId=$("#reportId").val();//报表id:
       reportConfig.reportName=$("#reportName").val();//报表名称:
       reportConfig.reportHeaderNames=$("#reportHeaderNames").val();//报表展示头:
       reportConfig.reportHeaders=$("#reportHeaders").val();//报表展示头key:
       reportConfig.orderByKey=$("#orderByKey").val();//报表排序key:
       reportConfig.reportSumKey=$("#reportSumKey").val();//报表合计字段:
       reportConfig.groupByKey=$("#groupByKey").val();//分组数据key:
       reportConfig.dataBaseKey=$("#dataBaseKey").val();//指定公司数据字段:
       reportConfig.reportSelectSql=$("#reportSelectSql").val();//报表sql:
       reportConfig.reportSumSql=$("#reportSumSql").val();//报表合计sql:
       reportConfig.replaceSql=$("#replaceSql").val();//需要替换的sql:
       
       reportConfig.lockFlag=$('#disableBtn').attr('data');//是否锁定报表
       console.log(reportConfig.lockFlag);
       
       reportConfig.reportQueryKeys=$("#reportQueryKeys").val();//报表查询条件:
       var newArr=[];
      /*  var inpArr=$('#reportQueryW .reportQueryChild.reportQueryChildBox');
       var reportQueryChildLength=$('#reportQueryW .reportQueryChild.reportQueryChildBox'); */
       var inpArr=$('#reportQueryW .reportQueryChild');
       var reportQueryChildLength=$('#reportQueryW .reportQueryChild');
        inpArr.each(function(i,k){
            var newWa={};
        	//console.log(k);
        	newWa.queryId          =reportQueryChildLength.eq(i).find('input').eq(0).val();
        	newWa.queryName        =reportQueryChildLength.eq(i).find('input').eq(1).val();
        	newWa.queryKey         =reportQueryChildLength.eq(i).find('input').eq(2).val();
        	newWa.queryDefaultValue=reportQueryChildLength.eq(i).find('input').eq(3).val();
    		newWa.queryType        =reportQueryChildLength.eq(i).find('select option:selected').val();
 			newWa.querySrcType     =reportQueryChildLength.eq(i).find('input').eq(4).val();
			newWa.querySrc         =reportQueryChildLength.eq(i).find('input').eq(5).val();
	       	newArr.push(newWa);
       })
       rConfig.reportConfig=reportConfig;
       rConfig.reportQueryList=newArr;

       	/* 子报表参数 */
      	 var subList ={};
	  	 subList.childConfigId= $('#childConfigId').val();//childConfigId
	  	 subList.linkUrl      = $('#linkUrl').val();//
	  	 subList.params       = $('#params').val();
	  	 subList.childName    = $('#childName').val();
	  	 subList.subReportId  = $('#subReportId').val();
	  	 subList.subParams    = $('#subParams').val();
  		rConfig.subList =subList;
        console.log(JSON.stringify(rConfig)+'传的data');
        return false;
       $.ajax({
			   url:base_url+'/report/saveDeployNew',
			   dataType:"json",
			   data:JSON.stringify(rConfig),
			   contentType:'application/json',
			   type:'post',
			   success: function(data){
				   console.log(JSON.stringify(data)+'成功之后的返回');
				   if(data.resultCode == '200'){
					   layer.msg(data.reason);
					   setTimeout(function(){
						   location.reload(); 
					   },2500)
				   }else if(data.resultCode == '400'){
					   layer.msg(data.reason);
				   }
			   },
			   error:function(XMLHttpRequest, textStatus, errorThrown){
					if(XMLHttpRequest.readyState == 0) {
					//here request not initialization, do nothing
					} else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
						layer.alert("服务器忙，请重试!");
					} else {
						layer.alert("系统异常，请联系系统管理员!");
					}
			   }						
		});
    	
    }
   
    //渲染报表数据
    function findReport(repoertId){
    	if(!isNaN(Number(repoertId))){
    		var reportKey= {reportKey:repoertId};
    		console.log(JSON.stringify(reportKey)+'查询报表数据');
    		console.log(typeof(reportKey.reportKey));
    		$.ajax({
				   url:base_url+'/report/query/config',
				   dataType:"json",
				   data:JSON.stringify(reportKey),
				   contentType:'application/json',
				   type:'post',
				   success: function(data){
					   console.log(JSON.stringify(data)+'初始化返回data');
					   
					   var reportConfig = data.reportConfig;
						   //渲染页面数据
						   if(reportConfig!=null) {
							   if(data.reportConfig.lockFlag=="1"){
								   	$('#disableBtn').css({'background':'#ccc'});
								   	$('#disableBtn').addClass('layui-btn-disabled');
								   	$('#disableBtn').text('解锁');
						        	$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").addClass('layui-btn-disabled');
						    		$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").prop('readonly',true)
						    		$("#disableBtn").attr('data',"1");
							   }else if(data.reportConfig.lockFlag=="0"||data.reportConfig.lockFlag==null){
								   	$('#disableBtn').css({'background':'#009688'});
								   	$('#disableBtn').removeClass('layui-btn-disabled');
								   	$('#disableBtn').text('锁定');
						        	$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").removeClass('layui-btn-disabled');
						    		$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").prop('readonly',false);
						    		$("#disableBtn").attr('data',"0");
							   }
							   
							   //配置
							   $("#reportId").val(reportConfig.reportId);
							   $("#reportName").val(reportConfig.reportName);
							   $("#reportHeaderNames").val(reportConfig.reportHeaderNames);
							   $("#reportHeaders").val(reportConfig.reportHeaders);
							  
							   $("#orderByKey").val(reportConfig.orderByKey);
							   $("#reportSumKey").val(reportConfig.reportSumKey);
							   $("#groupByKey").val(reportConfig.groupByKey);
							   $("#dataBaseKey").val(reportConfig.dataBaseKey);
							   $("#reportSelectSql").val(reportConfig.reportSelectSql);
							   $("#reportQueryKeys").val(reportConfig.reportQueryKeys);
							   $("#reportSumSql").val(reportConfig.reportSumSql);
							   $("#replaceSql").val(reportConfig.replaceSql);
							   /* 子报表 */
							     $('#childConfigId').val(reportConfig.reportId);//childConfigId
							  	 $('#linkUrl').val();//
							  	 $('#params').val();
							  	 $('#childName').val();
							  	 $('#subReportId').val();
							  	 $('#subParams').val();
							   
							   var htmls = '';
								for(var i=0;i<data.reportQuery.length;i++){
									 htmls +=`<div class="reportQueryChild clearfix">
											<div class="input-div one01">
											<label class='layui-form-label'>id:</label>
											<div class="layui-input-block">
												<input class='layui-input layui-btn-disabled' readonly="readonly" type="text" name="one01" id="" value=""/>
											</div>
										</div>
										<div class="input-div">
											<label class='layui-form-label'>查询条件名称:</label>
											<div class="layui-input-block">
												<input class='layui-input layui-btn-disabled' readonly="readonly" type="text" name="one02" id="" value=""/>
											</div>
										</div>
										<div class="input-div">
											<label class='layui-form-label'>提交时参数名:</label>
											<div class="layui-input-block">
												<input class='layui-input layui-btn-disabled' readonly="readonly" type="text" name="one03" id="" value=""/>
											</div>
										</div>
										<div class="input-div">
											<label class='layui-form-label'>查询条件默认值:</label>
											<div class="layui-input-block">
												<input class='layui-input layui-btn-disabled' readonly="readonly" type="text" name="one04" id="" value=""/>
											</div>
										</div>
										<div class="input-div">
											<label class='layui-form-label'>查询条件类型:</label>
											<div class="layui-input-block">
												<select class='layui-input layui-btn-disabled' disabled="disabled" name="one05">
													<option value="" >请选择</option>
													<option value="1" >select</option>
													<option value="2" >input</option>
													<option value="3" >radio</option>
													<option value="4" >checkBox</option>
													<option value="5" >data</option>
												</select>
											</div>
										</div>
										<div class="input-div">
											<label class='layui-form-label'>查询条件数值来源:</label>
											<div class="layui-input-block">
												<input class='layui-input layui-btn-disabled' readonly="readonly" type="text" name="one05" id="" value=""/>
											</div>
										</div>
										<div class="input-div">
											<label class='layui-form-label'>查询条件数值:</label>
											<div class="layui-input-block">
												<input class='layui-input layui-btn-disabled' readonly="readonly" type="text" name="one06" id="" value=""/>
											</div>
										</div>
										<div class="input-div delReport">
											<i class="layui-icon layui-icon-delete">&#xe640;</i> 
										</div>
									</div>
								</div>`;
								}
								
							   $('#reportQueryW').html(htmls);
							   var reportQueryDataW=data.reportQuery;
							   //渲染查询出数据
							   $.each(reportQueryDataW, function(i){
								   $('.reportQueryChild').eq(i).find('input').eq(0).val(this.queryId);
								   $('.reportQueryChild').eq(i).find('input').eq(1).val(this.queryName);
								   $('.reportQueryChild').eq(i).find('input').eq(2).val(this.queryKey);
								   $('.reportQueryChild').eq(i).find('input').eq(3).val(this.queryDefaultValue);
								   $('.reportQueryChild').eq(i).find('select option').eq(this.queryType).attr('selected','selected');
								   $('.reportQueryChild').eq(i).find('input').eq(4).val(this.querySrcType);
								   $('.reportQueryChild').eq(i).find('input').eq(5).val(this.querySrc);
							   }); 
						   }
						   if(reportConfig === undefined){
							   
							   $('#saveReport input').val("");
							   $('#saveReport textarea').val("");
							   $('#saveReport select option').eq(0).attr('selected','selected');
							   $('#reportQueryW').html('');
							   addClickW();

							   	$('#disableBtn').css({'background':'#009688'});
							   	$('#disableBtn').removeClass('layui-btn-disabled');
					        	$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").removeClass('layui-btn-disabled');
					    		$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").prop('readonly',false);
					    		$('#disableBtn').text('锁定');
								$("#disableBtn").attr('data',"0");
								
						   }
		
				   },
				   error:function(XMLHttpRequest, textStatus, errorThrown){
						if(XMLHttpRequest.readyState == 0) {
						//here request not initialization, do nothing
						} else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
							layer.alert("服务器忙，请重试!");
						} else {
							layer.alert("系统异常，请联系系统管理员!");
						}
				   }
			});
    	}
    }
    
    
   
    
    
    
   	//点击新增
	 $('#addBtn').click(function(){
  		$('#disableBtn').attr('data','0');
  		$('#disableBtn').css({'background':'#009688'});
  		$('#disableBtn').removeClass('layui-btn-disabled');
  		$('#disableBtn').text('锁定');
      	$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").removeClass('layui-btn-disabled');
  		$('#saveReport input,#saveReport textarea').not("#reportId,#reportQueryKeys").prop('readonly',false);
	 	$('#reportQueryW').html('');
	   	addClickW();
   		//var str = $("#reportSelect option").map(function(){return $(this).val();}).get().join(",").split(',');
		console.log('==============');
   		//console.log(str); 
/*    		$.each(str,function(i,k){
			 if(k==""){ */
			//	 console.log(k);
				 layui.use('form', function(){
					 	var form = layui.form;
					  	$('#reportSelect option').eq(0).prop('selected','selected');
				    	$('#saveReport input,#saveReport textarea').val(""); 
					  	form.render("select");
				});
				 
				
/* 			 }
	 	}) */
	})
	//查询条件模板
	function addClickW(){
		    	var str="";
		    	str +=`<div class="reportQueryChild reportQueryChildBox clearfix">
					<div class="input-div one01">
					<label class='layui-form-label'>id:</label>
					<div class="layui-input-block">
						<input class='layui-input' type="text"  name="one01" id="" value=""/>
						
						</div>
				</div>
				<div class="input-div">
					<label class='layui-form-label'>查询条件名称:</label>
					<div class="layui-input-block">
						<input class='layui-input' type="text" name="one02" id="" value=""/>
					</div>
				</div>
				<div class="input-div">
					<label class='layui-form-label'>提交时参数名:</label>
					<div class="layui-input-block">
						<input class='layui-input' type="text" name="one03" id="" value=""/>
					</div>
				</div>
				<div class="input-div">
					<label class='layui-form-label'>查询条件默认值:</label>
					<div class="layui-input-block">
						<input class='layui-input' type="text" name="one04" id="" value=""/>
					</div>
				</div>
				<div class="input-div">
					<label class='layui-form-label'>查询条件类型:</label>
					<div class="layui-input-block">
						<select class='layui-input' name="one05">
							<option value="" >请选择</option>
							<option value="1" >select</option>
							<option value="2" >input</option>
							<option value="3" >radio</option>
							<option value="4" >checkBox</option>
							<option value="5" >data</option>
						</select>
					</div>
				</div>
				<div class="input-div">
					<label class='layui-form-label'>查询条件数值来源:</label>
					<div class="layui-input-block">
						<input class='layui-input' type="text" name="one05" id="" value=""/>
					</div>
				</div>
				<div class="input-div">
					<label class='layui-form-label'>查询条件数值:</label>
					<div class="layui-input-block">
						<input class='layui-input' type="text" name="one06" id="" value=""/>
					</div>
				</div>
				<div class="input-div delReport" style="visibility: hidden!important;">
					<i class="layui-icon layui-icon-delete">&#xe640;</i> 
				</div>
			</div>
		</div>`;
    	$('#reportQueryW').append(str);
    }
    $('#layuiAdd').click(function(){
    	addClickW();
    });
    /* 删除报表 */
    $(document).on('click','.delReport .layui-icon-delete',function(){
    	var delId=$(this).parents('.reportQueryChild').find('.input-div:first input').val();
    	var delReportW={};
    	delReportW.queryId=delId;
    	$.ajax({
			   url:base_url+'/report/deleteReportQuery',
			   dataType:"json",
			   data:JSON.stringify(delReportW),
			   contentType:'application/json',
			   type:'post',
			   success: function(data){
				   if(data.resultCode == '200'){
					   layer.msg(data.msg);
					   setTimeout(function(){
						   location.reload(); 
					   },2500)
				   }else if(data.resultCode == '400'){
					   layer.msg(data.msg);
				   }
				  
			   },
			   error:function(XMLHttpRequest, textStatus, errorThrown){
					if(XMLHttpRequest.readyState == 0) {
					} else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
						layer.alert("服务器忙，请重试!");
					} else {
						layer.alert("系统异常，请联系系统管理员!");
					}
			   }    	
    	})
	
    })
   
    </script>
</body>
</html>