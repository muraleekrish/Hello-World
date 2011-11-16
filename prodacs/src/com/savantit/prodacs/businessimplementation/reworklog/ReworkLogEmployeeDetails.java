/*
 * Created on Feb 3, 2005
 *
 * ClassName	:  	ReworkLogEmployeeDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.reworklog;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReworkLogEmployeeDetails implements Serializable
{
    private int empId;
    private int empTypId;
    private String empName;
    private String empTypName;
    public ReworkLogEmployeeDetails()
    {
        empId = 0;
        empTypId = 0;
        empName = "";
        empTypName = "";
    }
    
    
    /**
     * @return Returns the empId.
     */
    public int getEmpId()
    {
        return empId;
    }
    /**
     * @param empId The empId to set.
     */
    public void setEmpId(int empId)
    {
        this.empId = empId;
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
     * @return Returns the empTypId.
     */
    public int getEmpTypId()
    {
        return empTypId;
    }
    /**
     * @param empTypId The empTypId to set.
     */
    public void setEmpTypId(int empTypId)
    {
        this.empTypId = empTypId;
    }
    /**
     * @return Returns the empTypName.
     */
    public String getEmpTypName()
    {
        return empTypName;
    }
    /**
     * @param empTypName The empTypName to set.
     */
    public void setEmpTypName(String empTypName)
    {
        this.empTypName = empTypName;
    }
}

/*
*$Log: ReworkLogEmployeeDetails.java,v $
*Revision 1.2  2005/02/05 08:54:29  kduraisamy
*public added in constructor.
*
*Revision 1.1  2005/02/03 14:43:10  kduraisamy
*fields are added for getReworkLogDetails().
*
*
*/