package com.ycgwl.kylin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hp001 on 2017/9/6.
 */
public class DateUtils {
	private static final String DATAFORMAT1 = "yyyy-MM-dd";
	private static final String DATAFORMAT3 = "yyyy-MM-dd HH:mm:ss";
	private static final String DATAFORMAT4 = "yyyy-MM-dd HH:mm";
	private static final String DATAFORMAT5 = "yyyy/MM/dd HH:mm";

	private static  final  SimpleDateFormat sdf1 = new SimpleDateFormat(DATAFORMAT1);
	private static final SimpleDateFormat sdf3 = new SimpleDateFormat(DATAFORMAT3);
	private static final SimpleDateFormat sdf4 = new SimpleDateFormat(DATAFORMAT4);
	private static final SimpleDateFormat sdf5 = new SimpleDateFormat(DATAFORMAT5);
	/**
	 * 字符串转日期
	 */
	public static Date getDate(String param){
		try {
			return org.apache.commons.lang.time.DateUtils.parseDateStrictly(param, CommonDateUtil.PATTERNS);
		} catch (ParseException e2) {
			return new Date();
		}
	}
	/**
	 * 把字符串对应的日期往前/往后推N天
	 */
	public static String fromDays(String current,int days) {
		try {
			LocalDate currentDate = LocalDate.parse(current, DateTimeFormatter.ofPattern(DATAFORMAT1));
			return currentDate.plusDays(days).toString();
		}catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取当前月第一天
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年10月31日
	 * @return
	 */
	public static Date getFirstDayOfMonth(){
		return Date.from(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());	
	}

	/**
	 * 获取当前时间长字符串
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月1日
	 * @return
	 */
	public static String getNowDateStr() {
		return sdf3.format(new Date());
	}

	/**
	 * 获取当前时间短字符串
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月6日
	 * @return
	 */
	public static String getNowDateShortStr() {
		return sdf1.format(new Date());
	}

	/**
	 * 获取当前时间短字符串（日期，时分）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月10日
	 * @return
	 */
	public static String getNowDateHourAndMinutesStr() {
		return sdf4.format(new Date());
	}

	/**
	 * 获取时间短字符串（日期，时分）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月10日
	 * @param date
	 * @return
	 */
	public static String getCurrentHourAndMinutesTime(Date date){
		String format = sdf4.format(date);
		return format;
	}

	/**
	 * 获取时间短字符串（yyyy/MM/dd HH:mm）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月22日
	 * @param date
	 * @return
	 */
	public static String getCurrentHourAndMinutesTime2(Date date){
		String format = sdf5.format(date);
		return format;
	}

	/**
	 * 获取短时间（日期，时分）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月23日
	 * @param param
	 * @return
	 */
	public static Date getHourAndMinutesDate(String param){
		Date parse = new Date();
		try {
			parse = sdf4.parse(param);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;	
	}
	/**
	 * 获取短时间（日期，时分）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年11月23日
	 * @param param
	 * @return
	 */
	public static Date getDetailDate(String param){
		if(param.length()==16) {
			param = param +":00";
		}
		Date parse = new Date();
		try {
			parse = sdf3.parse(param);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;	
	}

	/**
	 * 获取短时间（yyyy/MM/dd HH:mm）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月22日
	 * @param param
	 * @return
	 */
	public static Date getHourAndMinutesDate2(String param){
		Date parse = new Date();
		try {
			parse = sdf5.parse(param);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;	
	}

	/**
	 *  获取时间长字符串
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月6日
	 * @param date
	 * @return
	 */
	public static String getCurrentTimeStr(Date date){
		String format = sdf3.format(date);
		return format;
	}

	/**
	 * 获取时间年份字符串
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月17日
	 * @return
	 */
	public static String getNowYear(){
		Calendar now = Calendar.getInstance();
		return String.valueOf(now.get(Calendar.YEAR));
	}

	/**
	 * 获取当前时间年份
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @return
	 */
	public static Integer getNowYears(){
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR);
	}

	/**
	 * 获取当前时间月份
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @return
	 */
	public static Integer getNowMonths(){
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取传入时间年份
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param date
	 * @return
	 */
	public static Integer getDateYears(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取传入时间月份
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年1月29日
	 * @param date
	 * @return
	 */
	public static Integer getDateMonths(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static void main(String[] args) {
		String str = "2018-02-20 12:10:00";
		Date date = DateUtils.getDate(str);
		//    	System.out.println(DateUtils.getDateMonths(date));
		//    	System.out.println(DateUtils.getNowMonths());

		System.out.println(getFirstDayOfMonth());
		System.out.println(DateUtils.isTimeInterval(date, new Date()));
	}

	/**
	 * 判断结束时间、结束时间是否同一个月
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年2月7日
	 * @param dateStart
	 * @param dateEnd
	 * @return true 同月   false 不同月
	 */
	public static boolean isTimeInterval(Date dateStart, Date dateEnd){
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(dateStart);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(dateEnd);
		return calendarStart.get(Calendar.YEAR) == calendarEnd.get(Calendar.YEAR) && calendarStart.get(Calendar.MONTH) == calendarEnd.get(Calendar.MONTH);

	}
	
	/**
	 * 获取时间短字符串（日期，时分）
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2018年3月21日
	 * @param date
	 * @return
	 */
	public static String getCurrentHourAndMinutesTimeByStr(String date){
		String format = sdf4.format(date);
		return format;
	}
}
