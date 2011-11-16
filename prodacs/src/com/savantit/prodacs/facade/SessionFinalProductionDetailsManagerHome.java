// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   SessionFinalProductionDetailsManagerHome.java

package com.savantit.prodacs.facade;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

// Referenced classes of package com.savantit.prodacs.facade:
//            SessionFinalProductionDetailsManager

public interface SessionFinalProductionDetailsManagerHome
    extends EJBHome {

    public static final String COMP_NAME = "java:comp/env/ejb/SessionFinalProductionDetailsManager";
    public static final String JNDI_NAME = "SessionFinalProductionDetailsManager";

    public abstract SessionFinalProductionDetailsManager create() throws CreateException, RemoteException;
}
