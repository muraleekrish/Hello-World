
/*
 * Created on Dec 8, 2005
 *
 * ClassName	:  	ScheduleTaskDaily.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.datrics.scheduler.scheduletask;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.CreateException;
import javax.rmi.PortableRemoteObject;

import com.datrics.scheduler.taskrunner.TaskRunner;
import com.datrics.scheduler.valueobjects.JobDetails;
import com.savantit.prodacs.facade.SessionSchedulerManager;
import com.savantit.prodacs.facade.SessionSchedulerManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;


/**
 * @author sperumal
 *
 */
public class ScheduleTaskDaily  extends TimerTask
{
    String jobName;
    String jobClass;
    String schdlrId;
    int noOfDaysInterval;
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
    Date jobDate;
    Date jobEndDate;
    boolean isFirst;
    public ScheduleTaskDaily(String schdlrId, boolean isFirst,String jobClass,int noOfDaysInterval,int onceHr,int onceMin,int onceSecs,int repeatedHr,int repeatedMin,int repeatedSecs,int startTimeHr,int startTimeMin, int startTimeSec, int endTimeHr, int endTimeMin,int endTimeSec, Date jobStartDate, Date jobEndDate) throws InterruptedException
    {
        this.isFirst = isFirst;
        this.noOfDaysInterval = noOfDaysInterval;
        this.jobClass = jobClass;
        this.schdlrId = schdlrId;
        this.noOfDaysInterval = noOfDaysInterval;
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
        this.jobEndDate = jobEndDate;
        this.jobDate = jobStartDate;
    }
    public void run()
    {
        try
        {
            if(BuildConfig.DMODE)
                System.out.println("Inside run started ");
            if(jobDate != null)
            {
                EJBLocator objEJBLocator = new EJBLocator();
                /* 	Setting the JNDI name and Environment 	*/
                objEJBLocator.setJndiName("SessionSchedulerManager");
                objEJBLocator.setEnvironment();
                
                /* 	Creating the Home and Remote Objects 	*/
                SessionSchedulerManagerHome reportsHomeObj = (SessionSchedulerManagerHome)PortableRemoteObject.narrow(objEJBLocator.getHome(),SessionSchedulerManagerHome.class);
                SessionSchedulerManager reportsObj = null;
                try
                 {
                 reportsObj = (SessionSchedulerManager)PortableRemoteObject.narrow(reportsHomeObj.create(),SessionSchedulerManager.class);
                 }
                 catch (ClassCastException e1)
                 {
                 // TODO Auto-generated catch block
                  e1.printStackTrace();
                  }
                  catch (RemoteException e1)
                  {
                  // TODO Auto-generated catch block
                   e1.printStackTrace();
                   }
                   catch (CreateException e1)
                   {
                   // TODO Auto-generated catch block
                    e1.printStackTrace();
                    }
                if(onceHr > 0 || onceMin > 0 || onceSecs > 0)
                {
                    if(BuildConfig.DMODE)
                    {
                        System.out.println("Once Per Day Scheduled");
                        System.out.println("Job Date Is :"+jobDate);
                    }
                    GregorianCalendar gcScheduleTime = new GregorianCalendar();
                    gcScheduleTime.setTime(jobDate);
                    
                    if(isFirst)
                    {
                        gcScheduleTime.set(GregorianCalendar.HOUR_OF_DAY,onceHr);
                        gcScheduleTime.set(GregorianCalendar.MINUTE,onceMin);
                        gcScheduleTime.set(GregorianCalendar.SECOND,onceSecs);
                    }
                    
                    if(BuildConfig.DMODE)
                    {
                        System.out.println("Job Scheduled Time :"+gcScheduleTime.getTime());
                    }
                    if(jobEndDate != null)
                    {
                        if(BuildConfig.DMODE)
                        {
                            System.out.println(" Job End Date is Given as "+jobEndDate);
                            System.out.println(" Current Date Is :"+new Date());
                        }
                        boolean lastRun = false;
                        Date lastRunTime = gcScheduleTime.getTime();
                        
                        GregorianCalendar gcJobDate = new GregorianCalendar();
                        gcJobDate.setTime(jobDate);
                        gcJobDate.set(Calendar.HOUR_OF_DAY,0);
                        gcJobDate.set(Calendar.MINUTE,0);
                        gcJobDate.set(Calendar.SECOND,0);
                        
                        jobDate = gcJobDate.getTime();
                        
                        if(jobDate.compareTo(jobEndDate) <= 0 && (new Date().getTime()/1000 - gcScheduleTime.getTime().getTime()/1000)<=60)
                        {
                            if(BuildConfig.DMODE)
                            {
                                System.out.println(" Job Date is With in Job End Date and Schedule time is in future");
                            }
                            Timer timer = new Timer();
                            timer.schedule(new TaskRunner(jobName,jobClass),gcScheduleTime.getTime());
                            lastRun = true;
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Job Scheduled for "+gcScheduleTime.getTime() +"---"+lastRun);
                            }
                        }
                        else
                        {
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Scheduled time "+gcScheduleTime.getTime() +" is not with in job End Date or its a past time");
                            }
                        }
                        gcJobDate.add(Calendar.DATE,noOfDaysInterval);
                        Date nextJobDate = gcJobDate.getTime();
                        gcScheduleTime.add(Calendar.DATE, noOfDaysInterval);
                        
                        if(nextJobDate.compareTo(jobEndDate) <= 0)
                        {
                            
                            JobDetails objJobDetails = new JobDetails();
                            objJobDetails.setLastRun(lastRunTime);
                            objJobDetails.setLastRunStat(lastRun);
                            objJobDetails.setNextRun(gcScheduleTime.getTime());
                            
                            boolean resultUpdate;
                            resultUpdate = reportsObj.updateJbDetails(objJobDetails);
                            if(BuildConfig.DMODE)
                            {
                                if(resultUpdate)
                                {
                                    System.out.println("The job details updated");
                                    
                                }
                                else
                                {
                                    System.out.println("The job details not updated");
                                    
                                }
                            }
                            
                            
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("The next Scheduled Time is "+gcScheduleTime.getTime());
                                System.out.println("The next job Date is "+nextJobDate);
                            }
                           
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Job Scheduling Control Waiting for ..."+gcScheduleTime.getTime());
                            }
                           
                            new Timer().schedule(new ScheduleTaskDaily(schdlrId, false , jobClass,noOfDaysInterval,onceHr,onceMin,onceSecs,repeatedHr,repeatedMin,repeatedSecs,startTimeHr,startTimeMin,startTimeSec, endTimeHr, endTimeMin,endTimeSec, gcScheduleTime.getTime(), jobEndDate), gcScheduleTime.getTime());
                            
                        }
                        else
                        {
                            if(BuildConfig.DMODE)
                                System.out.println("The next Scheduled Time Cross the End Date");
                        }
                        
                    }
                    else
                    {
                        if(BuildConfig.DMODE)
                        {
                            System.out.println(" Job End Date is not Given");
                            System.out.println(" Current Date Is :"+new Date());
                        }
                        boolean lastRun = false;
                        Date lastRunTime = gcScheduleTime.getTime();
                        if((new Date().getTime()/1000 - gcScheduleTime.getTime().getTime()/1000)<=60)
                        {
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Schedule time is in future");
                            }
                            
                            Timer timer = new Timer();
                            timer.schedule(new TaskRunner(jobName,jobClass),gcScheduleTime.getTime());
                            lastRun = true;
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Job Scheduled for "+gcScheduleTime.getTime() +"---"+lastRun);
                            }
                            
                        }
                        else
                        {
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Scheduled time "+gcScheduleTime.getTime() +" is a past time");
                            }
                        }
                        gcScheduleTime.add(Calendar.DATE, noOfDaysInterval);
                        
                        
                        
                        JobDetails objJobDetails = new JobDetails();
                        objJobDetails.setLastRun(lastRunTime);
                        objJobDetails.setLastRunStat(lastRun);
                        objJobDetails.setNextRun(gcScheduleTime.getTime());
                        
                        boolean resultUpdate;
                        resultUpdate = reportsObj.updateJbDetails(objJobDetails);
                        if(BuildConfig.DMODE)
                        {
                            if(resultUpdate)
                            {
                                System.out.println("The job details updated");
                                
                            }
                            else
                            {
                                System.out.println("The job details not updated");
                                
                            }
                        }
                        
                        if(BuildConfig.DMODE)
                        {
                            System.out.println("The next Scheduled Time is "+gcScheduleTime.getTime());
                        }
                        if(BuildConfig.DMODE)
                        {
                            System.out.println("Job scheduling Control Waiting for ..."+gcScheduleTime.getTime());
                        }
                       
                        new Timer().schedule(new ScheduleTaskDaily(schdlrId, false ,jobClass,noOfDaysInterval,onceHr,onceMin,onceSecs,repeatedHr,repeatedMin,repeatedSecs,startTimeHr,startTimeMin,startTimeSec, endTimeHr, endTimeMin,endTimeSec, gcScheduleTime.getTime(), jobEndDate),gcScheduleTime.getTime());
                    } 
                    
                }
                else if(repeatedHr > 0 || repeatedMin > 0 || repeatedSecs > 0)
                {
                    //case for dailyschedule repeatedly.
                    if(BuildConfig.DMODE)
                    {
                        System.out.println("More than one per day scheduled");
                        System.out.println("Job Date Is :"+jobDate);
                    }
                    
                    GregorianCalendar gcScheduleTime = new GregorianCalendar();
                    gcScheduleTime.setTime(jobDate);
                    if(isFirst)
                    {
                        gcScheduleTime.set(GregorianCalendar.HOUR_OF_DAY,startTimeHr);
                        gcScheduleTime.set(GregorianCalendar.MINUTE,startTimeMin);
                        gcScheduleTime.set(GregorianCalendar.SECOND,startTimeSec);
                    }
                    
                    if(BuildConfig.DMODE)
                    {
                        System.out.println("Job Scheduled Time :"+gcScheduleTime.getTime());
                    }
                    if(jobEndDate != null)
                    {
                        
                        if(BuildConfig.DMODE)
                        {
                            System.out.println(" Job End Date is Given as "+jobEndDate);
                            System.out.println(" Current Date Is :"+new Date());
                        }
                        
                        boolean lastRun = false;
                        Date lastRunTime = gcScheduleTime.getTime();
                        
                        GregorianCalendar gcJobDate = new GregorianCalendar();
                        gcJobDate.setTime(jobDate);
                        gcJobDate.set(Calendar.HOUR_OF_DAY,0);
                        gcJobDate.set(Calendar.MINUTE,0);
                        gcJobDate.set(Calendar.SECOND,0);
                        jobDate = gcJobDate.getTime();
                        
                        if(jobDate.compareTo(jobEndDate) <= 0 && (new Date().getTime()/1000 - gcScheduleTime.getTime().getTime()/1000)<=60)
                        {
                            if(BuildConfig.DMODE)
                            {
                                System.out.println(" Job Date is With in Job End Date and Schedule time is in future");
                            }
                            
                            Timer timer = new Timer();
                            
                            timer.schedule(new TaskRunner(jobName,jobClass),gcScheduleTime.getTime());
                            lastRun = true;
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Job Scheduled for "+gcScheduleTime.getTime());
                            }
                        }  
                        else
                        {
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Scheduled time "+gcScheduleTime.getTime() +" is not with in job End Date or its a past time");
                            }
                        }
                        
                        
                        //to find the next schedule time.
                        
                        GregorianCalendar gcNextSchedule = new GregorianCalendar();
                        gcNextSchedule.setTime(gcScheduleTime.getTime());
                        gcNextSchedule.add(Calendar.HOUR_OF_DAY,repeatedHr);
                        gcNextSchedule.add(Calendar.MINUTE,repeatedMin);
                        gcNextSchedule.add(Calendar.SECOND,repeatedSecs);
                        
                        
                        
                        GregorianCalendar gcRepeatedEnd = new GregorianCalendar();
                        gcRepeatedEnd.setTime(gcScheduleTime.getTime());
                        gcRepeatedEnd.set(Calendar.HOUR_OF_DAY,endTimeHr);
                        gcRepeatedEnd.set(Calendar.MINUTE,endTimeMin);
                        gcRepeatedEnd.set(Calendar.SECOND,endTimeSec);
                        
                        if(BuildConfig.DMODE)
                        {
                            System.out.println("The  Scheduled End Time is "+gcRepeatedEnd.getTime());
                            
                        }
                        
                        if(gcNextSchedule.getTime().compareTo(gcRepeatedEnd.getTime())<=0)
                        {
                            JobDetails objJobDetails = new JobDetails();
                            objJobDetails.setLastRun(lastRunTime);
                            objJobDetails.setLastRunStat(lastRun);
                            objJobDetails.setNextRun(gcNextSchedule.getTime());
                            
                            boolean resultUpdate;
                            resultUpdate = reportsObj.updateJbDetails(objJobDetails);
                            if(BuildConfig.DMODE)
                            {
                                if(resultUpdate)
                                {
                                    System.out.println("The job details updated");
                                    
                                }
                                else
                                {
                                    System.out.println("The job details not updated");
                                    
                                }
                            }
                            
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Job scheduling Control Waiting for ..."+gcNextSchedule.getTime());
                            }
                           
                            new Timer().schedule(new ScheduleTaskDaily(schdlrId, false, jobClass,noOfDaysInterval,onceHr,onceMin,onceSecs,repeatedHr,repeatedMin,repeatedSecs,startTimeHr,startTimeMin,startTimeSec, endTimeHr, endTimeMin,endTimeSec, gcNextSchedule.getTime(),jobEndDate),gcNextSchedule.getTime());
                        }
                        else
                        {
                            
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("End of First Day");
                                System.out.println("Next Scheduled time is not with in the boundry(that days end time)");
                            }
                            gcJobDate.add(Calendar.DATE,noOfDaysInterval);
                            Date nextJobDate = gcJobDate.getTime();
                            
                            gcScheduleTime.add(Calendar.DATE, noOfDaysInterval);
                            gcScheduleTime.set(GregorianCalendar.HOUR_OF_DAY,startTimeHr);
                            gcScheduleTime.set(GregorianCalendar.MINUTE,startTimeMin);
                            gcScheduleTime.set(GregorianCalendar.SECOND,startTimeSec);
                            
                            if(nextJobDate.compareTo(jobEndDate) <= 0)
                            {
                                
                                
                                JobDetails objJobDetails = new JobDetails();
                                objJobDetails.setLastRun(lastRunTime);
                                objJobDetails.setLastRunStat(lastRun);
                                objJobDetails.setNextRun(gcScheduleTime.getTime());
                                
                                boolean resultUpdate;
                                resultUpdate = reportsObj.updateJbDetails(objJobDetails);
                                if(BuildConfig.DMODE)
                                {
                                    if(resultUpdate)
                                    {
                                        System.out.println("The job details updated");
                                        
                                    }
                                    else
                                    {
                                        System.out.println("The job details not updated");
                                        
                                    }
                                }
                                if(BuildConfig.DMODE)
                                {
                                    System.out.println("The next Scheduled Time is "+gcScheduleTime.getTime());
                                    System.out.println("The next job Date is "+nextJobDate);
                                }
                                
                                if(BuildConfig.DMODE)
                                {
                                    System.out.println("Job scheduling Control Waiting for ..."+gcScheduleTime.getTime());
                                }
                               
                                new Timer().schedule(new ScheduleTaskDaily(schdlrId, false , jobClass,noOfDaysInterval,onceHr,onceMin,onceSecs,repeatedHr,repeatedMin,repeatedSecs,startTimeHr,startTimeMin,startTimeSec, endTimeHr, endTimeMin,endTimeSec, gcScheduleTime.getTime(),jobEndDate),gcScheduleTime.getTime());
                            }
                            else
                            {
                                if(BuildConfig.DMODE)
                                    System.out.println("The next Scheduled Time Cross the End Date");
                            }
                            
                        }
                        
                        
                        
                    }
                    else
                    {
                        
                        if(BuildConfig.DMODE)
                        {
                            System.out.println(" Job End Date is not Given");
                            System.out.println(" Current Date Is :"+new Date());
                        }
                        
                        boolean lastRun = false;
                        Date lastRunTime = gcScheduleTime.getTime();
                        
                        if((new Date().getTime()/1000 - gcScheduleTime.getTime().getTime()/1000)<=60)
                        {
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Schedule time is in future");
                            }
                            
                            Timer timer = new Timer();
                            
                           
                            timer.schedule(new TaskRunner(jobName,jobClass),gcScheduleTime.getTime());
                            lastRun = true;
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Job Scheduled for "+gcScheduleTime.getTime());
                            }
                        } 
                        else
                        {
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Scheduled time "+gcScheduleTime.getTime() +" is a past time");
                            }
                            
                        }
                        //to find the next schedule time.
                        
                        GregorianCalendar gcNextSchedule = new GregorianCalendar();
                        gcNextSchedule.setTime(gcScheduleTime.getTime());
                        gcNextSchedule.add(Calendar.HOUR_OF_DAY,repeatedHr);
                        gcNextSchedule.add(Calendar.MINUTE,repeatedMin);
                        gcNextSchedule.add(Calendar.SECOND,repeatedSecs);
                        
                        GregorianCalendar gcRepeatedEnd = new GregorianCalendar();
                        gcRepeatedEnd.setTime(gcScheduleTime.getTime());
                        gcRepeatedEnd.set(Calendar.HOUR_OF_DAY,endTimeHr);
                        gcRepeatedEnd.set(Calendar.MINUTE,endTimeMin);
                        gcRepeatedEnd.set(Calendar.SECOND,endTimeSec);
                        
                        if(gcNextSchedule.getTime().compareTo(gcRepeatedEnd.getTime())<=0)
                        {
                            JobDetails objJobDetails = new JobDetails();
                            objJobDetails.setLastRun(lastRunTime);
                            objJobDetails.setLastRunStat(lastRun);
                            objJobDetails.setNextRun(gcNextSchedule.getTime());
                            
                            boolean resultUpdate;
                            resultUpdate = reportsObj.updateJbDetails(objJobDetails);
                            if(BuildConfig.DMODE)
                            {
                                if(resultUpdate)
                                {
                                    System.out.println("The job details updated");
                                    
                                }
                                else
                                {
                                    System.out.println("The job details not updated");
                                    
                                }
                            }
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Job scheduling Control Waiting for ..."+gcNextSchedule.getTime());
                            }
                            new Timer().schedule(new ScheduleTaskDaily(schdlrId, false, jobClass,noOfDaysInterval,onceHr,onceMin,onceSecs,repeatedHr,repeatedMin,repeatedSecs,startTimeHr,startTimeMin,startTimeSec, endTimeHr, endTimeMin,endTimeSec, gcNextSchedule.getTime(),jobEndDate),gcNextSchedule.getTime());
                        }
                        else
                        {
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("End of First Day");
                                System.out.println("Next Scheduled time is not with in the boundry(that days end time)");
                            }
                            
                            gcScheduleTime.add(Calendar.DATE, noOfDaysInterval);
                            gcScheduleTime.set(GregorianCalendar.HOUR_OF_DAY,startTimeHr);
                            gcScheduleTime.set(GregorianCalendar.MINUTE,startTimeMin);
                            gcScheduleTime.set(GregorianCalendar.SECOND,startTimeSec);
                            
                            
                            
                            JobDetails objJobDetails = new JobDetails();
                            objJobDetails.setLastRun(lastRunTime);
                            objJobDetails.setLastRunStat(lastRun);
                            objJobDetails.setNextRun(gcScheduleTime.getTime());
                            
                            boolean resultUpdate;
                            resultUpdate = reportsObj.updateJbDetails(objJobDetails);
                            if(BuildConfig.DMODE)
                            {
                                if(resultUpdate)
                                {
                                    System.out.println("The job details updated");
                                    
                                }
                                else
                                {
                                    System.out.println("The job details not updated");
                                    
                                }
                            }
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("The next Scheduled Time is "+gcScheduleTime.getTime());
                            }
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Job scheduling Control Waiting for ..."+gcScheduleTime.getTime());
                            }
                            
                            new Timer().schedule(new ScheduleTaskDaily(schdlrId, false , jobClass,noOfDaysInterval,onceHr,onceMin,onceSecs,repeatedHr,repeatedMin,repeatedSecs,startTimeHr,startTimeMin,startTimeSec, endTimeHr, endTimeMin,endTimeSec, gcScheduleTime.getTime(),jobEndDate),gcScheduleTime.getTime());
                            
                        }
                        
                    }
                    
                }
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(" ----- Scheduler Ends ------ ");
        
    }
    //main method start.
    public static void main(String[] args) throws InterruptedException
    {
        
        /*GregorianCalendar gc = new GregorianCalendar();
        System.out.println("The current time is:"+gc.get(Calendar.MILLISECOND));
        Date date = new Date();
        System.out.println("long:"+date.getTime());
        Thread.sleep(100);
        Date date1 = new Date();
        System.out.println("diff is:"+(date1.getTime() - date.getTime()));*/
        //Date date = new Date();
        //System.out.println("CurrentDate is:"+date.getTime());
        //Date date1 = new Date();
        //System.out.println("CurrentDate next is:"+date1.getTime());
        //System.out.println("diff is:"+(date1.getTime()/1000 - date.getTime()/1000));
        
        new Timer().schedule(new ScheduleTaskDaily("100",true,"com.savantit.prodacs.businessimplementation.reportmails.MailingReportsDetailsManager",1,0,0,0,0,0,30,11,52,0,11,53,0,new Date("08-jan-2006"),new Date("11-jan-2006")),0);
        
    }
}


/*
*$Log: ScheduleTaskDaily.java,v $
*Revision 1.3  2006/01/10 07:17:39  kduraisamy
*SCHEDULER MODIFIED.
*
*Revision 1.2  2005/12/14 12:33:17  kduraisamy
*initial commit for scheduling reports.
*
*Revision 1.3  2005/12/14 12:21:09  sperumal
*schedule for all are updated
*
*Revision 1.2  2005/12/12 04:51:58  sduraisamy
*initial commit.
*
*Revision 1.1  2005/12/12 04:46:21  sduraisamy
*initial commit.
*
*Revision 1.1  2005/12/10 06:04:49  sperumal
*initial commit.
*
*Revision 1.3  2005/12/10 05:45:24  sperumal
*changes are done
*
*Revision 1.2  2005/12/10 04:11:25  sperumal
**** empty log message ***
*
*Revision 1.1  2005/12/09 09:08:04  sperumal
*scheduler was commited initially
*
*
*/