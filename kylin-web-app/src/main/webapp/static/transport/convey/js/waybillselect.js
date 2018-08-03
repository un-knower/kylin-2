var dzshhdList = new Array();
var selectType= new Array();
var selectFazhan= new Array();

function stopPropagation(e) {//把事件对象传入
	if (e.stopPropagation){ //支持W3C标准 doc
		e.stopPropagation();
	}else{ //IE8及以下浏览器
		e.cancelBubble = true;
	}
}

	$(".dzLi").each(function(i,item){
		$(".dzLi").eq(i).text();
		selectType[i]=($(".dzLi").eq(i).text());
		
	});



//输入匹配下拉框
function selectList(){
	$(".selectInput").each(function(){
		$(this).bind("focus click",function(e){
			$(".selectUl").hide();
			stopPropagation(e);
			if($(this).siblings("ul").find("li").length>0){
		    	$(this).siblings("ul").show();
			    $(this).siblings("ul").find("li").show();
			}
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
			  };
		});	
	});	
}

//检查到站及发站网点
function checkInputSelect(){
	var _dazhanText=$("#daozhan").val();
	var _shhdText=$("#shhd").val();
	var flag0=false;
	var flag1=false;
	if(_dazhanText!=""){
		if($.inArray(_dazhanText, selectType)==-1){
			$.util.warning("到站不存在！");
			$("#daozhan").val("");
			return false;
	   }else{
		    return true;
	   }
	};
/*	if(_shhdText!=""){
		if($.inArray(_shhdText, selectFazhan)==-1){
			$.util.warning("发站网点不存在！");
			flag1=false;
	    }else{flag1=true;}
	}else{
		flag1=true;
	};
	
	if(flag0 && flag1){
		return true;
	}else{
		return false;
	}
	*/
}
function selectInput(obj){
	 obj.find("li").unbind("click").click(function(){
		var _target = $(this).parent("ul").siblings("input");
		var _this=$(this);
		_target.val(_this.text());
		if(_target.attr("id")=="daozhan"){
			getPoint();
		}
	 $(this).parent("ul").hide();
	 //checkInputSelect();
	}); 
	 
	$(document).click(function(){
		obj.hide();
	});
}
$('#daozhanSelect').dropdown({
    readOnly: true,
    input: '<input type="text" maxLength="4" placeholder="请输入关键字">',
    choice: function() {
      getPoint();//调用
    }
});

$('#transSelect').dropdown({
    readOnly: true,
    input: '<input type="text" maxLength="4" placeholder="请输入关键字">',
    choice: function() {
    }
});


/**
 * 功能描述：选择客户名称时加载客户信息 
 * 作者：yanxf 
 * 时间：2017-05-05
 */
$("#fhdwmch").easyAutocomplete({
	minCharNumber: 1,//至少需要1个字符
	url: function(phrase) {
		return base_url + "/transport/adjunct/customer?something=" + encodeURI(encodeURI(phrase));
	},
	getValue: function(element) {
		return element.customerName;
	},
	//listLocation: "objects",
	list: {
		onSelectItemEvent : function(){
        	var item = $("#fhdwmch").getSelectedItemData();
        	$("#fhdwmch").val(item.customerName);
			$('#khbm').val(item.customerKey);
			$("#fhdwdzh").val(item.address);
			$("#fhdwlxdh").val(item.phone);
			$("#fhdwyb").val(item.mobile);
        },
        onLoadEvent:function(){
        	$('#eac-container-fhdwmch').css('width', $("#fhdwmch").outerWidth());
        }
    },
	requestDelay : 500
});

forEachinput();
$("#khbm").easyAutocomplete({
	minCharNumber: 1,//至少需要1个字符
	url: function(phrase) {
		return base_url + "/transport/adjunct/customer?something=" + encodeURI(phrase);
	},
	getValue: function(element) {
		return element.customerKey;
	},
	//listLocation: "objects",
	list: {
		onSelectItemEvent : function(){
        	var item = $("#khbm").getSelectedItemData();
        	$("#khbm").val(item.customerKey);
			$('#fhdwmch').val(item.customerName);
			$("#fhdwdzh").val(item.address);
			$("#fhdwlxdh").val(item.phone);
			$("#fhdwyb").val(item.mobile);
        },onLoadEvent:function(){
        	$('#eac-container-khbm').css('width', $("#khbm").outerWidth());
        }
    },
	requestDelay : 500
});


