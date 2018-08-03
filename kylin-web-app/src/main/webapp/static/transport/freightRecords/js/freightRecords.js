/*
 * created by yl on 2018/4/20
 *  异常货运记录
 */

layui.use(['element', 'layer', 'form','laydate'], function(){
	var element = layui.element;
	var form = layui.form;
	element.init();
	form.render();
});

var vm = new Vue({
	el:"#rrapp",
	data:{    //变量声明
		checkInfo:{},        //查询条件
		dataList:[],        //查询结果
		dataInfo: {
			waybillNum: '',     //运单编号
			train: '',          //车牌号
			station: '',        //装车发站
			arriveStation: '',  //装车到站
			isHandle:'',        //是否处理
			yes:'',
			no:''
		},
		truckStart:'',  
		truckEnd:'',
		num:1,
		pageSize:20,  //每页显示条数
		pageNum:1
	},
	watch:{
		
	},
	created:function(){
		this._freightQuery();
	},
	mounted: function(){
		this._initDOM();
	},
	methods:{
		_initDOM:function(){
			var _this = this;
			layui.use(['layer', 'form','laydate'], function(){
				var form = layui.form;
       			var laydate = layui.laydate;
				//初始化录入时间
				var startTime = moment().subtract(7, 'days').format('YYYY-MM-DD');
				var endTime = moment().format('YYYY-MM-DD');
				$('#startDate').val(startTime);
				$('#endDate').val(endTime);
				//输入运单编号详细查询，录入时间清空
				$("#waybillNum").focus(function(){
			    	$('#startDate').val("");
			    	$('#endDate').val("");
			    });
			    $("#waybillNum").blur(function(){
				   if($(this).val() == ""){
					   $('#startDate').val(startTime);
					   $('#endDate').val(endTime);
				   }
			    });
				//渲染时间
				var laydate = layui.laydate;
				laydate.render({
					elem: '#startDate',
					type: 'date',
					max:endTime
				});
				laydate.render({
					elem: '#endDate',
					type: 'date',
					max:endTime
				});
			});
		},
		//查询，表单提交
		_freightQuery: function(){
			var _this = this;
			layui.use('form',function(){
				var form = layui.form;
				form.verify({
					waybillNum: function(value,item){
					  var reg = /\b[a-z0-9A-Z]{10}\b|\b[a-z0-9A-Z]{12}\b/;
					  if(value != ""){
						  if(!reg.test(value)){  
				            return '运单编号必须为10或12位数字或字母组合！';  
				          }
					  }
			        },
			        train: function(value,item){ 
			          var reg = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
			          if(value != ""){
						  if(!reg.test(value)){  
				            return '请输入正确车牌号！';  
				          }
					  }
			        }
				});
				form.on('submit(toCheck)', function(data){
					_this.checkInfo = data.field;
					_this._ajaxRequest(_this.pageNum);
					return false;
				});
			})
		},
		//表单提交请求数据
		_ajaxRequest:function(num){
			var _this = this;
			_this.checkInfo.num = num || _this.num;
			_this.checkInfo.size = _this.pageSize;
			//通过vm.$set为对象添加属性,进行实时监视
			/*this.$set(this.dataInfo,'startTime',$("#startDate").val());
			this.$set(this.dataInfo,'endTime',$("#endDate").val());
			this.dataInfo.station = this.truckStart;
			this.dataInfo.arriveStation = this.truckEnd;
			this.dataInfo.waybillNum = $("#waybillNum").val();
			this.dataInfo.train = $("#train").val();*/
			$.ajax({
				url: base_url + '/transport/query/freightRecord',
                type: 'post',
                dataType: 'JSON',
                contentType: 'application/json',
                data: JSON.stringify(_this.checkInfo),
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
                		var resultList = data.data.list.collection;
                    	for(var i in resultList){
                    		resultList[i].disabled = false;
                    		resultList[i].abnormalNum = "短少数量："+resultList[i].ds+"件；破损数量："+resultList[i].bjps+"件；湿损数量："+resultList[i].ssh+"件；污染数量："+resultList[i].wr+"件";
                    		//判断异常环节
                    		if(resultList[i].abnormalType == "2"){
                    			resultList[i].abnormalTypeText = "提货";
                    		}else if(resultList[i].abnormalType == "0"){
                    			resultList[i].abnormalTypeText = "干线";
                    		}else if(resultList[i].abnormalType == "1"){
                    			resultList[i].abnormalTypeText = "配送";
                    		}
                    		//判断是否处理
                    		if(resultList[i].isHandle == "1"){
                    			resultList[i].isHandleText = "已处理";
                    			//判断审核按钮是否可点
                    			if(userCompany != resultList[i].noticeCompany){//登录公司!=通知公司，审核按钮禁点
                    				resultList[i].disabled = true;
                    			}
                    		}else if(resultList[i].isHandle == "0"){
                    			resultList[i].isHandleText = "未处理";
                    		}
                    	}
                    	_this.dataList = resultList;
                    	//数据量，每页条数，总页数 
                    	$('.page-total').html(data.data.list.total);
    					$('.page-size').html(data.data.list.size);
    					$('.page-pages').html(data.data.list.pages);
    					//是否有中间页码 
    				    if($('.page-num')){
    						$('.page-num').remove();
    					}
    					if(data.data.list.pages > 1){
    						if(!data.data.list.isFirstPage){
    							$('.isFirstPage').show();
    							$('.page-prePage').attr('num',data.data.list.prePage);
    						}else{
    							$('.isFirstPage').hide();
    						}
    						$.each(data.data.list.navigatepageNums, function(i,e){
    							if(e == data.data.list.num){
    								$('.insert-li').before('<li class="active page-num"><a href="javascript:;" num='+e+'>'+e+'</a></li>');
    							}else{
    								$('.insert-li').before('<li class="page-num"><a href="javascript:;" num='+e+'>'+e+'</a></li>');
    							}
    						});
    						if(!data.data.list.isLastPage){
    							$('.isLastPage').show();
    							$('.nextPage').attr('num',data.data.list.nextPage);
    							$('.lastPage').attr('num',data.data.list.pages);
    						}else{
    							$('.isLastPage').hide();
    						}
    					}else{
    						$('.isLastPage').hide();
    					}
    					_this.eachPage();
                	}else{
                		layer.open({
                       		title:"提示信息",
                       		content:data.reason
                       	});
                	}
                	
                },
                error:function(data){
                	console.log(data);
                }
			});
			
		},
		//点击每一页
		eachPage:function(){
			var _this = this;
			$(".pagination a").each(function(i){	
				$(this).unbind('click').on('click', function(){
					_this._ajaxRequest($(this).attr("num"));
				});   
			});
		},
		//审核货运记录详情
		checkDetail:function(id,ydbhid){
			var _this = this;
			var width=$(document).width();
			$("body").css ("overflow-y","hidden");
			$("body").width(width);
			layer.open({
				  type: 2,
				  title: "货运记录审核（运单号:"+ydbhid+"）",
				  closeBtn: 1, //显示关闭按钮
				  shade: [0],
				  area: ['90%', '90%'],
				  anim: 2,
				  content: [base_url + "/transport/toCheckFreightRecords?id="+id, 'yes'], //iframe的url，no代表不显示滚动条
				  end: function(){
					  $("body").css ('overflow-y','auto');
		          }
			});
		},
		//打印货运记录详情
		printDetail:function(id,ydbhid){
			var width=$(document).width();
			$("body").css ("overflow-y","hidden");
			$("body").width(width);
			layer.open({
				  type: 2,
				  title: "货运记录打印（运单号:"+ydbhid+"）",
				  closeBtn: 1, //显示关闭按钮
				  shade: [0],
				  area: ['90%', '90%'],
				  anim: 2,
				  content: [base_url + "/transport/toPrintFreightRecords?id="+id, 'yes'], //iframe的url，no代表不显示滚动条
				  end: function () {
					  $("body").css ('overflow-y','auto');
		            }
			});
		},
	}
});