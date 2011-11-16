/*
 * Created on Dec 13, 2005
 *
 * ClassName	:  	ScheduleTaskMonthly.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.datrics.scheduler.scheduletask;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import com.datrics.scheduler.taskrunner.TaskRunner;
import com.datrics.scheduler.valueobjects.ExecuteDateDetails;

/**
 * @author sperumal
 *
 */
public class ScheduleTaskMonthly extends TimerTask
{
    private String jobName;
    private String jobClass;
    private int noOfMonthsInterval;
    private ExecuteDateDetails objDateDetails;
    private int onceHr;
    private int onceMin;
    private int onceSecs;
    private int repeatedHr;
    private int repeatedMin;
    private int repeatedSecs;
    private int startTimeHr;
    private int startTimeMin;
    private int startTimeSec;
    private int endTimeHr;
    private int endTimeMin;
    private int endTimeSec;
    private Date scheduledDate;
    private Date jobStartDate;
    private Date jobEndDate;
    private boolean isFirst = false;

    public ScheduleTaskMonthly(boolean isFirst,String jobName,String jobClass,int noOfMonthsInterval,ExecuteDateDetails objDateDetails,int onceHr,int onceMin,int onceSecs,int repeatedHr,int repeatedMin,int repeatedSecs,int startTimeHr,int startTimeMin,int startTimeSec,int endTimeHr,int endTimeMin,int endTimeSec,Date jobStartDate,Date jobEndDate)
    {
         this.jobName = jobName;
         this.jobClass = jobClass;
         this.noOfMonthsInterval = noOfMonthsInterval;
         this.objDateDetails = objDateDetails;
         this.onceHr = onceHr;
         this.onceMin = onceMin;
         this.onceSecs = onceSecs;
         this.repeatedHr = repeatedHr;
         this.repeatedMin = repeatedMin;
         this.repeatedSecs = repeatedSecs;
         this.startTimeHr = startTimeHr;
         this.startTimeMin = startTimeMin;
         this.startTimeSec = startTimeSec;
         this.endTimeHr =  endTimeHr;
         this.endTimeMin = endTimeMin;
         this.endTimeSec = endTimeSec;
         this.jobStartDate = jobStartDate;
         this.jobEndDate = jobEndDate;
         this.isFirst = isFirst;
         
         GregorianCalendar gc = new GregorianCalendar();
         gc.setTime(jobStartDate);
         if(objDateDetails.getExecuteDate() > 0)
         {
             gc.set(Calendar.DATE,objDateDetails.getExecuteDate());
         }
         else if(objDateDetails.getDayOffset() > 0)
         {
             int dayOffset = objDateDetails.getDayOffset();
             System.out.println("day offset:"+dayOffset);
             int executeDay = objDateDetails.getExecuteDay();
             //move to specified day in that month.
             while(gc.get(Calendar.DAY_OF_WEEK)!=executeDay)
             {
                 gc.add(Calendar.DATE,1);
             }
             //add week offset-1 after day is moved.
             if(dayOffset == 5)
             {
                 GregorianCalendar checkgc = new GregorianCalendar();
                 checkgc.setTime(gc.getTime());
                 checkgc.add(Calendar.WEEK_OF_MONTH,dayOffset-1);
                 if(checkgc.get(Calendar.MONTH) != gc.get(Calendar.MONTH))
                     dayOffset = 4;     
             }
             gc.add(Calendar.WEEK_OF_MONTH,dayOffset-1);
         }
         this.scheduledDate  = gc.getTime();
    }
   
