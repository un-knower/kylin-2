<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!-- <span id='now-date'>{{curr_time}}</span> -->
<script type="text/x-template" id='detailedList'>
		<div class='print-page'>
			<div class='list-top'>
				<div class='head-left head-tag imgAndtime' style='position: absolute;'>
					<img alt="logo" src="${ctx_static}/common/images/logo-min.png">
					
				</div>
				<h2 class='head-middle head-tag title'>{{title}}</h2>
				<div class='head-right head-tag' style='position: absolute;right: 0;top: 15px;'>
					<h5 class='head-tag'>{{identifier}}</h5>
					<span>第<i>{{first_page}}</i>页</span>
					&nbsp;
					
				</div>
			</div>
			<div class='zhuangche-sum'>
				<p class='head-tag zhuangche'>装车日期：{{curr_date}}</p>
				<p class='head-tag zhuangche'>装车发站：{{start_station}}</p>
				<p class='head-tag zhuangche'>装车到站：{{end_station}}</p>
			</div>
			<div>
				<table class='list-table'>
					<colgroup>
						<col width='6%' />
						<col width='9%' />
						<col width='6%' />
						<col width='6%' />
						<col width='4%' />
						<col width='4%' />
						<col width='4%' />
						<col width='4%' />
						<col width='5%' />
						<col width='8%' />
						<col width='4%' />
						<col width='5%' />
						<col width='4%' />
						<col width='6%' />
						<col width='4%' />
						<col width='6%' />
						<col width='6%' />
					</colgroup>
					<thead>
						<tr>
							<th>车厢号</th>
							<th>运输号码</th>
							<th>目的站</th>
							<th>到站网点</th>	
							<th>品名</th>
							<th>起票件数</th>
							<th>重量</th>
							<th>体积</th>
							<th>收货人</th>	
							<th>收货电话</th>
							<th>提货</th>
							<th>记事</th>
							<th>备注</th>
							<th>货付/代收</th>	
							<th>业务</th>
							<th>等托放货</th>
							<th>运行状态</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for='(obj,index) in tabledata'>
							<td>{{obj.chxh}}</td>
							<td>{{obj.yshhm}}</td>
							<td>{{obj.daozhanHwyd}}</td>
							<td>{{obj.dzshhdHwyd}}</td>	
							<td>{{obj.pinming}}</td>
							<td>{{obj.jianshuHwydxz}}</td>
							<td>{{obj.zhl}}</td>
							<td>{{obj.tiji}}</td>
							<td>{{obj.shhrmch}}</td>
							<td v-if='obj.shhryb != ""'>{{obj.shhryb}}</td>
							<td v-else>{{obj.shhrlxdh}}</td>
							<td>{{obj.ziti}}</td>
							<td>{{obj.beizhu}}</td>
							<td>{{obj.zhipiao}}</td>
							<td>{{obj.vhdfk}}/{{obj.vdsk}}</td>
							<td>{{obj.ywlx}}</td>
							<td>{{obj.tzfhzt}}</td>
							<td>{{obj.yxztHwyd}}</td>
						</tr>
						<tr>
							<td>合计</td>
							<td>共计{{totalData.count}}票货</td>
							<td colspan='3'></td>	
							<td>{{totalData.qipiao}}</td>
							<td>{{totalData.zhl}}</td>
							<td>{{totalData.tiji}}</td>
							<td colspan='5'></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class='persons'>
				<div class='head-tag footer-tag'>单证员：<span>{{username}}</span></div>
				<div class='head-tag footer-tag margin-setting'>审核：</div>
				<div class='head-tag footer-tag margin-setting'>操作员：</div>
			</div>
			<div class='btn-operation list-top list-footer no-print'>
				<button class='layui-btn layui-btn-normal no-print' @click='_printPage(".detailed-list")'>打印</button>
    			<button class='layui-btn layui-btn-primary no-print' @click='_closeLayer'>关闭</button>
			</div>
		</div>
</script>
<script type='text/javascript'>
/* <span>共<i>{{curr_page}}</i>页</span> */
/*注册一个生成清单的组件,注册组件在根实例化之前
 * 1、货车装载清单
 */
