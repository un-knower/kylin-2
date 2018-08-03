<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <title>返单接收确认录入</title>
   <%@ include file="/views/transport/include/head.jsp"%>
    <style>
      .breadcrumb {margin: 15px; display: block;visibility: visible !important;}
      .layui-table{width:98%;margin:10px auto;}
    .layui-form-label{padding: 9px 0px;width:110px;}
      .freight-table{margin:0 auto;}
      .freight-table input{width:100%;height:100%;text-align:center;border:0;}
      .freight-table td, .freight-table th{border-color:#777;}
      .freight-table td{background-color:#fff;}
      .layui-table td, .layui-table th{padding:9px 0;text-align:center;}
      .layui-form-save{margin-top:30px;text-align:center;}
      .saveBtn{height: 38px;line-height: 38px;margin: 0;display: inline-block;}
      .saveBtn, .layui-btn-normal{position: relative;top: -2px;}
      .freight-table .not-fill{background-color:#ccc;}
      /* form select{display:block !important;position:relative;left:50%;margin-left:-36px;}  */
      .date-wrap{width:155px !important;}
      .select-wrap{width:110px !important;}
      .layui-form-select{width:100px;left:50%;margin-left:-50px;}
      .requireFill::before{content:"*";color:red;}
      input::-webkit-input-placeholder { font-size:14px; }
	  input::-moz-placeholder { font-size:14px;} /* firefox 19+ */
	  input:-ms-input-placeholder {font-size:14px;} /* Internet Explorer 10+ */
      input:-moz-placeholder { font-size:14px;} /* firefox 14-18 */
      html input[disabled]{background-color:#fff;}
      html button[disabled], html input[disabled], html select[disabled]{cursor:not-allowed;}
    </style>
</head>
<body style="overflow-y: scroll;">
   <span class='layui-breadcrumb breadcrumb'>
      <a href='javaScript:void(0);'>当前位置  &gt;</a>
      <a href='javaScript:void(0);'>返单接收确认录入</a>
   </span>
   <div id="rrapp" v-cloak>
         <form class='layui-form '> 
            <div class='layui-form-item'>
               <div class='layui-inline'>
               <div class='input-div'>
                  <label class='layui-form-label requireFill'>运单编号：</label>
                  <div class='layui-input-block'>
                      <input type='text' class='layui-input' name='waybillNum' v-model="waybillNum" placeholder="请输入10或12位运单编号" autocomplete="off"> 
                  </div>
               </div>
            </div>
               <div class="layui-inline">
                <button class="layui-btn inquire" @click.prevent="searchAccept" lay-submit  lay-filter='inquire' id='inquire' style="top:-3px" >查询</button>
             </div> 
            </div>
            
         <table class='layui-table freight-table'>
            <!-- <colgroup>
               <col width='15%'>
               <col width='6%'>
               <col width='6%'>
               <col width='6%'>
               <col width='6%'>
               <col width='6%'>
            </colgroup> -->
            <thead>
               <tr>
                  <td>运单编号</td>
		          <td>发货人</td>
		          <td>收货人</td>
		          <td class="date-wrap">返单收到时间</td>
		          <td class="select-wrap">状态</td>
		          <td>确认人</td>
		          <td>确认时间</td>
		          <td>备注</td>
               </tr>
            </thead>
            <tbody id="showLogList">
               <tr v-show="isShow">
                  <td class="not-fill">{{dataList.ydbhid}}</td>
                  <td class="not-fill">{{dataList.fhdwmch}}</td>
                  <td class="not-fill">{{dataList.shhrmch}}</td>
                  <td><input type="text" id="accept-date" name="fdqstime" v-model="accept_date" class="exceedsModify" disabled></td>
                  <td>
                      <select  name="interest" id="selectWay">
                           <option value="1">合格(1)</option>
                           <option value="2">不合格(2)</option>
                           <option value="0">未返单(0)</option>
                      </select>
                      <input type="text" id="inputWay" disabled>
                  </td>
                  <td class="not-fill">{{dataList.qrr}}</td>
                  <td class="not-fill" id="qrtime">{{dataList.qrtime}}</td>
                  <td><input type="text" name="fdjscomm" v-model="accept_remark" class="exceedsModify" disabled></td>
               </tr>
            </tbody>
         </table>
         <div class="layui-form-item layui-form-save" style="text-align:center">
            <button class="layui-btn saveBtn exceedsModify" disabled  lay-submit  lay-filter='saveBtn' @click.prevent="saveAccept" id='saveBtn'>保存</button>
         </div>
      </form> 
      
      
      
   </div>
   <%@ include file="/views/transport/include/floor.jsp"%> 
   <script type="text/javascript">
         var base_url = '${base_url}';
         var ctx_static = '${ctx_static}';
         layui.use('layer', function(){}); 
         var vm = new Vue({
            el:"#rrapp",
            data:{
               isShow:false,
               waybillNum:"",   //运单编号
               id:1,
               accept_date:"",    //返单收到时间
               reorder_state:"",  //返单状态
               accept_remark:"", //返单接收备注
               dataList:{}
            },
            methods:{
              searchAccept:function(){
            	  var reg = /\b[a-z0-9A-Z]{10}\b|\b[a-z0-9A-Z]{12}\b/;
	           	   /* console.log(reg.test(H000021906)); */
	           	  if(this.waybillNum == ""){
	           		  layer.msg('运单编号不能为空！');
	           	  }else if(!reg.test(this.waybillNum)){
	           		  layer.msg('运单编号必须为10或12位数字或字母组合！');
	           	  }else{
	           		  var _this = this;
	                  var dataObj = {};
	                  dataObj.waybillNum = this.waybillNum;
                      $.ajax({
                        url: base_url + '/transport/reorder/selectReceiveReorder',
                        type: 'post',
                        dataType: 'JSON',
                        contentType: 'application/json',
                        data: JSON.stringify(dataObj),
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
                        		layer.open({
                            		title:"提示信息",
                            		content:data.reason
                            	});
                        	}else{
                        		 _this.isShow = true;
                        		 //如果确认时间为null
                        		 if(!data.data.qrtime){
                                	 var date = new Date();
                                	 var curDate = getMyDate(date);
                                	 data.data.qrtime = curDate;
                                 }
                                 _this.dataList = data.data;
                                 //console.log(data.data)
                                 //获取状态
                                 var fdzt = data.data.fdzt;
                                 //返单收到时间
                                 var fdqstime = data.data.fdqstime;
                                 //时间插件
                                 layui.use(['laydate','form'], function(){
                                    var form = layui.form;
                                    form.render();
                                    var laydate = layui.laydate;
      	                            if(data.data.fdJinZhiLuRu != "1"){
      	                            	 $(".exceedsModify").removeAttr("disabled");
                                    	 $("#inputWay").hide();
                                    	 if(fdqstime){
                                          	_this.accept_date = fdqstime.split(".")[0];
                                     	 }else{
                                     		//当前系统时间
                  						     var date = new Date();
                  						   	 _this.accept_date=getMyDate(date);
                                     	 }
      	                                //执行一个laydate实例
                                        laydate.render({
                                           elem: '#accept-date',      //指定元素
                                           format:"yyyy-MM-dd HH:mm:ss", //定义日期格式
                                           type: 'datetime',
                                           max: _this.accept_date,           //设置最大时间 
                                           done: function(value, date, endDate){
                                              _this.accept_date = value;
                                           }
                                        }); 
      	                            	_this.accept_remark = data.data.fdjscomm;
      	                            	if(fdzt != "1"){
	           	                        	$("dd").each(function(){
	           	                        		if($(this).attr("lay-value") == fdzt){
		                                  			var selCont = $(this).html();
		                                  			$("input.layui-unselect").val(selCont);
		                                  			$("dd").removeClass("layui-this");
		                                  			$(this).addClass("layui-this");
		                                  		}
	       	                              	});
        	                        	}
      	                             }
                                 });
                        	}
                          
                        },
                        error: function(data){
                           console.log(data);
                        }
                     });
	           	  }
                  
               },
               saveAccept:function(){
                    var _this = this;
                    //console.log(_this.accept_remark.length);
                    if(!this.accept_remark||this.accept_remark==""){
	                	var rL = 0;
	                }else{
	                	var rL = this.accept_remark.length;
	                }
                    if(rL > 200){
                    	layer.open({
                            title: '提示信息',
                            content: '备注最多录入200个字符！！'
                         }); 
                    	 this.accept_remark = "";
            	 	}else{
            	 		var dataArr = {};
                        dataArr.waybillNum = this.waybillNum;
                        dataArr.fdqstime = this.accept_date;
                        dataArr.fdzt = Number($(".layui-this").attr("lay-value"));
                        dataArr.fdjscomm = this.accept_remark;
                        //console.log(JSON.stringify(dataArr))
                        $.ajax({
                           url:base_url + '/transport/reorder/saveReceiveReorder',
                           type: 'post',
                           dataType: 'JSON',
                           contentType: 'application/json',
                           data: JSON.stringify(dataArr),
                           success:function(data){
                        	   if(data.resultCode != "200"){
	                           		layer.open({
	                               		title:"提示信息",
	                               		content:data.reason
	                               	});
                           		}else{
                           			layer.open({
                                       title: '提示信息',
                                       content: '保存成功！'
                                     }); 
                           			if(dataArr.fdzt == "1"){
                           				$("#saveBtn").attr("disabled","disabled");
                           			}
                           			
                           		}
                              
                           },
                           error:function(data){
                              console.log(data)
                           }
                        }); 
            	 	}  
                    
               }
            },
         });
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