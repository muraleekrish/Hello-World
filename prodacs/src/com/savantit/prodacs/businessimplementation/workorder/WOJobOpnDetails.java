/*
 * Created on Dec 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.workorder;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WOJobOpnDetails implements Serializable 
{
	private int opnSerialNo;
	private int woJbOpnId;
	private String opnName;
	private float opnStdHrs;
	private String opnStatus;
	private int opnGpId;
	private String opnGpCode;
	private boolean opnIncentive;
	
	public WOJobOpnDetails()
	{
		this.opnSerialNo = 0;
		this.woJbOpnId = 0;
		this.opnName = "";
		this.opnStdHrs = 0;
		this.opnStatus = "";
		this.opnGpId = 0;
		this.opnGpCode = "";
		this.opnIncentive = false;
		
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
	/**
	 * @return Returns the opnGpCode.
	 */
	public String getOpnGpCode() {
		return opnGpCode;
	}
	/**
	 * @param opnGpCode The opnGpCode to set.
	 */
	public void setOpnGpCode(String opnGpCode) {
		this.opnGpCode = opnGpCode;
	}
	/**
	 * @return Returns the opnGpId.
	 */
	public int getOpnGpId() {
		return opnGpId;
	}
	/**
	 * @param opnGpId The opnGpId to set.
	 */
	public void setOpnGpId(int opnGpId) {
		this.opnGpId = opnGpId;
	}
	/**
	 * @return Returns the opnName.
	 */
	public String getOpnName() {
		return opnName;
	}
	/**
	 * @param opnName The opnName to set.
	 */
	public void setOpnName(String opnName) {
		this.opnName = opnName;
	}
	/**
	 * @return Returns the opnSerialNo.
	 */
	public int getOpnSerialNo() {
		return opnSerialNo;
	}
	/**
	 * @param opnSerialNo The opnSerialNo to set.
	 */
	public void setOpnSerialNo(int opnSerialNo) {
		this.opnSerialNo = opnSerialNo;
	}
	/**
	 * @return Returns the opnStdHrs.
	 */
	public float getOpnStdHrs() {
		return opnStdHrs;
	}
	/**
	 * @param opnStdHrs The opnStdHrs to set.
	 */
	public void setOpnStdHrs(float opnStdHrs) {
		this.opnStdHrs = opnStdHrs;
	}
	/**
	 * @return Returns the woJbOpnNo.
	 */
	public int getWoJbOpnId() {
		return woJbOpnId;
	}
	/**
	 * @param woJbOpnNo The woJbOpnNo to set.
	 */
	public void setWoJbOpnId(int woJbOpnId) {
		this.woJbOpnId = woJbOpnId;
	}
    /**
     * @return Returns the opnStatus.
     */
    public String getOpnStatus()
    {
        return opnStatus;
    }
    /**
     * @param opnStatus The opnStatus to set.
     */
    public void setOpnStatus(String opnStatus)
    {
        this.opnStatus = opnStatus;
    }
}
/***
$Log: WOJobOpnDetails.java,v $
Revision 1.5  2005/08/09 04:46:52  kduraisamy
statuses added in wo view.

Revision 1.4  2005/07/15 07:40:37  kduraisamy
initial commit.

Revision 1.3  2004/12/20 05:36:12  kduraisamy
new field woJbOpnId included.

Revision 1.2  2004/12/09 05:54:46  kduraisamy
Log added.

***/