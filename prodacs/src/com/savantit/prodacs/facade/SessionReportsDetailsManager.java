// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionReportsDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.businessimplementation.payroll.PayRollException;
import com.savantit.prodacs.businessimplementation.production.CustomerMailJobDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingMachineDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.reports.*;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.ejb.EJBObject;

public interface SessionReportsDetailsManager
    extends EJBObject {

    public abstract MachineUtilizationReportDetails[] fetchMachineUtilization(Date date, Date date1) throws SQLException, ReportsException, RemoteException;

    public abstract IdleBrkdwnDetails[] fetchIdleBrkdwnHrs(Date date, Date date1) throws ReportsException, SQLException, RemoteException;

    public abstract MonthlyPercentageDetails[] fetchMonthlyPercentageOfMachine(String s, int i, int j) throws ParseException, ReportsException, SQLException, RemoteException;

    public abstract TopSheetDetails[] fetchTopSheetDetails() throws ReportsException, SQLException, RemoteException;

    public abstract HalfYearlyProductionReportDetail[] fetchHalfYearlyProductionReport(String s, int i, int j) throws SQLException, ReportsException, RemoteException;

    public abstract SalaryReportDetails[] fetchSalaryReports(int i, int j) throws SQLException, ReportsException, RemoteException;

    public abstract MonthlyReportDetails[] fetchMonthlyReportsDetails(int i, int j) throws SQLException, ReportsException, RemoteException;

    public abstract EmployeePerformanceReturnDetails[] fetchEmployeePerformance(Date date, Date date1, int i) throws ReportsException, SQLException, RemoteException;

    public abstract ProductionOfMachineDetails[] fetchProductionOfMachineDetails(Date date, Date date1, String s) throws ReportsException, SQLException, RemoteException;

    public abstract QuantityProducedDetails[] fetchQuantityProducedDetails(Date date, Date date1) throws ReportsException, SQLException, RemoteException;

    public abstract WoReferenceDetails[] fetchWoReferenceDetails(Date date, Date date1, int i) throws SQLException, ReportsException, RemoteException;

    public abstract ProductionAccountingMachineDetails[] fetchProductionAccountingDateDetails(Date date) throws SQLException, ReportsException, RemoteException;

    public abstract ProductionDateWiseDetails[] fetchProductionDetails(Date date, Date date1) throws ReportsException, SQLException, ProductionException, RemoteException;

    public abstract ActualHrsDetails[] fetchActualHrsDetails() throws SQLException, ReportsException, RemoteException;

    public abstract ProductionMachineWiseDetails[] fetchProductionMachineWiseDetails(Date date, Date date1) throws ReportsException, SQLException, ProductionException, RemoteException;

    public abstract HashMap getClosedPyrlCycleStatForPayroll() throws SQLException, PayRollException, RemoteException;

    public abstract EmployeePayrollDetails[] fetchEmployeePayrollDetails(int i) throws ReportsException, SQLException, RemoteException;

    public abstract JobTimeDetails[] fetchJobTimeDetails(Filter afilter[], String s, boolean flag, int i) throws SQLException, ReportsException, RemoteException;

    public abstract MonthlyReportDetails[] fetchMonthlyReportsDetailsValidationReport(int i, int j) throws SQLException, ReportsException, RemoteException;

    public abstract LinkedHashMap getAllCustomers() throws SQLException, CustomerException, RemoteException;

    public abstract CustomerMailJobDetails[] getAllJobStatusDetails(int i, Date date, Date date1) throws ProductionException, SQLException, RemoteException;

    public abstract IdleBrkdwnDetails fetchIdleBrkdwnHrs(Date date, Date date1, String s) throws ReportsException, SQLException, RemoteException;

    public abstract EmployeePerformanceReturnDetails[] fetchEmployeePerformanceByEmpType(Date date, Date date1, int i) throws ReportsException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllMachines() throws MachineException, SQLException, RemoteException;
}
