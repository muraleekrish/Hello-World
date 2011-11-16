// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionSchedulerManager.java

package com.savantit.prodacs.facade;

import com.datrics.scheduler.initializer.MailSchedulerException;
import com.datrics.scheduler.valueobjects.JobDetails;
import java.rmi.RemoteException;
import java.sql.SQLException;
import javax.ejb.EJBObject;

public interface SessionSchedulerManager
    extends EJBObject {

    public abstract Object[] fetch() throws RemoteException;

    public abstract JobDetails[] getAllScheduleDetails() throws SQLException, MailSchedulerException, RemoteException;

    public abstract boolean updateJbDetails(JobDetails jobdetails) throws RemoteException;
}
