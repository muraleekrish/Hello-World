// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionWorkCalendarDetailsManager.java

package com.savantit.prodacs.facade;

import com.savantit.prodacs.businessimplementation.workcalendar.AvailabilityDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.BaseCalendarDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.ShiftDefnDetails;
import com.savantit.prodacs.businessimplementation.workcalendar.WorkCalendarException;
import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface SessionWorkCalendarDetailsManager
    extends EJBObject {

    public abstract boolean addShiftDefinition(ShiftDefnDetails shiftdefndetails) throws WorkCalendarException, SQLException, RemoteException;

    public abstract boolean updateShiftDefinition(ShiftDefnDetails shiftdefndetails) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap deleteShiftDefinition(Vector vector) throws WorkCalendarException, SQLException, RemoteException;

    public abstract ShiftDefnDetails getShiftDefinition(int i) throws WorkCalendarException, SQLException, RemoteException;

    public abstract LinkedHashMap getShiftDefnNameList() throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap getAllShiftDefnDetails(Filter afilter[], String s, boolean flag, int i, int j) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap makeShiftDefinitionValid(Vector vector) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap makeShiftDefinitionInValid(Vector vector) throws WorkCalendarException, SQLException, RemoteException;

    public abstract boolean addBaseCalendar(BaseCalendarDetails basecalendardetails) throws WorkCalendarException, SQLException, RemoteException;

    public abstract boolean updateBaseCalendar(BaseCalendarDetails basecalendardetails) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap deleteBaseCalendar(Vector vector) throws WorkCalendarException, SQLException, RemoteException;

    public abstract BaseCalendarDetails getBaseCalendarDetails(int i) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap getBaseCalendarNameList() throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap getAllBaseCalendarDetails(Filter afilter[], String s, boolean flag, int i, int j) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap makeBaseCalendarValid(Vector vector) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap makeBaseCalendarInValid(Vector vector) throws WorkCalendarException, SQLException, RemoteException;

    public abstract boolean addAvailabilityCalendar(AvailabilityDetails availabilitydetails) throws WorkCalendarException, SQLException, RemoteException;

    public abstract boolean addCustomAvbltyDetails(AvailabilityDetails availabilitydetails, DBConnection dbconnection) throws WorkCalendarException, SQLException, RemoteException;

    public abstract boolean addCustomNonAvbltyDetails(AvailabilityDetails availabilitydetails, DBConnection dbconnection) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap deleteAvailablityCalendar(Vector vector) throws WorkCalendarException, SQLException, RemoteException;

    public abstract AvailabilityDetails getAvailabilityDetails(int i) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap getAvailabilityNameList() throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap getAllAvailabilityDetails(Filter afilter[], String s, boolean flag, int i, int j) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap makeAvailablityCalendarValid(Vector vector) throws WorkCalendarException, SQLException, RemoteException;

    public abstract HashMap makeAvailablityCalendarInValid(Vector vector) throws WorkCalendarException, SQLException, RemoteException;

    public abstract boolean updateAvailabilityCalendar(AvailabilityDetails availabilitydetails) throws WorkCalendarException, SQLException, RemoteException;

    public abstract boolean chooseAvbltyName(int i) throws WorkCalendarException, SQLException, RemoteException;
}
