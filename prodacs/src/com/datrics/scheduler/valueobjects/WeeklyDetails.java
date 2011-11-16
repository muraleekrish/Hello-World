/*
 * Created on Dec 14, 2005
 *
 * ClassName	:  	WeeklyDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.datrics.scheduler.valueobjects;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sperumal
 *
 */
public class WeeklyDetails implements Serializable
{

    private String scheduleType = "W";//If weekly schedule ,set this to W.
    private String jobName = "";
    private String jobClass = "";
    private int noOfWeeksInterval;
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
    private Date jobStartDate;
    private Date jobEndDate;
    private boolean isMonday = false;
    private boolean isTuesday = false;;
    private boolean isWednesday = false;
    private boolean isThursday = false;
    private boolean isFriday = false;
    private boolean isSaturday = false;
    private boolean isSunday = false;
    private ExecuteWeeklyDetails objExecuteWeeklyDetails = null; 
    
    public String getScheduleType()
    {
        return scheduleType;
    }
    
    public int getNoOfWeeksInterval()
    {
        return noOfWeeksInterval;
    }
    public void setNoOfWeeksInterval(int noOfWeeksInterval)
    {
        this.noOfWeeksInterval = noOfWeeksInterval;
    }
    
    public int getEndTimeHr()
    {
        return endTimeHr;
    }
    public void setEndTimeHr(int endTimeHr)
    {
        this.endTimeHr = endTimeHr;
    }
    public int getEndTimeMin()
    {
        return endTimeMin;
    }
    public void setEndTimeMin(int endTimeMin)
    {
        this.endTimeMin = endTimeMin;
    }
    public int getEndTimeSec()
    {
        return endTimeSec;
    }
    public void setEndTimeSec(int endTimeSec)
    {
        this.endTimeSec = endTimeSec;
    }
    public String getJobClass()
    {
        return jobClass;
    }
    public void setJobClass(String jobClass)
    {
        this.jobClass = jobClass;
    }
    public Date getJobEndDate()
    {
        return jobEndDate;
    }
    public void setJobEndDate(Date jobEndDate)
    {
        this.jobEndDate = jobEndDate;
    }
    public String getJobName()
    {
        return jobName;
    }
    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }
    public Date getJobStartDate()
    {
        return jobStartDate;
    }
    public void setJobStartDate(Date jobStartDate)
    {
        this.jobStartDate = jobStartDate;
    }
    
    public int getOnceHr()
    {
        return onceHr;
    }
    public void setOnceHr(int onceHr)
    {
        this.onceHr = onceHr;
    }
    public int getOnceMin()
    {
        return onceMin;
    }
    public void setOnceMin(int onceMin)
    {
        this.onceMin = onceMin;
    }
    public int getOnceSecs()
    {
        return onceSecs;
    }
    public void setOnceSecs(int onceSecs)
    {
        this.onceSecs = onceSecs;
    }
    public int getRepeatedHr()
    {
        return repeatedHr;
    }
    public void setRepeatedHr(int repeatedHr)
    {
        this.repeatedHr = repeatedHr;
    }
    public int getRepeatedMin()
    {
        return repeatedMin;
    }
    public void setRepeatedMin(int repeatedMin)
    {
        this.repeatedMin = repeatedMin;
    }
    public int getRepeatedSecs()
    {
        return repeatedSecs;
    }
    public void setRepeatedSecs(int repeatedSecs)
    {
        this.repeatedSecs = repeatedSecs;
    }
    public int getStartTimeHr()
    {
        return startTimeHr;
    }
    public void setStartTimeHr(int startTimeHr)
    {
        this.startTimeHr = startTimeHr;
    }
    public int getStartTimeMin()
    {
        return startTimeMin;
    }
    public void setStartTimeMin(int startTimeMin)
    {
        this.startTimeMin = startTimeMin;
    }
    public int getStartTimeSec()
    {
        return startTimeSec;
    }
    public void setStartTimeSec(int startTimeSec)
    {
        this.startTimeSec = startTimeSec;
    }

    public void setScheduleType(String scheduleType)
    {
        this.scheduleType = scheduleType;
    }
    public ExecuteWeeklyDetails getObjExecuteWeeklyDetails()
    {
        return objExecuteWeeklyDetails;
    }
    public void setObjExecuteWeeklyDetails(
            ExecuteWeeklyDetails objExecuteWeeklyDetails)
    {
        this.objExecuteWeeklyDetails = objExecuteWeeklyDetails;
    }
    public boolean isFriday()
    {
        return isFriday;
    }
    public void setFriday(boolean isFriday)
    {
        this.isFriday = isFriday;
    }
    public boolean isMonday()
    {
        return isMonday;
    }
    public void setMonday(boolean isMonday)
    {
        this.isMonday = isMonday;
    }
    public boolean isSaturday()
    {
        return isSaturday;
    }
    public void setSaturday(boolean isSaturday)
    {
        this.isSaturday = isSaturday;
    }
    public boolean isSunday()
    {
        return isSunday;
    }
    public void setSunday(boolean isSunday)
    {
        this.isSunday = isSunday;
    }
    public boolean isThursday()
    {
        return isThursday;
    }
    public void setThursday(boolean isThursday)
    {
        this.isThursday = isThursday;
    }
    public boolean isTuesday()
    {
        return isTuesday;
    }
    public void setTuesday(boolean isTuesday)
    {
        this.isTuesday = isTuesday;
    }
    public boolean isWednesday()
    {
        return isWednesday;
    }
    public void setWednesday(boolean isWednesday)
    {
        this.isWednesday = isWednesday;
    }
}

/*
*$Log: WeeklyDetails.java,v $
*Revision 1.2  2005/12/14 12:33:30  kduraisamy
*initial commit for scheduling reports.
*
*Revision 1.3  2005/12/14 12:21:23  sperumal
*initial commit
*
*
*/