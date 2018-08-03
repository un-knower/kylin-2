/*
 * 运输受理单打印
 * created by lhz on 2018/2/27
 */
/*
 * 生成实例
 * */ 
var vm = new Vue({
		el: '#arrivellodiing',
		data: {
			moneyData: {},
			otherData: {},
			printData2:{},
			printData3:{},
			countMoney:"",
			nowDate:""
		},
		methods: {
			_initHandler: function(){
				this.nowDate=getMyDate();
				var allDatas = JSON.parse(sessionStorage.getItem('conveyPrintInf'));
				this.moneyData = allDatas;
				this.otherData = allDatas.print;
				if(this.otherData.ywlx == "0"){
					this.otherData.ywlxText = "普通运输";
				}else if(this.otherData.ywlx == "1"){
					this.otherData.ywlxText = "快捷运输";
				} 
				this.printData2 = allDatas.printData[0];
				this.printData3 = allDatas.printData[1];	
			},
			_printHandler: function(){
				var ydbhid = this.otherData.ydbhid;
				$.ajax({
					url:base_url + '/transport/finance/updatePrintCountAndDate?waybillNum='+ydbhid,
					type:'get',
					dataType:'JSON',
					contentType:'application/json',
					success: function(data){
						//console.log(data);
						window.print();
					},
					error: function(data){
						console.log(data);
					}
				});
			}
		},
		created: function(){
			this._initHandler();                       
		},
		mounted: function(){
			
		}
	})
function getMyDate(){  
     var subDate = new Date(), 
     subYear = subDate.getFullYear(),  
     subMonth = subDate.getMonth()+1,  
     subDay = subDate.getDate(),  
     subHour = subDate.getHours(),  
     subMin = subDate.getMinutes(),  
     subSen = subDate.getSeconds(),  
     subTime = subYear +'-'+ getzf(subMonth) +'-'+ getzf(subDay) +' '+ getzf(subHour) +':'+ getzf(subMin)+':'+ getzf(subSen);//最后拼接时间  
     return subTime;  
 
   };  
   //补0操作  
   function getzf(num){  
       if(parseInt(num) < 10){  
           num = '0'+num;  
       }  
       return num;  
   } 