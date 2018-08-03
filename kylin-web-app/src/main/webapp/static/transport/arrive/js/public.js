/**
 * created by lhz on 2017/11/6
 *公共功能的函数
 */
function getCurrTime (){
	var date = new Date();
	var seperator1 = '-';
	var seperator2 = ':';
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if(month >1 && month <= 9){
		month = '0' + month;
	}
	if(strDate >1 && strDate <= 9){
		strDate = '0' + strDate;
	}
	var currTime = date.getFullYear() + seperator1 + month + seperator1
					+ strDate + ' ' + date.getHours() + seperator2 + 
					date.getMinutes() + seperator2 + date.getSeconds();
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
	day = changeNum (day);
	minute = changeNum (minute);
	second = changeNum (second);
	lastMonth = changeNum (lastMonth);
	lastMonthday = changeNum (lastMonthday);
	lastday = changeNum (lastday);
	lastYearmonth = changeNum (lastYearmonth);
	switch (getdata){
	   case '本月年月日':
		   return year+"-"+month+"-"+day;
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
	}
    // return year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second;
//	return year+"-"+month;
}