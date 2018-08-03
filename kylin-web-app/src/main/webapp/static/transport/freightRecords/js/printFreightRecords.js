/*
 * created by yl on 2018/05/08
 *  货运记录打印页面
 */

//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r != null) return unescape(r[2]); return null; //返回参数值
}
	//自动在打印之前执行
	window.onbeforeprint = function(){
		$(".layui-form-save").hide();
	}

    //自动在打印之后执行
    window.onafterprint = function(){
    	$(".layui-form-save").show();
    }  
var vm = new Vue({
    	el:'#rrapp',
    	data:{
    		data:{},       //传参对象
    		infoList:{},   //信息列表
    		detailList:[],  //细则列表
    		isFazhanShow:true,  //发站意见栏显示
    	},
    	created: function () {
    		// `this` 指向 vm 实例
    		this.getPrintData();
    	},
  	  	mounted:function(){
  		  
  	  	},
 		watch:{  
 				
        },
    	methods: {
    		  //打印界面加载出来后获取数据
    		  getPrintData:function(){
    			  var _this=this;
  			  	  var baseValue=window.location.href.split('=')[1];
  			  	  _this.data.id = baseValue;
    			  layui.use('layer', function(){
    				$.ajax({
    					url: base_url + '/transport/query/freightRecordDetail',
    					type: 'post',
    					dataType: 'JSON',
    					contentType: 'application/json',
    					data: JSON.stringify(_this.data),
    					beforeSend: function(){
    						 loading = layer.load(0,{
    							shade: [0.5,'#fff']
    						}) 
    					},
    					complete:function(){
    						layer.close(loading);
    					},
    					success: function(data){
    						_this.infoList = data.data.freightRecord;
    						if(data.data.freightRecord.abnormalType == "0"){
    							_this.infoList.abnormalType = "干线";
    						}else if(data.data.freightRecord.abnormalType == "1"){
    							_this.infoList.abnormalType = "配送";
    						}else if(data.data.freightRecord.abnormalType == "2"){
    							_this.infoList.abnormalType = "提货";
    						}
    						//获取发站和到站
    						var fazhan = _this.infoList.fazhan;
    						var daozhan = _this.infoList.daozhan; 
    						if(fazhan == daozhan){
    							_this.isFazhanShow = false;
    						}
    						_this.detailList = data.data.freightRecordDetailList;
    						
    					},
    					error: function(data){
    						console.log(data);
    					}
    				});
    			  });
    		  },
    		  print:function(){
    			  window.print();
    		  }
    	  
      }
});