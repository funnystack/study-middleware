package com.funny.combo.tools.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 一个关于日期操作的类,包含了一些常用日期操作方法
 * User: ZhangPeng
 * Date: 14-7-9
 * Time: 下午3:35
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {
    private static DateUtils instance = null;
    private static final String DEF_DATE_FORMAT = "yyyy/MM/dd";
	private static final String DEF_TIME_FORMAT = "HH:mm:ss";
	public static final String DEF_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEF_DATE_NO_TIME_FORMAT = "yyyy-MM-dd";

    /**
     * 这是一个单例模式的设计方式,在这个法中主要是得到本类的实例方法
     */
    public static synchronized DateUtils getInstance() {
        if (instance == null) {
            instance = new DateUtils();
        }
        return instance;
    }

    /**
     * 这是一个构造函数
     *
     */
    public DateUtils() {
    }

    /**
     *
     * 本方法主要是得到当前的日期
     *
     * @return
     *
     */
    public static Timestamp getSysTimestamp() {
    	final TimeZone zone = TimeZone.getTimeZone("GMT+8"); //获取中国时区
		TimeZone.setDefault(zone); //设置时区
        return new Timestamp((new Date()).getTime());
    }

    /**
     *
     * 得到某个日期的Timestamp
     *
     * @param datetime:输入的String类型的日期字符串
     *            格式为:2004-01-11
     * @return:Timestamp对象
     *
     */
    public static Timestamp getstrTimestamp(String datetime) {
        Date bb = new Date(datetime);
        if (bb != null) {
            return new Timestamp(bb.getTime());
        } else {
            return null;
        }
    }

    /**
     *
     * 按指定格式解析日期为timestamp对象
     *
     * @param datetime:输入的String类型的日期字符串
     *            格式为:2004-01-11
     * @param datepattern:指定输出的格式类型
     * @return Timestamp对象
     *
     */
    public static Timestamp getstrTimestamp(String datetime, String datepattern) {
        Date bb = null;

        try {
            DateFormat parser = new SimpleDateFormat(datepattern);
            bb = parser.parse(datetime);
            return new Timestamp(bb.getTime());
        } catch (ParseException ex) {
        }
        return null;
    }

    /**
     *
     * 得到本月开始时间
     *
     * @param strMon :输入的String类型的月份字符串 格式:2004-03
     * @return:String类型的本月开始时间
     *
     */
    public static String getCurMonBegin(String strMon) {
        String bdate = "";
        bdate = strMon + "-01";
        return bdate;
    }

    /**
     *
     * 得到本月结束时间
     *
     * @param strMon:输入的String类型的月份字符串
     *            格式:2004-03
     * @return :String类型的本月结束时间
     *
     */
    public static String getCurMonEnd(String strMon) {
        return getCurMonEnd(strMon, "yyyy-MM");
    }
    public static String getCurMonEndCHN(String strMon) {
        return getCurMonEnd(strMon, "yyyy年MM月");
    }

    public static String getCurMonEnd(String strMon, String pattern) {
        return null;
    }

    /**
     *
     * 获得指定日期所在week的起始日期，提供和返回的日期格式为yyyy-MM-dd
     *
     * @param strDate:提供的日期
     * @return 指定日期所在week的起始日期
     *
     */
    public static String getCurWeekBegin(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.parse(strDate, new ParsePosition(0));
        Calendar calendar = dateFormat.getCalendar();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, 1 - day);
        return dateFormat.format(calendar.getTime());
    }

    /**
     *
     * 获得指定日期所在week的终止日期，提供和返回的日期格式为yyyy-MM-dd
     *
     * @param strDate:提供的日期
     * @return:指定日期所在week的终止日期
     *
     */
    public static String getCurWeekEnd(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.parse(strDate, new ParsePosition(0));
        Calendar calendar = dateFormat.getCalendar();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, 7 - day);
        return dateFormat.format(calendar.getTime());
    }

    /**
     *
     * 日期大小的比较
     *
     * @param date:字符串格式的开始日期
     * @param edate:字符串格式的日期结束日期
     * @return:boolean 如果date>=edate则返回true,否则返回false
     *
     */
    public static boolean comDate(String date, String edate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = formatter.parse(date, new ParsePosition(0));
        Date dt2 = formatter.parse(edate, new ParsePosition(0));
        return (!dt1.before(dt2));
    }

    /**
     *
     * 日期大小的比较
     *
     * @param date:字符串格式的开始日期
     * @param edate:字符串格式的日期结束日期
     * @param pattern:指定的日期格式
     * @return:boolean 如果date>=edate则返回true,否则返回false
     *
     */
    public static boolean comDate(String date, String edate,String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date dt1 = formatter.parse(date, new ParsePosition(0));
        Date dt2 = formatter.parse(edate, new ParsePosition(0));
        return (!dt1.before(dt2));
    }

    /**
     *
     * String类型的日期加减
     *
     * @param date:系统时间；
     * @param type:加减的类型
     *            D 日期 M 月份 Y 年
     * @param into:加减的数量
     * @return:String 返回时间
     *
     */
    public static String addDateTime(String date, String type, int into) {
        date = date.replaceAll("-", "/");
        GregorianCalendar grc = new GregorianCalendar();
        grc.setTime(new Date(date));
        if (type.equals("D")) {
            grc.add(GregorianCalendar.DATE, into);
        } else if (type.equals("M")) {
            grc.add(GregorianCalendar.MONTH, into);
        } else if (type.equals("Y")) {
            grc.add(GregorianCalendar.YEAR, into);
        } else if (type.equals("HH")) {
            grc.add(GregorianCalendar.HOUR, into);
        } else if (type.equals("MI")) {
            grc.add(GregorianCalendar.MINUTE, into);
        } else if (type.equals("SS")) {
            grc.add(GregorianCalendar.SECOND, into);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String Sdate = new String(formatter.format(grc.getTime()));
        return Sdate;
    }

    public static String addTimeByPattern(String date, String type, int into,String pattern) {
        date = date.replaceAll("-", "/");
        GregorianCalendar grc = new GregorianCalendar();
        Date d =  parseDate(date, pattern);
        if(d == null){
        	d = new Date();
        }
        grc.setTime(d);
        if (type.equals("D")) {
            grc.add(GregorianCalendar.DATE, into);
        } else if (type.equals("M")) {
            grc.add(GregorianCalendar.MONTH, into);
        } else if (type.equals("Y")) {
            grc.add(GregorianCalendar.YEAR, into);
        } else if (type.equals("HH")) {
            grc.add(GregorianCalendar.HOUR, into);
        } else if (type.equals("MI")) {
            grc.add(GregorianCalendar.MINUTE, into);
        } else if (type.equals("SS")) {
            grc.add(GregorianCalendar.SECOND, into);
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        String Sdate = new String(formatter.format(grc.getTime()));
        return Sdate;
    }

    /**
     *
     * Timestamp型的日期加减
     *
     * @param date:Timestamp
     *            传入的基础时间
     * @param type:String
     *            加减的类型 D 日期 M 月份 Y 年
     * @param into:int
     *            加减的数量
     * @return:Timestamp 返回时间
     *
     */
    public static Timestamp addDateTime(Timestamp date, String type, int into) {
        GregorianCalendar grc = new GregorianCalendar();
        grc.setTime(new Date(date.getTime()));
        if (type.equals("D")) {
            grc.add(GregorianCalendar.DATE, into);
        } else if (type.equals("M")) {
            grc.add(GregorianCalendar.MONTH, into);
        } else if (type.equals("Y")) {
            grc.add(GregorianCalendar.YEAR, into);
        } else if (type.equals("HH")) {
            grc.add(GregorianCalendar.HOUR, into);
        } else if (type.equals("MI")) {
            grc.add(GregorianCalendar.MINUTE, into);
        } else if (type.equals("SS")) {
            grc.add(GregorianCalendar.SECOND, into);
        }
        return new Timestamp(new Date(grc.getTimeInMillis())
                .getTime());
    }

    /**
     *
     * 得到数据库时间日期
     *
     * @param date:数据库得到的带时间的字符串
     * @return:String 日期字符串
     *
     */
    public static String getDateString(String date) {
    	if(date!=null && date.length()>10)
    		date = date.substring(0, 10);
        return date;
    }

    /**
     *
     * 返回数据库时间的前10位字符串表示
     * @param date:Timestamp 日期
     * @return
     *
     */
    public static String getDateString(Timestamp date) {
		if (date == null)
			return null;
		String s = date.toString();
		return s.substring(0, 10);
	}

    /**
     *
     * 按指定格式转换日期字符串为日期对象,如果解析失败,返回null
     * @param date:日期字符串
     * @param pattern:指定的日期格式
     * @return:Date 日期
     *
     */
    public static Date parseDate(String date, String pattern) {
		if (date == null){
            return null;
        }

		try {
			DateFormat parser = new SimpleDateFormat(pattern);
			return parser.parse(date);
		} catch (ParseException ex) {
		}

		return null;
	}

    /**
     *
     * 按指定格式转换日期对象为日期字符串,如果解析失败,返回null
     * @param timestamp:Timestamp类型的日期
     * @param pattern:指定的日期格式
     * @return: String 日期
     * @deprecated 使用parseDate(Date, String)替代
     *
     */
    public static String parseDate(Timestamp timestamp, String pattern) {
		if (timestamp == null){
            return null;
        }
		DateFormat parser = new SimpleDateFormat(pattern);
		return parser.format(timestamp);
	}

    /**
     *
     * 计算两个时间的相差天数
     * @param time1:开始日期
     * @param time2:结束日期
     * @return:int 相差天数
     *
     */
	public static int getDiscrepancyNum(Timestamp time1, Timestamp time2) {
		int result = 0;
		if (time1 != null && time2 != null) {
			long temp = time1.getTime() - time2.getTime();
			if (temp > 0) {
				result = (int) ((temp / (24 * 60 * 60 * 1000)));
			} else {
				result = -(int) (((temp / (24 * 60 * 60 * 1000))));
			}

		}
		return result;
	}

	public static String getString(Date d, String pattern) {
		String ret;
		try {
			ret = new SimpleDateFormat(pattern).format(d);
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

	/**得到给定时间的第一天
	 * @param  inpdate
	 * @return String 返回给定时间的本月第一天
	 * */
	public static String getMonthFirstDay(String inpdate) {
		String[] inpdates =  inpdate.split("-");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(inpdates[0]),Integer.parseInt(inpdates[1]) -1, Integer.parseInt(inpdates[2]));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(calendar.getTime());
	}

	/**得到本月的最后一天*
	 * @param	inpdate
	 * @return String 返回给定时间的本月最后一天
	 * @return*/
	public static String getMonthLastDay(String inpdate) {
		String[] inpdates =  inpdate.split("-");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(inpdates[0]),Integer.parseInt(inpdates[1]) -1, Integer.parseInt(inpdates[2]));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(calendar.getTime());
	}

    /**
     * 格林威治时间字符串格式化，形如：Mon Nov 09 17:23:18 CST 2015 -- > 2015-11-09 17:23:18
     * @param str
     * @return
     * @throws ParseException
     */
    public static String formatGreenwich(String str,String formatPattern){
        String resultStr = null;
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
        if(StringUtils.isBlank(str) || StringUtils.isBlank(formatPattern)){
            return null;
        }
        try {
            Date date = sdf.parse(str);
            sdf = new SimpleDateFormat(formatPattern);
            resultStr = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    /***
     * 日期格式化为全制格式
     * @param date
     * @return
     */
    public static String formatDateToStr(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DEF_DATE_TIME_FORMAT);
        return sdf.format(date);
    }

    public static String formatDateToStr(Date date,String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
