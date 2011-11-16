/*
 * Created on Feb 26, 2005
 *
 * ClassName	:  	SalaryReportDetails.java
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
public class SalaryReportDetails implements Serializable
{
    private String empName;
    private SalHrsObjDetails objSalHrsDetails;
    private IncHrsObjDetails objIncHrsDetails;
    private OtHrsObjDetails objOtHrsDetails;
    public SalaryReportDetails()
    {
       empName = ""; 
       objSalHrsDetails = new SalHrsObjDetails();
       objIncHrsDetails = new IncHrsObjDetails();
       objOtHrsDetails = new OtHrsObjDetails();
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
     * @return Returns the objIncHrsDetails.
     */
    public IncHrsObjDetails getObjIncHrsDetails()
    {
        return objIncHrsDetails;
    }
    /**
     * @param objIncHrsDetails The objIncHrsDetails to set.
     */
    public void setObjIncHrsDetails(IncHrsObjDetails objIncHrsDetails)
    {
        this.objIncHrsDetails = objIncHrsDetails;
    }
    /**
     * @return Returns the objOtHrsDetails.
     */
    public OtHrsObjDetails getObjOtHrsDetails()
    {
        return objOtHrsDetails;
    }
    /**
     * @param objOtHrsDetails The objOtHrsDetails to set.
     */
    public void setObjOtHrsDetails(OtHrsObjDetails objOtHrsDetails)
    {
        this.objOtHrsDetails = objOtHrsDetails;
    }
    /**
     * @return Returns the objSalHrsDetails.
     */
    public SalHrsObjDetails getObjSalHrsDetails()
    {
        return objSalHrsDetails;
    }
    /**
     * @param objSalHrsDetails The objSalHrsDetails to set.
     */
    public void setObjSalHrsDetails(SalHrsObjDetails objSalHrsDetails)
    {
        this.objSalHrsDetails = objSalHrsDetails;
    }
}

/*
*$Log: SalaryReportDetails.java,v $
*Revision 1.1  2005/02/26 11:55:27  kduraisamy
*initial commit.
*
*
*/