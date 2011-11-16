/*
 * Created on Oct 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.employee;

/*
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.rmi.PortableRemoteObject;
*/

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
public class EmployeeTypeListAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		{
			String action = "failure";
			if (BuildConfig.DMODE)
				System.out.println("EmployeeTypeList : "+ action);
			return mapping.findForward(action);
		}
}
/***
$Log: EmployeeTypeListAction.java,v $
Revision 1.4  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.3  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.2  2004/11/10 12:23:42  sponnusamy
EmployeeType  related form and actions

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/