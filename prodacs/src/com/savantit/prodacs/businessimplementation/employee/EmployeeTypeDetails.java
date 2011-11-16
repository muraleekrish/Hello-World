/*
 * Created on Oct 28, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.employee;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EmployeeTypeDetails implements Serializable
{
	private int emp_Typ_Id;
	private String emp_Typ_Name;
	private boolean emp_Typ_Dt;
	private boolean emp_Typ_Ot;
	private boolean emp_Typ_Incentive;
	private String emp_Typ_Desc;
	private Date emp_Typ_DateStamp;
	private int emp_Typ_IsValid;
	private int min_Rqd_Qty;
	
	public EmployeeTypeDetails()
	{
		emp_Typ_Id = 0;
		emp_Typ_Name = "";
		emp_Typ_Dt = false;
		emp_Typ_Ot = false;
		emp_Typ_Incentive = false;
		
		emp_Typ_Desc = "";
		emp_Typ_DateStamp = null;
		emp_Typ_IsValid = 0;
		min_Rqd_Qty = 0;
			
	}
	/**
	 * @return Returns the emp_Typ_DateStamp.
	 */
	public Date getEmp_Typ_DateStamp() {
		return emp_Typ_DateStamp;
	}
	/**
	 * @param emp_Typ_DateStamp The emp_Typ_DateStamp to set.
	 */
	public void setEmp_Typ_DateStamp(Date emp_Typ_DateStamp) {
		this.emp_Typ_DateStamp = emp_Typ_DateStamp;
	}
	/**
	 * @return Returns the emp_Typ_Desc.
	 */
	public String getEmp_Typ_Desc() {
		return emp_Typ_Desc;
	}
	/**
	 * @param emp_Typ_Desc The emp_Typ_Desc to set.
	 */
	public void setEmp_Typ_Desc(String emp_Typ_Desc) {
		this.emp_Typ_Desc = emp_Typ_Desc;
	}
	/**
	 * @return Returns the emp_Typ_Id.
	 */
	public int getEmp_Typ_Id() {
		return emp_Typ_Id;
	}
	/**
	 * @param emp_Typ_Id The emp_Typ_Id to set.
	 */
	public void setEmp_Typ_Id(int emp_Typ_Id) {
		this.emp_Typ_Id = emp_Typ_Id;
	}
	/**
	 * @return Returns the emp_Typ_IsValid.
	 */
	public int getEmp_Typ_IsValid() {
		return emp_Typ_IsValid;
	}
	/**
	 * @param emp_Typ_IsValid The emp_Typ_IsValid to set.
	 */
	public void setEmp_Typ_IsValid(int emp_Typ_IsValid) {
		this.emp_Typ_IsValid = emp_Typ_IsValid;
	}
	/**
	 * @return Returns the emp_Typ_Name.
	 */
	public String getEmp_Typ_Name() {
		return emp_Typ_Name;
	}
	/**
	 * @param emp_Typ_Name The emp_Typ_Name to set.
	 */
	public void setEmp_Typ_Name(String emp_Typ_Name) {
		this.emp_Typ_Name = emp_Typ_Name;
	}
	/**
	 * @return Returns the min_Rqd_Qty.
	 */
	public int getMin_Rqd_Qty() {
		return min_Rqd_Qty;
	}
	/**
	 * @param min_Rqd_Qty The min_Rqd_Qty to set.
	 */
	public void setMin_Rqd_Qty(int min_Rqd_Qty) {
		this.min_Rqd_Qty = min_Rqd_Qty;
	}
    /**
     * @return Returns the emp_Typ_Dt.
     */
    public boolean isEmp_Typ_Dt()
    {
        return emp_Typ_Dt;
    }
    /**
     * @param emp_Typ_Dt The emp_Typ_Dt to set.
     */
    public void setEmp_Typ_Dt(boolean emp_Typ_Dt)
    {
        this.emp_Typ_Dt = emp_Typ_Dt;
    }
    /**
     * @return Returns the emp_Typ_Incentive.
     */
    public boolean isEmp_Typ_Incentive()
    {
        return emp_Typ_Incentive;
    }
    /**
     * @param emp_Typ_Incentive The emp_Typ_Incentive to set.
     */
    public void setEmp_Typ_Incentive(boolean emp_Typ_Incentive)
    {
        this.emp_Typ_Incentive = emp_Typ_Incentive;
    }
    /**
     * @return Returns the emp_Typ_Ot.
     */
    public boolean isEmp_Typ_Ot()
    {
        return emp_Typ_Ot;
    }
    /**
     * @param emp_Typ_Ot The emp_Typ_Ot to set.
     */
    public void setEmp_Typ_Ot(boolean emp_Typ_Ot)
    {
        this.emp_Typ_Ot = emp_Typ_Ot;
    }
}
/***
$Log: EmployeeTypeDetails.java,v $
Revision 1.3  2005/06/17 06:05:42  kduraisamy
DUTY OT SPECIFIED IN EMP_TYP_MSTR ITSELF.

Revision 1.2  2004/11/06 07:39:29  sduraisamy
Employee module modified for Log inclusion and Employee Manager modified for change in Filter Methods

***/