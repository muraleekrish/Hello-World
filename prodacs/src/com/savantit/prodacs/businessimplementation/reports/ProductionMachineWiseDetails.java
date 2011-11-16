/*
 * Created on Jun 2, 2005
 *
 * ClassName	:  	ProductionMachineWiseDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.reports;

import java.io.Serializable;
import java.util.Vector;



/**
 * @author kduraisamy
 *
 */
public class ProductionMachineWiseDetails implements Serializable
{
    private String mcName;
    private int prodId;
	private String mcCode;
	private String prodCrntDate;
	private int shiftId;
	private String shiftName;
	private String jobName;
	private String prodWorkType;
	private String prodIncntvFlag;
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
	private String prodPostFlg;
	private String prodTyp;
	private String prodDateStamp;
	private int prodIsValid;
	private String createdBy;
	private String modifiedBy;
	private int modifyCount;
	private Vector vecEmployeeDetails;
    
    public ProductionMachineWiseDetails()
    {
        mcCode = "";
        mcName = "";
        prodId = 0;
        shiftId = 0; 
    	shiftName = "";
    	jobName = "";
    	prodWorkType = "";
    	prodIncntvFlag = "";
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
    	prodPostFlg = "";
    	prodTyp = "";
    	prodCrntDate = "";
    	prodDateStamp = "";
    	prodIsValid = 0;
       	createdBy = "";
    	modifiedBy = "";
    	modifyCount = 0;
    	
        vecEmployeeDetails = new Vector();
    }
    /**
     * @return Returns the mcCode.
     */
    public String getMcCode()
    {
        return mcCode;
    }
    /**
     * @param mcCode The mcCode to set.
     */
    public void setMcCode(String mcCode)
    {
        this.mcCode = mcCode;
    }
    /**
     * @return Returns the mcName.
     */
    public String getMcName()
    {
        return mcName;
    }
    /**
     * @param mcName The mcName to set.
     */
    public void setMcName(String mcName)
    {
        this.mcName = mcName;
    }
    
