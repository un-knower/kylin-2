<%@ page contentType="text/html;charset=UTF-8"%>
<!-- 公司下拉选 -->
<script type='text/x-template' id='companyselect'>
	<div class='layui-inline'>
		<div class='input-div'>
			<label class='layui-form-label' style='padding: 9px 15px 9px 0px;'>{{gs}}：</label>
			<div v-show='istotal' class="layui-input-inline">
				<select class='select-company' v-model='companyName' lay-filter='zgs'>
					<option value="">所有公司</option>
				</select>
			</div>
			<div v-show='!istotal' class='layui-input-block'>
				<input id='companyN' :companyCode='code' v-model='companyName' type='text' class='layui-input' readonly>
			</div>
		</div>
	</div>
</script>
<script text='text/javascript'>
	var companySelect = Vue.extend({
		template: '#companyselect',
		data:function(){
			return {
				companyName: '',
				istotal: false,
				code: '',
				form$: '',
				gs: '',
				gsList: ''
			}
		},
		methods: {
			_ajaxRes: function(){
				var _self = this;
				EasyAjax.ajax_Get({
					dataType: 'json',
					contentType: "application/json",
	                url: base_url + '/transport/generalInfo/getCompanyListAndCurrCompanyInfo',
	                data: '',
	                async: false,
	                errorReason: true
				},function(data){
					_self.gsList = data.COMPANY_LIST;
					if(data.CURR_COMPANY_NAME == '总公司'){
						_self.istotal = true;
						layui.use('form', function(){
							var form = layui.form;
							$('.select-company').html('<option value="">所有公司</option>');
							$.each(data.COMPANY_LIST, function(i,k){
								$('.select-company').append('<option value='+k.companyName+' companyCode='+k.companyCode+'>'+k.companyName+'</option>');
							});
							form.render();
							form.on('select(zgs)',function(data){
								_self.companyName = data.value;
							})
							
						})
					}else{
						_self.istotal = false;
						_self.companyName = data.CURR_COMPANY_NAME;
						_self.code = data.CURR_COMPANY_CODE;
					}
				});
			}
		},
		mounted: function(){
			this._ajaxRes();
			if(!$('#_c').attr('cName')){
				this.gs = '公司';
			}else{
				var cname = $('#_c').attr('cName')
				this.gs = cname;
			}
		}
	})
</script>

<!-- 打印页面 -->
<script type='text/x-template' id='printFun'>
	<iframe id="printf" src="" width="0" height="0" frameborder="0"></iframe>
</script>
<script text='text/javascript'>
	var printPage = Vue.extend({
		template: '#printFun',
		props: ['stylecss','direction'],
		data:function(){
			return {
				printclick: 0,/*为了计算第一次点击时不出现打印页面的bug*/
				f: ''
			}
		},
		methods: {
			_printPage: function(obj){
				this.printclick ++;
				var stylecss = this.stylecss;
				/* var stylecss = '<style type="text/css">@import url("'+ctx_static+'/transport/excelRead/css/layui.css");'
				 +'@import url("'+ctx_static+'/publicFile/css/public.css");'
				 +'@import url("'+ctx_static+'/transport/arrivalloding/css/arrivalloding.css")</style>'; */
				this.f = document.getElementById('printf');
	           this.f.contentDocument.write(stylecss+$(obj).html());
		        this.f.contentDocument.close();/*清除上一次打印内容*/
		        var _f = this.f;
		           if(this.printclick == 1){
		            	window.close();
		            }else{
		            	if(this.direction == 'H'){
		            		var content = '打印时最好设置布局为横向';
		            	}else if(this.direction == 'Z'){
		            		var content = '打印时最好设置布局为纵向';
		            	}
		            	var printlayer = layer.open({
		            		content: content,
		            		btn: ['确定','取消'],
		            		yes: function(){
		            			_f.contentWindow.print();
		            			layer.close(printlayer);
		            		},
		            		btn2:function(){
		            			layer.close(printlayer);
		            		}
		            	})
		            } 
			}
		}
	})
</script>