/*
 * Created on Jun 11, 2005
 *
 * ClassName	:  	EmployeePayrollDetails.java
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
import java.util.Vector;

/**
 * @author kduraisamy
 *
 */
public class EmployeePayrollDetails implements Serializable
{
    private int empId;
    private String empName;
    private Vector vecShiftWiseDetails;
    public EmployeePayrollDetails()
    {
        empId = 0;
        empName = "";
        vecShiftWiseDetails = new Vector();
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
     * @return Returns the vecShiftWiseDetails.
     */
    public Vector getVecShiftWiseDetails()
    {
        return vecShiftWiseDetails;
    }
    /**
     * @param vecShiftWiseDetails The vecShiftWiseDetails to set.
     */
    public void setVecShiftWiseDetails(Vector vecShiftWiseDetails)
    {
        this.vecShiftWiseDetails = vecShiftWiseDetails;
    }
}

/*
*$Log: EmployeePayrollDetails.java,v $
*Revision 1.1  2005/06/11 08:08:59  kduraisamy
*initial commit.
*
*
*/