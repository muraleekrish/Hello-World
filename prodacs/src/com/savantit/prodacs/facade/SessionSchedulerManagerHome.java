// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionSchedulerManagerHome.java

package com.savantit.prodacs.facade;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

// Referenced classes of package com.savantit.prodacs.facade:
//            SessionSchedulerManager

public interface SessionSchedulerManagerHome
    extends EJBHome {

    public static final String COMP_NAME = "java:comp/env/ejb/SessionSchedulerManager";
    public static final String JNDI_NAME = "SessionSchedulerManager";

    public abstract SessionSchedulerManager create() throws CreateException, RemoteException;
}
