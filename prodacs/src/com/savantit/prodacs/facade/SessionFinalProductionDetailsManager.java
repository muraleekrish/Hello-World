// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionFinalProductionDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.job.OperationDetails;
import com.savantit.prodacs.businessimplementation.production.FinalProductionDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionFinalProductionDetailsManager
    extends EJBObject {

    public abstract boolean addNewFinalProductionDetails(FinalProductionDetails afinalproductiondetails[]) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap getAllFinalProductionDetails(Filter afilter[], String s, boolean flag, int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract boolean updateFinalProductionDetails(FinalProductionDetails finalproductiondetails) throws ProductionException, SQLException, RemoteException;

    public abstract FinalProductionDetails getFinalProductionDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException, RemoteException;

    public abstract Vector viewUnPostedFinalProductionDetails(Date date, Date date1) throws SQLException, ProductionException, RemoteException;

    public abstract HashMap postFinalProductionDetails(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getFinalProdnJobOperationDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getProdnJobDetailsByWorkOrder(int i) throws SQLException, ProductionException, RemoteException;

    public abstract Vector getProdnJobDetailsForUpdateByWorkOrder(int i, int j, int k, int l, int i1, int j1) throws SQLException, ProductionException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList(Date date) throws WorkCalendarException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllEmployeesByTypes() throws EmployeeException, SQLException, ProductionException, RemoteException;

    public abstract OperationDetails[] viewOperations(int i) throws SQLException, ProductionException, RemoteException;

    public abstract ProductionAccountingDateDetails isPosted(String s, Date date, int i) throws ProductionException, ParseException, SQLException, RemoteException;

    public abstract HashMap getAllWorkOrderJobStatus(Filter afilter[], String s, boolean flag, int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract boolean isModifyForFprod(int i) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getFinalProdnJobOperationDetailsForUpdate(int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList() throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap makeFinalProductionValid(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap makeFinalProductionInValid(Vector vector) throws ProductionException, SQLException, RemoteException;
}
