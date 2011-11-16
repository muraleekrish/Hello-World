/*
 * Created on Nov 5, 2004
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
public class ProductionAddForm extends ActionForm 
{
	private String proDate = "";
	private String proShift = "0";
	private String proMachine = "0";
	private String proNextBtn1 = ""; 
	private String proNextBtn2 = ""; 
	private String proNextBtn3 = ""; 
	private String proWorkOrderHash = "0";
	private String proNmlorRwk = "";
	private String proIncentive = "";
	private String proStartOperation = "";
	private String proEndOperation = "";
	private String proTotalHours = "";
	private String[] proEmployeeName = new String[0];
	private String proDutyHrs = "";
	private String proOTHrs = "";
	private String hidWrkOrdId = "0";
	private String hidChkdJobId = "0";
	private String hidWrkOrdJobId = "0";
	private String hidChkdJobName = "";
	private String formAction = "";
	private String showCount = "";
	private String proTotQtySnos = "";
	private String hidAllEmpDet = "";
	private String minQty = "";
	private String hidIsRadl = "";
	private String hidWrkOrdJbStIds = "";
	private String hidWoJbStIds = "";
	private String hidProdList = "";
	private String hidUserId = "";
	private String hidWoJbId = "";
	private String grpId = "";
	private String sno = "";
	private String name = "";
	private String stdHrs = "";
	private String gpCde = "";
	private String response = "";
	

	/**
	 * @return Returns the response.
	 */
	public String getResponse() {
		return response;
	}
	/**
	 * @param response The response to set.
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	/**
	 * @return Returns the gpCde.
	 */
	public String getGpCde() {
		return gpCde;
	}
	/**
	 * @param gpCde The gpCde to set.
	 */
	public void setGpCde(String gpCde) {
		this.gpCde = gpCde;
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
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return Returns the hidWoJbId.
	 */
	public String getHidWoJbId() {
		return hidWoJbId;
	}
	/**
	 * @param hidWoJbId The hidWoJbId to set.
	 */
	public void setHidWoJbId(String hidWoJbId) {
		this.hidWoJbId = hidWoJbId;
	}
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
	 * @return Returns the hidProdList.
	 */
	public String getHidProdList() {
		return hidProdList;
	}
	/**
	 * @param hidProdList The hidProdList to set.
	 */
	public void setHidProdList(String hidProdList) {
		this.hidProdList = hidProdList;
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
	 * @return Returns the proEndOperation.
	 */
	public String getProEndOperation() {
		return proEndOperation;
	}
	/**
	 * @param proEndOperation The proEndOperation to set.
	 */
	public void setProEndOperation(String proEndOperation) {
		this.proEndOperation = proEndOperation;
	}
	/**
	 * @return Returns the proIncentive.
	 */
	public String getProIncentive() {
		return proIncentive;
	}
	/**
	 * @param proIncentive The proIncentive to set.
	 */
	public void setProIncentive(String proIncentive) {
		this.proIncentive = proIncentive;
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
	 * @return Returns the proNextBtn1.
	 */
	public String getProNextBtn1() {
		return proNextBtn1;
	}
	/**
	 * @param proNextBtn1 The proNextBtn1 to set.
	 */
	public void setProNextBtn1(String proNextBtn1) {
		this.proNextBtn1 = proNextBtn1;
	}
	/**
	 * @return Returns the proNextBtn2.
	 */
	public String getProNextBtn2() {
		return proNextBtn2;
	}
	/**
	 * @param proNextBtn2 The proNextBtn2 to set.
	 */
	public void setProNextBtn2(String proNextBtn2) {
		this.proNextBtn2 = proNextBtn2;
	}
	/**
	 * @return Returns the proNextBtn3.
	 */
	public String getProNextBtn3() {
		return proNextBtn3;
	}
	/**
	 * @param proNextBtn3 The proNextBtn3 to set.
	 */
	public void setProNextBtn3(String proNextBtn3) {
		this.proNextBtn3 = proNextBtn3;
	}
	/**
	 * @return Returns the proNmlorRwk.
	 */
	public String getProNmlorRwk() {
		return proNmlorRwk;
	}
	/**
	 * @param proNmlorRwk The proNmlorRwk to set.
	 */
	public void setProNmlorRwk(String proNmlorRwk) {
		this.proNmlorRwk = proNmlorRwk;
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
	 * @return Returns the proStartOperation.
	 */
	public String getProStartOperation() {
		return proStartOperation;
	}
	/**
	 * @param proStartOperation The proStartOperation to set.
	 */
	public void setProStartOperation(String proStartOperation) {
		this.proStartOperation = proStartOperation;
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
	 * @return Returns the proWorkOrderHash.
	 */
	public String getProWorkOrderHash() {
		return proWorkOrderHash;
	}
	/**
	 * @param proWorkOrderHash The proWorkOrderHash to set.
	 */
	public void setProWorkOrderHash(String proWorkOrderHash) {
		this.proWorkOrderHash = proWorkOrderHash;
	}
	/**
	 * @return Returns the hidWrkOrdId.
	 */
	public String getHidWrkOrdId() {
		return hidWrkOrdId;
	}
	/**
	 * @param hidWrkOrdId The hidWrkOrdId to set.
	 */
	public void setHidWrkOrdId(String hidWrkOrdId) {
		this.hidWrkOrdId = hidWrkOrdId;
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
	 * @return Returns the hidWrkOrdJobId.
	 */
	public String getHidWrkOrdJobId() {
		return hidWrkOrdJobId;
	}
	/**
	 * @param hidWrkOrdJobId The hidWrkOrdJobId to set.
	 */
	public void setHidWrkOrdJobId(String hidWrkOrdJobId) {
		this.hidWrkOrdJobId = hidWrkOrdJobId;
	}
	/**
	 * @return Returns the hidChkdJobName.
	 */
	public String getHidChkdJobName() {
		return hidChkdJobName;
	}
	/**
	 * @param hidChkdJobName The hidChkdJobName to set.
	 */
	public void setHidChkdJobName(String hidChkdJobName) {
		this.hidChkdJobName = hidChkdJobName;
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
	 * @return Returns the hidChkdJobId.
	 */
	public String getHidChkdJobId() {
		return hidChkdJobId;
	}
	/**
	 * @param hidChkdJobId The hidChkdJobId to set.
	 */
	public void setHidChkdJobId(String hidChkdJobId) {
		this.hidChkdJobId = hidChkdJobId;
	}
	/**
	 * @return Returns the proTotQtySnos.
	 */
	public String getProTotQtySnos() {
		return proTotQtySnos;
	}
	/**
	 * @param proTotQtySnos The proTotQtySnos to set.
	 */
	public void setProTotQtySnos(String proTotQtySnos) {
		this.proTotQtySnos = proTotQtySnos;
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
	 * @return Returns the hidIsRadl.
	 */
	public String getHidIsRadl() {
		return hidIsRadl;
	}
	/**
	 * @param hidIsRadl The hidIsRadl to set.
	 */
	public void setHidIsRadl(String hidIsRadl) {
		this.hidIsRadl = hidIsRadl;
	}
	/**
	 * @return Returns the hidWrkOrdJbStIds.
	 */
	public String getHidWrkOrdJbStIds() {
		return hidWrkOrdJbStIds;
	}
	/**
	 * @param hidWrkOrdJbStIds The hidWrkOrdJbStIds to set.
	 */
	public void setHidWrkOrdJbStIds(String hidWrkOrdJbStIds) {
		this.hidWrkOrdJbStIds = hidWrkOrdJbStIds;
	}
	/**
	 * @return Returns the hidWoJbStIds.
	 */
	public String getHidWoJbStIds() {
		return hidWoJbStIds;
	}
	/**
	 * @param hidWoJbStIds The hidWoJbStIds to set.
	 */
	public void setHidWoJbStIds(String hidWoJbStIds) {
		this.hidWoJbStIds = hidWoJbStIds;
	}
}
/***
$Log: ProductionAddForm.java,v $
Revision 1.11  2005/11/24 12:06:06  vkrishnamoorthy
AJAX implemented for WorkOrder related changes.

Revision 1.10  2005/09/02 08:08:50  vkrishnamoorthy
Modified for WO Job Statuses Operation checking.

Revision 1.9  2005/07/01 13:00:53  vkrishnamoorthy
Modified as per View Operations.

Revision 1.8  2005/05/30 07:44:50  vkrishnamoorthy
UserName from session got and passed to create log.

Revision 1.7  2005/03/08 09:24:18  sponnusamy
Production add changed according to
requirement.

Revision 1.6  2005/02/04 06:17:18  sponnusamy
For Rework operations checked.

Revision 1.5  2005/01/27 15:11:19  sponnusamy
isRadlOperation condition included

Revision 1.4  2005/01/05 12:10:02  sponnusamy
changed according to Non_production

Revision 1.3  2004/12/31 11:00:26  sponnusamy
Production View page is completed

Revision 1.2  2004/12/29 15:38:19  sponnusamy
Production add and list are partially completed.

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/