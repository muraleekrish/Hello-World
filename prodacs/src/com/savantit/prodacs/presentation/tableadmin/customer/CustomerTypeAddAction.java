/*
 * Created on Jan 25, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.customer;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.savantit.prodacs.businessimplementation.customer.CustomerException;
import com.savantit.prodacs.businessimplementation.customer.CustomerTypDetails;
import com.savantit.prodacs.facade.SessionCustomerDetailsManager;
import com.savantit.prodacs.facade.SessionCustomerDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomerTypeAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		{
			String action = "failure";
			/* Creating instance to CustomerTypeAddForm.java */
			if (BuildConfig.DMODE)
				System.out.println("Customer Type Add Action");
			if (form instanceof CustomerTypeAddForm)
			{
				CustomerTypeAddForm frm = (CustomerTypeAddForm) form;
			
				EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
				ActionErrors errors = new ActionErrors();
				CustomerTypDetails objCustomerTypDetails = new CustomerTypDetails();
				try
				{
				/* 	Setting the JNDI name and Environment 	*/
					obj.setJndiName("SessionCustomerDetailsManagerBean");
				   	obj.setEnvironment();
	
				/* 	Creating the Home and Remote Objects 	*/
					SessionCustomerDetailsManagerHome customerHomeObj = (SessionCustomerDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionCustomerDetailsManagerHome.class);
					SessionCustomerDetailsManager custObj = (SessionCustomerDetailsManager)PortableRemoteObject.narrow(customerHomeObj.create(),SessionCustomerDetailsManager.class);
					
					objCustomerTypDetails.setCust_Typ_Name(frm.getCustomerTypeName());
					objCustomerTypDetails.setCust_Typ_Desc(frm.getDescription());
					try
					{
						int result = custObj.addCustomerTypDetails(objCustomerTypDetails);
						if (BuildConfig.DMODE)
							System.out.println("ID Added");
						
						if (result > 0)
						{
							ActionMessages message = new ActionMessages();
							message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Customer Type",frm.getCustomerTypeName()));
							
							saveMessages(request,message);
							action="success";
						}
						else
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
							if(!errors.isEmpty())
								saveErrors(request,errors);
							action="failure";				  			
						}
					}
					catch (CustomerException e)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
						if(!errors.isEmpty())
							saveErrors(request,errors);
						action = "failure";
					}
				}
				catch (Exception e)
				{
					if (BuildConfig.DMODE)
						e.printStackTrace();
				}
			}
			if (BuildConfig.DMODE)
				System.out.println("Customer Type: "+ action);
			return mapping.findForward(action);
		}
}

/***
$Log: CustomerTypeAddAction.java,v $
Revision 1.6  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.5  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.4  2005/03/12 05:06:39  vkrishnamoorthy
Messages added appropriately.

Revision 1.3  2005/03/06 09:46:17  vkrishnamoorthy
Initial commit of CustomerType.

Revision 1.2  2005/02/16 10:02:10  vkrishnamoorthy
Modified according to CutomerType Add.

Revision 1.1  2005/01/25 12:38:28  sponnusamy
New Customertype Added

***/
