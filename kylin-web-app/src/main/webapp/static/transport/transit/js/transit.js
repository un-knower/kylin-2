/*
 * created by wyp on 2018/3/30
 *
 *货物在途跟踪查看
 */

var wayBillNum=sessionStorage.getItem("transId");
var s_type=1;
var vm = new Vue({
	el:'#rrapp',
	data:{
		orderList:{}
	},
	watch:{
		 orderList:function(){
			  this.$nextTick(function(){
			  });
		  }
	},
	methods: {	
		getInfo: function(){
			//var wayBillNum="123456789012";
			//console.log(baseValue);
			  layui.use(['layer','form'], function(){
		    		var layer = layui.layer;
					EasyAjax.ajax_Post_Json({
						  dataType: 'json',
						  url:base_url + '/transport/trace/selectTrackInfo/'+wayBillNum,
						 // data:JSON.stringify(baseValue),
						  contentType : 'application/json',
						  errorReason: true,
						  beforeHandler: function(){
			                	loading = layer.load(0,{
			                		shade: [0.5,'#fff']
			                	})
			                },
			                afterHandler: function(){
			                	layer.close(loading);
			                }
						},function(res){
							if(res.resultCode==200){
								$(res.data).each(function(i,item){
									item.shijian=getMyDate(item.shijian);
								});
								vm.orderList=res.data;
							}else{
								layer.msg(res.reason);
							}
						});
					  }); 

		}

	},
	mounted: function(){
          this.getInfo(); //数据加载
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