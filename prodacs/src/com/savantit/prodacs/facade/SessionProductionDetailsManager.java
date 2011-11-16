// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionProductionDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.job.JobException;
import com.savantit.prodacs.businessimplementation.job.OperationDetails;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.businessimplementation.production.MachineNonAccountedDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.reports.ReportsException;
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

public interface SessionProductionDetailsManager
    extends EJBObject {

    public abstract HashMap addNewProductionDetails(ProductionDetails aproductiondetails[]) throws ProductionException, SQLException, RemoteException;

    public abstract Vector viewUnPostedProductionDetails(Date date, Date date1) throws SQLException, ProductionException, RemoteException;

    public abstract Vector getProdnJobDetailsByWorkOrder(int i) throws SQLException, ProductionException, RemoteException;

    public abstract Vector getProdnJobOperationDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract ProductionDetails getProductionDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap getAllProductionDetails(Filter afilter[], String s, boolean flag, int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllMachines() throws MachineException, SQLException, RemoteException;

    public abstract LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException, RemoteException;

    public abstract LinkedHashMap getAllJobs() throws JobException, SQLException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList() throws WorkCalendarException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllEmployees() throws EmployeeException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllEmployeeTypes() throws SQLException, EmployeeException, RemoteException;

    public abstract LinkedHashMap getAllEmployeesByTypes() throws EmployeeException, SQLException, ProductionException, RemoteException;

    public abstract float getStandardHrs(int i, int j, int k) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap minReqdEmployees() throws SQLException, ProductionException, RemoteException;

    public abstract ProductionAccountingDateDetails isPosted(String s, Date date, int i) throws ProductionException, ParseException, SQLException, RemoteException;

    public abstract Vector getProdnJobDetailsForUpdateByWorkOrder(int i, int j, int k, int l, int i1, int j1) throws SQLException, ProductionException, RemoteException;

    public abstract Vector getProdnJobOperationDetailsForUpdate(int i, int j, int k) throws ProductionException, SQLException, RemoteException;

    public abstract boolean isModify(int i) throws ProductionException, SQLException, RemoteException;

    public abstract boolean updateProductionDetails(ProductionDetails productiondetails) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap makeProductionValid(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap makeProductionInValid(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract boolean isRadlOperation(int i, int j, int k, int l) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllProdMachines() throws MachineException, SQLException, RemoteException;

    public abstract boolean isReworkOperations(int ai[], int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllProdMachines(Date date, int i) throws MachineException, SQLException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList(Date date) throws WorkCalendarException, SQLException, RemoteException;

    public abstract LinkedHashMap currentPostingRule() throws ProductionException, SQLException, RemoteException;

    public abstract boolean changePostingRule(int i) throws ProductionException, SQLException, RemoteException;

    public abstract OperationDetails[] viewOperations(int i) throws SQLException, ProductionException, RemoteException;

    public abstract HashMap postProductionDetails(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract MachineNonAccountedDetails[] checkMachineAccounting(Date date, Date date1, String s) throws ProductionException, SQLException, RemoteException;

    public abstract double fetchProductionEnteredHrs(Date date, Date date1, String s) throws ProductionException, SQLException, RemoteException;

    public abstract double fetchNonProductionEnteredHrs(Date date, Date date1, String s) throws ProductionException, SQLException, RemoteException;

    public abstract double calculateMachineAvailHrs(Date date, Date date1) throws ReportsException, SQLException, RemoteException;
}
