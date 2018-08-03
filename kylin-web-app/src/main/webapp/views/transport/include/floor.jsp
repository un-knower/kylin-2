<script src="${ctx_static}/common/jquery/jquery-1.12.2.min.js" type="text/javascript"></script>
<c:if test="${success_message != null }">
	<div id="success_message" style="display: none;">${ success_message }</div>
	<script>$("#loading").hide();</script>
</c:if>
<c:if test="${warning_message != null }">
	<div id="warning_message" style="display: none;">${ warning_message }</div>
	<script>$("#loading").hide();</script>
</c:if>
<c:if test="${error_message != null }">
	<div id="error_message" style="display: none;">${ error_message }</div>
	<script>$("#loading").hide();</script>
</c:if>
<!-- 常用1.3版本文件引入  -->
<script src="${ctx_static}/publicFile/layui/layui.js"></script>
<script src="${ctx_static}/transport/excelRead/js/jquery.min.js"></script>
<script src="${ctx_static}/transport/excelRead/js/easy.ajax.js"></script> 
<script src="${ctx_static}/publicFile/js/vue.js"></script>
<%-- <script src="${ctx_static}/transport/excelRead/js/table.js"></script> --%>
<%-- <script src="${ctx_static}/common/jquery/jquery-1.12.2.min.js" type="text/javascript"></script> --%>
<script src="${ctx_static}/common/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx_static}/common/jquery/moment.min.js" type="text/javascript"></script>
<script src="${ctx_static}/common/jquery/plugins/jquery.easy-autocomplete.js"></script>
<%-- <script src="${ctx_static}/common/jquery/plugins/jquery.city.js"></script>--%>
<script src="${ctx_static}/common/jquery/plugins/jquery.confirm.min.js"></script>
<script src="${ctx_static}/common/jquery/plugins/flatpickr.js"></script>
<script src="${ctx_static}/common/dropdown/jquery.dropdown.min.js" type="text/javascript" ></script>
<script src="${ctx_static}/common/common.js" type="text/javascript" ></script>
<script src="${ctx_static}/transport/common/js/layout.js" type="text/javascript" ></script>
