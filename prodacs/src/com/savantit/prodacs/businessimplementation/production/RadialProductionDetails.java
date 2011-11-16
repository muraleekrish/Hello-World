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
public class RadialProductionDetails implements Serializable 
{
    private int radlId;
	private String mcCode;
	private Date radlCrntDate;
	private int shiftId;
	private String shiftName;
	private String radlWorkType;
	private boolean radlIncntvFlg;
	private String woNo;
	private int woId;
	private int woJbId;
	private int radlStartOpn;
	private int radlEndOpn;
	private String radlQtySnos;
	private int radlTotQty;
	private float radlStdHrs;
	private float radlTotHrs;
	private boolean radlUpdatePyrl;
	private boolean radlUpdateWo;
	private boolean radlPostFlg;
	private int radlMatlTypeId;
	private String radlMatlTypeName;
	private double radlDiameter;
	private double radlLength;
	private int radlNoOfHoles;
	private double radlPreDiameter;
	private boolean radlCompletionFlg;
	private boolean isDeleted;
	private Date radlDateStamp;
	private int radlIsValid;
	private ProductionJobDetails objProductionJobDetails;
	private Vector radlEmpHrsDetails;
	private String createdBy;
	private String modifiedBy;
	private int modifyCount;
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
	public RadialProductionDetails()
	{
	    createdBy = "";
	    modifiedBy = "";
	}
	
