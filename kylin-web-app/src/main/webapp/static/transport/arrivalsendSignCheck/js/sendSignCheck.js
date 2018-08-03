/*
 * created by lhz on 2017/1/26
 * 送货签收单查询
 */
/*
 * 生成实例
 * */
var vm = new Vue({
	el: '#arrivellodiing',
	data: {/*变量声明*/
		checkinfo: {
			ydbhid:'',
			thqshdid:'',
			yipai: '',
		},
		company: '',
		username: '',
		cancelsendData: [],
		importDta : []
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
				var endDate = new Date();
				var startDate = new Date();
				startDate.setDate(endDate.getDate() - 7);/*取前一星期的日期*/
				var endTime = formatDate(endDate,'本月年月日');
				var startTime = formatDate(startDate,'本月年月日');
				$('#cardatestart').val(startTime);
				$('#cardateend').val(endTime);
			})
		},
		_contentChange: function(){
			$('#cardatestart').val('');
			$('#cardateend').val('');
		},
		_ajaxRequest: function(bool){/*查询ajax*/
			this.cancelsendData = [];
			this.company = this.$refs.companys.companyName;
			vm.$set(vm.checkinfo,'starttime',$('#cardatestart').val());
			vm.$set(vm.checkinfo,'endtime',$('#cardateend').val());
			vm.$set(vm.checkinfo,'gs',this.company);
        	EasyAjax.ajax_Post_Json({
				dataType: 'json',
                url: base_url + '/transport/bundleArrive/documents/delivery/manage/search',
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
				vm.importDta = data.collection;
				vm._tableRender(data.collection,bool);
			});
		},
		_tableRender: function(data,bool){
			layui.use('table', function(){
				vm.table$ = layui.table;
				vm.table$.render({
					elem: '#tableList',
					data: data,
					cols: [[
					    {type:'checkbox', fixed: true},
					    {field: 'gs', title: '公司', width: 60},
					    {field: 'id', title: '派车单号', width: 100},
					    {field: 'yshm', title: '运输号码', width: 150},
					    {field: 'ydbhid', title: '运单编号', width: 110},
					    {field: 'shhd', title: '发站网点', width: 100},
					    {field: 'ywlx', title: '业务类型', width: 100},
					    {field: 'fhdw', title: '发货单位', width: 180},
					    {field: 'fhdh', title: '发货电话', width: 120},
					    {field: 'shdw', title: '收货单位', width: 150},
					    {field: 'shlxr', title: '收货人手机', width: 130},
					    {field: 'shdz', title: '收货地址', width: 170},
					    {field: 'shdh', title: '送货电话', width: 120},
					    {field: 'js', title: '件数', width: 80},
					    {field: 'zl', title: '重量', width: 120},
					    {field: 'tj', title: '体积', width: 120},
					    {field: 'dsk', title: '代收款', width: 100},
					    {field: 'hdfk', title: '到付款', width: 100},
					    {field: 'dzygrid', title: '单证员', width: 100},
					    {field: 'dzy', title: '单证员账号', width: 100},
					    {field: 'kdtime', title: '开单时间', width: 180},
					    {field: 'jhshtime', title: '计划送货时间', width: 180},
					    {field: 'pcdd', title: '派车调度', width: 120},
					    {field: 'pcddgrid', title: '调度账号', width: 90},
					    {field: 'pcyes', title: '已派车', width: 90},
					    {field: 'thtime', title: '仓库提货时间', width: 182},
					    {field: 'zxbz', title: '装卸班组', width: 100},
					    {field: 'cgyqm', title: '仓管员', width: 80},
					    {field: 'qsr', title: '客户签收', width: 100},
					    {field: 'qstime', title: '签收时间', width: 180},
					    {field: 'pcpctime', title: '派车时间', width: 180},
					    {field: 'pcqsd', title: '起始地', width: 100},
					    {field: 'pcshd', title: '送货地', width: 100},
					    {field: 'tjhsr', title: '核算人', width: 100},
					    {field: 'tjhsrgrid', title: '核算人账号', width: 100},
					    {field: 'tjtime', title: '结算时间', width: 100},
					    {field: 'pszhsh', title: '派送指示', width: 100}
					]],
					id:"demoList",
					cellMinWidth: 80,
					limit: 10,
					limits: [10],
					page: true,
					done: function(res){
						if(bool){
							layer.msg('撤销成功！',{icon: 1});
						}
						vm._tableOn(res.collection);/*获取所有数据*/
					}
				})
			})
				
		},
		_tableOn: function(res){
			vm.table$.on('checkbox(table-data)', function(obj){
				  if(obj.type == 'all'){
						if(obj.checked){
							$.each(res, function(i,a){
								var dataObj = {gs:a.gs,id:a.id,gsid: a.gsid};
								if(JSON.stringify(vm.cancelsendData).indexOf(JSON.stringify(dataObj))==-1){
									vm.cancelsendData.push(dataObj);
								}
							})
						}else{
							$.each(res, function(i,a){
								var dataObj = {gs: a.gs,id:a.id,gsid:a.gsid};
								var index = JSON.stringify(vm.cancelsendData).indexOf(JSON.stringify(dataObj));
								if(index > -1){
									vm.cancelsendData = vm.cancelsendData.filter(function(item,idex){
										return item.id != dataObj.id;
									});
								}
							})
						}
						
					}else{
						var dataObj = {gs:obj.data.gs,id:obj.data.id,gsid:obj.data.gsid};
						if(obj.checked){
							vm.cancelsendData.push(dataObj);
						}else{
							var index = JSON.stringify(vm.cancelsendData).indexOf(JSON.stringify(dataObj));
							if(index > -1){
								vm.cancelsendData = vm.cancelsendData.filter(function(item,idex){
									return item.id != dataObj.id;
								});
							}
						}
					}
			});
		},
		_cancelReceipt: function(){/*撤销操作*/
			if(this.cancelsendData.length > 1){
				layer.msg('只能选择一条数据',{anim:6,icon:5});
				return false;
			}else if(this.cancelsendData.length == 0){
				layer.msg('请选择一条数据',{anim:6,icon:5});
				return false;
			}else{
				var commitData = this.cancelsendData[0];
			}
			EasyAjax.ajax_Post_Json({
				dataType: 'json',
	            url: base_url + '/transport/bundleArrive/documents/delivery/cancel',
	            contentType: "application/json",
	            data: JSON.stringify(commitData),
	            errorReason: true,
	            alertMsg: false,
	            beforeHandler: function(){
	            	loadings = layer.load(0,{
	            		shade: [0.5,'#fff']
	            	})
	            },
	            afterHandler: function(){
	            	layer.close(loadings);
	            }
			},function(data){
				vm._ajaxRequest(true);
			});
		},
		_pachiModify: function(){
			if(this.cancelsendData.length > 1){
				layer.msg('只能选择一条数据',{anim:6,icon:5});
				return false;
			}else if(this.cancelsendData.length == 0){
				layer.msg('请选择一条数据',{anim:6,icon:5});
				return false;
			}else{
				vm._layerContent();
			}
		},
		_layerContent: function(){
			layer.open({
				type: 2,
				area: ['1000px','80%'],
				title: '派车修改操作',
				closeBtn: 1, //显示关闭按钮
				shade: 0.8,
				content: [base_url + "/transport/carOut/toCheckDelivery?pcdid="+this.cancelsendData[0].id, 'yes']/*送货派车签收单  后面的yes是允许出现滚动条*/
				/*content: [base_url + "/transport/bundleArrive/arrive/toPrintSendSign", 'yes']    */
				/*content: [base_url + "/transport/bundleArrive/arrive/sendSignNodriver", 'yes']*//*送货签收单*/
			})
		},/*导出*/
		_importData: function(){
			if(vm.importDta.length == 0){
				layer.msg('暂无数据，请先查询');
			}else{
				window.location.href = base_url + '/transport/bundleArrive/exportExcel/delivery';
			}
			
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
