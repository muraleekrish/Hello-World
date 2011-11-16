/*
 * Created on Jan 3, 2005
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
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class RadialProductionListAction extends Action
{
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		String action="failure";
		
		/* Instance of Radial Prodution */
		if(form instanceof RadialProductionListForm)
		{
			//RadialProductionListForm frm = (RadialProductionListForm) form;
			action="success";
		}
		if (BuildConfig.DMODE)
			System.out.println("Radial Production List Action: "+ action);
		return mapping.findForward(action);
	}
}

/***
$Log: RadialProductionListAction.java,v $
Revision 1.5  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.4  2005/02/11 11:14:17  sponnusamy
Warnings removed

Revision 1.3  2005/02/03 05:09:15  vkrishnamoorthy
Log added.

***/