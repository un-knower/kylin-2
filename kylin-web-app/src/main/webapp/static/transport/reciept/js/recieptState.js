/*
created by yl in 2018/05/26
回单查看
*/


var vm = new Vue({
   el: '#rrapp',
   data: {
	   imgList:[],   //渲染列表数据
	   isShowList:false
   },
   watch:{
	  imgList:{
		  handler(val, oldVal){
			  this.$nextTick(function(){
				  var len = $("img[modal='zoomImg']").length;
			      $("img[modal='zoomImg']").each(function () {
				    $(this).on("click", function () {
				    	   var $index = $(this).attr("data-num");//获取点击的索引值
				           var num = $index;
						   var img = '<div class="content-imgs">'
									   +'<div class="cont-img">'
										 +'<div class="mask-layer-container">'
										   	+'<div class="mask-layer-imgbox auto-img-center">'
									   			/*+'<p><img src="'+fileImg+'" style="height:100%;width:100%;"/></p>'*/
									   		+'</div>'
									   	 +'</div>'
									   	 +'<div class="oper">'
											+'<span class="prev mask-prev"><i class="icon_tool-prev"></i></span>'
											+'<span class="next mask-next"><i class="icon_tool-next"></i></span>'
										 +'</div>'
										 +'<div class="tool-bar">'
										 	+'<div class="toolct">'
									   			+'<span class="oper_bigger mask-out" title="放大图片"><i class="icon_tool-bigger"></i></span>'
									   			+'<span class="oper_smaller mask-in" title="缩小图片"><i class="icon_tool-smaller"></i></span>'
									   			+'<span class="oper_rotate mask-clockwise" title="向右旋转"><i class="icon_tool-rotate"></i></span>'
									   		+'</div>'
								   		 +'</div>'
								   		+'<span class="layui-layer-ico cont-img-close mask-close" style="background-position: -149px -31px;"></span>'
								      +'</div>';
							      +'</div>';
						   $("body").append(img);
					       showImg(num);
					       //下一张
					       $(".mask-next").on("click", function () {
					    	   $(".mask-layer-imgbox p").remove();
					           num++;
					           if (num == len) {
					        	   num = 0;
					           }
					           showImg(num);
					       });
					       //上一张
					       $(".mask-prev").on("click", function () {
					    	   $(".mask-layer-imgbox p").remove();
					       	   num--;
					           if (num == -1) {
					        	   num = len - 1;
					           }
					           showImg(num);
					       });
				    });
				});
	          }); 
          },
          deep:true
	  }
   },
   created: function () {
		// `this` 指向 vm 实例
		this.searchInfo();
   },
   methods: {
	   //查询数据
	   searchInfo:function(){
	  	  var baseValue=window.location.href.split('=')[1];
	  	  console.log(baseValue)
		  $.ajax({
				url: base_url + '/transport/reorder/selectReceipt?ydbhid='+baseValue,
				type: 'get',
				dataType: 'JSON',
				contentType: 'application/json',
				beforeSend: function(){
					 loading = layer.load(0,{
						shade: [0.5,'#fff']
					}) 
				},
				complete:function(){
					layer.close(loading);
				},
				success: function(data){
					//console.log(data);
					if(data.resultCode == "200"){
						console.log(data.data)
						vm.imgList = data.data;
					}else{
						layer.open({title:"提示信息",content:data.reason});
					}
				},
				error: function(data){
					console.log(data);
				}
		 });
	   }
   	  }
   });
	 
	
	function showImg(num) {
		$(".mask-layer-imgbox").append('<p><img src="" style="height:100%;"/></p>');
		$(".mask-layer-imgbox img").prop("src", vm.imgList[num]['imgAddress']); //给弹出框的Img赋值
		
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
	    //关闭
        $(".mask-close").click(function () {
            $(".content-imgs").remove();
        });
	    
	}
	

	