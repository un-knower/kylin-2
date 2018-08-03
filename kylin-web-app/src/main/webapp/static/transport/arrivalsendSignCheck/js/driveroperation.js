/*
 * created by lhz on 2017/1/26
 * 取货（派车）签收单查询
 */
/*
 * 生成实例
 * */
var quhuoSign = [[/*取货派车查询司机/车号*/
				    {type:'checkbox', fixed: true},
				    {field: 'isTiShi', title: '公司', width: 60},
				    {field: 'isKaiyun', title: '派车单号', width: 80},
				    {field: 'isGreenchanel', title: '营业网点', width: 80},
				    {field: 'chxh', title: '客服员', width: 90},
				    {field: 'yshhm', title: '客服账号', width: 100},
				    {field: 'ydxzh', title: '到站', width: 70},
				    {field: 'fazhanHwyd', title: '业务类型（新）', width: 90},
				    {field: 'shhdHwyd', title: '运输方式（新）', width: 80},
				    {field: 'daozhanHwyd', title: '托运人', width: 80},
				    {field: 'dzshhdHwyd', title: '联系人', width: 100},
				    {field: 'fhshj', title: '省市', width: 170},
				    {field: 'fazhan', title: '市区县', width: 80},
				    {field: 'daozhan', title: '取货地址', width: 80},
				    {field: 'zhchrq', title: '联系电话', width: 100},
				    {field: 'fhdwmch', title: '下单件数', width: 290},
				    {field: 'pinming', title: '下单重量', width: 60},
				    {field: 'jianshuHwydxz', title: '下单体积', width: 83},
				    {field: 'jianshu', title: '统计件数', width: 78},
				    {field: 'zhl', title: '统计重量', width: 80},
				    {field: 'tiji', title: '统计体积', width: 60},
				    {field: 'shhrmch', title: '返单', width: 250},
				    {field: 'shhrlxdh', title: '带装卸', width: 120},
				    {field: 'ziti', title: '带运单', width: 90},
				    {field: 'yiti', title: '收运费', width: 90},
				    {field: 'beizhu', title: '下单时间', width: 60},
				    {field: 'xh', title: '计划取货时间', width: 100},
				    {field: 'hyy', title: '要求返回时间', width: 80},
				    {field: 'zhipiao2', title: '派车调度', width: 100},
				    {field: 'ydzh', title: '调度帐号', width: 100},
				    {field: 'tbjeHwydxz', title: '派车时间', width: 100},
				    {field: 'ysfs', title: '起始地', width: 100},
				    {field: 'grid', title: '取货地', width: 100},
				    {field: 'lrsj', title: '操作员', width: 100},
				    {field: 'dhgrid', title: '签收时间', width: 100},
				    {field: 'dhsj', title: '统计员', width: 100},
				    {field: 'vhjje', title: '统计员+AK1帐号', width: 100},
				    {field: 'vhjje', title: '结算时间', width: 100},
				    {field: 'vhjje', title: '已派车', width: 100},
				    {field: 'vhjje', title: '车号', width: 100},
				    {field: 'vhjje', title: '司机', width: 100},
				    {field: 'vhjje', title: '货物品名', width: 100}
				]],
	quhuoSign = [[/*取货派车查询*/
				    {type:'checkbox', fixed: true},
				    {field: 'isTiShi', title: '公司', width: 60},
				    {field: 'isKaiyun', title: '派车单号', width: 80},
				    {field: 'isGreenchanel', title: '营业网点', width: 80},
				    {field: 'chxh', title: '客服员', width: 90},
				    {field: 'yshhm', title: '客服账号', width: 100},
				    {field: 'ydxzh', title: '到站', width: 70},
				    {field: 'fazhanHwyd', title: '业务类型（旧）', width: 90},
				    {field: 'shhdHwyd', title: '运输方式（新）', width: 80},
				    {field: 'daozhanHwyd', title: '托运人', width: 80},
				    {field: 'dzshhdHwyd', title: '联系人', width: 100},
				    {field: 'fhshj', title: '收货省市', width: 170},
				    {field: 'fazhan', title: '收货市区县', width: 80},
				    {field: 'daozhan', title: '取货地址', width: 80},
				    {field: 'zhchrq', title: '联系电话', width: 100},
				    {field: 'fhdwmch', title: '下单件数', width: 290},
				    {field: 'pinming', title: '下单重量', width: 60},
				    {field: 'jianshuHwydxz', title: '下单体积', width: 83},
				    {field: 'jianshu', title: '统计件数', width: 78},
				    {field: 'zhl', title: '统计重量', width: 80},
				    {field: 'tiji', title: '统计体积', width: 60},
				    {field: 'shhrmch', title: '返单', width: 250},
				    {field: 'shhrlxdh', title: '带装卸', width: 120},
				    {field: 'ziti', title: '带运单', width: 90},
				    {field: 'yiti', title: '收运费', width: 90},
				    {field: 'beizhu', title: '下单时间', width: 60},
				    {field: 'xh', title: '计划取货时间', width: 100},
				    {field: 'hyy', title: '要求返回时间', width: 80},
				    {field: 'zhipiao2', title: '派车调度', width: 100},
				    {field: 'ydzh', title: '调度帐号', width: 100},
				    {field: 'tbjeHwydxz', title: '派车时间', width: 100},
				    {field: 'ysfs', title: '起始地', width: 100},
				    {field: 'grid', title: '取货地', width: 100},
				    {field: 'lrsj', title: '操作员', width: 100},
				    {field: 'dhgrid', title: '签收时间', width: 100},
				    {field: 'dhsj', title: '统计员', width: 100},
				    {field: 'vhjje', title: '统计员帐号', width: 100},
				    {field: 'vhjje', title: '结算时间', width: 100},
				    {field: 'vhjje', title: '已派车', width: 100}
				]]

