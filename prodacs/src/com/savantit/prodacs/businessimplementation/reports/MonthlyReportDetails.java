/*
 * Created on Feb 26, 2005
 *
 * ClassName	:  	MonthlyReportDetails.java
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
public class MonthlyReportDetails implements Serializable
{
    private String empName;
    private DtyHrsDetails objDtyHrsDetails;
    private OtHrsDetails objOtHrsDetails;
    
    public MonthlyReportDetails()
    {
        empName = "";
        objDtyHrsDetails = new DtyHrsDetails();
        objOtHrsDetails = new OtHrsDetails();
    }
    
    /**
     * @return Returns the empName.
     */
    public String getEmpName()
    {
        return empName;
    }
    /**
     * @param empName The empName to set.
     */
    public void setEmpName(String empName)
    {
        this.empName = empName;
    }
    /**
     * @return Returns the objDtyHrsDetails.
     */
    public DtyHrsDetails getObjDtyHrsDetails()
    {
        return objDtyHrsDetails;
    }
    /**
     * @param objDtyHrsDetails The objDtyHrsDetails to set.
     */
    public void setObjDtyHrsDetails(DtyHrsDetails objDtyHrsDetails)
    {
        this.objDtyHrsDetails = objDtyHrsDetails;
    }
    /**
     * @return Returns the objOtHrsDetails.
     */
    public OtHrsDetails getObjOtHrsDetails()
    {
        return objOtHrsDetails;
    }
    /**
     * @param objOtHrsDetails The objOtHrsDetails to set.
     */
    public void setObjOtHrsDetails(OtHrsDetails objOtHrsDetails)
    {
        this.objOtHrsDetails = objOtHrsDetails;
    }
}

/*
*$Log: MonthlyReportDetails.java,v $
*Revision 1.1  2005/02/27 06:50:16  kduraisamy
*intitial commit.
*
*
*/