    /**
     * @return Returns the vecEmployeeDetails.
     */
    public Vector getVecEmployeeDetails()
    {
        return vecEmployeeDetails;
    }
    /**
     * @param vecEmployeeDetails The vecEmployeeDetails to set.
     */
    public void setVecEmployeeDetails(Vector vecEmployeeDetails)
    {
        this.vecEmployeeDetails = vecEmployeeDetails;
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
     * @return Returns the jobName.
     */
    public String getJobName()
    {
        return jobName;
    }
    /**
     * @param jobName The jobName to set.
     */
    public void setJobName(String jobName)
    {
        this.jobName = jobName;
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
     * @return Returns the prodEndOpn.
     */
    public int getProdEndOpn()
    {
        return prodEndOpn;
    }
    /**
     * @param prodEndOpn The prodEndOpn to set.
     */
    public void setProdEndOpn(int prodEndOpn)
    {
        this.prodEndOpn = prodEndOpn;
    }
    /**
     * @return Returns the prodId.
     */
    public int getProdId()
    {
        return prodId;
    }
    /**
     * @param prodId The prodId to set.
     */
    public void setProdId(int prodId)
    {
        this.prodId = prodId;
    }
    /**
     * @return Returns the prodIsValid.
     */
    public int getProdIsValid()
    {
        return prodIsValid;
    }
    /**
     * @param prodIsValid The prodIsValid to set.
     */
    public void setProdIsValid(int prodIsValid)
    {
        this.prodIsValid = prodIsValid;
    }
    /**
     * @return Returns the prodQtySnos.
     */
    public String getProdQtySnos()
    {
        return prodQtySnos;
    }
    /**
     * @param prodQtySnos The prodQtySnos to set.
     */
    public void setProdQtySnos(String prodQtySnos)
    {
        this.prodQtySnos = prodQtySnos;
    }
    /**
     * @return Returns the prodStartOpn.
     */
    public int getProdStartOpn()
    {
        return prodStartOpn;
    }
    /**
     * @param prodStartOpn The prodStartOpn to set.
     */
    public void setProdStartOpn(int prodStartOpn)
    {
        this.prodStartOpn = prodStartOpn;
    }
    /**
     * @return Returns the prodStdHrs.
     */
    public float getProdStdHrs()
    {
        return prodStdHrs;
    }
    /**
     * @param prodStdHrs The prodStdHrs to set.
     */
    public void setProdStdHrs(float prodStdHrs)
    {
        this.prodStdHrs = prodStdHrs;
    }
    /**
     * @return Returns the prodTotHrs.
     */
    public float getProdTotHrs()
    {
        return prodTotHrs;
    }
    /**
     * @param prodTotHrs The prodTotHrs to set.
     */
    public void setProdTotHrs(float prodTotHrs)
    {
        this.prodTotHrs = prodTotHrs;
    }
    /**
     * @return Returns the prodTotQty.
     */
    public int getProdTotQty()
    {
        return prodTotQty;
    }
    /**
     * @param prodTotQty The prodTotQty to set.
     */
    public void setProdTotQty(int prodTotQty)
    {
        this.prodTotQty = prodTotQty;
    }
    /**
     * @return Returns the prodTyp.
     */
    public String getProdTyp()
    {
        return prodTyp;
    }
    /**
     * @param prodTyp The prodTyp to set.
     */
    public void setProdTyp(String prodTyp)
    {
        this.prodTyp = prodTyp;
    }
    /**
     * @return Returns the prodUpdatePyrl.
     */
    public boolean isProdUpdatePyrl()
    {
        return prodUpdatePyrl;
    }
    /**
     * @param prodUpdatePyrl The prodUpdatePyrl to set.
     */
    public void setProdUpdatePyrl(boolean prodUpdatePyrl)
    {
        this.prodUpdatePyrl = prodUpdatePyrl;
    }
    /**
     * @return Returns the prodUpdateWo.
     */
    public boolean isProdUpdateWo()
    {
        return prodUpdateWo;
    }
    /**
     * @param prodUpdateWo The prodUpdateWo to set.
     */
    public void setProdUpdateWo(boolean prodUpdateWo)
    {
        this.prodUpdateWo = prodUpdateWo;
    }
    /**
     * @return Returns the prodWorkType.
     */
    public String getProdWorkType()
    {
        return prodWorkType;
    }
    /**
     * @param prodWorkType The prodWorkType to set.
     */
    public void setProdWorkType(String prodWorkType)
    {
        this.prodWorkType = prodWorkType;
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
     * @return Returns the prodPostFlg.
     */
    public String getProdPostFlg()
    {
        return prodPostFlg;
    }
    /**
     * @param prodPostFlg The prodPostFlg to set.
     */
    public void setProdPostFlg(String prodPostFlg)
    {
        this.prodPostFlg = prodPostFlg;
    }
    /**
     * @return Returns the prodIncntvFlag.
     */
    public String getProdIncntvFlag()
    {
        return prodIncntvFlag;
    }
    /**
     * @param prodIncntvFlag The prodIncntvFlag to set.
     */
    public void setProdIncntvFlag(String prodIncntvFlag)
    {
        this.prodIncntvFlag = prodIncntvFlag;
    }
    /**
     * @return Returns the prodCrntDate.
     */
    public String getProdCrntDate()
    {
        return prodCrntDate;
    }
    /**
     * @param prodCrntDate The prodCrntDate to set.
     */
    public void setProdCrntDate(String prodCrntDate)
    {
        this.prodCrntDate = prodCrntDate;
    }
    /**
     * @return Returns the prodDateStamp.
     */
    public String getProdDateStamp()
    {
        return prodDateStamp;
    }
    /**
     * @param prodDateStamp The prodDateStamp to set.
     */
    public void setProdDateStamp(String prodDateStamp)
    {
        this.prodDateStamp = prodDateStamp;
    }
}

/*
*$Log: ProductionMachineWiseDetails.java,v $
*Revision 1.5  2005/06/10 14:22:07  kduraisamy
*prodCrntDate is changed to String.
*
*Revision 1.4  2005/06/10 13:20:10  kduraisamy
*time removed in date.
*
*Revision 1.3  2005/06/09 15:12:44  kduraisamy
*N is changed to Normal and DateStamp added to object.
*
*Revision 1.2  2005/06/09 14:30:15  kduraisamy
*unwanted field removed
*
*Revision 1.1  2005/06/09 06:06:07  kduraisamy
*initial commit.
*
*
*/