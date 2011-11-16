/*
 * Created on Dec 14, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.production;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NonProductionDetails implements Serializable 
{
	private int nonProdId;
	private String mcCode;
	private Date nonProdnCrntDate;
	private int shiftId;
	private String shiftName;
	private String idlOrBkDown;
	private String idlOrBrkDwnRsn;
	private int rsnId;
	private String rsn;
	private float nprodTotHrs;
	private boolean nprodUpdatePyrl;
	private boolean nprodPostFlg;
	private boolean isDeleted;
	private Date nprodDateStamp;
	private int nprodIsValid;
	private Vector nonprodnEmpHrsDetails;
	private String createdBy;
	private String modifiedBy;
	private int modifyCount;
	
	public NonProductionDetails()
	{
	    this.nonProdId = 0;
	    this.mcCode = "";
	    this.nonProdnCrntDate = null;
	    this.shiftId = 0;
	    this.shiftName = "";
	    this.idlOrBkDown = "";
	    this.idlOrBrkDwnRsn = "";
	    this.rsnId = 0;
	    this.rsn = "";
	    this.nprodTotHrs = 0;
	    this.nprodIsValid = 1;
	    this.nprodUpdatePyrl = false;
	    this.nprodPostFlg = false;
		this.isDeleted = false;
		this.createdBy = "";
		this.modifiedBy = "";
		this.modifyCount = 0;
	}
	
	
	
	
	
	
	/**
	 * @return Returns the idlOrBkDown.
	 */
	public String getIdlOrBkDown() {
		return idlOrBkDown;
	}
	/**
	 * @param idlOrBkDown The idlOrBkDown to set.
	 */
	public void setIdlOrBkDown(String idlOrBkDown) {
		this.idlOrBkDown = idlOrBkDown;
	}
	/**
	 * @return Returns the idlOrBrkDwnRsn.
	 */
	public String getIdlOrBrkDwnRsn() {
		return idlOrBrkDwnRsn;
	}
	/**
	 * @param idlOrBrkDwnRsn The idlOrBrkDwnRsn to set.
	 */
	public void setIdlOrBrkDwnRsn(String idlOrBrkDwnRsn) {
		this.idlOrBrkDwnRsn = idlOrBrkDwnRsn;
	}
	/**
	 * @return Returns the isDeleted.
	 */
	public boolean isDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted The isDeleted to set.
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * @return Returns the mcCode.
	 */
	public String getMcCode() {
		return mcCode;
	}
	/**
	 * @param mcCode The mcCode to set.
	 */
	public void setMcCode(String mcCode) {
		this.mcCode = mcCode;
	}
	/**
	 * @return Returns the nonProdId.
	 */
	public int getNonProdId() {
		return nonProdId;
	}
	/**
	 * @param nonProdId The nonProdId to set.
	 */
	public void setNonProdId(int nonProdId) {
		this.nonProdId = nonProdId;
	}
	/**
	 * @return Returns the nonProdnCrntDate.
	 */
	public Date getNonProdnCrntDate() {
		return nonProdnCrntDate;
	}
	/**
	 * @param nonProdnCrntDate The nonProdnCrntDate to set.
	 */
	public void setNonProdnCrntDate(Date nonProdnCrntDate) {
		this.nonProdnCrntDate = nonProdnCrntDate;
	}
	
	/**
	 * @return Returns the nprodPostFlg.
	 */
	public boolean isNprodPostFlg() {
		return nprodPostFlg;
	}
	/**
	 * @param nprodPostFlg The nprodPostFlg to set.
	 */
	public void setNprodPostFlg(boolean nprodPostFlg) {
		this.nprodPostFlg = nprodPostFlg;
	}
	/**
	 * @return Returns the nprodTotHrs.
	 */
	public float getNprodTotHrs() {
		return nprodTotHrs;
	}
	/**
	 * @param nprodTotHrs The nprodTotHrs to set.
	 */
	public void setNprodTotHrs(float nprodTotHrs) {
		this.nprodTotHrs = nprodTotHrs;
	}
	/**
	 * @return Returns the nprodUpdatePyrl.
	 */
	public boolean isNprodUpdatePyrl() {
		return nprodUpdatePyrl;
	}
	/**
	 * @param nprodUpdatePyrl The nprodUpdatePyrl to set.
	 */
	public void setNprodUpdatePyrl(boolean nprodUpdatePyrl) {
		this.nprodUpdatePyrl = nprodUpdatePyrl;
	}
	/**
	 * @return Returns the rsnId.
	 */
	public int getRsnId() {
		return rsnId;
	}
	/**
	 * @param rsnId The rsnId to set.
	 */
	public void setRsnId(int rsnId) {
		this.rsnId = rsnId;
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
	 * @return Returns the nonprodnEmpHrsDetails.
	 */
	public Vector getNonprodnEmpHrsDetails() {
		return nonprodnEmpHrsDetails;
	}
	/**
	 * @param nonprodnEmpHrsDetails The nonprodnEmpHrsDetails to set.
	 */
	public void setNonprodnEmpHrsDetails(Vector nonprodnEmpHrsDetails) {
		this.nonprodnEmpHrsDetails = nonprodnEmpHrsDetails;
	}
	/**
	 * @return Returns the nprodDateStamp.
	 */
	public Date getNprodDateStamp() {
		return nprodDateStamp;
	}
	/**
	 * @param nprodDateStamp The nprodDateStamp to set.
	 */
	public void setNprodDateStamp(Date nprodDateStamp) {
		this.nprodDateStamp = nprodDateStamp;
	}
	/**
	 * @return Returns the nprodIsValid.
	 */
	public int getNprodIsValid() {
		return nprodIsValid;
	}
	/**
	 * @param nprodIsValid The nprodIsValid to set.
	 */
	public void setNprodIsValid(int nprodIsValid) {
		this.nprodIsValid = nprodIsValid;
	}
    /**
     * @return Returns the rsn.
     */
    public String getRsn()
    {
        return rsn;
    }
    /**
     * @param rsn The rsn to set.
     */
    public void setRsn(String rsn)
    {
        this.rsn = rsn;
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

