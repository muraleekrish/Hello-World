/*
 * Created on Jun 2, 2005
 *
 * ClassName	:  	ProductionDateWiseDetails.java
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
import java.util.Vector;

/**
 * @author kduraisamy
 *
 */
public class ProductionDateWiseDetails implements Serializable
{
    private Date prodDate;
    private Vector vecProductionVersionWiseDetails;
    private ProductionVersionWiseDetails[] arrProductionVersionWiseDetails;

    public ProductionDateWiseDetails()
    {
        prodDate = null;
        vecProductionVersionWiseDetails = null;
        arrProductionVersionWiseDetails = null;
    }
    
    
    
    /**
     * @return Returns the prodDate.
     */
    public Date getProdDate()
    {
        return prodDate;
    }
    /**
     * @param prodDate The prodDate to set.
     */
    public void setProdDate(Date prodDate)
    {
        this.prodDate = prodDate;
    }
    /**
     * @return Returns the vecProductionVersionWiseDetails.
     */
    public Vector getVecProductionVersionWiseDetails()
    {
        return vecProductionVersionWiseDetails;
    }
    /**
     * @param vecProductionVersionWiseDetails The vecProductionVersionWiseDetails to set.
     */
    public void setVecProductionVersionWiseDetails(Vector vecProductionVersionWiseDetails)
    {
        this.vecProductionVersionWiseDetails = vecProductionVersionWiseDetails;
    }
    /**
     * @return Returns the arrProductionVersionWiseDetails.
     */
    public ProductionVersionWiseDetails[] getArrProductionVersionWiseDetails()
    {
        return arrProductionVersionWiseDetails;
    }
    /**
     * @param arrProductionVersionWiseDetails The arrProductionVersionWiseDetails to set.
     */
    public void setArrProductionVersionWiseDetails(ProductionVersionWiseDetails[] arrProductionVersionWiseDetails)
    {
        this.arrProductionVersionWiseDetails = arrProductionVersionWiseDetails;
    }
}

/*
*$Log: ProductionDateWiseDetails.java,v $
*Revision 1.1  2005/06/02 09:56:51  kduraisamy
*initial commit.
*
*
*/