var DetailedList = Vue.extend({
	template: '#detailedList',
	props: ['title','identifier','curr_time','tabledata','tablelist','username'],
	data: function(){
		return {
			first_page: 1,
			curr_page: 1,
			curr_date: '',
			start_station: '',
			end_station: '',
			f: '',
			printclick: 0,
			totalData:{}
		}
	},
	created: function(){
		this.curr_date = getCurrTime ();
		this.curr_date = this.curr_date.split(' ')[0];
		/* this.start_station = this.$root. */
	},
	methods: {
		_closeLayer: function(){
			layer.close(this.$root.layer$);
		},
		_printPage: function(obj){
			this.printclick ++;
			var stylecss = '<style type="text/css">@import url("'+ctx_static+'/transport/excelRead/css/layui.css");'
			 +'@import url("'+ctx_static+'/publicFile/css/public.css");'
			 +'@import url("'+ctx_static+'/transport/arrivalloding/css/arrivalloding.css")</style>';
			this.f = document.getElementById('printf');
            this.f.contentDocument.write(stylecss+$(obj).html());
	        this.f.contentDocument.close();/*清除上一次打印内容*/
	        var _f = this.f;
	           if(this.printclick == 1){
	            	window.close();
	            }else{
	            	var printlayer = layer.open({
	            		content: '打印时最好设置布局为横向',
	            		btn: ['确定','取消'],
	            		yes: function(){
	            			_f.contentWindow.print();
	            			layer.close(printlayer);
	            		},
	            		btn2:function(){
	            			layer.close(printlayer);
	            		}
	            	})
	            } 
		},
		_dataRender: function(){
			this.totalData.count = this.tabledata.length;
			this.totalData.qipiao = 0;
			this.totalData.zhl = 0;
			this.totalData.tiji = 0;
			this.totalData.vDsk = 0;
			for(var i=0,list=this.tabledata.length;i<list;i++){
				if(!this.tabledata[i].jianshuHwydxz){
					this.tabledata[i].jianshuHwydxz = 0;
				}
				if(!this.tabledata[i].zhl){
					this.tabledata[i].zhl = 0;
				}
				if(!this.tabledata[i].tiji){
					this.tabledata[i].tiji = 0;
				}
				if(!this.tabledata[i].vDsk){
					this.tabledata[i].vDsk = 0;
				}
				this.totalData.qipiao += Math.round(Number(this.tabledata[i].jianshuHwydxz));
				this.totalData.zhl += Math.round(Number(this.tabledata[i].zhl)*1000)/1000;
				this.totalData.tiji += Math.round(Number(this.tabledata[i].tiji)*1000)/1000;
				this.totalData.vDsk += Math.round(Number(this.tabledata[i].vDsk)*1000)/1000;
			}
			this.totalData.qipiao = Math.round(this.totalData.qipiao);
			this.totalData.zhl = Math.round(this.totalData.zhl*1000)/1000;
			this.totalData.tiji = Math.round(this.totalData.tiji*1000)/1000;
			this.totalData.vDsk = Math.round(this.totalData.vDsk*1000)/1000;
			if(this.totalData.vDsk == null){
				this.totalData.vDsk = 0;
			}
		}
	},
	watch: {
		'tablelist': function(){
			if(this.tablelist[0] != undefined){
				this.start_station = this.tablelist[0].fazhan;
				this.end_station = this.tablelist[0].daozhan;
			}
		},
		'tabledata': function(){
			this._dataRender();
		}
	}
	
	
});
</script>
<script type="text/x-template" id='receipt-bill'>
		<div class='print-page printReceipt'>
			<form class='layui-form check-condition' style='width: 100%;'>
			<div class='list-top'>
				<div class='head-tag imglogo'>
					<img alt="logo" src="${ctx_static}/common/images/logo-min.png">
				</div>
				<h2 class='head-middle head-tag title recipt-title'>{{title}}</h2>
				<div class='head-right head-tag'>
					<h5 class='head-tag'>{{identifier}}</h5>
				</div>
			</div>
			<div class='list-table tihuoNum'>
				<p class='head-tag list-info'>提货单号：{{tihuo_Num}}</p>
				<p class='head-tag list-info curr_time'>{{curr_time}}</p>
				<p class='head-tag list-info curr_time idNumber'>收据号&nbsp;NO.{{id_Num}}</p>
			</div>
			<div class='input-info'>
				<div class='layui-form-item layItem'>
					<div class='input-div'>
						<label class='layui-form-label print-label' style='width:80px'>提货人名称：</label>
						<div class='layui-input-block input-tag print-div'>
							<span class='print-text'>{{saveInfo.thrmch}}</span>
							<input v-model='saveInfo.thrmch' lay-verify='required' type='text' class='modifyStatus inputborder layui-input recipte-input' lay-filter='required' />
						</div>
					</div>
					<div class='input-div'>
						<label class='layui-form-label marginDiv print-label' style='width:90px'>提货人身份证：</label>
						<div class='layui-input-block  input-tag print-div'>
							<span class='print-text'>{{saveInfo.thrsfzhm}}</span>
							<input v-model='saveInfo.thrsfzhm' lay-verify='required|identity' type='text' class='modifyStatus inputborder layui-input recipte-input' lay-filter='required' />
						</div>
					</div>
					<div class='input-div'>
						<label class='layui-form-label marginDiv print-label' style='width:90px'>收货人身份证：</label>
						<div class='layui-input-block input-tag print-div' >
							<span class='print-text'>{{saveInfo.shhrsfzhm}}</span>
							<input v-model='saveInfo.shhrsfzhm' lay-verify='' type='text' class='modifyStatus inputborder layui-input recipte-input'/>
						</div>
					</div>
				</div>
			</div>
			<div>
				<table class='list-table'>
					<colgroup>
						<col width='15%' style="font-weight: normal"/>
                        <col width='10%'/>
                        <col width='8%'/>
                        <col width='10%'/>
                        <col width='10%'/>
                        <col width='15%'/>
                        <col width='15%'/>
                        <col width='20%'/>
					</colgroup>
					<thead>
						<tr>
							<th>运单编号</th>
							<th>品名</th>
							<th>件数</th>
							<th>重量</th>	
							<th>体积</th>
							<th>收货人</th>
							<th>仓位/操作员</th>
							<th colspan='2'>备注</th>	
						</tr>
					</thead>
					<tbody>
						<tr v-for='(obj,index) in tablelists'>
							<td>{{obj.ydbhid}}</td>
							<td>{{obj.pinming}}</td>
							<td>{{obj.jianshu}}</td>
							<td>{{obj.zhl}}</td>	
							<td>{{obj.tiji}}</td>
							<td>{{obj.shhrmch}}</td>
							<td style='position: relative;'>
								<span class='print-text' style='left: 5px;'>{{obj.cangwei}}</span>
								<input v-model='obj.cangwei' type='text' class='modifyStatus inputborder borderNone layui-input'/>
							</td>
							<td colspan='2'>
								<span class='print-text' style='left: 5px;'>{{obj.beizhu}}</span>
								<input v-model='obj.beizhu' type='text' class='modifyStatus inputborder borderNone layui-input'/>
							</td>
						</tr>
						<tr>
							<td>合计</td>
							<td>{{pingmingtotal}}</td>
							<td>{{total.jianshutotal}}</td>	
							<td>{{total.zhl}}</td>
							<td>{{total.tiji}}</td>
							<td></td>
							<td></td>
							<td colspan='2'></td>
						</tr>
						<tr>
							<td>备注</td>
							<td colspan='8' style='position: relative;'>
								<span class='print-text' style='left: 2px;'>{{saveInfo.beizhu2}}</span>
								<input v-model='saveInfo.beizhu2' type='text' class='modifyStatus inputborder borderNone layui-input'/>
							</td>
						</tr>
						<tr>
							<td>提货车号</td>
							<td colspan='2'></td>
							<td>提货时间</td>	
							<td></td>
							<td>班组</td>
							<td></td>
							<td>仓管员</td>
							<td><span style='opacity: 0'>凌红竹</span></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class='list-table'>
				<div class='head-tag footer-tag' style="width: 41%;">
					<label class='layui-form-label print-label'>开票人：</label>
					<div class='layui-input-block kpr-input input-tag print-div printKpr' >
					 {{saveInfo.kpr}}
                    </div>
				</div>
				<div class='head-tag footer-tag' style='margin-bottom: 5px;'>发货员</div>
				<div class='head-tag footer-tag margin-setting' style='margin-bottom: 5px;'>客户签字</div>
				<div class='tips-info list-table'>货物交付时，如无提出异议，视为完好交付</div>
			</div>
			<div class='list-table'>
				<span>备注：</span>
				<ol>
					<li>1.本提货单需加盖提货章和开票人本人签名后有效</li>
					<li>2.本提货单当日有效</li>
				</ol>
			</div>
			<div class='btn-operation list-top list-footer no-print'>
				<button v-show='isSave' class='layui-btn layui-btn-warm no-print' lay-submit lay-filter='save'>保存</button>
				<button v-show='!isSave' class='layui-btn layui-btn-disabled no-print' type='button'>保存</button>
				<button v-show='isSave' class='layui-btn layui-btn-disabled no-print' type='button'>打印</button>
				<button v-show='!isSave' class='layui-btn no-print' type='button' @click='_printHandler'>打印</button>
				<button class='layui-btn layui-btn-primary no-print' type='button' @click='_outLayer'>退出</button>
			</div>
			</form>

		</div>
