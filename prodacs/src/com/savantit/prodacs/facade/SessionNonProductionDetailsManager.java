// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionNonProductionDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.businessimplementation.production.NonProductionDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionNonProductionDetailsManager
    extends EJBObject {

    public abstract HashMap addNewNonProductionDetails(NonProductionDetails anonproductiondetails[]) throws ProductionException, SQLException, RemoteException;

    public abstract Vector viewUnPostedNonProductionDetails(Date date, Date date1) throws ProductionException, SQLException, RemoteException;

    public abstract NonProductionDetails getNonProductionDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap getAllNonProductionDetails(Filter afilter[], String s, boolean flag, int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllMachines() throws MachineException, SQLException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList() throws WorkCalendarException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllEmployees() throws EmployeeException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllEmployeesByTypes() throws EmployeeException, SQLException, ProductionException, RemoteException;

    public abstract LinkedHashMap getAllEmployeeTypes() throws SQLException, EmployeeException, RemoteException;

    public abstract LinkedHashMap getAllIdlBrkDwnRsns() throws SQLException, ProductionException, RemoteException;

    public abstract boolean updateNonProductionDetails(NonProductionDetails nonproductiondetails) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap minReqdEmployees() throws SQLException, ProductionException, RemoteException;

    public abstract ProductionAccountingDateDetails isPosted(String s, Date date, int i) throws ProductionException, ParseException, SQLException, RemoteException;

    public abstract HashMap makeNonProductionValid(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap makeNonProductionInValid(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract boolean isModifyForNprod(int i) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllMachines(Date date, int i) throws MachineException, SQLException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList(Date date) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap postNonProductionDetails(Vector vector) throws ProductionException, SQLException, RemoteException;
}
