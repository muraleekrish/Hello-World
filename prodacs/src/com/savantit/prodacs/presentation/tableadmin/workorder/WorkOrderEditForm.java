/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WorkOrderEditForm extends ActionForm 
{
	private String woDate = "";
	private String woCustomerType = "0";
	private String woCustomerName = "0";
	private String woHash = "";
	private String woContactName = "";
	private String woEstCompletion = "";
	private String woJobs = "";
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
	private String opnLevelIncentive = "";
	
	
	private String jobId = "0";
	private String hidModFields = "";
	private String hidFinalWoList = "";
	private String hidFinalWoOperList = "";
	private String hidModOperDet = "";
	private String finalWoJobDet = "";
	private String finalWoOperDet = "";
	private String hidModifyRecDet;
	private int id;
	private String[] ids = new String[0];
	

	
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
	 * @return Returns the hidModifyRecDet.
	 */
	public String getHidModifyRecDet() {
		return hidModifyRecDet;
	}
	/**
	 * @param hidModifyRecDet The hidModifyRecDet to set.
	 */
	public void setHidModifyRecDet(String hidModifyRecDet) {
		this.hidModifyRecDet = hidModifyRecDet;
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
	 * @return Returns the woJobs.
	 */
	public String getWoJobs() {
		return woJobs;
	}
	/**
	 * @param woJobs The woJobs to set.
	 */
	public void setWoJobs(String woJobs) {
		this.woJobs = woJobs;
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
		return errors;
	}
}
/***
$Log: WorkOrderEditForm.java,v $
Revision 1.8  2005/08/02 14:39:42  vkrishnamoorthy
Modified as per displaying Incentive, if exists.

Revision 1.7  2005/07/25 10:18:00  vkrishnamoorthy
WO DC Date added.

Revision 1.6  2005/07/24 08:57:26  vkrishnamoorthy
DC No included.

Revision 1.5  2005/02/24 11:49:00  vkrishnamoorthy
Partially modified.

Revision 1.4  2005/01/27 12:11:41  sponnusamy
JobName is changed as JobId(Appropriate changes made)

Revision 1.3  2004/12/16 09:20:34  sponnusamy
WorkOrder Controller is fully completed.

Revision 1.2  2004/12/15 16:28:07  sponnusamy
WorkOrder controlling part is partially completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/