</script>
<script type='text/javascript'>
/* <button type='out' class='layui-btn layui-btn-primary no-print' lay-filter='out'>退出</button> */
/*
 * 2、远成集团提货签收单
 * */
var receiptBill = Vue.extend({
	template: '#receipt-bill',
	props: ['curr_time','tablelist','xuhaos','username'],
	data: function(){
		return {
			title: '远成集团提货签收单',
			identifier: 'YY05-008',
			total:{},
			saveInfo: {
				list: [],
				thqshdid: ''
			},
			isSave: true,
			tihuo_Num: '',
			otherObj: '',
			isReceipt: false/*是否是提货单查询页面*/,
			tablelists: [],
			id_Num: ''
		}
	},
	methods: {
		_outLayer: function(){
				this.tihuo_Num = '';
				this.saveInfo = {
					list: [],
					thqshdid: ''
				};
				this.tablelists = JSON.parse(JSON.stringify(this.tablelist));/*深度拷贝*/
				this.isSave = true;              
			layer.close(this.$root.layer$);
			if(this.isReceipt){
				this.$root.clearTime();
			}
		},
		_dataRender: function(){
			this.total.jianshutotal = 0;
			this.total.zhl = 0;
			this.total.tiji = 0;
			for(var i=0,list=this.tablelists.length;i<list;i++){
				if(!this.tablelists[i].jianshu){
					this.tablelists[i].jianshu = 0
				}
				if(!this.tablelists[i].zhl){
					this.tablelists[i].zhl = 0
				}
				if(!this.tablelists[i].tiji){
					this.tablelists[i].tiji = 0
				}
				this.total.jianshutotal += Number(this.tablelists[i].jianshu);
				this.total.zhl += Number(this.tablelists[i].zhl);
				this.total.tiji += Number(this.tablelists[i].tiji);
			}
		},
		_saveHandler: function(){
			var _self = this;
			layui.use('form',function(){
				var form = layui.form;
				form.verify({
				    unId: function(value){
				    	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
				      if(value.length != 0){
				    	  if(!reg.test(value)){
				    		  return '请输入正确的身份证号码';
				    	  }
				      }
				    }
				  });
				form.on('submit(save)', function(){
					_self._saveAjax();
					return false;
				});
				form.on('submit(out)', function(){
					_self._outLayer();
					return false;
				});
			})
		},
		_saveAjax: function(){
			var _self = this;
			this.saveInfo.list = [];
			$.each(this.tablelists,function(i,a){
				_self.saveInfo.list.push({
					xuhao: a.xuhao,
					cangwei1: a.cangwei
				})
			})
			_self.saveInfo.shijian = _self.curr_time;
			if(!_self.isReceipt){
				var url = base_url + '/transport/bundleArrive/bill/delivery/save';
			}else{
				var url = base_url + '/transport/bundleArrive/bill/delivery/update'
			}
			var _self = this;
			EasyAjax.ajax_Post_Json({
				dataType: 'json',
				contentType: "application/json",
                url: url,
                data: JSON.stringify(this.saveInfo),
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
					_self.$root.clearTime();
					_self.isSave = false;
					_self.tihuo_Num = data.thqshdid;
					if(data.id == null || data.id == ''){
						$('.idNumber').hide();
					}else{
						$('.idNumber').show();
						_self.id_Num = data.id;
					}
					if(!_self.isReceipt){
						$('.receive-bill input').attr('readonly',true);
						$('.inputborder').hide();
						$('.print-text').show();
					}
					layer.msg(data.reason+'，<br/>可以进行打印。',{icon:1});
			}); 
		},
		_printHandler: function(){
			this.$root._doComponentsPrint('.receive-bill');
		}
	},
	created: function(){
		this._saveHandler();
	},
	computed: {
		pingmingtotal: function(){
			return this.tablelists.length;
		}
	},
	watch: {
		'tablelist': function(){
			this.tablelists = JSON.parse(JSON.stringify(this.tablelist));/*给一个中间值将不会同步父组件的值*/
			this._dataRender();
			if(this.$root._componentModify){/*页面是否可以修改*/
				this.$nextTick(function(){
					this.$root._componentModify();
				})
			}
		}
	}
});
</script>
<script type="text/x-template" id='send-bill'>
		<div class='print-page'>
			<div  class='list-top'>
				<h2 class='head-middle head-tag title send-title'>{{title}}</h2>
			</div>
			<div class='tihuoNum'>
				<p class='head-tag list-info' id="compName" style='margin-left: 28%;'>公司：{{compname}}</p>
				<p class='head-tag list-info'>NO.{{NOnumber}}</p>
			</div>
			<div>
				<table class='list-table send-table'>
					<tbody>
						<tr>
							<td rowspan='12' class='danzh'>单证号保存栏</td>
							<td>运输号码</th>
							<td colspan='3'>{{saveinfos.yshhm}}</td>
							<td>业务类型</td>	
							<td colspan='2'>
								{{saveinfos.ywlx}}
							</td>
							<td style='width: 150px;'>单证员</td>
						</tr>
						<tr>
							<td>发货单位</td>
							<td colspan='3'>
								<input v-model='saveinfos.fhdwmch' type='text' class='borderNone layui-input'/>
							</td>
							<td>发货电话</td>	
							<td colspan='2'>
								<input v-model='sendinfos.fhdh' type='text' class='borderNone layui-input'/>
							</td>
							<td>{{username}}</td>
						</tr>
						<tr>
							<td>收货单位</td>
							<td colspan='3'>
								<input v-model='saveinfos.shhrmch' type='text' class='borderNone layui-input'/>
							</td>
							<td>收货人手机</td>	
							<td colspan='2'>
								<input v-model='saveinfos.shhryb' type='text' class='borderNone layui-input'/>
							</td>
							<td>开单时间</td>
						</tr>
						<tr>
							<td>收货地址</td>
							<td colspan='3'>
								<input v-model='saveinfos.shhrdzh' type='text' class='borderNone layui-input'/>
							</td>
							<td>收货人电话</td>	
							<td colspan='2'>
								<input v-model='saveinfos.shhrlxdh' type='text' class='borderNone layui-input'/>
							</td>
							<td>
								<input type='text' id='zhuangcheDate' class='layui-input'>
							</td>
						</tr>
						<tr>
							<td colspan='2'>货物名称</td>
							<td>车箱号</td>
							<td>件数</td>	
							<td>重量</td>
							<td>体积</td>
							<td>返单</td>
							<td>计划送货时间</td>
						</tr>
						<tr v-for='(obj,i) in tablelist'>
							<td colspan='2'>{{obj.pinming}}</td>
							<td>{{obj.chxh}}</td>
							<td>{{obj.jianshu}}</td>	
							<td>{{obj.zhl}}</td>
							<td>{{obj.tiji}}</td>
							<td>{{obj.isfd}}</td>
							<td><input id='jhshtime' type='text' class='layui-input'/></td>
						</tr>
						<tr>
							<td colspan='7' rowspan='4'></td>
							<td>仓库提货时间</td>
						</tr>
						<tr>
							<td><span style='opacity: 0'>凌红竹</span></td>
						</tr>
						<tr>
							<td>装卸班组</td>
						</tr>
						<tr>
							<td><span style='opacity: 0'>凌红竹</span></td>
						</tr>
						<tr>
							<td rowspan='2'>备注</td>
							<td rowspan='2' colspan='6'>{{beizhu}}</td>
							<td>仓管员签名</td>
						</tr>
						<tr>
							<td rowspan='1'><span style='opacity: 0'>凌红竹</span></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class='btn-operation list-top list-footer no-print'>
				<button v-show='isSave' class='layui-btn layui-btn-warm no-print' @click='_saveAjax'>保存</button>
				<button v-show='!isSave' class='layui-btn layui-btn-disabled no-print'>保存</button>
    			<button class='layui-btn layui-btn-primary no-print' @click='_outLayer'>退出</button>
			</div>
		</div>
	</script>
	<script type="text/javascript" >

	/* <button v-show='isSave' class='layui-btn layui-btn-disabled no-print'>打印</button>*/
	/*<button v-show='!isSave' class='layui-btn no-print' @click='_printHandler'>打印</button> */
	/*
	 * 3、送货签收单
	 * */
	var sendBill = Vue.extend({
		template: '#send-bill',
		props: ['tablelist','xuhaos','company','curr_time','username','beizhu','compname','sendinfo'],
		data: function(){
			return {
				title: '送货签收单',
				identifier: 'YY05-008',
				saveInfo: {
					xuhaos: []
				},
				isSave: true,
				saveinfos: {
					
				},
				sendinfos: {},
				laydate$: '',
				NOnumber: ''
				
			}
		},
		methods: {
			_initDom: function(){
				layui.use('laydate',function(){
					vm.laydate$ = layui.laydate;
					vm.laydate$.render({
						elem: '#zhuangcheDate',
						type: 'datetime'
					});
					vm.laydate$.render({
						elem: '#jhshtime',
						type: 'datetime'
					});
				})
				this.sendinfos = JSON.parse(JSON.stringify(this.sendinfo));
			},
			_outLayer: function(){
				this.NOnumber = '';
				if(this.tablelist[0] != undefined){
					this.saveinfos = this.tablelist[0];
				};
				this.isSave = true;
				/* this.sendinfos = {}; */
				$('#zhuangcheDate').val('');
				$('#jhshtime').val('');
				layer.close(this.$root.layer$);
			},
			_saveAjax: function(){
				this.saveInfo.xuhaos = this.xuhaos;
				this.saveInfo.ywlx = this.saveinfos.ywlx;
				this.saveInfo.hdwmch = this.saveinfos.fhdwmch;
				this.saveInfo.fhdwlxdh = this.sendinfos.fhdh;
				this.saveInfo.shhrmch = this.saveinfos.shhrmch;
				this.saveInfo.shlxr = this.saveinfos.shhryb;
				this.saveInfo.shhrdzh = this.saveinfos.shhrdzh;
				this.saveInfo.shhrlxdh = this.saveinfos.shhrlxdh;
				this.saveInfo.kdtime = $('#zhuangcheDate').val();
				this.saveInfo.jhshtime = $('#jhshtime').val();
				this.saveInfo.thtime = this.saveinfos.thtime;
				this.saveInfo.zxbz = this.saveinfos.zxbz;
				this.saveInfo.cgyqm = this.saveinfos.cgyqm;
				this.saveInfo.common = this.beizhu;
				var _self = this;
				EasyAjax.ajax_Post_Json({
					dataType: 'json',
					contentType: "application/json",
	                url: base_url + '/transport/bundleArrive/documents/delivery/save',
	                data: JSON.stringify(this.saveInfo),
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
						_self.isSave = false;
						_self.tihuo_Num = data.thqshdid;
						_self.NOnumber = data.id;
						$('.send-bill input').attr('readonly',true);
						layer.msg(data.reason,{icon: 1});
				});
			},
			_printHandler: function(){
				this.$root._doComponentsPrint('.send-bill');
			}
		},
		created: function(){
			this._initDom();
		},
		watch: {
			'tablelist': function(){
				if(this.tablelist[0] != undefined){
					this.saveinfos = JSON.parse(JSON.stringify(this.tablelist[0]));
				};
				this.$nextTick(function(){
					this._initDom();
				})

			}
			/* 'sendinfo': function(){
				this.sendinfos = JSON.parse(JSON.stringify(this.sendinfo));/*给一个中间值将不会同步父组件的值*/
			/* }  */
		}
	});
	</script>