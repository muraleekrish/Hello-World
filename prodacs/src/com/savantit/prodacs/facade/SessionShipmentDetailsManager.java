// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionShipmentDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.businessimplementation.production.ShipmentDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.businessimplementation.workorder.WorkOrderException;
import com.savantit.prodacs.infra.beans.Filter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionShipmentDetailsManager
    extends EJBObject {

    public abstract HashMap getAllShipmentDetails(Filter afilter[], String s, boolean flag, int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract boolean addNewShipmentDetails(ShipmentDetails ashipmentdetails[]) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getWorkOrderList() throws SQLException, WorkOrderException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList(Date date) throws WorkCalendarException, SQLException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList() throws WorkCalendarException, SQLException, RemoteException;

    public abstract Vector getProdnJobDetailsByWorkOrder(int i) throws SQLException, ProductionException, RemoteException;

    public abstract Vector getShipmentOperationDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap getAllJobStatusDetails(String s, Date date, Date date1, Filter afilter[], String s1, boolean flag, int i, 
            int j) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getProdnJobDetailsForUpdateByWorkOrder(int i, int j, int k, int l, int i1, int j1) throws SQLException, ProductionException, RemoteException;

    public abstract boolean isModifyForDespatch(int i) throws ProductionException, SQLException, RemoteException;

    public abstract ShipmentDetails getShipmentDetails(int i) throws ProductionException, SQLException, RemoteException;

    public abstract Vector getShipmentOperationDetailsForUpdate(int i, int j) throws ProductionException, SQLException, RemoteException;

    public abstract boolean updateShipmentDetails(ShipmentDetails shipmentdetails) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap makeShipmentValid(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap makeShipmentInValid(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap getAllJobStatusDetails(int i, Date date, Date date1, Filter afilter[], String s, boolean flag, int j, 
            int k) throws ProductionException, SQLException, RemoteException;

    public abstract LinkedHashMap getCustomerTypes() throws CustomerException, SQLException, RemoteException;

    public abstract LinkedHashMap getCustomerNameByType(int i) throws CustomerException, SQLException, RemoteException;
}
