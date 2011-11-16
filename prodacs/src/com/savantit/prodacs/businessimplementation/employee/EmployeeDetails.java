/*
 * Created on Oct 26, 2004
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
public class EmployeeDetails implements Serializable
{
	private int emp_Id;
	private String emp_Name;
	private String emp_Code;
	private int emp_Typ_Id;
	private String emp_Typ_Name;
	private int emp_Stat_Id;
	private String emp_Stat_Name;
	private boolean emp_Insrvce;
	private Date emp_Doj;
	private Date emp_Dob;
	private String emp_Cntct_Addr1;
	private String emp_Cntct_Addr2;
	private String emp_Cntct_City;
	private String emp_Cntct_State;
	private String emp_Cntct_Pcode;
	private String emp_Cntct_Phone1;
	private String emp_Cntct_Phone2;
	private String emp_Cntct_Name;
	private String emp_BloodGp;
	private String emp_Permnt_Addr1;
	private String emp_Permnt_Addr2;
	private String emp_Permnt_City;
	private String emp_Permnt_State;
	private String emp_Permnt_Pcode;
	private String emp_Permnt_Phone1;
	private String emp_Permnt_Phone2;
	
	private int emp_MTD_Slry_Hrs;
	private int emp_MTD_OT_Hrs;
	private int emp_MTD_Incntv_Hrs;
	private int emp_YTD_Slry_Hrs;
	private int emp_YTD_OT_Hrs;
	private int emp_YTD_Incntv_Hrs;
	private Date emp_DateStamp;
	private int emp_Isvalid;
	
	public EmployeeDetails()
	{
		this.emp_Id=0;
	    this.emp_Name="";
	    this.emp_Code = "";
		this.emp_Typ_Id=0;
		this.emp_Stat_Id=0;
		this.emp_Insrvce=false;
		this.emp_Typ_Name = "";
	//	this.emp_Doj=new Date();
	//	this.emp_Dob=new Date();
		this.emp_Cntct_Addr1="";
		this.emp_Cntct_Addr2="";
		this.emp_Cntct_City="";
		this.emp_Cntct_State="";
		this.emp_Cntct_Pcode="";
		this.emp_Cntct_Phone1 = "";
		this.emp_Cntct_Phone2 = "";
		this.emp_Cntct_Name = "";
		this.emp_BloodGp = "";
		this.emp_Permnt_Addr1="";
		this.emp_Permnt_Addr2="";
		this.emp_Permnt_City="";
		this.emp_Permnt_State="";
		this.emp_Permnt_Pcode="";
		this.emp_Permnt_Phone1 = "";
		this.emp_Permnt_Phone2 = "";
		this.emp_MTD_Slry_Hrs=0;
		this.emp_MTD_OT_Hrs=0;
		this.emp_MTD_Incntv_Hrs=0;
		this.emp_YTD_Slry_Hrs=0;
		this.emp_YTD_OT_Hrs=0;
		this.emp_YTD_Incntv_Hrs=0;
	
		
	}
	
	
	
	/**
	 * @return Returns the emp_DateStamp.
	 */
	public Date getEmp_DateStamp() {
		return emp_DateStamp;
	}
	/**
	 * @param emp_DateStamp The emp_DateStamp to set.
	 */
	public void setEmp_DateStamp(Date emp_DateStamp) {
		this.emp_DateStamp = emp_DateStamp;
	}
	/**
	 * @return Returns the emp_Dob.
	 */
	public Date getEmp_Dob() {
		return emp_Dob;
	}
	/**
	 * @param emp_Dob The emp_Dob to set.
	 */
	public void setEmp_Dob(Date emp_Dob) {
		this.emp_Dob = emp_Dob;
	}
	/**
	 * @return Returns the emp_Doj.
	 */
	public Date getEmp_Doj() {
		return emp_Doj;
	}
	/**
	 * @param emp_Doj The emp_Doj to set.
	 */
	public void setEmp_Doj(Date emp_Doj) {
		this.emp_Doj = emp_Doj;
	}
	/**
	 * @return Returns the emp_Id.
	 */
	public int getEmp_Id() {
		return emp_Id;
	}
	/**
	 * @param emp_Id The emp_Id to set.
	 */
	public void setEmp_Id(int emp_Id) {
		this.emp_Id = emp_Id;
	}
	/**
	 * @return Returns the emp_Insrvce.
	 */
	public boolean getEmp_Insrvce() {
		return emp_Insrvce;
	}
	/**
	 * @param emp_Insrvce The emp_Insrvce to set.
	 */
	public void setEmp_Insrvce(boolean emp_Insrvce) {
		this.emp_Insrvce = emp_Insrvce;
	}
	/**
	 * @return Returns the emp_Isvalid.
	 */
	public int getEmp_Isvalid() {
		return emp_Isvalid;
	}
	/**
	 * @param emp_Isvalid The emp_Isvalid to set.
	 */
	public void setEmp_Isvalid(int emp_Isvalid) {
		this.emp_Isvalid = emp_Isvalid;
	}
	/**
	 * @return Returns the emp_MTD_Incntv_Hrs.
	 */
	public int getEmp_MTD_Incntv_Hrs() {
		return emp_MTD_Incntv_Hrs;
	}
	/**
	 * @param emp_MTD_Incntv_Hrs The emp_MTD_Incntv_Hrs to set.
	 */
	public void setEmp_MTD_Incntv_Hrs(int emp_MTD_Incntv_Hrs) {
		this.emp_MTD_Incntv_Hrs = emp_MTD_Incntv_Hrs;
	}
	/**
	 * @return Returns the emp_MTD_OT_Hrs.
	 */
	public int getEmp_MTD_OT_Hrs() {
		return emp_MTD_OT_Hrs;
	}
	/**
	 * @param emp_MTD_OT_Hrs The emp_MTD_OT_Hrs to set.
	 */
	public void setEmp_MTD_OT_Hrs(int emp_MTD_OT_Hrs) {
		this.emp_MTD_OT_Hrs = emp_MTD_OT_Hrs;
	}
	/**
	 * @return Returns the emp_MTD_Slry_Hrs.
	 */
	public int getEmp_MTD_Slry_Hrs() {
		return emp_MTD_Slry_Hrs;
	}
	/**
	 * @param emp_MTD_Slry_Hrs The emp_MTD_Slry_Hrs to set.
	 */
	public void setEmp_MTD_Slry_Hrs(int emp_MTD_Slry_Hrs) {
		this.emp_MTD_Slry_Hrs = emp_MTD_Slry_Hrs;
	}
	/**
	 * @return Returns the emp_Name.
	 */
	public String getEmp_Name() {
		return emp_Name;
	}
	/**
	 * @param emp_Name The emp_Name to set.
	 */
	public void setEmp_Name(String emp_Name) {
		this.emp_Name = emp_Name;
	}
	/**
	 * @return Returns the emp_Stat_Id.
	 */
	public int getEmp_Stat_Id() {
		return emp_Stat_Id;
	}
	/**
	 * @param emp_Stat_Id The emp_Stat_Id to set.
	 */
	public void setEmp_Stat_Id(int emp_Stat_Id) {
		this.emp_Stat_Id = emp_Stat_Id;
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
	 * @return Returns the emp_YTD_Incntv_Hrs.
	 */
	public int getEmp_YTD_Incntv_Hrs() {
		return emp_YTD_Incntv_Hrs;
	}
	/**
	 * @param emp_YTD_Incntv_Hrs The emp_YTD_Incntv_Hrs to set.
	 */
	public void setEmp_YTD_Incntv_Hrs(int emp_YTD_Incntv_Hrs) {
		this.emp_YTD_Incntv_Hrs = emp_YTD_Incntv_Hrs;
	}
	/**
	 * @return Returns the emp_YTD_OT_Hrs.
	 */
	public int getEmp_YTD_OT_Hrs() {
		return emp_YTD_OT_Hrs;
	}
	/**
	 * @param emp_YTD_OT_Hrs The emp_YTD_OT_Hrs to set.
	 */
	public void setEmp_YTD_OT_Hrs(int emp_YTD_OT_Hrs) {
		this.emp_YTD_OT_Hrs = emp_YTD_OT_Hrs;
	}
	/**
	 * @return Returns the emp_YTD_Slry_Hrs.
	 */
	public int getEmp_YTD_Slry_Hrs() {
		return emp_YTD_Slry_Hrs;
	}
	/**
	 * @param emp_YTD_Slry_Hrs The emp_YTD_Slry_Hrs to set.
	 */
	public void setEmp_YTD_Slry_Hrs(int emp_YTD_Slry_Hrs) {
		this.emp_YTD_Slry_Hrs = emp_YTD_Slry_Hrs;
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
	 * @return Returns the emp_Stat_Name.
	 */
	public String getEmp_Stat_Name() {
		return emp_Stat_Name;
	}
	/**
	 * @param emp_Stat_Name The emp_Stat_Name to set.
	 */
	public void setEmp_Stat_Name(String emp_Stat_Name) {
		this.emp_Stat_Name = emp_Stat_Name;
	}
    /**
     * @return Returns the emp_Code.
     */
    public String getEmp_Code()
    {
        return emp_Code;
    }
    /**
     * @param emp_Code The emp_Code to set.
     */
    public void setEmp_Code(String emp_Code)
    {
        this.emp_Code = emp_Code;
    }
    /**
     * @return Returns the emp_BloodGp.
     */
    public String getEmp_BloodGp()
    {
        return emp_BloodGp;
    }
    /**
     * @param emp_BloodGp The emp_BloodGp to set.
     */
    public void setEmp_BloodGp(String emp_BloodGp)
    {
        this.emp_BloodGp = emp_BloodGp;
    }
    /**
     * @return Returns the emp_Cntct_Addr1.
     */
    public String getEmp_Cntct_Addr1()
    {
        return emp_Cntct_Addr1;
    }
    /**
     * @param emp_Cntct_Addr1 The emp_Cntct_Addr1 to set.
     */
    public void setEmp_Cntct_Addr1(String emp_Cntct_Addr1)
    {
        this.emp_Cntct_Addr1 = emp_Cntct_Addr1;
    }
    /**
     * @return Returns the emp_Cntct_Addr2.
     */
    public String getEmp_Cntct_Addr2()
    {
        return emp_Cntct_Addr2;
    }
    /**
     * @param emp_Cntct_Addr2 The emp_Cntct_Addr2 to set.
     */
    public void setEmp_Cntct_Addr2(String emp_Cntct_Addr2)
    {
        this.emp_Cntct_Addr2 = emp_Cntct_Addr2;
    }
    /**
     * @return Returns the emp_Cntct_City.
     */
    public String getEmp_Cntct_City()
    {
        return emp_Cntct_City;
    }
    /**
     * @param emp_Cntct_City The emp_Cntct_City to set.
     */
    public void setEmp_Cntct_City(String emp_Cntct_City)
    {
        this.emp_Cntct_City = emp_Cntct_City;
    }
    /**
     * @return Returns the emp_Cntct_Name.
     */
    public String getEmp_Cntct_Name()
    {
        return emp_Cntct_Name;
    }
    /**
     * @param emp_Cntct_Name The emp_Cntct_Name to set.
     */
    public void setEmp_Cntct_Name(String emp_Cntct_Name)
    {
        this.emp_Cntct_Name = emp_Cntct_Name;
    }
    /**
     * @return Returns the emp_Cntct_Pcode.
     */
    public String getEmp_Cntct_Pcode()
    {
        return emp_Cntct_Pcode;
    }
    /**
     * @param emp_Cntct_Pcode The emp_Cntct_Pcode to set.
     */
    public void setEmp_Cntct_Pcode(String emp_Cntct_Pcode)
    {
        this.emp_Cntct_Pcode = emp_Cntct_Pcode;
    }
    /**
     * @return Returns the emp_Cntct_Phone1.
     */
    public String getEmp_Cntct_Phone1()
    {
        return emp_Cntct_Phone1;
    }
    /**
     * @param emp_Cntct_Phone1 The emp_Cntct_Phone1 to set.
     */
    public void setEmp_Cntct_Phone1(String emp_Cntct_Phone1)
    {
        this.emp_Cntct_Phone1 = emp_Cntct_Phone1;
    }
    /**
     * @return Returns the emp_Cntct_Phone2.
     */
    public String getEmp_Cntct_Phone2()
    {
        return emp_Cntct_Phone2;
    }
    /**
     * @param emp_Cntct_Phone2 The emp_Cntct_Phone2 to set.
     */
    public void setEmp_Cntct_Phone2(String emp_Cntct_Phone2)
    {
        this.emp_Cntct_Phone2 = emp_Cntct_Phone2;
    }
    /**
     * @return Returns the emp_Cntct_State.
     */
    public String getEmp_Cntct_State()
    {
        return emp_Cntct_State;
    }
    /**
     * @param emp_Cntct_State The emp_Cntct_State to set.
     */
    public void setEmp_Cntct_State(String emp_Cntct_State)
    {
        this.emp_Cntct_State = emp_Cntct_State;
    }
    /**
     * @return Returns the emp_Permnt_Addr1.
     */
    public String getEmp_Permnt_Addr1()
    {
        return emp_Permnt_Addr1;
    }
    /**
     * @param emp_Permnt_Addr1 The emp_Permnt_Addr1 to set.
     */
    public void setEmp_Permnt_Addr1(String emp_Permnt_Addr1)
    {
        this.emp_Permnt_Addr1 = emp_Permnt_Addr1;
    }
    /**
     * @return Returns the emp_Permnt_Addr2.
     */
    public String getEmp_Permnt_Addr2()
    {
        return emp_Permnt_Addr2;
    }
    /**
     * @param emp_Permnt_Addr2 The emp_Permnt_Addr2 to set.
     */
    public void setEmp_Permnt_Addr2(String emp_Permnt_Addr2)
    {
        this.emp_Permnt_Addr2 = emp_Permnt_Addr2;
    }
    /**
     * @return Returns the emp_Permnt_City.
     */
    public String getEmp_Permnt_City()
    {
        return emp_Permnt_City;
    }
    /**
     * @param emp_Permnt_City The emp_Permnt_City to set.
     */
    public void setEmp_Permnt_City(String emp_Permnt_City)
    {
        this.emp_Permnt_City = emp_Permnt_City;
    }
    /**
     * @return Returns the emp_Permnt_Pcode.
     */
    public String getEmp_Permnt_Pcode()
    {
        return emp_Permnt_Pcode;
    }
    /**
     * @param emp_Permnt_Pcode The emp_Permnt_Pcode to set.
     */
    public void setEmp_Permnt_Pcode(String emp_Permnt_Pcode)
    {
        this.emp_Permnt_Pcode = emp_Permnt_Pcode;
    }
    /**
     * @return Returns the emp_Permnt_Phone1.
     */
    public String getEmp_Permnt_Phone1()
    {
        return emp_Permnt_Phone1;
    }
    /**
     * @param emp_Permnt_Phone1 The emp_Permnt_Phone1 to set.
     */
    public void setEmp_Permnt_Phone1(String emp_Permnt_Phone1)
    {
        this.emp_Permnt_Phone1 = emp_Permnt_Phone1;
    }
    /**
     * @return Returns the emp_Permnt_Phone2.
     */
    public String getEmp_Permnt_Phone2()
    {
        return emp_Permnt_Phone2;
    }
    /**
     * @param emp_Permnt_Phone2 The emp_Permnt_Phone2 to set.
     */
    public void setEmp_Permnt_Phone2(String emp_Permnt_Phone2)
    {
        this.emp_Permnt_Phone2 = emp_Permnt_Phone2;
    }
    /**
     * @return Returns the emp_Permnt_State.
     */
    public String getEmp_Permnt_State()
    {
        return emp_Permnt_State;
    }
    /**
     * @param emp_Permnt_State The emp_Permnt_State to set.
     */
    public void setEmp_Permnt_State(String emp_Permnt_State)
    {
        this.emp_Permnt_State = emp_Permnt_State;
    }
}
/***
$Log: EmployeeDetails.java,v $
Revision 1.5  2005/08/17 07:50:41  kduraisamy
EMP_MSTR AND CUST_MSTR TABLE ALTERED.

Revision 1.4  2005/08/01 06:09:46  kduraisamy
EMP_CDE IS INTRODUCED.

Revision 1.3  2004/11/17 11:53:26  sduraisamy
Status Name included

Revision 1.2  2004/11/06 07:39:29  sduraisamy
Employee module modified for Log inclusion and Employee Manager modified for change in Filter Methods

***/