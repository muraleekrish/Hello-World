// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionPayrollDetailsManagerHome.java

package com.savantit.prodacs.facade;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

// Referenced classes of package com.savantit.prodacs.facade:
//            SessionPayrollDetailsManager

public interface SessionPayrollDetailsManagerHome
    extends EJBHome {

    public static final String COMP_NAME = "java:comp/env/ejb/SessionPayrollDetailsManager";
    public static final String JNDI_NAME = "SessionPayrollDetailsManagerBean";

    public abstract SessionPayrollDetailsManager create() throws CreateException, RemoteException;
}
