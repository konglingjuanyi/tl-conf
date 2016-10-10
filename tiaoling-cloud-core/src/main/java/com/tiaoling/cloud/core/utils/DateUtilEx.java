package com.tiaoling.cloud.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by yhl on 2016/9/30.
 */
public class DateUtilEx {
    public static int[] getWeekNumTop4(int weekNum)
    {
        int[] result=new int[4];
        for (int i =0 ; i <4; i++) {
            result[i]=weekNum--;
        }
        return result;
    }


    /**
     * 获取当前年
     * @return
     */
    public static int getYear(){
        Calendar now = Calendar.getInstance();
        int yearCount=now.get(Calendar.YEAR);
        return yearCount;
    }


    /**
     * 取得当前日期是多少周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }



    /**
     * 得到某一年周的总数
     *
     * @param year
     * @return
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return getWeekOfYear(c.getTime())+1;
    }

    /**
     * 获取某年某周的开始日期(以星期日开始)
     * @param year
     * @param week
     * @param format
     * @return
     */
    public static String getFirstDayOfWeekFormat(int year, int week,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);//设置年
        c.set(Calendar.WEEK_OF_YEAR,week);//设置星期
        c.set(Calendar.DAY_OF_WEEK, 1);//本周第一天，以星期日开始
        return sdf.format(c.getTime());
    }

    /**
     * 获取某年某周结束时间(以星期六结束)
     * @param year
     * @param week
     * @param format
     * @return
     */
    public static String getLastDayOfWeekFormat(int year, int week,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);//设置年
        c.set(Calendar.WEEK_OF_YEAR,week);//设置星期
        c.set(Calendar.DAY_OF_WEEK, 7);//本周最后一天，以星期六结束
        return sdf.format(c.getTime());
    }
    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort(Date date) {
        //Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(date);
        return dateString;
    }
}
