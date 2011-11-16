// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionPopDetailsManagerHome.java

package com.savantit.prodacs.facade;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

// Referenced classes of package com.savantit.prodacs.facade:
//            SessionPopDetailsManager

public interface SessionPopDetailsManagerHome
    extends EJBHome {

    public static final String COMP_NAME = "java:comp/env/ejb/SessionPopDetailsManager";
    public static final String JNDI_NAME = "SessionPopDetailsManagerBean";

    public abstract SessionPopDetailsManager create() throws CreateException, RemoteException;
}
