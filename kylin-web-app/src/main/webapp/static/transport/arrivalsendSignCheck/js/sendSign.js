/*
 * 送货（派车）签收单
 * created by yanxf on 2018/1/31
 */
/*
 * 生成实例
 * */
var pcqsdList=[];
var khObjectList=[];
var tangciList = [];
var pcshlcList = [];
var driverList = [];
var isDelivery = "";
var vm = new Vue({
	el: '#arrivellodiing',
	data: {/*变量声明*/
		isSave: false,
		fd:false,
		carOutInfo: {},
		goodsDetailInfo: {},
		vehicleDetailInfo: {},
		rowspan: 12,
		pcqsd_options:pcqsdList,
		khObject_options:khObjectList,
		tangci_options:tangciList,
		pcshlc_options:pcshlcList,
		driver_options:driverList,
		vehicleinfofrist:{},
		vehicleinfosecond:{},
		vehicleinfothird:{},
		vehicleinfofourth:{},
		select:0,
		printclick: 0,
		stylecss: '',
		printclick: 0,/*为了计算第一次点击时不出现打印页面的bug*/
		f: ''
	},
	watch:{
		goodsDetailInfo:function(){
			  this.$nextTick(function(){
				  this._goodsDetaillen();
				  this._selectDriverList();//调用司机下拉框
			  });
		  }
	  /*    ,
		  'vehicleinfofrist.sj': function(){
			 // this._isAllowPrint();
		  },
		  'vehicleinfosecond.sj': function(){
			 // this._isAllowPrint();
		  },
		  'vehicleinfothird.sj': function(){
			//  this._isAllowPrint();
		  },
		  'vehicleinfofourth.sj': function(){
			//  this._isAllowPrint();
		  }*/
	},
	methods: {
		_initDOM: function(){
			/*获取css样式   by lhz on 2018/2/6*/
			this.stylecss = '<style type="text/css">@import url("'+ctx_static+'/transport/excelRead/css/layui.css");'
			 +'@import url("'+ctx_static+'/publicFile/css/public.css");'
			 +'@import url("'+ctx_static+'/transport/arrivalsendSignCheck/css/sendSignCheck.css")</style>';
			
			layui.use(['layer','form'], function(){
			/*获取派车单号数据*/    
			var pcdid = window.location.href.split('=')[1];
			pcdid = decodeURI(pcdid);
			this.pcdid = pcdid;
			/* ******* */
			/*查询派车单信息*/
			var commitData = {pcdid: pcdid};
			EasyAjax.ajax_Post_Json({
				dataType: 'json',
	            url: base_url + '/transport/carOut/delivery/search',
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
			},function(res){
				console.log(res);
				if(res.resultCode == 200){
					//如果已做核算，将保存变为不可点
					if(res.resultInfo.isAccounting==1){
						$(".list-table input,.list-table select").attr("disabled",true);
						vm.isSave = true;	
					}
					isDelivery = res.resultInfo.isDelivery;
					vm.carOutInfo = res.resultInfo.transportCarOut;
					//格式化时间
					vm.carOutInfo.kdTime = getMyDate(res.resultInfo.transportCarOut.kdTime);
					vm.carOutInfo.jhshTime = getMyDate(res.resultInfo.transportCarOut.jhshTime);
					vm.carOutInfo.pcpcTime = getMyDate(res.resultInfo.transportCarOut.pcpcTime);
					vm.carOutInfo.pcshlc = res.resultInfo.transportCarOut.min + '-' + res.resultInfo.transportCarOut.max;
					
					vm.goodsDetailInfo = res.resultInfo.transportCarOutGoodsDetailList;
					vm.vehicleDetailInfo = res.resultInfo.transportCarOutVehicleDetailList;
					if(res.resultInfo.transportCarOutGoodsDetailList.fd==0){
						vm.flag=false;
					}else{
						vm.flag=true;
					} 	
					$(".jhshTime").html(res.resultInfo.transportCarOut.jhshTime);
				
					//车辆信息
					if(res.resultInfo.transportCarOutVehicleDetailList != null){
						switch(res.resultInfo.transportCarOutVehicleDetailList.length){
							case 4:
								vm.vehicleinfofourth = res.resultInfo.transportCarOutVehicleDetailList[3];
							case 3:
								vm.vehicleinfothird = res.resultInfo.transportCarOutVehicleDetailList[2];
							case 2:
								vm.vehicleinfosecond = res.resultInfo.transportCarOutVehicleDetailList[1];
							case 1:	
								vm.vehicleinfofrist = res.resultInfo.transportCarOutVehicleDetailList[0];
						}
					}		
				}
			 });
			});
		},
		_selectDriverList:function(){ //司机下拉列表
			$(".selectInput").each(function(){
				var _this=$(this);
				var obj=$(this).siblings(".selectUl");
				$(this).click(function(e){
					stopPropagation(e);
					obj.show();
					_this.siblings(".selectUl").find("li").show();
					selectInput(obj);
				})
			  
			});
			//var obj=$(".selectUl");
			//selectInput(obj)//下拉框
			this._driverSearch();
			
		},
            //司机输入匹配
       _driverSearch:function(){
    	   $(".selectInput").each(function(){
    		   var obj=$(this).siblings(".selectUl");
    		   $(this).on("input propertychange",function(){
    	    		   var searchName =  $(this).val(); 
    		   		    if (searchName == "") {
    		   		    	obj.show();
    		   		    	obj.find("li").show();
    		   		    } else {
    		   		    	obj.find("li").each(function() {
    		   		            var s_name = $(this).attr("value");    
    		   		            if (s_name.indexOf(searchName) != -1) {
    		   		              $(this).show();
    		   		            } else {
    		   		              $(this).hide();
    		   		            }
    		   		          });
    		   		    }
       		   });
    	   });
       },
		_goodsDetaillen: function(){
			//判断货物明细行数
			var goodsDetaillen = $(".goodsDetailTr").length;
			$(".jhshTime").attr("rowspan",goodsDetaillen);
		},
		
		//送货地、起始地
		_basicPcshd: function(){
			EasyAjax.ajax_Get_Json({
				dataType: 'json',
			    url: base_url + '/transport/generalInfo/pickGoodsAddress',
			    contentType: "application/json",
			    async:false,
			    errorReason: true,
			},function(data){
				var resultInfo = data.resultInfo;
				for(var i=0;i<resultInfo.length;i++){
					var info = {
							text:resultInfo[i].mdd,
							value:resultInfo[i].mdd,
							lc:resultInfo[i].lc
					};
					pcqsdList.push(info);	
				}
				//console.log(pcqsdList);
			});
		},
		//考核对象
		_basicKhObject: function(){
			EasyAjax.ajax_Get_Json({
				dataType: 'json',
			    url: base_url + '/transport/generalInfo/querCarOutBaseKh',
			    contentType: "application/json",
			    async:false,
			    errorReason: true,
			},function(data){
				var resultInfo = data.resultInfo;
				//console.log(resultInfo);
				for(var i=0;i<resultInfo.length;i++){
					var info = {
							text:resultInfo[i].name,
							value:resultInfo[i].code
					};
					khObjectList.push(info);	
				}
				//console.log(khObjectList);
			});
		},
		
		//提成趟次
		_basicTangci: function(){
			EasyAjax.ajax_Get_Json({
				dataType: 'json',
			    url: base_url + '/transport/generalInfo/queryCarOutBaseTc',
			    contentType: "application/json",
			    async:false,
			    errorReason: true,
			},function(data){
				var resultInfo = data.resultInfo;
				//console.log(resultInfo);
				for(var i=0;i<resultInfo.length;i++){
					var info = {
							text:resultInfo[i].name,
							value:resultInfo[i].id
					};
					tangciList.push(info);	
				}
				//console.log(tangciList);
			});
		},
		
		//送货里程
		_basicPcshlc: function(){
			EasyAjax.ajax_Get_Json({
				dataType: 'json',
			    url: base_url + '/transport/generalInfo/pickGoodsMileage',
			    contentType: "application/json",
			    async:false,
			    errorReason: true,
			},function(data){
				var resultInfo = data.resultInfo;
				//console.log(resultInfo);
				for(var i=0;i<resultInfo.length;i++){
					var info = {
							shlc:resultInfo[i].shlc,
							lc:resultInfo[i].lc,
							min:resultInfo[i].min,
							max:resultInfo[i].max
					};
					pcshlcList.push(info);	
				}
				
                $("#pcshd").change(function(){
             	   var html=$("#pcshd option:selected").attr("data");
             	   vm.carOutInfo.pcshlc=html
                })
				//console.log(tangciList);
			});
		},
		
		//司机
		_basicDriver: function(){
			EasyAjax.ajax_Get_Json({
				dataType: 'json',
			    url: base_url + '/transport/generalInfo/queryDriverInfo',
			    contentType: "application/json",
			    async:false,
			    errorReason: true,
			},function(data){
				var resultInfo = data.resultInfo;
				//console.log(resultInfo);
				for(var i=0;i<resultInfo.length;i++){
					var info = {
							xm:resultInfo[i].xm,
							bm:resultInfo[i].bm,
							grid:resultInfo[i].grid,
							zt:resultInfo[i].zt,
							gs:resultInfo[i].gs
					};
					driverList.push(info);	
				}
				//console.log(tangciList);
			});
		},
		/* ********created by lhz for print******/
		_printHandler: function(){
			this.printclick ++; 
			 var sessionData = {
				   carOutInfo: this.carOutInfo,
				   goodsDetailInfo: this.goodsDetailInfo,
				   vehicleinfofrist: this.vehicleinfofrist,
				   vehicleinfosecond: this.vehicleinfosecond,
				   vehicleinfothird: this.vehicleinfothird,
				   vehicleinfofourth: this.vehicleinfofourth,
				   fd: this.fd
			 };
			 sessionData = JSON.stringify(sessionData);
			 sessionStorage.setItem('sendSign',sessionData);
			$('#printf').attr('src', base_url + "/transport/bundleArrive/arrive/toPrintSendSign");
			this.f = document.getElementById('printf');
			var _f = this.f;
			$(this.f).on('load', function(){
				var c = $(this)[0].contentWindow;/*获取window对象*/
				c.vm._initHandler();
				/**update by wyp **/
				if(c.vm._initHandler()){
					_f.contentWindow.print();
				}
			})
		},
		_isAllowPrint: function(){
			this.isSave = false;
		},
		/* ********************************** */
		//保存
		_saveHandler: function() {
			/** 必填项校验*/
			//获取页面数据
			var b = [];	
			if($("#fourthch").val() != null && $("#fourthch").val() != '' && $("#fourthcx").val() != null && $("#fourthcx").val() != ''){
				var vehicleDetail = {};
				vehicleDetail.clxz = $("#fourthclxz").val();
				vehicleDetail.ch = $("#fourthch").val();
				vehicleDetail.cx = $("#fourthcx").val();
				vehicleDetail.sj = $("#fourthsj").val();
				b.push(vehicleDetail);
			}
			if($("#thirdch").val() != null && $("#thirdch").val() != '' && $("#thirdcx").val() != null && $("#thirdcx").val() != ''){
				var vehicleDetail = {};
				vehicleDetail.clxz = $("#thirdclxz").val();
				vehicleDetail.ch = $("#thirdch").val();
				vehicleDetail.cx = $("#thirdcx").val();
				vehicleDetail.sj = $("#thirdsj").val();
				b.push(vehicleDetail);
			}
			if($("#secondch").val() != null && $("#secondch").val() != '' && $("#secondcx").val() != null && $("#secondcx").val() != ''){
				var vehicleDetail = {};
				vehicleDetail.clxz = $("#secondclxz").val();
				vehicleDetail.ch = $("#secondch").val();
				vehicleDetail.cx = $("#secondcx").val();
				vehicleDetail.sj = $("#secondsj").val();
				b.push(vehicleDetail);
			}
			if($("#fristch").val() != null && $("#fristch").val() != '' && $("#fristcx").val() != null && $("#fristcx").val() != ''){
				var vehicleDetail = {};
				vehicleDetail.clxz = $("#fristclxz").val();
				vehicleDetail.ch = $("#fristch").val();
				vehicleDetail.cx = $("#fristcx").val();
				vehicleDetail.sj = $("#fristsj").val();
				b.push(vehicleDetail);
			}
			//console.log(b);
			
			var transportCarOut = {};
			transportCarOut.id = $("#id").val();//派车单号
			transportCarOut.gs = $("#gs").val();//公司
			transportCarOut.pcshd = $("#pcshd").val();//送货地
			transportCarOut.pcqsd = $("#pcqsd").val();//起始地
			transportCarOut.pcComm = $("#pcComm").val();//特别说明
			transportCarOut.khObject = $("#khObject").val();//考核对象
			transportCarOut.tangci = $("#tangci").val();//提成趟次
			transportCarOut.pcmdd = $("#pcmdd").val();//提成送货地
			$.each(this.pcshlc_options,function(i,a){
				if(vm.carOutInfo.pcshlc == a.lc){
					console.log(a);
					transportCarOut.min = a.min;
					transportCarOut.max = a.max;
					transportCarOut.pcshlc = a.shlc;//送货里程
				}
			})
			
			 var baseValue = {
				 transportCarOutVehicleDetailList : b,
				 transportCarOut : transportCarOut,
				 isDelivery : isDelivery
		    }
			EasyAjax.ajax_Post_Json({
				  dataType: 'json',
			      url:base_url + '/transport/carOut/delivery/save',
			      data:JSON.stringify(baseValue),
			      errorReason: true,
			      beforeHandler: function(){
			      	loading = layer.load(0,{
			          	shade: [0.5,'#fff']
			        });
			      },
			      afterHandler: function(){
			      	layer.close(loading);
			      }
			},function(res){
				vm.isSave = true;
			    $("#pcpcTime").html(getMyDate(res.resultInfo.transportCarOut.pcpcTime));
			   layer.msg('保存成功',  {
				   icon: 1,
				   time: 2000 //2s后自动关闭
			   });	
			});
		}
	},
	
	mounted: function(){
		this._initDOM();
		this._basicPcshd();
		this._basicKhObject();
		this._basicTangci();
		this._basicPcshlc();
		this._basicDriver();
		//this._printHandler();
	},
	created: function(){
		
	},
	components: {
		'company-select': companySelect,
		'print-page': printPage
	}
})

