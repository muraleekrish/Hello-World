/*
 * Created on May 12, 2005
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
public class PostingRuleForm extends ActionForm
{
	private String ruleNo;
	private String appliedRule;
	private String formAction;
	
	
	
	/**
	 * @return Returns the appliedRule.
	 */
	public String getAppliedRule() {
		return appliedRule;
	}
	/**
	 * @param appliedRule The appliedRule to set.
	 */
	public void setAppliedRule(String appliedRule) {
		this.appliedRule = appliedRule;
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
	 * @return Returns the ruleNo.
	 */
	public String getRuleNo() {
		return ruleNo;
	}
	/**
	 * @param ruleNo The ruleNo to set.
	 */
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
}


/***
$Log: PostingRuleForm.java,v $
Revision 1.1  2005/05/12 14:12:39  vkrishnamoorthy
Initial commit on ProDACS.

***/