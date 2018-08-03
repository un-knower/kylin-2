<c:if test="${success_message != null }">
	<div id="success_message" style="display: none;">${ success_message }</div>
</c:if>
<c:if test="${warning_message != null }">
	<div id="warning_message" style="display: none;">${ warning_message }</div>
</c:if>
<c:if test="${error_message != null }">
	<div id="error_message" style="display: none;">${ error_message }</div>
</c:if>
<script src="${ctx_static}/common/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="${ctx_static}/common/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx_static}/common/bootstrap/plugins/select.min.js" type="text/javascript"></script>
<script src="${ctx_static}/common/bootstrap/plugins/validator.min.js" type="text/javascript"></script>
<script src="${ctx_static}/common/jquery/plugins/jquery.confirm.min.js" type="text/javascript"></script>
<script src="${ctx_static}/common/common.js" type="text/javascript" ></script>