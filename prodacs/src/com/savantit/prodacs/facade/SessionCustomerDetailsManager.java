// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionCustomerDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.customer.CustomerDetails;
import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.customer.CustomerTypDetails;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionCustomerDetailsManager
    extends EJBObject {

    public abstract boolean addCustomerDetails(CustomerDetails customerdetails) throws SQLException, CustomerException, RemoteException;

    public abstract boolean updateCustomerDetails(CustomerDetails customerdetails) throws SQLException, CustomerException, RemoteException;

    public abstract CustomerDetails getCustomerDetails(int i) throws SQLException, CustomerException, RemoteException;

    public abstract LinkedHashMap getAllCustomers() throws SQLException, CustomerException, RemoteException;

    public abstract LinkedHashMap getCustomerTypes() throws CustomerException, SQLException, RemoteException;

    public abstract HashMap getCustomers(Filter afilter[], String s, boolean flag, int i, int j) throws SQLException, CustomerException, RemoteException;

    public abstract int addCustomerTypDetails(CustomerTypDetails customertypdetails) throws CustomerException, SQLException, RemoteException;

    public abstract HashMap makeCustomerValid(Vector vector) throws CustomerException, SQLException, RemoteException;

    public abstract HashMap makeCustomerInValid(Vector vector) throws CustomerException, SQLException, RemoteException;

    public abstract LinkedHashMap getCustomerNameByType(int i) throws CustomerException, SQLException, RemoteException;

    public abstract HashMap getAllCustomerTypeDetails(Filter afilter[], String s, boolean flag, int i, int j) throws SQLException, CustomerException, RemoteException;

    public abstract CustomerTypDetails getCustomerTypeDetails(int i) throws SQLException, CustomerException, RemoteException;

    public abstract boolean updateCustomerTypeDetails(CustomerTypDetails customertypdetails) throws SQLException, CustomerException, RemoteException;

    public abstract HashMap makeCustomerTypInValid(Vector vector) throws CustomerException, SQLException, RemoteException;

    public abstract HashMap makeCustomerTypValid(Vector vector) throws CustomerException, SQLException, RemoteException;
}
