/*
 * 送货（派车）签收单打印
 * 
 */
/*
 * 生成实例
 * */
var vm = new Vue({
		el: '#arrivellodiing',
		data: {
			allData: {
				carOutInfo: {},
				goodsDetailInfo: {},
				vehicleinfofrist: {},
				vehicleinfosecond: {},
				vehicleinfothird: {},
				vehicleinfofourth: {},
				fd: false
			}
		},
		methods: {
			_initHandler: function(){
				this.allData = JSON.parse(sessionStorage.getItem('sendSign'));
				/**update by wyp **/
				return true;
			}
		}
	})
