/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.sales.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SalesUtils {
    private SimpleDateFormat _standardDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    
    public SalesUtils() {
        super();
    }//constructor
    
    public Calendar dateToCalendar(Date date) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return calender;
    }//dateToCalendar
    
    public Date calendarToDate(Calendar calender) {
        return calender.getTime();
    }//calendarToDate

    public Date convertDateInFormat(SimpleDateFormat sdf, String dateToFormat) {
        Date date;
        try {
            date = sdf.parse(dateToFormat);
        } catch (Exception e) {
            date = new Date();
        }
        return date;
    }//convertDateInFormat

    public int findTodaysDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    } //findTodaysDay

    public Date addDaysToToday(int daysToAdd) {
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DATE, daysToAdd);
        return calendarToDate(calender);
    }//addDaysToToday
    
    public Date addHoursToToday(int hoursToAdd) {
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.HOUR, hoursToAdd);
        return calendarToDate(calender);
    }//addHoursToToday

    public Date addMinutesToToday(int minutesToAdd) {
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.MINUTE, minutesToAdd);
        return calendarToDate(calender);
    }//addMinutesToToday

    public Date setMinutesToDate(Date date, int minutes) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.set(Calendar.MINUTE, minutes);
        return calendarToDate(calender);
    }//setMinutesToDate
    
    public Date setTimeToDate(Date date, int hour, int minute, int second) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.set(Calendar.HOUR, hour);
        calender.set(Calendar.MINUTE, minute);
        calender.set(Calendar.SECOND, second);
        return calendarToDate(calender);
    }//setTimeToDate

    public String deriveInitialsForName(String name){
        name = name.trim();
        int stringLength = name.length();
        name = name.toUpperCase();
        String initials = name.charAt(0) + "";
        for (int i = 0; i < stringLength - 1; i++) {
            char x = name.charAt(i);
            if (x == ' ')
                initials = initials + name.charAt(i + 1);
        }//loop
        return initials;
    }//deriveInitialsForName

    //Accessors
    public void setStandardDateFormat(SimpleDateFormat f) { _standardDateFormat = f; }
    public SimpleDateFormat getStandardDateFormat() { return _standardDateFormat; }
}//SalesUtils
