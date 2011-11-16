/*
 * Created on Dec 20, 2004
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
public class UnPostedRadlProductionDetails implements Serializable
{
	private int radlProdId;
	private String mcCode;
	private Date radlProdCrntDate;
	private int shiftId;
	private String shiftName;
	private String radlProdWorkType;
	private boolean radlProdIncntvFlag;
	private int woId;
	private String woNo;
	private int jobId;
	private String jobName;
	private String jobDrwgNo;
	private String jobRvsnNo;
	private String jobMatlType;
	private String radlQtySnos;
	private int jobQty;
	private int radlProdStartOpn;
	private int radlProdEndOpn;
	private float radlProdStdHrs;
	private float radlProdTotHrs;
	private int radlMatlTypeId;
	private float radlDmtr;
	private float radlLength;
	private int radlNoOfHoles;
	private float radlPreDmtr;
	public UnPostedRadlProductionDetails()
	{
		radlProdId = 0;
		mcCode = "";
		radlProdCrntDate = null;
		shiftId = 0;
		shiftName = "";
		radlProdWorkType = "";
		radlProdIncntvFlag = false;
		woId = 0;
		woNo = "";
		jobId = 0;
		jobName = "" ;
		jobDrwgNo = "";
		jobRvsnNo = "";
		jobMatlType = "";
		radlProdStartOpn = 0;
		jobQty = 0;
		radlQtySnos = "";
		radlProdEndOpn = 0;
		radlProdStdHrs = 0;
		radlProdTotHrs = 0;
		radlMatlTypeId = 0;
		radlDmtr = 0;
		radlLength = 0;
		radlNoOfHoles = 0;
		radlPreDmtr = 0;
		
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
	 * @return Returns the radlDmtr.
	 */
	public float getRadlDmtr() {
		return radlDmtr;
	}
	/**
	 * @param radlDmtr The radlDmtr to set.
	 */
	public void setRadlDmtr(float radlDmtr) {
		this.radlDmtr = radlDmtr;
	}
	/**
	 * @return Returns the radlLength.
	 */
	public float getRadlLength() {
		return radlLength;
	}
	/**
	 * @param radlLength The radlLength to set.
	 */
	public void setRadlLength(float radlLength) {
		this.radlLength = radlLength;
	}
	/**
	 * @return Returns the radlMatlTypeId.
	 */
	public int getRadlMatlTypeId() {
		return radlMatlTypeId;
	}
	/**
	 * @param radlMatlTypeId The radlMatlTypeId to set.
	 */
	public void setRadlMatlTypeId(int radlMatlTypeId) {
		this.radlMatlTypeId = radlMatlTypeId;
	}
	/**
	 * @return Returns the radlNoOfHoles.
	 */
	public int getRadlNoOfHoles() {
		return radlNoOfHoles;
	}
	/**
	 * @param radlNoOfHoles The radlNoOfHoles to set.
	 */
	public void setRadlNoOfHoles(int radlNoOfHoles) {
		this.radlNoOfHoles = radlNoOfHoles;
	}
	/**
	 * @return Returns the radlPreDmtr.
	 */
	public float getRadlPreDmtr() {
		return radlPreDmtr;
	}
	/**
	 * @param radlPreDmtr The radlPreDmtr to set.
	 */
	public void setRadlPreDmtr(float radlPreDmtr) {
		this.radlPreDmtr = radlPreDmtr;
	}
	/**
	 * @return Returns the radlProdCrntDate.
	 */
	public Date getRadlProdCrntDate() {
		return radlProdCrntDate;
	}
	/**
	 * @param radlProdCrntDate The radlProdCrntDate to set.
	 */
	public void setRadlProdCrntDate(Date radlProdCrntDate) {
		this.radlProdCrntDate = radlProdCrntDate;
	}
	/**
	 * @return Returns the radlProdEndOpn.
	 */
	public int getRadlProdEndOpn() {
		return radlProdEndOpn;
	}
	/**
	 * @param radlProdEndOpn The radlProdEndOpn to set.
	 */
	public void setRadlProdEndOpn(int radlProdEndOpn) {
		this.radlProdEndOpn = radlProdEndOpn;
	}
	/**
	 * @return Returns the radlProdId.
	 */
	public int getRadlProdId() {
		return radlProdId;
	}
	/**
	 * @param radlProdId The radlProdId to set.
	 */
	public void setRadlProdId(int radlProdId) {
		this.radlProdId = radlProdId;
	}
	/**
	 * @return Returns the radlProdIncntvFlag.
	 */
	public boolean isRadlProdIncntvFlag() {
		return radlProdIncntvFlag;
	}
	/**
	 * @param radlProdIncntvFlag The radlProdIncntvFlag to set.
	 */
	public void setRadlProdIncntvFlag(boolean radlProdIncntvFlag) {
		this.radlProdIncntvFlag = radlProdIncntvFlag;
	}
	/**
	 * @return Returns the radlProdStdHrs.
	 */
	public float getRadlProdStdHrs() {
		return radlProdStdHrs;
	}
	/**
	 * @param radlProdStdHrs The radlProdStdHrs to set.
	 */
	public void setRadlProdStdHrs(float radlProdStdHrs) {
		this.radlProdStdHrs = radlProdStdHrs;
	}
	/**
	 * @return Returns the radlProdTotHrs.
	 */
	public float getRadlProdTotHrs() {
		return radlProdTotHrs;
	}
	/**
	 * @param radlProdTotHrs The radlProdTotHrs to set.
	 */
	public void setRadlProdTotHrs(float radlProdTotHrs) {
		this.radlProdTotHrs = radlProdTotHrs;
	}
	/**
	 * @return Returns the radlProdWorkType.
	 */
	public String getRadlProdWorkType() {
		return radlProdWorkType;
	}
	/**
	 * @param radlProdWorkType The radlProdWorkType to set.
	 */
	public void setRadlProdWorkType(String radlProdWorkType) {
		this.radlProdWorkType = radlProdWorkType;
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
	 * @return Returns the radlQtySnos.
	 */
	public String getRadlQtySnos() {
		return radlQtySnos;
	}
	/**
	 * @param radlQtySnos The radlQtySnos to set.
	 */
	public void setRadlQtySnos(String radlQtySnos) {
		this.radlQtySnos = radlQtySnos;
	}
	/**
	 * @return Returns the radlProdStartOpn.
	 */
	public int getRadlProdStartOpn() {
		return radlProdStartOpn;
	}
	/**
	 * @param radlProdStartOpn The radlProdStartOpn to set.
	 */
	public void setRadlProdStartOpn(int radlProdStartOpn) {
		this.radlProdStartOpn = radlProdStartOpn;
	}
}
