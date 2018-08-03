/*
 * created by lhz on 2017/1/10
 */
/*
 * 生成实例
 * */
/**
 * 
 */
var vm = new Vue({
	el: '#arrivellodiing',
	data: {/*变量声明*/
		currTime: '',
		componentShow: true,
		layer$: '',
		checkinfo: {
			transportCode: '',
			carNo: '',
			ziti: '',
			yiti: '',
			fazhan :'',
			daozhan : '',
			ysfs:''
		},
		isziti: false,
		table$: '',
		tihuoListData: [],
		tihuoListXuhao: [],
		printData: [],
		tihuoAjaxData: {
			url: base_url + '/transport/bundleArrive/bill/delivery',
			obj: ".receive-bill"
		},
		songhuoAjaxData: {
			url: base_url + '/transport/bundleArrive/documents/delivery',
			obj: ".send-bill"
		},
		company: '',
		username: '',
		beizhus: '',
		compname: '',
		sendinfo: {},
		importDta: [],
		Interval: '',
		_d: ''
	},
	methods: {
		_initDOM: function(){
			var username = window.location.href.split('=')[1];
			username = decodeURI(username);
			this.username = username;
			this._getfazhan();
			this._getDaozhan();
			this.driverList();
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
				form.on('radio(ziti)',function(data){
					//console.log("自提",data);
					vm.isziti = true;
					vm.checkinfo.ziti = 0;
					vm.checkinfo.yiti = '';
				});
				form.on('radio(shsm)',function(data){
					//console.log("送货上门",data);
					$('input:radio[name=isyiti]')[0].checked = false;
					$('input:radio[name=isyiti]')[1].checked = false;
					form.render('radio');
					vm.isziti = false;
					vm.checkinfo.ziti = 1;
					vm.checkinfo.yiti = '';
				});
				form.on('radio(all)',function(data){
					//console.log("全部",data);
					$('input:radio[name=isyiti]')[0].checked = false;
					$('input:radio[name=isyiti]')[1].checked = false;
					vm.isziti = false;
					vm.checkinfo.ziti = '';
					vm.checkinfo.yiti = '';
				});
				form.on('radio(yiti)',function(data){
					//console.log("已提",data);
					vm.checkinfo.yiti = 1;
				});
				form.on('radio(weiti)',function(data){
					//console.log("未提",data);
					vm.checkinfo.yiti = 0;
				});
				var endDate = new Date();
				var startDate = new Date();
				startDate.setDate(endDate.getDate() - 7);/*取前一星期的日期*/
				var endTime = formatDate(endDate,'本月年月日');
				var startTime = formatDate(startDate,'本月年月日');
				$('#cardatestart').val(startTime);
				$('#cardateend').val(endTime);
				//输入运单编号详细查询，装车日期清空
			    $("#waybillId").focus(function(){
			    	$('#cardatestart').val("");
			    	$('#cardateend').val("");
			    });
			    $("#waybillId").blur(function(){
				   if($(this).val() == ""){
					   $('#cardatestart').val(startTime);
					   $('#cardateend').val(endTime);
				   }
			    });
				   
				
			})
		},
		//获取发站下拉框
		_getfazhan: function(){
			var _self = this;
			EasyAjax.ajax_Get({
				dataType: 'json',
				contentType: "application/json",
                url: base_url + '/transport/generalInfo/getCompanyListAndCurrCompanyInfo',
                data: '',
                async: false,
                errorReason: true
			},function(data){
				//var selectType= new Array();
				var html="";
				$(data.COMPANY_LIST).each(function(i,item){
					//selectType[i]=item.companyName;
				    html+='<li value="'+item.companyCode+'">'+item.companyName+'</li>';
				});
				$(".fazhanUI").empty().append(html);
				$(".selectInput_fazhan,.tips_type_fazhan").click(function(e){
					if (e.stopPropagation){ //支持W3C标准 doc
						e.stopPropagation();
					}else{ //IE8及以下浏览器
						e.cancelBubble = true;
					}
					$(".fazhanUI").show();
				});	
				var obj=$(".fazhanUI");
				obj.find("li").click(function(){
					//var _target = $(this).parent("ul").siblings(".selectInput_fazhan");
					vm.checkinfo.fazhan=$(this).text();
					//_target.val($(this).text());
					$(this).parent("ul").hide();		
				});
				$(document).click(function(){
					obj.hide();
				});
			});
		},
		//获取到站下拉框
		_getDaozhan: function(){
			var _self = this;
			EasyAjax.ajax_Get({
				dataType: 'json',
				contentType: "application/json",
                url: base_url + '/transport/generalInfo/getCompanyListAndCurrCompanyInfo',
                data: '',
                async: false,
                errorReason: true
			},function(data){
				//var selectType= new Array();
				var html="";
				$(data.COMPANY_LIST).each(function(i,item){
					//selectType[i]=item.companyName;
				    html+='<li value="'+item.companyCode+'">'+item.companyName+'</li>';
				});
				$(".daozhanUI").empty().append(html);
				$(".selectInput_daozhan,.tips_type_daozhan").click(function(e){
					if (e.stopPropagation){ //支持W3C标准 doc
						e.stopPropagation();
					}else{ //IE8及以下浏览器
						e.cancelBubble = true;
					}
					$(".daozhanUI").show();
				});	
				var obj=$(".daozhanUI");
				obj.find("li").click(function(){
					//var _target = $(this).parent("ul").siblings(".selectInput_fazhan");
					vm.checkinfo.daozhan=$(this).text();
					//_target.val($(this).text());
					$(this).parent("ul").hide();		
				});
				$(document).click(function(){
					obj.hide();
				});
			});
		},
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
		_removeForm: function(){
			layui.use('form',function(){
				var form = layui.form;
				form.on('submit(toCheck)', function(){
					vm._ajaxRequest();
					return false;
				})
			})
		},
		//获取运输方式下拉框
		driverList: function(){
			EasyAjax.ajax_Post_Json({
			  dataType: 'json',
			  url:base_url + '/transport/bundle/conveyWays',
			  errorReason: true
			},function(res){
				var html="";
				$(res.conveyWays).each(function(i,item){
				    html+='<li value="'+item.wayName+'">'+item.wayName+'</li>';
				});
				$(".selectUl").empty().append(html);
				$(".selectInput,.tips_type").click(function(e){
					if (e.stopPropagation){ //支持W3C标准 doc
						e.stopPropagation();
					}else{ //IE8及以下浏览器
						e.cancelBubble = true;
					}
					$(".selectUl").show();
				});	
				var obj=$(".selectUl");
				obj.find("li").click(function(){
					vm.checkinfo.ysfs=$(this).text();
					$(this).parent("ul").hide();
				});

				$(document).click(function(){
					obj.hide();
				});
		  });  	
		},
		//发站公司
		fazhanList: function(){
				var html="";
				$(res.conveyWays).each(function(i,item){
				    html+='<li value="'+item.wayName+'">'+item.wayName+'</li>';
				});
				$(".fazhanUI").empty().append(html);
				$(".selectInput_fazhan,.tips_type_fazhan").click(function(e){
					if (e.stopPropagation){ //支持W3C标准 doc
						e.stopPropagation();
					}else{ //IE8及以下浏览器
						e.cancelBubble = true;
					}
					$(".fazhanUI").show();
				});	
				var obj=$(".fazhanUI");
				obj.find("li").click(function(){
					var _target = $(this).parent("ul").siblings(".selectInput_fazhan");
					vm.checkinfo.ysfs=$(this).text()
					//_target.val($(this).text());
					$(this).parent("ul").hide();		
				});
				$(document).click(function(){
					obj.hide();
				});
		},
		_tableRender: function(data){
			layui.use('table', function(){
				vm.table$ = layui.table;
				vm.table$.render({
					elem: '#tableList',
					data: data,
					cols: [[
						{type:'checkbox', fixed: true},
						{field: 'chxh', title: '车厢号'},
						{field: 'yshhm', title: '运输号码',width: 180},
					    {field: 'daozhanHwyd', title: '目的站'},
					    {field: 'dzshhdHwyd', title: '到站网点'},
					    {field: 'ysfs', title: '运输方式'},
					    {field: 'pinming', title: '品名'},
					    {field: 'jianshuHwydxz', title: '起票件数'},
					    {field: 'zhl', title: '重量'},
					    {field: 'tiji', title: '体积'},
					    {field: 'shhrmch', title: '收货人名称',width: 300},
					    {field: 'shhrlxdh', title: '收货人电话', width: 120},
					    {field: 'ziti', title: '提货方式'},
					    {field: 'beizhu', title: '记事'},
					    {field: 'vhdfk', title: '到付金额'},
					    {field: 'vDsk', title: '代收款'},
					    {field: 'ywlx', title: '业务类型'},
					    {field: 'tzfhzt', title: '等托运人指令放货', width: 140},
					    {field: 'yxztHwyd', title: '运行状态'},
					    {field: 'ydxzh', title: '细则号'},
					    {field: 'fazhanHwyd', title: '始发站'},
					    {field: 'shhdHwyd', title: '发站网点'},                
					    {field: 'fhshj', title: '发货时间', width: 150},
					    {field: 'fazhan', title: '装车发站'},
					    {field: 'daozhan', title: '装车到站'},
					    {field: 'zhchrq', title: '装车日期', width: 105},
					    {field: 'fhdwmch', title: '发货单位',width: 300},
					    {field: 'jianshu', title: '装车件数'},
					    {field: 'shhryb', title: '收货人手机', width: 120},
					    {field: 'yiti', title: '已提'},
					    {field: 'xh', title: '型号'},
					    {field: 'hyy', title: '货运员'},
					    {field: 'zhipiao2', title: '制票员'},
					    {field: 'ydzh', title: '是否到站'},
					    {field: 'tbjeHwydxz', title: '投保金额'},
					    {field: 'grid', title: '清单录入人'},
					    {field: 'lrsj', title: '录入时间', width: 150},
					    {field: 'dhgrid', title: '到货录入人'},
					    {field: 'dhsj', title: '到货时间', width: 150},
					    {field: 'vhjje', title: '合计金额'},
					    {field: 'isfd', title: '是否返单'},
					    {field: 'jffsHwydxz', title: '计费方式'},
					    {field: 'tijiCalc', title: '计费重量'},
					    {field: 'zhlCalc', title: '计费体积'},
					    {field: 'pszhsh', title: '派送指示'},
					    {field: 'ydts', title: '运到天数'},
					    {field: 'shhrdzh', title: '收货人地址',width: 300},
					    {field: 'lrtime', title: '签收录入时间', width: 150},
					    {field: 'isTiShi', title: '标记'},
					    {field: 'isKaiyun', title: '快运标识'},
					    {field: 'isGreenchanel', title: '绿色通道'}

					]],
					limit: 10,
					cellMinWidth: 100,
					limits: [10],
					id:"demoList",
					done: function(res, curr, count){
						/*console.log(res);//当前页数据
						console.log(curr);//当前页
						console.log(count);//总共条数*/
						vm._tableOn(res.data);

					},
					page: true
				})
			})
		},
		_tableOn: function(res){
			vm.table$.on('checkbox(table-data)',function(obj){
				/* 
				 * console.log(obj.checked); //当前是否选中状态
				 * console.log(obj.data); //选中行的相关数据
				 * console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
				 */
				if(obj.type == 'all'){
					if(obj.checked){
						$.each(res, function(i,a){
							if(vm.tihuoListXuhao.indexOf(a.xuhao) == -1){
								vm.tihuoListXuhao.push(a.xuhao);
								vm.tihuoListData.push(a);
							}
						});
						vm.printData = vm._d;/*得到所有打印数据*/
					}else{
						vm.printData = [];
						$.each(res, function(i,a){
							var index = vm.tihuoListXuhao.indexOf(a.xuhao);
							if(index > -1){
								vm.tihuoListXuhao.splice(index, 1);
								vm.tihuoListData.splice(index, 1);
							}
						})
					}
					
				}else{
					if(obj.checked){
						vm.tihuoListXuhao.push(obj.data.xuhao);
						vm.tihuoListData.push(obj.data);
						vm.printData.push(obj.data);
					}else{
						var index = vm.tihuoListXuhao.indexOf(obj.data.xuhao);
						if(index > -1){
							vm.tihuoListXuhao.splice(index, 1);
							vm.tihuoListData.splice(index, 1);
							vm.printData.splice(index, 1);
						}
					}
					
				}
			})
		},
		_SignHandler: function(config){
			var _self = this;                                                    
			if(vm.tihuoListXuhao.length > 0){
				if(config.obj == '.receive-bill'){//生成提货单
					if(vm.tihuoListData[0].ziti == '送货上门'){
						var comfirm = layer.open({
							content:'此运单号提货方式为送货上门，<br/>是否要更换为自提？',
							btn:['确定','取消'],
							yes: function(){
								vm._signAjax(config);
								layer.close(comfirm);
							}
						})
					}else{
						vm._signAjax(config);
					}
				}else{
					if(vm.tihuoListData[0].ziti == '自提'){
						var comfirm = layer.open({
							content:'此运单号提货方式为自提，<br/>是否要更换为送货上门？',
							btn:['确定','取消'],
							btnAlign: 'c',
							yes: function(){
								vm._signAjax(config);
								layer.close(comfirm);
							}
						})
					}else{
						vm._signAjax(config);
					}
				}
				
			}else{
				layer.msg('请选择至少一条信息！',{anim:6,icon:5});
			}
		},
		_signAjax: function(config){
			var _self = this;
			EasyAjax.ajax_Post_Json({
			dataType: 'json',
            url: config.url,
            contentType: "application/json",
            data: JSON.stringify(this.tihuoListXuhao),
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
			var sendbill = _self.$refs.sendbill;
			sendbill.sendinfos = {};
			$('.send-bill input').removeAttr('readonly');
			if(data.beizhu){
				_self.beizhus = data.beizhu;
			};
			if(data.compname){
				_self.compname = data.compname;
			};
			if(data.fhdh){
				sendbill.sendinfos.fhdh = data.fhdh;
			};
			if(data.shlxr){
				sendbill.sendinfos.shlxr = data.shlxr;
			};
			if(data.shdh){
				sendbill.sendinfos.shdh = data.shdh;
			};
			$(".printKpr").text(data.kpr);
			if(data.cangwei){
				$('.inputborder').show();
				$('.print-text').hide();
				$.each(_self.tihuoListData,function(i,a){
					a.cangwei = data.cangwei;
				})
			}
			vm._printClick(config.obj);
		});
		},
		_ajaxRequest: function(){
			vm.$set(vm.checkinfo,'loadingDateBegin',$('#cardatestart').val());
			vm.$set(vm.checkinfo,'loadingDateEnd',$('#cardateend').val());
//			if(!vm.checkinfo.fazhan || vm.checkinfo.fazhan==''){//装车发站必填
//				layer.msg('装车发站必填');
//				return false;
//			}
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
				vm.tihuoListXuhao = [];
				vm.tihuoList = [];
				vm.tihuoListData = [];
				layer.close(loading);
				$.each(data.message,function(i,a){
					vm._filedChange(a);
				});
				vm._d = data.message;
				vm.importDta = data.message;
				vm._tableRender(data.message);
			});
		},
		_filedChange: function(a){
			if(a.ziti == 0){
				a.ziti = '自提';
			}else{
				a.ziti = '送货上门';
			};
			if(a.tzfhzt == 1){
				a.tzfhzt = '等托放货';
			}else if(a.tzfhzt == 2){
				a.tzfhzt = '已取消';
			}else{
				a.tzfhzt = '无';
			};
			if(a.isfd == 0){
				a.isfd = '不返单';
			}else{
				a.isfd = '返单';
			}
			if(a.ydzh == '1'){
				a.ydzh = '已到'
			}else{
				a.ydzh = '未到'
			};
			if(a.ywlx == 0){
				a.ywlx = '普件';
			}else if(a.ywlx == '-1'){
				a.ywlx = '慢件';
			}else if(a.ywlx == '1'){
				a.ywlx = '快件';
			}else if(a.ywlx == '2'){
				a.ywlx = '特快';
			};
			if(a.yiti == 1){
				a.yiti = '提货';
			}else if(a.yiti == 2){
				a.yiti = '送货';
			}else if(a.yiti == 3){
				a.yiti = '中转';
			}else if(a.yiti == 0){
				a.yiti = '特快';
			};
			if(a.jffsHwydxz == 0){
				a.jffsHwydxz = '重货';
			}else if(a.jffsHwydxz == 1){
				a.jffsHwydxz = '轻货';
			}else if(a.jffsHwydxz == 2){
				a.jffsHwydxz = '按件';
			};
			
			if(a.zhchrq != null){
				a.zhchrq = formatDate(new Date(a.zhchrq),'本月年月日');
			};
			if(a.dhsj != null){
				a.dhsj = formatDate(new Date(a.dhsj),'年月日时分');
			};
			if(a.lrsj != null){
				a.lrsj = formatDate(new Date(a.lrsj),'年月日时分');
			};
			if(a.lrtime != null){
				a.lrtime = formatDate(new Date(a.lrtime),'年月日时分');
			};
		},
		_printClick: function(obj){
			this.$refs.printList.printclick = 0;
			var area$ = ['90%', '90%'];
			if(obj == '.send-bill'){
				area$ = ['90%','90%'];
			}else{
				$('.receive-bill input').removeAttr('readonly');
				$('.inputborder').show();
				$('.print-text').hide();
			};
			if(obj == '.detailed-list'){
				if(vm.printData.length == 0){
					layer.msg('至少选择一条数据打印');
					return false;
				}
			};
			this.layer$ = layer.open({
				type: 1,
				title: '  ',
				area: area$,
				content: $(obj),
				closeBtn:0
			});	
			vm._doComponentsPrint(obj)/*解决第一次打印空白的bug*/
		},
		
        /*打印客户标签*/	
		_printLabel:function(obj){
			if(obj == '.detailed-list'){
				if(vm.printData.length == 0){
					layer.msg('请选择一条数据打印');
				}else if(vm.printData.length > 1){
					layer.msg('每次只能选择一条数据打印客户标签');
				}else if(vm.printData.length == 1){
					var ydbhid = vm.printData[0].yshhm;
					var arr = ydbhid.split('-');
					ydbhid = arr[1];
					var width=$(document).width();
					$("body").css ("overflow-y","hidden");
					$("body").width(width);
					layer.open({
						  type: 2,
						  title: "货物运单（运单号:"+ydbhid+"）",
						  closeBtn: 1, //显示关闭按钮
						  shade: [0],
						  area: ['90%', '90%'],
						  anim: 2,
						  content: [base_url + "/transport/convey/printLabel?ydbhid="+ydbhid, 'yes'], //iframe的url，no代表不显示滚动条
						  end: function () {
							  $("body").css('overflow-y','auto');
				            }
						});
				}
			};
			
		},
		/*打印70*90客户标签*/	
		_printLabelBig:function(obj){
			if(obj == '.detailed-list'){
				if(vm.printData.length == 0){
					layer.msg('请选择一条数据打印');
				}else if(vm.printData.length > 1){
					layer.msg('每次只能选择一条数据打印客户标签');
				}else if(vm.printData.length == 1){
					var ydbhid = vm.printData[0].yshhm;
					var arr = ydbhid.split('-');
					ydbhid = arr[1];
					var width=$(document).width();
					$("body").css ("overflow-y","hidden");
					$("body").width(width);
					layer.open({
						  type: 2,
						  title: "货物运单（运单号:"+ydbhid+"）",
						  closeBtn: 1, //显示关闭按钮
						  shade: [0],
						  area: ['90%', '90%'],
						  anim: 2,
						  content: [base_url + "/transport/convey/printLabelBig?ydbhid="+ydbhid, 'yes'], //iframe的url，no代表不显示滚动条
						  end: function () {
							  $("body").css('overflow-y','auto');
				            }
						});
				}
			};
			
		},
		/* 生成分理数据  by  wyp   2018/01/22  */
		_printReceipt:function(){
			var len=$(".layui-table-view").length;
			if(len<1){
				layer.msg("请先查询运单编号！");
				return ;
			}
					
			var dataLength=0;
			var dataInf,
			    ydbhid="",
			    ydzh="",
				ydxzh="",
				hdfk="",
				dsk="";
				xuhao = "";
			//监听表格
			layui.use('table', function(){
				  var table = layui.table;				  
				  var $ = layui.$, active = {
				    getCheckData: function(){ //获取选中数据
				      var checkStatus = table.checkStatus('demoList')
				      ,data = checkStatus.data;
				      dataLength=data.length;
				      $.each(data, function (i, item){
				    	  ydbhid=item.ydbhid;
				    	  if(item.ydzh=="已到"){
				    		  item.ydzh=1;
				    	  }else{
				    		  item.ydzh=0;
				    	  }
				    	  ydzh=item.ydzh;
		    			  ydxzh=item.ydxzh;
		    			  hdfk=item.vhdfk;
		    			  dsk=item.vdsk;
		    			  xuhao = item.xuhao;
			           });     
				    }
				  };
				  active.getCheckData();
			});
			
			if(dataLength>0){
				if(dataLength==1){
					var baseValue={
							ydbhid:ydbhid,
					    	  ydzh:ydzh,
			    			  ydxzh:ydxzh,
			    			  hdfk:hdfk,
			    			  dsk:dsk,
			    			  xuhao:xuhao
					   }
					EasyAjax.ajax_Post_Json({
						  dataType: 'json',
						  url:base_url + '/transport/financialReceipts/collectMoney/search',
						  data:JSON.stringify(baseValue),
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
								if(res.resultInfo.isGenerate==1){
									layer.msg('该订单已经生成分理收据', {time: 1000}, function(){
									layer.open({
											  type: 2,
											  title: false,
											  closeBtn: 1, //显示关闭按钮
											  shade: 0.8,
											  area: ['90%', '95%'],
											  content: [base_url + "/transport/financialReceipts/collectMoney/?ydbhid="+ydbhid+"&ydzh="+ydzh+"&ydxzh="+ydxzh+"&hdfk="+hdfk+"&dsk="+dsk + "&xuhao=" + xuhao, 'yes'] //iframe的url，no代表不显示滚动条
											});
										});
									layuiIndex = layer.index;
								}else{	
									layer.open({
										  type: 2,
										  title: false,
										  closeBtn: 1, //显示关闭按钮
										  shade: 0.8,
										  area: ['90%', '95%'],
										  content: [base_url + "/transport/financialReceipts/collectMoney/?ydbhid="+ydbhid+"&ydzh="+ydzh+"&ydxzh="+ydxzh+"&hdfk="+hdfk+"&dsk="+dsk + "&xuhao=" + xuhao, 'yes'] //iframe的url，no代表不显示滚动条
										});
									layuiIndex = layer.index;
								}
							}else{
								layer.msg(res.reason);
							}
						});
				}else{
					layer.msg("只能选择一条运单号");
				}
			}else{
				layer.msg("请至少选择一条运单号");
			}	

		},
		_doComponentsPrint: function(obj){
			this.$refs.printList._printPage(obj);
		},/*导出*/
		_importData: function(){
			if(vm.importDta.length == 0){
				layer.msg('暂无数据，请先查询');
			}else{
				window.location.href = base_url + '/transport/bundleArrive/download/bundleArrive';
			}
		},printMoneyWithoutBorder:function(obj){ //打印运输受理单套打
			console.log("运输受理单套打");
			 if(obj == '.detailed-list'){
				if(vm.printData.length == 0){
					layer.msg('请选择一条数据打印');
				}else if(vm.printData.length > 1){
					layer.msg('每次只能选择一条数据打印客户标签');
				}else if(vm.printData.length == 1){
					var ydbhid = vm.printData[0].yshhm;
					var transportId = ydbhid.substring(7,ydbhid.length);
					var submitData = {year: '', wealthNo: '', companyCode: '',transportCode:transportId};
					EasyAjax.ajax_Post_Json({
		        		dataType: 'json',
		        		url: base_url + '/transport/finance/searchWealthPrint',
		        		data : JSON.stringify(submitData),
		        	    contentType : 'application/json',
		        		errorReason: true
		        	},function(data){
		        		var dataResult = data.entity;
			        	tabledata = data.entity; /*为保存红票财凭号*/
			        	if(!dataResult){
			        		layer.msg(data.reason);
			        	}else{
			        		sessionStorage.setItem('conveyPrintInf',JSON.stringify(tabledata));
				        	layer.open({
							  type: 2,
							  title: "运输受理单",
							  closeBtn: 1, //显示关闭按钮
							  shade: [0],
							  area: ['90%', '90%'],
							  //offset: 'rb', //右下角弹出
							 // time: 2000, //2秒后自动关闭
							  anim: 2,
							  content: [ base_url + "/transport/convey/carrayPrint", 'yes'], //iframe的url，no代表不显示滚动条
							  end: function () {
								  $("body").css ('overflow-y','auto');
					            }
							});
			        	}
		        	});
				}
			 }		
		},printMoneyWithBorder:function(obj){ //打印运输受理单A4
			console.log("打印运输受理单A4");
			 if(obj == '.detailed-list'){
				if(vm.printData.length == 0){
					layer.msg('请选择一条数据打印');
				}else if(vm.printData.length > 1){
					layer.msg('每次只能选择一条数据打印客户标签');
				}else if(vm.printData.length == 1){
					var ydbhid = vm.printData[0].yshhm;
					var transportId = ydbhid.substring(7,ydbhid.length);
					var submitData = {year: '', wealthNo: '', companyCode: '',transportCode:transportId};
				 EasyAjax.ajax_Post_Json({
			    		dataType: 'json',
			            url: base_url + '/transport/finance/searchWealthPrint',
			            data: JSON.stringify(submitData),
		        		errorReason: true,
			            beforeHandler: function(){
			           	 loading = layer.load(0,{
				            		shade: [0.5,'#fff']
				            	});
			            },
			            afterHandler: function(){
			           	 layer.close(loading);
			            }
			        },
			        function (data) {
			        	var dataResult = data.entity;
			        	tabledata = data.entity; /*为保存红票财凭号*/
			        	if(!dataResult){
			        		layer.msg(data.reason);
			        	}else{
			        		sessionStorage.setItem('conveyPrintInf',JSON.stringify(tabledata));
			        		layer.open({
								  type: 2,
								  title: "运输受理单",
								  closeBtn: 1, //显示关闭按钮
								  shade: [0],
								  area: ['90%', '90%'],
								  //offset: 'rb', //右下角弹出
								 // time: 2000, //2秒后自动关闭
								  anim: 2,
								  content: [ base_url + "/transport/convey/carrayPrintA4", 'yes'], //iframe的url，no代表不显示滚动条
								  end: function () {
									  $("body").css ('overflow-y','auto');
						            }
								});
			        	}
			        });
				} 
			 }	
		}
	},
	mounted: function(){
		this._getCurrTime();
		this._initDOM();
		//this.checkinfo.fazhan=companyName;
	},
	created: function(){
		this._removeForm();
		
	},
	components: {
		'detailed-list': DetailedList,
		'receipt-bill': receiptBill,
		'send-bill': sendBill,
		'company-select': companySelect
	}
})
