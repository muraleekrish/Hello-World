package com.savantit.prodacs.businessimplementation.payroll;


import java.util.TimerTask;



/*
 * Created on Feb 18, 2005
 *
 * ClassName	:  	WeeklyTask.java
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
public class WeeklyTask extends TimerTask
{
    int day;
    public WeeklyTask()
    {

        // TODO Auto-generated constructor stub
    }
     /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        System.out.println("Weekly Task");
       // GregorianCalendar gc = new GregorianCalendar();
        //gc.set(Calendar.DAY_OF_MONTH,day);
        //gc.add(Calendar.MONTH,1);
        //PayrollTimerManager.monthlyTimer.schedule(new MonthlyTask(day),gc.getTime());        
    }
}

/*
*$Log: WeeklyTask.java,v $
*Revision 1.2  2005/02/23 05:48:09  kduraisamy
*imports organised.
*
*Revision 1.1  2005/02/18 06:33:53  kduraisamy
*initial commit.
*
*
*/