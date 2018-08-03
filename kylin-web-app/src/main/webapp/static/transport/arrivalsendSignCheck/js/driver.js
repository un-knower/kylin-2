/*
 * created by lhz on 2017/1/26
 * 送货派车查询司机
 */
/*
 * 生成实例
 * */
var vm = new Vue({
	el: '#arrivellodiing',
	data: {/*变量声明*/
		checkinfo: {
			ydbhid: '',/*运单号*/
			thqshdid: '',/*派车单号*/
			yipai: ''
		},
		company: '',
		username: '',
		cancelsendData: [],
		importDta: []
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
		_ajaxRequest: function(){/*查询ajax*/
			this.cancelsendData = [];
			vm.$set(vm.checkinfo,'starttime',$('#cardatestart').val());
			vm.$set(vm.checkinfo,'endtime',$('#cardateend').val());
			EasyAjax.ajax_Post_Json({
				dataType: 'json',
                url: base_url + '/transport/bundleArrive/documents/delivery/manage/dirver/search',
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
				vm._tableRender(data.collection);
			});
			
		},
		_tableRender: function(data){
			layui.use('table', function(){
				vm.table$ = layui.table;
				vm.options = {
						data: data,
						cellMinWidth: 80,
						limit: 10,
						limits: [10],
						page: true,
						done: function(res){
							vm._tableOn(res.collection);/*获取所有数据*/
						}
				};
				vm.table$.reload('demoList',vm.options);
				/*为表格绑定hover事件*/
				var tip;
				$('td[data-field="pccomm"]').unbind().hover(function(){
					var content = $(this).find('div').html();
					var _this = this;
					tip = layer.tips(content,_this);
				},function(){
					layer.close(tip);
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
		_pachiModify: function(){
			console.log(this.cancelsendData);
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
				content: [base_url + "/transport/carOut/toCheckDelivery?pcdid="+this.cancelsendData[0].id, 'yes']/*送货派车签收单*/
			})
		},
		_importData: function(){
			if(vm.importDta.length == 0){
				layer.msg('暂无数据，请先查询');
			}else{
				window.location.href = base_url + '/transport/bundleArrive/exportExcel/driverdelivery';
			}
		}
	},
	mounted: function(){
		this._initDOM();
	},
	created: function(){
		
	}
})
