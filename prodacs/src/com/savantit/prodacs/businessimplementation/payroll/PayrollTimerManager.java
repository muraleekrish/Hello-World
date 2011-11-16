/*
 * Created on Feb 15, 2005
 *
 * ClassName	:  	PayrollTimer.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.payroll;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author kduraisamy
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public final class PayrollTimerManager extends TimerTask
{
    public static Timer weeklyTimer = new Timer();
    public static Timer monthlyTimer = new Timer();
    PayRollDetailsManager objPayRollDetailsManager = new PayRollDetailsManager();
    int days;
       
     /**
      * Construct and use a TimerTask and Timer.
     */
      
      public static void main ( String[] arguments ) 
      {
 
        TimerTask createPayroll  = new PayrollTimerManager();
        //perform the task once a day at 4 a.m., starting tomorrow morning
        //(other styles are possible as well)
   
        Timer timer = new Timer();
        //timer.scheduleAtFixedRate(createPayroll,getNextMonth(),1000*5);
        //int i = 5;
        int j = 5;
        timer.scheduleAtFixedRate(createPayroll,5*1000,(++j)*1000);
        
      }
      /**
       * Implements TimerTask's abstract run method.
       */
      
      public void run()
      {
      
          System.out.println("Fetching mail...");
          //objPayRollDetailsManager.createNewPayrollCycleStat();
         // GregorianCalendar gc = new GregorianCalendar();
         // days = gc.getMaximum(GregorianCalendar.DAY_OF_MONTH);
      }

      // PRIVATE ////

      //expressed in milliseconds
     
    
      
     // private long fONCE_PER_MONTH = 1000*60*60*24*days;
      private final static int fONE_MONTH = 1;
      private final static int fTWELVE_AM = 12;
      private final static int fZERO_MINUTES = 0;

      private static int getDelay(int i)
      {
          System.out.println("i :"+i); 
          return (++i);
      }
      
      private static int getDelay1(int j)
      {
          System.out.println("j :"+j); 
          return (++j);
      }
      
      
      private static Date getNextMonth()
      {
          Calendar nextmonth = new GregorianCalendar();
          // nextmonth.add(Calendar.MONTH, fONE_MONTH);
          //Calendar result = new GregorianCalendar(nextmonth.get(Calendar.YEAR),nextmonth.get(Calendar.MONTH),1,fTWELVE_AM,fZERO_MINUTES);
          // return result.getTime();
          return nextmonth.getTime();
          
      }
 }



/*
 * $Log: PayrollTimerManager.java,v $
 * Revision 1.1  2005/02/18 06:33:53  kduraisamy
 * initial commit.
 *
 *  
 */