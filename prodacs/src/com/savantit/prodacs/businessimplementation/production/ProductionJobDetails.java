/*
 * Created on Dec 10, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.production;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ProductionJobDetails implements Serializable 
{
	private int jobId;
	private String jobName;
	private String dwgNo;
	private String rvsnNo;
	private String matlType;
	private int totQty;
	private String pendingQtySnos;
	private String postedQtySnos;
	private String unPostedQtySnos;
	private Date lastProdnDate;
	private int shiftId;
	//
	private int woJbId;
	
	public ProductionJobDetails()
	{
		this.jobId = 0;
		this.jobName = "";
		this.dwgNo = "";
		this.rvsnNo = "";
		this.matlType = "";
		this.totQty = 0;
		this.pendingQtySnos = "";
		this.postedQtySnos = "";
		this.unPostedQtySnos = "";
		this.lastProdnDate = null;
		this.shiftId = 0;
		//
		this.woJbId = 0;
	}
	
	
	/**
	 * @return Returns the dwgNo.
	 */
	public String getDwgNo() {
		return dwgNo;
	}
	/**
	 * @param dwgNo The dwgNo to set.
	 */
	public void setDwgNo(String dwgNo) {
		this.dwgNo = dwgNo;
	}
	/**
	 * @return Returns the jobId.
	 */
	public int getJobId() {
		return jobId;
	}
	/**
	 * @param jobId The jobId to set.
	 */
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return Returns the jobName.
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName The jobName to set.
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return Returns the lastProdnDate.
	 */
	public Date getLastProdnDate() {
		return lastProdnDate;
	}
	/**
	 * @param lastProdnDate The lastProdnDate to set.
	 */
	public void setLastProdnDate(Date lastProdnDate) {
		this.lastProdnDate = lastProdnDate;
	}
	/**
	 * @return Returns the matlType.
	 */
	public String getMatlType() {
		return matlType;
	}
	/**
	 * @param matlType The matlType to set.
	 */
	public void setMatlType(String matlType) {
		this.matlType = matlType;
	}
	/**
	 * @return Returns the pendingQtySnos.
	 */
	public String getPendingQtySnos() {
		return pendingQtySnos;
	}
	/**
	 * @param pendingQtySnos The pendingQtySnos to set.
	 */
	public void setPendingQtySnos(String pendingQtySnos) {
		this.pendingQtySnos = pendingQtySnos;
	}
	/**
	 * @return Returns the postedQtySnos.
	 */
	public String getPostedQtySnos() {
		return postedQtySnos;
	}
	/**
	 * @param postedQtySnos The postedQtySnos to set.
	 */
	public void setPostedQtySnos(String postedQtySnos) {
		this.postedQtySnos = postedQtySnos;
	}
	/**
	 * @return Returns the rvsnNo.
	 */
	public String getRvsnNo() {
		return rvsnNo;
	}
	/**
	 * @param rvsnNo The rvsnNo to set.
	 */
	public void setRvsnNo(String rvsnNo) {
		this.rvsnNo = rvsnNo;
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
	 * @return Returns the totQty.
	 */
	public int getTotQty() {
		return totQty;
	}
	/**
	 * @param totQty The totQty to set.
	 */
	public void setTotQty(int totQty) {
		this.totQty = totQty;
	}
	/**
	 * @return Returns the unPostedQtySnos.
	 */
	public String getUnPostedQtySnos() {
		return unPostedQtySnos;
	}
	/**
	 * @param unPostedQtySnos The unPostedQtySnos to set.
	 */
	public void setUnPostedQtySnos(String unPostedQtySnos) {
		this.unPostedQtySnos = unPostedQtySnos;
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
}