	/**
     * @return Returns the radlMatlTypeName.
     */
    public String getRadlMatlTypeName()
    {
        return radlMatlTypeName;
    }
    /**
     * @param radlMatlTypeName The radlMatlTypeName to set.
     */
    public void setRadlMatlTypeName(String radlMatlTypeName)
    {
        this.radlMatlTypeName = radlMatlTypeName;
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
	 * @return Returns the radlCompletionFlg.
	 */
	public boolean isRadlCompletionFlg() {
		return radlCompletionFlg;
	}
	/**
	 * @param radlCompletionFlg The radlCompletionFlg to set.
	 */
	public void setRadlCompletionFlg(boolean radlCompletionFlg) {
		this.radlCompletionFlg = radlCompletionFlg;
	}
	/**
	 * @return Returns the radlCrntDate.
	 */
	public Date getRadlCrntDate() {
		return radlCrntDate;
	}
	/**
	 * @param radlCrntDate The radlCrntDate to set.
	 */
	public void setRadlCrntDate(Date radlCrntDate) {
		this.radlCrntDate = radlCrntDate;
	}
	/**
	 * @return Returns the radlDiametre.
	 */
	public double getRadlDiameter() {
		return radlDiameter;
	}
	/**
	 * @param radlDiametre The radlDiametre to set.
	 */
	public void setRadlDiameter(double radlDiameter) {
		this.radlDiameter = radlDiameter;
	}
	
	/**
	 * @return Returns the radlEndOpn.
	 */
	public int getRadlEndOpn() {
		return radlEndOpn;
	}
	/**
	 * @param radlEndOpn The radlEndOpn to set.
	 */
	public void setRadlEndOpn(int radlEndOpn) {
		this.radlEndOpn = radlEndOpn;
	}
	/**
	 * @return Returns the radlId.
	 */
	public int getRadlId() {
		return radlId;
	}
	/**
	 * @param radlId The radlId to set.
	 */
	public void setRadlId(int radlId) {
		this.radlId = radlId;
	}
	/**
	 * @return Returns the radlIncntvFlg.
	 */
	public boolean isRadlIncntvFlg() {
		return radlIncntvFlg;
	}
	/**
	 * @param radlIncntvFlg The radlIncntvFlg to set.
	 */
	public void setRadlIncntvFlg(boolean radlIncntvFlg) {
		this.radlIncntvFlg = radlIncntvFlg;
	}
	/**
	 * @return Returns the radlLength.
	 */
	public double getRadlLength() {
		return radlLength;
	}
	/**
	 * @param radlLength The radlLength to set.
	 */
	public void setRadlLength(double radlLength) {
		this.radlLength = radlLength;
	}
	/**
	 * @return Returns the radlMatlTypeId.
	 */
	public int getRadlMatlTypeId() {
		return radlMatlTypeId;
	}
	/**
	 * @param radlMatlTypeId The radlMatlTypeId to set.
	 */
	public void setRadlMatlTypeId(int radlMatlTypeId) {
		this.radlMatlTypeId = radlMatlTypeId;
	}
	/**
	 * @return Returns the radlNoOfHoles.
	 */
	public int getRadlNoOfHoles() {
		return radlNoOfHoles;
	}
	/**
	 * @param radlNoOfHoles The radlNoOfHoles to set.
	 */
	public void setRadlNoOfHoles(int radlNoOfHoles) {
		this.radlNoOfHoles = radlNoOfHoles;
	}
	/**
	 * @return Returns the radlPostFlg.
	 */
	public boolean isRadlPostFlg() {
		return radlPostFlg;
	}
	/**
	 * @param radlPostFlg The radlPostFlg to set.
	 */
	public void setRadlPostFlg(boolean radlPostFlg) {
		this.radlPostFlg = radlPostFlg;
	}
	/**
	 * @return Returns the radlPreDiametre.
	 */
	public double getRadlPreDiameter() {
		return radlPreDiameter;
	}
	/**
	 * @param radlPreDiametre The radlPreDiametre to set.
	 */
	public void setRadlPreDiameter(double radlPreDiameter) {
		this.radlPreDiameter = radlPreDiameter;
	}
	/**
	 * @return Returns the radlQtySnos.
	 */
	public String getRadlQtySnos() {
		return radlQtySnos;
	}
	/**
	 * @param radlQtyStartSno The radlQtySnos to set.
	 */
	public void setRadlQtySnos(String radlQtySnos) {
		this.radlQtySnos = radlQtySnos;
	}
	/**
	 * @return Returns the radlStartOpn.
	 */
	public int getRadlStartOpn() {
		return radlStartOpn;
	}
	/**
	 * @param radlStartOpn The radlStartOpn to set.
	 */
	public void setRadlStartOpn(int radlStartOpn) {
		this.radlStartOpn = radlStartOpn;
	}
	/**
	 * @return Returns the radlStdHrs.
	 */
	public float getRadlStdHrs() {
		return radlStdHrs;
	}
	/**
	 * @param radlStdHrs The radlStdHrs to set.
	 */
	public void setRadlStdHrs(float radlStdHrs) {
		this.radlStdHrs = radlStdHrs;
	}
	/**
	 * @return Returns the radlTotHrs.
	 */
	public float getRadlTotHrs() {
		return radlTotHrs;
	}
	/**
	 * @param radlTotHrs The radlTotHrs to set.
	 */
	public void setRadlTotHrs(float radlTotHrs) {
		this.radlTotHrs = radlTotHrs;
	}
	/**
	 * @return Returns the radlTotQty.
	 */
	public int getRadlTotQty() {
		return radlTotQty;
	}
	/**
	 * @param radlTotQty The radlTotQty to set.
	 */
	public void setRadlTotQty(int radlTotQty) {
		this.radlTotQty = radlTotQty;
	}
	/**
	 * @return Returns the radlUpdatePyrl.
	 */
	public boolean isRadlUpdatePyrl() {
		return radlUpdatePyrl;
	}
	/**
	 * @param radlUpdatePyrl The radlUpdatePyrl to set.
	 */
	public void setRadlUpdatePyrl(boolean radlUpdatePyrl) {
		this.radlUpdatePyrl = radlUpdatePyrl;
	}
	/**
	 * @return Returns the radlUpdateWo.
	 */
	public boolean isRadlUpdateWo() {
		return radlUpdateWo;
	}
	/**
	 * @param radlUpdateWo The radlUpdateWo to set.
	 */
	public void setRadlUpdateWo(boolean radlUpdateWo) {
		this.radlUpdateWo = radlUpdateWo;
	}
	/**
	 * @return Returns the radlWorkType.
	 */
	public String getRadlWorkType() {
		return radlWorkType;
	}
	/**
	 * @param radlWorkType The radlWorkType to set.
	 */
	public void setRadlWorkType(String radlWorkType) {
		this.radlWorkType = radlWorkType;
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
	 * @return Returns the woJbId.
	 */
	public int getWoJbId() {
		return woJbId;
	}
	/**
	 * @param woJbId The woJbId to set.
	 */
	public void setWoJbId(int woJbId) {
		this.woJbId = woJbId;
	}
	/**
	 * @return Returns the radlDateStamp.
	 */
	public Date getRadlDateStamp() {
		return radlDateStamp;
	}
	/**
	 * @param radlDateStamp The radlDateStamp to set.
	 */
	public void setRadlDateStamp(Date radlDateStamp) {
		this.radlDateStamp = radlDateStamp;
	}
	/**
	 * @return Returns the radlIsValid.
	 */
	public int getRadlIsValid() {
		return radlIsValid;
	}
	/**
	 * @param radlIsValid The radlIsValid to set.
	 */
	public void setRadlIsValid(int radlIsValid) {
		this.radlIsValid = radlIsValid;
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
	 * @return Returns the objProductionJobDetails.
	 */
	public ProductionJobDetails getObjProductionJobDetails() {
		return objProductionJobDetails;
	}
	/**
	 * @param objProductionJobDetails The objProductionJobDetails to set.
	 */
	public void setObjProductionJobDetails(
			ProductionJobDetails objProductionJobDetails) {
		this.objProductionJobDetails = objProductionJobDetails;
	}
	/**
	 * @return Returns the radlEmpHrsDetails.
	 */
	public Vector getRadlEmpHrsDetails() {
		return radlEmpHrsDetails;
	}
	/**
	 * @param radlEmpHrsDetails The radlEmpHrsDetails to set.
	 */
	public void setRadlEmpHrsDetails(Vector radlEmpHrsDetails) {
		this.radlEmpHrsDetails = radlEmpHrsDetails;
	}
	/**
	 * @return Returns the woNo.
	 */
	public String getWoNo() {
		return woNo;
	}
	/**
	 * @param woNo The woNo to set.
	 */
	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}
	/**
	 * @return Returns the woId.
	 */
	public int getWoId() {
		return woId;
	}
	/**
	 * @param woId The woId to set.
	 */
	public void setWoId(int woId) {
		this.woId = woId;
	}
}
