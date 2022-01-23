package com.jd.omni.membership.domain.util.date;

import com.jd.matrix.core.ducc.utils.JsonUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.apache.commons.lang3.time.DateFormatUtils.format;

/**
 * @author zaomeng
 * @date 2021/6/2 10:39 上午
 * @description
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyMMdd）
     */
    public static String getShortDate() {
        return getDate("yyMMdd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 按指定格式格式化日期
     */
    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (date != null && pattern != null && pattern.length() > 0) {
            formatDate = format(date, pattern);
        }
        return formatDate;
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
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDateWithPatterns(String str, String... myPatterns) {
        if (str == null) {
            return null;
        }
        try {
            if (myPatterns.length > 0) {
                return parseDate(str, myPatterns);
            } else {
                return parseDate(str, parsePatterns);
            }
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }


    /**
     * 获取开始日期对应的一定天数(days-1)的日期
     *
     * @param beginDate
     * @param days
     * @return
     * @throws Exception
     */
    public static List<String> getDateList(Date beginDate, int days) {
        List<String> dateList = new ArrayList<>();
        if (null == beginDate) {
            return dateList;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar to = Calendar.getInstance();
            to.setTime(beginDate);
            dateList.add(df.format(to.getTime()));
            for (int i = 0; i < days; i++) {
                to.add(Calendar.DAY_OF_MONTH, 1);
                dateList.add(df.format(to.getTime()));
            }
            return dateList;
        } catch (Exception e) {
            return dateList;
        }
    }

    /**
     * 以给定日期为基准，得到偏移量为 offsets 天的日期
     *
     * @param date
     * @param offsets
     * @return
     */
    public static Date dayOffsets(Date date, int offsets) {
        if (null == date) {
            return null;
        }
        int year = Integer.parseInt(format(date, "yyyy"));
        int month = Integer.parseInt(format(date, "MM")) - 1;
        int day = Integer.parseInt(format(date, "dd"));
        GregorianCalendar tempCalendar = new GregorianCalendar(year, month, day);
        tempCalendar.add(Calendar.DAY_OF_YEAR, offsets);
        return tempCalendar.getTime();
    }

    /**
     * 比较两个日期的大小
     * date1大于date2返回1
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return -1;
        }
        return date1.compareTo(date2);
    }


    public static Boolean checkDateWithPatterns(String str, String myPattern) {
        if (str == null) {
            return false;
        }
        try {
            parseDate(str, myPattern);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 获取1978年1月1日
     */
    public static Date getPreviousDate() {
        return parseDateWithPatterns("1978-01-01");
    }

    public static Long parseFormat(LocalDate date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Long.valueOf(date.format(format));
    }

    /**
     * 获取两个日期之间所有的日期
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getBetweenDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime() <= endDate.getTime()) {
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate = calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获得本月第一天0点时间
     * @return
     */
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获得本月最后一天23点59分59秒时间
     * @return
     */
    public static Date getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        return cal.getTime();
    }
}
