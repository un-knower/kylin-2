$(function() {
	if(! $.fn.serializeObject ){
		$.fn.serializeObject = function(){
		    var o = {};
		    $.each(this.serializeArray(), function() {
		        if (o[this.name] !== undefined) {
		            if (!o[this.name].push) {
		                o[this.name] = [o[this.name]];
		            }
		            o[this.name].push(this.value || '');
		        } else {
		            o[this.name] = this.value || '';
		        }
		    });
		    return o;
		}
	}
	if(!$.util){
        $.util = {
            confirm : function(title, context, okAction, canAction){
                $.confirm({
                    title: title, content:context, animation: 'left', closeAnimation: 'right',type: 'red',
                    buttons: {
                        confirm: { text: '确定', action: okAction },
                        cancel: { text: '取消', action: canAction }
                    }
                });
            },
            danger : function(title, context, action){
                $.confirm({
                    title: title, content:context, animation: 'left', closeAnimation: 'right',type: 'red',
                    buttons: {
                        danger: { text: '确定', btnClass: 'btn-red', action: action }
                    }
                });
            },
            success : function(title, context, action){
                $.confirm({
                    title: title, content:context, animation: 'left', closeAnimation: 'right',type: 'blue',
                    buttons: {
                        success: { text: '确定', btnClass: 'btn-blue', action: action }
                    },
                    autoClose: 'success|6000'
                });
            },
            warning : function(title, context, action){
                $.confirm({
                    title: title, content:context, animation: 'left', closeAnimation: 'right',type: 'orange',
                    buttons: {
                        warning: { text: '确定', btnClass: 'btn-orange', action: action }
                    }
                });
            },
            alert : function(title, context, ok_fuc){
                $.confirm({
                    title: title, content:context, animation: 'left', closeAnimation: 'right',
                    buttons: {
                        confirm: { text: '确定', action: ok_fuc }
                    }
                });
            }
        }
    }
	try{
		var success = $("#success_message").html();
		if(success){//cancelButton: false,
			$.util.success('操作提示', success);
		}
		var warning = $("#warning_message").html();
		if(warning){
			$.util.warning('操作警告', warning);
		}
		var error = $("#error_message").html();
		if(error){
			$.util.danger('操作异常', error);
		}
	}catch(e){}
});