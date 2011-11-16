// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionSecurityAdminManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.employee.EmployeeDetails;
import com.savantit.prodacs.businessimplementation.employee.EmployeeException;
import com.savantit.prodacs.businessimplementation.securityadmin.*;
import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJBObject;

public interface SessionSecurityAdminManager
    extends EJBObject {

    public abstract boolean addNewGroup(SecAdminGroupDetails secadmingroupdetails) throws SQLException, SecurityAdminException, RemoteException;

    public abstract boolean updateGroup(SecAdminGroupDetails secadmingroupdetails) throws SQLException, SecurityAdminException, RemoteException;

    public abstract LinkedHashMap getAllGroups() throws SQLException, SecurityAdminException, RemoteException;

    public abstract SecAdminGroupDetails getGroupDetails(int i) throws SQLException, SecurityAdminException, RemoteException;

    public abstract HashMap getAllGroupsDetails(Filter afilter[], String s, boolean flag, int i, int j) throws SQLException, SecurityAdminException, RemoteException;

    public abstract HashMap makeGroupsValid(Vector vector) throws SecurityAdminException, RemoteException;

    public abstract HashMap makeGroupsInValid(Vector vector) throws SecurityAdminException, RemoteException;

    public abstract HashMap deleteGroups(Vector vector) throws SecurityAdminException, RemoteException;

    public abstract int addNewContact(ContactDetails contactdetails, DBConnection dbconnection) throws SecurityAdminException, SQLException, RemoteException;

    public abstract int updateContact(ContactDetails contactdetails, DBConnection dbconnection) throws SQLException, SecurityAdminException, RemoteException;

    public abstract boolean addNewUser(SecAdminUserDetails secadminuserdetails) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SQLException, SecurityAdminException, RemoteException;

    public abstract HashMap getAllUsersDetails(Filter afilter[], String s, boolean flag, int i, int j) throws SQLException, SecurityAdminException, RemoteException;

    public abstract LinkedHashMap getAllEmployeeTypes() throws SQLException, EmployeeException, RemoteException;

    public abstract LinkedHashMap getAllEmployeesByType(int i) throws EmployeeException, SQLException, RemoteException;

    public abstract EmployeeDetails getEmployeeDetails(int i) throws SQLException, EmployeeException, RemoteException;

    public abstract HashMap deleteUsers(Vector vector) throws SecurityAdminException, RemoteException;

    public abstract boolean updateUser(SecAdminUserDetails secadminuserdetails) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SQLException, SecurityAdminException, RemoteException;

    public abstract HashMap makeUsersValid(Vector vector) throws SecurityAdminException, RemoteException;

    public abstract HashMap makeUsersInValid(Vector vector) throws SecurityAdminException, RemoteException;

    public abstract SecAdminUserDetails getUserDetails(String s) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SecurityAdminException, SQLException, IOException, RemoteException;

    public abstract boolean checkUserAuthentication(String s, String s1) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SecurityAdminException, SQLException, IOException, RemoteException;

    public abstract UserAuthDetails getUserResources(String s) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, SQLException, SecurityAdminException, IOException, RemoteException;

    public abstract LinkedHashMap getAllCustomers() throws SQLException, CustomerException, RemoteException;

    public abstract Vector getAllUsers() throws SecurityAdminException, SQLException, RemoteException;

    public abstract Vector getAllUsers(Filter afilter[]) throws SecurityAdminException, RemoteException;

    public abstract boolean checkMasterUserAuthentication(String s, String s1) throws SecurityAdminException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException, RemoteException;

    public abstract boolean addSystemInfo(SystemInfoDetails systeminfodetails) throws SecurityAdminException, SQLException, RemoteException;

    public abstract boolean checkUserAuthentication(String s, String s1, SystemInfoDetails systeminfodetails) throws SecurityAdminException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalStateException, IllegalBlockSizeException, BadPaddingException, IOException, RemoteException;
}
