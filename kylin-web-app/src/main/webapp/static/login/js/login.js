//DOM渲染完成
$(function () {
    var $userName = $("#userName"),
        $password = $("#password"),
        $submit = $("#submit"),
        $error = $(".error");
    //点击登录
    $submit.on("click", function () {
        login();
    });
    //回车登录
    $(document).keydown(function (e) {
        var e = e || window.event;
        if (e.keyCode == 13) {
            login();
        }
    });   
    function login(){
        var userName = $userName.val(),
            password = $password.val();
        //提交校验规则
        if ($.trim(userName).length < 2 || $.trim(userName).length > 12) {
            $error.html("用户名长度为2-12位字符");
            return false;
        } else if ($.trim(password).length < 6 || $.trim(password).length > 16) {
            $error.html("密码长度为6-16位字符");
            return false;
        } else {
            $("#loginForm").submit();
        }
    }
});