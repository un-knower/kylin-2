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
    
</head>
<body>
<style type="text/css">
   [v-cloak] {display: none;}
   .layui-form-label{width: 110px;}
   .layui-input-block select, .layui-input-block option{appearance:none;-moz-appearance:none;-webkit-appearance:none;}
   .layui-input-block select{width:80px;}
   #currentPlace, #sumReport{display:inline-block;}
   #sumReport{padding-right: 8px;line-height: 22px;font-size: 16px;color: #333!important;}
   .layui-table tr th{background:rgb(93,211,241);}
   .layui-table tr th, .layui-table tr{text-align:center;}
   .layui-table td, .layui-table th{padding:9px 5px;}
   .layui-table tr:hover{background:#f5f5f5;}
   .layui-table td{word-break:break-all;}
   
.margin_bottom{margin-bottom: 10px;}
	.tabBox.layui-table th{padding:6px 5px;}
/* 	.tabBox.layui-table th:nth-child(13){width:inherit!important;min-width:200px;}
	.tabBox.layui-table th:nth-child(32){width:inherit!important;min-width:200px;}
	.tabBox.layui-table th:nth-child(33){width:inherit!important;min-width:200px;}
	.tabBox.layui-table th:nth-child(38){width:inherit!important;min-width:200px;} */
	
	.totalBgcolor{background:#ccf5e6!important;}
	.totalBgcolor td{font-weight:bold;font-size:16px;}
	.s_addTr:first-child td .check-one{visibility: hidden;}
	.viewDetails{display:none;}
	.check-one{cursor:pointer;display:inline-block;width:18px;height:18px;line-height:18px;border:1px solid #bbb;border-radius:2px;font-weight:900;font-family:黑体;}
</style>
<div class="parmsUrl"></div>

<div id="rrapp" v-cloak>
	<div>
		<span class='layui-breadcrumb breadcrumb no-print' id="currentPlace">
			<a href='javaScript:void(0);'>当前位置:</a>
	 		<a href='javaScript:void(0);' id='reportName'></a>
		</span>
		<input type="hidden" name="num" >
		<button class="layui-btn toCheck" lay-submit lay-filter='searchBtn' @click="search(num)" id='searchBtn' style="top:-3px">查询</button>
		<button class="layui-btn toCheck" lay-submit lay-filter='searchBtn' @click="print()" id='printBtn' style="top:-3px">导出</button>
		
		     
		<input type="hidden" value="${reportKey}" id="reportKey"/>
	</div>  
	<!-- search -->
	<!--  <form class='layui-form '> 
		<div class='layui-form-item　margin_bottom'>
			<div class='layui-inline' v-for="item in searchList">
				<div class='input-div'>
					<label class='layui-form-label'>{{item.queryName}}</label>
					<div class='layui-input-block'>
					    <select v-if="item.queryType==1" :name="item.queryName" :data="item.queryKey" class='layui-input queryInp seletDom'>
                              <option value="">全部</option> 
                              <!-- <option v-for="obj in public_options" v-bind:value="obj.keyValue">{{obj.keyName}}</option>   --> 	
                         <!--     			  	
                        </select>
						<input v-if="item.queryType==2"  lay-verify="required" :name="item.queryName" type='text' :data="item.queryKey"  class='layui-input queryInp inputName'>
						<input v-if="item.queryType==5"  class="layui-input queryInp demoDate" name="startDate" :datename="item.queryName" :data="item.queryKey" lay-key="0" id="startDate" lay-verify="required" readonly type="text">
					</div>
				</div>
			</div>
		    <c:if test="${company=='总公司'}">
		    	<div class="layui-inline">
		    		<label class='layui-form-label'>公司:</label>
		    		<div class='layui-input-block'>
						<select class='layui-input' id="fazhan-company">						
							<option value="总公司">所有公司</option>
						</select>
					</div>
				</div>
		    </c:if>
		</div>
	<div>
		<button class="layui-btn viewDetails" lay-submit lay-filter='viewDetails' @click="viewDetails()" id='viewDetails' style="margin:15px;">{{lookName}}</button>
	</div>-->
	<!-- </form> -->
	<div>	   	
		<!-- table -->
		 <table class='layui-table tabBox childTab' style="margin:0 auto 20px auto;overflow-x: scroll;">
			<thead>
				<tr class="checkBoxTd">
					<th v-for="item in tabList">{{item}}</th>
				</tr>
			</thead>
			<tbody>
			   <tr class="s_addTr" v-for="(v,k) in cases">
			    	<td v-for="(vv,k) in v">{{vv}}</td>
			   </tr>	   
			</tbody>
		</table>  
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
    <%@ include file="/views/transport/include/floor.jsp"%>
    <script type="text/javascript">
	    var base_url = '${base_url}';
	    var ctx_static = '${ctx_static}';
	    var currTime="";
    </script>
    <script type="text/javascript" src="${ctx_static}/publicFile/js/public.js"></script>
    
   <script>
   var subReportError = '${SUB_REPORT_ERROR}';
   var params = '${params}';
   var paramsArray = new Array();
   if(params){
	   paramsArray = params.split("&");
   }
   
    var htmlStr="";
	layui.use(['layer','form'], function(){
		layer = layui.layer;
		var form = layui.form;
        form.render();
	});
	if(subReportError){
		layer.msg(subReportError);
    }
	

   var vm = new Vue({
		el:'#rrapp',
		data:{
			flag: true,
			//changeNum:0,
			searchList:{},
			tabList: [],
			public_options:[],
			cases:[],
			num:1,
			pageSize:20,  //每页显示条数
			pageNum:1,
			lookName:"",//子菜单
			clickChecked:"",
			attrList:[],
			inputType:"",
			checkedList: [],
			caseSave:[]
		},
		computed: {
	    
		},
		watch:{
			  searchList:function(){
					this.$nextTick(function(){
						var _this = this;
						//渲染select框
						$(".seletDom").each(function(){
							var selName= $(this).attr("name");
							var selKey= $(this).attr("data");
							//console.log(selName+'11');
							//console.log(selKey+'22');
							_this.publicOption($(this),selName,selKey);
						});
						_this.companySelect($("#fazhan-company"));
						//设置所有日期datenum为1
						$(".demoDate").attr("datenum","1");
						//设置所有不符合规则datenum为0
						var reportName = $("#reportName").html();
						if(reportName=="查询客户基本资料"||reportName=="查询客户价格信息"){
							$(".demoDate").attr("datenum","0");
						}
						if(reportName=="运输受理单"){
							var dateNameLength = $(".demoDate[datename='生成报表时间']").length;
							if(dateNameLength>=1){
								$(".demoDate[datename='生成报表时间']").attr("datenum","0");
							}
						}
						//设置运单编号、单据编号和运输号码的num值
						var numKey = 1;
						$(".inputName").each(function(){
                            var inputName = $(this).attr("name");
                            if(inputName=="运单编号" || inputName=="单据编号" ||inputName=="运输号码"){
                            	numKey = numKey + '1';
                            	$(this).attr("num",numKey);
                            	//_this.changeNum += 1;
                            }
                        });
						//渲染日期
						getDateFn();
				    });
					
			  },
			  tabList:function(){
				  this.$nextTick(function(){
					  var _this = this;
					  var thLength = $(".childTab.layui-table th").length;
					  //console.log(555);
					  //console.log(thLength); 
					  _this.countTableWidth(thLength);
				  })
			  },
			  tabTotal:function(){
				  this.$nextTick(function(){
					  var that = this;
						
				  })
			  },
			  cases:function(){
				  this.$nextTick(function(){
					  var _this = this;
					  var domEle = $("tbody tr").eq(0).find("td");
					  _this.changeThWidth(domEle);
				  });
			  }
		},
		created:function(){
			//this.getPage();
		},
		methods: {
			//计算表格宽度
			countTableWidth:function(length){
				/* console.log("当前窗口可视区域宽度",$(window).width());
				console.log("当前窗口文档对象宽度",$(document).width()); */
				//console.log("当前窗口文档body的宽度",$(document.body).width());
				var docWidth = $(document.body).width();
				if(docWidth>=1300){
					/* if(length<=15){
						  $(".childTab.layui-table").css("width","100%");
					  }else if(length>15 && length<=20){
						  $(".childTab.layui-table").css("width","120%");
					  }else if(length>20 && length<=25){
						  $(".childTab.layui-table").css("width","150%");
					  }else if(length>25 && length<=30){
						  $(".childTab.layui-table").css("width","180%");
					  }else if(length>30 && length<=36){
						  $(".childTab.layui-table").css("width","210%");
					  }else if(length>36 && length<=42){
						  $(".childTab.layui-table").css("width","240%");
					  }else if(length>42 && length<=46){
						  $(".childTab.layui-table").css("width","280%");
					  }else if(length>46){
						  $(".childTab.layui-table").css("width","335%");
					  } */
				}else{
					/* if(length<=10){
						  $(".childTab.layui-table").css("width","80%");
					  }else if(length>10 && length<=15){
						  $(".childTab.layui-table").css("width","110%");
					  }else if(length>15 && length<=18){
						  $(".childTab.layui-table").css("width","130%");
					  }else if(length>18 && length<=23){
						  $(".childTab.layui-table").css("width","160%");
					  }else if(length>23 && length<=26){
						  $(".childTab.layui-table").css("width","180%");
					  }else if(length>26 && length<=30){
						  $(".childTab.layui-table").css("width","215%");
					  }else if(length>30 && length<=33){
						  $(".childTab.layui-table").css("width","240%");
					  }else if(length>33 && length<=36){
						  $(".childTab.layui-table").css("width","280%");
					  }else if(length>36 && length<=46){
						  $(".childTab.layui-table").css("width","335%");
					  }else if(length>42 && length<=46){
						  $(".childTab.layui-table").css("width","350%");
					  }else if(length>46){
						  $(".childTab.layui-table").css("width","400%");
					  } */
				}
				  
			},
			//改变th宽度
			changeThWidth:function(domEle){
				$(domEle).each(function(){
					  var str = $(this).html();
					  //获取td内容长度
					  var len =  getByteLen(str);
					  var $index =  $(this).index();
					  var docWidth = $(document.body).width();
					 if(docWidth>=1500){
						 if(len <= 6){					
							  $("thead th").eq($index).css("width","110px");
						  }else if(len>6 && len<=12){
							  $("thead th").eq($index).css("width","125px");
						  }else if(len>12 && len<=18){
							  $("thead th").eq($index).css("width","180px");
						  }else if(len>18 && len<=24){
							  $("thead th").eq($index).css("width","220px");
						  }else if(len>24 && len<=30){
							  $("thead th").eq($index).css("width","250px");
						  }else if(len>30){
							  $("thead th").eq($index).css("width","320px");
						  }
					 }else{
						 if(len <= 6){
							  $("thead th").eq($index).css("width","70px");
						  }else if(len>6 && len<=12){
							  $("thead th").eq($index).css("width","105px");
						  }else if(len>12 && len<=18){
							  $("thead th").eq($index).css("width","150px");
						  }else if(len>18 && len<=24){
							  $("thead th").eq($index).css("width","170px");
						  }else if(len>24 && len<=30){
							  $("thead th").eq($index).css("width","200px");
						  }else if(len>30){
							  $("thead th").eq($index).css("width","320px");
						  }
					 }
				});
			},
			//下拉框数据
			publicOption: function(sleDom,queryName,queryKey) {
				var ss= {keyName:$.trim(queryName)};
				$.ajax({
					   url:base_url+'/transport/generalInfo/findBasicInfoByName',
					   dataType:"json",
					   contentType:'application/json',
					   data:JSON.stringify(ss),
					   type:'post',
					   success: function(data){
						   var childLength = $(sleDom).children('option').length;
						   if(childLength == 1){
							   htmlStr = "";
							   for(var i=0;i<data.data.length;i++){
								   htmlStr += "<option value='"+data.data[i].keyValue+"'>"+data.data[i].keyName+"</option>";
							   }
							   $(sleDom).append(htmlStr);
						   }
					  },
					  error:function(XMLHttpRequest, textStatus, errorThrown){
							if(XMLHttpRequest.readyState == 0) {
							//here request not initialization, do nothing
							} else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
								alert("服务器忙，请重试!");
							} else {
								alert("系统异常，请联系系统管理员!");
							}
					 }						
			    }); 
			},
			//公司下拉框数据
			companySelect : function (thisDom){
				$.ajax({
					   url:base_url+'/transport/generalInfo/getCompanyListAndCurrCompanyInfo',
					   dataType:"json",
					   contentType:'application/json',
					   type:'post',
					   success: function(data){
							$.each(data.COMPANY_LIST, function(i,k){
								$(thisDom).append('<option value='+k.companyName+' companyCode='+k.companyCode+'>'+k.companyName+'</option>');
							});
						   
						  },
					   error:function(XMLHttpRequest, textStatus, errorThrown){
							if(XMLHttpRequest.readyState == 0) {
							//here request not initialization, do nothing
							} else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
								alert("服务器忙，请重试!");
							} else {
								alert("系统异常，请联系系统管理员!");
							}
						}						
				}); 
			},	
			getPage: function(){
				var reportKey = $("#reportKey").val();
				var parameter = {reportKey:reportKey}
				$.ajax({
					   url:base_url+'/report/lazy/query/config',
					   dataType:"json",
					   contentType:'application/json',
					   data:JSON.stringify(parameter),
					   type:'post',
					   success: function(data){
						   console.log(JSON.stringify(data)+'rr');
						   //var aa=[];
						 if(data.success){
							 vm.searchList=data.reportQuery; //查询
							 vm.tabList = data.reportConfig.reportHeaderNames.split(","); //table表头渲染数据
							 console.log(vm.tabList);
							 var sss =data.reportConfig.reportHeaders.split(",");
							 $("#reportKey").val(data.reportConfig.reportId);
							 $("#reportName").text(data.reportConfig.reportName);				 
						 }   
						 $("#searchBtn").click();
					   },
					   error:function(XMLHttpRequest, textStatus, errorThrown){
								if(XMLHttpRequest.readyState == 0) {
								} else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
									alert("服务器忙，请重试!");
								} else {
									alert("系统异常，请联系系统管理员!");
								}
						}						
					});
			},
			//查询结果
			search:function(num){
				var value="";	
				var company = $("#fazhan-company").val();
				if (null=="undefined"){
					company=null;
				}
				var ss= {reportKey:$("#reportKey").val(),company:company}; 				
				this.pageNum = num || this.num;
				// $(".queryInp").each(function(i,item){					
				//      value= $(".queryInp").eq(i).val();
				//      ss[$(".queryInp").eq(i).attr("data")]=value;					 
				//});
				for(var i=0;i<paramsArray.length;i++){
					var keyValue = paramsArray[i].split("=");
					if(keyValue[0]!="reportKey" && keyValue[0]!="parentKey" && keyValue[0]!="withPara"){
						ss[keyValue[0]]=keyValue[1];		
					}
				}
				
				console.log(JSON.stringify(ss)+'查询所传的参数');
				ss.pageNum = this.pageNum;
				ss.pageSize = this.pageSize;	
				layui.use(['layer','form'], function(){
					layer = layui.layer;
 					$.ajax({
					   url:base_url+'/report/lazy/query/data',
					   dataType:"json",
					   contentType:'application/json',
					   data:JSON.stringify(ss),
					   type:'post',
					   beforeSend: function(){
						  
								loading = layer.load(0,{
									shade: [0.5,'#fff']
								})
						},
					   success: function(data){
		   					/* 是否有中间页码 */
						    if($('.page-num')){
								$('.page-num').remove();
							}
		   					/* 关闭加载层 */
						    layer.close(loading);
		   					/* 数据量，每页条数，总页数 */
						    $('.page-total').html(data.page.total);
							$('.page-size').html(data.page.size);
							$('.page-pages').html(data.page.pages);
							if(data.page.pages > 1){
								if(!data.page.isFirstPage){
									$('.isFirstPage').show();
									$('.page-prePage').attr('num',data.page.prePage);
								}else{
									$('.isFirstPage').hide();
								}
								$.each(data.page.navigatepageNums, function(i,e){
									if(e == data.page.num){
										$('.insert-li').before('<li class="active page-num"><a href="javascript:;" num='+e+'>'+e+'</a></li>');
									}else{
										$('.insert-li').before('<li class="page-num"><a href="javascript:;" num='+e+'>'+e+'</a></li>');
									}
									
								})
								if(!data.page.isLastPage){
									$('.isLastPage').show();
									$('.nextPage').attr('num',data.page.nextPage);
									$('.lastPage').attr('num',data.page.pages);
								}else{
									$('.isLastPage').hide();
								}
							}else{
								$('.isLastPage').hide();
							}
							//this.num=data.page.num;
							eachPage();
							/* 页面数据渲染 */
							if(data.success){
								/* 查询数据列表 */						
								var caseList=data.page.collection;
								console.log(JSON.stringify(caseList)+'bb');
								//sessionStorage.setItem("caseListData",JSON.stringify(caseList));//存储查询到的数据
								var s=data.headers;
								var ssss=[];
								for(let i=0;i<caseList.length;i++){
									//var date = new Date(caseList[i].fhshj);
									//caseList[i].fhshj=date.getFullYear() + '-' +addZero(date.getMonth()+1) + '-' +addZero(date.getDate());
									var arr = [];
									for(let j=0;j<s.length;j++){
										var aaa = caseList[i][s[j]];
										if(aaa!=null && aaa != ''){
											aaa =JSON.stringify(aaa).replace(/\"/g,'').replace(/\\/g,'');
										}
										arr[j]=aaa;
									}
									ssss.push(arr);
								}						
								vm.cases = ssss;
								console.log(vm.cases);
							 } 
					   },
					   error:function(XMLHttpRequest, textStatus, errorThrown){
								if(XMLHttpRequest.readyState == 0) {
								//here request not initialization, do nothing
								} else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
									alert("服务器忙，请重试!");
								} else {
									alert("系统异常，请联系系统管理员!");
								}
						}						
					});
				});
			},
			print:function(){
				var maxLength = parseInt($(".page-total").html());
				if(maxLength > 5000){
					layer.msg("一次最多导出5000条！");
					return
				}
				// 导出
				var ss= {reportKey:$("#reportKey").val()}; 
				ss.pageNum = 1;
				ss.pageSize = 100000;
				//ss.pageNum = this.pageNum;
				//ss.pageSize = this.pageSize;
				$(".queryInp").each(function(i,item){					
				      value= $(".queryInp").eq(i).val();
				      ss[$(".queryInp").eq(i).attr("data")]=value;					 
				});	
				$.ajax({
					   url:base_url+'/report/lazy/export/data',
					   //dataType:"json",
					   contentType:'application/json',
					   data:JSON.stringify(ss),
					   type:'post',
					   success: function(data){
						   window.location.href=base_url+'/static/upload/report.xls';							
					   },
					   error:function(XMLHttpRequest, textStatus, errorThrown){
								if(XMLHttpRequest.readyState == 0) {
								//here request not initialization, do nothing
								} else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
									alert("服务器忙，请重试!");
								} else {
									alert("系统异常，请联系系统管理员!");
								}
						}						
					}); 
				layer.msg('导出数据稍有延迟 请稍后!');
			},
			viewDetails:function(){
				 this.$nextTick(function(){
					/*  zibaobiao(); */
				  });
			},
		},
		mounted: function(){
	          this.getPage(); //数据加载  
		}
	});
   
   function eachPage(){
		$(".pagination a").each(function(i){	
			$(this).unbind('click').on('click', function(){
				//console.log($(this).attr("num"));
				vm.search($(this).attr("num"));
			});   
		});
	} 
   //补0函数
   function addZero(m){return m<10?'0'+m:m }
   //获取字符串长度（汉字算两个字符，字母数字算一个）
   function getByteLen(val) {
       var len = 0;
       for (var i = 0; i < val.length; i++) {
           var a = val.charAt(i);
           if (a.match(/[^\x00-\xff]/ig) != null) {
               len += 2;
           }else {
               len += 1;
           }
       }
       return len;
   }
   //获取日期函数
   function getDateFn(){
	    //渲染日期
		if($(".demoDate[datenum='1']").val()==""){//format
			$(".demoDate[datenum='1']").val(moment().subtract(7, 'days').format('YYYY-MM-DD') +' - '+ moment().format('YYYY-MM-DD'));
			$(".demoDate[data='T_HWYD_STATISTICS.FCHRQ']").val("");
			$(".demoDate[data='fhshj']").val("");
			$(".demoDate[data='T_HWYD_STATISTICS.qstime']").val(moment().subtract(1, 'months').startOf('month').format('YYYY-MM-DD') +' - '+ moment().subtract(1, 'months').endOf('month').format('YYYY-MM-DD'));
			//$(".demoDate[datenum='1']").eq(1).val(moment().subtract(1, 'months').startOf('month').format('YYYY-MM-DD') +' - '+ moment().subtract(1, 'months').endOf('month').format('YYYY-MM-DD'));
     	}
		var maxDate = moment().format('YYYY-MM-DD');
		var num = 0;
		layui.use('laydate', function(){
			  var laydate = layui.laydate;
			  $(".demoDate").each(function(){
				  num += 1;
				  $(this).attr("lay-key",num);
				  //常规用法
				  laydate.render({
				    elem: this,
				    format:"yyyy-MM-dd",
				    max:maxDate,
				    range: true
				  });
			  });
		});
	   
   }
   var timeout = setTimeout(function(){
	   $(".inputName[num^='1']").focus(function(){
		   $(".demoDate").val("");
	   });
	   $(".inputName[num^='1']").blur(function(){
		   if($(this).val() == ""){
			   if($(".demoDate[datenum='1']").val()==""){
				   $(".demoDate[datenum='1']").val(moment().subtract(7, 'days').format('YYYY-MM-DD') +' - '+ moment().format('YYYY-MM-DD'));
					$(".demoDate[data='T_HWYD_STATISTICS.FCHRQ']").val("");
					$(".demoDate[data='fhshj']").val("");
					$(".demoDate[data='T_HWYD_STATISTICS.qstime']").val(moment().subtract(1, 'months').startOf('month').format('YYYY-MM-DD') +' - '+ moment().subtract(1, 'months').endOf('month').format('YYYY-MM-DD'));
					//$(".demoDate[datenum='1']").eq(1).val(moment().subtract(1, 'months').startOf('month').format('YYYY-MM-DD') +' - '+ moment().subtract(1, 'months').endOf('month').format('YYYY-MM-DD'));
			   }
		   }
	   });
   },500);
   </script>
</body>
</html>