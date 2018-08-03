/**
 * 通用基础数据获取js
 */
var resultOptions = [];
var selectType= new Array();
//获取所有基础数据
function getAllOptions(){
	EasyAjax.ajax_Get_Json({
		dataType: 'json',
	    url: base_url + '/transport/generalInfo/queryAllBasicInfo',
	    contentType: "application/json",
	    async:false
	},function(data){
		var resultInfo = data.resultInfo;
		for(var i=0;i<resultInfo.length;i++){
			var info = {};
			info.text = resultInfo[i].keyName;
			info.value = resultInfo[i].keyValue;
			info.name = resultInfo[i].name;
			info.valuestr = resultInfo[i].keyValueStr;
			resultOptions[i] = info;
		}
	});
}

//获取某个基础数据
function getOptionsByName(name){
	var selectOptions = [];
	var count = 0;
	for(var i=0;i<resultOptions.length;i++){
		var infoName = resultOptions[i].name;
		if(infoName == name){
			var info = {};
			info.text = resultOptions[i].text;
			info.value = resultOptions[i].value;
			info.valuestr = resultOptions[i].valuestr;
			selectOptions[count] = info;
			count+=1;
		}
	}
	return selectOptions;
}

//vue
var vm = new Vue({
	el:'#newpicktohome',
	data:{
		fwfs_options:[],
		caozuoyaoqiu_options:[],
		isfd_options:[],
		pszhsh_options:[],
		fukuanfangshi_options:[],
		zyfs_options:[],
		daikebaozhuang_options:[],
		ywlx_options :[],
		ysfs_options :[],
		clxz_options :[],
		drivers_options :[],
		destination_options :[],
		destination_point_options :[],
		placeOptions :[],
		mileageOptions :[],
		tctimesOptions:[],
		orderEntity : { 
			gs : '',
			kfy : '',
			kfgrid : '',
			yywd : '',
			id :''
		},
		canModifyOrderEntity : false,
		canModifyGoodsDetail : false,
		canModifyDispatchDetail : false,
		jsonObj: {
			hwmc: '',
			bz: '',
			jianshu: '',
			zl: '',
			tj: '',
			shenMingJiaZhi: '',
			baoJiaJinE :''
		},
		dispatchObj: {
			clxz: '',
			ch: '',
			cx: '',
			sj: ''
		},
		goodsLists: [],
		dispatchLists : []
	},
	watch:{
		fwfs_options:function(){
		  this.$nextTick(function(){
				layui.use(['form','layer'],function(){
					 var layer= layui.layer;
				});
		  });
		},
		ysfs_options:function(){
			  this.$nextTick(function(){
				  $(vm.ysfs_options).each(function(i,item){
					  selectType[i]=item.text;
				  });
			  });
		},
		dispatchLists:function(){
			this.$nextTick(function(){
				  $(".sj").each(function(i){
						$(".sj").eq(i).attr("id","sj"+i);
					})
			});
		},
		orderEntity:function(){
			  this.$nextTick(function(){
  				$(vm.dispatchLists).each(function(i,item){
					$(".sj").eq(i).val(item.sj);
				 });
                 $(".sj").each(function(i){
					 $(".sj").eq(i).attr("id","sj"+i);
				 })
			  });
			}
	},
	methods: {
		_print: function(){
			var orderEntity = {};
			var dispatchList = [];
			var goodsList = [];
			$('.orderEntity').each(function(index,elem){
				var name = $(elem).attr('name');
				var value = $(elem).val();
				orderEntity[name] = value;
			})
			$('.goodsList').each(function(index,elem){
				var name = $(elem).attr('name');
				if(name=='hwmc'){
					goods = {};
				}
				var value = $(elem).val();
				goods[name] = value;
				if(name=='baoJiaJinE'){
					goodsList.push(goods);
				}
			})
			$('.dispatchList').each(function(index,elem){
				var name = $(elem).attr('name');
				if(name=='clxz'){
					dispatch = {};
				}
				var value = $(elem).val();
				dispatch[name] = value;
				if(name=='sj'){
					dispatchList.push(dispatch);
				}
			})
			if(!vm.canModifyGoodsDetail && vm.canModifyDispatchDetail){
				var pData = {"orderEntity":orderEntity,"canModifyGoodsDetail":vm.canModifyGoodsDetail,"goodsList":goodsList,"dispatchList":dispatchList,"canModifyDispatchDetail":vm.canModifyDispatchDetail};
			}else if(vm.canModifyGoodsDetail && !vm.canModifyDispatchDetail){
				var pData = {"orderEntity":orderEntity,"goodsList":goodsList,"canModifyGoodsDetail":vm.canModifyGoodsDetail,"canModifyDispatchDetail":vm.canModifyDispatchDetail};
			}else{
				var pData = {"orderEntity":orderEntity,"goodsList":goodsList,"dispatchList":dispatchList,"canModifyGoodsDetail":vm.canModifyGoodsDetail,"canModifyDispatchDetail":vm.canModifyDispatchDetail};
			}
			sessionStorage.setItem('printData',JSON.stringify(pData));
			
			layer.open({
				type: 2,
				area: ['80%','90%'],
				title: '派车修改操作',
				closeBtn: 1, //显示关闭按钮
				shade: 0.8,
				content: [base_url + "/transport/bundleArrive/arrive/toPrintReceiptSign", 'yes'] 
			})
		},
		_addHandler: function(){
			var newObject = {
					hwmc: '',
					bz: '',
					jianshu: '',
					zl: '',
					tj: '',
					shenMingJiaZhi: '',
					baoJiaJinE :''
			};
			if(this.goodsLists.length<3){
				this.goodsLists.push(newObject);
			}else{
				layer.msg("货物明细最多输入3条");
			}
		},	
		_addDispatch: function(){
			this._selectDriverList();
			var newObject = {
				clxz: '',
				ch: '',
				cx: '',
				sj: ''
			};
			var id = this.orderEntity.id;
			if(id && id!=''){
				if(this.dispatchLists && this.dispatchLists !=null && this.dispatchLists.length>=4){
					layer.msg("调度明细不能超过4条");
				}else{
					if(!this.dispatchLists || this.dispatchLists == null){
						this.dispatchLists = [{
							clxz: '',
							ch: '',
							cx: '',
							sj: ''
						}];
					}else{
						this.dispatchLists.push(newObject);
					}
				}
			}else{
				layer.msg("新增上门取货指令时，不能编辑调度派车单");
			}
		},
		_addTime : function(){
			layui.use(['element','laydate','form'], function(){
	    		var laydate = layui.laydate;
	    			laydate.render({
	    				elem: '#date1',
	    				theme: '#009688',
	    				type: 'datetime',
	    				trigger: 'click',
	    				format: 'yyyy-MM-dd HH:mm:ss', //可任意组合
	    				done:function(value){
	    					$("#date1").attr("data",value)
	    					vm.orderEntity.jhqhtime=value;
	    				}
	    			});	
	    			laydate.render({
	    				elem: '#date2',
	    				theme: '#009688',
	    				type: 'datetime',
	    				trigger: 'click',
	    				format: 'yyyy-MM-dd HH:mm:ss' //可任意组合
	    			});	
	    	});
		},
		_selectDriverList:function(){ //运输方式
			$(".selectInput").each(function(){
				$(this).bind("click",function(e){
					$(".selectUl").hide();
					stopPropagation(e);
					$(this).siblings("ul").show();
					$(this).siblings("ul").find("li").show();
					var obj=$(this).siblings("ul");
					selectInput(obj);
				});
				$(this).on("input propertychange",function(){
					var _this=$(this);
					var obj=_this.siblings("ul");
					var searchName =_this.val(); 
				    if (searchName == "") {
		   		    	obj.find("li").show();
		   		    } else {
		   		    	obj.find("li").each(function() {
		   		    		var s_name=""; 
		   		    		if(obj.hasClass("sjUl")){
		   		    		    s_name = $(this).attr("data"); 
		   		    		}else{
		   		    			s_name = $(this).attr("value");
		   		    		}    
		   		            if (s_name.indexOf(searchName) != -1) {
		   		              $(this).show();
		   		            }else {
		   		              $(this).hide();
		   		            }
		   		          });
		   		    }
				});	
				$(this).blur(function(){
					var _name=$(this).attr("name");
					var _text=$(this).val();
					if(_name=="ysfs"&& _text!=""){
						if($.inArray(_text, selectType)==-1){
							layer.msg( "运输方式不存在！");
							$(this).val("");
						
					  }
					}
				})
			});	
			
		}

	},
	created: function(){
	},
	mounted :function(){//挂载之后
		if(!pcid || typeof(pcid)=='undefined'){//新增
			//初始化数据
			this.orderEntity.gs = companyName;
			this.orderEntity.kfy = kfy;
			this.orderEntity.kfgrid = kfgrid;
			this.orderEntity.yywd = yywd;
			this._addHandler();
			setToDisable('dispatchList','adddispatch');
			setToDisable('dispatchRange');
		}else{//调度派车（修改）
			this.orderEntity.pcid = pcid;
			getEditInfo(pcid,openType);
		}
		this._addTime();
		//this._addDispatch();
		changeDaozhan(companyName);
		changeMileage();
	},
	updated :function(){//更新之后
		this._selectDriverList();
	}
});

