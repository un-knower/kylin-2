/*
 * created by yl on 2018/5/03
 *  异常货运记录录入
 */

layui.use(['element', 'layer', 'form','laydate'], function(){
	var element = layui.element;
	var form = layui.form;
	element.init();
	form.render();
	
});
var maxDate = moment().format('YYYY-MM-DD');
var vm = new Vue({
	el:"#rrapp",
	data:{    //变量声明
		checkInfo:{},        //查询条件
		dataList:{},        //查询结果渲染
		isDetailShow:false,  //是否显示录入表格
		saveDetailData:{},         //保存录入详情数据
		detailData:[               //录入详情数据
			{
                'wayBillNum':'',
                'goodsName':'',
                'ticketsNum':'',
                'ds':'',
                'bjps':'',
                'ss':'',
                'ws':'',
                'ycsl':'',
                'estimatedLoss':'',
                'remark':''
            }
		]
	},
	watch:{
		dataList:function(){
			this.$nextTick(function(){
				$("#rrapp input").attr("autocomplete","off");
				layui.use(['layer', 'form','laydate'], function(){
					var laydate = layui.laydate;
					//var minDate = vm.dataList.zhchrq;
					//到货日期
					laydate.render({
						elem: '#arriveDate',
						type: 'datetime',
						max:maxDate
					});
					//发生日期
					laydate.render({
						elem: '#appendDate',
						type: 'datetime',
						max:maxDate
					});
				});
			});
		},
		//监控录入异常详情数据变化
		detailData:{
            handler:function(newVal){
            	vm.autoGetYcsl(); 
            	$("#rrapp input").attr("autocomplete","off");
            },      
            deep: true    
        }
	},
	created:function(){
		//录入查询
		this._inputQuery();
	},
	mounted: function(){
		$("#rrapp input").attr("autocomplete","off");
		this._inputSave();
	},
	methods:{
		//录入查询
		_inputQuery: function(){
			var _this = this;
			layui.use('form',function(){
				var form = layui.form;
				form.verify({
					//必填项
					needFill: function(value,item){ 
		        	  if(value == ""){
		        		  return $(item).attr("dataname")+'不能为空！';
		        	  }
			        },
					waybillNum: function(value,item){ 
					  var reg = /\b[a-z0-9A-Z]{10}\b|\b[a-z0-9A-Z]{12}\b/;
					  if(_this.isMany=="0"){
						  if(value == ""){
							  return '运单编号不能为空！';
						  }else{
							  if(!reg.test(value)){  
					            return '运单编号必须为10或12位数字或字母组合！';  
					          }
						  }
					  }
			        }
				});
				form.on('submit(inquire)', function(data){
					_this.checkInfo.waybillNum = data.field.waybillNum;
					_this.checkInfo.exceptionType = data.field.exceptionType;
					$.ajax({
						url:base_url + '/transport/saveFreightRecordselect',
						type:'post',
						dataType:'JSON',
						//async:false,
						contentType:'application/json',
						data:JSON.stringify(_this.checkInfo),
						beforeSend:function(){
							loading = layer.load(0,{
								shade:[0.5,'#fff']
							})
						},
						complete:function(){
							layer.close(loading);
						},
						success:function(data){
							if(data.resultCode=="200"){ 
								_this.isDetailShow = true;
								if(data.data.data.iType == "2"){
									data.data.data.ycType="提货";
								}else if(data.data.data.iType == "0"){
									data.data.data.ycType="干线";
								}else if(data.data.data.iType == "1"){
									data.data.data.ycType="配送";
								}
								_this.dataList = data.data.data;
								_this.dataList.currUser = userName;
								_this.dataList.company = userCompany;
							}else{
								layer.open({title:"提示信息",content:data.reason});
							}
						},
						error:function(data){
							console.log(data);
						}
					});
					return false;
				});
				
			});
		},
		// 点击新增一条记录
        addRecord:function(){
            var  obj={
                'wayBillNum':'',
                'goodsName':'',
                'ticketsNum':'',
                'ds':'',
                'bjps':'',
                'ss':'',
                'ws':'',
                'ycsl':'',
                'estimatedLoss':'',
                'remark':''
            };
            this.detailData.push(obj);
        },
        //点击删除一条记录
        delRecord:function(){
        	if($(".inputRow").length > 1){
                this.detailData.pop();
        	}else{
        		layer.msg("至少保留一条异常记录！")
        	}
        },
        //自动获取异常数量
        autoGetYcsl:function(){
        	var _this = this;
            for(var k in _this.detailData){
                _this.detailData[k]['ycsl'] = Number(_this.detailData[k]['ds'])+Number(_this.detailData[k]['bjps'])+Number(_this.detailData[k]['ss'])+Number(_this.detailData[k]['ws']);
            }
        },
		//录入详情保存
		_inputSave:function(){
			var _this = this;
			layui.use('form',function(){
				var form = layui.form;
				//保存详情的表单验证
				_this._saveVerify();
				form.on('submit(saveBtn)', function(data){
					if(!data.field.happenedTime || !data.field.arrivalTime){
						layer.msg("发生日期或到货日期不能为空！")
					}else{
						//获取录入详情数据并请求
						_this._luruDetail();
					}
					return false;
				});
			});
			
		},
		//获取录入详情数据并请求
		_luruDetail:function(){
			var _this = this;
			//发生地点、发生日期、到货时间
			var mustArr = [];
            $(".mustLu").each(function(index,elem){
            	var name = $(elem).attr('name');
				var value = $(elem).val();
				mustArr[name] = value;
            })
            for(var key in mustArr ){
            	_this.saveDetailData[key] = mustArr[key];
            }
            //录入异常详情数据
			var dataArr = _this.detailData;
			for(var key in dataArr ){
                for(var v in dataArr[key]){
               	 if(v == "ycsl"){
               		 delete dataArr[key][v];
               	 }
                }
            }
			_this.saveDetailData.freightRecordDetailDto = dataArr;
            //录入人、录入公司、车牌号、装车日期、供应商名称
       	    /*_this.saveDetailData.currUserName = userName;
       	    _this.saveDetailData.company = userCompany;*/
       	    _this.saveDetailData.train = _this.dataList.chxh;
       	    _this.saveDetailData.fchrq = _this.dataList.zhchrq;
       	    _this.saveDetailData.wxName = _this.dataList.wxName;
       	    _this.saveDetailData.fazhan = _this.dataList.fazhan;
       	    _this.saveDetailData.daozhan = _this.dataList.daozhan;
       	    _this.saveDetailData.abnormalType = _this.dataList.iType;
       	    $.ajax({
				url: base_url + '/transport/save/freightRecord',
	            type: 'post',
	            dataType: 'JSON',
	            contentType: 'application/json',
	            data: JSON.stringify(_this.saveDetailData),
	            beforeSend: function(){
	                loading = layer.load(0,{
	                  shade: [0.5,'#fff']
	               }) 
	            },
	            complete:function(){
	               layer.close(loading);
	            },
	            success: function(data){
	            	if(data.resultCode=="200"){
	            		layer.open({
	            			title:'提示信息',
	            			content:'保存成功！',
	            			end: function () {
	            				window.location.reload();
	                        }
	            		});
	            		
	            	}else{
	            		layer.open({
	            			title:'提示信息',
	            			content:data.reason
	            		});
	            	}
	            },
	            error:function(data){
	            	console.log(data);
	            }
			});
		},
		//保存详情的表单验证
		_saveVerify:function(){
			var _this = this;
			layui.use('form',function(){
				var form = layui.form;
				var laydate = layui.laydate;
				form.verify({
					//必填项
					mustFill: function(value,item){ 
		        	  if(value == ""){
		        		  return $(item).attr("dataname")+'不能为空！';
		        	  }
			        },
					wayBillNum: function(value,item){ 
					  var reg = /\b[a-z0-9A-Z]{10}\b|\b[a-z0-9A-Z]{12}\b/;
					  if(_this.isMany=="0"){
						  if(value == ""){
							  return '运单编号不能为空！';
						  }else{
							  if(!reg.test(value)){  
					            return '运单编号必须为10或12位数字或字母组合！';  
					          }
						  }
					  }
			        },
			        //件数正则匹配
			        caseFill:function(value,item){
			        	//正整数（不包括0）
			        	var reg = /^[0-9]*[1-9][0-9]*$/;
			        	if(!reg.test(value)){  
			        		return '件数必须为正整数！';  
			        	}
			        },
			        //损坏件数正则匹配
			        brokenFill:function(value,item){
			        	//非负整数（包括0）
			        	var reg = /^([1-9]\d*|[0]{1,1})$/;
			        	//var reg2 = /^\\d+$/;
			        	if($.trim(value)!=""){
			        		if(!reg.test(value)){  
					            return $(item).attr("dataname")+'必须为非负整数！';  
					        }
			        	}
			        },
			        //异常数量正则匹配
			        abnormalFill:function(value,item){
			        	var jianshu = Number($(item).parent().parent().find(".caseNum").val());
			        	var abNum = Number(value);
			        	if(abNum <= 0){
					       return '短少、破损、湿损、污染件数至少填一项！'; 
			        	}else if(abNum>jianshu){
			        	   return '异常数量不能多于件数！'; 
			        	}
			        },
			        //预估损失正则匹配
			        estimateLoss:function(value,item){
			        	//var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
			        	var reg = /^[0-9]*[1-9][0-9]*$/;
			        	if(value != ""){
			        	  if(!reg.test(value)){  
				            return '预估损失必须为正整数！';  
				          }
			        	}
			        },
			        //事故原因描述长度
			        reasonDescribe:function(value,item){
			        	if(value != ""){
			        	  var length = getByteLen(value);
			        	  if(length > 200){  
				            return $(item).attr("dataname")+'不能多于200个字符！';  
				          }
			        	}
			        }
				});
			});
		}
	}
});

