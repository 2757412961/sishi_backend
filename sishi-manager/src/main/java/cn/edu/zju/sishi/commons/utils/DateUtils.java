package cn.edu.zju.sishi.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
  private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  public static Date parse(String date) {
    Date result = null;
    try {
      result = formatter.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static String format(Date date) {
    return formatter.format(date);
  }

  public static Date addByHour(Date date, int amount) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR_OF_DAY, amount);
    return calendar.getTime();
  }

  public static Date addByDay(Date date, int amount) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, amount);
    return calendar.getTime();
  }

  public static Date addByMinute(Date date, int amount) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    calendar.add(Calendar.MINUTE, amount);
    return calendar.getTime();
  }
}
