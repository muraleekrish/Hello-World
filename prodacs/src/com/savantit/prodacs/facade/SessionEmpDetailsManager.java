// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionEmpDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.employee.EmployeeDetails;
import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.employee.EmployeeStatusDetails;
import com.savantit.prodacs.businessimplementation.employee.EmployeeTypeDetails;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionEmpDetailsManager
    extends EJBObject {

    public abstract boolean addEmployee(EmployeeDetails employeedetails) throws SQLException, EmployeeException, RemoteException;

    public abstract boolean updateEmployee(EmployeeDetails employeedetails) throws SQLException, EmployeeException, RemoteException;

    public abstract LinkedHashMap getAllEmployees() throws Exception, RemoteException;

    public abstract EmployeeDetails getEmployeeDetails(int i) throws SQLException, EmployeeException, RemoteException;

    public abstract HashMap getAllEmployeeDetails(Filter afilter[], String s, boolean flag, int i, int j) throws SQLException, EmployeeException, RemoteException;

    public abstract HashMap makeEmployeesValid(Vector vector) throws SQLException, EmployeeException, RemoteException;

    public abstract HashMap makeEmployeesInValid(Vector vector) throws SQLException, EmployeeException, RemoteException;

    public abstract boolean addEmployeeTypeDetails(EmployeeTypeDetails employeetypedetails) throws SQLException, EmployeeException, RemoteException;

    public abstract boolean updateEmployeeTypeDetails(EmployeeTypeDetails employeetypedetails) throws SQLException, EmployeeException, RemoteException;

    public abstract LinkedHashMap getAllEmployeeTypes() throws SQLException, EmployeeException, RemoteException;

    public abstract EmployeeTypeDetails getEmployeeTypeDetails(int i) throws SQLException, EmployeeException, RemoteException;

    public abstract HashMap makeEmployeeTypesValid(Vector vector) throws SQLException, EmployeeException, RemoteException;

    public abstract HashMap makeEmployeeTypesInValid(Vector vector) throws SQLException, EmployeeException, RemoteException;

    public abstract HashMap getAllEmployeeTypeDetails(Filter afilter[], String s, boolean flag, int i, int j) throws SQLException, EmployeeException, RemoteException;

    public abstract int addNewEmployeeStatus(EmployeeStatusDetails employeestatusdetails) throws SQLException, EmployeeException, RemoteException;

    public abstract LinkedHashMap getAllEmployeeStatus() throws SQLException, EmployeeException, RemoteException;

    public abstract LinkedHashMap getAllEmployeesByType(int i) throws EmployeeException, SQLException, RemoteException;

    public abstract EmployeeTypeDetails[] getEmployeeTypeWithTeam() throws EmployeeException, SQLException, RemoteException;

    public abstract boolean updateEmployeeTypeDetailsWithTeam(EmployeeTypeDetails aemployeetypedetails[]) throws SQLException, EmployeeException, RemoteException;
}