function getPoint(){
	$("#dzshhd").val("");
	 $(".dzwd").html(""); 
	$.ajax({
		url:base_url + "/transport/adjunct/arriveNetWork?daozhan="+encodeURI(encodeURI($("#daozhan").val())),
		type:'post',
		dataType:"json",
		success:function(data){	
 			var html="";
			for (var i = 0; i < data.arriveNetWork.length; i++){
				if(pointValue && pointValue!='' && data.arriveNetWork[i]==pointValue){
					html+="<li selected='selected' value='"+data.arriveNetWork[i]+"'>"+data.arriveNetWork[i]+"</li>";
				}else{
					html+="<li value='"+data.arriveNetWork[i]+"'>"+data.arriveNetWork[i]+"</li>";
				}
			}
			  $(".dzwd").empty().append(html);
			  dzshhdList=data.arriveNetWork;
			  $(".dzshhdBox .dropdown-main").remove();
		 	  $("#dzshhd").siblings("a").remove();	
		 	  selectList();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
            if(XMLHttpRequest.readyState == 0) {
            //here request not initialization, do nothing
            } else if(XMLHttpRequest.readyState == 4 && XMLHttpRequest.status == 0){
                alert("服务器忙，请重试!");
            } else {
                alert("系统异常，请联系系统管理员!");
            }
        }
   });
}

function creatLi(){
	var trL = parseInt($('#details-tbody > tr').length)*1+1;
	var pinming = "";
	if(pinmingList){
		pinming +=	'<td><input type="text" class="edit-input pinming" name="details['+(trL)+'].pinming"/></td>';
	}else{
		pinming +=	'<td><input type="text" class="edit-input pinming" name="details['+(trL)+'].pinming"/></td>';
	}
	var str = '<tr>'+										
				'<td><input type="text" readonly="readonly" class="ydxzh" value="'+(trL)+'" name="details['+(trL)+'].ydxzh"/></td>'+
				pinming+
				'<td><input type="text" class="edit-input xh" name="details['+(trL)+'].xh"/></td>'+
				'<td><input type="text" class="edit-input jianshu" name="details['+(trL)+'].jianshu"/></td>'+
				'<td><input type="text" class="edit-input bzh" name="details['+(trL)+'].bzh"/></td>'+
				'<td><input type="text" class="edit-input zhl" name="details['+(trL)+'].zhl"/></td>'+
				'<td><input type="text" class="edit-input tiji" name="details['+(trL)+'].tiji"/></td>'+
				'<td><input type="text" class="edit-input tbje" name="details['+(trL)+'].tbje"/></td>'+
				'<td><input type="text" class="edit-input zchl" name="details['+(trL)+'].zchl"/></td>'+
				'<td><input type="text" class="edit-input zzxl" name="details['+(trL)+'].zzxl"/></td>'+
				'<td><input type="text" class="edit-input qchl" name="details['+(trL)+'].qchl"/></td>'+
				'<td><input type="text" class="edit-input qzxl" name="details['+(trL)+'].qzxl"/></td>'+
				'<td>'+
					'<select class="edit-input jffs" name="details['+(trL)+'].jffs">'+
						'<option value="0">重货</option>'+
						'<option value="1">轻货</option>'+
						'<option value="2">按件</option>'+
					'</select>'+
				'</td>'+
				'<td><input type="text" class="edit-input yunjia" name="details['+(trL)+'].yunjia"/></td>'+
				'<td class="noBorder"><a href="javascript:;" class="removeTr"></a></td>'
			'</tr>';
	$(str).appendTo($('#details-tbody'));
	mgr.bindEvent();
	forEachinput ();
	checkXuhao();
}

function checkXuhao(){
	rename('ydxzh',true);
	rename('xh',false);
	rename('pinming',false);
	rename('jianshu',false);
	rename('bzh',false);
	rename('zhl',false);
	rename('tiji',false);
	rename('tbje',false);
	rename('zchl',false);
	rename('zzxl',false);
	rename('qchl',false);
	rename('qzxl',false);
	rename('jffs',false);
	rename('yunjia',false);
}

function rename(classname,flag){
	var count = 0;
	$("."+classname).each(function(){
		$(this).attr("name",'details['+(count)+'].'+classname);
		count+=1;
		if(flag) $(this).val(count);
	});
}


function forEachinput(){
	if(companyName && companyName.indexOf("四川远成")!=-1){
		$(".pinming").easyAutocomplete({
			minCharNumber: 1,//至少需要1个字符
			url: function(phrase) {
				return base_url + "/transport/adjunct/pinming?something=" + encodeURI(encodeURI(phrase));
			},
			getValue: function(element) {
				return element;
			}
		});
	}
}

//发站网点
function getPoints(){
	var daozhan=encodeURI(encodeURI($("#verror").val()));
	EasyAjax.ajax_Post_Json({
	  dataType: 'json',
	  url:base_url+'/transport/adjunct/latticePoint/'+daozhan,
	  errorReason: true,
	},function(res){
		//渲染数据
		var html="";
		for (var i = 0; i < res.latticePoint.length; i++){
	        html+="<li class='fzli' value='"+res.latticePoint[i]+"'>"+res.latticePoint[i]+"</li>";
	        selectFazhan[i]=res.latticePoint[i];
		};
	    //zhzhuanWdArr=res.latticePoint;
		$(".selectUl:eq(0)").empty().append(html);	
		
		
	});
}


