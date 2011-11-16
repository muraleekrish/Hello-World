/*
 * Created on May 14, 2005
 *
 * ClassName	:  	WoReferenceDetails.java
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
//select WO_DATE,WO_NO,JB_NAME,JB_DRWG_NO,JB_RVSN_NO,JB_MATL_TYP,WOJB_QTY from WO_HEADER WH,WO_JB_HEADER WJH,JB_MSTR JM where WH.WO_ID = WJH.WO_ID and WJH.JB_ID = JM.JB_ID and CUST_ID = $P{customerId} and WO_DATE between $P{startDate} and $P{endDate} order by WO_DATE,WO_NO
public class WoReferenceDetails implements Serializable
{
  private Date woDate;
  private String woNo;
  private String jbName;
  private String jbDrwgNo;
  private String jbRvsnNo;
  private String jbMatlType;
  private int woJbQty;
 
public WoReferenceDetails()
{
    woDate = null;
    woNo = "";
    jbName = "";
    jbDrwgNo = "";
    jbRvsnNo = "";
    jbMatlType = "";
    woJbQty = 0;
 
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
 * @return Returns the jbMatlType.
 */
public String getJbMatlType()
{
    return jbMatlType;
}
/**
 * @param jbMatlType The jbMatlType to set.
 */
public void setJbMatlType(String jbMatlType)
{
    this.jbMatlType = jbMatlType;
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
 * @return Returns the jbRvsnNo.
 */
public String getJbRvsnNo()
{
    return jbRvsnNo;
}
/**
 * @param jbRvsnNo The jbRvsnNo to set.
 */
public void setJbRvsnNo(String jbRvsnNo)
{
    this.jbRvsnNo = jbRvsnNo;
}
/**
 * @return Returns the woDate.
 */
public Date getWoDate()
{
    return woDate;
}
/**
 * @param woDate The woDate to set.
 */
public void setWoDate(Date woDate)
{
    this.woDate = woDate;
}
/**
 * @return Returns the woJbQty.
 */
public int getWoJbQty()
{
    return woJbQty;
}
/**
 * @param woJbQty The woJbQty to set.
 */
public void setWoJbQty(int woJbQty)
{
    this.woJbQty = woJbQty;
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
}

/*
*$Log: WoReferenceDetails.java,v $
*Revision 1.1  2005/05/14 11:34:00  kduraisamy
*initial commit.
*
*
*/