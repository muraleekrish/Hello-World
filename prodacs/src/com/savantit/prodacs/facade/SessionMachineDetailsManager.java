// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionMachineDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.machine.MachineDetails;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.businessimplementation.machine.MachineTypeDetails;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionMachineDetailsManager
    extends EJBObject {

    public abstract int addMachineTypeDetails(MachineTypeDetails machinetypedetails) throws SQLException, MachineException, RemoteException;

    public abstract LinkedHashMap getAllMachineTypes() throws SQLException, MachineException, RemoteException;

    public abstract boolean addMachineDetails(MachineDetails machinedetails) throws SQLException, MachineException, RemoteException;

    public abstract boolean updateMachineDetails(MachineDetails machinedetails) throws SQLException, MachineException, RemoteException;

    public abstract MachineDetails getMachineDetails(String s) throws SQLException, MachineException, RemoteException;

    public abstract LinkedHashMap getAllMachines() throws MachineException, SQLException, RemoteException;

    public abstract HashMap makeMachinesValid(Vector vector) throws SQLException, MachineException, RemoteException;

    public abstract HashMap makeMachinesInValid(Vector vector) throws SQLException, MachineException, RemoteException;

    public abstract HashMap getAllMachineDetails(Filter afilter[], String s, boolean flag, int i, int j) throws SQLException, MachineException, RemoteException;

    public abstract HashMap getAllMachineTypeDetails(Filter afilter[], String s, boolean flag, int i, int j) throws SQLException, CustomerException, MachineException, RemoteException;

    public abstract MachineTypeDetails getMachineTypeDetails(int i) throws SQLException, MachineException, RemoteException;

    public abstract boolean updateMachineTypeDetails(MachineTypeDetails machinetypedetails) throws SQLException, MachineException, RemoteException;

    public abstract HashMap makeMachineTypInValid(Vector vector) throws MachineException, SQLException, RemoteException;

    public abstract HashMap makeMachineTypValid(Vector vector) throws MachineException, SQLException, RemoteException;
}
