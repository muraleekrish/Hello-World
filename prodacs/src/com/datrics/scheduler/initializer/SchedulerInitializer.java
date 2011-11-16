/*
 * Created on Dec 10, 2005
*/
package com.datrics.scheduler.initializer;

import java.util.Timer;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServlet;

import com.datrics.scheduler.scheduletask.ScheduleTaskDaily;
import com.datrics.scheduler.valueobjects.DailyDetails;

import com.savantit.prodacs.facade.SessionSchedulerManager;
import com.savantit.prodacs.facade.SessionSchedulerManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;

import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sduraisamy
 */
public class SchedulerInitializer extends HttpServlet 
{
    public void init()
    {
        try
        {
            if(BuildConfig.DMODE)
            {
                System.out.println("Befor Calling fetching Jobs");
            }
            EJBLocator objEJBLocator = new EJBLocator();
	        /* 	Setting the JNDI name and Environment 	*/
            objEJBLocator.setJndiName("SessionSchedulerManager");
            objEJBLocator.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
            SessionSchedulerManagerHome reportsHomeObj = (SessionSchedulerManagerHome)PortableRemoteObject.narrow(objEJBLocator.getHome(),SessionSchedulerManagerHome.class);
            SessionSchedulerManager reportsObj = (SessionSchedulerManager)PortableRemoteObject.narrow(reportsHomeObj.create(),SessionSchedulerManager.class);
		    Object[] obj = reportsObj.fetch();
            for(int i=0;i<obj.length;i++)
            {
                if(obj[i] instanceof DailyDetails)
                {
	                DailyDetails objDailyDetails = (DailyDetails)obj[i];
	                new Timer().schedule(new ScheduleTaskDaily(objDailyDetails.getSchedulerId(),true,objDailyDetails.getJobClass(),objDailyDetails.getNoOfDaysInterval(),objDailyDetails.getOnceHr(),objDailyDetails.getOnceMin(),objDailyDetails.getOnceSecs(),objDailyDetails.getRepeatedHr(),objDailyDetails.getRepeatedMin(),objDailyDetails.getRepeatedSecs(),objDailyDetails.getStartTimeHr(),objDailyDetails.getStartTimeMin(),objDailyDetails.getStartTimeSec(),objDailyDetails.getEndTimeHr(),objDailyDetails.getEndTimeMin(),objDailyDetails.getEndTimeSec(),objDailyDetails.getJobStartDate(),objDailyDetails.getJobEndDate()),0);
                }
            }
            if(BuildConfig.DMODE)
            {
                System.out.println("after Calling fetching Jobs");
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    

}
