/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.machine;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MachineTypeDetails implements Serializable
{
	private int mc_Typ_Id;
	private String mc_Typ_Name;
	private String mc_Typ_Desc;
	private Date mc_Typ_DateStamp;
	private int mc_Typ_IsValid;
	public MachineTypeDetails()
	{
		mc_Typ_Id = 0;
		mc_Typ_Name = "";
		mc_Typ_Desc = "";
		mc_Typ_DateStamp = null;
		mc_Typ_IsValid = 0;
	}
	/**
	 * @return Returns the mc_Typ_DateStamp.
	 */
	public Date getMc_Typ_DateStamp() {
		return mc_Typ_DateStamp;
	}
	/**
	 * @param mc_Typ_DateStamp The mc_Typ_DateStamp to set.
	 */
	public void setMc_Typ_DateStamp(Date mc_Typ_DateStamp) {
		this.mc_Typ_DateStamp = mc_Typ_DateStamp;
	}
	/**
	 * @return Returns the mc_Typ_Id.
	 */
	public int getMc_Typ_Id() {
		return mc_Typ_Id;
	}
	/**
	 * @param mc_Typ_Id The mc_Typ_Id to set.
	 */
	public void setMc_Typ_Id(int mc_Typ_Id) {
		this.mc_Typ_Id = mc_Typ_Id;
	}
	/**
	 * @return Returns the mc_Typ_IsValid.
	 */
	public int getMc_Typ_IsValid() {
		return mc_Typ_IsValid;
	}
	/**
	 * @param mc_Typ_IsValid The mc_Typ_IsValid to set.
	 */
	public void setMc_Typ_IsValid(int mc_Typ_IsValid) {
		this.mc_Typ_IsValid = mc_Typ_IsValid;
	}
	/**
	 * @return Returns the mc_Typ_Name.
	 */
	public String getMc_Typ_Name() {
		return mc_Typ_Name;
	}
	/**
	 * @param mc_Typ_Name The mc_Typ_Name to set.
	 */
	public void setMc_Typ_Name(String mc_Typ_Name) {
		this.mc_Typ_Name = mc_Typ_Name;
	}
    /**
     * @return Returns the mc_Typ_Desc.
     */
    public String getMc_Typ_Desc()
    {
        return mc_Typ_Desc;
    }
    /**
     * @param mc_Typ_Desc The mc_Typ_Desc to set.
     */
    public void setMc_Typ_Desc(String mc_Typ_Desc)
    {
        this.mc_Typ_Desc = mc_Typ_Desc;
    }
}

/***
$Log: MachineTypeDetails.java,v $
Revision 1.4  2005/03/08 05:16:15  kduraisamy
field desc added.

Revision 1.3  2004/11/19 04:39:02  sduraisamy
Serializable implemented in MachineTypeDetails EntityObject

Revision 1.2  2004/11/06 07:40:32  sduraisamy
Machine module modified for Log inclusion and change in Machine manager filter method

***/