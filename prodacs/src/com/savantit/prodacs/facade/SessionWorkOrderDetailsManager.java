// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionWorkOrderDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.job.JobDetails;
import com.savantit.prodacs.businessimplementation.job.JobException;
import com.savantit.prodacs.businessimplementation.workorder.DummyWorkOrderDetails;
import com.savantit.prodacs.businessimplementation.workorder.PreCloseDetails;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderDetails;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionWorkOrderDetailsManager
    extends EJBObject {

    public abstract int addJobGeneralName(String s) throws JobException, SQLException, RemoteException;

    public abstract boolean addJobDetails(JobDetails jobdetails) throws JobException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllJobGeneralName() throws JobException, SQLException, RemoteException;

    public abstract boolean addNewWorkOrder(WorkOrderDetails workorderdetails) throws WorkOrderException, SQLException, RemoteException;

    public abstract boolean updateWorkOrder(WorkOrderDetails workorderdetails) throws WorkOrderException, SQLException, RemoteException;

    public abstract HashMap makeWorkOrderValid(Vector vector) throws SQLException, WorkOrderException, RemoteException;

    public abstract HashMap makeWorkOrderInValid(Vector vector) throws SQLException, WorkOrderException, RemoteException;

    public abstract WorkOrderDetails getWorkOrderDetails(int i) throws WorkOrderException, SQLException, RemoteException;

    public abstract HashMap getAllWorkOrderDetails(Filter afilter[], String s, boolean flag, int i, int j) throws WorkOrderException, SQLException, RemoteException;

    public abstract boolean addNewDummyWorkOrder(DummyWorkOrderDetails dummyworkorderdetails) throws WorkOrderException, SQLException, RemoteException;

    public abstract HashMap workOrderJobCloseView(Filter afilter[], String s, boolean flag, int i, int j) throws WorkOrderException, SQLException, RemoteException;

    public abstract LinkedHashMap getCustomerNameByType(int i) throws CustomerException, SQLException, RemoteException;

    public abstract LinkedHashMap getCustomerTypes() throws CustomerException, SQLException, RemoteException;

    public abstract Vector getJobByGeneralName(int i, int j) throws JobException, SQLException, RemoteException;

    public abstract Vector getDrawingNoByJobName(int i, int j, String s) throws JobException, SQLException, RemoteException;

    public abstract Vector getRvsnNoByJobNameAndDrawingNo(int i, int j, String s, String s1) throws JobException, SQLException, RemoteException;

    public abstract Vector getMatlTypByJobNameDrawingNoAndRvsnNo(int i, int j, String s, String s1, String s2) throws JobException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllOperationGroupCodes() throws JobException, SQLException, RemoteException;

    public abstract JobDetails getJobDetails(int i) throws JobException, SQLException, RemoteException;

    public abstract PreCloseDetails getPreCloseDetails(int i) throws WorkOrderException, SQLException, RemoteException;

    public abstract HashMap getAllPreCloseDetails(Filter afilter[], String s, boolean flag, int i, int j) throws WorkOrderException, SQLException, RemoteException;

    public abstract HashMap makePreCloseLogValid(Vector vector) throws WorkOrderException, SQLException, RemoteException;

    public abstract HashMap makePreCloseLogInValid(Vector vector) throws WorkOrderException, SQLException, RemoteException;

    public abstract boolean addNewPreCloseLog(PreCloseDetails preclosedetails) throws SQLException, WorkOrderException, RemoteException;

    public abstract PreCloseDetails getForAddNewPreCloseLog(PreCloseDetails preclosedetails) throws SQLException, WorkOrderException, RemoteException;

    public abstract HashMap closeWorkOrder(Vector vector) throws SQLException, WorkOrderException, RemoteException;

    public abstract LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException, RemoteException;

    public abstract Vector getJobNameByWorkOrder(int i) throws SQLException, WorkOrderException, RemoteException;

    public abstract HashMap getAllDummyWorkOrderDetails(Filter afilter[], String s, boolean flag, int i, int j) throws WorkOrderException, SQLException, RemoteException;

    public abstract WorkOrderDetails getDummyWorkOrderDetails(int i) throws WorkOrderException, SQLException, RemoteException;

    public abstract LinkedHashMap getGeneralNameByCustomer(int i) throws JobException, SQLException, RemoteException;

    public abstract int getJobId(int i, int j, String s, String s1, String s2, String s3) throws JobException, SQLException, RemoteException;

    public abstract int addJobDetailsPartial(JobDetails jobdetails) throws JobException, SQLException, RemoteException;
}
