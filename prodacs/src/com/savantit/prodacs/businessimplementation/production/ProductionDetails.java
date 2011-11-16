/*
 * Created on Dec 9, 2004
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
public class ProductionDetails implements Serializable
{
	private int prodId;
	private String mcCode;
	private Date prodCrntDate;
	private int shiftId;
	private String shiftName;
	private String prodWorkType;
	private boolean prodIncntvFlag;
	private String woNo;
	private int woId;
	private int woJbId;
	private String prodQtySnos;
	private int prodTotQty;
	private int prodStartOpn;
	private int prodEndOpn;
	private float prodStdHrs;
	private float prodTotHrs;
	private boolean prodUpdatePyrl;
	private boolean prodUpdateWo;
	private boolean prodPostFlg;
	private boolean isDeleted;
	private String prodTyp;
	private Date prodDateStamp;
	private int prodIsValid;
	private ProductionJobDetails objProductionJobDetails; 
	private Vector prodnEmpHrsDetails;
	private String createdBy;
	private String modifiedBy;
	private int modifyCount;
	public ProductionDetails()
	{
		prodId = 0;
		mcCode = "";
		shiftId = 0;
		shiftName = "";
		prodWorkType = "";
		prodTyp = "";
		prodIncntvFlag = false;
		woNo = "";
		woId = 0;
		woJbId = 0;
		prodQtySnos = "";
		prodTotQty = 0;
		prodStartOpn = 0;
		prodEndOpn = 0;
		prodStdHrs = 0;
		prodTotHrs = 0;
		prodUpdatePyrl = false;
		prodUpdateWo = false;
		prodPostFlg = false;
		isDeleted = false;
		prodIsValid = 0;
		createdBy = "";
		modifiedBy = "";
		modifyCount = 0;
		
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
	 * @return Returns the prodCrntDate.
	 */
	public Date getProdCrntDate() {
		return prodCrntDate;
	}
	/**
	 * @param prodCrntDate The prodCrntDate to set.
	 */
	public void setProdCrntDate(Date prodCrntDate) {
		this.prodCrntDate = prodCrntDate;
	}
	/**
	 * @return Returns the prodDateStamp.
	 */
	public Date getProdDateStamp() {
		return prodDateStamp;
	}
	/**
	 * @param prodDateStamp The prodDateStamp to set.
	 */
	public void setProdDateStamp(Date prodDateStamp) {
		this.prodDateStamp = prodDateStamp;
	}
	/**
	 * @return Returns the prodEndOpn.
	 */
	public int getProdEndOpn() {
		return prodEndOpn;
	}
	/**
	 * @param prodEndOpn The prodEndOpn to set.
	 */
	public void setProdEndOpn(int prodEndOpn) {
		this.prodEndOpn = prodEndOpn;
	}
	/**
	 * @return Returns the prodId.
	 */
	public int getProdId() {
		return prodId;
	}
	/**
	 * @param prodId The prodId to set.
	 */
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	/**
	 * @return Returns the prodIncntvFlag.
	 */
	public boolean isProdIncntvFlag() {
		return prodIncntvFlag;
	}
	/**
	 * @param prodIncntvFlag The prodIncntvFlag to set.
	 */
	public void setProdIncntvFlag(boolean prodIncntvFlag) {
		this.prodIncntvFlag = prodIncntvFlag;
	}
	/**
	 * @return Returns the prodIsValid.
	 */
	public int getProdIsValid() {
		return prodIsValid;
	}
	/**
	 * @param prodIsValid The prodIsValid to set.
	 */
	public void setProdIsValid(int prodIsValid) {
		this.prodIsValid = prodIsValid;
	}
	/**
	 * @return Returns the prodnEmpHrsDetails.
	 */
	public Vector getProdnEmpHrsDetails() {
		return prodnEmpHrsDetails;
	}
	/**
	 * @param prodnEmpHrsDetails The prodnEmpHrsDetails to set.
	 */
	public void setProdnEmpHrsDetails(Vector prodnEmpHrsDetails) {
		this.prodnEmpHrsDetails = prodnEmpHrsDetails;
	}
	/**
	 * @return Returns the prodPostFlg.
	 */
	public boolean isProdPostFlg() {
		return prodPostFlg;
	}
	/**
	 * @param prodPostFlg The prodPostFlg to set.
	 */
	public void setProdPostFlg(boolean prodPostFlg) {
		this.prodPostFlg = prodPostFlg;
	}
	/**
	 * @return Returns the prodQtyStartSno.
	 */
	public String getProdQtySnos() {
		return prodQtySnos;
	}
	/**
	 * @param prodQtyStartSno The prodQtyStartSno to set.
	 */
	public void setProdQtySnos(String prodQtySnos) {
		this.prodQtySnos = prodQtySnos;
	}
	/**
	 * @return Returns the prodStartOpn.
	 */
	public int getProdStartOpn() {
		return prodStartOpn;
	}
	/**
	 * @param prodStartOpn The prodStartOpn to set.
	 */
	public void setProdStartOpn(int prodStartOpn) {
		this.prodStartOpn = prodStartOpn;
	}
	/**
	 * @return Returns the prodStdHrs.
	 */
	public float getProdStdHrs() {
		return prodStdHrs;
	}
	/**
	 * @param prodStdHrs The prodStdHrs to set.
	 */
	public void setProdStdHrs(float prodStdHrs) {
		this.prodStdHrs = prodStdHrs;
	}
	/**
	 * @return Returns the prodTotHrs.
	 */
	public float getProdTotHrs() {
		return prodTotHrs;
	}
	/**
	 * @param prodTotHrs The prodTotHrs to set.
	 */
	public void setProdTotHrs(float prodTotHrs) {
		this.prodTotHrs = prodTotHrs;
	}
	/**
	 * @return Returns the prodTotQty.
	 */
	public int getProdTotQty() {
		return prodTotQty;
	}
	/**
	 * @param prodTotQty The prodTotQty to set.
	 */
	public void setProdTotQty(int prodTotQty) {
		this.prodTotQty = prodTotQty;
	}
	/**
	 * @return Returns the prodTyp.
	 */
	public String getProdTyp() {
		return prodTyp;
	}
	/**
	 * @param prodTyp The prodTyp to set.
	 */
	public void setProdTyp(String prodTyp) {
		this.prodTyp = prodTyp;
	}
	/**
	 * @return Returns the prodUpdatePyrl.
	 */
	public boolean isProdUpdatePyrl() {
		return prodUpdatePyrl;
	}
	/**
	 * @param prodUpdatePyrl The prodUpdatePyrl to set.
	 */
	public void setProdUpdatePyrl(boolean prodUpdatePyrl) {
		this.prodUpdatePyrl = prodUpdatePyrl;
	}
	/**
	 * @return Returns the prodUpdateWo.
	 */
	public boolean isProdUpdateWo() {
		return prodUpdateWo;
	}
	/**
	 * @param prodUpdateWo The prodUpdateWo to set.
	 */
	public void setProdUpdateWo(boolean prodUpdateWo) {
		this.prodUpdateWo = prodUpdateWo;
	}
	/**
	 * @return Returns the prodWorkType.
	 */
	public String getProdWorkType() {
		return prodWorkType;
	}
	/**
	 * @param prodWorkType The prodWorkType to set.
	 */
	public void setProdWorkType(String prodWorkType) {
		this.prodWorkType = prodWorkType;
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
$Log: ProductionDetails.java,v $
Revision 1.9  2005/06/02 09:59:48  kduraisamy
created by added for getProdDetails().

Revision 1.8  2005/05/30 06:31:57  kduraisamy
loging of  prod entries added.

Revision 1.7  2005/01/13 09:46:56  kduraisamy
field woId added.

Revision 1.6  2004/12/29 11:53:46  kduraisamy
startSno changed to qtySnos.

Revision 1.5  2004/12/23 10:11:32  sduraisamy
variable name started in uppercase modified in entityobject

Revision 1.4  2004/12/20 13:55:45  sduraisamy
shiftName included

Revision 1.3  2004/12/11 11:53:13  kduraisamy
addProductionDetails() added..

Revision 1.2  2004/12/10 06:18:42  kduraisamy
additional field Vector ProdnEmpHrs added.

Revision 1.1  2004/12/10 06:04:04  kduraisamy
initial commit.

***/