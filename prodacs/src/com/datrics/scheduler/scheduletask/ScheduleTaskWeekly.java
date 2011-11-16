package com.datrics.scheduler.scheduletask;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import com.datrics.scheduler.taskrunner.TaskRunner;
import com.datrics.scheduler.valueobjects.ExecuteWeeklyDetails;


/*
 * Created on Dec 9, 2005
 */

/**
 * @author sduraisamy
 */
public class ScheduleTaskWeekly extends TimerTask 
{
    String jobName;
    String jobClass;
    int noOfWeeksInterval;
    int onceHr;
    int onceMin;
    int onceSecs;
    int repeatedHr;
    int repeatedMin;
    int repeatedSecs;
    int startTimeHr;
    int startTimeMin;
    int startTimeSec;
    int endTimeHr;
    int endTimeMin;
    int endTimeSec;
    Date jobStartDate;
    Date jobEndDate;
    ExecuteWeeklyDetails objExecuteWeeklyDetails = null;
    boolean isFirst = false;
    //private long weeksToMilliSecs = 7*24 * 60 * 60 * 1000;
    public ScheduleTaskWeekly(boolean isFirst,String jobName,String jobClass,int noOfWeeksInterval,int onceHr,int onceMin,int onceSecs,int repeatedHr,int repeatedMin,int repeatedSecs,int startTimeHr,int startTimeMin,int startTimeSec,int endTimeHr,int endTimeMin,int endTimeSec,Date jobStartDate,Date jobEndDate,ExecuteWeeklyDetails objExecuteWeeklyDetails)
    {
        this.isFirst = isFirst; 
        this.jobName = jobName;
        this.jobClass = jobClass;
        this.noOfWeeksInterval = noOfWeeksInterval;
        this.onceHr = onceHr;
        this.onceMin = onceMin;
        this.onceSecs = onceSecs;
        this.repeatedHr = repeatedHr;
        this.repeatedMin = repeatedMin;
        this.repeatedSecs = repeatedSecs;
        this.startTimeHr = startTimeHr;
        this.startTimeMin = startTimeMin;
        this.startTimeSec = startTimeSec;
        this.endTimeHr = endTimeHr;
        this.endTimeMin = endTimeMin;
        this.endTimeSec = endTimeSec;
        this.jobStartDate = jobStartDate;
        this.jobEndDate = jobEndDate;
        this.objExecuteWeeklyDetails = objExecuteWeeklyDetails;
    }
    public void run()
    {
        System.out.println("ScheduleTaskWeekly.run() started ");
        Timer timer = new Timer();
        
        if(jobStartDate!=null)
        {
	        if(onceHr > 0 || onceMin > 0 || onceSecs > 0)
	        {
	            GregorianCalendar weekStartTime = new GregorianCalendar();
	            weekStartTime.setTime(jobStartDate);
	            if(isFirst)
	            {
		            weekStartTime = new GregorianCalendar();
		            weekStartTime.setTime(jobStartDate);
		            System.out.println("Date:"+jobStartDate);
		            weekStartTime.set(GregorianCalendar.HOUR_OF_DAY,onceHr);
		            weekStartTime.set(GregorianCalendar.MINUTE,onceMin);
		            weekStartTime.set(GregorianCalendar.SECOND,onceSecs);
	            }
	            boolean flg = false;
	            if(jobEndDate != null)
	            {
	                System.out.println(" Scheduler for weekly Once EndDate  is not null " );
	                while(objExecuteWeeklyDetails.getRemainingExecutions() > 0 && weekStartTime.getTime().compareTo(jobEndDate) <= 0)
	                {
	                    switch(weekStartTime.get(GregorianCalendar.DAY_OF_WEEK))
	                    {
	                    
	                    case 1:
	                        if(objExecuteWeeklyDetails.isSunday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 2:
	                        if(objExecuteWeeklyDetails.isMonday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 3:
	                        if(objExecuteWeeklyDetails.isTuesday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 4:
	                        if(objExecuteWeeklyDetails.isWednesday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 5:
	                        if(objExecuteWeeklyDetails.isThursday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 6:
	                        if(objExecuteWeeklyDetails.isFriday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 7:
	                        if(objExecuteWeeklyDetails.isSaturday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                        
	                        
	                    }
	                    if(flg)
	                    {
	                        Timer newTimer = new Timer();
	                        newTimer.schedule(new TaskRunner(jobName,jobClass),weekStartTime.getTime());
	                        System.out.println(" Scheduled for day in a week "+ weekStartTime.getTime());
	                        objExecuteWeeklyDetails.setRemainingExecutions(objExecuteWeeklyDetails.getRemainingExecutions() - 1);
	                        flg = false;
	                        
	                    }
	                    weekStartTime.add(Calendar.DATE,1);
	                }
	                if(objExecuteWeeklyDetails.getRemainingExecutions()==0 && weekStartTime.getTime().compareTo(jobEndDate) <= 0)
			        {
			            //int diff = (7 - startDay + 1)%7;
			            weekStartTime.add(GregorianCalendar.DATE,(noOfWeeksInterval-1)*7  + 1);
			            System.out.println(" Schedule for next week "+ weekStartTime.getTime());
	                    objExecuteWeeklyDetails.setRemainingExecutions(objExecuteWeeklyDetails.getNoOfExecutions());
	                    new Timer().schedule(new ScheduleTaskWeekly(false,jobName,jobClass,noOfWeeksInterval,onceHr,onceMin,onceSecs,repeatedHr,repeatedMin,repeatedSecs, startTimeHr,startTimeMin,startTimeSec,endTimeHr,endTimeMin,endTimeSec,weekStartTime.getTime(),jobEndDate,objExecuteWeeklyDetails),weekStartTime.getTime());
	                    
			        }
	            }
	            else
	            {

	                System.out.println(" Scheduler for weekly Once EndDate  is  null " );
	                while(objExecuteWeeklyDetails.getRemainingExecutions() > 0)
	                {
	                    switch(weekStartTime.get(GregorianCalendar.DAY_OF_WEEK))
	                    {
	                    
	                    case 1:
	                        if(objExecuteWeeklyDetails.isSunday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 2:
	                        if(objExecuteWeeklyDetails.isMonday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 3:
	                        if(objExecuteWeeklyDetails.isTuesday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 4:
	                        if(objExecuteWeeklyDetails.isWednesday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 5:
	                        if(objExecuteWeeklyDetails.isThursday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 6:
	                        if(objExecuteWeeklyDetails.isFriday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                    case 7:
	                        if(objExecuteWeeklyDetails.isSaturday())
	                        {
	                            flg = true;
	                        }
	                        break;
	                        
	                        
	                    }
	                    if(flg)
	                    {
	                        Timer newTimer = new Timer();
	                        newTimer.schedule(new TaskRunner(jobName,jobClass),weekStartTime.getTime());
	                        System.out.println(" Scheduled for day in a week "+ weekStartTime.getTime());
	                        objExecuteWeeklyDetails.setRemainingExecutions(objExecuteWeeklyDetails.getRemainingExecutions() - 1);
	                        flg = false;
	                        
	                    }
	                    weekStartTime.add(Calendar.DATE,1);
	                }
	                if(objExecuteWeeklyDetails.getRemainingExecutions()==0)
			        {
			            weekStartTime.add(GregorianCalendar.DATE,(noOfWeeksInterval-1)*7  + 1);
			            System.out.println(" Schedule for next week "+ weekStartTime.getTime());
	                    objExecuteWeeklyDetails.setRemainingExecutions(objExecuteWeeklyDetails.getNoOfExecutions());
	                    new Timer().schedule(new ScheduleTaskWeekly(false,jobName,jobClass,noOfWeeksInterval,onceHr,onceMin,onceSecs,repeatedHr,repeatedMin,repeatedSecs, startTimeHr,startTimeMin,startTimeSec,endTimeHr,endTimeMin,endTimeSec,weekStartTime.getTime(),jobEndDate,objExecuteWeeklyDetails),weekStartTime.getTime());
	                    
			        }
	            }
	        }
	        else if(repeatedHr > 0 || repeatedMin > 0 || repeatedSecs > 0)
	        {
	            GregorianCalendar weekStartTime = new GregorianCalendar();
	            weekStartTime.setTime(jobStartDate);
	            if(isFirst)
	            {
		            weekStartTime = new GregorianCalendar();
		            weekStartTime.setTime(jobStartDate);
		            System.out.println("Date:"+jobStartDate);
		            weekStartTime.set(GregorianCalendar.HOUR_OF_DAY,startTimeHr);
		            weekStartTime.set(GregorianCalendar.MINUTE,startTimeMin);
		            weekStartTime.set(GregorianCalendar.SECOND,startTimeSec);
	            }
	            boolean flg = false;
	            int startDay = weekStartTime.get(GregorianCalendar.DAY_OF_WEEK);
	            if(jobEndDate != null)
	            {
	                System.out.println(" Scheduler for weekly EndDate is not null " );
		            while(objExecuteWeeklyDetails.getRemainingExecutions() > 0 && weekStartTime.getTime().compareTo(jobEndDate) <= 0)
		            {
		                switch(weekStartTime.get(GregorianCalendar.DAY_OF_WEEK))
				        {
				            
				            case 1:
				                if(objExecuteWeeklyDetails.isSunday())
				                {
				                    flg = true;
				                }
				                break;
				            case 2:
				                if(objExecuteWeeklyDetails.isMonday())
				                {
				                    flg = true;
				                }
				                break;
				            case 3:
				                if(objExecuteWeeklyDetails.isTuesday())
				                {
				                    flg = true;
				                }
				                break;
				            case 4:
				                if(objExecuteWeeklyDetails.isWednesday())
				                {
				                    flg = true;
				                }
				                break;
				            case 5:
				                if(objExecuteWeeklyDetails.isThursday())
				                {
				                    flg = true;
				                }
				                break;
				            case 6:
				                if(objExecuteWeeklyDetails.isFriday())
				                {
				                    flg = true;
				                }
				                break;
				            case 7:
				                if(objExecuteWeeklyDetails.isSaturday())
				                {
				                    flg = true;
				                }
				                break;
				                
				                
				        }
			            if(flg)
			            {
			                Timer newTimer = new Timer();
			                GregorianCalendar repeated = new GregorianCalendar();
			                repeated.setTime(weekStartTime.getTime());
			                System.out.println("Start of Repeated Scheduling for day in a week "+ repeated.getTime());
			                GregorianCalendar repeatedEnd = new GregorianCalendar();
			                repeatedEnd.setTime(weekStartTime.getTime());
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
				            objExecuteWeeklyDetails.setRemainingExecutions(objExecuteWeeklyDetails.getRemainingExecutions() - 1);
			                flg = false;
			            }
			            weekStartTime.add(Calendar.DATE,1);
			        }
		            
			        if(objExecuteWeeklyDetails.getRemainingExecutions() == 0 && weekStartTime.getTime().compareTo(jobEndDate) <= 0)
			        {
			            weekStartTime.add(GregorianCalendar.DATE,(noOfWeeksInterval-1)*7  + 1);
			            System.out.println(" Schedule for next week "+ weekStartTime.getTime());
	                    objExecuteWeeklyDetails.setRemainingExecutions(objExecuteWeeklyDetails.getNoOfExecutions());
	                    new Timer().schedule(new ScheduleTaskWeekly(false,jobName,jobClass,noOfWeeksInterval,onceHr,onceMin,onceSecs,repeatedHr,repeatedMin,repeatedSecs, startTimeHr,startTimeMin,startTimeSec,endTimeHr,endTimeMin,endTimeSec,weekStartTime.getTime(),jobEndDate,objExecuteWeeklyDetails),weekStartTime.getTime());
	                    
			        }
	            }
	            else
	            {
	                System.out.println(" Scheduler for weekly EndDate is null " );	
		            while(objExecuteWeeklyDetails.getRemainingExecutions() > 0 )
		            {
		                switch(weekStartTime.get(GregorianCalendar.DAY_OF_WEEK))
				        {
				            
				            case 1:
				                if(objExecuteWeeklyDetails.isSunday())
				                {
				                    flg = true;
				                }
				                break;
				            case 2:
				                if(objExecuteWeeklyDetails.isMonday())
				                {
				                    flg = true;
				                }
				                break;
				            case 3:
				                if(objExecuteWeeklyDetails.isTuesday())
				                {
				                    flg = true;
				                }
				                break;
				            case 4:
				                if(objExecuteWeeklyDetails.isWednesday())
				                {
				                    flg = true;
				                }
				                break;
				            case 5:
				                if(objExecuteWeeklyDetails.isThursday())
				                {
				                    flg = true;
				                }
				                break;
				            case 6:
				                if(objExecuteWeeklyDetails.isFriday())
				                {
				                    flg = true;
				                }
				                break;
				            case 7:
				                if(objExecuteWeeklyDetails.isSaturday())
				                {
				                    flg = true;
				                }
				                break;
				                
				                
				        }
			            if(flg)
			            {
			                Timer newTimer = new Timer();
			                GregorianCalendar repeated = new GregorianCalendar();
			                repeated.setTime(weekStartTime.getTime());
			                System.out.println("Start of Repeated Scheduling for day in a week "+ repeated.getTime());
			                GregorianCalendar repeatedEnd = new GregorianCalendar();
			                repeatedEnd.setTime(weekStartTime.getTime());
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
				            objExecuteWeeklyDetails.setRemainingExecutions(objExecuteWeeklyDetails.getRemainingExecutions() - 1);
			                flg = false;
			            }
			            weekStartTime.add(Calendar.DATE,1);
			        }
		            if(objExecuteWeeklyDetails.getRemainingExecutions() == 0)
			        {
			            weekStartTime.add(GregorianCalendar.DATE,(noOfWeeksInterval-1)*7  + 1);
			            System.out.println(" Schedule for next week "+ weekStartTime.getTime());
	                    objExecuteWeeklyDetails.setRemainingExecutions(objExecuteWeeklyDetails.getNoOfExecutions());
	                    new Timer().schedule(new ScheduleTaskWeekly(false,jobName,jobClass,noOfWeeksInterval,onceHr,onceMin,onceSecs,repeatedHr,repeatedMin,repeatedSecs, startTimeHr,startTimeMin,startTimeSec,endTimeHr,endTimeMin,endTimeSec,weekStartTime.getTime(),jobEndDate,objExecuteWeeklyDetails),weekStartTime.getTime());
	                    
			        }
	            
	            }
		    }
	        System.out.println(" Outside Scheduler ");    
        }
        
	}
    
    
    public static void main(String args[])
    {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        System.out.println("Today is:"+gc.get(Calendar.DAY_OF_WEEK));
        ExecuteWeeklyDetails  objExecuteWeeklyDetails = new ExecuteWeeklyDetails();
        objExecuteWeeklyDetails.setMonday(true);
        objExecuteWeeklyDetails.setWednesday(true);
        objExecuteWeeklyDetails.setFriday(true);
        //ScheduleTaskDaily sct = new ScheduleTaskDaily();
        new Timer().schedule(new ScheduleTaskWeekly(true,"","com.datrics.scheduler.taskrunner.Sample",2,0,0,0,1,0,0,11,0,0,15,0,0,new Date("13-dec-05"),new Date("28-dec-05"),objExecuteWeeklyDetails),0);
        //ScheduleTaskWeekly(isFirst,jobName,jobClass,noOfWeeksInterval,int onceHr,int onceMin,int onceSecs,int repeatedHr,int repeatedMin,int repeatedSecs,int startTimeHr,int startTimeMin,int startTimeSec,int endTimeHr,int endTimeMin,int endTimeSec,Date jobStartDate,Date jobEndDate,WeeklyDetails objExecuteWeeklyDetails)
        //Thread.sleep(10*60*1000);
    
    }
    
}
