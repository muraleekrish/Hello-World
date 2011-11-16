/*
 * Created on Dec 30, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PayOutProductionListAction extends Action
{
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		String action="failure";
		
		/* Creating a instance of PayoutProduction */
		if (form instanceof PayOutProductionListForm)
		{
			//PayOutProductionListForm frm=(PayOutProductionListForm) form;
			action="success";
		}
		if (BuildConfig.DMODE)
		{
			System.out.println("PayOutProduction Action: "+ action);
		}
		return mapping.findForward(action);	
	}
}

/***
$Log: PayOutProductionListAction.java,v $
Revision 1.5  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.4  2005/02/11 11:14:17  sponnusamy
Warnings removed

Revision 1.3  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/
