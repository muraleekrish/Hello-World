/*
 * Created on Dec 15, 2004
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
public class UnPostedProductionDetails implements Serializable
{
	private int prodId;
	private String mcCode;
	private Date prodCrntDate;
	private int shiftId;
	private String shiftName;
	private String prodWorkType;
	private boolean prodIncntvFlag;
	private int woId;
	private String woNo;
	private int jobId;
	private String jobName;
	private String jobDrwgNo;
	private String jobRvsnNo;
	private String jobMatlType;
	private String jobQtySnos;
	private int jobQty;
	private int prodStartOpn;
	private int prodEndOpn;
	private String companyDCNo;
	private float prodStdHrs;
	private float prodTotHrs;
	
	public UnPostedProductionDetails()
	{
		 this.prodId = 0;
		 this.mcCode = "";
		 this.prodCrntDate = null;
		 this.shiftId = 0;
		 this.shiftName = "";
		 this.prodWorkType = "";
		 this.prodIncntvFlag = false;
		 this.woId = 0;
		 this.woNo = "";
		 this.jobId = 0;
		 this.jobName = "";
		 this.jobDrwgNo = "";
		 this.jobRvsnNo = "";
		 this.jobMatlType = "";
		 this.jobQtySnos = "";
		 this.jobQty = 0;
		 this.prodStartOpn = 0;
		 this.prodEndOpn = 0;
		 this.prodStdHrs = 0;
		 this.prodTotHrs = 0;
		 this.companyDCNo = "";
		
	}
	
	/**
	 * @return Returns the jobDrwgNo.
	 */
	public String getJobDrwgNo() {
		return jobDrwgNo;
	}
	/**
	 * @param jobDrwgNo The jobDrwgNo to set.
	 */
	public void setJobDrwgNo(String jobDrwgNo) {
		this.jobDrwgNo = jobDrwgNo;
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
	 * @return Returns the jobMatlType.
	 */
	public String getJobMatlType() {
		return jobMatlType;
	}
	/**
	 * @param jobMatlType The jobMatlType to set.
	 */
	public void setJobMatlType(String jobMatlType) {
		this.jobMatlType = jobMatlType;
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
	 * @return Returns the jobQty.
	 */
	public int getJobQty() {
		return jobQty;
	}
	/**
	 * @param jobQty The jobQty to set.
	 */
	public void setJobQty(int jobQty) {
		this.jobQty = jobQty;
	}
	/**
	 * @return Returns the jobQtySnos.
	 */
	public String getJobQtySnos() {
		return jobQtySnos;
	}
	/**
	 * @param jobQtySnos The jobQtySnos to set.
	 */
	public void setJobQtySnos(String jobQtySnos) {
		this.jobQtySnos = jobQtySnos;
	}
	/**
	 * @return Returns the jobRvsnNo.
	 */
	public String getJobRvsnNo() {
		return jobRvsnNo;
	}
	/**
	 * @param jobRvsnNo The jobRvsnNo to set.
	 */
	public void setJobRvsnNo(String jobRvsnNo) {
		this.jobRvsnNo = jobRvsnNo;
	}
	/**
	 * @return Returns the mcCode.
	 */
	public String getMcCode() {
		return mcCode;
	}
	/**
	 * @param mcCode The mcCode to set.
	 */
	public void setMcCode(String mcCode) {
		this.mcCode = mcCode;
	}
	/**
	 * @return Returns the prodCrntDate.
	 */
	public Date getProdCrntDate() {
		return prodCrntDate;
	}
	/**
	 * @param prodCrntDate The prodCrntDate to set.
	 */
	public void setProdCrntDate(Date prodCrntDate) {
		this.prodCrntDate = prodCrntDate;
	}
	/**
	 * @return Returns the prodEndOpn.
	 */
	public int getProdEndOpn() {
		return prodEndOpn;
	}
	/**
	 * @param prodEndOpn The prodEndOpn to set.
	 */
	public void setProdEndOpn(int prodEndOpn) {
		this.prodEndOpn = prodEndOpn;
	}
	/**
	 * @return Returns the prodId.
	 */
	public int getProdId() {
		return prodId;
	}
	/**
	 * @param prodId The prodId to set.
	 */
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	/**
	 * @return Returns the prodIncntvFlag.
	 */
	public boolean isProdIncntvFlag() {
		return prodIncntvFlag;
	}
	/**
	 * @param prodIncntvFlag The prodIncntvFlag to set.
	 */
	public void setProdIncntvFlag(boolean prodIncntvFlag) {
		this.prodIncntvFlag = prodIncntvFlag;
	}
	/**
	 * @return Returns the prodStartOpn.
	 */
	public int getProdStartOpn() {
		return prodStartOpn;
	}
	/**
	 * @param prodStartOpn The prodStartOpn to set.
	 */
	public void setProdStartOpn(int prodStartOpn) {
		this.prodStartOpn = prodStartOpn;
	}
	/**
	 * @return Returns the prodStdHrs.
	 */
	public float getProdStdHrs() {
		return prodStdHrs;
	}
	/**
	 * @param prodStdHrs The prodStdHrs to set.
	 */
	public void setProdStdHrs(float prodStdHrs) {
		this.prodStdHrs = prodStdHrs;
	}
	/**
	 * @return Returns the prodTotHrs.
	 */
	public float getProdTotHrs() {
		return prodTotHrs;
	}
	/**
	 * @param prodTotHrs The prodTotHrs to set.
	 */
	public void setProdTotHrs(float prodTotHrs) {
		this.prodTotHrs = prodTotHrs;
	}
	/**
	 * @return Returns the prodWorkType.
	 */
	public String getProdWorkType() {
		return prodWorkType;
	}
	/**
	 * @param prodWorkType The prodWorkType to set.
	 */
	public void setProdWorkType(String prodWorkType) {
		this.prodWorkType = prodWorkType;
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
	 * @return Returns the shiftName.
	 */
	public String getShiftName() {
		return shiftName;
	}
	/**
	 * @param shiftName The shiftName to set.
	 */
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
    /**
     * @return Returns the companyDCNo.
     */
    public String getCompanyDCNo()
    {
        return companyDCNo;
    }
    /**
     * @param companyDCNo The companyDCNo to set.
     */
    public void setCompanyDCNo(String companyDCNo)
    {
        this.companyDCNo = companyDCNo;
    }
}
