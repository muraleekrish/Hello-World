/*
 * Created on Jul 20, 2005
 *
 * ClassName	:  	DespatchClearanceDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.production;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 */
public class DespatchClearanceDetails implements Serializable
{
    private int despatchId;
	private Date despatchCrntDate;
	private int shiftId;
	private String shiftName;
	private String woNo;
	private int woId;
	private int woJbId;
	private String despatchQtySnos;
	private int despatchTotQty;
	private int despatchStartOpn;
	private int despatchEndOpn;
	private boolean despatchPostFlg;
	private Date despatchDateStamp;
	private int despatchIsValid;
	private ProductionJobDetails objProductionJobDetails; 
	private Vector despatchEmpHrsDetails;
	private String createdBy;
	private String modifiedBy;
	private int modifyCount;
	public DespatchClearanceDetails()
	{
	    this.despatchId = 0;
	    this.despatchCrntDate = null;
	    this.shiftId = 0;
	    this.shiftName = "";
	    this.woNo = "";
	    this.woJbId = 0;
	    this.despatchQtySnos = "";
	    this.despatchTotQty = 0;
	    this.despatchStartOpn = 0;
	    this.despatchEndOpn = 0;
	    this.despatchPostFlg = false;
	    this.despatchDateStamp = null;
	    this.despatchIsValid = 0;
	    this.objProductionJobDetails = null;
	    this.despatchEmpHrsDetails = null;
	    this.createdBy = "";
	    this.modifyCount = 0;
	    
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
     * @return Returns the despatchCrntDate.
     */
    public Date getDespatchCrntDate()
    {
        return despatchCrntDate;
    }
    /**
     * @param despatchCrntDate The despatchCrntDate to set.
     */
    public void setDespatchCrntDate(Date despatchCrntDate)
    {
        this.despatchCrntDate = despatchCrntDate;
    }
    /**
     * @return Returns the despatchDateStamp.
     */
    public Date getDespatchDateStamp()
    {
        return despatchDateStamp;
    }
    /**
     * @param despatchDateStamp The despatchDateStamp to set.
     */
    public void setDespatchDateStamp(Date despatchDateStamp)
    {
        this.despatchDateStamp = despatchDateStamp;
    }
    /**
     * @return Returns the despatchEmpHrsDetails.
     */
    public Vector getDespatchEmpHrsDetails()
    {
        return despatchEmpHrsDetails;
    }
    /**
     * @param despatchEmpHrsDetails The despatchEmpHrsDetails to set.
     */
    public void setDespatchEmpHrsDetails(Vector despatchEmpHrsDetails)
    {
        this.despatchEmpHrsDetails = despatchEmpHrsDetails;
    }
    /**
     * @return Returns the despatchEndOpn.
     */
    public int getDespatchEndOpn()
    {
        return despatchEndOpn;
    }
    /**
     * @param despatchEndOpn The despatchEndOpn to set.
     */
    public void setDespatchEndOpn(int despatchEndOpn)
    {
        this.despatchEndOpn = despatchEndOpn;
    }
    /**
     * @return Returns the despatchId.
     */
    public int getDespatchId()
    {
        return despatchId;
    }
    /**
     * @param despatchId The despatchId to set.
     */
    public void setDespatchId(int despatchId)
    {
        this.despatchId = despatchId;
    }
    /**
     * @return Returns the despatchIsValid.
     */
    public int getDespatchIsValid()
    {
        return despatchIsValid;
    }
    /**
     * @param despatchIsValid The despatchIsValid to set.
     */
    public void setDespatchIsValid(int despatchIsValid)
    {
        this.despatchIsValid = despatchIsValid;
    }
    /**
     * @return Returns the despatchPostFlg.
     */
    public boolean isDespatchPostFlg()
    {
        return despatchPostFlg;
    }
    /**
     * @param despatchPostFlg The despatchPostFlg to set.
     */
    public void setDespatchPostFlg(boolean despatchPostFlg)
    {
        this.despatchPostFlg = despatchPostFlg;
    }
    /**
     * @return Returns the despatchQtySnos.
     */
    public String getDespatchQtySnos()
    {
        return despatchQtySnos;
    }
    /**
     * @param despatchQtySnos The despatchQtySnos to set.
     */
    public void setDespatchQtySnos(String despatchQtySnos)
    {
        this.despatchQtySnos = despatchQtySnos;
    }
    /**
     * @return Returns the despatchStartOpn.
     */
    public int getDespatchStartOpn()
    {
        return despatchStartOpn;
    }
    /**
     * @param despatchStartOpn The despatchStartOpn to set.
     */
    public void setDespatchStartOpn(int despatchStartOpn)
    {
        this.despatchStartOpn = despatchStartOpn;
    }
    /**
     * @return Returns the despatchTotQty.
     */
    public int getDespatchTotQty()
    {
        return despatchTotQty;
    }
    /**
     * @param despatchTotQty The despatchTotQty to set.
     */
    public void setDespatchTotQty(int despatchTotQty)
    {
        this.despatchTotQty = despatchTotQty;
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
    /**
     * @return Returns the objProductionJobDetails.
     */
    public ProductionJobDetails getObjProductionJobDetails()
    {
        return objProductionJobDetails;
    }
    /**
     * @param objProductionJobDetails The objProductionJobDetails to set.
     */
    public void setObjProductionJobDetails(ProductionJobDetails objProductionJobDetails)
    {
        this.objProductionJobDetails = objProductionJobDetails;
    }
    /**
     * @return Returns the shiftId.
     */
    public int getShiftId()
    {
        return shiftId;
    }
    /**
     * @param shiftId The shiftId to set.
     */
    public void setShiftId(int shiftId)
    {
        this.shiftId = shiftId;
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
     * @return Returns the woId.
     */
    public int getWoId()
    {
        return woId;
    }
    /**
     * @param woId The woId to set.
     */
    public void setWoId(int woId)
    {
        this.woId = woId;
    }
    /**
     * @return Returns the woJbId.
     */
    public int getWoJbId()
    {
        return woJbId;
    }
    /**
     * @param woJbId The woJbId to set.
     */
    public void setWoJbId(int woJbId)
    {
        this.woJbId = woJbId;
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
*$Log: DespatchClearanceDetails.java,v $
*Revision 1.1  2005/07/20 11:56:10  kduraisamy
*INITIAL COMMIT.
*
*
*/