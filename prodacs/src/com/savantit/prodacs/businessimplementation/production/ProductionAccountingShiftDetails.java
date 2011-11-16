/*
 * Created on May 31, 2005
 *
 * ClassName	:  	ProductionAccountingShiftDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.production;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 */
public class ProductionAccountingShiftDetails  implements Serializable
{
    private int shiftId;
    private String shiftName;
    private float prodHrs;
    private float nprodHrs;
    private float availHrs;
    public ProductionAccountingShiftDetails()
    {
        shiftId = 0;
        shiftName = "";
        prodHrs = 0;
        nprodHrs = 0;
        availHrs = 0;
    }
    

    /**
     * @return Returns the availHrs.
     */
    public float getAvailHrs()
    {
        return availHrs;
    }
    /**
     * @param availHrs The availHrs to set.
     */
    public void setAvailHrs(float availHrs)
    {
        this.availHrs = availHrs;
    }
    /**
     * @return Returns the nprodHrs.
     */
    public float getNprodHrs()
    {
        return nprodHrs;
    }
    /**
     * @param nprodHrs The nprodHrs to set.
     */
    public void setNprodHrs(float nprodHrs)
    {
        this.nprodHrs = nprodHrs;
    }
    /**
     * @return Returns the prodHrs.
     */
    public float getProdHrs()
    {
        return prodHrs;
    }
    /**
     * @param prodHrs The prodHrs to set.
     */
    public void setProdHrs(float prodHrs)
    {
        this.prodHrs = prodHrs;
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
}

/*
*$Log: ProductionAccountingShiftDetails.java,v $
*Revision 1.2  2005/05/31 10:19:36  kduraisamy
*SERIALIZABLE ADDED.
*
*Revision 1.1  2005/05/31 06:40:35  kduraisamy
*initial commit.
*
*
*/