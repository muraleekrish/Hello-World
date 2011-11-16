/*
 * Created on Nov 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.operationgroup;

import java.util.HashMap;
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

import com.savantit.prodacs.businessimplementation.job.JobException;
import com.savantit.prodacs.businessimplementation.job.OperationGroupDetails;
import com.savantit.prodacs.facade.SessionJobDetailsManager;
import com.savantit.prodacs.facade.SessionJobDetailsManagerHome;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.util.EJBLocator;


/**
 * @author sponnusamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class OperationGroupAddAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	{
		String action= "failure";
		/*Creating an instance of OperationGroupAddForm.java*/
		if (form instanceof OperationGroupAddForm)
		{
			OperationGroupAddForm frm = (OperationGroupAddForm)form;
			
			EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
			ActionErrors errors = new ActionErrors();
			try
			{
				/* 	Setting the JNDI name and Environment 	*/
				obj.setJndiName("SessionJobDetailsManagerBean");
		   		obj.setEnvironment();

		   		/* 	Creating the Home and Remote Objects 	*/
				SessionJobDetailsManagerHome jobHomeObj = (SessionJobDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionJobDetailsManagerHome.class);
				SessionJobDetailsManager opGpObj = (SessionJobDetailsManager)PortableRemoteObject.narrow(jobHomeObj.create(),SessionJobDetailsManager.class);
				try
				{
				/* Details Object */
				OperationGroupDetails objOpGrpDetails = new OperationGroupDetails();
				
				HashMap machineDetails = new HashMap();
				if (BuildConfig.DMODE)
					System.out.println("frm.getMachineName: "+frm.getMachineName());				
				StringTokenizer machDets = new StringTokenizer(frm.getMachineName(),"$");
				while (machDets.hasMoreTokens())
				{
					String mcName = machDets.nextToken();
					if (BuildConfig.DMODE)
						System.out.println("McName :"+mcName);
					machineDetails.put(mcName.trim(),"");
				}

				objOpGrpDetails.setOperationGroupCode(frm.getOperationGroupCode());
				if (BuildConfig.DMODE)
					System.out.println("-->"+frm.getMachDetsCheck());
				objOpGrpDetails.setMachineRelated((frm.getMachDetsCheck().equals("1"))?true:false);
				objOpGrpDetails.setHm_MachineDetails(machineDetails);
				
				boolean added = opGpObj.addOperationGroupDetails(objOpGrpDetails);
				if (added == true)
				{
					ActionMessages message = new ActionMessages();
					message.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("prodacs.common.message.added","Operation Group Code",frm.getOperationGroupCode()));
					
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
				catch (JobException je)
				{
					if (BuildConfig.DMODE)
						je.printStackTrace();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.name",je.getExceptionMessage()));
					if(!errors.isEmpty())
						saveErrors(request,errors);
					action = "failure";
				}
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
					if (BuildConfig.DMODE)
						e.printStackTrace();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("prodacs.common.error.general"));
					if(!errors.isEmpty())
						saveErrors(request,errors);
				}
				action = "failure";
			}
		}
		if (BuildConfig.DMODE)
			System.out.println("OperationGroup add:"+action);
		return mapping.findForward(action);
	}
}
/***
$Log: OperationGroupAddAction.java,v $
Revision 1.6  2005/07/26 11:11:56  vkrishnamoorthy
DMODE added to avid System.out.println()'s.

Revision 1.5  2005/07/14 12:23:57  vkrishnamoorthy
Machine related field added.

Revision 1.4  2005/03/16 06:32:47  vkrishnamoorthy
Extra spaces removed.

Revision 1.3  2005/03/03 07:01:21  vkrishnamoorthy
OperationGroupAdd modified.

Revision 1.2  2004/11/26 09:04:10  sponnusamy
Operation Group Forms are Completed

Revision 1.1  2004/11/05 07:56:53  sponnusamy
initial commit od prodacs

***/