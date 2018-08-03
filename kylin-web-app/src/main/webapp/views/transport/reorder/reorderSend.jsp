<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <title>返单发送确认录入</title>
   <%@ include file="/views/transport/include/head.jsp"%>
    <link rel="stylesheet" href="${ctx_static}/transport/arrive/css/freightRecords.css"/>
    <style>
      .breadcrumb{margin: 15px; display: block;visibility: visible !important;}
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
      .select-wrap{width:100px !important;}
      .layui-form-select{width:90px;left:50%;margin-left:-45px;}
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
      <a href='javaScript:void(0);'>返单发送确认录入</a>
   </span>
   <div id="rrapp" v-cloak>
         <form class='layui-form '> 
            <div class='layui-form-item'>
               <div class='layui-inline'>
               <div class='input-div'>
                  <label class='layui-form-label requireFill'>运单编号：</label>
                  <div class='layui-input-block'>
                      <input type='text' class='layui-input' required name='waybillNum' id="waybillNum" placeholder="请输入10或12位运单编号" v-model="waybillNum" autocomplete="off"> 
                  </div>
               </div>
            </div>
               <div class="layui-inline">
                <button class="layui-btn inquire" @click.prevent="searchSend" lay-submit  lay-filter='inquire' id='inquire' style="top:-3px" >查询</button>
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
                  <td>录入人</td>
                  <td>绿色通道</td>
                  <td>收货人</td>
                  <td class="date-wrap">返单寄出时间</td>
                  <td class="select-wrap">返单方式</td>
                  <td>返单邮寄号码</td>
                  <td>备注</td>
                  <td>发送人</td>
               </tr>
            </thead>
            <tbody id="showLogList">
               <tr v-show="isShow" v-for="item in dataList">
                  <td class="not-fill">{{item.ydbhid}}</td>
                  <td class="not-fill">{{item.fhdwmch}}</td>
                  <td class="not-fill">{{item.isGreenchannel}}</td>
                  <td class="not-fill">{{item.shhrmch}}</td>
                  <td><input type="text" id="send-date" disabled class="exceedsModify" name="fdjctime" v-model="send_date" required></td>
                  <td>
                     <select  name="interest" id="selectWay">
                          <option value="1">快件(1)</option>
                          <option value="2">内部(2)</option>
                          <option value="3">其他(3)</option>
                     </select>
                     <input type="text" id="inputWay" disabled>
                  </td>
                  <td><input type="text" name="fdyoujihao" disabled class="exceedsModify" v-model="send_num"></td>
                  <td><input type="text" name="fdfscomm" disabled class="exceedsModify" v-model="send_remark"></td>
                  <td class="not-fill">{{item.fdfsr}}</td>
               </tr>
            </tbody>
         </table>
         <div class="layui-form-item layui-form-save" style="text-align:center">
            <button class="layui-btn saveBtn exceedsModify"  lay-submit disabled  lay-filter='saveBtn' @click.prevent="saveSend" id='saveBtn'>保存</button>
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
               sysTime:"",      //系统时间
               waybillNum:"",   //运单编号
               id:1,
               send_date:"",    //返单寄出时间
               reorder_way:"",  //返单方式
               send_num:"",    //返单邮寄号码
               send_remark:"", //返单发送备注
               dataList:{}
            },
            methods:{
               searchSend:function(){
            	  var reg = /\b[a-z0-9A-Z]{10}\b|\b[a-z0-9A-Z]{12}\b/;
            	   /* console.log(reg.test(H000021906)); */
            	  var length = this.waybillNum.length;
            	  if(this.waybillNum == ""){
            		  layer.msg('运单编号不能为空！');
            	  }else if(!reg.test(this.waybillNum)){  
            		  layer.msg('运单编号必须为10或12位数字或字母组合！');
            	  }else{
            		  var _this = this;
                      var dataObj = {};
                      dataObj.waybillNum = this.waybillNum;
                         $.ajax({
                            url: base_url + '/transport/reorder/selectSendReorder',
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
                            	//console.log(data);
                            	if(data.resultCode != "200"){
                            		layer.open({
                                		title:"提示信息",
                                		content:data.reason
                                	});
                            	}else{
                            		_this.isShow = true;
                                    _this.dataList = data.data;
                                    layui.use(['laydate','form'], function(){
                                    	var form = layui.form;
                                        form.render(); 
                                        var laydate = layui.laydate;
                                    	var fdfs = data.data[0].fdfs;
                                    	var fdjctime = data.data[0].fdjctime;
                                    	//备注
                                    	_this.send_remark = data.data[0].fdfscomm;
                                    	//单号
                                   	    _this.send_num = data.data[0].fdYouJiHao;
                                        if(data.data[0].fdJinZhiLuRu != "1"){
                                        	 $(".exceedsModify").removeAttr("disabled");
                                        	 $("#inputWay").hide();
                                        	 if(fdjctime){
                                             	_this.send_date = fdjctime.split(".")[0];
                                        	 }else{
                                        		//当前系统时间
                     						     var date = new Date();
                     						   	 _this.send_date=getMyDate(date);
                                        	 }
                                        	//执行一个laydate实例
                                             laydate.render({
                                                elem: '#send-date', //指定元素
                                                format:"yyyy-MM-dd HH:mm:ss", //定义日期格式
                                                type: 'datetime',
                                                max: _this.send_date,           //设置最大时间 
                                                done: function(value, date, endDate){
                                                   _this.send_date = value;
                                                }
                                             }); 
                                        	 if(fdfs){
                                        		 $("dd").each(function(){
                                             		if($(this).attr("lay-value") == fdfs){
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
               saveSend:function(){
                    if(this.send_num && this.send_num!=""){
                    	this._ajaxData();
                    }else{
                    	layer.msg("返单邮寄号码不能为空");
                    }
               },
	           _ajaxData:function(){
	            	var nL = this.send_num.length;
                    var reg = /^[0-9a-zA-Z]+$/;
	                var flag = reg.test(this.send_num);
	                //console.log(this.send_remark);
	                if(!this.send_remark||this.send_remark==""){
	                	var rL = 0;
	                }else{
	                	var rL = this.send_remark.length;
	                }
	                if(nL>30 || !flag || rL>200){
	                	if(!flag){
	                		layer.msg("返单邮寄号码不能为空且只能输入字母或数字！");
	                		this.send_num = "";
	                	}
	                	if(nL>30){
	                		layer.msg("返单邮寄号码最多录入30个字符！");
	                		this.send_num = "";
	                	}
	                	if(rL>200){
	                		layer.msg("备注最多录入200个字符！");
	                		this.send_remark = "";
	                	}
	                }else{
	                	var dataArr = {};
	                    dataArr.waybillNum = this.waybillNum;
	                    dataArr.fdjctime = this.send_date;
	                    dataArr.fdfs = Number($(".layui-this").attr("lay-value"));
	                    dataArr.fdyoujihao = this.send_num;
	                    dataArr.fdfscomm = this.send_remark;
	                    $.ajax({
	                       url:base_url + '/transport/reorder/saveSendReorder',
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
	                           }
	                       },
	                       error:function(data){
	                          console.log(data)
	                       }
	                    });
	                }
	            }
         }
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