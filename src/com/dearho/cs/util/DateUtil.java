package com.dearho.cs.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class DateUtil
{
	
	
	private static SimpleDateFormat time = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat time10 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat time4 = new SimpleDateFormat("yyyy");
	private static SimpleDateFormat time6 = new SimpleDateFormat("yyyy-MM");
	private static SimpleDateFormat time8 = new SimpleDateFormat("HH:mm:ss");
	
	private static SimpleDateFormat time14 = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	
	public static String getChar19DateString(Date date) {
		return time.format(date);
	}
	
	public static String getChar19DateString() {
		return time.format(new Date());
	}
	public static String getChar4DateString() {
		return time4.format(new Date());
	}
	public static String getChar6DateString() {
		return time6.format(new Date());
	}
	public static String getChar10DateString() {
		return time10.format(new Date());
	}
	public static String getChar8DateString() {
		return time8.format(new Date());
	}
	
	public static String getChar14DateString() {
		return time14.format(new Date());
	}
	
	public static int getCurrentYear(){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return year;
	}
	
	
	public static Timestamp getCurrentDateTime(){
         String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
         Timestamp result = Timestamp.valueOf(nowTime);
         return result;
	}
	
	
	public static String getFormatDateTime(Timestamp time){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
	}
	/**
	* @Title: getAddDate
	* @Description: 得到几天之后的日期。
	* @param addDays
	* @return
	* @throws
	*/
	public static String getAddDate(int addDays){
		 Date date = new Date();
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 cal.add(Calendar.DATE, addDays);
		 return time10.format(cal.getTime());
	}
	public static String getAddDate(String currentDate,int addDays){
		Date date;
		try {
			date = time10.parse(currentDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, addDays);
		return time10.format(cal.getTime());
	}
	
	public static String getAddMonth(String currentMonth,int addMonth){
		Date date;
		try {
			date = time6.parse(currentMonth);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, addMonth);
		return time6.format(cal.getTime());
	}
	
	/**
	* @Title: getIntervalDays
	* @Description: 等到两个日期间隔几天。
	* @param fromDate 格式 2014-03-19
	* @param endDate 格式 2014-10-20
	* @return
	* @throws
	*/
	public static Integer getIntervalDays(String fromDate,String endDate){
		Date fdate=null;
		Date edate=null;
		try {
			fdate = time10.parse(fromDate);
			edate = time10.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		Calendar cal = Calendar.getInstance();     
        cal.setTime(fdate);     
        long time1 = cal.getTimeInMillis();                  
        cal.setTime(edate);     
        long time2 = cal.getTimeInMillis();          
        long between_days=(time2-time1)/(1000*3600*24);     
//        return Integer.parseInt(String.valueOf(between_days));//相差几天
        return Integer.parseInt(String.valueOf(between_days))+1;
	}
	public static void main(String[] args) {
		String s = getAddMonth("2014-12", 1);
		System.out.println(s);
	int day =	getIntervalDays("2014-03-31","2014-04-01");
	System.out.println(day);
//		boolean is = isWorkDate("2014-05-17");
//		if (is){
//			System.out.println("是工作日");
//		}else{
//			System.out.println("不是工作日");
//		}
	
		System.out.println(getWeekDay(null));
	}
  public static final long daysInterval(Date fromDate, Date toDate)
  {
    return (toDate.getTime() - fromDate.getTime()) / 86400000L;
  }
  public static final long daysInterval(Date fromDate) {
    if (fromDate == null)
      return 0L;
    Date toDate = new Date();
    return daysInterval(fromDate, toDate);
  }
  @SuppressWarnings("deprecation")
public static final int yearsInterval(Date fromDate, Date toDate) {
    if ((fromDate == null) || (toDate == null))
      return 0;
    return toDate.getYear() - fromDate.getYear();
  }
  public static final String leftDays(Date fromDate) {
    Date toDate = new Date();
    long l = daysInterval(fromDate, toDate);
    l = l * -1L + 1L;
    if (l <= 0L)
      return "到期";
    return String.valueOf(l);
  }
  public static final int yearsInterval(Date fromDate) {
    if (fromDate == null)
      return 0;
    Date toDate = new Date();
    return yearsInterval(fromDate, toDate);
  }
  public static boolean equalsDate(Date d1, Date d2) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(d1).equals(sdf.format(d2));
  }
  public static int compareDate(Date d1, Date d2) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(d1).compareTo(sdf.format(d2));
  }
  public static String getDate(int days) {
    GregorianCalendar calTmp = new GregorianCalendar();
    calTmp.add(5, -1 * days);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(calTmp.getTime());
  }
  public static Date toDate(int days) {
    GregorianCalendar calTmp = new GregorianCalendar();
    calTmp.add(5, -1 * days);
    return calTmp.getTime();
  }
  public static Date getFirstWeek(Date d) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(d);
    int dayOfWeek = cal.get(7);
    cal.add(5, cal.getActualMinimum(7) - dayOfWeek + 1);
    return cal.getTime();
  }
  public static Date getLastWeek(Date d) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(d);
    int dayOfWeek = cal.get(7);
    cal.add(5, cal.getActualMaximum(7) - dayOfWeek + 2);
    return cal.getTime();
  }
  public static Date getFirstMonth(Date d) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(d);
    int dayOfWeek = cal.get(5);
    cal.add(5, cal.getActualMinimum(5) - dayOfWeek);
    return cal.getTime();
  }
  public static Date getLastMonth(Date d) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(d);
    int dayOfWeek = cal.get(5);
    cal.add(5, cal.getActualMaximum(5) - dayOfWeek + 1);
    return cal.getTime();
  }
  public static Date getFirstYear(Date d) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(d);
    int dayOfWeek = cal.get(6);
    cal.add(5, cal.getActualMinimum(6) - dayOfWeek);
    return cal.getTime();
  }
  public static Date getLastYear(Date d) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(d);
    int dayOfWeek = cal.get(6);
    cal.add(5, cal.getActualMaximum(6) - dayOfWeek + 1);
    return cal.getTime();
  }

  public static Date getStart() {
    Calendar cal = new GregorianCalendar();
    cal.set(2008, 6, 8, 0, 0, 0);
    return cal.getTime();
  }

  public static Date getEnd() {
    Calendar cal = new GregorianCalendar();
    return cal.getTime();
  }
  public static String formatDate(Date date,String format){
	  if(date == null){
		  date = new Date();
	  }
	  SimpleDateFormat sdf = new SimpleDateFormat(format);
	  return sdf.format(date);
  }
  public static Date parseDate(String dateStr,String format) throws ParseException{
	  if(StringHelper.isEmpty(dateStr) || StringHelper.isEmpty(format)){
		  return null;
	  }
	  SimpleDateFormat sdf = new SimpleDateFormat(format);
	  return sdf.parse(dateStr);
  }
  public static void main1(String[] args) throws Exception {
    System.out.println(getDate(0));
    System.out.println(compareDate(new Date(), toDate(1)));
  }
  
  public static String getWeekDay(Date date){
	  if(date == null){
		  date = new Date();
	  }
	  Calendar calendar = Calendar.getInstance();
	  calendar.setTime(date);
	  int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	  switch (dayOfWeek) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		default:
			return "";
	  }
	
  }
  /**
   * 
   * @Title: isPM
   * @Description:
   * @return
   * @        throws
   */
  public static boolean isPM(){
	  GregorianCalendar ca = new GregorianCalendar(); 
	  if(0==ca.get(GregorianCalendar.AM_PM)){
		  return true;
	  }else{
		  return false;
	  }
  }
}