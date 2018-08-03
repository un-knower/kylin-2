<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>货物异常录入</title>
</head>
<body>
<form action="" method="post" enctype="multipart/form-data" name="upload_form">
	<input   type="file"   name="photo" >
  	<input type="button" onclick="upload()" value="确定"  />
</form>
</body>

<%@ include file="/views/transport/include/floor.jsp"%>
<script type="text/javascript">
	function upload(){
		var arr = [];
		var formData = new FormData();
		var file = $('input[name = photo]')[0].files[0];
		formData.append("file", file);
		formData.append("waybillNum", "1111111111");
		$.ajax({
	        url: "/kylin/transport/reorder/uploadReorder",
	        type: 'POST',
	        data: formData,
	        processData: false, // 告诉jQuery不要去处理发送的数据
	        contentType: false,// 告诉jQuery不要去设置Content-Type请求头
	        success: function (response) {

	        }
	    });
	}

</script>
</html>