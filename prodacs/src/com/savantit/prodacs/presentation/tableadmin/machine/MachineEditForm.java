/*
 * Created on Nov 1, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.machine;

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
public class MachineEditForm extends ActionForm 
{
	private String machineCode = "";
	private String machineName = "";
	private String machineType = "";
	private String installationDate = "";
	private String inUse = "";
	private String supplier = "";
	private String supplierContactPerson = "";
	private String supplierAddress = "";
	private String supplierPhoneNumber = "";
	private String serviceProvider = "";
	private String serviceContactPerson = "";
	private String serviceAddress = "";
	private String servicePhoneNumber = "";
	private String modifyMachine = "";
	private String modifyMachineCancel = "";
	private String formAction = "";
	private String id;
	private String[] ids = new String[0];

	
	/* MachineView.jsp variable */
	private String retMachineList;
	
	

	/**
	 * @return Returns the installationDate.
	 */
	public String getInstallationDate() {
		return installationDate;
	}
	/**
	 * @param installationDate The installationDate to set.
	 */
	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}
	/**
	 * @return Returns the inUse.
	 */
	public String getInUse() {
		return inUse;
	}
	/**
	 * @param inUse The inUse to set.
	 */
	public void setInUse(String inUse) {
		this.inUse = inUse;
	}
	/**
	 * @return Returns the machineCode.
	 */
	public String getMachineCode() {
		return machineCode;
	}
	/**
	 * @param machineCode The machineCode to set.
	 */
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	/**
	 * @return Returns the machineName.
	 */
	public String getMachineName() {
		return machineName;
	}
	/**
	 * @param machineName The machineName to set.
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	/**
	 * @return Returns the machineType.
	 */
	public String getMachineType() {
		return machineType;
	}
	/**
	 * @param machineType The machineType to set.
	 */
	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}
	/**
	 * @return Returns the modifyMachine.
	 */
	public String getModifyMachine() {
		return modifyMachine;
	}
	/**
	 * @param modifyMachine The modifyMachine to set.
	 */
	public void setModifyMachine(String modifyMachine) {
		this.modifyMachine = modifyMachine;
	}
	/**
	 * @return Returns the modifyMachineCancel.
	 */
	public String getModifyMachineCancel() {
		return modifyMachineCancel;
	}
	/**
	 * @param modifyMachineCancel The modifyMachineCancel to set.
	 */
	public void setModifyMachineCancel(String modifyMachineCancel) {
		this.modifyMachineCancel = modifyMachineCancel;
	}
	/**
	 * @return Returns the serviceAddress.
	 */
	public String getServiceAddress() {
		return serviceAddress;
	}
	/**
	 * @param serviceAddress The serviceAddress to set.
	 */
	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}
	/**
	 * @return Returns the serviceContactPerson.
	 */
	public String getServiceContactPerson() {
		return serviceContactPerson;
	}
	/**
	 * @param serviceContactPerson The serviceContactPerson to set.
	 */
	public void setServiceContactPerson(String serviceContactPerson) {
		this.serviceContactPerson = serviceContactPerson;
	}
	/**
	 * @return Returns the servicePhoneNumber.
	 */
	public String getServicePhoneNumber() {
		return servicePhoneNumber;
	}
	/**
	 * @param servicePhoneNumber The servicePhoneNumber to set.
	 */
	public void setServicePhoneNumber(String servicePhoneNumber) {
		this.servicePhoneNumber = servicePhoneNumber;
	}
	/**
	 * @return Returns the serviceProvider.
	 */
	public String getServiceProvider() {
		return serviceProvider;
	}
	/**
	 * @param serviceProvider The serviceProvider to set.
	 */
	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	/**
	 * @return Returns the supplier.
	 */
	public String getSupplier() {
		return supplier;
	}
	/**
	 * @param supplier The supplier to set.
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	/**
	 * @return Returns the supplierAddress.
	 */
	public String getSupplierAddress() {
		return supplierAddress;
	}
	/**
	 * @param supplierAddress The supplierAddress to set.
	 */
	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}
	/**
	 * @return Returns the supplierContactPerson.
	 */
	public String getSupplierContactPerson() {
		return supplierContactPerson;
	}
	/**
	 * @param supplierContactPerson The supplierContactPerson to set.
	 */
	public void setSupplierContactPerson(String supplierContactPerson) {
		this.supplierContactPerson = supplierContactPerson;
	}
	/**
	 * @return Returns the supplierPhoneNumber.
	 */
	public String getSupplierPhoneNumber() {
		return supplierPhoneNumber;
	}
	/**
	 * @param supplierPhoneNumber The supplierPhoneNumber to set.
	 */
	public void setSupplierPhoneNumber(String supplierPhoneNumber) {
		this.supplierPhoneNumber = supplierPhoneNumber;
	}
	/**
	 * @return Returns the retMachineList.
	 */
	public String getRetMachineList() {
		return retMachineList;
	}
	/**
	 * @param retMachineList The retMachineList to set.
	 */
	public void setRetMachineList(String retMachineList) {
		this.retMachineList = retMachineList;
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
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors = new ActionErrors();
		if (formAction.equalsIgnoreCase("update"))
		{
			if (machineType == null || machineType.trim().equals("0"))
			{
				errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.required","MachineType"));
			}
		}
		return errors;
	}
}
/***
$Log: MachineEditForm.java,v $
Revision 1.3  2005/01/19 11:29:17  vkrishnamoorthy
Error Messages thrown

Revision 1.2  2004/11/19 13:26:42  sponnusamy
Machine Forms are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/