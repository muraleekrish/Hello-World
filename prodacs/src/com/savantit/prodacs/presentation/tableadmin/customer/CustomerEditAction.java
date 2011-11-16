/*
 * Created on Oct 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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

import com.savantit.prodacs.businessimplementation.customer.CustomerDetails;
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
public class CustomerEditAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		{
			String action = "failure";
			/* Creating instance to CustomerListForm.java */
			if (form instanceof CustomerEditForm)
			{
				CustomerEditForm frm = (CustomerEditForm) form;
			
				EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
				ActionErrors errors = new ActionErrors();
				try
				{
				/* 	Setting the JNDI name and Environment 	*/
				   obj.setJndiName("SessionCustomerDetailsManagerBean");
				   obj.setEnvironment();
	
				/* 	Creating the Home and Remote Objects 	*/
					SessionCustomerDetailsManagerHome customerHomeObj = (SessionCustomerDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionCustomerDetailsManagerHome.class);
					SessionCustomerDetailsManager custObj = (SessionCustomerDetailsManager)PortableRemoteObject.narrow(customerHomeObj.create(),SessionCustomerDetailsManager.class);
	
					if (frm.getFormAction().equalsIgnoreCase("view"))
					{
						action = "view";
					}else if (frm.getFormAction().equalsIgnoreCase("modify"))
					{
						/* Object to customerDetails */
						CustomerDetails objCustomerDetails = new CustomerDetails();
						
						objCustomerDetails = custObj.getCustomerDetails(frm.getId());
						
						frm.setCustomerName(objCustomerDetails.getCust_Name());
						frm.setCustomerType(Integer.toString(objCustomerDetails.getCust_Typ_Id()));
						frm.setCustomerInService((objCustomerDetails.getCust_Insrvce().equals("1"))?"on":"off");
						if (BuildConfig.DMODE)
							System.out.println("objCustomerDetails.getCust_Insrvce() : "+ objCustomerDetails.getCust_Insrvce());
						frm.setAddress1(objCustomerDetails.getCust_Addr1());
						frm.setAddress2(objCustomerDetails.getCust_Addr2());
						frm.setCity(objCustomerDetails.getCust_City());
						frm.setState(objCustomerDetails.getCust_State());
						frm.setPincode(objCustomerDetails.getCust_Pcode());
						frm.setCountry(objCustomerDetails.getCust_Country());
						frm.setFirstName(objCustomerDetails.getCust_Cntct_Fname());
						frm.setLastName(objCustomerDetails.getCust_Cntct_Lname());
						frm.setDesignation(objCustomerDetails.getCust_Cntct_Designation());
						frm.setPhoneLine1(objCustomerDetails.getCust_Phone1());
						frm.setExtension1(objCustomerDetails.getCust_Extension1());
						frm.setPhoneLine2(objCustomerDetails.getCust_Phone2());
						frm.setExtension2(objCustomerDetails.getCust_Extension2());
						frm.setPhoneLine3(objCustomerDetails.getCust_Phone3());
						frm.setExtension3(objCustomerDetails.getCust_Extension3());
						frm.setFax(objCustomerDetails.getCust_Fax());
						frm.setMobile(objCustomerDetails.getCust_Mobile());
						frm.setEmail(objCustomerDetails.getCust_Email());
						frm.setWebsite(objCustomerDetails.getCust_Website());
						
					}else if (frm.getFormAction().equalsIgnoreCase("update"))
					{
						if (BuildConfig.DMODE)
							System.out.println("Updation");
						
						/* object to CustomerDetails */
						CustomerDetails objCustomerDetails = new CustomerDetails();

						objCustomerDetails.setCust_Id(frm.getId());
						objCustomerDetails.setCust_Name(frm.getCustomerName());
						objCustomerDetails.setCust_Typ_Id(Integer.parseInt(frm.getCustomerType()));
						objCustomerDetails.setCust_Insrvce(((frm.getCustomerInService().equals(""))?"0":"1"));
						if (BuildConfig.DMODE)
							System.out.println("Up In Service: "+frm.getCustomerInService());
						objCustomerDetails.setCust_Addr1(frm.getAddress1());
						objCustomerDetails.setCust_Addr2(frm.getAddress2());
						objCustomerDetails.setCust_City(frm.getCity());
						objCustomerDetails.setCust_State(frm.getState());
						objCustomerDetails.setCust_Pcode(frm.getPincode());
						objCustomerDetails.setCust_Country(frm.getCountry());
						objCustomerDetails.setCust_Cntct_Fname(frm.getFirstName());
						objCustomerDetails.setCust_Cntct_Lname(frm.getLastName());
						System.out.println("Desgination :"+frm.getDesignation());
						objCustomerDetails.setCust_Cntct_Designation(frm.getDesignation());
						objCustomerDetails.setCust_Phone1(frm.getPhoneLine1());
						objCustomerDetails.setCust_Extension1(frm.getExtension1());
						objCustomerDetails.setCust_Phone2(frm.getPhoneLine2());
						objCustomerDetails.setCust_Extension2(frm.getExtension2());
						objCustomerDetails.setCust_Phone3(frm.getPhoneLine3());
						objCustomerDetails.setCust_Extension3(frm.getExtension3());
						objCustomerDetails.setCust_Fax(frm.getFax());
						objCustomerDetails.setCust_Mobile(frm.getFax());
						objCustomerDetails.setCust_Email(frm.getEmail());
						objCustomerDetails.setCust_Website(frm.getWebsite());
						
						boolean up = custObj.updateCustomerDetails(objCustomerDetails);
						
						if (up == true)
						{
							ActionMessages messages = new ActionMessages();
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.updated","Customer",frm.getCustomerName()));
							saveMessages(request,messages);
							action="success";
						}
						else
						{
							ActionMessages messages = new ActionMessages();
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.notupdated","Customer",frm.getCustomerName()));
							saveMessages(request,messages);
							action="success";				  			
						}			  	
						action="success";
					}
					else if (frm.getFormAction().equalsIgnoreCase("makeValid"))
					{
						if (BuildConfig.DMODE)
							System.out.println("Make Valid");
						
						String ids[] = frm.getIds();
						Vector v = new Vector();
						for (int i = 0; i < ids.length; i++)
						{
							v.add(new Integer(ids[i]));
						}
						HashMap hm = new HashMap();
						try
						{
							hm = custObj.makeCustomerValid(v);
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
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.valid", Integer.toString(success),"Customer"));
						else if(success>1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.valid", Integer.toString(success),"Customers"));					
						if(notFound == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Customer"));						
						}
						else if(notFound > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Customers"));
						}
						
						if(inUse == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Customer"));						
						}
						else if(inUse > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Customers"));
						}
						if(locked == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Customer"));						
						}
						else if(locked > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Customers"));
						}
						if(general == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Customer"));						
						}
						else if(general > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Customers"));
						}
						if(hm.size()<v.size())
						{
							int diff = v.size()-hm.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Customer"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Customers"));
						}									
						if(!errors.isEmpty())
							saveErrors(request,errors);					
						saveMessages(request,messages);		
						action = "success";

					}else if (frm.getFormAction().equalsIgnoreCase("makeInvalid"))
					{
						if (BuildConfig.DMODE)
							System.out.println("Make InValid");
						
						String ids[] = frm.getIds();
						Vector v = new Vector();
						for (int i = 0; i < ids.length; i++)
						{
							v.add(new Integer(ids[i]));
						}
						HashMap hm = new HashMap();
						try
						{
							hm = custObj.makeCustomerInValid(v);
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
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.invalid", Integer.toString(success),"Customer"));
						else if(success>1)					
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.messages.invalid", Integer.toString(success),"Customers"));					
						if(notFound == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.notExists", Integer.toString(notFound),"Customer"));						
						}
						else if(notFound > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.notExists", Integer.toString(notFound),"Customers"));
						}
						
						if(inUse == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inUse", Integer.toString(inUse),"Customer"));						
						}
						else if(inUse > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inUse", Integer.toString(inUse),"Customers"));
						}
						if(locked == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.locked", Integer.toString(locked),"Customer"));						
						}
						else if(locked > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.locked", Integer.toString(locked),"Customers"));
						}
						if(general == 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general", Integer.toString(general),"Customer"));						
						}
						else if(general > 1)
						{
							errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.general", Integer.toString(general),"Customers"));
						}
						if(hm.size()<v.size())
						{
							int diff = v.size()-hm.size();
							if(diff==1)
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.inValid.general", Integer.toString(diff),"Customer"));
							else
								errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.errors.inValid.general", Integer.toString(diff),"Customers"));
						}									
						if(!errors.isEmpty())
							saveErrors(request,errors);					
						saveMessages(request,messages);		
						action = "success";
					}
						
			   }catch(Exception e)
			  	{
			   		if (BuildConfig.DMODE)
			   			e.printStackTrace();
			  	}
			}
			if (BuildConfig.DMODE)
				System.out.println("CustomerEdit: "+ action);
			return mapping.findForward(action);
		}
}
/***
$Log: CustomerEditAction.java,v $
Revision 1.7  2005/08/17 09:12:33  vkrishnamoorthy
Fields added.

Revision 1.6  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.5  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.4  2005/02/01 04:11:07  vkrishnamoorthy
MessageIds changed appropriately for makeValid and makeInvalid.

Revision 1.3  2004/11/17 12:00:27  sponnusamy
Customer associated forms are modified

Revision 1.2  2004/11/16 15:39:52  sponnusamy
Customer pages are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/