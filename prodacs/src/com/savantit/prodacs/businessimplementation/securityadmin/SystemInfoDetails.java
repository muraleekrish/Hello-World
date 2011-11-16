/*
 * Created on Dec 7, 2005
 *
 * ClassName	:  	SystemInfoDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.securityadmin;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 */
public class SystemInfoDetails implements Serializable
{
    private String cpuId;
    private String macAddr;
    private String driveSno;
    private String userName;
    private String systDetails;
    public SystemInfoDetails()
    {
        this.cpuId = "";
        this.macAddr = "";
        this.driveSno = "";
        this.userName = "";
        this.systDetails = "";
    }
    /**
     * @return Returns the cpuId.
     */
    public String getCpuId()
    {
        return cpuId;
    }
    /**
     * @param cpuId The cpuId to set.
     */
    public void setCpuId(String cpuId)
    {
        this.cpuId = cpuId;
    }
    /**
     * @return Returns the driveSno.
     */
    public String getDriveSno()
    {
        return driveSno;
    }
    /**
     * @param driveSno The driveSno to set.
     */
    public void setDriveSno(String driveSno)
    {
        this.driveSno = driveSno;
    }
    /**
     * @return Returns the macAddr.
     */
    public String getMacAddr()
    {
        return macAddr;
    }
    /**
     * @param macAddr The macAddr to set.
     */
    public void setMacAddr(String macAddr)
    {
        this.macAddr = macAddr;
    }
    /**
     * @return Returns the systDetails.
     */
    public String getSystDetails()
    {
        return systDetails;
    }
    /**
     * @param systDetails The systDetails to set.
     */
    public void setSystDetails(String systDetails)
    {
        this.systDetails = systDetails;
    }
    /**
     * @return Returns the userName.
     */
    public String getUserName()
    {
        return userName;
    }
    /**
     * @param userName The userName to set.
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}

/*
*$Log: SystemInfoDetails.java,v $
*Revision 1.2  2005/12/15 07:08:44  kduraisamy
*SCHEDULER TABLES ADDED.
*
*Revision 1.1  2005/12/07 05:55:24  kduraisamy
*Super user security added.
*
*
*/