/*
 * Created on Oct 10, 2005
 *
 * ClassName	:  	EmployeePerformanceTotDetails.java
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
public class EmployeePerformanceTotDetails implements Serializable
{
    private double totOpnHrs;
    private double totStdHrs;
    private double totSavedHrs;
    private double totExceededHrs;
    private String savedHrsPercentage;
    private String exceededHrsPercentage;
    private String netHrsSavedPercentage;
    private String empName;
    public EmployeePerformanceTotDetails()
    {
        this.totOpnHrs = 0;
        this.totStdHrs = 0;
        this.totSavedHrs = 0;
        this.totExceededHrs = 0;
        this.savedHrsPercentage = "";
        this.exceededHrsPercentage = "";
        this.netHrsSavedPercentage = "";
        this.empName = "";
    }
    
    /**
     * @return Returns the exceededHrsPercentage.
     */
    public String getExceededHrsPercentage()
    {
        return exceededHrsPercentage;
    }
    /**
     * @param exceededHrsPercentage The exceededHrsPercentage to set.
     */
    public void setExceededHrsPercentage(String exceededHrsPercentage)
    {
        this.exceededHrsPercentage = exceededHrsPercentage;
    }
    /**
     * @return Returns the savedHrsPercentage.
     */
    public String getSavedHrsPercentage()
    {
        return savedHrsPercentage;
    }
    /**
     * @param savedHrsPercentage The savedHrsPercentage to set.
     */
    public void setSavedHrsPercentage(String savedHrsPercentage)
    {
        this.savedHrsPercentage = savedHrsPercentage;
    }
    /**
     * @return Returns the totExceededHrs.
     */
    public double getTotExceededHrs()
    {
        return totExceededHrs;
    }
    /**
     * @param totExceededHrs The totExceededHrs to set.
     */
    public void setTotExceededHrs(double totExceededHrs)
    {
        this.totExceededHrs = totExceededHrs;
    }
    /**
     * @return Returns the totOpnHrs.
     */
    public double getTotOpnHrs()
    {
        return totOpnHrs;
    }
    /**
     * @param totOpnHrs The totOpnHrs to set.
     */
    public void setTotOpnHrs(double totOpnHrs)
    {
        this.totOpnHrs = totOpnHrs;
    }
    /**
     * @return Returns the totSavedHrs.
     */
    public double getTotSavedHrs()
    {
        return totSavedHrs;
    }
    /**
     * @param totSavedHrs The totSavedHrs to set.
     */
    public void setTotSavedHrs(double totSavedHrs)
    {
        this.totSavedHrs = totSavedHrs;
    }
    /**
     * @return Returns the totStdHrs.
     */
    public double getTotStdHrs()
    {
        return totStdHrs;
    }
    /**
     * @param totStdHrs The totStdHrs to set.
     */
    public void setTotStdHrs(double totStdHrs)
    {
        this.totStdHrs = totStdHrs;
    }
    /**
     * @return Returns the netHrsSavedPercentage.
     */
    public String getNetHrsSavedPercentage()
    {
        return netHrsSavedPercentage;
    }
    /**
     * @param netHrsSavedPercentage The netHrsSavedPercentage to set.
     */
    public void setNetHrsSavedPercentage(String netHrsSavedPercentage)
    {
        this.netHrsSavedPercentage = netHrsSavedPercentage;
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
}

/*
*$Log: EmployeePerformanceTotDetails.java,v $
*Revision 1.4  2005/12/26 13:25:32  kduraisamy
*empName field added.
*
*Revision 1.3  2005/10/26 12:00:26  kduraisamy
*netSavedHrs included with percentage in employeePerformance reports.
*
*Revision 1.2  2005/10/11 06:34:20  kduraisamy
*EmployeePerformance report changed.
*
*Revision 1.1  2005/10/10 09:58:30  kduraisamy
*EmployeePerformance report changed.
*
*
*/