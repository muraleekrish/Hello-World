// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionPostingDetails.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.production.ProductionException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionPostingDetails
    extends EJBObject {

    public abstract HashMap postProductionDetails(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap postNonProductionDetails(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap postRadlProductionDetails(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap postPopDetails(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract Vector viewUnPostedProductionDetails(Date date, Date date1) throws SQLException, ProductionException, RemoteException;

    public abstract Vector viewUnPostedNonProductionDetails(Date date, Date date1) throws ProductionException, SQLException, RemoteException;

    public abstract Vector viewUnPostedRadlProductionDetails(Date date, Date date1) throws SQLException, ProductionException, RemoteException;

    public abstract Vector viewUnPostedPopProductionDetails(Date date, Date date1) throws ProductionException, SQLException, RemoteException;

    public abstract HashMap postFinalProductionDetails(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract Vector viewUnPostedFinalProductionDetails(Date date, Date date1) throws SQLException, ProductionException, RemoteException;

    public abstract Vector viewUnPostedShipmentDetails(Date date, Date date1) throws SQLException, ProductionException, RemoteException;

    public abstract HashMap postShipmentDetails(Vector vector) throws ProductionException, SQLException, RemoteException;

    public abstract Vector viewUnPostedDespatchDetails(Date date, Date date1) throws SQLException, ProductionException, RemoteException;

    public abstract HashMap postDespatchDetails(Vector vector) throws ProductionException, SQLException, RemoteException;
}
