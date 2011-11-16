/*
 * Created on May 14, 2005
 *
 * ClassName	:  	QuantityProducedDetails.java
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

public class QuantityProducedDetails implements Serializable
{
  private String jbName;
  private String jbDrwgNo;
  private int quantity;
 
  public QuantityProducedDetails()
  {
      jbName = "";
      jbDrwgNo = "";
      quantity = 0;

  }
  

/**
 * @return Returns the jbDrwgNo.
 */
public String getJbDrwgNo()
{
    return jbDrwgNo;
}
/**
 * @param jbDrwgNo The jbDrwgNo to set.
 */
public void setJbDrwgNo(String jbDrwgNo)
{
    this.jbDrwgNo = jbDrwgNo;
}
/**
 * @return Returns the jbName.
 */
public String getJbName()
{
    return jbName;
}
/**
 * @param jbName The jbName to set.
 */
public void setJbName(String jbName)
{
    this.jbName = jbName;
}
/**
 * @return Returns the quantity.
 */
public int getQuantity()
{
    return quantity;
}
/**
 * @param quantity The quantity to set.
 */
public void setQuantity(int quantity)
{
    this.quantity = quantity;
}
}

/*
*$Log: QuantityProducedDetails.java,v $
*Revision 1.1  2005/05/14 11:18:56  kduraisamy
*initial commit.
*
*
*/