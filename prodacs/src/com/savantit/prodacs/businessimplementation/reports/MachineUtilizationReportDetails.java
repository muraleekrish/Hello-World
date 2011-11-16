/*
 * Created on Feb 10, 2005
 *
 * ClassName	:  	MachineUtilizationReportDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.reports;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MachineUtilizationReportDetails implements Serializable
{
    private String mcCode;
	private String mcName;
	private String workHrs;
	private String breakHrs;
	private String idleHrs;
	private String utiHrs;

	private String utiPer;
	private String breakPer;
	private String idlePer;
	
	
	
    /**
     * @return Returns the breakHrs.
     */
    public String getBreakHrs()
    {
        return breakHrs;
    }
    /**
     * @param breakHrs The breakHrs to set.
     */
    public void setBreakHrs(String breakHrs)
    {
        this.breakHrs = breakHrs;
    }
    /**
     * @return Returns the breakPer.
     */
    public String getBreakPer()
    {
        return breakPer;
    }
    /**
     * @param breakPer The breakPer to set.
     */
    public void setBreakPer(String breakPer)
    {
        this.breakPer = breakPer;
    }
    /**
     * @return Returns the idleHrs.
     */
    public String getIdleHrs()
    {
        return idleHrs;
    }
    /**
     * @param idleHrs The idleHrs to set.
     */
    public void setIdleHrs(String idleHrs)
    {
        this.idleHrs = idleHrs;
    }
    /**
     * @return Returns the idlePer.
     */
    public String getIdlePer()
    {
        return idlePer;
    }
    /**
     * @param idlePer The idlePer to set.
     */
    public void setIdlePer(String idlePer)
    {
        this.idlePer = idlePer;
    }
    /**
     * @return Returns the mcCode.
     */
    public String getMcCode()
    {
        return mcCode;
    }
    /**
     * @param mcCode The mcCode to set.
     */
    public void setMcCode(String mcCode)
    {
        this.mcCode = mcCode;
    }
    /**
     * @return Returns the mcName.
     */
    public String getMcName()
    {
        return mcName;
    }
    /**
     * @param mcName The mcName to set.
     */
    public void setMcName(String mcName)
    {
        this.mcName = mcName;
    }
    /**
     * @return Returns the utiHrs.
     */
    public String getUtiHrs()
    {
        return utiHrs;
    }
    /**
     * @param utiHrs The utiHrs to set.
     */
    public void setUtiHrs(String utiHrs)
    {
        this.utiHrs = utiHrs;
    }
    /**
     * @return Returns the utiPer.
     */
    public String getUtiPer()
    {
        return utiPer;
    }
    /**
     * @param utiPer The utiPer to set.
     */
    public void setUtiPer(String utiPer)
    {
        this.utiPer = utiPer;
    }
    /**
     * @return Returns the workHrs.
     */
    public String getWorkHrs()
    {
        return workHrs;
    }
    /**
     * @param workHrs The workHrs to set.
     */
    public void setWorkHrs(String workHrs)
    {
        this.workHrs = workHrs;
    }
}

/*
*$Log: MachineUtilizationReportDetails.java,v $
*Revision 1.2  2005/02/19 09:06:49  kduraisamy
*
*serializable added.
*
*Revision 1.1  2005/02/11 06:55:40  kduraisamy
*initial commit.
*
*
*/