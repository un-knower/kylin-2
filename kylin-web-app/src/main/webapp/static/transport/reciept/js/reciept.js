/*
created by yl in 2018/05/23
回单开发
*/

  // 用于压缩图片的canvas
  var canvas = document.createElement("canvas");
  var ctx = canvas.getContext('2d');
  //  瓦片canvas
  var tCanvas = document.createElement("canvas");
  var tctx = tCanvas.getContext("2d");
  var maxsize = 500 * 1024;
  var saveList = [];
var vm = new Vue({
   el: '#wrap',
   data: {
	   dataList:[],   //渲染列表数据
	   saveList:[],   //保存数据列表
	   fuzhiList:[],  //复制列表
	   single:[{}],     //单个校验
	   dataArray:[],  //存放所有图片
	   isCheck:true,  //是否校验
	   isShowList:false,     //图片列表默认不显示
	   checkFalse:0,         //校验失败张数
	   totalNum:0,    //选择图片总数
	   trueNum:0,  //提交成功个数
	   falseNum:0,  //提交失败个数
	   flag:false,
	   num1:0,   //不等于校验成功个数
	   isCompressFinish:false,  //是否压缩完成
	   compressNum:0    //图片加载个数
   },
   watch:{
	   dataList:{
		   handler(val, oldVal){
			   this.$nextTick(function(){
				   if(vm.dataList.length > 0){
					   var num1 = 0;
		        	   var num2 = 0;
					   for(var k in vm.dataList){
						   if(vm.dataList[k]['description'] !== "校验成功！"){
							   num1 += 1;
						   }
						   if(vm.dataList[k]['description'] == "运单号不存在！"){
							   num2 += 1;
						   }
					   }
					   if(num1 > 0){
						   $(".submit-data").attr("disabled","disabled");
						   vm.flag = false
					   }

					   if(num1 >= 0){
						   vm.checkFalse = num2;
					   }
					   if(vm.saveList.length == vm.dataList.length && num1==0){
						   vm.flag = true;
						   $(".submit-data").removeAttr("disabled");
					   }
					   
		           }else{
		        	   $('#myForm')[0].reset();
		           }
			   });
			   
           },
           deep:true
	   },
	   isCompressFinish(){
			  for(var k in vm.dataList){
				  if(vm.dataList[k]['description'] == '运单号不存在！'){
					  var curData = vm.dataList[k];
					  var curSave = vm.saveList[k];
					  vm.dataList.splice(k,1);
					  vm.saveList.splice(k,1);
					  vm.dataList.unshift(curData);
					  vm.saveList.unshift(curSave);
				  }
			  }
	   }
   },
   mounted(){
	   isTimeOut(true);
   },
   methods: {
	   
	   //选择文件
	   onFileChange(e){
		    var isLogin = isTimeOut(false);
			if(!isLogin){
				return
			}
		    var files = e.target.files || e.dataTransfer.files;
		    //如果未选择图片，不进行任何操作
		    if (!files.length) return;
		    files = Array.prototype.slice.call(files);
		    vm.dataList = [];
		    vm.saveList = [];
		    vm.fuzhiList = [];
		    vm.compressNum = 0;
		    /*console.log("打印选择的文件");
		    console.log(files);*/
		    //var path = $("#excell-file").val();
		    //var dataLen = vm.dataList.length;
		    //var totalLen = dataLen + files.length;
		    //每次最多可以选择50张
		    if (files.length > 50) {
		    	layer.open({title:'提示信息',content:'每次最多选择50张'});
		        return;
		    }
		    //图片列表最多50张
		    /*if(totalLen > 50){
        	   layer.open({title:'提示信息',content:'图片列表最多可渲染50张'});
        	   return
            }*/
		    /*var arr = [];
		    for(var i in files){
		    	arr.push(files[i]);
		    }
		    console.log(arr);
	    	for (var i = 0; i < arr.length; i++){
		    	for (var j = i + 1; j < arr.length; j++){
			    	 if (arr[i]['size'] > arr[j]['size']){
			    	  var temp = arr[i];
			    	  arr[i] = arr[j];
			    	  arr[j] = temp;
			    	 } 
		    	}
	    	}
		    console.log(arr);*/
		    files.forEach(function(file, i) {
		    	//判断文件类型，如果不是图片则不作处理
			    if (!/\/(?:jpeg|png|gif)/i.test(file.type)) return;
		        let dataObj = {
		    		src:'',        //图片
		    		photoName:'',      //图片名称
		            waybillNum:'',    //运单编号
			        description:'未校验',    //是否校验成功
			        submitResult:'未提交'    //是否上传成功
		        }
		        var initEle = 0;
	            vm.dataList.push(dataObj);
		        vm.fuzhiList.push(initEle);
		        vm.dataList[i]['photoName'] = file.name.split('.')[0];
	            var reg = RegExp(/-/);
	            if(file['name'].match(reg)){   //包含
	            	vm.dataList[i]['waybillNum'] = file.name.split('-')[0];
	            }else{
	            	vm.dataList[i]['waybillNum'] = file.name.split('.')[0];
	            }
	            var reader = new FileReader();
			    reader.readAsDataURL(file);
	            //文件读取成功以后
			    reader.onload = function() {
			        var result = this.result;
			        //创建一个img对象
			        var img = new Image();
			        img.src = result; 
			        //以base64格式读取上传的文件数据，判断数据长度，如果大于200KB的图片就调用compress方法进行压缩，否则直接上传
			        if (result.length <= maxsize) {  //图片长度小于200KB
			          //img = null;
			          //给渲染列表图片赋值
				      vm.dataList[i]['src'] = result;
			          vm.fuzhiList[i] = getFormData(result,file.type,file.name); 
				      vm.compressNum += 1;
			          if(vm.compressNum == vm.dataList.length){
				    	  vm.saveList = vm.fuzhiList;
			        	  vm.isCompressFinish = true;
			          }
			          return;
			        }else{   //大于200KB的图片进行压缩
			        	//图片加载完毕之后进行压缩，然后上传
			        	if (img.complete) {
				          callback();
				        } else {
				          img.onload = callback;
				        }
				          
			        }
			        
			        function callback() {
			          var data = compress(img);
			          //给渲染列表图片赋值
				      vm.dataList[i]['src'] = data;
				      vm.fuzhiList[i] = getFormData(data,file.type,file.name);
				      vm.compressNum += 1;
				      if(vm.compressNum == vm.dataList.length){
				    	  vm.saveList = vm.fuzhiList;
			        	  vm.isCompressFinish = true;
			          }
			          img = null;
			        }
			    }
			    
		    });
		    vm.totalNum = vm.dataList.length;
		    vm.trueNum = 0;
		    vm.falseNum = 0;
		    vm.checkFalse = 0;
		    vm.isShowList = true;
		    vm.isCheck = false;
       	    vm.checkNum();  
       },
       //点击校验按钮
       checkNum(){ 
    	   //console.log('校验')
    	   var checkList = [];
    	   for(var k in vm.dataList){
    		   checkList.push(Object.assign({}, vm.dataList[k]));
    	   }
    	   for(var k in checkList){
    		   delete checkList[k]['src'];
    		   delete checkList[k]['description'];
    	   }
    	   $.ajax({
			   url:base_url + '/transport/reorder/checkReorder',
			   type:'post',
			   data:JSON.stringify(checkList),
			   dataType:'json',
			   contentType:'application/json',
			   timeout:120000,
			   beforeSend(){
	   	        	loading = layer.load(0,{
	   	        		shade:[0.5,'#fff']
	   	        	});
	   	       },
	   	       complete(){
	   	        	layer.close(loading);
	   	       },
			   success(data){
				   //console.log(data);
				   if(data.resultCode == "200"){
					   for(var k in data.data){
						   //console.log(123110)
						   var firstDes = data.data[k]['description'];
						   vm.dataList[k]['photoName'] = data.data[k]['photoName'];
						   vm.dataList[k]['waybillNum'] = data.data[k]['waybillNum'];
						   vm.dataList[k]['description'] = firstDes;
						   if(firstDes == "运单号不存在！"){
							   layer.msg('运单编号：'+ data.data[k]['waybillNum'] + '不存在！', {time:3000});
						   }
					   }
				   }else{
					   layer.open({title:'提示信息',content:data.reason});
				   }
			   },
			   error(data){
				   //console.log(data);
				   layer.open({title:'提示信息',content:'连接超时，请检查您的网络！'});
			   }
		   });
       },
       //点击修改
       alterNum(index){
    	   $(".list-render tr").eq(index).find("input").removeAttr("disabled");
    	   $(".list-render tr").eq(index).find("input").focus();
    	   $(".list-render tr").eq(index).find("input").addClass('shadow-input');
    	   $(".list-render tr").eq(index).find(".alter-btn").hide();
    	   $(".list-render tr").eq(index).find(".sure-btn").show();
       },
       //点击确定
       sureNum(index){
    	   //console.log('我点击了确定按钮')
    	   var value = $.trim($(".list-render tr").eq(index).find("input").val());
    	   var reg = /\b[a-z0-9A-Z]{10}\b|\b[a-z0-9A-Z]{12}\b/;
    	   if(!value){
    		   layer.msg('运单编号不能为空！');
    	   }else{
    		   if(!reg.test(value)){
    			   layer.msg('运单编号必须为10或12位数字或字母组合！');
         	   }else{
         		  vm.dataList[index]['waybillNum'] = value; 
         		  vm.single[0]['photoName'] = vm.dataList[index]['photoName'];
         		  vm.single[0]['waybillNum'] = vm.dataList[index]['waybillNum'];
        	   	  $(".list-render tr").eq(index).find("input").attr("disabled","disabled");
        	      $(".list-render tr").eq(index).find(".sure-btn").hide();
        	      $(".list-render tr").eq(index).find(".alter-btn").show();
	        	  $(".list-render tr").eq(index).find("input").removeClass('shadow-input');

		       	  var isLogin = isTimeOut(false);
		   	   	  if(!isLogin){
		   	   			return
		   	   	  }
	        	  //调用单个编号验证接口
	        	  vm._singleAjax(vm.single,index);
         	   } 
    	   }
       },
       //单个运单编号验证接口
       _singleAjax(data,index){
    	   $.ajax({
    		   url:base_url + '/transport/reorder/checkReorder',
    		   type:'post',
    		   dataType:'json',
    		   data:JSON.stringify(data),
    		   contentType:'application/json',
    		   timeout:60000,
    		   beforeSend(){
	   	        	loading = layer.load(0,{
	   	        		shade:[0.5,'#fff']
	   	        	});
	   	       },
	   	       complete(){
	   	        	layer.close(loading);
	   	       },
    		   success(data){
    			  //console.log(data); 
    			  if(data.resultCode == "200"){
    				  var firstDes = data.data[0]['description'];
    				  vm.dataList[index]['description'] = firstDes;
    				  if(firstDes != "校验成功！"){
    					   var curData = vm.dataList[index];
    					   var curSave = vm.saveList[index];
    					   vm.dataList.splice(index,1);
    					   vm.saveList.splice(index,1);
    					   vm.dataList.unshift(curData);
    					   vm.saveList.unshift(curSave);
						   layer.msg('运单编号：'+ vm.dataList[index]['waybillNum'] + '不存在！', {time:3000});
					   }else{
						   var curData = vm.dataList[index];
    					   var curSave = vm.saveList[index];
    					   vm.dataList.splice(index,1);
    					   vm.saveList.splice(index,1);
    					   vm.dataList.push(curData);
    					   vm.saveList.push(curSave);
					   }
    				  //console.log(vm.dataList);
				   }else{
					   layer.open({title:'提示信息',content:data.reason});
				   }
    		   },
    		   error(data){
     			  layer.close(loading);
    			  layer.open({
    				  title:'提示信息',
    				  content:'连接超时，请检查您的网络！',
    				  yes:function(){
    					  vm.dataList[index]['description'] = '运单号不存在！';
    					  var curData = vm.dataList[index];
	   					  var curSave = vm.saveList[index];
	   					  vm.dataList.splice(index,1);
	   					  vm.saveList.splice(index,1);
	   					  vm.dataList.unshift(curData);
	   					  vm.saveList.unshift(curSave);
    					  layer.msg('运单编号：'+ vm.dataList[index]['waybillNum'] + '不存在！', {time:3000});
    				  }
    			  });
				  
    		   }
    	   });
       },
       //点击删除按钮
       delFile(index){
    	    var open = layer.open({
			    		   title:'提示信息',
			               content:'确定删除此图片吗？', 
			               shade:0.3, 
			               shadeClose:1,
			               //skin:'demoClass',  
			               moveType:1, 
			               btn:['确定','取消'], 
			               btn1:function(){ 
			            	   layer.close(open);
			            	   vm.dataList.splice(index, 1);
			            	   vm.saveList.splice(index, 1);
			            	   if(vm.dataList.length < 1){
			            		   vm.isShowList = false;
			            		   vm.isCheck = true;
			            	   }
			            	   vm.totalNum -= 1;
			            	   if(vm.falseNum > 0){
			            		   vm.falseNum -= 1;
			            	   }
			               },
			               btn2:function(){ 
			               }
			           });
       },
       //点击提交按钮
       saveImages(){
    	   if (navigator.onLine){ //正常工作
    		   //$('.bar-process').fadeIn();
    		   if(vm.flag){
        		   var isLogin = isTimeOut(false);
        	   	   if(!isLogin){
        	   			return;
        	   	   }
        	   	   vm.flag = false;
        	   	   //提交过程中，删除按钮进点
        	   	   $(".delete-btns").attr("disabled","disabled");
        	   	   for(var k in vm.dataList){
        	   		   vm.saveList[k].append('waybillNum',vm.dataList[k]['waybillNum']);
        	   	   }
		       	   let key = 0;
		       	   let saveLength = vm.saveList.length;
		       	   vm._ajaxCheck(key,vm.saveList,saveLength);
        	   }
        	   
    	   }else{ //执行离线状态时的任务
    		    layer.open({title:'提示信息',content:'亲，请检查您的网络！'});
    	   }
    	   
       },
       //提交请求
       _ajaxCheck(key,fileImg,length){
    	   $.ajax({
	   	        url: base_url + "/transport/reorder/uploadReorder",
	   	        type: 'POST',
	   	        data: fileImg[key],
	   	        processData: false, // 告诉jQuery不要去处理发送的数据
	   	        contentType: false,// 告诉jQuery不要去设置Content-Type请求头
	   	        async:false,
	   	        timeout:60000,
	   	        beforeSend(){
	   	        	loading = layer.load(0,{
		        		shade:[0.5,'#fff']
		        	});
	   	        },
	   	        complete(){
	   	        	layer.close(loading);
	   	        },
	   	        success: function (resp) {
	   	    	    //console.log(2)
	   	        	//console.log('提交请求');
	   	        	console.log(resp);
	   	        	if(resp.resultCode == 200){
	   	        		//var isSubmit = 0;
	   	        		/*for(var k in vm.dataList){
	   	        			if(vm.dataList[k]['submitResult'] == "未提交"){
	   	        				isSubmit += 1;
	   	        			}
	   	        		}*/
	   	        		if(vm.trueNum + vm.falseNum == vm.totalNum && vm.falseNum > 0){
	   	        			vm.falseNum -= 1;
	   	        		}
	   	        		vm.dataList[key]['submitResult'] = "提交成功！";
	   	        		vm.trueNum += 1;
	   	        		vm.dataList.splice(key,1);
	   	        		vm.saveList.splice(key,1);
	   	        		length -= 1;
	   	        		
	   	        	}else{
		   	        	//console.log('提交成功');
		   	        	//console.log(vm.dataList);
	   	        		vm.dataList[key]['submitResult'] = "提交失败！";
	   	        		if(vm.trueNum + vm.falseNum < vm.totalNum){
	   	        			vm.falseNum += 1;
	   	        		}
		   	        	key += 1;
	   	        	}
	   	        	if(key < length){
	   	        		setTimeout(function(){
	   	        			vm._ajaxCheck(key,fileImg,length);
	   	        		},1000);
	   	        		
	   	        	}else{
	   	        		if(vm.dataList.length > 0){
	   	        			vm.falseLength = vm.dataList.length;
	   	        			layer.open({'title':'提示信息',content:'存在提交失败数据，请检查之后重新提交！'});
	   	        		}else{
	   	        			layer.open({'title':'提示信息',content:'所选图片都已提交成功！'});
	   	        			$(".submit-data").attr("disabled","disabled");
	   	        		}
	   	        	   //提交结束，删除按钮可点
	         	   	   $(".delete-btns").removeAttr("disabled");
	   	        		
	   	        	}
	   	        },
	   	        error:function(XMLHttpRequest, textStatus, errorThrown){
	   	        	vm.dataList[key]['submitResult'] = "提交失败！";
	   	        	if(vm.trueNum + vm.falseNum < vm.totalNum){
   	        			vm.falseNum += 1;
   	        		}
	   	        	key += 1;
	   	        	if(key < length){
	   	        		setTimeout(function(){
	   	        			vm._ajaxCheck(key,fileImg,length);
	   	        		},1000);
	   	        	}else{
	   	        		layer.open({'title':'提示信息',content:'存在提交失败数据，请检查之后重新提交！'});
	   	        		//提交结束，删除按钮可点
		         	   	$(".delete-btns").removeAttr("disabled");
	   	        	}
	   	        	layer.close(loading);
	   	        }
	   	   });

       },
       //图片放大
       recieptImg(index){
		   var fileImg = vm.dataList[index]['src'];
		   var img = '<div class="layer-alert-img">'
						 +'<div class="mask-layer-container">'
						   	+'<div class="mask-layer-imgbox auto-img-center">'
					   			+'<p><img src="'+fileImg+'" style="height:100%;"/></p>'
					   		+'</div>'
					   	 +'</div>'
	     				 +'<div class="tool-bar">'
	     				 	+'<div class="toolct">'
					   			+'<span class="oper_bigger mask-out" title="放大图片"><i class="icon_tool-bigger"></i></span>'
					   			+'<span class="oper_smaller mask-in" title="缩小图片"><i class="icon_tool-smaller"></i></span>'
					   			+'<span class="oper_rotate mask-clockwise" title="向右旋转"><i class="icon_tool-rotate"></i></span>'
		   					+'</div>'
				   		 +'</div>'
				    +'</div>';
		   layer.open({    
		        type: 1,      
		        title: false, //不显示标题    
		        area:['90%','90%'],    
		        content: img, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响    
		        //成功弹出执行的函数
		        success: function(layero, index){
		            showImg();
		        }   
    	   });
         }
       }
   });

	//使用canvas对大图片进行压缩
	function compress(img) {
	  var initSize = img.src.length;
	  var width = img.width;
	  var height = img.height;
	  //如果图片大于八百万像素，计算压缩比并将大小压至800万以下
	  var ratio;
	  if ((ratio = width * height / 6000000) > 1) {
	    ratio = Math.sqrt(ratio);  //平方根
	    width /= ratio;
	    height /= ratio;
	  } else {
	    ratio = 1;
	  }
	  canvas.width = width;
	  canvas.height = height;
	  //铺底色
	  ctx.fillStyle = "#fff";
	  ctx.fillRect(0, 0, canvas.width, canvas.height);
	  //如果图片像素大于100万则使用瓦片绘制
	  var count;
	  if ((count = width * height / 1000000) > 1) {
	    count = ~~(Math.sqrt(count) + 1); //计算要分成多少块瓦片
	  //计算每块瓦片的宽和高
	    var nw = ~~(width / count);
	    var nh = ~~(height / count);
	    tCanvas.width = nw;
	    tCanvas.height = nh;
	    for (var i = 0; i < count; i++) {
	      for (var j = 0; j < count; j++) {
	        tctx.drawImage(img, i * nw * ratio, j * nh * ratio, nw * ratio, nh * ratio, 0, 0, nw, nh);
	        ctx.drawImage(tCanvas, i * nw, j * nh, nw, nh);
	      }
	    }
	  } else {
	    ctx.drawImage(img, 0, 0, width, height);
	  }
	  //进行最小压缩
	  var ndata = canvas.toDataURL('image/jpeg', 0.3);
	  /*console.log('压缩前：' + initSize);
	  console.log('压缩后：' + ndata.length);
	  console.log('压缩率：' + ~~(100 * (initSize - ndata.length) / initSize) + "%");*/
	  tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;
	  return ndata;
	}
	
	//获取每个blob对象
	function getFormData(baseStr,type,name){
		var text = window.atob(baseStr.split(",")[1]);
	    var buffer = new Uint8Array(text.length);
	    for (var i = 0; i < text.length; i++) {
	      buffer[i] = text.charCodeAt(i);
	    }
	    var blob = getBlob([buffer], type);
	    var xhr = new XMLHttpRequest();
	    var formData = new FormData();
	    /*formData["imgFile"]= blob;
	    formData['photoName'] = name;
	    formData['src'] = baseStr;*/
	    formData.append("imgFile", blob);
	    formData.append("photoName",name);
	    return formData;
	}
	/**
	   * 获取blob对象的兼容性写法
	   * @param buffer
	   * @param format
	   * @returns {*}
	   */
    function getBlob(buffer, format) {
	    try {
	      return new Blob(buffer, {type: format});
	    } catch (e) {
	      var bb = new (window.BlobBuilder || window.WebKitBlobBuilder || window.MSBlobBuilder);
	      buffer.forEach(function(buf) {
	        bb.append(buf);
	      });
	      return bb.getBlob(format);
	    }
	}
    
    function isTimeOut(bool){
    	var isL = true;
    	EasyAjax.ajax_Post_Json({
    		dataType: 'json',
            url: base_url + '/check/authentication',
            cancelPage: true,
            alertMsg: bool,
            async: false
        },
        function (data) {
        	companyName = data.company;
        	if(data.resultCode == 400){
        		isL = false;
        		if(bool){
        			layui.use(['form','element'],function(){
            			var form = layui.form,
            				element = layui.element;
            			layer.open({
            				content: '登录失效，将影响您的操作<br/>是否重新登陆？',
            				btn: ['是','否'],
            				shade: [1,'#fff'],
            				yes: function(){
            					window.top.location.href = base_url + "/views/loginPage.jsp";
            				},
            				btn2: function(){
            					
            				}
            			})
            		})
        		}
        	}else{
        		isL = true;
        	}
        });
    	return isL;
    }
    

    
    //图片查看器
    function showImg() {
        //图片居中显示
        /*var box_width = $(".auto-img-center").width(); //图片盒子宽度
        var box_height = $(".auto-img-center").height();//图片高度高度
        var initial_width = $(".auto-img-center img").width();//初始图片宽度
        var initial_height = $(".auto-img-center img").height();//初始图片高度
        if (initial_width > initial_height) {
            $(".auto-img-center img").css("width", box_width);
            var last_imgHeight = $(".auto-img-center img").height();
            $(".auto-img-center img").css("margin-top", -(last_imgHeight - box_height) / 2);
        } else {
            $(".auto-img-center img").css("height", box_height);
            var last_imgWidth = $(".auto-img-center img").width();
            $(".auto-img-center img").css("margin-left", -(last_imgWidth - box_width) / 2);
        }*/

        //图片拖拽
        var $div_img = $(".mask-layer-imgbox p");
        //绑定鼠标左键按住事件
        $div_img.bind("mousedown", function (event) {
            event.preventDefault && event.preventDefault(); //去掉图片拖动响应
            //获取需要拖动节点的坐标
            var offset_x = $(this)[0].offsetLeft;//x坐标
            var offset_y = $(this)[0].offsetTop;//y坐标
            //获取当前鼠标的坐标
            var mouse_x = event.pageX;
            var mouse_y = event.pageY;
            //绑定拖动事件
            //由于拖动时，可能鼠标会移出元素，所以应该使用全局（document）元素
            $(".mask-layer-imgbox").bind("mousemove", function (ev) {
                // 计算鼠标移动了的位置
                var _x = ev.pageX - mouse_x;
                var _y = ev.pageY - mouse_y;
                //设置移动后的元素坐标
                var now_x = (offset_x + _x ) + "px";
                var now_y = (offset_y + _y ) + "px";
                //改变目标元素的位置
                $div_img.css({
                    top: now_y,
                    left: now_x
                });
            });
        });
        //当鼠标左键松开，接触事件绑定
        $(".mask-layer-imgbox").bind("mouseup", function () {
            $(this).unbind("mousemove");
        });

        //缩放
        var zoom_n = 1;
        $(".mask-out").click(function () {
            zoom_n += 0.1;
            $(".mask-layer-imgbox img").css({
                "transform": "scale(" + zoom_n + ")",
                "-moz-transform": "scale(" + zoom_n + ")",
                "-ms-transform": "scale(" + zoom_n + ")",
                "-o-transform": "scale(" + zoom_n + ")",
                "-webkit-": "scale(" + zoom_n + ")"
            });
            layer.msg(parseInt(zoom_n*100) + '%');
        });
        $(".mask-in").click(function () {
            zoom_n -= 0.1;
            if (zoom_n <= 0.1) {
                zoom_n = 0.1;
                $(".mask-layer-imgbox img").css({
                    "transform":"scale(.1)",
                    "-moz-transform":"scale(.1)",
                    "-ms-transform":"scale(.1)",
                    "-o-transform":"scale(.1)",
                    "-webkit-transform":"scale(.1)"
                });
            } else {
                $(".mask-layer-imgbox img").css({
                    "transform": "scale(" + zoom_n + ")",
                    "-moz-transform": "scale(" + zoom_n + ")",
                    "-ms-transform": "scale(" + zoom_n + ")",
                    "-o-transform": "scale(" + zoom_n + ")",
                    "-webkit-transform": "scale(" + zoom_n + ")"
                });
            }
            layer.msg(parseInt(zoom_n*100) + '%');
        });
        //旋转
        var spin_n = 0;
        $(".mask-clockwise").click(function () {
            spin_n += 90;
            $(".mask-layer-imgbox img").parent("p").css({
                "transform":"rotate("+ spin_n +"deg)",
                "-moz-transform":"rotate("+ spin_n +"deg)",
                "-ms-transform":"rotate("+ spin_n +"deg)",
                "-o-transform":"rotate("+ spin_n +"deg)",
                "-webkit-transform":"rotate("+ spin_n +"deg)"
            });
        });
        $(".mask-counterclockwise").click(function () {
            spin_n -= 90;
            $(".mask-layer-imgbox img").parent("p").css({
                "transform":"rotate("+ spin_n +"deg)",
                "-moz-transform":"rotate("+ spin_n +"deg)",
                "-ms-transform":"rotate("+ spin_n +"deg)",
                "-o-transform":"rotate("+ spin_n +"deg)",
                "-webkit-transform":"rotate("+ spin_n +"deg)"
            });
        });
    }

    
    
    

   	/*if(!errorres && i == len-1){
   		if(needAlert && data.fhshj){
   			$('.bar-process').fadeOut(function(){
	        		element.progress('demo', '0%');
	        		layer.msg(data.fhshj, {
	        			icon: 5,
	        			anim:6
	        		});
       		});
   		}else{
   			$('.bar-process').fadeOut(function(){
	        		element.progress('demo', '0%');
	        		layer.open({
		        		 content: '存在失败数据，<br/>请检查数据并重新提交'
		        	});
       		});
   		}
   	 }
   	i ++;
    if(i < len){
    	setTimeout(function(){
    		checkHandlers._checkAjax(i,obj, len, tabledata)
        },100);
    }
    processNum += parseInt(percent); 进度条
	if(processNum > 99){
		element.progress('demo', '99%');
	}else{
		element.progress('demo', processNum + '%');
	}*/
	
    