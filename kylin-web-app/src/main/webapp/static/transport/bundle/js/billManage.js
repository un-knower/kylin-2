
$(function(){

	var trIndex = -2;

	/*
	$('#waijiedanhao').focusout(function(){  
		$.ajax( {  
			type : "POST",  
			url : base_url+"/transport/bundle/receipt/get",
			data: "ydbhid="+ $('#waijiedanhao').val(), 
			success : function(json) { 
				var	data="";
				var order = json.order;
				var trL = $('#tbody>tr').length;
				if (trL==undefined || trL ==null) {trl = 0;}
				var str = '<tr>'+										
				'<td>'+(trL+1)+'</td>'+
				'<td><input type="text" class="edit-input" name="items['+(trL+1)+']" value="'+order.ydbhid+'"/></td>'+
				'<td><input type="text" class="edit-input" readonly="readonly" value="'+order.fazhan+'"/></td>'+
				'<td><input type="text" class="edit-input" readonly="readonly" value="'+order.daozhan+'"/></td>'+
				'<td><input type="text" class="edit-input" readonly="readonly" value="'+order.fhdwmch+'"/></td>'+
				'<td><input type="text" class="edit-input" readonly="readonly" value="'+order.jshhj+'"/></td>'+
				'<td><input type="text" class="edit-input" readonly="readonly" value="'+order.fhzhl+'"/></td>'+
				'<td><input type="text" class="edit-input" readonly="readonly" value="'+order.tijihj+'"/></td>'+
				'<td><input type="text" class="edit-input" readonly="readonly" value="***"/></td>'+
				'<td><input type="text" class="edit-input" readonly="readonly" value="'+order.beizhu+'"/></td>'+
				'<td class="noBorder"><a href="javascript:;" class="removeTr"></a></td></tr>';
				$(str).appendTo($('#tbody')); 
			}

		})

	})  


	$("#sou").click(function(){
		$.ajax( {  
			type : "POST",  
			url : base_url+"/transport/bundle/receipt/query",
			data:$("#insertform0").serialize(), 
			success : function(json) { 
				
				var filter_numbs = new Array(); 	//存放表单中已有的运单标号
				$("#insertTable>tbody tr").each(function () {
					filter_numbs.push($(this).find("td")[1].find("input")[0].val());
				});

				var	data="";
				var list = json.list;
				for (var i = 0; i < list.length; i++) {
					
					var flag = true;
					for(var j=0;j<filter_numbs.length;j++){//判断后台过来的数据表单中是否已经添加了
						if(list[i].ydbhid == filter_numbers[j]){
							flag =false;
							;
						}
					}
					if(flag){
						fhr = list[i].fhr;
						if(fhr == "null" || fhr == "undefined" || fhr== ""){
							fhr = "未知发货人";
						}
						var fwfs ="";
						if(list[i].fwfs == 0){
							fwfs = "仓到站";
						}else if(list[i].fwfs == 1){
							fwfs = "仓到仓";
						}else if(list[i].fwfs == 2){
							fwfs = "站到仓";
						}else{
							fwfs = "站到站";
						}
						data = data+ '<thead>'+
						'<input type="hidden" name="ydbhid" value="'+list[i].ydbhid+'"></input>'+
						'<input type="hidden" name="zhl" value="'+list[i].fhzhl+'"></input>'+
						'<input type="hidden" name="tiji" value="'+list[i].tijihj+'"></input>'+
						'<input type="hidden" name="fazhan" value="'+list[i].fazhan+'"></input>'+
						'<input type="hidden" name="daozhan" value="'+list[i].daozhan+'"></input>'+
						'<input type="hidden" name="jianshu" value="'+list[i].jshhj+'"></input>'+
						'<input type="hidden" name="ysfs" value="'+list[i].ysfs+'"></input>'+
						'<input type="hidden" name="fwfs" value="'+list[i].fwfs+'"></input>'+
						'<input type="hidden" name="fhdwmch" value="'+list[i].fhdwmch+'"></input>'+
						'<input type="hidden" name="beizhu" value="'+list[i].beizhu+'"></input>'+
						'<tr id="aaddress">'+
						'<th id="company" colspan="5"><span class="full"> <input'+
						'	type="checkbox" value="编号" />运单编号:'+list[i].ydbhid+
						'</span> <span class="right">'+list[i].fhzhl+'吨/'+list[i].tijihj+'立方米</span></th>'+
						'</tr>'+
						'</thead>'+
						'<tbody>'+
						'	<tr id="aaddress">'+
						'	<td><span class="font-color">'+list[i].fazhan+'</span><br /> <span'+
						'		style="text-align: center;">'+fhr+'</span></td>'+
						'	<td><span class="font-color">'+list[i].daozhan+'</span><br /> <span'+
						'		style="text-align: center;">'+list[i].shhrmch+'</span></td>'+
						'	<td class="center">'+list[i].jshhj+'</td>'+
						'	<td class="center">'+list[i].ysfs+'</td>'+
						'	<td class="center">'+fwfs+'</tr>'+
						'</tbody>'
					}
				}
				$("#inserttable").html(data);		//.html可以直接将之前的表格给替换掉
				$("#record").text("一共搜索到了"+list.length+"条记录");
			}
		});
	})
	*/
})
