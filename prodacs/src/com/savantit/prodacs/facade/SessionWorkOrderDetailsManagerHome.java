// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionWorkOrderDetailsManagerHome.java

package com.savantit.prodacs.facade;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

// Referenced classes of package com.savantit.prodacs.facade:
//            SessionWorkOrderDetailsManager

public interface SessionWorkOrderDetailsManagerHome
    extends EJBHome {

    public static final String COMP_NAME = "java:comp/env/ejb/SessionWorkOrderDetailsManager";
    public static final String JNDI_NAME = "SessionWorkOrderDetailsManagerBean";

    public abstract SessionWorkOrderDetailsManager create() throws CreateException, RemoteException;
}
