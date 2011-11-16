/*
 * Created on Jun 11, 2005
 *
 * ClassName	:  	PayrollDetails.java
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
public class PayrollDetails implements Serializable
{
    private String prodDate;
    private String shiftName;
    private int shiftId;
    private float dutyHrs;
    private float otHrs;
    private float RglrSalHrs;
    private float OtSalHrs;
    private float IncentiveSalHrs;
    private int version;
    private String createdBy;
    public PayrollDetails()
    {
        prodDate= "";
        shiftName= "";
        shiftId= 0;
        dutyHrs= 0;
        otHrs= 0;
        RglrSalHrs= 0;
        OtSalHrs= 0;
        IncentiveSalHrs= 0;
        version= 0;
        createdBy= "";
        
    }
    
    
    
    /**
     * @return Returns the createdBy.
     */
    public String getCreatedBy()
    {
        return createdBy;
    }
    /**
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    /**
     * @return Returns the dutyHrs.
     */
    public float getDutyHrs()
    {
        return dutyHrs;
    }
    /**
     * @param dutyHrs The dutyHrs to set.
     */
    public void setDutyHrs(float dutyHrs)
    {
        this.dutyHrs = dutyHrs;
    }
    /**
     * @return Returns the incentiveSalHrs.
     */
    public float getIncentiveSalHrs()
    {
        return IncentiveSalHrs;
    }
    /**
     * @param incentiveSalHrs The incentiveSalHrs to set.
     */
    public void setIncentiveSalHrs(float incentiveSalHrs)
    {
        IncentiveSalHrs = incentiveSalHrs;
    }
    /**
     * @return Returns the otHrs.
     */
    public float getOtHrs()
    {
        return otHrs;
    }
    /**
     * @param otHrs The otHrs to set.
     */
    public void setOtHrs(float otHrs)
    {
        this.otHrs = otHrs;
    }
    /**
     * @return Returns the otSalHrs.
     */
    public float getOtSalHrs()
    {
        return OtSalHrs;
    }
    /**
     * @param otSalHrs The otSalHrs to set.
     */
    public void setOtSalHrs(float otSalHrs)
    {
        OtSalHrs = otSalHrs;
    }
    /**
     * @return Returns the prodDate.
     */
    public String getProdDate()
    {
        return prodDate;
    }
    /**
     * @param prodDate The prodDate to set.
     */
    public void setProdDate(String prodDate)
    {
        this.prodDate = prodDate;
    }
    /**
     * @return Returns the rglrSalHrs.
     */
    public float getRglrSalHrs()
    {
        return RglrSalHrs;
    }
    /**
     * @param rglrSalHrs The rglrSalHrs to set.
     */
    public void setRglrSalHrs(float rglrSalHrs)
    {
        RglrSalHrs = rglrSalHrs;
    }
    /**
     * @return Returns the shiftId.
     */
    public int getShiftId()
    {
        return shiftId;
    }
    /**
     * @param shiftId The shiftId to set.
     */
    public void setShiftId(int shiftId)
    {
        this.shiftId = shiftId;
    }
    /**
     * @return Returns the shiftName.
     */
    public String getShiftName()
    {
        return shiftName;
    }
    /**
     * @param shiftName The shiftName to set.
     */
    public void setShiftName(String shiftName)
    {
        this.shiftName = shiftName;
    }
    /**
     * @return Returns the version.
     */
    public int getVersion()
    {
        return version;
    }
    /**
     * @param version The version to set.
     */
    public void setVersion(int version)
    {
        this.version = version;
    }
}

/*
*$Log: PayrollDetails.java,v $
*Revision 1.1  2005/06/11 08:08:59  kduraisamy
*initial commit.
*
*
*/