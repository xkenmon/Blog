package cn.xkenmon.blog.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static String getShortTime(Date date) {
        if (date == null){
            return "未知时间";
        }
        String shortString;
        long now = Calendar.getInstance().getTimeInMillis();
        long delTime = (now - date.getTime()) / 1000;
        if (delTime > 365 * 24 * 60 * 60) {
            shortString = (int) (delTime / (365 * 24 * 60 * 60)) + "年前";
        } else if (delTime > 24 * 60 * 60) {
            shortString = (int) (delTime / (24 * 60 * 60)) + "天前";
        } else if (delTime > 60 * 60) {
            shortString = (int) (delTime / (60 * 60)) + "小时前";
        } else if (delTime > 60) {
            shortString = (int) (delTime / (60)) + "分钟前";
        } else if (delTime > 1) {
            shortString = delTime + "秒前";
        } else {
            shortString = "1秒前";
        }
        return shortString;
    }
}
