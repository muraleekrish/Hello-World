/*
 * Created on Dec 16, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.production;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PopDetails implements Serializable
{
	private int popId;
	//private String mcCode;
	private Date popCrntDate;
	private int shiftId;
	private String shiftName;
	private String popRsn;
	private boolean popUpdatePyrl;
	private boolean popPostFlg;
	private boolean popIsDeleted;
	private int empId;
	private String empName;
	private String empTypName;
	private int popIsValid;
	private Date popDateStamp;
	private Vector popEmpHrsDetails;
	private String createdBy;
	private String modifiedBy;
	private int modifyCount;
	
	public PopDetails()
	{
		
		this.popId = 0;
		//this.mcCode = "";
		this.shiftId = 0;
		this.shiftName = "";
		this.empId = 0;
		this.empName = "";
		this.empTypName = "";
		this.popRsn = "";
		this.popUpdatePyrl = false;
		this.popPostFlg = false;
		this.popIsDeleted = false;
		this.popIsValid = 0;
		this.createdBy = "";
		this.modifiedBy = "";
		this.modifyCount = 0;
	}
	/**
	 * @return Returns the popCrntDate.
	 */
	public Date getPopCrntDate() {
		return popCrntDate;
	}
	/**
	 * @param popCrntDate The popCrntDate to set.
	 */
	public void setPopCrntDate(Date popCrntDate) {
		this.popCrntDate = popCrntDate;
	}
	/**
	 * @return Returns the popId.
	 */
	public int getPopId() {
		return popId;
	}
	/**
	 * @param popId The popId to set.
	 */
	public void setPopId(int popId) {
		this.popId = popId;
	}
	/**
	 * @return Returns the popIsDeleted.
	 */
	public boolean isPopIsDeleted() {
		return popIsDeleted;
	}
	/**
	 * @param popIsDeleted The popIsDeleted to set.
	 */
	public void setPopIsDeleted(boolean popIsDeleted) {
		this.popIsDeleted = popIsDeleted;
	}
	/**
	 * @return Returns the popPostFlg.
	 */
	public boolean isPopPostFlg() {
		return popPostFlg;
	}
	/**
	 * @param popPostFlg The popPostFlg to set.
	 */
	public void setPopPostFlg(boolean popPostFlg) {
		this.popPostFlg = popPostFlg;
	}
	/**
	 * @return Returns the popRsn.
	 */
	public String getPopRsn() {
		return popRsn;
	}
	/**
	 * @param popRsn The popRsn to set.
	 */
	public void setPopRsn(String popRsn) {
		this.popRsn = popRsn;
	}
	/**
	 * @return Returns the popUpdatePyrl.
	 */
	public boolean isPopUpdatePyrl() {
		return popUpdatePyrl;
	}
	/**
	 * @param popUpdatePyrl The popUpdatePyrl to set.
	 */
	public void setPopUpdatePyrl(boolean popUpdatePyrl) {
		this.popUpdatePyrl = popUpdatePyrl;
	}
	/**
	 * @return Returns the shiftId.
	 */
	public int getShiftId() {
		return shiftId;
	}
	/**
	 * @param shiftId The shiftId to set.
	 */
	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}
	/**
	 * @return Returns the shiftName.
	 */
	public String getShiftName() {
		return shiftName;
	}
	/**
	 * @param shiftName The shiftName to set.
	 */
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	/**
	 * @return Returns the popEmpHrsDetails.
	 */
	public Vector getPopEmpHrsDetails() {
		return popEmpHrsDetails;
	}
	/**
	 * @param popEmpHrsDetails The popEmpHrsDetails to set.
	 */
	public void setPopEmpHrsDetails(Vector popEmpHrsDetails) {
		this.popEmpHrsDetails = popEmpHrsDetails;
	}
	/**
	 * @return Returns the empId.
	 */
	public int getEmpId() {
		return empId;
	}
	/**
	 * @param empId The empId to set.
	 */
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	/**
	 * @return Returns the empName.
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName The empName to set.
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return Returns the empTypName.
	 */
	public String getEmpTypName() {
		return empTypName;
	}
	/**
	 * @param empTypName The empTypName to set.
	 */
	public void setEmpTypName(String empTypName) {
		this.empTypName = empTypName;
	}
	/**
	 * @return Returns the popDateStamp.
	 */
	public Date getPopDateStamp() {
		return popDateStamp;
	}
	/**
	 * @param popDateStamp The popDateStamp to set.
	 */
	public void setPopDateStamp(Date popDateStamp) {
		this.popDateStamp = popDateStamp;
	}
	/**
	 * @return Returns the popIsValid.
	 */
	public int getPopIsValid() {
		return popIsValid;
	}
	/**
	 * @param popIsValid The popIsValid to set.
	 */
	public void setPopIsValid(int popIsValid) {
		this.popIsValid = popIsValid;
	}
    /**
     * @return Returns the createdBy.
     */
    public String getCreatedBy()
    {
        return createdBy;
    }
    /**
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    /**
     * @return Returns the modifiedBy.
     */
    public String getModifiedBy()
    {
        return modifiedBy;
    }
    /**
     * @param modifiedBy The modifiedBy to set.
     */
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
    /**
     * @return Returns the modifyCount.
     */
    public int getModifyCount()
    {
        return modifyCount;
    }
    /**
     * @param modifyCount The modifyCount to set.
     */
    public void setModifyCount(int modifyCount)
    {
        this.modifyCount = modifyCount;
    }
}
/***
$Log: PopDetails.java,v $
Revision 1.5  2005/08/08 07:18:05  kduraisamy
modify count field added.

Revision 1.4  2005/06/16 05:02:01  kduraisamy
mc_code removed.

Revision 1.3  2005/05/30 13:10:22  kduraisamy
POP AND RADL_DUMMYCOUNT ADDED.

Revision 1.2  2004/12/20 13:53:33  sduraisamy
isvalid and datestamp included

Revision 1.1  2004/12/16 12:23:50  sduraisamy
Initial Commit

***/