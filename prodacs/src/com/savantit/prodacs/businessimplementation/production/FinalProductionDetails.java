/*
 * Created on Jul 15, 2005
 *
 * ClassName	:  	FinalProductionDetails.java
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
public class FinalProductionDetails implements Serializable
{

	private int finalProdId;
	private Date finalCrntDate;
	private int shiftId;
	private String shiftName;
	private String woNo;
	private int woId;
	private int woJbId;
	private String finalProdQtySnos;
	private int finalProdTotQty;
	private int finalProdStartOpn;
	private int finalProdEndOpn;
	private float finalProdTotHrs;
	private boolean finalProdPostFlg;
	private Date finalProdDateStamp;
	private int finalProdIsValid;
	private ProductionJobDetails objProductionJobDetails; 
	private Vector prodnEmpHrsDetails;
	private String createdBy;
	private String modifiedBy;
	private int modifyCount;
	public FinalProductionDetails()
	{
		this.finalProdId = 0;
		this.shiftId = 0;
		this.shiftName = "";
		this.woNo = "";
		this.woId = 0;
		this.woJbId = 0;
		this.finalProdQtySnos = "";
		this.finalProdTotQty = 0;
		this.finalProdStartOpn = 0;
		this.finalProdEndOpn = 0;
		this.finalProdTotHrs = 0;
		this.finalProdPostFlg = false;
		this.finalProdIsValid = 0;
		this.createdBy = "";
		this.modifiedBy = "";
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
     * @return Returns the finalCrntDate.
     */
    public Date getFinalCrntDate()
    {
        return finalCrntDate;
    }
    /**
     * @param finalCrntDate The finalCrntDate to set.
     */
    public void setFinalCrntDate(Date finalCrntDate)
    {
        this.finalCrntDate = finalCrntDate;
    }
    /**
     * @return Returns the finalProdDateStamp.
     */
    public Date getFinalProdDateStamp()
    {
        return finalProdDateStamp;
    }
    /**
     * @param finalProdDateStamp The finalProdDateStamp to set.
     */
    public void setFinalProdDateStamp(Date finalProdDateStamp)
    {
        this.finalProdDateStamp = finalProdDateStamp;
    }
    /**
     * @return Returns the finalProdEndOpn.
     */
    public int getFinalProdEndOpn()
    {
        return finalProdEndOpn;
    }
    /**
     * @param finalProdEndOpn The finalProdEndOpn to set.
     */
    public void setFinalProdEndOpn(int finalProdEndOpn)
    {
        this.finalProdEndOpn = finalProdEndOpn;
    }
    /**
     * @return Returns the finalProdId.
     */
    public int getFinalProdId()
    {
        return finalProdId;
    }
    /**
     * @param finalProdId The finalProdId to set.
     */
    public void setFinalProdId(int finalProdId)
    {
        this.finalProdId = finalProdId;
    }
    /**
     * @return Returns the finalProdIsValid.
     */
    public int getFinalProdIsValid()
    {
        return finalProdIsValid;
    }
    /**
     * @param finalProdIsValid The finalProdIsValid to set.
     */
    public void setFinalProdIsValid(int finalProdIsValid)
    {
        this.finalProdIsValid = finalProdIsValid;
    }
    /**
     * @return Returns the finalProdPostFlg.
     */
    public boolean isFinalProdPostFlg()
    {
        return finalProdPostFlg;
    }
    /**
     * @param finalProdPostFlg The finalProdPostFlg to set.
     */
    public void setFinalProdPostFlg(boolean finalProdPostFlg)
    {
        this.finalProdPostFlg = finalProdPostFlg;
    }
    /**
     * @return Returns the finalProdQtySnos.
     */
    public String getFinalProdQtySnos()
    {
        return finalProdQtySnos;
    }
    /**
     * @param finalProdQtySnos The finalProdQtySnos to set.
     */
    public void setFinalProdQtySnos(String finalProdQtySnos)
    {
        this.finalProdQtySnos = finalProdQtySnos;
    }
    /**
     * @return Returns the finalProdStartOpn.
     */
    public int getFinalProdStartOpn()
    {
        return finalProdStartOpn;
    }
    /**
     * @param finalProdStartOpn The finalProdStartOpn to set.
     */
    public void setFinalProdStartOpn(int finalProdStartOpn)
    {
        this.finalProdStartOpn = finalProdStartOpn;
    }
    /**
     * @return Returns the finalProdTotQty.
     */
    public int getFinalProdTotQty()
    {
        return finalProdTotQty;
    }
    /**
     * @param finalProdTotQty The finalProdTotQty to set.
     */
    public void setFinalProdTotQty(int finalProdTotQty)
    {
        this.finalProdTotQty = finalProdTotQty;
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
     * @return Returns the prodnEmpHrsDetails.
     */
    public Vector getProdnEmpHrsDetails()
    {
        return prodnEmpHrsDetails;
    }
    /**
     * @param prodnEmpHrsDetails The prodnEmpHrsDetails to set.
     */
    public void setProdnEmpHrsDetails(Vector prodnEmpHrsDetails)
    {
        this.prodnEmpHrsDetails = prodnEmpHrsDetails;
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
    /**
     * @return Returns the finalProdTotHrs.
     */
    public float getFinalProdTotHrs()
    {
        return finalProdTotHrs;
    }
    /**
     * @param finalProdTotHrs The finalProdTotHrs to set.
     */
    public void setFinalProdTotHrs(float finalProdTotHrs)
    {
        this.finalProdTotHrs = finalProdTotHrs;
    }
}

/*
*$Log: FinalProductionDetails.java,v $
*Revision 1.2  2005/07/30 14:18:03  kduraisamy
*FPRO_TOT_HRS ADDED.
*
*Revision 1.1  2005/07/15 07:40:37  kduraisamy
*initial commit.
*
*
*/