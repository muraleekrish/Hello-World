/*
 * Created on Jan 11, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.reworklog;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReworkLogDetails implements Serializable
{
	private int rwkLogId;
	private String woNo;
	private int woId;
	private String rwkCategory;
	private String rwkReason;
	private String jbName;
	private int jbId;
	private Date rwkDate;
	private int woJbId;
	private int rwkId;
	private String authorizedBy;
	private boolean rwkIsvalid;
	private Date rwkDateStamp;
	
	private Vector vecRwkLogEmpDetails;
	private Vector vecRwkLogJbQtyDetails;
	public ReworkLogDetails()
	{
		woNo = "";
		woId = 0;
		jbId = 0;
		woJbId = 0;
		rwkId = 0;
		rwkLogId = 0;
		authorizedBy = "";
		rwkCategory = "";
		rwkReason = "";
		jbName = "";
		rwkDate  = null;
		rwkIsvalid = true;
		vecRwkLogJbQtyDetails = null;
		vecRwkLogEmpDetails = null;
		
	}
	/**
	 * @return Returns the vecRwkLogJbQtyDetails.
	 */
	public Vector getVecRwkLogJbQtyDetails() {
		return vecRwkLogJbQtyDetails;
	}
	/**
	 * @return Returns the jbId.
	 */
	public int getJbId() {
		return jbId;
	}
	/**
	 * @param jbId The jbId to set.
	 */
	public void setJbId(int jbId) {
		this.jbId = jbId;
	}
	/**
	 * @param vecRwkLogJbQtyDetails The vecRwkLogJbQtyDetails to set.
	 */
	public void setVecRwkLogJbQtyDetails(Vector vecRwkLogJbQtyDetails) {
		this.vecRwkLogJbQtyDetails = vecRwkLogJbQtyDetails;
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
	 * @return Returns the authorizedBy.
	 */
	public String getAuthorizedBy() {
		return authorizedBy;
	}
	/**
	 * @param authorizedBy The authorizedBy to set.
	 */
	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}
	/**
	 * @return Returns the rwkId.
	 */
	public int getRwkId() {
		return rwkId;
	}
	/**
	 * @param rwkId The rwkId to set.
	 */
	public void setRwkId(int rwkId) {
		this.rwkId = rwkId;
	}
	/**
	 * @return Returns the jbName.
	 */
	public String getJbName() {
		return jbName;
	}
	/**
	 * @param jbName The jbName to set.
	 */
	public void setJbName(String jbName) {
		this.jbName = jbName;
	}
	/**
	 * @return Returns the rwkCategory.
	 */
	public String getRwkCategory() {
		return rwkCategory;
	}
	/**
	 * @param rwkCategory The rwkCategory to set.
	 */
	public void setRwkCategory(String rwkCategory) {
		this.rwkCategory = rwkCategory;
	}
	/**
	 * @return Returns the rwkDate.
	 */
	public Date getRwkDate() {
		return rwkDate;
	}
	/**
	 * @param rwkDate The rwkDate to set.
	 */
	public void setRwkDate(Date rwkDate) {
		this.rwkDate = rwkDate;
	}
	/**
	 * @return Returns the rwkReason.
	 */
	public String getRwkReason() {
		return rwkReason;
	}
	/**
	 * @param rwkReason The rwkReason to set.
	 */
	public void setRwkReason(String rwkReason) {
		this.rwkReason = rwkReason;
	}
	/**
	 * @return Returns the rwkLogId.
	 */
	public int getRwkLogId() {
		return rwkLogId;
	}
	/**
	 * @param rwkLogId The rwkLogId to set.
	 */
	public void setRwkLogId(int rwkLogId) {
		this.rwkLogId = rwkLogId;
	}
	/**
	 * @return Returns the rwkDateStamp.
	 */
	public Date getRwkDateStamp() {
		return rwkDateStamp;
	}
	/**
	 * @param rwkDateStamp The rwkDateStamp to set.
	 */
	public void setRwkDateStamp(Date rwkDateStamp) {
		this.rwkDateStamp = rwkDateStamp;
	}
	
	/**
	 * @return Returns the rwkIsvalid.
	 */
	public boolean isRwkIsvalid() {
		return rwkIsvalid;
	}
	/**
	 * @param rwkIsvalid The rwkIsvalid to set.
	 */
	public void setRwkIsvalid(boolean rwkIsvalid) {
		this.rwkIsvalid = rwkIsvalid;
	}
    /**
     * @return Returns the vecRwkLogEmpDetails.
     */
    public Vector getVecRwkLogEmpDetails()
    {
        return vecRwkLogEmpDetails;
    }
    /**
     * @param vecRwkLogEmpDetails The vecRwkLogEmpDetails to set.
     */
    public void setVecRwkLogEmpDetails(Vector vecRwkLogEmpDetails)
    {
        this.vecRwkLogEmpDetails = vecRwkLogEmpDetails;
    }
    }