//公司目的站
function destinationStation(){
	EasyAjax.ajax_Get_Json({
		dataType: 'json',
	    url: base_url + '/transport/generalInfo/destinationStation',
	    contentType: "application/json",
	    async:false
	},function(data){
		var resultInfo = data.resultInfo;
		var destinationOptions = [];
		for(var i=0;i<resultInfo.length;i++){
			var info = {};
			info.text = resultInfo[i].name;
			info.value = resultInfo[i].bh;
			destinationOptions[i] = info;
		}
		vm.destination_options = destinationOptions;
	});
}

//起始地和取货地
function getplace(){
	EasyAjax.ajax_Get_Json({
		dataType: 'json',
		url: base_url + '/transport/generalInfo/pickGoodsAddress',
		contentType: "application/json",
		async:false
	},function(data){
		var resultInfo = data.resultInfo;
		var placeOptions = [];
		for(var i=0;i<resultInfo.length;i++){
			var info = {};
			info.min = resultInfo[i].min;
			info.max = resultInfo[i].max;
			info.text = resultInfo[i].mdd;
			info.value = resultInfo[i].mdd;
			placeOptions[i] = info;
		}
		vm.placeOptions = placeOptions;
	});
}

//里程
function getMileage(){
	EasyAjax.ajax_Get_Json({
		dataType: 'json',
		url: base_url + '/transport/generalInfo/pickGoodsAddress',
		contentType: "application/json",
		async:false
	},function(data){
		var resultInfo = data.resultInfo;
		var mileageOptions = [];
		for(var i=0;i<resultInfo.length;i++){
			var info = {};
			info.text = resultInfo[i].min;
			info.value = resultInfo[i].max;
			info.shlc = resultInfo[i].shlc;
			info.lc = resultInfo[i].min + '-' +resultInfo[i].max;
			info.mdd = resultInfo[i].mdd;
			mileageOptions[i] = info;
		}
		vm.mileageOptions = mileageOptions;
	});
}

