<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Kylin-麒麟系统</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
<link href="${ctx_static}/common/custom.css" rel="stylesheet" />
<style>
	[v-cloak]{display:none;}
	@media screen and (min-width: 1200px) and (max-width:1920px) {
        #navigation .nav-list{height:100%;overflow-y:scroll;}
    }
</style>
</head>
<body style="overflow:hidden;">
	<div id="kyapp" style="height:100%;" v-cloak>
		<div id="navigation">
			<h1><img src="${ctx_static}/common/images/logo.png"></h1>
			<h1 class='show-logo'><img src="${ctx_static}/common/images/logo.png"></h1>
		    <div class="nav-list">
			  <ul>
			  	  <li class="nav1 parent-li" v-for="(item,index) in menuList">
				    <a href="javascript:;" class="parent-menu" @click="menuParent($event,index)"><i class="layui-icon menu-icon">&#xe7a0;</i>{{item.name}}<i class="icon"></i></a>
				    <ul class="sub-menu">
				          <!-- <li v-for="(value,key) in item.list"><a :href="value.url" data-url="value.url" target="content_frame_name" class="tab-target list-group-item">{{value.name}}</a></li> -->
						  <li v-for="(value,key) in item.list"><a href="javascript:void(0)" :data="value.url" target="" class="tab-target list-group-item">{{value.name}}</a></li> 
					</ul>
				  </li>    
			   </ul>
		    </div>
		    <div class='nav-controll'>
				<i class='line_1 line_hide'></i>
				<i class='line_2 line_hide'></i>
				<i class='line_3 line_show'></i>
				<i class='line_4 line_show'></i>
			</div>
		</div>
		<div id="content">
			<div>
				<div class="headMenu">
					<div class="headBox">
						<font color="#FFFFFF">当前用户&nbsp&nbsp</font>
						<font class="tips">当前用户: ${ygzl.userName}--所属公司:[${ ygzl.company }]</font>
						&nbsp&nbsp&nbsp&nbsp
						<a href="${base_url}/logout">
							<font color="#FAFFBD" style="text-decoration:underline;">安全退出 </font>
						</a>
						<span class='username' style='display: none;'>${ user.userName }</span>
						&nbsp&nbsp
					</div>	
				</div>
			</div>
			
			<div class="layui-tab layui-tab-card" lay-filter='tabContent' lay-allowclose='true'>
				<ul class='layui-tab-title tab-ul' id='tab-ul'>
					<!-- *<li class='layui-this first-tab' lay-id='1'>运单查询</li> * -->
				</ul>
		  		<div class='layui-tab-content' style='padding: 0px 0;'>
					<!-- *<div class='layui-tab-item layui-show'>
						<iframe id="content_frame" name="content_frame_name" frameborder="no" width="100%" height="100%"></iframe>
					</div> * -->
				</div>
			</div>
				           
			<!-- <div class="content-div" style="padding: 0px;">
				<iframe id="content_frame" name="content_frame_name" frameborder="no" width="100%" height="100%"></iframe>
			</div> -->
		</div>
	</div>
</body>
<script src="${ctx_static}/common/jquery/jquery-1.12.2.min.js" type="text/javascript"></script>
<script src="${ctx_static}/common/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
<script src="${ctx_statics}/libs/vue.min.js"></script>
<script src="${ctx_statics}/libs/router.js"></script>

