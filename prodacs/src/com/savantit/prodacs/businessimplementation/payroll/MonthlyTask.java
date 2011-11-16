package com.savantit.prodacs.businessimplementation.payroll;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimerTask;
/*
 * Created on Feb 17, 2005
 *
 * ClassName	:  	MonthlyTask.java
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
public class MonthlyTask extends TimerTask
{
    int day;
    PayRollDetailsManager objPayRollDetailsManager = new PayRollDetailsManager();
    /**
     * 
     */
    public MonthlyTask(int day)
    {
        this.day = day;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        System.out.println("Monthly Task");
        
        try
        {
            objPayRollDetailsManager.createNewPayrollCycleStat();
        }
        catch (SQLException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (ParseException e2)
        {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        catch (PayRollException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.DAY_OF_MONTH,day);
        gc.add(Calendar.MONTH,1);
        PayrollTimerManager.monthlyTimer.schedule(new MonthlyTask(day),gc.getTime());        
    }
    
}

/*
*$Log: MonthlyTask.java,v $
*Revision 1.6  2005/07/15 07:40:37  kduraisamy
*initial commit.
*
*Revision 1.5  2005/04/22 14:24:11  kduraisamy
*exception Properly Handled.
*
*Revision 1.4  2005/04/21 08:01:31  kduraisamy
*Exception added.
*
*Revision 1.3  2005/04/20 12:31:45  kduraisamy
*Exceptions caught in method run()
*
*Revision 1.2  2005/02/18 10:52:35  kduraisamy
*task code modified.
*
*Revision 1.1  2005/02/18 06:33:53  kduraisamy
*initial commit.
*
*
*/