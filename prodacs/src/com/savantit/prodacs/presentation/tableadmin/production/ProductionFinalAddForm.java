/*
 * Created on Jul 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProductionFinalAddForm extends ActionForm
{
	private String proDate = "";
	private String proShift = "0";
	private String proWorkOrderHash = "0";
	private String proStartOperation = "";
	private String proEndOperation = "";
	private String proTotalHours = "";
	private String[] proEmployeeName = new String[0];
	
	private String formAction = "";
	private String minQty = "";
	private String hidWrkOrdId = "0";
	private String hidWrkOrdJobId = "0";
	private String showCount = "";
	private String hidChkdJobName = "";
	private String hidChkdJobId = "0";
	private String proTotQtySnos = "";
	private String hidAllEmpDet = "";
	private String hidWrkOrdJbStIds = "";
	private String hidProdList = "";
	private String hidIsRadl = "";
	private String hidUserId = ""; 
	private String hidWoJbId = "";
	private String grpId = "";
	private String sno = "";
	private String name = "";
	private String stdHrs = "";
	private String gpCde = "";
	

	
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
}


/***
$Log: ProductionFinalAddForm.java,v $
Revision 1.2  2005/08/01 09:29:12  vkrishnamoorthy
Total Hours included.

Revision 1.1  2005/07/18 05:57:01  vkrishnamoorthy
Initial commit on ProDACS.

***/