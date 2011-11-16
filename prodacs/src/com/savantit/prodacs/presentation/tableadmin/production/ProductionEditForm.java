/*
 * Created on Dec 31, 2004
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
public class ProductionEditForm extends ActionForm
{
	private String formAction = "";
	private int id;
	private String[] ids = new String[0];
	
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
	private String showCount = "";
	private String proTotQtySnos = "";
	private String hidAllEmpDet = "";
	private String minQty = "";
	private String hidIsRadl = "";
	private String hidWrkOrdJbStIds = "";
	private String modJobDet = "";
	private String hidUserId = "";
	private String hidWoJbId = "";
	private String grpId = "";
	private String sno = "";
	private String name = "";
	private String stdHrs = "";
	private String gpCde = "";
	private String loadCount = "";
	private String hidDate = "";
	private String hidSft = "";
	private String hidDate1 = "";
	private String hidSft1 = "";
	
	
	
	/**
	 * @return Returns the hidDate1.
	 */
	public String getHidDate1() {
		return hidDate1;
	}
	/**
	 * @param hidDate1 The hidDate1 to set.
	 */
	public void setHidDate1(String hidDate1) {
		this.hidDate1 = hidDate1;
	}
	/**
	 * @return Returns the hidSft1.
	 */
	public String getHidSft1() {
		return hidSft1;
	}
	/**
	 * @param hidSft1 The hidSft1 to set.
	 */
	public void setHidSft1(String hidSft1) {
		this.hidSft1 = hidSft1;
	}
	/**
	 * @return Returns the hidDate.
	 */
	public String getHidDate() {
		return hidDate;
	}
	/**
	 * @param hidDate The hidDate to set.
	 */
	public void setHidDate(String hidDate) {
		this.hidDate = hidDate;
	}
	/**
	 * @return Returns the hidSft.
	 */
	public String getHidSft() {
		return hidSft;
	}
	/**
	 * @param hidSft The hidSft to set.
	 */
	public void setHidSft(String hidSft) {
		this.hidSft = hidSft;
	}
	/**
	 * @return Returns the loadCount.
	 */
	public String getLoadCount() {
		return loadCount;
	}
	/**
	 * @param loadCount The loadCount to set.
	 */
	public void setLoadCount(String loadCount) {
		this.loadCount = loadCount;
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
	 * @return Returns the modJobDet.
	 */
	public String getModJobDet() {
		return modJobDet;
	}
	/**
	 * @param modJobDet The modJobDet to set.
	 */
	public void setModJobDet(String modJobDet) {
		this.modJobDet = modJobDet;
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
}

/***
$Log: ProductionEditForm.java,v $
Revision 1.9  2005/11/16 09:08:59  vkrishnamoorthy
Modified as per Production Accounting.

Revision 1.8  2005/10/06 07:43:05  vkrishnamoorthy
Modified for loading shift and machines if that already selected ones does not exist as per accountings.

Revision 1.7  2005/07/01 13:00:53  vkrishnamoorthy
Modified as per View Operations.

Revision 1.6  2005/05/30 07:44:50  vkrishnamoorthy
UserName from session got and passed to create log.

Revision 1.5  2005/02/04 06:17:18  sponnusamy
For Rework operations checked.

Revision 1.4  2005/01/28 10:02:04  sponnusamy
Log added

***/
