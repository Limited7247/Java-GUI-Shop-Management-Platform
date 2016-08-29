/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LimitedSolution.Utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Limited
 */
public class DateTimeHelper {

    public static final String FORMAT_DATETIME_DEFAULT = "dd/MM/yyyy HH:mm:ss";
    public static final String FORMAT_DATE_DEFAULT = "dd/MM/yyyy";
    public static final String FORMAT_TIME_DEFAULT = "HH:mm:ss";

    private static Date getCurrentDate() {
        return new Date();
    }

    public static String getDateTimeString(Date date, String format) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCurrentDateTimeString() {
        return getDateTimeString(new Date());
    }

    public static String getDateTimeString(Date date) {
        return getDateTimeString(date, FORMAT_DATETIME_DEFAULT);
    }

    public static String getCurrentDateString() {
        return getDateString(new Date());
    }

    public static String getDateString(Date date) {
        return getDateTimeString(date, FORMAT_DATE_DEFAULT);
    }

    public static String getCurrentTimeString() {
        return getTimeString(new Date());
    }

    public static String getTimeString(Date date) {
        return getDateTimeString(date, FORMAT_TIME_DEFAULT);
    }

    public static Date getDate(String dateString) {
        try {
            return new SimpleDateFormat(FORMAT_DATETIME_DEFAULT).parse(dateString);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static boolean isValidDateString(String dateString) {
        try {
            new SimpleDateFormat(FORMAT_DATETIME_DEFAULT).parse(dateString);
            return true;
        } catch (ParseException ex) {
        }

        try {
            new SimpleDateFormat(FORMAT_DATE_DEFAULT).parse(dateString);
            return true;
        } catch (ParseException ex) {
        }

        return false;
    }

    public static String getDateStringFormat(Date date) {
        if (!getDateTimeString(date).isEmpty()) return FORMAT_DATETIME_DEFAULT;
        if (!getDateString(date).isEmpty()) return FORMAT_DATE_DEFAULT;

        return "";
    }
    
    public static String tryGetDateTimeString(Date date)
    {
        try {
            return new SimpleDateFormat(getDateStringFormat(date)).format(date);
        } catch (Exception e) {
            return "";
        }
    }
}
