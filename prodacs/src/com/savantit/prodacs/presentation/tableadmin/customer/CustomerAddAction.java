/*
 * Created on Oct 28, 2004
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

import com.savantit.prodacs.businessimplementation.customer.CustomerDetails;
import com.savantit.prodacs.businessimplementation.customer.CustomerException;
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
public class CustomerAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		{
			String action = "failure";
			
			if (form instanceof CustomerAddForm)
			{
				/* Creating instance to CustomerListForm.java */
				CustomerAddForm frm = (CustomerAddForm) form;
				
				/* EJBLocator */
				EJBLocator obj = new EJBLocator();
				ActionErrors errors = new ActionErrors();
				try
				{
				/* 	Setting the JNDI name and Environment 	*/
  				    obj.setJndiName("SessionCustomerDetailsManagerBean");
				   	obj.setEnvironment();

				/* 	Creating the Home and Remote Objects 	*/
					SessionCustomerDetailsManagerHome customerHomeObj = (SessionCustomerDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionCustomerDetailsManagerHome.class);
					SessionCustomerDetailsManager custObj = (SessionCustomerDetailsManager)PortableRemoteObject.narrow(customerHomeObj.create(),SessionCustomerDetailsManager.class);
					
					/* Setting the form values to Details Variables */
					CustomerDetails objCustDet = new CustomerDetails();
					
					 objCustDet.setCust_Name(frm.getCustomerName().trim());
					 objCustDet.setCust_Typ_Id(Integer.parseInt(frm.getCustomerType().trim()));
					 objCustDet.setCust_Insrvce(((frm.getCustomerInService().equals(""))?"0":"1"));
					 if (BuildConfig.DMODE)
					 	System.out.println("in service:"+frm.getCustomerInService());
					 objCustDet.setCust_Addr1(frm.getAddress1().trim());
					 objCustDet.setCust_Addr2(frm.getAddress2().trim());
					 objCustDet.setCust_City(frm.getCity().trim());
					 objCustDet.setCust_State(frm.getState().trim());
					 objCustDet.setCust_Pcode(frm.getPincode().trim());
					 objCustDet.setCust_Country(frm.getCountry().trim());
					 objCustDet.setCust_Cntct_Fname(frm.getFirstName().trim());
					 objCustDet.setCust_Cntct_Lname(frm.getLastName().trim());
					 objCustDet.setCust_Cntct_Designation(frm.getDesignation().trim());
					 objCustDet.setCust_Phone1(frm.getPhoneLine1().trim());
					 objCustDet.setCust_Extension1(frm.getExtension1().trim());
					 objCustDet.setCust_Phone2(frm.getPhoneLine2().trim());
					 objCustDet.setCust_Extension2(frm.getExtension2().trim());
					 objCustDet.setCust_Phone3(frm.getPhoneLine3().trim());
					 objCustDet.setCust_Extension3(frm.getExtension3().trim());
					 objCustDet.setCust_Mobile(frm.getMobile().trim());
					 objCustDet.setCust_Email(frm.getEmail().trim());
					 objCustDet.setCust_Website(frm.getWebsite().trim());
					 objCustDet.setCust_Fax(frm.getFax().trim());
					
					boolean added = custObj.addCustomerDetails(objCustDet);
					
					if (added == true)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Customer",frm.getCustomerName()));
						
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
				catch(CustomerException e)
				{
					if (BuildConfig.DMODE)
						e.printStackTrace();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
					if(!errors.isEmpty())
						saveErrors(request,errors);
					action = "failure";
				}
				catch(Exception e)
				{
					if (BuildConfig.DMODE)
						e.printStackTrace();
					if(e.toString().toLowerCase().indexOf("parent key not found")!=-1)
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.parentKey.notFound"));
						if(!errors.isEmpty())
							saveErrors(request,errors);				
					}
					else
					{
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
						if(!errors.isEmpty())
							saveErrors(request,errors);
					}
					action = "failure";
				}
			
			}
			if (BuildConfig.DMODE)
				System.out.println("CustomerAdd: "+ action);
			return mapping.findForward(action);
		}
}
/***
$Log: CustomerAddAction.java,v $
Revision 1.7  2005/08/17 08:02:48  vkrishnamoorthy
Fields added.

Revision 1.6  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.5  2005/05/16 07:42:55  vkrishnamoorthy
Print Statements commented.

Revision 1.4  2005/02/12 09:22:58  vkrishnamoorthy
MessageId name changed.

Revision 1.3  2004/11/17 12:00:27  sponnusamy
Customer associated forms are modified

Revision 1.2  2004/11/16 15:39:52  sponnusamy
Customer pages are completed

Revision 1.1  2004/11/05 07:41:12  sponnusamy
initial commit od prodacs

***/