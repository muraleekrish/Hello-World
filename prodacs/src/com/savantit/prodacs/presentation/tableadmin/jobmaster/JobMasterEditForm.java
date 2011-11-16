/*
 * Created on Nov 3, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.jobmaster;

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
public class JobMasterEditForm extends ActionForm
{
	private String date;
	private String jobName;
	private String customerType;
	private String customerName;
	private String generalName;
	private String drawing;
	private String revision;
	private String materialType;
	private String incentive = "";
	private String transferOperations;
	private String transCustomerName;
	private String transGeneralName;
	private String transJobName;
	private String transfer;
	private String checkJobOperations;
	private String selectJobOperations;
	private String jobOperationName;
	private String jobStandardHrs;
	private String saveOperations;
	private String grpId;
	private String sno;
	private String opGrpName;
	private String opName;
	private String stdHrs;
	private String opnLevelIncntv;
	private String custTypId;
	private String custId;
	private String opnLevelIncentive;
	
	private String formAction="";
	private int id;
	private String[] ids = new String[0];
	private String transCustomerType = "0";
	private String customerId = "0";
	private String trCustId = "0";
	private String trCustomerId = "0";
	private String trGenId = "0";
	private String trJobId = "0";


	
	/**
	 * @return Returns the checkJobOperations.
	 */
	public String getCheckJobOperations() {
		return checkJobOperations;
	}
	/**
	 * @param checkJobOperations The checkJobOperations to set.
	 */
	public void setCheckJobOperations(String checkJobOperations) {
		this.checkJobOperations = checkJobOperations;
	}
	/**
	 * @return Returns the customerName.
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName The customerName to set.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return Returns the customerType.
	 */
	public String getCustomerType() {
		return customerType;
	}
	/**
	 * @param customerType The customerType to set.
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return Returns the drawing.
	 */
	public String getDrawing() {
		return drawing;
	}
	/**
	 * @param drawing The drawing to set.
	 */
	public void setDrawing(String drawing) {
		this.drawing = drawing;
	}
	/**
	 * @return Returns the generalName.
	 */
	public String getGeneralName() {
		return generalName;
	}
	/**
	 * @param generalName The generalName to set.
	 */
	public void setGeneralName(String generalName) {
		this.generalName = generalName;
	}
	/**
	 * @return Returns the incentive.
	 */
	public String getIncentive() {
		return incentive;
	}
	/**
	 * @param incentive The incentive to set.
	 */
	public void setIncentive(String incentive) {
		this.incentive = incentive;
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
	 * @return Returns the jobOperationName.
	 */
	public String getJobOperationName() {
		return jobOperationName;
	}
	/**
	 * @param jobOperationName The jobOperationName to set.
	 */
	public void setJobOperationName(String jobOperationName) {
		this.jobOperationName = jobOperationName;
	}
	/**
	 * @return Returns the jobStandardHrs.
	 */
	public String getJobStandardHrs() {
		return jobStandardHrs;
	}
	/**
	 * @param jobStandardHrs The jobStandardHrs to set.
	 */
	public void setJobStandardHrs(String jobStandardHrs) {
		this.jobStandardHrs = jobStandardHrs;
	}
	/**
	 * @return Returns the materialType.
	 */
	public String getMaterialType() {
		return materialType;
	}
	/**
	 * @param materialType The materialType to set.
	 */
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	/**
	 * @return Returns the revision.
	 */
	public String getRevision() {
		return revision;
	}
	/**
	 * @param revision The revision to set.
	 */
	public void setRevision(String revision) {
		this.revision = revision;
	}
	/**
	 * @return Returns the saveOperations.
	 */
	public String getSaveOperations() {
		return saveOperations;
	}
	/**
	 * @param saveOperations The saveOperations to set.
	 */
	public void setSaveOperations(String saveOperations) {
		this.saveOperations = saveOperations;
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
	 * @return Returns the transCustomerName.
	 */
	public String getTransCustomerName() {
		return transCustomerName;
	}
	/**
	 * @param transCustomerName The transCustomerName to set.
	 */
	public void setTransCustomerName(String transCustomerName) {
		this.transCustomerName = transCustomerName;
	}
	/**
	 * @return Returns the transfer.
	 */
	public String getTransfer() {
		return transfer;
	}
	/**
	 * @param transfer The transfer to set.
	 */
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}
	/**
	 * @return Returns the transferOperations.
	 */
	public String getTransferOperations() {
		return transferOperations;
	}
	/**
	 * @param transferOperations The transferOperations to set.
	 */
	public void setTransferOperations(String transferOperations) {
		this.transferOperations = transferOperations;
	}
	/**
	 * @return Returns the transGeneralName.
	 */
	public String getTransGeneralName() {
		return transGeneralName;
	}
	/**
	 * @param transGeneralName The transGeneralName to set.
	 */
	public void setTransGeneralName(String transGeneralName) {
		this.transGeneralName = transGeneralName;
	}
	/**
	 * @return Returns the transJobName.
	 */
	public String getTransJobName() {
		return transJobName;
	}
	/**
	 * @param transJobName The transJobName to set.
	 */
	public void setTransJobName(String transJobName) {
		this.transJobName = transJobName;
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
	 * @return Returns the grpId.
	 */
	public String getGrpId() {
		return grpId;
	}
	/**
	 * @param grpId The grpId to set.
	 */
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	/**
	 * @return Returns the opGrpName.
	 */
	public String getOpGrpName() {
		return opGrpName;
	}
	/**
	 * @param opGrpName The opGrpName to set.
	 */
	public void setOpGrpName(String opGrpName) {
		this.opGrpName = opGrpName;
	}
	/**
	 * @return Returns the opName.
	 */
	public String getOpName() {
		return opName;
	}
	/**
	 * @param opName The opName to set.
	 */
	public void setOpName(String opName) {
		this.opName = opName;
	}
	/**
	 * @return Returns the sno.
	 */
	public String getSno() {
		return sno;
	}
	/**
	 * @param sno The sno to set.
	 */
	public void setSno(String sno) {
		this.sno = sno;
	}
	/**
	 * @return Returns the stdHrs.
	 */
	public String getStdHrs() {
		return stdHrs;
	}
	/**
	 * @param stdHrs The stdHrs to set.
	 */
	public void setStdHrs(String stdHrs) {
		this.stdHrs = stdHrs;
	}
	/**
	 * @return Returns the customerId.
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId The customerId to set.
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return Returns the transCustomerType.
	 */
	public String getTransCustomerType() {
		return transCustomerType;
	}
	/**
	 * @param transCustomerType The transCustomerType to set.
	 */
	public void setTransCustomerType(String transCustomerType) {
		this.transCustomerType = transCustomerType;
	}
	/**
	 * @return Returns the trCustId.
	 */
	public String getTrCustId() {
		return trCustId;
	}
	/**
	 * @param trCustId The trCustId to set.
	 */
	public void setTrCustId(String trCustId) {
		this.trCustId = trCustId;
	}
	/**
	 * @return Returns the trCustomerId.
	 */
	public String getTrCustomerId() {
		return trCustomerId;
	}
	/**
	 * @param trCustomerId The trCustomerId to set.
	 */
	public void setTrCustomerId(String trCustomerId) {
		this.trCustomerId = trCustomerId;
	}
	/**
	 * @return Returns the trGenId.
	 */
	public String getTrGenId() {
		return trGenId;
	}
	/**
	 * @param trGenId The trGenId to set.
	 */
	public void setTrGenId(String trGenId) {
		this.trGenId = trGenId;
	}
	/**
	 * @return Returns the trJobId.
	 */
	public String getTrJobId() {
		return trJobId;
	}
	/**
	 * @param trJobId The trJobId to set.
	 */
	public void setTrJobId(String trJobId) {
		this.trJobId = trJobId;
	}
	/**
	 * @return Returns the custId.
	 */
	public String getCustId() {
		return custId;
	}
	/**
	 * @param custId The custId to set.
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}
	/**
	 * @return Returns the custTypId.
	 */
	public String getCustTypId() {
		return custTypId;
	}
	/**
	 * @param custTypId The custTypId to set.
	 */
	public void setCustTypId(String custTypId) {
		this.custTypId = custTypId;
	}
	/**
	 * @return Returns the opnLevelIncntv.
	 */
	public String getOpnLevelIncntv() {
		return opnLevelIncntv;
	}
	/**
	 * @param opnLevelIncntv The opnLevelIncntv to set.
	 */
	public void setOpnLevelIncntv(String opnLevelIncntv) {
		this.opnLevelIncntv = opnLevelIncntv;
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
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		if (formAction.equalsIgnoreCase("update"))
		{
			if (generalName == null || generalName.trim().equals("0"))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.required","GeneralName"));
			}
			if (jobName == null || jobName.trim().equals(""))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.required","JobName"));
			}
		}
		return errors;
	}
}
/***
$Log: JobMasterEditForm.java,v $
Revision 1.5  2005/07/05 11:40:26  vkrishnamoorthy
Job Operation Level Incentive added.

Revision 1.4  2005/01/19 13:02:49  vkrishnamoorthy
Error Messages thrown

Revision 1.3  2005/01/19 11:29:02  vkrishnamoorthy
Error Messages thrown for JobMasterEdit

Revision 1.2  2004/12/03 06:40:48  sponnusamy
JobMaster controller is completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/