//公司到站网点
function destPointByCompanyName(name){
	if(name == '') {
		layer.msg("请先选择目的地，到站网点根据目的地获取！");
		return false;
	}
	var dataArr = {companyName:name};
	EasyAjax.ajax_Post_Json({
		dataType: 'json',
		url: base_url + '/transport/generalInfo/destPointByCompanyName',
		data : JSON.stringify(dataArr),
	    contentType : 'application/json',
		errorReason: true
	},function(data){
		var resultInfo = data.resultInfo;
		var destinationOptions = [];
		for(var i=0;i<resultInfo.length;i++){
			var info = {};
			info.text = resultInfo[i].shhd;
			info.value = resultInfo[i].shhd;
			destinationOptions[i] = info;
		}
		vm.destination_point_options = destinationOptions;
	});
}

//获取司机信息
function queryDriverInfo(){
	EasyAjax.ajax_Get_Json({
		dataType: 'json',
	    url: base_url + '/transport/generalInfo/queryDriverInfo',
	    contentType: "application/json",
	    async:false
	},function(data){
		var resultInfo = data.resultInfo;
		var driverOptions = [];
		for(var i=0;i<resultInfo.length;i++){
			var info = {};
			info.text = resultInfo[i].xm;
			info.value = resultInfo[i].grid;
			driverOptions[i] = info;
		}
		vm.drivers_options = driverOptions;
	});
}

//基础数据赋值到vue data
getAllOptions('到货装载类型');
vm.fwfs_options = getOptionsByName('服务方式');
vm.pszhsh_options = getOptionsByName('派送指示');
vm.isfd_options = getOptionsByName('布尔值');
vm.caozuoyaoqiu_options = getOptionsByName('操作要求');
vm.fukuanfangshi_options = getOptionsByName('到货装载付款方式');
vm.clxz_options = getOptionsByName('车辆性质');
vm.zyfs_options =  getOptionsByName('作业方式');
vm.daikebaozhuang_options = getOptionsByName('代客包装');
vm.ysfs_options = getOptionsByName('运输方式');
vm.ywlx_options = getOptionsByName('业务类型');
vm.tctimesOptions = getOptionsByName('提成趟次');
queryDriverInfo();
destinationStation();//目的地
getplace();
getMileage();

