/**
 * created by lhz on 2017/11/6
 *公共功能的函数
 */
/*************/


function getCurrTime (){
	var date = new Date();
	var seperator1 = '-';
	var seperator2 = ':';
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	var Hours = date.getHours();
	var Minutes = date.getMinutes();
	var Seconds = date.getSeconds();
	if(month <= 9){
		month = '0' + month;
	}
	if(strDate >0 && strDate < 10){
		strDate = '0' + strDate;
	}
	if(Hours >=0 && Hours < 10 ){
		Hours = '0' + Hours;
	}
	if(Minutes >=0 && Minutes < 10){
		Minutes = '0' + Minutes;
	}
	if(Seconds >=0 && Seconds < 10){
		Seconds = '0' + Seconds;
	}
	var currTime = date.getFullYear() + seperator1 + month + seperator1
					+ strDate + ' ' + Hours + seperator2 + 
					Minutes + seperator2 + Seconds;
	return currTime;
}

//页码渲染
/*
 * data: 单页数据
 * pages: 总页数
 * tpl: jsrender引擎容器
 * cont： tbody
 * curr: 当前页
 * callback: 分页请求函数
 * count: 数据条数
 * */
function page(data, pages, tpl, cont, curr, callback, count, limit) {// 数据 jsrender渲染容器
	// 表格tbody
	var $tplArray = $(tpl).render(data);
	var pageFlag = true;
	var pgSize;
	$(cont).html('');
	$(cont).html($tplArray);
		layui.use([ 'layer', 'element', 'laydate', 'form', 'laypage' ],
			function() {
				layui.laypage.render({
					elem : 'page',
					pages : pages, // 总页数
					layout: ['count','prev','page','next','limit','skip'],
					curr : curr,
					limit : limit,
					limits: [10,20,30],
					count : count,
					jump : function(obj, first) {
						if (!first) {
							pgSize = obj.limit;
							curr = obj.curr;
							callback(obj.curr,pgSize);
						}
					}
			})
		});
}
	
	/*********计算日期天数********/
function dateInterval(beginTime,endTime) {
	//换算成毫秒
    var minutes = 1000 * 60
    var hours = minutes * 60
    var days = hours * 24
    
    var date1 = beginTime.replace('-',',');
    var date2 = endTime.replace('-',',');
    
    var day1 = Date.parse(date1);
    var day2 = Date.parse(date2);
    d = day2 - day1;
    d = d / days;
    
    return d;
  }

/**********格式化日期**********/
function formatDate(date,getdata){
	var year=date.getFullYear();
	var month=date.getMonth()+1;
	var monthCurr=date.getMonth()+1;
	var day=date.getDate();
	var hour=date.getHours();
	var minute=date.getMinutes();
    var second=date.getSeconds();
    var lastMonthday = new Date(year,month,0);
	lastMonthday = lastMonthday.getDate();//获取上月最后一天
	lastMonth = month-1;
	lastyear = year;
	lastday = day-1;
	lastYearmonth = month + 1;
	if(lastYearmonth == 13) lastYearmonth == 1;
	beforeyear = year - 1;
	if(lastMonth == 0){
		lastMonth = 12;
		lastyear = year - 1;
	}
	if(lastday == 0){
		lastday = lastMonthday;
		month = month - 1;
	}
	function changeNum (item){
		if(item<10){
			item="0"+item;
		}
		return item;
	}
	month = changeNum (month);
	monthCurr = changeNum (monthCurr);
	day = changeNum (day);
	minute = changeNum (minute);
	second = changeNum (second);
	lastMonth = changeNum (lastMonth);
	lastMonthday = changeNum (lastMonthday);
	lastday = changeNum (lastday);
	lastYearmonth = changeNum (lastYearmonth);
	switch (getdata){
	   case '本月年月日':
		   return year+"-"+monthCurr+"-"+day;
		   break;
	   case '上月年月日':
		   return lastyear+"-"+lastMonth+"-"+day;
		   break;
	   case '上月年月':
		   return lastyear+"-"+lastMonth;
		   break;
	   case '本月年月':
		   return year+"-"+month;
		   break;
	   case '本月年月前日':
		   return year+"-"+month+"-"+lastday;
		   break;
	   case '一年年月日':
		   return beforeyear+"-"+lastYearmonth+"-"+day;
		   break;
	   case '一年年月':
		   return beforeyear+"-"+lastYearmonth;
		   break;
	   case '年月日时分秒':
		   return year+"-"+month+"-"+day+' '+hour+':'+minute+':'+second;
		   break;
	   case '年月日时分':
		   return year+"-"+monthCurr+"-"+day+' '+hour+':'+minute;
		   break;
	}
    // return year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second;
//	return year+"-"+month;
}


//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r != null) return unescape(r[2]); return null; //返回参数值
}

//获取字符串长度（汉字算两个字符，字母数字算一个）
function getByteLen(val) {
    var len = 0;
    for (var i = 0; i < val.length; i++) {
        var a = val.charAt(i);
        if (a.match(/[^\x00-\xff]/ig) != null) {
            len += 2;
        }else {
            len += 1;
        }
    }
    return len;
}

//时间戳 毫秒转换日期
function getMyDate(str){  
	 if(str==null){
		 return "";
	 }else{
         var subDate = new Date(str), 
         subYear = subDate.getFullYear(),  
         subMonth = subDate.getMonth()+1,  
         subDay = subDate.getDate(),  
         subHour = subDate.getHours(),  
         subMin = subDate.getMinutes(),  
         subSen = subDate.getSeconds(),  
         subTime = subYear +'-'+ getzf(subMonth) +'-'+ getzf(subDay) +' '+ getzf(subHour) +':'+ getzf(subMin)+':'+ getzf(subSen);//最后拼接时间  
         return subTime;  
	 }
}; 

//补0操作  
function getzf(num){  
    if(parseInt(num) < 10){  
        num = '0'+num;  
    }  
    return num;  
}