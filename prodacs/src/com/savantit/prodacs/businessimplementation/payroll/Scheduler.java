package com.savantit.prodacs.businessimplementation.payroll;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

/*
 * Created on Feb 17, 2005
 *
 * ClassName	:  	Scheduler.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Scheduler
{

    /**
     * 
     */
    public Scheduler()
    {
    }
    
    public boolean createWeeklyTimer(Date startDate)
    {
        Timer timer = new Timer();
        timer.schedule(new WeeklyTask(),startDate,7*24*60*60*1000);
        PayrollTimerManager.weeklyTimer = timer;
        return true;
    }
    
    public boolean cancelWeeklyTimer()
    {
        PayrollTimerManager.weeklyTimer.cancel();
        return true;
    }
    public boolean updateWeeklyTimer(Date startDate)
    {        
        Timer timer = new Timer();
        timer.schedule(new WeeklyTask(),startDate,122333);
        PayrollTimerManager.weeklyTimer = timer;
        return true;
    }
    
    public boolean createMonthlyTimer(int day)
    {
        System.out.println("inside the createMonthlyTimer");
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.DAY_OF_MONTH,day);
        Timer timer = new Timer();
        timer.schedule(new MonthlyTask(day),gc.getTime());
        PayrollTimerManager.monthlyTimer = timer;
        return true;
    }

    public boolean cancelMonthlyTimer()
    {
        PayrollTimerManager.monthlyTimer.cancel();
        return true;
    }
    public boolean updateMonthlyTimer(int day)
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.DAY_OF_MONTH,day);
        Timer timer = new Timer();
        timer.schedule(new MonthlyTask(day),gc.getTime());
        PayrollTimerManager.monthlyTimer = timer;
        return true;
    }

    public static void main(String[] args)
    {
        Timer timer = new Timer();
        timer.schedule(new MonthlyTask(1),new Date());
        
    }
}

/*
*$Log: Scheduler.java,v $
*Revision 1.2  2005/02/18 10:52:35  kduraisamy
*task code modified.
*
*Revision 1.1  2005/02/18 06:33:53  kduraisamy
*initial commit.
*
*
*/