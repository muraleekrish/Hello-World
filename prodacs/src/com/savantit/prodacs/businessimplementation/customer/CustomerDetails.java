/*
 * Created on Sep 27, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.customer;
import java.io.Serializable;
import java.util.Date;
/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomerDetails implements Serializable
{
	private int cust_Id;
	private String cust_Name;
	private int cust_Typ_Id;
	private String cust_Typ_Name;
	private String cust_Insrvce;
	private String cust_Addr1;
	private String cust_Addr2;
	private String cust_City;
	private String cust_State;
	private String cust_Pcode;
	private String cust_Country;
	private String cust_Cntct_Fname;
	private String cust_Cntct_Lname;
	private String cust_Cntct_Designation;
	private String cust_Phone1;
	private String cust_Extension1;
	private String cust_Phone2;
	private String cust_Extension2;
	private String cust_Phone3;
	private String cust_Extension3;
	private String cust_Mobile;
	private String cust_Email;
	private String cust_Website;
	private String cust_Fax;
	private Date cust_LOPD;
	private Date cust_LODD;
	private String cust_LWOR;
	private int cust_YTD_OrdrVal;
	private int cust_YTD_TotOrdrs;
	
	private Date cust_Datestamp;
	private int cust_Isvalid;
	public CustomerDetails()
	{
		this.cust_Id = 0;
		this.cust_Name = "";
		this.cust_Typ_Id = 0;
		this.cust_Typ_Name = "";
		this.cust_Insrvce = "";
		this.cust_Addr1 = "";
		this.cust_Addr2 = "";
		this.cust_City = "";
		this.cust_State = "";
		this.cust_Pcode = "";
		this.cust_Country = "";
		this.cust_Cntct_Fname = "";
		this.cust_Cntct_Lname = "";
		this.cust_Cntct_Designation = "";
		this.cust_Phone1 = "";
		this.cust_Extension1 = "";
		this.cust_Phone2 = "";
		this.cust_Extension2 = "";
		this.cust_Phone3 = "";
		this.cust_Extension3 = "";
		this.cust_Mobile = "";
		this.cust_Email = "";
		this.cust_Website = "";
		this.cust_Fax = "";
		this.cust_Datestamp = null;
		this.cust_Isvalid = 0;
	}
	/**
	 * @return Returns the cust_Addr1.
	 */
	public String getCust_Addr1() {
		return cust_Addr1;
	}
	/**
	 * @param cust_Addr1 The cust_Addr1 to set.
	 */
	public void setCust_Addr1(String cust_Addr1) {
		this.cust_Addr1 = cust_Addr1;
	}
	/**
	 * @return Returns the cust_Addr2.
	 */
	public String getCust_Addr2() {
		return cust_Addr2;
	}
	/**
	 * @param cust_Addr2 The cust_Addr2 to set.
	 */
	public void setCust_Addr2(String cust_Addr2) {
		this.cust_Addr2 = cust_Addr2;
	}
	/**
	 * @return Returns the cust_City.
	 */
	public String getCust_City() {
		return cust_City;
	}
	/**
	 * @param cust_City The cust_City to set.
	 */
	public void setCust_City(String cust_City) {
		this.cust_City = cust_City;
	}
	/**
	 * @return Returns the cust_Cntct_Fname.
	 */
	public String getCust_Cntct_Fname() {
		return cust_Cntct_Fname;
	}
	/**
	 * @param cust_Cntct_Fname The cust_Cntct_Fname to set.
	 */
	public void setCust_Cntct_Fname(String cust_Cntct_Fname) {
		this.cust_Cntct_Fname = cust_Cntct_Fname;
	}
	/**
	 * @return Returns the cust_Cntct_Lname.
	 */
	public String getCust_Cntct_Lname() {
		return cust_Cntct_Lname;
	}
	/**
	 * @param cust_Cntct_Lname The cust_Cntct_Lname to set.
	 */
	public void setCust_Cntct_Lname(String cust_Cntct_Lname) {
		this.cust_Cntct_Lname = cust_Cntct_Lname;
	}
	/**
	 * @return Returns the cust_Country.
	 */
	public String getCust_Country() {
		return cust_Country;
	}
	/**
	 * @param cust_Country The cust_Country to set.
	 */
	public void setCust_Country(String cust_Country) {
		this.cust_Country = cust_Country;
	}
	/**
	 * @return Returns the cust_Fax.
	 */
	public String getCust_Fax() {
		return cust_Fax;
	}
	/**
	 * @param cust_Fax The cust_Fax to set.
	 */
	public void setCust_Fax(String cust_Fax) {
		this.cust_Fax = cust_Fax;
	}
	/**
	 * @return Returns the cust_Insrvce.
	 */
	public String getCust_Insrvce() {
		return cust_Insrvce;
	}
	/**
	 * @param cust_Insrvce The cust_Insrvce to set.
	 */
	public void setCust_Insrvce(String cust_Insrvce) {
		this.cust_Insrvce = cust_Insrvce;
	}
	
	/**
	 * @return Returns the cust_Name.
	 */
	public String getCust_Name() {
		return cust_Name;
	}
	/**
	 * @param cust_Name The cust_Name to set.
	 */
	public void setCust_Name(String cust_Name) {
		this.cust_Name = cust_Name;
	}
	/**
	 * @return Returns the cust_Pcode.
	 */
	public String getCust_Pcode() {
		return cust_Pcode;
	}
	/**
	 * @param cust_Pcode The cust_Pcode to set.
	 */
	public void setCust_Pcode(String cust_Pcode) {
		this.cust_Pcode = cust_Pcode;
	}
	/**
	 * @return Returns the cust_Phone1.
	 */
	public String getCust_Phone1() {
		return cust_Phone1;
	}
	/**
	 * @param cust_Phone1 The cust_Phone1 to set.
	 */
	public void setCust_Phone1(String cust_Phone1) {
		this.cust_Phone1 = cust_Phone1;
	}
	/**
	 * @return Returns the cust_Phone2.
	 */
	public String getCust_Phone2() {
		return cust_Phone2;
	}
	/**
	 * @param cust_Phone2 The cust_Phone2 to set.
	 */
	public void setCust_Phone2(String cust_Phone2) {
		this.cust_Phone2 = cust_Phone2;
	}
	/**
	 * @return Returns the cust_State.
	 */
	public String getCust_State() {
		return cust_State;
	}
	/**
	 * @param cust_State The cust_State to set.
	 */
	public void setCust_State(String cust_State) {
		this.cust_State = cust_State;
	}
	/**
	 * @return Returns the cust_Typ_Id.
	 */
	public int getCust_Typ_Id() {
		return cust_Typ_Id;
	}
	/**
	 * @param cust_Typ_Id The cust_Typ_Id to set.
	 */
	public void setCust_Typ_Id(int cust_Typ_Id) {
		this.cust_Typ_Id = cust_Typ_Id;
	}
	
	/**
	 * @return Returns the cust_Id.
	 */
	public int getCust_Id() {
		return cust_Id;
	}
	/**
	 * @param cust_Id The cust_Id to set.
	 */
	public void setCust_Id(int cust_Id) {
		this.cust_Id = cust_Id;
	}
	/**
	 * @return Returns the cust_Typ_Name.
	 */
	public String getCust_Typ_Name() {
		return cust_Typ_Name;
	}
	/**
	 * @param cust_Typ_Name The cust_Typ_Name to set.
	 */
	public void setCust_Typ_Name(String cust_Typ_Name) {
		this.cust_Typ_Name = cust_Typ_Name;
	}
	/**
	 * @return Returns the cust_Datestamp.
	 */
	public Date getCust_Datestamp() {
		return cust_Datestamp;
	}
	/**
	 * @param cust_Datestamp The cust_Datestamp to set.
	 */
	public void setCust_Datestamp(Date cust_Datestamp) {
		this.cust_Datestamp = cust_Datestamp;
	}
	/**
	 * @return Returns the cust_Isvalid.
	 */
	public int getCust_Isvalid() {
		return cust_Isvalid;
	}
	/**
	 * @param cust_Isvalid The cust_Isvalid to set.
	 */
	public void setCust_Isvalid(int cust_Isvalid) {
		this.cust_Isvalid = cust_Isvalid;
	}
	/**
	 * @return Returns the cust_LODD.
	 */
	public Date getCust_LODD() {
		return cust_LODD;
	}
	/**
	 * @param cust_LODD The cust_LODD to set.
	 */
	public void setCust_LODD(Date cust_LODD) {
		this.cust_LODD = cust_LODD;
	}
	/**
	 * @return Returns the cust_LOPD.
	 */
	public Date getCust_LOPD() {
		return cust_LOPD;
	}
	/**
	 * @param cust_LOPD The cust_LOPD to set.
	 */
	public void setCust_LOPD(Date cust_LOPD) {
		this.cust_LOPD = cust_LOPD;
	}
	/**
	 * @return Returns the cust_LWOR.
	 */
	public String getCust_LWOR() {
		return cust_LWOR;
	}
	/**
	 * @param cust_LWOR The cust_LWOR to set.
	 */
	public void setCust_LWOR(String cust_LWOR) {
		this.cust_LWOR = cust_LWOR;
	}
	/**
	 * @return Returns the cust_YTD_OrdrVal.
	 */
	public int getCust_YTD_OrdrVal() {
		return cust_YTD_OrdrVal;
	}
	/**
	 * @param cust_YTD_OrdrVal The cust_YTD_OrdrVal to set.
	 */
	public void setCust_YTD_OrdrVal(int cust_YTD_OrdrVal) {
		this.cust_YTD_OrdrVal = cust_YTD_OrdrVal;
	}
	/**
	 * @return Returns the cust_YTD_TotOrdrs.
	 */
	public int getCust_YTD_TotOrdrs() {
		return cust_YTD_TotOrdrs;
	}
	/**
	 * @param cust_YTD_TotOrdrs The cust_YTD_TotOrdrs to set.
	 */
	public void setCust_YTD_TotOrdrs(int cust_YTD_TotOrdrs) {
		this.cust_YTD_TotOrdrs = cust_YTD_TotOrdrs;
	}
    /**
     * @return Returns the cust_Cntct_Designation.
     */
    public String getCust_Cntct_Designation()
    {
        return cust_Cntct_Designation;
    }
    /**
     * @param cust_Cntct_Designation The cust_Cntct_Designation to set.
     */
    public void setCust_Cntct_Designation(String cust_Cntct_Designation)
    {
        this.cust_Cntct_Designation = cust_Cntct_Designation;
    }
    /**
     * @return Returns the cust_Email.
     */
    public String getCust_Email()
    {
        return cust_Email;
    }
    /**
     * @param cust_Email The cust_Email to set.
     */
    public void setCust_Email(String cust_Email)
    {
        this.cust_Email = cust_Email;
    }
    /**
     * @return Returns the cust_Extension1.
     */
    public String getCust_Extension1()
    {
        return cust_Extension1;
    }
    /**
     * @param cust_Extension1 The cust_Extension1 to set.
     */
    public void setCust_Extension1(String cust_Extension1)
    {
        this.cust_Extension1 = cust_Extension1;
    }
    /**
     * @return Returns the cust_Extension2.
     */
    public String getCust_Extension2()
    {
        return cust_Extension2;
    }
    /**
     * @param cust_Extension2 The cust_Extension2 to set.
     */
    public void setCust_Extension2(String cust_Extension2)
    {
        this.cust_Extension2 = cust_Extension2;
    }
    /**
     * @return Returns the cust_Extension3.
     */
    public String getCust_Extension3()
    {
        return cust_Extension3;
    }
    /**
     * @param cust_Extension3 The cust_Extension3 to set.
     */
    public void setCust_Extension3(String cust_Extension3)
    {
        this.cust_Extension3 = cust_Extension3;
    }
    /**
     * @return Returns the cust_Mobile.
     */
    public String getCust_Mobile()
    {
        return cust_Mobile;
    }
    /**
     * @param cust_Mobile The cust_Mobile to set.
     */
    public void setCust_Mobile(String cust_Mobile)
    {
        this.cust_Mobile = cust_Mobile;
    }
    /**
     * @return Returns the cust_Phone3.
     */
    public String getCust_Phone3()
    {
        return cust_Phone3;
    }
    /**
     * @param cust_Phone3 The cust_Phone3 to set.
     */
    public void setCust_Phone3(String cust_Phone3)
    {
        this.cust_Phone3 = cust_Phone3;
    }
    /**
     * @return Returns the cust_Website.
     */
    public String getCust_Website()
    {
        return cust_Website;
    }
    /**
     * @param cust_Website The cust_Website to set.
     */
    public void setCust_Website(String cust_Website)
    {
        this.cust_Website = cust_Website;
    }
}	
	

/***
$Log: CustomerDetails.java,v $
Revision 1.5  2005/08/17 07:06:07  kduraisamy
EMP_MSTR AND CUST_MSTR TABLE ALTERED.

Revision 1.4  2004/11/16 14:05:22  kduraisamy
additional four fields are added to entityobject

Revision 1.3  2004/11/15 08:40:13  kduraisamy
getter,setter added for datestamp and isvalid

Revision 1.2  2004/11/06 06:45:37  kduraisamy
cust_typ_name field added

***/