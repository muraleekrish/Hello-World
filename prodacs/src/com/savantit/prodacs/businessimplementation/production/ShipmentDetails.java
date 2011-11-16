/*
 * Created on Jul 21, 2005
 *
 * ClassName	:  	ShipmentDetails.java
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


/**
 * @author kduraisamy
 *
 */
public class ShipmentDetails implements Serializable
{

    private int shipmentId;
	private Date shipmentCrntDate;
	private int shiftId;
	private String shiftName;
	private String woNo;
	private int woId;
	private int woJbId;
	private String shipmentQtySnos;
	private int shipmentTotQty;
	private int shipmentStartOpn;
	private int shipmentEndOpn;
	private boolean shipmentPostFlg;
	private Date shipmentDateStamp;
	private int shipmentIsValid;
	private ProductionJobDetails objProductionJobDetails; 
	private String createdBy;
	private String modifiedBy;
	private int modifyCount;
	private String deliveryChallanNo;
	public ShipmentDetails()
	{
	    this.shipmentId = 0;
	    this.shipmentCrntDate = null;
	    this.shiftId = 0;
	    this.shiftName = "";
	    this.woNo = "";
	    this.woId = 0;
	    this.woJbId = 0;
	    this.shipmentQtySnos = "";
	    this.shipmentTotQty = 0;
	    this.shipmentStartOpn = 0;
	    this.shipmentEndOpn = 0;
	    this.shipmentPostFlg = false;
	    this.shipmentDateStamp = null;
	    this.shipmentIsValid = 0;
	    this.objProductionJobDetails = null;
	    this.createdBy = "";
	    this.modifiedBy = "";
	    this.modifyCount = 0;
	    this.deliveryChallanNo = "";
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
     * @return Returns the deliveryChallanNo.
     */
    public String getDeliveryChallanNo()
    {
        return deliveryChallanNo;
    }
    /**
     * @param deliveryChallanNo The deliveryChallanNo to set.
     */
    public void setDeliveryChallanNo(String deliveryChallanNo)
    {
        this.deliveryChallanNo = deliveryChallanNo;
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
     * @return Returns the shipmentCrntDate.
     */
    public Date getShipmentCrntDate()
    {
        return shipmentCrntDate;
    }
    /**
     * @param shipmentCrntDate The shipmentCrntDate to set.
     */
    public void setShipmentCrntDate(Date shipmentCrntDate)
    {
        this.shipmentCrntDate = shipmentCrntDate;
    }
    /**
     * @return Returns the shipmentDateStamp.
     */
    public Date getShipmentDateStamp()
    {
        return shipmentDateStamp;
    }
    /**
     * @param shipmentDateStamp The shipmentDateStamp to set.
     */
    public void setShipmentDateStamp(Date shipmentDateStamp)
    {
        this.shipmentDateStamp = shipmentDateStamp;
    }
    /**
     * @return Returns the shipmentEndOpn.
     */
    public int getShipmentEndOpn()
    {
        return shipmentEndOpn;
    }
    /**
     * @param shipmentEndOpn The shipmentEndOpn to set.
     */
    public void setShipmentEndOpn(int shipmentEndOpn)
    {
        this.shipmentEndOpn = shipmentEndOpn;
    }
    /**
     * @return Returns the shipmentId.
     */
    public int getShipmentId()
    {
        return shipmentId;
    }
    /**
     * @param shipmentId The shipmentId to set.
     */
    public void setShipmentId(int shipmentId)
    {
        this.shipmentId = shipmentId;
    }
    /**
     * @return Returns the shipmentIsValid.
     */
    public int getShipmentIsValid()
    {
        return shipmentIsValid;
    }
    /**
     * @param shipmentIsValid The shipmentIsValid to set.
     */
    public void setShipmentIsValid(int shipmentIsValid)
    {
        this.shipmentIsValid = shipmentIsValid;
    }
    /**
     * @return Returns the shipmentPostFlg.
     */
    public boolean isShipmentPostFlg()
    {
        return shipmentPostFlg;
    }
    /**
     * @param shipmentPostFlg The shipmentPostFlg to set.
     */
    public void setShipmentPostFlg(boolean shipmentPostFlg)
    {
        this.shipmentPostFlg = shipmentPostFlg;
    }
    /**
     * @return Returns the shipmentQtySnos.
     */
    public String getShipmentQtySnos()
    {
        return shipmentQtySnos;
    }
    /**
     * @param shipmentQtySnos The shipmentQtySnos to set.
     */
    public void setShipmentQtySnos(String shipmentQtySnos)
    {
        this.shipmentQtySnos = shipmentQtySnos;
    }
    /**
     * @return Returns the shipmentStartOpn.
     */
    public int getShipmentStartOpn()
    {
        return shipmentStartOpn;
    }
    /**
     * @param shipmentStartOpn The shipmentStartOpn to set.
     */
    public void setShipmentStartOpn(int shipmentStartOpn)
    {
        this.shipmentStartOpn = shipmentStartOpn;
    }
    /**
     * @return Returns the shipmentTotQty.
     */
    public int getShipmentTotQty()
    {
        return shipmentTotQty;
    }
    /**
     * @param shipmentTotQty The shipmentTotQty to set.
     */
    public void setShipmentTotQty(int shipmentTotQty)
    {
        this.shipmentTotQty = shipmentTotQty;
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
*$Log: ShipmentDetails.java,v $
*Revision 1.2  2005/07/22 09:10:22  kduraisamy
*imports organized.
*
*Revision 1.1  2005/07/22 07:07:34  kduraisamy
*INITIAL COMMIT.
*
*
*/