/**
 * 保存录入上门取货指令
*/
function savePickFormHomeOrder(){
	var dataArr = {};
	var orderEntity = {};
	var dispatchList = [];
	var goodsList = [];
	var submitData = {};
	var dispatch = {};
	var goods = {};
	changeMileage();
	$('.orderEntity').each(function(index,elem){
		var name = $(elem).attr('name');
		var value = $(elem).val();
		orderEntity[name] = value;
	})
	var songhuoshanglou = $("#songhuoshanglou").is(':checked');
	var daikezhuangxie = $("#daikezhuangxie").is(':checked');
	orderEntity['songhuoshanglou'] = songhuoshanglou==true?1:0;
	orderEntity['daikezhuangxie'] = daikezhuangxie==true?1:0;
	
	$('.orderEntity_lc').each(function(index,elem){
		var name = $(elem).attr('name');
		var value = $(elem).val();
		orderEntity[name] = value;
	})
	$('.goodsList').each(function(index,elem){
		var name = $(elem).attr('name');
		if(name=='hwmc'){
			goods = {};
		}
		var value = $(elem).val();
		goods[name] = value;
		if(name=='baoJiaJinE'){
			goodsList.push(goods);
		}
	})
	$('.dispatchList').each(function(index,elem){
		var name = $(elem).attr('name');
		if(name=='clxz'){
			dispatch = {};
		}
		var value = $(elem).val();
		dispatch[name] = value;
		if(name=='sj'){
			dispatchList.push(dispatch);
		}
	})
	if(!vm.canModifyGoodsDetail && vm.canModifyDispatchDetail){
		submitData = {"orderEntity":orderEntity,"canModifyGoodsDetail":vm.canModifyGoodsDetail,"dispatchList":dispatchList,"canModifyDispatchDetail":vm.canModifyDispatchDetail};
	}else if(vm.canModifyGoodsDetail && !vm.canModifyDispatchDetail){
		submitData = {"orderEntity":orderEntity,"goodsList":goodsList,"canModifyGoodsDetail":vm.canModifyGoodsDetail,"canModifyDispatchDetail":vm.canModifyDispatchDetail};
	}else{
		submitData = {"orderEntity":orderEntity,"goodsList":goodsList,"dispatchList":dispatchList,"canModifyGoodsDetail":vm.canModifyGoodsDetail,"canModifyDispatchDetail":vm.canModifyDispatchDetail};
    }
	dataArr = JSON.stringify(submitData);
	setToAble('lc');
	  layui.use(['layer','form'], function(){
		var checkDataFilter = checkData();
		    if(!checkDataFilter){
		 		return false;
		 	}  
		$.ajax({
		    type: "POST",
		    url:base_url + '/transport/bundleArrive/savePickFormHomeOrder',
		    contentType: "application/json",
		    data: dataArr,
		    dataType: "json",
		    beforeSend: function(){
            	loadings = layer.load(0,{
            		shade: [0.5,'#fff']
            	})
            },
            complete: function(){
            	layer.close(loadings);
            },
		    success: function(data){
				if(data.resultCode==200){
					vm.orderEntity.id = data.orderEntity.id;
					setToAble('lc');
					layer.msg("保存成功");
					$(".btn_save").unbind("click").attr("disabled",true).addClass("layui-btn-disabled");
				}else{
					layer.msg(data.reason);
				}
				setToDisable('lc');
		     },
		    error: function() {
		    	layer.msg("服务器异常");
		    } 				 				    
		});	
	  });
} 

function checkData(){
	//选择了要发票
	if(vm.orderEntity.isXuYaoFaPiao == 1){
		if(!vm.orderEntity.tuoyunrenshuihao || vm.orderEntity.tuoyunrenshuihao==''){
			layer.msg("有发票的请输入税号！");
			return false;
		}
	}
	if(vm.orderEntity.isfd == 1){
		if(!vm.orderEntity.fdnr || vm.orderEntity.fdnr==''){
			layer.msg("有返单的请填写返单名称！");
			return false;
		}
		if(!vm.orderEntity.fdlc || vm.orderEntity.fdlc==''){
			layer.msg("有返单的请填写返单联次！");
			return false;
		}
		if(!vm.orderEntity.fdsl || vm.orderEntity.fdsl==''){
			layer.msg("有返单的请填写返单份数！");
			return false;
		}
	}
	return true;
}


