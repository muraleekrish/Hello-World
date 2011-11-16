/*
 * Created on May 19, 2005
 *
 * ClassName	:  	ProductionAccountingDetails.java
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
public class ProductionAccountingDateDetails implements Serializable
{
    private String prodDate;
    private Vector vecProductionAccountingMachineDetails;
    public ProductionAccountingDateDetails()
    {
        this.prodDate = "";
        this.vecProductionAccountingMachineDetails = new Vector();
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
     * @return Returns the vecProductionAccountingMachineDetails.
     */
    public Vector getVecProductionAccountingMachineDetails()
    {
        return vecProductionAccountingMachineDetails;
    }
    /**
     * @param vecProductionAccountingMachineDetails The vecProductionAccountingMachineDetails to set.
     */
    public void setVecProductionAccntngMachineDetails(Vector vecProductionAccountingMachineDetails)
    {
        this.vecProductionAccountingMachineDetails = vecProductionAccountingMachineDetails;
    }
}

/*
*$Log: ProductionAccountingDateDetails.java,v $
*Revision 1.2  2005/06/02 09:38:24  kduraisamy
*vector initialized.
*
*Revision 1.1  2005/05/31 06:40:35  kduraisamy
*initial commit.
*
*Revision 1.3  2005/05/20 04:53:58  vkrishnamoorthy
*field date is changed to text.
*
*Revision 1.2  2005/05/19 15:02:27  vkrishnamoorthy
*Date is changed as text.
*
*Revision 1.1  2005/05/19 09:38:01  kduraisamy
*posting rule based on previous day corrected.
*
*
*/