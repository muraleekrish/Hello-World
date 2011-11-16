// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionDespatchClearanceDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.production.DespatchClearanceDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionDespatchClearanceDetailsManager
    extends EJBObject {

    public abstract boolean addNewDespatchClearanceDetails(DespatchClearanceDetails adespatchclearancedetails[]) throws ProductionException, SQLException, RemoteException;

    public abstract boolean updateDespatchClearanceDetails(DespatchClearanceDetails despatchclearancedetails) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap getAllDespatchClearanceDetails(Filter afilter[], String s, boolean flag, int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException, RemoteException;

    public abstract DespatchClearanceDetails getDespatchClearanceDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract Vector viewUnPostedDespatchDetails(Date date, Date date1) throws SQLException, ProductionException, RemoteException;

    public abstract HashMap postDespatchDetails(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getDespatchOperationDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getDespatchOperationDetailsForUpdate(int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract boolean isModifyForDespatch(int i) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap makeDespatchValid(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap makeDespatchInValid(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList(Date date) throws WorkCalendarException, SQLException, RemoteException;

    public abstract Vector getProdnJobDetailsByWorkOrder(int i) throws SQLException, ProductionException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList() throws WorkCalendarException, SQLException, RemoteException;

    public abstract Vector getProdnJobDetailsForUpdateByWorkOrder(int i, int j, int k, int l, int i1, int j1) throws SQLException, ProductionException, RemoteException;
}