//取货调度派车处理修改页面-数据加载
function getEditInfo(pcid,openType){
	layui.use(['layer','form'], function(){
	$.ajax({
	    type: "POST",
	    url:base_url + '/transport/bundleArrive/searchPickFormHomeOrder',
	    contentType: "application/json",
	    data: JSON.stringify({"pcid":pcid,"openType":openType}),
	    dataType: "json",
	    beforeSend: function(){
        	loadings = layer.load(0,{
        		shade: [0.5,'#fff']
        	})
        },
        complete: function(){
        	layer.close(loadings);
        },
	    success: function(data){
			if(data.resultCode==200 || data.resultCode==300){
				vm.orderEntity = data.orderEntity;
				vm.canModifyOrderEntity = data.canModifyOrderEntity;
				vm.canModifyGoodsDetail = data.canModifyGoodsDetail;
				vm.canModifyDispatchDetail = data.canModifyDispatchDetail;
				vm.canModifyColumnDdpcdd = data.canModifyColumnDdpcdd;
				vm.canModifyColumnDdpctime = data.canModifyColumnDdpctime;
				vm.canModifyColumnTctc = data.canModifyColumnTctc;
				vm.canModifyColumnDdqsd = data.canModifyColumnDdqsd;
				vm.canModifyColumnDdqhd = data.canModifyColumnDdqhd;
				vm.canModifyColumnDdcomm = data.canModifyColumnDdcomm;
				vm.canModifyColumnPcmdd = data.canModifyColumnPcmdd;
				vm.modifyReason = data.modifyReason;
				vm.goodsLists = data.goodsList;
				vm.dispatchLists = data.dispatchList;
				//console.log(dispatchLists);
				selectByClassNameValue('ywlx',data.orderEntity.ywlx);
				$("#date1").val(data.orderEntity.jhqhtime);
				if(data.orderEntity.songhuoshanglou==1){
					$("#songhuoshanglou").attr('checked','true');
				}else{
					$("#songhuoshanglou").removeAttr('checked');
				}
				if(data.orderEntity.daikezhuangxie==1){
					$("#daikezhuangxie").attr('checked','true');
				}else{
					$("#daikezhuangxie").removeAttr('checked');
				}
				if(this.canModifyDispatchDetail){
					setToAble('dispatchRange','adddispatch');
				}else{
					setToDisable('dispatchRange','adddispatch');
				}
				setTimeout("refleshInputCanModifyByInfo()",500);
				
				if(data.resultCode==300){
					layer.msg(data.modifyReason);
				}
				
			}else{
				layer.msg(data.reason);
			}
	     },
	    error: function() {
	    	layer.msg("服务器异常");
	    } 				 				    
	});	
	});
}

function changeMileage(){
	var mileage = '';
	var ddqhd = $("#ddqhd").val();
	if(ddqhd && ddqhd!=''){
		var mileageOps = vm.mileageOptions;
		for(var i=0;i<mileageOps.length;i++){
			var currMile = mileageOps[i];
			if(currMile.mdd == ddqhd){
				vm.orderEntity.pcshlc = currMile.shlc;
				vm.orderEntity.lc = currMile.text + '-' + currMile.value;
				break;
			}
		}
	}
}

//修改目的地联动到站网点
function changeDaozhan(companyName){
	var hwdaozhan = $("#hwdaozhan").val();
	if(!hwdaozhan || hwdaozhan == ''){
		if(companyName && companyName!=''){
			destPointByCompanyName(companyName);
		}
	}else{
		destPointByCompanyName(hwdaozhan);
	}
}

/**
 * 
 * 获取当前时间
 */
function updateDate(){
    var now = new Date(),
    //格式化日，如果小于9，前面补0
     day = ("0" + now.getDate()).slice(-2),
     month = ("0" + (now.getMonth() + 1)).slice(-2),
     hours = ("0" + (now.getHours())).slice(-2),
     minutes = ("0" + (now.getMinutes())).slice(-2),
     seconds = ("0" + (now.getSeconds())).slice(-2);
     today = now.getFullYear() + "-" + (month) + "-" + (day)+ " " + (hours)+ ":" + (minutes)+ ":" + (seconds);
	 return today;
}

function stopPropagation(e) {//把事件对象传入
	if (e.stopPropagation){ //支持W3C标准 doc
		e.stopPropagation();
	}else{ //IE8及以下浏览器
		e.cancelBubble = true;
	}
}

