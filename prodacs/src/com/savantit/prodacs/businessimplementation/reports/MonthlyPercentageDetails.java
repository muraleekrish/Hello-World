/*
 * Created on Feb 19, 2005
 *
 * ClassName	:  	MonthlyPercentageDetails.java
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
public class MonthlyPercentageDetails implements Serializable
{
    private double mcTotHrs;
	private double mcAvailHrs;

	private double brkDwnMechHrs;
	private double brkDwnElecHrs;
	private double totBrkDwnHrs;
	private String brkDwnMechPer;
	private String brkDwnElecPer;
	private String TotBrkDwnPer;
	
	private double idlInstHrs;
	private double idlManHrs;
	private double idlJobHrs;
	private double idlDnoHrs;
	private double idlPccHrs;
	private double idlToolsHrs;
	private double idlGaugeHrs;
	private double idlOtherHrs;
	private double TotIdleHrs;

	private String idlInstPer;
	private String idlManPer;
	private String idlJobPer;
	private String idlDnoPer;
	private String idlPccPer;
	private String idlToolsPer;
	private String idlGaugePer;
	private String idlOtherPer;
	private String totIdlPer;

	private double prodAccountedHrs;
	private double effProdHrs;
	private String perofProd;

	public MonthlyPercentageDetails()
	{
	     this.mcTotHrs = 0;
	     this.mcAvailHrs = 0;

	     this.brkDwnMechHrs = 0;
	     this.brkDwnElecHrs = 0;
	     this.totBrkDwnHrs = 0;
	     this.brkDwnMechPer = "";
	     this.brkDwnElecPer = "";
	     this.TotBrkDwnPer = "";
		
	     this.idlInstHrs = 0;
	     this.idlManHrs = 0;
	     this.idlJobHrs = 0;
	     this.idlDnoHrs = 0;
	     this.idlPccHrs = 0;
	     this.idlToolsHrs = 0;
	     this.idlGaugeHrs = 0;
	     this.idlOtherHrs = 0;
	     this.TotIdleHrs = 0;
	     
	     this.idlInstPer = "";
	     this.idlManPer = "";
	     this.idlJobPer = "";
	     this.idlDnoPer = "";
	     this.idlPccPer = "";
	     this.idlToolsPer = "";
	     this.idlGaugePer = "";
	     this.idlOtherPer = "";
	     this.totIdlPer = "";
	     
	     this.prodAccountedHrs = 0;
	     this.effProdHrs = 0;
	     this.perofProd = "";
		 
	}
    
    /**
     * @return Returns the brkDwnElecHrs.
     */
    public double getBrkDwnElecHrs()
    {
        return brkDwnElecHrs;
    }
    /**
     * @param brkDwnElecHrs The brkDwnElecHrs to set.
     */
    public void setBrkDwnElecHrs(double brkDwnElecHrs)
    {
        this.brkDwnElecHrs = brkDwnElecHrs;
    }
    /**
     * @return Returns the brkDwnElecPer.
     */
    public String getBrkDwnElecPer()
    {
        return brkDwnElecPer;
    }
    /**
     * @param brkDwnElecPer The brkDwnElecPer to set.
     */
    public void setBrkDwnElecPer(String brkDwnElecPer)
    {
        this.brkDwnElecPer = brkDwnElecPer;
    }
    /**
     * @return Returns the brkDwnMechHrs.
     */
    public double getBrkDwnMechHrs()
    {
        return brkDwnMechHrs;
    }
    /**
     * @param brkDwnMechHrs The brkDwnMechHrs to set.
     */
    public void setBrkDwnMechHrs(double brkDwnMechHrs)
    {
        this.brkDwnMechHrs = brkDwnMechHrs;
    }
    /**
     * @return Returns the brkDwnMechPer.
     */
    public String getBrkDwnMechPer()
    {
        return brkDwnMechPer;
    }
    /**
     * @param brkDwnMechPer The brkDwnMechPer to set.
     */
    public void setBrkDwnMechPer(String brkDwnMechPer)
    {
        this.brkDwnMechPer = brkDwnMechPer;
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
     * @return Returns the idlDnoPer.
     */
    public String getIdlDnoPer()
    {
        return idlDnoPer;
    }
    /**
     * @param idlDnoPer The idlDnoPer to set.
     */
    public void setIdlDnoPer(String idlDnoPer)
    {
        this.idlDnoPer = idlDnoPer;
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
     * @return Returns the idlGaugePer.
     */
    public String getIdlGaugePer()
    {
        return idlGaugePer;
    }
    /**
     * @param idlGaugePer The idlGaugePer to set.
     */
    public void setIdlGaugePer(String idlGaugePer)
    {
        this.idlGaugePer = idlGaugePer;
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
     * @return Returns the idlInstPer.
     */
    public String getIdlInstPer()
    {
        return idlInstPer;
    }
    /**
     * @param idlInstPer The idlInstPer to set.
     */
    public void setIdlInstPer(String idlInstPer)
    {
        this.idlInstPer = idlInstPer;
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
     * @return Returns the idlJobPer.
     */
    public String getIdlJobPer()
    {
        return idlJobPer;
    }
    /**
     * @param idlJobPer The idlJobPer to set.
     */
    public void setIdlJobPer(String idlJobPer)
    {
        this.idlJobPer = idlJobPer;
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
     * @return Returns the idlManPer.
     */
    public String getIdlManPer()
    {
        return idlManPer;
    }
    /**
     * @param idlManPer The idlManPer to set.
     */
    public void setIdlManPer(String idlManPer)
    {
        this.idlManPer = idlManPer;
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
     * @return Returns the idlOtherPer.
     */
    public String getIdlOtherPer()
    {
        return idlOtherPer;
    }
    /**
     * @param idlOtherPer The idlOtherPer to set.
     */
    public void setIdlOtherPer(String idlOtherPer)
    {
        this.idlOtherPer = idlOtherPer;
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
     * @return Returns the idlPccPer.
     */
    public String getIdlPccPer()
    {
        return idlPccPer;
    }
    /**
     * @param idlPccPer The idlPccPer to set.
     */
    public void setIdlPccPer(String idlPccPer)
    {
        this.idlPccPer = idlPccPer;
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
     * @return Returns the idlToolsPer.
     */
    public String getIdlToolsPer()
    {
        return idlToolsPer;
    }
    /**
     * @param idlToolsPer The idlToolsPer to set.
     */
    public void setIdlToolsPer(String idlToolsPer)
    {
        this.idlToolsPer = idlToolsPer;
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
     * @return Returns the perofProd.
     */
    public String getPerofProd()
    {
        return perofProd;
    }
    /**
     * @param perofProd The perofProd to set.
     */
    public void setPerofProd(String perofProd)
    {
        this.perofProd = perofProd;
    }
    /**
     * @return Returns the totBrkDwnHrs.
     */
    public double getTotBrkDwnHrs()
    {
        return totBrkDwnHrs;
    }
    /**
     * @param totBrkDwnHrs The totBrkDwnHrs to set.
     */
    public void setTotBrkDwnHrs(double totBrkDwnHrs)
    {
        this.totBrkDwnHrs = totBrkDwnHrs;
    }
    /**
     * @return Returns the totBrkDwnPer.
     */
    public String getTotBrkDwnPer()
    {
        return TotBrkDwnPer;
    }
    /**
     * @param totBrkDwnPer The totBrkDwnPer to set.
     */
    public void setTotBrkDwnPer(String totBrkDwnPer)
    {
        TotBrkDwnPer = totBrkDwnPer;
    }
    /**
     * @return Returns the totIdleHrs.
     */
    public double getTotIdleHrs()
    {
        return TotIdleHrs;
    }
    /**
     * @param totIdleHrs The totIdleHrs to set.
     */
    public void setTotIdleHrs(double totIdleHrs)
    {
        TotIdleHrs = totIdleHrs;
    }
    /**
     * @return Returns the totIdlPer.
     */
    public String getTotIdlPer()
    {
        return totIdlPer;
    }
    /**
     * @param totIdlPer The totIdlPer to set.
     */
    public void setTotIdlPer(String totIdlPer)
    {
        this.totIdlPer = totIdlPer;
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
*$Log: MonthlyPercentageDetails.java,v $
*Revision 1.2  2005/09/24 09:26:01  kduraisamy
*field prodAccountedHrs added.
*
*Revision 1.1  2005/02/22 06:14:23  kduraisamy
*initial commit.
*
*
*/