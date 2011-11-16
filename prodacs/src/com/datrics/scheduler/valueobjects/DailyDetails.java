/*
 * Created on Dec 10, 2005
 *
 * ClassName	:  	DailyDetails.java
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
public class DailyDetails implements Serializable
{
    private String scheduleType = "D";//If daily schedule ,set this to D.
    private String schedulerId = "";
    private String jobName = "";
    private String jobClass = "";
    private int noOfDaysInterval;
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
    
    public String getScheduleType()
    {
        return scheduleType;
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
    public int getNoOfDaysInterval()
    {
        return noOfDaysInterval;
    }
    public void setNoOfDaysInterval(int noOfDaysInterval)
    {
        this.noOfDaysInterval = noOfDaysInterval;
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
    public String getSchedulerId()
    {
        return schedulerId;
    }
    public void setSchedulerId(String schedulerId)
    {
        this.schedulerId = schedulerId;
    }
    public void setScheduleType(String scheduleType)
    {
        this.scheduleType = scheduleType;
    }
}

/*
*$Log: DailyDetails.java,v $
*Revision 1.3  2006/01/07 06:00:19  kduraisamy
*scheduler id included in value object.
*
*Revision 1.2  2005/12/14 12:33:30  kduraisamy
*initial commit for scheduling reports.
*
*Revision 1.2  2005/12/13 11:55:30  sperumal
**** empty log message ***
*
*Revision 1.1  2005/12/12 04:52:32  sduraisamy
*initial commit.
*
*Revision 1.1  2005/12/12 04:46:37  sduraisamy
*initial commit.
*
*Revision 1.1  2005/12/10 07:39:42  sperumal
*initial commit
*
*
*/