//下拉框 
function selectInput(obj){
	obj.find("li").click(function(){
		var _target = $(this).parent("ul").siblings("input");
		var tem=_target.attr("id");
		var _this=$(this);
		switch(tem){
		case "transType":_target.val(_this.text());
		            vm.orderEntity.ysfs=_this.text();
		 break;
		case "hwdaozhan":_target.val(_this.text());
                    vm.orderEntity.hwdaozhan=_this.text();
         break;
		case "ddqsd":_target.val(_this.text());
		             vm.orderEntity.ddqsd=_this.text();
		 break;
		case "hwdaozhanWangDian":_target.val(_this.text());
                    vm.orderEntity.hwdaozhanWangDian=_this.text();
         break; 
		case "sj0": _target.val(_this.attr("data"));
			    	vm.dispatchLists[0].sj=_target.val();
	     break;
		case "sj1": _target.val(_this.attr("data"));
    	            vm.dispatchLists[1].sj=_target.val();
		 break;
		case "sj2": _target.val(_this.attr("data"));
                    vm.dispatchLists[2].sj=_target.val(); 
	     break;
		case "sj3": _target.val(_this.attr("data"));
                    vm.dispatchLists[3].sj=_target.val();
	     break;
         default:
         break;
       }
	 $(this).parent("ul").hide();		
	});
	$(document).click(function(){
		obj.hide();
	});
}

function setToDisable(className,buttonName){
	$('.'+className).attr("disabled","disabled");
	$('.'+className).css({'background':'#F5F5F5','cursor':'not-allowed'});//#EEE0E5
	$('.'+className).unbind().on('click',function(){
		   layer.open({
				content: vm.modifyReason
		 });
	});
	if(buttonName){
		$('.'+buttonName).attr("disabled","disabled").addClass("layui-btn-disabled");
	}
}


function setToAble(className,buttonName){
	$('.'+className).removeAttr("disabled");
	$('.'+className).css({'background':'','cursor':'auto'});
	$('.'+className).removeAttr("disabled").removeClass("layui-btn-disabled");
	$('.'+className).unbind();
	if(buttonName){
		$('.'+buttonName).removeAttr("disabled").removeClass("layui-btn-disabled");
	}
}

function selectByClassNameValue(classname,value){
	$("."+classname).find("option[value='"+value+"']").attr("selected",true);
}

//加载完信息后，控制页面输入框是否可以编辑
function refleshInputCanModifyByInfo(){
	changeMileage();
	//对象
	if(vm.canModifyOrderEntity==false){
		setToDisable('orderEntity');
	}else{
		setToAble('orderEntity');
	}
	//货物信息
	if(vm.canModifyGoodsDetail==false){
		setToDisable('goodsList','addgoods');
	}else {
		setToAble('goodsList','addgoods');
	}
	//派车信息
	if(vm.canModifyDispatchDetail==false){
		setToDisable('dispatchList','adddispatch');
	} else {
		setToAble('dispatchList','adddispatch');
		pctime();
	}
	//字段
	if(vm.canModifyColumnDdpcdd == true){
		setToAble('ddpcdd');
	}else{
		setToDisable('ddpcdd');
	}
	//字段
	if(vm.canModifyDispatchDetail == true){
		if(vm.canModifyColumnPcmdd == true){
			setToAble('pcmdd');
		}else{
			setToDisable('pcmdd');
		}
	}
	//字段
	if(vm.canModifyColumnDdpctime == true){
		setToAble('ddpctime');
	}else{
		setToDisable('ddpctime');
	}
	//字段
	if(vm.canModifyColumnTctc == true){
		setToAble('tangci');
	}else{
		setToDisable('tangci');
	}
	//字段
	if(vm.canModifyColumnDdqsd == true){
		setToAble('ddqsd');
	}else{
		setToDisable('ddqsd');
	}
	//字段
	if(vm.canModifyColumnDdqhd == true){
		setToAble('ddqhd');
	}else{
		setToDisable('ddqhd');
	}
	//字段
	if(vm.canModifyColumnDdcomm == true){
		setToAble('ddcomm');
	}else{
		setToDisable('ddcomm');
	}
	setToAble('id');
}

function pctime(){
	var ddpctime=$("#date2").attr("data");
    if(ddpctime!=null){
  	  $("#date2").val($("#date2").attr("data").substring(0,19));
    }else{
			 var nowTime=self.setInterval(function(){
			var currTime = updateDate();
			$('#date2').val(currTime);
		}, 1000);
    }  
}