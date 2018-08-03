$(document).ready(function(){
	var search_form = $("form");
	$(".pagination a").each(function(i){
		$(this).on('click', function(){
			$("form input[name='num']").val($(this).attr("num"));
			$("form").submit();
		});   
	});
	$("button[name='btn_change'").on("click", function(){
		var btn = $(this), url = btn.val();
		btn.tooltip({animation:true, placement:'top', content: '2222222222'});
		$.util.confirm('提示', '你确定要切换该资源的状态吗？', function () {
        	$.getJSON(url, function(result){
				if(result.success){
					if(result.enable){
						$("span[name=enable_"+ result.resId +"]").html('启用状态').removeClass("label-danger").addClass("label-success");
						btn.html('禁用');
					}else{
						$("span[name=enable_"+ result.resId +"]").html('禁用状态').removeClass("label-success").addClass("label-danger");
						btn.html('启用');
					}
	        	}else{
	        		btn.tooltip({animation:true, placement:'top', content: result.message});
	        	}
			});
        });
	});
});