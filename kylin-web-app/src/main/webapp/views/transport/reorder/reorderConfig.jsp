<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <title>返单管理参数设置</title>
   <%@ include file="/views/transport/include/head.jsp"%>
    <style>
      .breadcrumb {margin: 15px; display: block;visibility: visible !important;}
      .layui-table{width:98%;margin:10px auto;}
      .freight-table{margin:0 auto;}
      .freight-table input{width:100%;height:100%;text-align:center;border:0;}
      .freight-table td, .freight-table th{border-color:#777;}
      .freight-table td{background-color:#fff;}
      .layui-table td, .layui-table th{padding:9px 0;text-align:center;}
      .layui-form-save{margin-top:30px;text-align:center;}
      .saveBtn{height: 38px;line-height: 38px;margin: 0;display: inline-block;}
      .saveBtn, .layui-btn-normal{position: relative;top: -2px;}
    </style>
</head>
<body style="overflow-y: scroll;">
   <span class='layui-breadcrumb breadcrumb'>
      <a href='javaScript:void(0);'>当前位置  &gt;</a>
      <a href='javaScript:void(0);'>返单管理参数设置</a>
   </span>
   <div id="rrapp" v-cloak>
         <form class='layui-form '>
         <table class='layui-table freight-table'>
            <thead>
               <tr>
                  <td>发站超期天数A</td>
                  <td>发站超期天数B</td>
                  <td>发站超期天数C</td>
                  <td>到站超期天数A</td>
                  <td>到站超期天数B</td>
                  <td>到站超期天数C</td>
               </tr>
            </thead>
            <tbody id="showLogList" v-show="isShow">
               <tr>
                  <td><input type="number" min="1" class="exceed-dates" name="fazhan_chaoqi_tianshu_1" v-model="fazhan_date1"></td>
                  <td><input type="number" min="1" class="exceed-dates" name="fazhan_chaoqi_tianshu_2" v-model="fazhan_date2"></td>
                  <td><input type="number" min="1" class="exceed-dates" name="fazhan_chaoqi_tianshu_3" v-model="fazhan_date3"></td>
                  <td><input type="number" min="1" class="exceed-dates" name="daozhan_chaoqi_tianshu_1" v-model="daozhan_date1"></td>
                  <td><input type="number" min="1" class="exceed-dates" name="daozhan_chaoqi_tianshu_2" v-model="daozhan_date2"></td>
                  <td><input type="number" min="1" class="exceed-dates" name="daozhan_chaoqi_tianshu_3" v-model="daozhan_date3"></td>
               </tr>
            </tbody>
         </table>
         <div class="layui-form-item layui-form-save" style="text-align:center">
            <button class="layui-btn saveBtn"  lay-submit  lay-filter='saveBtn' @click="saveData" id='saveBtn'>保存</button>
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
               id:1,
               fazhan_date1:0,  //发站超期天数A
               fazhan_date2:0,  //发站超期天数B
               fazhan_date3:0,  //发站超期天数C
               daozhan_date1:0,  //到站超期天数A
               daozhan_date2:0,  //到站超期天数B
               daozhan_date3:0,  //到站超期天数C
            },
            created:function(){
               this.seachInfo();
            },
            methods:{
               seachInfo:function(){
                  var _this=this;
                  layui.use('layer', function(){
                     $.ajax({
                        url: base_url + '/transport/reorder/selectReorderConfigure',
                        type: 'get',
                        dataType: 'JSON',
                        contentType: 'application/json',
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
                        	if(data.resultCode !="200"){
                        		layer.open({
                               		title:"提示信息",
                               		content:data.reason
                               	});
                        	}else{
                        		_this.isShow = true;
                                _this.id = data.data.id;
                                _this.fazhan_date1 = data.data.fazhan_chaoqi_tianshu_1;
                                _this.fazhan_date2 = data.data.fazhan_chaoqi_tianshu_2;
                                _this.fazhan_date3 = data.data.fazhan_chaoqi_tianshu_3;
                                _this.daozhan_date1 = data.data.daozhan_chaoqi_tianshu_1;
                                _this.daozhan_date2 = data.data.daozhan_chaoqi_tianshu_2;
                                _this.daozhan_date3 = data.data.daozhan_chaoqi_tianshu_3;
                        	}
                           
                        },
                        error: function(data){
                           console.log(data);
                        }
                     });
                   });
               },
               saveData:function(){
            	  var num = 0;
                  var arr = [];
                  var dataArr = {};
                  arr['id'] = this.id;
                  var reg = /(^[0-9]\d*$)/;
           		  $('.exceed-dates').each(function(index,elem){
                     var name = $(elem).attr('name');
                     var value = Number($(elem).val());
                     if(!reg.test(value)){
                    	 num += 1;
                     }
                     arr[name] = value;
                  });
                  for(var key in arr ){
                     dataArr[key] = arr[key];
                  }
                  if(num == 0){
                	  $.ajax({
                          url:base_url + '/transport/reorder/updateReorderConfigure',
                          type: 'post',
                          dataType: 'JSON',
                          contentType: 'application/json',
                          data: JSON.stringify(dataArr),
                          success:function(data){
                             //console.log(data);
                             layer.open({
                                 title: '提示信息',
                                 content: '保存成功！'
                             }); 
                          },
                          error:function(data){
                             
                          }
                       });
                  }else{
                     layer.open({
                          title: '提示信息',
                          content: '超期天数必须为正整数！'
                      }); 
                  }
               }
            },
         });
      
   </script>
</body>
</html>