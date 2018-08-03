/*
 * 取货（派车）签收单打印
 * created by lhz on 2018/2/27
 */
/*
 * 生成实例
 * */
var vm = new Vue({
		el: '#arrivellodiing',
		data: {
			allData: {
				goodsList: [],
				orderEntity: {},
				vehicleinfofrist: {},
				vehicleinfosecond: {},
				vehicleinfothird: {},
				vehicleinfofourth: {}
			}
		},
		methods: {
			_initHandler: function(){
				var allDatas = JSON.parse(sessionStorage.getItem('printData'));
				this.allData.goodsList = allDatas.goodsList;
				this.allData.orderEntity = allDatas.orderEntity;
				if(allDatas.dispatchList[0]!=undefined){
					this.allData.vehicleinfofrist = allDatas.dispatchList[0];
				}else{
					this.allData.vehicleinfofrist.clxz="";
					this.allData.vehicleinfofrist.ch="";
					this.allData.vehicleinfofrist.cx="";
					this.allData.vehicleinfofrist.sj="";
				}
				if(allDatas.dispatchList[1]!=undefined){
					this.allData.vehicleinfosecond = allDatas.dispatchList[1];
				}else{
					this.allData.vehicleinfosecond.clxz="";
					this.allData.vehicleinfosecond.ch="";
					this.allData.vehicleinfosecond.cx="";
					this.allData.vehicleinfosecond.sj="";
				}
				if(allDatas.dispatchList[2]!=undefined){
					this.allData.vehicleinfothird = allDatas.dispatchList[2];
				}else{
					this.allData.vehicleinfothird.clxz="";
					this.allData.vehicleinfothird.ch="";
					this.allData.vehicleinfothird.cx="";
					this.allData.vehicleinfothird.sj="";
				}
				if(allDatas.dispatchList[3]!=undefined){
					this.allData.vehicleinfofourth = allDatas.dispatchList[3];
				}else{
					this.allData.vehicleinfofourth.clxz="";
					this.allData.vehicleinfofourth.ch="";
					this.allData.vehicleinfofourth.cx="";
					this.allData.vehicleinfofourth.sj="";
				}
			},
			_printHandler: function(){
				window.print();
			}
		},
		created: function(){
			this._initHandler();
			console.log(this.allData);
		},
		mounted: function(){
			if(this.allData.orderEntity.songhuoshanglou == 1){
				$('#songhuoshanglou').attr('checked',true);
				//$('#daikezhuangxie').removeAttr('checked');
			}else{
				$('#songhuoshanglou').removeAttr('checked');
			}
			
		    if(this.allData.orderEntity.daikezhuangxie == 1){
				$('#daikezhuangxie').attr('checked',true);
				
			}else{
				$('#daikezhuangxie').removeAttr('checked');
			}
			
		}
	})
