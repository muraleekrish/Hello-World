/*
 * Created on Jul 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.production;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.savantit.prodacs.infra.util.BuildConfig;

/**
 * @author sponnusamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DespatchClearanceListAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of DespatchClearanceForm.java*/
		if (form instanceof DespatchClearanceForm)
		{
			action="success";
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("DespatchClearance List Action:"+action);
		}
		return mapping.findForward(action);
	}
}
/***
$Log: DespatchClearanceListAction.java,v $
Revision 1.2  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.1  2005/07/21 06:16:08  sponnusamy
Initial Commit of Despatch Clearance List Form and Action beans

***/