var vm = new Vue({
	el: '#arrivellodiing',
	data: {/*变量声明*/
		checkinfo: {
			transportCode: '',
			carNo: '',
			companyName: '',
			yipai: '',
		},
		company: '',
		username: ''
	},
	methods: {
		_initDOM: function(){
			/*获取当前登录人*/    
			var username = window.location.href.split('=')[1];
			username = decodeURI(username);
			this.username = username;
			/* ******* */
			/*初始化单选框以及日期*/
			layui.use(['laydate','form'], function(){
       			var form = layui.form;
       			var laydate = layui.laydate;
				laydate.render({
					elem: '#cardatestart',
					type: 'date'
				});
				laydate.render({
					elem: '#cardateend',
					type: 'date'
				});
				form.on('radio(all)',function(data){
					vm.checkinfo.yipai = '';
				});
				form.on('radio(yiti)',function(data){
					vm.checkinfo.yipai = true;
				});
				form.on('radio(weiti)',function(data){
					vm.checkinfo.yipai = false;
				});
				form.on('submit(toCheck)', function(){
					vm._ajaxRequest();
					return false;
				})
			})
		},
		_ajaxRequest: function(){/*查询ajax*/
			this.company = this.$refs.companys.companyName;
			vm.$set(vm.checkinfo,'loadingDateBegin',$('#cardatestart').val());
			vm.$set(vm.checkinfo,'loadingDateEnd',$('#cardateend').val());
			vm.$set(vm.checkinfo,'companyName',this.company);
			/*以上是要传的参数，部分可能字段名可能要更改，具体对比接口*/
			EasyAjax.ajax_Post_Json({
				dataType: 'json',
                url: base_url + '/transport/bundleArrive/searchBundleArrive',
                contentType: "application/json",
                data: JSON.stringify(this.checkinfo),
                errorReason: true,
                beforeHandler: function(){
                	loading = layer.load(0,{
                		shade: [0.5,'#fff']
                	})
                },
                afterHandler: function(){
                	layer.close(loading);
                }
			},function(data){
				layer.close(loading);
				/*一下是可能存在部分字段需要代号转换的*/
				/*$.each(data.message,function(i,a){
					vm._filedChange(a);
				});*/
				vm._tableRender(data.message);/*表格渲染*/
			});
		},
		_tableRender: function(data){
			layui.use('table', function(){
				vm.table$ = layui.table;
				vm.table$.render({
					elem: '#tableList',
					data: data,
					cols: '',
					limit: 10,
					limits: [10],
					id:"demoList",
					done: function(res, curr, count){
						/*console.log(res);//当前页数据
						console.log(curr);//当前页
						console.log(count);//总共条数*/
					},
					page: true
				})
			})
		},
		_layerContent: function(){
			layer.open({
				type: 2,
				area: ['1000px','80%'],
				title: false,
				closeBtn: 1, //显示关闭按钮
				shade: 0.8,
				/*content: [base_url + "/transport/bundleArrive/arrive/sendSign", 'yes']*//*送货派车签收单*/
				/*content: [base_url + "/transport/bundleArrive/arrive/sendSignSettle", 'yes']*//*送货签收单结算*/
				content: [base_url + "/transport/bundleArrive/arrive/sendSignNodriver", 'yes']/*送货签收单*/
			})
		}
	},
	mounted: function(){
		this._initDOM();
	},
	created: function(){
		
	},
	components: {
		'company-select': companySelect
	}
})
