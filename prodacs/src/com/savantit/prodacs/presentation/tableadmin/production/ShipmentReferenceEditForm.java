/*
 * Created on Jul 23, 2005
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
public class ShipmentReferenceEditForm extends ActionForm
{
	private String proDate = "";
	private String proShift = "0";
	private String proWorkOrderHash = "0";
	private String proDCNo = "";
	
	private String formAction = "";
	private String hidWrkOrdId = "";
	private String hidWrkOrdJobId = "";
	private String showCount = "";
	private String hidChkdJobName = "";
	private String hidChkdJobId = "";
	private String proTotQtySnos = "";
	private String hidUserId = "";
	private String hidWrkOrdJbStIds = "";
	private String hidProdList = "";
	private String hidWoJbId = "";
	private String modJobDet = "";
	
	private int id;
	private String[] ids = new String[0];

	
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
	 * @return Returns the proDCNo.
	 */
	public String getProDCNo() {
		return proDCNo;
	}
	/**
	 * @param proDCNo The proDCNo to set.
	 */
	public void setProDCNo(String proDCNo) {
		this.proDCNo = proDCNo;
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
}


/***
$Log: ShipmentReferenceEditForm.java,v $
Revision 1.1  2005/07/23 17:38:48  vkrishnamoorthy
Initial commit on ProDACS.

***/