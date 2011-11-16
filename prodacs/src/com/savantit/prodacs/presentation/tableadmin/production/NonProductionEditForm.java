/*
 * Created on Jan 4, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NonProductionEditForm extends ActionForm
{
	private String formAction="";
	private int id;
	private String[] ids = new String[0];
	
	private String proDate = "";
	private String proShift = "0";
	private String proMachine = "0";
	private String[] proEmployeeName = new String[0];
	private String proDutyHrs = "";
	private String proOTHrs = "";
	private String proTotalHours = "";
	private String nonProIdleRewrk = "0";
	private String idleBrkDwnRsn = "0";
	private String hidAllEmpDet = "";
	private String hidEmpDet = "";
	private String minQty = "";
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
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the ids.
	 */
	public String[] getIds() {
		return ids;
	}
	/**
	 * @param ids The ids to set.
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	/**
	 * @return Returns the hidAllEmpDet.
	 */
	public String getHidAllEmpDet() {
		return hidAllEmpDet;
	}
	/**
	 * @param hidAllEmpDet The hidAllEmpDet to set.
	 */
	public void setHidAllEmpDet(String hidAllEmpDet) {
		this.hidAllEmpDet = hidAllEmpDet;
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
	 * @return Returns the hidEmpDet.
	 */
	public String getHidEmpDet() {
		return hidEmpDet;
	}
	/**
	 * @param hidEmpDet The hidEmpDet to set.
	 */
	public void setHidEmpDet(String hidEmpDet) {
		this.hidEmpDet = hidEmpDet;
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

	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();

		if (formAction.equalsIgnoreCase("update"))
		{
			if(proDate==null || proDate.trim().length()<1 || proDate.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Non-Production Date"));
	
			if(proShift==null || proShift.trim().equals("0"))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Non-Production Shift"));
	
			if(proMachine==null || proMachine.trim().equals("0"))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Non-Production Machine"));
	
			if(idleBrkDwnRsn==null || idleBrkDwnRsn.trim().equals("0"))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Non-Production Reason"));
			
			if(proTotalHours==null || proTotalHours.trim().length() < 1 || proTotalHours.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Total Hours"));	
		}	
		return errors;
	}	

}

/***
$Log: NonProductionEditForm.java,v $
Revision 1.4  2005/05/30 12:34:25  vkrishnamoorthy
Modified as per log entries.

Revision 1.3  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/