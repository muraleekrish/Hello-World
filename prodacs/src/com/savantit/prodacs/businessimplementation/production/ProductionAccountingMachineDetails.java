/*
 * Created on May 31, 2005
 *
 * ClassName	:  	ProductionAccountingMachineDetails.java
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
import java.util.Vector;

/**
 * @author kduraisamy
 *
 */
public class ProductionAccountingMachineDetails  implements Serializable
{
    private String mcCode;
    private String mcName;
    private Vector vecProductionAccountingShiftDetails;
    private ProductionAccountingShiftDetails[] arrProductionAccountingShiftDetails;
    public ProductionAccountingMachineDetails()
    {
        mcCode = "";
        mcName = "";
        vecProductionAccountingShiftDetails = null;
        arrProductionAccountingShiftDetails = null;
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
     * @return Returns the mcName.
     */
    public String getMcName()
    {
        return mcName;
    }
    /**
     * @param mcName The mcName to set.
     */
    public void setMcName(String mcName)
    {
        this.mcName = mcName;
    }
    /**
     * @return Returns the vecProductionAccountingShiftDetails.
     */
    public Vector getVecProductionAccountingShiftDetails()
    {
        return vecProductionAccountingShiftDetails;
    }
    /**
     * @param vecProductionAccountingShiftDetails The vecProductionAccountingShiftDetails to set.
     */
    public void setVecProductionAccountingShiftDetails(Vector vecProductionAccountingShiftDetails)
    {
        this.vecProductionAccountingShiftDetails = vecProductionAccountingShiftDetails;
    }
    /**
     * @return Returns the arrProductionAccountingShiftDetails.
     */
    public ProductionAccountingShiftDetails[] getArrProductionAccountingShiftDetails()
    {
        return arrProductionAccountingShiftDetails;
    }
    /**
     * @param arrProductionAccountingShiftDetails The arrProductionAccountingShiftDetails to set.
     */
    public void setArrProductionAccountingShiftDetails(ProductionAccountingShiftDetails[] arrProductionAccountingShiftDetails)
    {
        this.arrProductionAccountingShiftDetails = arrProductionAccountingShiftDetails;
    }
}

/*
*$Log: ProductionAccountingMachineDetails.java,v $
*Revision 1.3  2005/05/31 13:10:28  kduraisamy
*field arr added.
*
*Revision 1.2  2005/05/31 10:19:36  kduraisamy
*SERIALIZABLE ADDED.
*
*Revision 1.1  2005/05/31 06:40:35  kduraisamy
*initial commit.
*
*
*/