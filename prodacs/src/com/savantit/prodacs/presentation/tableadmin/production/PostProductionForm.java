/*
 * Created on Jan 7, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PostProductionForm extends ActionForm 
{
	private String wrkOrderFromDate = "";
	private String wrkOrderToDate = "";
	private String hidProd = "";
	private String hidNonProd = "";
	private String hidPop= "";
	private String hidRdl = "";
	private String hidProdFinal = "";
	private String hidDespatch = "";
	private String hidShipment = "";
	
	private String formAction = "";
	private String maxItems = "15";
	private String page = "1";
	private String sortField = "MC_CDE";
	private String sortOrder = "ascending";   	
	private int id;
	private String[] ids = new String[0];

	
	/**
	 * @return Returns the hidDespatch.
	 */
	public String getHidDespatch() {
		return hidDespatch;
	}
	/**
	 * @param hidDespatch The hidDespatch to set.
	 */
	public void setHidDespatch(String hidDespatch) {
		this.hidDespatch = hidDespatch;
	}
	/**
	 * @return Returns the hidShipment.
	 */
	public String getHidShipment() {
		return hidShipment;
	}
	/**
	 * @param hidShipment The hidShipment to set.
	 */
	public void setHidShipment(String hidShipment) {
		this.hidShipment = hidShipment;
	}
	/**
	 * @return Returns the hidProdFinal.
	 */
	public String getHidProdFinal() {
		return hidProdFinal;
	}
	/**
	 * @param hidProdFinal The hidProdFinal to set.
	 */
	public void setHidProdFinal(String hidProdFinal) {
		this.hidProdFinal = hidProdFinal;
	}
	/**
	 * @return Returns the wrkOrderFromDate.
	 */
	public String getWrkOrderFromDate() {
		return wrkOrderFromDate;
	}
	/**
	 * @param wrkOrderFromDate The wrkOrderFromDate to set.
	 */
	public void setWrkOrderFromDate(String wrkOrderFromDate) {
		this.wrkOrderFromDate = wrkOrderFromDate;
	}
	/**
	 * @return Returns the wrkOrderToDate.
	 */
	public String getWrkOrderToDate() {
		return wrkOrderToDate;
	}
	/**
	 * @param wrkOrderToDate The wrkOrderToDate to set.
	 */
	public void setWrkOrderToDate(String wrkOrderToDate) {
		this.wrkOrderToDate = wrkOrderToDate;
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
	 * @return Returns the maxItems.
	 */
	public String getMaxItems() {
		return maxItems;
	}
	/**
	 * @param maxItems The maxItems to set.
	 */
	public void setMaxItems(String maxItems) {
		this.maxItems = maxItems;
	}
	/**
	 * @return Returns the page.
	 */
	public String getPage() {
		return page;
	}
	/**
	 * @param page The page to set.
	 */
	public void setPage(String page) {
		this.page = page;
	}
	/**
	 * @return Returns the sortField.
	 */
	public String getSortField() {
		return sortField;
	}
	/**
	 * @param sortField The sortField to set.
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	/**
	 * @return Returns the sortOrder.
	 */
	public String getSortOrder() {
		return sortOrder;
	}
	/**
	 * @param sortOrder The sortOrder to set.
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
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
	 * @return Returns the hidNonProd.
	 */
	public String getHidNonProd() {
		return hidNonProd;
	}
	/**
	 * @param hidNonProd The hidNonProd to set.
	 */
	public void setHidNonProd(String hidNonProd) {
		this.hidNonProd = hidNonProd;
	}
	/**
	 * @return Returns the hidPop.
	 */
	public String getHidPop() {
		return hidPop;
	}
	/**
	 * @param hidPop The hidPop to set.
	 */
	public void setHidPop(String hidPop) {
		this.hidPop = hidPop;
	}
	/**
	 * @return Returns the hidProd.
	 */
	public String getHidProd() {
		return hidProd;
	}
	/**
	 * @param hidProd The hidProd to set.
	 */
	public void setHidProd(String hidProd) {
		this.hidProd = hidProd;
	}
	/**
	 * @return Returns the hidRdl.
	 */
	public String getHidRdl() {
		return hidRdl;
	}
	/**
	 * @param hidRdl The hidRdl to set.
	 */
	public void setHidRdl(String hidRdl) {
		this.hidRdl = hidRdl;
	}
}

/***
$Log: PostProductionForm.java,v $
Revision 1.5  2005/07/24 09:17:31  vkrishnamoorthy
Despatch and Shipment included.

Revision 1.4  2005/07/18 11:50:09  vkrishnamoorthy
Post Production modified as per Production Final.

Revision 1.3  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/