/*
 * Created on Nov 4, 2004
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
public class DummyWorkOrderAddForm extends ActionForm 
{
	private String dyWOHash = "";
	private String dyCustomerName = "0";
	private String dyCustomerType = "0";
	private String dyJobName = "";
	private String dyGeneralName = "0";
	private String dyDrawingHash = "";
	private String dyRevisionHash = "";
	private String dyMaterialType = "";
	private String dyStartSno = "";
	private String dyTotQty = "";
	private String dyDcNo = "";
	private String dyDcDate = "";
	private String dyOpnGrpCode = "0";
	private String dyOpnName = "";
	private String dyStartOpn = "";
	private String dyEndOpn = "";
	private String formAction = "";

	
	/**
	 * @return Returns the dyDcDate.
	 */
	public String getDyDcDate() {
		return dyDcDate;
	}
	/**
	 * @param dyDcDate The dyDcDate to set.
	 */
	public void setDyDcDate(String dyDcDate) {
		this.dyDcDate = dyDcDate;
	}
	/**
	 * @return Returns the dyDcNo.
	 */
	public String getDyDcNo() {
		return dyDcNo;
	}
	/**
	 * @param dyDcNo The dyDcNo to set.
	 */
	public void setDyDcNo(String dyDcNo) {
		this.dyDcNo = dyDcNo;
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
	 * @return Returns the dyCustomerType.
	 */
	public String getDyCustomerType() {
		return dyCustomerType;
	}
	/**
	 * @param dyCustomerType The dyCustomerType to set.
	 */
	public void setDyCustomerType(String dyCustomerType) {
		this.dyCustomerType = dyCustomerType;
	}
	/**
	 * @return Returns the dyEndOpn.
	 */
	public String getDyEndOpn() {
		return dyEndOpn;
	}
	/**
	 * @param dyEndOpn The dyEndOpn to set.
	 */
	public void setDyEndOpn(String dyEndOpn) {
		this.dyEndOpn = dyEndOpn;
	}
	/**
	 * @return Returns the dyOpnGrpCode.
	 */
	public String getDyOpnGrpCode() {
		return dyOpnGrpCode;
	}
	/**
	 * @param dyOpnGrpCode The dyOpnGrpCode to set.
	 */
	public void setDyOpnGrpCode(String dyOpnGrpCode) {
		this.dyOpnGrpCode = dyOpnGrpCode;
	}
	/**
	 * @return Returns the dyOpnName.
	 */
	public String getDyOpnName() {
		return dyOpnName;
	}
	/**
	 * @param dyOpnName The dyOpnName to set.
	 */
	public void setDyOpnName(String dyOpnName) {
		this.dyOpnName = dyOpnName;
	}
	/**
	 * @return Returns the dyStartOpn.
	 */
	public String getDyStartOpn() {
		return dyStartOpn;
	}
	/**
	 * @param dyStartOpn The dyStartOpn to set.
	 */
	public void setDyStartOpn(String dyStartOpn) {
		this.dyStartOpn = dyStartOpn;
	}
	/**
	 * @return Returns the dyStartSno.
	 */
	public String getDyStartSno() {
		return dyStartSno;
	}
	/**
	 * @param dyStartSno The dyStartSno to set.
	 */
	public void setDyStartSno(String dyStartSno) {
		this.dyStartSno = dyStartSno;
	}
	/**
	 * @return Returns the dyTotQty.
	 */
	public String getDyTotQty() {
		return dyTotQty;
	}
	/**
	 * @param dyTotQty The dyTotQty to set.
	 */
	public void setDyTotQty(String dyTotQty) {
		this.dyTotQty = dyTotQty;
	}
	/**
	 * @return Returns the dyCustomerName.
	 */
	public String getDyCustomerName() {
		return dyCustomerName;
	}
	/**
	 * @param dyCustomerName The dyCustomerName to set.
	 */
	public void setDyCustomerName(String dyCustomerName) {
		this.dyCustomerName = dyCustomerName;
	}
	/**
	 * @return Returns the dyDrawingHash.
	 */
	public String getDyDrawingHash() {
		return dyDrawingHash;
	}
	/**
	 * @param dyDrawingHash The dyDrawingHash to set.
	 */
	public void setDyDrawingHash(String dyDrawingHash) {
		this.dyDrawingHash = dyDrawingHash;
	}
	/**
	 * @return Returns the dyGeneralName.
	 */
	public String getDyGeneralName() {
		return dyGeneralName;
	}
	/**
	 * @param dyGeneralName The dyGeneralName to set.
	 */
	public void setDyGeneralName(String dyGeneralName) {
		this.dyGeneralName = dyGeneralName;
	}
	/**
	 * @return Returns the dyJobName.
	 */
	public String getDyJobName() {
		return dyJobName;
	}
	/**
	 * @param dyJobName The dyJobName to set.
	 */
	public void setDyJobName(String dyJobName) {
		this.dyJobName = dyJobName;
	}
	/**
	 * @return Returns the dyMaterialType.
	 */
	public String getDyMaterialType() {
		return dyMaterialType;
	}
	/**
	 * @param dyMaterialType The dyMaterialType to set.
	 */
	public void setDyMaterialType(String dyMaterialType) {
		this.dyMaterialType = dyMaterialType;
	}
	/**
	 * @return Returns the dyRevisionHash.
	 */
	public String getDyRevisionHash() {
		return dyRevisionHash;
	}
	/**
	 * @param dyRevisionHash The dyRevisionHash to set.
	 */
	public void setDyRevisionHash(String dyRevisionHash) {
		this.dyRevisionHash = dyRevisionHash;
	}
	/**
	 * @return Returns the dyWOHash.
	 */
	public String getDyWOHash() {
		return dyWOHash;
	}
	/**
	 * @param dyWOHash The dyWOHash to set.
	 */
	public void setDyWOHash(String dyWOHash) {
		this.dyWOHash = dyWOHash;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();
		
		if (formAction.equalsIgnoreCase("add"))
		{
			if(dyWOHash==null || dyWOHash.trim().length()<1 || dyWOHash.trim().equals(""))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","WorkOrder No"));
			
			if(dyCustomerName == null || dyCustomerName.trim().equals("0"))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Customer Name"));	
	
			if(dyCustomerType == null || dyCustomerType.trim().equals("0"))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Customer Type"));	
	
			if(dyGeneralName == null || dyGeneralName.trim().equals("0"))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","General Name"));	
	
			if(dyOpnGrpCode == null || dyOpnGrpCode.trim().equals("0"))
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Operation Group Code"));	
	
			if(dyJobName == null || dyJobName.trim().equals("") || dyJobName.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Job Name"));	
	
			if(dyDrawingHash == null || dyDrawingHash.trim().equals("") || dyDrawingHash.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Drawing No"));	
	
			if(dyRevisionHash == null || dyRevisionHash.trim().equals("") || dyRevisionHash.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Revision No"));	
	
			if(dyMaterialType == null || dyMaterialType.trim().equals("") || dyMaterialType.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Material Type"));	
	
			if(dyStartSno == null || dyStartSno.trim().equals("") || dyStartSno.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Start S.No"));	
	
			if(dyTotQty == null || dyTotQty.trim().equals("") || dyTotQty.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Total Qty"));	
	
			if(dyOpnName == null || dyOpnName.trim().equals("") || dyOpnName.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Operation Name"));	
	
			if(dyStartOpn == null || dyStartOpn.trim().equals("") || dyStartOpn.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Start Operation"));	
	
			if(dyEndOpn == null || dyEndOpn.trim().equals("") || dyEndOpn.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","End Operation"));
	
			if(dyDcNo == null || dyDcNo.trim().equals("") || dyDcNo.trim().length() < 1)
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Delivery Challan No."));
		}
		return errors;
	}	
}
/***
$Log: DummyWorkOrderAddForm.java,v $
Revision 1.4  2005/07/25 10:34:10  vkrishnamoorthy
WO DC Date added.

Revision 1.3  2005/07/24 09:09:21  vkrishnamoorthy
DC No included.

Revision 1.2  2005/02/07 12:11:27  sponnusamy
DummyWorkOrder is completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/