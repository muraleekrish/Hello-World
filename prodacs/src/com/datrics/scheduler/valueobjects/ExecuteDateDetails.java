/*
 * Created on Dec 13, 2005
 *
 * ClassName	:  	ExecuteDateDetails.java
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

/**
 * @author sperumal
 *
 */
public class ExecuteDateDetails implements Serializable
{
    private int executeDate;
    private int executeDay;
    private  String occurDayOffset;
    
    private int dayOffset; 
    public int getExecuteDate()
    {
        return executeDate;
    }
    public void setExecuteDate(int executeDate)
    {
        this.executeDate = executeDate;
    }
    public int getExecuteDay()
    {
        return executeDay;
    }
    public void setExecuteDay(int executeDay)
    {
        this.executeDay = executeDay;
    }
    
    
    public String getOccurDayOffset()
    {
        return occurDayOffset;
    }
    public void setOccurDayOffset(String occurDayOffset)
    {
        this.occurDayOffset = occurDayOffset;
        if(occurDayOffset.equalsIgnoreCase("first"))
            dayOffset = 1;
        else if(occurDayOffset.equalsIgnoreCase("second"))
            dayOffset = 2;
        else if(occurDayOffset.equalsIgnoreCase("third"))
            dayOffset = 3;
        else if(occurDayOffset.equalsIgnoreCase("fourth"))
            dayOffset = 4;
        else if(occurDayOffset.equalsIgnoreCase("last"))
            dayOffset = 5;
    }
    public int getDayOffset()
    {
        return dayOffset;
    }
}

/*
*$Log: ExecuteDateDetails.java,v $
*Revision 1.1  2005/12/14 12:33:30  kduraisamy
*initial commit for scheduling reports.
*
*Revision 1.2  2005/12/14 12:21:23  sperumal
*initial commit
*
*Revision 1.1  2005/12/13 11:55:24  sperumal
*initial commit
*
*
*/