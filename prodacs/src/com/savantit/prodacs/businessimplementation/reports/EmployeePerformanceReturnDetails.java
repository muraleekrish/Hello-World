/*
 * Created on Oct 11, 2005
 *
 * ClassName	:  	EmployeePerformanceReturnDetails.java
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
public class EmployeePerformanceReturnDetails implements Serializable
{
    private Vector vecEmployeePerformanceDetails;
    private Vector vecEmployeePerformanceTotDetails;
    public EmployeePerformanceReturnDetails()
    {
        vecEmployeePerformanceDetails = new Vector();
        vecEmployeePerformanceTotDetails = new Vector();
    }
    /**
     * @return Returns the vecEmployeePerformanceDetails.
     */
    public Vector getVecEmployeePerformanceDetails()
    {
        return vecEmployeePerformanceDetails;
    }
    /**
     * @param vecEmployeePerformanceDetails The vecEmployeePerformanceDetails to set.
     */
    public void setVecEmployeePerformanceDetails(Vector vecEmployeePerformanceDetails)
    {
        this.vecEmployeePerformanceDetails = vecEmployeePerformanceDetails;
    }
    /**
     * @return Returns the vecEmployeePerformanceTotDetails.
     */
    public Vector getVecEmployeePerformanceTotDetails()
    {
        return vecEmployeePerformanceTotDetails;
    }
    /**
     * @param vecEmployeePerformanceTotDetails The vecEmployeePerformanceTotDetails to set.
     */
    public void setVecEmployeePerformanceTotDetails(Vector vecEmployeePerformanceTotDetails)
    {
        this.vecEmployeePerformanceTotDetails = vecEmployeePerformanceTotDetails;
    }
}

/*
*$Log: EmployeePerformanceReturnDetails.java,v $
*Revision 1.1  2005/10/11 06:32:07  kduraisamy
*EmployeePerformance report changed.
*
*
*/