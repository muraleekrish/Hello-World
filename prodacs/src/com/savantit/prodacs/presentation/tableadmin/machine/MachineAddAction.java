/*
 * Created on Nov 1, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.machine;

import java.util.GregorianCalendar;
import java.util.StringTokenizer;

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

import com.savantit.prodacs.businessimplementation.machine.MachineDetails;
import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.facade.SessionMachineDetailsManager;
import com.savantit.prodacs.facade.SessionMachineDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MachineAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping,	ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		String action = "failure";
		
		/* creating instance of MachineAddForm.java */
		if (form instanceof MachineAddForm)
		{
			MachineAddForm frm = (MachineAddForm)form;
			/* EJBLocator */
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			try
			{
			/* 	Setting the JNDI name and Environment 	*/
 			    obj.setJndiName("SessionMachineDetailsManagerBean");
			   	obj.setEnvironment();

			/* 	Creating the Home and Remote Objects 	*/
				SessionMachineDetailsManagerHome machineHomeObj = (SessionMachineDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionMachineDetailsManagerHome.class);
				SessionMachineDetailsManager machineObj = (SessionMachineDetailsManager)PortableRemoteObject.narrow(machineHomeObj.create(),SessionMachineDetailsManager.class);
			
				/* Setting the form values to Details Variables */
				MachineDetails objMachineDetails = new MachineDetails();
				
				objMachineDetails.setMach_Cde(frm.getMachineCode());
				objMachineDetails.setMach_Name(frm.getMachineName());
				objMachineDetails.setMach_Typ_Id(Integer.parseInt(frm.getMachineType().trim()));
				
				/* Date Conversion for InstallationDate */
				if (BuildConfig.DMODE)
					System.out.println("getInstallation: "+frm.getInstallationDate());
				if (!frm.getInstallationDate().equalsIgnoreCase(""))
				{
					StringTokenizer st = new StringTokenizer(frm.getInstallationDate(),"-");
					int yr = 0;
					int month = 0;
					int day = 0;
					if(st.hasMoreTokens())
					{
						yr = Integer.parseInt(st.nextToken().trim());
					}
					if(st.hasMoreTokens())
					{
						month = Integer.parseInt(st.nextToken().trim());
					}
					if(st.hasMoreTokens())
					{		
						day = Integer.parseInt(st.nextToken().trim());
					}
					GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);
					objMachineDetails.setMach_Install_Date(ge.getTime());
				}
				if (BuildConfig.DMODE)
					System.out.println("Date: "+objMachineDetails.getMach_Install_Date());
				
				objMachineDetails.setMach_InUse(((frm.getInUse().equals(""))?"0":"1"));
				if (BuildConfig.DMODE)
					System.out.println("Machine In-use: "+objMachineDetails.getMach_InUse());
				
					/* Supplier Details */
				objMachineDetails.setMach_SP_Name(frm.getSupplier());
				objMachineDetails.setMach_SP_Cntct_Person(frm.getSupplierContactPerson());
				objMachineDetails.setMach_SP_Addr(frm.getSupplierAddress());
				objMachineDetails.setMach_SP_Phone(frm.getSupplierPhoneNumber());
				
					/* Service Provider Details */
				objMachineDetails.setMach_SPLR_Name(frm.getServiceProvider());
				objMachineDetails.setMach_SPLR_Cntct_Person(frm.getServiceContactPerson());
				objMachineDetails.setMach_SPLR_Addr(frm.getServiceAddress());
				objMachineDetails.setMach_SPLR_Phone(frm.getServicePhoneNumber());
					
				boolean added = machineObj.addMachineDetails(objMachineDetails);
				
				if (added == true)
				{
					ActionMessages message = new ActionMessages();
					message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Machine",frm.getMachineName()));
					
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
			catch(MachineException e)
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
			System.out.println("MachineAdd: "+ action);
		return mapping.findForward(action);
	}

}
/***
$Log: MachineAddAction.java,v $
Revision 1.5  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.4  2005/02/16 10:03:47  vkrishnamoorthy
Modified for date set and get.

Revision 1.3  2005/01/25 07:29:43  sponnusamy
Installation Date Empty values adjusted

Revision 1.2  2004/11/19 13:26:42  sponnusamy
Machine Forms are completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/