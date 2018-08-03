/*
 * created by yl on 2018/05/08
 *  货运异常记录审核页面
 */

    var vm = new Vue({
    	el:'#rrapp',
    	data:{
    		data:{},       //传参对象
    		infoList:{},     //信息列表
    		detailList:[],   //细则列表
    		ideaData:{       //录入处理意见数据   
    			'responsibility':'',     //责任方
    			'noticeCompany':'',      //通知公司'
    			'noticeCompanyOpinion':'',//通知公司处理意见
    			'dzdwOpinion':'',       //到站单位意见
    			'dzdwName':'',    //到站单位负责人
    			'dzdwTime':'',    //到站单位处理时间
    			'fzdwOpinion':'',    //发站单位意见
    			'fzdwName':'',    //发站单位负责人
    			'fzdwTime':'',    //发站单位处理时间
    		},
    		respDisabled:true,           //责任方不可填
    		informDisabled:false,         //通知公司可填
    		informOpinionDisabled:true,  //通知公司不可填
    		isFazhanShow:false   //发站意见栏不显示
    	},
    	created: function () {
    		// `this` 指向 vm 实例
    		this.getCheckData();
    	},
	  	mounted:function(){
	  		$("#rrapp input").attr("autocomplete","off");
	  		this._saveOpinion();
		},
 		watch:{ 
 			//监控录入处理意见数据变化
 	        'ideaData.dzdwOpinion':function(oldVal,newVal){
 	        	if(vm.ideaData.dzdwName == ""){
 	        		vm.ideaData.dzdwName = userName;
 	        	}
 	        	if(vm.ideaData.dzdwTime == ""){
 	        		var curTime = moment().format('YYYY-MM-DD HH:mm:ss');
 	    			vm.ideaData.dzdwTime = curTime
 	        	}
    			
        		if(!this.ideaData.dzdwOpinion){
        			vm.ideaData.dzdwTime = '';
        			vm.ideaData.dzdwName = '';
        		}
 	    			
 	        },
 	        //监控录入处理意见数据变化
 	        'ideaData.fzdwOpinion':function(val){
 	        	if(vm.ideaData.fzdwName == ""){
 	        		vm.ideaData.fzdwName = userName;
 	        	}
 	        	if(vm.ideaData.fzdwTime == ""){
 	        		var curTime = moment().format('YYYY-MM-DD HH:mm:ss');
 	 				vm.ideaData.fzdwTime = curTime;
 	        	}
 	        	if(!this.ideaData.fzdwOpinion){
        			vm.ideaData.fzdwTime = '';
        			vm.ideaData.fzdwName = '';
        		}
 	        }	
        },
    	methods: {
    		  getCheckData:function(){
    			  var _this=this;
  			  	  var baseValue=window.location.href.split('=')[1];
  			  	  _this.data.id = baseValue;
    			  layui.use('layer', function(){
	    				$.ajax({
	    					url: base_url + '/transport/query/freightRecordDetail',
	    					type: 'post',
	    					dataType: 'JSON',
	    					contentType: 'application/json',
	    					data: JSON.stringify(_this.data),
	    					beforeSend: function(){
	    						 loading = layer.load(0,{
	    							shade: [0.5,'#fff']
	    						}) 
	    					},
	    					complete:function(){
	    						layer.close(loading);
	    					},
	    					success: function(data){
	    						_this.infoList = data.data.freightRecord;
	    						if(data.data.freightRecord.abnormalType == "0"){
	    							_this.infoList.abnormalType = "干线";
	    						}else if(data.data.freightRecord.abnormalType == "1"){
	    							_this.infoList.abnormalType = "配送";
	    						}else if(data.data.freightRecord.abnormalType == "2"){
	    							_this.infoList.abnormalType = "提货";
	    						}
	    						_this.detailList = data.data.freightRecordDetailList;
	    						
	    						//获取是否处理字段
	    						var isHandle = _this.infoList.isHandle;
	    						//获取通知公司
	    						var noticeCompany = _this.infoList.noticeCompany;
	    						//获取通知公司处理意见
	    						var noticeCompanyOpinion = _this.infoList.noticeCompanyOpinion;
	    						//获取到站意见栏信息
	    						var getOpinion1 = _this.infoList.dzhyj;
	    						var getResp1 = _this.infoList.dzdwlrr;
	    						var getTime1 = _this.infoList.dzdwlrsj;
	    						//获取发站意见栏信息
	    						var getOpinion2 = _this.infoList.fzhyj;
	    						var getResp2 = _this.infoList.fzdwlrr;
	    						var getTime2 = _this.infoList.fzlrsj;
	    						//获取责任方
	    						var responsibility = _this.infoList.responsibility;
	    						//获取发站
	    						var fazhan = _this.infoList.fazhan;
	    						//获取到站
	    						var daozhan = _this.infoList.daozhan; 

    							_this.ideaData.noticeCompany = noticeCompany;
    							_this.ideaData.noticeCompanyOpinion = noticeCompanyOpinion;
    							_this.ideaData.responsibility = responsibility;
    							
	    						//判断此记录是否处理
	    						if(isHandle == "1"){ //已处理，说明此时登录公司是通知公司（即通知公司已经填写），通知意见可以填写，保存可点
	    							if(fazhan != daozhan){ //发站!=到站，发站意见栏显示
	    								_this.isFazhanShow = true;
	    							} 
	    							//通知公司意见可填，保存可点（保存的是通知意见）
	    							_this.informOpinionDisabled = false;
	    							_this.informDisabled = true;
									$(".resp-opinion input").attr("disabled","disabled");
	    							//到站、发站信息
	    							_this.ideaData.dzdwOpinion = getOpinion1;
	    							_this.ideaData.dzdwName = getResp1;
	    							_this.ideaData.dzdwTime = getTime1;
	    							_this.ideaData.fzdwOpinion = getOpinion2;
	    							_this.ideaData.fzdwName = getResp2;
	    							_this.ideaData.fzdwTime = getTime2;
	    							
	    						}else{ //未处理
	    							//判断发站是否等于到站，说明到站负责人一定没有填写
	    							if(fazhan == daozhan){ //发站=到站，发站意见栏不显示，通知公司一定没有填写（此时登录公司一定是发站公司）
	    								//通知公司可填写、通知意见不可填写，责任方必填，保存可点（保存到站负责人）
	    								_this.respDisabled = false;
	    								$("#responsibility").attr("lay-verify","mustFill");
	    							}else{   //发站!=到站，发站意见栏显示
	    								_this.isFazhanShow = true;
	    								//判断通知公司是否填写
		    							if(noticeCompany){ //通知公司填写，此时说明到站意见栏一定填写，发站意见栏一定未填
	    									_this.informDisabled = true;
	    									//到站公司信息
    										_this.ideaData.dzdwOpinion = getOpinion1;
    		    							_this.ideaData.dzdwName = getResp1;
    		    							_this.ideaData.dzdwTime = getTime1;
	    									$(".daozhan-wrap input").attr("disabled","disabled");
		    								//判断登录公司
		    								if(userCompany == daozhan){   //登录公司=到站公司,所有框禁点，通知公司、责任方、保存禁点，发站意见栏隐藏
		    									_this.isFazhanShow = false;
		    									$("#checkBtn").attr("disabled","disabled");
	    										//提示到站意见栏已经提交审核，不可重复提交
	    										layer.open({title:'提示信息',content:'到站负责人已经提交审核，不可重复提交！'});
		    								}else if(userCompany == fazhan){   //登录公司=发站公司,通知公司、责任方禁点，发站意见栏显示，保存发站意见
		    									$(".fazhan-wrap input").attr("lay-verify","mustFill|lengthLimit");
		    								}else if(userCompany == noticeCompany){   //登录公司=通知公司，通知公司、责任方禁点，发站意见栏显示但不可点，通知公司处理意见可填，保存通知意见
		    									_this.informOpinionDisabled = false;
		    									$(".fazhan-wrap input").attr("disabled","disabled");
		    								}
		    							}else{    //通知公司未填写
		    								//判断登录公司
		    								if(userCompany == daozhan){   //登录公司=到站公司，发站意见栏隐藏
		    									_this.isFazhanShow = false;
		    									//判断到站意见栏是否填写
		    									if(responsibility){  //填写，通知公司、责任方、保存禁点
		    										//到站信息
		    										_this.ideaData.dzdwOpinion = getOpinion1;
		    		    							_this.ideaData.dzdwName = getResp1;
		    		    							_this.ideaData.dzdwTime = getTime1;
		    										_this.informDisabled = true;
		    										$("#checkBtn").attr("disabled","disabled");
		    										$(".daozhan-wrap input").attr("disabled","disabled");
		    										//提示到站意见栏已经提交审核，不可重复提交
		    										layer.open({title:'提示信息',content:'到站负责人已经提交审核，不可重复提交！'});
		    									}else{   //未填写，通知公司、责任方必填，保存可点
		    										_this.respDisabled = false;
		    										$("#responsibility").attr("lay-verify","mustFill");
		    									}
		    								}else if(userCompany == fazhan){   //登录公司=发站公司,通知公司、责任方禁点，发站意见栏可填
		    									//判断到站意见栏是否填写
		    									if(responsibility){  //填写，责任方禁点，发站意见栏可填、通知公司可填、保存可点
		    										//到站信息
		    										_this.ideaData.dzdwOpinion = getOpinion1;
		    		    							_this.ideaData.dzdwName = getResp1;
		    		    							_this.ideaData.dzdwTime = getTime1;
		    										$(".daozhan-wrap input").attr("disabled","disabled");
		    										$(".fazhan-wrap input").attr("lay-verify","mustFill|lengthLimit");
		    									}else{   //未填写，通知公司、责任方、发站意见栏禁填、保存禁点
		    										_this.informDisabled = true;
		    										$("#checkBtn").attr("disabled","disabled");
		    										$(".resp-opinion input").attr("disabled","disabled");
		    										//提示到站意见栏未提交审核，发站负责人不可提交审核
		    										layer.open({title:'提示信息',content:'到站负责人未提交审核，发站负责人不可提交审核！'});
		    									}
		    								}
		    							}
	    							}
	    						}
	    						
	    					},
	    					error: function(data){
	    						console.log(data);
	    					}
	    				});
    			  });
    		  },
    		  //录入处理意见数据
    		  _saveOpinion:function(){
					var _this = this;
					layui.use('form',function(){
						var form = layui.form;
						form.verify({
							//必填项
							mustFill:function(value,item){ 
				        	  if(value == ""){
				        		  return $(item).attr("dataname")+'不能为空！';
				        	  }
					        },
					        //判断通知公司
					        noticeCompany:function(value,item){
					        	//获取发站
	    						var fazhan = _this.infoList.fazhan;
	    						//获取到站
	    						var daozhan = _this.infoList.daozhan; 
					        	if(value != ""){
					        		if(value == fazhan || value == daozhan){
					        			return $(item).attr("dataname")+'不能为装车发站或装车到站！';
					        		}
					        	  }
					        },
					        //判断意见长度
					        lengthLimit:function(value,item){
					        	if(value != ""){
					        		var length = getByteLen(value);
					        		if(length > 50){
					        			return $(item).attr("dataname")+"最多只能录入50个字符！";
					        		}
					        	}
					        	
					        }
					        
						});
						form.on('submit(checkBtn)', function(data){
							//异常记录id、发站、到站
							_this.ideaData.id = _this.data.id;
							_this.ideaData.fazhan = _this.infoList.fazhan;
							_this.ideaData.daozhan = _this.infoList.daozhan;
				       	    $.ajax({
								url: base_url + '/transport/addComments',
					            type: 'post',
					            dataType: 'JSON',
					            contentType: 'application/json',
					            data: JSON.stringify(_this.ideaData),
					            beforeSend: function(){
					                loading = layer.load(0,{
					                  shade: [0.5,'#fff']
					               }) 
					            },
					            complete:function(){
					               layer.close(loading);
					            },
					            success: function(data){
					            	if(data.resultCode == "200"){
					            		layer.msg('审核成功！');
					            		$("#checkBtn").attr("disabled","disabled");
					            		$(".freight-table input").attr("disabled","disabled");
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
				       	    return false;
						});
					});
		            
			  }
    	}
    });
   