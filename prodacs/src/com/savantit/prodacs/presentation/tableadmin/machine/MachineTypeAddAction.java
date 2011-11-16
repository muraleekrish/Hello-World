/*
 * Created on Feb 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savantit.prodacs.presentation.tableadmin.machine;

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

import com.savantit.prodacs.businessimplementation.machine.MachineException;
import com.savantit.prodacs.businessimplementation.machine.MachineTypeDetails;
import com.savantit.prodacs.facade.SessionMachineDetailsManager;
import com.savantit.prodacs.facade.SessionMachineDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MachineTypeAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action = "failure";
		if (form instanceof MachineTypeAddForm)
		{
			MachineTypeAddForm frm = (MachineTypeAddForm) form;
			
			EJBLocator obj = new EJBLocator();
			ActionErrors errors = new ActionErrors();
			MachineTypeDetails machTypDetails = new MachineTypeDetails();
			
			try
			{
				obj.setJndiName("SessionMachineDetailsManagerBean");
				obj.setEnvironment();
				
				SessionMachineDetailsManagerHome homeObj = (SessionMachineDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionMachineDetailsManagerHome.class);
				SessionMachineDetailsManager remoteObj = (SessionMachineDetailsManager)PortableRemoteObject.narrow(homeObj.create(),SessionMachineDetailsManager.class);
				
				machTypDetails.setMc_Typ_Name(frm.getMachineTypeName());
				machTypDetails.setMc_Typ_Desc(frm.getDescription());
				try
				{
					int result = remoteObj.addMachineTypeDetails(machTypDetails);
					if (BuildConfig.DMODE)
						System.out.println("Machine Added");

					if (result > 0)
					{
						ActionMessages message = new ActionMessages();
						message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Machine Type",frm.getMachineTypeName()));
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
					errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("prodacs.common.error.name",e.getExceptionMessage()));
					if(!errors.isEmpty())
					{
						saveErrors(request,errors);
					}
					action = "failure";
				}
			}
			catch(Exception e)
			{
				if (BuildConfig.DMODE)
					e.printStackTrace();
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("Machine Type Add Action :"+action);
		return mapping.findForward(action);
	}
}

/***
$Log: MachineTypeAddAction.java,v $
Revision 1.6  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.5  2005/07/12 11:49:10  kduraisamy
imports organized

Revision 1.4  2005/03/12 05:06:39  vkrishnamoorthy
Messages added appropriately.

Revision 1.3  2005/03/08 07:20:14  vkrishnamoorthy
Modified according to MachineTypeAdd.

Revision 1.2  2005/02/17 07:26:19  vkrishnamoorthy
Modified as per NewMachineType adding.

Revision 1.1  2005/02/03 04:44:56  vkrishnamoorthy
Initial commit on MachineTypeAddAction.

***/