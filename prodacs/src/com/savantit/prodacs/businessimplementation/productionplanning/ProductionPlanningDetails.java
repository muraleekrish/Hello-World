/*
 * Created on Dec 2, 2005
 *
 * ClassName	:  	ProductionPlanningDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.productionplanning;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 */
public class ProductionPlanningDetails implements Serializable
{
    private int opnGpId;
    private String opnGpCode;
    private String opnGpMachines;
    private int openJobQtys;
    private int activeJobQtys;
    private double pendingStdHrs;
    
    public ProductionPlanningDetails()
    {
        this.opnGpId = 0;
        this.opnGpCode = "";
        this.opnGpMachines = "";
        this.openJobQtys = 0;
        this.activeJobQtys = 0;
        this.pendingStdHrs = 0;
    }
    
    
    
    /**
     * @return Returns the openJobQtys.
     */
    public int getOpenJobQtys()
    {
        return openJobQtys;
    }
    /**
     * @param openJobQtys The openJobQtys to set.
     */
    public void setOpenJobQtys(int openJobQtys)
    {
        this.openJobQtys = openJobQtys;
    }
    /**
     * @return Returns the opnGpCode.
     */
    public String getOpnGpCode()
    {
        return opnGpCode;
    }
    /**
     * @param opnGpCode The opnGpCode to set.
     */
    public void setOpnGpCode(String opnGpCode)
    {
        this.opnGpCode = opnGpCode;
    }
    /**
     * @return Returns the opnGpId.
     */
    public int getOpnGpId()
    {
        return opnGpId;
    }
    /**
     * @param opnGpId The opnGpId to set.
     */
    public void setOpnGpId(int opnGpId)
    {
        this.opnGpId = opnGpId;
    }
    /**
     * @return Returns the opnGpMachines.
     */
    public String getOpnGpMachines()
    {
        return opnGpMachines;
    }
    /**
     * @param opnGpMachines The opnGpMachines to set.
     */
    public void setOpnGpMachines(String opnGpMachines)
    {
        this.opnGpMachines = opnGpMachines;
    }
    /**
     * @return Returns the activeJobQtys.
     */
    public int getActiveJobQtys()
    {
        return activeJobQtys;
    }
    /**
     * @param pendingJobQtys The activeJobQtys to set.
     */
    public void setActiveJobQtys(int activeJobQtys)
    {
        this.activeJobQtys = activeJobQtys;
    }
    /**
     * @return Returns the pendingStdHrs.
     */
    public double getPendingStdHrs()
    {
        return pendingStdHrs;
    }
    /**
     * @param pendingStdHrs The pendingStdHrs to set.
     */
    public void setPendingStdHrs(double pendingStdHrs)
    {
        this.pendingStdHrs = pendingStdHrs;
    }
}

/*
*$Log: ProductionPlanningDetails.java,v $
*Revision 1.1  2005/12/05 05:31:05  kduraisamy
*initial commit.
*
*
*/