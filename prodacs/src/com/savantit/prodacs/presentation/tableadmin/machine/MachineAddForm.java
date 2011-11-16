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
public class MachineAddForm extends ActionForm 
{
	private String machineCode = "";
	private String machineName = "";
	private String machineType = "0";
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
	private String addMachine;
	
	
	/**
	 * @return Returns the addMachine.
	 */
	public String getAddMachine() {
		return addMachine;
	}
	/**
	 * @param addMachine The addMachine to set.
	 */
	public void setAddMachine(String addMachine) {
		this.addMachine = addMachine;
	}
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
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{ 
		ActionErrors errors = new ActionErrors();

		if(machineCode==null || machineCode.trim().length()<1 || machineCode.trim().equals(""))
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Machine Code"));

		if(machineName==null || machineName.trim().length()<1 || machineName.trim().equals(""))
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Machine Name"));
		
		if(machineType==null || machineType.trim().equals("0"))
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.required","Machine Type"));	
			
		return errors;
	}	

}
/***
$Log: MachineAddForm.java,v $
Revision 1.3  2004/12/22 08:02:04  sponnusamy
In date field Null values are restricted.

Revision 1.2  2004/11/19 13:26:42  sponnusamy
Machine Forms are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/