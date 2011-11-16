/*
 * Created on Mar 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.customer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

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

import com.savantit.prodacs.businessimplementation.customer.CustomerTypDetails;
import com.savantit.prodacs.facade.SessionCustomerDetailsManager;
import com.savantit.prodacs.facade.SessionCustomerDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomerTypeEditAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		if (form instanceof CustomerTypeEditForm)
		{
			CustomerTypeEditForm frm = (CustomerTypeEditForm) form;
			EJBLocator obj = new EJBLocator(); // Object for EJBLocator
			ActionErrors errors = new ActionErrors();
			
			try
			{
				/*JNDI Name Setting and Creation*/
				
				obj.setJndiName("SessionCustomerDetailsManagerBean");
				obj.setEnvironment();
				
				//Creation of Home and Remote Objects
				SessionCustomerDetailsManagerHome homeObj = (SessionCustomerDetailsManagerHome) PortableRemoteObject.narrow(obj.getHome(),SessionCustomerDetailsManagerHome.class);
				SessionCustomerDetailsManager custObj = (SessionCustomerDetailsManager)PortableRemoteObject.narrow(homeObj.create(),SessionCustomerDetailsManager.class);
				
				if (frm.getFormAction().equalsIgnoreCase("view"))
				{
					action = "view";
				}
				else if (frm.getFormAction().equalsIgnoreCase("modify"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Customer Modification Starts");
					
					/* Object to CustomerDetails*/
					CustomerTypDetails custTypeDetails = new CustomerTypDetails();
					
					custTypeDetails = custObj.getCustomerTypeDetails(frm.getId());
					
					frm.setCustomerTypeName(custTypeDetails.getCust_Typ_Name());
					frm.setDescription(custTypeDetails.getCust_Typ_Desc());
					if (BuildConfig.DMODE)
						System.out.println("Customer Modification Ends.");
				}
				else if (frm.getFormAction().equalsIgnoreCase("update"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Updation Starts");
					
					CustomerTypDetails custTypeDetails = new CustomerTypDetails();
					custTypeDetails.setCust_Typ_Id(frm.getId());
					custTypeDetails.setCust_Typ_Name(frm.getCustomerTypeName());
					custTypeDetails.setCust_Typ_Desc(frm.getDescription());
					
					boolean updated = custObj.updateCustomerTypeDetails(custTypeDetails);
					
					if (updated == true)
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","Customer Type",frm.getCustomerTypeName()));
						saveMessages(request,messages);
						action="success";
					}
					else
					{
						ActionMessages messages = new ActionMessages();
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.notupdated","Customer Type",frm.getCustomerTypeName()));
						saveMessages(request,messages);
						action="success";				  			
					}			  	
					action="success";
					
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("Make Invalid");

					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = custObj.makeCustomerTypInValid(v);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
							System.out.println("Problem in MakeInvalid");
					}
					
					int success = 0;
					int notFound = 0;
					int inUse = 0;
					int locked = 0;
					int general = 0;
					for (Iterator it=hm.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 0)
						{
							success++;
						}
						else if(value == 1)
						{
							notFound++;
						}
						else if(value == 2)
						{
							inUse++;
						}
						else if(value == 3)
						{
							locked++;
						}						
						else if(value == 4)
						{
							general++;
						}												
					}
					ActionMessages messages = new ActionMessages();
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Customer Type"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Customer Types"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Customer Type"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Customer Types"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Customer Type"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Customer Types"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Customer Type"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Customer Types"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Customer Type"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Customer Types"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Customer Type"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Customer Types"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				else if (frm.getFormAction().equalsIgnoreCase("makeValid"))
				{
					if (BuildConfig.DMODE)
						System.out.println("MakeValid");

					String ids[] = frm.getIds();
					Vector v = new Vector();
					for (int i = 0; i < ids.length; i++)
					{
						v.add(new Integer(ids[i]));
					}
					HashMap hm = new HashMap();
					try
					{
						hm = custObj.makeCustomerTypValid(v);
					}catch (Exception e)
					{
						if (BuildConfig.DMODE)
						{
							System.out.println("Error on Customer Type MakeValid");
							e.printStackTrace();
						}
					}
					int success = 0;
					int notFound = 0;
					int inUse = 0;
					int locked = 0;
					int general = 0;
					for (Iterator it=hm.entrySet().iterator(); it.hasNext(); ) 
					{ 
						Map.Entry entry = (Map.Entry)it.next(); 
						int value = ((Integer)entry.getValue()).intValue();
						if(value == 0)
						{
							success++;
						}
						else if(value == 1)
						{
							notFound++;
						}
						else if(value == 2)
						{
							inUse++;
						}
						else if(value == 3)
						{
							locked++;
						}						
						else if(value == 4)
						{
							general++;
						}												
					}
					ActionMessages messages = new ActionMessages();
					if(success<=1)
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Customer Type"));
					else if(success>1)					
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Customer Types"));					
					if(notFound == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Customer Type"));						
					}
					else if(notFound > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Customer Types"));
					}
					
					if(inUse == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Customer Type"));						
					}
					else if(inUse > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Customer Types"));
					}
					if(locked == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Customer Type"));						
					}
					else if(locked > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Customer Types"));
					}
					if(general == 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Customer Type"));						
					}
					else if(general > 1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Customer Types"));
					}
					if(hm.size()<v.size())
					{
						int diff = v.size()-hm.size();
						if(diff==1)
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Customer Type"));
						else
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Customer Types"));
					}									
					if(!errors.isEmpty())
						saveErrors(request,errors);					
					saveMessages(request,messages);		
					action = "success";
				}
				
			}catch (Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("CustomerTypeEdit Action :"+action);
		return mapping.findForward(action);
	}
}


/***
$Log: CustomerTypeEditAction.java,v $
Revision 1.3  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.2  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.1  2005/03/06 09:45:33  vkrishnamoorthy
Initial commit of CustomerType.

***/