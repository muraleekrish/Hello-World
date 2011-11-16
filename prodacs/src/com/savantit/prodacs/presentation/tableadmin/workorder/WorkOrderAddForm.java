/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

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
public class WorkOrderAddForm extends ActionForm 
{
	private String woDate = "";
	private String woCustomerType = "0";
	private String woCustomerName = "0";
	private String woHash = "";
	private String woContactName = "";
	private String woEstCompletion = "";
	private String woJobName = "0";
	private String woJobQty = "";
	private String woJobQtyStartNo = "";
	private String woDrawingHash = "0";
	private String woRevisionHash = "";
	private String woMaterialType = "0";
	private String saveWorkOrder = "";
	private String formAction = "";
	private String woGenName = "0";
	private String selectJobOperations = "0";
	private String woDcNo = "";
	private String dcDate = "";
	
	private String jobId = "0";
	private String hidModFields = "";
	private String hidFinalWoList = "";
	private String hidFinalWoOperList = "";
	private String hidModOperDet = "";
	private String finalWoJobDet = "";
	private String finalWoOperDet = "";
	private String opnLevelIncentive = "";
	private String custType = "0";
	private String genName = "0";
	private String custName = "0";
	private String jbName = "0";
	private String dwgNo = "0";
	private String revNo = "0";
	private String matlType = "0";
	
	
	
	
	/**
	 * @return Returns the custType.
	 */
	public String getCustType() {
		return custType;
	}
	/**
	 * @param custType The custType to set.
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}
	/**
	 * @return Returns the custName.
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName The custName to set.
	 */
	public void setCustName(String custName) {
		this.custName = custName;
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
	 * @return Returns the genName.
	 */
	public String getGenName() {
		return genName;
	}
	/**
	 * @param genName The genName to set.
	 */
	public void setGenName(String genName) {
		this.genName = genName;
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
	 * @return Returns the revNo.
	 */
	public String getRevNo() {
		return revNo;
	}
	/**
	 * @param revNo The revNo to set.
	 */
	public void setRevNo(String revNo) {
		this.revNo = revNo;
	}
	/**
	 * @return Returns the opnLevelIncentive.
	 */
	public String getOpnLevelIncentive() {
		return opnLevelIncentive;
	}
	/**
	 * @param opnLevelIncentive The opnLevelIncentive to set.
	 */
	public void setOpnLevelIncentive(String opnLevelIncentive) {
		this.opnLevelIncentive = opnLevelIncentive;
	}
	/**
	 * @return Returns the dcDate.
	 */
	public String getDcDate() {
		return dcDate;
	}
	/**
	 * @param dcDate The dcDate to set.
	 */
	public void setDcDate(String dcDate) {
		this.dcDate = dcDate;
	}
	/**
	 * @return Returns the woDcNo.
	 */
	public String getWoDcNo() {
		return woDcNo;
	}
	/**
	 * @param woDcNo The woDcNo to set.
	 */
	public void setWoDcNo(String woDcNo) {
		this.woDcNo = woDcNo;
	}
	/**
	 * @return Returns the finalWoJobDet.
	 */
	public String getFinalWoJobDet() {
		return finalWoJobDet;
	}
	/**
	 * @param finalWoJobDet The finalWoJobDet to set.
	 */
	public void setFinalWoJobDet(String finalWoJobDet) {
		this.finalWoJobDet = finalWoJobDet;
	}
	/**
	 * @return Returns the finalWoOperDet.
	 */
	public String getFinalWoOperDet() {
		return finalWoOperDet;
	}
	/**
	 * @param finalWoOperDet The finalWoOperDet to set.
	 */
	public void setFinalWoOperDet(String finalWoOperDet) {
		this.finalWoOperDet = finalWoOperDet;
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
	 * @return Returns the hidFinalWoList.
	 */
	public String getHidFinalWoList() {
		return hidFinalWoList;
	}
	/**
	 * @param hidFinalWoList The hidFinalWoList to set.
	 */
	public void setHidFinalWoList(String hidFinalWoList) {
		this.hidFinalWoList = hidFinalWoList;
	}
	/**
	 * @return Returns the hidFinalWoOperList.
	 */
	public String getHidFinalWoOperList() {
		return hidFinalWoOperList;
	}
	/**
	 * @param hidFinalWoOperList The hidFinalWoOperList to set.
	 */
	public void setHidFinalWoOperList(String hidFinalWoOperList) {
		this.hidFinalWoOperList = hidFinalWoOperList;
	}
	/**
	 * @return Returns the hidModFields.
	 */
	public String getHidModFields() {
		return hidModFields;
	}
	/**
	 * @param hidModFields The hidModFields to set.
	 */
	public void setHidModFields(String hidModFields) {
		this.hidModFields = hidModFields;
	}
	/**
	 * @return Returns the hidModOperDet.
	 */
	public String getHidModOperDet() {
		return hidModOperDet;
	}
	/**
	 * @param hidModOperDet The hidModOperDet to set.
	 */
	public void setHidModOperDet(String hidModOperDet) {
		this.hidModOperDet = hidModOperDet;
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
	 * @return Returns the saveWorkOrder.
	 */
	public String getSaveWorkOrder() {
		return saveWorkOrder;
	}
	/**
	 * @param saveWorkOrder The saveWorkOrder to set.
	 */
	public void setSaveWorkOrder(String saveWorkOrder) {
		this.saveWorkOrder = saveWorkOrder;
	}
	/**
	 * @return Returns the selectJobOperations.
	 */
	public String getSelectJobOperations() {
		return selectJobOperations;
	}
	/**
	 * @param selectJobOperations The selectJobOperations to set.
	 */
	public void setSelectJobOperations(String selectJobOperations) {
		this.selectJobOperations = selectJobOperations;
	}
	/**
	 * @return Returns the woContactName.
	 */
	public String getWoContactName() {
		return woContactName;
	}
	/**
	 * @param woContactName The woContactName to set.
	 */
	public void setWoContactName(String woContactName) {
		this.woContactName = woContactName;
	}
	/**
	 * @return Returns the woCustomerName.
	 */
	public String getWoCustomerName() {
		return woCustomerName;
	}
	/**
	 * @param woCustomerName The woCustomerName to set.
	 */
	public void setWoCustomerName(String woCustomerName) {
		this.woCustomerName = woCustomerName;
	}
	/**
	 * @return Returns the woCustomerType.
	 */
	public String getWoCustomerType() {
		return woCustomerType;
	}
	/**
	 * @param woCustomerType The woCustomerType to set.
	 */
	public void setWoCustomerType(String woCustomerType) {
		this.woCustomerType = woCustomerType;
	}
	/**
	 * @return Returns the woDate.
	 */
	public String getWoDate() {
		return woDate;
	}
	/**
	 * @param woDate The woDate to set.
	 */
	public void setWoDate(String woDate) {
		this.woDate = woDate;
	}
	/**
	 * @return Returns the woDrawingHash.
	 */
	public String getWoDrawingHash() {
		return woDrawingHash;
	}
	/**
	 * @param woDrawingHash The woDrawingHash to set.
	 */
	public void setWoDrawingHash(String woDrawingHash) {
		this.woDrawingHash = woDrawingHash;
	}
	/**
	 * @return Returns the woEstCompletion.
	 */
	public String getWoEstCompletion() {
		return woEstCompletion;
	}
	/**
	 * @param woEstCompletion The woEstCompletion to set.
	 */
	public void setWoEstCompletion(String woEstCompletion) {
		this.woEstCompletion = woEstCompletion;
	}
	/**
	 * @return Returns the woGenName.
	 */
	public String getWoGenName() {
		return woGenName;
	}
	/**
	 * @param woGenName The woGenName to set.
	 */
	public void setWoGenName(String woGenName) {
		this.woGenName = woGenName;
	}
	/**
	 * @return Returns the woHash.
	 */
	public String getWoHash() {
		return woHash;
	}
	/**
	 * @param woHash The woHash to set.
	 */
	public void setWoHash(String woHash) {
		this.woHash = woHash;
	}
	/**
	 * @return Returns the woJobName.
	 */
	public String getWoJobName() {
		return woJobName;
	}
	/**
	 * @param woJobName The woJobName to set.
	 */
	public void setWoJobName(String woJobName) {
		this.woJobName = woJobName;
	}
	/**
	 * @return Returns the woJobQty.
	 */
	public String getWoJobQty() {
		return woJobQty;
	}
	/**
	 * @param woJobQty The woJobQty to set.
	 */
	public void setWoJobQty(String woJobQty) {
		this.woJobQty = woJobQty;
	}
	/**
	 * @return Returns the woJobQtyStartNo.
	 */
	public String getWoJobQtyStartNo() {
		return woJobQtyStartNo;
	}
	/**
	 * @param woJobQtyStartNo The woJobQtyStartNo to set.
	 */
	public void setWoJobQtyStartNo(String woJobQtyStartNo) {
		this.woJobQtyStartNo = woJobQtyStartNo;
	}
	/**
	 * @return Returns the woMaterialType.
	 */
	public String getWoMaterialType() {
		return woMaterialType;
	}
	/**
	 * @param woMaterialType The woMaterialType to set.
	 */
	public void setWoMaterialType(String woMaterialType) {
		this.woMaterialType = woMaterialType;
	}
	/**
	 * @return Returns the woRevisionHash.
	 */
	public String getWoRevisionHash() {
		return woRevisionHash;
	}
	/**
	 * @param woRevisionHash The woRevisionHash to set.
	 */
	public void setWoRevisionHash(String woRevisionHash) {
		this.woRevisionHash = woRevisionHash;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();
		if (formAction.equalsIgnoreCase("add"))
		{
			if(woDate==null || woDate.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","WorkOrder Date"));

			if(woEstCompletion==null || woEstCompletion.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","WorkOrder Estimated Completion Date"));

			if(woCustomerType==null || woCustomerType.trim().equals("0"))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","WorkOrder CusomerType"));

			if(woCustomerName==null || woCustomerName.trim().equals("0"))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","WorkOrder CusomerName"));

			if(woHash==null || woHash.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","WorkOrder No"));

			if(woContactName==null || woContactName.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","WorkOrder Contact Name"));
		}
		return errors;
	}	

}
/***
$Log: WorkOrderAddForm.java,v $
Revision 1.8  2005/11/19 14:07:59  vkrishnamoorthy
Variables added to restore the values.

Revision 1.7  2005/08/02 12:48:53  vkrishnamoorthy
Modified as per displaying Incentive, if exists.

Revision 1.6  2005/07/25 10:18:00  vkrishnamoorthy
WO DC Date added.

Revision 1.5  2005/07/23 12:03:11  vkrishnamoorthy
WoDCNo included.

Revision 1.4  2005/02/22 06:06:20  sponnusamy
WorkOrder Add newly generated.

Revision 1.3  2005/01/27 12:11:49  sponnusamy
JobName is changed as JobId(Appropriate changes made)

Revision 1.2  2004/12/15 16:28:07  sponnusamy
WorkOrder controlling part is partially completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/