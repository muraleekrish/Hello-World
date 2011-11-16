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
public class MachineDetails implements Serializable
{
	private String mach_Cde;
	private String mach_Name;
	private int mach_Typ_Id;
	private String mach_Typ_Name;
	private String mach_InUse;
	private Date mach_Install_Date;
	private String mach_SPLR_Name;
	private String mach_SPLR_Cntct_Person;
	private String mach_SPLR_Phone;
	private String mach_SPLR_Addr;
	private String mach_SP_Name;
	private String mach_SP_Cntct_Person;
	private String mach_SP_Phone;
	private String mach_SP_Addr;
	private int mach_MTD_Hrs_Used;
	private int mach_MTD_Idl_Hrs;
	private int mach_MTD_Brk_Hrs;
	private Date mach_Datestamp;
	private int mach_IsValid;
	
	public MachineDetails()
	{
		mach_Cde = "";
		mach_Name = "";
		mach_Typ_Id = 0;
		mach_Typ_Name = "";
		mach_InUse = "";
		mach_Install_Date = null;
		mach_SPLR_Name = "";
		mach_SPLR_Cntct_Person = "";
		mach_SPLR_Phone = "";
		mach_SPLR_Addr = "";
		mach_SP_Name = "";
		mach_SP_Cntct_Person = "";
		mach_SP_Phone = "";
		mach_SP_Addr = "";
		mach_MTD_Hrs_Used = 0;
		mach_MTD_Idl_Hrs = 0;
		mach_MTD_Brk_Hrs = 0;
		mach_Datestamp = null;
		mach_IsValid = 0;
	}
	/**
	 * @return Returns the mach_Cde.
	 */
	public String getMach_Cde() {
		return mach_Cde;
	}
	/**
	 * @param mach_Cde The mach_Cde to set.
	 */
	public void setMach_Cde(String mach_Cde) {
		this.mach_Cde = mach_Cde;
	}
	/**
	 * @return Returns the mach_Datestamp.
	 */
	public Date getMach_Datestamp() {
		return mach_Datestamp;
	}
	/**
	 * @param mach_Datestamp The mach_Datestamp to set.
	 */
	public void setMach_Datestamp(Date mach_Datestamp) {
		this.mach_Datestamp = mach_Datestamp;
	}
	/**
	 * @return Returns the mach_Install_Date.
	 */
	public Date getMach_Install_Date() {
		return mach_Install_Date;
	}
	/**
	 * @param mach_Install_Date The mach_Install_Date to set.
	 */
	public void setMach_Install_Date(Date mach_Install_Date) {
		this.mach_Install_Date = mach_Install_Date;
	}
	/**
	 * @return Returns the mach_InUse.
	 */
	public String getMach_InUse() {
		return mach_InUse;
	}
	/**
	 * @param mach_InUse The mach_InUse to set.
	 */
	public void setMach_InUse(String mach_InUse) {
		this.mach_InUse = mach_InUse;
	}
	/**
	 * @return Returns the mach_IsValid.
	 */
	public int getMach_IsValid() {
		return mach_IsValid;
	}
	/**
	 * @param mach_IsValid The mach_IsValid to set.
	 */
	public void setMach_IsValid(int mach_IsValid) {
		this.mach_IsValid = mach_IsValid;
	}
	/**
	 * @return Returns the mach_MTD_Brk_Hrs.
	 */
	public int getMach_MTD_Brk_Hrs() {
		return mach_MTD_Brk_Hrs;
	}
	/**
	 * @param mach_MTD_Brk_Hrs The mach_MTD_Brk_Hrs to set.
	 */
	public void setMach_MTD_Brk_Hrs(int mach_MTD_Brk_Hrs) {
		this.mach_MTD_Brk_Hrs = mach_MTD_Brk_Hrs;
	}
	/**
	 * @return Returns the mach_MTD_Hrs_Used.
	 */
	public int getMach_MTD_Hrs_Used() {
		return mach_MTD_Hrs_Used;
	}
	/**
	 * @param mach_MTD_Hrs_Used The mach_MTD_Hrs_Used to set.
	 */
	public void setMach_MTD_Hrs_Used(int mach_MTD_Hrs_Used) {
		this.mach_MTD_Hrs_Used = mach_MTD_Hrs_Used;
	}
	/**
	 * @return Returns the mach_MTD_Idl_Hrs.
	 */
	public int getMach_MTD_Idl_Hrs() {
		return mach_MTD_Idl_Hrs;
	}
	/**
	 * @param mach_MTD_Idl_Hrs The mach_MTD_Idl_Hrs to set.
	 */
	public void setMach_MTD_Idl_Hrs(int mach_MTD_Idl_Hrs) {
		this.mach_MTD_Idl_Hrs = mach_MTD_Idl_Hrs;
	}
	/**
	 * @return Returns the mach_Name.
	 */
	public String getMach_Name() {
		return mach_Name;
	}
	/**
	 * @param mach_Name The mach_Name to set.
	 */
	public void setMach_Name(String mach_Name) {
		this.mach_Name = mach_Name;
	}
	/**
	 * @return Returns the mach_SP_Addr.
	 */
	public String getMach_SP_Addr() {
		return mach_SP_Addr;
	}
	/**
	 * @param mach_SP_Addr The mach_SP_Addr to set.
	 */
	public void setMach_SP_Addr(String mach_SP_Addr) {
		this.mach_SP_Addr = mach_SP_Addr;
	}
	/**
	 * @return Returns the mach_SP_Cntct_Person.
	 */
	public String getMach_SP_Cntct_Person() {
		return mach_SP_Cntct_Person;
	}
	/**
	 * @param mach_SP_Cntct_Person The mach_SP_Cntct_Person to set.
	 */
	public void setMach_SP_Cntct_Person(String mach_SP_Cntct_Person) {
		this.mach_SP_Cntct_Person = mach_SP_Cntct_Person;
	}
	/**
	 * @return Returns the mach_SP_Name.
	 */
	public String getMach_SP_Name() {
		return mach_SP_Name;
	}
	/**
	 * @param mach_SP_Name The mach_SP_Name to set.
	 */
	public void setMach_SP_Name(String mach_SP_Name) {
		this.mach_SP_Name = mach_SP_Name;
	}
	/**
	 * @return Returns the mach_SP_Phone.
	 */
	public String getMach_SP_Phone() {
		return mach_SP_Phone;
	}
	/**
	 * @param mach_SP_Phone The mach_SP_Phone to set.
	 */
	public void setMach_SP_Phone(String mach_SP_Phone) {
		this.mach_SP_Phone = mach_SP_Phone;
	}
	/**
	 * @return Returns the mach_SPLR_Addr.
	 */
	public String getMach_SPLR_Addr() {
		return mach_SPLR_Addr;
	}
	/**
	 * @param mach_SPLR_Addr The mach_SPLR_Addr to set.
	 */
	public void setMach_SPLR_Addr(String mach_SPLR_Addr) {
		this.mach_SPLR_Addr = mach_SPLR_Addr;
	}
	/**
	 * @return Returns the mach_SPLR_Cntct_Person.
	 */
	public String getMach_SPLR_Cntct_Person() {
		return mach_SPLR_Cntct_Person;
	}
	/**
	 * @param mach_SPLR_Cntct_Person The mach_SPLR_Cntct_Person to set.
	 */
	public void setMach_SPLR_Cntct_Person(String mach_SPLR_Cntct_Person) {
		this.mach_SPLR_Cntct_Person = mach_SPLR_Cntct_Person;
	}
	/**
	 * @return Returns the mach_SPLR_Name.
	 */
	public String getMach_SPLR_Name() {
		return mach_SPLR_Name;
	}
	/**
	 * @param mach_SPLR_Name The mach_SPLR_Name to set.
	 */
	public void setMach_SPLR_Name(String mach_SPLR_Name) {
		this.mach_SPLR_Name = mach_SPLR_Name;
	}
	/**
	 * @return Returns the mach_SPLR_Phone.
	 */
	public String getMach_SPLR_Phone() {
		return mach_SPLR_Phone;
	}
	/**
	 * @param mach_SPLR_Phone The mach_SPLR_Phone to set.
	 */
	public void setMach_SPLR_Phone(String mach_SPLR_Phone) {
		this.mach_SPLR_Phone = mach_SPLR_Phone;
	}
	/**
	 * @return Returns the mach_Typ_Id.
	 */
	public int getMach_Typ_Id() {
		return mach_Typ_Id;
	}
	/**
	 * @param mach_Typ_Id The mach_Typ_Id to set.
	 */
	public void setMach_Typ_Id(int mach_Typ_Id) {
		this.mach_Typ_Id = mach_Typ_Id;
	}
	/**
	 * @return Returns the mach_Typ_Name.
	 */
	public String getMach_Typ_Name() {
		return mach_Typ_Name;
	}
	/**
	 * @param mach_Typ_Name The mach_Typ_Name to set.
	 */
	public void setMach_Typ_Name(String mach_Typ_Name) {
		this.mach_Typ_Name = mach_Typ_Name;
	}
}
/***
$Log: MachineDetails.java,v $
Revision 1.3  2004/11/18 16:10:29  sduraisamy
Implemented Serializable in MachineDetails entity object

Revision 1.2  2004/11/06 07:40:32  sduraisamy
Machine module modified for Log inclusion and change in Machine manager filter method

***/