<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<link rel="stylesheet" href="${ctx_static}/transport/excelRead/css/layui.css"/>
<script src="${ctx_static}/transport/excelRead/js/layui.js"></script>
<script>
if(window.top==window.self){/*判断当前窗口是否是顶层窗口*/
	document.location.href = "${pageContext.request.contextPath}/views/loginPage.jsp";
}else{
	/* document.onclick = function(e){
		var obj = e.target ;
		console.log(obj);
	} */
	layui.use(['form','element'],function(){
		var form = layui.form,
			element = layui.element;
		layer.open({
			content: '登录失效，将影响您的操作<br/>是否重新登陆？',
			btn: ['是','否'],
			yes: function(){
				window.top.location.href =
                    "${pageContext.request.contextPath}/views/loginPage.jsp";
			},
			btn2: function(){
				
			}
		})
	})
}
</script>