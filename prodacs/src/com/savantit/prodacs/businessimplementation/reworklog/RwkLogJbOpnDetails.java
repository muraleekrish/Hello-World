/*
 * Created on Jan 11, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.reworklog;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RwkLogJbOpnDetails implements Serializable
{
	
	private String opnGpCode;
	private int opnSno;
	private float opnStdHrs;
	private String opnName;
	private int woJbOpnId;
	private boolean prodnEntered;
	public RwkLogJbOpnDetails()
	{
		opnGpCode = "";
	    opnSno = 0;
	    opnStdHrs = 0;
		opnName = "";
		woJbOpnId = 0;
		prodnEntered = false;
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
	 * @return Returns the opnSno.
	 */
	public int getOpnSno() {
		return opnSno;
	}
	/**
	 * @param opnSno The opnSno to set.
	 */
	public void setOpnSno(int opnSno) {
		this.opnSno = opnSno;
	}
	/**
	 * @return Returns the woJbOpnId.
	 */
	public int getWoJbOpnId() {
		return woJbOpnId;
	}
	/**
	 * @param woJbOpnId The woJbOpnId to set.
	 */
	public void setWoJbOpnId(int woJbOpnId) {
		this.woJbOpnId = woJbOpnId;
	}
	/**
	 * @return Returns the prodnEntered.
	 */
	public boolean isProdnEntered() {
		return prodnEntered;
	}
	/**
	 * @param prodnEntered The prodnEntered to set.
	 */
	public void setProdnEntered(boolean prodnEntered) {
		this.prodnEntered = prodnEntered;
	}
    /**
     * @return Returns the opnGpCode.
     */
    public String getOpnGpCode()
    {
        return opnGpCode;
    }
    /**
     * @param opnGpCode The opnGpCode to set.
     */
    public void setOpnGpCode(String opnGpCode)
    {
        this.opnGpCode = opnGpCode;
    }
    /**
     * @return Returns the opnStdHrs.
     */
    public float getOpnStdHrs()
    {
        return opnStdHrs;
    }
    /**
     * @param opnStdHrs The opnStdHrs to set.
     */
    public void setOpnStdHrs(float opnStdHrs)
    {
        this.opnStdHrs = opnStdHrs;
    }
}
