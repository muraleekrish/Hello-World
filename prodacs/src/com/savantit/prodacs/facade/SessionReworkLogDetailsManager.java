// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionReworkLogDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.job.ReworkException;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.reworklog.ReworkLogDetails;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionReworkLogDetailsManager
    extends EJBObject {

    public abstract HashMap getAllReworkLogDetails(Filter afilter[], String s, boolean flag, int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract boolean addReworkLogDetails(ReworkLogDetails reworklogdetails) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getRwkJobOperationDetails(int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getJobNameByWorkOrder(int i) throws SQLException, WorkOrderException, RemoteException;

    public abstract LinkedHashMap getAllReworkReasonsByCategory(int i) throws ReworkException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllReworkCategories() throws ReworkException, SQLException, RemoteException;

    public abstract LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException, RemoteException;

    public abstract ReworkLogDetails getReworkLogDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap makeReworkLogValid(Vector vector) throws SQLException, ProductionException, RemoteException;

    public abstract HashMap makeReworkLogInValid(Vector vector) throws SQLException, ProductionException, RemoteException;
}
