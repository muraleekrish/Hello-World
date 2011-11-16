/*
 * Created on Jan 3, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import org.apache.struts.action.ActionForm;


/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NonProductionAddForm extends ActionForm 
{
	private String proDate = "";
	private String proShift = "0";
	private String proMachine = "0";
	private String formAction = "";
	private String[] proEmployeeName = new String[0];
	private String proDutyHrs = "";
	private String proOTHrs = "";
	private String proTotalHours = "";
	private String nonProIdleRewrk = "0";
	private String idleBrkDwnRsn = "0";
	private String hidNonProdDet = "";
	private String minQty = "";
	private String showCount = "";
	private String hidUserId = "";
	
	
	/**
	 * @return Returns the hidUserId.
	 */
	public String getHidUserId() {
		return hidUserId;
	}
	/**
	 * @param hidUserId The hidUserId to set.
	 */
	public void setHidUserId(String hidUserId) {
		this.hidUserId = hidUserId;
	}
	/**
	 * @return Returns the showCount.
	 */
	public String getShowCount() {
		return showCount;
	}
	/**
	 * @param showCount The showCount to set.
	 */
	public void setShowCount(String showCount) {
		this.showCount = showCount;
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
	 * @return Returns the proDate.
	 */
	public String getProDate() {
		return proDate;
	}
	/**
	 * @param proDate The proDate to set.
	 */
	public void setProDate(String proDate) {
		this.proDate = proDate;
	}
	/**
	 * @return Returns the proDutyHrs.
	 */
	public String getProDutyHrs() {
		return proDutyHrs;
	}
	/**
	 * @param proDutyHrs The proDutyHrs to set.
	 */
	public void setProDutyHrs(String proDutyHrs) {
		this.proDutyHrs = proDutyHrs;
	}
	/**
	 * @return Returns the proEmployeeName.
	 */
	public String[] getProEmployeeName() {
		return proEmployeeName;
	}
	/**
	 * @param proEmployeeName The proEmployeeName to set.
	 */
	public void setProEmployeeName(String[] proEmployeeName) {
		this.proEmployeeName = proEmployeeName;
	}
	/**
	 * @return Returns the proMachine.
	 */
	public String getProMachine() {
		return proMachine;
	}
	/**
	 * @param proMachine The proMachine to set.
	 */
	public void setProMachine(String proMachine) {
		this.proMachine = proMachine;
	}
	/**
	 * @return Returns the proOTHrs.
	 */
	public String getProOTHrs() {
		return proOTHrs;
	}
	/**
	 * @param proOTHrs The proOTHrs to set.
	 */
	public void setProOTHrs(String proOTHrs) {
		this.proOTHrs = proOTHrs;
	}
	/**
	 * @return Returns the proShift.
	 */
	public String getProShift() {
		return proShift;
	}
	/**
	 * @param proShift The proShift to set.
	 */
	public void setProShift(String proShift) {
		this.proShift = proShift;
	}
	/**
	 * @return Returns the proTotalHours.
	 */
	public String getProTotalHours() {
		return proTotalHours;
	}
	/**
	 * @param proTotalHours The proTotalHours to set.
	 */
	public void setProTotalHours(String proTotalHours) {
		this.proTotalHours = proTotalHours;
	}
	/**
	 * @return Returns the nonProIdleRewrk.
	 */
	public String getNonProIdleRewrk() {
		return nonProIdleRewrk;
	}
	/**
	 * @param nonProIdleRewrk The nonProIdleRewrk to set.
	 */
	public void setNonProIdleRewrk(String nonProIdleRewrk) {
		this.nonProIdleRewrk = nonProIdleRewrk;
	}
	/**
	 * @return Returns the idleBrkDwnRsn.
	 */
	public String getIdleBrkDwnRsn() {
		return idleBrkDwnRsn;
	}
	/**
	 * @param idleBrkDwnRsn The idleBrkDwnRsn to set.
	 */
	public void setIdleBrkDwnRsn(String idleBrkDwnRsn) {
		this.idleBrkDwnRsn = idleBrkDwnRsn;
	}
	/**
	 * @return Returns the minQty.
	 */
	public String getMinQty() {
		return minQty;
	}
	/**
	 * @param minQty The minQty to set.
	 */
	public void setMinQty(String minQty) {
		this.minQty = minQty;
	}

	/**
	 * @return Returns the hidNonProdDet.
	 */
	public String getHidNonProdDet() {
		return hidNonProdDet;
	}
	/**
	 * @param hidNonProdDet The hidNonProdDet to set.
	 */
	public void setHidNonProdDet(String hidNonProdDet) {
		this.hidNonProdDet = hidNonProdDet;
	}
}

/***
$Log: NonProductionAddForm.java,v $
Revision 1.6  2005/05/30 12:34:25  vkrishnamoorthy
Modified as per log entries.

Revision 1.5  2005/03/09 11:01:33  sponnusamy
NonProduction Buffer Added

Revision 1.4  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/