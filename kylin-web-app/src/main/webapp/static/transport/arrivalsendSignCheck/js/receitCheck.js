/*
 * created by lhz on 2017/1/30
 * 提货签收单查询
 */
/*
 * 生成实例
 * */
var vm = new Vue({
	el: '#arrivellodiing',
	data: {/*变量声明*/
		checkinfo: {},
		company: '',
		username: '',
		cancelsendData: [],
		username: '',
		currTime: '',
		tihuoListXuhao: '',
		tihuoListData: '',
		Interval: ''
	},
	methods: {
		_getCurrTime: function(){
			var _self = this;
			clearInterval(_self.Interval);
			this.Interval = setInterval(function(){ 
				_self.currTime = getCurrTime ();
			},1000)
		},
		clearTime: function(){
			var _self = this;
			clearInterval(_self.Interval);
		},
		_initDOM: function(){
			/*获取当前登录人*/    
			var username = window.location.href.split('=')[1];
			username = decodeURI(username);
			this.username = username;
			/* ******* */
			/*初始化单选框以及日期*/
			layui.use(['laydate','form'], function(){
       			var form = layui.form;
				form.on('submit(toCheck)', function(){
					vm._ajaxRequest();
					return false;
				})
			})
		},
		_ajaxRequest: function(){/*查询ajax*/
			this.company = this.$refs.companys.companyName;
			vm.$set(vm.checkinfo,'companyName',this.company);
			loading = layer.load(0,{
        		shade: [0.5,'#fff']
        	})
			vm._tableRender(vm.checkinfo,loading);
		},
		_tableRender: function(data,loading){
			layui.use('table', function(){
				vm.table$ = layui.table;
				vm.table$.render({
					elem: '#tableList',
					url: base_url + '/transport/bundleArrive/bill/delivery/manage/search',
					id:"demoList",
					where:  data,
		       		method: 'post',
					request: {
						pageName: 'pageNums' //页码的参数名称，默认：page
						,limitName: 'pageSizes' //每页数据量的参数名，默认：limit
					},
					response: {
						statusName: 'resultCode' //数据状态的字段名称，默认：code
						,statusCode: 200 //成功的状态码，默认：0
						,msgName: 'reason' //状态信息的字段名称，默认：msg
						,countName: 'total' //数据总数的字段名称，默认：count
						,dataName: 'collection' //数据列表的字段名称，默认：data
					},
					cellMinWidth: 80,
					limit: 10,
					limits: [10],
					page: true,
					cols: [[
					    {type:'checkbox', fixed: true},
					    {field: 'thqshdid', title: '提货单号', event:'alertTihuo', width: 100},
					    {field: 'ydbhid', title: '运单号', event:'alertTihuo', width: 125},
					    {field: 'ydxzh', title: '细则', event:'alertTihuo', width: 130},
					    {field: 'shhrmch', title: '收货人', event:'alertTihuo', width: 250},
					    {field: 'pinming', title: '品名', event:'alertTihuo', width: 100},
					    {field: 'jianshu', title: '件数', event:'alertTihuo', width: 70},
					    {field: 'zhl', title: '重量', event:'alertTihuo', width: 90},
					    {field: 'tiji', title: '体积', event:'alertTihuo', width: 80},
					    {field: 'bzh', title: '包装', event:'alertTihuo', width: 80},
					    {field: 'cangwei1', title: '仓位/操作员', event:'alertTihuo',  width: 110},
					    {field: 'shijian', title: '时间', event:'alertTihuo', width: 170},
					    {field: 'thrmch', title: '提货人名称', event:'alertTihuo', width: 116},
					    {field: 'thrsfzhm', title: '提货人身份证号', event:'alertTihuo', width: 200},
					    /*{field: 'thrlxdh', title: '提货人联系电话', event:'alertTihuo', width: 150},*/
					    {field: 'shhrsfzhm', title: '收货人身份证', event:'alertTihuo', width: 200},
					    {field: 'kpr', title: '开票人', event:'alertTihuo', width: 80},
					    /*{field: 'shjhm', title: '收据号码', event:'alertTihuo', width: 100},*/
					    {field: 'gs', title: '公司', event:'alertTihuo', width: 100},
					    {fixed: 'right', width: 165, align:'center', toolbar: '#pickSignBill'}
					]],
					done: function(res){
						vm.cancelsendData = [];
						layer.close(loading);
						
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
								 var dataObj = {gs:a.gs,ydbhid:a.ydbhid};
								if(JSON.stringify(vm.cancelsendData).indexOf(JSON.stringify(dataObj))==-1){
									vm.cancelsendData.push(dataObj);
								}
							})
						}else{
							$.each(res, function(i,a){
								 var dataObj = {gs: a.gs,ydbhid:a.ydbhid};
								var index = JSON.stringify(vm.cancelsendData).indexOf(JSON.stringify(dataObj));
								if(index > -1){
									vm.cancelsendData = vm.cancelsendData.filter(function(item,index){
										return item.ydbhid != dataObj.ydbhid;
									})
								}
							})
						}
						
					}else{
						var dataObj = {gs:obj.data.gs,ydbhid:obj.data.ydbhid};
						if(obj.checked){
							vm.cancelsendData.push(dataObj);
						}else{
							var index = JSON.stringify(vm.cancelsendData).indexOf(JSON.stringify(dataObj));
							if(index > -1){
								vm.cancelsendData = vm.cancelsendData.filter(function(item,index){
									return item.ydbhid != dataObj.ydbhid;
								});
							}
						}
					}
			});
			/*点击行进入提货签收单页面编辑*/
			vm.table$.on('tool(table-data)', function(obj){
				var dataobj = obj.data;/*
				console.log(dataobj);
				vm.tihuoListXuhao = [];
				vm.tihuoListXuhao.push(dataobj.xuhao);*/
				var commitData = {thqshdid: dataobj.thqshdid,gs: dataobj.gs};
				var _self = this;
				EasyAjax.ajax_Post_Json({
				dataType: 'json',
	            url: base_url + '/transport/bundleArrive/bill/delivery/search',
	            contentType: "application/json",
	            data: JSON.stringify(commitData),
	            errorReason: true,
	            alertMsg: false,
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
				vm.tihuoListData = [];
				vm.tihuoListData = data.reason.sub;
				vm.$refs.tihuoSign.saveInfo = {
						thrsfzhm: data.reason.thrsfzhm,
						thrmch: data.reason.thrmch,
						shhrsfzhm: data.reason.shhrsfzhm,
						kpr: data.reason.kpr,
						thqshdid: data.reason.thqshdid
				}
				vm.$refs.tihuoSign.tihuo_Num = data.reason.thqshdid;
				vm.$refs.tihuoSign.isSave = false;
				vm.$refs.tihuoSign.isReceipt = true;
				vm._printClick('.receive-bill');
				vm.currTime = data.reason.shijian;
			});
				/*vm._printClick('.receive-bill');*/
			})
		},
		_componentModify: function(){
			var _self = this;
			$('.modifyStatus').unbind().on('input', function(){
				vm.$refs.tihuoSign.isSave = true;
				_self._getCurrTime();
			})
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
	            url: base_url + '/transport/bundleArrive/bill/delivery/cancel',
	            contentType: "application/json",
	            data: JSON.stringify(commitData),
	            errorReason: true,
	            alertMsg: false,
	            beforeHandler: function(){
	            	loading = layer.load(0,{
	            		shade: [0.5,'#fff']
	            	})
	            },
	            afterHandler: function(){
	            	layer.close(loading);
	            }
			},function(data){
				layer.msg('撤销成功！',{icon: 1});
				vm._ajaxRequest();
			});
		},
		_printClick: function(obj){
			this.printclick = 0;
			var area$ = ['1000px','500px'];
			this.layer$ = layer.open({
				type: 1,
				title: '提货签收单',
				area: area$,
				content: $(obj)
			});	
			vm._doComponentsPrint(obj)/*解决第一次打印空白的bug*/
		},
		_doComponentsPrint: function(obj){
			this.printclick ++;
			var stylecss = '<style type="text/css">@import url("'+ctx_static+'/transport/excelRead/css/layui.css");'
			 +'@import url("'+ctx_static+'/publicFile/css/public.css");'
			 +'@import url("'+ctx_static+'/transport/arrivalloding/css/arrivalloding.css")</style>';
			this.f = document.getElementById('printf');
            this.f.contentDocument.write(stylecss+$(obj).html());
	        this.f.contentDocument.close();/*清除上一次打印内容*/
	           if(this.printclick == 1){
	            	window.close();
	            }else{
	            	this.f.contentWindow.print();
	            } 
		},
		_layerContent: function(){
			layer.open({
				type: 2,
				area: ['1000px','80%'],
				title: false,
				closeBtn: 1, //显示关闭按钮
				shade: 0.8,
				content: [base_url + "/transport/bundleArrive/arrive/receiptSign", 'yes']/*取货（派车）签收单*/
				/*content: [base_url + "/transport/bundleArrive/arrive/receipttSignSettle", 'yes']*/
			})
		}
	},
	mounted: function(){
		this._initDOM();
		/*this._getCurrTime();*/
	},
	created: function(){
		
	},
	components: {
		'company-select': companySelect,
		'receipt-bill': receiptBill
	}
})
