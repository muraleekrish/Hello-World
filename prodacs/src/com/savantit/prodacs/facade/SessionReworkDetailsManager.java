// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionReworkDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.job.ReworkDetails;
import com.savantit.prodacs.businessimplementation.job.ReworkException;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionReworkDetailsManager
    extends EJBObject {

    public abstract boolean addNewReworkCategory(ReworkDetails reworkdetails) throws ReworkException, SQLException, RemoteException;

    public abstract LinkedHashMap getAllReworkCategories() throws ReworkException, SQLException, RemoteException;

    public abstract boolean addNewReworkReason(ReworkDetails reworkdetails) throws ReworkException, SQLException, RemoteException;

    public abstract boolean updateReworkReason(ReworkDetails reworkdetails) throws ReworkException, SQLException, RemoteException;

    public abstract ReworkDetails getReworkDetails(int i) throws ReworkException, SQLException, RemoteException;

    public abstract HashMap makeReworkReasonValid(Vector vector) throws ReworkException, SQLException, RemoteException;

    public abstract HashMap makeReworkReasonInValid(Vector vector) throws ReworkException, SQLException, RemoteException;

    public abstract HashMap getAllReworkDetails(Filter afilter[], String s, boolean flag, int i, int j) throws ReworkException, SQLException, RemoteException;
}
