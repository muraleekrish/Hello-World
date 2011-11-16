// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionJobDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.job.JobDetails;
import com.savantit.prodacs.businessimplementation.job.JobException;
import com.savantit.prodacs.businessimplementation.job.OperationGroupDetails;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionJobDetailsManager
    extends EJBObject {

    public abstract int addJobGeneralName(String s) throws JobException, SQLException, RemoteException;

    public abstract boolean addJobDetails(JobDetails jobdetails) throws JobException, SQLException, RemoteException;

    public abstract boolean updateJobDetails(JobDetails jobdetails) throws JobException, SQLException, RemoteException;

    public abstract HashMap deleteJobDetails(Vector vector) throws JobException, SQLException, RemoteException;

    public abstract JobDetails getJobDetails(int i) throws JobException, SQLException, RemoteException;

    public abstract HashMap getAllJobDetails(Filter afilter[], String s, boolean flag, int i, int j) throws SQLException, JobException, RemoteException;

    public abstract HashMap makeJobValid(Vector vector) throws JobException, SQLException, RemoteException;

    public abstract HashMap makeJobInValid(Vector vector) throws JobException, SQLException, RemoteException;

    public abstract boolean addOperationGroupDetails(OperationGroupDetails operationgroupdetails) throws JobException, SQLException, RemoteException;

    public abstract boolean updateOperationGroupDetails(OperationGroupDetails operationgroupdetails) throws JobException, SQLException, RemoteException;

    public abstract HashMap deleteOperationGroupDetails(Vector vector) throws JobException, SQLException, RemoteException;

    public abstract OperationGroupDetails getOperationGroupDetails(int i) throws JobException, SQLException, RemoteException;

    public abstract HashMap getAllOperationGroupDetails(Filter afilter[], String s, boolean flag, int i, int j) throws JobException, SQLException, RemoteException;

    public abstract HashMap makeOperationGroupValid(Vector vector) throws JobException, SQLException, RemoteException;

    public abstract HashMap makeOperationGroupInValid(Vector vector) throws JobException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllCustomers() throws CustomerException, SQLException, RemoteException;

    public abstract LinkedHashMap getCustomerTypes() throws CustomerException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllJobGeneralName() throws JobException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllJobs() throws JobException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllOperationGroupCodes() throws JobException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllMachines() throws MachineException, SQLException, RemoteException;

    public abstract LinkedHashMap getCustomerNameByType(int i) throws CustomerException, SQLException, RemoteException;

    public abstract LinkedHashMap getGeneralNameByCustomer(int i) throws JobException, SQLException, RemoteException;

    public abstract Vector getJobDetailsByGeneralNameWithCustomer(int i, int j) throws JobException, SQLException, RemoteException;
}
