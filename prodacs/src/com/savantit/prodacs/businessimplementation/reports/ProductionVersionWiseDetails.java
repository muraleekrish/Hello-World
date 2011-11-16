/*
 * Created on Jun 2, 2005
 *
 * ClassName	:  	ProductionVersionWiseDetails.java
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

import com.savantit.prodacs.businessimplementation.production.ProductionDetails;

/**
 * @author kduraisamy
 *
 */
public class ProductionVersionWiseDetails implements Serializable
{
    private int versionCount;
    private Vector vecProductionDetails;
    private ProductionDetails[] arrProductionDetails;
    public ProductionVersionWiseDetails()
    {
        versionCount = 0;
        vecProductionDetails = null;
        arrProductionDetails = null;
    }

    /**
     * @return Returns the arrProductionDetails.
     */
    public ProductionDetails[] getArrProductionDetails()
    {
        return arrProductionDetails;
    }
    /**
     * @param arrProductionDetails The arrProductionDetails to set.
     */
    public void setArrProductionDetails(ProductionDetails[] arrProductionDetails)
    {
        this.arrProductionDetails = arrProductionDetails;
    }
    /**
     * @return Returns the vecProductionDetails.
     */
    public Vector getVecProductionDetails()
    {
        return vecProductionDetails;
    }
    /**
     * @param vecProductionDetails The vecProductionDetails to set.
     */
    public void setVecProductionDetails(Vector vecProductionDetails)
    {
        this.vecProductionDetails = vecProductionDetails;
    }
    /**
     * @return Returns the versionCount.
     */
    public int getVersionCount()
    {
        return versionCount;
    }
    /**
     * @param versionCount The versionCount to set.
     */
    public void setVersionCount(int versionCount)
    {
        this.versionCount = versionCount;
    }
}

/*
*$Log: ProductionVersionWiseDetails.java,v $
*Revision 1.1  2005/06/02 09:56:51  kduraisamy
*initial commit.
*
*
*/