/*
 * created by lhz on 2017/1/26
 * 取货（派车）签收单查询
 */
var vm = new Vue({
	el: '#arrivellodiing',
	data: {/*变量声明*/
		checkinfo: {
			gsid: '',
			pcyes: '',
			xdtimeStart: '',
			xdtimeEnd: '',
			carNum:''
		},
		company: '',
		username: '',
		check_count: 0,
		check_pcid: [],
		table$: ''
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
					elem: '#xdtimeStart',
					type: 'date'
				});
				laydate.render({
					elem: '#xdtimeEnd',
					type: 'date'
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
				$('#xdtimeStart').val(startTime);
				$('#xdtimeEnd').val(endTime);
			})
		},
		_ajaxRequest: function(){/*查询ajax*/
			this.company = this.$refs.companys.code;
			vm.$set(vm.checkinfo,'xdtimeStart',$('#xdtimeStart').val());
			vm.$set(vm.checkinfo,'xdtimeEnd',$('#xdtimeEnd').val());
			var pcyes = '';
			if($('.pcyes_all').is(':checked')){
				pcyes = '';
			}else if($('.pcyes_yes').is(':checked')){
				pcyes = '1';
			}else if($('.pcyes_no').is(':checked')){
				pcyes = '0';
			}
			var carNum = $('#carNum').val();
			
			     
			var re=/[^\d]/g;
			if (re.test(carNum)){
				layer.msg("派车单号只能输入数字!");
				return;
			} 
			     
			vm.$set(vm.checkinfo,'carNum',carNum);    
			vm.$set(vm.checkinfo,'pcyes',pcyes);
			vm.$set(vm.checkinfo,'gsid',this.company);
			EasyAjax.ajax_Post_Json({
				dataType: 'json',
                url: base_url + '/transport/bundleArrive/searchDeliveryCarHandling',
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
				$.each(data.message,function(i,a){
					vm._filedChange(a);
				});
				vm._tableRender(data.message);/*表格渲染*/
			});
		},_filedChange: function(a){
			//业务类型 ywlx
			if(a.ywlx == 0){
				a.ywlx = '普件';
			}else if(a.ywlx == -1){
				a.ywlx = '慢件';
			}else if(a.ywlx == 1){
				a.ywlx = '快件';
			}else if(a.ywlx == 2){
				a.ywlx = '特快';
			}
			//派车状态
			if(a.pcyes == '0'){
				a.pcyes = '未派';
			}else if(a.pcyes == '1'){
				a.pcyes = '已派';
			}
			//重量
			a.zl = Math.round(a.zl*1000)/1000;
			a.tj = Math.round(a.tj*1000)/1000;
		},
		_tableRender: function(data){
			layui.use(['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element'], function(){
				vm.table$ = layui.table;
				layui.table.render({
					elem: '#tableList',
					data: data,
					cols: [[
					    {type:'checkbox'},
					    {field: 'gsid', title: '公司编码',width:80},
					    {field: 'xdtime', title: '下单时间',width:150},
					    {field: 'id', title: '派车单号',width:80},
					   // {field: 'OrderNo', title: '订单号'},
					    {field: 'yywd', title: '营业网点',width:100},
					    {field: 'kfy', title: '客服员',width:80},
					    {field: 'hwdaozhan', title: '货物到站',width:190},
					    {field: 'ywlx', title: '业务类型',width:80},
					    {field: 'ysfs', title: '运输方式',width:80},
					    {field: 'js', title: '件数',width:70},
					    {field: 'zl', title: '重量',width:70},
					    {field: 'tj', title: '体积',width:70},
					    {field: 'fhr', title: '发货人',width:270},
					    {field: 'qhadd', title: '取货地址',width:220},
					    {field: 'jhqhtime', title: '计划取货时间',width:140},
					    {field: 'pcyes', title: '已派车'},
					    {field: 'ddpcdd', title: '派车调度'},
					    {field: 'ddpctime', title: '派车时间',width:140},
					    {fixed: 'right', width: 250, align:'center', toolbar: '#dispatchAndCalc'}
					]],
					limit: 10,
					cellMinWidth: 100,
					limits: [10],
					id:"demoList",
					done: function(res, curr, count){
						vm._tableOn(res);			
					},
					page: true
				})
				
				layui.table.on('tool(table-data)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
					var data = obj.data //获得当前行数据
				    ,layEvent = obj.event; //获得 lay-event 对应的值
				    if(layEvent === 'dispatch'){
				    	layer.open({
						  type: 2,
						  title: "调度派车操作（派车单号:"+data.id+"）",
						  closeBtn: 1, //显示关闭按钮
						  shade: [0],
						  area: ['90%', '90%'],
						  //offset: 'rb', //右下角弹出
						 // time: 2000, //2秒后自动关闭
						  anim: 2,
						  content: [ base_url + "/transport/bundleArrive/picktohomeEdit/"+data.id+"/1", 'yes'], //iframe的url，no代表不显示滚动条
						  end: function () {
							  $("body").css ('overflow-y','auto');
				            }
						});
				    	//window.location.href = base_url + "/transport/bundleArrive/picktohomeEdit/"+data.id;
				    } else if(layEvent === 'calc'){
				    	layer.open({
						  type: 2,
						  title: "签收结算（派车单号:"+data.id+"）",
						  closeBtn: 1, //显示关闭按钮
						  shade: [0],
						  area: ['90%', '90%'],
						  //offset: 'rb', //右下角弹出
						 // time: 2000, //2秒后自动关闭
						  anim: 2,
						  content: [ base_url + "/transport/bundleArrive/arrive/receipttSignSettle/"+data.id, 'yes'], //iframe的url，no代表不显示滚动条
						  end: function () {
							  $("body").css ('overflow-y','auto');
				            }
						});
				    	//window.location.href = base_url + "/transport/bundleArrive/arrive/receipttSignSettle/"+data.id;
				    } else if(layEvent === 'pickgoods'){
				    	layer.open({
						  type: 2,
						  title: "取货派车操作（派车单号:"+data.id+"）",
						  closeBtn: 1, //显示关闭按钮
						  shade: [0],
						  area: ['90%', '90%'],
						  //offset: 'rb', //右下角弹出
						 // time: 2000, //2秒后自动关闭
						  anim: 2,
						  content: [ base_url + "/transport/bundleArrive/picktohomeEdit/"+data.id+"/0", 'yes'], //iframe的url，no代表不显示滚动条
						  end: function () {
							  $("body").css ('overflow-y','auto');
				            }
						});
				    	//window.location.href = base_url + "/transport/bundleArrive/picktohomeEdit/"+data.id;
				    }
				});
			})
		},
		_tableOn: function(res){
			vm.table$.on('checkbox(table-data)',function(obj){
				/* 
				 * console.log(obj.checked); //当前是否选中状态
				 * console.log(obj.data); //选中行的相关数据
				 * console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
				 */
				/*if(obj.checked){
					sessionStorage.setItem('printData',JSON.stringify(obj.data));
				}else{
					sessionStorage.setItem('printData','');
				}*/
			})
		},
		_layerContentPage: function(){
			layer.open({
				  type: 2,
				  title: "录入上门取货指令",
				  closeBtn: 1, //显示关闭按钮
				  shade: [0],
				  area: ['90%', '90%'],
				  //offset: 'rb', //右下角弹出
				 // time: 2000, //2秒后自动关闭
				  anim: 2,
				  content: [ base_url + "/transport/bundleArrive/picktohome", 'yes'], //iframe的url，no代表不显示滚动条
				  end: function () {
					  $("body").css ('overflow-y','auto');
		            }
				});
		},
		_printHandler: function(){/*打印派车单*/
			layer.open({
				type: 2,
				area: ['1000px','80%'],
				title: '派车修改操作',
				closeBtn: 1, //显示关闭按钮
				shade: 0.8,
				content: [base_url + "/transport/bundleArrive/arrive/toPrintReceiptSign", 'yes'] 
			})
		},
		 _expt:function(){
			 this.company = this.$refs.companys.code;
				var pcye = '';
				if($('.pcyes_all').is(':checked')){
					pcye = '';
				}else if($('.pcyes_yes').is(':checked')){
					pcye = '1';
				}else if($('.pcyes_no').is(':checked')){
					pcye = '0';
				}
				
				$.ajax({
					  url: base_url + '/transport/bundleArrive/exptVehicleDispatching',
					  type: 'post',
					  dataType: 'json',
					  contentType:'application/json',
					  data:JSON.stringify({gsid:this.company,
						  xdtimeStart:$('#xdtimeStart').val(),xdtimeEnd:$('#xdtimeEnd').val(),
						  pcyes:pcye}),			
					  timeout: 5000,
					  success: function (data) {
						  var comfirm = layer.open({
								content:'导出数据稍有延迟 请稍后',
								btn:['确定','取消'],
								yes: function(){
									console.log(data);
									 window.location.href = base_url +'/static/upload/exptVehicle.xls';
									 layer.close(comfirm);
								}
							})
						  
					  },
					  fail: function (err, status) {
					    console.log(err)
					  }
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
