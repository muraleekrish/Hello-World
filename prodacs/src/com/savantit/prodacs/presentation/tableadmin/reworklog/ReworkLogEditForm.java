/*
 * Created on Jan 5, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.reworklog;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReworkLogEditForm extends ActionForm 
{
	private String wrkOrdNum = "";
	private String jobName = "";
	private String authorizedBy = "";
	private String reworkReason = "";
	
	private String formAction = "";
	private int id;
	private String[] ids = new String[0];
	
	

	/**
	 * @return Returns the authorizedBy.
	 */
	public String getAuthorizedBy() {
		return authorizedBy;
	}
	/**
	 * @param authorizedBy The authorizedBy to set.
	 */
	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
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
	/**
	 * @return Returns the jobName.
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName The jobName to set.
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return Returns the reworkReason.
	 */
	public String getReworkReason() {
		return reworkReason;
	}
	/**
	 * @param reworkReason The reworkReason to set.
	 */
	public void setReworkReason(String reworkReason) {
		this.reworkReason = reworkReason;
	}
	/**
	 * @return Returns the wrkOrdNum.
	 */
	public String getWrkOrdNum() {
		return wrkOrdNum;
	}
	/**
	 * @param wrkOrdNum The wrkOrdNum to set.
	 */
	public void setWrkOrdNum(String wrkOrdNum) {
		this.wrkOrdNum = wrkOrdNum;
	}
}


/***
$Log: ReworkLogEditForm.java,v $
Revision 1.4  2005/02/05 09:15:14  sponnusamy
Modified according to ReworkLogView

Revision 1.3  2005/02/03 05:13:35  vkrishnamoorthy
Log added.

***/