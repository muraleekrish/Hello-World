// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionRadlProductionDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.job.JobException;
import com.savantit.prodacs.businessimplementation.job.OperationDetails;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.RadialProductionDetails;
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

public interface SessionRadlProductionDetailsManager
    extends EJBObject {

    public abstract LinkedHashMap getShiftDefnNameList() throws WorkCalendarException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllJobs() throws JobException, SQLException, RemoteException;

    public abstract LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException, RemoteException;

    public abstract LinkedHashMap getAllEmployees() throws EmployeeException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllEmployeeTypes() throws SQLException, EmployeeException, RemoteException;

    public abstract HashMap getAllRadialProductionDetails(Filter afilter[], String s, boolean flag, int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getProdnJobDetailsByWorkOrder(int i) throws SQLException, ProductionException, RemoteException;

    public abstract Vector getProdnJobOperationDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllEmployeesByTypes() throws EmployeeException, SQLException, ProductionException, RemoteException;

    public abstract HashMap minReqdEmployees() throws SQLException, ProductionException, RemoteException;

    public abstract HashMap addNewRadialProductionDetails(RadialProductionDetails aradialproductiondetails[]) throws ProductionException, SQLException, RemoteException;

    public abstract ProductionAccountingDateDetails isPosted(String s, Date date, int i) throws ProductionException, ParseException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllMatlTypes() throws ProductionException, SQLException, RemoteException;

    public abstract Vector viewUnPostedRadlProductionDetails(Date date, Date date1) throws SQLException, ProductionException, RemoteException;

    public abstract RadialProductionDetails getRadlProductionDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllRadialMachines() throws MachineException, SQLException, RemoteException;

    public abstract boolean isRadlOperation(int i, int j, int k, int l) throws ProductionException, SQLException, RemoteException;

    public abstract boolean isModifyForRadl(int i) throws ProductionException, SQLException, RemoteException;

    public abstract boolean updateRadlProductionDetails(RadialProductionDetails radialproductiondetails) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap makeRadlProductionValid(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap makeRadlProductionInValid(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getProdnJobOperationDetailsForUpdate(int i, int j, int k) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getProdnJobDetailsForUpdateByWorkOrder(int i, int j, int k, int l, int i1, int j1) throws SQLException, ProductionException, RemoteException;

    public abstract boolean isReworkOperations(int ai[], int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllRadlMachines(Date date, int i) throws MachineException, SQLException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList(Date date) throws WorkCalendarException, SQLException, RemoteException;

    public abstract OperationDetails[] viewOperations(int i) throws SQLException, ProductionException, RemoteException;

    public abstract HashMap postRadlProductionDetails(Vector vector) throws ProductionException, SQLException, RemoteException;
}
