// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionMachineDetailsManagerHome.java

package com.savantit.prodacs.facade;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

// Referenced classes of package com.savantit.prodacs.facade:
//            SessionMachineDetailsManager

public interface SessionMachineDetailsManagerHome
    extends EJBHome {

    public static final String COMP_NAME = "java:comp/env/ejb/SessionMachineDetailsManager";
    public static final String JNDI_NAME = "SessionMachineDetailsManagerBean";

    public abstract SessionMachineDetailsManager create() throws CreateException, RemoteException;
}
