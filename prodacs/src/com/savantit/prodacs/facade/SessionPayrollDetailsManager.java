// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionPayrollDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.payroll.PayRollException;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.infra.beans.Filter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public interface SessionPayrollDetailsManager
    extends EJBObject {

    public abstract Vector getEmployeeDetails(int i, int j) throws SQLException, PayRollException, RemoteException;

    public abstract boolean createNewPayrollCycle(String s, String s1) throws SQLException, PayRollException, RemoteException;

    public abstract HashMap getAllPyrlCycleStatForInterface() throws SQLException, PayRollException, RemoteException;

    public abstract HashMap getForPayRoll(int i, int j, int k) throws SQLException, PayRollException, RemoteException;

    public abstract boolean createPyrlInterface(int i, String s) throws SQLException, PayRollException, SAXException, IOException, ParserConfigurationException, ProductionException, RemoteException;

    public abstract HashMap createPayRoll(Vector vector, String s) throws SQLException, PayRollException, RemoteException;

    public abstract HashMap getAllPyrlCycleStatForPayroll() throws SQLException, PayRollException, RemoteException;

    public abstract boolean adjustPayRollDetails(Vector vector, String s) throws SQLException, PayRollException, RemoteException;

    public abstract boolean closePayrollDetails(int i) throws PayRollException, SQLException, RemoteException;

    public abstract HashMap getForPayRollAdjstmnt(Filter afilter[], String s, boolean flag) throws PayRollException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllEmployeesByType(int i) throws EmployeeException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllEmployeeTypes() throws SQLException, EmployeeException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList() throws WorkCalendarException, SQLException, RemoteException;

    public abstract Vector getPayRollDateShiftDetails(int i) throws SQLException, PayRollException, RemoteException;

    public abstract HashMap getForClosePayRoll(int i, int j, int k) throws SQLException, PayRollException, RemoteException;

    public abstract boolean createNewPayrollCycleStat() throws PayRollException, SQLException, ParseException, RemoteException;

    public abstract HashMap getAllPayRollIdsInfo(Filter afilter[], String s, boolean flag, int i, int j) throws PayRollException, SQLException, RemoteException;

    public abstract HashMap getCurrentPayrollCycle() throws SQLException, PayRollException, RemoteException;
}
