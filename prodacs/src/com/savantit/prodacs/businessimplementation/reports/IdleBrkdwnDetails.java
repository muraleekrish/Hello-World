/*
 * Created on Feb 19, 2005
 *
 * ClassName	:  	IdleBrkdwnDetails.java
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
public class IdleBrkdwnDetails implements Serializable
{

    private String mcCde;
    private float mechHrs;
    private float elecHrs;
    private float instHrs;
    private float manHrs;
    private float jobHrs;
    private float drgHrs;
    private float pccHrs;
    private float toolsHrs;
    private float gaugeHrs;
    private float othersHrs;
    private float totHrs;
    public IdleBrkdwnDetails()
    {
        mcCde = "";
        mechHrs = 0;
        elecHrs = 0;
        instHrs = 0;
        manHrs = 0;
        jobHrs = 0;
        drgHrs = 0;
        pccHrs = 0;
        toolsHrs = 0;
        gaugeHrs = 0;
        othersHrs = 0;
        totHrs = 0;
           
    }
    
    
    /**
     * @return Returns the drgHrs.
     */
    public float getDrgHrs()
    {
        return drgHrs;
    }
    /**
     * @param drgHrs The drgHrs to set.
     */
    public void setDrgHrs(float drgHrs)
    {
        this.drgHrs = drgHrs;
    }
    /**
     * @return Returns the elecHrs.
     */
    public float getElecHrs()
    {
        return elecHrs;
    }
    /**
     * @param elecHrs The elecHrs to set.
     */
    public void setElecHrs(float elecHrs)
    {
        this.elecHrs = elecHrs;
    }
    /**
     * @return Returns the gaugeHrs.
     */
    public float getGaugeHrs()
    {
        return gaugeHrs;
    }
    /**
     * @param gaugeHrs The gaugeHrs to set.
     */
    public void setGaugeHrs(float gaugeHrs)
    {
        this.gaugeHrs = gaugeHrs;
    }
    /**
     * @return Returns the instHrs.
     */
    public float getInstHrs()
    {
        return instHrs;
    }
    /**
     * @param instHrs The instHrs to set.
     */
    public void setInstHrs(float instHrs)
    {
        this.instHrs = instHrs;
    }
    /**
     * @return Returns the jobHrs.
     */
    public float getJobHrs()
    {
        return jobHrs;
    }
    /**
     * @param jobHrs The jobHrs to set.
     */
    public void setJobHrs(float jobHrs)
    {
        this.jobHrs = jobHrs;
    }
    /**
     * @return Returns the manHrs.
     */
    public float getManHrs()
    {
        return manHrs;
    }
    /**
     * @param manHrs The manHrs to set.
     */
    public void setManHrs(float manHrs)
    {
        this.manHrs = manHrs;
    }
    /**
     * @return Returns the mcCde.
     */
    public String getMcCde()
    {
        return mcCde;
    }
    /**
     * @param mcCde The mcCde to set.
     */
    public void setMcCde(String mcCde)
    {
        this.mcCde = mcCde;
    }
    /**
     * @return Returns the mechHrs.
     */
    public float getMechHrs()
    {
        return mechHrs;
    }
    /**
     * @param mechHrs The mechHrs to set.
     */
    public void setMechHrs(float mechHrs)
    {
        this.mechHrs = mechHrs;
    }
    /**
     * @return Returns the othersHrs.
     */
    public float getOthersHrs()
    {
        return othersHrs;
    }
    /**
     * @param othersHrs The othersHrs to set.
     */
    public void setOthersHrs(float othersHrs)
    {
        this.othersHrs = othersHrs;
    }
    /**
     * @return Returns the pccHrs.
     */
    public float getPccHrs()
    {
        return pccHrs;
    }
    /**
     * @param pccHrs The pccHrs to set.
     */
    public void setPccHrs(float pccHrs)
    {
        this.pccHrs = pccHrs;
    }
    /**
     * @return Returns the toolsHrs.
     */
    public float getToolsHrs()
    {
        return toolsHrs;
    }
    /**
     * @param toolsHrs The toolsHrs to set.
     */
    public void setToolsHrs(float toolsHrs)
    {
        this.toolsHrs = toolsHrs;
    }
    /**
     * @return Returns the totHrs.
     */
    public float getTotHrs()
    {
        return totHrs;
    }
    /**
     * @param totHrs The totHrs to set.
     */
    public void setTotHrs(float totHrs)
    {
        this.totHrs = totHrs;
    }
}

/*
*$Log: IdleBrkdwnDetails.java,v $
*Revision 1.2  2005/09/23 14:31:39  kduraisamy
*field totHrs added.
*
*Revision 1.1  2005/02/22 06:14:07  kduraisamy
*initial commit.
*
*
*/