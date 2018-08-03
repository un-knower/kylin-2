//返回
$("#btn-return").on("click",function(){
	window.location.href = base_url + "/transport/convey/manage"
});
//清空
$("#clearalls").click(function() {
	$.util.confirm("操作提示","是否确认清空所有内容？", function check(){
		if(1){
        	 window.location.href=base_url + "/transport/convey/insert";
        	}
    } );
});

//清空
$("#clearall").click(function() {
	$.util.confirm("操作提示","是否确认清空所有内容？", function check(){
        if(1){
        	//window.location.reload();
        	location.replace(location.href);
        	}
    } );
});

//点击按钮添加
$('.handle-add').on('click',function(){
	creatLi();
})
//点击图标添加
$(".addTr").on('click',function(){
	creatLi();
})
//点击添加背景色
$("#details-tbody").delegate('.edit-input','click',function(ev){
	var oTr = $(this).parent().parent();
	oTr.css('backgroundColor','#ddecff');
	oTr.siblings().css('backgroundColor','#fff');
	trIndex = $(this).closest("tr").index();
})
//点击别处取消背景色
$(document).click(function(ev){
	var target1 = $("#details-tbody");
	var target2 = $(".handle-delete");
	if(!target1.is(ev.target) && target1.has(ev.target).length === 0){
		target1.children().css('backgroundColor','#fff');
		$(".handle-delete").css('background-color','#ccc');
	}else{
		$(".handle-delete").css('background-color','#FF0000');
	}
})
//点击图标删除
$('#details-tbody').delegate('.removeTr','click',function(){
	if($("#details-tbody tr").length < 2){
		$.util.warning('操作提示', '至少保留一项数据');
	}else{
		var tr = $(this).parent().parent();
		tr.remove();
		$('#details-tbody>tr').each(function(index,item){
			$(item.children[0]).find("span").html(index + 1);
		})
	}
	checkXuhao();
})
//点击按钮删除
$(".handle-delete").on('click',function(){
	if(trIndex != -2){
		if($("#details-tbody tr").length < 2){
			$.util.warning('操作提示', '至少保留一项数据');
		}else{
			$("#details-tbody tr").eq(trIndex).remove();
			$('#details-tbody>tr').each(function(index,item){
				$(item.children[0]).find("span").html(index + 1);
			})
			//删除完，让trIndex = -2，这样必须得再次选中，才能删除，否则，会一直删除
			trIndex = -2;
		}
	}
})
//input,select值改变时，说明，是不想删除的，只是想编辑，所以，操作完成后让trIndex = -2;
$("#details-tbody").delegate('.edit-input','input',function(){
	trIndex = -2;
})


