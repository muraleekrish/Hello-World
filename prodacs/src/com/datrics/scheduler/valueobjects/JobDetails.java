/*
 * Created on Jan 7, 2006
 *
 * ClassName	:  	JobDetails.java
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
 * @author kduraisamy
 *
 */
public class JobDetails implements Serializable
{
    private String jbName;
    private String jbType;
    private Date jbStartDate;
    private Date jbEndDate;
    private Date lastRun;
    private boolean lastRunStat;
    private Date nextRun;

    public Date getJbEndDate()
    {
        return jbEndDate;
    }
    public void setJbEndDate(Date jbEndDate)
    {
        this.jbEndDate = jbEndDate;
    }
    public String getJbName()
    {
        return jbName;
    }
    public void setJbName(String jbName)
    {
        this.jbName = jbName;
    }
    public Date getJbStartDate()
    {
        return jbStartDate;
    }
    public void setJbStartDate(Date jbStartDate)
    {
        this.jbStartDate = jbStartDate;
    }
    public String getJbType()
    {
        return jbType;
    }
    public void setJbType(String jbType)
    {
        this.jbType = jbType;
    }
    public Date getLastRun()
    {
        return lastRun;
    }
    public void setLastRun(Date lastRun)
    {
        this.lastRun = lastRun;
    }
    public boolean isLastRunStat()
    {
        return lastRunStat;
    }
    public void setLastRunStat(boolean lastRunStat)
    {
        this.lastRunStat = lastRunStat;
    }
    public Date getNextRun()
    {
        return nextRun;
    }
    public void setNextRun(Date nextRun)
    {
        this.nextRun = nextRun;
    }
}

/*
*$Log: JobDetails.java,v $
*Revision 1.1  2006/01/07 06:06:09  kduraisamy
*initial commit.
*
*
*/