package com.sizatn.springdemo.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * @param num 过去的天数
	 * @param parseString 解析格式，例如：yyyy-MM-dd HH:mm:ss 或者 yyyy-MM-dd
	 * @return
	 * @desc 获取num天之前的日期（例如今天4月21日，num为2，返回的结果为4月19日）
	 */
	public static Date getDaysAgoOfDate(int num, String parseString) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, c.get(Calendar.DATE) - num);
		return parseDate(formatDate(c.getTime(), parseString));
	}
	
	/**
	 * @return 返回四个时间的字符串
	 * @desc 如果dateStr不为空，则根据dateStr计算上一周周一到周日的日期和当前周周一到周日的日期
	 * 		如果dateStr为空，则根据当前日期计算上一周周一到周日的日期和当前周周一到周日的日期
	 */
	public static String convertWeekByDate(Date date) {
		// 设置时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day - 7);
		String beginWeekDate = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, 6);
		String endWeekDate = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -13);
		String preBeginWeekDate = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, 6);
		String preEndWeekDate = sdf.format(cal.getTime());
		cal.add(Calendar.YEAR, -1);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() + 1);
		String yearBeginWeekDate = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, 6);
		String yearEndWeekDate = sdf.format(cal.getTime());
		return preBeginWeekDate + "=" + preEndWeekDate + "=" + beginWeekDate + "=" + endWeekDate + "="
				+ yearBeginWeekDate + "=" + yearEndWeekDate;
	}
	
	/**
	 * @return
	 * @desc 如果date为不为空，则根据date日期计算上上个月第一天和最后一天日期和上个月月第一天和最后一天日期
	 * 		如果date为空，则根据当前日期计算上上个月第一天和最后一天日期和上个月月第一天和最后一天日期
	 * 		例如：当前时间是2016-06-06，则计算时间为：2016-04和2016－05两个月的第一天和最后一天
	 */
	public static String convertMonthByDate(Date date) {
		// 设置时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.MONTH, -2);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String prePreMonthFirstDay = sdf.format(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		String prePreMonthLastDay = sdf.format(cal.getTime());
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String preMonthFirstDay = sdf.format(cal.getTime());
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String preMonthLastDay = sdf.format(cal.getTime());
		cal.add(Calendar.YEAR, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String preYearMonthFirstDay = sdf.format(cal.getTime());
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String preYearMonthLastDay = sdf.format(cal.getTime());
		return prePreMonthFirstDay + "=" + prePreMonthLastDay + "=" + preMonthFirstDay + "=" + preMonthLastDay + "="
				+ preYearMonthFirstDay + "=" + preYearMonthLastDay;
	}
	
	/**
	 * @param date
	 * @return
	 * @desc 如果date为不为空，则根据date日期计算月份天数；如果date为空，则根据当前日期计算月份天数
	 */
	public static int maxDayOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * @param date
	 * @return
	 * @desc 如果date为不为空，则根据date年份计算前一年年月；如果date为空，则根据当前年份计算前一年年月
	 */
	public static String getPreYear(Date date) {
		// 设置时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.YEAR, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return sdf.format(cal.getTime());
	}
	
	/** 
     * 取得月第一天 
     *  
     * @param date 
     * @return 
     */  
	public static Date getFirstDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}  
  
    /** 
     * 取得月最后一天 
     *  
     * @param date 
     * @return 
     */  
	public static Date getLastDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
	
	/** 
     * 取得季度第一天 
     *  
     * @param date 
     * @return 
     */  
	public static Date getFirstDateOfSeason(Date date) {
		return getFirstDateOfMonth(getSeasonDate(date)[0]);
	}  
  
    /** 
     * 取得季度最后一天 
     *  
     * @param date 
     * @return 
     */  
	public static Date getLastDateOfSeason(Date date) {
		return getLastDateOfMonth(getSeasonDate(date)[2]);
	}
    
    /** 
     * 取得季度第一天，seasonNum为1，2，3，4
     *  
     * @param seasonNum 
     * @return 
     */  
	public static Date getFirstDateOfSeason(int seasonNum) {
		return getFirstDateOfMonth(getSeasonDate(seasonNum)[0]);
	}  
  
    /** 
     * 取得季度最后一天，seasonNum为1，2，3，4
     *  
     * @param seasonNum
     * @return 
     */  
	public static Date getLastDateOfSeason(int seasonNum) {
		return getLastDateOfMonth(getSeasonDate(seasonNum)[2]);
	}
    
    /** 
     * 通过date取得季度月
     *  
     * @param date 
     * @return 
     */  
	public static Date[] getSeasonDate(Date date) {
		Date[] season = new Date[3];

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int nSeason = getSeason(date);

		if (nSeason == 1) {// 第一季度
			c.set(Calendar.MONTH, Calendar.JANUARY);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.FEBRUARY);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.MARCH);
			season[2] = c.getTime();
		} else if (nSeason == 2) {// 第二季度
			c.set(Calendar.MONTH, Calendar.APRIL);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.MAY);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.JUNE);
			season[2] = c.getTime();
		} else if (nSeason == 3) {// 第三季度
			c.set(Calendar.MONTH, Calendar.JULY);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.AUGUST);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.SEPTEMBER);
			season[2] = c.getTime();
		} else if (nSeason == 4) {// 第四季度
			c.set(Calendar.MONTH, Calendar.OCTOBER);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.NOVEMBER);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.DECEMBER);
			season[2] = c.getTime();
		}
		return season;
	}
    
    /** 
     * 通过seasonNum（1，2，3，4）取得季度月
     *  
     * @param seasonNum 
     * @return 
     */  
	public static Date[] getSeasonDate(int seasonNum) {
		Date[] season = new Date[3];

		Calendar c = Calendar.getInstance();
		int nSeason = seasonNum;

		if (nSeason == 1) {// 第一季度
			c.set(Calendar.MONTH, Calendar.JANUARY);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.FEBRUARY);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.MARCH);
			season[2] = c.getTime();
		} else if (nSeason == 2) {// 第二季度
			c.set(Calendar.MONTH, Calendar.APRIL);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.MAY);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.JUNE);
			season[2] = c.getTime();
		} else if (nSeason == 3) {// 第三季度
			c.set(Calendar.MONTH, Calendar.JULY);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.AUGUST);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.SEPTEMBER);
			season[2] = c.getTime();
		} else if (nSeason == 4) {// 第四季度
			c.set(Calendar.MONTH, Calendar.OCTOBER);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.NOVEMBER);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.DECEMBER);
			season[2] = c.getTime();
		}
		return season;
	}
    
	/**
	 * 1 第一季度
	 * 2 第二季度
	 * 3 第三季度
	 * 4 第四季度
	 * 
	 * @param date
	 * @return
	 */
	public static int getSeason(Date date) {

		int season = 0;

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
		case Calendar.JANUARY:
		case Calendar.FEBRUARY:
		case Calendar.MARCH:
			season = 1;
			break;
		case Calendar.APRIL:
		case Calendar.MAY:
		case Calendar.JUNE:
			season = 2;
			break;
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.SEPTEMBER:
			season = 3;
			break;
		case Calendar.OCTOBER:
		case Calendar.NOVEMBER:
		case Calendar.DECEMBER:
			season = 4;
			break;
		default:
			break;
		}
		return season;
	}
	
	/**
	 * @param date
	 * @return
	 * @desc 获取时间的23:59:59
	 */
	public static Date getLastHourOfDate(Date date) {
		Date d = parseDate(formatDate(date, "yyyy-MM-dd"));
		Date d1 = DateUtils.addHours(d, 23);
		Date d2 = DateUtils.addMinutes(d1, 59);
		Date d3 = DateUtils.addSeconds(d2, 59);
		return d3;
	}
	
	/**
	 * 毫秒数转日期字符串
	 * @param millis
	 * @param format
	 * @return
	 */
	public static String dateToString(long millis, String format) {
		Date date = new Date(millis);
		return dateToString(date, format);
	}
	
	public static String dateToString(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = "";
		if (date != null) {
			dateString = formatter.format(date);
		}
		return dateString;
	}
	
	/**
	 * 获取日期之间的天数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDaysBetween(Calendar d1, Calendar d2) {
		// swap dates so that d1 is start and d2 is end
		if (d1.after(d2)) {
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}
	
	/**
	 * 获取工作日
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getWorkingDay(Calendar d1, Calendar d2) {
		int result = -1;
		// swap dates so that d1 is start and d2 is end
		if (d1.after(d2)) {
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		// int betweendays = getDaysBetween(d1, d2);
		// int charge_date = 0;
		
		// 开始日期的日期偏移量
		int charge_start_date = 0;
		// 结束日期的日期偏移量
		int charge_end_date = 0;
		// 日期不在同一个日期内
		int stmp;
		int etmp;
		stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
		// 开始日期为星期六和星期日时偏移量为0
		if (stmp != 0 && stmp != 6) {
			charge_start_date = stmp - 1;
		}
		// 结束日期为星期六和星期日时偏移量为0
		if (etmp != 0 && etmp != 6) {
			charge_end_date = etmp - 1;
		}
		// }
		result = (getDaysBetween(getNextMonday(d1), getNextMonday(d2)) / 7)
				* 5 + charge_start_date - charge_end_date;
		// System.out.println("charge_start_date>" + charge_start_date);
		// System.out.println("charge_end_date>" + charge_end_date);
		// System.out.println("between day is-->" + betweendays);
		return result;
	}
	
	/**
	 * 获取中文日期
	 * @param date
	 * @return
	 */
	public String getChineseWeek(Calendar date) {
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		return dayNames[dayOfWeek - 1];
	}

	/**
	 * 获得日期的下一个星期一的日期
	 * @param date
	 * @return
	 */
	public static Calendar getNextMonday(Calendar date) {
		Calendar result = null;
		result = date;
		do {
			result = (Calendar) result.clone();
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	}

	/**
	 * 获取休息日
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getHolidays(Calendar d1, Calendar d2) {
		return getDaysBetween(d1, d2) - getWorkingDay(d1, d2);
	}
	
	public static String getYMD(Date date) {
		try {
			if (date == null) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String s = sdf.format(date);
			return s;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	public static String getYMDHMS(Date date) {
		try {
			if (date == null) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s = sdf.format(date);
			return s;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 是否闰年
	 * @param year	年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	
	/**
	 * 获取某年某月的最后一天
	 * @param year	年
	 * @param month	月
	 * @return 最后一天
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (DateUtils.isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}
	
	/**
	 * 获得本季度
	 * @param month
	 * @return
	 */
	public static String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;
		// years+"-"+String.valueOf(start_month)+"-1";//
		// getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days
				+ ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}
	
	/**
	 * 得到二个日期间的间隔天数
	 */
	public static long getTwoDay(String sj1, String sj2) throws Exception{
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		java.util.Date date = myFormatter.parse(sj1);
		java.util.Date mydate = myFormatter.parse(sj2);
		day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	// 计算当月最后一天,返回字符串
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取上个月的1号
	 * @return
	 */
	public static Date getPreviousMonthFirst() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		lastDate.set(Calendar.HOUR_OF_DAY, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);		
		
		return lastDate.getTime();
	}
	/**
	 * 获取上个月的最后一天
	 * @return
	 */
	public static Date getPreviousMonthEnd(){
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.DATE,-1);//减去一天，变为上个月最后一天
		lastDate.set(Calendar.HOUR_OF_DAY, 23);
		lastDate.set(Calendar.MINUTE, 59);
		lastDate.set(Calendar.SECOND, 59);
		return lastDate.getTime();	
	}
	
	/**
	 * 获取与当前时间相间隔的固定天数的起始时间（时分秒为：00:00:00）
	 * @param previousDay
	 * @return
	 */
	public static Date getDateBegin(int intervalDay){
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.DATE, intervalDay);
		lastDate.set(Calendar.HOUR_OF_DAY, 0);
		lastDate.set(Calendar.MINUTE, 0);
		lastDate.set(Calendar.SECOND, 0);		
		
		return lastDate.getTime();		
	}
	
	/**
	 * 获取与当前时间相间隔的固定天数的起始时间（时分秒为：23:59:59）
	 * @param intervalDay
	 * @return
	 */
	public static Date getDateEnd(int intervalDay){
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.DATE, intervalDay);
		lastDate.set(Calendar.HOUR_OF_DAY, 23);
		lastDate.set(Calendar.MINUTE, 59);
		lastDate.set(Calendar.SECOND, 59);		
		
		return lastDate.getTime();		
	}	

	/**
	 * 获取当月第一天
	 * @return
	 */
	public static Date getFirstDayOfMonth() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		return lastDate.getTime();
	}

	/**
	 * 获得本周星期日的日期
	 * @return
	 */
	public static String getCurrentWeekday() {
		int mondayPlus = DateUtils.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得当前日期与本周日相差的天数
	 * @return
	 */
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**
	 * 获得本周一的日期
	 * @return
	 */
	public static String getMondayOFWeek() {
		int mondayPlus = DateUtils.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}
	
	/**
	 * 获得上周星期日的日期
	 * @return
	 */
	public String getPreviousWeekSunday() {
		int weeks = -1;
		int mondayPlus = DateUtils.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得下周星期日的日期
	 * @return
	 */
	public static String getNextSunday() {
		int mondayPlus = DateUtils.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public static int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -maxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	/**
	 * 获得下个月第一天的日期
	 * @return
	 */
	public static Date getNextMonthFirst() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		return lastDate.getTime();
	}

	/**
	 * 获得下个月最后一天的日期
	 * @return
	 */
	public static Date getNextMonthEnd() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		return lastDate.getTime();
	}

	/**
	 *  获得明年最后一天的日期
	 * @return
	 */
	public static Date getNextYearEnd() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		return lastDate.getTime();
	}

	/**
	 * 获得明年第一天的日期
	 * @return
	 */
	public static Date getNextYearFirst() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		return lastDate.getTime();
	}

	/**
	 * 获得本年有多少天
	 * @return
	 */
	public static int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int maxYear = cd.get(Calendar.DAY_OF_YEAR);
		return maxYear;
	}
	
}
