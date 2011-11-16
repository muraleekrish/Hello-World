/*
 * Created on Feb 25, 2005
 *
 * ClassName	:  	HalfYearlyProductionReportDetail.java
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
 */
public class HalfYearlyProductionReportDetail implements Serializable
{
    private String serialNumber="";
    private String desription="";
    private String month1="";
    private String month2="";
    private String month3="";
    private String month4="";
    private String month5="";
    private String month6="";
    
    public HalfYearlyProductionReportDetail()
    {
        
    }

    /**
     * @return Returns the desription.
     */
    public String getDesription()
    {
        return desription;
    }
    /**
     * @param desription The desription to set.
     */
    public void setDesription(String desription)
    {
        this.desription = desription;
    }
    /**
     * @return Returns the month1.
     */
    public String getMonth1()
    {
        return month1;
    }
    /**
     * @param month1 The month1 to set.
     */
    public void setMonth1(String month1)
    {
        this.month1 = month1;
    }
    /**
     * @return Returns the month2.
     */
    public String getMonth2()
    {
        return month2;
    }
    /**
     * @param month2 The month2 to set.
     */
    public void setMonth2(String month2)
    {
        this.month2 = month2;
    }
    /**
     * @return Returns the month3.
     */
    public String getMonth3()
    {
        return month3;
    }
    /**
     * @param month3 The month3 to set.
     */
    public void setMonth3(String month3)
    {
        this.month3 = month3;
    }
    /**
     * @return Returns the month4.
     */
    public String getMonth4()
    {
        return month4;
    }
    /**
     * @param month4 The month4 to set.
     */
    public void setMonth4(String month4)
    {
        this.month4 = month4;
    }
    /**
     * @return Returns the month5.
     */
    public String getMonth5()
    {
        return month5;
    }
    /**
     * @param month5 The month5 to set.
     */
    public void setMonth5(String month5)
    {
        this.month5 = month5;
    }
    /**
     * @return Returns the month6.
     */
    public String getMonth6()
    {
        return month6;
    }
    /**
     * @param month6 The month6 to set.
     */
    public void setMonth6(String month6)
    {
        this.month6 = month6;
    }
    /**
     * @return Returns the serialNumber.
     */
    public String getSerialNumber()
    {
        return serialNumber;
    }
    /**
     * @param serialNumber The serialNumber to set.
     */
    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }
}

/*
*$Log: HalfYearlyProductionReportDetail.java,v $
*Revision 1.1  2005/02/25 11:23:35  kduraisamy
*initial commit.
*
*
*/