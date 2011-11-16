/*
 * Created on Feb 21, 2005
 *
 * ClassName	:  	HalfYearlyPercentageDetails.java
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
public class HalfYearlyPercentageDetails implements Serializable
{

    private double mcTotHrs;
	private double mcAvailHrs;
	private double brkDwnHrs;
	
	private double totProdHrs;
    private String yearString;	
	private double idlInstHrs;
	private double idlManHrs;
	private double idlJobHrs;
	private double idlDnoHrs;
	private double idlPccHrs;
	private double idlToolsHrs;
	private double idlGaugeHrs;
	private double idlOtherHrs;
	private double totIdlHrs;
	private double prodAccountedHrs;
	private double effProdHrs;
	private String perOfProd;
	
	

    /**
     * 
     */
    public HalfYearlyPercentageDetails()
    {
        mcTotHrs = 0;
    	mcAvailHrs = 0;

    	brkDwnHrs = 0;
    	
    	totProdHrs = 0;
    	idlInstHrs = 0;
    	idlManHrs = 0;
    	idlJobHrs = 0;
    	idlDnoHrs = 0;
    	idlPccHrs = 0;
    	idlToolsHrs = 0;
    	idlGaugeHrs = 0;
    	idlOtherHrs = 0;
    	totIdlHrs = 0;
    	yearString = "";
    	effProdHrs = 0;
    	prodAccountedHrs = 0;
    	perOfProd = "";

    }
    
    /**
     * @return Returns the brkDwnHrs.
     */
    public double getBrkDwnHrs()
    {
        return brkDwnHrs;
    }
    /**
     * @param brkDwnHrs The brkDwnHrs to set.
     */
    public void setBrkDwnHrs(double brkDwnHrs)
    {
        this.brkDwnHrs = brkDwnHrs;
    }
    /**
     * @return Returns the effProdHrs.
     */
    public double getEffProdHrs()
    {
        return effProdHrs;
    }
    /**
     * @param effProdHrs The effProdHrs to set.
     */
    public void setEffProdHrs(double effProdHrs)
    {
        this.effProdHrs = effProdHrs;
    }
    /**
     * @return Returns the idlDnoHrs.
     */
    public double getIdlDnoHrs()
    {
        return idlDnoHrs;
    }
    /**
     * @param idlDnoHrs The idlDnoHrs to set.
     */
    public void setIdlDnoHrs(double idlDnoHrs)
    {
        this.idlDnoHrs = idlDnoHrs;
    }
    /**
     * @return Returns the idlGaugeHrs.
     */
    public double getIdlGaugeHrs()
    {
        return idlGaugeHrs;
    }
    /**
     * @param idlGaugeHrs The idlGaugeHrs to set.
     */
    public void setIdlGaugeHrs(double idlGaugeHrs)
    {
        this.idlGaugeHrs = idlGaugeHrs;
    }
    /**
     * @return Returns the idlInstHrs.
     */
    public double getIdlInstHrs()
    {
        return idlInstHrs;
    }
    /**
     * @param idlInstHrs The idlInstHrs to set.
     */
    public void setIdlInstHrs(double idlInstHrs)
    {
        this.idlInstHrs = idlInstHrs;
    }
    /**
     * @return Returns the idlJobHrs.
     */
    public double getIdlJobHrs()
    {
        return idlJobHrs;
    }
    /**
     * @param idlJobHrs The idlJobHrs to set.
     */
    public void setIdlJobHrs(double idlJobHrs)
    {
        this.idlJobHrs = idlJobHrs;
    }
    /**
     * @return Returns the idlManHrs.
     */
    public double getIdlManHrs()
    {
        return idlManHrs;
    }
    /**
     * @param idlManHrs The idlManHrs to set.
     */
    public void setIdlManHrs(double idlManHrs)
    {
        this.idlManHrs = idlManHrs;
    }
    /**
     * @return Returns the idlOtherHrs.
     */
    public double getIdlOtherHrs()
    {
        return idlOtherHrs;
    }
    /**
     * @param idlOtherHrs The idlOtherHrs to set.
     */
    public void setIdlOtherHrs(double idlOtherHrs)
    {
        this.idlOtherHrs = idlOtherHrs;
    }
    /**
     * @return Returns the idlPccHrs.
     */
    public double getIdlPccHrs()
    {
        return idlPccHrs;
    }
    /**
     * @param idlPccHrs The idlPccHrs to set.
     */
    public void setIdlPccHrs(double idlPccHrs)
    {
        this.idlPccHrs = idlPccHrs;
    }
    /**
     * @return Returns the idlToolsHrs.
     */
    public double getIdlToolsHrs()
    {
        return idlToolsHrs;
    }
    /**
     * @param idlToolsHrs The idlToolsHrs to set.
     */
    public void setIdlToolsHrs(double idlToolsHrs)
    {
        this.idlToolsHrs = idlToolsHrs;
    }
    /**
     * @return Returns the mcAvailHrs.
     */
    public double getMcAvailHrs()
    {
        return mcAvailHrs;
    }
    /**
     * @param mcAvailHrs The mcAvailHrs to set.
     */
    public void setMcAvailHrs(double mcAvailHrs)
    {
        this.mcAvailHrs = mcAvailHrs;
    }
    /**
     * @return Returns the mcTotHrs.
     */
    public double getMcTotHrs()
    {
        return mcTotHrs;
    }
    /**
     * @param mcTotHrs The mcTotHrs to set.
     */
    public void setMcTotHrs(double mcTotHrs)
    {
        this.mcTotHrs = mcTotHrs;
    }
    /**
     * @return Returns the perOfProd.
     */
    public String getPerOfProd()
    {
        return perOfProd;
    }
    /**
     * @param perOfProd The perOfProd to set.
     */
    public void setPerOfProd(String perOfProd)
    {
        this.perOfProd = perOfProd;
    }
    /**
     * @return Returns the totIdlHrs.
     */
    public double getTotIdlHrs()
    {
        return totIdlHrs;
    }
    /**
     * @param totIdlHrs The totIdlHrs to set.
     */
    public void setTotIdlHrs(double totIdlHrs)
    {
        this.totIdlHrs = totIdlHrs;
    }
    /**
     * @return Returns the totProdHrs.
     */
    public double getTotProdHrs()
    {
        return totProdHrs;
    }
    /**
     * @param totProdHrs The totProdHrs to set.
     */
    public void setTotProdHrs(double totProdHrs)
    {
        this.totProdHrs = totProdHrs;
    }
    
    /**
     * @return Returns the yearString.
     */
    public String getYearString()
    {
        return yearString;
    }
    /**
     * @param yearString The yearString to set.
     */
    public void setYearString(String yearString)
    {
        this.yearString = yearString;
    }
    /**
     * @return Returns the prodAccountedHrs.
     */
    public double getProdAccountedHrs()
    {
        return prodAccountedHrs;
    }
    /**
     * @param prodAccountedHrs The prodAccountedHrs to set.
     */
    public void setProdAccountedHrs(double prodAccountedHrs)
    {
        this.prodAccountedHrs = prodAccountedHrs;
    }
}

/*
*$Log: HalfYearlyPercentageDetails.java,v $
*Revision 1.3  2005/09/24 10:41:42  kduraisamy
*field prodAccountedHrs added.
*
*Revision 1.2  2005/02/25 11:23:35  kduraisamy
*initial commit.
*
*Revision 1.1  2005/02/22 06:14:07  kduraisamy
*initial commit.
*
*
*/