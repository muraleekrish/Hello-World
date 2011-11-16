/*
 * Created on May 20, 2005
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
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProductionAcctngDetailsAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/*Creating an instance of ProductionAcctngDetailsForm.java*/
		if (BuildConfig.DMODE)
		{
			System.out.println("Production Accounting Action Starts");
		}
		if (form instanceof ProductionAcctngDetailsForm)
		{
			ProductionAcctngDetailsForm frm = (ProductionAcctngDetailsForm) form;
			if (BuildConfig.DMODE)
			{	
				System.out.println("Form Action :"+frm.getFormAction());
			}
			if (frm.getFormAction().equalsIgnoreCase("prodAdd"))
			{
				action = "prodAdd";
			}
			else if (frm.getFormAction().equalsIgnoreCase("rdlProdAdd"))
			{
				action = "rdlProdAdd";
			}
			else if (frm.getFormAction().equalsIgnoreCase("nonProdAdd"))
			{
				action = "nonProdAdd";
			}
			else if (frm.getFormAction().equalsIgnoreCase("popAdd"))
			{
				action = "popAdd";
			}
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("Production Accouting Details Action"+action);
		}
		return mapping.findForward(action);
	}
}


/***
$Log: ProductionAcctngDetailsAction.java,v $
Revision 1.2  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.1  2005/05/20 09:34:19  vkrishnamoorthy
Initial commit on ProDACS.

***/