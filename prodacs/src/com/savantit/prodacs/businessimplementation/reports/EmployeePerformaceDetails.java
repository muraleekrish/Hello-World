/*
 * Created on May 14, 2005
 *
 * ClassName	:  	EmployeePerformaceDetails.java
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
import java.util.Date;

/**
 * @author kduraisamy
 *
 */
public class EmployeePerformaceDetails implements Serializable
{
    private Date prodCrntDate;
    private String mcCode;
    private String shiftName;
    private String woNo;
    private String details;
    private String startOpn;
    private String endOpn;
    private float dtyHrs;
    private float otHrs;
    private float stdHrs;
    private float opnHrs;
    private float incntvSalHrs;
    private float dtySalHrs;
    private float otSalHrs;
    private float savedHrs;
    private float exceededHrs;
    public EmployeePerformaceDetails()
    {
        this.prodCrntDate = null;
        this.mcCode = "";
        this.shiftName = "";
        this.woNo = "";
        this.details = "";
        this.startOpn = "";
        this.endOpn = "";
        this.dtyHrs = 0;
        this.otHrs = 0;
        this.stdHrs = 0;
        this.opnHrs = 0;
        this.incntvSalHrs = 0;
        this.dtySalHrs = 0;
        this.otSalHrs = 0;
        this.savedHrs = 0;
        this.exceededHrs = 0;
      }
    
    /**
     * @return Returns the details.
     */
    public String getDetails()
    {
        return details;
    }
    /**
     * @param details The details to set.
     */
    public void setDetails(String details)
    {
        this.details = details;
    }
    /**
     * @return Returns the dtyHrs.
     */
    public float getDtyHrs()
    {
        return dtyHrs;
    }
    /**
     * @param dtyHrs The dtyHrs to set.
     */
    public void setDtyHrs(float dtyHrs)
    {
        this.dtyHrs = dtyHrs;
    }
    /**
     * @return Returns the dtySalHrs.
     */
    public float getDtySalHrs()
    {
        return dtySalHrs;
    }
    /**
     * @param dtySalHrs The dtySalHrs to set.
     */
    public void setDtySalHrs(float dtySalHrs)
    {
        this.dtySalHrs = dtySalHrs;
    }
    /**
     * @return Returns the endOpn.
     */
    public String getEndOpn()
    {
        return endOpn;
    }
    /**
     * @param endOpn The endOpn to set.
     */
    public void setEndOpn(String endOpn)
    {
        this.endOpn = endOpn;
    }
    /**
     * @return Returns the incntvSalHrs.
     */
    public float getIncntvSalHrs()
    {
        return incntvSalHrs;
    }
    /**
     * @param incntvSalHrs The incntvSalHrs to set.
     */
    public void setIncntvSalHrs(float incntvSalHrs)
    {
        this.incntvSalHrs = incntvSalHrs;
    }
    /**
     * @return Returns the mcCode.
     */
    public String getMcCode()
    {
        return mcCode;
    }
    /**
     * @param mcCode The mcCode to set.
     */
    public void setMcCode(String mcCode)
    {
        this.mcCode = mcCode;
    }
    /**
     * @return Returns the opnHrs.
     */
    public float getOpnHrs()
    {
        return opnHrs;
    }
    /**
     * @param opnHrs The opnHrs to set.
     */
    public void setOpnHrs(float opnHrs)
    {
        this.opnHrs = opnHrs;
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
        return otSalHrs;
    }
    /**
     * @param otSalHrs The otSalHrs to set.
     */
    public void setOtSalHrs(float otSalHrs)
    {
        this.otSalHrs = otSalHrs;
    }
    /**
     * @return Returns the prodCrntDate.
     */
    public Date getProdCrntDate()
    {
        return prodCrntDate;
    }
    /**
     * @param prodCrntDate The prodCrntDate to set.
     */
    public void setProdCrntDate(Date prodCrntDate)
    {
        this.prodCrntDate = prodCrntDate;
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
     * @return Returns the startOpn.
     */
    public String getStartOpn()
    {
        return startOpn;
    }
    /**
     * @param startOpn The startOpn to set.
     */
    public void setStartOpn(String startOpn)
    {
        this.startOpn = startOpn;
    }
    /**
     * @return Returns the stdHrs.
     */
    public float getStdHrs()
    {
        return stdHrs;
    }
    /**
     * @param stdHrs The stdHrs to set.
     */
    public void setStdHrs(float stdHrs)
    {
        this.stdHrs = stdHrs;
    }
    /**
     * @return Returns the woNo.
     */
    public String getWoNo()
    {
        return woNo;
    }
    /**
     * @param woNo The woNo to set.
     */
    public void setWoNo(String woNo)
    {
        this.woNo = woNo;
    }
    /**
     * @return Returns the exceededHrs.
     */
    public float getExceededHrs()
    {
        return exceededHrs;
    }
    /**
     * @param exceededHrs The exceededHrs to set.
     */
    public void setExceededHrs(float exceededHrs)
    {
        this.exceededHrs = exceededHrs;
    }
    /**
     * @return Returns the savedHrs.
     */
    public float getSavedHrs()
    {
        return savedHrs;
    }
    /**
     * @param savedHrs The savedHrs to set.
     */
    public void setSavedHrs(float savedHrs)
    {
        this.savedHrs = savedHrs;
    }
}

/*
*$Log: EmployeePerformaceDetails.java,v $
*Revision 1.2  2005/10/10 09:58:30  kduraisamy
*EmployeePerformance report changed.
*
*Revision 1.1  2005/05/14 10:16:02  kduraisamy
*initial commit.
*
*
*/