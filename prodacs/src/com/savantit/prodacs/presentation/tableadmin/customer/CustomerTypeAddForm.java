/*
 * Created on Jan 25, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.customer;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomerTypeAddForm extends ActionForm 
{
	private String customerTypeName = "";
	private String description = "";
	private int id = 0;

	
	/**
	 * @return Returns the customerTypeName.
	 */
	public String getCustomerTypeName() {
		return customerTypeName;
	}
	/**
	 * @param customerTypeName The customerTypeName to set.
	 */
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
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
}


/***
$Log: CustomerTypeAddForm.java,v $
Revision 1.2  2005/02/16 10:02:10  vkrishnamoorthy
Modified according to CutomerType Add.

Revision 1.1  2005/01/25 12:38:28  sponnusamy
New Customertype Added

***/