<script type="text/javascript">
var username = $('.username').html();
var base_url = '${base_url}';
var company = '${ user.companyString }';
company = company.split('-')[0];
$(document).ready(function(){
	
	//转码
	function cjkEncode(text) {                                                                            
      if (text == null) {         
        return "";         
      }         
      var newText = "";         
      for (var i = 0; i < text.length; i++) {         
        var code = text.charCodeAt (i);          
        if (code >= 128 || code == 91 || code == 93) {  //91 is "[", 93 is "]".         
          newText += "[" + code.toString(16) + "]";         
        } else {         
          newText += text.charAt(i);         
        }         
      }         
      return newText;         
    } 
	$(".content-div").height( $(window).height() - 46);
	$(".navigation").height( $(window).height() - 46);
	$("#content_frame").attr("src", "${base_url}/transport/convey/manage");
	$(window).resize(function(){
			$(".content-div").height( $(window).height() - 46);
			$(".navigation").height( $(window).height() - 46);
			$("#content_frame").load(function() {
					$(this).height($(".content-div").height());
					$(this).width(Math.max($(window).height(), $(".content-div").width()));
				
			});
	});
	layui.use('element',function(){
		var element = layui.element;
		//控制导航栏显示
		var controll = false;
		$('.nav-controll').on('click',function(){
			// controll = !controll;
			if(controll){
				$('.line_hide').css({
					opacity: 1,
					top: '0px'
				});
				$('.line_show').css({
					transform: 'translate(0%,0%) rotate(0deg)'
				});
				$('#navigation').css({
					left: '-160px'
					/* transform: 'translateX(-100%)' */
				});
				$('#content').css({
					padding: '0 0 60px 0px'
				})
			}else{
				$('.line_hide').css({
					opacity: 0,
					top: '10px'
				});
				$('.line_3').css({
					transform: 'translate(3%,-200%) rotate(45deg)'
				});
				$('.line_4').css({
					transform: 'translate(3%,-600%) rotate(-45deg)'
				});
				$('#navigation').css({
					left: '0%'
					/* transform: 'translateX(0%)' */
				});
				$('#content').css({
					padding: '0 0 60px 160px'
				})
			}
		})
		
	})   
});
var vm = new Vue({
	el:'#kyapp',
	data:{
		user:{},
		menuList:{},
		main:"main.html",
		password:'',
		newPassword:'',
        navTitle:"控制台"
	},
	watch:{
		'menuList':function(){
       		this.$nextTick(function(){
       			layui.use('element',function(){
               		var element = layui.element;
                    //点击子级菜单
    				$('.tab-target').each(function(index,elem){ 
    					$(elem).on('click', function(){
    						var url = $(elem).attr('data');
    						var isExist = false;
    						$('#tab-ul li').each(function(num,item){
    							if($(item).attr('lay-id') == index+1){
    								element.tabChange('tabContent', index+1);
    								isExist = true;
    							}
    						})
    						if(!isExist){
    							var title = $(elem).html();
    							vm.addTab(element,title,index+1,url);
    							element.tabChange('tabContent', index+1);
    						}
    						vm.navChange();
    					})
    				}) 
               	})
       		});
			
		}
	},
	created: function(){
		this.getMenuList();
		//this.getUser();
	},
	
	mounted:function(){
		
	},
	methods: {
		getMenuList: function (event) {
			$.getJSON("${base_url}/sys/menu/nav?_"+$.now(), function(r){
				vm.menuList = r.menuList;
			});
		},
		getUser: function(){
			$.getJSON("${base_url}/sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		},
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['806px', '467px'],
                closeBtn: 1,
                shadeClose: false,
                content: ['http://cdn.renren.io/donate.jpg', 'no']
            });
        },
        //点击父级菜单
        menuParent:function(event,index){
        	var el = event.currentTarget;
        	var $li = $(el).parent();
    		var $selected = $li.hasClass('selected');
    		if($selected){
    			$li.removeClass('selected');
    			$li.find('.sub-menu').stop().slideUp(200);
    		}else{
    			$li.addClass('selected').siblings().removeClass('selected');
    			$li.find('.sub-menu').stop().slideDown(200);
    			$li.siblings().find('.sub-menu').stop().slideUp(200);
    		}
        },
        //点击子级菜单
        /* menuChild:function(event,index){
        	console.log("index值,",index)
        	layui.use('element',function(){
        		var element = layui.element;
   				var el = event.currentTarget;
   				var url = $(el).attr("data");
   				var isExist = false;
   				console.log("获取tab长度，",$('#tab-ul li').length)
   				$('#tab-ul li').each(function(num,item){
   					if($(item).attr('lay-id') == index+1){
   						console.log("判断是否进入tab循环")
   						console.log($(item))
   						console.log($(item).attr('lay-id'))
   						
   						element.tabChange('tabContent', index+1);
   						isExist = true;
   					}
   				})
   				if(!isExist){
   					var title = $(el).html();
   					vm.addTab(element,title,index+1,url);
   					element.tabChange('tabContent', index+1);
   				}
   				vm.navChange();
   				//左侧小菜单添加active
   				$(".list-group-item").removeClass("active");
   				$(el).addClass("active");
        	});
        }, */
        //新增tab项
        addTab:function(element,titleName,index,url){
    			if(url.indexOf("http:")!=-1){
    				var FRaddr = url+"?company="+cjkEncode(company);
    				var content = "<iframe src="+FRaddr+" frameborder='no' width='100%' height='100%'></iframe>"
    			}else{
    				var content = "<iframe src="+base_url+url+"?#username="+username+" frameborder='no' width='100%' height='100%'></iframe>"
    			}
    			element.tabAdd('tabContent',{
    				title: titleName
    				,content: content
    				,id: index
    			})
        },
        //导航栏切换
        navChange:function(){
        	$('li[lay-id]').each(function(i,a){
				$(a).unbind('click').click(function(){
					var text = $(a).contents().filter(function(index,content){
						if(index == 0){
							return content;
						}
					}).text();
					$('.sub-menu').each(function(k,j){
						var LiTogether = $(j).find('li');
						LiTogether.each(function(q,e){
							var navValue = $(e).find('a').text();
							if(navValue == text){
								LiTogether.find('a').removeClass('active');
								$(e).find('a').addClass('active');
								var ulParent = $(j).parent();
								if(!ulParent.hasClass('selected')){
									$('.sub-menu').slideUp().parent().removeClass('selected');
									$(j).slideDown().parent().addClass('selected');
								}
							}
						})
					})
				})
			})
        }
		
	},
	updated: function(){
		//路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
	},
});
function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			routerList(router, menu.list);
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				
				//替换iframe的url
			    vm.main = url.replace('#', '');
			    
			    //导航菜单展开
			    $(".treeview-menu li").removeClass("active");
			    $("a[href='"+url+"']").parents("li").addClass("active");
			    
			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}
	}
}

</script>
</html>