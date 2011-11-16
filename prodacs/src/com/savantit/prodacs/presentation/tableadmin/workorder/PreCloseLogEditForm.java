/*
 * Created on Nov 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.workorder;

import org.apache.struts.action.ActionForm;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PreCloseLogEditForm extends ActionForm
{
	private String preCloseLogWOHash;
	private String preCloseLogJobName;
	private String selectJobQty;
	private String preCloseLogJobQtySno;
	private String addOperation;
	private String remove;
	/* Used in PreCloseLogView.jsp page */
	private String retPreCloseLogList;
	
	private String formAction="";
	private int id;
	private String[] ids = new String[0];

	
	/**
	 * @return Returns the addOperation.
	 */
	public String getAddOperation() {
		return addOperation;
	}
	/**
	 * @param addOperation The addOperation to set.
	 */
	public void setAddOperation(String addOperation) {
		this.addOperation = addOperation;
	}
	/**
	 * @return Returns the preCloseLogJobName.
	 */
	public String getPreCloseLogJobName() {
		return preCloseLogJobName;
	}
	/**
	 * @param preCloseLogJobName The preCloseLogJobName to set.
	 */
	public void setPreCloseLogJobName(String preCloseLogJobName) {
		this.preCloseLogJobName = preCloseLogJobName;
	}
	/**
	 * @return Returns the preCloseLogJobQtySno.
	 */
	public String getPreCloseLogJobQtySno() {
		return preCloseLogJobQtySno;
	}
	/**
	 * @param preCloseLogJobQtySno The preCloseLogJobQtySno to set.
	 */
	public void setPreCloseLogJobQtySno(String preCloseLogJobQtySno) {
		this.preCloseLogJobQtySno = preCloseLogJobQtySno;
	}
	/**
	 * @return Returns the preCloseLogWOHash.
	 */
	public String getPreCloseLogWOHash() {
		return preCloseLogWOHash;
	}
	/**
	 * @param preCloseLogWOHash The preCloseLogWOHash to set.
	 */
	public void setPreCloseLogWOHash(String preCloseLogWOHash) {
		this.preCloseLogWOHash = preCloseLogWOHash;
	}
	/**
	 * @return Returns the remove.
	 */
	public String getRemove() {
		return remove;
	}
	/**
	 * @param remove The remove to set.
	 */
	public void setRemove(String remove) {
		this.remove = remove;
	}
	/**
	 * @return Returns the retPreCloseLogList.
	 */
	public String getRetPreCloseLogList() {
		return retPreCloseLogList;
	}
	/**
	 * @param retPreCloseLogList The retPreCloseLogList to set.
	 */
	public void setRetPreCloseLogList(String retPreCloseLogList) {
		this.retPreCloseLogList = retPreCloseLogList;
	}
	/**
	 * @return Returns the selectJobQty.
	 */
	public String getSelectJobQty() {
		return selectJobQty;
	}
	/**
	 * @param selectJobQty The selectJobQty to set.
	 */
	public void setSelectJobQty(String selectJobQty) {
		this.selectJobQty = selectJobQty;
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
}
/***
$Log: PreCloseLogEditForm.java,v $
Revision 1.2  2004/12/21 11:23:08  sponnusamy
PreCloseLog Controller is fully completed.

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/