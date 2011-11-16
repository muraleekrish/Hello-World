/*
 * Created on Dec 8, 2005
 *
 * ClassName	:  	TaskRunner.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.datrics.scheduler.taskrunner;


import java.util.Date;

import java.util.TimerTask;

/**
 * @author sperumal
 *
 */
public class TaskRunner extends TimerTask
{

    private String jobName="";
    private String jobClass ="";
    public TaskRunner(String jobName,String jobClass)
    {
        this.jobName =jobName;
        this.jobClass =jobClass;
    }

    public void run()
    {
        try
        {
            System.out.println("  Today date is:"+ new Date());
             Class obj =  Class.forName(jobClass);
             TaskRunnerInterface interfaceObj = (TaskRunnerInterface)obj.newInstance();
             boolean isExec = interfaceObj.execute();
             System.out.println("Scheduled Task executed "+isExec);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
    }
}

/*
*$Log: TaskRunner.java,v $
*Revision 1.2  2005/12/14 12:33:17  kduraisamy
*initial commit for scheduling reports.
*
*Revision 1.2  2005/12/12 04:52:21  sduraisamy
**** empty log message ***
*
*Revision 1.1  2005/12/12 04:46:29  sduraisamy
*initial commit.
*
*Revision 1.1  2005/12/10 06:04:49  sperumal
*initial commit.
*
*Revision 1.2  2005/12/10 05:45:24  sperumal
*changes are done
*
*Revision 1.1  2005/12/09 09:08:04  sperumal
*scheduler was commited initially
*
*
*/