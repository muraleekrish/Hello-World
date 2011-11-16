/*
 * Created on Mar 6, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.machine;

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
public class MachineTypeListAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		if (form instanceof MachineTypeListForm)
		{
			action = "success";
		}
		if (BuildConfig.DMODE)
			System.out.println("MachineTypeList action :"+action);
		return mapping.findForward(action);
	}
}

/***
$Log: MachineTypeListAction.java,v $
Revision 1.2  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.1  2005/03/08 07:19:38  vkrishnamoorthy
initial commit on MachineType.

***/