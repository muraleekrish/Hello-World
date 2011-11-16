/*
 * Created on Nov 23, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.job;

import java.io.Serializable;

/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OperationDetails implements Serializable
{
	private int opnSerialNo;
	private String opnName;
	private float opnStdHrs;
	private int opnGpId;
	private String opnGpCode;
	private boolean opnIncentive;
	public OperationDetails()
	{
		opnSerialNo = 0;
		opnName = "";
		opnStdHrs = 0;
		opnGpId = 0;
		opnGpCode = "";
		opnIncentive = false;
		
	}
	/**
	 * @return Returns the operationName.
	 */
	public String getOpnName() {
		return opnName;
	}
	/**
	 * @param operationName The operationName to set.
	 */
	public void setOpnName(String opnName) {
		this.opnName = opnName;
	}
	/**
	 * @return Returns the operationSerialNo.
	 */
	public int getOpnSerialNo() {
		return opnSerialNo;
	}
	/**
	 * @param operationSerialNo The operationSerialNo to set.
	 */
	public void setOpnSerialNo(int opnSerialNo) {
		this.opnSerialNo = opnSerialNo;
	}
	/**
	 * @return Returns the operationStdHrs.
	 */
	public float getOpnStdHrs() {
		return opnStdHrs;
	}
	/**
	 * @param operationStdHrs The operationStdHrs to set.
	 */
	public void setOpnStdHrs(float opnStdHrs) {
		this.opnStdHrs = opnStdHrs;
	}
	/**
	 * @return Returns the operationGpCode.
	 */
	public String getOpnGpCode() {
		return opnGpCode;
	}
	/**
	 * @param operationGpCode The operationGpCode to set.
	 */
	public void setOpnGpCode(String opnGpCode) {
		this.opnGpCode = opnGpCode;
	}
	/**
	 * @return Returns the operationGpId.
	 */
	public int getOpnGpId() {
		return opnGpId;
	}
	/**
	 * @param operationGpId The operationGpId to set.
	 */
	public void setOpnGpId(int opnGpId) {
		this.opnGpId = opnGpId;
	}
    
    /**
     * @return Returns the opnIncentive.
     */
    public boolean isOpnIncentive()
    {
        return opnIncentive;
    }
    /**
     * @param opnIncentive The opnIncentive to set.
     */
    public void setOpnIncentive(boolean opnIncentive)
    {
        this.opnIncentive = opnIncentive;
    }
}

/***
$Log: OperationDetails.java,v $
Revision 1.3  2005/07/05 06:14:33  kduraisamy
opnIncentive field added.

Revision 1.2  2004/11/24 07:27:56  kduraisamy
Log added

***/
