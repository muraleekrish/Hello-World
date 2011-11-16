/*
 * Created on May 14, 2005
 *
 * ClassName	:  	ProductionOfMachineDetails.java
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
//PROD_CRNT_DATE,SHIFT_NAME,WO_NO,JB_DRWG_NO,CUST_NAME,JB_NAME,PROD_TOT_QTY,WOJB_SNO,
//PROD_START_OPN,PROD_END_OPN,PROD_STD_HRS,PROD_TOT_HRS ACTUAL_HRS,EMP_NAME
public class ProductionOfMachineDetails implements Serializable
{
    private Date prodCrntDate;
    private String shiftName;
    private String woNo;
    private String jbDrwgNo;
    private String custName;
    private String jbName;
    private int prodTotQty;
    private int woJbSno;
    private int prodStartOpn;
    private int prodEndOpn;
    private float prodStdHrs;
    private float prodTotHrs;
    private float actualHrs;
    private String empName;
    /**
     * 
     */
    public ProductionOfMachineDetails()
    {
        prodCrntDate = null;
        shiftName = "";
        woNo = "";
        jbDrwgNo = "";
        custName = "";
        jbName = "";
        prodTotQty = 0;
        woJbSno = 0;
        prodStartOpn = 0;
        prodEndOpn = 0;
        prodStdHrs = 0;
        prodTotHrs = 0;
        actualHrs = 0;
        empName = "";
    }
    
    

    /**
     * @return Returns the actualHrs.
     */
    public float getActualHrs()
    {
        return actualHrs;
    }
    /**
     * @param actualHrs The actualHrs to set.
     */
    public void setActualHrs(float actualHrs)
    {
        this.actualHrs = actualHrs;
    }
    /**
     * @return Returns the custName.
     */
    public String getCustName()
    {
        return custName;
    }
    /**
     * @param custName The custName to set.
     */
    public void setCustName(String custName)
    {
        this.custName = custName;
    }
    /**
     * @return Returns the empName.
     */
    public String getEmpName()
    {
        return empName;
    }
    /**
     * @param empName The empName to set.
     */
    public void setEmpName(String empName)
    {
        this.empName = empName;
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
     * @return Returns the prodEndOpn.
     */
    public int getProdEndOpn()
    {
        return prodEndOpn;
    }
    /**
     * @param prodEndOpn The prodEndOpn to set.
     */
    public void setProdEndOpn(int prodEndOpn)
    {
        this.prodEndOpn = prodEndOpn;
    }
    /**
     * @return Returns the prodStartOpn.
     */
    public int getProdStartOpn()
    {
        return prodStartOpn;
    }
    /**
     * @param prodStartOpn The prodStartOpn to set.
     */
    public void setProdStartOpn(int prodStartOpn)
    {
        this.prodStartOpn = prodStartOpn;
    }
    /**
     * @return Returns the prodStdHrs.
     */
    public float getProdStdHrs()
    {
        return prodStdHrs;
    }
    /**
     * @param prodStdHrs The prodStdHrs to set.
     */
    public void setProdStdHrs(float prodStdHrs)
    {
        this.prodStdHrs = prodStdHrs;
    }
    /**
     * @return Returns the prodTotHrs.
     */
    public float getProdTotHrs()
    {
        return prodTotHrs;
    }
    /**
     * @param prodTotHrs The prodTotHrs to set.
     */
    public void setProdTotHrs(float prodTotHrs)
    {
        this.prodTotHrs = prodTotHrs;
    }
    /**
     * @return Returns the prodTotQty.
     */
    public int getProdTotQty()
    {
        return prodTotQty;
    }
    /**
     * @param prodTotQty The prodTotQty to set.
     */
    public void setProdTotQty(int prodTotQty)
    {
        this.prodTotQty = prodTotQty;
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
     * @return Returns the woJbSno.
     */
    public int getWoJbSno()
    {
        return woJbSno;
    }
    /**
     * @param woJbSno The woJbSno to set.
     */
    public void setWoJbSno(int woJbSno)
    {
        this.woJbSno = woJbSno;
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
*$Log: ProductionOfMachineDetails.java,v $
*Revision 1.1  2005/05/14 10:33:02  kduraisamy
*initial commit.
*
*
*/