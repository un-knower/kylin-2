/**
	 * 功能描述：点击修改运单移除只读模式
	 * 作者：yanxf 
	 * 时间：2017-12-12
	 */
	            
$(document).ready(function(){
	var modifyInfo = {
			_initCheckbox: function(){
				//上门取货费
				$('.catch-goods').on('change', function(){
					if(!$('.zdc').is(':checked') && !$('.zdz').is(':checked') && !$('.cdz').is(':checked') && !$('.cdc').is(':checked')){
						layer.msg('请选择服务方式，再填写上门取货/送货费用',{anim:6,icon:5});
						$('.catch-goods').val(0);
						return false;
					}
					var catchfee = $('.catch-goods').val();
					if($('.zdc').is(':checked') || $('.zdz').is(':checked')){
						if(catchfee != 0){
							layer.msg('站到仓、站到站，上门收货费必须为0',{anim:6,icon:5});
							$('.catch-goods').val(0);
						}
					}
				});
				//上门取货费
				$('.send-goods').on('change', function(){
					if(!$('.zdc').is(':checked') && !$('.zdz').is(':checked') && !$('.cdz').is(':checked') && !$('.cdc').is(':checked')){
						layer.msg('请选择服务方式，再填写上门取货/送货费用',{anim:6,icon:5});
						$('.send-goods').val(0);
						return false;
					}
					var catchfee = $('.send-goods').val();
					if($('.cdz').is(':checked') || $('.zdz').is(':checked')){
						if(catchfee != 0){
							layer.msg('仓到站、站到站，送货上门费必须为0',{anim:6,icon:5});
							$('.send-goods').val(0);
						}
					}
				});
			},
			_init: function(){
				modifyInfo._initCheckbox();
				if(!$('.datetime-picker').val()){
					$('.datetime-picker').val(moment().format('YYYY-MM-DD'));
				}
				$('.select-box').attr("disabled","")
				$(".modify-info,.modify-money").attr("readonly","readonly")
								 .css({'cursor':'not-allowed','background-color': 'rgb(235, 235, 228)'});
				$('#shhd').attr("readonly",'')
				   .css({'cursor':'not-allowed','background-color': 'rgb(235, 235, 228)'});
				$('.waybillId').attr("readonly",'')
							   .css({'cursor':'not-allowed','background-color': 'rgb(235, 235, 228)'});
				if($("#editOrder").data('status')){
					modifyInfo._modifyClickHandler();
					$("#editOrder").css({'background':'##38c76e','cursor':'auto'});
				}else{
					$("#editOrder").css({'background':'#ccc','cursor':'not-allowed'})
								   .unbind().on('click',function(){
									   layer.open({
											content: $("#editOrder").data('message')
										});
								   });
					$('#btn-save').hide();
				}
				if($("#editCertify").data('status')){
					modifyInfo._moneyModify();
					$("#editOrder").css({'background':'##38c76e','cursor':'pointer'});
				}else{
					$('#btn-save').hide();
					$("#editCertify").css({'background':'#ccc','cursor':'not-allowed'})
									.unbind().on('click',function(){
										/*$.util.warning('操作提示', $("#editCertify").data('message'));*/
										layer.open({
											content: $("#editCertify").data('message')
										});
									});
				}              
			},
			_modifyClickHandler: function(){
				$("#editOrder").unbind().on("click",function(){
					selectList();
					$('#shhd').removeAttr("readonly")
					   .css({'cursor':'pointer','background-color': '#fff'});
					$('#modify_order').val(1);
					clickNum ++;
					$('.select-box').unbind();
					$('.edit-hidden').hide();
					$('.select-box').removeAttr("disabled");
					$('.zdc,.zdz').on('click',function(){
						var catchfee = $('.catch-goods').val();
						if($('.zdc').is(':checked') || $('.zdz').is(':checked')){
							if(catchfee != 0){
								$('.catch-goods').val(0);
								layer.msg('当前服务方式下<strong>上门收货费</strong>必须为0',{anim:6,icon:5});
							}
						}
					});
					$('.cdz,.cdc').change(function(){
						var catchfee = $('.send-goods').val();
						if($('.cdz').is(':checked') || $('.cdc').is(':checked')){
							if(catchfee != 0){
								layer.msg('当前服务方式下<strong>送货上门费</strong>必须为0',{anim:6,icon:5});
							}
						}
					})
					$(".modify-info").removeAttr("readonly")
									 .css({'cursor':'auto','background-color': ''})
									 .on('change',function(){
										 if($(this).attr('id') == 'daishou'){
											 $('#daishou-hidden').val($('#daishou').val());
										 }
										 /*$('#modify_order').val(1);*/
									 });
					$('.waybillId').css({'cursor':'auto','background-color': ''})
					   .on('change',function(){
						   /*$('#modify_order').val(1);*/
					   });
					$('.operation-btn').show();
					$('#btn-save').show();
					//下拉选控件
					if(clickNum == 1){
						$('#daozhanSelect').dropdown({
						      readOnly: true,
						      input: '<input type="text" maxLength="4" placeholder="请输入关键字">',
						      choice: function() {
						        getPoint();//调用
						      }
						 });
						getPoint();
						$('#conveyMethod').dropdown({
						      readOnly: true,
						      input: '<input type="text" maxLength="4" placeholder="请输入关键字">',
						      choice: function() {
						    	  
						      }
						 }); 
						
					}
				});
				
			},
			_moneyModify: function(){
				$("#editCertify").unbind().on("click",function(){
					monneyClick ++;
					$('.M-modify-status').val(1);
					$('#btn-save').show();
					$(".modify-money").removeAttr("readonly")
									 .css({'cursor':'auto','background-color': ''})
									 .on('change',function(){
										 /*$('.M-modify-status').val(1);*/
									 });
				});
			},
			_selectHandler: function(){
				$('.select-box').unbind('click').on('click', function(){
					$(this).blur();
					return false;
				})
			}
	}
	layui.use(['form','element'],function(){
		var form = layui.form,
			element = layui.element;
		modifyInfo._init();
	})
	
});