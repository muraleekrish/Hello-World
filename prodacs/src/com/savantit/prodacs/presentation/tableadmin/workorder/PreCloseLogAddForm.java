/*
 * Created on Nov 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PreCloseLogAddForm extends ActionForm 
{
	private String preCloseLogWOHash = "0";
	private String preCloseLogJobName = "0";
	private String preCloseLogJobQtySno = "";
	private String preCloseReason = "";
	private String formAction = "";
	
	private String woId = "";
	private String jobId = "";
	
	private String woJbStatId = "";
	private String jbQtySno = "";
	private String proEntOpns = "";
	private String compOpns = "";
	private String reworkOpns = "";
	private String openOpns = "";
	private String opnDetails = "";
	private String preClsDetVar = "0";
	private String flagOpnDet = "0";

	/**
	 * @return Returns the preCloseReason.
	 */
	public String getPreCloseReason() {
		return preCloseReason;
	}
	/**
	 * @param preCloseReason The preCloseReason to set.
	 */
	public void setPreCloseReason(String preCloseReason) {
		this.preCloseReason = preCloseReason;
	}
	/**
	 * @return Returns the preCloseLogJobName.
	 */
	public String getPreCloseLogJobName() {
		return preCloseLogJobName;
	}
	/**
	 * @param preCloseLogJobName The preCloseLogJobName to set.
	 */
	public void setPreCloseLogJobName(String preCloseLogJobName) {
		this.preCloseLogJobName = preCloseLogJobName;
	}
	/**
	 * @return Returns the preCloseLogJobQtySno.
	 */
	public String getPreCloseLogJobQtySno() {
		return preCloseLogJobQtySno;
	}
	/**
	 * @param preCloseLogJobQtySno The preCloseLogJobQtySno to set.
	 */
	public void setPreCloseLogJobQtySno(String preCloseLogJobQtySno) {
		this.preCloseLogJobQtySno = preCloseLogJobQtySno;
	}
	/**
	 * @return Returns the preCloseLogWOHash.
	 */
	public String getPreCloseLogWOHash() {
		return preCloseLogWOHash;
	}
	/**
	 * @param preCloseLogWOHash The preCloseLogWOHash to set.
	 */
	public void setPreCloseLogWOHash(String preCloseLogWOHash) {
		this.preCloseLogWOHash = preCloseLogWOHash;
	}
	/**
	 * @return Returns the woId.
	 */
	public String getWoId() {
		return woId;
	}
	/**
	 * @param woId The woId to set.
	 */
	public void setWoId(String woId) {
		this.woId = woId;
	}
	/**
	 * @return Returns the formAction.
	 */
	public String getFormAction() {
		return formAction;
	}
	/**
	 * @param formAction The formAction to set.
	 */
	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}
	/**
	 * @return Returns the jobId.
	 */
	public String getJobId() {
		return jobId;
	}
	/**
	 * @param jobId The jobId to set.
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return Returns the compOpns.
	 */
	public String getCompOpns() {
		return compOpns;
	}
	/**
	 * @param compOpns The compOpns to set.
	 */
	public void setCompOpns(String compOpns) {
		this.compOpns = compOpns;
	}
	/**
	 * @return Returns the jbQtySno.
	 */
	public String getJbQtySno() {
		return jbQtySno;
	}
	/**
	 * @param jbQtySno The jbQtySno to set.
	 */
	public void setJbQtySno(String jbQtySno) {
		this.jbQtySno = jbQtySno;
	}
	/**
	 * @return Returns the openOpns.
	 */
	public String getOpenOpns() {
		return openOpns;
	}
	/**
	 * @param openOpns The openOpns to set.
	 */
	public void setOpenOpns(String openOpns) {
		this.openOpns = openOpns;
	}
	/**
	 * @return Returns the proEntOpns.
	 */
	public String getProEntOpns() {
		return proEntOpns;
	}
	/**
	 * @param proEntOpns The proEntOpns to set.
	 */
	public void setProEntOpns(String proEntOpns) {
		this.proEntOpns = proEntOpns;
	}
	/**
	 * @return Returns the reworkOpns.
	 */
	public String getReworkOpns() {
		return reworkOpns;
	}
	/**
	 * @param reworkOpns The reworkOpns to set.
	 */
	public void setReworkOpns(String reworkOpns) {
		this.reworkOpns = reworkOpns;
	}
	/**
	 * @return Returns the woJbStatId.
	 */
	public String getWoJbStatId() {
		return woJbStatId;
	}
	/**
	 * @param woJbStatId The woJbStatId to set.
	 */
	public void setWoJbStatId(String woJbStatId) {
		this.woJbStatId = woJbStatId;
	}
	/**
	 * @return Returns the flagOpnDet.
	 */
	public String getFlagOpnDet() {
		return flagOpnDet;
	}
	/**
	 * @param flagOpnDet The flagOpnDet to set.
	 */
	public void setFlagOpnDet(String flagOpnDet) {
		this.flagOpnDet = flagOpnDet;
	}
	/**
	 * @return Returns the opnDetails.
	 */
	public String getOpnDetails() {
		return opnDetails;
	}
	/**
	 * @param opnDetails The opnDetails to set.
	 */
	public void setOpnDetails(String opnDetails) {
		this.opnDetails = opnDetails;
	}
	/**
	 * @return Returns the preClsDetVar.
	 */
	public String getPreClsDetVar() {
		return preClsDetVar;
	}
	/**
	 * @param preClsDetVar The preClsDetVar to set.
	 */
	public void setPreClsDetVar(String preClsDetVar) {
		this.preClsDetVar = preClsDetVar;
	}
}
/***
$Log: PreCloseLogAddForm.java,v $
Revision 1.3  2005/02/01 04:15:40  sponnusamy
PreClose reason added.

Revision 1.2  2004/12/21 11:23:08  sponnusamy
PreCloseLog Controller is fully completed.

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/