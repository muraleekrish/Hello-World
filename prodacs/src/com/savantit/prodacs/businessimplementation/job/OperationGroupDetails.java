/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.job;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**	
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OperationGroupDetails implements Serializable 
{
	private int operationGroupId;
	private String operationGroupCode;
	private Date opnGpDateStamp;
	private int opnGpIsValid;
	private boolean machineRelated;
	HashMap hm_MachineDetails = null; 
	public OperationGroupDetails()
	{
		operationGroupId = 0;
		operationGroupCode = "";
		opnGpDateStamp = null;
		opnGpIsValid = 0;
		hm_MachineDetails = new HashMap();
		machineRelated = false;
	}
	
	/**
	 * @return Returns the operationGroupId.
	 */
	public int getOperationGroupId() {
		return operationGroupId;
	}
	/**
	 * @param operationGroupId The operationGroupId to set.
	 */
	public void setOperationGroupId(int operationGroupId) {
		this.operationGroupId = operationGroupId;
	}
	
	/**
	 * @return Returns the opnGpDateStamp.
	 */
	public Date getOpnGpDateStamp() {
		return opnGpDateStamp;
	}
	/**
	 * @param opnGpDateStamp The opnGpDateStamp to set.
	 */
	public void setOpnGpDateStamp(Date opnGpDateStamp) {
		this.opnGpDateStamp = opnGpDateStamp;
	}
	/**
	 * @return Returns the opnGpIsValid.
	 */
	public int getOpnGpIsValid() {
		return opnGpIsValid;
	}
	/**
	 * @param opnGpIsValid The opnGpIsValid to set.
	 */
	public void setOpnGpIsValid(int opnGpIsValid) {
		this.opnGpIsValid = opnGpIsValid;
	}
	
	/**
	 * @return Returns the hm_MachineDetails.
	 */
	public HashMap getHm_MachineDetails() {
		return hm_MachineDetails;
	}
	/**
	 * @param hm_MachineDetails The hm_MachineDetails to set.
	 */
	public void setHm_MachineDetails(HashMap hm_MachineDetails) {
		this.hm_MachineDetails = hm_MachineDetails;
	}
	/**
	 * @return Returns the operationGroupCode.
	 */
	public String getOperationGroupCode() {
		return operationGroupCode;
	}
	/**
	 * @param operationGroupCode The operationGroupCode to set.
	 */
	public void setOperationGroupCode(String operationGroupCode) {
		this.operationGroupCode = operationGroupCode;
	}
    /**
     * @return Returns the machineRelated.
     */
    public boolean isMachineRelated()
    {
        return machineRelated;
    }
    /**
     * @param machineRelated The machineRelated to set.
     */
    public void setMachineRelated(boolean machineRelated)
    {
        this.machineRelated = machineRelated;
    }
}
/***
$Log: OperationGroupDetails.java,v $
Revision 1.9  2005/07/14 11:21:15  kduraisamy
OPN GP MCRELATED FIELD ADDED.

Revision 1.8  2004/11/23 12:31:01  sduraisamy
JobDetails ,OperationGroupDetails ,OperationDetails Entity Objects modified and errors Corrected

Revision 1.7  2004/11/21 11:43:43  sduraisamy
Operation Serial No,Operation Std Hrs and Operation Name removed

Revision 1.6  2004/11/18 08:54:45  kduraisamy
operationStdHrs name changed.

Revision 1.5  2004/11/18 08:52:18  kduraisamy
operationStdHrs name changed.

Revision 1.4  2004/11/18 07:53:29  sduraisamy
operation sno,stdhrs,operation name included

Revision 1.3  2004/11/15 07:03:19  kduraisamy
initial commit

Revision 1.2  2004/11/09 04:58:20  kduraisamy
Log added.

***/