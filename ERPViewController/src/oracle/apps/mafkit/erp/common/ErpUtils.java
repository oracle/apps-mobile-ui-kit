/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.erp.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ErpUtils {
    private SimpleDateFormat _standardDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    
    public ErpUtils() {
        super();
    }//constructor
    
    public Date setDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal.getTime();
    }//setDate
    
    public Date setTimeToDate(Date date, int hours, int minutes, int seconds){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);
        return cal.getTime();
    }//setTimeToDate

    public Date addDaysToToday(int daysToAdd) {
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DATE, daysToAdd);
        return calendarToDate(calender);
    }//addDaysToToday

    public Date calendarToDate(Calendar calender) {
        return calender.getTime();
    }//calendarToDate
    
    public long dateDiff(Date d1, Date d2, TimeUnit timeUnit){
        long milliseconds = d2.getTime() - d1.getTime();
        return timeUnit.convert(milliseconds, TimeUnit.MILLISECONDS);
    }//dateDiff

    //Accessors
    public void setStandardDateFormat(SimpleDateFormat f) { _standardDateFormat = f; }
    public SimpleDateFormat getStandardDateFormat() { return _standardDateFormat; }
}//ErpUtils
