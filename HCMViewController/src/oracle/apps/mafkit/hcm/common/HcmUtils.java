/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.hcm.common;

import java.util.Calendar;
import java.util.Date;

public class HcmUtils {
    
    public HcmUtils(){
        super();
    }//constructor
    
    public Date setDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal.getTime();
    }//setDate
    
    public Date addDaysToToday(int daysToAdd) {
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DATE, daysToAdd);
        return calendarToDate(calender);
    }//addDaysToToday

    public Date calendarToDate(Calendar calender) {
        return calender.getTime();
    }//calendarToDate
    
}//HcmUtils