    public void run()
    {

        System.out.println("ScheduleTaskMonthly.run() started ");
        Timer timer = new Timer();
        if(jobStartDate!=null)
        {
	        if(onceHr > 0 || onceMin > 0 || onceSecs > 0)
	        {
	            GregorianCalendar monthStartTime = new GregorianCalendar();
	            monthStartTime.setTime(scheduledDate);
	            if(isFirst)
	            {
	                System.out.println("Job start Date:"+jobStartDate);
		            monthStartTime.set(GregorianCalendar.HOUR_OF_DAY,onceHr);
		            monthStartTime.set(GregorianCalendar.MINUTE,onceMin);
		            monthStartTime.set(GregorianCalendar.SECOND,onceSecs);
	            }
	            if(jobEndDate != null)
	            {
	                System.out.println(" Scheduler for Monthly Once. EndDate  is bounded " );
	                if(monthStartTime.getTime().compareTo(jobEndDate) <= 0)
	                {
                        Timer newTimer = new Timer();
                        System.out.println(" Scheduled for day in a Month "+ monthStartTime.getTime());
                        newTimer.schedule(new TaskRunner(jobName,jobClass),monthStartTime.getTime());
                        System.out.println(" End of Scheduled for day in a Month "+ monthStartTime.getTime());
                    }
	                //move to next month start date 1.
	                monthStartTime.add(Calendar.MONTH,noOfMonthsInterval);
	                monthStartTime.set(Calendar.DATE,1);
	                if(monthStartTime.getTime().compareTo(jobEndDate) <= 0)
			        {
	                    
			            System.out.println(" Schedule for next Months interval "+ monthStartTime.getTime());
	                    new Timer().schedule(new ScheduleTaskMonthly(false,jobName,jobClass,noOfMonthsInterval,objDateDetails,onceHr,onceMin,onceSecs, repeatedHr, repeatedMin, repeatedSecs, startTimeHr, startTimeMin, startTimeSec,endTimeHr,endTimeMin, endTimeSec, monthStartTime.getTime(),jobEndDate),monthStartTime.getTime());
	                    System.out.println(" End of Schedule for next Months interval "+ monthStartTime.getTime());
	                }
	            }
	            else
	            {
	                System.out.println(" Scheduler for Monthly Once. EndDate  is Unbounded " );
                    Timer newTimer = new Timer();
                    System.out.println(" Scheduled for day in a Month "+ monthStartTime.getTime());
                    newTimer.schedule(new TaskRunner(jobName,jobClass),monthStartTime.getTime());
                    System.out.println(" End of Scheduled for day in a Month "+ monthStartTime.getTime());
                    //move to next month start date 1.
	                monthStartTime.add(Calendar.MONTH,noOfMonthsInterval);
	                monthStartTime.set(Calendar.DATE,1);
	                
	                //call schedule for next month.
		            System.out.println(" Schedule for next Months interval "+ monthStartTime.getTime());
                    new Timer().schedule(new ScheduleTaskMonthly(false,jobName,jobClass,noOfMonthsInterval,objDateDetails,onceHr,onceMin,onceSecs, repeatedHr, repeatedMin, repeatedSecs, startTimeHr, startTimeMin, startTimeSec,endTimeHr,endTimeMin, endTimeSec, monthStartTime.getTime(),jobEndDate),monthStartTime.getTime());
                    System.out.println(" End of Schedule for next Months interval "+ monthStartTime.getTime());
	                
	            }
	        }
	        else if(repeatedHr > 0 || repeatedMin > 0 || repeatedSecs > 0)
	        {
	            GregorianCalendar monthStartTime = new GregorianCalendar();
	            monthStartTime.setTime(scheduledDate);
	            if(isFirst)
	            {
	                System.out.println("Job start Date:"+jobStartDate);
		            monthStartTime.set(GregorianCalendar.HOUR_OF_DAY,startTimeHr);
		            monthStartTime.set(GregorianCalendar.MINUTE,startTimeMin);
		            monthStartTime.set(GregorianCalendar.SECOND,startTimeSec);
	            }
	            if(jobEndDate != null)
	            {
	                System.out.println(" Scheduler for Monthly Repeated Scheduling. EndDate  is bounded " );
	                if(monthStartTime.getTime().compareTo(jobEndDate) <= 0)
	                {
                        Timer newTimer = new Timer();
                        System.out.println(" Scheduled for day in a Month "+ monthStartTime.getTime());
                        GregorianCalendar repeated = new GregorianCalendar();
		                repeated.setTime(monthStartTime.getTime());
                        System.out.println("Start of Repeated Scheduling for day in a Month "+ repeated.getTime());
		                GregorianCalendar repeatedEnd = new GregorianCalendar();
		                repeatedEnd.setTime(monthStartTime.getTime());
		                repeatedEnd.set(Calendar.HOUR_OF_DAY,endTimeHr);
		                repeatedEnd.set(Calendar.MINUTE,endTimeMin);
		                repeatedEnd.set(Calendar.SECOND,endTimeSec);//to check before.
		                while(repeated.getTime().compareTo(repeatedEnd.getTime())<=0)
		                {
		                    System.out.println(" Scheduled for time in that day "+ repeated.getTime());
		                    newTimer.schedule(new TaskRunner(jobName,jobClass),repeated.getTime());
		                    repeated.add(Calendar.HOUR_OF_DAY,repeatedHr);
		                    repeated.add(Calendar.MINUTE,repeatedMin);
		                    repeated.add(Calendar.SECOND,repeatedSecs);
			            }
		                System.out.println(" End of Scheduled time in that day "+ repeated.getTime());
			            
                        System.out.println(" End of  Month Scheduling "+ monthStartTime.getTime());
                    }
	                //move to next month start date 1.
	                monthStartTime.add(Calendar.MONTH,noOfMonthsInterval);
	                monthStartTime.set(Calendar.DATE,1);
	                if(monthStartTime.getTime().compareTo(jobEndDate) <= 0)
			        {
	                    
			            System.out.println(" Schedule for next Months interval "+ monthStartTime.getTime());
	                    new Timer().schedule(new ScheduleTaskMonthly(false,jobName,jobClass,noOfMonthsInterval,objDateDetails,onceHr,onceMin,onceSecs, repeatedHr, repeatedMin, repeatedSecs, startTimeHr, startTimeMin, startTimeSec,endTimeHr,endTimeMin, endTimeSec, monthStartTime.getTime(),jobEndDate),monthStartTime.getTime());
	                    System.out.println(" End of Schedule for next Months interval "+ monthStartTime.getTime());
	                }
	            }
	            else
	            {

	                System.out.println(" Scheduler for Repeated Scheduling . EndDate  is Unbounded " );
	                
                    Timer newTimer = new Timer();
                    System.out.println(" Scheduled for day in a Month "+ monthStartTime.getTime());
                    GregorianCalendar repeated = new GregorianCalendar();
	                repeated.setTime(monthStartTime.getTime());
                    System.out.println("Start of Repeated Scheduling for day in a Month "+ repeated.getTime());
	                GregorianCalendar repeatedEnd = new GregorianCalendar();
	                repeatedEnd.setTime(monthStartTime.getTime());
	                repeatedEnd.set(Calendar.HOUR_OF_DAY,endTimeHr);
	                repeatedEnd.set(Calendar.MINUTE,endTimeMin);
	                repeatedEnd.set(Calendar.SECOND,endTimeSec);//to check before.
	                while(repeated.getTime().compareTo(repeatedEnd.getTime())<=0)
	                {
	                    System.out.println(" Scheduled for time in that day "+ repeated.getTime());
	                    newTimer.schedule(new TaskRunner(jobName,jobClass),repeated.getTime());
	                    repeated.add(Calendar.HOUR_OF_DAY,repeatedHr);
	                    repeated.add(Calendar.MINUTE,repeatedMin);
	                    repeated.add(Calendar.SECOND,repeatedSecs);
		            }
	                System.out.println(" End of Scheduled time in that day "+ repeated.getTime());
			            
                    System.out.println(" End of  Month Scheduling "+ monthStartTime.getTime());
                    //move to next month start date 1.
	                monthStartTime.add(Calendar.MONTH,noOfMonthsInterval);
	                monthStartTime.set(Calendar.DATE,1);
	                
                    System.out.println(" Schedule for next Months interval "+ monthStartTime.getTime());
                    new Timer().schedule(new ScheduleTaskMonthly(false,jobName,jobClass,noOfMonthsInterval,objDateDetails,onceHr,onceMin,onceSecs, repeatedHr, repeatedMin, repeatedSecs, startTimeHr, startTimeMin, startTimeSec,endTimeHr,endTimeMin, endTimeSec, monthStartTime.getTime(),jobEndDate),monthStartTime.getTime());
                }
	        }
	        System.out.println(" Outside Scheduler ");    
        }
    }
    
    public static void main(String[] args)
    {
        
        ExecuteDateDetails obj = new ExecuteDateDetails();
        //obj.setExecuteDate(13);/*1- sunday,2-mon,3-tue.....7-sat.
        obj.setOccurDayOffset("last");
        obj.setExecuteDay(5);
        new Timer().schedule(new ScheduleTaskMonthly(true,"","com.datrics.scheduler.taskrunner.Sample",1,obj,0,0,0,1,0,0,11,0,0,14,0,0,new Date("01-nov-05"),null),0);
    }

}

/*
*$Log: ScheduleTaskMonthly.java,v $
*Revision 1.1  2005/12/14 12:33:17  kduraisamy
*initial commit for scheduling reports.
*
*Revision 1.2  2005/12/14 12:21:09  sperumal
*schedule for all are updated
*
*Revision 1.1  2005/12/13 11:54:49  sperumal
*initial commit
*
*
*/