//时间戳 毫秒转换日期
function getMyDate(str){  
	 if(str==null){
		 return "";
	 }else{
	    var subDate = new Date(str), 
	    subYear = subDate.getFullYear(),  
	    subMonth = subDate.getMonth()+1,  
	    subDay = subDate.getDate(),  
	    subHour = subDate.getHours(),  
	    subMin = subDate.getMinutes(),  
	    subSen = subDate.getSeconds(),  
	    subTime = subYear +'-'+ getzf(subMonth) +'-'+ getzf(subDay) +' '+ getzf(subHour) +':'+ getzf(subMin) +':'+getzf(subSen);//最后拼接时间  
	    return subTime;
	 }
};  
//补0操作  
function getzf(num){  
    if(parseInt(num) < 10){  
        num = '0'+num;  
    }  
    return num;  
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
		var _target = $(this).parent("ul").siblings(".selectInput");
		_target.val($(this).text());
		vm.vehicleinfofrist.sj=$(".selectInput").eq(0).val();
		vm.vehicleinfosecond.sj=$(".selectInput").eq(2).val();
		vm.vehicleinfothird.sj=$(".selectInput").eq(3).val();
		vm.vehicleinfofourth.sj=$(".selectInput").eq(4).val();
		vm.carOutInfo.pcqsd=$(".selectInput").eq(1).val();
		$(this).parent("ul").hide();		
	});
	
	$(document).click(function(){
		obj.hide();
	});
}

